package com.githup.liuyanggithup.amon.samples.apollo.controller;

import com.githup.liuyanggithup.amon.Limiter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: xia_xun
 * @Date: 2019/4/30
 * @description:
 */
@RestController
@RequestMapping(value = "/hello")
public class HelloController {

    @RequestMapping(value = "amon")
    @Limiter(name = "amon.test.amon",blockStrategy = true,blockMsg = "amon limiter")
    public String amon() {
        return "hello";
    }


}
