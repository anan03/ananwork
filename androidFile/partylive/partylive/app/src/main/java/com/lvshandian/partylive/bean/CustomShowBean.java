package com.lvshandian.partylive.bean;

import java.io.Serializable;

/**
 * Created by sll on 2016/12/14.
 */

public class CustomShowBean implements Serializable {

    /**
     * name : 嘿嘿嘿
     * roomId : 4293
     * cost : 9
     * status : 0    1是开始，2是结束，0是未开始，-1是失效
     * userId : 12709
     * id : 75
     */

    private String name;
    private String roomId;
    private String cost;
    private String status;
    private String userId;
    private String id;

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
