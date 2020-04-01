package com.logo.demo.inteceptor.utils;

import org.springframework.util.Assert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class PropUtils {

    private  static Properties properties;

    public static void main(String[] args) throws Exception {
        readPropertiesFile("config.properties");
        System.out.println(String.valueOf(properties.getProperty("username")));
        System.out.println(String.valueOf(properties.getProperty("password")));
        System.out.println(Integer.valueOf(properties.getProperty("age")));
    }

    public static void readPropertiesFile(String filePath) throws Exception {
        InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
        Properties properties = new Properties();
        properties.load(resourceAsStream);
        PropUtils.properties = properties;
    }



}
