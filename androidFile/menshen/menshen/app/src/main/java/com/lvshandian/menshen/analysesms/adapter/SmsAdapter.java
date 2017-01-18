package com.lvshandian.menshen.analysesms.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;

import com.lvshandian.menshen.R;
import com.lvshandian.menshen.analysesms.AnayseSmsActivity;
import com.lvshandian.menshen.analysesms.AnayseSmsUploadActivity;
import com.lvshandian.menshen.base.CommonAdapter;
import com.lvshandian.menshen.bean.SmsInfo;
import com.lvshandian.menshen.base.ViewHolder;
import com.lvshandian.menshen.utils.DateUtil;
import com.lvshandian.menshen.utils.LogUtils;
import com.lvshandian.menshen.utils.ToastUtil;
import com.lvshandian.menshen.view.ToastUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zhang on 2016/10/27.
 */

public class SmsAdapter extends CommonAdapter<SmsInfo> {

    public static boolean isDelete = false;//是否显示删除；
    public static boolean isSelect = false;//显示che是否全选或者不权限；

    public static SmsAdapter smsAdapter;


    //用来控制收藏的选中的情况
    public static HashMap<Integer, Boolean> isSelected = new HashMap<Integer, Boolean>();
    public static List<SmsInfo> mDatas;

    public SmsAdapter(Context context, List mDatas, int itemLayoutId, boolean isShow) {
        super(context, mDatas, itemLayoutId);
        smsAdapter = this;
        this.mDatas = mDatas;
        indata();
    }

    public static void indata() {

        for (int i = 0; i < mDatas.size(); i++) {
            getIsSelected().put(i, false);
        }

    }

    public static HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        SmsAdapter.isSelected = isSelected;
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
            ((CheckBox) helper.getView(R.id.checkbox)).setChecked(getIsSelected().get(position));
            ((CheckBox) helper.getView(R.id.checkbox)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//
//                    if (AnayseSmsUploadActivity.tvSingle != null) {
//                        int k1 = 0;
//                        for (int k = 0; k < mDatas.size(); k++) {
//                            if (isSelected.get(k)) {
//                                k1++;
//                            }
//
//                        }
//                        if (k1 > 10) {
//                            ToastUtil.makeToast("上传短信个数不能超过10个", true);
//                            return;
//                        }
//
//                    }


                    LogUtils.e("tag======posion" + position);
                    if (isSelected.get(position)) {
                        isSelected.put(position, false);
                        setIsSelected(isSelected);
                        LogUtils.e("tag======put" + false);
                    } else {
                        isSelected.put(position, true);
                        setIsSelected(isSelected);
                        LogUtils.e("tag======put" + true);
                    }

                    int tag = 0;
                    for (int i = 0; i < getIsSelected().size(); i++) {

                        if (getIsSelected().get(i)) {
                            tag++;
                            break;
                        }
                    }
                    LogUtils.e("tag======size" + getIsSelected().size());
                    if (tag == 0) {

                        if (AnayseSmsUploadActivity.tvSingle != null) {

                            AnayseSmsUploadActivity.tvSingle
                                    .setTextColor(mContext.getResources().getColor(
                                            R.color.texthui_color));

                        }
                        if (null != AnayseSmsActivity.tvAll && AnayseSmsActivity.tvSingle != null) {

                            AnayseSmsActivity.tvSingle
                                    .setTextColor(mContext.getResources().getColor(
                                            R.color.texthui_color));

                            AnayseSmsActivity.tvAll
                                    .setTextColor(mContext.getResources().getColor(
                                            R.color.back347));
                        }

                    } else {
                        if (AnayseSmsUploadActivity.tvSingle != null) {
                            AnayseSmsUploadActivity.tvSingle
                                    .setTextColor(mContext.getResources().getColor(
                                            R.color.back347));

                        }
                        if (null != AnayseSmsActivity.tvAll && AnayseSmsActivity.tvSingle != null) {

                            AnayseSmsActivity.tvSingle
                                    .setTextColor(mContext.getResources().getColor(
                                            R.color.back347));

                            AnayseSmsActivity.tvAll
                                    .setTextColor(mContext.getResources().getColor(
                                            R.color.texthui_color));
                        }

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
