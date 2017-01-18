package com.lvshandian.asktoask.module.answer.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.adapter.CommonAdapter;
import com.lvshandian.asktoask.common.adapter.ViewHolder;
import com.lvshandian.asktoask.common.http.HttpDatas;
import com.lvshandian.asktoask.entry.Answer;
import com.lvshandian.asktoask.module.answer.activity.AnswerDetailActivity;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.UrlBuilder;
import com.lvshandian.asktoask.utils.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

/**
 * Created by 王伟 on 2016/9/19.
 * 答咖首页adapter 列表
 */
public class AnswerAdapter extends CommonAdapter<Answer.DataBean.ClientUsersBean> {
    Context context;
    HttpDatas httpdata;
    public AnswerAdapter(Context context, List<Answer.DataBean.ClientUsersBean> mDatas, int itemLayoutId, HttpDatas httpdatas) {
        super(context, mDatas, itemLayoutId);
        this.context = context;
        httpdata = httpdatas;

    }
    @Override
    public void convert(ViewHolder helper, final Answer.DataBean.ClientUsersBean item, int position) {

        ImageLoader.getInstance().displayImage(item.userHeadImg, (ImageView) helper.getView(R.id.iv_answer_item_chiefly2));
       final View view=helper.getView(R.id.tv_answer_chiefly_name);
        helper.setText(R.id.tv_answer_chiefly_name, item.userRealName);
        helper.setText(R.id.tv_answer_collect_num, item.userCollect + "");
        helper.setText(R.id.tv__answer_praise_num, item.userPraise + "");
        helper.setText(R.id.tv_accept_rate, "采纳率为："+item.extend1 );
        helper.getView(R.id.rl_answer_detail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Global.isLogin(context)){
                    ToastUtils.showSnackBar(view,"请先去登陆后再查看");
                }else{
                    Intent intent = new Intent(context, AnswerDetailActivity.class);
                    intent.putExtra(AnswerDetailActivity.ANSWERDETAILID,item.userId);
                    intent.putExtra(AnswerDetailActivity.ANSWERDETAILNAME,item.userRealName);
                    context.startActivity(intent);
                }


            }
        });
        final TextView tvisApprove= (TextView) helper.getView(R.id.tv_is_focus);
        if ("y".equals(item.extend2)) {
            tvisApprove.setText("已关注");
            tvisApprove.setTextColor(context.getResources().getColor(R.color.cccccccolor));
            tvisApprove.setBackgroundResource(R.drawable.answer_chiefly_unfocus);
            item.isfocus=false;
        } else {
            tvisApprove.setText("+ 关注");
            tvisApprove.setTextColor(context.getResources().getColor(R.color.main));
            tvisApprove.setBackgroundResource(R.drawable.answer_chiefly_focus);
            item.isfocus=true;
        }
        tvisApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Global.isLogin(context)) {
                    ToastUtils.showSnackBar(view, "请先去登陆");
                    return;
                }

                if (item.isfocus) {
                    tvisApprove.setEnabled(false);
                    item.isfocus = false;
//                      focus(item.userId);//去关注
                    RequestParams params = new RequestParams();
                    params.addBodyParameter("attentorId", Global.getUserId(context));
                    params.addBodyParameter("attentoredId", item.userId);
                    HttpUtils http = new HttpUtils();
                    http.send(HttpRequest.HttpMethod.POST,
                            UrlBuilder.serverUrl + UrlBuilder.FOCUS_ANSWER_URL,
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
                                        Log.d("aaa", msg);

                                        if ("OK".equals(msg)) {
                                            tvisApprove.setText("已关注");
                                            tvisApprove.setTextColor(context.getResources().getColor(R.color.cccccccolor));
                                            tvisApprove.setBackgroundResource(R.drawable.answer_chiefly_unfocus);

                                            ToastUtils.showSnackBar(tvisApprove, "关注成功");

                                            tvisApprove.setEnabled(true);
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onFailure(HttpException error, String msg) {


                                }
                            });

                } else {
                    item.isfocus = true;
                    tvisApprove.setEnabled(false);
//                      focuscancle(item.userId);//取消关注
                    RequestParams params = new RequestParams();
                    params.addBodyParameter("attentorId", Global.getUserId(context));
                    params.addBodyParameter("attentoredId", item.userId);
                    HttpUtils http = new HttpUtils();
                    http.send(HttpRequest.HttpMethod.POST,
                            UrlBuilder.serverUrl + UrlBuilder.CANCLE_FOCUS_ANSWER_URL,
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
                                        if ("OK".equals(msg)) {
                                            tvisApprove.setText("+ 关注");
                                            tvisApprove.setTextColor(context.getResources().getColor(R.color.main));
                                            tvisApprove.setBackgroundResource(R.drawable.answer_chiefly_focus);
                                            ToastUtils.showSnackBar(tvisApprove, "取消关注");
                                            tvisApprove.setEnabled(true);
                                        }
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
        });


    }



}
