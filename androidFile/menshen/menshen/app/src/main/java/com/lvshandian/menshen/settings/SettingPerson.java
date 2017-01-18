package com.lvshandian.menshen.settings;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.lvshandian.menshen.R;
import com.lvshandian.menshen.UrlBuilder;
import com.lvshandian.menshen.base.BaseActivity;
import com.lvshandian.menshen.bean.User;
import com.lvshandian.menshen.httprequest.HttpDatas;
import com.lvshandian.menshen.utils.JsonUtil;
import com.lvshandian.menshen.utils.LogUtils;
import com.lvshandian.menshen.utils.TextUtils;
import com.lvshandian.menshen.utils.UpdateImagerUtils;
import com.lvshandian.menshen.utils.XUtils;
import com.lvshandian.menshen.view.CircleImageView;
import com.lvshandian.menshen.view.DialogView;
import com.lvshandian.menshen.view.ToastUtils;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import org.xutils.common.util.LogUtil;

import java.io.File;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhang on 2016/11/6.
 * 创建个人信息修改页面
 */

public class SettingPerson extends BaseActivity {
    @Bind(R.id.view)
    View view;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.ll_parent_view)
    AutoLinearLayout llParentView;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.iv_head)
    CircleImageView ivHead;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.img)
    ImageView img;
    @Bind(R.id.rl_name)
    AutoRelativeLayout rlName;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.img1)
    ImageView img1;
    @Bind(R.id.rl_phone)
    AutoRelativeLayout rlPhone;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected int getLayoutId() {
        return R.layout.seeting_settingperson;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            int tagCode = data.getInt(HttpDatas.code);

            switch (msg.what) {

                /**
                 * 图片上传失败
                 */
                case 400:
                    ToastUtils.showSnackBar(snackView, "图片上传失败");

                    break;
                /**
                 * 图片上传成功
                 */
                case 200:
                    ToastUtils.showSnackBar(snackView, "图片上传成功");
                    if (photo != null) {
                        LogUtils.e("zzz111" + (String) msg.obj);

                        User user = JsonUtil.json2Bean((String) msg.obj, User.class);

                        LogUtils.e("zzz22" + user.toString());

                        XUtils.dropTable(User.class);
                        XUtils.addTable(user);
                        LogUtils.e("zzz" + user.toString());
                        Glide.with(mContext)
                                .load(user.getHeadPortraitImgUrl()).error(R.mipmap.wode_touxiang).placeholder(R.mipmap.wode_touxiang)
                                .into(ivHead);
                        //刷新首页界面的头像以及名称
                        Intent intent = new Intent();
                        intent.setAction("修改我的信息");
                        sendBroadcast(intent);
                    }
                    break;
            }
        }
    };

    @Override
    protected void initListener() {

        rlPhone.setOnClickListener(this);
        rlName.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        ivHead.setOnClickListener(this);
    }

    @Override
    protected void initialized() {

        if (null != XUtils.findAll(User.class) && null != XUtils.findAll(User.class).get(0)) {
            User user = (User) XUtils.findAll(User.class).get(0);

            if (TextUtils.isEmpty(user.getUserName())) {
                tvName.setText(user.getPhone());
            } else {
                tvName.setText(user.getUserName());
            }
            tvPhone.setText(user.getPhone());
            Glide.with(mContext)
                    .load(user.getHeadPortraitImgUrl()).error(R.mipmap.wode_touxiang).placeholder(R.mipmap.wode_touxiang)
                    .into(ivHead);
            LogUtils.e("zzz1" + user.toString());
        } else {
            ToastUtils.showSnackBar(snackView, "user不能为空");
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    final int CODE_CAMERA = 2001;
    final int READ_EXTERNAL_STORAGE = 2002;

    //请求成功
    @PermissionGrant(CODE_CAMERA)
    public void requestSdcardSuccess() {
        //权限请求
        MPermissions.requestPermissions(SettingPerson.this, READ_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    @PermissionDenied(CODE_CAMERA)
    public void requestSdcardFailed() {
        finish();
    }

    //请求成功
    @PermissionGrant(READ_EXTERNAL_STORAGE)
    public void requeststorage() {
        //权限请求
        new DialogView(getContext(), llParentView, "", "相机", "相册", "取消",
                new DialogView.MyCameraCallback() {
                    @Override
                    public void refreshCallback(int tag) {

                        switch (tag) {
                            //调用相机
                            case 1:
                                Intent openCameraIntent = new Intent(
                                        MediaStore.ACTION_IMAGE_CAPTURE);
                                tempUri = Uri.fromFile(new File(Environment
                                        .getExternalStorageDirectory(), "image.jpg"));
                                // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                                startActivityForResult(openCameraIntent, TAKE_PICTURE);
                                break;
                            //调用相册
                            case 2:
                                Intent openAlbumIntent = new Intent(
                                        Intent.ACTION_GET_CONTENT);
                                openAlbumIntent.setType("image/*");
                                startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                                break;
                            //取消
                            case 3:
                                break;
                        }
                    }
                });


    }

    @PermissionDenied(READ_EXTERNAL_STORAGE)
    public void requesttoage() {
        finish();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            //更改用户名
            case R.id.rl_name:
                //跳转到修改呢称界面
                Intent intent = new Intent(getContext(), SettingNameActivity.class);
                startActivityForResult(intent, 100);
                break;
            //更改头像
            case R.id.iv_head:
                //权限请求
                MPermissions.requestPermissions(SettingPerson.this, CODE_CAMERA, Manifest.permission.CAMERA);
                break;
            //返回键
            case R.id.iv_back:
                finish();
                break;
        }
    }

    private Bitmap photo = null;
    protected static final int TAKE_PICTURE = 110;
    protected static final int CHOOSE_PICTURE = 120;
    private static final int CROP_SMALL_PICTURE = 123;
    protected static Uri tempUri;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        LogUtils.e("=======");

        if (resultCode == 250) {
            if (data != null) {
                Bundle bundle = data.getExtras();
                String name = bundle.getString("name");
                tvName.setText(name);
                LogUtil.e("onActivityResult" + name);
            }
        }


        switch (requestCode) {
            case TAKE_PICTURE:
                startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
                break;
            case CHOOSE_PICTURE:

                if (null != data && null != data.getData()) {
                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                }

                break;
            case CROP_SMALL_PICTURE:
                if (data != null) {
                    setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                }
                break;


        }
        super.onActivityResult(requestCode, resultCode, data);


    }


    /**
     * 保存裁剪之后的图片数据
     *
     * @param
     * @param
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            photo = extras.getParcelable("data");
//            photo = UpdateImagerUtils.toRoundBitmap(photo, tempUri); // 这个时候的图片已经被处理成圆形的了
            uploadPic(photo);
        }
    }

    private void uploadPic(Bitmap bitmap) {

        String imagePath = UpdateImagerUtils.savePhoto(bitmap, Environment
                .getExternalStorageDirectory().getAbsolutePath(), String
                .valueOf(System.currentTimeMillis()));
        Log.e("imagePath", imagePath + "");
        if (imagePath != null) {
            try {
                UpdateImagerUtils.uploadFile(mContext, imagePath, UrlBuilder.serverUrl + UrlBuilder.PASSWORD_URL, mHandler);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
                .setName("SettingPerson Page") // TODO: Define a title for the content shown.
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
}
