package com.boss.filesystem.service;

import com.boss.framework.annotation.SystemLog;
import com.boss.framework.enums.SystemLogEnum;
import com.boss.framework.model.response.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: lpb
 * @create: 2020-07-21 13:51
 */
public interface FileService {

    /**
     * 上传文件
     * @param file 文件
     * @return 统一响应结果
     */

    ResponseResult uploadFile(MultipartFile file) ;

    /**
     * 下载文件
     * @param objectKey 文件名
     * @return 文件下载地址
     */

    String getFile(String objectKey);
}
