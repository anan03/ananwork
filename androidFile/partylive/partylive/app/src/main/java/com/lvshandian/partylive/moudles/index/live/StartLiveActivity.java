package com.lvshandian.partylive.moudles.index.live;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.ksyun.media.player.IMediaPlayer;
import com.ksyun.media.player.KSYMediaPlayer;
import com.ksyun.media.streamer.filter.imgtex.ImgTexFilterMgt;
import com.ksyun.media.streamer.kit.KSYStreamer;
import com.ksyun.media.streamer.kit.StreamerConstants;
import com.lvshandian.partylive.R;
import com.lvshandian.partylive.UrlBuilder;
import com.lvshandian.partylive.adapter.CommonAdapter;
import com.lvshandian.partylive.adapter.ViewHolder;
import com.lvshandian.partylive.base.BarrageDateBean;
import com.lvshandian.partylive.base.BaseActivity;
import com.lvshandian.partylive.base.Constant;
import com.lvshandian.partylive.base.CustomStringCallBack;
import com.lvshandian.partylive.bean.CreatReadyBean;
import com.lvshandian.partylive.bean.CustomGiftBean;
import com.lvshandian.partylive.bean.CustomShowBean;
import com.lvshandian.partylive.bean.CustomdateBean;
import com.lvshandian.partylive.bean.DianBoDateBean;
import com.lvshandian.partylive.bean.GiftBean;
import com.lvshandian.partylive.bean.LianMaiDateBean;
import com.lvshandian.partylive.bean.LiveBean;
import com.lvshandian.partylive.bean.LiveFamilyMemberBean;
import com.lvshandian.partylive.bean.RoomUserBean;
import com.lvshandian.partylive.bean.RoomUserDataBean;
import com.lvshandian.partylive.bean.SendGiftBean;
import com.lvshandian.partylive.gles.FBO;
import com.lvshandian.partylive.httprequest.HttpDatas;
import com.lvshandian.partylive.httprequest.RequestCode;
import com.lvshandian.partylive.interf.ResultListener;
import com.lvshandian.partylive.moudles.index.CustomNotificationType;
import com.lvshandian.partylive.moudles.index.adapter.FamilyMemberAdapter;
import com.lvshandian.partylive.moudles.index.live.gift.GiftFrameLayout;
import com.lvshandian.partylive.moudles.index.live.gift.GiftSendModel;
import com.lvshandian.partylive.moudles.mine.bean.GongXianBean;
import com.lvshandian.partylive.moudles.mine.bean.GongXianData;
import com.lvshandian.partylive.moudles.mine.my.GongXianActivity;
import com.lvshandian.partylive.moudles.mine.my.OtherPersonHomePageActivity;
import com.lvshandian.partylive.moudles.mine.my.adapter.OnItemClickListener;
import com.lvshandian.partylive.moudles.mine.my.adapter.RoomUsersDataAdapter;
import com.lvshandian.partylive.utils.CacheUtils;
import com.lvshandian.partylive.utils.ChannelToLiveBean;
import com.lvshandian.partylive.utils.Config;
import com.lvshandian.partylive.utils.DESUtil;
import com.lvshandian.partylive.utils.GrademipmapUtils;
import com.lvshandian.partylive.utils.GuanZhuUtils;
import com.lvshandian.partylive.utils.JavaBeanMapUtils;
import com.lvshandian.partylive.utils.JsonUtil;
import com.lvshandian.partylive.utils.LogUtils;
import com.lvshandian.partylive.utils.QosObject;
import com.lvshandian.partylive.utils.QosThread;
import com.lvshandian.partylive.utils.SendRoomMessageUtils;
import com.lvshandian.partylive.utils.Sharedpreferences;
import com.lvshandian.partylive.utils.SoftKeyBoardUtils;
import com.lvshandian.partylive.utils.ToastUtil;
import com.lvshandian.partylive.view.BarrageView;
import com.lvshandian.partylive.view.RotateLayout;
import com.lvshandian.partylive.view.RoundDialog;
import com.lvshandian.partylive.view.ToastUtils;
import com.lvshandian.partylive.wangyiyunxin.chatroom.fragment.ChatRoomMessageFragment;
import com.lvshandian.partylive.wangyiyunxin.chatroom.helper.ChatRoomMemberCache;
import com.lvshandian.partylive.wangyiyunxin.live.fragment.ChatRoomSessionListFragment;
import com.lvshandian.partylive.wangyiyunxin.live.fragment.LiveMessageFragment;
import com.lvshandian.partylive.wangyiyunxin.main.helper.SystemMessageUnreadManager;
import com.lvshandian.partylive.wangyiyunxin.main.reminder.ReminderItem;
import com.lvshandian.partylive.wangyiyunxin.main.reminder.ReminderManager;
import com.lvshandian.partylive.wangyiyunxin.main.reminder.ReminderSettings;
import com.lvshandian.partylive.widget.AvatarView;
import com.lvshandian.partylive.widget.myrecycler.RefreshRecyclerView;
import com.lvshandian.partylive.widget.myrecycler.adapter.RefreshRecyclerViewAdapter;
import com.lvshandian.partylive.widget.myrecycler.manager.RecyclerMode;
import com.lvshandian.partylive.widget.myrecycler.manager.RecyclerViewManager;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.common.ui.dialog.DialogMaker;
import com.netease.nim.uikit.common.ui.drop.DropFake;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nim.uikit.session.constant.Extras;
import com.netease.nimlib.sdk.AbortableFuture;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.ResponseCode;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.chatroom.ChatRoomService;
import com.netease.nimlib.sdk.chatroom.ChatRoomServiceObserver;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomKickOutEvent;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMember;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMessage;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomStatusChangeData;
import com.netease.nimlib.sdk.chatroom.model.EnterChatRoomData;
import com.netease.nimlib.sdk.chatroom.model.EnterChatRoomResultData;
import com.netease.nimlib.sdk.chatroom.model.MemberOption;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.SystemMessageObserver;
import com.netease.nimlib.sdk.msg.SystemMessageService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomNotification;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.qiniu.android.dns.DnsManager;
import com.qiniu.android.dns.IResolver;
import com.qiniu.android.dns.NetworkInfo;
import com.qiniu.android.dns.http.DnspodFree;
import com.qiniu.android.dns.local.AndroidDnsServer;
import com.qiniu.android.dns.local.Resolver;
import com.qiniu.pili.droid.streaming.AVCodecType;
import com.qiniu.pili.droid.streaming.AudioSourceCallback;
import com.qiniu.pili.droid.streaming.CameraStreamingSetting;
import com.qiniu.pili.droid.streaming.FrameCapturedCallback;
import com.qiniu.pili.droid.streaming.MediaStreamingManager;
import com.qiniu.pili.droid.streaming.MicrophoneStreamingSetting;
import com.qiniu.pili.droid.streaming.StreamStatusCallback;
import com.qiniu.pili.droid.streaming.StreamingPreviewCallback;
import com.qiniu.pili.droid.streaming.StreamingProfile;
import com.qiniu.pili.droid.streaming.StreamingSessionListener;
import com.qiniu.pili.droid.streaming.StreamingState;
import com.qiniu.pili.droid.streaming.StreamingStateChangedListener;
import com.qiniu.pili.droid.streaming.SurfaceTextureCallback;
import com.qiniu.pili.droid.streaming.WatermarkSetting;
import com.qiniu.pili.droid.streaming.widget.AspectFrameLayout;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Request;
import com.tandong.bottomview.view.BottomView;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SmsHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/21.
 */

public class StartLiveActivity extends BaseActivity implements
        ReminderManager.UnreadNumChangedCallback,
        View.OnLayoutChangeListener,
        StreamStatusCallback,
        StreamingPreviewCallback,
        SurfaceTextureCallback,
        AudioSourceCallback,
        CameraPreviewFrameView.Listener,
        StreamingSessionListener,
        StreamingStateChangedListener, com.lvshandian.partylive.view.CameraPreviewFrameView.Listener {
    //    @Bind(R.id.camera_preview)
//    GLSurfaceView mCameraPreview;
    @Bind(R.id.live_head)
    AvatarView liveHead;
    @Bind(R.id.live_name)
    TextView liveName;
    @Bind(R.id.live_num)
    TextView liveNum;
    @Bind(R.id.live_jinpiao)
    TextView liveJinpiao;
    @Bind(R.id.ll_yp_labe)
    AutoLinearLayout llYpLabe;
    @Bind(R.id.live_id)
    TextView liveId;
    @Bind(R.id.live_close)
    ImageView liveClose;
    @Bind(R.id.gift_layout1)
    GiftFrameLayout giftFrameLayout1;
    @Bind(R.id.gift_layout2)
    GiftFrameLayout giftFrameLayout2;
    @Bind(R.id.iv_live_mei)
    ImageView ivLiveMei;
    @Bind(R.id.iv_live_privatechat)
    ImageView ivLivePrivatechat;
    @Bind(R.id.iv_live_dianbo)
    ImageView ivLiveDianbo;
    @Bind(R.id.iv_live_lianmai)
    ImageView ivLiveLianmai;
    @Bind(R.id.iv_live_switch)
    ImageView ivLiveSwitch;
    @Bind(R.id.iv_live_biaoyan)
    ImageView ivLiveBiaoyan;
    @Bind(R.id.iv_live_share)
    ImageView ivLiveShare;
    @Bind(R.id.ll_bottom_menu)
    AutoRelativeLayout llBottomMenu;
    @Bind(R.id.fl_bottom_menu)
    FrameLayout flBottomMenu;
    @Bind(R.id.watch_room_message)
    AutoFrameLayout watchRoomMessage;
    @Bind(R.id.rl_live_root)
    AutoRelativeLayout mRoot;
    @Bind(R.id.rlv_list_live_audiences)
    RecyclerView rlvListLiveAudiences;
    @Bind(R.id.tab_new_indicator)
    ImageView tabNewIndicator;
    @Bind(R.id.tab_new_msg_label)
    DropFake tabNewMsgLabel;
    @Bind(R.id.video_lian)
    SurfaceView mVideoSurfaceView;
    @Bind(R.id.lm_fm)
    AutoFrameLayout lmFm;
    @Bind(R.id.iv_live_dianBo_num)
    TextView ivLiveDianBoNum;
    @Bind(R.id.iv_live_lianMai_num)
    TextView ivLiveLianMaiNum;
    @Bind(R.id.barrageview)
    BarrageView barrageview;
    @Bind(R.id.start_room_jaiZu)
    ImageView startRoomJaiZu;

    protected PowerManager.WakeLock mWl;
    private CreatReadyBean creatReadyBean;

    private final static int PERMISSION_REQUEST_CAMERA_AUDIOREC = 1;


    private boolean mHWEncoderUnsupported;
    private boolean mSWEncoderUnsupported;

    private boolean IS_START_LIVE = true;


    private final static String TAG = "lsd";

    private boolean mBeauty = false;

    private int shareType = 1;

//    private MediaPlayer mPlayer;

    private int mPalyTimerDuration = 1000;

    private ChatRoomMessage message;

    private List<GiftBean> mGiftList = new ArrayList<>();
    private List<DianBoDateBean> dianBoDateList = new ArrayList<>();
    private List<LianMaiDateBean> lianMaiDateList = new ArrayList<>();

    List<GiftSendModel> giftSendModelList = new ArrayList<GiftSendModel>();

    private String roomId;
    private ChatRoomMessageFragment messageFragment;
    private AbortableFuture<EnterChatRoomResultData> enterRequest;
    //消息中心Fragment
    private ChatRoomSessionListFragment sessionListFragment;
    private LiveMessageFragment liveMessageFragment;
    protected Handler mMainHandler = new Handler();
    //记录点播人的id
    private String sessionId;
    //记录连麦人的id
    private String sessionIdForLive;
    //表演id
    private String vodId;
    //记录未处理表演数
    private int showNum;
    //记录未处理连麦数
    private int liveNums;
    //记录在线人数
    private int liveOnLineNums;
    //记录禁言的人
    private List<String> mutedList = new ArrayList<>();

    //主播端连麦拉流
    private KSYMediaPlayer ksyMediaPlayer;
    private KSYStreamer mStreamer;
    private QosThread mQosThread;

    private Surface mSurface = null;

    private SurfaceHolder mSurfaceHolder = null;

    private Dialog dialogForSelect;
    private boolean mPause = false;

    private long mStartTime = 0;
    protected StreamingProfile mProfile;
    private int mCurrentCamFacingIndex;
    protected CameraStreamingSetting mCameraStreamingSetting;
    protected MicrophoneStreamingSetting mMicrophoneStreamingSetting;

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);

            switch (msg.what) {
                //礼物列表
                case RequestCode.GET_GIFT:


                    mGiftList = JsonUtil.json2BeanList(json, GiftBean.class);

                    break;
                case RequestCode.REQUEST_USER_INFO:
                    //请求用户信息
                    LogUtils.i(json.toString());
                    CustomdateBean customdateBean = JSON.parseObject(json, CustomdateBean.class);
                    showDialogForCallOther(customdateBean);
                    break;
                case RequestCode.REQUEST_ROOM_VODS:
                    //点播的节目列表
                    LogUtils.i(json.toString());
                    dianBoDateList = JSON.parseArray(json, DianBoDateBean.class);

                    break;
                case RequestCode.REQUEST_ROOM_VOD_START:
                    //主播开始表演
                    LogUtils.i(json.toString());
                    showPopBiaoYan();
//                    showStartPop.showAtLocation(showStartPopView, Gravity.CENTER, 0, 0);
                    break;
                case RequestCode.REQUEST_ROOM_VOD_END:
                    //主播结束表演
                    LogUtils.i(json.toString());
                    break;
                case RequestCode.REQUEST_ROOM_LIVE:
                    //主播获取全部申请(连麦)列表
                    LogUtils.i(json.toString());
                    lianMaiDateList = JSON.parseArray(json, LianMaiDateBean.class);
                    break;
                case RequestCode.REQUEST_ROOM_CLOES:

                    LogUtils.i("主播隔一段时间刷新状态关闭直播间" + json.toString());
                    break;
                case RequestCode.TIMERLIVE:

                    LogUtils.i("主播隔一段时间刷新状态" + json.toString());
                    break;

                case 1000:
                    LogUtils.i("主播隔一段时间刷新状态");
                    httpDatas.getDataDialog("主播隔一段时间刷新状态", false, urlBuilder.TimerLive(creatReadyBean.getId()), myHandler, RequestCode.TIMERLIVE);
                    break;
            }
        }
    };
    private UMSocialService mController;

    /**
     * 设置未处理点播消息数
     *
     * @author sll
     * @time 2016/12/9 15:55
     */
    private void setShowNum() {
        if (showNum > 0) {
            ivLiveDianBoNum.setVisibility(View.VISIBLE);
            ivLiveDianBoNum.setText(showNum + "");
        } else {
            ivLiveDianBoNum.setVisibility(View.GONE);
            ivLiveDianBoNum.setText(showNum + "");
        }
    }

    /**
     * 设置未处理连麦消息数
     *
     * @author sll
     * @time 2016/12/9 15:55
     */
    private void setLiveNum() {
        if (liveNums > 0) {
            ivLiveLianMaiNum.setVisibility(View.VISIBLE);
            ivLiveLianMaiNum.setText(liveNums + "");
        } else {
            ivLiveLianMaiNum.setVisibility(View.GONE);
            ivLiveLianMaiNum.setText(liveNums + "");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_startlive;
    }

    @Override
    protected void initListener() {
//        mCameraPreview.setZOrderOnTop(false);
//        mCameraPreview.setZOrderMediaOverlay(true);
        mVideoSurfaceView.setZOrderOnTop(true);
        mVideoSurfaceView.setZOrderMediaOverlay(true);

        liveJinpiao.setOnClickListener(this);
        liveClose.setOnClickListener(this);
        ivLiveBiaoyan.setOnClickListener(this);
        ivLiveDianbo.setOnClickListener(this);
        ivLiveLianmai.setOnClickListener(this);
        ivLiveMei.setOnClickListener(this);
        ivLiveSwitch.setOnClickListener(this);
        ivLiveShare.setOnClickListener(this);
        ivLivePrivatechat.setOnClickListener(this);
        startRoomJaiZu.setOnClickListener(this);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                RoomUserBean bean = mDatas.get(position);
                ifattention("请求用户信息", bean.getUserId(), RequestCode.REQUEST_USER_INFO);
            }
        });
        //点赞
        mRoot.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (sessionListFragment != null) {
                    sessionListFragment.hide();
                }
                if (liveMessageFragment != null) {
                    liveMessageFragment.hide();
                }
                if (messageFragment != null)
                    messageFragment.hideEditText();
                return false;
            }
        });
    }

    /**
     * 获取用户信息
     *
     * @param details     打印接口信息
     * @param liveid      查询用户的id
     * @param handlerCode
     */
    private void ifattention(String details, String liveid, int handlerCode) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("currentUserId", appUser.getId());
        map.put("userId", liveid);
        httpDatas.getDataForJson(details, com.android.volley.Request.Method.POST, UrlBuilder.IF_ATTENTION, map, myHandler, handlerCode);
    }

    Timer timer = new Timer();

    @Override
    protected void initialized() {

        mController = UMServiceFactory.getUMSocialService("com.umeng.share");
        share_url = "http://www.partylive.cn/Live/live.html?" + "userId=" + appUser.getId();
        startAnimation(3);
        getGiftList();
        showPopwindowDianBo();
        showPopwindowLianMai();
        creatReadyBean = (CreatReadyBean) getIntent().getSerializableExtra("START");

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                myHandler.sendEmptyMessage(1000);
            }
        }, 30000, 30000);

        um43Share();

        creatReadyBean.getId();
        roomId = creatReadyBean.getRoomId();

        PowerManager mPm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWl = mPm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "MyTag");
        requestPermission(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        try {
            initLivePlay(DESUtil.decrypt(creatReadyBean.getPublishUrl()));
            LogUtils.i("推流地址　：" + DESUtil.decrypt(creatReadyBean.getPublishUrl()));
            LogUtils.i("拉流地址　：" + DESUtil.decrypt(creatReadyBean.getBroadcastUrl()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        initPaly();

        liveHead.setAvatarUrl(creatReadyBean.getCreator().getPicUrl());


        if (creatReadyBean.getCreator().getNickName().length() > 3) {
            liveName.setText(creatReadyBean.getCreator().getNickName().substring(0, 3) + "...");
        } else {
            liveName.setText(creatReadyBean.getCreator().getNickName());
        }
        liveOnLineNums = Integer.parseInt(creatReadyBean.getOnlineUserNum());
        liveNum.setText(liveOnLineNums + "");
        liveId.setText("ID号:" + creatReadyBean.getCreator().getId());
        requestJinPiao();
        Sharedpreferences.setParam(this, "ZhuBoId", appUser.getId());
        Sharedpreferences.setParam(this, "isZhuBo", true);
        registerReceiverStartLiveActivity();
        // 注册监听
        registerObservers(true);
        //注册监听自定义通知
        registerReceiveCustom(true);
        // 登录聊天室
        enterRoom();


        initRecycle();

        //网易云信消息未读数
        registerMsgUnreadInfoObserver(true);
        registerSystemMessageObservers(true);
        requestSystemMessageUnreadCount();

        dialogForSelect = new Dialog(this, R.style.homedialog);
        initQuitDialog();
    }

    private void initPaly() {
        AspectFrameLayout afl = (AspectFrameLayout) findViewById(R.id.cameraPreview_afl);
        afl.setShowMode(AspectFrameLayout.SHOW_MODE.FULL);
        com.lvshandian.partylive.view.CameraPreviewFrameView cameraPreviewFrameView =
                (com.lvshandian.partylive.view.CameraPreviewFrameView) findViewById(R.id.cameraPreview_surfaceView);
        cameraPreviewFrameView.setListener(this);

        WatermarkSetting watermarksetting = new WatermarkSetting(this);
        watermarksetting.setResourceId(R.drawable.umeng_socialize_facebook)
                .setSize(WatermarkSetting.WATERMARK_SIZE.MEDIUM)
                .setAlpha(100)
                .setCustomPosition(0.5f, 0.5f);

        mMediaStreamingManager = new MediaStreamingManager(this, afl, cameraPreviewFrameView,
                AVCodecType.HW_VIDEO_WITH_HW_AUDIO_CODEC); // hw codec
        mMediaStreamingManager.prepare(mCameraStreamingSetting, mMicrophoneStreamingSetting, null, mProfile);
        mMediaStreamingManager.setStreamingStateListener(this);
        mMediaStreamingManager.setSurfaceTextureCallback(this);
        mMediaStreamingManager.setStreamingSessionListener(this);
        mMediaStreamingManager.setStreamStatusCallback(this);
        setFocusAreaIndicator();
    }

    private RotateLayout mRotateLayout;

    protected void setFocusAreaIndicator() {
        if (mRotateLayout == null) {
            mRotateLayout = (RotateLayout) findViewById(R.id.focus_indicator_rotate_layout);
            mMediaStreamingManager.setFocusAreaIndicator(mRotateLayout,
                    mRotateLayout.findViewById(R.id.focus_indicator));
        }
    }


    private final String share_title = "分享是真爱,你是我的菜\n";
    private String share_content = "";
    //    private final String share_url = "http://show.partylive.cn/";
    private String share_url = "";
    private String livePicUrl = "";

    private void um43Share() {

        mController.getConfig().closeToast();
        share_content = "分享是真爱,你是我的菜!" + creatReadyBean.getCreator().getNickName() + "正在直播,快来一起看~";
        livePicUrl = creatReadyBean.getLivePicUrl();
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(this, Constant.QQ_APPID, Constant.QQ_SECRET);
        qqSsoHandler.setTitle(share_title);
        qqSsoHandler.setTargetUrl(share_url);
        qqSsoHandler.addToSocialSDK();
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(this, Constant.QQ_APPID, Constant.QQ_SECRET);
        qZoneSsoHandler.setTargetUrl(share_url);
        qZoneSsoHandler.addToSocialSDK();

        /**
         * 微信
         */
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(this, Constant.WX_APPID, Constant.WX_SECRET);
        wxHandler.setTargetUrl(share_url);
        wxHandler.setTitle(share_title);
        wxHandler.addToSocialSDK();
        wxHandler.showCompressToast(false);
        // 添加微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(this, Constant.WX_APPID, Constant.WX_SECRET);

        wxCircleHandler.setTargetUrl(share_url);
        wxCircleHandler.setTitle(share_title);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
        wxCircleHandler.showCompressToast(false);

        SmsHandler smsHandler = new SmsHandler();
        smsHandler.setTargetUrl(share_url);
        smsHandler.addToSocialSDK();

        mController.setShareContent(share_content);
        mController.setShareImage(new UMImage(mContext, livePicUrl));
        mController.getConfig().removePlatform(SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN, SHARE_MEDIA.SINA, SHARE_MEDIA.TENCENT);
    }

    private DianBoDateAdapter dianBoAdapter;
    private LianMaiDateAdapter lianMaiAdapter;

    /**
     * 自定义通知接收
     *
     * @author sll
     * @time 2016/12/6 18:08
     */
    private void registerReceiveCustom(boolean register) {
        if (register)
            LogUtils.i("WangYi_CN", "接收自定义通知:开启");
        else
            LogUtils.i("WangYi_CN", "接收自定义通知:结束");
        MsgServiceObserve service = NIMClient.getService(MsgServiceObserve.class);
        service.observeCustomNotification(observer, register);
        service.observeReceiveMessage(incomingMessageObserver, register);
    }

    /**
     * 消息接收观察者
     */
    Observer<List<IMMessage>> incomingMessageObserver = new Observer<List<IMMessage>>() {
        @Override
        public void onEvent(List<IMMessage> messages) {
            if (messages == null || messages.isEmpty()) {
                return;
            }
            int unread = NIMClient.getService(MsgService.class).getTotalUnreadCount() + 1;
            LogUtils.i("WangYi", "消息接收观察者unread:" + unread);
            tabNewMsgLabel.setVisibility(unread > 0 ? View.VISIBLE : View.GONE);
            if (unread > 0) {
                tabNewMsgLabel.setText(String.valueOf(ReminderSettings.unreadMessageShowRule(unread)));
            }
        }
    };

    Observer<CustomNotification> observer = new Observer<CustomNotification>() {
        @Override
        public void onEvent(CustomNotification message) {
            // 在这里处理自定义通知。
            LogUtils.i("WangYi_CN", "Start接收自定义通知(SessionId):" + message.getSessionId());
            LogUtils.i("WangYi_CN", "Start接收自定义通知(fromAccount):" + message.getFromAccount());
            LogUtils.i("WangYi_CN", "Start接收自定义通知(SessionType):" + message.getSessionType());
            LogUtils.i("WangYi_CN", "Start接收自定义通知(ApnsText):" + message.getApnsText());
            LogUtils.i("WangYi_CN", "Start接收自定义通知(Content):" + message.getContent());
            if (message.getPushPayload() != null)
                LogUtils.i("WangYi_CN", "Start接收自定义通知(pushPayload):" + message.getPushPayload().toString());
            DianBoDateBean dianBoDateBean = JSON.parseObject(message.getContent(), DianBoDateBean.class);
            if (dianBoDateBean != null) {
                String type = dianBoDateBean.getType();
                if (type != null) {
                    if (type.equals(CustomNotificationType.IM_P2P_TYPE_ORDERSHOW)) {
                        //主播收到点播消息
                        SendRoomMessageUtils.sendMessageLocal("200", messageFragment, "miu_" + appUser.getId(), message.getApnsText(), message.getSessionId());
                        showNum++;
                        setShowNum();
                        httpDatas.getData("请求直播室点播列表", urlBuilder.roomVods(creatReadyBean.getId()), myHandler, RequestCode.REQUEST_ROOM_VODS);
                    } else if (type.equals(CustomNotificationType.IM_P2P_TYPE_SUBLIVE_PRIVATE)) {
                        //客户视频连线，私聊
                        SendRoomMessageUtils.sendMessageLocal("200", messageFragment, "miu_" + appUser.getId(), message.getApnsText(), message.getSessionId());
                        liveNums++;
                        setLiveNum();
                        httpDatas.getData("主播获取全部申请(连麦)列表", urlBuilder.roomLive(creatReadyBean.getId()), myHandler, RequestCode.REQUEST_ROOM_LIVE);
                    } else if (type.equals(CustomNotificationType.IM_P2P_TYPE_SUBLIVE_PUBLIC)) {
                        //客户视频连线，公聊
                        SendRoomMessageUtils.sendMessageLocal("200", messageFragment, "miu_" + appUser.getId(), message.getApnsText(), message.getSessionId());
                        liveNums++;
                        setLiveNum();
                        httpDatas.getData("主播获取全部申请(连麦)列表", urlBuilder.roomLive(creatReadyBean.getId()), myHandler, RequestCode.REQUEST_ROOM_LIVE);
                    }
                }
            }

        }
    };

    /*最上边观众头像列表开始*/

    private int page = 1;
    private boolean isRefresh = true;//是否是刷新的标记
    private List<RoomUserBean> mDatas = new ArrayList<>();
    private RoomUsersDataAdapter mAdapter;
    private GridLayoutManager mLayoutManager;

    /**
     * RecycleView初始
     */
    private void initRecycle() {
        mLayoutManager = new GridLayoutManager(mContext, 1, LinearLayoutManager.HORIZONTAL, false);
        rlvListLiveAudiences.setLayoutManager(mLayoutManager);
        mAdapter = new RoomUsersDataAdapter(mContext, mDatas);
        rlvListLiveAudiences.setAdapter(mAdapter);

        requestNet();
    }

    /**
     * 聊天室头像列表
     */
    private void requestNet() {
        String url = UrlBuilder.serverUrl + UrlBuilder.room;
        if (appUser != null) {
            url += creatReadyBean.getId();
            if (isRefresh) {
                //下拉刷新,从第一页开始，重新请求
                page = 1;
            } else {
                //上拉加载，从下一页开始加载
                page++;
            }
            url += "/users?pageNum=" + page;
            LogUtils.i("聊天室头像列表(url):" + url);
            OkHttpUtils.get().url(url).build().execute(new CustomStringCallBack(mContext, HttpDatas.KEY_CODE) {
                @Override
                public void onFaild() {
//                    showToast(isRefresh ? "刷新失败" : "加载失败");
//                    finshRefresh();
                }

                @Override
                public void onSucess(String data) {
                    LogUtils.i("聊天室头像列表(data):" + data);
                    RoomUserDataBean visitor = JsonUtil.json2Bean(data, RoomUserDataBean.class);
                    hanlderVisitors(visitor);
                }
            });
        }
    }

    /**
     * 取消刷新和加载
     */
    private void finshRefresh() {
    }

    /**
     * 直播观众头像列表
     *
     * @param visitor
     */
    private void hanlderVisitors(RoomUserDataBean visitor) {
        liveNum.setText(liveOnLineNums++ + "");
        if (visitor != null) {
            List<RoomUserBean> result = visitor.getResult();
            if (result != null) {
                if (isRefresh) {
                    //下拉刷新需要清除数据
                    mDatas.clear();
                }
                mDatas.addAll(result);
                mAdapter.notifyDataSetChanged();
            }
        }

        finshRefresh();
    }

    /*最上边观众头像列表结束*/


    @Override
    public void onBackPressed() {
        stopLive();
        super.onBackPressed();
    }

    /**
     * 请求金票数量
     */
    private void requestJinPiao() {
        String url = UrlBuilder.serverUrl + UrlBuilder.yqRen;
        if (creatReadyBean != null) {
            CreatReadyBean.CreatorBean creator = creatReadyBean.getCreator();
            if (creator != null) {
                String id = creator.getId();
                url += id;
                url += "/investors?pageNum=" + 1;
                OkHttpUtils.get().url(url).build().execute(new CustomStringCallBack(mContext, HttpDatas.KEY_CODE) {
                    @Override
                    public void onFaild() {

                    }

                    @Override
                    public void onSucess(String data) {

                        LogUtils.e("贡献榜: " + data);
                        GongXianData gongXian = JsonUtil.json2Bean(data, GongXianData.class);
                        if (gongXian != null) {
                            List<GongXianBean> result = gongXian.getResult();
                            if (result != null) {
                                int total = 0;
                                int size = result.size();
                                for (int i = 0; i < size; i++) {
                                    GongXianBean gongXianBean = result.get(i);
                                    String investment = gongXianBean.getInvestment();
                                    total += Integer.valueOf(investment);
                                }
                                liveJinpiao.setText(total + "");
                                LogUtils.e("贡献榜金币: " + total);
                            }
                        }
                    }
                });
            }
        }
    }

    /**
     * 确认退出对话框
     */
    private void initQuitDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_quit_login, null);
        mQuitDialog = new RoundDialog(this, view, R.style.dialog, 0.66f, 0.2f);
        mQuitDialog.setCanceledOnTouchOutside(false);

        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_cancel);
        TextView tvSure = (TextView) view.findViewById(R.id.tv_sure);
        tvTitle.setText("确定结束直播么？");
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mQuitDialog != null && mQuitDialog.isShowing()) {
                    mQuitDialog.dismiss();
                }
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mQuitDialog != null && mQuitDialog.isShowing()) {
                    mQuitDialog.dismiss();
                }
                stopLive();
                videoPlayEnd();
                timer.cancel();
                finish();
            }
        });
    }

    private RoundDialog mQuitDialog;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //排行榜
            case R.id.live_jinpiao:
                Intent intent = new Intent(this, GongXianActivity.class);
                intent.putExtra(getString(R.string.user_id), creatReadyBean.getCreator().getId());
                startActivity(intent);
                break;
            //关闭直播
            case R.id.live_close:

                //退出登录
                if (mQuitDialog != null && !mQuitDialog.isShowing()) {
                    mQuitDialog.show();
                }
                break;
            //美颜
            case R.id.iv_live_mei:

                if (!mHandler.hasMessages(MSG_FB)) {
                    mHandler.sendEmptyMessage(MSG_FB);
                }
                break;
            //私信
            case R.id.iv_live_privatechat:
                /**
                 * 2
                 */
                sessionListFragment = new ChatRoomSessionListFragment();
                sessionListFragment.init(getSupportFragmentManager());
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.watch_room_message_fragment, sessionListFragment);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            //点播
            case R.id.iv_live_dianbo:
                /**
                 * 1
                 */
//                if (null != ksyMediaPlayer && !ksyMediaPlayer.isPlaying()) {
//                    Toast.makeText(StartLiveActivity.this, "目前还没有直播", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                if (dianBoDateList != null && dianBoDateList.size() > 0) {
                    showNum = dianBoDateList.size();
                    setShowNum();
                    dianBoAdapter.setmDatas(dianBoDateList);
                    popupWindow_dianbo.showAtLocation(contentView, Gravity.BOTTOM, 0, 0);
                } else
                    ToastUtils.showSnackBar(ivLiveDianbo, "暂无点播数据");
                break;
            //连麦
            case R.id.iv_live_lianmai:
//                lianmailaliu("http://pull7.a8.com/live/1481854796112023.flv");


                break;
            //摄像头旋转
            case R.id.iv_live_switch:
                mHandler.removeCallbacks(mSwitcher);
                mHandler.postDelayed(mSwitcher, 100);
                break;
            //增加表演
            case R.id.iv_live_biaoyan:
                if (creatShow) {
                    initShowDialog();
                } else {
                    showSurePlayDialog();
                }
                break;
            //分享
            case R.id.iv_live_share:
                mController.openShare(this, false);
                break;
            case R.id.start_room_jaiZu:
                //家族
                getFamilyMember();
        }
    }

    /**
     * 家族
     * userId 主播ID
     *
     * @author sll
     * @time 2016/12/22 16:11
     */
    private void getFamilyMember() {
        String url = UrlBuilder.chargeServerUrl + UrlBuilder.selectFamilyMember;
        LogUtils.i("家族url: " + url);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("userId", creatReadyBean.getCreator().getId());
        String json = new org.json.JSONObject(hashMap).toString();
        LogUtils.i("家族Json:　" + json);
        OkHttpUtils.get().url(url)
                .params(hashMap)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(com.squareup.okhttp.Request request, Exception e) {
                        LogUtils.i("家族onError: " + e.toString());
                    }

                    @Override
                    public void onResponse(String response) {
                        LogUtils.i("家族onResponse: " + response);
                        LiveFamilyMemberBean liveFamilyMember = JsonUtil.json2Bean(response, LiveFamilyMemberBean.class);
                        if (liveFamilyMember != null && liveFamilyMember.getObj() != null && liveFamilyMember.getObj().size() > 0)
                            showFamilyList(liveFamilyMember);
                        else
                            showToast("该用户尚未加入家族或家族成员未开播");
                    }
                });
    }

    /**
     * 展示家族列表
     *
     * @author sll
     * @time 2016/12/23 11:22
     */
    private void showFamilyList(LiveFamilyMemberBean liveFamilyMember) {
        final List<LiveBean> mList = new ArrayList<>();
        for (LiveFamilyMemberBean.ObjBean o : liveFamilyMember.getObj()) {
            if (o.getRooms() != null)
                mList.add(ChannelToLiveBean.getLiveBeanFromObj(o));
        }
        BottomView familySelectView = new BottomView(this, R.style.BottomViewTheme_Transparent, R.layout.view_show_familyr_members);
        familySelectView.setAnimation(R.style.BottomToTopAnim);
        familySelectView.showBottomView(true);

        View mFamilyView = familySelectView.getView();
        RefreshRecyclerView familyMembers = (RefreshRecyclerView) mFamilyView.findViewById(R.id.view_family_members_recycler);
        FamilyMemberAdapter adapter = new FamilyMemberAdapter(0, this, mList, creatReadyBean.getCreator().getId());
        GridLayoutManager mLayoutManager = new GridLayoutManager(StartLiveActivity.this, 2, GridLayoutManager.HORIZONTAL, false);

        familyMembers.setLayoutManager(mLayoutManager);
        familyMembers.setAdapter(adapter);

        RecyclerViewManager.with(adapter, mLayoutManager)
                .setMode(RecyclerMode.NONE)
                .into(familyMembers, this);
    }

    /**
     * 确认开始表演对话框
     */
    private void showSurePlayDialog() {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.dialog_sure_buy_vip, null);
        final RoundDialog mSureDialog = new RoundDialog(this, inflate);

        TextView tvTitle = (TextView) inflate.findViewById(R.id.tv_title);
        TextView tvContent = (TextView) inflate.findViewById(R.id.tv_content);
        TextView tvCancel = (TextView) inflate.findViewById(R.id.tv_cancel);
        TextView tvSure = (TextView) inflate.findViewById(R.id.tv_sure);

        tvTitle.setText("表演");
        tvContent.setText("现在开始表演吗?");
        tvCancel.setText("等会");
        tvSure.setText("开始");
        inflate.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSureDialog != null && mSureDialog.isShowing()) {
                    mSureDialog.dismiss();
                }
            }
        });
        inflate.findViewById(R.id.tv_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSureDialog != null && mSureDialog.isShowing()) {
                    mSureDialog.dismiss();
                }

                startShow();

            }
        });
        mSureDialog.show();
    }

    /**
     * 开始表演
     */

    /*主播开始表演
        http://miulive.cc:8080/api/v1/room/show/{id}/start get请求 其中id为表演id
        发送：无
        接收：{
        "code": "0",
        "message": "成功",
        "data": "true"
        }
    */
    private void startShow() {

        if (mShowBean != null) {
            String id = mShowBean.getId();
            String url = UrlBuilder.serverUrl + UrlBuilder.roomShow + "/" + id + "/start";
            LogUtils.e("开始表演: " + url);

            OkHttpUtils.get().url(url)
                    .build().execute(new CustomStringCallBack(mContext, HttpDatas.KEY_CODE) {
                @Override
                public void onFaild() {
                    LogUtils.e("开始表演失败");
                }

                @Override
                public void onSucess(String data) {
                    LogUtils.e("开始表演: " + data);
                    endShowDialog();
                }
            });
        }

    }

    /**
     * 表演结束对话框
     */
    private void endShowDialog() {
        final Dialog dialog = new Dialog(this, R.style.dialog);
        View view = View.inflate(this, R.layout.pop_dianbo_start, null);
        view.findViewById(R.id.pop_show_end).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }

                endShow();
            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.show();
    }

    /**
     * 结束表演
     */
    /*5.主播结束表演
        http://miulive.cc:8080/api/v1/room/show/{id}/end get请求 其中id为表演id
        发送：无
        接收：{
        "code": "0",
        "message": "成功",
        "data": "true"
        }
   */
    private void endShow() {
        if (mShowBean != null) {
            String id = mShowBean.getId();
            String url = UrlBuilder.serverUrl + UrlBuilder.roomShow + "/" + id + "/end";
            LogUtils.e("结束表演: " + url);

            OkHttpUtils.get().url(url)
                    .build().execute(new CustomStringCallBack(mContext, HttpDatas.KEY_CODE) {
                @Override
                public void onFaild() {
                    LogUtils.e("结束表演失败");
                }

                @Override
                public void onSucess(String data) {
                    LogUtils.e("结束表演: " + data);
                    //endShowDialog();
                    ivLiveBiaoyan.setImageResource(R.mipmap.mg_room_btn_add);
                    creatShow = true;
                }
            });
        }
    }

    private boolean creatShow = true;

    /**
     * 准备表演对话框
     */
    private void initShowDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.add_gjz_dialog_view, null);

        TextView tvTitle = (TextView) view.findViewById(R.id.context);
        final EditText etBy = (EditText) view.findViewById(R.id.et_by);
        final EditText etJb = (EditText) view.findViewById(R.id.et_jb);
        TextView btnSure = (TextView) view.findViewById(R.id.queren);
        final RoundDialog mDialog = new RoundDialog(this, view, R.style.dialog, 0.75f, 0.5f);
        tvTitle.setText("增加表演");
        etBy.setHint("给表演起个好听的名字");
        etJb.setHint("输入观看表演需要支付的金币");
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }

                prapareShow(etBy.getText().toString().trim(), etJb.getText().toString().trim());
            }
        });

        view.findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
            }
        });
        mDialog.show();
    }

    /**
     * 准备表演
     */

    /*1.新建表演
        http://miulive.cc:8080/api/v1/room/show post提交
        发送：{ "userId":"1","roomId":"2","name":"表演名称","cost":"100"}
        userId为用户id，roomId为当前直播间id，name为表演名称，cost为观看表演需要支付金币数
        接收：{
        "code": "0",
        "message": "成功",
        "data": "true"
        }
    */
    private void prapareShow(String name, String cost) {
        if (TextUtils.isEmpty(name)) {
            showToast("请输入表演名称");
            return;
        }

        if (TextUtils.isEmpty(cost)) {
            showToast("请输入观看表演需要支付的金币");
            return;
        }

        Map params = new HashMap<String, String>();
        params.put("userId", appUser.getId());
        params.put("roomId", creatReadyBean.getId());
        params.put("name", name);
        params.put("cost", cost);

        String json = new JSONObject(params).toString();
        String url = UrlBuilder.serverUrl + UrlBuilder.roomShow;
        LogUtils.e("新建表演: " + url);
        LogUtils.e("新建表演json: " + json);
        OkHttpUtils.postString().url(url)
                .content(json)
                .mediaType(MediaType.parse("application/json"))
                .build().execute(new CustomStringCallBack(mContext, HttpDatas.KEY_CODE) {
            @Override
            public void onFaild() {
                SoftKeyBoardUtils.hiddenKeyBoard(getWindow().getDecorView());
            }

            @Override
            public void onSucess(String data) {
                LogUtils.e("新建表演: " + data);

                mShowBean = JsonUtil.json2Bean(data, CustomShowBean.class);

                SoftKeyBoardUtils.hiddenKeyBoard(getWindow().getDecorView());
                ivLiveBiaoyan.setImageResource(R.mipmap.play);
                creatShow = false;
            }
        });
    }

    private CustomShowBean mShowBean;

    //点播列表
    private PopupWindow popupWindow_dianbo;
    //连麦列表
    private PopupWindow popupWindow_lianMai;
    private View contentView;
    //表演时弹框
    private PopupWindow showStartPop;
    private View showStartPopView;

    /**
     * 显示popupWindow_dianbo
     */
    private void showPopwindowDianBo() {
        //加载弹出框的布局
        contentView = LayoutInflater.from(StartLiveActivity.this).inflate(R.layout.pop_dianbo, null);
        dianBoDateList = new ArrayList<>();
        dianBoAdapter = new DianBoDateAdapter(this, dianBoDateList, R.layout.item_pop_dianbo);
        //绑定控件
        ListView listView = (ListView) contentView.findViewById(R.id.pop_dianbo_list);
        listView.setAdapter(dianBoAdapter);

        //设置弹出框的宽度和高度
        popupWindow_dianbo = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow_dianbo.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        popupWindow_dianbo.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        popupWindow_dianbo.setOutsideTouchable(true);
        //设置可以点击
        popupWindow_dianbo.setTouchable(true);
        //进入退出的动画
        popupWindow_dianbo.setAnimationStyle(R.style.mypopwindow_anim_style);

        // 按下android回退物理键 PopipWindow消失解决
        listView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                    if (popupWindow_dianbo != null && popupWindow_dianbo.isShowing()) {
                        popupWindow_dianbo.dismiss();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    /**
     * popupWindow_lianMai
     */
    private void showPopwindowLianMai() {
        //加载弹出框的布局
        contentView = LayoutInflater.from(StartLiveActivity.this).inflate(
                R.layout.pop_dianbo, null);
        lianMaiDateList = new ArrayList<>();
        lianMaiAdapter = new LianMaiDateAdapter(this, lianMaiDateList, R.layout.item_pop_dianbo);
        //绑定控件
        ListView listView = (ListView) contentView.findViewById(R.id.pop_dianbo_list);
        listView.setAdapter(lianMaiAdapter);

        //设置弹出框的宽度和高度
        popupWindow_lianMai = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow_lianMai.setFocusable(true);// 取得焦点
        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        popupWindow_lianMai.setBackgroundDrawable(new BitmapDrawable());
        //点击外部消失
        popupWindow_lianMai.setOutsideTouchable(true);
        //设置可以点击
        popupWindow_lianMai.setTouchable(true);
        //进入退出的动画
        popupWindow_lianMai.setAnimationStyle(R.style.mypopwindow_anim_style);

        // 按下android回退物理键 PopipWindow消失解决
        listView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                    if (popupWindow_lianMai != null && popupWindow_lianMai.isShowing()) {
                        popupWindow_lianMai.dismiss();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    /**
     * 开始表演，弹出结束表演的框
     *
     * @author sll
     * @time 2016/12/8 18:18
     */
    private void showPopBiaoYan() {
        //加载弹出框的布局
        final Dialog dialog = new Dialog(this, R.style.dialog);
        View view = View.inflate(this, R.layout.pop_dianbo_start, null);
        view.findViewById(R.id.pop_show_end).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }

                //结束表演，调结束接口
                if (showStartPop != null && showStartPop.isShowing())
                    showStartPop.dismiss();
                //直播结束接口
                httpDatas.getData("主播结束表演", UrlBuilder.roomVodEnd(vodId), myHandler, RequestCode.REQUEST_ROOM_VOD_END);
                sendCustomNotification("miu_" + sessionId, "主播结束表演", "2", CustomNotificationType.IM_P2P_TYPE_ORDERSHOW_END);

            }
        });
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.show();
    }

    /**
     * @dw 获取礼物列表
     */
    private void getGiftList() {

        httpDatas.getDatanoloading("礼物列表", com.android.volley.Request.Method.GET, UrlBuilder.GET_GIFT, null, myHandler, RequestCode.GET_GIFT);

    }

    public void registerReceiverStartLiveActivity() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("WatchLiveActivity");
        registerReceiver(broadcastReceiver, intentFilter);
    }

    /**
     * 广播接收，收到网易云信收到或发出的自定义消息
     *
     * @author sll
     * @time 2016/11/1 17:44
     */
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {


        @Override
        public void onReceive(Context context, Intent intent) {

            String userId = intent.getStringExtra("FromAccount");
            LogUtils.i("WangYi_gift", "userId:" + userId);
//            customGiftBean = (CustomGiftBean) intent.getSerializableExtra("CustomGiftBean");
            message = (ChatRoomMessage) intent.getSerializableExtra("ChatRoomMessage");
            Map<String, Object> remote = null;
            if (message != null) {
                remote = message.getRemoteExtension();
                LogUtil.i("WangYi_gift", "收到礼物广播message != null" + message.getSessionId());
            }
            if (message != null && remote != null && !TextUtils.isEmpty((String) remote.get("type"))) {
                int type = Integer.parseInt((String) remote.get("type"));
                switch (type) {
                    case 105:
                        requestNet();
                        break;
                    case 107:
                        //点亮
                        break;
                    case 108:
                        //点赞
//
                        break;
                    case 109:
                        //收到 109 礼物消息 发送过来广播
                        CustomGiftBean customGiftBean = JavaBeanMapUtils.mapToBean(remote, CustomGiftBean.class);
                        if (Integer.parseInt(customGiftBean.getGift_item_number()) < 1) {
                            return;
                        }
                        final UserInfoProvider.UserInfo userInfo = NimUIKit.getUserInfoProvider().getUserInfo(message.getFromAccount());
                        String url = userInfo != null ? userInfo.getAvatar() : null;
                        String name;
                        if (mGiftList == null) {
                            LogUtils.i("礼物列表空");
                            return;
                        }

                        if (message.getChatRoomMessageExtension() == null) {
                            name = appUser.getNickName();
                        } else {
                            name = message.getChatRoomMessageExtension().getSenderNick();
                        }
                        liveJinpiao.setText((Integer.parseInt(liveJinpiao.getText().toString()) + ((Integer.parseInt(customGiftBean.getGift_item_number()) * Integer.parseInt(customGiftBean.getGift_coinnumber_index())))) + "");

                        if (message.getChatRoomMessageExtension() == null) {
                            name = appUser.getNickName();
                        } else {
                            name = message.getChatRoomMessageExtension().getSenderNick();
                        }
                        GiftSendModel giftSendModel = new GiftSendModel(Integer.parseInt(customGiftBean.getGift_item_number()) - 1, url, name, customGiftBean.getGift_item_message(), mGiftList.get(Integer.parseInt(customGiftBean.getGift_item_index()) - 1).getStaticIcon());
                        if (customGiftBean.getGift_item_index().equals("7")) {//烟花礼物
                            mLuxuryGiftFireworksShowQueue.add(new SendGiftBean());
                            showFireworksAnimation(new SendGiftBean());//散花
                        } else if (customGiftBean.getGift_item_index().equals("8")) {//游轮礼物
                            mLuxuryGiftCruisesShowQueue.add(new SendGiftBean());
                            showCruisesAnimation(new SendGiftBean());
                        } else if (customGiftBean.getGift_item_index().equals("15")) {//红色小轿车
                            mLuxuryGiftCarShowQueue.add(new SendGiftBean());
                            showRedCarAnimation(new SendGiftBean());
                        } else if (customGiftBean.getGift_item_index().equals("22")) {//飞机礼物
                            mLuxuryGiftPlainShowQueue.add(new SendGiftBean());
                            showPlainAnimation(new SendGiftBean());//飞机
                        } else {
                            starGiftAnimation(giftSendModel);
                        }
//                        if (customGiftBean.getGift_item_index().equals("1")) {//烟花礼物
//                            mLuxuryGiftFireworksShowQueue.add(new SendGiftBean());
//                            showFireworksAnimation(new SendGiftBean());//散花
//                        } else if (customGiftBean.getGift_item_index().equals("9")) {//游轮礼物
//                            mLuxuryGiftCruisesShowQueue.add(new SendGiftBean());
//                            showCruisesAnimation(new SendGiftBean());
//                        } else if (customGiftBean.getGift_item_index().equals("3")) {//红色小轿车
//                            mLuxuryGiftCarShowQueue.add(new SendGiftBean());
//                            showRedCarAnimation(new SendGiftBean());
//                        } else if (customGiftBean.getGift_item_index().equals("2")) {//飞机礼物
//                            mLuxuryGiftPlainShowQueue.add(new SendGiftBean());
//                            showPlainAnimation(new SendGiftBean());//飞机
//                        } else {
//                            starGiftAnimation(giftSendModel);
//                            giftSendModel = null;
//                        }

                        break;

                    default:
                        break;
                }
                LogUtil.i("WangYi_gift", "聊天室，发礼物广播:收到礼物消息" + remote.get("gift_item_message"));
            } else if (message != null && remote != null && !TextUtils.isEmpty((String) remote.get("danmu")) && remote.get("danmu").equals("true")) {
                final UserInfoProvider.UserInfo userInfo = NimUIKit.getUserInfoProvider().getUserInfo(message.getFromAccount());
                String url = userInfo != null ? userInfo.getAvatar() : null;
//                ArrayList<BarrageDateBean> data = new ArrayList();
                BarrageDateBean barrageDateBean = new BarrageDateBean();
                barrageDateBean.setContent(message.getContent());
                if (message.getChatRoomMessageExtension() != null) {
                    barrageDateBean.setNickName("@" + message.getChatRoomMessageExtension().getSenderNick());
                } else {
                    barrageDateBean.setNickName("@" + appUser.getNickName());
                }
                barrageDateBean.setPicUrl(url);
//                data.add(barrageDateBean);
                barrageview.addSentence(barrageDateBean);
                if (message.getFromAccount().equals("miu_" + appUser.getId())) {
//                    liveJinpiao.setText((Integer.parseInt(appUser.getGoldCoin()) - 1) + "");
                    appUser.setGoldCoin((Integer.parseInt(appUser.getGoldCoin()) - 1) + "");
                    CacheUtils.saveObject(StartLiveActivity.this, appUser, CacheUtils.USERINFO);
                }
            } else if (userId != null && userId.indexOf("miu_") != -1) {
                userId = userId.substring(4);
                ifattention("请求用户信息", userId, RequestCode.REQUEST_USER_INFO);
            }

        }
    };


    private void starGiftAnimation(GiftSendModel model) {
        if (!giftFrameLayout1.isShowing()) {
            sendGiftAnimation(giftFrameLayout1, model);
        } else if (!giftFrameLayout2.isShowing()) {
            sendGiftAnimation(giftFrameLayout2, model);
        } else {
            giftSendModelList.add(model);
        }
    }

    private void sendGiftAnimation(final GiftFrameLayout view, GiftSendModel model) {
        view.setModel(model);
        AnimatorSet animatorSet = view.startAnimation(model.getGiftCount());
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                synchronized (giftSendModelList) {
                    if (giftSendModelList.size() > 0) {
                        view.startAnimation(giftSendModelList.get(giftSendModelList.size() - 1).getGiftCount());
                        giftSendModelList.remove(giftSendModelList.size() - 1);
                    }
                }
            }
        });
    }

    //七牛
    protected Button mShutterButton;
    private Button mMuteButton;
    private Button mTorchBtn;
    private Button mCameraSwitchBtn;
    private Button mCaptureFrameBtn;
    private Button mEncodingOrientationSwitcherBtn;
    private Button mFaceBeautyBtn;
    protected TextView mStatusTextView;
    protected MediaStreamingManager mMediaStreamingManager;
    private static final int ZOOM_MINIMUM_WAIT_MILLIS = 33; //ms
    private boolean mIsTorchOn = false;
    private boolean mIsNeedMute = false;
    private boolean mIsNeedFB = false;
    private boolean mIsEncOrientationPort = true;
    private boolean mIsPreviewMirror = false;
    protected TextView mLogTextView;
    protected TextView mStatView;
    private Switcher mSwitcher = new Switcher();
    private boolean mIsEncodingMirror = false;
    private static final int MSG_START_STREAMING = 0;
    private static final int MSG_STOP_STREAMING = 1;
    private static final int MSG_SET_ZOOM = 2;
    private static final int MSG_MUTE = 3;
    private static final int MSG_FB = 4;
    private static final int MSG_PREVIEW_MIRROR = 5;
    private static final int MSG_ENCODING_MIRROR = 6;
    protected boolean mShutterButtonPressed = false;
    private int mCurrentZoom = 0;
    private boolean mOrientationChanged = false;
    private int mMaxZoom = 0;
    private ScreenShooter mScreenShooter = new ScreenShooter();
    private EncodingOrientationSwitcher mEncodingOrientationSwitcher = new EncodingOrientationSwitcher();

    private class EncodingOrientationSwitcher implements Runnable {

        @Override
        public void run() {
            Log.i(TAG, "mIsEncOrientationPort:" + mIsEncOrientationPort);
            stopStreaming();
            mOrientationChanged = !mOrientationChanged;
            mIsEncOrientationPort = !mIsEncOrientationPort;
            mProfile.setEncodingOrientation(mIsEncOrientationPort ? StreamingProfile.ENCODING_ORIENTATION.PORT : StreamingProfile.ENCODING_ORIENTATION.LAND);
            mMediaStreamingManager.setStreamingProfile(mProfile);
            setRequestedOrientation(mIsEncOrientationPort ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT : ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            mMediaStreamingManager.notifyActivityOrientationChanged();
            updateOrientationBtnText();
//            Toast.makeText(StartLiveActivity.this, Config.HINT_ENCODING_ORIENTATION_CHANGED,
//                    Toast.LENGTH_SHORT).show();
            Log.i(TAG, "EncodingOrientationSwitcher -");
        }
    }

    private class ScreenShooter implements Runnable {
        @Override
        public void run() {
            final String fileName = "PLStreaming_" + System.currentTimeMillis() + ".jpg";
            mMediaStreamingManager.captureFrame(100, 100, new FrameCapturedCallback() {
                private Bitmap bitmap;

                @Override
                public void onFrameCaptured(Bitmap bmp) {
                    if (bmp == null) {
                        return;
                    }
                    bitmap = bmp;
                }
            });
        }
    }

    private class Switcher implements Runnable {
        @Override
        public void run() {
            mCurrentCamFacingIndex = (mCurrentCamFacingIndex + 1) % CameraStreamingSetting.getNumberOfCameras();

            CameraStreamingSetting.CAMERA_FACING_ID facingId;
            if (mCurrentCamFacingIndex == CameraStreamingSetting.CAMERA_FACING_ID.CAMERA_FACING_BACK.ordinal()) {
                facingId = CameraStreamingSetting.CAMERA_FACING_ID.CAMERA_FACING_BACK;
            } else if (mCurrentCamFacingIndex == CameraStreamingSetting.CAMERA_FACING_ID.CAMERA_FACING_FRONT.ordinal()) {
                facingId = CameraStreamingSetting.CAMERA_FACING_ID.CAMERA_FACING_FRONT;
            } else {
                facingId = CameraStreamingSetting.CAMERA_FACING_ID.CAMERA_FACING_3RD;
            }
            Log.i(TAG, "switchCamera:" + facingId);
            mMediaStreamingManager.switchCamera(facingId);
        }
    }

    protected Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_START_STREAMING:
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // disable the shutter button before startStreaming
                            setShutterButtonEnabled(false);
                            boolean res = mMediaStreamingManager.startStreaming();
                            mShutterButtonPressed = true;
                            Log.i(TAG, "res:" + res);
                            if (!res) {
                                mShutterButtonPressed = false;
                                setShutterButtonEnabled(true);
                            }
                            setShutterButtonPressed(mShutterButtonPressed);
                        }
                    }).start();
                    break;
                case MSG_STOP_STREAMING:
                    if (mShutterButtonPressed) {
                        // disable the shutter button before stopStreaming
                        setShutterButtonEnabled(false);
                        boolean res = mMediaStreamingManager.stopStreaming();
                        if (!res) {
                            mShutterButtonPressed = true;
                            setShutterButtonEnabled(true);
                        }
                        setShutterButtonPressed(mShutterButtonPressed);
                    }
                    break;
                case MSG_SET_ZOOM:
                    mMediaStreamingManager.setZoomValue(mCurrentZoom);
                    break;
                case MSG_MUTE:
                    mIsNeedMute = !mIsNeedMute;
                    mMediaStreamingManager.mute(mIsNeedMute);
                    updateMuteButtonText();
                    break;
                case MSG_FB:
                    mIsNeedFB = !mIsNeedFB;
                    mMediaStreamingManager.setVideoFilterType(mIsNeedFB ?
                            CameraStreamingSetting.VIDEO_FILTER_TYPE.VIDEO_FILTER_BEAUTY
                            : CameraStreamingSetting.VIDEO_FILTER_TYPE.VIDEO_FILTER_NONE);
                    updateFBButtonText();
                    break;
                case MSG_PREVIEW_MIRROR:
                    mIsPreviewMirror = !mIsPreviewMirror;
                    mMediaStreamingManager.setPreviewMirror(mIsPreviewMirror);
                    Toast.makeText(mContext, "镜像成功", Toast.LENGTH_SHORT).show();
                    break;
                case MSG_ENCODING_MIRROR:
                    mIsEncodingMirror = !mIsEncodingMirror;
                    mMediaStreamingManager.setEncodingMirror(mIsEncodingMirror);
                    Toast.makeText(mContext, "镜像成功", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Log.e(TAG, "Invalid message");
                    break;
            }
        }
    };

    private void updateCameraSwitcherButtonText(int camId) {
        if (mCameraSwitchBtn == null) {
            return;
        }
        if (camId == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            mCameraSwitchBtn.setText("Back");
        } else {
            mCameraSwitchBtn.setText("Front");
        }
    }

    private void initButtonText() {
        updateFBButtonText();
        updateCameraSwitcherButtonText(mCameraStreamingSetting.getReqCameraId());
        mCaptureFrameBtn.setText("Capture");
        updateFBButtonText();
        updateMuteButtonText();
        updateOrientationBtnText();
    }

    private void updateOrientationBtnText() {
        if (mIsEncOrientationPort) {
            mEncodingOrientationSwitcherBtn.setText("Land");
        } else {
            mEncodingOrientationSwitcherBtn.setText("Port");
        }
    }

    private void updateFBButtonText() {
        if (mFaceBeautyBtn != null) {
            mFaceBeautyBtn.setText(mIsNeedFB ? "FB Off" : "FB On");
        }
    }

    private void updateMuteButtonText() {
        if (mMuteButton != null) {
            mMuteButton.setText(mIsNeedMute ? "Unmute" : "Mute");
        }
    }

    protected void setShutterButtonPressed(final boolean pressed) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mShutterButtonPressed = pressed;
                mShutterButton.setPressed(pressed);
            }
        });
    }

    protected void setShutterButtonEnabled(final boolean enable) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mShutterButton.setFocusable(enable);
                mShutterButton.setClickable(enable);
                mShutterButton.setEnabled(enable);
            }
        });
    }

    /**
     * @dw 初始化直播播放器
     */
    private void initLivePlay(String tuiliu) {

        mProfile = new StreamingProfile();

        try {

            mProfile.setPublishUrl(tuiliu);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        StreamingProfile.AudioProfile aProfile = new StreamingProfile.AudioProfile(44100, 96 * 1024);
        StreamingProfile.VideoProfile vProfile = new StreamingProfile.VideoProfile(30, 1000 * 1024, 48);
        StreamingProfile.AVProfile avProfile = new StreamingProfile.AVProfile(vProfile, aProfile);

        mProfile.setVideoQuality(StreamingProfile.VIDEO_QUALITY_MEDIUM2)
                .setAudioQuality(StreamingProfile.AUDIO_QUALITY_MEDIUM2)
//                .setAVProfile(avProfile)
//                .setPreferredVideoEncodingSize(960, 544)
                .setEncodingSizeLevel(Config.ENCODING_LEVEL)
                .setEncoderRCMode(StreamingProfile.EncoderRCModes.BITRATE_PRIORITY)
                .setDnsManager(getMyDnsManager())
                .setAdaptiveBitrateEnable(true)
                .setFpsControllerEnable(true)
                .setStreamStatusConfig(new StreamingProfile.StreamStatusConfig(3))
//                .setEncodingOrientation(StreamingProfile.ENCODING_ORIENTATION.PORT)
                .setSendingBufferProfile(new StreamingProfile.SendingBufferProfile(0.2f, 0.8f, 3.0f, 20 * 1000));

        CameraStreamingSetting.CAMERA_FACING_ID cameraFacingId = chooseCameraFacingId();
        mCurrentCamFacingIndex = cameraFacingId.ordinal();
        mCameraStreamingSetting = new CameraStreamingSetting();
        mCameraStreamingSetting.setCameraId(Camera.CameraInfo.CAMERA_FACING_BACK)
                .setContinuousFocusModeEnabled(true)
                .setRecordingHint(false)
                .setCameraFacingId(cameraFacingId)
//                .setCameraSourceImproved(true)
                .setResetTouchFocusDelayInMs(3000)
//                .setFocusMode(CameraStreamingSetting.FOCUS_MODE_CONTINUOUS_PICTURE)
                .setCameraPrvSizeLevel(CameraStreamingSetting.PREVIEW_SIZE_LEVEL.MEDIUM)
                .setCameraPrvSizeRatio(CameraStreamingSetting.PREVIEW_SIZE_RATIO.RATIO_16_9)
                .setBuiltInFaceBeautyEnabled(true)
                .setFaceBeautySetting(new CameraStreamingSetting.FaceBeautySetting(1.0f, 1.0f, 0.8f))
                .setVideoFilter(CameraStreamingSetting.VIDEO_FILTER_TYPE.VIDEO_FILTER_BEAUTY);

        mIsNeedFB = true;
        mMicrophoneStreamingSetting = new MicrophoneStreamingSetting();
        mMicrophoneStreamingSetting.setBluetoothSCOEnabled(false);

        initUIs();
        push(creatReadyBean.getId());
    }


    private CameraStreamingSetting.CAMERA_FACING_ID chooseCameraFacingId() {
        if (CameraStreamingSetting.hasCameraFacing(CameraStreamingSetting.CAMERA_FACING_ID.CAMERA_FACING_3RD)) {
            return CameraStreamingSetting.CAMERA_FACING_ID.CAMERA_FACING_3RD;
        } else if (CameraStreamingSetting.hasCameraFacing(CameraStreamingSetting.CAMERA_FACING_ID.CAMERA_FACING_FRONT)) {
            return CameraStreamingSetting.CAMERA_FACING_ID.CAMERA_FACING_FRONT;
        } else {
            return CameraStreamingSetting.CAMERA_FACING_ID.CAMERA_FACING_BACK;
        }
    }

    private static DnsManager getMyDnsManager() {
        IResolver r0 = new DnspodFree();
        IResolver r1 = AndroidDnsServer.defaultResolver();
        IResolver r2 = null;
        try {
            r2 = new Resolver(InetAddress.getByName("119.29.29.29"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return new DnsManager(NetworkInfo.normal, new IResolver[]{r0, r1, r2});
    }

    private void initUIs() {
        View rootView = findViewById(R.id.content);
        rootView.addOnLayoutChangeListener(this);
        mMuteButton = (Button) findViewById(R.id.mute_btn);
        mShutterButton = (Button) findViewById(R.id.toggleRecording_button);
        mTorchBtn = (Button) findViewById(R.id.torch_btn);
        mCameraSwitchBtn = (Button) findViewById(R.id.camera_switch_btn);
        mCaptureFrameBtn = (Button) findViewById(R.id.capture_btn);
        mFaceBeautyBtn = (Button) findViewById(R.id.fb_btn);

        mStatusTextView = (TextView) findViewById(R.id.streamingStatus);
        Button previewMirrorBtn = (Button) findViewById(R.id.preview_mirror_btn);
        Button encodingMirrorBtn = (Button) findViewById(R.id.encoding_mirror_btn);

        mLogTextView = (TextView) findViewById(R.id.log_info);
        mStatView = (TextView) findViewById(R.id.stream_status);

        mFaceBeautyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mHandler.hasMessages(MSG_FB)) {
                    mHandler.sendEmptyMessage(MSG_FB);
                }
            }
        });

        mMuteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mHandler.hasMessages(MSG_MUTE)) {
                    mHandler.sendEmptyMessage(MSG_MUTE);
                }
            }
        });

        previewMirrorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mHandler.hasMessages(MSG_PREVIEW_MIRROR)) {
                    mHandler.sendEmptyMessage(MSG_PREVIEW_MIRROR);
                }
            }
        });

        encodingMirrorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mHandler.hasMessages(MSG_ENCODING_MIRROR)) {
                    mHandler.sendEmptyMessage(MSG_ENCODING_MIRROR);
                }
            }
        });

        mShutterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mShutterButtonPressed) {
                    stopStreaming();
                } else {
                    startStreaming();
                }
            }
        });

        mTorchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        if (!mIsTorchOn) {
                            mIsTorchOn = true;
                            mMediaStreamingManager.turnLightOn();
                        } else {
                            mIsTorchOn = false;
                            mMediaStreamingManager.turnLightOff();
                        }
                        setTorchEnabled(mIsTorchOn);
                    }
                }).start();
            }
        });

        mCameraSwitchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHandler.removeCallbacks(mSwitcher);
                mHandler.postDelayed(mSwitcher, 100);
            }
        });

        mCaptureFrameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHandler.removeCallbacks(mScreenShooter);
                mHandler.postDelayed(mScreenShooter, 100);
            }
        });


        mEncodingOrientationSwitcherBtn = (Button) findViewById(R.id.orientation_btn);
        mEncodingOrientationSwitcherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeCallbacks(mEncodingOrientationSwitcher);
                mHandler.post(mEncodingOrientationSwitcher);
            }
        });

        SeekBar seekBarBeauty = (SeekBar) findViewById(R.id.beautyLevel_seekBar);
        seekBarBeauty.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                CameraStreamingSetting.FaceBeautySetting fbSetting = mCameraStreamingSetting.getFaceBeautySetting();
                fbSetting.beautyLevel = progress / 100.0f;
                fbSetting.whiten = progress / 100.0f;
                fbSetting.redden = progress / 100.0f;

                mMediaStreamingManager.updateFaceBeautySetting(fbSetting);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        initButtonText();
    }

    private void setTorchEnabled(final boolean enabled) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String flashlight = enabled ? getString(R.string.flash_light_off) : getString(R.string.flash_light_on);
                mTorchBtn.setText(flashlight);
            }
        });
    }

    protected void startStreaming() {
        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_START_STREAMING), 50);
    }

    protected void stopStreaming() {
        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_STOP_STREAMING), 50);
    }

    private void push(String roomid) {

        String url = UrlBuilder.serverUrl + UrlBuilder.livepush(roomid);
        LogUtils.i(url);
        OkHttpUtils.get()
                .url(url)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {
            }

            @Override
            public void onResponse(String response) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocationOi
        ButterKnife.bind(this);
    }

    // Example to handle camera related operation
    private void setCameraAntiBanding50Hz() {
//        Camera.Parameters parameters = mStreamer.getCameraCapture().getCameraParameters();
//        if (parameters != null) {
//            parameters.setAntibanding(Camera.Parameters.ANTIBANDING_50HZ);
//            mStreamer.getCameraCapture().setCameraParameters(parameters);
//        }
    }


    private KSYStreamer.OnInfoListener mOnInfoListener = new KSYStreamer.OnInfoListener() {
        @Override
        public void onInfo(int what, int msg1, int msg2) {
            switch (what) {
                case StreamerConstants.KSY_STREAMER_CAMERA_INIT_DONE:
                    Log.d(TAG, "KSY_STREAMER_CAMERA_INIT_DONE");
                    setCameraAntiBanding50Hz();
//                    mStreamer.startStream();


                    break;
                case StreamerConstants.KSY_STREAMER_OPEN_STREAM_SUCCESS:
                    Log.d(TAG, "KSY_STREAMER_OPEN_STREAM_SUCCESS");

                    break;
                case StreamerConstants.KSY_STREAMER_FRAME_SEND_SLOW:
                    Log.d(TAG, "KSY_STREAMER_FRAME_SEND_SLOW " + msg1 + "ms");
                    Toast.makeText(StartLiveActivity.this, "网络不佳",
                            Toast.LENGTH_SHORT).show();
                    break;
                case StreamerConstants.KSY_STREAMER_EST_BW_RAISE:
                    Log.d(TAG, "BW raise to " + msg1 / 1000 + "kbps");
                    break;
                case StreamerConstants.KSY_STREAMER_EST_BW_DROP:
                    Log.d(TAG, "BW drop to " + msg1 / 1000 + "kpbs");
                    break;
                default:
                    Log.d(TAG, "OnInfo: " + what + " msg1: " + msg1 + " msg2: " + msg2);
                    break;
            }
        }
    };


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CAMERA_AUDIOREC: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //播放
//                    mStreamer.startCameraPreview();
                } else {
                    Log.e(TAG, "No CAMERA or AudioRecord permission");
//                    Toast.makeText(this, "No CAMERA or AudioRecord permission",
//                            Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        httpDatas.getDataDialog("关闭直播间", false, urlBuilder.cloesAnchor(creatReadyBean.getId()), myHandler, RequestCode.REQUEST_ROOM_CLOES);
        stopLive();
        videoPlayEnd();
        super.onDestroy();
        registerReceiveCustom(false);
        unregisterReceiver(broadcastReceiver);
        barrageview.setSentenceList(new ArrayList<BarrageDateBean>());
        mMediaStreamingManager.destroy();
    }

    public void onResume() {
        super.onResume();
        mWl.acquire();
        // 一般可以在onResume中开启摄像头预览
//        mStreamer.startCameraPreview();
//        // 调用KSYStreamer的onResume接口
//        mStreamer.onResume();
//        // 如果正在推流，切回音视频模式，该逻辑务必加上
//        if (mStreamer.isRecording()) {
//            mStreamer.setAudioOnly(false);
//        }

        mMediaStreamingManager.resume();
    }

    protected boolean mIsReady = false;

    @Override
    public void onPause() {
        super.onPause();
        mWl.release();
//        mStreamer.onPause();
//        mStreamer.stopCameraPreview();
//        if (mStreamer.isRecording()) {
//            mStreamer.setAudioOnly(true);
//        }


        //mIsReady = false;
        mShutterButtonPressed = false;
        mHandler.removeCallbacksAndMessages(null);
        mMediaStreamingManager.pause();

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            stopLive();
            videoPlayEnd();
            finish();
        }

        return super.onKeyDown(keyCode, event);


    }


    //-----聊天室开始-----

    /**
     * 注册监听
     *
     * @author sll
     * @time 2016/11/18 11:21
     */
    private void registerObservers(boolean register) {
        NIMClient.getService(ChatRoomServiceObserver.class).observeOnlineStatus(onlineStatus, register);
        NIMClient.getService(ChatRoomServiceObserver.class).observeKickOutEvent(kickOutObserver, register);
    }

    Observer<ChatRoomStatusChangeData> onlineStatus = new Observer<ChatRoomStatusChangeData>() {
        @Override
        public void onEvent(ChatRoomStatusChangeData chatRoomStatusChangeData) {
            if (!chatRoomStatusChangeData.roomId.equals(roomId)) {
                return;
            }
            if (chatRoomStatusChangeData.status == StatusCode.CONNECTING) {
//                DialogMaker.updateLoadingMessage("连接中...");
                LogUtils.i("WangYi", "连接中...");
            } else if (chatRoomStatusChangeData.status == StatusCode.LOGINING) {
//                DialogMaker.updateLoadingMessage("登录中...");
                LogUtils.i("WangYi", "登录中...");
            } else if (chatRoomStatusChangeData.status == StatusCode.LOGINED) {
//                if (fragment != null) {
//                    fragment.updateOnlineStatus(true);
//                }
                LogUtils.i("WangYi", "检测在线");
            } else if (chatRoomStatusChangeData.status == StatusCode.UNLOGIN) {
//                if (fragment != null) {
//                    fragment.updateOnlineStatus(false);
//                }
                LogUtils.i("WangYi", "检测不在线");
                int code = NIMClient.getService(ChatRoomService.class).getEnterErrorCode(roomId);
                LogUtils.d(TAG, "chat room enter error code:" + code);
                if (code != ResponseCode.RES_ECONNECTION) {
                    LogUtils.i("WangYi", "未登录,code=" + code);
//                    Toast.makeText(WatchLiveActivity.this, "未登录,code=" + code, Toast.LENGTH_LONG).show();
                }
            } else if (chatRoomStatusChangeData.status == StatusCode.NET_BROKEN) {
//                if (fragment != null) {
//                    fragment.updateOnlineStatus(false);
//                }
                Toast.makeText(StartLiveActivity.this, R.string.net_broken, Toast.LENGTH_SHORT).show();
            }
            LogUtils.i(TAG, "chat room online status changed to " + chatRoomStatusChangeData.status.name());
        }
    };
    Observer<ChatRoomKickOutEvent> kickOutObserver = new Observer<ChatRoomKickOutEvent>() {
        @Override
        public void onEvent(ChatRoomKickOutEvent chatRoomKickOutEvent) {
            Toast.makeText(StartLiveActivity.this, "被踢出聊天室，原因:" + chatRoomKickOutEvent.getReason(), Toast.LENGTH_SHORT).show();
            clearChatRoom();
        }
    };

    public void clearChatRoom() {
        ChatRoomMemberCache.getInstance().clearRoomCache(roomId);
        finish();
    }

    /**
     * 登录聊天室
     *
     * @author sll
     * @time 2016/11/18 11:21
     */
    private void enterRoom() {
//        DialogMaker.showProgressDialog(this, null, "", true, new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                if (enterRequest != null) {
//                    LogUtils.i("WangYi", "enterRequest != null");
//                    enterRequest.abort();
//                    onLoginDone();
//                    finish();
//                }
//            }
//        }).setCanceledOnTouchOutside(false);
        EnterChatRoomData data = new EnterChatRoomData(roomId);
        enterRequest = NIMClient.getService(ChatRoomService.class).enterChatRoom(data);
        enterRequest.setCallback(new RequestCallback<EnterChatRoomResultData>() {
            @Override
            public void onSuccess(EnterChatRoomResultData result) {
                LogUtils.i("WangYi", "Success");
                onLoginDone();
//                roomInfo = result.getRoomInfo();
//                LogUtils.i("WangYi", "roomInfo:" + roomInfo);
//                ChatRoomMember member = result.getMember();
//                member.setRoomId(roomInfo.getRoomId());
//                ChatRoomMemberCache.getInstance().saveMyMember(member);
//                initChatRoomFragment();
                initMessageFragment();
            }

            @Override
            public void onFailed(int code) {
                LogUtils.i("WangYi", "onFailed");
                // test
                LogUtil.ui("enter chat room failed, callback code=" + code);
                onLoginDone();
                if (code == ResponseCode.RES_CHATROOM_BLACKLIST) {
//                    Toast.makeText(WatchLiveActivity.this, "你已被拉入黑名单，不能再进入", Toast.LENGTH_SHORT).show();
                    LogUtils.i("WangYi", "你已被拉入黑名单，不能再进入");
                } else if (code == ResponseCode.RES_ENONEXIST) {
//                    Toast.makeText(WatchLiveActivity.this, "聊天室不存在", Toast.LENGTH_SHORT).show();
                    LogUtils.i("WangYi", "聊天室不存在");
                } else {
//                    Toast.makeText(WatchLiveActivity.this, "enter chat room failed, code=" + code, Toast.LENGTH_SHORT).show();
                    LogUtils.i("WangYi", "enter chat room failed, code=" + code);
                }
//                finish();
                initMessageFragment();
            }

            @Override
            public void onException(Throwable exception) {
                onLoginDone();
//                Toast.makeText(WatchLiveActivity.this, "enter chat room exception, e=" + exception.getMessage(), Toast.LENGTH_SHORT).show();
                LogUtils.i("WangYi", "enter chat room exception, e=" + exception.getMessage());
                finish();
            }
        });
    }

    private static Handler handler;

    protected final Handler getHandler() {
        if (handler == null) {
            handler = new Handler(getMainLooper());
        }
        return handler;
    }

    private void initMessageFragment() {
        messageFragment = (ChatRoomMessageFragment) getSupportFragmentManager().findFragmentById(R.id.watch_room_message_fragment);
        if (messageFragment != null) {
            messageFragment.init(roomId, creatReadyBean.getId());
        } else {
            // 如果Fragment还未Create完成，延迟初始化
            getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    initMessageFragment();
                }
            }, 50);
        }
    }

    private void onLoginDone() {
        enterRequest = null;
        DialogMaker.dismissProgressDialog();
    }
    //-----聊天室结束-----


    //关闭直播
    private void stopLive() {
        IS_START_LIVE = false;

//        mStreamer.stopStream();
//        mStreamer.stopMixMusic();
//        if (null != mPlayer) {
//            mPlayer.stop();
//        }
        if (mMainHandler != null) {
            mMainHandler.removeCallbacksAndMessages(null);
            mMainHandler = null;
        }
//        mStreamer.release();

    }


    /**
     * PopupWindow
     * =======
     * /**
     * >>>>>>> 866b152a914bd65994a3a384a47cc6c2e57d9132
     *
     * @param num 倒数时间
     * @dw 开始直播倒数计时
     */
    private FBO mFBO = new FBO();

    private void startAnimation(final int num) {
        final TextView tvNum = new TextView(this);
        tvNum.setTextColor(getResources().getColor(R.color.white));
        tvNum.setText(num + "");
        tvNum.setTextSize(30);
        mRoot.addView(tvNum);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tvNum.getLayoutParams();
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        tvNum.setLayoutParams(params);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(tvNum, "scaleX", 5f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(tvNum, "scaleY", 5f, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mRoot.removeView(tvNum);
                if (num == 1) {
//                    mStreamer.startStream();

                    return;
                }
                startAnimation(num == 3 ? 2 : 1);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        animatorSet.setDuration(1000);
        animatorSet.start();

    }

    /**
     * 展示游客详情
     *
     * @param customdateBean 用户信息
     * @author sll
     * @time 2016/11/25 9:40
     */
    private boolean isMutedTag = false;

    public void showDialogForCallOther(final CustomdateBean customdateBean) {
        final View view = View.inflate(this, R.layout.dialog_video_room, null);
        AvatarView civ_image = (AvatarView) view.findViewById(R.id.civ_image);
        final ImageView iv_sex = (ImageView) view.findViewById(R.id.iv_sex);
        ImageView iv_grade = (ImageView) view.findViewById(R.id.iv_grade);
        final TextView tv_report = (TextView) view.findViewById(R.id.tv_report);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        TextView tv_location = (TextView) view.findViewById(R.id.tv_location);
        TextView tv_focus_num = (TextView) view.findViewById(R.id.tv_focus_num);
        TextView tv_funs_num = (TextView) view.findViewById(R.id.tv_funs_num);
        TextView tv_send_num = (TextView) view.findViewById(R.id.tv_send_num);
        TextView tv_gold_coin = (TextView) view.findViewById(R.id.tv_gold_coin);
        TextView tvID = (TextView) view.findViewById(R.id.tv_id);
        if (isMutedTag) {
            tv_report.setText("取消禁言");
        } else {
            tv_report.setText("禁言");
        }

        if (customdateBean.getGender().equals("0")) {
            iv_sex.setImageResource(R.mipmap.female);
        } else {
            iv_sex.setImageResource(R.mipmap.male);
        }
        tvID.setText("ID:" + customdateBean.getId());
        civ_image.setAvatarUrl(customdateBean.getPicUrl());
        iv_grade.setImageResource(GrademipmapUtils.LevelImg[Integer.valueOf(customdateBean.getLevel()) - 1]);
        tv_name.setText(customdateBean.getNickName() + " [在线]");
        tv_location.setText(customdateBean.getAddress());
        tv_focus_num.setText(customdateBean.getFollowNum());
        tv_funs_num.setText(customdateBean.getFansNum());
        tv_send_num.setText(customdateBean.getSpendGoldCoin());
        tv_gold_coin.setText(customdateBean.getGoldCoin());
        tv_report.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //禁言或取消禁言
//                for (String ml : mutedList) {
//                    if (ml.equals("miu_" + customdateBean.getId())) {
//                        isMutedTag = false;
//                    }
//                }
                if (isMutedTag) {
                    isMutedTag = false;
                } else {
                    isMutedTag = true;
                }

                setMuted(tv_report, isMutedTag, "miu_" + customdateBean.getId());
                dialogForSelect.dismiss();
            }
        });
        view.findViewById(R.id.iv_x).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //关闭
                dialogForSelect.dismiss();
            }
        });
        view.findViewById(R.id.tv_foucs).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //关注

                //关注

                guanZhu((TextView) view.findViewById(R.id.tv_foucs), customdateBean);

//                dialogForSelect.dismiss();
            }
        });
        view.findViewById(R.id.tv_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //视频
                dialogForSelect.dismiss();
            }
        });
        view.findViewById(R.id.tv_chat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //聊天
                Intent intent = new Intent();
                intent.putExtra(Extras.EXTRA_ACCOUNT, "miu_" + customdateBean.getId());
                intent.putExtra("SESSION_NAME", customdateBean.getNickName());
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                Bundle arguments = intent.getExtras();
                arguments.putSerializable(Extras.EXTRA_TYPE, SessionTypeEnum.P2P);
                liveMessageFragment = new LiveMessageFragment();
                liveMessageFragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction().replace(R.id.watch_room_message_fragment, liveMessageFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
                dialogForSelect.dismiss();
            }

        });
        view.findViewById(R.id.tv_home_page).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StartLiveActivity.this, OtherPersonHomePageActivity.class).putExtra(getString(R.string.visiti_person), customdateBean.getId()));
                //主页
                dialogForSelect.dismiss();
            }
        });
        dialogForSelect.setCanceledOnTouchOutside(true);
        dialogForSelect.setContentView(view);
        dialogForSelect.show();
    }

    private void guanZhu(final TextView tvFoucs, final CustomdateBean bean) {
        GuanZhuUtils.newInstance().guanZhu(this, appUser.getId(), bean.getId(), new ResultListener() {
            @Override
            public void onSucess(String data) {
                String follow = bean.getFollow();
                if (TextUtils.equals("0", follow)) {
                    bean.setFollow("1");
                } else if (TextUtils.equals("1", follow)) {
                    bean.setFollow("0");
                }

                focus(tvFoucs, bean);
            }

            @Override
            public void onFaild() {
                ToastUtil.makeToast("修改失败");
            }
        });
    }

    /**
     * 显示关注(未关注)
     */
    private void focus(TextView tvFouce, CustomdateBean bean) {
        String follow = bean.getFollow();
        if (TextUtils.equals("0", follow)) {
            tvFouce.setText("未关注");
        } else if (TextUtils.equals("1", follow)) {
            tvFouce.setText("已关注");
        }
    }

    /**
     * 设置/取消禁言
     *
     * @param isMuted 是否禁言 true:添加, false:取消
     * @param account 要禁言的用户id
     * @author sll
     * @time 2016/12/9 18:55
     */
    private void setMuted(final TextView textView, boolean isMuted, String account) {

        MemberOption option = new MemberOption(roomId, account);
        NIMClient.getService(ChatRoomService.class).markChatRoomMutedList(isMuted, option)
                .setCallback(new RequestCallback<ChatRoomMember>() {
                    @Override
                    public void onSuccess(ChatRoomMember param) {
                        ToastUtil.makeToast(getString(R.string.set_success));
                        if (param.isMuted()) {
                            textView.setText("取消禁言");
                            isMutedTag = true;
                            mutedList.add(param.getAccount());
                        } else {
                            textView.setText("禁言");
                            isMutedTag = false;
                            mutedList.remove(param.getAccount());
                        }
                    }

                    @Override
                    public void onFailed(int code) {
                        Log.d(TAG, "set muted failed:" + code);
                    }

                    @Override
                    public void onException(Throwable exception) {

                    }
                });
    }

/*设置网易云信消息未读等  开始*/

    /**
     * 注册未读消息数量观察者
     */
    private void registerMsgUnreadInfoObserver(boolean register) {
        if (register) {
            ReminderManager.getInstance().registerUnreadNumChangedCallback(this);
        } else {
            ReminderManager.getInstance().unregisterUnreadNumChangedCallback(this);
        }
    }

    /**
     * 未读消息数量观察者实现
     */
    @Override
    public void onUnreadNumChanged(ReminderItem item) {
//        MainTab tab = MainTab.fromReminderId(item.getId());
//        if (tab != null) {
//            tabs.updateTab(tab.tabIndex, item);
//        }
        int unread = NIMClient.getService(MsgService.class).getTotalUnreadCount();
        if (item == null || tabNewMsgLabel == null || tabNewIndicator == null) {
            if (tabNewMsgLabel != null)
                tabNewMsgLabel.setVisibility(View.GONE);
            if (tabNewIndicator != null)
                tabNewIndicator.setVisibility(View.GONE);
            return;
        }
//        int unread = item.unread();
        boolean indicator = item.indicator();
        tabNewMsgLabel.setVisibility(unread > 0 ? View.VISIBLE : View.GONE);
        tabNewIndicator.setVisibility(indicator ? View.VISIBLE : View.GONE);
        if (unread > 0) {
            tabNewMsgLabel.setText(String.valueOf(ReminderSettings.unreadMessageShowRule(unread)));
        }
    }

    /**
     * 注册/注销系统消息未读数变化
     *
     * @param register
     */
    private void registerSystemMessageObservers(boolean register) {
        NIMClient.getService(SystemMessageObserver.class).observeUnreadCountChange(sysMsgUnreadCountChangedObserver,
                register);
    }

    private Observer<Integer> sysMsgUnreadCountChangedObserver = new Observer<Integer>() {
        @Override
        public void onEvent(Integer unreadCount) {
            SystemMessageUnreadManager.getInstance().setSysMsgUnreadCount(unreadCount);
            ReminderManager.getInstance().updateContactUnreadNum(unreadCount);
        }
    };

    /**
     * 查询系统消息未读数
     */
    private void requestSystemMessageUnreadCount() {
        int unread = NIMClient.getService(SystemMessageService.class).querySystemMessageUnreadCountBlock();
        SystemMessageUnreadManager.getInstance().setSysMsgUnreadCount(unread);
        ReminderManager.getInstance().updateContactUnreadNum(unread);
    }

    private void enableMsgNotification(boolean enable) {
        if (enable) {
            /**
             * 设置最近联系人的消息为已读
             *
             * @param account,    聊天对象帐号，或者以下两个值：
             *                    {@link #MSG_CHATTING_ACCOUNT_ALL} 目前没有与任何人对话，但能看到消息提醒（比如在消息列表界面），不需要在状态栏做消息通知
             *                    {@link #MSG_CHATTING_ACCOUNT_NONE} 目前没有与任何人对话，需要状态栏消息通知
             */
            NIMClient.getService(MsgService.class).setChattingAccount(MsgService.MSG_CHATTING_ACCOUNT_NONE, SessionTypeEnum.None);
        } else {
            NIMClient.getService(MsgService.class).setChattingAccount(MsgService.MSG_CHATTING_ACCOUNT_ALL, SessionTypeEnum.None);
        }
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

    }

    @Override
    public void onAudioSourceAvailable(ByteBuffer byteBuffer, int i, long l, boolean b) {

    }

    @Override
    public void notifyStreamStatusChanged(StreamingProfile.StreamStatus streamStatus) {

    }

    @Override
    public boolean onPreviewFrame(byte[] bytes, int i, int i1, int i2, int i3, long l) {
        return true;
    }

    @Override
    public boolean onRecordAudioFailedHandled(int i) {
        mMediaStreamingManager.updateEncodingType(AVCodecType.SW_VIDEO_CODEC);
        mMediaStreamingManager.startStreaming();
        return true;
    }

    @Override
    public boolean onRestartStreamingHandled(int i) {
        return mMediaStreamingManager.startStreaming();
    }

    @Override
    public Camera.Size onPreviewSizeSelected(List<Camera.Size> list) {
        Camera.Size size = null;
        if (list != null) {
            for (Camera.Size s : list) {
                if (s.height >= 480) {
                    size = s;
                    break;
                }
            }
        }
//        Log.e(TAG, "selected size :" + size.width + "x" + size.height);
        return size;
    }

    @Override
    public void onSurfaceCreated() {
        mFBO.initialize(this);
    }

    @Override
    public void onSurfaceChanged(int width, int height) {
        mFBO.updateSurfaceSize(width, height);
    }

    @Override
    public void onSurfaceDestroyed() {
        mFBO.release();
    }

    @Override
    public int onDrawFrame(int texId, int texWidth, int texHeight, float[] transformMatrix) {
        // newTexId should not equal with texId. texId is from the SurfaceTexture.
        // Otherwise, there is no filter effect.
        int newTexId = mFBO.drawFrame(texId, texWidth, texHeight);
//        Log.i(TAG, "onDrawFrame texId:" + texId + ",newTexId:" + newTexId + ",texWidth:" + texWidth + ",texHeight:" + texHeight);
        return newTexId;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.i(TAG, "onSingleTapUp X:" + e.getX() + ",Y:" + e.getY());

        if (mIsReady) {
            setFocusAreaIndicator();
            mMediaStreamingManager.doSingleTapUp((int) e.getX(), (int) e.getY());
            return true;
        }
        return false;
    }

    @Override
    public boolean onZoomValueChanged(float factor) {
        if (mIsReady && mMediaStreamingManager.isZoomSupported()) {
            mCurrentZoom = (int) (mMaxZoom * factor);
            mCurrentZoom = Math.min(mCurrentZoom, mMaxZoom);
            mCurrentZoom = Math.max(0, mCurrentZoom);

            Log.d(TAG, "zoom ongoing, scale: " + mCurrentZoom + ",factor:" + factor + ",maxZoom:" + mMaxZoom);
            if (!mHandler.hasMessages(MSG_SET_ZOOM)) {
                mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ZOOM), ZOOM_MINIMUM_WAIT_MILLIS);
                return true;
            }
        }
        return false;
    }

    private String mStatusMsgContent;
    private String mLogContent = "\n";

    @Override
    public void onStateChanged(StreamingState streamingState, Object extra) {
        Log.i(TAG, "StreamingState streamingState:" + streamingState + ",extra:" + extra);

        switch (streamingState) {
            case PREPARING:
                mStatusMsgContent = getString(R.string.string_state_preparing);
                break;
            case READY:
                mIsReady = true;
                mMaxZoom = mMediaStreamingManager.getMaxZoom();
                mStatusMsgContent = getString(R.string.string_state_ready);
                // start streaming when READY
                startStreaming();
                break;
            case CONNECTING:
                mStatusMsgContent = getString(R.string.string_state_connecting);
                break;
            case STREAMING:
                mStatusMsgContent = getString(R.string.string_state_streaming);
                setShutterButtonEnabled(true);
                setShutterButtonPressed(true);
                break;
            case SHUTDOWN:
                mStatusMsgContent = getString(R.string.string_state_ready);
                setShutterButtonEnabled(true);
                setShutterButtonPressed(false);
                if (mOrientationChanged) {
                    mOrientationChanged = false;
                    startStreaming();
                }
                break;
            case IOERROR:
                mLogContent += "IOERROR\n";
                mStatusMsgContent = getString(R.string.string_state_ready);
                setShutterButtonEnabled(true);
                break;
            case UNKNOWN:
                mStatusMsgContent = getString(R.string.string_state_ready);
                break;
            case SENDING_BUFFER_EMPTY:
                break;
            case SENDING_BUFFER_FULL:
                break;
            case AUDIO_RECORDING_FAIL:
                break;
            case OPEN_CAMERA_FAIL:
                Log.e(TAG, "Open Camera Fail. id:" + extra);
                break;
            case DISCONNECTED:
                mLogContent += "DISCONNECTED\n";
                break;
            case INVALID_STREAMING_URL:
                Log.e(TAG, "Invalid streaming url:" + extra);
                break;
            case UNAUTHORIZED_STREAMING_URL:
                Log.e(TAG, "Unauthorized streaming url:" + extra);
                mLogContent += "Unauthorized Url\n";
                break;
            case CAMERA_SWITCHED:
//                mShutterButtonPressed = false;
                if (extra != null) {
                    Log.i(TAG, "current camera id:" + (Integer) extra);
                }
                Log.i(TAG, "camera switched");
                final int currentCamId = (Integer) extra;
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateCameraSwitcherButtonText(currentCamId);
                    }
                });
                break;
            case TORCH_INFO:
                if (extra != null) {
                    final boolean isSupportedTorch = (Boolean) extra;
                    Log.i(TAG, "isSupportedTorch=" + isSupportedTorch);
                    this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (isSupportedTorch) {
                                mTorchBtn.setVisibility(View.VISIBLE);
                            } else {
                                mTorchBtn.setVisibility(View.GONE);
                            }
                        }
                    });
                }
                break;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mLogTextView != null) {
                    mLogTextView.setText(mLogContent);
                }
                mStatusTextView.setText(mStatusMsgContent);
            }
        });


    }

    /**
     * 观众点播节目的消息列表适配
     *
     * @author sll
     * @time 2016/12/8 19:13
     */
    public class DianBoDateAdapter extends CommonAdapter<DianBoDateBean> {
        public DianBoDateAdapter(Context context, List<DianBoDateBean> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, final DianBoDateBean item, final int position) {
            helper.setText(R.id.pop_user_name, "@" + item.getNickName()).setText(R.id.pop_content, "愿意付款" + item.getCost() + "点播" + item.getName() + "表演");
            AvatarView avatar = helper.getView(R.id.pop_user_acater);
            avatar.setAvatarUrl(item.getPicUrl());
            //拒绝
            final TextView popRefuse = helper.getView(R.id.pop_refuse);
            final TextView popAgree = helper.getView(R.id.pop_agree);
            popRefuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showNum--;
                    setShowNum();
                    vodId = item.getId();
//                    popAgree.setVisibility(View.INVISIBLE);
//                    popRefuse.setText("已拒绝");
                    sendCustomNotification("miu_" + item.getUserId(), "主播拒绝了您点播的请求", "2", CustomNotificationType.IM_P2P_TYPE_ORDERSHOW_ACK);
                    dianBoDateList.remove(position);
                    //直播结束接口
                    httpDatas.getData("主播结束表演", UrlBuilder.roomVodEnd(vodId), myHandler, RequestCode.REQUEST_ROOM_VOD_END);
                    popupWindow_dianbo.dismiss();
                }
            });

            //同意
            popAgree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showNum--;
                    setShowNum();
                    sessionId = item.getUserId();
                    vodId = item.getId();
                    sendCustomNotification("miu_" + item.getUserId(), "主播同意了您点播的请求", "2", CustomNotificationType.IM_P2P_TYPE_ORDERSHOW_ACK);
//                        dianBoDateList.remove(position);
                    //弹框，直到表演结束
                    popupWindow_dianbo.dismiss();
                    //直播开始接口
                    httpDatas.getData("主播开始表演", UrlBuilder.roomVodStart(vodId), myHandler, RequestCode.REQUEST_ROOM_VOD_START);
                    dianBoDateList.remove(position);
                }
            });

        }
    }

    /**
     * 观众连麦的消息列表适配
     *
     * @author sll
     * @time 2016/12/8 19:13
     */
    public class LianMaiDateAdapter extends CommonAdapter<LianMaiDateBean> {
        public LianMaiDateAdapter(Context context, List<LianMaiDateBean> mDatas, int itemLayoutId) {
            super(context, mDatas, itemLayoutId);
        }

        @Override
        public void convert(ViewHolder helper, final LianMaiDateBean item, final int position) {
            helper.setText(R.id.pop_user_name, "@" + item.getNickName()).setText(R.id.pop_content, "愿意付款" + item.getAmount() + "连麦");
            AvatarView avatar = helper.getView(R.id.pop_user_acater);
            avatar.setAvatarUrl(item.getUserAvatar());
            //拒绝
            final TextView popRefuse = helper.getView(R.id.pop_refuse);
            final TextView popAgree = helper.getView(R.id.pop_agree);
            popRefuse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    liveNums--;
                    setLiveNum();
                    popAgree.setVisibility(View.INVISIBLE);
                    popRefuse.setText("已拒绝");
                    sendCustomNotificationForLive("miu_" + item.getUserId(), "主播拒绝了您的视频连线请求", "2", CustomNotificationType.IM_P2P_TYPE_SUBLIVE_ACK, "1");
//                        dianBoDateList.remove(position);
                    lianMaiDateList.remove(position);
                    //主播确认申请状态
                    //{ "id":"1","status":"1"}
                    //id为申请id，status为状态，-1为拒绝，1为同意
                    Map map = new HashMap();
                    map.put("id", item.getId());
                    map.put("status", "-1");
                    httpDatas.getDataForJsoNoloading("主播确认申请状态", com.android.volley.Request.Method.POST, UrlBuilder.room_result, map, myHandler, RequestCode.REQUEST_ROOM_VOD_END);
                    popupWindow_lianMai.dismiss();
                }
            });

            //同意
            popAgree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    liveNums--;
                    setLiveNum();
                    sessionId = item.getUserId();
                    vodId = item.getId();
                    sendCustomNotificationForLive("miu_" + item.getUserId(), "主播同意了您的视频连线请求", "2", CustomNotificationType.IM_P2P_TYPE_SUBLIVE_ACK, "0");
                    Map map = new HashMap();
                    map.put("id", item.getId());
                    map.put("status", "1");
                    httpDatas.getDataForJsoNoloading("主播确认申请状态", com.android.volley.Request.Method.POST, UrlBuilder.room_result, map, myHandler, RequestCode.REQUEST_ROOM_VOD_END);
                    popupWindow_lianMai.dismiss();
                }
            });

        }
    }

    /**
     * 2
     */
//    private void lianmailaliu(String lmll) {
//        lmFm.setVisibility(View.VISIBLE);
//
//
//        //视频流播放地址
////        String mrl="http://pull6.a8.com/live/1479350967188968.flv";
//        String mrl = "http://pull.a8.com/live/1481871210332000.flv";
//        LogUtils.i("拉流地址" + mrl);
//        //视频播放器init
//        ksyMediaPlayer = new KSYMediaPlayer.Builder(mContext).build();
//
//        /*
//        * 参数：OnBufferingUpdateListener
//        * 功能：设置Buffering的监听器，当播放器在Buffering时会发出此回调，通知外界Buffering的进度
//        * 返回值：无
//        * */
//        ksyMediaPlayer.setOnBufferingUpdateListener(mOnBufferingUpdateListener2);
//        /*
//        * 参数：OnCompletionListener
//        * 功能：设置Completion的监听器，在视频播放完成后会发出此回调
//        * 返回值：无
//        * */
//        ksyMediaPlayer.setOnCompletionListener(mOnCompletionListener2);
//        /*
//        * 参数：OnPreparedListener
//        * 功能：设置Prepared状态的监听器，在调用prepare()/prepareAsync()之后，正常完成解析后会通过此监听器通知外界。
//        * 返回值：无
//        * */
//        ksyMediaPlayer.setOnPreparedListener(mOnPreparedListener2);
//        /*
//        *参数：OnInfoListener
//        *功能：设置Info监听器，播放器可通过此回调接口将消息通知开发者
//        *返回值：无
//        * */
//        ksyMediaPlayer.setOnInfoListener(mOnInfoListener2);
//        /*参数：OnVideoSizeChangedListener
//        功能：设置VideoSizeChanged的监听器，当视频的宽度或高度发生变化时会发出次回调，通知外界视频的最新宽度和高度*/
////        ksyMediaPlayer.setOnVideoSizeChangedListener(mOnVideoSizeChangeListener2);
//        /*参数：OnErrorListener
//        功能：设置Error监听器，当播放器遇到error时，会发出此回调并送出error code
//        返回值：无*/
//        ksyMediaPlayer.setOnErrorListener(mOnErrorListener2);
//        /*参数：OnSeekCompleteListener
//        功能：设置Seek Complete的监听器，Seek操作完成后会有此回调
//        返回值：无*/
//        ksyMediaPlayer.setOnSeekCompleteListener(mOnSeekCompletedListener2);
//        /*参数：screenOn 值为true时，播放时屏幕保持常亮，反之则否
//        功能：使用SurfaceHolder控制播放期间屏幕是否保持常亮。须调用接口setDisplay设置SurfaceHolder，此接口才有效
//        返回值：无*/
//        ksyMediaPlayer.setScreenOnWhilePlaying(true);
//        /*参数：直播音频缓存最大值，单位为秒
//        功能：设置直播音频缓存上限，由此可控制追赶功能的阈值。该值为负数时，关闭直播追赶功能。此接口只对直播有效
//        返回值：无*/
//        ksyMediaPlayer.setBufferTimeMax(0);
//
//
//        try {
//            ksyMediaPlayer.setDataSource(mrl);
//            ksyMediaPlayer.prepareAsync();
//            LogUtils.e("啊梵蒂冈梵蒂冈");
//            ksyMediaPlayer.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(IMediaPlayer iMediaPlayer) {
//                    LogUtils.e("aaaa");
//                    iMediaPlayer.start();
//                }
//            });
//
//            mSurfaceHolder = mVideoSurfaceView.getHolder();
//            mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);
//            mSurfaceHolder.addCallback(mSurfaceCallback);
////        隐藏输入框
////        mVideoSurfaceView.setOnTouchListener(mTouchListener);
//            mVideoSurfaceView.setKeepScreenOn(true);
//            this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
//            mCameraPreview.setZOrderOnTop(false);
//            mCameraPreview.setZOrderMediaOverlay(false);
//            mVideoSurfaceView.setZOrderOnTop(true);
//            mVideoSurfaceView.setZOrderMediaOverlay(true);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//    }

    public static final int UPDATE_SEEKBAR = 0;

    public static final int HIDDEN_SEEKBAR = 1;

    public static final int UPDATE_QOS = 2;

    /**
     * 3
     */
//    private void updateQosInfo(QosObject obj) {
//
//        if (ksyMediaPlayer != null) {
//            long bits = ksyMediaPlayer.getDecodedDataSize() * 8 / (mPause ? mPauseStartTime - mPausedTime - mStartTime : System.currentTimeMillis() - mPausedTime - mStartTime);
//
//        }
//
//    }
    /**
     * 4
     */
//    private final SurfaceHolder.Callback mSurfaceCallback = new SurfaceHolder.Callback() {
//        @Override
//        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//            if (ksyMediaPlayer != null) {
//                final Surface newSurface = holder.getSurface();
//                ksyMediaPlayer.setDisplay(holder);
//                ksyMediaPlayer.setScreenOnWhilePlaying(true);
//                if (mSurface != newSurface) {
//                    mSurface = newSurface;
//                    ksyMediaPlayer.setSurface(mSurface);
//                }
//            }
//        }
//
//        @Override
//        public void surfaceCreated(SurfaceHolder holder) {
//        }
//
//        @Override
//        public void surfaceDestroyed(SurfaceHolder holder) {
//            Log.d(TAG, "surfaceDestroyed");
//            if (ksyMediaPlayer != null) {
//                mSurface = null;
//            }
//        }
//    };
//
//
//    private IMediaPlayer.OnPreparedListener mOnPreparedListener2 = new IMediaPlayer.OnPreparedListener() {
//        @Override
//        public void onPrepared(IMediaPlayer mp) {
//
//            ksyMediaPlayer.start();
//        }
//    };


    private long mPauseStartTime = 0;

    private long mPausedTime = 0;

    private int mVideoWidth = 0;

    private int mVideoHeight = 0;

    private IMediaPlayer.OnVideoSizeChangedListener mOnVideoSizeChangeListener2 = new IMediaPlayer.OnVideoSizeChangedListener() {
        @Override
        public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sarNum, int sarDen) {
            if (mVideoWidth > 0 && mVideoHeight > 0) {
                if (width != mVideoWidth || height != mVideoHeight) {
                    mVideoWidth = mp.getVideoWidth();
                    mVideoHeight = mp.getVideoHeight();

                    // maybe we could call scaleVideoView here.
                    if (mVideoSurfaceView != null) {
//                        mVideoSurfaceView.setVideoDimension(mVideoWidth, mVideoHeight);
                        mVideoSurfaceView.requestLayout();
                    }
                }
            }
        }
    };

    private IMediaPlayer.OnSeekCompleteListener mOnSeekCompletedListener2 = new IMediaPlayer.OnSeekCompleteListener() {
        @Override
        public void onSeekComplete(IMediaPlayer mp) {
            Log.e(TAG, "onSeekComplete...............");
        }
    };

    private IMediaPlayer.OnCompletionListener mOnCompletionListener2 = new IMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(IMediaPlayer mp) {
            //直播完成 直播结束
            videoPlayEnd();

        }
    };

//    private IMediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener2 = new IMediaPlayer.OnBufferingUpdateListener() {
//        @Override
//        public void onBufferingUpdate(IMediaPlayer mp, int percent) {
//            long duration = ksyMediaPlayer.getDuration();
//            long progress = duration * percent / 100;
//        }
//    };
    //错误异常监听
//    private IMediaPlayer.OnErrorListener mOnErrorListener2 = new IMediaPlayer.OnErrorListener() {
//        @Override
//        public boolean onError(IMediaPlayer mp, int what, int extra) {
//            switch (what) {
//                case KSYMediaPlayer.MEDIA_ERROR_UNKNOWN:
//                    Log.e(TAG, "OnErrorListener, Error Unknown:" + what + ",extra:" + extra);
//                    break;
//
//                default:
//                    Log.e(TAG, "OnErrorListener, Error:" + what + ",extra:" + extra);
//            }
//            videoPlayEnd();
//            return false;
//        }
//    };
//
//    public IMediaPlayer.OnInfoListener mOnInfoListener2 = new IMediaPlayer.OnInfoListener() {
//        @Override
//        public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
//            switch (i) {
//                case KSYMediaPlayer.MEDIA_INFO_BUFFERING_START:
//                    Log.d(TAG, "Buffering Start.");
//                    break;
//                case KSYMediaPlayer.MEDIA_INFO_BUFFERING_END:
//                    Log.d(TAG, "Buffering End.");
//                    break;
//                case KSYMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START:
////                    Toast.makeText(mContext, "Audio Rendering Start", Toast.LENGTH_SHORT).show();
//                    break;
//                case KSYMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
////                    Toast.makeText(mContext, "Video Rendering Start", Toast.LENGTH_SHORT).show();
//                    break;
//                case KSYMediaPlayer.MEDIA_INFO_SUGGEST_RELOAD:
//                    // Player find a new stream(video or audio), and we could reload the video.
//                    if (ksyMediaPlayer != null)
////                        ksyMediaPlayer.reload(laliu, false);
//                        break;
//                case KSYMediaPlayer.MEDIA_INFO_RELOADED:
//                    Toast.makeText(mContext, "Succeed to reload video.", Toast.LENGTH_SHORT).show();
//                    Log.d(TAG, "Succeed to reload video.");
//                    return false;
//            }
//            return false;
//        }
//    };

    //直播结束释放资源
    public void videoPlayEnd() {
//        if (ksyMediaPlayer != null) {
//            ksyMediaPlayer.release();
//            ksyMediaPlayer = null;
//        }

        if (mQosThread != null) {
            mQosThread.stopThread();
            mQosThread = null;
        }
    }

    protected List<SendGiftBean> mLuxuryGiftFireworksShowQueue = new ArrayList<>();

    protected List<SendGiftBean> mLuxuryGiftCruisesShowQueue = new ArrayList<>();

    protected List<SendGiftBean> mLuxuryGiftCarShowQueue = new ArrayList<>();

    protected List<SendGiftBean> mLuxuryGiftPlainShowQueue = new ArrayList<>();

    protected boolean isFireworkGiftAnimationPlayEnd = true;

    protected boolean isCruisesGiftAnimationPlayEnd = true;

    protected boolean isCarGiftAnimationPlayEnd = true;

    protected boolean isPlainGiftAnimationPlayEnd = true;

    private AnimationDrawable animationDrawable;

    /**
     * @dw 飞机
     */
    protected void showPlainAnimation(SendGiftBean mSendGiftBean) {
        if (!isPlainGiftAnimationPlayEnd) {
            return;
        }
        //飞机动画初始化
        isPlainGiftAnimationPlayEnd = false;
        //撒花的颜色
        final int[] colorArr = new int[]{R.color.red, R.color.yellow, R.color.blue, R.color.lite_blue, R.color.orange, R.color.pink};

        final View plainView = getLayoutInflater().inflate(R.layout.view_live_gift_animation_plain, null);
        mRoot.addView(plainView);
        final RelativeLayout mRootAnimation = (RelativeLayout) plainView.findViewById(R.id.rl_animation_flower);
        ImageView imageView = (ImageView) plainView.findViewById(R.id.iv_animation_plain);
        AnimationDrawable plainAnimationDrawable = (AnimationDrawable) imageView.getBackground();
        plainAnimationDrawable.start();
        int screenW = getWindowManager().getDefaultDisplay().getWidth();
        ObjectAnimator plainAnimator = ObjectAnimator.ofFloat(plainView, "translationX", screenW, 0);
        plainAnimator.setDuration(3000);
        plainAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                Random random = new Random();
                int num = random.nextInt(50) + 10;
                int width = mRootAnimation.getWidth();
                int height = mRootAnimation.getHeight();
                //获取花朵
                for (int i = 0; i < num; i++) {
                    int color = random.nextInt(5);
                    int x = random.nextInt(50);
                    final int tx = random.nextInt(width);
                    final int ty = random.nextInt(height);
                    final TextView flower = new TextView(StartLiveActivity.this);
                    flower.setX(x);
                    flower.setText("❀");
                    flower.setTextColor(getResources().getColor(colorArr[color]));
                    flower.setTextSize(50);
                    //每个花朵的动画
                    mRootAnimation.addView(flower);
                    flower.animate().alpha(0f).translationX(tx).translationY(ty).setDuration(5000).start();

                }
                //飞机移动到中间后延迟一秒钟,开始继续前行-x
                myHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        ObjectAnimator plainAnimator = ObjectAnimator.ofFloat(plainView, "translationX", -plainView.getWidth());
                        plainAnimator.setDuration(2000);
                        plainAnimator.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                if (null != plainView) {
                                    if (null != mRoot) {
                                        mRoot.removeView(plainView);
                                    }
                                    mLuxuryGiftPlainShowQueue.remove(0);
                                    isPlainGiftAnimationPlayEnd = true;
                                    if (mLuxuryGiftPlainShowQueue.size() > 0) {
                                        showPlainAnimation(mLuxuryGiftPlainShowQueue.get(0));
                                    }
                                }

                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });
                        plainAnimator.start();
                    }
                }, 4000);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });//n^m ^ n^m ^ m
        plainAnimator.start();//❀
    }

    /**
     * @dw 红色小轿车动画
     * @author 魏鹏
     */
    protected void showRedCarAnimation(SendGiftBean sendGiftBean) {
        if (!isCarGiftAnimationPlayEnd) {
            return;
        }
        isCarGiftAnimationPlayEnd = false;
        //获取汽车动画布局
        final View generalCar = getLayoutInflater().inflate(R.layout.view_live_gift_animation_car_general, null);
        //添加到当前页面
        mRoot.addView(generalCar);
        //获取到汽车image控件
        final ImageView redCar = (ImageView) generalCar.findViewById(R.id.iv_animation_red_car);
        //获取背景
        animationDrawable = (AnimationDrawable) redCar.getBackground();
        animationDrawable.start();
        final int screenW = getWindowManager().getDefaultDisplay().getWidth();
        final int screenH = getWindowManager().getDefaultDisplay().getHeight();
        final int vw = redCar.getLayoutParams().width;
        //汽车动画init
        ObjectAnimator ox = ObjectAnimator.ofFloat(generalCar, "translationX", screenW + vw, screenW / 2 - vw / 2);
        ox.setDuration(1500);
        ObjectAnimator oy = ObjectAnimator.ofFloat(generalCar, "translationY", screenH >> 2);
        //设置背景帧动画
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ox, oy);
        animatorSet.setDuration(1500);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(final Animator animation) {
                //小汽车停在中间
                redCar.setBackgroundResource(R.drawable.gift_car_red_animation2);
                animationDrawable = (AnimationDrawable) redCar.getBackground();
                animationDrawable.start();
                myHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //小汽车切换帧动画开始继续移动向-x
                        redCar.setBackgroundResource(R.drawable.gift_car_red_animation);
                        animationDrawable = (AnimationDrawable) redCar.getBackground();
                        animationDrawable.start();
                        generalCar.animate().translationX(~vw)
                                .withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        //小汽车从底部重新回来切换帧动画
                                        redCar.setBackgroundResource(R.drawable.gift_car_red_animation3);
                                        animationDrawable = (AnimationDrawable) redCar.getBackground();
                                        animationDrawable.start();
                                        ObjectAnimator oX = ObjectAnimator.ofFloat(generalCar, "translationX", screenW, screenW / 2 - vw / 2);
                                        ObjectAnimator oY = ObjectAnimator.ofFloat(generalCar, "translationY", screenH / 2, screenH >> 2);

                                        //重新初始化帧动画参数
                                        AnimatorSet animatorSet = new AnimatorSet();
                                        animatorSet.playTogether(oX, oY);
                                        animatorSet.setDuration(2000);
                                        animatorSet.addListener(new Animator.AnimatorListener() {
                                            @Override
                                            public void onAnimationStart(Animator animation) {

                                            }

                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                                //小汽车加速帧动画切换
                                                redCar.setBackgroundResource(R.drawable.gift_car_red_animation4);
                                                animationDrawable = (AnimationDrawable) redCar.getBackground();
                                                animationDrawable.start();
                                                generalCar.animate().translationX(-vw).translationY(0)
                                                        .setDuration(1000)
                                                        .withEndAction(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                //从汽车队列中移除,开始下一个汽车动画
                                                                if (generalCar == null) return;
                                                                mRoot.removeView(generalCar);
                                                                mLuxuryGiftCarShowQueue.remove(0);
                                                                animationDrawable = null;
                                                                isCarGiftAnimationPlayEnd = true;
                                                                if (mLuxuryGiftCarShowQueue.size() > 0) {
                                                                    showRedCarAnimation(mLuxuryGiftCarShowQueue.get(0));
                                                                }
                                                            }
                                                        })
                                                        .start();

                                            }

                                            @Override
                                            public void onAnimationCancel(Animator animation) {

                                            }

                                            @Override
                                            public void onAnimationRepeat(Animator animation) {

                                            }
                                        });
                                        animatorSet.start();

                                    }
                                })
                                .setDuration(1000).start();
                    }
                }, 500);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();


    }

    /**
     * @dw 邮轮
     * @author 魏鹏
     */
    protected void showCruisesAnimation(SendGiftBean mSendGiftBean) {
        if (!isCruisesGiftAnimationPlayEnd) {
            return;
        }
        isCruisesGiftAnimationPlayEnd = false;
        final View cruisesView = getLayoutInflater().inflate(R.layout.view_live_gift_animation_cruises, null);
        final int ww = getWindowManager().getDefaultDisplay().getWidth();
        //游轮上的用户头像
        AvatarView mUhead = (AvatarView) cruisesView.findViewById(R.id.live_cruises_uhead);
        ((TextView) cruisesView.findViewById(R.id.tv_live_cruises_uname)).setText(mSendGiftBean.getNicename());
        mUhead.setAvatarUrl(mSendGiftBean.getAvatar());

        mRoot.addView(cruisesView);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) cruisesView.getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        cruisesView.setLayoutParams(params);
        final RelativeLayout cruises = (RelativeLayout) cruisesView.findViewById(R.id.rl_live_cruises);

        //游轮开始平移动画
        cruises.animate().translationX(ww / 2 + ww / 3).translationY(120f).withEndAction(new Runnable() {
            @Override
            public void run() {
                myHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //游轮移动到中间后重新开始移动
                        cruises.animate().translationX(ww * 2).translationY(200f).setDuration(3000)
                                .withEndAction(new Runnable() {
                                    @Override
                                    public void run() {
                                        //结束后从队列移除开始下一个邮轮动画
                                        if (mRoot == null) return;
                                        mRoot.removeView(cruisesView);
                                        mLuxuryGiftCruisesShowQueue.remove(0);
                                        isCruisesGiftAnimationPlayEnd = true;
                                        if (mLuxuryGiftCruisesShowQueue.size() > 0) {
                                            showCruisesAnimation(mLuxuryGiftCruisesShowQueue.get(0));
                                        }
                                    }
                                }).start();
                    }
                }, 2000);

            }
        }).setDuration(3000).start();

        //邮轮海水动画
        ImageView seaOne = (ImageView) cruisesView.findViewById(R.id.iv_live_water_one);
        ImageView seaTwo = (ImageView) cruisesView.findViewById(R.id.iv_live_water_one2);
        ObjectAnimator obj = ObjectAnimator.ofFloat(seaOne, "translationX", -50, 50);
        obj.setDuration(1000);
        obj.setRepeatCount(-1);
        obj.setRepeatMode(ObjectAnimator.REVERSE);
        obj.start();
        ObjectAnimator obj2 = ObjectAnimator.ofFloat(seaTwo, "translationX", 50, -50);
        obj2.setDuration(1000);
        obj2.setRepeatCount(-1);
        obj2.setRepeatMode(ObjectAnimator.REVERSE);
        obj2.start();
    }

    /**
     * @dw 烟花
     * @author 魏鹏
     */
    protected void showFireworksAnimation(SendGiftBean mSendGiftBean) {
        if (!isFireworkGiftAnimationPlayEnd) {
            return;
        }
        isFireworkGiftAnimationPlayEnd = false;
        //初始化烟花动画
        final ImageView animation = new ImageView(this);
        animation.setBackgroundResource(R.drawable.gift_fireworks_heart_animation);
        AnimationDrawable an = (AnimationDrawable) animation.getBackground();
        an.setOneShot(true);
        mRoot.addView(animation);
        RelativeLayout.LayoutParams pa = (RelativeLayout.LayoutParams) animation.getLayoutParams();
        pa.width = 400;
        pa.height = pa.WRAP_CONTENT;
        pa.addRule(RelativeLayout.CENTER_IN_PARENT);
        animation.setScaleType(ImageView.ScaleType.CENTER_CROP);
        animation.setLayoutParams(pa);
        an.start();
        int duration = 0;
        for (int i = 0; i < an.getNumberOfFrames(); i++) {
            duration += an.getDuration(i);
        }
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //删除当前礼物,开始队列下一个
                if (mRoot == null) return;
                mRoot.removeView(animation);
                mLuxuryGiftFireworksShowQueue.remove(0);
                isFireworkGiftAnimationPlayEnd = true;
                if (mLuxuryGiftFireworksShowQueue.size() > 0) {
                    showFireworksAnimation(mLuxuryGiftFireworksShowQueue.get(0));
                }

            }
        }, duration);

    }
}
