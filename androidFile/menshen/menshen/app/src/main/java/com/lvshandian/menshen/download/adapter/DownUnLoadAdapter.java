package com.lvshandian.menshen.download.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;

import com.lvshandian.menshen.R;
import com.lvshandian.menshen.analysesms.adapter.SmsAdapter;
import com.lvshandian.menshen.base.CommonAdapter;
import com.lvshandian.menshen.base.ViewHolder;
import com.lvshandian.menshen.bean.Downloadinfo;

import java.util.HashMap;
import java.util.List;

/**
 * Created by zhang on 2016/11/12.\
 * 创建下载对象
 */

public class DownUnLoadAdapter extends CommonAdapter<Downloadinfo> {
    //用来控制选中的情况
    public static HashMap<Integer, Boolean> isSelected = new HashMap<Integer, Boolean>();

    private static List<Downloadinfo> mDatas;

    public DownUnLoadAdapter(Context context, List<Downloadinfo> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
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
    public void convert(ViewHolder helper, Downloadinfo item, final int position) {
        helper.setText(R.id.download_name, item.getAppName());
        helper.setImageResource(R.id.download_head, item.getAppIcon());
        helper.setText(R.id.download_size, item.getApkSize());

        ((CheckBox) helper.getView(R.id.checkbox)).setChecked(getIsSelected().get(position));

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
    }
}
