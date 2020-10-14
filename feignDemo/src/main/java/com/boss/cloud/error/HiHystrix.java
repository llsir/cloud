package com.boss.cloud.error;

import com.boss.cloud.client.EurekaFeignClient;
import org.springframework.stereotype.Component;

/**
 * @author: lpb
 * @create: 2020-07-15 20:57
 */
@Component
public class HiHystrix implements EurekaFeignClient {
    @Override
    public String sayHiFromClientEureka() {
        return "Hi , sorry error.";
    }
}
