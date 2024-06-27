package com.njz.http;

import lombok.Data;

import java.io.Serializable;
@Data
public class ResponseBean<T> implements Serializable {
    private String code; // 错误码
    private String msg;  // 错误消息
    private boolean success;  // 错误消息
    private Object obj;  // 错误消息

    public ResponseBean(String code, String msg, boolean success, T obj) {
        this.code = code;
        this.msg = msg;
        this.success = success;
        this.obj = obj;
    }

    public static ResponseBean fail(String msg) {
        return new ResponseBean("1", msg, false, null);
    }
    public static ResponseBean fail(String msg, Object obj) {
        return new ResponseBean("1", msg, false, obj);
    }
    public static ResponseBean ok(String msg) {
        return new ResponseBean("0", msg, true, null);
    }
    public static ResponseBean ok(String msg, Object obj) {
        return new ResponseBean("1", msg, true, obj);
    }
}

