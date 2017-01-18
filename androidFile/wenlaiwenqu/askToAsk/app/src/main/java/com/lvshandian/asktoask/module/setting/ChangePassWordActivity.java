package com.lvshandian.asktoask.module.setting;

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
import com.lvshandian.asktoask.module.login.LoginActivity;
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
 * Created by Administrator on 2016/9/5.
 */
public class ChangePassWordActivity extends BaseActivity {

    @Bind(R.id.tv_titlebar_centertext)
    TextView tvTitlebarCentertext;
    @Bind(R.id.ll_titlebar_zuojiantou)
    LinearLayout llTitlebarZuojiantou;
    @Bind(R.id.et_changepasswod_phonenum)
    EditText etChangepasswodPhonenum;
    @Bind(R.id.et_changepasswod_yanzhengma)
    EditText etChangepasswodYanzhengma;
    @Bind(R.id.btn_changepasswod_getyanzhengma)
    Button btnChangepasswodGetyanzhengma;
    @Bind(R.id.et_changepasswod_password)
    EditText etChangepasswodPassword;
    @Bind(R.id.btn_changepasswod_gochange)
    Button btnChangepasswodGochange;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RequestCode.MINE_WANGJIMIMA_REQUESTCODE:
                    gotoActivity(LoginActivity.class, false);
                    ToastUtils.showSnackBar(snackView, "修改密码成功");
                    App.finishActivity();
                    break;
                case RequestCode.SMS_ALTER_COOD://验证码获取成功
                    ToastUtils.showSnackBar(snackView, "获取验证码成功");
                    break;

                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_changepassword;
    }

    @Override
    protected void initListener() {
        llTitlebarZuojiantou.setOnClickListener(this);
        btnChangepasswodGochange.setOnClickListener(this);
        btnChangepasswodGetyanzhengma.setOnClickListener(this);
    }

    @Override
    protected void initialized() {
        tvTitlebarCentertext.setText(R.string.setting_xiugaimima);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_titlebar_zuojiantou:
                finish();
                break;
            case R.id.btn_changepasswod_gochange:           //修改密码
                changePassWord();
                break;
            case R.id.btn_changepasswod_getyanzhengma:      //获取验证码
                getYanZhengMa();
                break;
            default:
                break;
        }
    }

    /**
     * 修改密码
     */
    private void changePassWord() {


        String yanZhengMa = MethodUtils.getTextContent(etChangepasswodYanzhengma);

        if (TextUtils.isEmpty(MethodUtils.getTextContent(etChangepasswodPhonenum))) {
            ToastUtils.showSnackBar(snackView, "请输入手机号码?");
            return;
        }
        if (!MethodUtils.getTextContent(etChangepasswodPhonenum).matches(Constant.PHONENUM_REGEX)) {
            ToastUtils.showSnackBar(snackView, "请输入正确的手机号码");
            return;
        }
        if (com.lvshandian.asktoask.utils.TextUtils.isEmpty(yanZhengMa)) {

            ToastUtils.showSnackBar(snackView, "请输入验证码");
            return;
        }


        String phoneNum = MethodUtils.getTextContent(etChangepasswodPhonenum);
        String password = MethodUtils.getTextContent(etChangepasswodPassword);

        password = EncryptUtils.md5(password);
        password = EncryptUtils.md5(password);
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("userName", phoneNum);
        map.put("userPassword", password);
        map.put("smsCode", MethodUtils.getTextContent(etChangepasswodYanzhengma));
        httpDatas.getData("修改密码", Request.Method.POST, UrlBuilder.WANGJIMIMA_URL, map, mHandler, RequestCode.MINE_WANGJIMIMA_REQUESTCODE);


    }


    private YanzhengmaTimer task;
    private Integer time = 60;

    /**
     * 获取验证码
     */
    private void getYanZhengMa() {

        if (TextUtils.isEmpty(MethodUtils.getTextContent(etChangepasswodPhonenum))) {
            ToastUtils.showSnackBar(snackView, "请输入手机号码");
            return;
        }
        if (!MethodUtils.getTextContent(etChangepasswodPhonenum).matches(Constant.PHONENUM_REGEX)) {
            ToastUtils.showSnackBar(snackView, "请输入正确的手机号码");
            return;
        }

        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("userName", MethodUtils.getTextContent(etChangepasswodPhonenum));
        httpDatas.getData("获取修改密码验证码", Request.Method.GET, UrlBuilder.SMS_COOD, map, mHandler, RequestCode.SMS_ALTER_COOD);


        if (time == 60) {
            Timer timer = new Timer();
            task = new YanzhengmaTimer();
            timer.schedule(task, new Date(), 1000L);
        }
    }

    /**
     * 校验此验证码是否与收到的相同
     *
     * @param yanZhengMa
     * @return
     */
    private boolean checkYanZhengMa(String yanZhengMa) {

        return true;
    }


    private class YanzhengmaTimer extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (time >= 0) {
                        btnChangepasswodGetyanzhengma.setText(getString(R.string.setting_genghuanshoujihao_daojishitext, time--));
                        btnChangepasswodGetyanzhengma.setClickable(false);
                    } else {
                        time = 60;
                        btnChangepasswodGetyanzhengma.setText("获取验证码");
                        btnChangepasswodGetyanzhengma.setClickable(true);
                        task.cancel();
                    }
                }
            });
        }
    }
}
