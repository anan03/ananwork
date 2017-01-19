package com.lvshandian.partylive.httprequest;

/**
 * author: newlq on 2016/9/1 17:08.
 * email： @lvshandian.com
 * company:北京绿闪电科技有限公司
 * details：请求常量类， 用于判断请求与handler 回调的msg.what 的值 是否是统一请求
 */
public interface RequestCode {

    int LOGIN_TAG = 1000;     //登录
    int REGISTER_TAG = 1001;     //注册
    int GETHALLLIST = 2000; //huoqu
    int USER_TAG = 1002; //修改用户信息
    int ALIYUN_TAG = 1003; //请求阿里云id、key
    int START_LIVE = 1004;  //开启直播
    int MY_PHOTO = 1005; //请求图片
    int MY_PHOTO_LOAD = 1006; //请求阿里云id、key
    int ATTENTION_LIVE = 1007;//关注人的列表
    int SEACH_USER = 1008;//搜索用户
    int ATTENTION_USER = 1008;//关注人的列表
    int IF_ATTENTION = 1009;//判断是否被关注过
    int GET_GIFT = 1010;//获取礼物列表
    int SEND_GIFT = 1011;//赠送礼物
    int ROOM_LIANMAI = 1012;//连麦
    int REQUEST_ROOM_CLOES = 1013;

    int MY_PHOTO_UPLOAD_CODE = 4001; //上传图片
    int MY_PHOTO_DELETE_CODE = 4002; //删除图片
    int MY_VIDEO_LOAD = 4003; //视频列表
    int MY_VIDEO_UPLOAD = 4003; //上传视频


    int MODIFY_PASSWORD = 3000;//修改密码
    int REAL_NAME_VERTIFY = MODIFY_PASSWORD + 1;//实名认证
    int RECENT_VISITORS = REAL_NAME_VERTIFY + 1;//最经访客
    int THIRD_LOGIN = RECENT_VISITORS + 1;//第三方登录

    int REQUEST_USER_INFO = 2001;//请求用户信息
    int REQUEST_ZHUBO_INFO = 2002;//请求主播信息
    int REQUEST_ROOM_VOD = 2003;//点播节目
    int REQUEST_ROOM_VODS = 2004;//点播的节目列表
    int REQUEST_ROOM_VOD_START = 2005;//主播开始表演
    int REQUEST_ROOM_VOD_END = 2006;//主播结束表演
    int REQUEST_ROOM_VOD_RESULT = 2007;//用户满意度反馈接口
    int REQUEST_ROOM_JOIN = 2008;//加入直播间
    int REQUEST_ROOM_LIVE = 2009;//主播获取全部申请列表
    int REQUEST_ROOM_EXIT = 2010;//退出直播间
    int REQUEST_ROOM_SHOW_WATCH = 2011;//观众确认观看表演
    int REQUEST_USER_SERACH = 2012;//查找（附近）用户列表
    int REQUEST_ROOM_SERACH = 2013;//查找（附近）直播列表
    int REQUEST_EXCHANGE = 2014;//兑换
    int REQUEST_REPORT = 2015;//举报
    int IMAGER_BANNER = 2016;//大厅添加图片轮播接口
    int TIMERLIVE = 2017;//主播获取全部申请列表

}
