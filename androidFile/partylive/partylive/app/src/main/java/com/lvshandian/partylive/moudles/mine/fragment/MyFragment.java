package com.lvshandian.partylive.moudles.mine.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.base.BaseFragment;
import com.lvshandian.partylive.bean.AppUser;
import com.lvshandian.partylive.interf.ResultListener;
import com.lvshandian.partylive.moudles.mine.activity.ChargeMoneyActivity;
import com.lvshandian.partylive.moudles.mine.activity.ExchangeCenterActivity;
import com.lvshandian.partylive.moudles.mine.bean.BuyMember;
import com.lvshandian.partylive.moudles.mine.my.AuthenticationActivity;
import com.lvshandian.partylive.moudles.mine.my.GongXianActivity;
import com.lvshandian.partylive.moudles.mine.my.InviteFriendActivity;
import com.lvshandian.partylive.moudles.mine.my.OpenMemberActivity;
import com.lvshandian.partylive.moudles.mine.my.RealNameVertifyActivity;
import com.lvshandian.partylive.moudles.mine.my.RecentVisitorsActivity;
import com.lvshandian.partylive.moudles.mine.my.SettingActivity;
import com.lvshandian.partylive.utils.CacheUtils;
import com.lvshandian.partylive.utils.GuanZhuUtils;
import com.lvshandian.partylive.utils.JsonUtil;
import com.lvshandian.partylive.utils.LogUtils;
import com.lvshandian.partylive.view.CustomPopWindow;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 首页左边关注
 */
public class MyFragment extends BaseFragment implements View.OnClickListener {

    @Bind(R.id.tv_coin)
    TextView tvCoin;
    @Bind(R.id.ll_coin)
    AutoLinearLayout llCoin;
    @Bind(R.id.tv_caifu)
    TextView tvCaifu;
    @Bind(R.id.ll_caifu)
    AutoLinearLayout llCaifu;
    @Bind(R.id.ll_gongxian)
    AutoLinearLayout llGongxian;
    @Bind(R.id.ll_zjfk)
    AutoLinearLayout llZjfk;
    @Bind(R.id.ll_invitefriend)
    AutoLinearLayout llInvitefriend;
    @Bind(R.id.ll_vip)
    AutoLinearLayout llVip;
    @Bind(R.id.ll_earnest)
    AutoLinearLayout llEarnest;
    @Bind(R.id.ll_setting)
    AutoLinearLayout llSetting;
    @Bind(R.id.btn_charge)
    TextView btnCharge;
    @Bind(R.id.tv_member)
    TextView tvMember;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void initListener() {
        llCoin.setOnClickListener(this);
        llCaifu.setOnClickListener(this);
        llSetting.setOnClickListener(this);
        llEarnest.setOnClickListener(this);
        llVip.setOnClickListener(this);
        llGongxian.setOnClickListener(this);
        llZjfk.setOnClickListener(this);
    }

    @Override
    protected void initialized() {
        EventBus.getDefault().register(this);
        llInvitefriend.setOnClickListener(this);
        FragmentActivity activity = getActivity();
        initUser();
    }

    private void initUser() {
        final AppUser userInfo = (AppUser) CacheUtils.readObject(mContext, CacheUtils.USERINFO);
        if (mContext != null && userInfo != null) {
            GuanZhuUtils.newInstance().personInfo(mContext, userInfo.getId(), new ResultListener() {
                @Override
                public void onSucess(String data) {
                    LogUtils.e("用户信息Fragment: "+data);
                    AppUser user = JsonUtil.json2Bean(data, AppUser.class);
                    if (user != null) {
                        initInfo(user);
                        CacheUtils.saveObject(mContext, user, CacheUtils.USERINFO);
                    } else {
                        initInfo(userInfo);
                    }
                }

                @Override
                public void onFaild() {
                    initInfo(userInfo);
                }
            });
        }
    }

    /**
     * 用户信息
     *
     * @param userInfo
     */
    private void initInfo(AppUser userInfo) {
        if (userInfo != null) {
            String goldCoin = userInfo.getGoldCoin();
            tvCoin.setText("金币: " + goldCoin);
            tvCaifu.setText("财富:"+userInfo.getReceivedGoldCoin());

            String vip = userInfo.getVip();
            if (TextUtils.equals(vip, "1")) {
                String vipTime = userInfo.getVipTime();
                if (!TextUtils.isEmpty(vipTime)) {
                    tvMember.setText("会员  有效期至:" + vipTime);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //邀请好友
            case R.id.ll_invitefriend:
                gotoActivity(InviteFriendActivity.class, false);
                break;
            case R.id.ll_coin:
                gotoActivity(ChargeMoneyActivity.class, false);
                break;
            case R.id.ll_caifu:
                gotoActivity(ExchangeCenterActivity.class, false);
                break;
            case R.id.ll_setting:
                gotoActivity(SettingActivity.class, false);
                break;
            case R.id.ll_earnest:
                //认证
                AppUser userInfo = (AppUser) CacheUtils.readObject(mContext, CacheUtils.USERINFO);
                String verified = userInfo.getVerified();
                if (TextUtils.equals(verified, "0")) {
                    //未认证
                    gotoActivity(AuthenticationActivity.class, false);
                } else {
                    //已提交认证
                    gotoActivity(RealNameVertifyActivity.class, false);
                }
                break;
            case R.id.ll_vip:
                gotoActivity(OpenMemberActivity.class, false);
                break;
            case R.id.ll_zjfk:
                // showDialog(v);
                gotoActivity(RecentVisitorsActivity.class, false);
                break;
            case R.id.ll_gongxian:
                Intent intent = new Intent(mContext, GongXianActivity.class);
                intent.putExtra(getString(R.string.user_id), appUser.getId());
                startActivity(intent);
                break;
        }
    }

    /**
     * 显示对话框
     *
     * @param v
     */
    private void showDialog(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.dialog_video_room, null);
        AlertDialog alertDialog = builder.create();
        alertDialog.setView(inflate);
        //  alertDialog.show();

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = FrameLayout.LayoutParams.WRAP_CONTENT;
        CustomPopWindow popupWindow = new CustomPopWindow(inflate, width * 3 / 4, height, getActivity());
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }



    @Subscribe
    public void onEventMainThread(BuyMember member){
        initUser();
    }
}
