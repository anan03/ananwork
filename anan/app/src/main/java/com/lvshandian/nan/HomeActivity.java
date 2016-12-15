package com.lvshandian.nan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.lvshandian.myapplication.R;
import com.lvshandian.nan.activity.FixTabActivity;
import com.lvshandian.nan.activity.LayoutActivity;
import com.lvshandian.nan.activity.PagerFragment;

/**
 * Viewpager+Fragment
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    Button bt_pagerfragment, bt_pagerfragment_gd, bt_pagerfragment_bj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        inilistener();
    }


    private void initView() {
        bt_pagerfragment = (Button) findViewById(R.id.bt_pagerfragment);
        bt_pagerfragment_gd = (Button) findViewById(R.id.bt_pagerfragment_gd);
        bt_pagerfragment_bj = (Button) findViewById(R.id.bt_pagerfragment_bj);

    }

    private void inilistener() {
        bt_pagerfragment.setOnClickListener(this);
        bt_pagerfragment_gd.setOnClickListener(this);
        bt_pagerfragment_bj.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            //ViewPager+Fragment
            case R.id.bt_pagerfragment:
                startActivity(new Intent(HomeActivity.this, PagerFragment.class));
                break;
            //固定
            case R.id.bt_pagerfragment_gd:
                startActivity(new Intent(HomeActivity.this, FixTabActivity.class));
                break;
            //布局
            case R.id.bt_pagerfragment_bj:
                startActivity(new Intent(HomeActivity.this, LayoutActivity.class));
                break;
        }

    }

}
