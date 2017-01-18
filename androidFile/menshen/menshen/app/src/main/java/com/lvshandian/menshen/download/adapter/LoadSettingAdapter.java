package com.lvshandian.menshen.download.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.android.volley.Request;
import com.lvshandian.menshen.R;
import com.lvshandian.menshen.UrlBuilder;
import com.lvshandian.menshen.analysesms.SmsSettingActivity;
import com.lvshandian.menshen.base.CommonAdapter;
import com.lvshandian.menshen.base.ViewHolder;
import com.lvshandian.menshen.bean.Keyworkinfo;
import com.lvshandian.menshen.download.LoadSettingActivity;
import com.lvshandian.menshen.httprequest.HttpDatas;
import com.lvshandian.menshen.httprequest.RequestCode;
import com.lvshandian.menshen.utils.LogUtils;
import com.lvshandian.menshen.utils.TextUtils;
import com.lvshandian.menshen.utils.XUtils;
import com.lvshandian.menshen.view.DialogView;
import com.lvshandian.menshen.view.ToastUtils;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhang on 2016/10/27.
 */


public class LoadSettingAdapter extends CommonAdapter<Keyworkinfo> {
    public View llPart;
    public Context conText;
    private int tag;
    public ArrayList<Keyworkinfo> list;
    HttpDatas httpDatas;
    private int position;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            int tagCode = data.getInt(HttpDatas.code);

            switch (msg.what) {
                //删除关键字成功
                case RequestCode.DELETE_KEYWORK_URL://删除成功
                    if (tagCode == RequestCode.REQUEST_CODE) {

                        ToastUtils.showSnackBar(llPart, data.getString(HttpDatas.info));
                    }
                    //更新数据库进行删除
                    XUtils.deleteEntity(Keyworkinfo.class, "ids", list.get(tag).getKeywordId() + "");
                    //进行页面的刷新
                    list.remove(tag);
                    LogUtils.e("list" + list.toString());
                    notifyDataSetChanged();
                    break;
            }
        }
    };
    String[] str;

    public LoadSettingAdapter(View llPart, Context context, ArrayList<Keyworkinfo> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        httpDatas = new HttpDatas(mContext, llPart);
        this.conText = context;
        this.llPart = llPart;
        this.list = mDatas;
    }

    @Override
    public void convert(ViewHolder helper, final Keyworkinfo item, final int position) {
        this.position = position;
        LogUtils.e("list" + mDatas.toString());
        LogUtils.e("list.size" + mDatas.size());
//        if (item.getKeyword().trim() != null && !item.getKeyword().trim().equals("")) {
//            LogUtils.e("item" + item);
//            str = TextUtils.out(item.getKeyword());
//            helper.getView(R.id.ll_part).setVisibility(View.VISIBLE);
//            helper.setText(R.id.anay_item_tv_phone, str[0]);
//        } else {
//            helper.getView(R.id.ll_part).setVisibility(View.GONE);
//        }
        if (item.getKeyword().trim() != null && !item.getKeyword().trim().equals("")) {
            LogUtils.e("item" + item);
            helper.getView(R.id.ll_part).setVisibility(View.VISIBLE);
            helper.setText(R.id.anay_item_tv_phone, item.getKeyword());
            if (item.getUserId() == 0) {
                helper.getView(R.id.img).setVisibility(View.GONE);
            } else {
                helper.getView(R.id.img).setVisibility(View.VISIBLE);
            }

        } else {
            helper.getView(R.id.ll_part).setVisibility(View.GONE);
        }


        helper.getView(R.id.img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DialogView(conText, LoadSettingActivity.relativeLayou, "删除关键字", "确定", "取消", "关键字删除后将不被拦截", new DialogView.MyCallback() {
                    @Override
                    public void refreshActivity() {

                        tag = position;
                        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
                        map.put("ids", item.getKeywordId() + "");
                        httpDatas.getData("删除短信关键字", Request.Method.POST, UrlBuilder.DELETE_KEYWORK_URL, map, mHandler, RequestCode.DELETE_KEYWORK_URL);
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



