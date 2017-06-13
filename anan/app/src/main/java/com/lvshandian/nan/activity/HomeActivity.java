package com.lvshandian.nan.activity;

import android.view.View;
import android.widget.Button;

import com.lvshandian.myapplication.R;
import com.lvshandian.nan.base.BaseActivity;

/**
 * Viewpager+Fragment
 */
public class HomeActivity extends BaseActivity {

    Button bt_pagerfragment, bt_pagerfragment_gd, bt_pagerfragment_bj;

    @Override
    protected void initListener() {
        bt_pagerfragment.setOnClickListener(this);
        bt_pagerfragment_gd.setOnClickListener(this);
        bt_pagerfragment_bj.setOnClickListener(this);
    }

    @Override
    protected void initialized() {
        bt_pagerfragment = (Button) findViewById(R.id.bt_pagerfragment);
        bt_pagerfragment_gd = (Button) findViewById(R.id.bt_pagerfragment_gd);
        bt_pagerfragment_bj = (Button) findViewById(R.id.bt_pagerfragment_bj);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            //ViewPager+Fragment
            case R.id.bt_pagerfragment:
                gotoActivity(PagerFragment.class, false);
                break;
            //固定
            case R.id.bt_pagerfragment_gd:
                gotoActivity(FixTabActivity.class, false);
                break;
            //布局
            case R.id.bt_pagerfragment_bj:
                gotoActivity(LayoutActivity.class, false);
                break;
        }

    }

}
