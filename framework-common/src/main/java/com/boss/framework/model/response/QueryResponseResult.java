package com.boss.framework.model.response;

/**
 * @author: lpb
 * @create: 2020-07-12 20:48
 */
public class QueryResponseResult<T> extends ResponseResult{

    public T query;

    public QueryResponseResult(ResultCode code,T query){
        super(code);
        this.query = query;
    }
}
