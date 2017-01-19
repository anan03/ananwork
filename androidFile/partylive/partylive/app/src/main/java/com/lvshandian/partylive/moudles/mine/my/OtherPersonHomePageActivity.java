package com.lvshandian.partylive.moudles.mine.my;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.UrlBuilder;
import com.lvshandian.partylive.base.BaseActivity;
import com.lvshandian.partylive.base.CustomStringCallBack;
import com.lvshandian.partylive.httprequest.HttpDatas;
import com.lvshandian.partylive.interf.ResultListener;
import com.lvshandian.partylive.moudles.index.adapter.ViewPageFragmentAdapter;
import com.lvshandian.partylive.moudles.mine.bean.OtherPersonBean;
import com.lvshandian.partylive.moudles.mine.fragment.OrtherPersonImagaFragment;
import com.lvshandian.partylive.moudles.mine.fragment.OrtherPersonMyFragment;
import com.lvshandian.partylive.moudles.mine.fragment.OrtherVideoMyFragment;
import com.lvshandian.partylive.utils.GrademipmapUtils;
import com.lvshandian.partylive.utils.GuanZhuUtils;
import com.lvshandian.partylive.utils.JsonUtil;
import com.lvshandian.partylive.utils.LogUtils;
import com.lvshandian.partylive.utils.PicassoUtil;
import com.lvshandian.partylive.view.ToastUtils;
import com.lvshandian.partylive.wangyiyunxin.session.SessionHelper;
import com.lvshandian.partylive.widget.AvatarView;
import com.lvshandian.partylive.widget.PagerSlidingTabStrip;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.friend.FriendService;
import com.squareup.okhttp.MediaType;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by gjj on 2016/11/23.
 */

public class OtherPersonHomePageActivity extends BaseActivity {
    @Bind(R.id.av_header)
    AvatarView avHeader;
    @Bind(R.id.iv_sex)
    ImageView ivSex;
    @Bind(R.id.iv_grade)
    ImageView ivGrade;
    @Bind(R.id.top)
    AutoRelativeLayout top;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_sign)
    TextView tvSign;
    @Bind(R.id.tv_fanse)
    TextView tvFanse;
    @Bind(R.id.tv_foucs)
    TextView tvFoucs;
    @Bind(R.id.pst_indicator)
    PagerSlidingTabStrip pstIndicator;
    @Bind(R.id.vp_pager)
    ViewPager vpPager;
    @Bind(R.id.btn_focus)
    Button btnFocus;
    @Bind(R.id.btn_video)
    Button btnVideo;
    @Bind(R.id.btn_talk)
    Button btnTalk;
    @Bind(R.id.btn_black)
    Button btnBlack;
    private String userId;
    private OtherPersonBean mOtherBean;
    //标记是否被拉黑
    private boolean black;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_other_person_page;
    }

    @Override
    protected void initListener() {
        btnFocus.setOnClickListener(this);
        btnVideo.setOnClickListener(this);
        btnTalk.setOnClickListener(this);
        btnBlack.setOnClickListener(this);
        tvFanse.setOnClickListener(this);
        tvFoucs.setOnClickListener(this);
    }

    public OtherPersonBean getBean() {
        return mOtherBean;
    }

    @Override
    protected void initialized() {
        initTitle("", null, null);
        Intent intent = getIntent();
        userId = intent.getStringExtra(getString(R.string.visiti_person));
        requestVisitorInfo();

        //判断是否拉黑
        black = NIMClient.getService(FriendService.class).isInBlackList("miu_" + userId);
        if (black) {
            btnBlack.setText("取消拉黑");
        } else {
            btnBlack.setText("拉黑");
        }
    }

    /**
     * 获取其他人信息
     */
    /*4.2 获取用户信息
    服务器接口地址：http://miulive.cc:8080/api/v1/user/info post请求 举例http://miulive.cc:8080/api/v1/user/info
    客户端设置Content-Type: application/json;charset=UTF-8
    发送：{"userId":"1","currentUserId":"2"} 其中userId是要查询的用户id，currentUserId是当前用户id*/
    private void requestVisitorInfo() {
        if (userId != null) {
            Map<String, String> jsonMap = new HashMap<>();
            jsonMap.clear();
            jsonMap.put("userId", userId);
            jsonMap.put("currentUserId", appUser.getId());
            JSONObject jsonObject = new JSONObject(jsonMap);
            String json = jsonObject.toString();
            OkHttpUtils
                    .postString()
                    .url(UrlBuilder.serverUrl + UrlBuilder.IF_ATTENTION)
                    .content(json)
                    .mediaType(MediaType.parse("application/json;charset=UTF-8"))
                    .build()
                    .execute(new CustomStringCallBack(mContext, HttpDatas.KEY_CODE) {
                        @Override
                        public void onFaild() {

                        }

                        @Override
                        public void onSucess(String data) {
                            LogUtils.e("访客信息: " + data);

                            mOtherBean = JsonUtil.json2Bean(data, OtherPersonBean.class);
                            initInfo();
                            initView();
                        }
                    });
        }

    }

    private void initInfo() {
        if (mOtherBean != null) {
            String picUrl = mOtherBean.getPicUrl();
            PicassoUtil.newInstance().onRoundnessImage(mContext, picUrl, avHeader);

            String nickName = mOtherBean.getNickName();
            String id = mOtherBean.getId();
            String online = mOtherBean.getOnline();

            tvName.setText(nickName + "[" + (online.endsWith("0") ? "离线" : "在线") + "]" + " ID:" + id);

            String gender = mOtherBean.getGender();
            ivSex.setImageResource(TextUtils.equals(gender, "1") ? R.mipmap.male : R.mipmap.female);

            String gradeSatisfied = mOtherBean.getGradeSatisfied();
            int i = Integer.parseInt(gradeSatisfied);
            ivGrade.setImageResource(GrademipmapUtils.LevelImg[i]);

            String signature = mOtherBean.getSignature();
            tvSign.setText(signature);

            String fansNum = mOtherBean.getFansNum();
            tvFanse.setText("粉丝:" + fansNum);

            String followNum = mOtherBean.getFollowNum();
            tvFoucs.setText("关注:" + followNum);

            focus();
        }
    }

    /**
     * 显示关注(未关注)
     */
    private void focus() {
        String follow = mOtherBean.getFollow();
        if (TextUtils.equals("0", follow)) {
            btnFocus.setText("未关注");
        } else if (TextUtils.equals("1", follow)) {
            btnFocus.setText("已关注");
        }
    }

    private void initView() {

        ViewPageFragmentAdapter viewPageFragmentAdapter = new ViewPageFragmentAdapter(getSupportFragmentManager(), vpPager);
        viewPageFragmentAdapter.addTab(getString(R.string.my), "my", OrtherPersonMyFragment.class, null);
        viewPageFragmentAdapter.addTab(getString(R.string.photo), "xc", OrtherPersonImagaFragment.class, null);
        viewPageFragmentAdapter.addTab(getString(R.string.smallvideo), "dp", OrtherVideoMyFragment.class, null);
        vpPager.setAdapter(viewPageFragmentAdapter);
        vpPager.setOffscreenPageLimit(3);
        pstIndicator.setViewPager(vpPager);
        pstIndicator.setUnderlineColor(getResources().getColor(R.color.global));
        pstIndicator.setDividerColor(getResources().getColor(R.color.global));
        pstIndicator.setIndicatorColor(getResources().getColor(R.color.chose));
        pstIndicator.setTextColor(Color.WHITE);
        pstIndicator.setTextSize(15);
        pstIndicator.setSelectedTextColor(getResources().getColor(R.color.chose));
        pstIndicator.setIndicatorHeight(2);
        pstIndicator.setZoomMax(0f);

        vpPager.setCurrentItem(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_titlebar_left:
                defaultFinish();
                break;
            case R.id.btn_focus:
                guanZhu();
                break;
            case R.id.btn_video:
                Toast.makeText(mContext, "视频", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_talk:
                SessionHelper.startP2PSession(this, "miu_" + mOtherBean.getId());
                break;
            case R.id.btn_black:
                //拉黑
                if (black) {
                    removeUserToBlackList("miu_" + mOtherBean.getId());
                } else {
                    addUserToBlackList("miu_" + mOtherBean.getId());
                }
                break;
            case R.id.tv_fanse:
                Intent intent = new Intent(mContext, FunseListActivity.class);
                intent.putExtra(getString(R.string.visiti_person), userId);
                startActivity(intent);
                break;
            case R.id.tv_foucs:
                intent = new Intent(mContext, FollowListActivity.class);
                intent.putExtra(getString(R.string.visiti_person), userId);
                startActivity(intent);
                break;
        }
    }

    /**
     * 加入黑名单
     *
     * @author sll
     * @time 2016/11/30 17:40
     */
    private void addUserToBlackList(final String account) {
        NIMClient.getService(FriendService.class).addToBlackList(account).setCallback(new RequestCallback<Void>() {
            @Override
            public void onSuccess(Void param) {
                ToastUtils.showSnackBar(btnBlack, "加入黑名单成功");
                btnBlack.setText("取消拉黑");
                black = NIMClient.getService(FriendService.class).isInBlackList(account);
            }

            @Override
            public void onFailed(int code) {
                ToastUtils.showSnackBar(btnBlack, "加入黑名单失败,code:" + code);
            }

            @Override
            public void onException(Throwable exception) {

            }
        });
    }

    private void removeUserToBlackList(final String account) {
        NIMClient.getService(FriendService.class).removeFromBlackList(account).setCallback(new RequestCallback<Void>() {
            @Override
            public void onSuccess(Void param) {
                ToastUtils.showSnackBar(btnBlack, "移出黑名单成功");
                btnBlack.setText("拉黑");
                black = NIMClient.getService(FriendService.class).isInBlackList(account);
            }

            @Override
            public void onFailed(int code) {
                ToastUtils.showSnackBar(btnBlack, "移出黑名单失败，错误码：" + code);
            }

            @Override
            public void onException(Throwable exception) {

            }
        });
    }

    /**
     * 关注(取消关注)
     */
    private void guanZhu() {
        GuanZhuUtils.newInstance().guanZhu(this, appUser.getId(), mOtherBean.getId(), new ResultListener() {
            @Override
            public void onSucess(String data) {
                String follow = mOtherBean.getFollow();
                if (TextUtils.equals("0", follow)) {
                    mOtherBean.setFollow("1");
                } else if (TextUtils.equals("1", follow)) {
                    mOtherBean.setFollow("0");
                }

                focus();
            }

            @Override
            public void onFaild() {
                ToastUtils.showSnackBar(btnBlack, "修改失败");
            }
        });
    }
}
