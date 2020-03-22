package com.example.demo03.entity.enums;

public enum ResponseEnum {
    UNKNOWN_ERROR(-1,"未知错误"),
    SUCCESS(10000,"成功"),
    TIME_OUT(1,"连接超时"),
    SERVER_ERROR(2,"服务器错误"),
    BIND_SERVER_SUCCESS(10001,"模型连接成功"),
    MODEL_ERROR(3,"模型连接错误")
    ;

    private Integer code;
    private String msg;

    ResponseEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
