package com.lvshandian.asktoask.module.user.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.lvshandian.asktoask.entry.DataQuzaBean;
import com.lvshandian.asktoask.entry.DataUseQuiz;
import com.lvshandian.asktoask.entry.DataUserAnswer;
import com.lvshandian.asktoask.entry.UserAnswerInfo;
import com.lvshandian.asktoask.entry.Warn;
import com.lvshandian.asktoask.module.interaction.activity.HuPicLunBoActivity;
import com.lvshandian.asktoask.module.interaction.adapter.WarnAdapter;
import com.lvshandian.asktoask.module.user.adapter.MyGridViewAdapter;
import com.lvshandian.asktoask.module.user.adapter.UserQuzaInfoAdapter;
import com.lvshandian.asktoask.utils.DateUtil;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.JsonUtil;
import com.lvshandian.asktoask.utils.PicUtils;
import com.lvshandian.asktoask.utils.ShareUtils;
import com.lvshandian.asktoask.utils.TextUtils;
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
 * 创建提问详情  我的模块
 */
public class QuzaDetailsActivity extends BaseActivity {
    @Bind(R.id.iv_back_approve_address)
    ImageView ivBackApproveAddress;
    TextView pserson_answer_count;
    TextView tvTitleHudong;
    TextView tvTimeHudong;
    TextView tvPriceHudong;
    LinearLayout llPicJump;
    CheckBox ckHudongCollectNum;
    TextView tvHudongCollectNum;
    CheckBox ckHudongPraise;
    TextView tvPraiseNum;
    LinearLayout ivShareHudong;
    View view;
    boolean iSCollection = false;
    boolean iSPraise = false;
    @Bind(R.id.pull_lv_collect)
    PullToRefreshListView pullLvCollect;
    private DataUseQuiz.DataBean dataBean;
    private ListView listview;
    private UserQuzaInfoAdapter userFansAdapter;
    private ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
    private DataQuzaBean.QuestionVoBean dataQuza;//请求到的数据
    private List<UserAnswerInfo.DataBean> list;  //回答列表
    private UserAnswerInfo.DataBean UserDataBean;
    public static List<UserAnswerInfo.DataBean> listreal = new ArrayList<>();  //回答列表适配的
    public static boolean isAccept = false;
    private List<UserAnswerInfo.DataBean> listselect = new ArrayList<>();
    private boolean isfirstcollect = true;
    private boolean isfirstparse = true;
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);
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
                case RequestCode.USER_ACCEPT:
                    listselect.clear();
                    accept.setVisibility(View.INVISIBLE);//采纳成功了隐藏采纳按钮
                    isAccept = true;
                    retqusetAnswer();//重新请你去列表数据
                    userFansAdapter.notifyDataSetChanged();
                    break;
                case RequestCode.USER_AQUZA_DETAILS_CODE:
                    DataQuzaBean dataQuzaBean = JsonUtil.json2Bean(json, DataQuzaBean.class);
                    String pid = dataQuzaBean.getPId();
                    String cId = dataQuzaBean.getCId();
                    //设置头部数据  提问详情里的数据  上方用户数据
                    dataQuza = dataQuzaBean.getQuestionVo();
                    if (TextUtils.isEmpty(dataQuza.getQuestionImgs())) {
                        gvDetailHudong.setVisibility(View.GONE);
                    } else {
                        gvDetailHudong.setVisibility(View.VISIBLE);
                        gvDetailHudong.setAdapter((new MyGridViewAdapter(PicUtils.getPic(dataQuza.getQuestionImgs()), QuzaDetailsActivity.this)));

                        gvDetailHudong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(getContext(), HuPicLunBoActivity.class);
                                intent.putExtra(HuPicLunBoActivity.PIC, dataQuza.getQuestionImgs());
                                startActivity(intent);

                            }
                        });
                    }
                    //标题
                    tvTitleHudong.setText(dataQuza.getQuestionTitle());
                    tvTimeHudong.setText(DateUtil.timesOne(dataQuza.getQuestionPublishDate()));
                    tvPriceHudong.setText("￥" + dataQuza.getQuestionMoney() + ".00");
                    ImageLoader.getInstance().displayImage(dataQuzaBean.questionVo.getUserHeadImg(), ivUser);//显示头像
                    name.setText(dataQuza.getUserRealName());//姓名
                    desc.setText(dataQuza.getQuestionData());//内容
                    if (TextUtils.isEmpty(dataQuza.getUserSex())) {
                        ivsex.setVisibility(view.INVISIBLE);
                    } else if ("男".equals(dataQuza.getUserSex())) {
                        ivsex.setVisibility(view.VISIBLE);
                        ivsex.setImageResource(R.drawable.sex_men);
                    } else {
                        ivsex.setVisibility(view.VISIBLE);
                        ivsex.setImageResource(R.drawable.sex_women);
                    }

                    if (!TextUtils.isEmpty(dataQuza.getUserSchool())) {
                        school.setVisibility(View.VISIBLE);
                        school.setText(dataQuza.getUserSchool());
                    } else {
                        school.setVisibility(View.INVISIBLE);
                    }
                    if (!TextUtils.isEmpty(dataQuza.getUserMajor())) {
                        major.setVisibility(View.VISIBLE);
                        major.setText(dataQuza.getUserMajor());
                    } else {
                        major.setVisibility(View.INVISIBLE);
                    }
                    if (!TextUtils.isEmpty(dataQuza.getUserGrade())) {
                        grade.setVisibility(View.VISIBLE);
                        grade.setText(dataQuza.getUserGrade());
                    } else {
                        grade.setVisibility(View.INVISIBLE);
                    }
                    tvHudongCollectNum.setText("" + dataQuza.getQuestionCollection());
                    tvPraiseNum.setText("" + dataQuza.getQuestionPraise());
                    //初始化点赞和收藏
                    if (TextUtils.isParse(dataQuzaBean.getQuestionVo().getQuestionId(), pid)) {
                        iSPraise = true;
                    } else {
                        iSPraise = false;
                    }
                    if (TextUtils.isParse(dataQuzaBean.getQuestionVo().getQuestionId(), cId)) {
                        iSCollection = true;
                    } else {
                        iSCollection = false;
                    }

                    /**
                     * 初始收藏和点赞自己是否点
                     */
                    ckHudongCollectNum.setChecked(iSCollection);
                    ckHudongPraise.setChecked(iSPraise);

                    //备用.为了添加头部数据
                    List<UserAnswerInfo.DataBean> listDetails = new ArrayList<>();
                    listDetails.clear();
                    userFansAdapter = new UserQuzaInfoAdapter(getContext(), listDetails, R.layout.useranswer_info_detail);
                    //问题已采纳展示
                    if (dataQuza.getExtend1().equals("1")) {
                        accept.setVisibility(View.INVISIBLE);//隐藏按钮
                        isAccept = true;
                        retqusetAnswer();//详情下的请求回答列表
                    } else {
                        isAccept = false;
                        accept.setVisibility(View.VISIBLE);//显示
                        //问题未采纳不展示隐藏采纳按钮
                        retqusetAnswer();//详情下的请求回答列表
                    }
                    pullLvCollect.setAdapter(userFansAdapter);
                    break;
                //提问的详情列表
                case RequestCode.USER_ANSWER_QUWSTION_COOD_INFO:
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
                    if (!android.text.TextUtils.isEmpty(totalRecord)) {
                        pserson_answer_count.setText(totalRecord);
                    }
                    list = JsonUtil.json2BeanList(datainfo, UserAnswerInfo.DataBean.class);
                    if (list == null || list.size() == 0) {
                        accept.setVisibility(View.INVISIBLE);
                    }
                    if (ismore) {
                        listreal.addAll(list);
                        userFansAdapter.notifyDataSetChanged();
                    } else {
                        listreal.clear();
                        listreal = list;
                    }
                    userFansAdapter = new UserQuzaInfoAdapter(getContext(), listreal, R.layout.useranswer_info_detail);//已经回答人的列表adapter
                    userFansAdapter.setOnDataChangeLitener(new UserQuzaInfoAdapter.OnDatasChanLitener() {
                        @Override
                        public void onDatasChange(SparseArray<UserAnswerInfo.DataBean> array) {
                            if (array != null) {
                                int size = array.size();
                                if (size == 0) {
                                    listselect.clear();
                                } else {
                                    listselect.clear();
                                    for (int i = 0; i < size; i++) {
                                        UserAnswerInfo.DataBean select = array.valueAt(i);
                                        if (select.ischeck) {
                                            listselect.add(select);
                                        }
                                    }

                                }

                            }
                        }
                    });

                    pullLvCollect.setAdapter(userFansAdapter);
                    pullLvCollect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            ToastUtils.showSnackBar(snackView, "" + position);
                            if (userFansAdapter != null) {
                                userFansAdapter.selectIndex = position - 2;
                                userFansAdapter.notifyDataSetChanged();
                                ToastUtils.showSnackBar(snackView, "" + (position - 2));
                                UserDataBean = list.get(position - 2);
                            }

                        }
                    });


                    break;

            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_quzadetils_info;
    }


    @Override
    protected void initListener() {
        //查找头部控件
        getHeadLayoutId();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userFansAdapter != null) {
                    userFansAdapter.selectIndex = -1;
                    userFansAdapter.notifyDataSetChanged();
                }
            }
        });

        ivBackApproveAddress.setOnClickListener(this);
        accept.setOnClickListener(this);
        //分享
        pullLvCollect.setMode(PullToRefreshBase.Mode.BOTH);
        pullLvCollect.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
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
                retqusetQuza();//请求提问详情

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                ismore = true;
                page = (Integer.parseInt(page) + 1) + "";
                retqusetQuza();
            }
        });
    }

    private Boolean ismore = false;

    @Override
    protected void initialized() {
        //配置listview
        dataBean = (DataUseQuiz.DataBean) getIntent().getSerializableExtra("item");
        pullLvCollect.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        listview = pullLvCollect.getRefreshableView();
        listview.addHeaderView(view);
        retqusetQuza();//请求提问详情
    }


    private TextView name;
    private TextView school;
    private TextView major;
    private TextView grade;
    private TextView desc;
    MyGridView gvDetailHudong;

    //提问提问详情；
    private void retqusetQuza() {
        map.clear();
        map.put("userId", dataBean.getUserId());
        map.put("questionId", dataBean.getQuestionId());
        httpDatas.getData("提问问题详情", Request.Method.POST, com.lvshandian.asktoask.utils.UrlBuilder.USER_QUIZ_DETAILS, map, mHandler, RequestCode.USER_AQUZA_DETAILS_CODE);
        pullLvCollect.post(new Runnable() {
            @Override
            public void run() {
                pullLvCollect.onRefreshComplete();
            }
        });
    }

    private String page = "1";

    //请求回答列表
    private void retqusetAnswer() {
        map.clear();
        map.put("userId", Global.getUserId(mContext));
        map.put("pageNum", page);
        map.put("questionId", dataQuza.getQuestionId());
        map.put("quizzerId", dataQuza.getUserId());
        httpDatas.getData("提问问题回答列表", Request.Method.POST, com.lvshandian.asktoask.utils.UrlBuilder.USER_ANSWER_QUESTION_INFO, map, mHandler, RequestCode.USER_ANSWER_QUWSTION_COOD_INFO);
        pullLvCollect.post(new Runnable() {
            @Override
            public void run() {
                pullLvCollect.onRefreshComplete();
            }
        });
    }

    //采纳请求
    private void acceptRequest() {
        StringBuffer strquest = new StringBuffer();//答案id
        StringBuffer strquester = new StringBuffer();//回答者id
        for (int i = 0; i < listselect.size(); i++) {

            if (i == listselect.size() - 1) {
                strquest.append("," + listselect.get(i).answerId + ",");
            } else {
                strquest.append("," + listselect.get(i).answerId);
            }

            if (i == listselect.size() - 1) {
                strquester.append("," + listselect.get(i).answererId + ",");
            } else {
                strquester.append("," + listselect.get(i).answererId);
            }
        }
        map.clear();
        //采纳人Id
        map.put("acceptorId", Global.getUserId(mContext));
        //问题的赏金
        String str = tvPriceHudong.getText().toString();
        map.put("questionMoney", str.replaceAll("￥", ""));
        map.put("answedIds", strquest.toString());//所有的答案ids
        //问题ID
        map.put("questionId", dataBean.getQuestionId());
        map.put("acceptedIds", strquester.toString());//被采纳人的ids
        //被采纳人id
        httpDatas.getData("采纳问题", Request.Method.POST, com.lvshandian.asktoask.utils.UrlBuilder.USER_ACCEPT, map, mHandler, RequestCode.USER_ACCEPT);
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
            case R.id.iv_back_approve_address:
                finish();
                break;
            case R.id.tv_accept:
                if (listselect.size() == 0) {
                    ToastUtils.showSnackBar(snackView, "请选择答师");
                } else {
                    acceptRequest();//采纳接口
                }
                break;
        }
    }

    /**
     * 去点赞
     */
    private void goToPraise(DataQuzaBean.QuestionVoBean transferbean) {
        map.clear();
        map.put("praiserId", Global.getUserId(mContext));
        map.put("praiseredId", transferbean.getUserId());
        map.put("questionId", transferbean.getQuestionId());
        httpDatas.getData("点赞", Request.Method.POST, com.lvshandian.asktoask.utils.UrlBuilder.PRAISE_URL, map, mHandler, RequestCode.PRAISE_CODE, false);
    }

    /**
     * 取消点赞
     */
    private void goToCanclePraise(DataQuzaBean.QuestionVoBean transferbean) {
        map.clear();
        map.put("praiserId", Global.getUserId(mContext));
        map.put("praiseredId", transferbean.getUserId());
        map.put("questionId", transferbean.getQuestionId());
        httpDatas.getData("取消点赞", Request.Method.POST, com.lvshandian.asktoask.utils.UrlBuilder.PRAISE_CANCEL_URL, map, mHandler, RequestCode.PRAISE_CANCLE_CODE, false);
    }

    /**
     * 收藏
     */
    private void goToCollect(DataQuzaBean.QuestionVoBean transferbean) {
        map.clear();
        map.put("collectorId", Global.getUserId(mContext));
        map.put("collectordId", transferbean.getUserId());
        map.put("questionId", transferbean.getQuestionId());
        httpDatas.getData("收藏", Request.Method.POST, com.lvshandian.asktoask.utils.UrlBuilder.COLLECT_URL, map, mHandler, RequestCode.COLLECT_CODE, false);
    }

    /**
     * 取消收藏
     */
    private void goToCancleCollect(DataQuzaBean.QuestionVoBean transferbean) {
        map.clear();
        map.put("collectorId", Global.getUserId(mContext));
        map.put("collectordId", transferbean.getUserId());
        map.put("questionId", transferbean.getQuestionId());
        httpDatas.getData("取消收藏", Request.Method.POST, com.lvshandian.asktoask.utils.UrlBuilder.COLLECT_CANCLE_URL, map, mHandler, RequestCode.COLLECT_CANCLE_CODE, false);
    }

    List<Warn.DataBean> listwarn;  //举报集合
    private TextView accept;//采纳按钮
    private ImageView ivsex;
    private ImageView ivUser;
    ListView lvWarn; //举报
    AlertDialog alert;  //举报对话框

    //查找控件布局
    public void getHeadLayoutId() {
        view = View.inflate(mContext, R.layout.activity_quzadetails_head, null);
        tvTitleHudong = (TextView) view.findViewById(R.id.tv_title_hudong);//标题
        tvTimeHudong = (TextView) view.findViewById(R.id.tv_time_hudong);//时间
        tvPriceHudong = (TextView) view.findViewById(R.id.tv_price_hudong);//价格
        ivUser = (ImageView) view.findViewById(R.id.iv_hudong_type_detail);
        ckHudongCollectNum = (CheckBox) view.findViewById(R.id.ck_hudong_collect_num);//收藏的chebox
        tvHudongCollectNum = (TextView) view.findViewById(R.id.tv_hudong_collect_num);//收藏
        ckHudongPraise = (CheckBox) view.findViewById(R.id.ck_hudong_praise);//点赞的checkbox
        pserson_answer_count = (TextView) view.findViewById(R.id.tv_answer_num_header);//条数
        tvPraiseNum = (TextView) view.findViewById(R.id.tv_praise_num_head);//点赞
        name = (TextView) view.findViewById(R.id.tv_hudong_type_username);//姓名
        school = (TextView) view.findViewById(R.id.tv_hudong_detail_label_school);//学校
        major = (TextView) view.findViewById(R.id.tv_hudong_detail_label_major);//major
        grade = (TextView) view.findViewById(R.id.tv_hudong_detail_label_grade);//年级
        desc = (TextView) view.findViewById(R.id.tv_hudong_detail_desc);//问题描述
        ivShareHudong = (LinearLayout) view.findViewById(R.id.ll_question_detail_share);//分享
        accept = (TextView) view.findViewById(R.id.tv_accept);//采纳
        ivsex = (ImageView) view.findViewById(R.id.iv_hudong_detail_sex);//头像
        view.findViewById(R.id.tv_hudong_detail_warn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getLayoutInflater().inflate(R.layout.hudong_detail_warn, null);
                lvWarn = (ListView) view.findViewById(R.id.lv_warn);
                view.findViewById(R.id._hudong_detail_confirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RequestSuccess();//去举报
                        alert.dismiss();
                    }
                });
                alert = new AlertDialog.Builder(QuzaDetailsActivity.this).setView(view).create();
//                    listwarn = JsonUtil.json2BeanList(json, Warn.DataBean.class);
                listwarn = new ArrayList<>();
                listwarn.add(new Warn.DataBean(false, "举报", "发布色情/政治/违法内容", "举报", "举报", "举报", "举报", "举报"));
                listwarn.add(new Warn.DataBean(false, "举报", "被骗钱了", "举报", "举报", "举报", "举报", "举报"));
                listwarn.add(new Warn.DataBean(false, "举报", "被骚扰了(垃圾信息、谩骂)", "举报", "举报", "举报", "举报", "举报"));
                lvWarn.setAdapter(new WarnAdapter(getContext(), listwarn, R.layout.item_warn));
                alert.setCanceledOnTouchOutside(true);
                alert.show();
            }
        });


        ivShareHudong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataUserAnswer.DataBean.UserAndQuestionsBean item = new DataUserAnswer.DataBean.UserAndQuestionsBean();
                item.setQuestionId(dataQuza.getQuestionId());
                item.setQuestionData(dataQuza.getQuestionData());
                item.setUserId(dataQuza.getUserId());
                item.setQuestionTitle(dataQuza.getQuestionTitle());
                ShareUtils.goToShare(mContext, item);
            }
        });


        //gridview适配数据
        gvDetailHudong = (MyGridView) view.findViewById(R.id.gv_detail_hudong);
        ckHudongPraise.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isfirstparse && iSPraise) {
                    isfirstparse = false;
                    return;
                }
                if (isChecked) {
                    tvPraiseNum.setText((Integer.parseInt(tvPraiseNum.getText().toString()) + 1) + "");
                    goToPraise(dataQuza);

                } else {
                    tvPraiseNum.setText((Integer.parseInt(tvPraiseNum.getText().toString()) - 1) + "");
                    goToCanclePraise(dataQuza);

                }
            }
        });
        ckHudongCollectNum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isfirstcollect && iSCollection) {
                    isfirstcollect = false;
                    return;
                }

                if (isChecked) {
                    tvHudongCollectNum.setText((Integer.parseInt(tvHudongCollectNum.getText().toString()) + 1) + "");
                    goToCollect(dataQuza);

                } else {
                    tvHudongCollectNum.setText((Integer.parseInt(tvHudongCollectNum.getText().toString()) - 1) + "");
                    goToCancleCollect(dataQuza);

                }
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userFansAdapter != null) {
                    userFansAdapter.selectIndex = -1;
                    userFansAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * 发送举报
     *
     * @return
     */

    private void RequestSuccess() {
        map.clear();
        map.put("reporterId", Global.getUserId(getContext()));
        map.put("reportedId", dataQuza.getUserId());
        map.put("questionId", dataQuza.questionId);
        {
            String str = "";
            for (int i = 0; i < listwarn.size(); i++) {
                if (listwarn.get(i).isselect) {
                    str = str + listwarn.get(i).reportData;
                }
            }
            map.put("reportData", str);
            httpDatas.getData("互动详情举报", Request.Method.POST, UrlBuilder.HUDONGWARN_URL, map, mHandler, RequestCode.HUDONG_WARN_SUCCESS_CODE);
        }
    }


}

