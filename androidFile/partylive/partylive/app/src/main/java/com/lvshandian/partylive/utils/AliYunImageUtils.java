package com.lvshandian.partylive.utils;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSFederationToken;
import com.lvshandian.partylive.UrlBuilder;
import com.lvshandian.partylive.base.CustomStringCallBack;
import com.lvshandian.partylive.httprequest.HttpDatas;
import com.lvshandian.partylive.interf.ResultListener;
import com.lvshandian.partylive.moudles.start.bean.AliYunBean;
import com.zhy.http.okhttp.OkHttpUtils;

/**
 * Created by gjj on 2016/11/22.
 */

public class AliYunImageUtils {
    private AliYunImageUtils() {
    }

    public static AliYunImageUtils utils;

    public static AliYunImageUtils newInstance() {
        if (utils == null) {
            synchronized (AliYunImageUtils.class) {
                if (utils == null) {
                    utils = new AliYunImageUtils();
                }
            }
        }
        return utils;
    }

    /**
     * 上传头像
     *
     * @param imagePath
     */
    public void uploadImage(final Context context, final String imagePath, final ResultListener listener) {
        OkHttpUtils.get().url(UrlBuilder.serverUrl + UrlBuilder.aliyun)
                .build().execute(new CustomStringCallBack(context, HttpDatas.KEY_CODE) {

            @Override
            public void onFaild() {
                LogUtils.e("data1" + "请求失败");
                if (listener != null) {
                    listener.onFaild();
                }
            }

            @Override
            public void onSucess(String data) {
                final AliYunBean aliyun = JSON.parseObject(data, AliYunBean.class);
                LogUtils.e("data2" + data);
                OSSCredentialProvider credetialProvider = new OSSFederationCredentialProvider() {
                    @Override
                    public OSSFederationToken getFederationToken() {
                        try {
                            String ak = aliyun.getAccessKeyId();
                            String sk = aliyun.getAccessKeySecret();
                            String token = aliyun.getSecurityToken();
                            String expiration = aliyun.getExpiration();
                            return new OSSFederationToken(ak, sk, token, expiration);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                };
                OSSClientUtil.init(context, credetialProvider);
                try {
                    String imageName = String.valueOf(System.currentTimeMillis()).substring(8);
                    OSSClientUtil.uploadFile(imageName + ".png", imagePath);
//                    String headUrl = "http://image.miulive.cc/" + imageName + ".png";
                    String headUrl = UrlBuilder.ALIYUN_IMG + imageName + ".png";

                    if (listener != null) {
                        listener.onSucess(headUrl);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 上传短视频
     *
     * @param imagePath
     */
    public void uploadVideo(final Context context, final String imagePath, final ResultListener listener) {
        OkHttpUtils.get().url(UrlBuilder.serverUrl + UrlBuilder.aliyun)
                .build().execute(new CustomStringCallBack(context, HttpDatas.KEY_CODE) {

            @Override
            public void onFaild() {
                if (listener != null) {
                    listener.onFaild();
                }
            }

            @Override
            public void onSucess(String data) {
                final AliYunBean aliyun = JSON.parseObject(data, AliYunBean.class);
                LogUtils.e("datadata" + data.toString());


                OSSCredentialProvider credetialProvider = new OSSFederationCredentialProvider() {
                    @Override
                    public OSSFederationToken getFederationToken() {
                        try {
                            String ak = aliyun.getAccessKeyId();
                            String sk = aliyun.getAccessKeySecret();
                            String token = aliyun.getSecurityToken();
                            String expiration = aliyun.getExpiration();
                            return new OSSFederationToken(ak, sk, token, expiration);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                };
                OSSClientUtil.init(context, credetialProvider);
                try {
                    String imageName = String.valueOf(System.currentTimeMillis()).substring(8);
                    OSSClientUtil.uploadFile(imageName + ".mp4", imagePath);
//                    String headUrl = "http://image.miulive.cc/" + imageName + ".mp4";
                    String headUrl = UrlBuilder.ALIYUN_IMG + imageName + ".mp4";
                    if (listener != null) {
                        listener.onSucess(headUrl);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
