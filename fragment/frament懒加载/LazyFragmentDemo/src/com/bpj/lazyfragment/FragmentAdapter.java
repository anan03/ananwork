package com.bpj.lazyfragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
/**
 * FragmentÌí¼ÓµÄÊÊÅäÆ÷£»
 * @author baopengjian
 *
 */
public class FragmentAdapter extends FragmentPagerAdapter {
	
	private Fragment[] mFragments;
	
	public FragmentAdapter(FragmentManager fm,Fragment[] mFragments) {
		super(fm);
		this.mFragments = mFragments;
	}

	@Override
	public Fragment getItem(int arg0) {
		return this.mFragments[arg0];
	}

	@Override
	public int getCount() {
		return mFragments.length;
	}

}
