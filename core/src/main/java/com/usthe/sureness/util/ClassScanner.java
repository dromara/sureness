package com.usthe.sureness.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.regex.Pattern;

/**
 * A util class used to scan all classes in some packages, include all child
 * folders and jars inSlide of packages' path.
 *
 * @author cent
 * @email 292462859@qq.com
 * @date 2019/1/8.
 *
 * @author Yong(shrink, downgrade to Java7)
 * @since 2.5.0
 *
 * @author tomsun28(fix code style)
 */
public enum ClassScanner {
    ;
    public enum ProtocolTypes {
        /**
         * http type
         */
        http,
        /**
         * https type
         */
        https,
        /**
         * file type
         */
        file,
        /**
         * jar type
         */
        jar
    }

    private static final Logger log = LoggerFactory.getLogger(ClassScanner.class);
    private static final Character STAR_CHAR = '*';
    private static final String STAR_STR = "*";

    /**
     * Find all classes in packages 扫描一或多个包下的所有Class，包含接口类
     *
     * @param scanBasePackages scan pages
     * @return Classes
     */
    public static List<Class<?>> scanPackages(String... scanBasePackages) {
        List<Class<?>> classList = new LinkedList<>();
        if (scanBasePackages.length == 0) {
            return classList;
        }
        for (String pkg : scanBasePackages) {
            if (pkg != null && pkg.length() != 0) {
                classList.addAll(ClassScanner.scanOnePackage(pkg));
            }
        }
        return classList;
    }

    /**
     * Find all classes in packages
     *
     * @param scanBasePackages package
     * @return classes
     */
    public static List<Class<?>> scanPackages(List<String> scanBasePackages) {
        List<Class<?>> classList = new LinkedList<>();
        if (scanBasePackages.size() == 0) {
            return classList;
        }
        for (String pkg : scanBasePackages) {
            if (pkg != null && pkg.length() != 0) {
                classList.addAll(ClassScanner.scanOnePackage(pkg));
            }
        }
        return classList;
    }

    /**
     * Find all classes with given annotation in packages
     *
     * @param annotation annotation
     * @param scanBasePackages package
     * @return classes
     */
    public static List<Class<?>> scanByAnnotation(Class<? extends Annotation> annotation, String... scanBasePackages) {
        List<Class<?>> classList = scanPackages(scanBasePackages);
        List<Class<?>> result = new ArrayList<>();
        for (Class<?> clz : classList) {
            Annotation clzAnnotation = clz.getAnnotation(annotation);
            if (clzAnnotation != null) {
                result.add(clz);
            }
        }
        return result;
    }

    /**
     * Find all classes with given name patten
     *
     * @param nameSimpleReg name patten, only 1 * allow
     * @param scanBasePackages scan package
     * @return classes
     */
    public static List<Class<?>> scanByName(String nameSimpleReg, String... scanBasePackages) {
        List<Class<?>> classList = scanPackages(scanBasePackages);
        List<Class<?>> result = new ArrayList<>();
        for (Class<?> clz : classList) {
            if (nameMatch(nameSimpleReg, clz.getName())) {
                result.add(clz);
            }
        }
        return result;
    }

    /**
     * find all classes in one package
     *
     * @param pkg package
     * @return Class
     */
    private static List<Class<?>> scanOnePackage(String pkg) {
        List<Class<?>> classList = new LinkedList<>();
        try {
            String pathName = package2Path(pkg);
            Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(pathName);
            classList = scanUrls(pkg, urls);
        } catch (IOException e) {
            log.error("Warning: Can not scan package：{}", pkg);
        }

        return classList;
    }

    /**
     * find all classes in urls
     *
     * @param pkg package
     * @param urls urls
     * @return Class
     * @throws IOException when io error
     */
    private static List<Class<?>> scanUrls(String pkg, Enumeration<URL> urls) throws IOException {
        List<Class<?>> classList = new LinkedList<>();
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            String protocol = url.getProtocol();

            if (ProtocolTypes.file.name().equals(protocol)) {
                String path = URLDecoder.decode(url.getFile(), "UTF-8");
                classList.addAll(recursiveScan4Path(pkg, path));

            } else if (ProtocolTypes.jar.name().equals(protocol)) {
                String jarPath = getJarPathFormUrl(url);
                classList.addAll(recursiveScan4Jar(pkg, jarPath));
            }
        }
        return classList;
    }

    /**
     * get real path from url
     * eg: jar:file:/Users/cent/.gradle/caches/modules-2/files-2.1/org/lombok/1.18.4/7103ab51/lombok-1.18.4.jar!/org
     *
     * @param url url
     * @return file
     */
    private static String getJarPathFormUrl(URL url) {
        String file = url.getFile();
        return file.substring(0, file.lastIndexOf("!")).replaceFirst("file:", "");
    }

    /**
     * recursive scan for path
     *
     * @param pkg package
     * @param filePath path
     * @return Class list
     */
    private static List<Class<?>> recursiveScan4Path(String pkg, String filePath) {
        List<Class<?>> classList = new LinkedList<>();

        File file = new File(filePath);
        if (!file.exists() || !file.isDirectory()) {
            return classList;
        }

        File[] classes = file.listFiles(child -> isClass(child.getName()));
        if (classes != null) {
            for (File child : classes) {
                String className = classFile2SimpleClass(
                        pkg + "." + child.getName());

                try {
                    Class<?> clz = Thread.currentThread().getContextClassLoader().loadClass(className);
                    classList.add(clz);
                } catch (ClassNotFoundException | LinkageError e) {
                    log.error("Warning: Can not load class: {}", className);
                }

            }
        }

        File[] dirs = file.listFiles(File::isDirectory);
        if (dirs != null) {
            for (File child : dirs) {
                String childPackageName = pkg + "." + child.getName();
                String childPath = filePath + "/" + child.getName();
                classList.addAll(recursiveScan4Path(childPackageName, childPath));
            }
        }
        return classList;
    }

    /**
     * Recursive scan 4 jar
     *
     * @param pkg package
     * @param jarPath jar path
     * @return Class list
     * @throws IOException when io error
     */
    private static List<Class<?>> recursiveScan4Jar(String pkg, String jarPath) throws IOException {
        List<Class<?>> classList = new LinkedList<>();

        try (JarInputStream jin = new JarInputStream(new FileInputStream(jarPath))) {
            JarEntry entry = jin.getNextJarEntry();
            while (entry != null) {
                String name = entry.getName();
                entry = jin.getNextJarEntry();

                if (!name.contains(package2Path(pkg))) {
                    continue;
                }
                if (isClass(name)) {
                    if (isAnonymousInnerClass(name)) {
                        continue;
                    }

                    String className = classFile2SimpleClass(path2Package(name));
                    try {
                        Class<?> clz = Thread.currentThread().getContextClassLoader().loadClass(className);
                        classList.add(clz);
                    } catch (ClassNotFoundException | LinkageError e) {
                        log.error("Warning: Can not load class: {}", className);
                    }
                }
            }
        }
        return classList;
    }

    /**
     * A simple string matcher, only 1 * allowed, but many regex string can
     * split by "|" char, for example: <br/>
     * "abc.ef*|*gh|ijk*lmn" matches "abc.efxxx", "xxxgh","ijkxxxlmn"
     */
    public static boolean nameMatch(String regex, String name) {
        if (regex == null || regex.length() == 0 || name == null || name.length() == 0) {
            return false;
        }
        do {
            int i = regex.indexOf('|');
            if (i < 0) {
                return doSingleNameMatch(regex, name);
            }
            if (doSingleNameMatch(regex.substring(0, i), name)) {
                return true;
            }
            regex = regex.substring(i + 1);
        } while (true);
    }

    /** A simple matcher, only 1 * allowed represents any string */
    private static boolean doSingleNameMatch(String regex, String name) {
        if (regex == null || regex.length() == 0 || name == null || name.length() == 0) {
            return false;
        }
        if (STAR_CHAR == (regex.charAt(0))) {
            return name.endsWith(regex.substring(1));
        } else if (regex.endsWith(STAR_STR)) {
            return name.startsWith(regex.substring(0, regex.length() - 1));
        } else {
            int starPos = regex.indexOf('*');
            if (-1 == starPos) {
                return regex.equals(name);
            }
            return name.startsWith(regex.substring(0, starPos)) && name.endsWith(regex.substring(starPos + 1));
        }
    }

    /**
     * ===== Inside used static tool methods =====
     */
    private static final Pattern ANONYMOUS_INNER_CLASS_PATTERN = Pattern.compile("^[\\s\\S]*\\${1}\\d+\\.class$");

    private static String package2Path(String packageName) {
        return packageName.replace(".", "/");
    }

    private static String path2Package(String pathName) {
        return pathName.replaceAll("/", ".");
    }

    private static boolean isClass(String fileName) {
        if (fileName == null || fileName.length() == 0) {
            return false;
        }
        return fileName.endsWith(".class");
    }

    private static String classFile2SimpleClass(String classFileName) {
        return classFileName.replace(".class", "");
    }

    private static boolean isAnonymousInnerClass(String className) {
        return ANONYMOUS_INNER_CLASS_PATTERN.matcher(className).matches();
    }

}