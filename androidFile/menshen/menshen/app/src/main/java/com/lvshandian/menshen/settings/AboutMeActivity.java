package com.lvshandian.menshen.settings;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.lvshandian.menshen.MainActivity;
import com.lvshandian.menshen.R;
import com.lvshandian.menshen.UrlBuilder;
import com.lvshandian.menshen.base.BaseActivity;
import com.lvshandian.menshen.httprequest.HttpDatas;
import com.lvshandian.menshen.httprequest.RequestCode;
import com.lvshandian.menshen.utils.TextUtils;
import com.lvshandian.menshen.utils.ToastUtil;
import com.lvshandian.menshen.view.ToastUtils;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhang on 2016/10/25.
 * 创建意见反馈界面
 */

public class AboutMeActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.ll_parent_view)
    AutoLinearLayout llParentView;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.rl_includ)
    AutoRelativeLayout rlInclud;
    @Bind(R.id.editText)
    EditText editText;
    @Bind(R.id.bt_tj)
    Button btTj;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle data = msg.getData();
            int tagCode = data.getInt(HttpDatas.code);


            switch (msg.what) {

                case RequestCode.YIJIAN_CODE://意见反馈请求成功
                    if (tagCode == RequestCode.REQUEST_CODE) {
                        ToastUtils.showSnackBar(snackView, data.getString(HttpDatas.info));
                        return;
                    }
                    ToastUtils.showSnackBar(snackView, "意见反馈成功");
                    finish();
                    break;

                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.settings_aboutme_activity;
    }

    @Override
    protected void initListener() {
        ivBack.setOnClickListener(this);
        btTj.setOnClickListener(this);
    }

    @Override
    protected void initialized() {
        rlInclud.setBackgroundColor(getResources().getColor(R.color.back347));
        tvTitle.setText("消息");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back://返回
                finish();
                break;
            case R.id.bt_tj://提交
                if (TextUtils.isEmpty(TextUtils.getTextContent(editText))) {
                    ToastUtils.showSnackBar(snackView, "提交内容不能为空");
                    return;
                }
                ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
                //意见反馈
                map.put("userId", getIntent().getStringExtra("userId"));
                map.put("content", TextUtils.getTextContent(editText));
                map.put("status", "1");
                httpDatas.getData("意见反馈", Request.Method.POST, UrlBuilder.YIJIAN_URL, map, mHandler, RequestCode.YIJIAN_CODE);
                break;


        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
