package com.logo.demo.inteceptor.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(value = "com.logo.demo.inteceptor.mbg")
public class MybatisConfig {
}
