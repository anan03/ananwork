package com.lvshandian.nan.loadimag;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.lvshandian.myapplication.R;
import com.lvshandian.nan.activity.HomeActivity;
import com.lvshandian.nan.activity.PagerFragment;

/**
 * Created by zhang on 2016/12/17.
 */

public class LoadImagerActivity extends AppCompatActivity implements View.OnClickListener {

    Button bt_glide, bt_imagload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadimager);
        bt_glide = (Button) findViewById(R.id.bt_glide);
        bt_imagload = (Button) findViewById(R.id.bt_imagload);

        initListener();
    }

    private void initListener() {

        bt_imagload.setOnClickListener(this);
        bt_glide.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            //glide图片
            case R.id.bt_glide:

                startActivity(new Intent(LoadImagerActivity.this, GlideActivity.class));
                break;
            //ImagLoad图片
            case R.id.bt_imagload:
                startActivity(new Intent(LoadImagerActivity.this, ImageLoadActivity.class));

                break;


        }

    }
}
