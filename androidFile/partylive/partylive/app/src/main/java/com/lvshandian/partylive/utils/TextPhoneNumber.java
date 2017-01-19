package com.lvshandian.partylive.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证手机号码
 * <p/>
 * 验证真实姓名
 * <p/>
 * 验证邮箱格式
 *
 * @author Administrator
 */
public class TextPhoneNumber {
    public static boolean check(String str, String regex) {
        boolean flag;
        try {
            Pattern e = Pattern.compile(regex);
            Matcher matcher = e.matcher(str);
            flag = matcher.matches();
        } catch (Exception var4) {
            flag = false;
        }

        return flag;
    }

    public static boolean isPhone(String cellphone) {
        // String regex = "^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8]))//d{8}$";
        String regex = "^((13[0-9])|(14[5-7])|(15([0-9]))|(17([0-9]))|(18[0-9]))\\d{8}$";
        return check(cellphone, regex);
    }

    /**
     * 判定输入汉字
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 检测String是否全是中文
     *
     * @param name
     * @return
     */
    public static boolean checkNameChese(String name) {
        boolean res = true;
        char[] cTemp = name.toCharArray();
        for (int i = 0; i < name.length(); i++) {
            if (!isChinese(cTemp[i])) {
                res = false;
                break;
            }
        }
        return res;
    }

    /**
     * 判断email格式是否正确
     * true 正确
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

}
