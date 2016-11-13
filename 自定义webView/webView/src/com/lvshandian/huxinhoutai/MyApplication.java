package com.lvshandian.huxinhoutai;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

/**
 * Created by zhang on 2016/10/11. 创建app
 */
public class MyApplication extends Application {

	public static Context mContext;

	@Override
	public void onCreate() {

		mContext = this;
		super.onCreate();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		mContext = null;
	}

	private static List<Activity> listActivity = new ArrayList<Activity>();

	public static void setListActivity(Activity activity) {
		listActivity.add(activity);
	}

	public static void finishActivity() {

		for (Activity activity : listActivity) {
			activity.finish();
		}
	}

	public static void finishLastActivity() {
		if (listActivity.size() > 0) {
			listActivity.get(listActivity.size() - 1).finish();
		}
	}

}
