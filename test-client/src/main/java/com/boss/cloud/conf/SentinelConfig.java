package com.boss.cloud.conf;

import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author: lpb
 * @create: 2020-08-13 17:20
 */
//@Configuration
public class SentinelConfig {

    @PostConstruct
    public void init(){
        WebCallbackManager.setRequestOriginParser(new IpRequestOriginParser());
    }
}
