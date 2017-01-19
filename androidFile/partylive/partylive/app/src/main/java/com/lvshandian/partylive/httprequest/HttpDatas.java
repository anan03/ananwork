package com.lvshandian.partylive.httprequest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.devspark.appmsg.AppMsg;
import com.lvshandian.partylive.MyApplication;
import com.lvshandian.partylive.R;
import com.lvshandian.partylive.UrlBuilder;
import com.lvshandian.partylive.moudles.mine.activity.ChargeMoneyActivity;
import com.lvshandian.partylive.utils.LogUtils;
import com.lvshandian.partylive.view.LoadingDialog;
import com.lvshandian.partylive.view.ToastUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhang on 2016/10/11.
 */
public class HttpDatas {
    private View view;
    UrlBuilder urlBuilder;
    public static final int KEY_CODE = 0;//请求成功码SUCCESS_CODE的int类型
    private String SUCCESS_CODE = "0";//成功
    private String FAIL_CODE = "1";//失败
    private String SERVERS_CODE = "500";//服务器内部错误
    public static final String info = "data";
    public static final String obj = "obj";

    private int CODE_SUCCESS = 0;//成功
    private int CODE_Failure = 1;//失敗
    LoadingDialog mDialog;
    private Context context;

    private boolean isDialog = true;

    public HttpDatas(Context context, View view) {
        this.view = view;
        this.context = context;
        urlBuilder = new UrlBuilder();
        mDialog = new LoadingDialog(context);
    }


    private FastJsonRequest<SdkHttpResult> fastJsonRequest;
    private FastJsonRequest<SdkHttpResultSuccess> fastJsonRequest_success;

    /**
     * @param details     接口名
     * @param url         接口地址
     * @param handler
     * @param handlerCode
     */
    public void getData(String details, String url, final Handler handler, final int handlerCode) {
        url = urlBuilder.serverUrl + url;
        LogUtils.i(details + ":" + url);
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
        fastJsonRequest = new FastJsonRequest<SdkHttpResult>(url, SdkHttpResult.class, new Response.Listener<SdkHttpResult>() {
            @Override
            public void onResponse(SdkHttpResult response) {

                if (response.getCode().equals(SUCCESS_CODE)) {
                    Message message = new Message();
                    Bundle data = new Bundle();
                    data.putString(info, response.getData());
                    message.setData(data);
                    message.what = handlerCode;
                    handler.sendMessage(message);
                } else if (response.getCode().equals(SERVERS_CODE)) {
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
    public void getData(final String details, int method, String url, Map<String, String> map, final Handler handler, final int handlerCode) {
        url = urlBuilder.build(url, map);
        LogUtils.i(details + ":" + url);
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
        fastJsonRequest = new FastJsonRequest<SdkHttpResult>(method, url, SdkHttpResult.class, map, new Response.Listener<SdkHttpResult>() {
            @Override
            public void onResponse(SdkHttpResult response) {

                String s = response.toString();
                LogUtils.e("WXPAY： " + s);
                if (response.getCode().equals(SUCCESS_CODE)) {
                    Message message = new Message();
                    Bundle data = new Bundle();
                    LogUtils.i(details + "返回内容:" + response.toString());
                    data.putString(info, response.getData());
                    message.setData(data);
                    message.what = handlerCode;
                    handler.sendMessage(message);
                } else if (response.getCode().equals(SERVERS_CODE)) {
                    LogUtils.i("服务器内部错误:" + response.getMessage());
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
    public void getDataRequest(final String details, int method, String url, Map<String, String> map, final Handler handler, final int handlerCode) {
        url = urlBuilder.build(url, map);
        LogUtils.i(details + ":" + url);
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
        fastJsonRequest_success = new FastJsonRequest<SdkHttpResultSuccess>(method, url, SdkHttpResultSuccess.class, map, new Response.Listener<SdkHttpResultSuccess>() {
            @Override
            public void onResponse(SdkHttpResultSuccess response) {

                String s = response.toString();
                LogUtils.e("WXPAY： " + s);
                if (response.getCode() == CODE_SUCCESS) {
                    Message message = new Message();
                    Bundle data = new Bundle();
                    LogUtils.i(details + "返回内容:" + response.toString());
                    data.putSerializable(obj, response.getObj());
                    message.setData(data);
                    message.what = handlerCode;
                    handler.sendMessage(message);
                } else if (response.getCode() == CODE_Failure) {
                    LogUtils.i(details + "异常:" + response.toString());
                }
                if (mDialog.isShowing()) {
                    mDialog.dismiss();
                }
            }
        }, errorListener);
        addRequestQueueSuccess();
    }

    private void addRequestQueueSuccess() {
        if (fastJsonRequest_success != null) {
            MyApplication.requestQueueiInstance().add(fastJsonRequest_success);
        }
    }

    /**
     * 拼接json字符串作为参数上传
     * @param details     接口名
     * @param method      请求方式
     * @param url         接口地址
     * @param map         参数
     * @param handler
     * @param handlerCode
     */
    public void getDataForJson(final String details, int method, String url, Map<String, String> map, final Handler handler, final int handlerCode) {
        LogUtils.i(details + ":" + url);
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
        map.put("type", "0");
        // map.put("Charset", "UTF-8");
        //  map.put("Content-Type", "application/json");
//        map.put("Accept-Encoding", "gzip,deflate");
        JSONObject params = new JSONObject(map);
        //第二个参数我们传了user=zhangqi。则请求方法就为post
        String url1 = UrlBuilder.serverUrl + url;
        LogUtils.e("url1: " + url1);
        BaseJsonRequest objRequest = new BaseJsonRequest(method, url1, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject obj) {
                        SdkHttpResult response = JSON.parseObject(obj.toString(), SdkHttpResult.class);
                        LogUtils.i(details + ":" + response.toString());

                        if (response.getCode().equals(SUCCESS_CODE)) {
                            Message message = new Message();
                            Bundle data = new Bundle();
                            data.putString(info, response.getData());
                            message.setData(data);
                            message.what = handlerCode;
                            handler.sendMessage(message);
                        } else if (response.getCode().equals(SERVERS_CODE)) {
//                            ToastUtils.showSnackBar(view, response.getMessage());
                            AppMsg.makeText((Activity) context, response.getMessage(), new AppMsg.Style(1000, R.mipmap.toast_background)).show();

                            LogUtils.i("服务器内部错误:" + response.getMessage());
                        } else {
                            AppMsg.makeText((Activity) context, response.getMessage(), new AppMsg.Style(1000, R.mipmap.toast_background)).show();
//                            ToastUtils.showSnackBar(view, response.getMessage());
                        }
                        if (mDialog.isShowing()) {
                            mDialog.dismiss();
                        }
                    }
                }, errorListener);
        // 将这个request加入到requestQueue中，就可以执行了
        MyApplication.requestQueueiInstance().add(objRequest);
    }


    /**
     * 拼接json字符串作为参数上传  noloading
     *
     * @param details     接口名
     * @param method      请求方式
     * @param url         接口地址
     * @param map         参数
     * @param handler
     * @param handlerCode
     */
    public void getDataForJsoNoloading(final String details, int method, String url, Map<String, String> map, final Handler handler, final int handlerCode) {
        LogUtils.i(details + ":" + url);

        map.put("type", "0");
        // map.put("Charset", "UTF-8");
        //  map.put("Content-Type", "application/json");
//        map.put("Accept-Encoding", "gzip,deflate");
        JSONObject params = new JSONObject(map);
        LogUtils.i("JSONObject:" + params.toString());
        //第二个参数我们传了user=zhangqi。则请求方法就为post
        BaseJsonRequest objRequest = new BaseJsonRequest(method, UrlBuilder.serverUrl + url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject obj) {
                        SdkHttpResult response = JSON.parseObject(obj.toString(), SdkHttpResult.class);
                        LogUtils.i(details + ":" + response.toString());

                        if (response.getCode().equals(SUCCESS_CODE)) {
                            Message message = new Message();
                            Bundle data = new Bundle();
                            data.putString(info, response.getData());
                            message.setData(data);
                            message.what = handlerCode;
                            handler.sendMessage(message);
                        } else if (response.getCode().equals(SERVERS_CODE)) {
                            ToastUtils.showSnackBar(view, response.getMessage());
                            LogUtils.i("服务器内部错误:" + response.getMessage());
                        } else if (response.getMessage() != null && response.getMessage().equals("账户余额不足")) {
                            //账户余额不足,跳转到充值页面
                            context.startActivity(new Intent(context, ChargeMoneyActivity.class));
                        } else {
                        }
                        if (mDialog.isShowing()) {
                            mDialog.dismiss();
                        }
                    }
                }, errorListener);
        // 将这个request加入到requestQueue中，就可以执行了
        MyApplication.requestQueueiInstance().add(objRequest);
    }


    /**
     * @param details     接口名
     * @param method      请求方式
     * @param url         接口地址
     * @param map         参数
     * @param handler
     * @param handlerCode
     */
    public void getDatanoloading(final String details, int method, String url, Map<String, String> map, final Handler handler, final int handlerCode) {
        url = urlBuilder.build(url, map);
        LogUtils.i(details + ":" + url);

        fastJsonRequest = new FastJsonRequest<SdkHttpResult>(method, url, SdkHttpResult.class, map, new Response.Listener<SdkHttpResult>() {
            @Override
            public void onResponse(SdkHttpResult response) {

                if (response.getCode().equals(SUCCESS_CODE)) {
                    Message message = new Message();
                    Bundle data = new Bundle();
                    LogUtils.i(details + "返回内容:" + response.toString());
                    data.putString(info, response.getData());
                    message.setData(data);
                    message.what = handlerCode;
                    handler.sendMessage(message);
                } else if (response.getCode().equals(SERVERS_CODE)) {
                    ToastUtils.showSnackBar(view, "服务器内部错误");
                    LogUtils.i("服务器内部错误:" + response.getMessage());
                }

            }
        }, errorListener);
        addRequestQueue();
    }


    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Charset", "UTF-8");
        headers.put("Content-Type", "application/x-javascript");
        headers.put("Accept-Encoding", "gzip,deflate");
        return headers;
    }

    public Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            LogUtils.i("返回内容:" + "请求失败");
            LogUtils.i("error:" + error.toString());
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
        LogUtils.e(details + ":" + url);
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
        fastJsonRequest = new FastJsonRequest<SdkHttpResult>(url, SdkHttpResult.class, new Response.Listener<SdkHttpResult>() {
            @Override
            public void onResponse(SdkHttpResult response) {
                if (response.getCode().equals(SUCCESS_CODE)) {
                    Message message = new Message();
                    Bundle data = new Bundle();
                    data.putString(info, response.getData());
                    message.setData(data);
                    message.what = handlerCode;
                    handler.sendMessage(message);
                } else if (response.getCode().equals(SERVERS_CODE)) {
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


    public void getDataDialog(String details, final boolean isDialog, String url, final Handler handler, final int handlerCode) {
        url = urlBuilder.serverUrl + url;
        LogUtils.i(details + ":" + url);
        this.isDialog = isDialog;
        if (isDialog) {
            if (!mDialog.isShowing()) {
                mDialog.show();
            }
        }
        fastJsonRequest = new FastJsonRequest<SdkHttpResult>(url, SdkHttpResult.class, new Response.Listener<SdkHttpResult>() {
            @Override
            public void onResponse(SdkHttpResult response) {
                if (response.getCode().equals(SUCCESS_CODE)) {
                    Message message = new Message();
                    Bundle data = new Bundle();
                    data.putString(info, response.getData());
                    message.setData(data);
                    message.what = handlerCode;
                    handler.sendMessage(message);
                } else if (response.getCode().equals(SERVERS_CODE)) {
                    ToastUtils.showSnackBar(view, "服务器内部错误");
                }

                if (isDialog) {
                    if (mDialog.isShowing()) {
                        mDialog.dismiss();
                    }
                }

            }
        }, errorListener);
        addRequestQueue();
    }
}

