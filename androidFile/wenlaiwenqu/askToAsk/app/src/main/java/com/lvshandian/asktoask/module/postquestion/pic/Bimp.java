package com.lvshandian.asktoask.module.postquestion.pic;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Bimp {
    public static int max = 0;
    public static boolean act_bool = true;
    public static List<Bitmap> bmp = new ArrayList<Bitmap>();

    // 图片sd地址 上传服务器时把图片调用下面方法压缩后 保存到临时文件夹 图片压缩后小于100KB，失真度不明显
    public static List<String> drr = new ArrayList<String>();
//
//    public static Bitmap revitionImageSize(String path) throws IOException {
//        BufferedInputStream in = new BufferedInputStream(new FileInputStream(
//                new File(path)));
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeStream(in, null, options);
//        in.close();
//        int i = 0;
//        Bitmap bitmap = null;
//        while (true) {
//            if ((options.outWidth >> i <= 1000)
//                    && (options.outHeight >> i <= 1000)) {
//                in = new BufferedInputStream(
//                        new FileInputStream(new File(path)));
//                options.inSampleSize = (int) Math.pow(2.0D, i);
//                options.inJustDecodeBounds = false;
//                bitmap = BitmapFactory.decodeStream(in, null, options);
//                break;
//            }
//            i += 1;
//        }
//        return bitmap;
//    }

    /**
     * 根据路径获得突破并压缩返回bitmap用于显示
     *
     * @return
     */
//  /**
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    /**
     * 根据路径获得突破并压缩返回bitmap用于显示
     *
     * @return
     */
    @SuppressWarnings("static-access")
    public static Bitmap revitionImageSize(String filePath) {


        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }
}
