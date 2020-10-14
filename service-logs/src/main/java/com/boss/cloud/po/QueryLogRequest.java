package com.boss.cloud.po;

import lombok.Data;

import java.util.Date;

/**
 * @author: lpb
 * @create: 2020-07-22 13:30
 */
@Data
public class QueryLogRequest {

    private String message;

    private String type;

    private Date startTime;

    private Date endTime;

}
