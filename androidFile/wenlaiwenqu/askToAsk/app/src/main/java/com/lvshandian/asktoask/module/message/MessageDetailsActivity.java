package com.lvshandian.asktoask.module.message;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.lvshandian.asktoask.BaseActivity;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.http.HttpDatas;
import com.lvshandian.asktoask.common.http.RequestCode;
import com.lvshandian.asktoask.entry.DataMessageLeave;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.TextUtils;
import com.lvshandian.asktoask.utils.ToastUtils;

import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;

/**
 * 消息模块中我的留言 on 2016/9/22.
 * 创建消息留言
 */
public class MessageDetailsActivity extends BaseActivity {
    @Bind(R.id.v_title)
    View vTitle;
    @Bind(R.id.iv_back_approve_address)
    ImageView ivBackApproveAddress;
    @Bind(R.id.leave_name)
    TextView leaveName;
    @Bind(R.id.tv_leaveed_name_men)
    TextView tvLeaveedNameMen;
    @Bind(R.id.rl_title)
    RelativeLayout rlTitle;
    @Bind(R.id.ll_leavemessage)
    RelativeLayout llLeavemessage;
    DataMessageLeave.DataBean2.DataBean item;//传过来的留言人的bean
    @Bind(R.id.et_leave_data)
    EditText etLeaveData;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_eavemessage_layout;
    }

    @Override
    protected void initListener() {

        ivBackApproveAddress.setOnClickListener(this);
        tvLeaveedNameMen.setOnClickListener(this);


    }

    public static boolean isleaveSuccess=false;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);
            switch (msg.what) {
                //消息模块留言
                case RequestCode.MESSAGE_LEAVE_ANSWER:
                    isleaveSuccess=true;
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void initialized() {
        item = (DataMessageLeave.DataBean2.DataBean) getIntent().getSerializableExtra("item");

        leaveName.setText(item.getUserRealName());//被留言人的名字


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_back_approve_address:
                finish();
                break;
            case R.id.tv_leaveed_name_men://留言接口
                if(TextUtils.isEmpty(etLeaveData.getText().toString())){
                    ToastUtils.showSnackBar(snackView,"留言不能为空");
                }else{
                    requestMessage();//发送留言
                }

                break;
        }
    }

    /**
     * 留言
     */

    private void requestMessage() {
        ConcurrentHashMap map = new ConcurrentHashMap<>();
        map.clear();
        map.put("leaverId", Global.getUserId(getContext()));
        map.put("leavedId",item.getUserId());
        map.put("leaverData", etLeaveData.getText().toString());
        httpDatas.getData("消息模块给别人留言", Request.Method.POST, com.lvshandian.asktoask.utils.UrlBuilder.MESSAGER_LEAVE_ANSWER, map, mHandler, RequestCode.MESSAGE_LEAVE_ANSWER);
    }



}
