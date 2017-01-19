package com.lvshandian.partylive.bean;

/**
 * Created by sll on 2016/12/23.
 */

import java.util.List;

/**
 * 直播家族中的返回实体类
 *
 * @author sll
 * @time 2016/12/23 11:08
 */
public class LiveFamilyMemberBean {

    /**
     * success : true
     * code : 1
     * msg : 查询成功
     * obj : [{"id":12861,"userName":"o9jFVwSpQSrlkAkXIk6COGTqBLwU","password":"c_4rtCR9xO-wzwlK5fOdwj984tnGTkl5De_xWxKMU59ESmDS754VhqPv8IyHAksjF5fbL5_uDlF3W2guC7juBXISnAZ2bJSyPCCRvdhBg8w","address":"北京市","age":null,"nickName":"米阳SOHO","picUrl":"http://wx.qlogo.cn/mmopen/PiajxSqBRaEJB7GHRVSzvj2kjqqrea3YQ6uMVc4axezZnLROHdEDAYbRY81L2wZAKTasZwGtwsy9pjYHibIMcdKQ/0","signature":null,"gender":"1","phoneNum":null,"level":8,"location":"40.00387,116.40281","poStrings":390497,"veriInfo":null,"verified":1,"neteaseAccount":"miu_12861","neteaseToken":"b3db01c32cbe318ebef0bfbfdc94973d","regTime":"2016-12-20 14:27:13","live":1,"online":1,"refreshTime":1482462157703,"status":1,"goldCoin":49975526,"autoUpdateTime":"2016-12-22 18:13:35","livePicUrl":"http://image.miulive.cc/live/12861/liveshowpic-1482462044.png","vip":1,"deviceToken":"EE92897A-845B-408E-A97F-27A4F9B170A4","fansNum":13,"followNum":19,"lockedGoldCoin":2597,"receivedGoldCoin":269975,"spendGoldCoin":38974,"shareCode":"LJQE25F4","StringroducerId":0,"gradePleased":8,"gradeUnsatisfy":4,"gradeSatisfied":66,"servicePleased":7,"serviceUnsatisfy":4,"serviceSatisfied":63,"payForVideoChat":100,"unionId":"o_AvUw0lvTwO83fJJ6DVDekIcYUk","exchangeGoldCoin":29000,"exchangeCash":0,"exchangeStatus":1,"roomId":5143,"robot":0,"consortiaId":0,"vipTime":"2017-12-13 11:12:09","channelId":0,"familyId":27,"familyLeaderFlag":0,"channel":null,"rooms":{"id":5143,"userId":12861,"name":"","city":"北京市","roomId":6179442,"livePicUrl":"http://image.miulive.cc/live/12861/liveshowpic-1482462044.png","pubStat":1,"publishUrl":"rtmp://istream99.a8.com/live/1482462046016000?ikProfile=3&ikWidth=368&ikHeight=640&ikBr=640&ikHost=ws&ikOp=1","broadcastUrl":"http://pull99.a8.com/live/1482462046016000.flv","shareUrl":"http://miulive.cc/s/5143","payForChat":100,"privateChat":1,"timeStamp":1482462046160,"onlineUserNum":2,"status":1,"likeNum":0,"hotNum":0,"refreshTime":1482462167135,"privateFlag":null,"roomPw":null,"roomPay":null,"users":null},"family":{"familyId":null,"familyName":"才艺家族","familyDesc":null,"createtime":null,"updatetime":null,"createUserId":null,"updateUserId":null}}]
     */

    private boolean success;
    private String code;
    private String msg;
    /**
     * id : 12861
     * userName : o9jFVwSpQSrlkAkXIk6COGTqBLwU
     * password : c_4rtCR9xO-wzwlK5fOdwj984tnGTkl5De_xWxKMU59ESmDS754VhqPv8IyHAksjF5fbL5_uDlF3W2guC7juBXISnAZ2bJSyPCCRvdhBg8w
     * address : 北京市
     * age : null
     * nickName : 米阳SOHO
     * picUrl : http://wx.qlogo.cn/mmopen/PiajxSqBRaEJB7GHRVSzvj2kjqqrea3YQ6uMVc4axezZnLROHdEDAYbRY81L2wZAKTasZwGtwsy9pjYHibIMcdKQ/0
     * signature : null
     * gender : 1
     * phoneNum : null
     * level : 8
     * location : 40.00387,116.40281
     * poStrings : 390497
     * veriInfo : null
     * verified : 1
     * neteaseAccount : miu_12861
     * neteaseToken : b3db01c32cbe318ebef0bfbfdc94973d
     * regTime : 2016-12-20 14:27:13
     * live : 1
     * online : 1
     * refreshTime : 1482462157703
     * status : 1
     * goldCoin : 49975526
     * autoUpdateTime : 2016-12-22 18:13:35
     * livePicUrl : http://image.miulive.cc/live/12861/liveshowpic-1482462044.png
     * vip : 1
     * deviceToken : EE92897A-845B-408E-A97F-27A4F9B170A4
     * fansNum : 13
     * followNum : 19
     * lockedGoldCoin : 2597
     * receivedGoldCoin : 269975
     * spendGoldCoin : 38974
     * shareCode : LJQE25F4
     * StringroducerId : 0
     * gradePleased : 8
     * gradeUnsatisfy : 4
     * gradeSatisfied : 66
     * servicePleased : 7
     * serviceUnsatisfy : 4
     * serviceSatisfied : 63
     * payForVideoChat : 100
     * unionId : o_AvUw0lvTwO83fJJ6DVDekIcYUk
     * exchangeGoldCoin : 29000
     * exchangeCash : 0.0
     * exchangeStatus : 1
     * roomId : 5143
     * robot : 0
     * consortiaId : 0
     * vipTime : 2017-12-13 11:12:09
     * channelId : 0
     * familyId : 27
     * familyLeaderFlag : 0
     * channel : null
     * rooms : {"id":5143,"userId":12861,"name":"","city":"北京市","roomId":6179442,"livePicUrl":"http://image.miulive.cc/live/12861/liveshowpic-1482462044.png","pubStat":1,"publishUrl":"rtmp://istream99.a8.com/live/1482462046016000?ikProfile=3&ikWidth=368&ikHeight=640&ikBr=640&ikHost=ws&ikOp=1","broadcastUrl":"http://pull99.a8.com/live/1482462046016000.flv","shareUrl":"http://miulive.cc/s/5143","payForChat":100,"privateChat":1,"timeStamp":1482462046160,"onlineUserNum":2,"status":1,"likeNum":0,"hotNum":0,"refreshTime":1482462167135,"privateFlag":null,"roomPw":null,"roomPay":null,"users":null}
     * family : {"familyId":null,"familyName":"才艺家族","familyDesc":null,"createtime":null,"updatetime":null,"createUserId":null,"updateUserId":null}
     */

    private List<ObjBean> obj;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ObjBean> getObj() {
        return obj;
    }

    public void setObj(List<ObjBean> obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        private String id;
        private String userName;
        private String password;
        private String address;
        private String age;
        private String nickName;
        private String picUrl;
        private String signature;
        private String gender;
        private String phoneNum;
        private String level;
        private String location;
        private String poStrings;
        private String veriInfo;
        private String verified;
        private String neteaseAccount;
        private String neteaseToken;
        private String regTime;
        private String live;
        private String online;
        private long refreshTime;
        private String status;
        private String goldCoin;
        private String autoUpdateTime;
        private String livePicUrl;
        private String vip;
        private String deviceToken;
        private String fansNum;
        private String followNum;
        private String lockedGoldCoin;
        private String receivedGoldCoin;
        private String spendGoldCoin;
        private String shareCode;
        private String StringroducerId;
        private String gradePleased;
        private String gradeUnsatisfy;
        private String gradeSatisfied;
        private String servicePleased;
        private String serviceUnsatisfy;
        private String serviceSatisfied;
        private String payForVideoChat;
        private String unionId;
        private String exchangeGoldCoin;
        private double exchangeCash;
        private String exchangeStatus;
        private String roomId;
        private String robot;
        private String consortiaId;
        private String vipTime;
        private String channelId;
        private String familyId;
        private String familyLeaderFlag;
        private String channel;
        private RoomsBean rooms;
        /**
         * familyId : null
         * familyName : 才艺家族
         * familyDesc : null
         * createtime : null
         * updatetime : null
         * createUserId : null
         * updateUserId : null
         */

        private FamilyBean family;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
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

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getPhoneNum() {
            return phoneNum;
        }

        public void setPhoneNum(String phoneNum) {
            this.phoneNum = phoneNum;
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

        public String getPoStrings() {
            return poStrings;
        }

        public void setPoStrings(String poStrings) {
            this.poStrings = poStrings;
        }

        public String getVeriInfo() {
            return veriInfo;
        }

        public void setVeriInfo(String veriInfo) {
            this.veriInfo = veriInfo;
        }

        public String getVerified() {
            return verified;
        }

        public void setVerified(String verified) {
            this.verified = verified;
        }

        public String getNeteaseAccount() {
            return neteaseAccount;
        }

        public void setNeteaseAccount(String neteaseAccount) {
            this.neteaseAccount = neteaseAccount;
        }

        public String getNeteaseToken() {
            return neteaseToken;
        }

        public void setNeteaseToken(String neteaseToken) {
            this.neteaseToken = neteaseToken;
        }

        public String getRegTime() {
            return regTime;
        }

        public void setRegTime(String regTime) {
            this.regTime = regTime;
        }

        public String getLive() {
            return live;
        }

        public void setLive(String live) {
            this.live = live;
        }

        public String getOnline() {
            return online;
        }

        public void setOnline(String online) {
            this.online = online;
        }

        public long getRefreshTime() {
            return refreshTime;
        }

        public void setRefreshTime(long refreshTime) {
            this.refreshTime = refreshTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getGoldCoin() {
            return goldCoin;
        }

        public void setGoldCoin(String goldCoin) {
            this.goldCoin = goldCoin;
        }

        public String getAutoUpdateTime() {
            return autoUpdateTime;
        }

        public void setAutoUpdateTime(String autoUpdateTime) {
            this.autoUpdateTime = autoUpdateTime;
        }

        public String getLivePicUrl() {
            return livePicUrl;
        }

        public void setLivePicUrl(String livePicUrl) {
            this.livePicUrl = livePicUrl;
        }

        public String getVip() {
            return vip;
        }

        public void setVip(String vip) {
            this.vip = vip;
        }

        public String getDeviceToken() {
            return deviceToken;
        }

        public void setDeviceToken(String deviceToken) {
            this.deviceToken = deviceToken;
        }

        public String getFansNum() {
            return fansNum;
        }

        public void setFansNum(String fansNum) {
            this.fansNum = fansNum;
        }

        public String getFollowNum() {
            return followNum;
        }

        public void setFollowNum(String followNum) {
            this.followNum = followNum;
        }

        public String getLockedGoldCoin() {
            return lockedGoldCoin;
        }

        public void setLockedGoldCoin(String lockedGoldCoin) {
            this.lockedGoldCoin = lockedGoldCoin;
        }

        public String getReceivedGoldCoin() {
            return receivedGoldCoin;
        }

        public void setReceivedGoldCoin(String receivedGoldCoin) {
            this.receivedGoldCoin = receivedGoldCoin;
        }

        public String getSpendGoldCoin() {
            return spendGoldCoin;
        }

        public void setSpendGoldCoin(String spendGoldCoin) {
            this.spendGoldCoin = spendGoldCoin;
        }

        public String getShareCode() {
            return shareCode;
        }

        public void setShareCode(String shareCode) {
            this.shareCode = shareCode;
        }

        public String getStringroducerId() {
            return StringroducerId;
        }

        public void setStringroducerId(String StringroducerId) {
            this.StringroducerId = StringroducerId;
        }

        public String getGradePleased() {
            return gradePleased;
        }

        public void setGradePleased(String gradePleased) {
            this.gradePleased = gradePleased;
        }

        public String getGradeUnsatisfy() {
            return gradeUnsatisfy;
        }

        public void setGradeUnsatisfy(String gradeUnsatisfy) {
            this.gradeUnsatisfy = gradeUnsatisfy;
        }

        public String getGradeSatisfied() {
            return gradeSatisfied;
        }

        public void setGradeSatisfied(String gradeSatisfied) {
            this.gradeSatisfied = gradeSatisfied;
        }

        public String getServicePleased() {
            return servicePleased;
        }

        public void setServicePleased(String servicePleased) {
            this.servicePleased = servicePleased;
        }

        public String getServiceUnsatisfy() {
            return serviceUnsatisfy;
        }

        public void setServiceUnsatisfy(String serviceUnsatisfy) {
            this.serviceUnsatisfy = serviceUnsatisfy;
        }

        public String getServiceSatisfied() {
            return serviceSatisfied;
        }

        public void setServiceSatisfied(String serviceSatisfied) {
            this.serviceSatisfied = serviceSatisfied;
        }

        public String getPayForVideoChat() {
            return payForVideoChat;
        }

        public void setPayForVideoChat(String payForVideoChat) {
            this.payForVideoChat = payForVideoChat;
        }

        public String getUnionId() {
            return unionId;
        }

        public void setUnionId(String unionId) {
            this.unionId = unionId;
        }

        public String getExchangeGoldCoin() {
            return exchangeGoldCoin;
        }

        public void setExchangeGoldCoin(String exchangeGoldCoin) {
            this.exchangeGoldCoin = exchangeGoldCoin;
        }

        public double getExchangeCash() {
            return exchangeCash;
        }

        public void setExchangeCash(double exchangeCash) {
            this.exchangeCash = exchangeCash;
        }

        public String getExchangeStatus() {
            return exchangeStatus;
        }

        public void setExchangeStatus(String exchangeStatus) {
            this.exchangeStatus = exchangeStatus;
        }

        public String getRoomId() {
            return roomId;
        }

        public void setRoomId(String roomId) {
            this.roomId = roomId;
        }

        public String getRobot() {
            return robot;
        }

        public void setRobot(String robot) {
            this.robot = robot;
        }

        public String getConsortiaId() {
            return consortiaId;
        }

        public void setConsortiaId(String consortiaId) {
            this.consortiaId = consortiaId;
        }

        public String getVipTime() {
            return vipTime;
        }

        public void setVipTime(String vipTime) {
            this.vipTime = vipTime;
        }

        public String getChannelId() {
            return channelId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        public String getFamilyId() {
            return familyId;
        }

        public void setFamilyId(String familyId) {
            this.familyId = familyId;
        }

        public String getFamilyLeaderFlag() {
            return familyLeaderFlag;
        }

        public void setFamilyLeaderFlag(String familyLeaderFlag) {
            this.familyLeaderFlag = familyLeaderFlag;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public RoomsBean getRooms() {
            return rooms;
        }

        public void setRooms(RoomsBean rooms) {
            this.rooms = rooms;
        }

        public FamilyBean getFamily() {
            return family;
        }

        public void setFamily(FamilyBean family) {
            this.family = family;
        }

        public static class FamilyBean {
            private String familyId;
            private String familyName;
            private String familyDesc;
            private String createtime;
            private String updatetime;
            private String createUserId;
            private String updateUserId;

            public String getFamilyId() {
                return familyId;
            }

            public void setFamilyId(String familyId) {
                this.familyId = familyId;
            }

            public String getFamilyName() {
                return familyName;
            }

            public void setFamilyName(String familyName) {
                this.familyName = familyName;
            }

            public String getFamilyDesc() {
                return familyDesc;
            }

            public void setFamilyDesc(String familyDesc) {
                this.familyDesc = familyDesc;
            }

            public String getCreatetime() {
                return createtime;
            }

            public void setCreatetime(String createtime) {
                this.createtime = createtime;
            }

            public String getUpdatetime() {
                return updatetime;
            }

            public void setUpdatetime(String updatetime) {
                this.updatetime = updatetime;
            }

            public String getCreateUserId() {
                return createUserId;
            }

            public void setCreateUserId(String createUserId) {
                this.createUserId = createUserId;
            }

            public String getUpdateUserId() {
                return updateUserId;
            }

            public void setUpdateUserId(String updateUserId) {
                this.updateUserId = updateUserId;
            }
        }
    }
}
