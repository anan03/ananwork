package com.lvshandian.partylive.moudles.mine.fragment.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.moudles.mine.bean.PhotoBean;
import com.lvshandian.partylive.moudles.mine.bean.VideoBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by zhang on 2016/11/18.
 * 创建图片适配器
 */

public class VideoAdapter extends BaseAdapter {
    List<VideoBean> list;
    Context mContext;

    public VideoAdapter(Context mContext, List<VideoBean> list) {
        this.mContext = mContext;
        this.list = list;

    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     */
    ViewHolder viewHolder;

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {


        viewHolder = new ViewHolder();


        view = LayoutInflater.from(mContext).inflate(
                R.layout.frament_mine_photo, null);
        viewHolder.img_gridview = (ImageView) view
                .findViewById(R.id.img_gridview);
        int width = viewHolder.img_gridview.getMeasuredWidth();
        viewHolder.img_gridview.setMinimumHeight(width);
        view.setTag(viewHolder);
        if (position == list.size() - 1) {
            ImageLoader.getInstance().displayImage("drawable://" + R.mipmap.plus, viewHolder.img_gridview);
            return view;
        } else {
//            ImageLoader.getInstance().displayImage("drawable://" + R.mipmap.app_icon, viewHolder.img_gridview);
            ImageLoader.getInstance().displayImage(list.get(position).getThumbnailUrl(), viewHolder.img_gridview);
        }

        return view;

    }

    private class ViewHolder {
        ImageView img_gridview;
    }

}
