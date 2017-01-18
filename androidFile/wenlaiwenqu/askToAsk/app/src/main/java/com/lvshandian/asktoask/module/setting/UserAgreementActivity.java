package com.lvshandian.asktoask.module.setting;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lvshandian.asktoask.BaseActivity;
import com.lvshandian.asktoask.R;

import butterknife.Bind;

/**
 * 用户协议on 2016/9/7.
 */
public class UserAgreementActivity extends BaseActivity {

    @Bind(R.id.tv_titlebar_centertext)
    TextView tvTitlebarCentertext;
    @Bind(R.id.ll_titlebar_zuojiantou)
    LinearLayout llTitlebarZuojiantou;

    @Bind(R.id.tv_yonghuxieyi_title)
    TextView tvYonghuxieyiTitle;
    @Bind(R.id.tv_yonghuxieyi_xieyineirong)
    TextView tvYonghuxieyiXieyineirong;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_yonghuxieyi;
    }

    @Override
    protected void initListener() {
        llTitlebarZuojiantou.setOnClickListener(this);
    }

    @Override
    protected void initialized() {
        if ("SettingAactivity".equals(getIntent().getStringExtra("classname"))) {
            tvTitlebarCentertext.setText(R.string.setting_guanyuwomen);
            tvYonghuxieyiXieyineirong.setText(getResources().getString(R.string.setting_about));

        } else {
            tvTitlebarCentertext.setText(R.string.mine_itemtext_yonghuxieyi);
            tvYonghuxieyiXieyineirong.setText(getResources().getString(R.string.setting_xieyi));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_titlebar_zuojiantou:
                finish();
                break;
            default:
                break;
        }
    }

}
