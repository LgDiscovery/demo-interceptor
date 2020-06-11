package com.logo.demo.inteceptor.huTool;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class HuToolUtils {

    private static Logger LOGGER = LoggerFactory.getLogger(HuToolUtils.class);

    public static void main(String[] args) throws IOException {
        int a = 1;
        Convert.toStr(a);
        String[] b = {"1","2","3","4"};
        Integer[] integers = Convert.toIntArray(b);
        List<String> strings = Convert.toList(String.class, b);
        for (Integer i:integers){
            System.out.println(i);
        }
        System.out.println();
        Arrays.asList(integers).forEach(System.out::println);

        String str = "test";
        StrUtil.isEmpty(str);
        System.out.println(StrUtil.addPrefixIfNot(str, "kkk-"));
        System.out.println(StrUtil.removePrefix(str, "kkk-"));

        String template = "这只是个占位符：{}";
        System.out.println(StrUtil.format(template, "我是占位符"));

        HuToolUtils.class.getResourceAsStream("generator.properties");
        ClassPathResource resource = new ClassPathResource("generator.properties");
        Properties properties = new Properties();
        properties.load(resource.getStream());
        LOGGER.info("/classPath:{}",properties);

        Method[] method = ReflectUtil.getMethods(HuToolUtils.class);

        HuToolUtils huToolUtils = ReflectUtil.newInstance(HuToolUtils.class);

        double n1 = 1.234;
        double n2 = 1.234;
        double result;
        result = NumberUtil.add(n1,n2);
        result = NumberUtil.sub(n1, n2);
        result = NumberUtil.mul(n1,n2);
        result = NumberUtil.div(n1,n2);


    }
}
