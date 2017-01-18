package com.lvshandian.asktoask.module.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.android.volley.Request;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.adapter.CommonAdapter;
import com.lvshandian.asktoask.common.adapter.ViewHolder;
import com.lvshandian.asktoask.common.http.HttpDatas;
import com.lvshandian.asktoask.common.http.RequestCode;
import com.lvshandian.asktoask.entry.DataUserAnswer;
import com.lvshandian.asktoask.entry.DataUserCollect;
import com.lvshandian.asktoask.entry.HuDongItem;
import com.lvshandian.asktoask.module.interaction.activity.HuDongDetailActivity;
import com.lvshandian.asktoask.utils.DateUtil;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.ShareUtils;
import com.lvshandian.asktoask.utils.TextUtils;
import com.lvshandian.asktoask.utils.UrlBuilder;
import com.lvshandian.asktoask.utils.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 我的模块
 * 创建收藏适配器
 */
public class UserCollectAdapter extends CommonAdapter<DataUserCollect.DataBean.QuestionsBean> {
    Context context;
    // 用来控制点赞CheckBox的选中状况
    private static HashMap<Integer, Boolean> likeIssSelected;
    //用来控制收藏的选中的情况
    private static HashMap<Integer, Boolean> collectisSelected;
    HttpDatas httpData;
    private ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
    private View snackView;
    List<DataUserCollect.DataBean.QuestionsBean> mDatas;
    final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();

            String json = data.getString(HttpDatas.info);
            HuDongItem.DataBean bean = null;
            switch (msg.what) {
                case RequestCode.PRAISE_CODE:  //点赞
//                    ToastUtils.showSnackBar(snackView, "点赞成功");
                    break;
                case RequestCode.PRAISE_CANCLE_CODE:  //取消点赞
//                    ToastUtils.showSnackBar(snackView, "取消点赞");
                    break;
                case RequestCode.COLLECT_CODE://收藏
//                    ToastUtils.showSnackBar(snackView, "收藏成功");
                    break;
                case RequestCode.COLLECT_CANCLE_CODE://取消收藏
//                    ToastUtils.showSnackBar(snackView, "取消收藏");
                    break;

            }
        }
    };
    private String cidArray = "";
    private String pidArray = "";

    public UserCollectAdapter(String cidArray, String pidArray, Context context, HttpDatas httpData, View snackView, List<DataUserCollect.DataBean.QuestionsBean> mDatas, int itemLayoutId,HttpDatas http) {
        super(context, mDatas, itemLayoutId);
        this.context = context;
        this.httpData = httpData;
        this.mDatas = mDatas;
        this.cidArray = cidArray;
        this.pidArray = pidArray;
        this.snackView = snackView;
        likeIssSelected = new HashMap<Integer, Boolean>();
        collectisSelected = new HashMap<Integer, Boolean>();
        this.httpData=http;
        initDate();
    }

    /**
     * 初始化点赞状态
     */
    private void initDate() {
        for (int i = 0; i < mDatas.size(); i++) {
            if (TextUtils.isEmpty(mDatas.get(i).getQuestionId()) || TextUtils.isEmpty(pidArray)) {
                getIsSelected().put(i, false);
                continue;
            }
            if (TextUtils.isParse(mDatas.get(i).getQuestionId(), pidArray)) {
                getIsSelected().put(i, true);
            } else {
                getIsSelected().put(i, false);
            }
        }
        for (int i = 0; i < mDatas.size(); i++) {
            if (TextUtils.isEmpty(mDatas.get(i).getQuestionId()) || TextUtils.isEmpty(cidArray)) {
                getCollectisSelected().put(i, false);
                continue;
            }
            //收藏
            if (TextUtils.isParse(mDatas.get(i).getQuestionId(), cidArray)) {
                getCollectisSelected().put(i, true);
            } else {
                getCollectisSelected().put(i, false);

            }
        }
    }

    /**
     * 点赞
     *
     * @return
     */
    public static HashMap<Integer, Boolean> getIsSelected() {
        return likeIssSelected;
    }

    public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        UserCollectAdapter.likeIssSelected = isSelected;
    }

    /**
     * 收藏
     *
     * @return
     */
    public static HashMap<Integer, Boolean> getCollectisSelected() {
        return collectisSelected;
    }

    public static void setCollectIsSelected(HashMap<Integer, Boolean> collectisSelected) {
        UserCollectAdapter.collectisSelected = collectisSelected;
    }

    @Override
    public void convert(final ViewHolder helper, final DataUserCollect.DataBean.QuestionsBean item, final int position) {
        helper.setText(R.id.tv_title_hudong, item.getQuestionTitle());
//        helper.setText(R.id.tv_time_hudong, timesOne(item.getQuestionPublishDate()));
        helper.setText(R.id.tv_price_hudong, "￥" + item.getQuestionMoney()+"0");
        helper.setText(R.id.tv_type_hudong, item.getQuestionType());
        helper.setText(R.id.tv_hudong_collect_num, "" + item.getQuestionCollection());
        helper.setText(R.id.tv_praise_num, "" + item.getQuestionPraise());
        helper.setText(R.id.tv_time_hudong, "" + DateUtil.timesOne(item.getQuestionPublishDate()));
        final Boolean iscollect=TextUtils.isCollect(item.questionId, cidArray);
        final Boolean ispraise=TextUtils.isCollect(item.questionId, pidArray);

        //跳转到详情界面
        helper.getView(R.id.ll_user_collect_detail_ready).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HuDongItem.DataBean.PageBean.DataBean2 beandetail = new HuDongItem.DataBean.PageBean.DataBean2();
                beandetail.questionTitle = item.questionTitle;
                beandetail.questionType = item.questionType;
                beandetail.questionMoney = item.questionMoney;
                beandetail.userId = item.userId;
                beandetail.questionPublishDate = item.questionPublishDate;
                beandetail.questionCollection = item.questionCollection;
                beandetail.questionPraise = item.questionPraise;
                beandetail.answerNum = item.answerNum;
                beandetail.userHeadImg = item.userHeadImg;
                beandetail.userRealName = item.userRealName;
                beandetail.questionImgs = item.questionImgs;
                beandetail.userSchool = item.userSchool;
                beandetail.userGrade = item.userGrade;
                beandetail.userMajor = item.userMajor;
                beandetail.questionId = item.questionId;
                beandetail.extend1 = item.extend1;
                beandetail.iscollect =iscollect;
                beandetail.ispraise = ispraise;
                Intent intent = new Intent(context, HuDongDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(HuDongDetailActivity.TRANSFER, beandetail);
                context.startActivity(intent);
            }
        });





        if (TextUtils.isEmpty(item.getQuestionImgs())) {
            helper.getView(R.id.ll_pic_jump).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.ll_pic_jump).setVisibility(View.VISIBLE);
            int num = isBig2(item.getQuestionImgs());
            if (num == 1) {
                ImageLoader.getInstance().displayImage(pic[0], (ImageView) helper.getView(R.id.iv_itemleft_hudong));
            }
            if (num == 2) {
                ImageLoader.getInstance().displayImage(pic[0], (ImageView) helper.getView(R.id.iv_itemleft_hudong));
                ImageLoader.getInstance().displayImage(pic[1], (ImageView) helper.getView(R.id.iv_itemright_hudong));
            }

        }
        //分享监听
        helper.getView(R.id.iv_share_hudong).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataUserAnswer.DataBean.UserAndQuestionsBean item1=new DataUserAnswer.DataBean.UserAndQuestionsBean();
                item1.setQuestionId(item.getQuestionId());
                item1.setQuestionData(item.getQuestionData());
                item1.setUserId(item.getUserId());
                item1.setQuestionTitle(item.getQuestionTitle());
                ShareUtils.goToShare(mContext,item1);

            }
        });
        //点赞监听
        helper.getView(R.id.ck_hudong_praise).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (likeIssSelected.get(position)) {
                    likeIssSelected.put(position, false);
                    setIsSelected(likeIssSelected);
                    mDatas.get(position).setQuestionPraise(item.getQuestionPraise() - 1);
                    helper.setText(R.id.tv_praise_num, item.getQuestionPraise() + "");
                    //取消点赞

                    goToCanclePraise(item);

                } else {
                    likeIssSelected.put(position, true);
                    setIsSelected(likeIssSelected);
                    mDatas.get(position).setQuestionPraise(item.getQuestionPraise() + 1);
                    helper.setText(R.id.tv_praise_num, item.getQuestionPraise() + "");
                    //点赞
                    goToPraise(item);
                }


            }
        });

        //收藏监听
        helper.getView(R.id.ck_hudong_collect_num).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (collectisSelected.get(position)) {
                    collectisSelected.put(position, false);
                    setCollectIsSelected(collectisSelected);
                    mDatas.get(position).setQuestionCollection(item.getQuestionCollection() - 1);
                    helper.setText(R.id.tv_hudong_collect_num, item.getQuestionCollection() + "");
                    //取消收藏
                    goToCancleCollect(item);

                } else {
                    collectisSelected.put(position, true);
                    setCollectIsSelected(collectisSelected);
                    mDatas.get(position).setQuestionCollection(item.getQuestionCollection() + 1);
                    helper.setText(R.id.tv_hudong_collect_num, item.getQuestionCollection() + "");
                    //收藏
                    goToCollect(item);
                }


            }
        });


        // 根据点赞likeisSelected来设置checkbox的选中状况
        CheckBox checkBox = (CheckBox) helper.getView(R.id.ck_hudong_praise);
        checkBox.setChecked(getIsSelected().get(position));

        // 根据点赞collectisSelected来设置checkbox的选中状况
        CheckBox checkBoxcoolect = (CheckBox) helper.getView(R.id.ck_hudong_collect_num);
        checkBoxcoolect.setChecked(getCollectisSelected().get(position));
    }

    /**
     * 去点赞
     */
    private void goToPraise(DataUserCollect.DataBean.QuestionsBean transferbean) {
        map.clear();
        map.put("praiserId", Global.getUserId(context));
        map.put("praiseredId", transferbean.getUserId());
        map.put("questionId", transferbean.getQuestionId());
        httpData.getData("点赞", Request.Method.POST, UrlBuilder.PRAISE_URL, map, mHandler, RequestCode.PRAISE_CODE,false);
    }

    /**
     * 取消点赞
     */
    private void goToCanclePraise(DataUserCollect.DataBean.QuestionsBean transferbean) {
        map.clear();
        map.put("praiserId",Global.getUserId(context));
        map.put("praiseredId", transferbean.getUserId());
        map.put("questionId", transferbean.getQuestionId());
        httpData.getData("取消点赞", Request.Method.POST, UrlBuilder.PRAISE_CANCEL_URL, map, mHandler, RequestCode.PRAISE_CANCLE_CODE,false);
    }

    /**
     * 收藏
     */
    private void goToCollect(DataUserCollect.DataBean.QuestionsBean transferbean) {
        map.clear();
        map.put("collectorId", Global.getUserId(context));
        map.put("collectordId", transferbean.getUserId());
        map.put("questionId", transferbean.getQuestionId());
        httpData.getData("收藏", Request.Method.POST, UrlBuilder.COLLECT_URL, map, mHandler, RequestCode.COLLECT_CODE,false);
    }

    /**
     * 取消收藏
     */
    private void goToCancleCollect(DataUserCollect.DataBean.QuestionsBean transferbean) {
        map.clear();
        map.put("collectorId", Global.getUserId(context));
        map.put("collectordId", transferbean.getUserId());
        map.put("questionId", transferbean.getQuestionId());
        httpData.getData("取消收藏", Request.Method.POST, UrlBuilder.COLLECT_CANCLE_URL, map, mHandler, RequestCode.COLLECT_CANCLE_CODE,false);
    }

    String[] pic;

    //判断后端返回来图片是否大于两张
    private int isBig2(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        } else {
            pic = TextUtils.convertStrToArray(str);
            if (pic.length == 1) {
                return 1;
            } else {
                return 2;
            }
        }

    }


}
