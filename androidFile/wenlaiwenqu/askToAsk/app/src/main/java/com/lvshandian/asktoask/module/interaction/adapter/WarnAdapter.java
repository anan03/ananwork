package com.lvshandian.asktoask.module.interaction.adapter;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.lidroid.xutils.BitmapUtils;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.adapter.CommonAdapter;
import com.lvshandian.asktoask.common.adapter.ViewHolder;
import com.lvshandian.asktoask.entry.Warn;

import java.util.List;

/**
 * 互动详情举报适配器 on 2016/9/27.
 */
public class WarnAdapter extends CommonAdapter<Warn.DataBean>{
    Context context;
    BitmapUtils bitmapUtils;

    public WarnAdapter(Context context, List<Warn.DataBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.context = context;
        bitmapUtils= new BitmapUtils(context);
    }

    @Override
    public void convert(ViewHolder helper, final Warn.DataBean item, int position) {
        helper.setText(R.id.tv_warn_content, item.reportData);
       CheckBox ck=helper.getView(R.id.ck_select);
        ck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    item.isselect=true;
                }else{
                    item.isselect=false;
                }
            }
        });
    }


}
