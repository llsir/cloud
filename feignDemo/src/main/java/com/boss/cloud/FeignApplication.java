package com.boss.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.alibaba.sentinel.annotation.SentinelRestTemplate;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @author: lpb
 * @create: 2020-07-14 11:33
 */
@SpringBootApplication
@EnableDiscoveryClient//注册eureka
@EnableFeignClients//启用Feign
//@EnableHystrix//启用hystrix熔断器
//@EnableHystrixDashboard//启用hystrix监控器
public class FeignApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(FeignApplication.class,args);
    }

    @Bean
    @LoadBalanced
    @SentinelRestTemplate
    public RestTemplate restTemplate(){
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }

}
