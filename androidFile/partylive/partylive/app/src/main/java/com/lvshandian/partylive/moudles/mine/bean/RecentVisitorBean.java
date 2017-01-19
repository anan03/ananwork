package com.lvshandian.partylive.moudles.mine.bean;

import java.io.Serializable;

/**
 * Created by gjj on 2016/11/23.
 * 最近访客item实体
 */

public class RecentVisitorBean implements Serializable{

    /**
     * gender : 1
     * level : 10
     * nickName : lvtu
     * picUrl : http://image.miulive.cc/avatar/12718/objectKey-15227949757-headerpic-1478952140.png
     * roomId : 2292
     * signature : 其实是
     * userId : 12718
     * vip : 0
     */

    private String gender;
    private String level;
    private String nickName;
    private String picUrl;
    private String roomId;
    private String signature;
    private String userId;
    private String vip;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }
}
