package com.lvshandian.asktoask.module.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.lvshandian.asktoask.BaseActivity;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.http.HttpDatas;
import com.lvshandian.asktoask.common.http.RequestCode;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.L;
import com.lvshandian.asktoask.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;


/**
 * author: newlq on 2016/9/6 17:08.
 * email：@lvshandian.com
 * company:北京绿闪电科技有限公司
 * details：我的悬赏页面
 */
public class MineRewardActivity extends BaseActivity {


    @Bind(R.id.tv_titlebar_centertext)
    TextView tvTitlebarCentertext;
    @Bind(R.id.ll_titlebar_zuojiantou)
    LinearLayout llTitlebarZuojiantou;
    @Bind(R.id.btn_wodexuanshang_money)
    TextView btnWodexuanshangMoney;
    @Bind(R.id.btn_wodexuanshang_gotixian)
    Button btnWodexuanshangGotixian;

    private String xuanshang = "0";

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RequestCode.GETXUANSHANG_REQUESTCODE:
                    Bundle data = msg.getData();
                    String json = data.getString(HttpDatas.info);
                    L.e(json + "悬赏的金额");

                    if (json == null) {
                        ToastUtils.showSnackBar(snackView, "服务器异常");
                        return;
                    }
                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        xuanshang = jsonObject.optString("user_money", "0");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    btnWodexuanshangMoney.setText(getString(R.string.wodexuanshang_qianshu, xuanshang));
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }

        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wodexuanshang;
    }

    @Override
    protected void initListener() {
        llTitlebarZuojiantou.setOnClickListener(this);
        btnWodexuanshangGotixian.setOnClickListener(this);
    }

    @Override
    protected void initialized() {
        tvTitlebarCentertext.setText(R.string.mine_itemtext_wodexuanshang);
        getXuanShangJinE();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_titlebar_zuojiantou:
                finish();
                break;
            case R.id.btn_wodexuanshang_gotixian:
                Intent intent = new Intent(this, WithDrawActivity.class);
                intent.putExtra("xuanshangjine", xuanshang);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    /**
     * 提现按钮
     */
    private void getXuanShangJinE() {


        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("userId", Global.getUserId(mContext));
        httpDatas.getData("获取获得的悬赏金额", Request.Method.POST, UrlBuilder.WODEXUANSHANG_GETXUANSHANG_URL, map, mHandler, RequestCode.GETXUANSHANG_REQUESTCODE);
    }


}
