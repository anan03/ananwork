package com.lvshandian.partylive.moudles.mine.fragment;


import android.widget.TextView;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.base.BaseFragment;
import com.lvshandian.partylive.moudles.mine.bean.OtherPersonBean;
import com.lvshandian.partylive.moudles.mine.my.OtherPersonHomePageActivity;

import butterknife.Bind;

/**
 * 首页左边关注
 */
public class OrtherPersonMyFragment extends BaseFragment {

    @Bind(R.id.tv_reciver)
    TextView tvReciver;
    @Bind(R.id.tv_service_rate)
    TextView tvServiceRate;
    @Bind(R.id.tv_scor_rate)
    TextView tvScorRate;
    @Bind(R.id.tv_sign)
    TextView tvSign;
    @Bind(R.id.tv_vedio_money)
    TextView tvVedioMoney;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_other_person_home;
    }

    @Override
    protected void initListener() {

    }


    @Override
    protected void initialized() {
        OtherPersonHomePageActivity activity = (OtherPersonHomePageActivity) getActivity();
        OtherPersonBean bean = activity.getBean();
        if (bean != null) {
            String goldCoin = bean.getReceivedGoldCoin();
            tvReciver.setText("收到: " + goldCoin);

            String serviceSatisfied = bean.getServiceSatisfied();
            tvServiceRate.setText("服务好评率: " + serviceSatisfied + "%");

            String gradeSatisfied = bean.getGradeSatisfied();
            tvScorRate.setText("评分好评率: " + gradeSatisfied + "%");

            String signature = bean.getSignature();
            tvSign.setText("个性签名: " + signature);

            String payForVideoChat = bean.getPayForVideoChat();
            tvVedioMoney.setText("视频聊天费用: " + payForVideoChat);
        }
    }
}
