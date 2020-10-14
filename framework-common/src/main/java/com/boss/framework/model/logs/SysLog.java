package com.boss.framework.model.logs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: lpb
 * @create: 2020-07-22 09:14
 */
@Data
@ToString
@NoArgsConstructor
@Document(collection = "logs")
public class SysLog {

    @Id
    private String id;

    private String type;

    private String message;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;



}
