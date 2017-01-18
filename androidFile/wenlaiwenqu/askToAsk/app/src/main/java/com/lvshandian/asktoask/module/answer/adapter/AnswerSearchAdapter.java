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
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.adapter.CommonAdapter;
import com.lvshandian.asktoask.common.adapter.ViewHolder;
import com.lvshandian.asktoask.common.http.HttpDatas;
import com.lvshandian.asktoask.common.http.RequestCode;
import com.lvshandian.asktoask.entry.AnswerSearch;
import com.lvshandian.asktoask.module.answer.activity.AnswerDetailActivity;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.ToastUtils;
import com.lvshandian.asktoask.utils.UrlBuilder;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
/**
 *
 *  答咖搜索adapter
 */
public class AnswerSearchAdapter extends CommonAdapter<AnswerSearch.DataBean> {
    Context context;
    HttpDatas httpdata;
    ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
     TextView tvisApprove;
    public AnswerSearchAdapter(Context context, List<AnswerSearch.DataBean> mDatas, int itemLayoutId,HttpDatas httpdatas) {
        super(context, mDatas, itemLayoutId);
        this.context = context;
        httpdata = httpdatas;
    }

    @Override
    public void convert(ViewHolder helper, final AnswerSearch.DataBean item, int position) {
        ImageLoader.getInstance().displayImage(item.userHeadImg,(ImageView)(helper.getView(R.id.iv_answer_item_search)));
        helper.setText(R.id.tv_answer_search_name, item.userRealName);
        helper.setText(R.id.tv_accept_rate,  "采纳率为："+item.extend1);//采纳率赋值
        helper.getView(R.id.rl_answer_detail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * 答咖详情
                 */
                Intent intent = new Intent(context, AnswerDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(AnswerDetailActivity.ANSWERDETAILID, item.userId);
                context.startActivity(intent);
            }
        });
        tvisApprove = (TextView) helper.getView(R.id.tv_is_focus);

        if ("y".equals(item.extend2)) {
            tvisApprove.setText("已关注");
            tvisApprove.setBackgroundResource(R.drawable.answer_chiefly_focus);
            item.isfocus = true;
        } else {
            tvisApprove.setText("+ 关注");
            tvisApprove.setBackgroundResource(R.drawable.answer_chiefly_unfocus);
            item.isfocus = false;
        }

        tvisApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (item.isfocus) {
                    tvisApprove.setText("+ 关注");
                    tvisApprove.setBackgroundResource(R.drawable.answer_chiefly_unfocus);
                    item.isfocus = false;
                    map.clear();
                    map.put("attentorId", Global.getUserId(mContext));
                    map.put("attentoredId", item.userId);
                    httpdata.getData("取消关注", Request.Method.POST, UrlBuilder.CANCLE_FOCUS_ANSWER_URL, map, mHandler, RequestCode.CANCLE_FOCUS_CODE,false);
                } else {

                    tvisApprove.setText("已关注");
                    tvisApprove.setBackgroundResource(R.drawable.answer_chiefly_focus);
                    item.isfocus = true;
                    map.clear();
                    map.put("attentorId", Global.getUserId(mContext));
                    map.put("attentoredId", item.userId);
                    httpdata.getData("关注", Request.Method.POST, UrlBuilder.FOCUS_ANSWER_URL, map, mHandler, RequestCode.FOCUS_RECODE,false);

                }

            }
        });


    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);
            switch (msg.what) {
                case RequestCode.FOCUS_RECODE:
                    ToastUtils.showSnackBar(tvisApprove, "关注成功");
                    break;
                case RequestCode.CANCLE_FOCUS_CODE:
                    ToastUtils.showSnackBar(tvisApprove,"取消关注");
                    break;
            }

        }

    };







}
