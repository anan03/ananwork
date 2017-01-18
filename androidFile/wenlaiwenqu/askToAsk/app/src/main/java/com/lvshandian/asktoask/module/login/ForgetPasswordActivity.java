package com.lvshandian.asktoask.module.login;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.lvshandian.asktoask.App;
import com.lvshandian.asktoask.BaseActivity;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.http.RequestCode;
import com.lvshandian.asktoask.utils.Constant;
import com.lvshandian.asktoask.utils.EncryptUtils;
import com.lvshandian.asktoask.utils.MethodUtils;
import com.lvshandian.asktoask.utils.ToastUtils;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;

/**
 * author: newlq on 2016/9/1 17:08.
 * email： @lvshandian.com
 * company:北京绿闪电科技有限公司
 * details：忘记密码
 */
public class ForgetPasswordActivity extends BaseActivity {

    @Bind(R.id.tv_titlebar_centertext)
    TextView tvTitlebarCentertext;
    @Bind(R.id.ll_titlebar_zuojiantou)
    LinearLayout lltitlebarzuojiantou;
    @Bind(R.id.et_forgetpassword_phonenum)
    EditText etForgetpasswordPhonenum;
    @Bind(R.id.et_forgetpassword_yanzhengma)
    EditText etForgetpasswordYanzhengma;
    @Bind(R.id.btn_forgetpassword_getyanzhengma)
    Button btnForgetpasswordGetyanzhengma;
    @Bind(R.id.et_forgetpassword_password)
    EditText etForgetpasswordPassword;
    @Bind(R.id.btn_forgetpassword_go)
    Button btn_forgetpassword_go;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RequestCode.WANGJIMIMA_URL_REQUESTCODE:
                    changePasswordSucces();
                    break;
                case RequestCode.SMS_FIND_COOD:
                    ToastUtils.showSnackBar(snackView, "验证码获发送成功");
                    break;

                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forgetpassword;
    }

    @Override
    protected void initListener() {
        lltitlebarzuojiantou.setOnClickListener(this);
        btn_forgetpassword_go.setOnClickListener(this);
        btnForgetpasswordGetyanzhengma.setOnClickListener(this);
    }

    @Override
    protected void initialized() {
        tvTitlebarCentertext.setText("忘记密码");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_forgetpassword_getyanzhengma:     //获取验证码
                getYanZhengMa();
                break;
            case R.id.btn_forgetpassword_go:                //忘记密码确定按钮
                wangjimima();
                break;
            case R.id.ll_titlebar_zuojiantou:                //左箭头点击事件
                finish();
                break;
            default:
                break;
        }
    }

    private YanzhengmaTimer task;
    private Integer time = 60;

    /**
     * 获取验证码
     */
    private void getYanZhengMa() {

        if (TextUtils.isEmpty(MethodUtils.getTextContent(etForgetpasswordPhonenum))) {
            ToastUtils.showSnackBar(snackView, "请输入手机号码?");
            return;
        }
        if (!MethodUtils.getTextContent(etForgetpasswordPhonenum).matches(Constant.PHONENUM_REGEX)) {
            ToastUtils.showSnackBar(snackView, "请输入正确的手机号码");
            return;
        }

        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("userName", MethodUtils.getTextContent(etForgetpasswordPhonenum));
        httpDatas.getData("获取找回密码验证码", Request.Method.GET, UrlBuilder.SMS_COOD, map, mHandler, RequestCode.SMS_FIND_COOD);


        if (time == 60) {
            Timer timer = new Timer();
            task = new YanzhengmaTimer();
            timer.schedule(task, new Date(), 1000L);
        }
    }


    private class YanzhengmaTimer extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (time >= 0) {
                        btnForgetpasswordGetyanzhengma.setText(getString(R.string.setting_genghuanshoujihao_daojishitext, time--));
                        btnForgetpasswordGetyanzhengma.setClickable(false);
                    } else {
                        time = 60;
                        btnForgetpasswordGetyanzhengma.setText("获取验证码");
                        btnForgetpasswordGetyanzhengma.setClickable(true);
                        task.cancel();
                    }
                }
            });
        }
    }

    /**
     * 跳转到登陆页
     */
    private void changePasswordSucces() {
        gotoActivity(LoginActivity.class, false);
        App.finishActivity();
    }

    /**
     * 执行忘记密码流程 访问接口
     */
    private void wangjimima() {
        String yanZhengMa = MethodUtils.getTextContent(etForgetpasswordYanzhengma);

        String phoneNum = MethodUtils.getTextContent(etForgetpasswordPhonenum);
        String password = MethodUtils.getTextContent(etForgetpasswordPassword);

        if (TextUtils.isEmpty(phoneNum) || TextUtils.isEmpty(password)) {
            ToastUtils.showSnackBar(snackView, "帐号或密码不可为空");
            return;
        }
        if (TextUtils.isEmpty(yanZhengMa)) {
            ToastUtils.showSnackBar(snackView, "验证码不可为空");
            return;
        }
        if (yanZhengMa.length() < 4) {
            ToastUtils.showSnackBar(snackView, "验证码格式不正确");
            return;
        }
        if (!phoneNum.matches(Constant.PHONENUM_REGEX)) {
            ToastUtils.showSnackBar(snackView, "请输入正确的手机号码");
            return;
        }

        password = EncryptUtils.md5(password);
        password = EncryptUtils.md5(password);
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("userName", phoneNum);
        map.put("userPassword", password);
        map.put("smsCode", MethodUtils.getTextContent(etForgetpasswordYanzhengma));


        httpDatas.getData("忘记密码", Request.Method.POST, UrlBuilder.WANGJIMIMA_URL, map, mHandler, RequestCode.WANGJIMIMA_URL_REQUESTCODE);


    }
}
