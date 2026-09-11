package com.example.book.entity;

import lombok.Data;

/**
 * 统一返回体：所有 Controller 接口都返回它，前端只认 code / msg / data 三个字段
 * 约定：
 * 200 成功       —— data 里是业务数据，msg 固定为"操作成功"
 * 400 参数不合法 —— 前端直接提示 msg
 * 401 未登录     —— 由 LoginInterceptor 返回，前端跳回 login.html
 * 500 服务端异常 —— 具体原因看后端日志
 */
@Data
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    // 成功，并且有业务数据要返回（查询类接口用）
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }

    // 成功，但没有数据要返回（新增/修改/删除这类只看成不成功的接口用）
    public static <T> Result<T> success() {
        return new Result<>(200, "操作成功", null);
    }

    // 失败，默认 500
    public static <T> Result<T> fail(String msg) {
        return new Result<>(500, msg, null);
    }

    // 失败，自己指定状态码，比如 400 参数不合法、401 未登录
    public static <T> Result<T> fail(Integer code, String msg) {
        return new Result<>(code, msg, null);
    }
}
