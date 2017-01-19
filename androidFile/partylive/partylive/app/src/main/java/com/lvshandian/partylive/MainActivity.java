package com.lvshandian.partylive;

import android.content.Intent;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;

import com.android.volley.Request;
import com.lvshandian.partylive.base.BaseActivity;
import com.lvshandian.partylive.bean.QuitApp;
import com.lvshandian.partylive.bean.QuitLogin;
import com.lvshandian.partylive.httprequest.HttpDatas;
import com.lvshandian.partylive.httprequest.RequestCode;
import com.lvshandian.partylive.interf.ResultListener;
import com.lvshandian.partylive.moudles.mine.bean.PhotoBean;
import com.lvshandian.partylive.moudles.mine.bean.VideoBean;
import com.lvshandian.partylive.moudles.start.LoginActivity;
import com.lvshandian.partylive.moudles.start.LogoutHelper;
import com.lvshandian.partylive.utils.AliYunImageUtils;
import com.lvshandian.partylive.utils.Constant;
import com.lvshandian.partylive.utils.ImagerLoadUtils;
import com.lvshandian.partylive.utils.LogUtils;
import com.lvshandian.partylive.view.MainTab;
import com.lvshandian.partylive.wangyiyunxin.config.preference.Preferences;
import com.lvshandian.partylive.view.ToastUtils;
import com.lvshandian.partylive.widget.MyFragmentTabHost;
import com.maiml.wechatrecodervideolibrary.recoder.WechatRecoderActivity;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.zhy.autolayout.AutoRelativeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements
        TabHost.OnTabChangeListener,
        View.OnTouchListener {


    @Bind(R.id.realtabcontent)
    FrameLayout realtabcontent;
    @Bind(android.R.id.tabhost)
    MyFragmentTabHost tabhost;
    @Bind(R.id.activity_main)
    AutoRelativeLayout activityMain;
    private String headUrl = "";
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);

            switch (msg.what) {
                /**
                 * 图片上传成功
                 */
                case RequestCode.MY_PHOTO_UPLOAD_CODE:
                    ToastUtils.showSnackBar(snackView, "上传成功");
                    PhotoBean photoBean = new PhotoBean();
                    photoBean.setId("yes");
                    EventBus.getDefault().post(photoBean);
                    break;
                /**
                 * 上传视频成功
                 */
                case RequestCode.MY_VIDEO_UPLOAD:
                    VideoBean videoBean = new VideoBean();
                    videoBean.setId("yes");
                    EventBus.getDefault().post(videoBean);

                    ToastUtils.showSnackBar(snackView, "上传视频成功");

                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initialized() {
        //  EventBus.getDefault().register(this);
        LogUtils.i(System.currentTimeMillis() + "");

        tabhost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        if (android.os.Build.VERSION.SDK_INT > 10) {
            tabhost.getTabWidget().setShowDividers(0);
        }

        initTabs();

        // 中间按键图片触发
        tabhost.setCurrentTab(100);
        tabhost.setOnTabChangedListener(this);
        tabhost.setNoTabChangedTag("2");
        registerObservers(true);
    }

    private void initTabs() {
        MainTab[] tabs = MainTab.values();
        final int size = tabs.length;
        for (int i = 0; i < size; i++) {
            MainTab mainTab = tabs[i];

            TabHost.TabSpec tab = tabhost.newTabSpec(String.valueOf(mainTab.getResName()));
            View indicator = LayoutInflater.from(getApplicationContext()).inflate(R.layout.tab_indicator, null);
            ImageView tabImg = (ImageView) indicator.findViewById(R.id.tab_img);

            Drawable drawable = this.getResources().getDrawable(mainTab.getResIcon());
            tabImg.setImageDrawable(drawable);
            /*if (i != 1) {
                Drawable drawable = this.getResources().getDrawable(
                        mainTab.getResIcon());
                title.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null,
                        null);
                //indicator.setVisibility(View.INVISIBLE);
                // mTabHost.setNoTabChangedTag(getString(mainTab.getResName()));*//*
            }*/
            tab.setIndicator(indicator);
            tab.setContent(new TabHost.TabContentFactory() {

                @Override
                public View createTabContent(String tag) {
                    return new View(MainActivity.this);
                }
            });

            tabhost.addTab(tab, mainTab.getClz(), null);
            tabhost.getTabWidget().getChildAt(i).setOnTouchListener(this);
        }
    }

    @Subscribe
    public void onEventMainThread(QuitLogin quit) {
        //接收自设置页面的 退出登录
        defaultFinish();
    }

    private void registerObservers(boolean register) {
        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(userStatusObserver, register);
    }

    /**
     * 用户状态变化
     */
    Observer<StatusCode> userStatusObserver = new Observer<StatusCode>() {

        @Override
        public void onEvent(StatusCode code) {
            if (code.wontAutoLogin()) {
                kickOut(code);
            }
        }
    };

    /**
     * 退出登录（被挤下来了）
     *
     * @author sll
     * @time 2016/11/23 11:48
     */
    private void kickOut(StatusCode code) {
        Preferences.saveUserToken("");
        onLogout();
    }

    // 注销
    private void onLogout() {
        // 清理缓存&注销监听&清除状态
        LogoutHelper.logout();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(LoginActivity.KICK_OUT, true);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        //EventBus.getDefault().unregister(this);
        registerObservers(false);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public void onTabChanged(String tabId) {

    }

    protected static final int CHOOSE_PICTURE = 120;
    private static final int CROP_SMALL_PICTURE = 123;
    private Bitmap photo = null;
    protected static Uri tempUri;
    private String imageName = "";
    private String imagePath = "";
    private static final int REQ_CODE = 10001;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CHOOSE_PICTURE:
                if (data == null || data.getData() == null) {
                    return;
                }
                startPhotoZoom(data.getData());

                break;
            case CROP_SMALL_PICTURE:
                if (data != null) {
                    setPicToView(data);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);

        if (RESULT_OK == resultCode) {


            if (requestCode == REQ_CODE) {
                final String videoPath = data.getStringExtra(WechatRecoderActivity.VIDEO_PATH);


                MediaMetadataRetriever media = new MediaMetadataRetriever();
                media.setDataSource(videoPath);


                Bitmap bitmap = media.getFrameAtTime();
                String bitmapPhth = savePicture(bitmap);
                //先上传背景图
                AliYunImageUtils.newInstance().uploadImage(mContext, bitmapPhth, new ResultListener() {


                    @Override
                    public void onSucess(String data) {
                        //视频封面图片
                        //得到视频的路径
                        aliyunIdKeyVideo(videoPath, data);
                    }

                    @Override
                    public void onFaild() {

                    }


                });

//                play(videoPath);
            }

        }

    }

    private void aliyunIdKeyVideo(String videopath, final String photo) {


        AliYunImageUtils.newInstance().uploadVideo(mContext, videopath, new ResultListener() {
            @Override
            public void onSucess(String data) {
                LogUtils.e("videoPath--data" + data);

                ConcurrentHashMap map = new ConcurrentHashMap<>();
                map.put("userId", appUser.getId());
                map.put("thumbnailUrl", photo);//视频封面
                map.put("url", data);//视频路径
                httpDatas.getDataForJson("上传视频", Request.Method.POST, UrlBuilder.MY_VIDEO_UPLOAD, map, mHandler, RequestCode.MY_VIDEO_UPLOAD);

            }

            @Override
            public void onFaild() {

            }
        });

    }

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }


    /**
     * 请求阿里云id、key，成功后上传头像
     *
     * @author sll
     * @time 2016/11/15 13:59
     */
    private void aliyunIdKey() {


        AliYunImageUtils.newInstance().uploadImage(mContext, imagePath, new ResultListener() {
            @Override
            public void onSucess(String data) {

                ConcurrentHashMap map = new ConcurrentHashMap<>();
                map.put("userId", appUser.getId());
                LogUtils.e("headUrl" + data);
                map.put("url", data);
                httpDatas.getDataForJson("上传图库图片", Request.Method.POST, UrlBuilder.MY_PHOTO_UPLOAD, map, mHandler, RequestCode.MY_PHOTO_UPLOAD_CODE);


            }

            @Override
            public void onFaild() {

            }
        });


//        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
//        httpDatas.getDataForJson("请求阿里云id、key", Request.Method.GET, UrlBuilder.aliyun, map, mHandler, RequestCode.ALIYUN_TAG);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
//    protected void startPhotoZoom(Uri uri) {
//        if (uri == null) {
//            Log.i("tag", "The uri is not exist.");
//        }
//        tempUri = uri;
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        intent.setDataAndType(uri, "image/*");
//        // 设置裁剪
//        intent.putExtra("crop", "true");
//        // aspectX aspectY 是宽高的比例
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        // outputX outputY 是裁剪图片宽高
//        intent.putExtra("outputX", 200);
//        intent.putExtra("outputY", 200);
//        intent.putExtra("return-data", true);
//        startActivityForResult(intent, CROP_SMALL_PICTURE);
//    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param
     * @param
     */
    private Uri imageUri;

    /**
     * save the picture data
     *
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            if (photo != null) {
                // DemoApplication.instance.startProgressDialog(
                // PersonalInformationAct.this, "");
                String imgpath = savePicture(photo);
                // String imgpath = ImageUtils.savePicToSdcard1(photo);
                imageName = String.valueOf(System.currentTimeMillis()).substring(8);
                imagePath = imgpath;

                Log.e("imagePath", imagePath + "");
                if (imagePath != null) {
                    try {
                        aliyunIdKey();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
//                ImgUpload mImgUpload = new ImgUpload();
//                mImgUpload.imgUpLoad(imgpath, mHandler);

            }
        }

    }

    @SuppressLint("SdCardPath")
    public String savePicture(Bitmap bitmap) {
        String pictureName = "/mnt/sdcard/" + "partylive" + ".jpg";
        File file = new File(pictureName);
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

            out.flush();
            out.close();
            return pictureName;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pictureName;
    }
//    protected void setImageToView(Intent data) {
//
//
//        Bundle extras = data.getExtras();
//        if (extras != null) {
//            photo = extras.getParcelable("data");
//            uploadPic(photo);
//        }
//    }


    private void uploadPic(Bitmap bitmap) {
        imageName = String.valueOf(System.currentTimeMillis()).substring(8);
        imagePath = ImagerLoadUtils.savePhoto(bitmap, Environment
                .getExternalStorageDirectory().getAbsolutePath(), String
                .valueOf(System.currentTimeMillis()));
        Log.e("imagePath", imagePath + "");
        if (imagePath != null) {
            try {
                aliyunIdKey();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private long firstPressed;
    private long dowableClick = 1000 * 2;//连续返回两次退出应用

    @Override
    public void onBackPressed() {
        long now = System.currentTimeMillis();
        if ((now - firstPressed <= dowableClick) && firstPressed != 0) {
            EventBus.getDefault().post(new QuitApp());
            super.onBackPressed();
            System.exit(0);
        } else {
            showToast("再按一次退出应用");
            firstPressed = now;
        }
    }
}
