package com.lvshandian.menshen.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ldb on 2016/6/3.
 * dp与px互转工具
 */
public class UiUtils {
    public static int dp2px(Context context, int dp) {
        // 获取像素密码
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    /**
     * px转dp的工具类
     *
     * @param px 要转换的px值
     * @return 该px对应的dp值
     */
    public static int px2dp(Context context, int px) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (px / density + 0.5f);
    }

    /**
     * px转sp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dp(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 让指定字符串中的指定子字符串变色
     *
     * @param string     长字符串
     * @param startIndex 起始index位置
     * @param contains   子字符串
     * @param color      颜色
     * @return
     */
    public static CharSequence showTextWithColor(TextView textView, String string, int startIndex, String contains, int color) {
        SpannableString ss = new SpannableString(string);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        ss.setSpan(colorSpan, startIndex, contains.length() + startIndex, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(ss);
        return ss;
    }


    /**
     * As meaning of method name.
     * 获得两点之间的距离
     *
     * @param p0
     * @param p1
     * @return
     */
    public static float getDistanceBetween2Points(PointF p0, PointF p1) {
        float distance = (float) Math.sqrt(Math.pow(p0.y - p1.y, 2) + Math.pow(p0.x - p1.x, 2));
        return distance;
    }


    /**
     * WebView加载html
     *
     * @param wv
     * @param html
     */
    public static void webViewLoadHtml(WebView wv, String html) {
        String encoding = "UTF-8";//编码
        String mimeType = "text/html";//html mimType类型
        wv.loadDataWithBaseURL(null, html, mimeType, encoding, null);
    }

    /**
     * 显示本地图片
     *
     * @param path
     * @param image
     */
    public static void setImage(String path, ImageView image) {
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeFile(path, option);
        image.setImageBitmap(bitmap);
    }
}
