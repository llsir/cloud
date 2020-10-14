package com.boss.framework.aop;

import com.boss.framework.annotation.SystemLog;
import com.boss.framework.conf.MQStream;
import com.boss.framework.enums.SystemLogEnum;
import com.boss.framework.model.logs.SysLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author: lpb
 * @create: 2020-07-22 09:24
 */
@Component
@Slf4j
@Aspect
public class SysLogAspect {

    @Autowired
    MQStream mqStream;

    @Pointcut(value = "@annotation(com.boss.framework.annotation.SystemLog)")
    public void logPointcut(){}

    @After("logPointcut()")
    public void afterLog(JoinPoint joinPoint){
        SysLog sysLog = wrapSysLog(joinPoint);
        log.info("Log信息："+sysLog);
        mqStream.logOutput().send(MessageBuilder.withPayload(sysLog).build());
    }

    @AfterThrowing(value = "logPointcut()",throwing = "e")
    public void throwingLog(JoinPoint joinPoint,Exception e){
        SysLog sysLog = wrapSysLog(joinPoint);
        sysLog.setType(SystemLogEnum.THROW_LOG.getType());
        sysLog.setMessage(sysLog.getMessage()+"==="+e);
        log.info("异常Log信息："+sysLog);
        mqStream.logOutput().send(MessageBuilder.withPayload(sysLog).build());
    }

    private SysLog wrapSysLog(JoinPoint joinPoint){
        SysLog sysLog = new SysLog();
        MethodSignature  signature = (MethodSignature)joinPoint.getSignature();
        String methodName = signature.getDeclaringTypeName()+"."+signature.getName();
        SystemLog systemLog = signature.getMethod().getAnnotation(SystemLog.class);
        if(!StringUtils.isEmpty(systemLog.type())){
            sysLog.setType(systemLog.type().getType());
        }
        sysLog.setMessage(methodName);
        sysLog.setCreateTime(new Date());
        return sysLog;
    }


}
