package com.logo.demo.inteceptor.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义一个注解，定义此注解的主要目的是把它添加在需要实现幂等的方法上，
 * 凡是某个方法注解了它，都会实现自动幂等。后台利用反射如果扫描到这个注解，
 * 就会处理这个方法实现自动幂等，
 * 使用元注解ElementType.METHOD表示它只能放在方法上，
 * etentionPolicy.RUNTIME表示它在运行时
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoIdempotent {
}
