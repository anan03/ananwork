package com.lvshandian.nan;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Environment;

import com.lvshandian.myapplication.R;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.io.File;

/**
 * Created by zhang on 2016/12/17.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化Imagload
        initImageLoader();
    }

    public void initImageLoader() {
        //默认的方式
//ImageLoaderConfiguration configuration=ImageLoaderConfiguration.createDefault(this);

//sdcard的路径
        String sdPath = Environment.getExternalStorageDirectory().getPath();

//自定义的方式
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(480, 800)//内存缓存文件的最大长宽
                .discCacheExtraOptions(480, 800, null)//本地缓存的详细信息
                .threadPoolSize(3)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)//设置当前线程的优先级
                .tasksProcessingOrder(com.nostra13.universalimageloader.core.assist.QueueProcessingType.FIFO)//设置任务的处理顺序
                .denyCacheImageMultipleSizesInMemory()//防止内存中图片重复
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))//设置自己的内存缓存大小   2M
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13)//内存缓存百分比
                .discCache(new UnlimitedDiscCache(new File(sdPath + "/huang/image1")))//设置缓存的图片在sdcard中的位置
                .discCacheSize(50 * 1024 * 1024)//硬盘缓存大小    50M
                .discCacheFileCount(100)//硬盘缓存文件个数
                .discCacheFileNameGenerator(new com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator())//md5加密的方式，或new HashCodeFileNameGenerator()
                .imageDownloader(new com.nostra13.universalimageloader.core.download.BaseImageDownloader(this))
                .imageDecoder(new BaseImageDecoder(true))//图片解码
                .defaultDisplayImageOptions(getOption())//是否使用默认的图片加载配置，null表示不使用
                .writeDebugLogs()
                .build();

//初始化
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(configuration);
    }

    public DisplayImageOptions getOption() {
        DisplayImageOptions option = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_launcher)//设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.ic_launcher)//设置图片uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.ic_launcher)//设置图片加载或解码过程中发生错误显示的图片
                .resetViewBeforeLoading(false)//设置图片在加载前是否重置、复位
//.delayBeforeLoading(1000)//下载前的延迟时间
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)//设置下载的图片是否缓存在sd卡中
                .considerExifParams(false)//思考可交换的参数
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
                .displayer(new RoundedBitmapDisplayer(40))//设置图片的圆角半径
                .displayer(new FadeInBitmapDisplayer(0))//设置图片显示的透明度过程的时间
                .build();

        return option;
    }

}
