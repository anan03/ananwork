package com.lvshandian.asktoask.common.http;

import java.io.Serializable;

/**
 * Created by ldb on 2015-02-26.
 */
public class SdkHttpResult implements Serializable {
    private int status;
    private String msg;
    private String data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {

        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}