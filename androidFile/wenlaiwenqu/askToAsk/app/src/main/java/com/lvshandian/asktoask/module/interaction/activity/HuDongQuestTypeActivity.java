package com.lvshandian.asktoask.module.interaction.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
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
import com.lvshandian.asktoask.entry.HuDongItem;
import com.lvshandian.asktoask.entry.HuDonglPicType;
import com.lvshandian.asktoask.module.interaction.adapter.HuDongPicTypeAdapter;
import com.lvshandian.asktoask.module.interaction.piceffect.SwipeFlingAdapterView;
import com.lvshandian.asktoask.utils.DialogView;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.JsonUtil;
import com.lvshandian.asktoask.utils.TextUtils;
import com.lvshandian.asktoask.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;

/**
 * 互动问题类型
 */
public class HuDongQuestTypeActivity extends BaseActivity {
    public static final String TYPE = "TYPEPIC";
    public static LinearLayout llParentView;
    @Bind(R.id.tv_hudong_type)
    TextView tvHudongType;
    @Bind(R.id.iv_hudong_quest_type_back)
    ImageView ivHudongQuestTypeback;
    @Bind(R.id.frame)
    SwipeFlingAdapterView frame;
    List<HuDonglPicType.DataBean.PageBeanBean.DataBeanInner> list;
    @Bind(R.id.ck_hudong_collect_num_quest_type)
    CheckBox ckHudongCollectNumQuestType;
    @Bind(R.id.ck_hudong_praise_nume_quest_type)
    CheckBox ckHudongPraiseNumeQuestType;
    @Bind(R.id.iv_share_hudong_quest_type)
    LinearLayout ivShareHudongQuestType;
    @Bind(R.id.tv_hudong_collect_num_detail)
    TextView tvHudongCollectNumDetail;
    @Bind(R.id.tv_praise_num_detail)
    TextView tvPraiseNumDetail;
    @Bind(R.id.ll_hasdata)
    LinearLayout llHasdata;
    @Bind(R.id.tv_show_nodata)
    TextView tvShowNodata;
    private String title = "";
    private String content = "";
    private List<HuDonglPicType.DataBean.PageBeanBean.DataBeanInner> listadd = new ArrayList<>();
    HuDongPicTypeAdapter adapter;
    private String realnum = "1";
    private boolean m2 = false;
    private boolean m3 = false;
    private boolean m4 = false;
    private boolean m1 = false;
    private String userid;//点赞和收藏用到的参数
    private String questionid;
    private boolean norequest = false;
    HuDonglPicType.DataBean pictype;
    private boolean firstcollect;
    private boolean firstpraise;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_hudong_type;
    }

    HuDongItem.DataBean.PageBean.DataBean2 item = new HuDongItem.DataBean.PageBean.DataBean2();

    /**
     * 添加监听
     */
    @Override
    protected void initListener() {
        llParentView = (LinearLayout) findViewById(R.id.llParentView);
        //互动详情分享
        ivShareHudongQuestType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item.questionId = questionid;
                item.userId = userid;
                item.questionTitle = title;
                item.questionData = content;
                new DialogView(HuDongQuestTypeActivity.this, HuDongQuestTypeActivity.llParentView, item);//分享
            }
        });
        ivHudongQuestTypeback.setOnClickListener(this);
        frame.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                //去互动详情界面
                goToHuDongDetail(listadd.get(itemPosition), ckHudongCollectNumQuestType.isChecked(), ckHudongPraiseNumeQuestType.isChecked());

            }
        });
        /**
         * 收藏
         */
        ckHudongCollectNumQuestType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (!Global.isLogin(getContext())) {
                    ckHudongCollectNumQuestType.setChecked(firstcollect);
                    ToastUtils.showSnackBar(snackView, "请先去登陆");
                    return;
                }

                if (isChecked) {
                    m2 = true;//收藏没有选中
                    if (m1) {//收藏选中
                        tvHudongCollectNumDetail.setText((Integer.parseInt(tvHudongCollectNumDetail.getText().toString()) + 1) + "");
                        RequestParams params = new RequestParams();
                        params.addBodyParameter("collectorId", Global.getUserId(getContext()));
                        params.addBodyParameter("collectordId", userid);
                        params.addBodyParameter("questionId", questionid);
                        HttpUtils http = new HttpUtils();
                        http.send(HttpRequest.HttpMethod.POST,
                                UrlBuilder.serverUrl + UrlBuilder.COLLECT_URL,
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

                    }
                } else {
                    m1 = true;
                    if (m2) {
                        tvHudongCollectNumDetail.setText((Integer.parseInt(tvHudongCollectNumDetail.getText().toString()) - 1) + "");
                        RequestParams params = new RequestParams();
                        params.addBodyParameter("collectorId", Global.getUserId(getContext()));
                        params.addBodyParameter("collectordId", userid);
                        params.addBodyParameter("questionId", questionid);
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
                                            object.getString("msg");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }

                                    @Override
                                    public void onFailure(HttpException error, String msg) {

                                    }
                                });
                    }
                }
            }
        });
        /**
         * 点赞
         */

        ckHudongPraiseNumeQuestType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (!Global.isLogin(getContext())) {
                    ckHudongPraiseNumeQuestType.setChecked(firstpraise);
                    ToastUtils.showSnackBar(snackView, "请先去登陆");

                    return;
                }
                if (isChecked) {
                    m4 = true;
                    if (m3) {//点赞选中了
                        tvPraiseNumDetail.setText((Integer.parseInt(tvPraiseNumDetail.getText().toString()) + 1) + "");
                        RequestParams params = new RequestParams();
                        params.addBodyParameter("praiserId", Global.getUserId(getContext()));
                        params.addBodyParameter("praiseredId", userid);
                        params.addBodyParameter("questionId", questionid);
                        HttpUtils http = new HttpUtils();
                        http.send(HttpRequest.HttpMethod.POST,
                                com.lvshandian.asktoask.utils.UrlBuilder.serverUrl + com.lvshandian.asktoask.utils.UrlBuilder.PRAISE_URL,
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


                    }
                } else {
                    m3 = true;
                    if (m4) {//点赞没有选中
                        tvPraiseNumDetail.setText((Integer.parseInt(tvPraiseNumDetail.getText().toString()) - 1) + "");
                        RequestParams params = new RequestParams();
                        params.addBodyParameter("praiserId", Global.getUserId(getContext()));
                        params.addBodyParameter("praiseredId", userid);
                        params.addBodyParameter("questionId", questionid);
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
                    }
                }
            }
        });


    }

    /**
     * 跳转到互动详情界面
     *
     * @param item
     */
    private void goToHuDongDetail(HuDonglPicType.DataBean.PageBeanBean.DataBeanInner item, boolean iscollect, boolean isparse) {

        HuDongItem.DataBean.PageBean.DataBean2 transferbean = new HuDongItem.DataBean.PageBean.DataBean2();
        transferbean.questionTitle = item.questionTitle;
        transferbean.questionData = item.questionData;
        transferbean.questionType = item.questionType;
        transferbean.questionMoney = item.questionMoney;
        transferbean.isanonymity = item.isanonymity;
        transferbean.userId = item.userId;
        transferbean.questionPublishDate = item.questionPublishDate;
        transferbean.questionCollection = item.questionCollection;
        transferbean.questionPraise = item.questionPraise;
        transferbean.answerNum = item.answerNum;
        transferbean.userHeadImg = item.userHeadImg;
        transferbean.userRealName = item.userRealName;
        transferbean.userSex = item.userSex;
        transferbean.questionImgs = item.questionImgs;
        transferbean.userSchool = item.userSchool;
        transferbean.userGrade = item.userGrade;
        transferbean.userMajor = item.userMajor;
        transferbean.questionId = item.questionId;
        transferbean.isapprove = item.isapprove;
        transferbean.extend1 = item.extend1;
        transferbean.iscollect = ckHudongCollectNumQuestType.isChecked();
        transferbean.ispraise = ckHudongPraiseNumeQuestType.isChecked();
        transferbean.questionPraise = Integer.parseInt(tvPraiseNumDetail.getText().toString());//点赞赋值
        transferbean.questionCollection = Integer.parseInt(tvHudongCollectNumDetail.getText().toString());//收藏赋值
        Intent intent = new Intent(getContext(), HuDongDetailActivity.class);
        intent.putExtra(HuDongDetailActivity.TRANSFER, transferbean);
        intent.putExtra(HuDongDetailActivity.PARSENUM, tvPraiseNumDetail.getText().toString());//收藏点赞的数量
        intent.putExtra(HuDongDetailActivity.COLLECTNUM, tvHudongCollectNumDetail.getText().toString());
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_hudong_quest_type_back:
                finish();
                break;
            default:
                break;
        }

    }


    @Override
    protected void initialized() {
        //上方标题互动问题类型
        tvHudongType.setText(getIntent().getStringExtra(TYPE));
        httprequest();//请求图片信息
    }

    /**
     * 图片类型数据
     */
    private void httprequest() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.clear();
        map.put("userId", Global.getUserId(getContext()));
        map.put("questionType", getIntent().getStringExtra(TYPE));//搜索的图片列表类型
        map.put("pageNum", realnum);
        httpDatas.getData("互动问题类型图片", Request.Method.POST, UrlBuilder.HUDONG_QUESTION_TYPE_URL, map, mHandler, RequestCode.HU_DONG_QUESTION_TYPE);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);
            switch (msg.what) {
                case RequestCode.HU_DONG_QUESTION_TYPE:  //互动问题类型图片
                    pictype = JsonUtil.json2Bean(json, HuDonglPicType.DataBean.class);
                    list = pictype.pageBean.data;//后台返回的数据
                    if (!realnum.equals("1") && list.size() == 0) {
                        realnum = "1";
                        httprequest();
                    }
                    //第一次请求比并且没请求到数据的时候 展示暂无数据
                    if (list.size() == 0 && realnum.equals("1")) {
                        llHasdata.setVisibility(View.GONE);
                        tvShowNodata.setVisibility(View.VISIBLE);
                    } else {
                        llHasdata.setVisibility(View.VISIBLE);
                        tvShowNodata.setVisibility(View.GONE);
                        listadd.addAll(list);//适配的数据集合  总的集合
                        title = listadd.get(0).questionTitle;//标题
                        content = listadd.get(0).questionData;//内容
                        tvHudongCollectNumDetail.setText(listadd.get(0).questionCollection + "");//收藏点赞赋值，第一条数据赋值
                        tvPraiseNumDetail.setText(listadd.get(0).questionPraise + "");
                        userid = listadd.get(0).userId;//收藏点赞用到的参数
                        questionid = listadd.get(0).questionId;//收藏点赞用到的参数
                        //如果选中 防止加1 防止设置加1
                        if (TextUtils.isCollect(listadd.get(0).questionId, pictype.cId)) {
                            firstcollect = true;
                            m1 = false;  //防止设置中出现收藏数据加1
                            //网络请求第一次赋值
                            ckHudongCollectNumQuestType.setChecked(true);//如果是选中状态
                        } else {
                            firstcollect = false;
                            m1 = true;
                            ckHudongCollectNumQuestType.setChecked(false);//如果是没有选中
                        }

                        if ((TextUtils.isParse(listadd.get(0).questionId, pictype.pId))) {
                            firstpraise = true;
                            m3 = false;
                            ckHudongPraiseNumeQuestType.setChecked(TextUtils.isParse(listadd.get(0).questionId, pictype.pId));
                        } else {
                            firstpraise = false;
                            m3 = true;
                            ckHudongPraiseNumeQuestType.setChecked(TextUtils.isParse(listadd.get(0).questionId, pictype.pId));
                        }

                        adapter = new HuDongPicTypeAdapter(getContext(), listadd, R.layout.item_hudong_type_pic);//互动图片适配器
                        frame.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        frame.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
                            @Override
                            public void removeFirstObjectInAdapter() {


                                if (listadd.size() == 1) {
                                    title = listadd.get(0).questionTitle;//标题
                                    content = listadd.get(0).questionData;//内容
                                    tvHudongCollectNumDetail.setText(listadd.get(0).questionCollection + "");
                                    tvPraiseNumDetail.setText(listadd.get(0).questionPraise + "");
                                    userid = listadd.get(0).userId;//用到的参数
                                    questionid = listadd.get(0).questionId;//用到的参数
                                    //没展示的数据
                                    if (TextUtils.isCollect(listadd.get(0).questionId, pictype.cId)) {
                                        m1 = false;
                                        ckHudongCollectNumQuestType.setChecked(TextUtils.isCollect(listadd.get(0).questionId, pictype.cId));//如果是选中状态
                                    } else {
                                        m2 = false;
                                        ckHudongCollectNumQuestType.setChecked(TextUtils.isCollect(listadd.get(0).questionId, pictype.cId));//如果是选中状态
                                    }
                                    if ((TextUtils.isParse(listadd.get(0).questionId, pictype.pId))) {
                                        m3 = false;
                                        ckHudongPraiseNumeQuestType.setChecked(TextUtils.isParse(listadd.get(0).questionId, pictype.pId));
                                    } else {
                                        m4 = false;
                                        ckHudongPraiseNumeQuestType.setChecked(TextUtils.isParse(listadd.get(0).questionId, pictype.pId));
                                    }
                                    return;
                                }
                                listadd.remove(0);
                                title = listadd.get(0).questionTitle;//标题
                                content = listadd.get(0).questionData;//内容
                                tvHudongCollectNumDetail.setText(listadd.get(0).questionCollection + "");
                                tvPraiseNumDetail.setText(listadd.get(0).questionPraise + "");
                                userid = listadd.get(0).userId;//用到的参数
                                questionid = listadd.get(0).questionId;//用到的参数
                                //没展示的数据
                                if (TextUtils.isCollect(listadd.get(0).questionId, pictype.cId)) {
                                    m1 = false;
                                    ckHudongCollectNumQuestType.setChecked(TextUtils.isCollect(listadd.get(0).questionId, pictype.cId));//如果是选中状态
                                } else {
                                    m2 = false;
                                    ckHudongCollectNumQuestType.setChecked(TextUtils.isCollect(listadd.get(0).questionId, pictype.cId));//如果是选中状态
                                }
                                if ((TextUtils.isParse(listadd.get(0).questionId, pictype.pId))) {
                                    m3 = false;
                                    ckHudongPraiseNumeQuestType.setChecked(TextUtils.isParse(listadd.get(0).questionId, pictype.pId));
                                } else {
                                    m4 = false;
                                    ckHudongPraiseNumeQuestType.setChecked(TextUtils.isParse(listadd.get(0).questionId, pictype.pId));
                                }
                            }

                            @Override
                            public void onLeftCardExit(Object dataObject) {

                            }

                            @Override
                            public void onRightCardExit(Object dataObject) {

                            }

                            @Override
                            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                                if (itemsInAdapter == 2) {
                                    if (!norequest) {
                                        realnum = (Integer.parseInt(realnum) + 1) + "";
                                        httprequest();
                                    }
                                }
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onScroll(float scrollProgressPercent) {
                            }
                        });
                    }

                    break;
                default:
                    break;
            }
        }
    };


}
