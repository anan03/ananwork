package com.lvshandian.partylive.base;

import java.io.Serializable;

/**
 * Created by sll on 2016/12/10.
 */

/**
 * 弹幕实体类
 *
 * @author sll
 * @time 2016/12/10 17:46
 */
public class BarrageDateBean implements Serializable {
    private String nickName;
    private String content;
    private String picUrl;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
