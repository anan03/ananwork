package com.lvshandian.asktoask.module.interaction.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bm.library.Info;
import com.bm.library.PhotoView;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.lidroid.xutils.BitmapUtils;
import com.lvshandian.asktoask.BaseActivity;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.utils.PermisionUtils;
import com.lvshandian.asktoask.utils.PicUtils;
import com.lvshandian.asktoask.utils.TextUtils;
import com.lvshandian.asktoask.utils.ToastUtils;
import com.lvshandian.asktoask.view.TouchImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import java.io.File;
import java.util.ArrayList;

import butterknife.Bind;

/**
 * 互动展示九张图片
 * 可以轮播
 * on 2016/10/25.
 */
public class HuPicLunBoActivity extends BaseActivity {

    public static String PIC = "PIC";
    @Bind(R.id.vp_pic_nine)
    ViewPager vpPicNine;
    @Bind(R.id.tv_show_pics)
    TextView tvShowPics;
    @Bind(R.id.tv_save_photo)
    TextView tvSavePhoto;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    //界面列表
    private ArrayList<View> views;
    String pics[];
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hudong_pic_layout;
    }

    final int REQUECT_CODE_READ_SMS = 100;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_save_photo:


                PermisionUtils.newInstance().checkWriteStoragePermission(getWindow().getDecorView().findViewById(android.R.id.content), new PermisionUtils.OnPermissionGrantedLintener() {
                    @Override
                    public void permissionGranted() {
                        permision();
                    }
                });

                break;
            case R.id.iv_back:
                finish();
                break;
        }

    }


    //请求成功
    public void permision() {
        PermisionUtils.newInstance().checkReadStoragePermission(getWindow().getDecorView().findViewById(android.R.id.content), new PermisionUtils.OnPermissionGrantedLintener() {
            @Override
            public void permissionGranted() {
                ImageView iv = (ImageView) views.get(vpPicNine.getCurrentItem());
                Bitmap image = ((BitmapDrawable) iv.getDrawable()).getBitmap();
                if (!TextUtils.isEmpty(pics[vpPicNine.getCurrentItem()])) {
                    String[] sts = TextUtils.convertStr(pics[vpPicNine.getCurrentItem()]);
                    PicUtils.saveImageToGallery(HuPicLunBoActivity.this, image, sts[sts.length - 1]);
                } else {
                    ToastUtils.showSnackBar(snackView, "请打开权限");
                }
            }
        });
    }


    BitmapUtils bitmapUtils;

    @Override
    protected void initialized() {
        String pic = getIntent().getStringExtra(PIC);
        pics = PicUtils.getPic(pic);
        views = new ArrayList<>();
        Bitmap.Config bitmapConfig = Bitmap.Config.RGB_565;
        bitmapUtils = new BitmapUtils(HuPicLunBoActivity.this);
        bitmapUtils.configDefaultAutoRotation(true);
        bitmapUtils.configDefaultLoadFailedImage(R.drawable.no_pic);
        bitmapUtils.configDefaultLoadingImage(R.drawable.no_pic);

//        DisplayImageOptions options = new DisplayImageOptions.Builder().
//                cacheInMemory(true)
//                .showImageOnFail(R.mipmap.ic_launcher)
//                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
//                .displayer(new FadeInBitmapDisplayer(100))
//                .considerExifParams(true).showImageOnLoading(R.mipmap.ic_launcher)
//                .cacheOnDisk(true).bitmapConfig(bitmapConfig).build();

        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        //初始化引导图片列表
        for (int i = 0; i < pics.length; i++) {
//            TouchImageView iv = new TouchImageView(getContext());

            PhotoView iv = new PhotoView(getContext());
            iv.enable();
            iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
//            iv.setScaleType(ImageView.ScaleType.CENTER);
//            Info info = PhotoView.getImageViewInfo(ImageView);
//            iv.animaFrom(info);
            iv.setLayoutParams(mParams);
//            ImageLoader.getInstance().displayImage(pics[i], iv, options);
            bitmapUtils.display(iv, pics[i]);


            views.add(iv);

        }
        tvShowPics.setText("1/" + pics.length);
        vpPicNine.setAdapter(new ViewPagerAdapter(views));
        ivBack.setOnClickListener(this);


    }

    @Override
    protected void initListener() {
        tvSavePhoto.setOnClickListener(this);
        vpPicNine.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvShowPics.setText((position + 1) + "/" + views.size());


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("HuPicLunBo Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    public class ViewPagerAdapter extends PagerAdapter {

        //界面列表
        private ArrayList<View> views;

        public ViewPagerAdapter(ArrayList<View> views) {
            this.views = views;
        }

        //获得当前界面数
        @Override
        public int getCount() {
            if (views != null) {
                return views.size();
            }
            return 0;
        }

        //初始化position位置的界面
        @Override
        public Object instantiateItem(View view, int position) {

            ((ViewPager) view).addView(views.get(position), 0);

            return views.get(position);
        }

        //判断是否由对象生成界面
        @Override
        public boolean isViewFromObject(View view, Object arg1) {
            return (view == arg1);
        }

        //销毁position位置的界面
        @Override
        public void destroyItem(View view, int position, Object arg2) {
            ((ViewPager) view).removeView(views.get(position));
        }
    }


}
