package com.lvshandian.asktoask.module.user.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;

import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.adapter.CommonAdapter;
import com.lvshandian.asktoask.common.adapter.ViewHolder;
import com.lvshandian.asktoask.entry.DiscountCoupon;
import com.lvshandian.asktoask.utils.DateUtil;

import java.util.List;

/**
 * Created by newlq on 2016/9/7.
 * 优惠券的adapter
 */
public class UserYouHuiQuanAdapter extends CommonAdapter<DiscountCoupon> {

    public UserYouHuiQuanAdapter(Context context, List<DiscountCoupon> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, DiscountCoupon item, int position) {
        helper.setText(R.id.tv_youhuiquan_jine, item.getDiscountCouponMoney());
        LinearLayout ll= helper.getView(R.id.ll_youhuijuan_nei);

        switch (item.getDiscountCouponMoney()) {
            case "1":
                helper.setText(R.id.tv_youhuiquan_tishi, mContext.getString(R.string.setting_youhuiquan_youhuiquantishi, "10"));
                break;
            case "2":
                helper.setText(R.id.tv_youhuiquan_tishi, mContext.getString(R.string.setting_youhuiquan_youhuiquantishi, "22"));
                break;
            case "5":
                helper.setText(R.id.tv_youhuiquan_tishi, mContext.getString(R.string.setting_youhuiquan_youhuiquantishi, "58"));
                break;
            case "10":
                helper.setText(R.id.tv_youhuiquan_tishi, mContext.getString(R.string.setting_youhuiquan_youhuiquantishi, "100"));
                break;
            case "18":
                helper.setText(R.id.tv_youhuiquan_tishi, mContext.getString(R.string.setting_youhuiquan_youhuiquantishi, "200"));
                break;
            default:
                helper.setText(R.id.tv_youhuiquan_tishi, mContext.getString(R.string.setting_youhuiquan_youhuiquantishi, "0"));
                break;
        }
        String startDate = DateUtil.getFormatString(item.getDiscountCouponDate(), "yyyy.MM.dd");
        String endDate = DateUtil.getFormatString(item.getDiscountCouponDated(), "yyyy.MM.dd");

        helper.setText(R.id.tv_youhuiquan_shiyongqixian, mContext.getString(R.string.setting_youhuiquan_shiyongqixian, startDate + "-" + endDate));
        if ("1".equals(item.getStatus())) {
            helper.setText(R.id.tv_youhuiquan_shifoukeyong, mContext.getString(R.string.setting_youhuiquan_keyong));
            ll.setBackgroundResource(R.drawable.item_youhuiquanbg);
        } else if ("0".equals(item.getStatus())) {
            helper.setText(R.id.tv_youhuiquan_shifoukeyong, mContext.getString(R.string.setting_youhuiquan_bukeyong));
            ll.setBackgroundResource(R.drawable.youhuijuan_no);
        }
    }
}
