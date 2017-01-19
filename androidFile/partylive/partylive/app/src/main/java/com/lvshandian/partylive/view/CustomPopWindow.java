package com.lvshandian.partylive.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 自定义PopupWindow，显示之前背景半透明，dismiss时恢复
 */
public class CustomPopWindow extends PopupWindow {

    private Activity activity;

    public CustomPopWindow(Context context, Activity activity) {
        super(context);
        this.activity = activity;
    }

    public CustomPopWindow(Context context, AttributeSet attrs, Activity activity) {
        super(context, attrs);
        this.activity = activity;
    }

    public CustomPopWindow(Context context, AttributeSet attrs, int defStyleAttr, Activity activity) {
        super(context, attrs, defStyleAttr);
        this.activity = activity;
    }

    public CustomPopWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, Activity activity) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.activity = activity;
    }

    public CustomPopWindow(Activity activity) {
        this.activity = activity;
    }

    public CustomPopWindow(View contentView, Activity activity) {
        super(contentView);
        this.activity = activity;
    }

    public CustomPopWindow(int width, int height, Activity activity) {
        super(width, height);
        this.activity = activity;
    }

    public CustomPopWindow(View contentView, int width, int height, Activity activity) {
        super(contentView, width, height);
        this.activity = activity;
    }

    public CustomPopWindow(View contentView, int width, int height, boolean focusable, Activity activity) {
        super(contentView, width, height, focusable);
        this.activity = activity;
    }

    @Override
    public void showAsDropDown(View anchor) {
        //显示之前设置背景透明度
        backgroundAlpha(0.4f);
        super.showAsDropDown(anchor);
    }
    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        //显示之前设置背景透明度
        backgroundAlpha(0.4f);
        super.showAsDropDown(anchor, xoff, yoff);
    }
    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        //显示之前设置背景透明度
        backgroundAlpha(0.4f);
        super.showAsDropDown(anchor, xoff, yoff, gravity);
    }
    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        //显示之前设置背景透明度
        backgroundAlpha(0.4f);
        super.showAtLocation(parent, gravity, x, y);
    }

    @Override
    public void dismiss() {
        //显示之前设置背景透明度
        backgroundAlpha(1.0f);
        super.dismiss();
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        if (activity != null) {
            WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
            lp.alpha = bgAlpha; //0.0-1.0
            activity.getWindow().setAttributes(lp);
        }
    }
}
