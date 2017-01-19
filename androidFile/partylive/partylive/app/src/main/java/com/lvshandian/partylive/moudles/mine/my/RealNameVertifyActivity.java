package com.lvshandian.partylive.moudles.mine.my;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.base.BaseActivity;

import butterknife.Bind;

/**
 * Created by gjj on 2016/12/2.
 */

public class RealNameVertifyActivity extends BaseActivity {

    @Bind(R.id.iv_image)
    ImageView ivImage;
    @Bind(R.id.tv_content)
    TextView tvContent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_real_name_vertify;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initialized() {
        initTitle("", "实名认证", null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_titlebar_left:
                defaultFinish();
                break;
        }
    }
}
