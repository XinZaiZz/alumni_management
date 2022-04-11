package com.youxin.alumni_management.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author youxin
 * @program crud_security_demo
 * @description 返回结果类
 * @date 2022-04-06 10:01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result implements Serializable {
    //返回状态码
    private Integer code;

    //返回信息
    private String message;

    //直接定义返回状态码类
//    private ResultCode resultCode;

    //返回内容
    private Object data;

//    public Result(ResultCode resultCode, Object data) {
//        this.code = resultCode.code();
//        this.message = resultCode.message();
//        this.data = data;
//    }
//
//    public Result(ResultCode resultCode) {
//        this.code = resultCode.code();
//        this.message = resultCode.message();
//    }


    //直接使用静态方法调用成功或失败
    public static Result success(ResultCode resultCode, Object data) {
        Result result = new Result();
        result.setCode(resultCode.code());
        result.setMessage(resultCode.message());
//        result.setResultCode(resultCode);
        result.setData(data);
        return result;
    }

    public static Result success(ResultCode resultCode) {
        Result result = new Result();
        result.setCode(resultCode.code());
        result.setMessage(resultCode.message());
        return result;
    }

    //返回失败
    public static Result failure(ResultCode resultCode, Object data) {
        Result result = new Result();
        result.setCode(resultCode.code());
        result.setMessage(resultCode.message());
//        result.setResultCode(resultCode);
        result.setData(data);
        return result;
    }

    //返回失败
    public static Result failure(ResultCode resultCode) {
        Result result = new Result();
        result.setCode(resultCode.code());
        result.setMessage(resultCode.message());
//        result.setResultCode(resultCode);
        return result;
    }
}
