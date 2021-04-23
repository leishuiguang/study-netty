package com.lsg.cloudcommon.result;

/**
 * 响应异常码
 */
public enum ResultCode {
    OK("0", "操作成功"),
    SYSTEM_ERROR("4", "系统异常"),
    SERVICE_ERROR("5", "服务异常"),
    ;
    /**
     * 异常码
     */
    private String code;
    /**
     * 异常提示信息
     */
    private String message;

    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
