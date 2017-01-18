package com.lvshandian.asktoask.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.lvshandian.asktoask.module.postquestion.pic.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片处理
 */
public class PicUtils {

    //获得图片数组
    public static String[] getPic(String str) {
        String[] pic = null;
        if (com.lvshandian.asktoask.utils.TextUtils.isEmpty(str)) {
            return pic;
        } else {
            return com.lvshandian.asktoask.utils.TextUtils.convertStrToArray(str);

        }

    }

    /**
     * 从网络上获取到bitmap对象
     *
     * @param urlpath
     * @return
     */

    public static Bitmap getBitmapFromUrl(String urlpath) {
        byte[] result = null;
        URL url = null;
        try {
            url = new URL(urlpath);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            int max = conn.getContentLength();
            InputStream is = conn.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            result = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return BitmapFactory.decodeByteArray(result, 0, result.length);
    }

    /**
     * 压缩bitmap
     *
     * @param image
     * @return
     */
    public static Bitmap comPress(Bitmap image) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, out);
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        int be = 2;
        newOpts.inSampleSize = be;
        ByteArrayInputStream isBm = new ByteArrayInputStream(out.toByteArray());
        out.close();
        return BitmapFactory.decodeStream(isBm, null, null);
    }

    /**
     * 保存图片到手机
     *
     * @param context
     * @param bmp
     */

    public static void saveImageToGallery(Context context, Bitmap bmp) {
        String fileName = Environment.getExternalStorageDirectory().getPath() + "/wlwq/" + System.currentTimeMillis() + ".jpg";
        L.d("aaa", "九张图保存图片路径和名字" + fileName);
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            bmp.recycle();
        } catch (FileNotFoundException e) {
//            ToastUtils.showToast(context, "文件未发现");
            e.printStackTrace();
        } catch (IOException e) {
//            ToastUtils.showToast(context, "保存出错了...");
            e.printStackTrace();
        } catch (Exception e) {
//            ToastUtils.showToast(context, "保存出错了...");
            e.printStackTrace();
        }

        // 最后通知图库更新
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), fileName, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
    }

    /**
     * 参数一：上下文
     * 参数二：图片
     * 参数三：网络地址最后一部分；
     *
     * @param context
     * @param bmp
     * @param fileNa1me
     */

    public static void saveImageToGallery(Context context, Bitmap bmp,
                                          String fileNa1me) {
        if (bmp == null) {
            Toast.makeText(context, "保存失败", Toast.LENGTH_SHORT).show();
            return;
        }
        // 首先保存图片
        File appDir = new File(Environment.getExternalStorageDirectory(),
                "DeskTop/photo");
        if (deleteLastFromFloder(appDir.toString(), fileNa1me)) {
            //图片以保存
            Toast.makeText(context, "已保存", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!appDir.exists()) {
            appDir.mkdirs();//创建文件夹
        }
        File file = new File(appDir, fileNa1me + System.currentTimeMillis());
        String string = file.getPath();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
        L.d("aaa", "文件地址" + string);
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, out);

            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(), string, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                Uri.parse("file://" + "DeskTop/photo")));
        MediaScannerConnection.scanFile(context,
                new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {

                    }
                });
    }

    /**
     * 保存图片到sd中
     *
     * @param bitmap
     */


    public static String uploadPic(Bitmap bitmap) {
        String imagelifePath = UpdateImagerUtils.savePhoto(bitmap, Environment
                .getExternalStorageDirectory().getAbsolutePath(), String
                .valueOf(System.currentTimeMillis()));
        return imagelifePath;
    }


    /**
     * Save Bitmap to a file.保存图片到SD卡。
     *
     * @param
     * @param
     * @return
     * @throws IOException
     */
    public static void saveMyBitmap(Bitmap mBitmap, String bitName) {
        File f = new File("/sdcard/" + bitName + ".jpg");
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 拍照从手机相册获取到图片路径
     *
     * @param uri
     * @return
     */
    public static String getRealFilePath(final Uri uri, Context context) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public static boolean deleteLastFromFloder(String path, String strPath) {
        boolean success = false;
        try {
            ArrayList<File> images = new ArrayList<File>();
            getFiles(images, path);
            File latestSavedImage = images.get(0);
            if (latestSavedImage.exists()) {
                for (int i = 1; i < images.size(); i++) {
                    File nextFile = images.get(i);
                    if (TextUtils.isString(strPath.trim(), nextFile.toString().trim())) {
                        L.d("aaa", "图片文件名称" + nextFile.toString());
                        return true;
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public static void getFiles(ArrayList<File> fileList, String path) {
        File[] allFiles = new File(path).listFiles();
        for (int i = 0; i < allFiles.length; i++) {
            File file = allFiles[i];
            if (file.isFile()) {
                fileList.add(file);
            } else if (!file.getAbsolutePath().contains(".thumnail")) {
                getFiles(fileList, file.getAbsolutePath());
            }
        }
    }
}
