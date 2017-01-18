package com.lvshandian.asktoask.module.interaction.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.lidroid.xutils.BitmapUtils;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.adapter.CommonAdapter;
import com.lvshandian.asktoask.common.adapter.ViewHolder;
import com.lvshandian.asktoask.entry.HuDongDetail;
import com.lvshandian.asktoask.entry.HuDongItem;
import com.lvshandian.asktoask.utils.DateUtil;
import com.lvshandian.asktoask.utils.TextUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 互动详情里面的adapter on 2016/9/27.
 */
public class HuDongDetailAdapter extends CommonAdapter<HuDongDetail.DataBean.DataBeanNei>{
    Context context;
    BitmapUtils bitmapUtils;

    public HuDongDetailAdapter(Context context, List<HuDongDetail.DataBean.DataBeanNei> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.context = context;
        bitmapUtils = new BitmapUtils(context);
    }

    @Override
    public void convert(ViewHolder helper, HuDongDetail.DataBean.DataBeanNei item, int position) {
        ImageLoader.getInstance().displayImage(item.userHeadImg, (ImageView) helper.getView(R.id.iv_hudong_type_detail_list));
        if("1".equals(item.isaccept)){//1的话是采纳了
            helper.getView(R.id.tv_al_accept).setVisibility(View.VISIBLE);
        }else{
            helper.getView(R.id.tv_al_accept).setVisibility(View.INVISIBLE);
        }

        if(!TextUtils.isEmpty(item.userSchool)){
            helper.getView(R.id.tv_hudong_detail_label_school_list).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_hudong_detail_label_school_list, item.userSchool);
        }else{
            helper.getView(R.id.tv_hudong_detail_label_school_list).setVisibility(View.INVISIBLE);
        }


        if(!TextUtils.isEmpty(item.userGrade)){
            helper.getView(R.id.tv_hudong_detail_label_grade_list).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_hudong_detail_label_grade_list, item.userGrade);
        }else{
            helper.getView(R.id.tv_hudong_detail_label_grade_list).setVisibility(View.INVISIBLE);
        }

        if(!TextUtils.isEmpty(item.userMajor)){
            helper.getView(R.id.tv_hudong_detail_label_major_list).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_hudong_detail_label_major_list, item.userMajor);
        }else{
            helper.getView(R.id.tv_hudong_detail_label_major_list).setVisibility(View.INVISIBLE);
        }


        ImageView iv=helper.getView(R.id.iv_hudong_detail_sex_list);//性别图标
        helper.setText(R.id.tv_hudong_type_username_list, item.userRealName);//名字
        helper.setText(R.id.tv_hudong_detail_content_list,item.answerData);//日期
        helper.setText(R.id.tv_date_hudong_detail, DateUtil.timesOne(item.answerDate));//答案
        Log.d("aaa", "item" + item.toString());
        Log.d("aaa","usersex"+item.userSex);
        if("男".equals(item.userSex)){
            iv.setVisibility(View.VISIBLE);
            iv.setImageResource(R.drawable.sex_men);
        }
        if("女".equals(item.userSex)){
            iv.setVisibility(View.VISIBLE);
            iv.setImageResource(R.drawable.sex_women);
        }


    }


}
