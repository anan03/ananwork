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
import com.lvshandian.asktoask.module.message.LeaveListMEActivity;
import com.lvshandian.asktoask.module.message.MessageDetailsActivity;
import com.lvshandian.asktoask.utils.DateUtil;
import com.lvshandian.asktoask.utils.TextUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 *
 *
 * 我的模块中的
 * 留言adapter  消息留言adapter
 *
 *
 */
public class LeaveMessagAdapter extends CommonAdapter<DataMessageLeave.DataBean2.DataBean> {
    Context context;
    public LeaveMessagAdapter(Context context, List<DataMessageLeave.DataBean2.DataBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.context = context;
    }

    @Override
    public void convert(ViewHolder helper, DataMessageLeave.DataBean2.DataBean item, int position) {

        ImageLoader.getInstance().displayImage(item.getUserHeadImg(), (ImageView) helper.getView(R.id.iv_hudong_type_detail));
        helper.setText(R.id.tv_hudong_type_username, item.getUserRealName());
        if (("男").equals(item.getUserSex())) {
            Drawable sexicon = mContext.getResources().getDrawable(R.drawable.sex_men);
            sexicon.setBounds(0, 0, sexicon.getMinimumWidth(), sexicon.getMinimumHeight());
            ((TextView) helper.getView(R.id.tv_hudong_type_username)).setCompoundDrawables(null, null, sexicon, null);
        } else if ("女".equals(item.getUserSex())) {
            Drawable sexicon = mContext.getResources().getDrawable(R.drawable.sex_women);
            sexicon.setBounds(0, 0, sexicon.getMinimumWidth(), sexicon.getMinimumHeight());
            ((TextView) helper.getView(R.id.tv_hudong_type_username)).setCompoundDrawables(null, null, sexicon, null);
        }

        TextView tvschool=helper.getView(R.id.tv_hudong_detail_label_school);
        TextView job=helper.getView(R.id.tv_hudong_detail_label_major);
        TextView grade=helper.getView(R.id.tv_hudong_detail_label_grade);

        helper.setText(R.id.tv_time_hudong_detail, item.leaverData);//留言内容
        if(TextUtils.isEmpty(item.getUserSchool())){
            tvschool.setVisibility(View.INVISIBLE);
        }else{
            tvschool.setVisibility(View.VISIBLE);
            tvschool.setText(item.getUserSchool());
        }
        if(TextUtils.isEmpty(item.getUserMajor())){
            job.setVisibility(View.INVISIBLE);
        }else{
            job.setVisibility(View.VISIBLE);
            job.setText(item.getUserMajor());
        }
        if(TextUtils.isEmpty(item.getUserGrade())){
            grade.setVisibility(View.INVISIBLE);
        }else{
            grade.setVisibility(View.VISIBLE);
            grade.setText(item.getUserGrade());
        }
        TextView tvnoread=(TextView)helper.getView(R.id.tv_no_read);
        if("1".equals(item.getExtend1())){
            tvnoread.setVisibility(View.GONE);
        }else{
            tvnoread.setVisibility(View.VISIBLE);
        }
//        helper.setText(R.id.tv_time_hudong_detail, DateUtil.getRecentlyDays(item.getLeaverDate()));
        helper.getView(R.id.ll_leavemessage).setOnClickListener(new MyOnclickListener(item));
    }

    class MyOnclickListener implements View.OnClickListener {

        private DataMessageLeave.DataBean2.DataBean item;

        public MyOnclickListener(DataMessageLeave.DataBean2.DataBean item) {

            this.item = item;
        }

        @Override
        public void onClick(View v) {


            switch (v.getId()) {
                /**
                 * 留言条目监听给我的的所有的留言
                 */
                case R.id.ll_leavemessage:
                    Intent intent = new Intent(context, LeaveListMEActivity.class);
                    intent.putExtra(LeaveListMEActivity.TRANCE, item);
                    context.startActivity(intent);
                    break;
            }

        }
    }
}
