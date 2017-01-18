package com.lvshandian.asktoask.module.interaction.adapter;

import android.content.Context;

import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.adapter.CommonAdapter;
import com.lvshandian.asktoask.common.adapter.ViewHolder;
import com.lvshandian.asktoask.entry.HuDongItem;
import com.lvshandian.asktoask.utils.DateUtil;
import com.lvshandian.asktoask.utils.TextUtils;

import java.util.List;

/**
 * 互动搜索adapter on 2016/9/18.
 */
public class HuDongSearchAdapter extends CommonAdapter<HuDongItem.DataBean.PageBean.DataBean2> {
    Context context;
    HuDongItem.DataBean pagebean;
    public HuDongSearchAdapter(Context context, List<HuDongItem.DataBean.PageBean.DataBean2> mDatas, int itemLayoutId,HuDongItem.DataBean pagebean) {
        super(context, mDatas, itemLayoutId);
        this.context = context;
        this.pagebean=pagebean;


    }

    @Override
    public void convert(ViewHolder helper, HuDongItem.DataBean.PageBean.DataBean2 item,int position) {
        helper.setText(R.id.tv_search_title,item.getQuestionTitle());
        helper.setText(R.id.tv_search_time, DateUtil.timesOne(item.getQuestionPublishDate()));
        helper.setText(R.id.tv_type_hudong_search, item.getQuestionType());

        item.ispraise=TextUtils.isParse(item.questionId, pagebean.pId);

        item.iscollect= TextUtils.isCollect(item.questionId, pagebean.cId);

    }
}
