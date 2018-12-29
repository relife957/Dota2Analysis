package com.wangyi.dotaapi.Exception;

import java.util.UUID;

/**
 * @author wangyi
 * @create 2018-12-12 10:28 PM
 **/
public class ApiResult {
    private String id ;
    private String message ;

    public ApiResult(String message) {
        id = UUID.randomUUID().toString();
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
