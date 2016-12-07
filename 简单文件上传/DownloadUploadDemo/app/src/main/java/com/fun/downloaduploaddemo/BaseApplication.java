package com.fun.downloaduploaddemo;

import android.app.Application;

import org.xutils.x;

/**
 * Created by HZF on 2016/4/25.
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }

}
