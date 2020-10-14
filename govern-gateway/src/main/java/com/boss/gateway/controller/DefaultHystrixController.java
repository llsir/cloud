package com.boss.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: lpb
 * @create: 2020-07-23 16:01
 */
@RestController
@Slf4j
public class DefaultHystrixController {

    @Value("${server.port}")
    private String port;

    @RequestMapping("/defaultfallback")
    public String defaultfallback(){
        log.info("服务降级中");
        log.info("port:"+port);
        return "error: 服务异常";
    }
}
