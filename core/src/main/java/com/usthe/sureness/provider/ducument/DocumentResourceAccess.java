package com.usthe.sureness.provider.ducument;

import org.yaml.snakeyaml.Yaml;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.StandardCharsets;

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
            throw new FileNotFoundException("sureness file: " + DEFAULT_FILE_NAME + " not found, " +
                    "please create the file if you need config resource");
        }
        return yaml.loadAs(inputStream, DocumentResourceEntity.class);
    }

    /**
     * 将更新的信息写入到配置文件中
     * @param entity 写入的内容实体
     * @throws IOException 文件不存在或者读写异常时
     */
    public static void dumpConfig(DocumentResourceEntity entity) throws IOException {
        Yaml yaml = new Yaml();
        URL url = DocumentResourceAccess.class.getClassLoader().getResource(yamlFileName);
        if (url == null) {
            throw new FileNotFoundException("sureness file: " + DEFAULT_FILE_NAME + " not found, " +
                    "please create the file if you need config resource");
        }
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(url.getPath()), StandardCharsets.UTF_8)) {
            yaml.dump(entity, writer);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public static void setYamlName(String name) {
        yamlFileName = name;
    }
}
