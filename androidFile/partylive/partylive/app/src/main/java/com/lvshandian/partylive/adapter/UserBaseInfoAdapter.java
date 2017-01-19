package com.lvshandian.partylive.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lvshandian.partylive.MyApplication;
import com.lvshandian.partylive.R;
import com.lvshandian.partylive.UrlBuilder;
import com.lvshandian.partylive.bean.UserBean;
import com.lvshandian.partylive.httprequest.BaseJsonRequest;
import com.lvshandian.partylive.httprequest.HttpDatas;
import com.lvshandian.partylive.httprequest.RequestCode;
import com.lvshandian.partylive.httprequest.SdkHttpResult;
import com.lvshandian.partylive.utils.GrademipmapUtils;
import com.lvshandian.partylive.utils.JsonUtil;
import com.lvshandian.partylive.utils.LogUtils;
import com.lvshandian.partylive.view.ToastUtils;
import com.lvshandian.partylive.widget.CircleImageView;







import com.netease.nim.uikit.common.util.C;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;
import org.kymjs.kjframe.Core;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


/**
 *
 */
public class UserBaseInfoAdapter extends BaseAdapter {
    private List<UserBean> users;
    private Context context;
    private String appuserid;


    public UserBaseInfoAdapter(List<UserBean> users, Context context, String appuserid) {
        this.users = users;
        this.context = context;
        this.appuserid = appuserid;

    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = View.inflate(context, R.layout.item_attention_fans,null);


            viewHolder = new ViewHolder();
            viewHolder.mUHead = (CircleImageView) convertView.findViewById(R.id.cv_userHead);
            viewHolder.mUSex  = (ImageView) convertView.findViewById(R.id.tv_item_usex);
            viewHolder.mULevel  = (ImageView) convertView.findViewById(R.id.tv_item_ulevel);
            viewHolder.mUNice = (TextView) convertView.findViewById(R.id.tv_item_uname);
            viewHolder.mUSign = (TextView) convertView.findViewById(R.id.tv_item_usign);
            viewHolder.mIsFollow = (ImageView) convertView.findViewById(R.id.iv_item_attention);
            convertView.setTag(viewHolder);
        }


        viewHolder = (ViewHolder) convertView.getTag();
        final UserBean u = users.get(position);
        Core.getKJBitmap().display(viewHolder.mUHead, u.getPicUrl());

        viewHolder.mUSex.setImageResource(Integer.parseInt(u.getGender()) == 1 ? R.mipmap.nan : R.mipmap.nv);
        viewHolder.mIsFollow.setImageResource(Integer.parseInt(u.getFollow())== 1 ? R.mipmap.me_following:R.mipmap.me_follow);
        viewHolder.mULevel.setImageResource(GrademipmapUtils.LevelImg[Integer.parseInt(u.getLevel()) -1]);
        viewHolder.mUNice.setText(u.getNickName());
        viewHolder.mUSign.setText(u.getSignature());
        viewHolder.mIsFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                attention(u);

//                ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
//                    map.put("followUserId",u.getId());
//                    map.put("userId", appuserid);
//
//
//                    httpDatas.getDataForJson("关注用去", Request.Method.POST, UrlBuilder.ATTENTION_USER, map, mHandler, RequestCode.ATTENTION_USER);


                if (Integer.parseInt(u.getFollow()) == 1) {//1 已经关注 0未关注
                    u.setFollow("0");
                    ((ImageView)v.findViewById(R.id.iv_item_attention)).setImageResource(R.mipmap.me_follow);
                } else {
                    u.setFollow("1");

                    ((ImageView)v.findViewById(R.id.iv_item_attention)).setImageResource(R.mipmap.me_following);
                }



//                OkHttpUtils.post()
//                        .url(UrlBuilder.serverUrl+UrlBuilder.ATTENTION_USER)
//                        .addParams("userId",appuserid)
//                        .addParams("followUserId",u.getId())
//
//                        .build().execute(new StringCallback() {
//                    @Override
//                    public void onError(Request request, Exception e) {
//                        LogUtils.i(request.toString()+e);
//
//                    }
//
//                    @Override
//                    public void onResponse(String response) {
//                        LogUtils.i(response.toString());
//
//
//                        if (Integer.parseInt(u.getFollow()) == 1) {//1 已经关注 0未关注
//                            u.setFollow("0");
//                            ((ImageView)v.findViewById(R.id.iv_item_attention)).setImageResource(R.mipmap.me_follow);
//                        } else {
//                            u.setFollow("1");
//
//                            ((ImageView)v.findViewById(R.id.iv_item_attention)).setImageResource(R.mipmap.me_following);
//                        }
//
//                    }
//                });




            }

        });
        return convertView;
    }
    private class ViewHolder{
        public CircleImageView mUHead;
        public ImageView mUSex,mULevel,mIsFollow;
        public TextView mUNice,mUSign;
    }


    private void attention(final UserBean u){


        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("followUserId",u.getId());
        map.put("userId", appuserid);
        map.put("type", "0");
            // map.put("Charset", "UTF-8");
            //  map.put("Content-Type", "application/json");
//        map.put("Accept-Encoding", "gzip,deflate");
            JSONObject params = new JSONObject(map);
            LogUtils.i("JSONObject:" + params.toString());
            //第二个参数我们传了user=zhangqi。则请求方法就为post
            BaseJsonRequest objRequest = new BaseJsonRequest(Request.Method.POST, UrlBuilder.serverUrl + UrlBuilder.ATTENTION_USER, params,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject obj) {
                            SdkHttpResult response = JSON.parseObject(obj.toString(), SdkHttpResult.class);
                            LogUtils.i(response.toString());



                        }
                    }, errorListener);
            // 将这个request加入到requestQueue中，就可以执行了
            MyApplication.requestQueueiInstance().add(objRequest);




    }

    public Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {


        }
    };
}
