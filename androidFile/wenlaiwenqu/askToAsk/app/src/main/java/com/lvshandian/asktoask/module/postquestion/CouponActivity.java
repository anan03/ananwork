package com.lvshandian.asktoask.module.postquestion;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.lidroid.xutils.util.LogUtils;
import com.lvshandian.asktoask.BaseActivity;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.http.HttpDatas;
import com.lvshandian.asktoask.common.http.RequestCode;
import com.lvshandian.asktoask.entry.DiscountCoupon;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.TextUtils;
import com.lvshandian.asktoask.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;

/**
 * 提问问题 优惠劵
 * 优惠劵 on 2016/9/27.
 */
public class CouponActivity extends BaseActivity {
    @Bind(R.id.iv_hudong_detail_back)
    ImageView ivHudongDetailBack;
    @Bind(R.id.tv_hudong_detail_type)
    TextView tvHudongDetailType;
    @Bind(R.id.tv_nums_coupon)
    TextView tvNumsCoupon;
    @Bind(R.id.lv_ask_)
    ListView lvAsk;
    public static DiscountCoupon bean;
    public static boolean issave;

    @Override
    protected void initListener() {
        ivHudongDetailBack.setOnClickListener(this);
        lvAsk.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                float selectMoney = 0;
                bean = mAdapterDatas.get(position);
                if (!TextUtils.isEmpty(bean.getDiscountCouponMoney())) {
                    switch (bean.getDiscountCouponMoney()) {
                        case "1":
                            selectMoney = 10;
                            break;
                        case "2":
                            selectMoney = 22;
                            break;
                        case "5":
                            selectMoney = 58;
                            break;
                        case "10":
                            selectMoney = 100;
                            break;
                        case "18":
                            selectMoney = 200;
                            break;
                        default:
                            selectMoney = 0;
                            break;

                    }
                }
                LogUtils.e("11pricetotal" + pricetotal);
                LogUtils.e("11selectMoney" + selectMoney);
                if (bean.getStatus().equals("0")) {
                    ToastUtils.showSnackBar(snackView, "请选择不过期的优惠劵");
                } else if (pricetotal < selectMoney) {
                    ToastUtils.showSnackBar(snackView, "悬赏金额不足，不能使用该优惠券");
                } else {
                    issave = true;
                    finish();
                }

            }
        });

    }

    private List<DiscountCoupon> mAdapterDatas = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coupon_ask_layout;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);
            switch (msg.what) {
                case RequestCode.ASK_COUPON:
                    mAdapterDatas = JSON.parseArray(json, DiscountCoupon.class);
                    tvNumsCoupon.setText("有" + mAdapterDatas.size() + "张优惠劵");
                    CouponAdapter adapter = new CouponAdapter(CouponActivity.this, mAdapterDatas, R.layout.item_youhuiquan);
                    lvAsk.setAdapter(adapter);
                    break;
            }
        }
    };

    float pricetotal = 0;

    @Override
    protected void initialized() {
        if (!TextUtils.isEmpty(getIntent().getStringExtra("pricetotal"))) {
            pricetotal = Float.parseFloat(getIntent().getStringExtra("pricetotal"));
        }
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("userId", Global.getUserId(getContext()));
        httpDatas.getData("发布问题优惠券", Request.Method.POST, UrlBuilder.COUPON_URL, map, mHandler, RequestCode.ASK_COUPON);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_hudong_detail_back:
                issave = false;
                finish();
                break;
        }
    }


}
