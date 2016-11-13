package com.bpj.lazyfragment;

import android.support.v4.app.Fragment;

public class LazyFragment extends Fragment {
	/**
	 * 当前Fragment是否显示：true,显示
	 */
	public boolean isVisible ;
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		isVisible = false;
		if(getUserVisibleHint()){
			 isVisible = true;
			 onVisible();
		}
	}
	
	protected void onVisible(){
		
	}
	
}
