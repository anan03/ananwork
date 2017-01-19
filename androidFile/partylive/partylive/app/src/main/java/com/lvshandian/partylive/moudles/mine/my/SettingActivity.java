package com.lvshandian.partylive.moudles.mine.my;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lvshandian.partylive.MainActivity;
import com.lvshandian.partylive.MyApplication;
import com.lvshandian.partylive.R;
import com.lvshandian.partylive.base.BaseActivity;
import com.lvshandian.partylive.bean.QuitLogin;
import com.lvshandian.partylive.moudles.mine.bean.LoginFrom;
import com.lvshandian.partylive.moudles.start.LoginActivity;
import com.lvshandian.partylive.moudles.start.LogoutHelper;
import com.lvshandian.partylive.utils.CacheUtils;
import com.lvshandian.partylive.utils.FileCacheUtils;
import com.lvshandian.partylive.view.RoundDialog;
import com.lvshandian.partylive.wangyiyunxin.config.preference.Preferences;
import com.lvshandian.partylive.wangyiyunxin.contact.activity.*;
import com.lvshandian.partylive.wangyiyunxin.session.SessionHelper;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.UMComment;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;

import butterknife.Bind;

/**
 * Created by gjj on 2016/11/18.
 * 设置页面
 */

public class SettingActivity extends BaseActivity {
    @Bind(R.id.all_change_key)
    AutoLinearLayout allChangeKey;
    @Bind(R.id.all_black_list)
    AutoLinearLayout allBlackList;
    @Bind(R.id.all_system_message)
    AutoLinearLayout allSystemMessage;
    @Bind(R.id.all_say_tips)
    AutoLinearLayout allSayTips;
    @Bind(R.id.tv_cache)
    TextView tvCache;
    @Bind(R.id.all_clear_cache)
    AutoLinearLayout allClearCache;
    @Bind(R.id.btn_quit)
    Button btnQuit;
    private RoundDialog mQuitDialog;
    private RoundDialog mCacheDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initListener() {
        allChangeKey.setOnClickListener(this);
        allBlackList.setOnClickListener(this);
        allSystemMessage.setOnClickListener(this);
        allClearCache.setOnClickListener(this);
        btnQuit.setOnClickListener(this);
        allSayTips.setOnClickListener(this);
    }

    @Override
    protected void initialized() {
        initTitle("", "设置", null);
        setCache();
        initQuitDialog();
        initClearCacheDialog();
        LoginFrom from = (LoginFrom) CacheUtils.readObject(this, CacheUtils.PASSWORD);
        if (from != null) {
            boolean thirdLogin = from.isThirdLogin();
            if (thirdLogin) {
                allChangeKey.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_titlebar_left:
                defaultFinish();
                break;
            case R.id.all_change_key:
                //修改密码
                modifyPassword();
                break;
            case R.id.all_black_list:
                //黑名单
                blackList();
                break;
            case R.id.all_system_message:
                //系统消息
                systemMessage();
                break;
            case R.id.all_say_tips:
                //意见反馈
                sayTips();
                break;
            case R.id.all_clear_cache:
                //清除缓存
                if (mCacheDialog != null && !mCacheDialog.isShowing()) {
                    mCacheDialog.show();
                }

                break;
            case R.id.btn_quit:
                //退出登录
                if (mQuitDialog != null && !mQuitDialog.isShowing()) {
                    mQuitDialog.show();
                }
                break;
        }
    }

    /**
     * 退出登录
     */
    private void quitLogin() {
        logout();
        //清空已保存的用户信息
        CacheUtils.saveObject(this, null, CacheUtils.PASSWORD);
        CacheUtils.saveObject(mContext, null, CacheUtils.USERINFO);
        //发送到MainActivity，关闭页面
        EventBus.getDefault().post(new QuitLogin());
        //开启登录页面
        gotoActivity(LoginActivity.class, true);

        UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");


       /* mController.deleteOauth(this, SHARE_MEDIA.WEIXIN, new SocializeListeners.SocializeClientListener() {
            @Override
            public void onStart() {
                Toast.makeText(mContext,"正在解除授权",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete(int i, SocializeEntity socializeEntity) {
                Toast.makeText(mContext,"解除授权成功",Toast.LENGTH_SHORT).show();
            }
        });*/


    }

    /**
     * 注销
     */
    private void logout() {
        LogoutHelper.logout();
        removeLoginState();
        NIMClient.getService(AuthService.class).logout();
    }

    /**
     * 清除登陆状态
     */
    private void removeLoginState() {
        Preferences.saveUserToken("");
    }


    /**
     * 显示缓存
     */
    private void setCache() {
        String size = FileCacheUtils.getCacheSize(mContext);
        if (size != null && size.equals("0.0Byte")) {
            size = "0KB";
        }
        if (tvCache != null)
            tvCache.setText(size);
    }


    /**
     * 清除缓存
     */
    private void clearCache() {
        FileCacheUtils.cleanApplicationData(mContext);
        setCache();
    }

    /**
     * 系统消息
     */
    private void systemMessage() {
        gotoActivity(SystemMessageActivity.class, false);
    }

    /**
     * 意见反馈
     *
     * @author sll
     * @time 2016/12/1 10:12
     */
    private void sayTips() {
        SessionHelper.startP2PSession(this, "miu_12877");
    }

    /**
     * 黑名单
     */
    private void blackList() {
//        gotoActivity(BlackListActivity.class, false);
        com.lvshandian.partylive.wangyiyunxin.contact.activity.BlackListActivity.start(this);
    }

    /**
     * 修改密码
     */
    private void modifyPassword() {
        gotoActivity(ModifyPasswordActivity.class, false);
    }


    /**
     * 确认退出对话框
     */
    private void initQuitDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_quit_login, null);
        mQuitDialog = new RoundDialog(this, view, R.style.dialog, 0.66f, 0.2f);
        mQuitDialog.setCanceledOnTouchOutside(false);

        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView tvSure = (TextView) view.findViewById(R.id.tv_sure);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mQuitDialog != null && mQuitDialog.isShowing()) {
                    mQuitDialog.dismiss();
                }
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mQuitDialog != null && mQuitDialog.isShowing()) {
                    mQuitDialog.dismiss();
                }
                quitLogin();
            }
        });
    }


    /**
     * 清除缓存对话框
     */
    private void initClearCacheDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_quit_login, null);
        mCacheDialog = new RoundDialog(this, view, R.style.dialog, 0.66f, 0.2f);
        mCacheDialog.setCanceledOnTouchOutside(false);

        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView tvSure = (TextView) view.findViewById(R.id.tv_sure);

        tvTitle.setText("确认清除本地缓存?");
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCacheDialog != null && mCacheDialog.isShowing()) {
                    mCacheDialog.dismiss();
                }
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCacheDialog != null && mCacheDialog.isShowing()) {
                    mCacheDialog.dismiss();
                }
                clearCache();
            }
        });
    }


    @Subscribe
    public void onEventMainThread(QuitLogin quit) {
        //接收自ModifyPasswordActivity 修改密码成功后退出登录使用
        finish();
    }
}
