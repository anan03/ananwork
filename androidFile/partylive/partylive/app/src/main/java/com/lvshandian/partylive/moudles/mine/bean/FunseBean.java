package com.lvshandian.partylive.moudles.mine.bean;

import java.io.Serializable;

/**
 * Created by gjj on 2016/12/6.
 */

public class FunseBean implements Serializable{

    /**
     * follow : 1
     * gender : 1
     * level : 13
     * nickName : 狡猾小兵
     * picUrl : http://image.miulive.cc/11834.png
     * roomId : 3273
     * signature : 我的大刀早已饥渴难耐
     * userId : 12858
     * vip : 0
     */

    private String follow;
    private String gender;
    private String level;
    private String nickName;
    private String picUrl;
    private String roomId;
    private String signature;
    private String userId;
    private String vip;

    private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getFollow() {
        return follow;
    }

    public void setFollow(String follow) {
        this.follow = follow;
    }

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
