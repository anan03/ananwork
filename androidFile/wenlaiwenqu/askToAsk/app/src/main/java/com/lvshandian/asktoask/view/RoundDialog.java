package com.lvshandian.asktoask.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by lws on 2016/9/7.
 */
public class RoundDialog extends Dialog {

    public RoundDialog(Context context, View layout, int style) {
        super(context, style);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int displayWidth = wm.getDefaultDisplay().getWidth();
        params.width = displayWidth * 3 / 4;
        window.setAttributes(params);
    }
}
