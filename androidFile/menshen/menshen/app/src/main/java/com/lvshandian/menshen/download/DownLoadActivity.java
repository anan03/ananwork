package com.lvshandian.menshen.download;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lvshandian.menshen.R;
import com.lvshandian.menshen.base.BaseActivity;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhang on 2016/11/10.
 * 创建卸载软件界面
 */
public class DownLoadActivity extends BaseActivity {
    AutoRelativeLayout rlRl;
    private TextView tv_load;
    private TextView tv_unload;
    private ViewPager viewpager;
    private List<Fragment> listFrament;
    private AutoLinearLayout rl_bianji;
    private ImageView ivBack;

    @Override
    protected int getLayoutId() {
        return R.layout.download_activity;
    }


    @Override
    protected void initListener() {
        tv_load = (TextView) findViewById(R.id.tv_leavemessage);
        tv_unload = (TextView) findViewById(R.id.tv_instationmessage);
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        rl_bianji = (AutoLinearLayout) findViewById(R.id.rl_guanjianzi);
        ivBack = (ImageView) findViewById(R.id.iv_back);
        rl_bianji.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        tv_load.setOnClickListener(new MyListenner());
        tv_unload.setOnClickListener(new MyListenner());

    }

    @Override
    protected void initialized() {

        FragmentUnload fragmentunLoad = new FragmentUnload();//所有的应用
        FragmentLoad fragmentLoad = new FragmentLoad();//卸载的应用
        listFrament = new ArrayList<Fragment>();
        listFrament.add(fragmentLoad);
        listFrament.add(fragmentunLoad);
        viewpager.setAdapter(new MyFrageStatePagerAdapter(getSupportFragmentManager()));
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                changeView(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     * 设置Viewpager适配器
     */


    class MyFrageStatePagerAdapter extends FragmentStatePagerAdapter {

        public MyFrageStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return listFrament.get(position);
        }

        @Override
        public int getCount() {
            return listFrament.size();
        }


    }

    class MyListenner implements View.OnClickListener {


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_leavemessage:

                    changeView(0);
                    viewpager.setCurrentItem(0, true);
                    break;
                case R.id.tv_instationmessage:
                    changeView(1);
                    viewpager.setCurrentItem(1, true);
                    break;


            }
        }
    }

    //手动设置ViewPager要显示的视图
    private void changeView(int desTab) {
        switch (desTab) {
            case 0:
                tv_load.setTextColor(getResources().getColor(R.color.white));
                tv_unload.setTextColor(getResources().getColor(R.color.lightgrey));
                break;
            case 1:
                tv_load.setTextColor(getResources().getColor(R.color.lightgrey));
                tv_unload.setTextColor(getResources().getColor(R.color.white));
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back://返回
                finish();
                break;
            case R.id.rl_guanjianzi://跳转到关键字修改界面
                Intent intent = new Intent(DownLoadActivity.this, LoadSettingActivity.class);
                intent.putExtra("userId", getIntent().getStringExtra("userId"));
                startActivity(intent);
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
