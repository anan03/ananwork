package com.lvshandian.partylive.utils;

import android.view.Gravity;
import android.widget.Toast;

import com.lvshandian.partylive.MyApplication;

/**
 * Created zhang  on 2016/9/18.
 * 调试类
 */

public class ToastUtil {

    private static Toast mTempToast = null;

    /**
     * @param str     待弹出String

     * @param
     * @author zhang
     */
    public static void makeToast(String str) {

        int duration = 0;

        if (true) {

            duration = Toast.LENGTH_SHORT;
        } else {

            duration = Toast.LENGTH_LONG;
        }

        if (mTempToast == null) {
            mTempToast = Toast.makeText(MyApplication.mContext, str, duration);
        } else {

            mTempToast.setText(str);
        }

        mTempToast.setGravity(Gravity.CENTER, 0, 0);
        mTempToast.show();
    }

    /**
     * 只debug模式下显示
     *
     * @param str
     * @param
     * @author Jooper
     * @2016年7月3日 下午6:10:50
     */
    public static void debugToast(String str, boolean isShort) {

        int duration = 0;

        if (isShort) {

            duration = Toast.LENGTH_SHORT;
        } else {

            duration = Toast.LENGTH_LONG;
        }

        if (Constant.TOASTDEBUG) {

            if (mTempToast == null) {

                mTempToast = Toast.makeText(MyApplication.mContext, str, duration);
            } else {

                mTempToast.setText(str);
            }
            mTempToast.show();
        }
    }
}