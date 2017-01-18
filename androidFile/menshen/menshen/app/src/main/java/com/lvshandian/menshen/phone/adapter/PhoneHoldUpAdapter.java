package com.lvshandian.menshen.phone.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.android.volley.Request;
import com.lvshandian.menshen.R;
import com.lvshandian.menshen.UrlBuilder;
import com.lvshandian.menshen.base.CommonAdapter;
import com.lvshandian.menshen.base.ViewHolder;
import com.lvshandian.menshen.bean.PhoneBean;
import com.lvshandian.menshen.httprequest.HttpDatas;
import com.lvshandian.menshen.httprequest.RequestCode;
import com.lvshandian.menshen.phone.PhoneHoldUpActivity;
import com.lvshandian.menshen.utils.LogUtils;
import com.lvshandian.menshen.utils.ToastUtil;
import com.lvshandian.menshen.utils.XUtils;
import com.lvshandian.menshen.view.DialogView;
import com.lvshandian.menshen.view.ToastUtils;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhang on 2016/10/24.
 */

public class PhoneHoldUpAdapter extends CommonAdapter<PhoneBean> {
    private View view;
    HttpDatas httpDatas;
    Context context;
    private int tag;
    private String userId;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            int tagCode = data.getInt(HttpDatas.code);
            switch (msg.what) {
                //删除关键字成功
                case RequestCode.PHONE_DELETE://删除成功
                    if (tagCode == RequestCode.REQUEST_CODE) {

                        ToastUtils.showSnackBar(view, data.getString(HttpDatas.info));
                    }
                    XUtils.deleteEntity(PhoneBean.class, "ids", mDatas.get(tag).getDnsegId() + "");

                    LogUtils.e("mdatas===" + mDatas.get(tag).toString());
                    mDatas.remove(tag);
                    notifyDataSetChanged();
                    break;


            }
        }
    };


    public PhoneHoldUpAdapter(Context context, List<PhoneBean> mDatas, int itemLayoutId, View view, String userId) {
        super(context, mDatas, itemLayoutId);
        this.view = view;
        this.context = context;
        this.userId = userId;
        httpDatas = new HttpDatas(mContext, view);

    }

    @Override
    public void convert(ViewHolder helper, final PhoneBean item, final int position) {
        helper.setText(R.id.tv_phone, item.getDnseg());
        if (item.getDnsegType() == 1) {
            helper.getView(R.id.iv_delete).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.iv_delete).setVisibility(View.VISIBLE);
        }


        helper.getView(R.id.iv_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogView(context.getApplicationContext(), view, "删除号段", "确定", "取消", "号段删除后将不被拦截", new DialogView.MyCallback() {
                    @Override
                    public void refreshActivity() {
                        tag = position;
                        int dnsegType = item.getDnsegType();
                        if (dnsegType == 1) {
                            ToastUtils.showSnackBar(view, "不能删除");
                            return;
                        }
                        //删除请求的接口
                        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
                        map.put("userId", userId);
                        map.put("ids", item.getDnsegId() + "");
                        httpDatas.getData("拦截电话号码删除", Request.Method.POST, UrlBuilder.PHONE_DELETE, map, mHandler, RequestCode.PHONE_DELETE);
                    }

                    @Override
                    public void refreshActivity(String tag) {

                    }

                    @Override
                    public void refreshActivity(String context, String minString, String maxString) {

                    }
                });
            }
        });

    }
}
