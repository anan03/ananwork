package com.lvshandian.partylive.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by gjj on 2016/12/9.
 * <p>
 * 显示 SHOW_TIME时长 后自动消失的PopupWindow
 */

public class ShowPop extends PopupWindow {
    private final long SHOW_TIME = 1000 * 3;

    public ShowPop(Context context) {
        super(context);
    }

    public ShowPop(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShowPop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ShowPop(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public ShowPop() {
    }

    public ShowPop(View contentView) {
        super(contentView);
    }

    public ShowPop(int width, int height) {
        super(width, height);
    }

    public ShowPop(View contentView, int width, int height) {
        super(contentView, width, height);
    }

    public ShowPop(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

    @Override
    public void showAsDropDown(View anchor) {
        try{
            super.showAsDropDown(anchor);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try{
                        if (isShowing()) {
                            dismiss();
                        }
                    }catch (Exception e){

                    }
                }
            }, SHOW_TIME);
        }catch (Exception e){

        }

    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        try{
            super.showAtLocation(parent, gravity, x, y);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try{
                        if (isShowing()) {
                            dismiss();
                        }
                    }catch (Exception e){

                    }
                }
            }, SHOW_TIME);
        }catch (Exception e){

        }

    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        try {
            super.showAsDropDown(anchor, xoff, yoff);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try{
                        if (isShowing()) {
                            dismiss();
                        }
                    }catch (Exception e){

                    }
                }
            }, SHOW_TIME);
        } catch (Exception e) {

        }

    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        try{
            super.showAsDropDown(anchor, xoff, yoff, gravity);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try{
                        if (isShowing()) {
                            dismiss();
                        }
                    }catch (Exception e){

                    }
                }
            }, SHOW_TIME);
        }catch (Exception e){

        }

    }
}
