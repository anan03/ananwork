package com.lvshandian.menshen.utils;

import android.view.Gravity;
import android.widget.Toast;

/**
 * Created zhang  on 2016/9/18.
 * 调试类
 */
import com.lvshandian.menshen.MyApplication;

public class ToastUtil {

    private static Toast mTempToast = null;

    /**
     * @param str     待弹出String
     * @param isShort 弹出时长是否较短
     * @param
     * @author zhang
     */
    public static void makeToast(String str, boolean isShort) {

        int duration = 0;

        if (isShort) {

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