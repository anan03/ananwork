package com.lvshandian.partylive.moudles.mine.bean;

import java.io.Serializable;

/**
 * Created by zhang on 2016/11/21.
 */

public class PhotoBean implements Serializable {


    /**
     * id : 241
     * userId : 12859
     * url : http://image.miulive.cc/album/12859/showpic-18513884422-1479698977.png
     * thumbnailUrl : http://image.miulive.cc/album/12859/showpic-18513884422-1479698977.png!320
     */

    private String id;
    private String userId;
    private String url;
    private String thumbnailUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    @Override
    public String toString() {
        return "bean{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", url='" + url + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                '}';
    }
}
