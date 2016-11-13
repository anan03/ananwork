package com.bpj.lazyfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Fragment1  extends LazyFragment{
	
	public static Fragment1 newInstance(){
		Fragment1 fragment1 = new Fragment1();
		return fragment1;
	}
	
	@Override
	public void setMenuVisibility(boolean menuVisible) {
		super.setMenuVisibility(menuVisible);
		if (this.getView() != null)
			this.getView()
					.setVisibility(menuVisible ? View.VISIBLE : View.GONE);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
	
		View view = inflater.inflate(R.layout.fragment1,null);
		return view;
	}
	
	@Override
	protected void onVisible() {
		System.out.println("Fragment1¿ªÊ¼¼ÓÔØ¡£¡£¡£");
	}
	
}
