package com.lvshandian.partylive.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.lvshandian.partylive.base.Constant;
import com.lvshandian.partylive.moudles.mine.bean.ChargeMoney;
import com.lvshandian.partylive.utils.LogUtils;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        api = WXAPIFactory.createWXAPI(this, Constant.WX_APPID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {

        int code = resp.errCode;
        String errStr = resp.errStr;
        String openId = resp.openId;
        String transaction = resp.transaction;
        String toString = resp.toString();

        LogUtils.e("code: " + code);
        LogUtils.e("errStr: " + errStr);
        LogUtils.e("openId: " + openId);
        LogUtils.e("transaction: " + transaction);
        LogUtils.e("toString: " + toString);

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            LogUtils.e("resp.errCode: " + resp.errCode);
            int errCode = resp.errCode;
            if (errCode == 0) {

                LogUtils.e("微信支付成功");
            } else {
                LogUtils.e("微信支付失败");
            }
            EventBus.getDefault().post(new ChargeMoney(errCode));
            finish();
        }
    }
}