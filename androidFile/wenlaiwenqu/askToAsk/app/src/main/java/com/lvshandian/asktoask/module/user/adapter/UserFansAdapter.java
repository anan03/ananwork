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
import com.lvshandian.asktoask.entry.DataUserFans;
import com.lvshandian.asktoask.module.user.activity.FansAnswerDetailActivity;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.UrlBuilder;
import com.lvshandian.asktoask.utils.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 粉丝列表 on 2016/9/21.
 * 创建粉丝适配器Adapter
 */
public class UserFansAdapter extends CommonAdapter<DataUserFans.DataBean> {
    Context context;
    private List<DataUserFans.DataBean> mDatas;
    private ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();


    public UserFansAdapter(Context context, HttpDatas httpDatas, View snackView, List<DataUserFans.DataBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.context = context;
        this.mDatas = mDatas;


    }

    @Override
    public void convert(ViewHolder helper, final DataUserFans.DataBean item, int position) {
        ImageLoader.getInstance().displayImage((String) item.getUserHeadImg(), (ImageView) helper.getView(R.id.iv_answer_item_chiefly));
        helper.setText(R.id.tv_answer_chiefly_name, item.getUserRealName());
        helper.getView(R.id.ll_fases).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, FansAnswerDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("item", item);
                intent.putExtra("attentorId", item.attentoredId);
                intent.putExtra("attentoredId", "" + item.getAttentoredId());
                context.startActivity(intent);
            }
        });
        final TextView tv_attention=helper.getView(R.id.tv_fanses);
        if("y".equals(item.getExtend2())){
            item.isfocus=false;
            tv_attention.setBackgroundResource(R.drawable.answer_chiefly_unfocus);
            tv_attention.setText("已关注");
            tv_attention.setTextColor(mContext.getResources().getColor(R.color.cccccccolor));
        }else{
            item.isfocus=true;
            tv_attention.setBackgroundResource(R.drawable.answer_chiefly_focus);
            tv_attention.setText(" + 关注");
            tv_attention.setTextColor(mContext.getResources().getColor(R.color.main));
        }
       tv_attention.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //取消关注请求
               if (!item.isfocus) {
                   tv_attention.setEnabled(false);
                   item.isfocus = true;
                   RequestParams params = new RequestParams();
                   params.addBodyParameter("attentorId", Global.getUserId(context));
                   params.addBodyParameter("attentoredId",item.getAttentorId());
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
                                           tv_attention.setText("+ 关注");
                                           tv_attention.setTextColor(context.getResources().getColor(R.color.main));
                                           tv_attention.setBackgroundResource(R.drawable.answer_chiefly_focus);
//                                           ToastUtils.showSnackBar(tv_attention, "取消关注");
                                           tv_attention.setEnabled(true);
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
                   //关注
                   tv_attention.setEnabled(false);
                   item.isfocus = false;
                   RequestParams params = new RequestParams();
                   params.addBodyParameter("attentorId", Global.getUserId(context));
                   params.addBodyParameter("attentoredId", item.getAttentorId());
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
                                       if ("OK".equals(msg)) {
                                           tv_attention.setText("已关注");
                                           tv_attention.setTextColor(context.getResources().getColor(R.color.cccccccolor));
                                           tv_attention.setBackgroundResource(R.drawable.answer_chiefly_unfocus);

//                                           ToastUtils.showSnackBar(tv_attention, "关注成功");

                                           tv_attention.setEnabled(true);
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



}
