package com.example.springblogdemo.pojo.response;

import com.example.springblogdemo.common.enums.ResultCodeEnum;
import lombok.Data;

/**
 * 统一响应结构
 *
 * <p>使用原生类型会触发「未检查的转换」警告，这里把泛型补全。</p>
 *
 * @param <T> 业务数据的类型
 */
@Data
public class Result<T> {
    private int code;
    private String errMsg;
    private T data;

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setData(data);
        return result;
    }

    public static <T> Result<T> fail(String errMsg) {
        Result<T> result = new Result<>();
        result.setCode(ResultCodeEnum.FAIL.getCode());
        result.setErrMsg(errMsg);
        return result;
    }
}
