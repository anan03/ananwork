package com.lvshandian.menshen.analysesms.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.lvshandian.menshen.R;
import com.lvshandian.menshen.bean.SmsInfo;
import com.lvshandian.menshen.utils.DateUtil;
import com.lvshandian.menshen.utils.LogUtils;

import java.util.HashMap;
import java.util.List;

import static com.lvshandian.menshen.R.layout.anaysesms_item_framentapp;

/**
 * Created by zhang on 2016/11/8.
 */

public class SmsBaseAdapter extends BaseAdapter {

    ViewHolder viewHolder = null;
    public static boolean isDelete = false;//是否显示删除；
    public static boolean isSelect = false;//显示che是否全选或者不权限；

    //用来控制收藏的选中的情况
    private static HashMap<Integer, Boolean> isSelected;
    public static List<SmsInfo> mDatas;
    Context context;

    public SmsBaseAdapter(Context context, List<SmsInfo> mDatas, boolean isShow) {
        this.isDelete = isShow;
        this.mDatas = mDatas;
        this.context = context;
        isSelected = new HashMap<Integer, Boolean>();
        indata();
    }

    public static void indata() {

        if (isSelect) {
            for (int i = 0; i < mDatas.size(); i++) {
                getIsSelected().put(i, true);
            }
        } else {
            for (int i = 0; i < mDatas.size(); i++) {
                getIsSelected().put(i, false);
            }
        }

    }

    public static HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        SmsBaseAdapter.isSelected = isSelected;
    }

    @Override
    public int getCount() {

        if (mDatas.size() < 1) {

            return 0;
        }
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LogUtils.e("----------" + mDatas.toString());
        final SmsInfo item = mDatas.get(position);

        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(
                    R.layout.anaysesms_item_framentapp, null);
            viewHolder.anay_item_tv_body = (TextView) view.findViewById(R.id.anay_item_tv_body);
            viewHolder.anay_item_tv_date = (TextView) view.findViewById(R.id.anay_item_tv_date);
            viewHolder.anay_item_tv_phone = (TextView) view.findViewById(R.id.anay_item_tv_phone);
            viewHolder.anay_item_xlan = (TextView) view.findViewById(R.id.anay_item_xlan);
            viewHolder.checkbox = (CheckBox) view
                    .findViewById(R.id.checkbox);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        //内容
        viewHolder.anay_item_tv_body.setText(item.getBody());
        //时间
        viewHolder.anay_item_tv_date.setText(DateUtil.timesTheree(item.getDate()));
        //手机号码
        if (item.getPerson() != null && !item.getPerson().equals("")) {//如果没有备注就显示手机号码

            viewHolder.anay_item_tv_phone.setText(item.getPerson());
        } else {
            viewHolder.anay_item_tv_phone.setText(item.getAddress());
        }

        //是否已读
        if (item.getRead() == 1) {
            viewHolder.anay_item_xlan.setVisibility(View.GONE);
        } else if (item.getRead() == 0) {
            viewHolder.anay_item_xlan.setVisibility(View.VISIBLE);
        }
        //是否显示删除checkbox
        if (isDelete) {
            viewHolder.checkbox.setVisibility(View.VISIBLE);
        } else {
            viewHolder.checkbox.setVisibility(View.GONE);
        }


        viewHolder.checkbox.setChecked(getIsSelected().get(position));
        return view;
    }


    //
    final static class ViewHolder {
        TextView anay_item_tv_body, anay_item_tv_date, anay_item_tv_phone, anay_item_xlan;
        CheckBox checkbox;

    }

}
