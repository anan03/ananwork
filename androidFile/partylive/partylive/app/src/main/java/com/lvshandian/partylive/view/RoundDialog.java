package com.lvshandian.partylive.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.lvshandian.partylive.R;

/**
 * Created by lws on 2016/9/7.
 */
public class RoundDialog extends Dialog {

    public RoundDialog(Context context, View layout) {
        super(context, R.style.dialog);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int displayWidth = wm.getDefaultDisplay().getWidth();
        params.width = displayWidth * 3 / 4;
        window.setAttributes(params);
    }

    public RoundDialog(Context context, View layout, int style) {
        super(context, style);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int displayWidth = wm.getDefaultDisplay().getWidth();
        int displayHeight = wm.getDefaultDisplay().getHeight();
        params.width = displayWidth * 3 / 4;
        params.height = displayHeight * 2 / 3;
        window.setAttributes(params);
    }


    /**
     * 自定义对话框
     *
     * @param context 上下文
     * @param layout  布局
     * @param style   主题
     * @param width   宽度占屏幕宽度的百分比 (0,1]
     * @param height  高度占屏幕高度的百分比 (0,1]
     */
    public RoundDialog(Context context, View layout, int style, float width, float height) {
        super(context, style);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int displayWidth = wm.getDefaultDisplay().getWidth();
        int displayHeight = wm.getDefaultDisplay().getHeight();
        params.width = (int) (displayWidth * width);
        params.height = (int) (displayHeight * height);
        window.setAttributes(params);
    }

}
