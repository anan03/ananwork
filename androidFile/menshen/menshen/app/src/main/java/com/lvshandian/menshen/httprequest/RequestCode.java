package com.lvshandian.menshen.httprequest;

/**
 * 创建请求码数据类
 */
public interface RequestCode {

    int LOGIN_TAG = 1000;     //登录
    int REQUEST_CODE = 504;     //504请求返回状态码
    int SMS_COOD = 1001;     //获取验证码
    int REGIST_CODE = 1002; //注册
    int PASS_CODE = 1003; //，密码
    int VERSION_CODE = 1004; //版本更新请求码
    int YIJIAN_CODE = 1005; //意见反馈
    int ISREGISTER_CODE_CODE = 1006; //是否注册标志获取验证码
    int ISREGISTER_UPDATE_CODE = 1007; //是否注册标志进行修改的操作；
    int BANNER_IMAGER_CODE = 1008; //是否注册标志进行修改的操作；
    int MESSAGE_WEI_CODE = 1009; //是否注册标志进行修改的操作；
    int MESSAGE_CODE = 1010; //消息请求；
    int KEYWORK_CODE = 1011; //查询所有短信关键字
    int ADD_KEYWORK_URL = 1012; //添加关键字
    int DELETE_KEYWORK_URL = 1013; //删除短信关键字
    int PHONE_SELECT = 1014; //查询手机号码字段
    int PHONE_ADD = 1015; //添加手机号码字段
    int PHONE_DELETE = 1016; //删除手机号码字段
    int UPDETE_DELETE = 1017; //修改昵称
    int SMS_UPLOAD = 1018; //上传短信
    int MESSAGE_SEETING = 1019; //上传短信 = 1018; //上传短信
    int SELECT_APKSIZE = 1020; //上传短信 = 1018; //上传短信

}
