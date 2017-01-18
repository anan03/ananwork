package com.lvshandian.asktoask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lvshandian.asktoask.common.http.HttpDatas;
import com.lvshandian.asktoask.utils.UrlBuilder;
import com.tandong.sa.activity.SmartFragmentActivity;

import butterknife.ButterKnife;

/**
 * Activity 基类  ldb 2016/3/24
 */
public abstract class BaseActivity extends SmartFragmentActivity implements View.OnClickListener {

    protected View snackView;
    protected Context mContext;
    protected HttpDatas httpDatas;
    protected UrlBuilder UrlBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        PgyCrashManager.register(this);
        //沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }
        UrlBuilder = new UrlBuilder();
        int layoutId = getLayoutId();
        if (layoutId != 0) {
            setContentView(layoutId);
            // 删除窗口背景
            ButterKnife.bind(this);
        }
        mContext = this.getApplicationContext();
        snackView = getWindow().getDecorView().getRootView();
        httpDatas = new HttpDatas(this, snackView);
        App.setListActivity(this);
        preliminary();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 向用户展示信息前的准备工作在这个方法里处理
     */
    protected void preliminary() {
        // 初始化组件
        initListener();
        // 初始化数据
        initialized();
    }

    /**
     * 获取全局的Context
     *
     * @return {@link #mContext = this.getApplicationContext();}
     */
    public Context getContext() {
        return mContext;
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


    /**
     * 默认退出
     */
    public void defaultFinish() {
        super.finish();
    }


    public void gotoActivity(Class<? extends Activity> clazz, boolean finish) {
        Intent intent = new Intent(this, clazz);
        this.startActivity(intent);
        if (finish) {
            this.finish();
            this.overridePendingTransition(R.anim.activity_move_in_start, R.anim.activity_move_out_start);
        }

    }

}
