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
import com.lvshandian.menshen.R;
import com.lvshandian.menshen.UrlBuilder;
import com.lvshandian.menshen.base.BaseActivity;
import com.lvshandian.menshen.httprequest.HttpDatas;
import com.lvshandian.menshen.httprequest.RequestCode;
import com.lvshandian.menshen.utils.Constant;
import com.lvshandian.menshen.utils.EncryptUtils;
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
 * 创建注册界面
 */
public class registerActivity extends BaseActivity {


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
            if (data.getInt(HttpDatas.code) == RequestCode.REQUEST_CODE) {
                ToastUtils.showSnackBar(snackView, data.getString(HttpDatas.info));
                return;
            }

            switch (msg.what) {
                case RequestCode.SMS_COOD:       // 获取验证码成功
                    try {

                        ToastUtils.showSnackBar(snackView, "验证码获发送成功");
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

                case RequestCode.REGIST_CODE:       // 注册成功进行跳转
                    ToastUtils.showSnackBar(snackView, "注册成功");
                    timerSms.cancel();
                    Sp.setParam(mContext, Constant.SP_SMSCODE, 0);
                    gotoActivity(LoginActivity.class, true);
                    finish();
                    break;
                case 300000:       // 关闭计时器，并且清除Sms
                    timerSms.cancel();
//                    Sp.setParam(mContext, Constant.SP_SMSCODE, 0);break;

            }

        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.login_activity_register;
    }


    @Override
    protected void initListener() {

        tvCode.setOnClickListener(this);
        btRegister.setOnClickListener(this);
        llParentView.setOnClickListener(this);

    }

    @Override
    protected void initialized() {
        tvTitle.setText("注册");
        etCode.setText("");
        etName.setText("");
        etPassword.setText("");
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.bt_register://注册账号
                register();

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
                requesstCode();
                break;
            case R.id.ll_parent_view://返回
                finish();
                break;


        }
    }

    //获取验证码
    private void requesstCode() {


        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("phone", TextUtils.getTextContent(etName));
        httpDatas.getData("获取验证码", Request.Method.POST, UrlBuilder.SMS_COOD_URL, map, mHandler, RequestCode.SMS_COOD);


        if (time == 60) {
            Timer timer = new Timer();
            task = new YanzhengmaTimer();
            timer.schedule(task, new Date(), 1000L);
        }

    }

    private YanzhengmaTimer task;
    private Integer time = 60;

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

    //注册接口
    private void register() {

        String username = TextUtils.getTextContent(etName);
        String passWord = TextUtils.getTextContent(etPassword);

        String yanZhengMa = TextUtils.getTextContent(etCode);

        if (android.text.TextUtils.isEmpty(username)) {
            ToastUtils.showSnackBar(snackView, "手机号码不能为空");
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
            return;
        }


        //加密密码
        passWord = EncryptUtils.md5(passWord);
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("phone", username);
        map.put("password", passWord);
        //暂无验证码
        httpDatas.getData("注册", Request.Method.POST, UrlBuilder.REGIST_URL, map, mHandler, RequestCode.REGIST_CODE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
