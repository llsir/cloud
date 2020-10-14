package com.boss.auth.client;

import com.boss.ucenter.entity.dto.UserExt;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: lpb
 * @create: 2020-07-28 15:54
 */
@FeignClient(value = "service-ucenter")
public interface UcenterClient {

    @GetMapping("/user/getUserExt")
    UserExt getUserExt(@RequestParam(value = "username",required = true) String userName);

}
