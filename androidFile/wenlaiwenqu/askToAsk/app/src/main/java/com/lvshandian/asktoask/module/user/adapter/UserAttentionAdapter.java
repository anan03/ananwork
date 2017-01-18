package com.lvshandian.asktoask.module.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.adapter.CommonAdapter;
import com.lvshandian.asktoask.common.adapter.ViewHolder;
import com.lvshandian.asktoask.common.http.HttpDatas;
import com.lvshandian.asktoask.common.http.RequestCode;
import com.lvshandian.asktoask.entry.DataUserAttention;
import com.lvshandian.asktoask.module.user.activity.AttentionDetailActivity;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.UrlBuilder;
import com.lvshandian.asktoask.utils.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 创建关注适配器
 */
public class UserAttentionAdapter extends CommonAdapter<DataUserAttention.DataBean> {
    Context context;
    public UserAttentionAdapter(Context context, HttpDatas httpDatas, View snackView, List<DataUserAttention.DataBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public void convert(final ViewHolder helper, final DataUserAttention.DataBean item, int position) {

        ImageLoader.getInstance().displayImage(item.getUserHeadImg(), (ImageView) helper.getView(R.id.iv_answer_item_chiefly));
        helper.setText(R.id.tv_answer_chiefly_name, item.getUserRealName());
        helper.getView(R.id.ll_attention).setOnClickListener(new MyonclickListener(item, helper));
        final TextView tvFocus=helper.getView(R.id.tv_background);
        item.isfocus=false;

        /**
         *创建点击关注监听
         *
         */
        tvFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item.isfocus) {
                    tvFocus.setEnabled(false);
                    item.isfocus = false;
                    RequestParams params = new RequestParams();
                    params.addBodyParameter("attentorId", Global.getUserId(context));
                    params.addBodyParameter("attentoredId", item.getAttentoredId());
                    HttpUtils http = new HttpUtils();
                    http.send(HttpRequest.HttpMethod.POST,
                            UrlBuilder.serverUrl + UrlBuilder.FOCUS_ANSWER_URL,
                            params,
                            new RequestCallBack<String>() {

                                @Override
                                public void onStart() {

                                }

                                @Override
                                public void onLoading(long total, long current, boolean isUploading) {
                                }

                                @Override
                                public void onSuccess(ResponseInfo<String> responseInfo) {
                                    String str = responseInfo.result;
                                    try {
                                        JSONObject object = new JSONObject(str);
                                        String msg = object.getString("msg");
                                        Log.d("aaa", msg);

                                        if ("OK".equals(msg)) {
                                            tvFocus.setText("已关注");
                                            tvFocus.setTextColor(context.getResources().getColor(R.color.cccccccolor));
                                            tvFocus.setBackgroundResource(R.drawable.answer_chiefly_unfocus);

                                            ToastUtils.showSnackBar(tvFocus, "关注成功");

                                            tvFocus.setEnabled(true);
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onFailure(HttpException error, String msg) {


                                }
                            });

                } else {
                    item.isfocus = true;
                    tvFocus.setEnabled(false);
                    RequestParams params = new RequestParams();
                    params.addBodyParameter("attentorId", Global.getUserId(context));
                    params.addBodyParameter("attentoredId",item.getAttentoredId());
                    HttpUtils http = new HttpUtils();
                    http.send(HttpRequest.HttpMethod.POST,
                            UrlBuilder.serverUrl + UrlBuilder.CANCLE_FOCUS_ANSWER_URL,
                            params,
                            new RequestCallBack<String>() {

                                @Override
                                public void onStart() {

                                }

                                @Override
                                public void onLoading(long total, long current, boolean isUploading) {
                                }

                                @Override
                                public void onSuccess(ResponseInfo<String> responseInfo) {
                                    String str = responseInfo.result;
                                    try {
                                        JSONObject object = new JSONObject(str);
                                        String msg = object.getString("msg");
                                        if ("OK".equals(msg)) {
                                            tvFocus.setText("+ 关注");
                                            tvFocus.setTextColor(context.getResources().getColor(R.color.main));
                                            tvFocus.setBackgroundResource(R.drawable.answer_chiefly_focus);
                                            ToastUtils.showSnackBar(tvFocus, "取消关注");
                                            tvFocus.setEnabled(true);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onFailure(HttpException error, String msg) {


                                }
                            });

                }



            }
        });

    }

    class MyonclickListener implements View.OnClickListener {
        private DataUserAttention.DataBean item;
        MyonclickListener(DataUserAttention.DataBean item, ViewHolder helper) {

            this.item = item;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                /**
                 * 跳转到关注详情
                 */
                case R.id.ll_attention:

                    Intent intent = new Intent(context, AttentionDetailActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("item", item);
                    intent.putExtra("attentorId", "" + item.getAttentorId());
                    intent.putExtra("attentoredId", item.getAttentoredId());
                    context.startActivity(intent);
                    break;
            }
        }
    }


}
