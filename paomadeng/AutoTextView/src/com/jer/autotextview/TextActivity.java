package com.jer.autotextview;

import android.app.Activity;
import android.os.Bundle;

public class TextActivity extends Activity {

	private AutoTextView t1;
	private AutoTextView t2;
	private AutoTextView t3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		t1 = (AutoTextView) findViewById(R.id.tv);
		t2 = (AutoTextView) findViewById(R.id.tv2);
		t3 = (AutoTextView) findViewById(R.id.tv3);
		// 设置滚动的速度
		t1.setScrollMode(AutoTextView.SCROLL_SLOW);
		t2.setScrollMode(AutoTextView.SCROLL_NORM);
		t3.setScrollMode(AutoTextView.SCROLL_FAST);
	}

}
