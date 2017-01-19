package com.lvshandian.partylive.moudles.mine.my.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.base.CommonAdapter;
import com.lvshandian.partylive.base.ViewHolder;
import com.lvshandian.partylive.bean.ResultBean;
import com.lvshandian.partylive.utils.PicassoUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/11/18.
 */

public class InviteUserAdapter extends CommonAdapter<ResultBean> {
    public InviteUserAdapter(Context context, List<ResultBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, ResultBean item, int position) {
        TextView tvName = helper.getView(R.id.tv_name);
        TextView tvStar = helper.getView(R.id.tv_star);
        TextView tvReciveCoin = helper.getView(R.id.tv_recive_coin);
        TextView tvUseCoin = helper.getView(R.id.tv_use_coin);
        TextView tvSign = helper.getView(R.id.tv_sign);
        ImageView ivImage = helper.getView(R.id.iv_header);

        tvName.setText(item.getNickName());
        tvStar.setText(String.valueOf(item.getLevel()));
        tvReciveCoin.setText("收到:" + item.getGoldCoin() + "金币");
        tvUseCoin.setText("花费:" + item.getSpendGoldCoin() + "金币");
        tvSign.setText(item.getSignature());

        PicassoUtil.newInstance().onRoundnessImage(mContext, item.getPicUrl(), ivImage);
        String gender = item.getGender();
        Drawable drawable = mContext.getResources().getDrawable(gender == null || gender.equals("1") ? R.mipmap.male : R.mipmap.female);
        tvName.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
    }
}
