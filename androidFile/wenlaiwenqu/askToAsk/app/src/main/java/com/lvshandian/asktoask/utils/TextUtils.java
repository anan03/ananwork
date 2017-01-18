package com.lvshandian.asktoask.utils;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
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
     * 以逗号/字符串 得到字符数组
     *
     * @param str
     * @return
     */
    public static String[] convertStr(String str) {
        String[] strArray;
        strArray = str.split("/"); //拆分字符为"," ,然后把结果交给数组strArray
        return strArray;
    }

    /**
     * //判断是否收藏了
     *
     * @param uesrId 用户问题id
     * @param strzu  后端数据
     * @return
     */
    public static boolean isCollect(String uesrId, String strzu) {

        return Arrays.asList(convertStrToArray(strzu)).contains(uesrId);
    }

    /**
     * 某个字段是否包含该字符
     *
     * @param questId 短字符
     * @param str     长字符
     * @return
     */
    public static boolean isString(String questId, String str) {

        if (str.indexOf(questId) != -1) {
            return true;
        }
        return false;

    }

    /**
     * //判断是否点赞了
     *
     * @param questId
     * @param str
     * @return
     */
    public static boolean isParse(String questId, String str) {
        return Arrays.asList(convertStrToArray(str)).contains(questId);
    }


    /**
     * 判断该字符串中只有汉字
     *
     * @param
     * @return
     */
    public static boolean IsString(String txt) {

//        Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]+");
//        Matcher m = p.matcher(txt);

        return txt.matches("[\\u4e00-\\u9fa5]+");
//
//        if (m.matches()) {
//            return true;
//        }

//
//
//        char[] chars = str.toCharArray();
//        boolean isGB2312 = false;
//        for (int i = 0; i < chars.length; i++) {
//            byte[] bytes = ("" + chars[i]).getBytes();
//            if (bytes.length == 2) {
//                int[] ints = new int[2];
//                ints[0] = bytes[0] & 0xff;
//                ints[1] = bytes[1] & 0xff;
//                if (ints[0] >= 0x81 && ints[0] <= 0xFE && ints[1] >= 0x40 && ints[1] <= 0xFE) {
//                    isGB2312 = true;
//                    break;
//                }
//            }
//        }
//        return isGB2312;

    }

    //判断手机格式是否正确
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);

        return m.matches();
    }

    //判断email格式是否正确
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    /**
     * 截取字符串的前六位
     *
     * @param target
     * @return
     */
    public static String get6str(String target) {
        String str = target.substring(0, 6);
        return str;

    }


}
