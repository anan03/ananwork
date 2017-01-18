package com.lvshandian.asktoask.common.http;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lvshandian.asktoask.App;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.utils.L;
import com.lvshandian.asktoask.utils.UrlBuilder;
import com.lvshandian.asktoask.view.LoadingDialog;
import com.lvshandian.asktoask.utils.ToastUtils;

import java.util.Map;

/**
 * Created by ldb on 2016/7/8.获取数据
 */
public class HttpDatas {
    private View view;
    UrlBuilder urlBuilder;

    private int SUCCESS_CODE = 200;//成功
    private int SERVERS_CODE = 500;//服务器内部错误
    public static final String info = "data";
    public static final String inforesponse = "response";


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
                    data.putSerializable(inforesponse, response);
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
    public void getData(String details, int method, String url, Map<String, String> map, final Handler handler, final int handlerCode,boolean show) {
        url = urlBuilder.build(url, map);
        L.i(details + ":" + url);
        if (!mDialog.isShowing()&&show) {
            mDialog.show();
        }
        fastJsonRequest = new FastJsonRequest<SdkHttpResult>(method, url, SdkHttpResult.class, map, new Response.Listener<SdkHttpResult>() {
            @Override
            public void onResponse(SdkHttpResult response) {

                if (response.getStatus() == SUCCESS_CODE) {
                    Message message = new Message();
                    Bundle data = new Bundle();
                    data.putString(info, response.getData());
                    data.putSerializable(inforesponse, response);
                    message.setData(data);
                    message.what = handlerCode;
                    handler.sendMessage(message);
                } else if (response.getStatus() == SERVERS_CODE) {
                    ToastUtils.showSnackBar(view, response.getMsg());
                    L.i("服务器内部错误:" + response.getMsg());
                }
                if (mDialog.isShowing()) {
                    mDialog.dismiss();
                }
            }
        }, errorListener);
        addRequestQueue();
    }


    public void getData(String details, int method, String url, Map<String, String> map, final Handler handler, final int handlerCode) {
        url = urlBuilder.build(url, map);
        L.i(details + ":" + url);
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
        fastJsonRequest = new FastJsonRequest<SdkHttpResult>(method, url, SdkHttpResult.class, map, new Response.Listener<SdkHttpResult>() {
            @Override
            public void onResponse(SdkHttpResult response) {

                if (response.getStatus() == SUCCESS_CODE) {
                    Message message = new Message();
                    Bundle data = new Bundle();
                    data.putString(info, response.getData());
                    data.putSerializable(inforesponse, response);
                    message.setData(data);
                    message.what = handlerCode;
                    handler.sendMessage(message);
                } else if (response.getStatus() == SERVERS_CODE) {
                    ToastUtils.showSnackBar(view, response.getMsg());
                    L.i("服务器内部错误:" + response.getMsg());
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
            App.requestQueueiInstance().add(fastJsonRequest);
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
                    data.putSerializable(inforesponse, response);
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
            App.requestQueueiInstance().add(fastJsonRequest);
        }
    }

    public void cancelByTag(String tag) {
        App.requestQueueiInstance().cancelAll(tag);
    }
}
