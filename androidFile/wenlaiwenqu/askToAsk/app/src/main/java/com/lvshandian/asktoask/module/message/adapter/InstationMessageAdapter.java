package com.lvshandian.asktoask.module.message.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.adapter.CommonAdapter;
import com.lvshandian.asktoask.common.adapter.ViewHolder;
import com.lvshandian.asktoask.entry.InstationMessageBean;
import com.lvshandian.asktoask.module.message.InstationDetailsActivity;
import com.lvshandian.asktoask.utils.DateUtil;

import java.util.List;

/**
 *  on 2016/10/8.
 *
 *  站内信
 *
 *
 *
 */
public class InstationMessageAdapter extends CommonAdapter<InstationMessageBean.DataBean> {

    private Context context;

    public InstationMessageAdapter(Context context, List<InstationMessageBean.DataBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.context=context;
    }

    @Override
    public void convert(ViewHolder helper, final InstationMessageBean.DataBean item, int position) {


        helper.setText(R.id.tv_message_date, DateUtil.timesOne(item.addDate));
                helper.getView(R.id.ll_onclick_instation).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(context, InstationDetailsActivity.class);
                        intent.putExtra(InstationDetailsActivity.TRANCE,item);
                        context.startActivity(intent);
                    }
                });






    }
}
