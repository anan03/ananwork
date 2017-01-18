package com.lvshandian.menshen.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import com.lvshandian.menshen.bean.Keyworkinfo;
import com.lvshandian.menshen.bean.SmsInfo;
import com.lvshandian.menshen.utils.LogUtils;
import com.lvshandian.menshen.utils.TextUtils;
import com.lvshandian.menshen.utils.XUtils;
import com.lvshandian.menshen.view.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


/**
 * @author idulc
 */
public class ReceiverSms extends BroadcastReceiver {
    /**
     * 以BroadcastReceiver接收SMS短信
     */
    public static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (ACTION.equals(intent.getAction())) {
//            Intent i = new Intent(context, ReceiverSmsActivity.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            SmsMessage[] msgs = getMessageFromIntent(intent);

            StringBuilder sBuilder = new StringBuilder();
            String bodyString = "";

            if (msgs != null && msgs.length > 0) {
                for (SmsMessage msg : msgs) {
                    sBuilder.append("接收到了短信：\n发件人是：");
                    sBuilder.append(msg.getDisplayOriginatingAddress());
                    sBuilder.append("\n------短信内容-------\n");
                    sBuilder.append(msg.getDisplayMessageBody());
                    bodyString = msg.getDisplayMessageBody();


//                    i.putExtra("sms_address", msg.getDisplayOriginatingAddress());
//                    i.putExtra("sms_body", msg.getDisplayMessageBody());
                }
            }
            //信息内容
//            Toast.makeText(context, sBuilder, Toast.LENGTH_SHORT).show();
//            context.startActivity(i);


            int typte = 0;
            List<Keyworkinfo> listKeyWork = XUtils.findAll(Keyworkinfo.class);
            if (null != listKeyWork && listKeyWork.size() > 0) {

                for (int i = 0; i < listKeyWork.size(); i++) {
                    if (TextUtils.isString(listKeyWork.get(i).getKeyword(), bodyString)) {
                        int keyType = listKeyWork.get(i).getKeywordType();
                        if (keyType == 1) {
                            Toast.makeText(context, "您有一条诈骗短信", Toast.LENGTH_SHORT).show();
                            EventBus.getDefault().post("allReflash");
                        } else if (keyType == 2) {
                            Toast.makeText(context, "您有一条垃圾短信", Toast.LENGTH_SHORT).show();
                            EventBus.getDefault().post("allReflash");
                        } else if (keyType == 3) {
                            Toast.makeText(context, "您有一条伪基站短信", Toast.LENGTH_SHORT).show();
                            EventBus.getDefault().post("allReflash");
                        }
                        break;
                    }

                }
            }

        }

    }

    public static SmsMessage[] getMessageFromIntent(Intent intent) {
        SmsMessage retmeMessage[] = null;
        Bundle bundle = intent.getExtras();
        Object pdus[] = (Object[]) bundle.get("pdus");
        retmeMessage = new SmsMessage[pdus.length];
        for (int i = 0; i < pdus.length; i++) {
            byte[] bytedata = (byte[]) pdus[i];
            retmeMessage[i] = SmsMessage.createFromPdu(bytedata);
        }
        return retmeMessage;
    }
}
