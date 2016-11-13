package com.example.webview;

import com.lvshandian.huxinhoutai.view.ProgressWebView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ProgressWebView webview = (ProgressWebView) findViewById(R.id.webview);
		// 标题
		TextView findViewById = (TextView) findViewById(R.id.tv);
		ImageView findViewById1 = (ImageView) findViewById(R.id.img);
		webview.loadUrl("http://google.114.org/", findViewById, findViewById1);

		findViewById1.setVisibility(View.GONE);
	}

}
