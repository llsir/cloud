package com.boss.cloud.conf;

import com.boss.cloud.service.LogService;
import com.boss.framework.conf.MQStream;
import com.boss.framework.model.logs.SysLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

/**
 * @author: lpb
 * @create: 2020-07-22 10:40
 */
@Slf4j
@Component
public class LogMqListener {

    @Autowired
    LogService logService;

    @StreamListener(MQStream.LOG_INPUT)
    public void input(SysLog sysLog){
        log.info("开始记录日志==================");
        logService.save(sysLog);
        log.info("记录日志完成==================");
    }
}
