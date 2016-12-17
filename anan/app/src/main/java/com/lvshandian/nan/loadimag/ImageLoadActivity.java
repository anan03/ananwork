package com.lvshandian.nan.loadimag;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.lvshandian.myapplication.R;
import com.lvshandian.nan.loadimag.Utils.GlideUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by zhang on 2016/12/17.
 * Glide加载图片
 * 1.导入夹包
 * 2.在Appliction里面配置
 */

public class ImageLoadActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);
        ImageView imageView = (ImageView) findViewById(R.id.iv);
//        GlideUtils.load(this, "http://image.miulive.cc/47814.png", imageView);
        ImageLoader.getInstance().displayImage("http://image.miulive.cc/47814.png", imageView);
    }
}
