package com.lvshandian.menshen.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import com.lvshandian.menshen.bean.User;
import com.tandong.sa.loopj.AsyncHttpClient;
import com.tandong.sa.loopj.AsyncHttpResponseHandler;
import com.tandong.sa.loopj.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class UpdateImagerUtils {

    /**
     * @param path 要上传的文件路径
     * @param url  服务端接收URL
     * @throws Exception
     */
    public static void uploadFile(Context context, String path, String url, final Handler mHandler) throws Exception {
        File file = new File(path);
        LogUtils.e("url:" + url);
        if (file.exists() && file.length() > 0) {
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("file", file);
            User user = (User) XUtils.findAll(User.class).get(0);
            LogUtils.e("user" + user.getPhone());
            params.put("phone", user.getPhone());
            // 上传文件
            client.post(url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers,
                                      byte[] responseBody) {
                    // 上传成功后要做的工作
                    try {
                        String isoString = new String(responseBody);
                        JSONObject jsonObject = new JSONObject(isoString);
                        LogUtils.e("上传成功---" + isoString);
                        int status = jsonObject.optInt("status");
                        if (status == 200) {
                            Message msg = new Message();

                            msg.what = 200;
                            msg.obj = jsonObject.optString("data");
                            mHandler.sendMessage(msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(int statusCode, Header[] headers,
                                      byte[] responseBody, Throwable error) {
                    // 上传失败后要做到工作
                    String isoString = null;
                    isoString = new String(responseBody);
                    LogUtils.e("上传失败---" + isoString);
                    Message msg = new Message();
                    msg.what = 400;
                    mHandler.sendMessage(msg);

                }

                @Override
                public void onProgress(int bytesWritten, int totalSize) {
                    // TODO Auto-generated method stub
                    super.onProgress(bytesWritten, totalSize);
                    int count = (int) ((bytesWritten * 1.0 / totalSize) * 100);
                    // 上传进度显示
//                    progress.setProgress(count);
//                    Log.e("上传 Progress>>>>>", bytesWritten + " / " + totalSize);
                }

                @Override
                public void onRetry(int retryNo) {
                    // TODO Auto-generated method stub
                    super.onRetry(retryNo);
                    // 返回重试次数
                }

            });
        } else {
//            Toast.makeText(mContext, "文件不存在", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * Save image to the SD card
     * 头像上传类
     *
     * @param photoBitmap
     * @param photoName
     * @param path
     */

    public static String savePhoto(Bitmap photoBitmap, String path,
                                   String photoName) {
        String localPath = null;
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File photoFile = new File(path, photoName + ".png");
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(photoFile);
                if (photoBitmap != null) {
                    if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100,
                            fileOutputStream)) { // 转换完成
                        localPath = photoFile.getPath();
                        fileOutputStream.flush();
                    }
                }
            } catch (FileNotFoundException e) {
                photoFile.delete();
                localPath = null;
                e.printStackTrace();
            } catch (IOException e) {
                photoFile.delete();
                localPath = null;
                e.printStackTrace();
            } finally {
                try {
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                        fileOutputStream = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return localPath;
    }


}
