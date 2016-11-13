package com.example.webview05;

import com.example.utils.Utils;

import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

public class MainActivity extends Activity {

	private WebView webview;
	private Button button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		webview = (WebView) findViewById(R.id.webview);
		button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				webview.loadUrl("javascript:doAlert()");
			}
		});
		// 获取WebSetting对象
		WebSettings webSettings = webview.getSettings();
		// 设置支持javascript
		webSettings.setJavaScriptEnabled(true);
		// 将Android里面定义的类Utils对象暴露给javascript
		webview.addJavascriptInterface(new Utils(MainActivity.this), "utils");
		webview.loadUrl("file:///android_asset/test.html");
		webview.setWebChromeClient(new WebChromeClient());
	}

	// 用户按下设备中某个按键的时候调用这个方法
	// 参数1:keycode：用户按键所代表的按键码
	// 参数2:keyEvent：按键事件，所有的keycode都在这个类中
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		// 用户按下返回键的时候打印日志 canGoBack判断当前webView是否存在历史纪录，如果存在返回true
		if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
			// 返回webview的历史纪录
			webview.goBack();
			// 代表已经处理了这个事件
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
