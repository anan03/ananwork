package com.lvshandian.nan.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.lvshandian.myapplication.R;
import com.lvshandian.nan.MyApplication;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/13.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    protected Context mContext;
    private LayoutInflater mInflator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(false);
            tintManager.setStatusBarTintResource(R.color.colorPrimary);//通知栏所需颜色
        }
        mInflator = LayoutInflater.from(this);
        int layoutId = getLayoutId();
        if (layoutId != 0) {
            setContentView(layoutId);
            // 删除窗口背景
            ButterKnife.bind(this);
        }
        mContext = this;
        MyApplication.addListActivity(this);
//        appUser = (AppUser) CacheUtils.readObject(this, CacheUtils.USERINFO);
        preliminary();
        //注册EventBUs
        /**
         1. compile 'org.greenrobot:eventbus:3.0.0'

         2.EventBus.getDefault().register(this); //第1步: 注册
         EventBus.getDefault().unregister(this);//反注册
         3.
         //发送消息方
         EventBus.getDefault().post("reflash");
         //接受方：
         @Subscribe //第2步:注册一个在后台线程执行的方法,用于接收事件
         public void onEventMainThread(String event) ｛

         ｝
         */
        EventBus.getDefault().register(this);
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

    @Subscribe //第2步:注册一个在后台线程执行的方法,用于接收事件
    public void onEventMainThread(String event) {


    }

    /**
     * 默认退出
     */
    public void defaultFinish() {
        super.finish();
    }

    /**
     * 初始化监听
     */
    protected abstract void initListener();

    /**
     * 初始化数据
     */
    protected abstract void initialized();

    /**
     * 布局文件ID
     *
     * @return
     */
    protected abstract int getLayoutId();

    public void gotoActivity(Class<? extends Activity> clazz, boolean finish) {
        Intent intent = new Intent(this, clazz);
        this.startActivity(intent);
        if (finish) {
            this.finish();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            //设置切换动画，从右边进入，左边退出
            this.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册
    }
}
