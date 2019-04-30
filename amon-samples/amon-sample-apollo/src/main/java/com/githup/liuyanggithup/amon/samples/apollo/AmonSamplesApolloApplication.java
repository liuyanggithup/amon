package com.githup.liuyanggithup.amon.samples.apollo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.githup.liuyanggithup.amon"})
public class AmonSamplesApolloApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmonSamplesApolloApplication.class, args);
    }

}
