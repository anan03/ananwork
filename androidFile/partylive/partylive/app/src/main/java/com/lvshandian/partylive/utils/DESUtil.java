package com.lvshandian.partylive.utils;


import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import java.io.IOException;
import java.security.SecureRandom;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

public class DESUtil {
    private static final String DES = "DES";
    private static final String KEY = "lUkQ%CI@6gJY7NGw";

    public DESUtil() {
    }

    public static void main(String[] args) throws Exception {
        String data = "18500303719";
        String key = "lUkQ%CI@6gJY7NGw";
        System.out.println(encrypt(data, key));
        System.out.println(decrypt(encrypt(data, key), key));
        System.out.println(decrypt("5ows1r6RErfTPHjlAebQ40cOWAJwB1lbe0O1EGJpGDApdNcdsqOjPMxyY4WPMFTi", "lUkQ%CI@6gJY7NGw"));
    }

    public static String encrypt(String data, String key) throws Exception {
        byte[] bt = encrypt(data.getBytes(), key.getBytes());
        String strs = (new BASE64Encoder()).encode(bt);
        return strs;
    }

    /**
     * 加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static String encrypt(String data) throws Exception {
        byte[] bt = encrypt(data.getBytes(), "lUkQ%CI@6gJY7NGw".getBytes());
        String strs = (new BASE64Encoder()).encode(bt);
        return strs;
    }

    public static String decrypt(String data, String key) throws IOException, Exception {
        if (data == null) {
            return null;
        } else {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] buf = decoder.decodeBuffer(data);
            byte[] bt = decrypt(buf, key.getBytes());
            return new String(bt);
        }
    }

    /**
     * 解密
     *
     * @param data
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data) throws IOException, Exception {
        if (data == null) {
            return null;
        } else {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] buf = decoder.decodeBuffer(data);
            byte[] bt = decrypt(buf, "lUkQ%CI@6gJY7NGw".getBytes());
            return new String(bt);
        }
    }

    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(1, securekey, sr);
        return cipher.doFinal(data);
    }

    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        SecureRandom sr = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(2, securekey, sr);
        return cipher.doFinal(data);
    }
}
