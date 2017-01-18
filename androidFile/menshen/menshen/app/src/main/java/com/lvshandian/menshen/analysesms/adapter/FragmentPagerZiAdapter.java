package com.lvshandian.menshen.analysesms.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

import static com.tandong.sa.view.AutoReFreshListView.c;

public class FragmentPagerZiAdapter extends android.support.v4.app.FragmentPagerAdapter {

    private Fragment[] fragmentClass;

    public Bundle[] getmBundles() {
        return mBundles;
    }

    public void setmBundles(Bundle[] mBundles) {
        this.mBundles = mBundles;
    }

    private Bundle[] mBundles;
    private int currentIndex;
    private List<String> titles;
    FragmentManager fm;

    public FragmentPagerZiAdapter(FragmentManager fm, Fragment[] fragmentClass, List<String> titles, Bundle[] mBundles) {
        super(fm);
        this.fm = fm;
        this.fragmentClass = fragmentClass;
        this.titles = titles;
        this.mBundles = mBundles;
    }

    public FragmentPagerZiAdapter(FragmentManager fm, Fragment[] fragmentClass, Bundle[] mBundles) {
        super(fm);
        this.fragmentClass = fragmentClass;
        this.mBundles = mBundles;
    }

    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        try {
            Fragment c = fragmentClass[arg0];
            Fragment mFragment = c;
            if (mBundles != null && arg0 < mBundles.length) {
                mFragment.setArguments(mBundles[arg0]);
            }
            return mFragment;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return fragmentClass == null ? 0 : fragmentClass.length;
    }

    /**
     * 保存当前viewpager的下标
     *
     * @param index
     */
    public void saveCurrentIndex(int index) {
        currentIndex = index;
    }

    /**
     * 读取保存的viewpager的下标
     *
     * @return
     */
    public int getSaveCurrentIndex() {
        return currentIndex;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    public void setFragments(Fragment[] fragmentClass) throws IllegalAccessException, InstantiationException {
        if (this.fragmentClass != null) {
            FragmentTransaction ft = fm.beginTransaction();

            for (int i = 0; i < fragmentClass.length; i++) {
            }
            for (Fragment f : this.fragmentClass) {
                ft.remove(f);
            }
            ft.commit();
            ft = null;
            fm.executePendingTransactions();
        }
        this.fragmentClass = fragmentClass;
        notifyDataSetChanged();
    }


}
