package com.lvshandian.partylive.service;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.lvshandian.partylive.utils.LogUtils;
import com.umeng.message.UTrack;
import com.umeng.message.UmengBaseIntentService;
import com.umeng.message.entity.UMessage;

import org.android.agoo.client.BaseConstants;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

/**
 * Developer defined push intent service.
 * Remember to call {@link com.umeng.message.PushAgent#setPushIntentServiceClass(Class)}.
 *
 * @author lucas
 */
//完全自定义处理类
//参考文档的1.6.5
//http://dev.umeng.com/push/android/integration#1_6_5
public class MyPushIntentService extends UmengBaseIntentService {

    @Override
    protected void onMessage(final Context context, final Intent intent) {
        // 需要调用父类的函数，否则无法统计到消息送达
        super.onMessage(context, intent);
        try {
            //可以通过MESSAGE_BODY取得消息体
            String message = intent.getStringExtra(BaseConstants.MESSAGE_BODY);
            final UMessage msg = new UMessage(new JSONObject(message));
            LogUtils.e("message=" + message);    //消息体
            LogUtils.e("custom=" + msg.custom);    //自定义消息的内容
            LogUtils.e("title=" + msg.title);    //通知标题
            LogUtils.e("text=" + msg.text);    //通知内容
            // code  to handle message here
            // ...

            // 对完全自定义消息的处理方式，点击或者忽略
            boolean isClickOrDismissed = true;
            if (isClickOrDismissed) {
                //完全自定义消息的点击统计
                UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
            } else {
                //完全自定义消息的忽略统计
                UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
            }
            LogUtils.e("当前线程: " + Thread.currentThread().getName());
            Looper mainLooper = Looper.getMainLooper();
            new Handler(mainLooper).post(new Runnable() {
                @Override
                public void run() {
                    EventBus.getDefault().post(msg);
/*
                  View inflate = View.inflate(context, R.layout.um_push_dialog, null);
                    RoundDialog mDialog = new RoundDialog(context, inflate, R.style.dialog);
                    TextView tvContent = (TextView) inflate.findViewById(R.id.tv_content);
                    tvContent.setText(msg.custom);
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                    mDialog.show();
                    LogUtils.e("mDialog: " + mDialog.isShowing());
                    LogUtils.e("当前线程mainLooper: " + Thread.currentThread().getName());*/
                }
            });

        } catch (Exception e) {
            LogUtils.e("消息错误: " + e.getMessage());
        }
    }
}
