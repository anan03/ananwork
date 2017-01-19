package com.lvshandian.partylive.utils;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.lvshandian.partylive.UrlBuilder;
import com.lvshandian.partylive.base.CustomStringCallBack;
import com.lvshandian.partylive.httprequest.HttpDatas;
import com.lvshandian.partylive.interf.ResultListener;
import com.squareup.okhttp.MediaType;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gjj on 2016/11/28.
 */

public class GuanZhuUtils {

    private static Map<String, String> params;

    private GuanZhuUtils() {
    }

    private static GuanZhuUtils utils;

    /**
     * 获取单例
     * @return 返回工具类对象
     */
    public static GuanZhuUtils newInstance() {
        if (utils == null) {
            synchronized (GuanZhuUtils.class) {
                if (utils == null) {
                    utils = new GuanZhuUtils();
                    params = new HashMap<>();
                }
            }
        }
        return utils;
    }

    /*1.用户关注(取消关注)
    http://miulive.cc:8080/api/v1/user/follow post提交
    发送：{ "userId":"1","followUserId":"2"}
    userId为当前用户id，followUserId为需要关注的用户id，关注之后再次关注会取消关注
    接收：{
    "code": "0",
    "message": "成功",
    "data": "true"
    }
    0是正确，其他的为错误码*/
    public void guanZhu(Context context, String userId, String followUserId, final ResultListener listener) {
        params.clear();
        params.put("followUserId", followUserId);
        params.put("userId", userId);

        JSONObject jsonObject = new JSONObject(params);
        LogUtils.e("关注JSON: " + jsonObject.toString());
        String url = UrlBuilder.serverUrl + UrlBuilder.ATTENTION_USER;
        LogUtils.e("关注URL: " + url);
        OkHttpUtils
                .postString()
                .url(url)
                .content(jsonObject.toString())
                .mediaType(MediaType.parse("application/json"))
                .build()
                .execute(new CustomStringCallBack(context, HttpDatas.KEY_CODE) {
                    @Override
                    public void onFaild() {
                        if (listener != null) {
                            listener.onFaild();
                        }
                    }

                    @Override
                    public void onSucess(String data) {
                        if (listener != null) {
                            listener.onSucess(data);
                        }
                    }
                });
    }

    /*4.1，获取用户信息
    服务器接口地址：http://miulive.cc:8080/api/v1/user/{id} get请求 其中id为参数字段，为用户id 举例http://miulive.cc:8080/api/v1/user/1
    客户端设置Content-Type: application/json;charset=UTF-8
    发送：无*/

    public void personInfo(Context context, String userId, final ResultListener listener) {
        String url = UrlBuilder.serverUrl + UrlBuilder.user + "/" + userId;
        OkHttpUtils.get().url(url).build().execute(new CustomStringCallBack(context, HttpDatas.KEY_CODE) {
            @Override
            public void onFaild() {
                if (listener != null) {
                    listener.onFaild();
                }
            }

            @Override
            public void onSucess(String data) {
                if (listener != null) {
                    listener.onSucess(data);
                }
            }
        });


    }
}
