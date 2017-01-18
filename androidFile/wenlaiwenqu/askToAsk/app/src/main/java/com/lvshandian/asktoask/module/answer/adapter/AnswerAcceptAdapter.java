package com.lvshandian.asktoask.module.answer.adapter;

import android.content.Context;

import com.lidroid.xutils.BitmapUtils;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.adapter.CommonAdapter;
import com.lvshandian.asktoask.common.adapter.ViewHolder;
import com.lvshandian.asktoask.entry.AnswerAcceptItem;

import java.util.List;

/**
 * 答咖采纳adapter on 2016/9/30.
 */
public class AnswerAcceptAdapter extends CommonAdapter<AnswerAcceptItem.DataBean>{

    private Context context;
    BitmapUtils bitmapUtils;

    public AnswerAcceptAdapter(Context context, List<AnswerAcceptItem.DataBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.context = context;
        bitmapUtils = new BitmapUtils(context);
    }

    @Override
    public void convert(ViewHolder helper, AnswerAcceptItem.DataBean item, int position) {
        helper.setText(R.id.tv_content,"内容");
        helper.setText(R.id.tv_date,"时间");
        helper.setText(R.id.tv_name,"名字");
        helper.setText(R.id.tv_words,"名字");



    }
}
