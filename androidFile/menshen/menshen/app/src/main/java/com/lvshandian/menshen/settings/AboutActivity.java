package com.lvshandian.menshen.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lvshandian.menshen.R;
import com.lvshandian.menshen.base.BaseActivity;
import com.zhy.autolayout.AutoLinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhang on 2016/11/6.
 * 创建关于我们界面
 */

public class AboutActivity extends BaseActivity {

    @Bind(R.id.view)
    View view;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.ll_parent_view)
    AutoLinearLayout llParentView;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.textView)
    TextView textView;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected int getLayoutId() {
        return R.layout.about_activity;
    }


    @Override
    protected void initListener() {
        ivBack.setOnClickListener(this);

    }

    @Override
    protected void initialized() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_back:
                finish();
                break;
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
