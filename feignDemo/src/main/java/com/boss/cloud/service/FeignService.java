package com.boss.cloud.service;

import com.boss.cloud.client.EurekaFeignClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author: lpb
 * @create: 2020-07-14 15:20
 */
@Service
public class FeignService {

    @Resource(type = EurekaFeignClient.class)
    EurekaFeignClient eurekaFeignClient;

//    @Autowired
//    RestTemplate restTemplate;

//    @HystrixCommand(fallbackMethod = "hiError")
    public String hi()
    {
//        return restTemplate.getForObject("http://boss-client/hi", String.class);
        return eurekaFeignClient.sayHiFromClientEureka();
    }

    public String hiError(){
        return "hi,sorry error!";
    }

}
