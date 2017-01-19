package com.lvshandian.partylive.bean;

import java.io.Serializable;

/**
 * 直播用户
 * Created by zz on 2016/11/11.
 */

public class LiveBean implements Serializable {


    /**
     * broadcastUrl : IwtWa+EvtZ8D+tBhQspkIq2We0h+7DQgUZROWl6p/NaqCwqnOfxJIt9JXEAeNiWD
     * creator : {"id":"10395","nickName":"也是醉了","picUrl":"http://q.qlogo.cn/qqapp/1105343191/99E54F4A4F6CFE3E1288D38877EA6BBB/100"}
     * city : 火星      地址
     * privateChat : 1
     * roomId : 3559372    网易云信群聊id
     * timeStamp : 1478793619168   时间戳
     * livePicUrl : http://img.meelive.cn/MTQ3NjAyNDc5ODUwNCMyNTYjanBn.jpg   直播背景图片，相册拍照图片；
     * name :                                                                  房间昵称；
     * onlineUserNum : 10                                  观看人数
     * shareUrl : http://miulive.cc/s/1028              分享Url
     * id : 1028                                              房间号
     * payForChat : 188
     * pubStat : 1
     * status : 1
     * <p>
     * "privateFlag":"1",                    是否是私密 1私密 否则为空
     * "roomPay":"123",                      私密直播錢
     * "roomPw":"123",                       私密直播密碼
     */

    private String broadcastUrl;
    private CreatorBean creator;
    private String city;
    private String privateChat;
    private String roomId;
    private String timeStamp;
    private String livePicUrl;
    private String name;
    private String onlineUserNum;
    private String shareUrl;
    private String id;
    private String payForChat;
    private String pubStat;
    private String status;
    private String privateFlag;
    private String roomPay;
    private String roomPw;
    /**
     * roomLocation : 40.00379,116.40320
     * roomDistance : 0.01
     * publishUrl : 5ows1r6RErfTPHjlAebQ40cOWAJwB1lby1iAUE4G9+Noi9JUbHQ6pTHIPxKStqBz2ftl8LT86oxi
     * OCAz1uFc/BHFOGUlT5kawAGmauClPDthZfwxbBeqap7sQp5U/dMddqYnGZv5sGNMHGQOyyjzww==
     */

    private String roomLocation;
    private String roomDistance;
    private String publishUrl;

    public String getPrivateFlag() {
        return privateFlag;
    }

    public void setPrivateFlag(String privateFlag) {
        this.privateFlag = privateFlag;
    }

    public String getRoomPay() {
        return roomPay;
    }

    public void setRoomPay(String roomPay) {
        this.roomPay = roomPay;
    }

    public String getRoomPw() {
        return roomPw;
    }

    public void setRoomPw(String roomPw) {
        this.roomPw = roomPw;
    }

    public String getBroadcastUrl() {
        return broadcastUrl;
    }

    public void setBroadcastUrl(String broadcastUrl) {
        this.broadcastUrl = broadcastUrl;
    }

    public CreatorBean getCreator() {
        return creator;
    }

    public void setCreator(CreatorBean creator) {
        this.creator = creator;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPrivateChat() {
        return privateChat;
    }

    public void setPrivateChat(String privateChat) {
        this.privateChat = privateChat;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
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

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPayForChat() {
        return payForChat;
    }

    public void setPayForChat(String payForChat) {
        this.payForChat = payForChat;
    }

    public String getPubStat() {
        return pubStat;
    }

    public void setPubStat(String pubStat) {
        this.pubStat = pubStat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRoomLocation() {
        return roomLocation;
    }

    public void setRoomLocation(String roomLocation) {
        this.roomLocation = roomLocation;
    }

    public String getRoomDistance() {
        return roomDistance;
    }

    public void setRoomDistance(String roomDistance) {
        this.roomDistance = roomDistance;
    }

    public String getPublishUrl() {
        return publishUrl;
    }

    public void setPublishUrl(String publishUrl) {
        this.publishUrl = publishUrl;
    }

    public static class CreatorBean implements Serializable {

        /**
         * id : 10395          主播id
         * nickName : 也是醉了     主播昵称
         * picUrl : http://q.qlogo.cn/qqapp/1105343191/99E54F4A4F6CFE3E1288D38877EA6BBB/100
         */

        private String id;
        private String nickName;
        private String picUrl;
        /**
         * signature : null
         * gender : 1
         * age : null
         * address : 北京市
         * level : 8
         * location : 40.00379,116.40320
         * online : null
         * distance : null
         */

        private Object signature;
        private String gender;
        private Object age;
        private String address;
        private String level;
        private String location;
        private Object online;
        private Object distance;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public Object getSignature() {
            return signature;
        }

        public void setSignature(Object signature) {
            this.signature = signature;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public Object getAge() {
            return age;
        }

        public void setAge(Object age) {
            this.age = age;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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

        public Object getOnline() {
            return online;
        }

        public void setOnline(Object online) {
            this.online = online;
        }

        public Object getDistance() {
            return distance;
        }

        public void setDistance(Object distance) {
            this.distance = distance;
        }
    }

    @Override
    public String toString() {
        return "LiveBean{" +
                "broadcastUrl='" + broadcastUrl + '\'' +
                ", creator=" + creator +
                ", city='" + city + '\'' +
                ", privateChat='" + privateChat + '\'' +
                ", roomId='" + roomId + '\'' +
                ", timeStamp='" + timeStamp + '\'' +
                ", livePicUrl='" + livePicUrl + '\'' +
                ", name='" + name + '\'' +
                ", onlineUserNum='" + onlineUserNum + '\'' +
                ", shareUrl='" + shareUrl + '\'' +
                ", id='" + id + '\'' +
                ", payForChat='" + payForChat + '\'' +
                ", pubStat='" + pubStat + '\'' +
                ", status='" + status + '\'' +
                ", privateFlag='" + privateFlag + '\'' +
                ", roomPay='" + roomPay + '\'' +
                ", roomPw='" + roomPw + '\'' +
                ", roomLocation='" + roomLocation + '\'' +
                ", roomDistance='" + roomDistance + '\'' +
                ", publishUrl='" + publishUrl + '\'' +
                '}';
    }
}
