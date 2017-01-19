package com.lvshandian.partylive.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.ObjectMetadata;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.lvshandian.partylive.UrlBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * https://help.aliyun.com/document_detail/32044.html?spm=5176.doc32043.6.245.s6MyhE
 * Created by sll on 2016/11/14.
 */
public class OSSClientUtil {
    /**
     * 阿里云ACCESS_ID
     */
    private static String ACCESS_ID = "STS.CGNoxmqF3wtdLxaGCGgnQovAW";
    /**
     * 阿里云ACCESS_KEY
     */
    private static String ACCESS_KEY = "9cxm9gns1XhU88iYRGvFV3sVntkz4hD8RQPrtCaucUYQ";
    /**
     * 阿里云OSS_ENDPOINT
     */
//    private static String OSS_ENDPOINT = "http://oss-cn-qingdao.aliyuncs.com";
    private static String OSS_ENDPOINT = "http://oss-cn-shanghai.aliyuncs.com";
    /**
     * 阿里云BUCKET_NAME  OSS
     */
//    private static String BUCKET_NAME = "miulive";
    private static String BUCKET_NAME = "partylive";

    private static OSSClient client;

    /**
     * 初始化阿里云
     *
     * @author sll
     * @time 2016/11/15 18:51
     */
    public static void init(Context context, OSSCredentialProvider credetialProvider) {
        // 明文设置secret的方式建议只在测试时使用，更多鉴权模式请参考后面的`访问控制`章节
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次

        client = new OSSClient(context.getApplicationContext(), UrlBuilder.OSS_ENDPOINT, credetialProvider, conf);
    }


    /**
     * 上传文件
     *
     * @param Objectkey 上传到OSS起的名
     * @param filename  本地文件名
     * @throws ClientException
     * @throws FileNotFoundException
     */
    public static void uploadFile(String Objectkey, String filename)
            throws ClientException, FileNotFoundException, Exception {
//        File file = new File(filename);
//        ObjectMetadata objectMeta = new ObjectMetadata();
//        objectMeta.setContentLength(file.length());
//        //判断上传类型，多的可根据自己需求来判定
//        if (filename.endsWith("xml")) {
//            objectMeta.setContentType("text/xml");
//        } else if (filename.endsWith("jpg")) {
//            objectMeta.setContentType("image/jpeg");
//        } else if (filename.endsWith("png")) {
//            objectMeta.setContentType("image/png");
//        }
//        InputStream input = new FileInputStream(file);
//        client.putObject(BUCKET_NAME, Objectkey, input, objectMeta);


        // 构造上传请求
        PutObjectRequest put = new PutObjectRequest(UrlBuilder.BUCKET_NAME, Objectkey, filename);
        // 异步上传时可以设置进度回调
        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
            @Override
            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
                LogUtils.d("PutObject", "currentSize: " + currentSize + " totalSize: " + totalSize);
            }
        });

        OSSAsyncTask task = client.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                LogUtils.d("PutObject", "UploadSuccess");
                Message msg = new Message();
                msg.what = 200;
            }

            @Override
            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                // 请求异常
                if (clientExcepion != null) {
                    // 本地异常如网络异常等
                    clientExcepion.printStackTrace();
                }
                if (serviceException != null) {
                    // 服务异常
                    LogUtils.e("ErrorCode", serviceException.getErrorCode());
                    LogUtils.e("RequestId", serviceException.getRequestId());
                    LogUtils.e("HostId", serviceException.getHostId());
                    LogUtils.e("RawMessage", serviceException.getRawMessage());
                }
            }
        });

// task.cancel(); // 可以取消任务

        task.waitUntilFinished(); // 可以等待直到任务完成


    }

}
