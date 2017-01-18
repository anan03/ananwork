package com.lvshandian.menshen.httprequest;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lvshandian.menshen.MyApplication;
import com.lvshandian.menshen.R;
import com.lvshandian.menshen.UrlBuilder;
import com.lvshandian.menshen.utils.TextUtils;
import com.lvshandian.menshen.view.LoadingDialog;
import com.lvshandian.menshen.view.ToastUtils;
import com.tandong.sa.zUImageLoader.utils.L;

import java.util.Map;

/**
 * Created by zhang on 2016/10/11.
 */
public class HttpDatas {
    private View view;
    UrlBuilder urlBuilder;
    private int SUCCESS_CODE = 200;//成功
    private int SERVERS_CODE = 500;//服务器内部错误
    private int SERVERS_CODE504 = 504;//请求成功提取错误信息
    public static final String info = "data";
    public static final String code = "code";
    LoadingDialog mDialog;

    public HttpDatas(Context context, View view) {
        this.view = view;
        urlBuilder = new UrlBuilder();
        mDialog = new LoadingDialog(context);
    }

    private FastJsonRequest<SdkHttpResult> fastJsonRequest;

    /**
     * @param details     接口名
     * @param url         接口地址
     * @param handler
     * @param handlerCode
     */
    public void getData(String details, String url, final Handler handler, final int handlerCode) {
        url = urlBuilder.serverUrl + url;
        L.i(details + ":" + url);
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
        fastJsonRequest = new FastJsonRequest<SdkHttpResult>(url, SdkHttpResult.class, new Response.Listener<SdkHttpResult>() {
            @Override
            public void onResponse(SdkHttpResult response) {
                if (response.getStatus() == SUCCESS_CODE) {
                    Message message = new Message();
                    Bundle data = new Bundle();
                    data.putString(info, response.getData());
                    message.setData(data);
                    message.what = handlerCode;
                    handler.sendMessage(message);
                } else if (response.getStatus() == SERVERS_CODE) {
                    ToastUtils.showSnackBar(view, "服务器内部错误");
                }
                if (mDialog.isShowing()) {
                    mDialog.dismiss();
                }
            }
        }, errorListener);
        addRequestQueue();
    }

    /**
     * @param details     接口名
     * @param method      请求方式
     * @param url         接口地址
     * @param map         参数
     * @param handler
     * @param handlerCode
     */
    public void getData(String details, int method, String url, Map<String, String> map, final Handler handler, final int handlerCode) {
        url = urlBuilder.build(url, map);
        L.i(details + ":" + url);
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
        fastJsonRequest = new FastJsonRequest<SdkHttpResult>(method, url, SdkHttpResult.class, map, new Response.Listener<SdkHttpResult>() {
            @Override
            public void onResponse(SdkHttpResult response) {

                if (response.getStatus() == SUCCESS_CODE) {//请求成功有数据
                    Message message = new Message();
                    Bundle data = new Bundle();
                    data.putString(info, response.getData());
                    data.putInt(code, response.getStatus());
                    message.setData(data);
                    message.what = handlerCode;
                    L.i("200data" + response.getData());//打印请求成功的数据
                    L.i("200sdk" + response.toString());//打印请求成功的数据
                    handler.sendMessage(message);
                } else if (response.getStatus() == SERVERS_CODE504) {//请求成功无数据
                    Message message = new Message();
                    Bundle data = new Bundle();
                    data.putString(info, response.getMsg());
                    data.putInt(code, response.getStatus());
                    message.setData(data);
                    message.what = handlerCode;
                    L.i("504data" + response.getData());//打印请求成功的数据
                    handler.sendMessage(message);
                } else if (response.getStatus() == SERVERS_CODE) {//服务器内部错误
                    ToastUtils.showSnackBar(view, "服务器内部错误");
                    L.i("500状态：=" + response.getStatus() + "服务器内部错误");
                } else {//其他错误

                    if (response.getStatus() == 401) {
                        ToastUtils.showSnackBar(view, "参数不能为空");
                        L.i("状态：=" + response.getStatus() + "参数不能为空");
                    } else if (response.getStatus() == 400) {
                        ToastUtils.showSnackBar(view, "类型转换异常");
                        L.i("状态：=" + response.getStatus() + "类型转换异常");
                    } else {
                        ToastUtils.showSnackBar(view, "状态：=" + response.getStatus() + "消息msg=" + response.getMsg());
                        L.i("状态：=" + response.getStatus() + "消息msg=" + response.getMsg());
                    }
                }
                if (mDialog.isShowing()) {
                    mDialog.dismiss();
                }
            }
        }, errorListener);
        addRequestQueue();
    }

    public Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

            ToastUtils.showSnackBar(view, R.string.check_network);

            if (mDialog.isShowing()) {
                mDialog.dismiss();
            }
        }
    };

    private void addRequestQueue() {
        if (fastJsonRequest != null) {
            MyApplication.requestQueueiInstance().add(fastJsonRequest);
        }
    }

    //多加一个tag参数，用于指定取消
    public void getData(String details, String url, final Handler handler, final int handlerCode, String tag) {
        L.i(details + ":" + url);
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
        fastJsonRequest = new FastJsonRequest<SdkHttpResult>(url, SdkHttpResult.class, new Response.Listener<SdkHttpResult>() {
            @Override
            public void onResponse(SdkHttpResult response) {
                if (response.getStatus() == SUCCESS_CODE) {
                    Message message = new Message();
                    Bundle data = new Bundle();
                    data.putString(info, response.getData());
                    message.setData(data);
                    message.what = handlerCode;
                    handler.sendMessage(message);
                } else if (response.getStatus() == SERVERS_CODE) {
                    ToastUtils.showSnackBar(view, "服务器内部错误");
                }
                if (mDialog.isShowing()) {
                    mDialog.dismiss();
                }
            }
        }, errorListener);
        addRequestQueue(tag);
    }

    private void addRequestQueue(String tag) {
        if (fastJsonRequest != null) {
            fastJsonRequest.setTag(tag);
            MyApplication.requestQueueiInstance().add(fastJsonRequest);
        }
    }

    public void cancelByTag(String tag) {
        MyApplication.requestQueueiInstance().cancelAll(tag);
    }
}

