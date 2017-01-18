package com.lvshandian.menshen.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.lvshandian.menshen.utils.Sp;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zhang on 2016/11/15.
 */

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //接收安装广播
        if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            String packageName = intent.getDataString();
            System.out.println("安装了:" + packageName + "包名的程序");
        }
        //接收卸载广播
        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            String packageName = intent.getDataString();
            System.out.println("卸载了:" + packageName + "包名的程序");
            //刷新卸载界面
            Sp.setParam(context, "apk", packageName);
            //发送消息方
            EventBus.getDefault().post("reflashapk");
        }
    }
}
