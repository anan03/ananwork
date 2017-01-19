package com.lvshandian.partylive.moudles.mine.activity;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.UrlBuilder;
import com.lvshandian.partylive.base.BaseActivity;
import com.lvshandian.partylive.base.Constant;
import com.lvshandian.partylive.bean.AppUser;
import com.lvshandian.partylive.bean.WXPayBean;
import com.lvshandian.partylive.interf.ResultListener;
import com.lvshandian.partylive.moudles.mine.bean.ChargeMoney;
import com.lvshandian.partylive.utils.CacheUtils;
import com.lvshandian.partylive.utils.GuanZhuUtils;
import com.lvshandian.partylive.utils.JsonUtil;
import com.lvshandian.partylive.utils.LogUtils;
import com.squareup.okhttp.Request;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by gjj on 2016/11/16.
 * 充值页面
 */

public class ChargeMoneyActivity extends BaseActivity {
    @Bind(R.id.tv_acount)
    TextView tvAcount;
    @Bind(R.id.tv_yu)
    TextView tvYu;
    @Bind(R.id.tv_6)
    TextView tv6;
    @Bind(R.id.tv_18)
    TextView tv18;
    @Bind(R.id.tv_30)
    TextView tv30;
    @Bind(R.id.tv_98)
    TextView tv98;
    @Bind(R.id.tv_980)
    TextView tv980;
    @Bind(R.id.cb_ali)
    CheckBox cbAli;
    @Bind(R.id.all_ali_pay)
    AutoLinearLayout allAliPay;
    @Bind(R.id.cb_we_chat)
    CheckBox cbWeChat;
    @Bind(R.id.all_wechat_pay)
    AutoLinearLayout allWechatPay;
    @Bind(R.id.btn_buy)
    Button btnBuy;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_charge_money;
    }

    @Override
    protected void initListener() {
        tv6.setOnClickListener(this);
        tv18.setOnClickListener(this);
        tv30.setOnClickListener(this);
        tv98.setOnClickListener(this);
        tv980.setOnClickListener(this);
       /* allAliPay.setOnClickListener(this);
        allWechatPay.setOnClickListener(this);*/
        cbWeChat.setClickable(false);
        cbWeChat.setFocusable(false);
        btnBuy.setOnClickListener(this);
    }

    @Override
    protected void initialized() {
        initTitle("", "充值", null);
        getUserInfo();
    }

    private void getUserInfo() {
        final AppUser userInfo = (AppUser) CacheUtils.readObject(mContext, CacheUtils.USERINFO);
        mUser = userInfo;
        if (userInfo != null) {
            GuanZhuUtils.newInstance().personInfo(mContext, userInfo.getId(), new ResultListener() {
                @Override
                public void onSucess(String data) {
                    LogUtils.e("用户信息: "+data);
                    AppUser user = JsonUtil.json2Bean(data, AppUser.class);
                    if (user != null) {
                        mUser=user;
                        CacheUtils.saveObject(mContext, user, CacheUtils.USERINFO);
                        initInfo(user);
                    } else {
                        onFaild();
                    }
                }
                @Override
                public void onFaild() {
                    initInfo(userInfo);
                }
            });
        }
    }

    private AppUser mUser;

    /**
     * @param user
     */
    private void initInfo(AppUser user) {
        if (user != null) {
            String goldCoin = user.getGoldCoin();
            tvYu.setText(goldCoin + " 金币");
            String nickName = user.getNickName();
            tvAcount.setText(nickName);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_titlebar_left:
                defaultFinish();
                break;
            case R.id.tv_6:
                chargeMoney(6);
                break;
            case R.id.tv_18:
                chargeMoney(18);
                break;
            case R.id.tv_30:
                chargeMoney(30);
                break;
            case R.id.tv_98:
                chargeMoney(98);
                break;
            case R.id.tv_980:
                chargeMoney(980);
                break;
            case R.id.all_ali_pay:
                cbAli.setChecked(true);
                cbWeChat.setChecked(false);
                break;
            case R.id.all_wechat_pay:
              /*  cbAli.setChecked(false);
                cbWeChat.setChecked(true);*/
                break;
        }
    }

    /**
     * 充值
     *
     * @param i 充值金额
     */
    private void chargeMoney(int i) {

        String url = UrlBuilder.chargeServerUrl + UrlBuilder.wxPay;
        LogUtils.e("url: " + url);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("total_fee", String.valueOf(i));
        hashMap.put("userid", mUser.getId());
        String json = new JSONObject(hashMap).toString();
        LogUtils.e("Json:　" + json);
        OkHttpUtils.get().url(url)
                .params(hashMap)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {
                        LogUtils.e("e: " + e.toString());
                    }

                    @Override
                    public void onResponse(String response) {
                        LogUtils.e("response: " + response);
                        WXPayBean wxPayBean = JsonUtil.json2Bean(response, WXPayBean.class);
                        pay(wxPayBean);
                    }
                });

    }


    /**
     * 支付
     *
     * @param wxPayBean
     */
    private void pay(WXPayBean wxPayBean) {
        if (wxPayBean != null) {
            WXPayBean.ReturnValueBean value = wxPayBean.getReturn_value();
            if (value != null) {
                String wxAppid = Constant.WX_APPID;
                String wxPartnerid = Constant.WX_PARTNERID;
                String prepay_id = value.getPrepay_id();
                String nonce_str = value.getNonce_str();
                String timestamp = value.getTimestamp();
                String sign = value.getSign();

                IWXAPI msgApi = WXAPIFactory.createWXAPI(mContext, null);
                msgApi.registerApp(wxAppid);
                PayReq request = new PayReq();
                request.appId = wxAppid;
                request.partnerId = wxPartnerid;
                request.prepayId = prepay_id;
                request.packageValue = "Sign=WXPay";
                request.nonceStr = nonce_str;
                request.timeStamp = timestamp;
                request.sign = sign;
                msgApi.sendReq(request);
            } else {
                error();
            }
        } else {
            error();
        }
    }

    private void error() {

    }

    @Subscribe
    public void onEventMainThread(ChargeMoney chargeMoney) {
        if (chargeMoney != null) {
            int errCode = chargeMoney.getErrCode();
            if (errCode == 0) {
                //支付成功
                LogUtils.e("errCode: "+errCode);
                getUserInfo();

            } else {
                //支付失败
                error();
            }
        }
    }
}
