package com.lvshandian.menshen.login;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Telephony;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.lvshandian.menshen.MainActivity;
import com.lvshandian.menshen.R;
import com.lvshandian.menshen.UrlBuilder;
import com.lvshandian.menshen.base.BaseActivity;
import com.lvshandian.menshen.bean.Keyworkinfo;
import com.lvshandian.menshen.bean.SmsBean;
import com.lvshandian.menshen.bean.SmsInfo;
import com.lvshandian.menshen.bean.User;
import com.lvshandian.menshen.httprequest.HttpDatas;
import com.lvshandian.menshen.httprequest.RequestCode;
import com.lvshandian.menshen.utils.Constant;
import com.lvshandian.menshen.utils.EncryptUtils;
import com.lvshandian.menshen.utils.JsonUtil;
import com.lvshandian.menshen.utils.LogUtils;
import com.lvshandian.menshen.utils.Sp;
import com.lvshandian.menshen.utils.TextUtils;
import com.lvshandian.menshen.utils.XUtils;
import com.lvshandian.menshen.view.ToastUtils;
import com.tandong.sa.zUImageLoader.utils.L;

import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 创建登录界面
 */
public class LoginActivity extends BaseActivity {


    @Bind(R.id.et_name)
    EditText etName;
    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.et_password)
    EditText etPassword;
    @Bind(R.id.tv_password)
    TextView tvPassword;
    @Bind(R.id.bt_login)
    Button btLogin;
    @Bind(R.id.bt_register)
    Button btRegister;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle data = msg.getData();
            int tagCode = data.getInt(HttpDatas.code);
            if (tagCode == RequestCode.REQUEST_CODE) {
                ToastUtils.showSnackBar(snackView, data.getString(HttpDatas.info));
                return;
            }
            switch (msg.what) {
                case RequestCode.LOGIN_TAG://登录成功执行的操作进行跳转
                    etPassword.setText("");
                    etName.setText("");
                    //将数据存储到数据 库
                    String dataString = data.getString(HttpDatas.info);
                    LogUtils.d(dataString);
                    User user = JsonUtil.json2Bean(dataString, User.class);
                    XUtils.dropTable(SmsBean.class);
                    XUtils.dropTable(Keyworkinfo.class);
                    XUtils.dropTable(User.class);
                    XUtils.addTable(user);
                    LogUtils.e("登录成功---user" + user.toString());

                    Sp.setParam(mContext, Constant.IS_LOGIN, true);
                    gotoActivity(MainActivity.class, true);
                    break;

                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.login_activity_login;
    }

    @Override
    protected void initListener() {
        //初始话监听
        btLogin.setOnClickListener(this);
        btRegister.setOnClickListener(this);
        tvPassword.setOnClickListener(this);

    }

    @Override
    protected void initialized() {

        if ((boolean) Sp.getParam(mContext, Constant.IS_LOGIN, false)) {
            gotoActivity(MainActivity.class, true);
        }

        tvPassword.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        etPassword.setText("");
        etName.setText("");
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tv_password://忘记密码
                etPassword.setText("");
                etName.setText("");
                gotoActivity(ForgetPasswordActivity.class, false);
                break;
            case R.id.bt_register://注册账号
                etPassword.setText("");
                etName.setText("");
                gotoActivity(registerActivity.class, false);
                break;
            case R.id.bt_login://登录
                login();
                break;
        }

    }

    //登录
    private void login() {

        String phonenum = TextUtils.getTextContent(etName);
        String passWord = TextUtils.getTextContent(etPassword);

        //临时帐号密码开始
//        String phonenum = "13264013833";
//        String passWord = "qwerty";
        //临时帐号密码结束

        if (TextUtils.isEmpty(phonenum)) {
            ToastUtils.showSnackBar(snackView, "手机号不能为空");
            return;
        }
        if (!TextUtils.isPhone(phonenum)) {
            ToastUtils.showSnackBar(snackView, "请输入正确的手机号码");
            return;
        }
        if (TextUtils.isEmpty(passWord)) {
            ToastUtils.showSnackBar(snackView, "密码不能为空");
            return;
        }
        if (passWord.length() < 6) {
            ToastUtils.showSnackBar(snackView, "密码最少6位");
            return;
        }


        //密码进行加密
        passWord = EncryptUtils.md5(passWord);
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("phone", phonenum);
        map.put("password", passWord);
        httpDatas.getData("系统登录", Request.Method.POST, UrlBuilder.LOGIN_URL, map, mHandler, RequestCode.LOGIN_TAG);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}


