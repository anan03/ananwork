package com.lvshandian.partylive.bean;

/**
 * Created by sll on 2016/12/6.
 */

import java.io.Serializable;

/**
 * 点播节目的实体类
 *
 * @author sll
 * @time 2016/12/6 20:37
 */
public class DianBoDateBean implements Serializable {

    /**
     * 请求能否点播节目的接口返回数据
     * cost : 1
     * gender : 1
     * id : 82
     * name : 4级今后
     * nickName : 大圣
     * picUrl : http://image.miulive.cc/63781.png
     * roomId : 21
     * status : 0
     * userId : 12916
     */

    private String cost;
    private String gender;
    private String id;
    private String name;
    private String nickName;
    private String picUrl;
    private String roomId;
    private String status;
    private String userId;
    /**
     * 收到的点播节目的自定义通知的content内容
     * content : 大圣出1金币，点播表演回家咯,请查看
     * type : 3
     * userAvatar : http://image.miulive.cc/63781.png
     * userName : 大圣
     * vip : 0
     */

    private String content;
    private String type;
    private String userAvatar;
    private String userName;
    private String vip;
    private String SessionId;

    public String getSessionId() {
        return SessionId;
    }

    public void setSessionId(String sessionId) {
        SessionId = sessionId;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }
}
