package com.lvshandian.partylive.moudles.mine.my;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.lvshandian.partylive.R;
import com.lvshandian.partylive.UrlBuilder;
import com.lvshandian.partylive.base.BaseActivity;
import com.lvshandian.partylive.httprequest.HttpDatas;
import com.lvshandian.partylive.httprequest.RequestCode;
import com.lvshandian.partylive.moudles.mine.bean.PhotoBean;
import com.lvshandian.partylive.moudles.mine.my.adapter.CommentAdapter;
import com.lvshandian.partylive.utils.LogUtils;
import com.lvshandian.partylive.utils.TextUtils;
import com.lvshandian.partylive.view.DialogView;
import com.lvshandian.partylive.view.LoadingDialog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.bean.Gender;
import com.umeng.socialize.bean.MultiStatus;
import com.umeng.socialize.bean.SnsAccount;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.UMComment;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.utils.LoginInfoHelp;
import com.zhy.autolayout.AutoRelativeLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;

/**
 * Created by zhang on 2016/11/21.
 * 创建图片详情界面
 */

public class PhotoDetails extends BaseActivity {


    @Bind(R.id.image_back)
    ImageView imageBack;
    @Bind(R.id.image_delete)
    ImageView imageDelete;
    @Bind(R.id.Image_head)
    ImageView ImageHead;
    @Bind(R.id.et_comment)
    EditText etComment;
    @Bind(R.id.rl_part)
    AutoRelativeLayout rlPart;
    @Bind(R.id.lv)
    ListView lv;
    @Bind(R.id.refresh)
    MaterialRefreshLayout refresh;
    @Bind(R.id.tv_send_message)
    TextView tvSendMessage;

    @Override
    protected int getLayoutId() {
        return R.layout.my_activity_photodetails;
    }

    private Handler mHandler = new Handler() {


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);
            switch (msg.what) {

                case RequestCode.MY_PHOTO_DELETE_CODE://图片请求列表
                    PhotoBean photoBean = new PhotoBean();
                    photoBean.setId("yes");
                    EventBus.getDefault().post(photoBean);
                    //图片删除成功
                    PhotoDetails.this.finish();
                    break;
                case 2://请求列表
                    selectLieBiao();
                    break;

            }

        }
    };

    @Override
    protected void initListener() {
        imageDelete.setOnClickListener(this);
        imageBack.setOnClickListener(this);
        tvSendMessage.setOnClickListener(this);
    }

    private LoadingDialog mDialog;
    private PhotoBean photoBean;
    private UMSocialService controller;
    final List<UMComment> mComments = new ArrayList<UMComment>();
    private CommentAdapter commentAdapter;
    private int Page = 0;
    private boolean isRequst = true;
    private long comment = -1;
    private int isTag = 0;

    @Override
    protected void initialized() {
        mDialog = new LoadingDialog(mContext);
        photoBean = (PhotoBean) getIntent().getSerializableExtra("photo");
        LogUtils.n("photoBean" + photoBean.toString());
        if (TextUtils.isEmpty(getIntent().getStringExtra("isShow"))) {
            imageDelete.setVisibility(View.GONE);
        } else {
            imageDelete.setVisibility(View.VISIBLE);
        }
        ImageLoader.getInstance().displayImage(photoBean.getUrl(), ImageHead);
        commentAdapter = new CommentAdapter(mContext, mComments, R.layout.item_photoactivity);
        lv.setAdapter(commentAdapter);
        //设置listView
        inListView();

    }

    private void inListView() {
        int[] colors = new int[1];
        colors[0] = getResources().getColor(R.color.main);
        refresh.setProgressColors(colors);
        refresh.setLoadMore(true);

        refresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                /**
                 * 刷新
                 */

                if (LoginInfoHelp.isLogin(mContext)) {//已登录,获取评论
                    //发送评论,具体参数参见下面第三点
                    comment = -1;
                    Page = 0;
                    isRequst = true;
                    selectLieBiao();
                    return;
                }
                Toast.makeText(PhotoDetails.this, "用户未登录", Toast.LENGTH_SHORT);


            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                // TODO Auto-generated method stub
                super.onRefreshLoadMore(materialRefreshLayout);

                if (isRequst) {
                    Page++;
                    selectLieBiao();

                }

            }
        });
        //获取评论
        getComment();
    }

    private void getComment() {
        controller = UMServiceFactory.getUMSocialService(photoBean.getId() + "");
        isTag = 0;
        final SnsAccount snsAccount = new SnsAccount(appUser.getNickName(),
                Gender.MALE,
                appUser.getPicUrl(),
                appUser.getId());
        final SocializeListeners.SocializeClientListener listener = new SocializeListeners.SocializeClientListener() {
            @Override
            public void onStart() {
                LogUtils.n("SocializeClientListener-onStart");
            }

            @Override
            public void onComplete(int status, SocializeEntity entity) {
                LogUtils.n("SocializeClientListener-onComplete");
                if (status == 200) {
                    LogUtils.n("用户登录成功信息2entityname" + entity.getNickName() + "pic" + entity.getPv());
                    getComment1();
                } else {
                    Toast.makeText(PhotoDetails.this, "登录失败", Toast.LENGTH_SHORT);
                    LogUtils.n("用户登录失败1");
                }

            }
        };

        if (LoginInfoHelp.isLogin(mContext)) {//已登录,获取评论
            //发送评论,具体参数参见下面第三点
            LogUtils.n("已登录--");
            if (!mDialog.isShowing()) {
                mDialog.show();
            }
            selectLieBiao();

        } else {//未登录进行登录
            LogUtils.n("未登录--");
            if (!mDialog.isShowing()) {
                mDialog.show();
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    controller.login(mContext, snsAccount, listener);
                }
            }).start();
        }
    }

    private void getComment1() {
        isTag = 0;
        final SnsAccount snsAccount1 = new SnsAccount(appUser.getNickName(),
                Gender.MALE,
                appUser.getPicUrl(),
                appUser.getId());
        final SocializeListeners.SocializeClientListener listener1 = new SocializeListeners.SocializeClientListener() {
            @Override
            public void onStart() {
                LogUtils.n("SocializeClientListener-onStart");
            }

            @Override
            public void onComplete(int status, SocializeEntity entity) {
                LogUtils.n("SocializeClientListener-onComplete");
                if (status == 200) {
                    LogUtils.n("用户登录成功信息1entityname" + entity.getNickName() + "pic" + entity.getPv());
                    mHandler.sendEmptyMessage(2);
                } else {
                    Toast.makeText(PhotoDetails.this, "登录失败", Toast.LENGTH_SHORT);
                    LogUtils.n("用户登录失败2");
                }

            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                controller.login(mContext, snsAccount1, listener1);
            }
        }).start();
    }


    private void selectLieBiao() {
        controller.getComments(mContext,
                new SocializeListeners.FetchCommetsListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onComplete(int status,
                                           List<UMComment> commentss,
                                           SocializeEntity entity) {
                        if (status == 200 && commentss != null) {
                            if (null == commentss || commentss.size() < 1) {
                                isRequst = false;
                                Toast.makeText(PhotoDetails.this, "加载完毕", Toast.LENGTH_SHORT);
                                LogUtils.n("加载完毕");
                                refresh.finishRefreshLoadMore();
                                refresh.finishRefreshing();
                                refresh.setLoadMore(false);
                                mDialog.dismiss();
                                return;
                            }
                            if (Page == 0) {
                                mComments.clear();
                                mComments.addAll(commentss);
                                commentAdapter = new CommentAdapter(mContext, mComments, R.layout.item_photoactivity);
                                lv.setAdapter(commentAdapter);
                                refresh.setLoadMore(true);
                                // refresh complete
                                refresh.finishRefresh();
                                comment = mComments.get(mComments.size() - 1).mDt;

                            } else {
                                mComments.addAll(commentss);
                                commentAdapter.notifyDataSetChanged();
                                refresh.finishRefreshLoadMore();
                                comment = mComments.get(mComments.size() - 1).mDt;
                            }

                        } else {
                            if (isTag == 0) {
                                isTag++;
                                selectLieBiao();
                                return;
                            }
                            LogUtils.n("获取评论失败");
                            Toast.makeText(
                                    mContext,
                                    "获取评论失败", Toast.LENGTH_SHORT).show();

                        }
                        mDialog.dismiss();
                    }
                }, comment);// 翻页最有一个参数传入已获取的最后一条评论的时间（SocializeComment.dt）
        //是否强制登录后才能发表评论. 取值为false表示将以游客身份发表评论
    }


    private void sentComment(final Context mContext,
                             final UMSocialService controller,
                             UMComment socializeComment) {
        controller.postComment(mContext, socializeComment,
                new SocializeListeners.MulStatusListener() {
                    @Override
                    public void onStart() {


                    }

                    @Override
                    public void onComplete(
                            MultiStatus multiStatus,
                            int status, SocializeEntity entity) {
                        if (status == 200) {
                            /**
                             * 刷新
                             */
                            LogUtils.n("发送评论成功" + appUser.toString());
                            comment = -1;
                            Page = 0;
                            isRequst = true;
                            selectLieBiao();
                            etComment.setText("");
                            mDialog.dismiss();
                        } else {
                            if (isTag == 0) {
                                isTag++;
                                UMComment socializeComment = new UMComment();
                                socializeComment.mText = TextUtils.getTextContent(etComment);
                                socializeComment.mUname = appUser.getNickName();
                                socializeComment.mUserIcon = appUser.getPicUrl();
                                socializeComment.mUid = photoBean.getId();
                                LogUtils.n("二次发送评论onComplete" + appUser.toString());
                                // check login
                                sentComment(mContext, controller, socializeComment);
                            } else {
                                LogUtils.n("发送失败onComplete" + appUser.toString());
                                Toast.makeText(mContext, "发送失败", Toast.LENGTH_SHORT)
                                        .show();
                                mDialog.dismiss();
                            }
                            LogUtils.n("发送失败status" + status + "");
                        }

                    }
                });
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.image_back://返回

                finish();
                break;
            case R.id.tv_send_message://发送评论
                isTag = 0;
                if (TextUtils.isEmpty(TextUtils.getTextContent(etComment))) {
                    Toast.makeText(mContext, "发送内容不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!mDialog.isShowing()) {
                    mDialog.show();
                }
                if (LoginInfoHelp.isLogin(mContext)) {//
                    final UMComment socializeComment = new UMComment();
                    socializeComment.mUserIcon = appUser.getPicUrl();
                    socializeComment.mText = TextUtils.getTextContent(etComment);
                    socializeComment.mUname = appUser.getNickName();
                    socializeComment.mUid = photoBean.getId();
                    LogUtils.n("发送评论" + appUser.toString());
                    // check login
                    sentComment(mContext, controller, socializeComment);

                } else {
                    Toast.makeText(mContext, "发送失败", Toast.LENGTH_SHORT).show();
                    LogUtils.n("用户未登录");
                    mDialog.dismiss();
                }


                break;
            case R.id.image_delete://删除

                new DialogView(getContext(), rlPart,

                        "", "相机", "删除", "取消",
                        new DialogView.MyCameraCallback()

                        {
                            @Override
                            public void refreshCallback(int tag) {

                                switch (tag) {
                                    //调用相机
                                    case 1:
                                        break;
                                    //调用相册
                                    case 2:
                                        ConcurrentHashMap map = new ConcurrentHashMap<>();
                                        httpDatas.getDataForJson("删除图片", Request.Method.DELETE, UrlBuilder.photoDelete(photoBean.getId()), map, mHandler, RequestCode.MY_PHOTO_DELETE_CODE);
                                        break;
                                    //取消
                                    case 3:
                                        break;
                                }
                            }
                        }

                );

                break;
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
    }


}
