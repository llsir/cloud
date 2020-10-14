package com.boss.filesystem;

import com.boss.framework.conf.MQStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * @author: lpb
 * @create: 2020-07-21 15:57
 */
@SpringBootApplication
@EnableBinding(MQStream.class)
@ComponentScans({@ComponentScan({"com.boss.framework"})})
public class FileApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class,args);
    }

}
