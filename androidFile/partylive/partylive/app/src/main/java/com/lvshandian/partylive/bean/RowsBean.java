package com.lvshandian.partylive.bean;

/**
 * Created by sll on 2016/12/22.
 */

/**
 * 道更多的返回实体类
 *
 * @author sll
 * @time 2016/12/22 16:58
 */
public class RowsBean {
    /**
     * id : 12709
     * userName : 13641168243
     * password : e10adc3949ba59abbe56e057f20f883e
     * address : 北京市
     * age : null
     * nickName : 完美
     * picUrl : http://image.miulive.cc/avatar/12709/StringKey-13641168243-headerpic-1482236299.png
     * signature : 完美世界
     * gender : 1
     * phoneNum : 13641168243
     * level : 18
     * location : 40.00370,116.40297
     * poStrings : 9933604
     * veriInfo : null
     * verified : 0
     * neteaseAccount : miu_12709
     * neteaseToken : 2e4dedd75edd7736d5d9700dbe326382
     * regTime : 2016-12-20 20:18:06
     * live : 0
     * online : 0
     * refreshTime : 1482240087752
     * status : 1
     * goldCoin : 6675
     * autoUpdateTime : 2016-12-21 11:33:46
     * livePicUrl : http://image.miulive.cc/live/12709/liveshowpic-1482239896.png
     * vip : 0
     * deviceToken : 445CA86F-9303-4E67-B219-D5E164CF3996
     * fansNum : 18
     * followNum : 25
     * lockedGoldCoin : 188
     * receivedGoldCoin : 666766
     * spendGoldCoin : 993324
     * shareCode : YVU5RZ8V
     * StringroducerId : 0
     * gradePleased : 2
     * gradeUnsatisfy : 0
     * gradeSatisfied : 100
     * servicePleased : 7
     * serviceUnsatisfy : 0
     * serviceSatisfied : 100
     * payForVideoChat : 99
     * unionId : null
     * exchangeGoldCoin : 0
     * exchangeCash : 0.0
     * exchangeStatus : 0
     * roomId : 4714
     * robot : 0
     * consortiaId : 0
     * vipTime : null
     * channelId : 4
     * familyId : 27
     * familyLeaderFlag : 0
     * channel : {"id":null,"channelName":"旅游","channelDesc":null,"createtime":null,"updatetime":null,"createUserId":null,"updateUserId":null,"user":null,"usersList":null}
     * rooms : {"id":4714,"userId":12709,"name":"","city":"北京市","roomId":6110348,"livePicUrl":"http://img2.inke.cn/MTQ4MjI5MTQ4MzYwMiM4MzQjanBn.jpg","pubStat":1,"publishUrl":"rtmp://istream99.a8.com/live/1482239898229000?ikProfile=3&ikWidth=368&ikHeight=640&ikBr=640&ikHost=ws&ikOp=1","broadcastUrl":"http://pull99.a8.com/live/1482326885947094.flv","shareUrl":"http://miulive.cc/s/4714","payForChat":99,"privateChat":1,"timeStamp":1482239898385,"onlineUserNum":33,"status":1,"likeNum":0,"hotNum":0,"refreshTime":1482328830668,"privateFlag":null,"roomPw":null,"roomPay":null,"users":null}
     * family : null
     */
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
    /**
     * id : null
     * channelName : 旅游
     * channelDesc : null
     * createtime : null
     * updatetime : null
     * createUserId : null
     * updateUserId : null
     * user : null
     * usersList : null
     */

    private ChannelBean channel;
    /**
     * id : 4714
     * userId : 12709
     * name :
     * city : 北京市
     * roomId : 6110348
     * livePicUrl : http://img2.inke.cn/MTQ4MjI5MTQ4MzYwMiM4MzQjanBn.jpg
     * pubStat : 1
     * publishUrl : rtmp://istream99.a8.com/live/1482239898229000?ikProfile=3&ikWidth=368&ikHeight=640&ikBr=640&ikHost=ws&ikOp=1
     * broadcastUrl : http://pull99.a8.com/live/1482326885947094.flv
     * shareUrl : http://miulive.cc/s/4714
     * payForChat : 99
     * privateChat : 1
     * timeStamp : 1482239898385
     * onlineUserNum : 33
     * status : 1
     * likeNum : 0
     * hotNum : 0
     * refreshTime : 1482328830668
     * privateFlag : null
     * roomPw : null
     * roomPay : null
     * users : null
     */

    private RoomsBean rooms;
    private String family;

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

    public ChannelBean getChannel() {
        return channel;
    }

    public void setChannel(ChannelBean channel) {
        this.channel = channel;
    }

    public RoomsBean getRooms() {
        return rooms;
    }

    public void setRooms(RoomsBean rooms) {
        this.rooms = rooms;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public static class ChannelBean {
        private String id;
        private String channelName;
        private String channelDesc;
        private String createtime;
        private String updatetime;
        private String createUserId;
        private String updateUserId;
        private String user;
        private String usersList;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getChannelName() {
            return channelName;
        }

        public void setChannelName(String channelName) {
            this.channelName = channelName;
        }

        public String getChannelDesc() {
            return channelDesc;
        }

        public void setChannelDesc(String channelDesc) {
            this.channelDesc = channelDesc;
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

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getUsersList() {
            return usersList;
        }

        public void setUsersList(String usersList) {
            this.usersList = usersList;
        }
    }

}
