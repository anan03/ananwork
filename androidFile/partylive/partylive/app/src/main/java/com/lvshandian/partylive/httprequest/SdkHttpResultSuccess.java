package com.lvshandian.partylive.httprequest;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhang on 2016/10/11.
 */
public class SdkHttpResultSuccess implements Serializable {

    /**
     * success : true
     * code : 0
     * msg : 成功
     * obj : [{"id":8,"picUrl":"http://192.168.1.104:8081/admin/static/images/1483427905528704.png","pointUrl":"https://www.baidu.com","creatTime":"2017-01-03 15:18:25","status":"1","carouselName":"1"},{"id":9,"picUrl":"http://192.168.1.104:8081/admin/static/images/1483427925307836.png","pointUrl":"https://www.baidu.com","creatTime":"2017-01-03 15:18:45","status":"1","carouselName":"2"},{"id":10,"picUrl":"http://192.168.1.104:8081/admin/static/images/1483427949767778.jpg","pointUrl":"https://www.baidu.com","creatTime":"2017-01-03 15:19:09","status":"1","carouselName":"3"}]
     */

    private boolean success;
    private int code;
    private String msg;
    private String obj;

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

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

}
