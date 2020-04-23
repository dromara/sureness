package com.usthe.sureness.provider.ducument;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 资源文件里内容的操作类
 * @author tomsun28
 * @date 21:41 2019-05-24
 */
public class DocumentResourceAccess {

    private static final String DEFAULT_FILE_NAME = "sureness.yml";

    /** 默认的资源文件名称位置 sureness.yml **/
    private static String yamlFileName;

    static {
        yamlFileName = DEFAULT_FILE_NAME;
    }

    /**
     * 从配置文件里读取resource配置信息
     * @return 配置实体对象
     * @throws IOException 文件不存在或者读取文件异常时
     */
    public static DocumentResourceEntity loadConfig() throws IOException {
        Yaml yaml = new Yaml();
        InputStream inputStream = DocumentResourceAccess.class.getClassLoader().getResourceAsStream(yamlFileName);
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
    }

    /**
     * config file path name
     * @param fileName 文件路径名称
     */
    public static void setYamlName(String fileName) {
        yamlFileName = fileName;
    }
}
