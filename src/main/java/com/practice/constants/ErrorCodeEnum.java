package com.practice.constants;

import java.util.Objects;

public enum ErrorCodeEnum {

    OK("200", ""),
    VALUE_NULL("300", ""),
    PARAM_NULL("301", ""),
    SIGN_ERROR("400", ""),
    NO_LOGIN("401", ""),
    SYS_ERROR("500", "");

    ErrorCodeEnum(String code, String msg){
        this.code = code;
        this.msg = msg;
    }

    private final String code;
    private final String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getMsgByCode(String code) {
        for (ErrorCodeEnum entry : ErrorCodeEnum.values()) {
            if (Objects.equals(entry.getCode(), code)) {
                return entry.getMsg();
            }
        }

        return "";
    }
}
