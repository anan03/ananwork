package com.lvshandian.partylive.moudles.mine.my.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.adapter.CommonAdapter;
import com.lvshandian.partylive.adapter.ViewHolder;
import com.lvshandian.partylive.utils.DateUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.bean.UMComment;

import java.util.List;

/**
 * Created by Administrator on 2016/12/11 0011.
 */

public class CommentAdapter extends CommonAdapter<UMComment> {
    public CommentAdapter(Context context, List<UMComment> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        Log.e("mDatas", mDatas.toString());
    }

    @Override
    public void convert(ViewHolder helper, UMComment item, int position) {
        helper.setText(R.id.tv_nick_name, "@" + item.mUname);
        helper.setText(R.id.tv_context, item.mText);
        helper.setText(R.id.tv_data, "" + DateUtil.getRecentlyDays(item.mDt));
        ImageLoader.getInstance().displayImage(item.mUserIcon, (ImageView) helper.getView(R.id.iv_pic));
    }
}
