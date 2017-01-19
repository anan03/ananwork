package com.lvshandian.partylive;

import com.lvshandian.partylive.utils.LogUtils;
import com.lvshandian.partylive.utils.TextUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by zhang on 2016/10/11.
 * 创建网络管理类请求，网址管理类；
 */
public class UrlBuilder {

    //    public static final String serverUrl = "http://192.168.0.105:8080";// 服务器网址公网  http://60.205.171.6:8800
//    public static final String chargeServerUrl = "http://192.168.0.105:8081/admin";// 充值端口
    public static final String serverUrl = "http://60.205.171.6:8800";// 服务器网址公网  http://60.205.171.6:8800
    public static final String chargeServerUrl = "http://60.205.171.6:80/admin";// 充值端口
    public static final String LOGIN = "/api/v1/login";    //登录
    public static final String REGISTER = "/api/v1/register"; //注册
    public static final String CODE = "/api/v1/code";    //邀请码
    public static final String HAllLIST = "/api/v1/live/hot";    //大厅直播数据
    public static final String reward = "/api/v1/room/reward";    //大厅直播数据
    public static final String user = "/api/v1/user";    //修改用户信息
    public static final String aliyun = "/aliyun/token";    //请求阿里云id、key
    public static final String IMAGER_BANNER = "/appCarouselFigure/carouselList";    //请求阿里云id、key
    public static final String yqRen = "/api/v1/user/";//获取自己跌邀请的人
    public static final String ATTENTION = "/api/v1/live/user/";//关注人的直播列表
    public static final String START = "/api/v1/room/create/";//新建直播/api/v1/room/create
    public static final String SEARCH = "/api/v1/user/find";//搜索用户
    public static final String ATTENTION_USER = "/api/v1/user/follow";//关注取消关注
    public static final String IF_ATTENTION = "/api/v1/user/info";//判断是否被关注过
    public static final String SEND_GIFT = "/api/v1/room/reward";//送礼物
    public static final String joinRoom = "/api/v1/room/";//加入直播间
    public static final String wxPay = "/appwx/wxPay";//微信支付
    public static final String ifEnter = "/appRooms/ifEnter";//判断是否进入过该房间
    public static final String updateCoin = "/appRooms/updateCoin";//支付进入
    public static final String join = "/appRooms/join";//进入直播间
    public static final String selectListByChannel = "/appusers/selectListByChannel.do";//查询正在直播的用户按照频道进行分组(粉丝数最多的前两条记录)展示
    public static final String selectListByChannelId = "/appusers/selectListByChannelId.do";//查询正在直播的用户按照频道进行分组(粉丝数最多的前两条记录)展示
    public static final String selectFamilyMember = "/appusers/selectFamilyMember.do";//家族
    public static final String MY_PHOTO_UPLOAD = "/api/v1/user/album/pic";//我的界面图片上传
    public static final String MY_VIDEO_UPLOAD = "/api/v1/user/album/video";//我的界面图片上传
    public static final String modifyPass = "/api/v1/user/password";//修改密码
    public static final String realInfoAuthen = "/api/v1/user/verify";//实名认证
    public static final String recent_visitors = "/api/v1/user/";//最近访客
    public static final String openRegister = "/api/v1/open/register";//第三方登录
    public static final String roomShow = "/api/v1/room/show";//新建表演
    public static final String GET_GIFT = "/api/v1/gift";//获取礼物
    public static final String room = "/api/v1/room/";//获取礼物
    public static final String room_vod = "/api/v1/room/vod";//点播接口
    public static final String vodResult = "/api/v1/user/service/result";//用户满意度接口
    public static final String roomJoin = "/api/v1/room/join";//加入直播间
    public static final String barrage = "/api/v1/barrage/";//发送弹幕消息扣金币
    public static final String roomExit = "/api/v1/room/exit";//退出直播间
    public static final String showWatch = "/api/v1/room/show/watch";//观众确认观看表演
    public static final String showBuy = "/api/v1/room/show/buy";//主播开始表演后观众观看表演
    public static final String userSearch = "/api/v1/user/search";//查找（附近）用户列表
    public static final String roomSearch = "/api/v1/room/search";//查找（附近）直播列表
    public static final String report = "/api/v1/user/report";//举报
    //阿里云
    public static final String ALIYUN_IMG = "http://partylive.oss-cn-shanghai.aliyuncs.com/";//阿里云图片視頻生成地址
    public static final String OSS_ENDPOINT = "http://oss-cn-shanghai.aliyuncs.com";//阿里云OSS_ENDPOINT
    public static final String BUCKET_NAME = "partylive";// 阿里云BUCKET_NAME  OSS

    /**
     * 兑换
     *
     * @author sll
     * @time 2016/12/23 18:05
     */
    public static final String exchange(String userId, String goldCoin) {

        return "/api/v1/user/" + userId + "/exchange/" + goldCoin;
    }

    public static final String cloesAnchor(String id) {//关闭直播

        return "/api/v1/room/" + id + "/leave";
    }

    /**
     * 主播获取全部申请(连麦)列表
     * http://miulive.cc:8080/api/v1/room/{roomId}/live
     *
     * @author sll
     * @time 2016/12/9 17:44
     */
    public static final String roomLive(String roomId) {
        return "/api/v1/room/" + roomId + "/live";
    }

    /**
     * 主播30进行一次数据的刷新；
     * http://miulive.cc:8080/api/v1/room/{roomId}/live
     *
     * @author sll
     * @time 2016/12/9 17:44
     */
    public static final String TimerLive(String roomId) {
        return "/api/v1/room/" + roomId + "/alive";
    }

    /**
     * 用戶300秒进行一次数据的更新；
     * http://miulive.cc:8080/api/v1/room/{roomId}/live
     *
     * @author sll
     * @time 2016/12/9 17:44
     */
    public static final String TimerUserLive(String roomId, String userId) {
        return "/api/v1/room/" + roomId + "/user/" + userId + "/alive";
    }

    /**
     * 获取直播间中的点播列表
     * http://miulive.cc:8080/api/v1/room/{roomId}/vods
     *
     * @author sll
     * @time 2016/12/8 16:20
     */
    public static final String roomVods(String roomId) {
        return "/api/v1/room/" + roomId + "/vods";
    }

    /**
     * 主播开始表演
     * http://miulive.cc:8080/api/v1/room/vod/{id}/start
     *
     * @param id 为表演id
     * @author sll
     * @time 2016/12/8 18:54
     */
    public static final String roomVodStart(String id) {
        return "/api/v1/room/vod/" + id + "/start";
    }

    /**
     * 主主播结束表演
     * http://miulive.cc:8080/api/v1/room/vod/{id}/end
     * bi
     *
     * @param id 为表演id
     * @author sll
     * @time 2016/12/8 18:54
     */
    public static final String roomVodEnd(String id) {
        return "/api/v1/room/vod/" + id + "/end";
    }

    public static final String room_lianmai = "/api/v1/room/live/apply";//连麦
    public static final String room_result = "/api/v1/room/live/result";//主播确认申请状态

    public static final String myPhoto(String id) {//我的界面图片展示

        return "/api/v1/user/" + id + "/album/pics";

    }

    /**
     * 获取用户申请信息
     * 服务器接口地址：http://miulive.cc:8080/api/v1/room/{roomId}/{userId}/live get请求 其中roomId为直播间id，不是roomid 参数字段，userId为用户id 举例
     *
     * @author sll
     * @time 2016/12/16 11:08
     */
    public static final String roomLive(String roomId, String userId) {

        return "/api/v1/room/" + roomId + "/" + userId + "/live";

    }

    /**
     * 观众获取有效申请列表
     * 服务器接口地址：http://miulive.cc:8080/api/v1/room/{roomId}/live/valid get请求 其中roomId为直播间id，不是roomid 参数字段
     *
     * @author sll
     * @time 2016/12/16 11:08
     */
    public static final String roomLiveValid(String roomId, String userId) {

        return "/api/v1/room/" + roomId + "/" + userId + "/live/valid";

    }

    /**
     * 观众退出连线
     * 服务器接口地址：http://miulive.cc:8080/api/v1/room/{roomId}/{userId}/live/exit get请求 其中roomId为直播间id，不是roomid 参数字段，userId为用户id
     *
     * @author sll
     * @time 2016/12/16 11:08
     */
    public static final String roomLiveExit(String roomId, String userId) {

        return "/api/v1/room/" + roomId + "/" + userId + "/live/exit";

    }

    public static final String photoDelete(String id) {//图片详情删除
        return "/api/v1/user/album/" + id;

    }


    public static final String myattention(String id) {//关注人的直播列表
        return "/api/v1/live/user/" + id + "/follow";

    }


    public static final String myVideo(String id) {//我的界面视频图片展示
        return "/api/v1/user/" + id + "/album/videos";
    }

    public static final String livepush(String id) {//livepush
        return "/api/v1/room/" + id + "/push";
    }


    /**
     * 构建请求url
     *
     * @param route
     * @param params
     * @return
     */
    public String build(String route, Map<String, String> params) {
        StringBuilder sb = new StringBuilder(serverUrl);//.append("/").append(serverName);
        sb.append(route);
        if (params != null && params.size() > 0) {
            int index = 0;
            for (String key : params.keySet()) {
                String mark = index++ == 0 ? "?" : "&";
                String v = params.get(key);
                if (TextUtils.isEmpty(v)) {
                    LogUtils.e("key: " + key);
                }
                try {
                    String value = URLEncoder.encode(params.get(key), "UTF-8");
                    sb.append(mark).append(key).append("=").append(value);
                } catch (UnsupportedEncodingException e) {
                }
            }
        }
        return sb.toString();
    }

}
