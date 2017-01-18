package com.lvshandian.asktoask.module.answer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lvshandian.asktoask.BaseActivity;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.http.HttpDatas;
import com.lvshandian.asktoask.common.http.RequestCode;
import com.lvshandian.asktoask.entry.AnswerDetail;
import com.lvshandian.asktoask.entry.DataAdoption;
import com.lvshandian.asktoask.entry.DataQuiz;
import com.lvshandian.asktoask.module.answer.adapter.AnswerDetailInnerAdapter;
import com.lvshandian.asktoask.module.user.adapter.DataAdoptionAdapter;
import com.lvshandian.asktoask.module.user.adapter.DataAskAdapter;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.JsonUtil;
import com.lvshandian.asktoask.utils.TextUtils;
import com.lvshandian.asktoask.utils.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;

/**
 * 答咖详情界面  2016/9/23.
 */
public class AnswerDetailActivity extends BaseActivity {
    public static final String ANSWERDETAILID = "detailID";
    public static final String ANSWERDETAILNAME = "detaiName";
    ImageView ivAnswerDetail;
    TextView tvAnswerName;
    ImageView userSex;
    TextView tvAnswerFocusNum;
    TextView tvAnswerFanNum;
    TextView userSchool;
    TextView userMajor;
    TextView userGrade;
    @Bind(R.id.pull_lv_collect)
    PullToRefreshListView pullLvCollect;
    @Bind(R.id.tv_leave_message)
    TextView tvLeaveMessage;
    @Bind(R.id.ll_answer_detail_leave_words)
    LinearLayout llAnswerDetailLeaveWords;
    @Bind(R.id.ll_answer_detail_focus)
    LinearLayout llAnswerDetailFocus;
    @Bind(R.id.dibu)
    LinearLayout dibu;
    private String isAttention;
    private String leavename;
    private int recode = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_answer_detail_layout;
    }

    @Override
    protected void initListener() {
        llAnswerDetailLeaveWords.setOnClickListener(this);
        llAnswerDetailFocus.setOnClickListener(this);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);
            switch (msg.what) {
                case RequestCode.ANSWER_DETAIL://答咖回答列表
                    AnswerDetail.DataBean detail = JsonUtil.json2Bean(json, AnswerDetail.DataBean.class);
                    isAttention = detail.isAttention;//1的话是已经关注了
                    leavename = detail.userRealName;
                    setValue(detail);
                    break;
                case RequestCode.ASK_CODE://答咖详情里面的提问列表

                    List<DataQuiz.DataBean> dataBeanquiz = JsonUtil.json2BeanList(json, DataQuiz.DataBean.class);
                    if (dataBeanquiz.size() == 0 || dataBeanquiz == null) {
                        ToastUtils.showSnackBar(snackView, "该答师没有提问问题");
                    }
                    DataAskAdapter dataQuizAdapterataAskAdapter = new DataAskAdapter(getContext(), dataBeanquiz, R.layout.item_ask, httpDatas, getIntent().getStringExtra(ANSWERDETAILID));
                    pullLvCollect.setAdapter(dataQuizAdapterataAskAdapter);

                    break;
                case RequestCode.ACCEPT_CODE://答咖详情里面的采纳

                    List<DataAdoption.DataBean> dataBeanList = JsonUtil.json2BeanList(json, DataAdoption.DataBean.class);

                    if (dataBeanList.size() == 0 || dataBeanList == null) {
                        ToastUtils.showSnackBar(snackView, "该答师的回答没被他人采纳");
                    }

                    //答咖详情传来的用户答师名字
                    DataAdoptionAdapter dataAdoptionAdapter = new DataAdoptionAdapter(getContext(), getIntent().getStringExtra(ANSWERDETAILNAME), dataBeanList, R.layout.item_user_dataadoption);
                    pullLvCollect.setAdapter(dataAdoptionAdapter);
                    break;
                case RequestCode.LEAVE_WORDS:
                    Toast.makeText(getContext(), "留言成功", Toast.LENGTH_SHORT).show();
                    AnswerLeaveWordsActivity.isconfirm = false;
                    break;

                case RequestCode.FOCUS_RECODE:
                    Toast.makeText(getContext(), "关注成功", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                default:
                    break;
            }
        }


    };

    /**
     * 得到网络数据后展示
     * 答咖回答列表
     */
    private void setValue(AnswerDetail.DataBean detail) {

        ImageLoader.getInstance().displayImage(detail.userHeadImg, ivAnswerDetail);
        if (!TextUtils.isEmpty(detail.userRealName)) {
            tvAnswerName.setText(detail.userRealName);
        } else {
            tvAnswerName.setText("匿名");
        }
        if ("男".equals(detail.userSex)) {
            userSex.setVisibility(View.VISIBLE);
            userSex.setImageResource(R.drawable.sex_men);
        } else if("女".equals(detail.userSex)) {
            userSex.setVisibility(View.VISIBLE);
            userSex.setImageResource(R.drawable.sex_women);
        }else{
            userSex.setVisibility(View.INVISIBLE);
        }
        tvAnswerFocusNum.setText(detail.userAttentions + "");
        tvAnswerFanNum.setText(detail.userFans + "");
        userSchool.setText(detail.userSchool);
        userGrade.setText(detail.userGrade);
        userMajor.setText(detail.userMajor);
        tvAnswerSolveNum.setText(detail.extend2 + "");
        tvAnswerAskNum.setText(detail.userAsk + "");
        tvAnswerAcceptNum.setText(detail.adoptionRates);
        if (detail.answer.size() == 0 || detail.answer == null) {
            ToastUtils.showSnackBar(snackView, "该答师没有回答记录");
        }
        pullLvCollect.setAdapter(new AnswerDetailInnerAdapter(getContext(), detail.answer, R.layout.item_answer_detail_inner, tvAnswerName.getText().toString(), httpDatas));

    }

    View headView;
    ImageView ivDetailBack;
    TextView tvAnswerSolveNum,tvAnswerAskNum,tvAnswerAcceptNum,tvAnswer,tvAsk,tv_accept;
    View answerLine,askLine,tvAcceptLine;

    ConcurrentHashMap map;

    @Override

    protected void initialized() {
        map = new ConcurrentHashMap<>();
        headView = View.inflate(mContext, R.layout.activity_answer_detail_header_layout, null);
        ivDetailBack = (ImageView) headView.findViewById(R.id.iv_detail_back);
        ivAnswerDetail = (ImageView) headView.findViewById(R.id.iv_answer_detail);
        tvAnswerName = (TextView) headView.findViewById(R.id.tv_answer_name);
        userSex = (ImageView) headView.findViewById(R.id.user_sex);
        tvAnswerFocusNum = (TextView) headView.findViewById(R.id.tv_answer_focus_num);
        tvAnswerFanNum = (TextView) headView.findViewById(R.id.tv_answer_fan_num);
        userSchool = (TextView) headView.findViewById(R.id.user_school);
        userMajor = (TextView) headView.findViewById(R.id.user_major);
        userGrade = (TextView) headView.findViewById(R.id.user_grade);
        tvAnswerSolveNum = (TextView) headView.findViewById(R.id.tv_answer_solve_num);
        tvAnswerAskNum = (TextView) headView.findViewById(R.id.tv_answer_ask_num);
        tvAnswerAcceptNum = (TextView) headView.findViewById(R.id.tv_answer_accept_num);
        answerLine= (View) headView.findViewById(R.id.answer_line);
        askLine = (View) headView.findViewById(R.id.ask_line);
        tvAcceptLine = (View) headView.findViewById(R.id.tv_accept_line);
        tvAsk = (TextView) headView.findViewById(R.id.tv_ask);
        tv_accept = (TextView) headView.findViewById(R.id.tv_accept);
        tvAnswer = (TextView) headView.findViewById(R.id.tv_answer);
        ListView list=pullLvCollect.getRefreshableView();
        list.addHeaderView(headView);
        ivDetailBack.setOnClickListener(new MyListener());
        headView.findViewById(R.id.ll_answer).setOnClickListener(new MyListener());
        headView.findViewById(R.id.ll_ask).setOnClickListener(new MyListener());
        headView.findViewById(R.id.ll_accept).setOnClickListener(new MyListener());
        answerDetail();
    }

    /**
     * 请求接口数据 回答接口
     */
    private void answerDetail() {
        map.clear();
        map.put("dId", Global.getUserId(getContext()));
        map.put("userId", getIntent().getStringExtra(ANSWERDETAILID));//从答咖首页传来的用户id
        httpDatas.getData("答咖详情", Request.Method.POST, UrlBuilder.ANSERSEARCHDETAIL_URL, map, mHandler, RequestCode.ANSWER_DETAIL);
    }

    class MyListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_detail_back:
                    finish();
                    break;
                case R.id.ll_ask://  答咖详情提问列表
                    tvAnswerSolveNum.setTextColor(getResources().getColor(R.color.grey));
                    answerLine.setVisibility(View.INVISIBLE);
                    tvAnswer.setTextColor(getResources().getColor(R.color.grey));
                    tvAnswerAskNum.setTextColor(getResources().getColor(R.color.main));
                    tvAsk.setTextColor(getResources().getColor(R.color.main));
                    askLine.setVisibility(View.VISIBLE);
                    askLine.setBackgroundColor(getResources().getColor(R.color.main));
                    tvAnswerAcceptNum.setTextColor(getResources().getColor(R.color.grey));
                    tv_accept.setTextColor(getResources().getColor(R.color.grey));
                    tvAcceptLine.setVisibility(View.INVISIBLE);
                    askQuestion();//重新请求数据 提问
                    break;
                case R.id.ll_accept: //
                    tvAnswerSolveNum.setTextColor(getResources().getColor(R.color.grey));
                    answerLine.setVisibility(View.INVISIBLE);
                    tvAnswer.setTextColor(getResources().getColor(R.color.gray));
                    tvAnswerAskNum.setTextColor(getResources().getColor(R.color.grey));
                    tvAsk.setTextColor(getResources().getColor(R.color.grey));
                    askLine.setVisibility(View.INVISIBLE);
                    tvAnswerAcceptNum.setTextColor(getResources().getColor(R.color.main));
                    tv_accept.setTextColor(getResources().getColor(R.color.main));
                    tvAcceptLine.setVisibility(View.VISIBLE);
                    tvAcceptLine.setBackgroundColor(getResources().getColor(R.color.main));
                    accept();//重新请求数据 采纳
                    break;
                case R.id.ll_answer:
                    tvAnswerSolveNum.setTextColor(getResources().getColor(R.color.main));
                    tvAnswer.setTextColor(getResources().getColor(R.color.main));
                    answerLine.setVisibility(View.VISIBLE);
                    answerLine.setBackgroundColor(getResources().getColor(R.color.main));
                    tvAnswerAskNum.setTextColor(getResources().getColor(R.color.grey));
                    tvAsk.setTextColor(getResources().getColor(R.color.grey));
                    askLine.setVisibility(View.INVISIBLE);
                    tvAnswerAcceptNum.setTextColor(getResources().getColor(R.color.grey));
                    tv_accept.setTextColor(getResources().getColor(R.color.grey));
                    tvAcceptLine.setVisibility(View.INVISIBLE);
                    answerDetail();//重新请求数据 回答数据
                    break;


            }


        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_answer_detail_leave_words:
                leaveWords();
                break;
            case R.id.ll_answer_detail_focus:
                if ("1".equals(isAttention)) {
                    ToastUtils.showSnackBar(snackView, "你已经关注过");
                } else {
                    focus();
                }


                break;
            case R.id.iv_detail_back:
                finish();
                break;
            case R.id.ll_ask://  答咖详情提问列表
                tvAnswerSolveNum.setTextColor(getResources().getColor(R.color.grey));
                answerLine.setVisibility(View.INVISIBLE);
                tvAnswer.setTextColor(getResources().getColor(R.color.grey));


                tvAnswerAskNum.setTextColor(getResources().getColor(R.color.main));
                tvAsk.setTextColor(getResources().getColor(R.color.main));
                askLine.setVisibility(View.VISIBLE);
                askLine.setBackgroundColor(getResources().getColor(R.color.main));


                tvAnswerAcceptNum.setTextColor(getResources().getColor(R.color.grey));
                tv_accept.setTextColor(getResources().getColor(R.color.grey));
                tvAcceptLine.setVisibility(View.INVISIBLE);

                askQuestion();//重新请求数据 提问
                break;
            case R.id.ll_accept: //
                tvAnswerSolveNum.setTextColor(getResources().getColor(R.color.grey));
                answerLine.setVisibility(View.INVISIBLE);
                tvAnswer.setTextColor(getResources().getColor(R.color.gray));

                tvAnswerAskNum.setTextColor(getResources().getColor(R.color.grey));
                tvAsk.setTextColor(getResources().getColor(R.color.grey));
                askLine.setVisibility(View.INVISIBLE);


                tvAnswerAcceptNum.setTextColor(getResources().getColor(R.color.main));
                tv_accept.setTextColor(getResources().getColor(R.color.main));
                tvAcceptLine.setVisibility(View.VISIBLE);
                tvAcceptLine.setBackgroundColor(getResources().getColor(R.color.main));


                accept();//重新请求数据 采纳
                break;
            case R.id.ll_answer:
                tvAnswerSolveNum.setTextColor(getResources().getColor(R.color.main));
                tvAnswer.setTextColor(getResources().getColor(R.color.main));
                answerLine.setVisibility(View.VISIBLE);
                answerLine.setBackgroundColor(getResources().getColor(R.color.main));


                tvAnswerAskNum.setTextColor(getResources().getColor(R.color.grey));
                tvAsk.setTextColor(getResources().getColor(R.color.grey));
                askLine.setVisibility(View.INVISIBLE);


                tvAnswerAcceptNum.setTextColor(getResources().getColor(R.color.grey));
                tv_accept.setTextColor(getResources().getColor(R.color.grey));
                tvAcceptLine.setVisibility(View.INVISIBLE);
                answerDetail();//重新请求数据 回答数据
                break;


        }


    }


    /**
     * 答咖去留言
     */
    private void leaveWords() {
        Intent intent = new Intent(this, AnswerLeaveWordsActivity.class);
        intent.putExtra(AnswerLeaveWordsActivity.LEAVENAME, leavename);
        startActivityForResult(intent, recode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (AnswerLeaveWordsActivity.isconfirm) {
            map.clear();
            map.put("leaverId", Global.getUserId(getContext()));
            map.put("leavedId", getIntent().getStringExtra(ANSWERDETAILID));
            map.put("leaverData", AnswerLeaveWordsActivity.editText.getText().toString());
            httpDatas.getData("答咖详情留言", Request.Method.POST, UrlBuilder.LEAVE_WORDS_URL, map, mHandler, RequestCode.LEAVE_WORDS);
        }

    }

    /**
     * 答咖详情里面的提问
     */
    private void askQuestion() {
        map.clear();
        map.put("userId", getIntent().getStringExtra(ANSWERDETAILID));
        httpDatas.getData("答咖详情里面的提问", Request.Method.POST, UrlBuilder.ASK_URL, map, mHandler, RequestCode.ASK_CODE);

    }

    /**
     * 采纳
     */
    private void accept() {
        map.clear();
        map.put("userId", getIntent().getStringExtra(ANSWERDETAILID));
        httpDatas.getData("答咖详情里面的采纳", Request.Method.POST, UrlBuilder.ACCEPT_URL, map, mHandler, RequestCode.ACCEPT_CODE);


    }

    /**
     * 答咖详情关注
     */

    private void focus() {
        map.clear();
        map.put("attentorId", Global.getUserId(getContext()));
        map.put("attentoredId", getIntent().getStringExtra(ANSWERDETAILID));
        httpDatas.getData("答咖详情关注", Request.Method.POST, UrlBuilder.FOCUS_ANSWER_URL, map, mHandler, RequestCode.FOCUS_RECODE);
    }


}