package com.logo.demo.inteceptor.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

public class PropUtils {

    private static Logger logger = LoggerFactory.getLogger(PropUtils.class);

    public static Properties properties;

    static {
        try {
            readPropertiesFile("config.properties");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("读取properties文件失败");
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(String.valueOf(properties.getProperty("username")));
        System.out.println(String.valueOf(properties.getProperty("password")));
        System.out.println(Integer.valueOf(properties.getProperty("age")));
    }

    private static void readPropertiesFile(String filePath) throws Exception {
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        Properties properties = new Properties();
        properties.load(resourceAsStream);
        PropUtils.properties = properties;
    }



}
