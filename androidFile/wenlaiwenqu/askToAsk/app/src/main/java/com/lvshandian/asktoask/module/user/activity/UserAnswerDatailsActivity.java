package com.lvshandian.asktoask.module.user.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lvshandian.asktoask.BaseActivity;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.http.HttpDatas;
import com.lvshandian.asktoask.common.http.RequestCode;
import com.lvshandian.asktoask.entry.DataUserAnswer;
import com.lvshandian.asktoask.entry.HuDongItem;
import com.lvshandian.asktoask.entry.UserAnswerInfo;
import com.lvshandian.asktoask.module.interaction.activity.HuPicLunBoActivity;
import com.lvshandian.asktoask.module.user.adapter.MyGridViewAdapter;
import com.lvshandian.asktoask.module.user.adapter.UserAnswerInfoAdapter;
import com.lvshandian.asktoask.utils.Constant;
import com.lvshandian.asktoask.utils.DateUtil;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.JsonUtil;
import com.lvshandian.asktoask.utils.MethodUtils;
import com.lvshandian.asktoask.utils.PicUtils;
import com.lvshandian.asktoask.utils.ShareUtils;
import com.lvshandian.asktoask.view.MyGridView;
import com.lvshandian.asktoask.utils.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;

/**
 * 创建回答页面详情  我的模块里面的
 */
public class UserAnswerDatailsActivity extends BaseActivity {
    ImageView ivHudongDetailBack;
    TextView tvHudongDetailType;
    ImageView ivHudongTypeDetail;
    TextView tvHudongTypeUsername;
    ImageView ivHudongDetailSex;
    TextView tvHudongDetailLabelSchool;
    TextView tvHudongDetailLabelMajor;
    TextView tvHudongDetailLabelGrade;
    LinearLayout llHudongDetailLab;
    TextView tvHudongDetailPrice;
    TextView tvTitleHudongDetail;
    TextView tvTimeHudongDetail;
    LinearLayout llHudongDetailOncick;
    ImageView ivItemleftHudongDetail;
    ImageView ivItemrightHudongDetail;
    LinearLayout llPicJump;
    CheckBox ckHudongDetailCollectNum;
    TextView tvHudongDetailCollectNum;
    CheckBox ck_hudong_detail_praise;
    TextView tvPraiseNum;
    LinearLayout ivShareHudongDetail;
    LinearLayout llBig;
    TextView tvWenzhu;
    TextView tv_texttwo;
    String[] pic;//图片数组
    RelativeLayout rlHudongUnseletMaster;
    TextView pserson_answer_count;
    @Bind(R.id.pull_lv_collect)
    PullToRefreshListView pullLvCollect;
    //    @Bind(R.id.et_answer_content)
    EditText etAnswerContent;
    @Bind(R.id.iv_hudong_detail_text)
    ImageView ivHudongDetailText;
    @Bind(R.id.tv_send_answer)
    TextView tvSendAnswer;
    @Bind(R.id.ll_answer)
    LinearLayout llAnswer;
    @Bind(R.id.iv_back_approve_address)
    ImageView ivBackApproveAddress;
    private MyGridView gv_item_img;
    private ListView listview;
    boolean iSCollection = false;
    boolean iSPraise = false;
    List<UserAnswerInfo.DataBean> list;
    List<UserAnswerInfo.DataBean> listreal = new ArrayList<>();
    private String page = "1";
    private Boolean ismore = false;

    private ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
    private DataUserAnswer.DataBean.UserAndQuestionsBean dataBean;
    private View headerview;
    final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);
            HuDongItem.DataBean bean = null;
            Intent intent = new Intent();
            intent.setAction(Constant.BROADCAST_ANSWER);
            switch (msg.what) {
                case RequestCode.PRAISE_CODE:  //点赞
//                    ToastUtils.showSnackBar(snackView, "点赞成功");
                    sendBroadcast(intent);

                    break;
                case RequestCode.PRAISE_CANCLE_CODE:  //取消点赞
//                    ToastUtils.showSnackBar(snackView, "取消点赞");
                    sendBroadcast(intent);

                    break;
                case RequestCode.COLLECT_CODE://收藏
//                    ToastUtils.showSnackBar(snackView, "收藏成功");
                    sendBroadcast(intent);

                    break;
                case RequestCode.COLLECT_CANCLE_CODE://取消收藏
//                    ToastUtils.showSnackBar(snackView, "取消收藏");
                    sendBroadcast(intent);
                    break;

                case RequestCode.USER_ANSWER_QUWSTION_COOD://回答问题
                    ToastUtils.showSnackBar(snackView, "回答问题成功");
                    etAnswerContent.setText("");
                    retqusetAnswer();

                    break;
                case RequestCode.USER_ANSWER_QUWSTION_COOD_INFO://回答问题详情列表；

                    /**
                     * 判断是否有答师来进行布局的更换
                     */
                    JSONObject obj = null;
                    String totalRecord = "";
                    String datainfo = "";
                    try {
                        obj = new JSONObject(json);
                        datainfo = obj.optString("data");
                        totalRecord = "" + obj.optInt("totalRecord");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (!TextUtils.isEmpty(totalRecord)) {
                        pserson_answer_count.setText(totalRecord + "人");

                    }
                    list = JsonUtil.json2BeanList(datainfo, UserAnswerInfo.DataBean.class);
                    if (list.size() == 0 & !ismore) {
                        /**
                         * 没有数据不展示
                         */
                        rlHudongUnseletMaster.setVisibility(View.VISIBLE);

                    } else {
                        /**
                         * 有数据展示答师数据
                         */
                        rlHudongUnseletMaster.setVisibility(View.GONE);

                    }
                    if (ismore) {
                        listreal.addAll(list);
                        userFansAdapter.notifyDataSetChanged();
                    } else {
                        listreal.clear();
                        listreal = list;
                    }
                    userFansAdapter = new UserAnswerInfoAdapter(getContext(), listreal, R.layout.item_userauza_info);
                    pullLvCollect.setAdapter(userFansAdapter);

                    break;


            }
        }
    };
    UserAnswerInfoAdapter userFansAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activit_user_answer_info_detail;


    }

    @Override
    protected void initListener() {
        etAnswerContent = (EditText) findViewById(R.id.et_answer_content);
        addHeadLayout();//找头部数据
        //收藏
        ckHudongDetailCollectNum.setOnClickListener(this);
        tvHudongDetailCollectNum.setOnClickListener(this);
        //点赞
        ck_hudong_detail_praise.setOnClickListener(this);
        tvPraiseNum.setOnClickListener(this);
        //分享
        ivShareHudongDetail.setOnClickListener(this);
        //发送
        tvSendAnswer.setOnClickListener(this);

        ivBackApproveAddress.setOnClickListener(this);
        pullLvCollect.setMode(PullToRefreshBase.Mode.BOTH);
        pullLvCollect.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {//下拉刷新
                String label = DateUtils.formatDateTime(
                        getContext(),
                        System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                // 显示最后更新的时间
                refreshView.getLoadingLayoutProxy()
                        .setLastUpdatedLabel(label);
                ismore = false;
                page = "1";
                retqusetAnswer();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {//上拉加载更多
                ismore = true;
                page = (Integer.parseInt(page) + 1) + "";
                retqusetAnswer();
            }
        });
    }

    //查找Head 头部控件，赋值
    private void addHeadLayout() {
        headerview = View.inflate(mContext, R.layout.activity_user_answer_detail_header_layout, null);
        ivHudongDetailBack = (ImageView) headerview.findViewById(R.id.iv_hudong_detail_back);
        tvHudongDetailType = (TextView) headerview.findViewById(R.id.tv_hudong_detail_type);
        ivHudongTypeDetail = (ImageView) headerview.findViewById(R.id.iv_hudong_type_detail);
        tvHudongTypeUsername = (TextView) headerview.findViewById(R.id.tv_hudong_type_username);
        ivHudongDetailSex = (ImageView) headerview.findViewById(R.id.iv_hudong_detail_sex);
        tvHudongDetailLabelSchool = (TextView) headerview.findViewById(R.id.tv_hudong_detail_label_school);
        tvHudongDetailLabelMajor = (TextView) headerview.findViewById(R.id.tv_hudong_detail_label_major);
        tvHudongDetailLabelGrade = (TextView) headerview.findViewById(R.id.tv_hudong_detail_label_grade);
        llHudongDetailLab = (LinearLayout) headerview.findViewById(R.id.ll_hudong_detail_lab);
        tvHudongDetailPrice = (TextView) headerview.findViewById(R.id.tv_hudong_detail_price);
        tvTitleHudongDetail = (TextView) headerview.findViewById(R.id.tv_title_hudong_detail);
        tvTimeHudongDetail = (TextView) headerview.findViewById(R.id.tv_time_hudong_detail);
        llHudongDetailOncick = (LinearLayout) headerview.findViewById(R.id.ll_hudong_detail_oncick);
        ivItemleftHudongDetail = (ImageView) headerview.findViewById(R.id.iv_itemleft_hudong_detail);
        ivItemrightHudongDetail = (ImageView) headerview.findViewById(R.id.iv_itemright_hudong_detail);
        ckHudongDetailCollectNum = (CheckBox) headerview.findViewById(R.id.ck_hudong_detail_collect_num);
        tvHudongDetailCollectNum = (TextView) headerview.findViewById(R.id.tv_hudong_detail_collect_num);
        llHudongDetailOncick = (LinearLayout) headerview.findViewById(R.id.ll_hudong_detail_oncick);
        ck_hudong_detail_praise = (CheckBox) headerview.findViewById(R.id.ck_hudong_detail_praise);
        tvPraiseNum = (TextView) headerview.findViewById(R.id.tv_praise_num);
        llBig = (LinearLayout) headerview.findViewById(R.id.ll_big);
        tvWenzhu = (TextView) headerview.findViewById(R.id.tv_wenzhu);
        rlHudongUnseletMaster = (RelativeLayout) headerview.findViewById(R.id.rl_hudong_unselet_master);
        ivShareHudongDetail = (LinearLayout) headerview.findViewById(R.id.iv_share_hudong_detail);
        llPicJump = (LinearLayout) headerview.findViewById(R.id.ll_pic_jump);
        gv_item_img = (MyGridView) headerview.findViewById(R.id.gv_item_img);
        pserson_answer_count = (TextView) headerview.findViewById(R.id.pserson_answer_count);
        tv_texttwo = (TextView) headerview.findViewById(R.id.tv_texttwo);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            //收藏
            case R.id.tv_hudong_detail_collect_num:
            case R.id.ck_hudong_detail_collect_num:

                if (iSCollection) {
                    //取消收藏
                    iSCollection = false;
                    goToCancleCollect(dataBean);
                    dataBean.setQuestionCollection(dataBean.getQuestionCollection() - 1);

                    tvHudongDetailCollectNum.setText(dataBean.getQuestionCollection() + "");
                } else {

                    iSCollection = true;
                    //收藏
                    if (com.lvshandian.asktoask.utils.TextUtils.isEmpty("" + dataBean.getQuestionCollection())) {
                        dataBean.setQuestionCollection(1);
                    }

                    //收藏
                    goToCollect(dataBean);
                    dataBean.setQuestionCollection(dataBean.getQuestionCollection() + 1);

                    tvHudongDetailCollectNum.setText(dataBean.getQuestionCollection() + "");
                }

                break;
            //点赞
            case R.id.ck_hudong_detail_praise:
            case R.id.tv_praise_num:

                //点赞

                if (iSPraise) {
                    //取消点赞
                    iSPraise = false;
                    goToCanclePraise(dataBean);
                    dataBean.setQuestionPraise(dataBean.getQuestionPraise() - 1);
                    tvPraiseNum.setText(dataBean.getQuestionPraise() + "");
                } else {
                    iSPraise = true;
                    //点赞
                    if (com.lvshandian.asktoask.utils.TextUtils.isEmpty("" + dataBean.getQuestionPraise())) {
                        dataBean.setQuestionPraise(1);
                    }
                    goToPraise(dataBean);
                    dataBean.setQuestionPraise(dataBean.getQuestionPraise() + 1);
                    tvPraiseNum.setText(dataBean.getQuestionPraise() + "");
                }


                break;
            //分享
            case R.id.iv_share_hudong_detail:

                DataUserAnswer.DataBean.UserAndQuestionsBean item = new DataUserAnswer.DataBean.UserAndQuestionsBean();
                item.setQuestionId(dataBean.getQuestionId());
                item.setQuestionData(dataBean.getQuestionData());
                item.setUserId(dataBean.getUserId());
                item.setQuestionTitle(dataBean.getQuestionTitle());
                ShareUtils.goToShare(mContext, item);

                break;
            //发送
            case R.id.tv_send_answer:
                if (TextUtils.isEmpty(MethodUtils.getTextContent(etAnswerContent))) {
                    ToastUtils.showSnackBar(snackView, "请输入回答");
                    return;
                }
                anserQuza(dataBean);
                break;

            //返回
            case R.id.iv_back_approve_address:

                finish();
                break;


        }

    }


    /**
     * 请求回答列表数据
     */
    @Override
    protected void initialized() {


        requestData();
        //给头部设置数据
        headData();


    }

    /**
     * 设置头部数据
     */
    private void headData() {
        /**
         * 初始收藏和点赞自己是否点赞，假设都未点赞和收藏：
         //         */
        //点赞
        ck_hudong_detail_praise.setChecked(getIntent().getBooleanExtra("idPid", false));
        ckHudongDetailCollectNum.setChecked(getIntent().getBooleanExtra("idCid", false));
        iSPraise = getIntent().getBooleanExtra("idPid", false);
        iSCollection = getIntent().getBooleanExtra("idCid", false);
        //头像
        ImageLoader.getInstance().displayImage(dataBean.getUserHeadImg(), ivHudongTypeDetail);
        //姓名
        tvHudongTypeUsername.setText(dataBean.getUserRealName());
        tv_texttwo.setText(dataBean.getQuestionData());
        //学校
        if (com.lvshandian.asktoask.utils.TextUtils.isEmpty(dataBean.getUserSchool())) {
            tvHudongDetailLabelSchool.setVisibility(View.INVISIBLE);
        } else {
            tvHudongDetailLabelSchool.setVisibility(View.VISIBLE);
            tvHudongDetailLabelSchool.setText(dataBean.getUserSchool());
        }
        //专业
        if (com.lvshandian.asktoask.utils.TextUtils.isEmpty(dataBean.getUserMajor())) {
            tvHudongDetailLabelMajor.setVisibility(View.INVISIBLE);
        } else {
            tvHudongDetailLabelMajor.setVisibility(View.VISIBLE);

            tvHudongDetailLabelMajor.setText(dataBean.getUserMajor());
        }
        //年级
        if (com.lvshandian.asktoask.utils.TextUtils.isEmpty(dataBean.getUserGrade())) {
            tvHudongDetailLabelGrade.setVisibility(View.INVISIBLE);
        } else {
            tvHudongDetailLabelGrade.setVisibility(View.VISIBLE);
            tvHudongDetailLabelGrade.setText(dataBean.getUserGrade());
        }
        //赏金
        tvHudongDetailPrice.setText("" + dataBean.getQuestionMoney() + ".00");
        //内容
        tvTitleHudongDetail.setText(dataBean.getQuestionTitle());
        //时间
        tvTimeHudongDetail.setText(DateUtil.timesOne(dataBean.getQuestionPublishDate()));
        pic = PicUtils.getPic(dataBean.getQuestionImgs());
        gv_item_img.setAdapter(new MyGridViewAdapter(pic, mContext));
        gv_item_img.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), HuPicLunBoActivity.class);
                intent.putExtra(HuPicLunBoActivity.PIC, dataBean.getQuestionImgs());
                startActivity(intent);

            }
        });

        //收藏个数
        tvHudongDetailCollectNum.setText("" + dataBean.getQuestionCollection());
        //点赞个数
        tvPraiseNum.setText("" + dataBean.getQuestionPraise());
        if (!TextUtils.isEmpty(dataBean.getUserSex())) {
            if ("男".equals(dataBean.getUserSex())) {
                Drawable sexicon = getResources().getDrawable(R.drawable.sex_men);
                sexicon.setBounds(0, 0, sexicon.getMinimumWidth(), sexicon.getMinimumHeight());
                tvHudongTypeUsername.setCompoundDrawables(null, null, sexicon, null);
            } else if ("女".equals(dataBean.getUserSex())) {
                Drawable sexicon = getResources().getDrawable(R.drawable.sex_women);
                sexicon.setBounds(0, 0, sexicon.getMinimumWidth(), sexicon.getMinimumHeight());
                tvHudongTypeUsername.setCompoundDrawables(null, null, sexicon, null);
            }

        }


    }

    /**
     * 数据源
     */


    private void requestData() {
        dataBean = (DataUserAnswer.DataBean.UserAndQuestionsBean) getIntent().getSerializableExtra("dataBean");
        listview = pullLvCollect.getRefreshableView();
        listview.addHeaderView(headerview);
        retqusetAnswer();
    }

    /**
     * 请求回答详情列表
     */
    private void retqusetAnswer() {
        map.clear();
        map.put("userId", Global.getUserId(mContext));
        map.put("pageNum", page);
        map.put("questionId", dataBean.getQuestionId());
        map.put("quizzerId", dataBean.getUserId());
        httpDatas.getData("回答问题列表", Request.Method.POST, com.lvshandian.asktoask.utils.UrlBuilder.USER_ANSWER_QUESTION_INFO, map, mHandler, RequestCode.USER_ANSWER_QUWSTION_COOD_INFO);
        pullLvCollect.post(new Runnable() {
            @Override
            public void run() {
                pullLvCollect.onRefreshComplete();
            }
        });

    }


    /**
     * 回答问题
     */
    private void anserQuza(DataUserAnswer.DataBean.UserAndQuestionsBean transferbean) {
        map.clear();
        map.put("quizzerId", transferbean.getUserId());
        map.put("answererId", Global.getUserId(mContext));
        map.put("answerData", MethodUtils.getTextContent(etAnswerContent));
        map.put("questionId", "" + transferbean.getQuestionId());
        map.put("questionTitle", transferbean.getQuestionTitle());
        httpDatas.getData("回答问题", Request.Method.POST, com.lvshandian.asktoask.utils.UrlBuilder.USER_ANSWER_QUESTION, map, mHandler, RequestCode.USER_ANSWER_QUWSTION_COOD);
    }


    /**
     * 去点赞
     */
    private void goToPraise(DataUserAnswer.DataBean.UserAndQuestionsBean transferbean) {
        map.clear();
        map.put("praiserId", Global.getUserId(mContext));
        map.put("praiseredId", "" + transferbean.getUserId());
        map.put("questionId", "" + transferbean.getQuestionId());
        httpDatas.getData("点赞", Request.Method.POST, com.lvshandian.asktoask.utils.UrlBuilder.PRAISE_URL, map, mHandler, RequestCode.PRAISE_CODE, false);
    }

    /**
     * 取消点赞
     */
    private void goToCanclePraise(DataUserAnswer.DataBean.UserAndQuestionsBean transferbean) {
        map.clear();
        map.put("praiserId", Global.getUserId(mContext));
        map.put("praiseredId", transferbean.getUserId());
        map.put("questionId", "" + transferbean.getQuestionId());
        httpDatas.getData("取消点赞", Request.Method.POST, com.lvshandian.asktoask.utils.UrlBuilder.PRAISE_CANCEL_URL, map, mHandler, RequestCode.PRAISE_CANCLE_CODE, false);
    }

    /**
     * 收藏
     */
    private void goToCollect(DataUserAnswer.DataBean.UserAndQuestionsBean transferbean) {
        map.clear();
        map.put("collectorId", Global.getUserId(mContext));
        map.put("collectordId", transferbean.getUserId());
        map.put("questionId", "" + transferbean.getQuestionId());
        httpDatas.getData("收藏", Request.Method.POST, com.lvshandian.asktoask.utils.UrlBuilder.COLLECT_URL, map, mHandler, RequestCode.COLLECT_CODE, false);
    }

    /**
     * 取消收藏
     */
    private void goToCancleCollect(DataUserAnswer.DataBean.UserAndQuestionsBean transferbean) {
        map.clear();
        map.put("collectorId", Global.getUserId(mContext));
        map.put("collectordId", transferbean.getUserId());
        map.put("questionId", "" + transferbean.getQuestionId());
        httpDatas.getData("取消收藏", Request.Method.POST, com.lvshandian.asktoask.utils.UrlBuilder.COLLECT_CANCLE_URL, map, mHandler, RequestCode.COLLECT_CANCLE_CODE, false);
    }


}
