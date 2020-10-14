package com.boss.cloud.conf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: lpb
 * @create: 2020-08-13 17:33
 */
@ControllerAdvice
@Slf4j
public class ExceptionCatch {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String exc(Exception e){
        log.info(e.getMessage());
        e.printStackTrace();
        return "error";
    }
}
