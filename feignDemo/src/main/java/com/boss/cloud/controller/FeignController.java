package com.boss.cloud.controller;

import com.boss.cloud.client.EurekaFeignClient;
import com.boss.cloud.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: lpb
 * @create: 2020-07-14 15:20
 */
@RestController
public class FeignController {

    @Autowired
    FeignService feignService;

    @GetMapping("/sayHi")
    public String hi(){
        return feignService.hi();
    }

}
