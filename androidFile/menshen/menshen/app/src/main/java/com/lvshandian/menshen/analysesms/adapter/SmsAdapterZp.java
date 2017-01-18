package com.lvshandian.menshen.analysesms.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;

import com.lvshandian.menshen.R;
import com.lvshandian.menshen.base.CommonAdapter;
import com.lvshandian.menshen.base.ViewHolder;
import com.lvshandian.menshen.bean.SmsInfo;
import com.lvshandian.menshen.utils.DateUtil;
import com.lvshandian.menshen.utils.LogUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zhang on 2016/10/27.
 */

public class SmsAdapterZp extends CommonAdapter<SmsInfo> {

    public static boolean isDelete = false;//是否显示删除；
    public static boolean isSelect = false;//显示che是否全选或者不权限；

    public static SmsAdapterZp smsAdapter;


    //用来控制收藏的选中的情况
    private static HashMap<Integer, Boolean> isSelected = new HashMap<Integer, Boolean>();
    public static List<SmsInfo> mDatas;

    public SmsAdapterZp(Context context, List mDatas, int itemLayoutId, boolean isShow) {
        super(context, mDatas, itemLayoutId);
        smsAdapter = this;
        this.isDelete = isShow;
        this.mDatas = mDatas;
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
        SmsAdapterZp.isSelected = isSelected;
    }

    @Override
    public void convert(ViewHolder helper, SmsInfo item, final int position) {

        helper.setText(R.id.anay_item_tv_body, item.getBody());
//        helper.setText(R.id.anay_item_tv_date, "" + DateUtil.timesTheree(item.getDate()));
        helper.setText(R.id.anay_item_tv_date, "" + DateUtil.getRecentlyDays(item.getDate()));

        if (item.getPerson() != null && !item.getPerson().equals("")) {//如果没有备注就显示手机号码
            helper.setText(R.id.anay_item_tv_phone, item.getPerson());
        } else {
            helper.setText(R.id.anay_item_tv_phone, item.getAddress());
        }
        LogUtils.e("" + item.getPerson());

        //read：是否阅读0未读，1已读
        if (item.getRead() == 1) {
            helper.getView(R.id.anay_item_xlan).setVisibility(View.GONE);
        } else if (item.getRead() == 0) {
            helper.getView(R.id.anay_item_xlan).setVisibility(View.VISIBLE);
        }
        // 根据点赞collectisSelected来设置checkbox的选中状况
        //是否隐藏显示删除的chebox
        if (isDelete) {
            helper.getView(R.id.checkbox).setVisibility(View.VISIBLE);
            ((CheckBox) helper.getView(R.id.checkbox)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isSelected.get(position)) {
                        isSelected.put(position, false);
                        setIsSelected(isSelected);

                    } else {
                        isSelected.put(position, true);
                        setIsSelected(isSelected);

                    }
                }
            });
        } else {
            helper.getView(R.id.checkbox).setVisibility(View.GONE);
        }


//        helper.setText(R.id.anay_item_xlan, item.getBody());
//        helper.setText(R.id.anay_item_tv_body, item.getBody());
//        helper.setImageResource(R.id.menu_item1_iv, mDatas.get(position).getIcon());
        //删除
//        context.getContentResolver().delete(
//                Uri.parse("content://sms"), "_id=" + id, null);
    }
}
