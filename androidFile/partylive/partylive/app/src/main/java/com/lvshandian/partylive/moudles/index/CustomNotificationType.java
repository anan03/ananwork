package com.lvshandian.partylive.moudles.index;

/**
 * Created by sll on 2016/12/7.
 */

/**
 * 关于点播节目的自定义通知类型
 *
 * @author sll
 * @time 2016/12/7 11:06
 */
public class CustomNotificationType {
    public static String IM_P2P_TYPE_SUBLIVE_ACK = "0";//客户视频连线，私聊回复
    public static String IM_P2P_TYPE_SUBLIVE_PRIVATE = "1";//创建客户视频连线，私聊
    public static String IM_P2P_TYPE_SUBLIVE_PUBLIC = "2";//创建客户视频连线，公聊
    public static String IM_P2P_TYPE_ORDERSHOW = "3";//观众发送客户点播
    public static String IM_P2P_TYPE_ORDERSHOW_ACK = "4";//主播反馈客户点播
    public static String IM_P2P_TYPE_ORDERSHOW_END = "6";//主播完成观众点播的表演
}
