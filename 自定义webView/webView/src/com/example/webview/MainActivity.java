package com.example.webview;

import com.lvshandian.huxinhoutai.view.ProgressWebView;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ProgressWebView webview = (ProgressWebView) findViewById(R.id.webview);
		TextView findViewById = (TextView) findViewById(R.id.tv);
		ImageView findViewById1 = (ImageView) findViewById(R.id.img);
		webview.loadUrl("http://google.114.org/", findViewById, findViewById1);
	}

}
