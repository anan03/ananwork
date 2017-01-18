package com.lvshandian.asktoask.module.message.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.adapter.CommonAdapter;
import com.lvshandian.asktoask.common.adapter.ViewHolder;
import com.lvshandian.asktoask.entry.DataMessageLeave;
import com.lvshandian.asktoask.entry.LeaveMeBean;
import com.lvshandian.asktoask.module.message.LeaveListMEActivity;
import com.lvshandian.asktoask.utils.DateUtil;
import com.lvshandian.asktoask.utils.TextUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 *
 *  给我留言adapter
 *
 * Created by zhang on 2016/10/9.
 */
public class LeaveMessagMeAdapter extends CommonAdapter<LeaveMeBean.Datebean> {
    Context context;
    BitmapUtils bitmapUtils;
    DataMessageLeave.DataBean2.DataBean bean;


    public LeaveMessagMeAdapter(Context context, DataMessageLeave.DataBean2.DataBean item,List<LeaveMeBean.Datebean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.context = context;
        bean=item;
        bitmapUtils = new BitmapUtils(context);

    }

    @Override
    public void convert(ViewHolder helper, LeaveMeBean.Datebean item, int position) {


        ImageLoader.getInstance().displayImage(bean.getUserHeadImg(), (ImageView) helper.getView(R.id.iv_hudong_type_detail));

        helper.setText(R.id.tv_hudong_type_username, bean.getUserRealName());
        if (("男").equals(bean.getUserSex())) {
            Drawable sexicon = mContext.getResources().getDrawable(R.drawable.sex_men);
            sexicon.setBounds(0, 0, sexicon.getMinimumWidth(), sexicon.getMinimumHeight());
            ((TextView) helper.getView(R.id.tv_hudong_type_username)).setCompoundDrawables(null, null, sexicon, null);
        } else if ("女".equals(bean.getUserSex())) {
            Drawable sexicon = mContext.getResources().getDrawable(R.drawable.sex_women);
            sexicon.setBounds(0, 0, sexicon.getMinimumWidth(), sexicon.getMinimumHeight());
            ((TextView) helper.getView(R.id.tv_hudong_type_username)).setCompoundDrawables(null, null, sexicon, null);
        }

        if(!TextUtils.isEmpty(bean.getUserSchool())){
            helper.getView(R.id.tv_hudong_detail_label_school).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_hudong_detail_label_school, bean.getUserSchool() + "");
        }else{
           helper.getView(R.id.tv_hudong_detail_label_school).setVisibility(View.INVISIBLE);
        }

        if(!TextUtils.isEmpty(bean.getUserMajor())){
            helper.getView(R.id.tv_hudong_detail_label_major).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_hudong_detail_label_major, bean.getUserMajor() + "");
        }else{
            helper.getView(R.id.tv_hudong_detail_label_major).setVisibility(View.INVISIBLE);
        }

        if(!TextUtils.isEmpty(bean.getUserGrade())){
            helper.getView(R.id.tv_hudong_detail_label_grade).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_hudong_detail_label_grade, bean.getUserGrade() + "");
        }else{
            helper.getView(R.id.tv_hudong_detail_label_grade).setVisibility(View.INVISIBLE);
        }

        helper.setText(R.id.tv_leavedata, item.leaverData+ "");//留言
        helper.setText(R.id.date_time, DateUtil.timesSwitch(item.leaverDate));//时间date_time
        Log.d("aaa","打印时间"+item.leaverDate);

    }


}
