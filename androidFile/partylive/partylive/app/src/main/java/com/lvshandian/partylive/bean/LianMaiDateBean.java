package com.lvshandian.partylive.bean;

import java.io.Serializable;

/**
 * Created by sll on 2016/12/9.
 */

/**
 * 连麦消息的实体类
 *
 * @author sll
 * @time 2016/12/9 19:42
 */
public class LianMaiDateBean implements Serializable {

    /**
     * id : 152
     * roomId : 3886
     * userId : 12861
     * nickName : 米阳SOHO
     * userAvatar : http://wx.qlogo.cn/mmopen/PiajxSqBRaEJB7GHRVSzvj2kjqqrea3YQ6uMVc4axezZnLROHdEDAYbRY81L2wZAKTasZwGtwsy9pjYHibIMcdKQ/0
     * publishUrl : null
     * broadcastUrl : null
     * amount : 8
     * type : 2
     * status : 0
     * applyTime : 2016-12-09 18:07:02
     */

    private String id;
    private String roomId;
    private String userId;
    private String nickName;
    private String userAvatar;
    private Object publishUrl;
    private Object broadcastUrl;
    private String amount;
    private String type;
    private String status;
    private String applyTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public Object getPublishUrl() {
        return publishUrl;
    }

    public void setPublishUrl(Object publishUrl) {
        this.publishUrl = publishUrl;
    }

    public Object getBroadcastUrl() {
        return broadcastUrl;
    }

    public void setBroadcastUrl(Object broadcastUrl) {
        this.broadcastUrl = broadcastUrl;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }
}
