package com.youxin.alumni_management.utils;

//返回状态码枚举类
public enum ResultCode {
    SUCCESS(1, "成功"),
    FAIL(0, "失败"),
//    用户错误
    USER_PWD_ERROR(4002, "用户旧密码错误"),
    USER_NOT_EXIST(4003, "用户不存在"),
    USER_HAS_EXISTED(4004, "用户已经存在"),
    USER_INFO_ERROR(4005, "当前用户错误"),
    //参数错误
    PARAM_IS_INVALID(1001, "参数错误"),
    PARAM_IS_BLANK(1002, "参数为空"),
    PARAM_TYPE_BIND_ERROR(1003, "参数类型错误"),
    PARAM_NOT_COMPLETE(1004, "参数缺失"),
    //结果错误
    RESULT_EMPTY(2001, "查询结果为空");


    //状态码
    private Integer code;

    //状态信息
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer code() {
        return  this.code;
    }

    public String message() {
        return this.message;
    }

}
