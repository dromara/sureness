package com.usthe.sureness.provider.ducument;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Operation content in resource document file - sureness.yml
 * @author tomsun28
 * @date 21:41 2019-05-24
 */
public class DocumentResourceAccess {

    private static final String DEFAULT_FILE_NAME = "sureness.yml";

    /** default resource document file name - sureness.yml **/
    private static String yamlFileName;

    static {
        yamlFileName = DEFAULT_FILE_NAME;
    }

    /**
     * load config information form document
     * @return config object
     * @throws IOException When the file does not exist or the file is read abnormally
     */
    public static DocumentResourceEntity loadConfig() throws IOException {
        return loadConfig(yamlFileName);
    }

    /**
     * load config information form document
     * @param yamlFileName yml file name
     * @return config object
     * @throws IOException When the file does not exist or the file is read abnormally
     */
    public static DocumentResourceEntity loadConfig(String yamlFileName) throws IOException {
        Yaml yaml = new Yaml();
        InputStream inputStream = null;
        try {
            // 1. classpath：/config/
            // 2. classpath: /
            inputStream = DocumentResourceAccess.class.getClassLoader().getResourceAsStream("config/" + yamlFileName);
            if (inputStream == null) {
                inputStream = DocumentResourceAccess.class.getClassLoader().getResourceAsStream(yamlFileName);
            }
            if (inputStream == null) {
                inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(yamlFileName);
            }
            if (inputStream == null) {
                File yamlFile = new File(yamlFileName);
                if (yamlFile.exists()) {
                    try (FileInputStream fileInputStream = new FileInputStream(yamlFile)) {
                        return yaml.loadAs(fileInputStream, DocumentResourceEntity.class);
                    } catch (IOException e) {
                        throw new IOException(e);
                    }
                }
            }
            if (inputStream == null) {
                throw new FileNotFoundException("sureness file: " + DEFAULT_FILE_NAME + " not found, " +
                        "please create the file if you need config resource");
            }
            return yaml.loadAs(inputStream, DocumentResourceEntity.class);
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    /**
     * config file path name
     * @param fileName file path name
     */
    public static void setYamlName(String fileName) {
        yamlFileName = fileName;
    }
}
