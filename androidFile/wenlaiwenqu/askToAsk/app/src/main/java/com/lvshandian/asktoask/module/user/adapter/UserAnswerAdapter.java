package com.lvshandian.asktoask.module.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import com.android.volley.Request;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.adapter.CommonAdapter;
import com.lvshandian.asktoask.common.adapter.ViewHolder;
import com.lvshandian.asktoask.common.http.HttpDatas;
import com.lvshandian.asktoask.common.http.RequestCode;
import com.lvshandian.asktoask.entry.DataUserAnswer;
import com.lvshandian.asktoask.module.interaction.activity.HuPicLunBoActivity;
import com.lvshandian.asktoask.module.user.activity.UserAnswerDatailsActivity;
import com.lvshandian.asktoask.utils.DateUtil;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.PicUtils;
import com.lvshandian.asktoask.utils.ShareUtils;
import com.lvshandian.asktoask.utils.TextUtils;
import com.lvshandian.asktoask.utils.UrlBuilder;
import com.lvshandian.asktoask.view.MyGridView;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 创建 回答适配器
 */
public class UserAnswerAdapter extends CommonAdapter<DataUserAnswer.DataBean.UserAndQuestionsBean> {
    Context context;
    HttpDatas httpData;
    private ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
    String[] pic;
    private View snackView;
    List<DataUserAnswer.DataBean.UserAndQuestionsBean> mDatas;
    // 用来控制点赞CheckBox的选中状况
    private static HashMap<Integer, Boolean> likeIssSelected;
    //用来控制收藏的选中的情况
    private static HashMap<Integer, Boolean> collectisSelected;
    private String cidArray = "";
    private String pidArray = "";
    final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
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

    public UserAnswerAdapter(String cidArray, String pidArray, Context context, HttpDatas httpData, View snackView, List<DataUserAnswer.DataBean.UserAndQuestionsBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.context = context;
        this.mDatas = mDatas;
        this.httpData = httpData;
        this.snackView = snackView;
        likeIssSelected = new HashMap<Integer, Boolean>();
        collectisSelected = new HashMap<Integer, Boolean>();
        this.cidArray = cidArray;
        this.pidArray = pidArray;
        initDate();

    }

    /**
     * 初始化点赞状态
     */
    private void initDate() {
        //点赞
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
        UserAnswerAdapter.likeIssSelected = isSelected;
    }

    /**
     * //     * 收藏
     *
     * @return
     */
    public static HashMap<Integer, Boolean> getCollectisSelected() {
        return collectisSelected;
    }

    public static void setCollectIsSelected(HashMap<Integer, Boolean> collectisSelected) {
        UserAnswerAdapter.collectisSelected = collectisSelected;
    }

    @Override
    public void convert(final ViewHolder helper, final DataUserAnswer.DataBean.UserAndQuestionsBean item, final int position) {
        ImageLoader.getInstance().displayImage(item.getUserHeadImg(), (ImageView) helper.getView(R.id.iv_hudong_type_detail));//头像
        ImageView ivSex=helper.getView(R.id.iv_answer_detail_sex);
        if (!TextUtils.isEmpty(item.getUserSex())) {
            ivSex.setVisibility(View.VISIBLE);
            if (item.getUserSex().equals("男")) {
                helper.setImageResource(R.id.iv_answer_detail_sex, R.drawable.sex_men);

            } else if (item.getUserSex().equals("女")) {
                helper.setImageResource(R.id.iv_answer_detail_sex, R.drawable.sex_women);

            }
        }else{
            ivSex.setVisibility(View.INVISIBLE);
        }

        /**
         * 学校专业和年级为空的话 不展示
         */

        //姓名
        helper.setText(R.id.tv_hudong_type_username, item.getUserRealName());
        //学校
        if(TextUtils.isEmpty(item.getUserSchool())){
            helper.getView(R.id.tv_hudong_detail_label_school).setVisibility(View.INVISIBLE);
        }else{
            helper.getView(R.id.tv_hudong_detail_label_school).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_hudong_detail_label_school, item.getUserSchool() + "");
        }
        //专业
        if(TextUtils.isEmpty(item.getUserMajor())){
            helper.getView(R.id.tv_hudong_detail_label_major).setVisibility(View.INVISIBLE);
        }else{
            helper.getView(R.id.tv_hudong_detail_label_major).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_hudong_detail_label_major, item.getUserMajor() + "");
        }
        //年级
        if(TextUtils.isEmpty(item.getUserGrade())){
            helper.getView(R.id.tv_hudong_detail_label_grade).setVisibility(View.INVISIBLE);
        }else{
            helper.getView(R.id.tv_hudong_detail_label_grade).setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_hudong_detail_label_grade, item.getUserGrade() + "");
        }
        //发表内容
        helper.setText(R.id.tv_title_hudong_detail, item.getQuestionTitle() + "");
        //发表详情
        helper.setText(R.id.tv_content_answer, item.getQuestionData());
        //时间
        helper.setText(R.id.tv_time_hudong_detail, DateUtil.timesOne(item.getQuestionPublishDate()) + "");
        //收藏个数
        helper.setText(R.id.tv_hudong_detail_collect_num, item.getQuestionCollection() + "");
        //点赞个数
        helper.setText(R.id.tv_praise_num, item.getQuestionPraise() + "");
        //赏金
        helper.setText(R.id.tv_hudong_detail_price, "￥"+item.getQuestionMoney() + ".00");
        pic = PicUtils.getPic(item.getQuestionImgs());
        ((MyGridView) helper.getView(R.id.gv_item_img)).setAdapter(new MyGridViewAdapter(pic, context));//gridview展示图片
        ((MyGridView) helper.getView(R.id.gv_item_img)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, HuPicLunBoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(HuPicLunBoActivity.PIC,item.getQuestionImgs());
                context.startActivity(intent);
            }
        });

        //点赞监听
        helper.getView(R.id.ck_hudong_detail_praise).setOnClickListener(new OnClickListener() {

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
        helper.getView(R.id.ck_hudong_detail_collect_num).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (collectisSelected.get(position)) {
                    collectisSelected.put(position, false);
                    setCollectIsSelected(collectisSelected);
                    mDatas.get(position).setQuestionCollection(item.getQuestionCollection() - 1);
                    helper.setText(R.id.tv_hudong_detail_collect_num, item.getQuestionCollection() + "");
                    //取消收藏
                    goToCancleCollect(item);
                } else {
                    collectisSelected.put(position, true);
                    setCollectIsSelected(collectisSelected);
                    mDatas.get(position).setQuestionCollection(item.getQuestionCollection() + 1);
                    helper.setText(R.id.tv_hudong_detail_collect_num, item.getQuestionCollection() + "");
                    //收藏
                    goToCollect(item);
                }
            }
        });
        helper.getView(R.id.ll_item_answer).setOnClickListener(new MyonclickListener(item, position));
        //分享
        helper.getView(R.id.iv_share_hudong_detail).setOnClickListener(new MyonclickListener(item, position));
        // 根据点赞likeisSelected来设置checkbox的选中状况
        CheckBox checkBox = (CheckBox) helper.getView(R.id.ck_hudong_detail_praise);
        checkBox.setChecked(getIsSelected().get(position));
        // 根据点赞collectisSelected来设置checkbox的选中状况
        CheckBox checkBoxcoolect = (CheckBox) helper.getView(R.id.ck_hudong_detail_collect_num);
        checkBoxcoolect.setChecked(getCollectisSelected().get(position));
    }

    class MyonclickListener implements OnClickListener {
        DataUserAnswer.DataBean.UserAndQuestionsBean item;
        private int position;
        MyonclickListener(DataUserAnswer.DataBean.UserAndQuestionsBean item, int position) {
            this.position = position;
            this.item = item;
        }
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                /**
                 * 跳转到回答详情
                 */
                case R.id.ll_item_answer:
                    Intent intent = new Intent(context, UserAnswerDatailsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("dataBean", item);
                    //点赞
                    intent.putExtra("idPid", getIsSelected().get(position));
                    //收藏
                    intent.putExtra("idCid", collectisSelected.get(position));
                    context.startActivity(intent);
                    break;
                //分享
                case R.id.iv_share_hudong_detail:
                    ShareUtils.goToShare(mContext,item);
                    break;
            }
        }
    }


    /**
     * 去点赞
     */
    private void goToPraise(DataUserAnswer.DataBean.UserAndQuestionsBean transferbean) {
        map.clear();
        map.put("praiserId", Global.getUserId(context));
        map.put("praiseredId", transferbean.getUserId());
        map.put("questionId", "" + transferbean.getQuestionId());
        httpData.getData("点赞", Request.Method.POST, UrlBuilder.PRAISE_URL, map, mHandler, RequestCode.PRAISE_CODE);
    }

    /**
     * 取消点赞
     */
    private void goToCanclePraise(DataUserAnswer.DataBean.UserAndQuestionsBean transferbean) {
        map.clear();
        map.put("praiserId", Global.getUserId(context));
        map.put("praiseredId", transferbean.getUserId());
        map.put("questionId", "" + transferbean.getQuestionId());
        httpData.getData("取消点赞", Request.Method.POST, UrlBuilder.PRAISE_CANCEL_URL, map, mHandler, RequestCode.PRAISE_CANCLE_CODE);
    }

    /**
     * 收藏
     */
    private void goToCollect(DataUserAnswer.DataBean.UserAndQuestionsBean transferbean) {
        map.clear();
        map.put("collectorId", Global.getUserId(context));
        map.put("collectordId", transferbean.getUserId());
        map.put("questionId", "" + transferbean.getQuestionId());
        httpData.getData("收藏", Request.Method.POST, UrlBuilder.COLLECT_URL, map, mHandler, RequestCode.COLLECT_CODE);
    }

    /**
     * 取消收藏
     */
    private void goToCancleCollect(DataUserAnswer.DataBean.UserAndQuestionsBean transferbean) {
        map.clear();
        map.put("collectorId", Global.getUserId(context));
        map.put("collectordId", transferbean.getUserId());
        map.put("questionId", "" + transferbean.getQuestionId());
        httpData.getData("取消收藏", Request.Method.POST, UrlBuilder.COLLECT_CANCLE_URL, map, mHandler, RequestCode.COLLECT_CANCLE_CODE);
    }

}