package com.boss.auth.exception;

import com.boss.framework.model.response.CommonCode;
import com.boss.framework.model.response.ResponseResult;
import com.boss.framework.model.response.ResultCode;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionCatch {

    private final Logger LOGGER = LoggerFactory.getLogger(ExceptionCatch.class);

    private ImmutableMap<Class<? extends Throwable>, ResultCode> EXCEPTIONS;

    public static ImmutableMap.Builder<Class<? extends Throwable>,ResultCode> builder = ImmutableMap.builder();

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult systemException(Exception e){
        if(EXCEPTIONS == null){
           EXCEPTIONS = builder.build();
        }
        ResultCode resultCode = EXCEPTIONS.get(e.getClass());
        if(resultCode != null){
            return new ResponseResult(resultCode);
        }
        LOGGER.error("catch exception info:{}",e.getMessage());
        e.printStackTrace();
        return new ResponseResult(CommonCode.SERVER_ERROR);
    }

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult customException(CustomException e){
        ResponseResult responseResult = new ResponseResult(e.getResultCode());
        return responseResult;
    }

    static{
        builder.put(org.springframework.http.converter.HttpMessageNotReadableException.class, CommonCode.INVLIDATE);
        //除了CustomException以外的异常类型及对应的错误代码在这里定义,，如果不定义则统一返回固定的错误信息
    }

}
