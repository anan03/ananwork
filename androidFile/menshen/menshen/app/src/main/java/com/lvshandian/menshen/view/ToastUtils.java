package com.lvshandian.menshen.view;


import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lvshandian.menshen.MyApplication;
import com.lvshandian.menshen.R;

/**
 * SnackBar当做吐丝使用
 */
public class ToastUtils {
    private static Snackbar snackBar;

    public static void showSnackBar(View viewArgs, String toast) {
        if (snackBar != null) {
            snackBar.dismiss();
            snackBar = null;
        }
        snackBar = Snackbar.make(viewArgs, toast, Snackbar.LENGTH_SHORT);
        snackBar.setActionTextColor(MyApplication.mContext.getResources().getColor(R.color.skyblue)).setDuration(Snackbar.LENGTH_SHORT);
        View view = snackBar.getView();
        TextView text = (TextView) view.findViewById(R.id.snackbar_text);
        text.setText(toast);
        text.setTextColor(Color.WHITE);
        init(viewArgs, view);
    }


    public static void showSnackBar(View viewArgs, int toast) {
        if (snackBar != null) {
            snackBar.dismiss();
            snackBar = null;
        }
        if (viewArgs == null) {
            return;
        }
        snackBar = Snackbar.make(viewArgs, toast, Snackbar.LENGTH_SHORT);
        snackBar.setActionTextColor(MyApplication.mContext.getResources().getColor(R.color.skyblue)).setDuration(Snackbar.LENGTH_SHORT);
        View view = snackBar.getView();
        TextView text = (TextView) view.findViewById(R.id.snackbar_text);
        text.setText(toast);
        text.setTextColor(Color.WHITE);
        init(viewArgs, view);
    }

    private static void init(View viewArgs, View view) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
        view.setBackgroundColor(MyApplication.mContext.getResources().getColor(R.color.skyblue));
        layoutParams.setMargins(30, 10, 30, UiUtils.dp2px(MyApplication.mContext, 100));//4个参数按顺序分别是左上右下
        layoutParams.width = FrameLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        view.setLayoutParams(layoutParams);
        snackBar.show();
    }

    /**
     * 取消吐丝
     */
    public static void dimssToast() {
        if (snackBar != null) {
            snackBar.dismiss();
            snackBar = null;
        }
    }
}
