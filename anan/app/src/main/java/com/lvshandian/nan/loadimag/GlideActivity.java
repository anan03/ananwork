package com.lvshandian.nan.loadimag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.lvshandian.myapplication.R;
import com.lvshandian.nan.loadimag.Utils.GlideUtils;

/**
 * Created by zhang on 2016/12/17.
 * Glide加载图片
 */

public class GlideActivity extends AppCompatActivity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        ImageView imageView = (ImageView) findViewById(R.id.iv);
        GlideUtils.load(this, "http://image.miulive.cc/47814.png", imageView);
    }
}
