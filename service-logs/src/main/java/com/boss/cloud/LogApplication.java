package com.boss.cloud;

import com.boss.framework.conf.MQStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * @author: lpb
 * @create: 2020-07-22 10:10
 */
@SpringBootApplication
@EnableBinding(MQStream.class)
public class LogApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogApplication.class,args);
    }

}
