package com.lvshandian.asktoask.module.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.lvshandian.asktoask.BaseActivity;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.http.HttpDatas;
import com.lvshandian.asktoask.common.http.RequestCode;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.MethodUtils;
import com.lvshandian.asktoask.utils.ToastUtils;

import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;

/**
 *  2016/9/30 0030.
 *
 *  我的里面的留言界面
 *
 */
public class LeaveMessageActivity extends BaseActivity {
    @Bind(R.id.iv_answer_activity_back)
    ImageView ivAnswerActivityBack;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_send)
    TextView tvSend;
    public static boolean isSuccess=false;
    @Bind(R.id.et_message)
    EditText etMessage;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);
            switch (msg.what) {
                //发送留言
                case RequestCode.LEAVE_WORDS_MESSAGE:
                    isSuccess=true;
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_leavemessage;
    }

    @Override
    protected void initListener() {
        tvSend.setOnClickListener(this);
        ivAnswerActivityBack.setOnClickListener(this);

    }

    @Override
    protected void initialized() {
        tvName.setText(getIntent().getStringExtra("name"));
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            /**
             * 发送
             */
            case R.id.tv_send:
                if (TextUtils.isEmpty(MethodUtils.getTextContent(etMessage))) {
                    ToastUtils.showSnackBar(snackView, "请输入留言");
                    return;
                }
                sendMessage();

                break;
            /**
             *
             * 返回
             */

            case R.id.iv_answer_activity_back:
                finish();
                break;

        }

    }

    private void sendMessage() {
        ConcurrentHashMap map = new ConcurrentHashMap<>();
        map.put("leaverId", Global.getUserId(getContext()));
        map.put("leavedId", getIntent().getStringExtra("attentoredId"));
        map.put("leaverData", MethodUtils.getTextContent(etMessage));
        httpDatas.getData("留言接口", Request.Method.POST, UrlBuilder.LEAVE_WORDS_CODEURL, map, mHandler, RequestCode.LEAVE_WORDS_MESSAGE);

    }


}
