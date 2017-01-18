package com.lvshandian.menshen.push.adapter;

import android.content.Context;

import com.lvshandian.menshen.R;
import com.lvshandian.menshen.base.CommonAdapter;
import com.lvshandian.menshen.base.ViewHolder;
import com.lvshandian.menshen.bean.Message;
import com.lvshandian.menshen.utils.DateUtil;

import java.util.List;

/**
 * Created by zhang on 2016/10/27.
 */

public class MessageAdapter extends CommonAdapter<Message> {
    public MessageAdapter(Context context, List mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }


    @Override
    public void convert(ViewHolder helper, Message item, int position) {
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_context, item.getContent());
        helper.setText(R.id.tv_date, DateUtil.timesFor(item.getCreatedDate()));
    }
}
