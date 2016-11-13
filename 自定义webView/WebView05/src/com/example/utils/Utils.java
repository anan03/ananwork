package com.example.utils;

import com.example.webview05.R;

import android.app.AlertDialog;
import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

public class Utils {
	private Context mContext;

	public Utils(Context context) {
		this.mContext = context;
	}

	@JavascriptInterface
	public void showList() {
		new AlertDialog.Builder(mContext)
				.setTitle("图书列表")
				.setIcon(R.drawable.ic_launcher)
				.setItems(
						new String[] { "疯狂java讲义", "疯狂Android讲义",
								"轻量级java EE开发" }, null)
				.setPositiveButton("确定", null).create().show();

	}

	@JavascriptInterface
	public void showToast() {

		Toast.makeText(mContext, "调用成功", Toast.LENGTH_LONG).show();
	}

}
