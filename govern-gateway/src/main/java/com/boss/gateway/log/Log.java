package com.boss.gateway.log;

import com.boss.gateway.utils.EnvironmentUtils;
import com.boss.gateway.utils.IpUtils;
import lombok.Data;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * 日志实体类，方便后续接入ELK
 */
@Data
public class Log {
    private TYPE logType;
    private LEVEL level;
    private String appEnv;
    private String applicationName;
    private String hostName;
    private String ip;
    private Long handleTime;
    private String timeStamp;
    private String requestUrl;
    private String userName;
    private String account;
    private String requestBody;
    private String responseBody;
    private String requestId;
    private String requestMethod;
    private Integer status;
    private String serverIp;
    private String sessionId;
    private String _class;

    public Log() {
        this(TYPE.REQUEST);
    }

    public Log(TYPE logType) {
        this.logType = logType;
        this.applicationName = EnvironmentUtils.getAppEnv();
        this.hostName = IpUtils.getHostName();
        this.timeStamp = ZonedDateTime.now(ZoneOffset.of("+08:00")).toString();
        this.serverIp = IpUtils.getLocalIp();
    }

    /**
     * 日志级别枚举类
     */
    public static enum LEVEL {
        OFF,
        ERROR,
        WARN,
        INFO,
        DEBUG,
        TRACE,
        ALL;

        private LEVEL() {
        }
    }

    /**
     * 日志类型枚举
     */
    public static enum TYPE {
        REQUEST,
        RESPONSE,
        OUT;

        private TYPE() {
        }
    }
}
