package com.boss.auth;

import com.boss.auth.client.UcenterClient;
import com.boss.ucenter.entity.dto.UserExt;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author: lpb
 * @create: 2020-07-28 16:04
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ClientTest {

    @Resource(type = UcenterClient.class)
    UcenterClient userClient;

    @Test
    public void test1(){
        UserExt userExt = userClient.getUserExt("'test01");
        log.debug(userExt.getName());
    }
}
