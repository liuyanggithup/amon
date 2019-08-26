package com.githup.liuyanggithup.amon;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: seventeen
 * @Date: 2019/4/5
 * @description:
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Limiter {


    String name();

    double rate() default 100.0;

    int permits() default 60;

    boolean blockStrategy() default true;

    String blockMsg() default "amon limiter block";


}
