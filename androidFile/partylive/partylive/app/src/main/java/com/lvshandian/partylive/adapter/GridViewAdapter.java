package com.lvshandian.partylive.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.bean.GiftBean;
import com.lvshandian.partylive.widget.LoadUrlImageView;


import java.util.List;

/**
 * Created by Administrator on 2016/3/28.
 */
public class GridViewAdapter extends BaseAdapter {
    private List<GiftBean> giftList;
    private Context context;

    public GridViewAdapter(List<GiftBean> giftList, Context context) {
        this.giftList = giftList;
        this.context = context;
    }

    public GridViewAdapter(List<GiftBean> giftList) {
        this.giftList = giftList;
    }

    @Override
    public int getCount() {
        return giftList.size();
    }

    @Override
    public Object getItem(int position) {
        return giftList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView == null){
            convertView = View.inflate(context, R.layout.item_show_gift,null);
            viewHolder = new ViewHolder();
            viewHolder.mGiftViewImg = (LoadUrlImageView) convertView.findViewById(R.id.iv_show_gift_img);
            viewHolder.mGiftPrice = (TextView) convertView.findViewById(R.id.tv_show_gift_price);
            viewHolder.mGiftExperience = (TextView) convertView.findViewById(R.id.tv_show_gift_experience);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GiftBean g = giftList.get(position);
        viewHolder.mGiftViewImg.setImageLoadUrl(g.getStaticIcon());
        viewHolder.mGiftExperience.setText("+" + Integer.parseInt(g.getCurrencyAmount()) *10 + "经验值");
        viewHolder.mGiftPrice.setText(g.getCurrencyAmount()+"");
        if(Integer.parseInt(g.getCombo()) == 1){
            convertView.findViewById(R.id.iv_show_gift_selected).setBackgroundResource(R.mipmap.icon_continue_gift);
        }
        return convertView;
    }
    private class ViewHolder{
        public LoadUrlImageView mGiftViewImg;
        public TextView mGiftPrice,mGiftExperience;
    }
}
