package com.boss.framework.conf;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

/**
 * @author: lpb
 * @create: 2020-07-22 09:49
 */
@Component
public interface MQStream {

    String LOG_INPUT = "log_input";

    String LOG_OUTPUT = "log_output";

    @Input(LOG_INPUT)
    SubscribableChannel logInput();

    @Output(LOG_OUTPUT)
    SubscribableChannel logOutput();

}
