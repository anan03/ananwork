package com.lvshandian.partylive.bean;

import java.io.Serializable;

/**
 * Created by zhang on 2017/1/3.
 */

public class ImagBean implements Serializable {

    /**
     * carouselName : 1
     * creatTime : 2017-01-03 15:18:25
     * id : 8
     * picUrl : http://192.168.1.104:8081/admin/static/images/1483427905528704.png
     * pointUrl : https://www.baidu.com
     * status : 1
     */

    private String carouselName;
    private String creatTime;
    private int id;
    private String picUrl;
    private String pointUrl;
    private String status;

    public String getCarouselName() {
        return carouselName;
    }

    public void setCarouselName(String carouselName) {
        this.carouselName = carouselName;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPointUrl() {
        return pointUrl;
    }

    public void setPointUrl(String pointUrl) {
        this.pointUrl = pointUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ImagBean{" +
                "carouselName='" + carouselName + '\'' +
                ", creatTime='" + creatTime + '\'' +
                ", id=" + id +
                ", picUrl='" + picUrl + '\'' +
                ", pointUrl='" + pointUrl + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
