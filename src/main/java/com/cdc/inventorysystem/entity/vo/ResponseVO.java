package com.cdc.inventorysystem.entity.vo;


import com.cdc.inventorysystem.common.enums.ResponseStatusEnum;

public class ResponseVO {

    /**
     * 状态码
     */
    private int code;

    /**
     * 状态信息
     */
    private String msg;

    /**
     * 结果
     */
    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    /**
     *
     * 错误信息

     */
    private String errMsg;

    public ResponseVO(ResponseStatusEnum responseStatus, Object data) {
        this.code = responseStatus.getCode();
        this.msg = responseStatus.getMsg();
        this.data = data;
    }

    public ResponseVO(ResponseStatusEnum responseStatus, Object data, String errMsg) {
        this.code = responseStatus.getCode();
        this.msg = responseStatus.getMsg();
        this.data = data;
        this.errMsg = errMsg;
    }
}
