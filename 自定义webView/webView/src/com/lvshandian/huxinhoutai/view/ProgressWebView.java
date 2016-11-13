package com.lvshandian.huxinhoutai.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.webview.R;
import com.lvshandian.huxinhoutai.MyApplication;
import com.lvshandian.huxinhoutai.interfaces.VebViewGoBack;

/**
 * @author LDB
 * @version 1.0
 * @time: 2015-12-25 下午1:08:25
 * @fun:
 * @fix: 修改加载失败后的中显示
 */
public class ProgressWebView extends LinearLayout implements VebViewGoBack {

	WebView mWebView;
	ProgressBar mProgressBar;

	private Context mContext;

	private String url;

	String[] titleArray = { "首页", "产品", "发现", "广场", "我的" };

	public ProgressWebView(Context context) {
		this(context, null);
	}

	@SuppressLint("NewApi")
	public ProgressWebView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	@SuppressLint("NewApi")
	public ProgressWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
		initView(context);
	}

	private void initView(Context context) {
		View view = View.inflate(context, R.layout.view_web_progress, this);
		mWebView = (WebView) view.findViewById(R.id.web_view);
		mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void loadUrl(String url, TextView textView, ImageView goBack) {
		if (url == null) {
			// url = "http://blog.csdn.net/gyw520gyw";
			return;
		}
		initWebview(url, textView, goBack);
	}

	@SuppressLint("JavascriptInterface")
	private void initWebview(String url, final TextView textView,
			final ImageView goBack) {

		mWebView.addJavascriptInterface(this, "android");

		WebSettings webSettings = mWebView.getSettings();

		webSettings.setJavaScriptEnabled(true);
		// 设置可以访问文件
		webSettings.setAllowFileAccess(true);
		// 设置可以支持缩放
		webSettings.setSupportZoom(true);
		// 设置默认缩放方式尺寸是far
		webSettings.setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
		// 设置出现缩放工具
		webSettings.setBuiltInZoomControls(false);
		webSettings.setDefaultFontSize(16);

		mWebView.loadUrl(url);

		// 设置WebViewClient
		mWebView.setWebViewClient(new WebViewClient() {
			// url拦截
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// 使用自己的WebView组件来响应Url加载事件，而不是使用默认浏览器器加载页面
				view.loadUrl(url);
				// 相应完成返回true
				return true;
				// return super.shouldOverrideUrlLoading(view, url);
			}

			// 页面开始加载
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				mProgressBar.setVisibility(View.VISIBLE);
				super.onPageStarted(view, url, favicon);
			}

			// 页面加载完成
			@Override
			public void onPageFinished(WebView view, String url) {
				mProgressBar.setVisibility(View.GONE);
				textView.setText(view.getTitle());
				if (isExits(view.getTitle())) {
					goBack.setVisibility(View.INVISIBLE);
				} else {
					goBack.setVisibility(View.VISIBLE);
				}
				super.onPageFinished(view, url);
			}

			// WebView加载的所有资源url
			@Override
			public void onLoadResource(WebView view, String url) {
				super.onLoadResource(view, url);
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				// view.loadData(errorHtml, "text/html; charset=UTF-8", null);
				super.onReceivedError(view, errorCode, description, failingUrl);
			}

		});

		// 设置WebChromeClient
		mWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			// 处理javascript中的alert
			public boolean onJsAlert(WebView view, String url, String message,
					final JsResult result) {
				return super.onJsAlert(view, url, message, result);
			}

			;

			@Override
			// 处理javascript中的confirm
			public boolean onJsConfirm(WebView view, String url,
					String message, final JsResult result) {
				return super.onJsConfirm(view, url, message, result);
			}

			@Override
			// 处理javascript中的prompt
			public boolean onJsPrompt(WebView view, String url, String message,
					String defaultValue, final JsPromptResult result) {
				return super.onJsPrompt(view, url, message, defaultValue,
						result);
			}

			;

			// 设置网页加载的进度条
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				mProgressBar.setProgress(newProgress);
				super.onProgressChanged(view, newProgress);
			}

			// 设置程序的Title
			@Override
			public void onReceivedTitle(WebView view, String title) {
				super.onReceivedTitle(view, title);
			}
		});
		mWebView.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					if (keyCode == KeyEvent.KEYCODE_BACK
							&& mWebView.canGoBack()) { // 表示按返回键

						mWebView.goBack(); // 后退

						// webview.goForward();//前进
						return true; // 已处理
					}
				}
				return false;
			}
		});
	}

	public boolean canBack() {
		if (mWebView.canGoBack()) {
			mWebView.goBack();
			return false;
		} else {
			MyApplication.finishLastActivity();
		}
		return true;
	}

	private boolean isExits(String getTitle) {
		boolean isFlag = false;
		for (String title : titleArray) {
			if (title.equals(getTitle)) {
				isFlag = true;
			}
		}
		return isFlag;
	}

	@Override
	public void goback() {
		canBack();
	}

	@Override
	public void reload() {
		mWebView.reload();
	}
}
