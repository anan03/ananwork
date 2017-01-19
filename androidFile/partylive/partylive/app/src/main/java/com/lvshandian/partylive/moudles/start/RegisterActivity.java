package com.lvshandian.partylive.moudles.start;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.lvshandian.partylive.MainActivity;
import com.lvshandian.partylive.MyApplication;
import com.lvshandian.partylive.R;
import com.lvshandian.partylive.UrlBuilder;
import com.lvshandian.partylive.base.BaseActivity;
import com.lvshandian.partylive.bean.AppUser;
import com.lvshandian.partylive.httprequest.HttpDatas;
import com.lvshandian.partylive.httprequest.RequestCode;
import com.lvshandian.partylive.utils.CacheUtils;
import com.lvshandian.partylive.utils.LogUtils;
import com.lvshandian.partylive.utils.TextPhoneNumber;
import com.lvshandian.partylive.utils.TimeCount;
import com.lvshandian.partylive.utils.ToastUtil;
import com.lvshandian.partylive.view.ToastUtils;
import com.tandong.sa.loopj.RequestParams;

import org.json.JSONObject;

import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;

/**
 * 注册页面
 * Created by 张振 on 2016/11/8.
 */

public class RegisterActivity extends BaseActivity {
    @Bind(R.id.ed_register_phone)
    EditText edRegisterPhone;
    @Bind(R.id.ed_register_code)
    EditText edRegisterCode;
    @Bind(R.id.tv_send_code)
    TextView tvSendCode;
    @Bind(R.id.ed_register_password)
    EditText edRegisterPassword;
    @Bind(R.id.ed_register_recommend_code)
    EditText edRegisterRecommendCode;
    @Bind(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @Bind(R.id.btn_register)
    Button btnRegister;
    private int waitTime = 60;
    private String phone = "";//账号


    private Handler mHandler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);

            switch (msg.what) {
                case RequestCode.LOGIN_TAG:
//                    ToastUtils.showSnackBar(snackView, json.toString());
                    appUser = JSON.parseObject(json, AppUser.class);
                    //存储用户信息
                    CacheUtils.saveObject(RegisterActivity.this, appUser, CacheUtils.USERINFO);
                    //登陆成功后，跳转到修改用户信息页面
                    gotoActivity(RegisterUserActivity.class, true);
                    break;
                case RequestCode.REGISTER_TAG:
                    showToast("注册成功");
                    appUser = JSON.parseObject(json, AppUser.class);
                    //存储用户信息
                    CacheUtils.saveObject(RegisterActivity.this, appUser, CacheUtils.USERINFO);
                    //登陆成功后，跳转到修改用户信息页面
                    startActivity(new Intent(RegisterActivity.this, RegisterUserActivity.class).putExtra("isRegister", true));
//                    gotoActivity(RegisterUserActivity.class, true);
                    //注册成功后，登录
//                    login(edRegisterPhone.getText().toString(), edRegisterPassword.getText().toString());
//                    gotoActivity(RegisterUserActivity.class, true);
//                    ToastUtils.showSnackBar(snackView, json.toString());
                    break;

            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initListener() {
        tvSendCode.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }


    @Override
    protected void initialized() {
        //sharesdk短信验证码
        SMSSDK.initSDK(this, "170e5b18761f6", "822e1a47a63ecc31e4a8d281669dbbd6");
        EventHandler eh = new EventHandler() {

            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                mHandler.sendMessage(msg);
            }

        };
        SMSSDK.registerEventHandler(eh);
        initTitle("", "注册", "");
        tvSendCode.setText("获取验证码");

    }

    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {

            // TODO Auto-generated method stub
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;

            if (result == SMSSDK.RESULT_COMPLETE) {
                //短信注册成功后，返回MainActivity,然后提示新好友
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功
//                    ToastUtils.showSnackBar(btnRegister,"验证码已发送");
                    Toast.makeText(RegisterActivity.this, "验证成功", Toast.LENGTH_SHORT).show();

                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //已经验证
                    Toast.makeText(RegisterActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
//                    ToastUtil.makeToast("验证码已发送");

                    showToast("验证码已发送");

                }
            } else {
                int status = 0;
                try {
                    ((Throwable) data).printStackTrace();
                    Throwable throwable = (Throwable) data;
                    JSONObject object = new JSONObject(throwable.getMessage());
                    String des = object.optString("detail");
                    status = object.optInt("status");
                    if (!TextUtils.isEmpty(des)) {
                        Toast.makeText(RegisterActivity.this, des, Toast.LENGTH_SHORT).show();
                        return;
                    }
                } catch (Exception e) {
                    SMSLog.getInstance().w(e);
                }
            }
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_titlebar_left:
                defaultFinish();
                break;

            case R.id.tv_send_code:
                sendCode();
                break;

            case R.id.btn_register:
//               testCode();
                String phone = edRegisterPhone.getText().toString();
                String registerCode = edRegisterCode.getText().toString();
                String pwd = edRegisterPassword.getText().toString();
                String recommendCode = edRegisterRecommendCode.getText().toString();
                if (TextUtils.isEmpty(phone) || phone.length() != 11 || !TextPhoneNumber.isPhone(phone)) {
                    showToast("手机号不正确");
                    return;
                }
                if (TextUtils.isEmpty(registerCode)) {
                    showToast("短信验证码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(pwd) || pwd.length() < 4 || pwd.length() > 16) {
                    showToast("密码不正确，请设置为4-16位字符");
                    return;
                }
                register(phone, pwd, registerCode, recommendCode);
                break;
        }

    }

    /**
     * 注册
     */
    private void register(String name, String pass, String code, String yqcode) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("userName", name);
        map.put("password", pass);
        map.put("identityCode", code);
        map.put("identityCodeAndroid", "android");
//        if (!TextUtils.isEmpty(yqcode))
        map.put("introducerCode", yqcode);
        httpDatas.getDataForJson("注册", Request.Method.POST, UrlBuilder.REGISTER, map, mHandler2, RequestCode.REGISTER_TAG);
    }

    /**
     * 登录
     *
     * @author sll
     * @time 2016/11/11 18:05
     */
    private void login(String name, String pass) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("userName", name);
        map.put("password", pass);
        httpDatas.getDataForJson("登录", Request.Method.POST, UrlBuilder.LOGIN, map, mHandler2, RequestCode.LOGIN_TAG);
    }

    /**
     * 发送验证码
     */
    private void sendCode() {
        // 给request赋一个TAG，以便于取消时候使用
        phone = edRegisterPhone.getText().toString();
        if (!phone.equals("") && phone.length() == 11) {
            TimeCount time = new TimeCount(this, 60000, 1000, tvSendCode);
            time.start();
//            tvSendCode.setEnabled(false);
//            tvSendCode.setTextColor(getResources().getColor(R.color.black));
//            tvSendCode.setText("重新获取(" + waitTime + ")");
//            handler.postDelayed(runnable, 1000);

            SMSSDK.getVerificationCode("86", phone);
        } else {
            showToast("请输入正确手机号");
//            ToastUtils.showSnackBar(edRegisterPhone,"请输入正确手机号");
        }

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            waitTime--;
            tvSendCode.setText("重新获取(" + waitTime + ")");
            if (waitTime == 0) {
                handler.removeCallbacks(runnable);
                tvSendCode.setText("发送验证码");
                tvSendCode.setEnabled(true);
                waitTime = 60;
                return;
            }
            handler.postDelayed(this, 1000);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}
