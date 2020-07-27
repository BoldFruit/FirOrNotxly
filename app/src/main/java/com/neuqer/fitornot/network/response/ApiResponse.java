package com.neuqer.fitornot.network.response;

import com.google.gson.annotations.SerializedName;

public class ApiResponse<T> {

    /**
     * flag : 1
     * data : d
     * errorcode : 1
     * errormsg : sdfas
     */
    private int flag;
    private T data;
    @SerializedName("errcode")
    private int errcode;
    @SerializedName("errmsg")
    private String errmsg;

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setErrorcode(int errorCode) {
        this.errcode = errorCode;
    }

    public void setErrormsg(String errorMsg) {
        this.errmsg = errorMsg;
    }

    public int getFlag() {
        return flag;
    }

    public T getData() {
        return data;
    }

    public int getErrorCode() {
        return errcode;
    }

    public String getErrorMsg() {
        return errmsg;
    }
}
