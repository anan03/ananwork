package com.lvshandian.partylive.moudles.mine.activity;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.lvshandian.partylive.R;
import com.lvshandian.partylive.UrlBuilder;
import com.lvshandian.partylive.base.BaseActivity;
import com.lvshandian.partylive.bean.AppUser;
import com.lvshandian.partylive.bean.GiftBean;
import com.lvshandian.partylive.httprequest.HttpDatas;
import com.lvshandian.partylive.httprequest.RequestCode;
import com.lvshandian.partylive.interf.ResultListener;
import com.lvshandian.partylive.utils.CacheUtils;
import com.lvshandian.partylive.utils.GuanZhuUtils;
import com.lvshandian.partylive.utils.JsonUtil;
import com.lvshandian.partylive.utils.LogUtils;
import com.lvshandian.partylive.utils.ToastUtil;
import com.lvshandian.partylive.view.ToastUtils;
import com.lvshandian.partylive.widget.AvatarView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;

/**
 * Created by gjj on 2016/11/17.
 */

public class ExchangeActivity extends BaseActivity {
    @Bind(R.id.av_image)
    AvatarView avImage;
    @Bind(R.id.tv_user_name)
    TextView tvUserName;
    @Bind(R.id.tv_gold_coin)
    TextView tvGoldCoin;
    @Bind(R.id.btn_100)
    Button btn100;
    @Bind(R.id.btn_300)
    Button btn300;
    @Bind(R.id.btn_500)
    Button btn500;
    @Bind(R.id.btn_1000)
    Button btn1000;
    @Bind(R.id.btn_2000)
    Button btn2000;
    @Bind(R.id.btn_5000)
    Button btn5000;
    @Bind(R.id.textView)
    TextView textView;
    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);
            switch (msg.what) {
                //兑换
                case RequestCode.REQUEST_EXCHANGE:
                    LogUtils.i("兑换:" + json);
                    showToast("兑换成功");
                    GuanZhuUtils.newInstance().personInfo(ExchangeActivity.this, appUser.getId(), new ResultListener() {
                        @Override
                        public void onSucess(String data) {
                            AppUser user = JsonUtil.json2Bean(data, AppUser.class);
                            if (user != null) {
                                CacheUtils.saveObject(mContext, user, CacheUtils.USERINFO);
                            }
                        }

                        @Override
                        public void onFaild() {

                        }
                    });
                    break;
            }
        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.activity_exchange;
    }

    @Override
    protected void initListener() {
        btn100.setOnClickListener(this);
        btn300.setOnClickListener(this);
        btn500.setOnClickListener(this);
        btn1000.setOnClickListener(this);
        btn2000.setOnClickListener(this);
        btn5000.setOnClickListener(this);
    }

    @Override
    protected void initialized() {
        initTitle("", "兑换", null);
        AppUser userInfo = (AppUser) CacheUtils.readObject(mContext, CacheUtils.USERINFO);
        if (userInfo != null) {
            tvUserName.setText(userInfo.getNickName());
            tvGoldCoin.setText(userInfo.getReceivedGoldCoin());

            ImageLoader.getInstance().loadImage(userInfo.getPicUrl(), new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {

                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    avImage.setImageBitmap(bitmap);
                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_titlebar_left:
                defaultFinish();
                break;
            case R.id.btn_100:
                //兑换
                showDialog("100");
                break;
            case R.id.btn_300:
                showDialog("300");
                break;
            case R.id.btn_500:
                showDialog("500");
                break;
            case R.id.btn_1000:
                showDialog("1000");
                break;
            case R.id.btn_2000:
                showDialog("2000");
                break;
            case R.id.btn_5000:
                showDialog("5000");
                break;
        }
    }

    /**
     * 支付密码进入私密直播
     * 是否用金票100兑换金币
     *
     * @author sll
     * @time 2016/12/16 16:34
     */
    private void showDialog(final String goldCoin) {
        final Dialog dialog = new Dialog(this, R.style.homedialog);
        final View view = View.inflate(this, R.layout.dialog_join_secret_pwd, null);
        TextView title = (TextView) view.findViewById(R.id.dialog_prompt_title);
        title.setText("兑换");
        TextView text = (TextView) view.findViewById(R.id.dialog_prompt_text);
        text.setText("否用金票" + goldCoin + "兑换金币?");
        final EditText pwdEdit = (EditText) view.findViewById(R.id.join_secret_pwd_edit);
        pwdEdit.setVisibility(View.GONE);
        //取消
        view.findViewById(R.id.join_secret_pwd_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //确定
        view.findViewById(R.id.join_secret_pwd_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exchange(goldCoin);
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        dialog.show();
    }

    /**
     * 兑换,将金票兑换成金币
     *
     * @author sll
     * @time 2016/12/23 18:24
     */
    private void exchange(String goldCoin) {
        httpDatas.getData("兑换", UrlBuilder.exchange(appUser.getId(), goldCoin), myHandler, RequestCode.REQUEST_EXCHANGE);

    }
}
