package com.boss.cloud.conf;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.UndeclaredThrowableException;

/**
 * @author: lpb
 * @create: 2020-08-13 17:33
 */
@ControllerAdvice
@Slf4j
public class ExceptionCatch {

    @ExceptionHandler(ParamFlowException.class)
    @ResponseBody
    public String paramExe(ParamFlowException e){
        log.info("error:{}",e.getClass().getSimpleName());
        e.printStackTrace();
        return "error";
    }

    @ExceptionHandler(UndeclaredThrowableException.class)
    @ResponseBody
    public String blockExe(UndeclaredThrowableException e){
        if(ParamFlowException.class.equals(e.getUndeclaredThrowable().getClass())){
            log.info("error:{}");
            e.printStackTrace();
            return "error："+e.getUndeclaredThrowable().getClass().getSimpleName();
        }
        return "error";
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String exc(Exception e){
        log.info(e.getClass().getSimpleName());
        e.printStackTrace();
        return "error";
    }
}
