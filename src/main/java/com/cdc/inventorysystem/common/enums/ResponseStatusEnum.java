package com.cdc.inventorysystem.common.enums;

/**
 * 返回结果状态枚举
 */
public enum ResponseStatusEnum {

    SUCCESS(200, "成功"),
    NOT_FOUND(404, "找不到"),
    INTERNAL_ERROR(500, "服务器错误"),
    NO_REGISTER(-1, "用户未注册..."),
    PARAMETER_ERROR(-2, "参数异常..."),
    SYSTEM_ERROR(-3, "系统出错了..."),
    NO_AUTH(-4, "用户未验证..."),
    ACCOUNT_LOCK(-5, "账号被屏蔽");


    private int code;

    private String msg;

    ResponseStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
