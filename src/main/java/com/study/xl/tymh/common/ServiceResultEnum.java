package com.study.xl.tymh.common;

public enum ServiceResultEnum {

    SUCCESS("0000","成功"),

    UNKNOWN_ERROR("0001","未知异常"),

    DB_ERROR("0002","数据库操作异常"),

    PARAM_ERROR("0003","参数验证错误"),

    SYSTEM_ERROR("0004","系统异常"),

    BUSINESS_ERROR("0005","业务错误"),

    INFO_ERROR("0006", "提示级错误"),

    SYSTEM_MAINTAIN_ERROR("0007","系统正在维护");

    private String code;
    private String msg;

    public String getCode(){
        return code;
    }
    public String getMsg(){
        return msg;
    }

    ServiceResultEnum(String code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
