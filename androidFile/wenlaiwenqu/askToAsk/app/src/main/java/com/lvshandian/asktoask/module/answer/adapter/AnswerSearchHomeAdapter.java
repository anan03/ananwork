package com.lvshandian.asktoask.module.answer.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.lidroid.xutils.BitmapUtils;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.adapter.CommonAdapter;
import com.lvshandian.asktoask.common.adapter.ViewHolder;
import com.lvshandian.asktoask.common.http.HttpDatas;
import com.lvshandian.asktoask.common.http.RequestCode;
import com.lvshandian.asktoask.entry.AnswerSearchHome;
import com.lvshandian.asktoask.module.answer.activity.AnswerDetailActivity;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.UrlBuilder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 答咖搜索的adapter on 2016/10/14.
 */
public class AnswerSearchHomeAdapter extends CommonAdapter<AnswerSearchHome.DataBean>{
    Context context;
    private boolean isfocus = false;
    HttpDatas httpdata;
    AnswerSearchHome.DataBean itemreal;
    ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

    public AnswerSearchHomeAdapter(Context context, List<AnswerSearchHome.DataBean> mDatas, int itemLayoutId, HttpDatas httpdatas) {
        super(context, mDatas, itemLayoutId);
        this.context = context;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);
            switch (msg.what) {
                case RequestCode.FOCUS_RECODE:
//                    Toast.makeText(context,"答咖关注成功",Toast.LENGTH_SHORT).show();
                    break;
                case RequestCode.CANCLE_FOCUS_CODE:
//                    Toast.makeText(context,"答咖取消关注",Toast.LENGTH_SHORT).show();
                    break;
            }

        }

    };



    @Override
    public void convert(ViewHolder helper, final AnswerSearchHome.DataBean item, int position) {

        ImageLoader.getInstance().displayImage(item.userHeadImg, (ImageView) helper.getView(R.id.iv_answer_item_chiefly2));
        helper.setText(R.id.tv_answer_chiefly_name, item.userRealName);
        //答咖详情跳转
        helper.getView(R.id.rl_answer_detail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AnswerDetailActivity.class);
                intent.putExtra(AnswerDetailActivity.ANSWERDETAILID,item.userId);
                intent.putExtra(AnswerDetailActivity.ANSWERDETAILNAME,item.userRealName);
                context.startActivity(intent);
            }
        });
        itemreal=item;
        final TextView tvisApprove = (TextView) helper.getView(R.id.tv_is_focus);
        if ("y".equals(item.extend2)) {
            tvisApprove.setText("已关注");
            tvisApprove.setTextColor(context.getResources().getColor(R.color.cccccccolor));
            tvisApprove.setBackgroundResource(R.drawable.answer_chiefly_unfocus);
            itemreal.isfocus=false;
        } else {
            tvisApprove.setText("+ 关注");
            tvisApprove.setTextColor(context.getResources().getColor(R.color.main));
            tvisApprove.setBackgroundResource(R.drawable.answer_chiefly_focus);

            itemreal.isfocus=true;
        }
        tvisApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (itemreal.isfocus) {
                    tvisApprove.setText("已关注");
                    tvisApprove.setTextColor(context.getResources().getColor(R.color.cccccccolor));
                    tvisApprove.setBackgroundResource(R.drawable.answer_chiefly_unfocus);
                    itemreal.isfocus = false;
                    goToFocus();


                } else {
                    tvisApprove.setText("+ 关注");
                    tvisApprove.setTextColor(context.getResources().getColor(R.color.main));
                    tvisApprove.setBackgroundResource(R.drawable.answer_chiefly_focus);
                    itemreal.isfocus = true;
                    goToCancleFocus();
                }

            }
        });


    }


    /**
     * 去关注
     */

    private void goToFocus() {

        map.clear();
//        attentorId=1&attentoredId=2
        map.put("attentorId", Global.getUserId(mContext));
        map.put("attentoredId", itemreal.userId);
        httpdata.getData("关注", Request.Method.POST, UrlBuilder.FOCUS_ANSWER_URL, map, mHandler, RequestCode.FOCUS_RECODE);
    }

    /**
     * 取消关注
     */

    private void goToCancleFocus() {
        map.clear();
//        attentorId=1&attentoredId=2
        map.put("attentorId", Global.getUserId(mContext));
        map.put("attentoredId", itemreal.userId);
        httpdata.getData("取消关注", Request.Method.POST, UrlBuilder.CANCLE_FOCUS_ANSWER_URL, map, mHandler, RequestCode.CANCLE_FOCUS_CODE);
    }


}
