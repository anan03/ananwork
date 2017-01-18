package com.lvshandian.asktoask.module.setting;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.lvshandian.asktoask.BaseActivity;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.http.RequestCode;
import com.lvshandian.asktoask.utils.Constant;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.MethodUtils;
import com.lvshandian.asktoask.utils.TextUtils;
import com.lvshandian.asktoask.utils.ToastUtils;

import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * author: newlq on 2016/9/5
 * email：@lvshandian.com
 * company:北京绿闪电科技有限公司
 * details：意见反馈
 */
public class OpinionBackActivity extends BaseActivity {

    EditText etyijianfankuicontent;
    @Bind(R.id.et_yijianfankui_youxiangdizhi)
    EditText etyijianfankuiyouxiangdizhi;
    @Bind(R.id.iv_hudong_detail_back)
    ImageView ivHudongDetailBack;
    @Bind(R.id.tv_submit)
    TextView tvSubmit;
    @Bind(R.id.tv_hudong_detail_type)
    TextView tvHudongDetailType;
    @Bind(R.id.rl_head_title)
    RelativeLayout rlHeadTitle;
    @Bind(R.id.et_yijianfankui_content)
    EditText etYijianfankuiContent;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case RequestCode.MINE_YIJIANFANKUI_REQUESTCODE:
                    ToastUtils.showSnackBar(snackView, "发送成功");
                    finish();//结束界面
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_yijianfankui;
    }

    @Override
    protected void initListener() {
        tvSubmit.setOnClickListener(this);//提交
        ivHudongDetailBack.setOnClickListener(this);//返回键
    }

    @Override
    protected void initialized() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_hudong_detail_back:
                finish();
                break;
            case R.id.tv_submit:
                fasongyijianfankui();//意见反馈
                break;
            default:
                break;
        }
    }

    /**
     * 发送意见反馈
     */
    private void fasongyijianfankui() {
        String fankuineirong = MethodUtils.getTextContent(etYijianfankuiContent);
        String youxiangdizhi = MethodUtils.getTextContent(etyijianfankuiyouxiangdizhi);

        if (TextUtils.isEmpty(fankuineirong)) {
            ToastUtils.showSnackBar(snackView, "请填写意见反馈");
            return;
        }
        if (!MethodUtils.isEmpty(fankuineirong)) {
            if (!MethodUtils.isEmpty(youxiangdizhi) && !youxiangdizhi.matches(Constant.EMAIL_REGEX)) {
                ToastUtils.showSnackBar(snackView, "请输入正确的邮箱地址");
                return;
            }
            ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
            map.put("userId", Global.getUserId(getContext()));
            map.put("feedbackMessage", fankuineirong);
            map.put("email", youxiangdizhi);
            httpDatas.getData("意见反馈", Request.Method.POST, UrlBuilder.MINE_YIJIANFANKUI_URL, map, mHandler, RequestCode.MINE_YIJIANFANKUI_REQUESTCODE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
