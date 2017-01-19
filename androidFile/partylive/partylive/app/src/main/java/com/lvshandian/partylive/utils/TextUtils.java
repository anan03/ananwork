package com.lvshandian.partylive.utils;

import android.widget.TextView;

import java.util.Arrays;

/**
 * Created zhang  on 2016/9/18.
 * 字符串的一些常用的操作 工具类
 */
public class TextUtils {
    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        if (null == str || str.equals("") || str.isEmpty() || str.equals("null")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 返回TextView 和Edittext 控件的内容且不可为空
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
     * 以逗号窃取字符串 得到字符数组
     *
     * @param str
     * @return
     */
    public static String[] convertStrToArray(String str) {
        String[] strArray;
        strArray = str.split(","); //拆分字符为"," ,然后把结果交给数组strArray
        return strArray;
    }


    /**
     * 某个字段是否包含该字符
     *
     * @param questId 短字符
     * @param str     长字符
     * @return
     */
    public static boolean isString(String questId, String str) {

        return Arrays.asList(convertStrToArray(str)).contains(questId);
    }


}
