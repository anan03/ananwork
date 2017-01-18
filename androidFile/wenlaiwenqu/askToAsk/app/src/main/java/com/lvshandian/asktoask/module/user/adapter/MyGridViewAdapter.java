package com.lvshandian.asktoask.module.user.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.lvshandian.asktoask.R;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Created by zhang on 2016/10/9.
 */
public class MyGridViewAdapter extends BaseAdapter {

    private String[] pic;
    private Context context;

    public MyGridViewAdapter(String[] pic, Context context) {

        this.pic = pic;
        this.context = context;
    }

    @Override
    public int getCount() {

        if (pic == null || pic.length == 0) {
            return 0;

        }
        return pic.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    ViewHolder vh;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(context,
                R.layout.item_mygridview, null);

        if (convertView == null || convertView.getTag() == null) {

            vh = new ViewHolder();

            convertView = View.inflate(context,
                    R.layout.item_mygridview, null);

            vh.img_gridview = (ImageView) convertView
                    .findViewById(R.id.img_gridview);
            convertView.setTag(vh);
        } else {

            vh = (ViewHolder) convertView.getTag();
        }
        ImageLoader.getInstance().displayImage(pic[position], vh.img_gridview);
        return convertView;
    }

    class ViewHolder {
        ImageView img_gridview;
    }

}
