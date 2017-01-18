package com.lvshandian.menshen.bean;

/**
 * Created by zhang on 2016/11/1.
 */

public class Banner {


    /**
     * bannerId : 2
     * bannerType : 1
     * title : 鞠婧祎
     * imgUrl : http://192.168.1.143:8081/img/201610311647040633218.jpeg
     * url : http://192.168.1.143:8081/img/201610311620507011852.jpg
     * status : 1
     * createdDate : 1477902066000
     * updatedDate : 1477904660000
     * deletedDate : null
     * content : null
     */

    private int bannerId;
    private int bannerType;
    private String title;
    private String imgUrl;
    private String url;
    private int status;
    private long createdDate;
    private long updatedDate;
    private Object deletedDate;
    private Object content;

    public int getBannerId() {
        return bannerId;
    }

    public void setBannerId(int bannerId) {
        this.bannerId = bannerId;
    }

    public int getBannerType() {
        return bannerType;
    }

    public void setBannerType(int bannerType) {
        this.bannerType = bannerType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public long getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(long updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Object getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Object deletedDate) {
        this.deletedDate = deletedDate;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
