package com.lvshandian.partylive.moudles.mine.my;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
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
import com.lvshandian.partylive.moudles.mine.bean.VideoBean;
import com.lvshandian.partylive.moudles.mine.my.adapter.CommentAdapter;
import com.lvshandian.partylive.utils.Constant;
import com.lvshandian.partylive.utils.LogUtils;
import com.lvshandian.partylive.utils.TextUtils;
import com.lvshandian.partylive.view.DialogView;
import com.lvshandian.partylive.view.FullScreenVideoView;
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
 * 创建短视屏详情界面
 */

public class Videotails extends BaseActivity {


    @Bind(R.id.image_back)
    ImageView imageBack;
    @Bind(R.id.image_delete)
    ImageView imageDelete;
    @Bind(R.id.et_comment)
    EditText etComment;
    @Bind(R.id.rl_part)
    AutoRelativeLayout rlPart;

    private String videoPath;
    private MediaController mediaController;

    @Override
    protected int getLayoutId() {
        return R.layout.my_activity_videodetails;
    }

    private Handler mHandler = new Handler() {


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);
            switch (msg.what) {

                case RequestCode.MY_PHOTO_DELETE_CODE://图片请求列表
                    //发送至OrtherVideoMyFragment
                    VideoBean videoBean = new VideoBean();
                    videoBean.setId("yes");
                    EventBus.getDefault().post(videoBean);
                    //图片删除成功EventBus.getDefault().post(videoBean);
                    Videotails.this.finish();
                    break;
                case 2://请求列表
                    selectLieBiao();
                    break;
            }

        }
    };
    FullScreenVideoView ImageHead;
    MaterialRefreshLayout refresh;
    TextView tvSendMessage;
    @Bind(R.id.lv)
    ListView lv;

    @Override
    protected void initListener() {
        imageDelete.setOnClickListener(this);
        imageBack.setOnClickListener(this);
        tvSendMessage.setOnClickListener(this);
    }

    private UMSocialService controller;
    final List<UMComment> mComments = new ArrayList<UMComment>();
    private CommentAdapter commentAdapter;
    private int Page = 0;
    private VideoBean videoBean;
    private ImageView img_app;
    private boolean isRequst = true;
    private long comment = -1;
    private int isTag = 0;
    private LoadingDialog mDialog;

    @Override
    protected void initialized() {
        mDialog = new LoadingDialog(mContext);
        tvSendMessage = (TextView) findViewById(R.id.tv_send_message);
        lv = (ListView) findViewById(R.id.lv);
        ImageHead = (FullScreenVideoView) findViewById(R.id.Image_head);
        refresh = (MaterialRefreshLayout) findViewById(R.id.refresh);
        videoBean = (VideoBean) getIntent().getSerializableExtra("video");
        if (TextUtils.isEmpty(getIntent().getStringExtra("isShow"))) {
            imageDelete.setVisibility(View.GONE);
        } else {
            imageDelete.setVisibility(View.VISIBLE);
        }
        img_app = (ImageView) findViewById(R.id.img_app);
        videoPath = videoBean.getUrl();
        ImageLoader.getInstance().displayImage(videoBean.getThumbnailUrl(), img_app);
        play(videoPath);
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
                Toast.makeText(Videotails.this, "用户未登录", Toast.LENGTH_SHORT);


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
        controller = UMServiceFactory.getUMSocialService(videoBean.getId() + "");
        isTag = 0;
        final SnsAccount snsAccount = new SnsAccount(appUser.getNickName(),
                Gender.FEMALE,
                appUser.getPicUrl(),
                appUser.getId());
        final SocializeListeners.SocializeClientListener listener = new SocializeListeners.SocializeClientListener() {
            @Override
            public void onStart() {
                Log.e("ym", "photoBean.getId()" + videoBean.getId());
            }

            @Override
            public void onComplete(int status, SocializeEntity entity) {
                if (status == 200) {


                    LogUtils.n("用户登录成功信息2entityname" + entity.getNickName() + "pic" + entity.getPv());
                    getComment1();
                } else {
                    Toast.makeText(Videotails.this, "登录失败", Toast.LENGTH_SHORT);
                    Log.e("zyn", "登录失败status" + status);
                }

            }
        };
        if (LoginInfoHelp.isLogin(mContext)) {//已登录,获取评论
            //发送评论,具体参数参见下面第三点
            if (!mDialog.isShowing()) {
                mDialog.show();
            }
            selectLieBiao();

        } else {//未登录进行登录
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
        controller = UMServiceFactory.getUMSocialService(videoBean.getId() + "");
        isTag = 0;
        final SnsAccount snsAccount1 = new SnsAccount(appUser.getNickName(),
                Gender.FEMALE,
                appUser.getPicUrl(),
                appUser.getId());
        final SocializeListeners.SocializeClientListener listener1 = new SocializeListeners.SocializeClientListener() {
            @Override
            public void onStart() {
                Log.e("ym", "photoBean.getId()" + videoBean.getId());
            }

            @Override
            public void onComplete(int status, SocializeEntity entity) {
                if (status == 200) {
                    mHandler.sendEmptyMessage(2);
                    LogUtils.n("用户登录成功信息2entityname" + entity.getNickName() + "pic" + entity.getPv());
                } else {
                    Toast.makeText(Videotails.this, "登录失败", Toast.LENGTH_SHORT);
                    Log.e("zyn", "登录失败status" + status);
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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.image_back://返回

                finish();
                break;
            case R.id.image_delete://删除

                new DialogView(getContext(), rlPart, "", "相机", "删除", "取消",
                        new DialogView.MyCameraCallback() {
                            @Override
                            public void refreshCallback(int tag) {

                                switch (tag) {
                                    //调用相机
                                    case 1:
                                        break;
                                    //调用相册
                                    case 2:
                                        ConcurrentHashMap map = new ConcurrentHashMap<>();
                                        httpDatas.getDataForJson("删除视频", Request.Method.DELETE, UrlBuilder.photoDelete(videoBean.getId()), map, mHandler, RequestCode.MY_PHOTO_DELETE_CODE);
                                        break;
                                    //取消
                                    case 3:
                                        break;
                                }
                            }
                        });

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
                if (LoginInfoHelp.isLogin(mContext)) {//已登录,获取评论
                    final UMComment socializeComment = new UMComment();
                    socializeComment.mUserIcon = appUser.getPicUrl();
                    socializeComment.mText = TextUtils.getTextContent(etComment);
                    socializeComment.mUname = appUser.getNickName();
                    socializeComment.mUid = videoBean.getId();
                    LogUtils.e("appUsername", appUser.toString());
                    sentComment(mContext, controller, socializeComment);

                } else {
                    mDialog.dismiss();
                    Toast.makeText(mContext, "发送失败", Toast.LENGTH_SHORT).show();
                    LogUtils.e("发送失败", "用户未登录");
                }

                break;
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
    }

    private void play(final String path) {


        mediaController = new MediaController(this);
        ImageHead.setVideoPath(path);
        // 设置VideView与MediaController建立关联
        ImageHead.setMediaController(mediaController);

//        // 设置MediaController与VideView建立关联
        mediaController.setMediaPlayer(ImageHead);

        mediaController.setVisibility(View.INVISIBLE);
        // 让VideoView获取焦点
//        videoView.requestFocus();
        // 开始播放
        ImageHead.start();
        ImageHead.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                img_app.setVisibility(View.GONE);
                ImageHead.setVisibility(View.VISIBLE);
                mp.setLooping(true);
            }
        });

        ImageHead.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                ImageHead.setVideoPath(path);
                ImageHead.start();
            }
        });

        ImageHead.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {

                return false;
            }
        });
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
                                Toast.makeText(Videotails.this, "加载完毕", Toast.LENGTH_SHORT);
                                LogUtils.e("加载完毕");
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
                            LogUtils.e("获取评论失败" + "status" + status);
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
                                socializeComment.mUid = videoBean.getId();
                                LogUtils.e("appUsername", appUser.getNickName());
                                LogUtils.e("appUsername", appUser.toString());
                                // check login
                                sentComment(mContext, controller, socializeComment);
                            } else {
                                Toast.makeText(mContext, "发送失败", Toast.LENGTH_SHORT)
                                        .show();
                                mDialog.dismiss();
                            }
                            LogUtils.e("status", status + "");
                        }
                    }
                });
    }


}
