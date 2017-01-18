package com.lvshandian.asktoask.module.message;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lvshandian.asktoask.BaseFragment;
import com.lvshandian.asktoask.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * author: ldb on 2016/8/30 17:08.
 * email：lvdabing@lvshandian.com
 * company:北京绿闪电科技有限公司
 * details：消息
 */
public class MessageFragment extends BaseFragment {
    @Bind(R.id.tv_leavemessage)
    TextView tvLeavemessage;
    @Bind(R.id.tv_leaveline)
    TextView tvLeaveline;
    @Bind(R.id.tv_instationmessage)
    TextView tvInstationmessage;
    @Bind(R.id.tv_instationline)
    TextView tvInstationline;
    @Bind(R.id.viewpager)
    ViewPager viewpager;
    private List<Fragment> listFrament;


    @Override
    protected int getLayoutId() {
        return R.layout.message_fragment_layout;
    }

    @Override
    protected void initListener() {

        tvLeavemessage.setOnClickListener(new MyListenner());
        tvInstationmessage.setOnClickListener(new MyListenner());
    }

    @Override
    protected void initialized() {


        LeaveMessageFragment leaveFragment = new LeaveMessageFragment();//留言fragment

        InstationMessageFragment instationFragment = new InstationMessageFragment();//站内信

        listFrament = new ArrayList<Fragment>();

        listFrament.add(leaveFragment);
        listFrament.add(instationFragment);
        viewpager.setAdapter(new MyFrageStatePagerAdapter(getChildFragmentManager()));
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    //手动设置ViewPager要显示的视图
    private void changeView(int desTab) {

        switch (desTab) {

            case 0:
                tvLeavemessage.setTextColor(getResources().getColor(R.color.main));
                tvInstationmessage.setTextColor(getResources().getColor(R.color.black));
                tvLeaveline.setVisibility(View.VISIBLE);
                tvInstationline.setVisibility(View.GONE);
                break;
            case 1:
                tvLeavemessage.setTextColor(getResources().getColor(R.color.black));
                tvInstationmessage.setTextColor(getResources().getColor(R.color.main));
                tvInstationline.setVisibility(View.VISIBLE);
                tvLeaveline.setVisibility(View.GONE);
                break;
        }


    }

    class MyListenner implements View.OnClickListener {


        @Override
        public void onClick(View v) {


            switch (v.getId()) {
                /**
                 * 留言监听
                 */
                case R.id.tv_leavemessage:

                    changeView(0);
                    viewpager.setCurrentItem(0, true);
                    break;
                /**
                 * 站内信监听
                 */
                case R.id.tv_instationmessage:

                    changeView(1);
                    viewpager.setCurrentItem(1, true);
                    break;

            }
        }
    }
}
