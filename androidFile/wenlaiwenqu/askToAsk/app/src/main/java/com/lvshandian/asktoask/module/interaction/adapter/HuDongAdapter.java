package com.lvshandian.asktoask.module.interaction.adapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lvshandian.asktoask.MainActivity;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.adapter.CommonAdapter;
import com.lvshandian.asktoask.common.adapter.ViewHolder;
import com.lvshandian.asktoask.common.http.HttpDatas;
import com.lvshandian.asktoask.entry.HuDongItem;
import com.lvshandian.asktoask.module.interaction.InteractionFragment;
import com.lvshandian.asktoask.module.interaction.activity.HuDongDetailActivity;
import com.lvshandian.asktoask.module.login.LoginActivity;
import com.lvshandian.asktoask.utils.DateUtil;
import com.lvshandian.asktoask.utils.DialogView;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.TextUtils;
import com.lvshandian.asktoask.utils.UrlBuilder;
import com.lvshandian.asktoask.view.HuDongSharePopupwindow;
import com.lvshandian.asktoask.view.LoadUrlImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.List;
/**
 * 互动 下方列表
 */
public class HuDongAdapter extends CommonAdapter<HuDongItem.DataBean.PageBean.DataBean2> {
    HuDongItem.DataBean.PageBean.DataBean2 transferbean;
    Context context;
    BitmapUtils bitmapUtils;
    HuDongItem.DataBean dataBean2;//的值传过来 用于判断是否点赞
    // 用来控制点赞CheckBox的选中状况
    private static HashMap<Integer, Boolean> likeIssSelected  = new HashMap<Integer, Boolean>();
    //用来控制收藏的选中的情况
    private static HashMap<Integer, Boolean>  collectisSelected = new HashMap<Integer, Boolean>();
    private View viewreal;
    String[] pic;




    public HuDongAdapter(Context context, List<HuDongItem.DataBean.PageBean.DataBean2> mDatas, int itemLayoutId, HuDongItem.DataBean dataBean, HttpDatas httpDatasreal, View view, HuDongSharePopupwindow popu) {
        super(context, mDatas, itemLayoutId);
        this.context = context;
        bitmapUtils = new BitmapUtils(context);
        dataBean2 = dataBean;
        viewreal=view;
        init();

    }

    /**
     * //     * 收藏
     *
     * @return
     */
    public static HashMap<Integer, Boolean> getCollectisSelected() {
        return collectisSelected;
    }


    /**
     * 点赞
     *
     * @return
     */
    public static HashMap<Integer, Boolean> getIsSelected() {
        return likeIssSelected;
    }

    /**
     * 用map来保存状态 避免错乱
     * @param
     */

    private void init(){
        for (int i = 0; i < mDatas.size(); i++) {
            if (TextUtils.isEmpty(mDatas.get(i).questionId) || TextUtils.isEmpty(dataBean2.cId)) {
                getCollectisSelected().put(i, false);
                continue;
            }
            //收藏
            if (TextUtils.isCollect(mDatas.get(i).questionId, dataBean2.cId)) {
                getCollectisSelected().put(i, true);
            } else {
                getCollectisSelected().put(i, false);

            }

        }


        //点赞
        for (int i = 0; i < mDatas.size(); i++) {

            if (TextUtils.isEmpty(mDatas.get(i).questionId) || TextUtils.isEmpty(dataBean2.pId)) {
                getIsSelected().put(i, false);
                continue;
            }

            if (TextUtils.isParse(mDatas.get(i).questionId, dataBean2.pId)) {
                getIsSelected().put(i, true);
            } else {
                getIsSelected().put(i, false);

            }


        }

    }




    @Override
    public void convert(ViewHolder helper, final HuDongItem.DataBean.PageBean.DataBean2 item, final int position) {
        helper.setText(R.id.tv_title_hudong, item.getQuestionTitle());
        helper.setText(R.id.tv_time_hudong, DateUtil.timesOne(item.getQuestionPublishDate()));
        helper.setText(R.id.tv_price_hudong, "￥" + item.getQuestionMoney()+"0");
        helper.setText(R.id.tv_type_hudong, item.getQuestionType());
        LinearLayout llbigcollect=(LinearLayout)helper.getView(R.id.ll_big_onclick_collect);//收藏
        LinearLayout llbigpraise=(LinearLayout)helper.getView(R.id.ll_big_onclick_praise);//点赞
        ImageView ivleft=(ImageView)helper.getView(R.id.iv_itemleft_hudong);
        ImageView ivright=(ImageView)helper.getView(R.id.iv_itemright_hudong);
        final TextView tvcollectnum = (TextView) helper.getView(R.id.tv_hudong_collect_num);//收藏的数量
        final TextView tvparsenum = (TextView) helper.getView(R.id.tv_praise_num);//点赞的数量
        final ImageView ckPraise = helper.getView(R.id.ck_hudong_praise);
        final ImageView ckCollect = helper.getView(R.id.ck_hudong_collect_num);
        tvcollectnum.setText(item.questionCollection+"");//收藏赋值
        tvparsenum.setText(item.questionPraise+"");//点赞赋值
        transferbean = item;
        //没有图片的话 不展示图片
        if(TextUtils.isEmpty(item.questionImgs)){
            helper.getView(R.id.ll_pic_jump).setVisibility(View.GONE);
        }else{
            helper.getView(R.id.ll_pic_jump).setVisibility(View.VISIBLE);
            int num = isBig2(item.questionImgs);
            if (num == 1) {
                ImageLoader.getInstance().displayImage(pic[0],ivleft);
                ivleft.setVisibility(View.VISIBLE);
                ivright.setVisibility(View.INVISIBLE);
            }
            if (num == 2) {
                 ivleft.setVisibility(View.VISIBLE);
                ivright.setVisibility(View.VISIBLE);
                        ImageLoader.getInstance().displayImage(pic[0], ivleft);
                ImageLoader.getInstance().displayImage(pic[1], ivright);
            }
        }
            if (likeIssSelected.get(position)!=null&&likeIssSelected.get(position)) {//是否点赞过
                //设置checkbox选中
                ckPraise.setBackgroundResource(R.drawable.collectsel);
                item.ispraise=true;

            }//已经点赞
            else{
                ckPraise.setBackgroundResource(R.drawable.collectunsel);
                item.ispraise=false;

            }


            if (collectisSelected.get(position)!=null&&collectisSelected.get(position)) {
                //设置checkbox选中
                ckCollect.setBackgroundResource(R.drawable.praisesel);
                item.iscollect=true;

            }
            else{
                ckCollect.setBackgroundResource(R.drawable.praiseunsel);
                item.iscollect=false;
            }

/**
 * 收藏
 */

        llbigcollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Global.isLogin(context)) {
                    if (collectisSelected.get(position)==null||!collectisSelected.get(position)) {
                        item.questionCollection++;
                        tvcollectnum.setText(Long.parseLong(tvcollectnum.getText().toString()) + 1 + "");
                        ckCollect.setBackgroundResource(R.drawable.praisesel);
                        item.iscollect = true;
                        collectisSelected.put(position,true);
                        item.iscollectonclick= true;
                        RequestParams params = new RequestParams();
                        params.addBodyParameter("collectorId", Global.getUserId(context));
                        params.addBodyParameter("collectordId", item.getUserId());
                        params.addBodyParameter("questionId", item.questionId);
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

                    } else {
                        item.questionCollection--;
                        tvcollectnum.setText(Long.parseLong(tvcollectnum.getText().toString()) - 1 + "");
                        ckCollect.setBackgroundResource(R.drawable.praiseunsel);
                        item.iscollect = false;
                        collectisSelected.put(position, false);
                        RequestParams params = new RequestParams();
                        params.addBodyParameter("collectorId", Global.getUserId(context));
                        params.addBodyParameter("collectordId", item.getUserId());
                        params.addBodyParameter("questionId", item.questionId);
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
                } else {
                    showDialog(context,viewreal);//用户没有登录的话去登录
                }
            }
        });
        /**
         * 点赞
         */

        llbigpraise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( Global.isLogin(context)) {
                    if (likeIssSelected.get(position)==null||!likeIssSelected.get(position)) {
                        item.questionPraise++;
                        tvparsenum.setText(Long.parseLong(tvparsenum.getText().toString()) + 1 + "");
                        ckPraise.setBackgroundResource(R.drawable.collectsel);
                        item.ispraise = true;
                        likeIssSelected.put(position,true);
                        RequestParams params = new RequestParams();
                        params.addBodyParameter("praiserId", Global.getUserId(context));
                        params.addBodyParameter("praiseredId", item.getUserId());
                        params.addBodyParameter("questionId", item.questionId);
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
                                        String str=responseInfo.result;
                                        try {
                                            JSONObject object=new JSONObject(str);
                                            object.getString("msg");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }

                                    @Override
                                    public void onFailure(HttpException error, String msg) {


                                    }
                                });
                    } else {
                        item.questionPraise--;
                        tvparsenum.setText(Long.parseLong(tvparsenum.getText().toString()) - 1 + "");
                        ckPraise.setBackgroundResource(R.drawable.collectunsel);
                        item.ispraise = false;
                        likeIssSelected.put(position,false);
                        RequestParams params = new RequestParams();
                        params.addBodyParameter("praiserId",Global.getUserId(context));
                        params.addBodyParameter("praiseredId", item.getUserId());
                        params.addBodyParameter("questionId",item.questionId);
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

                } else {
                    showDialog(context,viewreal);//用户没有登录的话去登录
                }
            }
        });

        helper.getView(R.id.ll_share_hudong).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogView(mContext, MainActivity.llParentView,item);//分享
            }
        });
        helper.getView(R.id.ll_hudong_chiefly_oncick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHuDongDetailActivity(item, tvparsenum.getText().toString(), tvcollectnum.getText().toString());//互动详情界面
            }
        });

    }



    /**
     * 弹出框让用户登录
     */
    public void showDialog(Context context, View view) {


        new DialogView(context, view, 0, "你还未登录，请登录", "确定", "取消",
                new DialogView.MyCallback() {


                    @Override
                    public void refreshActivity() {

                      goToLogin();
                    }
                });


    }


    /**
     * 去登录
     */
    private void goToLogin() {

        context.startActivity(new Intent(context, LoginActivity.class));
    }

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


    /**
     * 互动首页跳转到互动详情界面
     */

    private void goToHuDongDetailActivity(HuDongItem.DataBean.PageBean.DataBean2 transferbean,String parsenum,String collectnum) {
        InteractionFragment.isfrush=true;
        Intent intent = new Intent(context, HuDongDetailActivity.class);
        transferbean.questionCollection=Integer.parseInt(collectnum);
        transferbean.questionPraise=Integer.parseInt(parsenum);
        intent.putExtra(HuDongDetailActivity.TRANSFER, transferbean);
        context.startActivity(intent);
    }






}

