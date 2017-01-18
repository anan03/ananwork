package com.lvshandian.menshen.analysesms;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lvshandian.menshen.R;
import com.lvshandian.menshen.base.BaseActivity;
import com.lvshandian.menshen.bean.SmsInfo;
import com.lvshandian.menshen.utils.DateUtil;
import com.lvshandian.menshen.utils.LogUtils;
import com.lvshandian.menshen.utils.SmsWriteOpUtil;
import com.lvshandian.menshen.utils.Sp;
import com.lvshandian.menshen.utils.TextUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhang on 2016/11/6.
 * 创建短信详情界面
 */

public class AnayseSmsDetails extends BaseActivity {
    @Bind(R.id.data)
    TextView data;
    @Bind(R.id.tv_context)
    TextView tvContext;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;

    @Override
    protected int getLayoutId() {
        return R.layout.anayse_smsdetails;
    }

    @Override
    protected void initListener() {
        ivBack.setOnClickListener(this);
    }

    @Override
    protected void initialized() {
        SmsInfo smsInfo = (SmsInfo) getIntent().getSerializableExtra("smsinfo");
        if (smsInfo.getPerson() != null && !smsInfo.getPerson().equals("")) {//如果没有备注就显示手机号码
            tvTitle.setText(smsInfo.getAddress());
        } else {
            tvTitle.setText(smsInfo.getAddress());
        }
        tvContext.setText(smsInfo.getBody());
        data.setText(DateUtil.timesTheree(smsInfo.getDate()));

        LogUtils.e("getRead" + smsInfo.getRead());
        if (smsInfo.getRead() == 0) {
            if (!SmsWriteOpUtil.isWriteEnabled(getApplicationContext())) {
                SmsWriteOpUtil.setWriteEnabled(
                        getApplicationContext(), true);
            }
            ContentValues values = new ContentValues();
            LogUtils.e("设置为已读");
            values.put("read", "1");
            getContentResolver().update(Uri.parse("content://sms/"), values, "_id=?", new String[]{smsInfo.get_id() + ""});
            String readString = (String) Sp.getParam(mContext, "read", "");
            if (!TextUtils.isEmpty(readString)) {

                Sp.setParam(mContext, "read", readString + "&" + smsInfo.get_id());
            } else {
                Sp.setParam(mContext, "read", smsInfo.get_id() + "");
            }
            //发送消息方
            EventBus.getDefault().post("reflashread");
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            //返回
            case R.id.iv_back:
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
