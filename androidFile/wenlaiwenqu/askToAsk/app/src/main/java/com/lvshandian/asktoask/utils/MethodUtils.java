package com.lvshandian.asktoask.utils;

import android.text.TextUtils;
import android.widget.TextView;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2016/9/1.
 * 一些工具方法
 */
public class MethodUtils {

//    private static volatile ConcurrentHashMap<String, String> map ;
//
//
//    public static ConcurrentHashMap<String, String> getMap() {
//        if (map == null){
//            synchronized (MethodUtils.class){
//                map = map == null ? new ConcurrentHashMap<String, String>() : map;
//            }
//        }
//        if (!map.isEmpty())
//            map.clear();
//        return map;
//    }


    /**
     * 返回TextView 和Edittext 控件的内容且不可为空
     *
     * @param tv
     * @return
     */
    public static String getTextContentHint(TextView tv) {
        if (tv != null) {

            if (tv.getHint() != null) {
                return tv.getHint().toString().trim();
            } else {
                return "";
            }
        }

        return "";
    }

    /**
     * 返回TextView 根据textview获取到字符串的值
     *
     * @param tv
     * @return
     */
    public static String getTextContent(TextView tv) {
        if (tv != null)
            return tv.getText().toString().trim();
        return "";
    }

    /**
     * 判断字符串内容是否是空，等不合法情况
     *
     * @param source
     * @return 如果是null 或 “” 等情况 返回null
     */
    public static boolean isEmpty(String source) {
        return "null".equalsIgnoreCase(source) || TextUtils.isEmpty(source);
    }

}
