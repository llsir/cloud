package com.boss.cloud.client;

import com.boss.cloud.error.HiHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: lpb
 * @create: 2020-07-14 15:17
 */
@FeignClient(value = "test-client",fallback = HiHystrix.class)
public interface EurekaFeignClient {

    @GetMapping("/hi")
    String sayHiFromClientEureka();
}
