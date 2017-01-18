package com.lvshandian.asktoask.module.user.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lvshandian.asktoask.BaseActivity;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.http.HttpDatas;
import com.lvshandian.asktoask.common.http.RequestCode;
import com.lvshandian.asktoask.entry.AnswerDetail;
import com.lvshandian.asktoask.entry.DataAdoption;
import com.lvshandian.asktoask.entry.DataQuiz;
import com.lvshandian.asktoask.entry.DataUserAttention;
import com.lvshandian.asktoask.module.answer.adapter.AnswerDetailInnerAdapter;
import com.lvshandian.asktoask.module.user.adapter.DataAdoptionAdapter;
import com.lvshandian.asktoask.module.user.adapter.DataAskAdapter;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.JsonUtil;
import com.lvshandian.asktoask.utils.MethodUtils;
import com.lvshandian.asktoask.utils.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;


import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;

/**
 *  2016/9/29 0029.
 * <p/>
 * 创建关注详情界面
 */
public class AttentionDetailActivity extends BaseActivity {
    @Bind(R.id.pull_lv_collect)
    PullToRefreshListView pullLvCollect;
    @Bind(R.id.tv_leave_message)
    TextView tvLeaveMessage;
    @Bind(R.id.ll_answer_detail_leave_words)
    LinearLayout llAnswerDetailLeaveWords;
    @Bind(R.id.ll_answer_detail_focus)
    LinearLayout llAnswerDetailFocus;
    ImageView ivDetailBack;
    ImageView ivAnswerDetail;
    TextView tvAnswerName;
    ImageView userSex;
    TextView tvAnswerFocusNum;
    TextView tvAnswerFanNum;
    TextView userSchool;
    TextView userMajor;
    TextView userGrade;
    TextView tvAnswerSolveNum;
    TextView tvAnswerAskNum;
    LinearLayout llAsk;
    TextView tvAnswerAcceptNum;
    LinearLayout llAccept;
    //提问
    View tv_answer_ask_numtext_color;
    TextView tv_answer_ask_num_text;
    //采纳
    View tv_answer_accept_num_text_color;
    TextView tv_answer_accept_num_text;
    //回答
    View tv_answer_solve_num_text_color;
    TextView tv_answer_solve_num_text;
    private ConcurrentHashMap map;

    //    @Bind(R.id.swrelayout)
//    SwipeRefreshLayout swrelayout;
    private String attentoredId;
    private final int CODE = 1100;
    private int ATTENCODE = 101;
    private DataUserAttention.DataBean dataBean;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);
            switch (msg.what) {
                //关注请求接收数据
                case RequestCode.USERATTENTION_DETAILS_CODE:

                    AnswerDetail.DataBean detail = JsonUtil.json2Bean(json, AnswerDetail.DataBean.class);

//                    头像
                    ImageLoader.getInstance().displayImage(detail.userHeadImg, ivAnswerDetail);
                    //姓名
                    tvAnswerName.setText(detail.userRealName);
                    //关注
                    tvAnswerFocusNum.setText(detail.userAttentions+"");
//                    //粉丝
                    tvAnswerFanNum.setText(detail.userFans+"");
                    //学校
                    userSchool.setText(detail.userSchool);
                    //专业
                    userMajor.setText(detail.userMajor);
                    //年级
                    userGrade.setText(detail.userGrade);
                    //回答
                    tvAnswerSolveNum.setText(detail.userAnswer+"");
                    //提问
                    tvAnswerAskNum.setText(detail.userAsk + "");
                    //采样率
                    tvAnswerAcceptNum.setText(detail.adoptionRates);

                    //性别：

                    if ("男".equals(detail.userSex)) {
                        Drawable sexicon = getResources().getDrawable(R.drawable.sex_men);
                        sexicon.setBounds(0, 0, sexicon.getMinimumWidth(), sexicon.getMinimumHeight());
                        tvAnswerName.setCompoundDrawables(null, null, sexicon, null);
                    } else if ("女".equals(detail.userSex)) {
                        Drawable sexicon = getResources().getDrawable(R.drawable.sex_women);
                        sexicon.setBounds(0, 0, sexicon.getMinimumWidth(), sexicon.getMinimumHeight());
                        tvAnswerName.setCompoundDrawables(null, null, sexicon, null);
                    }



                    pullLvCollect.setAdapter(new AnswerDetailInnerAdapter(getContext(), detail.answer, R.layout.item_answer_detail_inner, tvAnswerName.getText().toString(), httpDatas));

                    break;
                //关注
                case RequestCode.LIKE_CODE:
                    ToastUtils.showSnackBar(snackView, "关注成功");
                    finish();
                    break;


                //采纳率
                case RequestCode.ATTENTION_ADOPTIONRATE_CODE:


                    List<DataAdoption.DataBean> dataBeanList =JsonUtil.json2BeanList(json, DataAdoption.DataBean.class);

                    DataAdoptionAdapter dataAdoptionAdapter = new DataAdoptionAdapter(getContext(), dataBean.getUserRealName(), dataBeanList, R.layout.item_user_dataadoption);

                    pullLvCollect.setAdapter(dataAdoptionAdapter);

                    break;


                //提问
                case RequestCode.ATTENTION_AUIZ_CODE:
                    List<DataQuiz.DataBean> dataBeanquiz =JsonUtil.json2BeanList(json, DataQuiz.DataBean.class);
                    DataAskAdapter dataQuizAdapterataAskAdapter = new DataAskAdapter(getContext(), dataBeanquiz, R.layout.item_ask,httpDatas,dataBean.getAttentoredId());
                    pullLvCollect.setAdapter(dataQuizAdapterataAskAdapter);
                    break;


            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fandetails_detail;
    }

    private View view;

    @Override
    protected void initListener() {
        view = View.inflate(mContext, R.layout.attentiontails_head, null);
        ivDetailBack = (ImageView) view.findViewById(R.id.iv_detail_back);
        ivAnswerDetail = (ImageView) view.findViewById(R.id.iv_answer_detail);
        tvAnswerName = (TextView) view.findViewById(R.id.tv_answer_name);
        userSex = (ImageView) view.findViewById(R.id.user_sex);
        tvAnswerFocusNum = (TextView) view.findViewById(R.id.tv_answer_focus_num);
        tvAnswerFanNum = (TextView) view.findViewById(R.id.tv_answer_fan_num);
        userSchool = (TextView) view.findViewById(R.id.user_school);
        userMajor = (TextView) view.findViewById(R.id.user_major);
        userGrade = (TextView) view.findViewById(R.id.user_grade);
        tvAnswerSolveNum = (TextView) view.findViewById(R.id.tv_answer_solve_num);

        //提问
        tvAnswerAskNum = (TextView) view.findViewById(R.id.tv_answer_ask_num);
        tv_answer_ask_numtext_color = (View) view.findViewById(R.id.tv_answer_ask_numtext_color);
        tv_answer_ask_num_text = (TextView) view.findViewById(R.id.tv_answer_ask_num_text);
        //采纳
        tvAnswerAcceptNum = (TextView) view.findViewById(R.id.tv_answer_accept_num);
        tv_answer_accept_num_text_color = (View) view.findViewById(R.id.tv_answer_accept_num_text_color);
        tv_answer_accept_num_text = (TextView) view.findViewById(R.id.tv_answer_accept_num_text);
        //回答
        tvAnswerSolveNum = (TextView) view.findViewById(R.id.tv_answer_solve_num);
        tv_answer_solve_num_text_color = (View) view.findViewById(R.id.tv_answer_solve_num_text_color);
        tv_answer_solve_num_text = (TextView) view.findViewById(R.id.tv_answer_solve_num_text);

        //采纳率
        llAccept = (LinearLayout) view.findViewById(R.id.ll_accept);
        //提问
        llAsk = (LinearLayout) view.findViewById(R.id.ll_ask);
        //回答
        LinearLayout ll_anser = (LinearLayout) view.findViewById(R.id.ll_anser);


        llAnswerDetailLeaveWords.setOnClickListener(this);
        llAnswerDetailFocus.setOnClickListener(this);

        ivDetailBack.setOnClickListener(this);
        //回答监听
        ll_anser.setOnClickListener(this);
        //提问监听；
        llAsk.setOnClickListener(this);
        //采纳率监听
        llAccept.setOnClickListener(this);


    }

    private ListView listView;

    @Override
    protected void initialized() {
        pullLvCollect.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listView = pullLvCollect.getRefreshableView();
        attentoredId = getIntent().getStringExtra("attentoredId");
        dataBean = (DataUserAttention.DataBean) getIntent().getSerializableExtra("item");
        listView.addHeaderView(view);

        pullLvCollect.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(
                        getContext(),
                        System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                // 显示最后更新的时间
                refreshView.getLoadingLayoutProxy()
                        .setLastUpdatedLabel(label);
                requesHttp();
            }
        });

        requesHttp();

    }

    /**
     * 关注详情
     */
    private void requesHttp() {

        ConcurrentHashMap map = new ConcurrentHashMap<>();
        map.clear();

        map.put("dId", Global.getUserId(getContext()));
        map.put("userId",dataBean.getAttentoredId());//从答咖首页传来的用户id

//        map.put("attentoredId", dataBean.getAttentoredId());
        httpDatas.getData("我的界面关注详情", Request.Method.POST, UrlBuilder.ANSERSEARCHDETAIL_URL, map, mHandler, RequestCode.USERATTENTION_DETAILS_CODE);
        pullLvCollect.post(new Runnable() {
            @Override
            public void run() {
                pullLvCollect.onRefreshComplete();
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            //关注
            case R.id.ll_answer_detail_focus:
                map = new ConcurrentHashMap<>();
                map.clear();
                map.put("attentoredId", dataBean.getAttentoredId());
                map.put("attentorId", Global.getUserId(mContext));
                httpDatas.getData("我的界面关注详情问题", Request.Method.POST, UrlBuilder.FOCUS_ANSWER_URL, map, mHandler, RequestCode.LIKE_CODE);

                break;
            //留言
            case R.id.ll_answer_detail_leave_words:

                Intent intent = new Intent(this, LeaveMessageActivity.class);
                intent.putExtra("attentoredId", attentoredId);
                intent.putExtra("name", MethodUtils.getTextContent(tvAnswerName));
                startActivityForResult(intent, CODE);


                break;
            //采纳率
            case R.id.ll_accept:
                tv_answer_ask_numtext_color.setVisibility(View.INVISIBLE);//提问线显示
                tv_answer_solve_num_text_color.setVisibility(View.INVISIBLE);
                tv_answer_accept_num_text_color.setVisibility(View.VISIBLE);

                tvAnswerAcceptNum.setTextColor((mContext.getResources().getColor(R.color.main)));
                tv_answer_accept_num_text_color.setBackgroundResource(R.color.main);
                tv_answer_accept_num_text.setTextColor(mContext.getResources().getColor(R.color.main));
                tvAnswerSolveNum.setTextColor((mContext.getResources().getColor(R.color.gray)));
                tv_answer_solve_num_text_color.setBackgroundResource(R.color.gray);
                tv_answer_solve_num_text.setTextColor(mContext.getResources().getColor(R.color.gray));
                tvAnswerAskNum.setTextColor((mContext.getResources().getColor(R.color.gray)));
                tv_answer_ask_numtext_color.setBackgroundResource(R.color.gray);
                tv_answer_ask_num_text.setTextColor(mContext.getResources().getColor(R.color.gray));
                map = new ConcurrentHashMap<>();
                map.clear();
                map.put("userId", dataBean.getAttentoredId());
                httpDatas.getData("我的界面关注详情采纳率", Request.Method.POST, UrlBuilder.ATTENTION_ADOPTIONRATE, map, mHandler, RequestCode.ATTENTION_ADOPTIONRATE_CODE);
                break;
            //提问
            case R.id.ll_ask:
                tv_answer_ask_numtext_color.setVisibility(View.VISIBLE);//提问线显示
                tv_answer_solve_num_text_color.setVisibility(View.INVISIBLE);
                tv_answer_accept_num_text_color.setVisibility(View.INVISIBLE);

                //采样
                tvAnswerAcceptNum.setTextColor((mContext.getResources().getColor(R.color.gray)));
                tv_answer_accept_num_text_color.setBackgroundResource(R.color.gray);
                tv_answer_accept_num_text.setTextColor(mContext.getResources().getColor(R.color.gray));
                //回答
                tvAnswerSolveNum.setTextColor((mContext.getResources().getColor(R.color.gray)));
                tv_answer_solve_num_text_color.setBackgroundResource(R.color.gray);
                tv_answer_solve_num_text.setTextColor(mContext.getResources().getColor(R.color.gray));
                //提问
                tvAnswerAskNum.setTextColor((mContext.getResources().getColor(R.color.main)));
                tv_answer_ask_numtext_color.setBackgroundResource(R.color.main);
                tv_answer_ask_num_text.setTextColor(mContext.getResources().getColor(R.color.main));

                map = new ConcurrentHashMap<>();
                map.clear();
                map.put("userId", dataBean.getAttentoredId());
                httpDatas.getData("我的界面关注详情提问", Request.Method.POST, UrlBuilder.ATTENTION_AUIZ, map, mHandler, RequestCode.ATTENTION_AUIZ_CODE);

                break;
            //回答
            case R.id.ll_anser:

                tv_answer_ask_numtext_color.setVisibility(View.INVISIBLE);//提问线显示
                tv_answer_solve_num_text_color.setVisibility(View.VISIBLE);
                tv_answer_accept_num_text_color.setVisibility(View.INVISIBLE);

                requesHttp();
                //采样
                tvAnswerAcceptNum.setTextColor((mContext.getResources().getColor(R.color.gray)));
                tv_answer_accept_num_text_color.setBackgroundResource(R.color.gray);
                tv_answer_accept_num_text.setTextColor(mContext.getResources().getColor(R.color.gray));
                //回答
                tvAnswerSolveNum.setTextColor((mContext.getResources().getColor(R.color.main)));
                tv_answer_solve_num_text_color.setBackgroundResource(R.color.main);
                tv_answer_solve_num_text.setTextColor(mContext.getResources().getColor(R.color.main));
                //提问
                tvAnswerAskNum.setTextColor((mContext.getResources().getColor(R.color.gray)));
                tv_answer_ask_numtext_color.setBackgroundResource(R.color.gray);
                tv_answer_ask_num_text.setTextColor(mContext.getResources().getColor(R.color.gray));
                break;
            //返回监听
            case R.id.iv_detail_back:
                finish();
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(LeaveMessageActivity.isSuccess){
            ToastUtils.showSnackBar(snackView,"留言成功");
            LeaveMessageActivity.isSuccess=false;
        }

    }


}
