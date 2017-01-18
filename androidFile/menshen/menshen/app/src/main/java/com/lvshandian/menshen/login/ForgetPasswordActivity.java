package com.lvshandian.menshen.login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.lvshandian.menshen.MyApplication;
import com.lvshandian.menshen.R;
import com.lvshandian.menshen.UrlBuilder;
import com.lvshandian.menshen.base.BaseActivity;
import com.lvshandian.menshen.httprequest.HttpDatas;
import com.lvshandian.menshen.httprequest.RequestCode;
import com.lvshandian.menshen.utils.Constant;
import com.lvshandian.menshen.utils.EncryptUtils;
import com.lvshandian.menshen.utils.LogUtils;
import com.lvshandian.menshen.utils.Sp;
import com.lvshandian.menshen.utils.TextUtils;
import com.lvshandian.menshen.view.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 创建找回密码界面
 */
public class ForgetPasswordActivity extends BaseActivity {
    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.et_code)
    EditText etCode;
    @Bind(R.id.tv_code)
    TextView tvCode;
    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.bt_register)
    Button btRegister;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.ll_parent_view)
    LinearLayout llParentView;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    Timer timerSms = new Timer();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Bundle data = msg.getData();
            int tagCode = data.getInt(HttpDatas.code);

            switch (msg.what) {
                case RequestCode.SMS_COOD:       // 获取验证码成功
                    if (tagCode == RequestCode.REQUEST_CODE) {
                        ToastUtils.showSnackBar(snackView, data.getString(HttpDatas.info));
                        return;
                    }
                    try {

//                        ToastUtils.showSnackBar(snackView, "验证码获发送成功");
                        LogUtils.e("验证码获发送成功");
                        String number = data.getString(HttpDatas.info);

                        JSONObject obj = new JSONObject(number);
                        //将验证码存储到sp中
                        Sp.setParam(mContext, Constant.SP_SMSCODE, obj.getInt("number"));
                        //一分钟之后将数据清除

                        timerSms.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                mHandler.sendEmptyMessage(300000);
                            }
                        }, 300000);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;

                case RequestCode.PASS_CODE:       // 重置密码成功

                    if (tagCode == RequestCode.REQUEST_CODE) {
                        ToastUtils.showSnackBar(snackView, data.getString(HttpDatas.info));
                        return;
                    }
                    ToastUtils.showSnackBar(snackView, "重置密码成功");
                    timerSms.cancel();
                    Sp.setParam(mContext, Constant.SP_SMSCODE, 0);
                    MyApplication.finishLastActivity();
                    gotoActivity(LoginActivity.class, true);
                    break;
                case 300000:       // 关闭计时器，并且清除Sms
                    timerSms.cancel();
                    Sp.setParam(mContext, Constant.SP_SMSCODE, 0);
                    break;
                case RequestCode.ISREGISTER_CODE_CODE:

                    //504已经注册，200没有注册，已注册可以进行密码的修改

                    // 返回用户是否注册进行短信验证码发送已经注册
                    if (tagCode == RequestCode.REQUEST_CODE) {
                        LogUtils.e("用户已注册获取验证码状态码" + tagCode);
                        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
                        map.put("phone", TextUtils.getTextContent(etName));
                        httpDatas.getData("获取验证码", Request.Method.POST, UrlBuilder.SMS_COOD_URL, map, mHandler, RequestCode.SMS_COOD);

                        if (time == 60) {
                            Timer timer = new Timer();
                            task = new YanzhengmaTimer();
                            timer.schedule(task, new Date(), 1000L);
                        }
                    } else {
                        LogUtils.e("用户未注册获取验证码状态码" + tagCode);
                        ToastUtils.showSnackBar(snackView, "你的账号还没有注册");
                    }
                    //已经注册进行获取验证码


                    break;
                case RequestCode.ISREGISTER_UPDATE_CODE:       // 返回用户是否注册进行修改密码
                    //进行修改密码
                    //504已经注册，200没有注册，已注册可以进行密码的修改
                    // 返回用户是否注册进行短信验证码发送已经注册
                    if (tagCode == RequestCode.REQUEST_CODE) {
                        LogUtils.e("账号已注册可以进行修改密码  状态码--" + tagCode);
                        register();
                    } else {
                        LogUtils.e("你的账号还没有注册  状态码--" + tagCode);
                        ToastUtils.showSnackBar(snackView, "你的账号还没有注册");
                    }
                    break;
            }

        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.login_activity_forgetpassword;
    }

    @Override
    protected void initListener() {

        tvCode.setOnClickListener(this);
        btRegister.setOnClickListener(this);
        llParentView.setOnClickListener(this);

    }

    @Override
    protected void initialized() {
        if (getIntent().getStringExtra("tag") != null && !getIntent().getStringExtra("tag").equals("")) {
            tvTitle.setText("修改密码");
            btRegister.setText("确定重置密码");
        } else {
            tvTitle.setText("忘记密码");
            btRegister.setText("确定修改");
        }


        etCode.setText("");
        etName.setText("");
        etPassword.setText("");
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.bt_register://进行修改密码
                if (TextUtils.isEmpty(TextUtils.getTextContent(etName))) {
                    ToastUtils.showSnackBar(snackView, "请输入手机号码");
                    return;
                }
                if (!TextUtils.isPhone(TextUtils.getTextContent(etName))) {
                    ToastUtils.showSnackBar(snackView, "请输入正确的手机号码");
                    return;
                }

                //进行修改密码
                isRegisterRegister();

                break;
            case R.id.tv_code://获取验证码

                if (TextUtils.isEmpty(TextUtils.getTextContent(etName))) {
                    ToastUtils.showSnackBar(snackView, "请输入手机号码");
                    return;
                }
                if (!TextUtils.isPhone(TextUtils.getTextContent(etName))) {
                    ToastUtils.showSnackBar(snackView, "请输入正确的手机号码");
                    return;
                }
                //获取验证码
                isRegisterCode();
                break;

            case R.id.ll_parent_view://返回
                finish();
                break;

        }
    }


    private YanzhengmaTimer task;
    private Integer time = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    //判断是否注册短信验证
    public void isRegisterCode() {

        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("phone", TextUtils.getTextContent(etName));
        httpDatas.getData("判断是否注册", Request.Method.GET, UrlBuilder.ISREGISTER_URL, map, mHandler, RequestCode.ISREGISTER_CODE_CODE);
    }

    //判断是否注册进行密码的修改
    public void isRegisterRegister() {

        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("phone", TextUtils.getTextContent(etName));
        httpDatas.getData("判断是否注册", Request.Method.GET, UrlBuilder.ISREGISTER_URL, map, mHandler, RequestCode.ISREGISTER_UPDATE_CODE);
    }


    private class YanzhengmaTimer extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (time >= 0) {
                        tvCode.setText(getString(R.string.setting_genghuanshoujihao_daojishitext, time--));
                        tvCode.setClickable(false);
                    } else {
                        time = 60;
                        tvCode.setText("获取验证码");
                        tvCode.setClickable(true);
                        task.cancel();
                    }
                }
            });
        }
    }


    //修改密码
    private void register() {

        String username = TextUtils.getTextContent(etName);
        String passWord = TextUtils.getTextContent(etPassword);

        String yanZhengMa = TextUtils.getTextContent(etCode);

        if (android.text.TextUtils.isEmpty(username)) {
            ToastUtils.showSnackBar(snackView, "帐号密码不可为空");
            return;
        }
        if (android.text.TextUtils.isEmpty(passWord)) {
            ToastUtils.showSnackBar(snackView, "密码不能为空");
            return;
        }
        if (android.text.TextUtils.isEmpty(yanZhengMa)) {
            ToastUtils.showSnackBar(snackView, "验证码不可为空");
            return;
        }
        if (passWord.length() < 6) {
            ToastUtils.showSnackBar(snackView, "密码最少6位");
            return;
        }
        int code = (int) Sp.getParam(mContext, Constant.SP_SMSCODE, 0);
        if (!(code + "").equals(yanZhengMa)) {
            ToastUtils.showSnackBar(snackView, "验证码有误");
            LogUtils.e("本地验证码code--" + code);
            return;
        }


        //加密密码
        passWord = EncryptUtils.md5(passWord);
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("phone", username);
        map.put("password", passWord);
        httpDatas.getData("修改密码", Request.Method.POST, UrlBuilder.PASSWORD_URL, map, mHandler, RequestCode.PASS_CODE);

    }

}
