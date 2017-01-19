package com.lvshandian.partylive.bean;

import java.io.Serializable;

/**
 * Created by sll on 2016/12/2.
 */

public class CustomLightBean implements Serializable {

    /**
     * number : 1
     *  blue : 0.100000
     * red : 0.700000
     * green : 0.400000
     * vip : 0
     * userId : 12718
     * type : 108
     */

    private String number;
    private float blue;
    private float red;
    private float green;
    private String vip;
    private String userId;
    private String type;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
