package com.neusoft.passwordserver.pojo.response;

/**
 * Created by fpf
 * Created date 2018/7/27
 */
public class BaseResponse<T> {

    private int resCode;

    private String resMessage;

    private T data;

    public int getResCode() {
        return resCode;
    }

    public void setResCode(int resCode) {
        this.resCode = resCode;
    }

    public String getResMessage() {
        return resMessage;
    }

    public void setResMessage(String resMessage) {
        this.resMessage = resMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
