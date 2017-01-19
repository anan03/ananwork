package com.lvshandian.partylive.moudles.mine.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.base.BaseActivity;
import com.lvshandian.partylive.moudles.mine.my.TiXianActivity;

import butterknife.Bind;

/**
 * Created by Administrator on 2016/11/16.
 */

public class ExchangeCenterActivity extends BaseActivity {
    @Bind(R.id.tv_has_money)
    TextView tvHasMoney;
    @Bind(R.id.tv_exchange_money)
    TextView tvExchangeMoney;
    @Bind(R.id.tv_exchane_red_bag)
    TextView tvExchaneRedBag;
    @Bind(R.id.tv_can_exchange_red_bag)
    TextView tvCanExchangeRedBag;
    @Bind(R.id.btn_exchange_top)
    Button btnExchangeTop;
    @Bind(R.id.btn_exchange_buttom)
    Button btnExchangeButtom;
    @Bind(R.id.tv_problem)
    TextView tvProblem;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_exchange_center;
    }

    @Override
    protected void initListener() {
        btnExchangeButtom.setOnClickListener(this);
        btnExchangeTop.setOnClickListener(this);
    }

    @Override
    protected void initialized() {
        initTitle("", "兑换中心", null);
        tvHasMoney.setText(appUser.getReceivedGoldCoin());
        tvExchangeMoney.setText(appUser.getExchangeGoldCoin());

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_titlebar_left:
                defaultFinish();
                break;
            case R.id.btn_exchange_buttom:
                gotoActivity(ExchangeActivity.class, false);
                break;
            case R.id.btn_exchange_top:
                gotoActivity(TiXianActivity.class, false);
                break;
        }
    }
}
