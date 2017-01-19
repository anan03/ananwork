package com.lvshandian.partylive.moudles.start;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.lvshandian.partylive.MainActivity;
import com.lvshandian.partylive.MyApplication;
import com.lvshandian.partylive.R;
import com.lvshandian.partylive.UrlBuilder;
import com.lvshandian.partylive.base.BaseActivity;
import com.lvshandian.partylive.base.BaseUMAuthListener;
import com.lvshandian.partylive.base.Constant;
import com.lvshandian.partylive.base.CustomStringCallBack;
import com.lvshandian.partylive.bean.AppUser;
import com.lvshandian.partylive.httprequest.HttpDatas;
import com.lvshandian.partylive.httprequest.RequestCode;
import com.lvshandian.partylive.moudles.mine.bean.LoginFrom;
import com.lvshandian.partylive.utils.CacheUtils;
import com.lvshandian.partylive.utils.DESUtil;
import com.lvshandian.partylive.utils.LogUtils;
import com.lvshandian.partylive.utils.MD5Utils;
import com.lvshandian.partylive.utils.TextPhoneNumber;
import com.lvshandian.partylive.view.LoadingDialog;
import com.lvshandian.partylive.wangyiyunxin.config.DemoCache;
import com.lvshandian.partylive.wangyiyunxin.config.preference.Preferences;
import com.lvshandian.partylive.wangyiyunxin.config.preference.UserPreferences;
import com.netease.nim.uikit.cache.DataCacheManager;
import com.netease.nim.uikit.common.ui.dialog.EasyAlertDialogHelper;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.ClientType;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.squareup.okhttp.MediaType;
/*import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;*/
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 登录页面
 * Created by 张振 on 2016/11/8.
 */

public class LoginActivity extends BaseActivity {
    @Bind(R.id.ed_login_phone)
    EditText edLoginPhone;
    @Bind(R.id.ed_login_password)
    EditText edLoginPassword;
    @Bind(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.btn_register)
    Button btnRegister;
    @Bind(R.id.iv_qqlogin)
    ImageView ivQqlogin;
    @Bind(R.id.iv_wxlogin)
    ImageView ivWxlogin;
    private Handler mHandler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);

            switch (msg.what) {
                //关注请求接收数据
                case RequestCode.LOGIN_TAG:
                    loginSucess(json);
                    LogUtils.e("用户信息: " + json);
                    break;

            }
        }
    };
    private UMSocialService mController;
    private LoadingDialog mLoading;

    /**
     * 登录成功
     *
     * @param json
     */
    private void loginSucess(String json) {
        appUser = JSON.parseObject(json, AppUser.class);
        String passWord = edLoginPassword.getText().toString().trim();
        if (!TextUtils.isEmpty(passWord)) {
            //第三方登录没有输入密码,所以只有在输入用户名密码登录时才不为Empty
            passWord = MD5Utils.md5(passWord);
            appUser.setPassword(passWord);
        }
        //存储用户信息
        CacheUtils.saveObject(LoginActivity.this, appUser, CacheUtils.USERINFO);
        loginWangYi();
    }

    private AbortableFuture<LoginInfo> loginRequest;
    private String account = null;
    private String token = null;

    public static final String KICK_OUT = "KICK_OUT";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initListener() {
        btnLogin.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        ivQqlogin.setOnClickListener(this);
        ivWxlogin.setOnClickListener(this);
    }

    @Override
    protected void initialized() {
        initTitle(null, "登录", "");
        //请求基础权限，存储
        requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);

        onParseIntent();
        mLoading = new LoadingDialog(this);
        mController = UMServiceFactory.getUMSocialService("com.umeng.share");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_register:
                gotoActivity(RegisterActivity.class, false);
                break;
            case R.id.btn_login:
                if (TextUtils.isEmpty(edLoginPhone.getText().toString().trim()) || !TextPhoneNumber.isPhone(edLoginPhone.getText().toString())) {
                    showToast("用户名不正确");
                    return;
                }
                if (TextUtils.isEmpty(edLoginPassword.getText().toString().trim())) {
                    showToast("密码不能为空");
                    return;
                }
                login(edLoginPhone.getText().toString(), edLoginPassword.getText().toString().trim());
//                gotoActivity(MainActivity.class,false);
                break;

            case R.id.tv_forget_password:
                gotoActivity(RegisterActivity.class, false);
                break;

            case R.id.iv_qqlogin:
                //  gotoActivity(RegisterActivity.class, false);
                qqLogin();
                break;

            case R.id.iv_wxlogin:
                weChatLogin();
                break;


        }

    }

    private final String UNIONID = "unionid";


    /**
     * QQ登录
     */
    private void qqLogin() {
        thirdShouQuan(SHARE_MEDIA.QQ);
    }

    /**
     * 微信登录
     */
    private void weChatLogin() {
        thirdShouQuan(SHARE_MEDIA.WEIXIN);
    }

    /**
     * 请求第三方授权
     *
     * @param platForm
     */
    private void thirdShouQuan(final SHARE_MEDIA platForm) {

        UMWXHandler umwxHandler = new UMWXHandler(this, Constant.WX_APPID, Constant.WX_SECRET);
        umwxHandler.addToSocialSDK();

        mController.doOauthVerify(this, platForm, new SocializeListeners.UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                if (mLoading != null && !mLoading.isShowing()) {
                    mLoading.show();
                    mLoading.setText("正在获取授权");
                }
            }

            @Override
            public void onComplete(Bundle bundle, SHARE_MEDIA share_media) {

                if (bundle == null) {
                    LogUtils.e("bundle: " + bundle);
                    if (mLoading != null && mLoading.isShowing()) {
                        mLoading.dismiss();
                    }
                    return;
                }

                for (String key : bundle.keySet()) {
                    LogUtils.e("bundle key: " + key + " ,value: " + bundle.get(key));
                }

                final Map<String, String> params = new HashMap<>();
                params.clear();
                params.put("password", bundle.get("access_token").toString());
                String uid = bundle.getString("uid");
                if (bundle != null && !TextUtils.isEmpty(uid)) {
                    mController.getPlatformInfo(mContext, platForm, new SocializeListeners.UMDataListener() {
                        @Override
                        public void onStart() {
                            mLoading.setText("正在获取用户信息");
                        }

                        @Override
                        public void onComplete(int i, Map<String, Object> map) {
                            Set<String> strings = map.keySet();
                            if (strings != null) {
                                for (String key : strings) {
                                    Object value = (Object) map.get(key);
                                    LogUtils.e(key + " ," + (value == null ? value : value.toString()));
                                }
                                params.put("unionId", map.get(UNIONID).toString());
                                params.put("userName", map.get(UNIONID).toString());
                                params.put("nickName", map.get("nickname").toString());
                                params.put("picUrl", map.get("headimgurl").toString());
                                params.put("gender", map.get("sex").toString());
                                thirdLogin(params);
                            }
                            if (mLoading != null && mLoading.isShowing()) {
                                mLoading.dismiss();
                            }
                        }
                    });
                } else {
                    Toast.makeText(mContext, "授权失败", Toast.LENGTH_SHORT).show();
                    if (mLoading != null && mLoading.isShowing()) {
                        mLoading.dismiss();
                    }
                }
            }

            @Override
            public void onError(SocializeException e, SHARE_MEDIA share_media) {
                if (mLoading != null && mLoading.isShowing()) {
                    mLoading.dismiss();
                }
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media) {
                if (mLoading != null && mLoading.isShowing()) {
                    mLoading.dismiss();
                }
            }
        });
    }


    /**
     * 第三方登录
     *
     * @param params 请求参数,用于生成请求实体 json
     */
    private void thirdLogin(Map<String, String> params) {
        if (params != null) {
            JSONObject jsonObject = new JSONObject(params);
            String json = jsonObject.toString();
            LogUtils.e("json: " + json);
            OkHttpUtils.postString()
                    .url(UrlBuilder.serverUrl + UrlBuilder.openRegister)
                    .addHeader("udid", "partylive")
                    .mediaType(MediaType.parse("application/json;charset=UTF-8"))
                    .content(json)
                    .build().
                    execute(new CustomStringCallBack(LoginActivity.this, HttpDatas.KEY_CODE) {
                        @Override
                        public void onFaild() {
                            showToast("登录失败");
                        }

                        @Override
                        public void onSucess(String data) {
                            LogUtils.e("第三方登录: " + data);
                            loginSucess(data);
                            LoginFrom loginFrom = new LoginFrom();
                            loginFrom.setThirdLogin(true);
                            loginFrom.setPassword("");
                            CacheUtils.saveObject(mContext, loginFrom, CacheUtils.PASSWORD);
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // mShareAPI.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 登录
     *
     * @author sll
     * @time 2016/11/11 18:05
     */
    private void login(String name, String pass) {


        LoginFrom loginFrom = new LoginFrom();
        loginFrom.setThirdLogin(false);
        loginFrom.setPassword(pass);
        CacheUtils.saveObject(mContext, loginFrom, CacheUtils.PASSWORD);
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("userName", name);
        map.put("password", pass);
        httpDatas.getDataForJson("登录", Request.Method.POST, UrlBuilder.LOGIN, map, mHandler2, RequestCode.LOGIN_TAG);
    }

    private void onParseIntent() {
        if (getIntent().getBooleanExtra(KICK_OUT, false)) {
            int type = NIMClient.getService(AuthService.class).getKickedClientType();
            String client;
            switch (type) {
                case ClientType.Web:
                    client = "网页端";
                    break;
                case ClientType.Windows:
                    client = "电脑端";
                    break;
                case ClientType.REST:
                    client = "服务端";
                    break;
                default:
                    client = "移动端";
                    break;
            }
            EasyAlertDialogHelper.showOneButtonDiolag(LoginActivity.this, getString(R.string.kickout_notify),
                    String.format(getString(R.string.kickout_content), client), getString(R.string.ok), true, null);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
            LogUtils.i("WangYi", "account:" + account + "\ntoken:" + token);
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
                //CacheUtils.saveObject(mContext,)
            }

            @Override
            public void onFailed(int code) {
                onLoginDone();
                if (code == 302 || code == 404) {
                    Toast.makeText(LoginActivity.this, R.string.login_failed, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "登录失败: " + code, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onException(Throwable exception) {
                Toast.makeText(LoginActivity.this, R.string.login_exception, Toast.LENGTH_LONG).show();
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
}
