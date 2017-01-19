package com.lvshandian.partylive.bean;

import java.io.Serializable;

/**
 * Created by sll on 2016/11/30.
 */

public class CustomGiftBean implements Serializable {
    /**
     * gift_Grand_Prix_number : 2
     * gift_item_number : 1  礼物的数量
     * userId : 12718
     * gift_item_index : 4  礼物的Id根据id判断是什么礼物
     * gift_item_message :  赠送1个 冰淇淋 !
     * gift_coinnumber_index : 5 礼物价格
     * type : 109
     * gift_Grand_Prix : 50
     */

    private String gift_Grand_Prix_number;
    private String gift_item_number;
    private String userId;
    private String gift_item_index;
    private String gift_item_message;
    private String gift_coinnumber_index;
    private String type;
    private String gift_Grand_Prix;

    public String getGift_Grand_Prix_number() {
        return gift_Grand_Prix_number;
    }

    public void setGift_Grand_Prix_number(String gift_Grand_Prix_number) {
        this.gift_Grand_Prix_number = gift_Grand_Prix_number;
    }

    public String getGift_coinnumber_index() {
        return gift_coinnumber_index;
    }

    public void setGift_coinnumber_index(String gift_coinnumber_index) {
        this.gift_coinnumber_index = gift_coinnumber_index;
    }

    public String getGift_Grand_Prix() {
        return gift_Grand_Prix;
    }

    public void setGift_Grand_Prix(String gift_Grand_Prix) {
        this.gift_Grand_Prix = gift_Grand_Prix;
    }

    public String getGift_item_index() {
        return gift_item_index;
    }

    public void setGift_item_index(String gift_item_index) {
        this.gift_item_index = gift_item_index;
    }

    public String getGift_item_message() {
        return gift_item_message;
    }

    public void setGift_item_message(String gift_item_message) {
        this.gift_item_message = gift_item_message;
    }

    public String getGift_item_number() {
        return gift_item_number;
    }

    public void setGift_item_number(String gift_item_number) {
        this.gift_item_number = gift_item_number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CustomGiftBean{" +
                "gift_Grand_Prix_number='" + gift_Grand_Prix_number + '\'' +
                ", gift_item_number='" + gift_item_number + '\'' +
                ", userId='" + userId + '\'' +
                ", gift_item_index='" + gift_item_index + '\'' +
                ", gift_item_message='" + gift_item_message + '\'' +
                ", gift_coinnumber_index='" + gift_coinnumber_index + '\'' +
                ", type='" + type + '\'' +
                ", gift_Grand_Prix='" + gift_Grand_Prix + '\'' +
                '}';
    }
}
