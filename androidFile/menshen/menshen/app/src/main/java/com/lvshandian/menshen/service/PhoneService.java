package com.lvshandian.menshen.service;

import java.lang.reflect.Method;
import java.util.List;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.android.internal.telephony.ITelephony;
import com.lvshandian.menshen.bean.PhoneBean;
import com.lvshandian.menshen.receiver.PhoneBroadcastReceiver;
import com.lvshandian.menshen.utils.TextUtils;
import com.lvshandian.menshen.utils.XUtils;

public class PhoneService extends Service {
    String TAG = "tag";
    TelephonyManager telManager;

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        telManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        telManager.listen(new MyPhoneStateListener(),
                PhoneStateListener.LISTEN_CALL_STATE);
    }

    /**
     * 挂断电话
     */
    private void endCall() {
        Class<TelephonyManager> c = TelephonyManager.class;
        try {
            Method getITelephonyMethod = c.getDeclaredMethod("getITelephony", (Class[]) null);
            getITelephonyMethod.setAccessible(true);
            ITelephony iTelephony = null;
            iTelephony = (ITelephony) getITelephonyMethod.invoke(telManager, (Object[]) null);
            iTelephony.endCall();
        } catch (Exception e) {
        }
    }

    private class MyPhoneStateListener extends PhoneStateListener {
        String phoneNumber;

        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING: /* 接通 */
                    phoneNumber = incomingNumber;
                    List<PhoneBean> list = XUtils.findAll(PhoneBean.class);
                    for (int i = 0; i < list.size(); i++) {
                        if (TextUtils.isString(list.get(i).getDnseg(), phoneNumber)) {
                            endCall();
                            break;
                        }
                    }

            }
            super.onCallStateChanged(state, incomingNumber);
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent localIntent = new Intent();
        localIntent.setClass(this, PhoneService.class); // 销毁时重新启动Service
        this.startService(localIntent);
    }

    private int flags;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY_COMPATIBILITY;
    }

    @Override
    public void onStart(Intent intent, int startId) {
// 再次动态注册广播
        IntentFilter localIntentFilter = new IntentFilter("android.intent.action.USER_PRESENT");
        localIntentFilter.setPriority(Integer.MAX_VALUE);// 整形最大值
        myReceiver searchReceiver = new myReceiver();
        registerReceiver(searchReceiver, localIntentFilter);
        super.onStart(intent, startId);
    }

    public class myReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            context.startService(new Intent(context, PhoneBroadcastReceiver.class));
        }
    }
}
