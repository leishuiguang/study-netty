package com.lsg.cloudcommon.result;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Result<T> {
    private String code;
    private String message;
    private T data;

    public static Result success() {
        return new Result<>(ResultCode.OK);
    }

    public Result success(T data) {
        return new Result<>(ResultCode.OK, data);
    }

    public static Result failure() {
        return new Result<>(ResultCode.SYSTEM_ERROR);
    }

    public static Result failure(ResultCode resultCode) {
        return new Result<>(resultCode);
    }

    public Result(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    public Result(ResultCode resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = null;
    }

    public Result(T data) {
        this.code = ResultCode.OK.getCode();
        this.message = ResultCode.OK.getMessage();
        this.data = data;
    }

    public Result() {
    }
}
