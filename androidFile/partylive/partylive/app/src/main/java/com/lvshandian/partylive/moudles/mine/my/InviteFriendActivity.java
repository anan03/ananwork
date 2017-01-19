package com.lvshandian.partylive.moudles.mine.my;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.UrlBuilder;
import com.lvshandian.partylive.base.BaseActivity;
import com.lvshandian.partylive.base.Constant;
import com.lvshandian.partylive.base.CustomStringCallBack;
import com.lvshandian.partylive.bean.AppUser;
import com.lvshandian.partylive.bean.InviteUserBean;
import com.lvshandian.partylive.bean.ResultBean;
import com.lvshandian.partylive.httprequest.HttpDatas;
import com.lvshandian.partylive.lib.cjj.MaterialRefreshLayout;
import com.lvshandian.partylive.lib.cjj.MaterialRefreshListener;
import com.lvshandian.partylive.moudles.mine.my.adapter.InviteUserAdapter;
import com.lvshandian.partylive.utils.CacheUtils;
import com.lvshandian.partylive.utils.JsonUtil;
import com.lvshandian.partylive.utils.LogUtils;
import com.lvshandian.partylive.utils.TextUtils;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.umeng.socialize.bean.CustomPlatform;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.BaseShareContent;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.TencentWbShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SmsHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMTencentSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * 邀请好友页面
 * Created by zz on 2016/11/10.
 */

public class InviteFriendActivity extends BaseActivity {
    @Bind(R.id.tv_my_ydcode)
    TextView tvMyYdcode;
    @Bind(R.id.btn_invite)
    Button btnInvite;
    @Bind(R.id.lv_list)
    ListView lvList;
    @Bind(R.id.mrl_layout)
    MaterialRefreshLayout mrlLayout;
    private TextView tv_back;

    private List<ResultBean> mDatas = new ArrayList<>();

    private AppUser userInfo;
    private InviteUserAdapter mAdapter;
    private RotateAnimation mAnim;
    private ImageView ivAnimation;
    private TextView tvFooter;
    private UMSocialService mController;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_invitefriend;
    }

    @Override
    protected void initListener() {
        btnInvite.setOnClickListener(this);
        mrlLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                page = 1;//下拉刷新，每次都从第一页开始请求
                requestYaoQingRen(true);

            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                requestYaoQingRen(false);
            }
        });
    }

    private int page = 1;

    /**
     * 上拉刷新和下拉加载的请求
     *
     * @param isRefresh 是否是下拉刷新
     *                  true 下拉刷新
     *                  false 上拉加载
     */
    public void requestYaoQingRen(final boolean isRefresh) {
       /* if (mAnim != null && ivAnimation != null) {
            ivAnimation.setVisibility(View.VISIBLE);
            ivAnimation.startAnimation(mAnim);
            tvFooter.setText(isRefresh ? "正在刷新" : "正在加载");
        }*/

        String url = UrlBuilder.serverUrl + UrlBuilder.yqRen;
        Log.e("lsd", "用户信息: " + userInfo);
        if (userInfo != null) {
            String id = userInfo.getId();
            url += id;
            url = url + "/" + "referrals";
            url = url + "?pageNum=" + page;
            LogUtils.e("邀请人信息: " + url);
            OkHttpUtils.get().url(url).build().execute(new CustomStringCallBack(mContext, HttpDatas.KEY_CODE) {
                @Override
                public void onFaild() {
                    showToast("加载失败");
                 /*   ivAnimation.clearAnimation();
                    mAnim.cancel();
                    ivAnimation.setVisibility(View.GONE);
                    tvFooter.setText("下拉刷新上拉加载");*/
                }

                @Override
                public void onSucess(String data) {
                    page++;//每次请求完毕后请求页数+1
                    LogUtils.e("邀请人data: " + data);
                    InviteUserBean inviteUserBean = JsonUtil.json2Bean(data, InviteUserBean.class);
                    handInviteUserBean(inviteUserBean, isRefresh);
                    showToast(isRefresh ? "刷新完毕" : "加载完毕");
                    mrlLayout.finishRefreshLoadMore();
                    mrlLayout.finishRefresh();
                  /*  ivAnimation.clearAnimation();
                    mAnim.cancel();
                    ivAnimation.setVisibility(View.GONE);
                    tvFooter.setText("下拉刷新上拉加载");*/
                }
            });
        }
    }

    /**
     * 处理邀请人
     *
     * @param inviteUserBean
     * @param isRefresh
     */
    private void handInviteUserBean(InviteUserBean inviteUserBean, boolean isRefresh) {
        if (inviteUserBean == null) {
            showToast("加载完毕");
            return;
        }
        List<ResultBean> result = inviteUserBean.getResult();
        if (isRefresh) {
            mDatas.clear();
        }
        if (result != null && result.size() > 0) {
            mDatas.addAll(result);
        } else {
            showToast("加载完毕");
        }
        mAdapter.notifyDataSetChanged();
    }


    @Override
    protected void initialized() {
        mController = UMServiceFactory.getUMSocialService("com.umeng.share");
        um43Share();
        initTitle("", "邀请好友", "");
        initAdapter();
        initShareCode();

        initAnimation();
        mrlLayout.setLoadMore(true);
        View footerView = LayoutInflater.from(mContext).inflate(R.layout.footer_view, null);
        ivAnimation = (ImageView) footerView.findViewById(R.id.iv_animation);
        tvFooter = (TextView) footerView.findViewById(R.id.tv_footer);
        lvList.addFooterView(footerView);
        footerView.setOnClickListener(this);
        requestYaoQingRen(true);
    }

    private final String share_title = "分享是真爱,你是我的菜\n";
    private String share_content = "";
    private final String share_url = "http://show.partylive.cn/";
    private String livePicUrl = "";

    private void um43Share() {

        AppUser appUser = (AppUser) CacheUtils.readObject(mContext, CacheUtils.USERINFO);
        mController.getConfig().closeToast();
        share_content = "分享是真爱,你是我的菜!" + appUser.getNickName() + "正在直播,快来一起看~";
        livePicUrl = appUser.getLivePicUrl();

        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, Constant.QQ_APPID, Constant.QQ_SECRET);
        qqSsoHandler.setTitle(share_title);
        qqSsoHandler.setTargetUrl(share_url);
        qqSsoHandler.addToSocialSDK();

        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this, Constant.QQ_APPID, Constant.QQ_SECRET);
        qZoneSsoHandler.setTargetUrl(share_url);
        qZoneSsoHandler.addToSocialSDK();


        /**
         * 微信
         */

        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(this, Constant.WX_APPID, Constant.WX_SECRET);
        wxHandler.setTargetUrl(share_url);
        wxHandler.setTitle(share_title);
        wxHandler.addToSocialSDK();
        wxHandler.showCompressToast(false);
        // 添加微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(this, Constant.WX_APPID, Constant.WX_SECRET);

        wxCircleHandler.setTargetUrl(share_url);
        wxCircleHandler.setTitle(share_title);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
        wxCircleHandler.showCompressToast(false);

        SmsHandler smsHandler = new SmsHandler();
        smsHandler.setTargetUrl(share_url);
        smsHandler.addToSocialSDK();


        UMTencentSsoHandler tencentSsoHandler=new UMTencentSsoHandler(InviteFriendActivity.this,Constant.TXWB_APPID,Constant.TXWB_SECRET) {
            @Override
            protected void initResource() {

            }

            @Override
            public void authorize(Activity activity, SocializeListeners.UMAuthListener umAuthListener) {

            }

            @Override
            protected void handleOnClick(CustomPlatform customPlatform, SocializeEntity socializeEntity, SocializeListeners.SnsPostListener snsPostListener) {

            }

            @Override
            protected void sendReport(boolean b) {

            }

            @Override
            public int getRequstCode() {
                return 0;
            }
        };
        tencentSsoHandler.setTargetUrl(share_url);
        tencentSsoHandler.addToSocialSDK();


        mController.setShareContent(share_content);
        mController.setShareImage(new UMImage(mContext, livePicUrl));
        mController.getConfig().removePlatform(SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN, SHARE_MEDIA.SINA, SHARE_MEDIA.TENCENT);
    }


    /**
     * 动画初始
     */
    private void initAnimation() {
        mAnim = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mAnim.setDuration(1000 * 2);
        mAnim.setRepeatCount(-1);
        mAnim.setRepeatMode(Animation.REVERSE);
    }

    /**
     * 适配器初始
     */
    private void initAdapter() {
        mAdapter = new InviteUserAdapter(mContext, mDatas, R.layout.item_invite_user);
        lvList.setAdapter(mAdapter);
    }

    /**
     * 初始邀请码
     */
    private void initShareCode() {
        userInfo = (AppUser) CacheUtils.readObject(mContext, CacheUtils.USERINFO);
        if (userInfo != null) {
            String shareCode = userInfo.getShareCode();
            if (!TextUtils.isEmpty(shareCode)) {
                tvMyYdcode.setText("我的邀请码:" + shareCode);
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_titlebar_left:
                finish();
                break;
            case R.id.btn_invite:
                mController.openShare(this, false);
                break;
            case R.id.all_footer:
                mrlLayout.autoRefresh();
                break;
        }
    }
}
