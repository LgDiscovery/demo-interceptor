package com.logo.demo.inteceptor.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class YmlUtils {

    private static Logger logger = LoggerFactory.getLogger(YmlUtils.class);

    public static Map<String,Object> map;

    static {
        try {
            readYmlFile("config.yml");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("读取yml配置文件失败");
        }
    }

    public static void main(String[] args) {
        System.out.println(map.get("username"));
        System.out.println(map.get("password"));
        System.out.println(map.get("age"));
    }

    private static void readYmlFile(String filePath) throws Exception {
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        Yaml yaml = new Yaml();
        map = (Map<String, Object>) yaml.load(resourceAsStream);
    }
}
