package com.lvshandian.partylive.utils;

import java.security.MessageDigest;

/**
 * Created by zhang on 2016/10/12.
 */
public class EncryptUtils {


    /**
     * 16位的加密，是32位加密后的截取 8, 24
     *
     * @param sourceText 源文本
     * @return 加密后的文本
     */
    public static String md5(String sourceText) {
        int i;
        StringBuffer buf = new StringBuffer();
        try {
            //生成实现指定摘要算法的 MessageDigest 对象。
            MessageDigest md = MessageDigest.getInstance("MD5");
            //使用指定的字节数组更新摘要。
            md.update(sourceText.getBytes());
            //通过执行诸如填充之类的最终操作完成哈希计算。
            byte b[] = md.digest();
            //生成具体的md5密码到buf数组
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
            buf = new StringBuffer();
        }
        return buf.toString().trim();
    }

}
