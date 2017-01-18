package com.lvshandian.menshen.push;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.lvshandian.menshen.R;
import com.lvshandian.menshen.UrlBuilder;
import com.lvshandian.menshen.base.BaseActivity;
import com.lvshandian.menshen.bean.Message;
import com.lvshandian.menshen.httprequest.HttpDatas;
import com.lvshandian.menshen.httprequest.RequestCode;
import com.lvshandian.menshen.utils.DateUtil;
import com.lvshandian.menshen.utils.LogUtils;
import com.lvshandian.menshen.view.ToastUtils;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhang on 2016/11/9.
 * 创建消息详情界面
 */

public class MessageDetails extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.ll_parent_view)
    AutoLinearLayout llParentView;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.rl_includ)
    AutoRelativeLayout rlInclud;
    @Bind(R.id.tv_date)
    TextView tvDate;
    @Bind(R.id.ll_item)
    AutoLinearLayout llItem;
    @Bind(R.id.tv_context)
    TextView tvContext;
    @Bind(R.id.tv_title_message)
    TextView tvTitleMessage;

    @Override
    protected int getLayoutId() {
        return R.layout.push_activit_messagedetails;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            int tagCode = data.getInt(HttpDatas.code);
            switch (msg.what) {
                case RequestCode.MESSAGE_SEETING://消息设置成已读消息
                    if (tagCode == RequestCode.REQUEST_CODE) {
                        ToastUtils.showSnackBar(snackView, data.getString(HttpDatas.info));
                        return;
                    }
                    //设置成已读信息
                    LogUtils.e("设置成已读信息");
                    EventBus.getDefault().post("reflash");
                    break;


            }
        }
    };

    String userid = "";

    @Override
    protected void initListener() {
        ivBack.setOnClickListener(this);
    }

    private Message message;

    @Override
    protected void initialized() {
        message = (Message) getIntent().getSerializableExtra("msg");
        LogUtils.e("MessageDeteils--message" + message.toString());
        userid = getIntent().getStringExtra("userId");
        LogUtils.e("userid" + userid);
        rlInclud.setBackgroundColor(getResources().getColor(R.color.back347));
        tvTitle.setText("通知消息");
        tvTitleMessage.setText(message.getTitle());
        tvContext.setText(message.getContent());
        tvDate.setText(DateUtil.timesFor(message.getCreatedDate()));
        requstMessage();
    }

    private void requstMessage() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("userId", userid);
        map.put("systemMessagesId", message.getSystemMessagesId() + "");
        //进行消息界面的数据请求
        httpDatas.getData("消息界面", Request.Method.POST, UrlBuilder.MESSAGE_SEETING, map, mHandler, RequestCode.MESSAGE_SEETING);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back://返回
                finish();
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
