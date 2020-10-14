package com.boss.cloud.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.boss.cloud.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author: lpb
 * @create: 2020-08-14 09:19
 */
@Service
@Slf4j
public class ClientServiceImpl implements ClientService {

    @Override
//    @SentinelResource(value = "ahi",blockHandler = "hiError")
    public String sayHi(String name, String port) {
        return "你好:"+name+port;
    }

    public String hiError(String name, String port,BlockException e)
    {
        log.info("Block error:{}",e.getMessage());
        return "Block error in hi method:"+e.getRule();
    }
}
