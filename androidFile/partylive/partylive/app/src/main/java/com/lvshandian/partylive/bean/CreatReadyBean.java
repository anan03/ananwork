package com.lvshandian.partylive.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/21.
 */

public class CreatReadyBean implements Serializable {

    /**
     * broadcastUrl : IwtWa+EvtZ8D+tBhQspkIq2We0h+7DQgVlQ1myD2p4T68YgyGA7rKcnUGi4iNhRM
     * city : 北京
     * creator : {"address":"北京市","gender":"男","id":"12722","level":"2","location":"40.00375,116.40329","nickName":"奥特曼","picUrl":"http://image.miulive.cc/13167561314.png","signature":"Airline","userName":"13167561314","verified":"0","vip":"0"}
     * id : 2141
     * likeNum : 0
     * livePicUrl : http://image.miulive.cc/live/12722/liveshowpic-1479457184.png
     * name : 奥特曼
     * onlineUserNum : 0
     * pubStat : 0
     * publishUrl : 5ows1r6RErdVEJABumrBzB2oE0lICxXMCxZW4RzzY8QHpr6hkl/DzJaVVjhFZYq3GbqzakfMx+TC oTDxkvjMmr2hJtRUaMEYYBHQgaxDiXtP7YjaPXFLPTgOMVPZWzdxP+y+QanNCabIw36YuhRpxw==
     * roomId : 5113125
     * shareUrl : http://miulive.cc/s/2141
     * status : 1
     */

    private String broadcastUrl; //播放地址
    private String city;
    private CreatorBean creator;
    private String id;
    private String likeNum;
    private String livePicUrl;
    private String name;
    private String onlineUserNum;
    private String pubStat;
    private String publishUrl;//推流地址
    private String roomId;
    private String shareUrl;
    private String status;

    public String getBroadcastUrl() {
        return broadcastUrl;
    }

    public void setBroadcastUrl(String broadcastUrl) {
        this.broadcastUrl = broadcastUrl;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public CreatorBean getCreator() {
        return creator;
    }

    public void setCreator(CreatorBean creator) {
        this.creator = creator;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(String likeNum) {
        this.likeNum = likeNum;
    }

    public String getLivePicUrl() {
        return livePicUrl;
    }

    public void setLivePicUrl(String livePicUrl) {
        this.livePicUrl = livePicUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOnlineUserNum() {
        return onlineUserNum;
    }

    public void setOnlineUserNum(String onlineUserNum) {
        this.onlineUserNum = onlineUserNum;
    }

    public String getPubStat() {
        return pubStat;
    }

    public void setPubStat(String pubStat) {
        this.pubStat = pubStat;
    }

    public String getPublishUrl() {
        return publishUrl;
    }

    public void setPublishUrl(String publishUrl) {
        this.publishUrl = publishUrl;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class CreatorBean implements Serializable {
        /**
         * address : 北京市
         * gender : 男
         * id : 12722
         * level : 2
         * location : 40.00375,116.40329
         * nickName : 奥特曼
         * picUrl : http://image.miulive.cc/13167561314.png
         * signature : Airline
         * userName : 13167561314
         * verified : 0
         * vip : 0
         */

        private String address;
        private String gender;
        private String id;
        private String level;
        private String location;
        private String nickName;
        private String picUrl;
        private String signature;
        private String userName;
        private String verified;
        private String vip;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
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

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getVerified() {
            return verified;
        }

        public void setVerified(String verified) {
            this.verified = verified;
        }

        public String getVip() {
            return vip;
        }

        public void setVip(String vip) {
            this.vip = vip;
        }


        @Override
        public String toString() {
            return "CreatorBean{" +
                    "address='" + address + '\'' +
                    ", gender='" + gender + '\'' +
                    ", id='" + id + '\'' +
                    ", level='" + level + '\'' +
                    ", location='" + location + '\'' +
                    ", nickName='" + nickName + '\'' +
                    ", picUrl='" + picUrl + '\'' +
                    ", signature='" + signature + '\'' +
                    ", userName='" + userName + '\'' +
                    ", verified='" + verified + '\'' +
                    ", vip='" + vip + '\'' +
                    '}';
        }


    }

    @Override
    public String toString() {
        return "CreatReadyBean{" +
                "broadcastUrl='" + broadcastUrl + '\'' +
                ", city='" + city + '\'' +
                ", creator=" + creator +
                ", id='" + id + '\'' +
                ", likeNum='" + likeNum + '\'' +
                ", livePicUrl='" + livePicUrl + '\'' +
                ", name='" + name + '\'' +
                ", onlineUserNum='" + onlineUserNum + '\'' +
                ", pubStat='" + pubStat + '\'' +
                ", publishUrl='" + publishUrl + '\'' +
                ", roomId='" + roomId + '\'' +
                ", shareUrl='" + shareUrl + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
