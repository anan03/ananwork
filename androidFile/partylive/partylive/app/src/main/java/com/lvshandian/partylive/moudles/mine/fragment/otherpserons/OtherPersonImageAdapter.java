package com.lvshandian.partylive.moudles.mine.fragment.otherpserons;

import android.content.Context;
import android.widget.ImageView;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.base.CommonAdapter;
import com.lvshandian.partylive.base.ViewHolder;
import com.lvshandian.partylive.moudles.mine.bean.PhotoBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by gjj on 2016/11/23.
 * 创建图片适配器
 */

public class OtherPersonImageAdapter extends CommonAdapter<PhotoBean> {
    public OtherPersonImageAdapter(Context context, List<PhotoBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder helper, PhotoBean item, int position) {
        ImageView view = helper.getView(R.id.img_gridview);
        ImageLoader.getInstance().displayImage(mDatas.get(position).getUrl(),view);
    }
}
