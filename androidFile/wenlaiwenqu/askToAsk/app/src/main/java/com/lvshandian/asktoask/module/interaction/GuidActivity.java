package com.lvshandian.asktoask.module.interaction;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lvshandian.asktoask.BaseActivity;
import com.lvshandian.asktoask.MainActivity;
import com.lvshandian.asktoask.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 开机引导页 on 2016/10/11.
 */
public class GuidActivity extends BaseActivity {

    @Bind(R.id.contentPager)
    ViewPager contentPager;

    //界面列表
    private ArrayList<View> views;

    //引导图片资源

    private  final int[] pics = { R.drawable.guide1,

            R.drawable.guide2, R.drawable.guide3};


    @Override
    protected void initialized() {
        views=new ArrayList<>();

        LinearLayout.LayoutParams mParams =new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        //初始化引导图片列表

        for(int i=0; i<pics.length; i++) {

            ImageView iv = new ImageView(this);

            iv.setLayoutParams(mParams);

            iv.setImageResource(pics[i]);

            views.add(iv);

        }
        contentPager.setAdapter(new ViewPagerAdapter(views));
    }

    @Override
    protected void initListener() {
        contentPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==2){
                    views.get(position).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(GuidActivity.this, StartActivity.class);
                            startActivity(intent);
                        }
                    });
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_guid_layout;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    public class ViewPagerAdapter extends PagerAdapter {

        //界面列表
        private ArrayList<View> views;

        public ViewPagerAdapter (ArrayList<View> views){
            this.views = views;
        }

        //获得当前界面数
        @Override
        public int getCount() {
            if (views != null) {
                return views.size();
            }
            return 0;
        }

        //初始化position位置的界面
        @Override
        public Object instantiateItem(View view, int position) {

            ((ViewPager) view).addView(views.get(position), 0);

            return views.get(position);
        }

        //判断是否由对象生成界面
        @Override
        public boolean isViewFromObject(View view, Object arg1) {
            return (view == arg1);
        }

        //销毁position位置的界面
        @Override
        public void destroyItem(View view, int position, Object arg2) {
            ((ViewPager) view).removeView(views.get(position));
        }
    }


}
