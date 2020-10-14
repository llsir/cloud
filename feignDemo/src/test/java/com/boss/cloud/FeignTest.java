package com.boss.cloud;

import com.boss.cloud.client.EurekaFeignClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author: lpb
 * @create: 2020-07-14 12:21
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class FeignTest {

    private static final Logger logger = LoggerFactory.getLogger(FeignTest.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Resource(type = EurekaFeignClient.class)
    EurekaFeignClient eurekaFeignClient;

    @Test
    public void restTemplateTest(){
        String forObject = restTemplate.getForObject("http://127.0.0.1/hi", String.class);
        logger.info(forObject);
    }

    @Test
    public void testRibbon(){
        for(int i = 0;i < 5;i++){
            String forObject = restTemplate.getForObject("http://boss-client/hi", String.class);
            logger.info(forObject);
//            LoadBalancerInterceptor //使用ribbon后远程调用会走这个拦截器
        }
    }
    @Test
    public void testLoadBalanced(){
        for(int i = 0;i < 5;i++){
            ServiceInstance instance = loadBalancerClient.choose("boss-client");
            logger.info(instance.getHost()+":"+instance.getPort());
        }
    }

    @Test
    public void testFeign(){
        for(int i = 0;i < 5;i++){
            String s = eurekaFeignClient.sayHiFromClientEureka();
            logger.info(s);
        }
    }
}
