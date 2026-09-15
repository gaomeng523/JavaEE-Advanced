package com.example.springaopdemo.common;

import lombok.Data;

/**
 * 统一返回结果包装类
 */
@Data
public class Result<T> {

    /** 状态码：200 成功，-1 失败 */
    private Integer code;

    /** 提示信息 */
    private String msg;

    /** 数据 */
    private T data;

    public Result() {
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> Result<T> success() {
        return new Result<>(200, "操作成功", null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<>(-1, msg, null);
    }
}
