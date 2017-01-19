package com.lvshandian.partylive.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.lvshandian.partylive.MyApplication;

/**
 * Created by gjj on 2016/8/5.
 * 操作软键盘的工具类
 */
public class SoftKeyBoardUtils {

    private static InputMethodManager imm;

    private static InputMethodManager getInstace() {
        if (imm == null) {
            imm = (InputMethodManager) MyApplication.mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        return imm;
    }

    /**
     * 隐藏软键盘
     *
     * @param et
     */
    public static void hiddenKeyBoard(View et) {
        getInstace().hideSoftInputFromWindow(et.getWindowToken(), 0); //强制隐藏键盘
    }

    /**
     * 显示软键盘
     *
     * @param et
     */
    public static void showKeyBoard(View et) {
        getInstace().showSoftInput(et, 0);
    }
}
