package com.lvshandian.partylive;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.lvshandian.partylive.base.BaseActivity;
import com.lvshandian.partylive.httprequest.HttpDatas;
import com.lvshandian.partylive.httprequest.RequestCode;
import com.lvshandian.partylive.view.ToastUtils;
import com.tandong.sa.loopj.RequestParams;


/**
 * 创建首页互
 */

public class HomeActivity extends BaseActivity {

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);

            Log.e("log", json.toString());
            switch (msg.what) {
                //关注请求接收数据
                case RequestCode.LOGIN_TAG:
                    ToastUtils.showSnackBar(snackView, json.toString());
                    break;

            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initListener() {
        RequestParams params = new RequestParams();
        params.put("name", "smartandroid");
        params.put("password", "123456");
    }

    @Override
    protected void initialized() {
//        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
//        map.put("userName", "18513884422");
//        map.put("userPassword", "123456");
//        httpDatas.getData("系统登录", Request.Method.POST, UrlBuilder.LOGIN_URL, map, mHandler, RequestCode.LOGIN_TAG);
    }


    @Override
    public void onClick(View v) {



    }
}
