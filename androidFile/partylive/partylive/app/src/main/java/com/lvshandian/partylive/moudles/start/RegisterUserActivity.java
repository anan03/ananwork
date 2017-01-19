package com.lvshandian.partylive.moudles.start;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.lvshandian.partylive.MainActivity;
import com.lvshandian.partylive.R;
import com.lvshandian.partylive.UrlBuilder;
import com.lvshandian.partylive.base.BaseActivity;
import com.lvshandian.partylive.bean.AppUser;
import com.lvshandian.partylive.httprequest.HttpDatas;
import com.lvshandian.partylive.httprequest.RequestCode;
import com.lvshandian.partylive.interf.ResultListener;
import com.lvshandian.partylive.utils.AliYunImageUtils;
import com.lvshandian.partylive.utils.CacheUtils;
import com.lvshandian.partylive.utils.DESUtil;
import com.lvshandian.partylive.utils.FastBlur;
import com.lvshandian.partylive.utils.LogUtils;
import com.lvshandian.partylive.utils.TextUtils;
import com.lvshandian.partylive.utils.ToastUtil;
import com.lvshandian.partylive.utils.UpdateImagerUtils;
import com.lvshandian.partylive.wangyiyunxin.config.DemoCache;
import com.lvshandian.partylive.wangyiyunxin.config.preference.Preferences;
import com.lvshandian.partylive.wangyiyunxin.config.preference.UserPreferences;
import com.lvshandian.partylive.widget.AvatarView;
import com.netease.nim.uikit.cache.DataCacheManager;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.RequestCallbackWrapper;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.uinfo.UserService;
import com.netease.nimlib.sdk.uinfo.constant.UserInfoFieldEnum;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.zhy.autolayout.AutoLinearLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 修改用户信息，注册后完善信息
 * Created by sll on 2016/11/14.
 */
public class RegisterUserActivity extends BaseActivity {
    @Bind(R.id.choise_user_head)
    AvatarView choiseUserHead;
    @Bind(R.id.ed_register_nick)
    EditText edRegisterNick;
    @Bind(R.id.ed_register_gender)
    TextView edRegisterGender;
    @Bind(R.id.ed_register_private_costs)
    EditText edRegisterPrivateCosts;
    @Bind(R.id.ed_register_signature)
    EditText edRegisterSignature;
    @Bind(R.id.btn_change_user)
    Button btnChangeUser;
    @Bind(R.id.all_layout)
    AutoLinearLayout allLayout;
    @Bind(R.id.register_layout)
    AutoLinearLayout registerLayout;

    //用于头像地址
    private String headUrl;
    private String imageName;
    private String imagePath;

    private AbortableFuture<LoginInfo> loginRequest;
    private String account = null;
    private String token = null;

    private PopupWindow popupWindow;


    private Handler mHandler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);

            switch (msg.what) {
                //关注请求接收数据
                case RequestCode.USER_TAG:
                    appUser = JSON.parseObject(json, AppUser.class);
                    LogUtils.e("修改个人信息: " + json);
                    //存储用户信息
                    CacheUtils.saveObject(RegisterUserActivity.this, appUser, CacheUtils.USERINFO);
                    showToast("修改成功");
                    sendUserToWangYi();
                    loginWangYi();
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register_user;
    }

    @Override
    protected void initListener() {
        choiseUserHead.setOnClickListener(this);
        btnChangeUser.setOnClickListener(this);
        edRegisterGender.setOnClickListener(this);
    }

    @Override
    protected void initialized() {
        initTitle("", "修改个人资料", "");
        if (!getIntent().getBooleanExtra("isRegister", false))
            initappUser();
        initPopView();
    }

    /**
     * 初始个人信息
     */
    private void initappUser() {
        if (appUser != null) {
            edRegisterNick.setText(appUser.getNickName());
            if (appUser.getGender().equals("1"))
                edRegisterGender.setText("男");
            if (appUser.getGender().equals("0"))
                edRegisterGender.setText("女");
            edRegisterPrivateCosts.setText(appUser.getPayForVideoChat());
            edRegisterSignature.setText(appUser.getSignature());

            String picUrl = appUser.getPicUrl();
            LogUtils.e("picUrl: " + picUrl);
            ImageLoader.getInstance().loadImage(picUrl, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {

                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    choiseUserHead.setImageBitmap(bitmap);
                    bitmap = FastBlur.doBlur(bitmap, 5, true);
                    BitmapDrawable drawable = new BitmapDrawable(bitmap);
                    allLayout.setBackground(drawable);
                }

                @Override
                public void onLoadingCancelled(String s, View view) {

                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_titlebar_left:
                //返回
                defaultFinish();
                break;
            case R.id.ed_register_gender:
                //选择性别
                //显示窗口
                popupWindow.showAtLocation(registerLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
                break;
            case R.id.choise_user_head:
                //修改头像，上传到阿里云获得返回地址
                requestPermission(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                selectPhoto();
                break;
            case R.id.btn_change_user:
                String nick = edRegisterNick.getText().toString();
                String gender = edRegisterGender.getText().toString();
                String payForVideoChat = edRegisterPrivateCosts.getText().toString();
                String signature = edRegisterSignature.getText().toString();
                if (getIntent().getBooleanExtra("isRegister", false))
                    if (TextUtils.isEmpty(headUrl)) {
                        ToastUtil.makeToast("请修改头像");
                        return;
                    }
                if (TextUtils.isEmpty(nick)) {
                    ToastUtil.makeToast("请填写昵称");
                    return;
                }
                if (TextUtils.isEmpty(gender) || !(gender.equals("男") || gender.equals("女"))) {
                    ToastUtil.makeToast("请填写男或女");
                    return;
                }
                if (TextUtils.isEmpty(payForVideoChat)) {
                    ToastUtil.makeToast("请填写视频私聊费用");
                    return;
                }
                if (userState())
                    changeappUser(headUrl, nick, signature, gender, payForVideoChat);
                break;
        }
    }

    /**
     * 修改用户信息
     * "id": "1"
     * "picUrl": null, 头像
     * "nickName": null, 昵称
     * "signature": null, 签名
     * "gender": null,性别
     * "payForVideoChat": null,视频私聊费用
     *
     * @author sll
     * @time 2016/11/15 13:59
     */
    private void changeappUser(String picUrl, String nickName, String signature, String gender, String payForVideoChat) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("id", appUser.getId());
        if (!TextUtils.isEmpty(picUrl))
            map.put("picUrl", picUrl);
        map.put("nickName", nickName);
        map.put("signature", signature);
        if (gender.equals("男"))
            map.put("gender", "1");
        if (gender.equals("女"))
            map.put("gender", "0");
        map.put("payForVideoChat", payForVideoChat);
        httpDatas.getDataForJson("修改用户信息", Request.Method.POST, UrlBuilder.user, map, mHandler2, RequestCode.USER_TAG);
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
                headUrl = data;
                LogUtils.e("data1" + data);
            }

            @Override
            public void onFaild() {

            }
        });
//        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
//        httpDatas.getDataForJson("请求阿里云id、key", Request.Method.GET, UrlBuilder.aliyun, map, mHandler2, RequestCode.ALIYUN_TAG);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void selectPhoto() {
        imageName = String.valueOf(System.currentTimeMillis()).substring(8);
        destUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath(), imageName + ".png"));
        final Dialog dialog = new Dialog(this, R.style.homedialog);
        final View view = View.inflate(this, R.layout.dialog_user_head, null);
        final LinearLayout cancelHeadLinear = (LinearLayout) view.findViewById(R.id.cancel_head_linear);
        //拍照
        view.findViewById(R.id.photo_head_linear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                tempUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), imageName + ".png"));
                // 指定照片保存路径（SD卡），imageName + ".png"为一个临时文件，每次拍照后这个图片都会被替换
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                startActivityForResult(openCameraIntent, TAKE_PICTURE);
                dialog.dismiss();
            }
        });
        //相册
        view.findViewById(R.id.album_head_linear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                openAlbumIntent.setType("image/*");
                startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                dialog.dismiss();
            }
        });
        //取消
        cancelHeadLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        dialog.show();
    }

    private Bitmap photo = null;
    protected static final int TAKE_PICTURE = 110;
    protected static final int CHOOSE_PICTURE = 120;
    private static final int CROP_SMALL_PICTURE = 123;
    protected static Uri tempUri;
    protected Uri destUri = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        LogUtils.e("onActivityResult");

//        if (resultCode == 250) {
//            if (data != null) {
//                Bundle bundle = data.getExtras();
//                String name = bundle.getString("name");
//            }
//        }
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
//                if (data != null) {
//                    setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
//                }
                try {
                    photo = BitmapFactory.decodeStream(getContentResolver().openInputStream(destUri));
                    uploadPic(photo);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
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
//        Bundle extras = data.getExtras();
//        if (extras != null) {
//            photo = extras.getParcelable("data");
//            photo = UpdateImagerUtils.toRoundBitmap(photo, tempUri); // 这个时候的图片已经被处理成圆形的了
//            uploadPic(photo);
//        }
    }

    private void uploadPic(Bitmap bitmap) {
        choiseUserHead.setImageBitmap(bitmap);

        //"avater/" + appUser.getId() + "/" +
        // imageName = appUser.getUserName();
        LogUtils.e("imageName", imageName);
        imagePath = UpdateImagerUtils.savePhoto(bitmap, Environment.getExternalStorageDirectory().getAbsolutePath(), imageName);
//        imagePath = UpdateImagerUtils.savePhoto(bitmap, "/mnt/sdcard/", imageName);
        LogUtils.e("imagePath", imagePath + "");
        if (imagePath != null) {
            try {
                aliyunIdKey();
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
            LogUtils.i("tag", "The uri is not exist.");
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
        intent.putExtra(MediaStore.EXTRA_OUTPUT, destUri);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    /**
     * 登录网易云信
     *
     * @author sll
     * @time 2016/11/16 13:39
     */
    private void loginWangYi() {
        // 云信只提供消息通道，并不包含用户资料逻辑。开发者需要在管理后台或通过服务器接口将用户帐号和token同步到云信服务器。
        // 在这里直接使用同步到云信服务器的帐号和token登录。
        // 这里为了简便起见，demo就直接使用了密码的md5作为token。
        // 如果开发者直接使用这个demo，只更改appkey，然后就登入自己的账户体系的话，需要传入同步到云信服务器的token，而不是用户密码。
        try {
            account = DESUtil.decrypt(appUser.getNeteaseAccount());
            token = DESUtil.decrypt(appUser.getNeteaseToken());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 登录
        loginRequest = NIMClient.getService(AuthService.class).login(new LoginInfo(account, token));
        loginRequest.setCallback(new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {
                LogUtils.i("WangYi", "login success");

                onLoginDone();
                DemoCache.setAccount(account);
                saveLoginInfo(account, token);

                // 初始化消息提醒
                NIMClient.toggleNotification(UserPreferences.getNotificationToggle());

                // 初始化免打扰
                if (UserPreferences.getStatusConfig() == null) {
                    UserPreferences.setStatusConfig(DemoCache.getNotificationConfig());
                }
                NIMClient.updateStatusBarNotificationConfig(UserPreferences.getStatusConfig());

                // 构建缓存
                DataCacheManager.buildDataCacheAsync();

                // 进入主界面
//                MainActivity.start(LoginActivity.this, null);
                gotoActivity(MainActivity.class, true);
            }

            @Override
            public void onFailed(int code) {
                onLoginDone();
                if (code == 302 || code == 404) {
                    Toast.makeText(RegisterUserActivity.this, R.string.login_failed, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterUserActivity.this, "登录失败: " + code, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onException(Throwable exception) {
                Toast.makeText(RegisterUserActivity.this, R.string.login_exception, Toast.LENGTH_LONG).show();
                onLoginDone();
            }
        });
    }

    private void onLoginDone() {
        loginRequest = null;
    }

    private void saveLoginInfo(final String account, final String token) {
        Preferences.saveUserAccount(account);
        Preferences.saveUserToken(token);
    }

    private void sendUserToWangYi() {
        Map<UserInfoFieldEnum, Object> fields = new HashMap<>(1);
        fields.put(UserInfoFieldEnum.Name, appUser.getNickName());
        fields.put(UserInfoFieldEnum.AVATAR, appUser.getPicUrl());
        NIMClient.getService(UserService.class).updateUserInfo(fields)
                .setCallback(new RequestCallbackWrapper<Void>() {

                    @Override
                    public void onResult(int code, Void aVoid, Throwable throwable) {
                        LogUtils.i("WangYi", "real login, code=" + code);
                        if (code == ResponseCode.RES_SUCCESS) {
                            LogUtils.i("WangYi", "上传昵称头像成功");
                        } else {
                            LogUtils.i("WangYi", "上传昵称头像失败");
                        }
                    }
                });
    }

    /**
     * 选择popWindow
     * edRegisterGender
     */
    private void initPopView() {
        View popView = View.inflate(this, R.layout.pop_select_gender, null);
        TextView tvSelectMale = (TextView) popView.findViewById(R.id.tv_select_male);
        TextView tvSelectFemale = (TextView) popView.findViewById(R.id.tv_select_female);
        TextView tvCancel = (TextView) popView.findViewById(R.id.tv_cancel);
        if (tvSelectMale != null) {
            //男
            tvSelectMale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (popupWindow.isShowing()) {
                        edRegisterGender.setText("男");
                        popupWindow.dismiss();
                    }
                }
            });
        }
        if (tvSelectFemale != null) {
            //女
            tvSelectFemale.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (popupWindow.isShowing()) {
                        edRegisterGender.setText("女");
                        popupWindow.dismiss();
                    }
                }
            });
        }
        if (tvCancel != null) {
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (popupWindow.isShowing()) {
                        popupWindow.dismiss();
                    }
                }
            });
        }
        int width = FrameLayout.LayoutParams.MATCH_PARENT;
        int height = FrameLayout.LayoutParams.MATCH_PARENT;
        popupWindow = new PopupWindow(popView, width, height);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.alpha_half)));// 颜色设置为透明
    }
}
