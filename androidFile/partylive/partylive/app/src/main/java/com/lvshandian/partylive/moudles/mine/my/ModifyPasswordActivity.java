package com.lvshandian.partylive.moudles.mine.my;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.lvshandian.partylive.R;
import com.lvshandian.partylive.base.BaseActivity;
import com.lvshandian.partylive.bean.AppUser;
import com.lvshandian.partylive.bean.QuitLogin;
import com.lvshandian.partylive.httprequest.HttpDatas;
import com.lvshandian.partylive.httprequest.RequestCode;
import com.lvshandian.partylive.moudles.mine.bean.LoginFrom;
import com.lvshandian.partylive.moudles.start.LoginActivity;
import com.lvshandian.partylive.utils.CacheUtils;
import com.lvshandian.partylive.utils.LogUtils;
import com.lvshandian.partylive.utils.MD5Utils;
import com.lvshandian.partylive.utils.TextUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by gjj on 2016/11/18.
 * 修改密码
 */

public class ModifyPasswordActivity extends BaseActivity {
    @Bind(R.id.et_old_password)
    EditText etOldPassword;
    @Bind(R.id.et_new_password)
    EditText etNewPassword;
    @Bind(R.id.et_repeat_password)
    EditText etRepeatPassword;
    @Bind(R.id.btn_save)
    Button btnSave;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RequestCode.MODIFY_PASSWORD:
                    String info = msg.getData().getString(HttpDatas.info);
                    LogUtils.e("修改密码: " + info);
                    quitLogin();
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_password;
    }

    @Override
    protected void initListener() {
        btnSave.setOnClickListener(this);
    }

    @Override
    protected void initialized() {
        initTitle("", "修改密码", null);
    }

    /**
     * 修改密码成功后，退出登录
     */
    private void quitLogin() {
        //清空已保存的用户信息
        CacheUtils.saveObject(mContext, null, CacheUtils.USERINFO);
        //发送到MainActivity，关闭页面
        EventBus.getDefault().post(new QuitLogin());
        //开启登录页面
        gotoActivity(LoginActivity.class, true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_titlebar_left:
                defaultFinish();
                break;
            case R.id.btn_save:
                if (checkInfo()) {
                    saveNewPass();
                }
                break;
        }
    }

    /**
     * 保存新密码
     */
    private void saveNewPass() {
        Map<String, String> params = new HashMap<>();
        params.put("id", appUser.getId());
        params.put("password", etNewPassword.getText().toString().trim());
        httpDatas.getDataForJson("修改密码", Request.Method.POST, urlBuilder.modifyPass, params, handler, RequestCode.MODIFY_PASSWORD);
    }


    /**
     * 检查信息
     *
     * @return
     */
    private boolean checkInfo() {
        AppUser userInfo = (AppUser) CacheUtils.readObject(mContext, CacheUtils.USERINFO);
        if (userInfo != null) {


            String etOldPass = etOldPassword.getText().toString().trim();
            if (TextUtils.isEmpty(etOldPass)) {
                showToast("请输入旧密码");
                return false;
            }
            LoginFrom from = (LoginFrom) CacheUtils.readObject(this, CacheUtils.PASSWORD);
            if (from != null) {
                boolean thirdLogin = from.isThirdLogin();
                if (!thirdLogin) {
                    String oldPassword = from.getPassword();
                    LogUtils.e("oldPassWord: " + oldPassword);
                    LogUtils.e("etOldPass: " + etOldPass);
                    if (!android.text.TextUtils.equals(oldPassword, etOldPass)) {
                        showToast("旧密码不正确");
                        return false;
                    }
                }
            }



            String newPass = etNewPassword.getText().toString().trim();
            String repeatPass = etRepeatPassword.getText().toString().trim();
            if (android.text.TextUtils.isEmpty(newPass)) {
                showToast("请输入新密码");
                return false;
            }
            if (android.text.TextUtils.isEmpty(repeatPass)) {
                showToast("请确认新密码");
                return false;
            }
            if (!android.text.TextUtils.equals(repeatPass, newPass)) {
                showToast("两次输入的密码不一致");
                return false;
            }
        }
        return true;
    }
}
