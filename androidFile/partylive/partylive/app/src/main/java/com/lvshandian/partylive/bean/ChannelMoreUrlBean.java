package com.lvshandian.partylive.bean;

/**
 * Created by sll on 2016/12/22.
 */

import java.util.List;

/**
 * 频道更多的返回类
 *
 * @author sll
 * @time 2016/12/22 16:56
 */
public class ChannelMoreUrlBean {

    /**
     * success : true
     * code : 0
     * msg :
     * obj : {"total":2,"rows":[{"id":12709,"userName":"13641168243","password":"e10adc3949ba59abbe56e057f20f883e","address":"北京市","age":null,"nickName":"完美","picUrl":"http://image.miulive.cc/avatar/12709/objectKey-13641168243-headerpic-1482236299.png","signature":"完美世界","gender":"1","phoneNum":"13641168243","level":18,"location":"40.00370,116.40297","points":9933604,"veriInfo":null,"verified":0,"neteaseAccount":"miu_12709","neteaseToken":"2e4dedd75edd7736d5d9700dbe326382","regTime":"2016-12-20 20:18:06","live":0,"online":0,"refreshTime":1482240087752,"status":1,"goldCoin":6675,"autoUpdateTime":"2016-12-21 11:33:46","livePicUrl":"http://image.miulive.cc/live/12709/liveshowpic-1482239896.png","vip":0,"deviceToken":"445CA86F-9303-4E67-B219-D5E164CF3996","fansNum":18,"followNum":25,"lockedGoldCoin":188,"receivedGoldCoin":666766,"spendGoldCoin":993324,"shareCode":"YVU5RZ8V","introducerId":0,"gradePleased":2,"gradeUnsatisfy":0,"gradeSatisfied":100,"servicePleased":7,"serviceUnsatisfy":0,"serviceSatisfied":100,"payForVideoChat":99,"unionId":null,"exchangeGoldCoin":0,"exchangeCash":0,"exchangeStatus":0,"roomId":4714,"robot":0,"consortiaId":0,"vipTime":null,"channelId":4,"familyId":27,"familyLeaderFlag":0,"channel":{"id":null,"channelName":"旅游","channelDesc":null,"createtime":null,"updatetime":null,"createUserId":null,"updateUserId":null,"user":null,"usersList":null},"rooms":{"id":4714,"userId":12709,"name":"","city":"北京市","roomId":6110348,"livePicUrl":"http://img2.inke.cn/MTQ4MjI5MTQ4MzYwMiM4MzQjanBn.jpg","pubStat":1,"publishUrl":"rtmp://istream99.a8.com/live/1482239898229000?ikProfile=3&ikWidth=368&ikHeight=640&ikBr=640&ikHost=ws&ikOp=1","broadcastUrl":"http://pull99.a8.com/live/1482326885947094.flv","shareUrl":"http://miulive.cc/s/4714","payForChat":99,"privateChat":1,"timeStamp":1482239898385,"onlineUserNum":33,"status":1,"likeNum":0,"hotNum":0,"refreshTime":1482328830668,"privateFlag":null,"roomPw":null,"roomPay":null,"users":null},"family":null},{"id":13211,"userName":"7442FE87BCBDCCC6C8590E78BA7FAAB2","password":"58D058E8B5C766FD4B1023C5ABC9EBFB","address":"北京市","age":null,"nickName":"驯鹿","picUrl":"http://q.qlogo.cn/qqapp/1105632511/7442FE87BCBDCCC6C8590E78BA7FAAB2/100","signature":null,"gender":"1","phoneNum":null,"level":6,"location":"40.00371,116.40284","points":85749,"veriInfo":null,"verified":0,"neteaseAccount":"miu_0","neteaseToken":"123","regTime":"2016-12-20 20:21:24","live":0,"online":0,"refreshTime":1482303507929,"status":1,"goldCoin":9991435,"autoUpdateTime":"2016-12-21 15:08:44","livePicUrl":"http://image.miulive.cc/live/13211/liveshowpic-1482302002.png","vip":0,"deviceToken":"8AE62199-5C42-4E5F-8E25-EF5E733F268F","fansNum":0,"followNum":1,"lockedGoldCoin":0,"receivedGoldCoin":13140,"spendGoldCoin":8564,"shareCode":"7UXDYC36","introducerId":0,"gradePleased":0,"gradeUnsatisfy":0,"gradeSatisfied":0,"servicePleased":0,"serviceUnsatisfy":0,"serviceSatisfied":0,"payForVideoChat":100,"unionId":"7442FE87BCBDCCC6C8590E78BA7FAAB2","exchangeGoldCoin":0,"exchangeCash":0,"exchangeStatus":1,"roomId":4719,"robot":0,"consortiaId":0,"vipTime":null,"channelId":4,"familyId":null,"familyLeaderFlag":0,"channel":{"id":null,"channelName":"旅游","channelDesc":null,"createtime":null,"updatetime":null,"createUserId":null,"updateUserId":null,"user":null,"usersList":null},"rooms":{"id":4719,"userId":13211,"name":"","city":"北京市","roomId":6131137,"livePicUrl":"http://img2.inke.cn/MTQ4MjIzMzU4MDQ5MSM4MTkjanBn.jpg","pubStat":1,"publishUrl":"rtmp://istream4.a8.com/live/1482302011968000?ikProfile=3&ikWidth=368&ikHeight=640&ikBr=640&ikHost=ws&ikOp=1","broadcastUrl":"http://pull99.a8.com/live/1482327121986463.flv","shareUrl":"http://miulive.cc/s/4719","payForChat":100,"privateChat":1,"timeStamp":1482302012127,"onlineUserNum":31,"status":1,"likeNum":0,"hotNum":0,"refreshTime":1482328830825,"privateFlag":null,"roomPw":null,"roomPay":null,"users":null},"family":null}]}
     */

    private boolean success;
    private int code;
    private String msg;
    /**
     * total : 2
     * rows : [{"id":12709,"userName":"13641168243","password":"e10adc3949ba59abbe56e057f20f883e","address":"北京市","age":null,"nickName":"完美","picUrl":"http://image.miulive.cc/avatar/12709/objectKey-13641168243-headerpic-1482236299.png","signature":"完美世界","gender":"1","phoneNum":"13641168243","level":18,"location":"40.00370,116.40297","points":9933604,"veriInfo":null,"verified":0,"neteaseAccount":"miu_12709","neteaseToken":"2e4dedd75edd7736d5d9700dbe326382","regTime":"2016-12-20 20:18:06","live":0,"online":0,"refreshTime":1482240087752,"status":1,"goldCoin":6675,"autoUpdateTime":"2016-12-21 11:33:46","livePicUrl":"http://image.miulive.cc/live/12709/liveshowpic-1482239896.png","vip":0,"deviceToken":"445CA86F-9303-4E67-B219-D5E164CF3996","fansNum":18,"followNum":25,"lockedGoldCoin":188,"receivedGoldCoin":666766,"spendGoldCoin":993324,"shareCode":"YVU5RZ8V","introducerId":0,"gradePleased":2,"gradeUnsatisfy":0,"gradeSatisfied":100,"servicePleased":7,"serviceUnsatisfy":0,"serviceSatisfied":100,"payForVideoChat":99,"unionId":null,"exchangeGoldCoin":0,"exchangeCash":0,"exchangeStatus":0,"roomId":4714,"robot":0,"consortiaId":0,"vipTime":null,"channelId":4,"familyId":27,"familyLeaderFlag":0,"channel":{"id":null,"channelName":"旅游","channelDesc":null,"createtime":null,"updatetime":null,"createUserId":null,"updateUserId":null,"user":null,"usersList":null},"rooms":{"id":4714,"userId":12709,"name":"","city":"北京市","roomId":6110348,"livePicUrl":"http://img2.inke.cn/MTQ4MjI5MTQ4MzYwMiM4MzQjanBn.jpg","pubStat":1,"publishUrl":"rtmp://istream99.a8.com/live/1482239898229000?ikProfile=3&ikWidth=368&ikHeight=640&ikBr=640&ikHost=ws&ikOp=1","broadcastUrl":"http://pull99.a8.com/live/1482326885947094.flv","shareUrl":"http://miulive.cc/s/4714","payForChat":99,"privateChat":1,"timeStamp":1482239898385,"onlineUserNum":33,"status":1,"likeNum":0,"hotNum":0,"refreshTime":1482328830668,"privateFlag":null,"roomPw":null,"roomPay":null,"users":null},"family":null},{"id":13211,"userName":"7442FE87BCBDCCC6C8590E78BA7FAAB2","password":"58D058E8B5C766FD4B1023C5ABC9EBFB","address":"北京市","age":null,"nickName":"驯鹿","picUrl":"http://q.qlogo.cn/qqapp/1105632511/7442FE87BCBDCCC6C8590E78BA7FAAB2/100","signature":null,"gender":"1","phoneNum":null,"level":6,"location":"40.00371,116.40284","points":85749,"veriInfo":null,"verified":0,"neteaseAccount":"miu_0","neteaseToken":"123","regTime":"2016-12-20 20:21:24","live":0,"online":0,"refreshTime":1482303507929,"status":1,"goldCoin":9991435,"autoUpdateTime":"2016-12-21 15:08:44","livePicUrl":"http://image.miulive.cc/live/13211/liveshowpic-1482302002.png","vip":0,"deviceToken":"8AE62199-5C42-4E5F-8E25-EF5E733F268F","fansNum":0,"followNum":1,"lockedGoldCoin":0,"receivedGoldCoin":13140,"spendGoldCoin":8564,"shareCode":"7UXDYC36","introducerId":0,"gradePleased":0,"gradeUnsatisfy":0,"gradeSatisfied":0,"servicePleased":0,"serviceUnsatisfy":0,"serviceSatisfied":0,"payForVideoChat":100,"unionId":"7442FE87BCBDCCC6C8590E78BA7FAAB2","exchangeGoldCoin":0,"exchangeCash":0,"exchangeStatus":1,"roomId":4719,"robot":0,"consortiaId":0,"vipTime":null,"channelId":4,"familyId":null,"familyLeaderFlag":0,"channel":{"id":null,"channelName":"旅游","channelDesc":null,"createtime":null,"updatetime":null,"createUserId":null,"updateUserId":null,"user":null,"usersList":null},"rooms":{"id":4719,"userId":13211,"name":"","city":"北京市","roomId":6131137,"livePicUrl":"http://img2.inke.cn/MTQ4MjIzMzU4MDQ5MSM4MTkjanBn.jpg","pubStat":1,"publishUrl":"rtmp://istream4.a8.com/live/1482302011968000?ikProfile=3&ikWidth=368&ikHeight=640&ikBr=640&ikHost=ws&ikOp=1","broadcastUrl":"http://pull99.a8.com/live/1482327121986463.flv","shareUrl":"http://miulive.cc/s/4719","payForChat":100,"privateChat":1,"timeStamp":1482302012127,"onlineUserNum":31,"status":1,"likeNum":0,"hotNum":0,"refreshTime":1482328830825,"privateFlag":null,"roomPw":null,"roomPay":null,"users":null},"family":null}]
     */

    private ObjBean obj;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public static class ObjBean {
        private int total;

        private List<RowsBean> rows;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<RowsBean> getRows() {
            return rows;
        }

        public void setRows(List<RowsBean> rows) {
            this.rows = rows;
        }

    }
}
