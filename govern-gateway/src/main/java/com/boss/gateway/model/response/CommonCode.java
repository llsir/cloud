package com.boss.gateway.model.response;

/**
 * @author: lpb
 * @create: 2020-07-10 09:02
 */
public enum CommonCode implements ResultCode {

    //操作成功时返回的状态码
    SUCCESS(true,10000,"操作成功！"),
    //操作失败时返回的状态码
    FAIL(false,11111,"操作失败！"),
    UNAUTHENTICATED(false,10001,"此操作需要登陆系统！"),
    UNAUTHORISE(false,10002,"权限不足，无权操作！"),
    //参数输入有误时返回的状态码
    INVLIDATE(false,10003,"非法参数！"),
    //用户不存在
    USER_NOTEXISTS(false,10004,"用户不存在！"),
    USERNAME_ISEMPTY(false,10005,"用户名不能为空！"),
    USERNAME_HASEXISTS(false,10006,"用户名已存在！"),
    ROLE_NOTEXISTS(false,10007,"角色不存在！"),
    USER_ROLE_HASEXIST(false,10007,"用户已拥有当前角色！"),

    ;

    //操作是否成功
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;
    CommonCode(boolean success,int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }
    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
