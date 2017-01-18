package com.lvshandian.asktoask.module.login;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.lvshandian.asktoask.BaseActivity;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.http.HttpDatas;
import com.lvshandian.asktoask.common.http.RequestCode;
import com.lvshandian.asktoask.utils.Constant;
import com.lvshandian.asktoask.utils.EncryptUtils;
import com.lvshandian.asktoask.utils.L;
import com.lvshandian.asktoask.utils.MethodUtils;
import com.lvshandian.asktoask.utils.ToastUtils;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;

/**
 * author: newlq on 2016/9/5 17:08.
 * email：@lvshandian.com
 * company:北京绿闪电科技有限公司
 * details：注册页
 */
public class registerActivity extends BaseActivity {


    @Bind(R.id.tv_titlebar_centertext)
    TextView tvTitlebarCentertext;
    @Bind(R.id.et_register_phonenum)
    EditText etRegisterPhonenum;
    @Bind(R.id.et_register_yanzhengma)
    EditText etRegisterYanzhengma;
    @Bind(R.id.btn_register_getyanzhengma)
    Button btnRegisterGetyanzhengma;
    @Bind(R.id.et_register_password)
    EditText etRegisterPassword;
    @Bind(R.id.et_register_yaoqingma)
    EditText etRegisterYaoqingma;
    @Bind(R.id.btn_register_goregister)
    Button btnRegisterGoregister;
    @Bind(R.id.iv_register_argeeprovision)
    ImageView ivRegisterArgeeprovision;
    @Bind(R.id.tv_register_provisionlink)
    TextView tvRegisterProvisionlink;
    @Bind(R.id.ll_titlebar_zuojiantou)
    LinearLayout lltitlebarzuojiantou;

    private YanzhengmaTimer task;
    private Integer time = 60;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case RequestCode.SIMPLE_REGISTER_URL_REQUESTCODE:       // 注册得到的结果
                    Bundle data = msg.getData();
                    String json = data.getString(HttpDatas.info);
                    L.e(json);
                    ToastUtils.showSnackBar(snackView, "注册成功");
                    gotoActivity(LoginActivity.class, true);
                    break;
                case RequestCode.SMS_COOD:       // 注册验证码的获取
                    Bundle data1 = msg.getData();
                    ToastUtils.showSnackBar(snackView, "验证码获取成功");
                    break;


                default:
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

        lltitlebarzuojiantou.setOnClickListener(this);
        tvRegisterProvisionlink.setOnClickListener(this);
        ivRegisterArgeeprovision.setOnClickListener(this);

        btnRegisterGoregister.setOnClickListener(this);
        btnRegisterGetyanzhengma.setOnClickListener(this);
    }

    @Override
    protected void initialized() {
        tvTitlebarCentertext.setText("注册");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register_getyanzhengma:   //获取验证码

                if (TextUtils.isEmpty(MethodUtils.getTextContent(etRegisterPhonenum))) {
                ToastUtils.showSnackBar(snackView, "请输入手机号码?");
                return;
            }
                if (!MethodUtils.getTextContent(etRegisterPhonenum).matches(Constant.PHONENUM_REGEX)) {
                    ToastUtils.showSnackBar(snackView, "请输入正确的手机号码");
                    return;
                }
                getYanZhengMa();
                break;
            case R.id.btn_register_goregister:      //注册按钮
                zhuce();
                break;
            case R.id.tv_register_provisionlink:    //点击进入服务条款
                ToastUtils.showSnackBar(snackView, "点击进入服务条款");
                break;
            case R.id.iv_register_argeeprovision:   //条款选中状态
                ToastUtils.showSnackBar(snackView, "是否被选中");
                break;
            case R.id.ll_titlebar_zuojiantou:       //左侧titleBar箭头
                finish();
                break;
            default:
                break;
        }
    }
    /**
     * 获取验证码
     */
    private void getYanZhengMa() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("userName", MethodUtils.getTextContent(etRegisterPhonenum));
        httpDatas.getData("获取注册验证码", Request.Method.GET, UrlBuilder.SMS_COOD, map, mHandler, RequestCode.SMS_COOD);


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
                        btnRegisterGetyanzhengma.setText(getString(R.string.setting_genghuanshoujihao_daojishitext, time--));
                        btnRegisterGetyanzhengma.setClickable(false);
                    } else {
                        time = 60;
                        btnRegisterGetyanzhengma.setText("获取验证码");
                        btnRegisterGetyanzhengma.setClickable(true);
                        task.cancel();
                    }
                }
            });
        }
    }

    /**
     * 系统注册
     */
    private void zhuce() {
        String username = MethodUtils.getTextContent(etRegisterPhonenum);
        String passWord = MethodUtils.getTextContent(etRegisterPassword);

        String yaoQingMa = MethodUtils.getTextContent(etRegisterYaoqingma);
        String yanZhengMa = MethodUtils.getTextContent(etRegisterYanzhengma);

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(passWord)) {
            ToastUtils.showSnackBar(snackView, "帐号密码不可为空");
            return;
        }
        if (TextUtils.isEmpty(yanZhengMa)) {
            ToastUtils.showSnackBar(snackView, "验证码不可为空");
            return;
        }
        if (passWord.length()<6) {
            ToastUtils.showSnackBar(snackView, "密码长度太短");
            return;
        }
        if (passWord.length()>16) {
            ToastUtils.showSnackBar(snackView, "密码长度太长");
            return;
        }
        passWord = EncryptUtils.md5(EncryptUtils.md5(passWord));

        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("userName", username);
        map.put("password", passWord);

        map.put("invitationCode", yaoQingMa);
        map.put("smsCode", yanZhengMa);         //暂无验证码
        httpDatas.getData("系统注册", Request.Method.POST, UrlBuilder.REGIST_URL, map, mHandler, RequestCode.SIMPLE_REGISTER_URL_REQUESTCODE);

    }

}
