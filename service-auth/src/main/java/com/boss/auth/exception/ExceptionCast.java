package com.boss.auth.exception;

import com.boss.framework.model.response.ResultCode;

public class ExceptionCast  {

    public static void cast(ResultCode resultCode){
        throw new CustomException(resultCode);
    }
}
