package com.lvshandian.asktoask.module.login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.lvshandian.asktoask.BaseActivity;
import com.lvshandian.asktoask.MainActivity;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.http.HttpDatas;
import com.lvshandian.asktoask.common.http.RequestCode;
import com.lvshandian.asktoask.entry.User;
import com.lvshandian.asktoask.entry.UserThirdpartyBean;
import com.lvshandian.asktoask.utils.Constant;
import com.lvshandian.asktoask.utils.EncryptUtils;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.JsonUtil;
import com.lvshandian.asktoask.utils.L;
import com.lvshandian.asktoask.utils.MethodUtils;
import com.lvshandian.asktoask.utils.ToastUtils;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * author: newlq on 2016/9/1 17:08.
 * email：@lvshandian.com
 * company:北京绿闪电科技有限公司
 * details：登录页
 */
public class LoginActivity extends BaseActivity implements PlatformActionListener {

    @Bind(R.id.tv_titlebar_centertext)
    TextView tvTitlebarCentertext;
    @Bind(R.id.et_login_phonenum)
    EditText etLoginPhonenum;
    @Bind(R.id.et_login_password)
    EditText etLoginPassword;
    @Bind(R.id.tv_login_lijizhuce)
    TextView tvLoginLijizhuce;
    @Bind(R.id.tv_login_wangjimima)
    TextView tvLoginWangjimima;
    @Bind(R.id.btn_login_click)
    Button btnLoginClick;
    @Bind(R.id.iv_login_xinlang)
    ImageView ivLoginXinlang;
    @Bind(R.id.iv_login_weixin)
    ImageView ivLoginWeixin;
    @Bind(R.id.iv_login_qq)
    ImageView ivLoginQq;
    @Bind(R.id.ll_titlebar_zuojiantou)
    LinearLayout llTitlebarZuojiantou;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);
            switch (msg.what) {
                case RequestCode.SIMPLE_LOGIN_URL_REQUESTCODE:
                    try {

                        if (MethodUtils.isEmpty(json)) {
                            ToastUtils.showSnackBar(snackView, "登录失败");
                            return;
                        }
                        /**
                         * 登录成功
                         */
                        JSONObject obj = new JSONObject(json.toString());
                        User.getUser().setUserId(obj.getString("userId"));
                        User.getUser().setIsapprove(obj.getString("isapprove"));

                        if("1".equals(obj.getString("isapprove"))){
                            Global.setParam(getContext(),"isappprove","1");
                        }else{
                            Global.setParam(getContext(),"isappprove","2");
                        }

                        //存储登录状态，和UserId
                        Global.setParam(getContext(), Constant.USERID, obj.getString("userId"));
                        Global.setParam(getContext(), Constant.ISLOGIN, true);
                        //设置推送标签
                        Set<String> set = new HashSet<String>();
                        set.add(Constant.TAGONE);
                        set.add(Constant.TAGTWO);
                        JPushInterface.setAliasAndTags(getApplicationContext(),
                                obj.getString("userId"),
                                set,
                                mAliasCallback);


                        if (!MethodUtils.isEmpty(json))
                            gotoActivity(MainActivity.class, true);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;

                case MSG_SET_ALIAS:
                    Log.d(TAG, "Set alias in handler.");
                    // 调用 JPush 接口来设置别名。
                    Set<String> set = new HashSet<String>();
                    set.add(Constant.TAGONE);
                    set.add(Constant.TAGTWO);

                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            (String) Global.getParam(getContext(), Constant.USERID, ""),
                            set,
                            mAliasCallback);
                    break;
                //第三方登录成功
                case RequestCode.LOGIN_TAG:
                    Log.d("json1", json);
                    if (MethodUtils.isEmpty(json)) {
                        ToastUtils.showSnackBar(snackView, "登录失败");
                        return;
                    }
                    UserThirdpartyBean userThirdpartyBean = JsonUtil.json2Bean(json, UserThirdpartyBean.class);
                    User.getUser().setUserId(userThirdpartyBean.getUserId());
                    //存储登录状态，和UserId
                    Global.setParam(getContext(), Constant.USERID, userThirdpartyBean.getUserId());
                    Global.setParam(getContext(), Constant.ISLOGIN, true);
                    if("1".equals(userThirdpartyBean.getIsapprove())){
                        Global.setParam(getContext(),"isappprove","1");
                    }else{
                        Global.setParam(getContext(),"isappprove","2");
                    }
                    //设置推送标签
                    Set<String> set1 = new HashSet<String>();
                    set1.add(Constant.TAGONE);
                    set1.add(Constant.TAGTWO);
                    JPushInterface.setAliasAndTags(getApplicationContext(),
                            userThirdpartyBean.getUserId(),
                            set1,
                            mAliasCallback);
                    gotoActivity(MainActivity.class, true);
                    break;


                case 1000000:
                    //
                    ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

                    PlatformDb platDB = (PlatformDb) msg.obj;
                    map.put("uid", "" + platDB.getUserId());
                    map.put("token", platDB.getToken());
                    map.put("userRealName",platDB.getUserName());
                    map.put("userHeadImg",platDB.getUserIcon());
                    map.put("status", "" + login_tag);
                    httpDatas.getData("第三方系统登录", Request.Method.POST, UrlBuilder.THIRDPARTY_LOGIN_URL, map, mHandler, RequestCode.LOGIN_TAG);

                    break;

            }
        }
    };
    //登录的Id
    private static int login_tag = 0;

    private static final int MSG_SET_ALIAS = 1001;
    String TAG = "TAG";
    private final TagAliasCallback mAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs;
            switch (code) {
                case 0:
                    logs = "Set tag and alias success";
                    Log.i(TAG, logs);
                    // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。

                    Global.setParam(getContext(), Constant.ISTAG, true);
                    break;
                case 6002:
                    logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                    Log.i(TAG, logs);
                    // 延迟 60 秒来调用 Handler 设置别名
                    // 延迟 60 秒来调用 Handler 设置别名
                    if (!(boolean) Global.getParam(getContext(), Constant.ISTAG, false)) {
                        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                    }
                    break;
                default:
                    logs = "Failed with errorCode = " + code;
                    Log.e(TAG, logs);
            }
//            ExampleUtil.showToast(logs, getApplicationContext());
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initListener() {
        ivLoginQq.setOnClickListener(this);
        ivLoginWeixin.setOnClickListener(this);
        ivLoginXinlang.setOnClickListener(this);
        llTitlebarZuojiantou.setOnClickListener(this);
        btnLoginClick.setOnClickListener(this);

        tvLoginLijizhuce.setOnClickListener(this);
        tvLoginWangjimima.setOnClickListener(this);
    }

    @Override
    protected void initialized() {
        ShareSDK.initSDK(this);
        tvTitlebarCentertext.setText(R.string.login_denglutext);

    }

    private String type = "";
    private String[] names = {QQ.NAME, Wechat.NAME, SinaWeibo.NAME};

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login_click:      //登录
                login();
                break;
            case R.id.iv_login_qq:          //QQ登录
                type = "qq";
                login_tag = 1;
                otherLogin(names[0]);
                break;
            case R.id.iv_login_weixin:      //微信登录
                type = "wx";
                login_tag = 2;
                otherLogin(names[1]);
                break;
            case R.id.iv_login_xinlang:     //新浪登录
                type = "sina";
                login_tag = 3;
                otherLogin(names[2]);
                break;
            case R.id.tv_login_lijizhuce:   //注册按钮
                zhuce();
                break;
            case R.id.tv_login_wangjimima:   //忘记密码
                wangjimima();
                break;
            case R.id.ll_titlebar_zuojiantou:   //返回监听
                gotoActivity(MainActivity.class,false);
                break;
            default:
                break;
        }
    }

    //第三方登录
    private void otherLogin(String name) {
        Platform other = ShareSDK.getPlatform(name);
//        other.showUser(null);//执行登录，登录后在回调里面获取用户资料
        other.SSOSetting(false);  //设置false表示使用SSO授权方式
        other.setPlatformActionListener(this);
        other.authorize();

    }

    /**
     * 忘记密码
     * 打开去修改密码
     */
    private void wangjimima() {
        gotoActivity(ForgetPasswordActivity.class, false);
    }

    /**
     * 打开注册页
     */
    private void zhuce() {
        gotoActivity(registerActivity.class, false);
    }

    /**
     * 第三方：新浪登录
     */
    private void xlLogin() {
        ToastUtils.showSnackBar(snackView, "新浪登录");
    }

    /**
     * 第三方：微信登录
     */
    private void wxLogin() {
        ToastUtils.showSnackBar(snackView, "微信登录");
    }

    /**
     * 第三方：QQ登录
     */
    private void qqLogin() {
        ToastUtils.showSnackBar(snackView, "QQ登录");
    }

    /**
     * 普通登录
     */
    private void login() {
        String phonenum = MethodUtils.getTextContent(etLoginPhonenum);
        String passWord = MethodUtils.getTextContent(etLoginPassword);

        if (TextUtils.isEmpty(phonenum) || TextUtils.isEmpty(passWord)) {
            ToastUtils.showSnackBar(snackView, "帐号或密码不可为空");
            return;
        } else {
            passWord = EncryptUtils.md5(passWord);
            passWord = EncryptUtils.md5(passWord);
            if (!phonenum.matches(Constant.PHONENUM_REGEX)) {
                ToastUtils.showSnackBar(snackView, "请输入正确的手机号码");
                return;
            }

            ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
            map.put("userName", phonenum);
            map.put("userPassword", passWord);
            httpDatas.getData("系统登录", Request.Method.POST, UrlBuilder.LOGIN_URL, map, mHandler, RequestCode.SIMPLE_LOGIN_URL_REQUESTCODE);
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 登录成功的返回值
     *
     * @param platform
     * @param i
     * @param hashMap
     */

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        //用户资源都保存到res
        //通过打印res数据看看有哪些数据是你想要的
        PlatformDb platDB = platform.getDb();//获取数平台数据DB
        //通过DB获取各种数据

        Message mes = new Message();
        mes.obj = platform.getDb();
        mes.what = 1000000;

        mHandler.sendMessage(mes);
//
//

    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {

    }

    @Override
    public void onCancel(Platform platform, int i) {


    }
}


