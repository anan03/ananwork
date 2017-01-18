package com.lvshandian.asktoask.module.user.adapter;

import android.content.Context;

import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.adapter.CommonAdapter;
import com.lvshandian.asktoask.common.adapter.ViewHolder;
import com.lvshandian.asktoask.entry.DataAdoption;
import com.lvshandian.asktoask.entry.DataMessageLeave;
import com.lvshandian.asktoask.utils.DateUtil;

import java.util.List;

/**
 * Created by zhang on 2016/10/6.
 * 创建提问适配器
 */

public class DataAdoptionAdapter extends CommonAdapter<DataAdoption.DataBean> {
    private String name;

    public DataAdoptionAdapter(Context context, String name, List<DataAdoption.DataBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.name = name;
    }

    @Override
    public void convert(ViewHolder helper, DataAdoption.DataBean item, int position) {

        helper.setText(R.id.tv_words, item.getAnswerData());
        helper.setText(R.id.tv_date, DateUtil.timesTwo(item.getAnswerDate()));
        helper.setText(R.id.tv_name, name);
        helper.setText(R.id.tv_content, item.getExtend1());
    }
}
