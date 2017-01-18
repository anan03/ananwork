package com.lvshandian.asktoask.module.interaction.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.adapter.CommonAdapter;
import com.lvshandian.asktoask.common.adapter.ViewHolder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 互动详情界面里图片adapter on 2016/10/7.
 */
public class HuDongDetailPicsAdapter extends CommonAdapter<String>{


    private Context context;



    public HuDongDetailPicsAdapter(Context context, List<String> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.context = context;
    }

    @Override
    public void convert(ViewHolder helper, String item, int position) {

        ImageLoader.getInstance().displayImage(item,(ImageView)helper.getView(R.id.iv_hudong_detail_pics));



    }
}
