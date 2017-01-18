package com.lvshandian.menshen;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by zhang on 2016/10/11.
 * 创建网络管理类请求，网址管理类；
 */
public class UrlBuilder {

//    public static final String serverUrl = "http://192.168.1.100:8081";// 服务器网址
    public static final String serverUrl = "http://60.205.137.27:8081/";// 外网地址
    public static final String LOGIN_URL = "/user/login";// 登录
    public static final String SMS_COOD_URL = "/sms/send/verification_code";// 获取验证码
    public static final String REGIST_URL = "/user/register";// 注册
    public static final String PASSWORD_URL = "/user/update";// 重置密码
    public static final String ISREGISTER_URL = "/user/get_phone";// 判断手机号码是否注册
    public static final String VERSION_URL = "/appVersion/get";// 版本更新
    public static final String YIJIAN_URL = "/feedback/add";// 意见反馈


    public static final String BANNER_IMAGER = "/content/get";// banner图片
    public static final String MESSAGE_WEI = "/messager/get_no";// 未读消息个数
    public static final String MESSAGE_SEETING = "/messager/put_read";// 消息设置成已读消息
    public static final String MESSAGE_URL = "/messager/find";// 消息设置成信息
    public static final String KEYWORK_URL = "/keyword/list";// 查询所有短信关键字内容
    public static final String PHONE_SELECT = "/dnseg/find";// 查询所有手机电话
    public static final String PHONE_ADD = "/dnseg/add";// 查询所有手机电话
    public static final String PHONE_DELETE = "/dnseg/user_delete";// 查询所有手机电话
    public static final String UPDETE_DELETE = "/user/update1";// 修改昵称
    public static final String ADD_KEYWORK_URL = "/keyword/add/";//添加短信关键字
    public static final String DELETE_KEYWORK_URL = "/keyword/delete/";//删除短信关键字
    public static final String SMS_UPLOAD = "/sms_upload/add";//上传短信
    public static final String SELECT_APKSIZE = "/warning/list";// 查询应用包的大小

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
