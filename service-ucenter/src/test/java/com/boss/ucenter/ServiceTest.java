package com.boss.ucenter;

import com.boss.ucenter.dao.imp.PermissionMapper;
import com.boss.ucenter.dao.imp.RoleMapper;
import com.boss.ucenter.entity.dto.PermissionExt;
import com.boss.ucenter.entity.dto.RoleExt;
import com.boss.ucenter.entity.dto.UserExt;
import com.boss.ucenter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: lpb
 * @create: 2020-07-21 11:32
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(ServiceTest.class);

    @Autowired
    UserService userService;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    PermissionMapper permissionMapper;

    @Test
    public void serviceTest(){
        UserExt test01 = userService.getUserExt("test01");
        logger.info(test01.toString());
    }

    @Test
    public void roleTest(){
        List<RoleExt> allRoles = roleMapper.getAllRoles();
        log.debug(allRoles.toString());
    }

    @Test
    public void permissionTest(){
        List<PermissionExt> list = permissionMapper.list();
        log.debug(list.toString());
    }

    @Test
    public void resourceTest(){
        Resource resource = new ClassPathResource("publickey.txt");
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream());
            BufferedReader br = new BufferedReader(inputStreamReader);
            log.info(br.lines().collect(Collectors.joining("\n")));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

}
