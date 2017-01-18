package com.lvshandian.asktoask.module.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.android.volley.Request;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.adapter.CommonAdapter;
import com.lvshandian.asktoask.common.adapter.ViewHolder;
import com.lvshandian.asktoask.common.http.HttpDatas;
import com.lvshandian.asktoask.common.http.RequestCode;
import com.lvshandian.asktoask.entry.AnswerDetailToAsk;
import com.lvshandian.asktoask.entry.DataFandetails;
import com.lvshandian.asktoask.entry.DataUserAnswer;
import com.lvshandian.asktoask.entry.HuDongItem;
import com.lvshandian.asktoask.module.interaction.activity.HuDongDetailActivity;
import com.lvshandian.asktoask.utils.DateUtil;
import com.lvshandian.asktoask.utils.JsonUtil;
import com.lvshandian.asktoask.utils.TextUtils;
import com.lvshandian.asktoask.utils.UrlBuilder;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 创建关注详情适配器
 */

public class DataFandetailsAdapter extends CommonAdapter<DataFandetails.DataBean.AnswersBean> {
    Context context;
    private String name = "";
    HttpDatas HttpDatas;
    ConcurrentHashMap<String, String> map;

    public DataFandetailsAdapter(Context context, String name,List<DataFandetails.DataBean.AnswersBean> mDatas, int itemLayoutId,HttpDatas httpDatas) {
        super(context, mDatas, itemLayoutId);
        this.name = name;
        this.context = context;
        map=new ConcurrentHashMap<>();
        this.HttpDatas=httpDatas;



    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);
            switch (msg.what) {
                case RequestCode.ANSWER_DETAIL_ANSWER_ASK_MEN://答咖回答列表
                    AnswerDetailToAsk.DataBean bean= JsonUtil.json2Bean(json, AnswerDetailToAsk.DataBean.class);
                    AnswerDetailToAsk.DataBean.UserAndQuestionBean beannei=bean.userAndQuestion;
                    if(TextUtils.isCollect(beannei.userId, bean.cId)){
                        beannei.isCollect=true;
                    }else{
                        beannei.isCollect=false;
                    }
                    if(TextUtils.isParse(beannei.userId, bean.pId)){
                        beannei.isParse=true;
                    }else{
                        beannei.isParse=false;
                    }


                    HuDongItem.DataBean.PageBean.DataBean2 beandetail=new  HuDongItem.DataBean.PageBean.DataBean2();
                    beandetail.questionTitle = beannei.questionTitle;
                    beandetail.questionData = beannei.questionData;
                    beandetail.questionType = beannei.questionType;
                    beandetail.questionMoney = beannei.questionMoney;
                    beandetail.isanonymity = beannei.isanonymity;
                    beandetail.userId = beannei.userId;
                    beandetail.questionPublishDate = beannei.questionPublishDate;
                    beandetail.questionCollection = beannei.questionCollection;
                    beandetail.questionPraise = beannei.questionPraise;
                    beandetail.answerNum = beannei.answerNum;
                    beandetail.userHeadImg = beannei.userHeadImg;
                    beandetail.userRealName =beannei.userRealName;
                    beandetail.userSex = beannei.userSex;
                    beandetail.questionImgs = beannei.questionImgs;
                    beandetail.userSchool = beannei.userSchool;
                    beandetail.userGrade = beannei.userGrade;
                    beandetail.userMajor =beannei.userMajor;
                    beandetail.questionId = beannei.questionId;
                    beandetail.isapprove = beannei.isapprove;
                    beandetail.extend1 = beannei.extend1;
                    beandetail.iscollect = beannei.isCollect;
                    beandetail.ispraise = beannei.isParse;
                    GoToHuDongDetai(beandetail);

                    break;
                default:break;
            }
        }


    };


    /**
     * 跳转到互动详情
     * @param hudongdetail
     */
    private  void GoToHuDongDetai(HuDongItem.DataBean.PageBean.DataBean2 hudongdetail){
        Intent intent=new Intent(context, HuDongDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(HuDongDetailActivity.TRANSFER,hudongdetail);
        context.startActivity(intent);
    }

    @Override
    public void convert(ViewHolder helper, final DataFandetails.DataBean.AnswersBean item, int position) {


        helper.setText(R.id.tv_words, item.getAnswerData());
        helper.setText(R.id.tv_date, DateUtil.timesTwo(item.getAnswerDate()));
        helper.setText(R.id.tv_name, name);
        helper.setText(R.id.tv_content, item.getExtend1());
        helper.getView(R.id.answer_detail_answer_toask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.clear();
                map.put("userId",item.quizzerId);
                map.put("questionID", item.getQuestionId());
                HttpDatas.getData("答咖详情跳转提问人", Request.Method.POST, UrlBuilder.ANSWER_DETAIL_ANSWER_ASKPENPLE, map, mHandler, RequestCode.ANSWER_DETAIL_ANSWER_ASK_MEN);
            }
        });




    }
}
