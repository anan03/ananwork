package com.bpj.lazyfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Fragment2  extends LazyFragment {
	
	public static Fragment2 newInstance(){
		Fragment2 fragment1 = new Fragment2();
		return fragment1;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
	
		View view = inflater.inflate(R.layout.fragment2,null);
		return view;
	}
	
	@Override
	public void setMenuVisibility(boolean menuVisible) {
		super.setMenuVisibility(menuVisible);
		if (this.getView() != null)
			this.getView()
					.setVisibility(menuVisible ? View.VISIBLE : View.GONE);
	}
	
	@Override
	protected void onVisible() {
		System.out.println("Fragment2¿ªÊ¼¼ÓÔØ¡£¡£¡£");
	}
	
}
