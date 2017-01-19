package com.lvshandian.partylive.wangyiyunxin.main.activity;

import com.lvshandian.partylive.MyApplication;
import com.lvshandian.partylive.R;
import com.netease.nim.uikit.common.activity.UI;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import butterknife.ButterKnife;

/**
 * 网易云信的自定义基类
 *
 * @author sll
 * @time 2016/11/23 15:48
 */
public abstract class WangYiBaseActivity extends UI implements View.OnClickListener {
    protected TextView titlebar_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.alpha_half);//通知栏所需颜色
        }

        int layoutId = getLayoutId();
        if (layoutId != 0) {
            setContentView(layoutId);
            // 删除窗口背景
            ButterKnife.bind(this);
        }
        MyApplication.setListActivity(this);
//        screenLandscapeDir();
        preliminary();
    }

    /**
     * 初始化标题,不需要右按钮，传空即可
     *
     * @param left
     * @param title
     * @param right
     */
    protected void initTitle(String left, String title, String right) {
        TextView titlebar_left = (TextView) findViewById(R.id.tv_titlebar_left);
        TextView titlebar_title = (TextView) findViewById(R.id.tv_titlebar_title);
        titlebar_right = (TextView) findViewById(R.id.tv_titlebar_right);

        if (left == null) {
            titlebar_left.setVisibility(View.INVISIBLE);
        } else {
            titlebar_left.setText(left);
            titlebar_left.setVisibility(View.VISIBLE);
        }
        if (right == null) {
            titlebar_right.setVisibility(View.INVISIBLE);
        } else {
            titlebar_right.setText(right);
            titlebar_right.setVisibility(View.VISIBLE);
        }
        titlebar_title.setText(title);
        titlebar_left.setOnClickListener(this);
        titlebar_right.setOnClickListener(this);
        titlebar_title.setOnClickListener(this);

    }

    /**
     * 向用户展示信息前的准备工作在这个方法里处理
     */
    protected void preliminary() {
        // 初始化数据
        initialized();
        // 初始化组件
        initListener();

    }

    /**
     * 布局文件ID
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化监听
     */
    protected abstract void initListener();

    /**
     * 初始化数据
     */
    protected abstract void initialized();

    @Override
    public void onClick(View view) {

    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}
