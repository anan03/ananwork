package com.lvshandian.partylive.bean;

import java.util.List;

/**
 * Created by sll on 2016/12/22.
 */

/**
 * 频道请求后的实体
 *
 * @author sll
 * @time 2016/12/22 15:33
 */
public class ChannelUrlBean {

    /**
     * success : true
     * code : 0
     * msg :
     * obj : []
     */

    private boolean success;
    private int code;
    private String msg;
    private List<ChannelBean> obj;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

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

    public List<ChannelBean> getObj() {
        return obj;
    }

    public void setObj(List<ChannelBean> obj) {
        this.obj = obj;
    }
}
