package com.lvshandian.partylive.httprequest;

/**
 * Created by zhang on 2016/10/11.
 */
public class SdkHttpResult {
    private String code;
    private String message;
    private String data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{" +
                "code:" + code +
                ", message:" + message +
                ", data:" + data +
                '}';
    }
}
