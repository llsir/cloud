package com.boss.cloud.controller;

import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.fastjson.JSON;
import com.boss.cloud.response.CommonCode;
import com.boss.cloud.response.ResponseResult;
import com.boss.cloud.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.xml.ws.http.HTTPException;
import java.util.concurrent.TimeUnit;

/**
 * @author: lpb
 * @create: 2020-07-14 14:28
 */
@RestController
@RefreshScope
@Slf4j
public class ClientController {

    @Value("${server.port}")
    private String port;

    @Value("${abc.name}")
    private String name;

    @Autowired
    ClientService clientService;

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/hi")
    public String hello()
    {
        try {
            TimeUnit.MILLISECONDS.sleep(600);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return clientService.sayHi(name,port);
    }

    @GetMapping("/hello/{name}")
//    @SentinelResource(value = "paramtest",blockHandler = "helloError")
    @SentinelResource(value = "paramtest")
    public String welcome(@PathVariable("name")String name)
    {
        return "welcome:"+name;
    }

    @GetMapping("/hiapi/hi")
    public String hiApi(){
        return "hi:"+port;
    }

    @GetMapping("/hiapi/hi2")
    public String hiApi2(@RequestParam String age) throws FlowException {
        throw new FlowException("400");
    }

    public String welError()
    {
        log.info("Block error");
        return "Block error in welcome method:";
    }

    public String helloError(String name,BlockException e){
        log.info("error:{}",e.getClass().getSimpleName());
        return "error in ："+e.getClass().getSimpleName();
    }




}
