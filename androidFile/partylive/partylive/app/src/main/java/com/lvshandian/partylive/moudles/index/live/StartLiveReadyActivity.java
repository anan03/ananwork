package com.lvshandian.partylive.moudles.index.live;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.lvshandian.partylive.R;
import com.lvshandian.partylive.UrlBuilder;
import com.lvshandian.partylive.base.BaseActivity;
import com.lvshandian.partylive.bean.AppUser;
import com.lvshandian.partylive.bean.CreatReadyBean;
import com.lvshandian.partylive.bean.LiveBean;
import com.lvshandian.partylive.httprequest.HttpDatas;
import com.lvshandian.partylive.httprequest.RequestCode;
import com.lvshandian.partylive.utils.JsonUtil;
import com.lvshandian.partylive.utils.LogUtils;
import com.lvshandian.partylive.widget.LoadUrlImageView;
import com.tandong.sa.json.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/21.
 */

public class StartLiveReadyActivity extends BaseActivity {
    @Bind(R.id.head)
    LoadUrlImageView head;
    @Bind(R.id.btn_start)
    Button btnStart;
    private CreatReadyBean creatReadyBean;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);


            switch (msg.what) {
                //关注请求接收数据
                case RequestCode.START_LIVE:
                    LogUtils.i(json.toString());

                    creatReadyBean = JsonUtil.json2Bean(json, CreatReadyBean.class);
                    String res = json.toString();

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("START", creatReadyBean);

                    Intent intent = new Intent(StartLiveReadyActivity.this, StartLiveActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_startlive_ready;
    }

    @Override
    protected void initListener() {
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start();

            }
        });

    }

    @Override
    protected void initialized() {
        head.setImageLoadUrl(appUser.getPicUrl());

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void start() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("name", appUser.getNickName());
        map.put("city", "北京");
        map.put("userId", appUser.getId());
        map.put("privateChat", 1 + "");
        map.put("payForChat", 200 + "");
        map.put("livePicUrl", appUser.getPicUrl());
        httpDatas.getDataForJson("开启直播", Request.Method.POST, UrlBuilder.START, map, mHandler, RequestCode.START_LIVE);
    }
}
