package com.lvshandian.nan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lvshandian.myapplication.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button bt_pagerfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        inilistener();

    }

    private void initView() {
        bt_pagerfragment = (Button) findViewById(R.id.bt_pagerfragment);
    }

    private void inilistener() {
        bt_pagerfragment.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            //ViewPager+Fragment
            case R.id.bt_pagerfragment:
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                break;


        }

    }
}
