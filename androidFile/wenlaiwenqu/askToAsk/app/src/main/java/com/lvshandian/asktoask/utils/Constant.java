package com.lvshandian.asktoask.utils;

/**
 * 常量类 on 2016/9/2.
 */
public class Constant {

    public static final String PHONENUM_REGEX = "^[1][34578][0-9]{9}$";
    public static final String EMAIL_REGEX = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

    //    修改个人信息页
    public static final String Change_personal_WhichInfo = "changewhichinfo";           //tag
    public static final String Change_personal_WhichInfoHint = "changewhichinfohint";
    public static final String Change_personal_InfoContent = "whichinfocontent";        //需要回显的tag

    public static final String IMAGER_HEAD_PATH = "IMAGER_HEAD_PATH";  //相册图片路径保存
    public static final String IMAGER_HEAD_BG = "IMAGER_HEADBG_PATH";  //相册BG

    public static final String ISLOGIN = "LONGIN";
    public static final String USERID = "USERID";
    //推送标签
    public static final String TAGTWO = "all";
    public static final String TAGONE = "android";
    //标签设置成功后保存到sp里面；
    public static final String ISTAG = "isTAG";

    //推送消息，发送广播更新站内信标志；tagIntent
    public static final String RECEIVERMESSAGE = "InstationMEssageFragment";
    //推送消息，发送广播更新站内信标志；内容标志1
    public static final String MESSAGE = "MESSAGE";
    //推送消息，发送广播更新站内信标志；内容标志2
    public static final String EXTRAS = "EXTRAS";


    //问题列表广播
    public static final String BROADCAST_ANSWER = "BROADCAST_ANSWER";


}
