package com.lvshandian.partylive.moudles.mine.my;

import android.view.View;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.base.BaseActivity;

/**
 * Created by Administrator on 2016/12/20.
 */

public class TiXianActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_tixian;
    }

    @Override
    protected void initListener() {


    }

    @Override
    protected void initialized() {
        initTitle("","首次提现账号绑定","");

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
