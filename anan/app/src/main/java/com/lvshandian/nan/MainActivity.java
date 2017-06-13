package com.lvshandian.nan;

import android.view.View;
import android.widget.Button;

import com.lvshandian.myapplication.R;
import com.lvshandian.nan.activity.HomeActivity;
import com.lvshandian.nan.base.BaseActivity;
import com.lvshandian.nan.loadimag.LoadImagerActivity;

public class MainActivity extends BaseActivity {

    Button bt_pagerfragment, bt_imager;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    @Override
    protected void initListener() {
        bt_pagerfragment.setOnClickListener(this);
        bt_imager.setOnClickListener(this);
    }

    @Override
    protected void initialized() {
        bt_pagerfragment = (Button) findViewById(R.id.bt_pagerfragment);
        bt_imager = (Button) findViewById(R.id.bt_imager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            //ViewPager+Fragment
            case R.id.bt_pagerfragment:
                gotoActivity(HomeActivity.class, false);
                break;
            //图片加载
            case R.id.bt_imager:
                gotoActivity(LoadImagerActivity.class, false);
                break;

        }

    }

}
