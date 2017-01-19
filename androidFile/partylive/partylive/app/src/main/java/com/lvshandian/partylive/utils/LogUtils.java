package com.lvshandian.partylive.utils;

import android.util.Log;

/**
 * Log打印Utils
 *
 * @author zhang
 */
public class LogUtils {

    private LogUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    private static final String TAG = "lsd";

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (Constant.LOGDEBUG)
            Log.i(TAG, msg);
    }

    public static void d(String msg) {
        if (Constant.LOGDEBUG)
            Log.d(TAG, msg);
    }

    public static void e(String msg) {
        if (Constant.LOGDEBUG)
            Log.e(TAG, msg);
    }

    public static void v(String msg) {
        if (Constant.LOGDEBUG)
            Log.v(TAG, msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (Constant.LOGDEBUG)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (Constant.LOGDEBUG)
            Log.i(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (Constant.LOGDEBUG)
            Log.i(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (Constant.LOGDEBUG)
            Log.i(tag, msg);
    }

    public static void j(String msg) {
        if (Constant.LOGDEBUG)
            Log.d("Jooper", msg);
    }

    public static void n(String msg) {
        if (Constant.LOGDEBUG)
            Log.d("zyn", msg);
    }
}