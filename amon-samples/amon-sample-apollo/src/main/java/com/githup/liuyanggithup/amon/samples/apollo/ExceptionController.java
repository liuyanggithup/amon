package com.githup.liuyanggithup.amon.samples.apollo;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.githup.liuyanggithup.amon.exception.AmonException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 程序异常统一返回
 *
 * @author xingfeng
 * @date 2018/10/26
 */

@ControllerAdvice
public class ExceptionController {

    private static final Logger log = LoggerFactory.getLogger("ERROR_LOG");

    @ExceptionHandler(value = AmonException.class)
    @ResponseBody
    public String bizException(AmonException amonException) {
        log.error("occur an amonException: ", amonException);
        return amonException.getMessage();
    }

}
