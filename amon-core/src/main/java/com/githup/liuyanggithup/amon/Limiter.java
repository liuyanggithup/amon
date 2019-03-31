package com.githup.liuyanggithup.amon;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Limiter {


    String name();

    double rate() default 100.0;

    String blockMsg() default "apollo limiter block";


}
