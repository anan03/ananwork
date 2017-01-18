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
public class ChangePhoneNumActivity extends BaseActivity {


    @Bind(R.id.tv_titlebar_centertext)
    TextView tvTitlebarCentertext;
    @Bind(R.id.ll_titlebar_zuojiantou)
    LinearLayout llTitlebarZuojiantou;
    @Bind(R.id.et_changephonenum_phonenum)
    EditText etChangephonenumPhonenum;
    @Bind(R.id.et_changephonenum_yanzhengma)
    EditText etChangephonenumYanzhengma;
    @Bind(R.id.btn_changephonenum_getyanzhengma)
    Button btnChangephonenumGetyanzhengma;
    @Bind(R.id.et_changephonenum_newpphonenum)
    EditText etChangephonenumnewpphonenum;
    @Bind(R.id.btn_changephonenum_gochange)
    Button btnChangephonenumGochange;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RequestCode.MINE_XIUGAISHOUJIHAO_REQUESTCODE:
                    App.finishActivity();
                    ToastUtils.showSnackBar(snackView, "更换手机号成功");
                    gotoActivity(LoginActivity.class, false);

                    break;

                case RequestCode.SMS_ALTERPHONE_COOD://获取验证码成功

                    ToastUtils.showSnackBar(snackView, "发送验证码成功");


                    break;


                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };
    private YanzhengmaTimer task;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_changephonenum;
    }

    @Override
    protected void initListener() {
        llTitlebarZuojiantou.setOnClickListener(this);
        btnChangephonenumGochange.setOnClickListener(this);
        btnChangephonenumGetyanzhengma.setOnClickListener(this);
    }

    @Override
    protected void initialized() {
        tvTitlebarCentertext.setText(R.string.setting_genghuanshoujihao);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_titlebar_zuojiantou:                   //左箭头
                finish();
                break;
            case R.id.btn_changephonenum_getyanzhengma:         //验证码按钮
                getYanZhengMa();
                break;
            case R.id.btn_changephonenum_gochange:              //确认修改手机号码
                changePhoneNum();
                break;
        }
    }

    Integer time = 60;

    /**
     * 获取验证码
     */
    private void getYanZhengMa() {

        if (TextUtils.isEmpty(MethodUtils.getTextContent(etChangephonenumPhonenum))) {
            ToastUtils.showSnackBar(snackView, "请输入手机号码");
            return;
        }
        if (!MethodUtils.getTextContent(etChangephonenumPhonenum).matches(Constant.PHONENUM_REGEX)) {
            ToastUtils.showSnackBar(snackView, "请输入正确的手机号码");
            return;
        }
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("userName", MethodUtils.getTextContent(etChangephonenumPhonenum));
        httpDatas.getData("获取修改密码验证码", Request.Method.GET, UrlBuilder.SMS_COOD, map, mHandler, RequestCode.SMS_ALTERPHONE_COOD);


        if (time == 60) {
            Timer timer = new Timer();
            task = new YanzhengmaTimer();
            timer.schedule(task, new Date(), 1000L);
        }
    }

    /**
     * 校验此验证码是否是收到的
     *
     * @param yanZhengMa
     * @return
     */
    private boolean checkYanZhengMa(String yanZhengMa) {

        return true;
    }


    /**
     * 更改手机号
     */
    private void changePhoneNum() {
        String yanzhengma = MethodUtils.getTextContent(etChangephonenumYanzhengma);

        if (TextUtils.isEmpty(MethodUtils.getTextContent(etChangephonenumPhonenum))) {
            ToastUtils.showSnackBar(snackView, "请输入你的手机号");
            return;
        }
        if (!MethodUtils.getTextContent(etChangephonenumPhonenum).matches(Constant.PHONENUM_REGEX)) {
            ToastUtils.showSnackBar(snackView, "请输入正确的手机号码");
            return;
        }
        if (TextUtils.isEmpty(MethodUtils.getTextContent(etChangephonenumPhonenum))) {
            ToastUtils.showSnackBar(snackView, "请输入你的新手机号");
            return;
        }
        if (!MethodUtils.getTextContent(etChangephonenumnewpphonenum).matches(Constant.PHONENUM_REGEX)) {
            ToastUtils.showSnackBar(snackView, "请输入新手机号码的格式");
            return;
        }


        if (com.lvshandian.asktoask.utils.TextUtils.isEmpty(yanzhengma)) {

            ToastUtils.showSnackBar(snackView, "请输入验证码");
            return;
        }


        String oldPhoneNum = MethodUtils.getTextContent(etChangephonenumPhonenum);
        String newPhoneNum = MethodUtils.getTextContent(etChangephonenumnewpphonenum);

        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("userName", oldPhoneNum);
        map.put("newUserName", newPhoneNum);
        map.put("smsCode", yanzhengma);
        httpDatas.getData("更改手机号", Request.Method.POST, UrlBuilder.MINE_XIUGAISHOUJIHAO_URL, map, mHandler, RequestCode.MINE_XIUGAISHOUJIHAO_REQUESTCODE);


    }

    private class YanzhengmaTimer extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (time >= 0) {
                        btnChangephonenumGetyanzhengma.setText(getString(R.string.setting_genghuanshoujihao_daojishitext, time--));
                        btnChangephonenumGetyanzhengma.setClickable(false);
                    } else {
                        time = 60;
                        btnChangephonenumGetyanzhengma.setText("获取验证码");
                        btnChangephonenumGetyanzhengma.setClickable(true);
                        task.cancel();
                    }
                }
            });
        }
    }

}
