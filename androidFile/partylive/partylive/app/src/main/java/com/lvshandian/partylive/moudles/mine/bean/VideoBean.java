package com.lvshandian.partylive.moudles.mine.bean;

import java.io.Serializable;

/**
 * Created by zhang on 2016/11/24.
 */

public class VideoBean implements Serializable {


    /**
     * id : 287
     * thumbnailUrl : http://image.miulive.cc/shortvideo/12859/shortvideo-18513884422-1479951845.png
     * url : http://image.miulive.cc/shortvideo/12859/shortvideo-18513884422-1479951845.mp4
     * userId : 12859
     */

    private String id;
    private String thumbnailUrl;
    private String url;
    private String userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "VideoBean{" +
                "id='" + id + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", url='" + url + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
