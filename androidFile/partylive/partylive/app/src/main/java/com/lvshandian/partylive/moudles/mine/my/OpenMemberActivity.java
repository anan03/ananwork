package com.lvshandian.partylive.moudles.mine.my;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.UrlBuilder;
import com.lvshandian.partylive.base.BaseActivity;
import com.lvshandian.partylive.base.CustomStringCallBack;
import com.lvshandian.partylive.httprequest.HttpDatas;
import com.lvshandian.partylive.moudles.mine.bean.BuyMember;
import com.lvshandian.partylive.moudles.mine.bean.OpemMemberBean;
import com.lvshandian.partylive.utils.JsonUtil;
import com.lvshandian.partylive.utils.LogUtils;
import com.lvshandian.partylive.utils.TextUtils;
import com.lvshandian.partylive.view.RoundDialog;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;

/**
 * Created by gjj on 2016/11/21.
 */

public class OpenMemberActivity extends BaseActivity {
    @Bind(R.id.btn_buy)
    Button btnBuy;
    private AlertDialog mDialog;
    private RoundDialog mSureDialog;
    private TextView tvContent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_open_member;
    }

    @Override
    protected void initListener() {
        btnBuy.setOnClickListener(this);
    }

    @Override
    protected void initialized() {
        initTitle("", "开通会员", null);
        initDialog();
        intSureDialog();
    }

    private int mBuyCount = 0;//购买数量

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_titlebar_left:
                defaultFinish();
                break;
            case R.id.btn_buy:
                if (mDialog != null && !mDialog.isShowing()) {
                    mDialog.show();
                }
                break;
            case R.id.btn_500:
                if (mSureDialog != null && !mSureDialog.isShowing()) {
                    tvContent.setText("您确认购买1个月会员吗");
                    mBuyCount = 500;
                    mSureDialog.show();
                }
                break;
            case R.id.btn_1300:
                if (mSureDialog != null && !mSureDialog.isShowing()) {
                    tvContent.setText("您确认购买3个月会员吗");
                    mBuyCount = 1300;
                    mSureDialog.show();
                }
                break;
            case R.id.btn_2300:
                if (mSureDialog != null && !mSureDialog.isShowing()) {
                    tvContent.setText("您确认购买6个月会员吗");
                    mBuyCount = 2300;
                    mSureDialog.show();
                }
                break;
            case R.id.btn_4000:
                if (mSureDialog != null && !mSureDialog.isShowing()) {
                    tvContent.setText("您确认购买12个月会员吗");
                    mBuyCount = 4000;
                    mSureDialog.show();
                }
                break;
            case R.id.iv_x:
                if (mDialog != null && mDialog.isShowing()) {
                    mBuyCount = 0;
                    mDialog.dismiss();
                }
                break;
        }
    }

    /**
     * 对话框
     */
    public void initDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_open_vip, null);
        mDialog = builder.create();
        Button btn500 = (Button) inflate.findViewById(R.id.btn_500);
        Button btn1300 = (Button) inflate.findViewById(R.id.btn_1300);
        Button btn2300 = (Button) inflate.findViewById(R.id.btn_2300);
        Button btn4000 = (Button) inflate.findViewById(R.id.btn_4000);

        btn500.setOnClickListener(this);
        btn1300.setOnClickListener(this);
        btn2300.setOnClickListener(this);
        btn4000.setOnClickListener(this);
        ImageView ivX = (ImageView) inflate.findViewById(R.id.iv_x);
        mDialog.setCanceledOnTouchOutside(false);
        WindowManager mWm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int width = mWm.getDefaultDisplay().getWidth();
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width * 3 / 4, ViewGroup.LayoutParams.WRAP_CONTENT);
        inflate.setLayoutParams(params);
        mDialog.setView(inflate);
        ivX.setOnClickListener(this);
    }

    /**
     * 确认购买对话框
     */
    public void intSureDialog() {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.dialog_sure_buy_vip, null);
        mSureDialog = new RoundDialog(this, inflate);
        inflate.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSureDialog != null && mSureDialog.isShowing()) {
                    mSureDialog.dismiss();
                }
            }
        });

        inflate.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSureDialog != null && mSureDialog.isShowing()) {
                    mSureDialog.dismiss();
                }
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
                buyMember();
            }
        });
        tvContent = (TextView) inflate.findViewById(R.id.tv_content);
    }

    /**
     * 购买会员
     */
    private void buyMember() {
        if (mBuyCount == 0) {
            showToast("请选择开通时长");
            return;
        }

        String id = appUser.getId();
        String url = UrlBuilder.serverUrl + UrlBuilder.yqRen;
        url += id;
        url += "/buy/" + mBuyCount;

        OkHttpUtils.get().url(url)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
                showToast(R.string.check_network);
            }

            @Override
            public void onResponse(String response) {
                LogUtils.e("开通会员: " + response);
                OpemMemberBean memberBean = JsonUtil.json2Bean(response, OpemMemberBean.class);
                if (memberBean != null) {
                    String code = memberBean.getCode();
                    if (android.text.TextUtils.equals(code, "0")) {
                        showToast("购买成功!");
                        EventBus.getDefault().post(new BuyMember());
                    } else if (android.text.TextUtils.equals(code, "1")) {
                        showToast(memberBean.getMessage());
                    }
                } else {
                    showToast("购买失败!");
                }
            }
        });
    }
}
