package com.lvshandian.asktoask.module.interaction.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.volley.Request;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lvshandian.asktoask.BaseActivity;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.http.HttpDatas;
import com.lvshandian.asktoask.common.http.RequestCode;
import com.lvshandian.asktoask.entry.HuDongDetail;
import com.lvshandian.asktoask.entry.HuDongItem;
import com.lvshandian.asktoask.entry.User;
import com.lvshandian.asktoask.entry.Warn;
import com.lvshandian.asktoask.module.interaction.InteractionFragment;
import com.lvshandian.asktoask.module.interaction.adapter.HuDongDetailAdapter;
import com.lvshandian.asktoask.module.interaction.adapter.WarnAdapter;
import com.lvshandian.asktoask.module.login.LoginActivity;
import com.lvshandian.asktoask.module.user.adapter.MyGridViewAdapter;
import com.lvshandian.asktoask.utils.DateUtil;
import com.lvshandian.asktoask.utils.DialogView;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.JsonUtil;
import com.lvshandian.asktoask.utils.L;
import com.lvshandian.asktoask.utils.PicUtils;
import com.lvshandian.asktoask.utils.TextUtils;
import com.lvshandian.asktoask.view.MyGridView;
import com.lvshandian.asktoask.utils.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import butterknife.Bind;
/**
 * 互动首页列表跳转详情
 */
public class HuDongDetailActivity extends BaseActivity {
    public static String TRANSFER = "transfer";
    public static String COLLECTNUM = "collectnum";
    public static String PARSENUM = "parsenum";
    @Bind(R.id.iv_hudong_detail_back)
    ImageView ivHudongDetailBack;
    ImageView ivHudongTypeDetail;
    TextView tvHudongTypeUsername;
    ImageView ivHudongDetailSex;
    TextView tvHudongDetailLabelSchool;
    TextView tvHudongDetailLabelMajor;
    TextView tvHudongDetailLabelGrade;
    TextView tvHudongDetailPrice;
    TextView tvHudongDetailWarn;
    TextView tvTitleHudongDetail;
    TextView tvTimeHudongDetail;
    TextView tvQuestionDesc;
    @Bind(R.id.tv_hudong_detail_type)
    TextView tvHudongDetailType;
    LinearLayout llHudongDetailOncick;
    MyGridView gvDetailHudong;
    TextView tvHudongDetailCollectNum;
    TextView tvPraiseNum;
    LinearLayout ivShareHudongDetail;
    RelativeLayout rlHudongUnseletMaster;
    @Bind(R.id.pull_lv_collect)
    PullToRefreshListView lvHudongDetail;
    @Bind(R.id.et_answer_content)
    EditText etAnswerContent;
    @Bind(R.id.tv_send_answer)
    TextView tvSendAnswer;
    @Bind(R.id.ll_hudong_detail)
    LinearLayout llHudongDetail;
    TextView tvHudongShareDetail;
    CheckBox ckHudongDetailCollectNum;
    CheckBox ckHudongDetailPraise;
    private List<HuDongDetail.DataBean.DataBeanNei> list = new ArrayList<>();
    private List<HuDongDetail.DataBean.DataBeanNei> listreal = new ArrayList<>();
    private HuDongDetailAdapter adapter;
    List<Warn.DataBean> listwarn;  //举报集合
    private HuDongItem.DataBean.PageBean.DataBean2 beantransfer;
    private ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
    AlertDialog alert;  //举报对话框
    private String realpageNum = "1";//分页加载
    private boolean ismoreMyAnswer=false;//是否加载更多
    private TextView answernum;
    ListView lvWarn; //举报
    private boolean isfrush=false;
    @Override
    protected void initListener() {

    }
    boolean iSCollection = false;
    boolean iSPraise = false;
    private void addListener(){
        ckHudongDetailCollectNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Global.isLogin(getContext())){
                    ckHudongDetailCollectNum.setChecked(beantransfer.iscollect);
                    ToastUtils.showSnackBar(snackView,"请先去登陆");

                    return;
                }

                if (iSCollection) {
                    //取消收藏
                    iSCollection = false;
                    RequestParams params = new RequestParams();
                    params.addBodyParameter("collectorId", Global.getUserId(getContext()));
                    params.addBodyParameter("collectordId", beantransfer.getUserId());
                    params.addBodyParameter("questionId", beantransfer.questionId);
                    HttpUtils http = new HttpUtils();
                    http.send(HttpRequest.HttpMethod.POST,
                            UrlBuilder.serverUrl + UrlBuilder.COLLECT_CANCLE_URL,
                            params,
                            new RequestCallBack<String>() {

                                @Override
                                public void onStart() {

                                }

                                @Override
                                public void onLoading(long total, long current, boolean isUploading) {
                                }

                                @Override
                                public void onSuccess(ResponseInfo<String> responseInfo) {
                                    String str = responseInfo.result;
                                    try {
                                        JSONObject object = new JSONObject(str);
                                        String msg = object.getString("msg");


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onFailure(HttpException error, String msg) {


                                }
                            });
                    beantransfer.setQuestionCollection(beantransfer.getQuestionCollection() - 1);

                    tvHudongDetailCollectNum.setText(beantransfer.getQuestionCollection() + "");
                } else {
                    iSCollection = true;
                    RequestParams params = new RequestParams();
                    params.addBodyParameter("collectorId", Global.getUserId(getContext()));
                    params.addBodyParameter("collectordId", beantransfer.getUserId());
                    params.addBodyParameter("questionId", beantransfer.questionId);
                    HttpUtils http = new HttpUtils();
                    http.send(HttpRequest.HttpMethod.POST,
                            com.lvshandian.asktoask.utils.UrlBuilder.serverUrl + com.lvshandian.asktoask.utils.UrlBuilder.COLLECT_URL,
                            params,
                            new RequestCallBack<String>() {

                                @Override
                                public void onStart() {

                                }

                                @Override
                                public void onLoading(long total, long current, boolean isUploading) {
                                }

                                @Override
                                public void onSuccess(ResponseInfo<String> responseInfo) {
                                    String str = responseInfo.result;
                                    try {
                                        JSONObject object = new JSONObject(str);
                                        String msg = object.getString("msg");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFailure(HttpException error, String msg) {

                                }
                            });

                    beantransfer.setQuestionCollection(beantransfer.getQuestionCollection() + 1);

                    tvHudongDetailCollectNum.setText(beantransfer.getQuestionCollection() + "");
                }


            }
        });

        ckHudongDetailPraise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Global.isLogin(getContext())){
                    ckHudongDetailPraise.setChecked(beantransfer.ispraise);
                    ToastUtils.showSnackBar(snackView, "请先去登陆");
                    return;
                }

                //点赞

                if (iSPraise) {
                    //取消点赞
                    iSPraise = false;
                    RequestParams params = new RequestParams();
                    params.addBodyParameter("praiserId", Global.getUserId(getContext()));
                    params.addBodyParameter("praiseredId", beantransfer.getUserId());
                    params.addBodyParameter("questionId", beantransfer.questionId);
                    HttpUtils http = new HttpUtils();
                    http.send(HttpRequest.HttpMethod.POST,
                            UrlBuilder.serverUrl + UrlBuilder.PRAISE_CANCEL_URL,
                            params,
                            new RequestCallBack<String>() {

                                @Override
                                public void onStart() {

                                }

                                @Override
                                public void onLoading(long total, long current, boolean isUploading) {
                                }

                                @Override
                                public void onSuccess(ResponseInfo<String> responseInfo) {
                                    String str = responseInfo.result;
                                    try {
                                        JSONObject object = new JSONObject(str);
                                        object.getString("msg");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onFailure(HttpException error, String msg) {

                                }
                            });
                    beantransfer.setQuestionPraise(beantransfer.getQuestionPraise() - 1);
                    tvPraiseNum.setText(beantransfer.getQuestionPraise() + "");
                } else {
                    iSPraise = true;
                    //点赞
                    if (com.lvshandian.asktoask.utils.TextUtils.isEmpty("" + beantransfer.getQuestionPraise())) {
                        beantransfer.setQuestionPraise(1);
                    }
                    RequestParams params = new RequestParams();
                    params.addBodyParameter("praiserId", Global.getUserId(getContext()));
                    params.addBodyParameter("praiseredId", beantransfer.getUserId());
                    params.addBodyParameter("questionId", beantransfer.questionId);
                    HttpUtils http = new HttpUtils();
                    http.send(HttpRequest.HttpMethod.POST,
                            UrlBuilder.serverUrl + UrlBuilder.PRAISE_URL,
                            params,
                            new RequestCallBack<String>() {

                                @Override
                                public void onStart() {

                                }

                                @Override
                                public void onLoading(long total, long current, boolean isUploading) {
                                }

                                @Override
                                public void onSuccess(ResponseInfo<String> responseInfo) {
                                    String str = responseInfo.result;
                                    try {
                                        JSONObject object = new JSONObject(str);
                                        String str1 = object.getString("msg");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onFailure(HttpException error, String msg) {


                                }
                            });
                    beantransfer.setQuestionPraise(beantransfer.getQuestionPraise() + 1);
                    tvPraiseNum.setText(beantransfer.getQuestionPraise() + "");
                }

            }
        });


        //显示多张图片
        gvDetailHudong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), HuPicLunBoActivity.class);
                intent.putExtra(HuPicLunBoActivity.PIC, beantransfer.questionImgs);
                startActivity(intent);
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
                case RequestCode.HUDONG_DETAIL_MASTER_ANSWER:  //互动详情里面的答师回答
                    HuDongDetail.DataBean bean = JsonUtil.json2Bean(json, HuDongDetail.DataBean.class);
                    dealDataBeab(bean);
                    break;
                case RequestCode.HUDONG_WARN_CODE:   //互动详情里面的举报内容
                    View view = getLayoutInflater().inflate(R.layout.hudong_detail_warn, null);
                    lvWarn = (ListView) view.findViewById(R.id.lv_warn);
                    view.findViewById(R.id._hudong_detail_confirm).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alert.dismiss();
                            RequestSuccess();//去举报
                        }
                    });
                    alert = new AlertDialog.Builder(HuDongDetailActivity.this).setView(view).create();
//                    listwarn = JsonUtil.json2BeanList(json, Warn.DataBean.class);
                    listwarn=new ArrayList<>();
                    listwarn.add(new Warn.DataBean(false,"举报","发布色情/政治/违法内容","举报","举报","举报","举报","举报"));
                    listwarn.add(new Warn.DataBean(false,"举报","被骗钱了","举报","举报","举报","举报","举报"));
                    listwarn.add(new Warn.DataBean(false, "举报", "被骚扰了(垃圾信息、谩骂)", "举报", "举报", "举报", "举报", "举报"));
                    lvWarn.setAdapter(new WarnAdapter(getContext(), listwarn, R.layout.item_warn));
                    alert.setCanceledOnTouchOutside(true);
                    alert.show();
                    break;
                case RequestCode.HUDONG_WARN_SUCCESS_CODE:   //互动详情 举报
                    alert.dismiss();
                    ToastUtils.showSnackBar(snackView, "举报成功");
                    break;

                case RequestCode.ANSWER_QUESTION_CODE:
                    answernum.setText((Integer.parseInt(answernum.getText().toString())+1)+"");//本地数值加1
                    etAnswerContent.setText("");
                    realpageNum="1";
                    listreal.clear();
                    isfrush=true;
                    httpRequest();//你回答问题成功了 展示你的回答信息
                    break;
                default:
                    break;
            }


        }

    };

    private void dealDataBeab(HuDongDetail.DataBean bean) {
        if(ismoreMyAnswer){
            list = bean.data;
            listreal.addAll(list);
            adapter.notifyDataSetChanged();
        }else{
            listreal=bean.data;
            adapter = new HuDongDetailAdapter(getContext(), listreal, R.layout.item_hudong_detail);
            adapter.setmDatas(listreal);
            lvHudongDetail.setAdapter(adapter);
            adapter.notifyDataSetChanged();}
        }

    @Override
    protected void initialized() {
        map = new ConcurrentHashMap<>();
        beantransfer = (HuDongItem.DataBean.PageBean.DataBean2) getIntent().getSerializableExtra(TRANSFER);
        iSCollection=beantransfer.iscollect;
        iSPraise=beantransfer.ispraise;
        View viewHead = View.inflate(mContext, R.layout.activity_hudongdetail_header_layout, null);
        ckHudongDetailCollectNum=(CheckBox)viewHead.findViewById(R.id.ck_hudong_detail_collect_num);
        ckHudongDetailPraise=(CheckBox)viewHead.findViewById(R.id.ck_hudong_detail_praise);
        tvHudongDetailWarn=(TextView)viewHead.findViewById(R.id.tv_hudong_detail_warn);
        llHudongDetailOncick=(LinearLayout)viewHead.findViewById(R.id.ll_hudong_detail_oncick);
        tvHudongShareDetail=(TextView)viewHead.findViewById(R.id.tv_hudong_share_detail);
        ivShareHudongDetail=(LinearLayout)viewHead.findViewById(R.id.iv_share_hudong_detail);
        tvQuestionDesc=(TextView)viewHead.findViewById(R.id.tv_hudong_detail_desc);
        rlHudongUnseletMaster=(RelativeLayout)viewHead.findViewById(R.id.rl_hudong_unselet_master);
        ivHudongTypeDetail=(ImageView)viewHead.findViewById(R.id.iv_hudong_type_detail);//头部图标
        tvHudongTypeUsername=(TextView)viewHead.findViewById(R.id.tv_hudong_type_username);
        ivHudongDetailSex=(ImageView)viewHead.findViewById(R.id.iv_hudong_detail_sex);
        tvHudongDetailPrice=(TextView)viewHead.findViewById(R.id.tv_hudong_detail_price);
        answernum=(TextView)viewHead.findViewById(R.id.tv_answer_num_header);
        tvHudongDetailLabelSchool=(TextView)viewHead.findViewById(R.id.tv_hudong_detail_label_school);//学校类型
        tvHudongDetailLabelMajor=(TextView)viewHead.findViewById(R.id.tv_hudong_detail_label_major);//专业
        tvHudongDetailLabelGrade=(TextView)viewHead.findViewById(R.id.tv_hudong_detail_label_grade);//大学
        tvHudongDetailCollectNum=(TextView)viewHead.findViewById(R.id.tv_hudong_detail_collect_num);
        tvPraiseNum=(TextView)viewHead.findViewById(R.id.tv_praise_num_head);
        tvTitleHudongDetail=(TextView)viewHead.findViewById(R.id.tv_title_hudong_detail);
        tvTimeHudongDetail=(TextView)viewHead.findViewById(R.id.tv_time_hudong_detail);
        tvPraiseNum.setText(beantransfer.getQuestionPraise()+"");
        tvHudongDetailCollectNum.setText(beantransfer.getQuestionCollection()+"");
        ckHudongDetailCollectNum.setChecked(beantransfer.iscollect);
        ckHudongDetailPraise.setChecked(beantransfer.ispraise);
        ImageLoader.getInstance().displayImage(beantransfer.getUserHeadImg(), ivHudongTypeDetail);
        //是否匿名 还是真实姓名

            tvHudongTypeUsername.setText(beantransfer.getUserRealName());

        if ("男".equals(beantransfer.getUserSex())) {
            ivHudongDetailSex.setVisibility(View.VISIBLE);
            ivHudongDetailSex.setImageResource(R.drawable.sex_men);
        } else if("女".equals(beantransfer.getUserSex())){
            ivHudongDetailSex.setVisibility(View.VISIBLE);
            ivHudongDetailSex.setImageResource(R.drawable.sex_women);
        }else{
            ivHudongDetailSex.setVisibility(View.INVISIBLE);
        }
        tvHudongDetailPrice.setText(beantransfer.getQuestionMoney() + "0");//悬赏价格
        if(TextUtils.isEmpty(beantransfer.userSchool)){
            tvHudongDetailLabelSchool.setVisibility(View.INVISIBLE);

        }else{
            tvHudongDetailLabelSchool.setVisibility(View.VISIBLE);
            tvHudongDetailLabelSchool.setText(beantransfer.userSchool);//任务标签
        }
        if(TextUtils.isEmpty(beantransfer.userMajor)){
            tvHudongDetailLabelMajor.setVisibility(View.INVISIBLE);

        }else{
            tvHudongDetailLabelMajor.setVisibility(View.VISIBLE);
            tvHudongDetailLabelMajor.setText(beantransfer.userMajor);//专业
        }
        if(TextUtils.isEmpty(beantransfer.userGrade)){
            tvHudongDetailLabelGrade.setVisibility(View.INVISIBLE);

        }else{
            tvHudongDetailLabelGrade.setVisibility(View.VISIBLE);
            tvHudongDetailLabelGrade.setText(beantransfer.userGrade); //大学
        }
        tvHudongDetailType.setText(beantransfer.getQuestionType());//问题类型
        tvTitleHudongDetail.setText(beantransfer.getQuestionTitle());
        tvTimeHudongDetail.setText(DateUtil.timesOne(beantransfer.getQuestionPublishDate()));
        tvQuestionDesc.setText(beantransfer.getQuestionData());//问题描述
        answernum.setText(beantransfer.getAnswerNum() + "");  //回答条数从本界面过来的
        //gridview适配数据
        gvDetailHudong=(MyGridView)viewHead.findViewById(R.id.gv_detail_hudong);
        gvDetailHudong.setAdapter((new MyGridViewAdapter(PicUtils.getPic(beantransfer.questionImgs), mContext)));
        lvHudongDetail.setMode(PullToRefreshBase.Mode.BOTH);//支持上拉加载更多和下拉刷新
        ListView listview = lvHudongDetail.getRefreshableView();
        listview.addHeaderView(viewHead);

        httpRequest();//请求回答的人

        lvHudongDetail.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {//下拉刷新
                ismoreMyAnswer = false;
                realpageNum = "1";
                httpRequest();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {//上拉加载更多
                ILoadingLayout endLabels = lvHudongDetail.getLoadingLayoutProxy(false, true);
                endLabels.setPullLabel("加载更多");// 刚开始上拉时显示的提示
                int p = Integer.parseInt(realpageNum) + 1;
                realpageNum = "" + p;
                ismoreMyAnswer = true;
                httpRequest();//请求问题答案
            }
        });
        tvHudongDetailWarn.setOnClickListener(this);
        ivHudongDetailBack.setOnClickListener(this);
        tvSendAnswer.setOnClickListener(this);
        etAnswerContent.setOnFocusChangeListener(new View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // 此处为得到焦点时的处理内容
                    if (!Global.isLogin(getContext())) {
                        etAnswerContent.setInputType(InputType.TYPE_NULL);//关闭软键盘
                        showDialog(llHudongDetail);//弹出对话框让用户去登录
                    } else {
                        if (!"1".equals(Global.getParam(getContext(),"isappprove","3"))) {
                            etAnswerContent.setInputType(InputType.TYPE_NULL);//关闭软键盘
                            ToastUtils.showSnackBar(snackView, "请先去认证答师");
                        }
                    }
                }
            }
        });
        //互动详情分享
        ivShareHudongDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogView(HuDongDetailActivity.this, llHudongDetail,beantransfer);//分享
            }
        });

        //添加收藏和点赞监听
        addListener();}


    @Override
    protected void onResume() {
        super.onResume();

    }

    /**
     * 弹出框让用户登录
     */
    public void showDialog(View view) {
        new DialogView(HuDongDetailActivity.this, view, 0, "你还未登录，请登录", "确定", "取消",
                new DialogView.MyCallback() {
                    @Override
                    public void refreshActivity() {
                        gotoActivity(LoginActivity.class, true);

                    }
                });
    }

    /**
     * 回答问题的人  下方列表
     */
    private void httpRequest() {
        map.clear();
        map.put("pageNum", realpageNum);
        map.put("questionId", beantransfer.questionId);
        map.put("quizzerId", beantransfer.getUserId());
        if(Global.getUserId(getContext()).equals("")){
            map.put("userId","qw");
        }else{
            map.put("userId",Global.getUserId(getContext()));
        }

        if(isfrush){
            httpDatas.getData("互动详情界面回答问题的人", Request.Method.POST, UrlBuilder.HUDONG_DETAIL_MASTER_ANSWER, map, mHandler, RequestCode.HUDONG_DETAIL_MASTER_ANSWER,false);
        }else{
            httpDatas.getData("互动详情界面回答问题的人", Request.Method.POST, UrlBuilder.HUDONG_DETAIL_MASTER_ANSWER, map, mHandler, RequestCode.HUDONG_DETAIL_MASTER_ANSWER);
        }

        lvHudongDetail.post(new Runnable() {
            @Override
            public void run() {
                lvHudongDetail.onRefreshComplete();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_hudong_detail_back:
                if(InteractionFragment.isfrush){//如果是从首页过来的才去刷新首页数据
                    EventBus.getDefault().post("frush");
                }

                finish();
                break;
            case R.id.tv_hudong_detail_warn:
                if(Global.isLogin(getContext())){
                    requestWarn();//举报
            }else{
                    ToastUtils.showSnackBar(snackView,"请先去登陆");
                }

                break;
            case R.id.tv_send_answer:
                if(Global.isLogin(getContext())){
                    httpSolveQuestion();//发送问题答案
                }else{
                    ToastUtils.showSnackBar(snackView, "请先去登陆");
                }

                break;
        }
    }

    /**
     * 发送问题答案
     */

    private void httpSolveQuestion() {
        map.clear();
        map.put("quizzerId", beantransfer.getUserId());
        map.put("answererId", Global.getUserId(getContext()));
        map.put("answerData", etAnswerContent.getText().toString());
        map.put("questionId", beantransfer.questionId);
        map.put("questionTitle", beantransfer.getQuestionTitle());
        httpDatas.getData("互动详情发送问题答案", Request.Method.POST, UrlBuilder.HUDONG_DETAIL_ANSWER_QUESTION, map, mHandler, RequestCode.ANSWER_QUESTION_CODE);
    }

    /**
     * 请求举报内容
     */
    private void requestWarn() {
        map.clear();
        httpDatas.getData("互动详情里面的举报内容", Request.Method.POST, UrlBuilder.HUDONGWARNCONTENT_URL, map, mHandler, RequestCode.HUDONG_WARN_CODE);
    }

    /**
     * 发送举报
     *
     * @return
     */

    private void RequestSuccess() {
        map.clear();
        map.put("reporterId", Global.getUserId(getContext()));
        map.put("reportedId", beantransfer.getUserId());
        map.put("questionId", beantransfer.questionId);
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hudong_detail_layout;
    }


}
