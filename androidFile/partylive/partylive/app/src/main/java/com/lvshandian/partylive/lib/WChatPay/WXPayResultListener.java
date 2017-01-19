package com.lvshandian.partylive.lib.WChatPay;

/**
 * Created by gjj on 2016/11/3.
 */

/**
 * 微信支付回调
 */
public interface WXPayResultListener {
    void onSucess();

    void onFailed();
}
