package com.lvshandian.nan.loadimag.Utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lvshandian.myapplication.R;

import java.io.File;

/**
 * Created by zhang on 2016/12/17.
 * Glide加载图片
 * 使用步骤
 * 1.加入Glide；
 * 2.直接调用类库方法
 */

public class GlideUtils {
    /**
     * (6)显示gif动画:
     * Glide.with(context).load(gifUrl).asGif() // 判断加载的url资源是否为gif格式的资源
     * .error(R.drawable.full_cake).into(imageViewGif);
     * <p>
     * (7)显示本地视:
     * String filePath = "/storage/emulated/0/Pictures/example_video.mp4";
     * Glide.with(context).load(Uri.fromFile(new File(filePath)))
     * .into(imageViewGifAsBitmap);
     */
    //图片加载
    public static void load(Context context, String Url, ImageView imageView) {

        Glide.with(context).load(Url)//加载图片
                .placeholder(R.mipmap.ic_launcher)//网络图片加载中的图片
                .error(R.mipmap.ic_launcher)//加载失败时的图片
                .fitCenter()//设置图片的展示类型：填充
                .into(imageView);//图片控件
    }

    //加载Gif图片
    public static void loadGif(Context context, String Url, ImageView imageView) {

        Glide.with(context).load(Url)//加载图片
                .asGif()
                .placeholder(R.mipmap.ic_launcher)//网络图片加载中的图片
                .error(R.mipmap.ic_launcher)//加载失败时的图片
                .fitCenter()//设置图片的展示类型：填充
                .into(imageView);//图片控件
    }

    //加载视频
    public static void loadVideo(Context context, String Url, ImageView imageView) {

        Glide.with(context).load(Uri.fromFile(new File(Url)))//加载图片
                .asGif()
                .placeholder(R.mipmap.ic_launcher)//网络图片加载中的图片
                .error(R.mipmap.ic_launcher)//加载失败时的图片
                .fitCenter()//设置图片的展示类型：填充
                .into(imageView);//視頻控件
    }


}
