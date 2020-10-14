package com.boss.framework.enums;

/**
 * @author: lpb
 * @create: 2020-07-22 09:22
 */
public enum SystemLogEnum {

    SAVE_LOG("保存"),
    UPDATE_LOG("修改"),
    DELETE_LOG("删除"),
    REGISTER_LOG("注册"),
    UPLOAD_FILE_LOG("文件上传"),
    DOWNLOAD_FILE_LOG("文件下载"),
    LOGIN_LOG("登录"),
    THROW_LOG("异常"),
    ;

    private String type;

    SystemLogEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
