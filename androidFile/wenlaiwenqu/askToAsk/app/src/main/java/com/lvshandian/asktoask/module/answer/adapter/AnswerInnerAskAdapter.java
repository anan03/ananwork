package com.lvshandian.asktoask.module.answer.adapter;

import android.content.Context;
import com.lidroid.xutils.BitmapUtils;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.adapter.CommonAdapter;
import com.lvshandian.asktoask.common.adapter.ViewHolder;
import com.lvshandian.asktoask.entry.AnswerAskItem;
import java.util.List;

/**
 * 答咖详情 提问adapter on 2016/9/30.
 */
public class AnswerInnerAskAdapter extends CommonAdapter<AnswerAskItem.DataBean>{
    private Context context;
    BitmapUtils bitmapUtils;

    public AnswerInnerAskAdapter(Context context, List<AnswerAskItem.DataBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.context = context;
        bitmapUtils = new BitmapUtils(context);
    }
    @Override
    public void convert(ViewHolder helper, AnswerAskItem.DataBean item, int position) {
        helper.setText(R.id.tv_content,"内容");
        helper.setText(R.id.tv_date,"时间");
        helper.setText(R.id.tv_ask_num,"数量");

    }



}
