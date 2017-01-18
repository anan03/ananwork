package com.lvshandian.menshen.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by zhang on 2016/11/6.
 */

public class NetworkUtils {

    /**
     * 判断 网络是否可用
     *
     * @param activity
     * @return
     * @author
     */
    public static boolean IsNetworkAvailable(Context activity) {
        boolean b = false;
        ConnectivityManager cManager = (ConnectivityManager) activity
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            b = true;
        } else {

            b = false;
        }
        return b;
    }
}
