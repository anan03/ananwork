package com.lvshandian.partylive.moudles.index.live;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.ksyun.media.player.IMediaPlayer;
import com.ksyun.media.player.KSYMediaPlayer;
import com.lvshandian.partylive.R;
import com.lvshandian.partylive.UrlBuilder;
import com.lvshandian.partylive.adapter.GridViewAdapter;
import com.lvshandian.partylive.adapter.ViewPageGridViewAdapter;
import com.lvshandian.partylive.base.BarrageDateBean;
import com.lvshandian.partylive.base.BaseActivity;
import com.lvshandian.partylive.base.Constant;
import com.lvshandian.partylive.base.CustomStringCallBack;
import com.lvshandian.partylive.bean.ChannelMoreUrlBean;
import com.lvshandian.partylive.bean.CustomGiftBean;
import com.lvshandian.partylive.bean.CustomShowBean;
import com.lvshandian.partylive.bean.CustomdateBean;
import com.lvshandian.partylive.bean.DianBoDateBean;
import com.lvshandian.partylive.bean.GiftBean;
import com.lvshandian.partylive.bean.IfAttentionBean;
import com.lvshandian.partylive.bean.LiveBean;
import com.lvshandian.partylive.bean.LiveFamilyMemberBean;
import com.lvshandian.partylive.bean.RoomUserBean;
import com.lvshandian.partylive.bean.RoomUserDataBean;
import com.lvshandian.partylive.bean.SendGiftBean;
import com.lvshandian.partylive.httprequest.HttpDatas;
import com.lvshandian.partylive.httprequest.RequestCode;
import com.lvshandian.partylive.interf.ResultListener;
import com.lvshandian.partylive.moudles.index.CustomNotificationType;
import com.lvshandian.partylive.moudles.index.adapter.FamilyMemberAdapter;
import com.lvshandian.partylive.moudles.index.live.gift.GiftFrameLayout;
import com.lvshandian.partylive.moudles.index.live.gift.GiftSendModel;
import com.lvshandian.partylive.moudles.mine.activity.ChargeMoneyActivity;
import com.lvshandian.partylive.moudles.mine.bean.GongXianBean;
import com.lvshandian.partylive.moudles.mine.bean.GongXianData;
import com.lvshandian.partylive.moudles.mine.my.GongXianActivity;
import com.lvshandian.partylive.moudles.mine.my.OtherPersonHomePageActivity;
import com.lvshandian.partylive.moudles.mine.my.adapter.OnItemClickListener;
import com.lvshandian.partylive.moudles.mine.my.adapter.RoomUsersDataAdapter;
import com.lvshandian.partylive.utils.CacheUtils;
import com.lvshandian.partylive.utils.ChannelToLiveBean;
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
import com.lvshandian.partylive.utils.Strings;
import com.lvshandian.partylive.utils.ThreadManager;
import com.lvshandian.partylive.utils.ToastUtils;
import com.lvshandian.partylive.utils.Utils;
import com.lvshandian.partylive.view.BarrageView;
import com.lvshandian.partylive.view.DialogView;
import com.lvshandian.partylive.view.RoundDialog;
import com.lvshandian.partylive.wangyiyunxin.chatroom.fragment.ChatRoomMessageFragment;
import com.lvshandian.partylive.wangyiyunxin.chatroom.helper.ChatRoomMemberCache;
import com.lvshandian.partylive.wangyiyunxin.live.fragment.ChatRoomSessionListFragment;
import com.lvshandian.partylive.wangyiyunxin.live.fragment.LiveMessageFragment;
import com.lvshandian.partylive.wangyiyunxin.main.helper.SystemMessageUnreadManager;
import com.lvshandian.partylive.wangyiyunxin.main.reminder.ReminderItem;
import com.lvshandian.partylive.wangyiyunxin.main.reminder.ReminderManager;
import com.lvshandian.partylive.wangyiyunxin.main.reminder.ReminderSettings;
import com.lvshandian.partylive.widget.AvatarView;
import com.lvshandian.partylive.widget.MediaController;
import com.lvshandian.partylive.widget.VideoSurfaceView;
import com.lvshandian.partylive.widget.myrecycler.RefreshRecyclerView;
import com.lvshandian.partylive.widget.myrecycler.adapter.RefreshRecyclerViewAdapter;
import com.lvshandian.partylive.widget.myrecycler.listener.OnBothRefreshListener;
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
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMessage;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomStatusChangeData;
import com.netease.nimlib.sdk.chatroom.model.EnterChatRoomData;
import com.netease.nimlib.sdk.chatroom.model.EnterChatRoomResultData;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.SystemMessageObserver;
import com.netease.nimlib.sdk.msg.SystemMessageService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.CustomNotification;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.PLNetworkManager;
import com.pili.pldroid.player.widget.PLVideoTextureView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.UnknownHostException;
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
 * 观看直播页面
 * Created by 张振 on 2016/11/13.
 */

public class WatchLiveActivity extends BaseActivity implements ReminderManager.UnreadNumChangedCallback {


    private static final String TAG = "WatchLiveActivity";

    //    @Bind(R.id.video)O)O
//    VideoSurfaceView mVideoSurfaceView = null;
    @Bind(R.id.rl_loading)
    RelativeLayout rlLoading;
    @Bind(R.id.iv_load)
    ImageView ivLoad;
    @Bind(R.id.live_head)
    AvatarView liveHead;
    @Bind(R.id.live_name)
    TextView liveName;
    @Bind(R.id.live_num)
    TextView liveNum;
    @Bind(R.id.ll_yp_labe)
    AutoLinearLayout llYpLabe;
    @Bind(R.id.live_jinpiao)
    TextView liveJinpiao;
    @Bind(R.id.live_id)
    TextView liveId;
    @Bind(R.id.btn_attention)
    Button btnAttention;
    @Bind(R.id.iv_live_privatechat)
    ImageView ivLivePrivatechat;
    @Bind(R.id.iv_live_dianbo)
    ImageView ivLiveDianbo;
    @Bind(R.id.iv_live_lianmai)
    ImageView ivLiveLianmai;
    @Bind(R.id.iv_live_gift)
    ImageView ivLiveGift;
    @Bind(R.id.iv_live_share)
    ImageView ivLiveShare;
    @Bind(R.id.ll_bottom_menu)
    RelativeLayout llBottomMenu;
    @Bind(R.id.fl_bottom_menu)
    FrameLayout flBottomMenu;
    @Bind(R.id.gift_layout1)
    GiftFrameLayout giftFrameLayout1;
    @Bind(R.id.gift_layout2)
    GiftFrameLayout giftFrameLayout2;
    @Bind(R.id.live_close)
    ImageView liveClose;
    @Bind(R.id.all_header)
    AutoLinearLayout allHeader;
    @Bind(R.id.rlv_list_live_audiences)
    RecyclerView rlvListLiveAudiences;
    //    @Bind(R.id.mrl_layout_live_audiences)
//    MaterialRefreshLayout mrlLayoutLiveAudiences;
    @Bind(R.id.rl_live_root)
    RelativeLayout mRoot;
    @Bind(R.id.tab_new_indicator)
    ImageView tabNewIndicator;
    @Bind(R.id.tab_new_msg_label)
    DropFake tabNewMsgLabel;
    @Bind(R.id.video_lian)
    SurfaceView videoLian;
    @Bind(R.id.lm_fm)
    AutoFrameLayout lmFm;
    @Bind(R.id.barrageview)
    BarrageView barrageview;
    @Bind(R.id.watch_room_jaizu)
    ImageView watchRoomJaizu;

    private RoundDialog mQuitDialog;
    private LiveBean liveBean; //主播bean
    private Uri mVideoUri;
    private boolean mBackPressed;

    public static final int UPDATE_SEEKBAR = 0;

    public static final int HIDDEN_SEEKBAR = 1;

    public static final int UPDATE_QOS = 2;

    private Gson mGson = new Gson();

//    private KSYMediaPlayer ksyMediaPlayer;

//    private QosThread mQosThread;

    private Surface mSurface = null;

//    private SurfaceHolder mSurfaceHolder = null;

//    private KSYMediaPlayer ksyMediaPlayer2;

    private QosThread mQosThread2;

    private Surface mSurface2 = null;

    private SurfaceHolder mSurfaceHolder2 = null;

    private Handler mHandler;

    private Handler mHandlertui = new Handler();

    private boolean mPlayerPanelShow = false;

    private boolean mPause = false;

    private long mStartTime = 0;

    private long mPauseStartTime = 0;

    private long mPausedTime = 0;

    private int mVideoWidth = 0;

    private int mVideoHeight = 0;

//    private View mLoadingView;

    private BottomView mGiftSelectView, familySelectView;

    private TextView mUserCoin;

    private LinearLayout ll_user_coin;

    private ViewPager mVpGiftView;//礼物view

    private GiftBean mSelectedGiftItem;//当前选中的礼物

    private RelativeLayout mSendGiftLian;

    private Button mSendGiftBtn;

    private String mGiftResStr; //礼物服务端返回数据

    private ViewPageGridViewAdapter mVpGiftAdapter;

    private List<GiftBean> mGiftList = new ArrayList<>();

    private List<GridView> mGiftViews = new ArrayList<>();

    private int mShowGiftSendOutTime = 2;


    private long mLitLastTime = 0;

    private AnimationDrawable animationDrawable;
    private IfAttentionBean ifAttentionBean;//判断主播是否被关注过

    private String roomId;
    private ChatRoomMessageFragment messageFragment;
    private AbortableFuture<EnterChatRoomResultData> enterRequest;
    //消息中心Fragment
    private ChatRoomSessionListFragment sessionListFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private boolean isfirstclick = true;


    protected Map<Integer, View> mGiftShowNow = new HashMap<>();//当前正在显示的两个动画

    protected Map<Integer, SendGiftBean> mGiftShowQueue = new HashMap(); //礼物消息队列

    protected int mShowGiftFirstUid = 0;

    protected int mShowGiftSecondUid = 0;

    private int GiFT_NUM = 0;//练送礼物个数

    private TextView lian_nuum;

    private int sendgiftmoney; //当前送礼物消费多少钱

    List<GiftSendModel> giftSendModelList = new ArrayList<GiftSendModel>();

    private ChatRoomMessage message;
    private Dialog dialogForSelect;
    private LiveMessageFragment liveMessageFragment;


    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);

            switch (msg.what) {

                case 121212:

                    initLive(mVideoPath);
                    break;

                //礼物列表
                case RequestCode.GET_GIFT:
                    mGiftResStr = json;
                    mGiftList = JsonUtil.json2BeanList(mGiftResStr, GiftBean.class);
                    break;
                //关注请求接收数据
                case RequestCode.ATTENTION_USER:

                    break;
                //赠送礼物
                case RequestCode.SEND_GIFT:
                    LogUtils.i("赠送礼物" + json);
                    if (json.equals("true")) {
                        if (GiFT_NUM == 0) {
                            return;
                        }
                        Map<String, Object> map = new HashMap<>();
                        map.put("gift_item_index", mSelectedGiftItem.getId());
                        map.put("gift_item_number", GiFT_NUM + "");
                        map.put("gift_coinnumber_index", mSelectedGiftItem.getCurrencyAmount());
                        map.put("gift_item_message", "赠送了" + GiFT_NUM + "个" + mSelectedGiftItem.getName());
                        map.put("vip", appUser.getVip());
                        map.put("userId", appUser.getId());
                        SendRoomMessageUtils.onCustomMessageSendGift(messageFragment, liveBean.getRoomId(), map);
                        sendgiftmoney = 0;
                        GiFT_NUM = 0;
                        LogUtils.i(mSelectedGiftItem.getCurrencyAmount() + "。。。。赠送了1个" + mSelectedGiftItem.getName());
                        mUserCoin.setText((Integer.parseInt(mUserCoin.getText().toString()) - sendgiftmoney) + "");
                        appUser.setGoldCoin((Integer.parseInt(mUserCoin.getText().toString()) - sendgiftmoney) + "");
                        CacheUtils.saveObject(WatchLiveActivity.this, appUser, CacheUtils.USERINFO);
                    } else {
                        showToast("请稍后再试");
                    }
                    break;
                //判断是否被关注过
                case RequestCode.IF_ATTENTION:
                    LogUtils.i(json.toString());
                    ifAttentionBean = JsonUtil.json2Bean(json, IfAttentionBean.class);
                    liveJinpiao.setText(ifAttentionBean.getReceivedGoldCoin());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (Integer.parseInt(ifAttentionBean.getFollow()) == 1) {
                                btnAttention.setVisibility(View.GONE);
                            } else {
                                btnAttention.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    break;
                case RequestCode.REQUEST_USER_INFO:
                    //请求用户信息
                    LogUtils.i(json.toString());
                    CustomdateBean customdateBean = JSON.parseObject(json, CustomdateBean.class);
                    showDialogForCallOther(customdateBean);
                    break;
                case RequestCode.REQUEST_ZHUBO_INFO:
                    //请求用户信息
                    LogUtils.i(json.toString());
                    zhuBo = JSON.parseObject(json, CustomdateBean.class);

                    break;
                case RequestCode.REQUEST_ROOM_VOD:
                    LogUtils.i("WangYi_CN", "请求接口成功，点播节目成功");
                    //点播节目
                    LogUtils.i(json.toString());
                    DianBoDateBean dianBoDateBean = JSON.parseObject(json, DianBoDateBean.class);
                    sendCNOnDemand(dianBoDateBean.getNickName(), dianBoDateBean.getCost(), dianBoDateBean.getName(), dianBoDateBean.getId());
                    break;
                case RequestCode.ROOM_LIANMAI:
                    LogUtils.i("请求接口成功，可以连麦");
                    break;
                case RequestCode.REQUEST_REPORT:
                    showToast("已成功举报该用户");
                    break;
                case RequestCode.REQUEST_ROOM_SHOW_WATCH:
                    //支付成功，扣除金币并取消弹框
                    LogUtils.i("WangYi", "观众确认观看表演" + json.toString());
                    if (json.toString() != null && json.toString().equals("true")) {
                        payState = true;
                        if (mUserCoin != null) {
                            mUserCoin.setText((Integer.parseInt(appUser.getGoldCoin()) - Integer.parseInt(showCost)) + "");
                            appUser.setGoldCoin(mUserCoin.getText().toString());
                        } else
                            appUser.setGoldCoin((Integer.parseInt(appUser.getGoldCoin()) - Integer.parseInt(showCost)) + "");
                        CacheUtils.saveObject(WatchLiveActivity.this, appUser, CacheUtils.USERINFO);
                    }
                    if (mNoPayShowDialog != null && mNoPayShowDialog.isShowing()) {
                        mNoPayShowDialog.dismiss();
                    }
                    if (mDialog != null && mDialog.isShowing()) {
                        mDialog.dismiss();
                    }
                    //记录表演showId，用于下次进入判断是否支付过
                    Sharedpreferences.setParam(WatchLiveActivity.this, "showId" + roomId, showId);
                    break;
                case RequestCode.TIMERLIVE:

                    LogUtils.i("主播隔一段时间刷新状态" + json.toString());
                    break;

                case 10001:
                    LogUtils.i("主播隔一段时间刷新状态");
                    httpDatas.getDataDialog("主播隔一段时间刷新状态", false, urlBuilder.TimerUserLive(liveBean.getId(), liveBean.getCreator().getId()), myHandler, RequestCode.TIMERLIVE);
                    break;
            }
        }
    };
    private UMSocialService mController;

    private final String share_title = "分享是真爱,你是我的菜\n";
    private String share_content = "";
    private String share_url = "";
    private String livePicUrl = "";

    private int liveOnLineNums;
    private RoundDialog mNoPayShowDialog;
    private RoundDialog mDialog;
    private Toast mToast = null;

    @Override
    protected void onPause() {
        super.onPause();
        mIsActivityPaused = true;

    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsActivityPaused = false;
    }

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
                videoPlayEnd();
            }
        });
    }

    private void um43Share() {

        mController.getConfig().closeToast();
        share_content = "分享是真爱,你是我的菜!" + liveBean.getCreator().getNickName() + "正在直播,快来一起看~";
        livePicUrl = liveBean.getLivePicUrl();

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


    /**
     * 发送自定义通知给主播，用于点播节目
     *
     * @author sll
     * @time 2016/12/6 16:17
     */
    private void sendCNOnDemand(String userName, String cost, String name, String id) {
        LogUtils.i("WangYi_CN", "发送自定义通知给主播，用于点播节目");
        String content = userName + "出" + cost + "金币，点播表演" + name + ",请查看";
        // 构造自定义通知，指定接收者
        sendCustomNotification("miu_" + liveBean.getCreator().getId(), content, id, CustomNotificationType.IM_P2P_TYPE_ORDERSHOW);
//        sendCustomNotification("miu_" + appUser.getId(), content, id, CustomNotificationType.IM_P2P_TYPE_ORDERSHOW);
    }

    /**
     * 自定义通知接收
     *
     * @author sll
     * @time 2016/12/6 18:08
     */
    private void registerReceiveCustom(boolean register) {
        if (register)
            LogUtils.i("WangYi_CN", "Watch接收自定义通知:开启");
        else
            LogUtils.i("WangYi_CN", "Watch接收自定义通知:关闭");
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
            LogUtils.i("WangYi_CN", "Watch接收自定义通知(SessionId):" + message.getSessionId());
            LogUtils.i("WangYi_CN", "Watch接收自定义通知(fromAccount):" + message.getFromAccount());
            LogUtils.i("WangYi_CN", "Watch接收自定义通知(SessionType):" + message.getSessionType());
            LogUtils.i("WangYi_CN", "Watch接收自定义通知(ApnsText):" + message.getApnsText());
            LogUtils.i("WangYi_CN", "Watch接收自定义通知(Content):" + message.getContent());
            if (message.getPushPayload() != null)
                LogUtils.i("WangYi_CN", "Watch接收自定义通知(pushPayload):" + message.getPushPayload().toString());

            DianBoDateBean dianBoDateBean = JSON.parseObject(message.getContent(), DianBoDateBean.class);
            if (dianBoDateBean != null) {
                String type = dianBoDateBean.getType();
                if (type != null) {
                    if (type.equals(CustomNotificationType.IM_P2P_TYPE_ORDERSHOW_ACK)) {
                        //收到主播反馈,同意或拒绝
                        showDialogForOrdershowAck(message.getApnsText());
                    } else if (type.equals(CustomNotificationType.IM_P2P_TYPE_ORDERSHOW_END)) {
                        //主播表演结束
                        if (dialogForOrdershowEnd == null || !dialogForOrdershowEnd.isShowing())
                            showDialogForOrdershowEnd();
                    }
                }
            }
        }
    };

    /**
     * 收到主播反馈,同意或拒绝
     *
     * @param content 反馈信息
     * @author sll
     * @time 2016/12/8 20:08
     */
    public void showDialogForOrdershowAck(String content) {
        SendRoomMessageUtils.sendMessageLocal("200", messageFragment, "miu_" + appUser.getId(), content, "miu_" + liveBean.getCreator().getId());
        final Dialog dialogForOrdershowAck = new Dialog(this, R.style.homedialog);
        final View view = View.inflate(this, R.layout.dialog_video_room_show_ack, null);
        TextView showAckText = (TextView) view.findViewById(R.id.dialog_show_ack_text);
        showAckText.setText(content);
        view.findViewById(R.id.dialog_show_ack_x).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogForOrdershowAck.dismiss();
            }
        });
        view.findViewById(R.id.dialog_show_ack_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogForOrdershowAck.dismiss();
            }
        });
        dialogForOrdershowAck.setCanceledOnTouchOutside(true);
        dialogForOrdershowAck.setContentView(view);
        dialogForOrdershowAck.show();
    }

    /**
     * 支付表演对话框
     */
    private void initPayShowDialog(final CustomShowBean bean) {
        if (bean != null) {
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_anchor_play, null);
            TextView tvContent = (TextView) view.findViewById(R.id.tv_content);

            tvContent.setText("主播即将表演" + bean.getName() + ",需要" + bean.getCost() + "金币，不支付可看不到哦");
            mDialog = new RoundDialog(this, view, R.style.dialog, 0.66f, 0.25f);
            view.findViewById(R.id.iv_x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDialog != null && mDialog.isShowing()) {
                        mDialog.dismiss();
                    }
                }
            });
            view.findViewById(R.id.btn_pay).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDialog != null && mDialog.isShowing()) {
                        mDialog.dismiss();
                    }
                    showWatch(bean.getId());
                }
            });
            mDialog.show();
        }

    }

    private String radioText;//记录对主播表演的评价
    private Dialog dialogForOrdershowEnd;

    /**
     * 收到主播反馈,结束表演，提交评价（满意/不满意）
     *
     * @author sll
     * @time 2016/12/8 20:08
     */
    public void showDialogForOrdershowEnd() {
        dialogForOrdershowEnd = new Dialog(this, R.style.homedialog);
        final View view = View.inflate(this, R.layout.dialog_video_room_show_end, null);
        final RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.dialog_show_end_radio);
        final RadioButton radioButton_Y = (RadioButton) view.findViewById(R.id.dialog_show_end_radio_y);
        final RadioButton radioButton_N = (RadioButton) view.findViewById(R.id.dialog_show_end_radio_n);
        radioText = "满意";
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == radioButton_Y.getId()) {
                    radioText = "满意";
                    radioButton_Y.setChecked(true);
                    radioButton_N.setChecked(false);
                } else {
                    radioText = "不满意";
                    radioButton_Y.setChecked(false);
                    radioButton_N.setChecked(true);
                }
            }
        });
        view.findViewById(R.id.dialog_show_end_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogForOrdershowEnd.dismiss();
                vodResult(radioText);
            }
        });
        dialogForOrdershowEnd.setCanceledOnTouchOutside(true);
        dialogForOrdershowEnd.setContentView(view);
        dialogForOrdershowEnd.show();
    }

    /**
     * 用户满意度接口
     * http://miulive.cc:8080/api/v1/user/service/result post提交
     * 发送：{"fromUserId":"6","toUserId":"9","result":"1"}
     * fromUserId为提交者id，toUserId为提供服务者id，result为是否满意，0不满意，1满意
     *
     * @author sll
     * @time 2016/12/9 11:07
     */
    private void vodResult(String result) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("fromUserId", appUser.getId());
        map.put("toUserId", liveBean.getCreator().getId());
        if (result.equals("不满意"))
            map.put("result", "0");
        else
            map.put("result", "1");
        httpDatas.getDataForJson("用户满意度接口", Request.Method.POST, UrlBuilder.vodResult, map, mHandler, RequestCode.REQUEST_ROOM_VOD_RESULT);

    }

    /**
     * 举报
     *
     * @author sll
     * @time 2016/12/24 16:01
     */
    private void reportDialog(final String userId) {
        final Dialog dialog = new Dialog(this, R.style.homedialog);
        final View view = View.inflate(this, R.layout.dialog_join_secret_pwd, null);
        final EditText pwdEdit = (EditText) view.findViewById(R.id.join_secret_pwd_edit);
        final TextView promptText = (TextView) view.findViewById(R.id.dialog_prompt_text);
        promptText.setVisibility(View.GONE);
        pwdEdit.setHint("请输入举报内容");
        //取消
        view.findViewById(R.id.join_secret_pwd_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //确定
        view.findViewById(R.id.join_secret_pwd_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(pwdEdit.getText().toString())) {
                    com.lvshandian.partylive.view.ToastUtils.showSnackBar(pwdEdit, "请输入举报内容");
                }
                report(userId, pwdEdit.getText().toString());
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        dialog.show();
    }

    /**
     * 举报
     * reportUserId 自己的id
     * userId 被举报人的id
     * content 举报内容
     *
     * @author sll
     * @time 2016/12/24 15:54
     */
    private void report(String userId, String content) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("reportUserId", appUser.getId());
        map.put("userId", userId);
        map.put("content", content);
        httpDatas.getDataForJsoNoloading("举报", Request.Method.GET, UrlBuilder.report, map, mHandler, RequestCode.REQUEST_REPORT);

    }

    private CustomdateBean zhuBo;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_watchlive;
    }

    @Override
    protected void initListener() {
        float x = 10;
        ivLivePrivatechat.setOnClickListener(this);
        ivLiveDianbo.setOnClickListener(this);
        ivLiveGift.setOnClickListener(this);
        ivLiveShare.setOnClickListener(this);
        ivLiveLianmai.setOnClickListener(this);
        llYpLabe.setOnClickListener(this);
        liveJinpiao.setOnClickListener(this);
        allHeader.setOnClickListener(this);
        liveClose.setOnClickListener(this);
        watchRoomJaizu.setOnClickListener(this);

        //点赞
        mRoot.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (sessionListFragment != null) {
                    sessionListFragment.hide();
                }
                if (liveMessageFragment != null)
                    liveMessageFragment.hide();
                if (messageFragment != null) {
                    messageFragment.hideEditText();
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (isfirstclick) {
                        LogUtils.i("1111111");
                        Map<String, Object> map = new HashMap<>();
                        map.put("red", 0.700000);
                        map.put("green", 0.400000);
                        map.put("blue", 0.100000);
                        map.put("number", 1 + "");
                        map.put("vip", appUser.getVip());
                        map.put("userId", appUser.getId());
                        SendRoomMessageUtils.onCustomMessageDianzan(SendRoomMessageUtils.MESSAGE_LIKE_LIGHT, messageFragment, liveBean.getRoomId(), map);
                        isfirstclick = false;
                    } else {
                        LogUtils.i("22222");
                        showLit();
                    }
                }
                return false;
            }
        });

        //关注主播
        btnAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
                map.put("followUserId", liveBean.getCreator().getId());
                map.put("userId", appUser.getId());
                httpDatas.getDataForJsoNoloading("关注用户", Request.Method.POST, UrlBuilder.ATTENTION_USER, map, myHandler, RequestCode.ATTENTION_USER);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        btnAttention.setVisibility(View.GONE);
                    }
                });

            }
        });

//        mrlLayoutLiveAudiences.setMaterialRefreshListener(new MaterialRefreshListener() {
//            @Override
//            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
//                isRefresh = true;
//                requestNet();
//            }
//
//            @Override
//            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
//                isRefresh = false;
//                requestNet();
//            }
//        });

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                RoomUserBean bean = mDatas.get(position);
                ifattention("请求用户信息", bean.getUserId(), RequestCode.REQUEST_USER_INFO);
//                Intent intent = new Intent(mContext, OtherPersonHomePageActivity.class);
//                intent.putExtra(getString(R.string.visiti_person), bean.getUserId());
//                startActivity(intent);
            }
        });

    }

    private MediaController mMediaController;
    Timer timer = new Timer();
    private int mIsLiveStreaming = 1;

    @Override
    protected void initialized() {

        mController = UMServiceFactory.getUMSocialService("com.umeng.share");
        share_url = "http://www.partylive.cn/Live/live.html?" + "userId=" + appUser.getId();
        getGiftList();
        liveBean = (LiveBean) getIntent().getSerializableExtra("LIVE");
        um43Share();
        ifattention("请求主播信息", liveBean.getCreator().getId(), RequestCode.REQUEST_ZHUBO_INFO);
        roomId = liveBean.getRoomId();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                myHandler.sendEmptyMessage(10001);
            }
        }, 30000, 30000);
        if (!TextUtils.isEmpty(liveBean.getId()))
            Sharedpreferences.setParam(this, "ZhuBoId", liveBean.getCreator().getId());
        Sharedpreferences.setParam(this, "isZhuBo", false);
//        roomId = "5137074";
//        requestJinPiao();   //更换
        try {
//            laliu = DESUtil.decrypt(liveBean.getBroadcastUrl());
            mVideoPath = DESUtil.decrypt(liveBean.getBroadcastUrl());
        } catch (Exception e) {
            e.printStackTrace();
        }

        liveHead.setAvatarUrl(liveBean.getCreator().getPicUrl());
        liveName.setText(liveBean.getCreator().getNickName());
        liveNum.setText(liveBean.getOnlineUserNum());
        liveId.setText("ID号:" + liveBean.getCreator().getId());
        ifattention("判断主播是否被关注过", liveBean.getCreator().getId(), RequestCode.IF_ATTENTION);
//        LogUtils.i("拉流地址" + laliu);
        mHandler = new UIHandler(this);
        initLive(mVideoPath);
        // 注册监听
        registerObservers(true);
        //注册监听自定义通知
        registerReceiveCustom(true);
        // 登录聊天室
        enterRoom();

        registerReceiverWatchLiveActivity();

        initRecycle();

        //网易云信消息未读数
        registerMsgUnreadInfoObserver(true);
        registerSystemMessageObservers(true);
        requestSystemMessageUnreadCount();

        dialogForSelect = new Dialog(this, R.style.homedialog);
        fragmentManager = getSupportFragmentManager();
        requestShows();

        join();
        initQuitDialog();

//        startloading();
    }

    /**
     * 进入直播间，为私密直播插入记录
     *
     * @author sll
     * @time 2016/12/16 17:14
     */
    private void join() {
        String url = UrlBuilder.chargeServerUrl + UrlBuilder.join;
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("roomId", liveBean.getId());
        hashMap.put("userId", appUser.getId());
        String json = new JSONObject(hashMap).toString();
        LogUtils.e("Json:　" + json);
        OkHttpUtils.get().url(url)
                .params(hashMap)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(com.squareup.okhttp.Request request, Exception e) {
                    }

                    @Override
                    public void onResponse(String response) {
                    }
                });
    }

    /*
        2.获取直播间中的表演列表
        http://miulive.cc:8080/api/v1/room/{roomId}/shows 获取直播间中的表演列表 get请求 roomId为直播间的id，例 http://miulive.cc:8080/api/v1/room/1/shows
        发送：无
        接收：
        其中status, 1是开始，2是结束，0是未开始，-1是失效
        { code: "0", message: "成功", data: [ { id: "1", roomId: "1", userId: "1", name: "测试", cost: "100" }, { id: "2", roomId: "1", userId: "1", name: "测试", cost: "100" } ]}
        新用户进来获取表演列表，或者客户端定时每五分钟更新一下表演列表
    */
    private void requestShows() {
        String url = UrlBuilder.serverUrl + UrlBuilder.room + liveBean.getId() + "/shows";
        LogUtils.e("直播间所有表演: " + url);
        OkHttpUtils.get().url(url).build().execute(new CustomStringCallBack(mContext, HttpDatas.KEY_CODE) {
            @Override
            public void onFaild() {
                LogUtils.e("直播间所有表演: " + "失败");
            }

            @Override
            public void onSucess(String data) {
                LogUtils.e("直播间所有表演data: " + data);
                List<CustomShowBean> mShowBeans = JsonUtil.json2BeanList(data, CustomShowBean.class);
                if (mShowBeans != null && mShowBeans.size() > 0) {
                    isShowingDialog(mShowBeans);
                }
            }
        });
    }

    /**
     * 判断主播是否正在表演
     *
     * @param mShowBeans
     */
    private void isShowingDialog(List<CustomShowBean> mShowBeans) {
        if (mShowBeans != null && mShowBeans.size() > 0) {
            CustomShowBean showBean = mShowBeans.get(0);
            String status = showBean.getStatus();  //1是开始，2是结束，0是未开始，-1是失效
            showCost = showBean.getCost();
            showId = showBean.getId();
            if (showId.equals(Sharedpreferences.getParam(WatchLiveActivity.this, "showId" + roomId, "")))
                return;
            switch (status) {
                case "0":
                    initPayShowDialog(showBean);
                    break;
                case "1":
                    initGrayPayDialog(showBean);
                    break;
            }
        }
    }

    private void initGrayPayDialog(final CustomShowBean showBean) {
        if (showBean != null) {
            View view = LayoutInflater.from(this).inflate(R.layout.dialog_gray_pay, null);
            TextView tvContent = (TextView) view.findViewById(R.id.tv_content);

            tvContent.setText("主播表演中,需要支付" + showBean.getCost() + "金币\n不支付可看不到哦");
            view.findViewById(R.id.iv_x).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    videoPlayEnd();
                }
            });
            view.findViewById(R.id.btn_pay).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showBuy(showBean.getId());
                }
            });
            mNoPayShowDialog = new RoundDialog(this, view, R.style.dialog, 0.9f, 0.9f);
            mNoPayShowDialog.setCanceledOnTouchOutside(false);
            mNoPayShowDialog.setCancelable(false);
            mNoPayShowDialog.show();
        }
    }

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
//        mrlLayoutLiveAudiences.setLoadMore(true);
//        RecycleViewDivider myDecoration = new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL, R.drawable.divider);
//        rlvListLiveAudiences.addItemDecoration(myDecoration);

        //进入页面自动刷新
//        mrlLayoutLiveAudiences.autoRefresh();
        requestNet();
    }

    /**
     * 聊天室头像列表
     */
    private void requestNet() {
        String url = UrlBuilder.serverUrl + UrlBuilder.room;
        if (appUser != null) {
            url += liveBean.getId();
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
                    showToast(isRefresh ? "刷新失败 " : "加载失败");
                    finshRefresh();
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
//        mrlLayoutLiveAudiences.finishRefreshLoadMore();
//        mrlLayoutLiveAudiences.finishRefresh();
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
//                liveNum.setText(result.size());
            }
        }

        finshRefresh();
    }

    /*最上边观众头像列表结束*/


    /**
     * 请求金票数量
     */
    private void requestJinPiao() {
        String url = UrlBuilder.serverUrl + UrlBuilder.yqRen;
        if (liveBean != null) {
            LiveBean.CreatorBean creator = liveBean.getCreator();
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
                            }
                        }
                    }
                });
            }
        }
    }

    public void registerReceiverWatchLiveActivity() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("WatchLiveActivity");
        registerReceiver(broadcastReceiver, intentFilter);
    }

    //表演支付状态
    private boolean payState;
    //表演支付金额
    private String showCost = "0";
    private String showId = "0";
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
                    case 101:
                        //主播创建表演，弹出提示支付弹框
                        //{name=嘿嘿嘿, roomId=4293, cost=9, status=0, userId=12709, id=75}
                        if (remote.get("data") != null) {
                            CustomShowBean customShow = JavaBeanMapUtils.mapToBean((Map) remote.get("data"), CustomShowBean.class);
                            showCost = customShow.getCost();
                            showId = customShow.getId();
                            initPayShowDialog(customShow);
                        }
                        break;
                    case 103:
                        //主播开始表演了，请支付5，没支付的看不到哦！
                        if (!payState) {
                            //没支付，弹框遮盖
                            Map Remote = message.getRemoteExtension();
                            CustomShowBean showBean = new CustomShowBean();
                            try {
                                JSONObject jsonObject = new JSONObject((String) Remote.get("data"));
                                showCost = (String) jsonObject.get("cost");
                                showId = (String) jsonObject.get("show_id");
                                showBean.setCost(showCost);
                                showBean.setId((String) jsonObject.get("show_id"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            initGrayPayDialog(showBean);
                        }
                        break;
                    case 104:
                        //主播表演结束了，谢谢大家支持！
                        if (!payState) {
                            //没支付，取消遮盖弹框和提示支付弹框
                            if (mNoPayShowDialog != null && mNoPayShowDialog.isShowing()) {
                                mNoPayShowDialog.dismiss();
                            }
                            if (mDialog != null && mDialog.isShowing()) {
                                mDialog.dismiss();
                            }
                        } else {
                            if (dialogForOrdershowEnd == null || !dialogForOrdershowEnd.isShowing())
                                showDialogForOrdershowEnd();
                        }
                        payState = false;
                        break;
                    case 105:
                        //105 进入房间，刷新最上边列表
//                        mrlLayoutLiveAudiences.autoRefresh();
                        requestNet();
                        break;
                    case 107:
                        LogUtils.i("1111111");
                        //礼物效果
                        showLit();
                        break;
                    case 108:
                        LogUtils.i("2222222");
                        //107、108是点亮、点赞
//                        showLit();
                        break;
                    case 109:
                        //收到 109 礼物消息 发送过来广播
                        CustomGiftBean customGiftBean = JavaBeanMapUtils.mapToBean(remote, CustomGiftBean.class);
                        if (Integer.parseInt(customGiftBean.getGift_item_number()) < 1) {
                            return;
                        }

                        LogUtils.e("customGiftBean" + customGiftBean.toString());
                        final UserInfoProvider.UserInfo userInfo = NimUIKit.getUserInfoProvider().getUserInfo(message.getFromAccount());
                        LogUtils.e("userInfo.getName()" + userInfo.getName() + "  getAccount  :" + userInfo.getAccount() + "  getAvatar  :" + userInfo.getAvatar());
                        String url = userInfo != null ? userInfo.getAvatar() : null;
                        String name;
                        if (mGiftList == null) {
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

//                        if (customGiftBean.getGift_item_index().equals("8")) {//皇家游轮
////                            mLuxuryGiftFireworksShowQueue.add(mSendGiftInfo);
////                            showFireworksAnimation(mSendGiftInfo);
//                        } else if (customGiftBean.getGift_item_index().equals("7")) {//礼花
////                            mLuxuryGiftCruisesShowQueue.add(mSendGiftInfo);
////                            showCruisesAnimation(mSendGiftInfo);
//                        } else if (customGiftBean.getGift_item_index().equals("15")) {//霓虹跑车
////                            mLuxuryGiftCarShowQueue.add(mSendGiftInfo);
////                            showRedCarAnimation(mSendGiftInfo);
//                        } else if (customGiftBean.getGift_item_index().equals("22")) {//飞机礼物
////                            mLuxuryGiftPlainShowQueue.add(mSendGiftInfo);
////                            showPlainAnimation(mSendGiftInfo);
//                        } else if (customGiftBean.getGift_item_index().equals("2")) {//测试2
//                            mLuxuryGiftPlainShowQueue.add(new SendGiftBean());
//                            showPlainAnimation(new SendGiftBean());//飞机
//                        } else if (customGiftBean.getGift_item_index().equals("1")) {//散花1
//                            mLuxuryGiftPlainShowQueue.add(new SendGiftBean());
//                            showFireworksAnimation(new SendGiftBean());//散花
//                        } else if (customGiftBean.getGift_item_index().equals("3")) {//3
//                            mLuxuryGiftPlainShowQueue.add(new SendGiftBean());
//                            showRedCarAnimation(mSendGiftInfo);//红色轿车
//                        } else {
//                            mLuxuryGiftPlainShowQueue.add(new SendGiftBean());
//                            showCruisesAnimation(mSendGiftInfo);//游轮
//                        }
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
//                        }

                        giftSendModel = null;
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
                    if (mUserCoin != null) {
                        mUserCoin.setText((Integer.parseInt(appUser.getGoldCoin()) - 1) + "");
                        appUser.setGoldCoin(mUserCoin.getText().toString());
                    } else
                        appUser.setGoldCoin((Integer.parseInt(appUser.getGoldCoin()) - 1) + "");
                    CacheUtils.saveObject(WatchLiveActivity.this, appUser, CacheUtils.USERINFO);
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


    /**
     * 展示游客详情
     *
     * @param customdateBean 用户信息
     * @author sll
     * @time 2016/11/25 9:40
     */
    public void showDialogForCallOther(final CustomdateBean customdateBean) {
        final View view = View.inflate(this, R.layout.dialog_video_room, null);
        AvatarView civ_image = (AvatarView) view.findViewById(R.id.civ_image);
        ImageView iv_sex = (ImageView) view.findViewById(R.id.iv_sex);
        ImageView iv_grade = (ImageView) view.findViewById(R.id.iv_grade);
        TextView tv_report = (TextView) view.findViewById(R.id.tv_report);
        TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
        TextView tv_location = (TextView) view.findViewById(R.id.tv_location);
        TextView tv_focus_num = (TextView) view.findViewById(R.id.tv_focus_num);
        TextView tv_funs_num = (TextView) view.findViewById(R.id.tv_funs_num);
        TextView tv_send_num = (TextView) view.findViewById(R.id.tv_send_num);
        TextView tv_gold_coin = (TextView) view.findViewById(R.id.tv_gold_coin);
        TextView tvID = (TextView) view.findViewById(R.id.tv_id);
        if (liveBean.getCreator().getId().equals(appUser.getId())) {
            tv_report.setText("禁言/撤销");
        } else {
            tv_report.setText("举报");
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
        String address = customdateBean.getAddress();
        tv_location.setText(address);
        tv_focus_num.setText(customdateBean.getFollowNum());
        tv_funs_num.setText(customdateBean.getFansNum());
        tv_send_num.setText(customdateBean.getSpendGoldCoin());
        tv_gold_coin.setText(customdateBean.getGoldCoin());
        tv_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //举报
                dialogForSelect.dismiss();
                reportDialog(customdateBean.getUserId());
            }
        });
        view.findViewById(R.id.iv_x).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //关闭
                dialogForSelect.dismiss();
            }
        });
        final TextView tvFoucs = (TextView) view.findViewById(R.id.tv_foucs);
        tvFoucs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //关注

                guanZhu(tvFoucs, customdateBean);
                //dialogForSelect.dismiss();
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
                startActivity(new Intent(WatchLiveActivity.this, OtherPersonHomePageActivity.class).putExtra(getString(R.string.visiti_person), customdateBean.getId()));
                //主页
                dialogForSelect.dismiss();
            }
        });
        dialogForSelect.setCanceledOnTouchOutside(true);
        dialogForSelect.setContentView(view);
        if (!dialogForSelect.isShowing())
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
                showDialogForOrdershowAck("修改失败");
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
     * @dw 获取礼物列表
     */
    private void getGiftList() {

        httpDatas.getDatanoloading("礼物列表", Request.Method.GET, UrlBuilder.GET_GIFT, null, myHandler, RequestCode.GET_GIFT);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.live_close:

                //退出登录
                if (mQuitDialog != null && !mQuitDialog.isShowing()) {
                    mQuitDialog.show();
                }

                break;
            //点播
            case R.id.iv_live_dianbo:
                new DialogView(mContext, mRoot, "请输入关键字", "确定", "取消", 1, new DialogView.MyCallback() {
                    @Override
                    public void refreshActivity() {
                    }

                    @Override
                    public void refreshActivity(String perform, String gold) {
                        if (com.lvshandian.partylive.utils.TextUtils.isEmpty(perform)) {
                            showToast("请输入要表演的内容");
                            return;
                        }
                        if (com.lvshandian.partylive.utils.TextUtils.isEmpty(gold)) {
                            showToast("请输入您想支付的金币数");
                            return;
                        }
                        /**
                         * 进行点播表演
                         */
                        roomVod("点播表演", perform, gold);
                    }

                    @Override
                    public void refreshActivity(String context, String minString, String maxString) {

                    }
                });

                break;
            //私信
            case R.id.iv_live_privatechat:
                sessionListFragment = new ChatRoomSessionListFragment();
                sessionListFragment.init(getSupportFragmentManager());
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.watch_room_message_fragment, sessionListFragment);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            //连麦
            case R.id.iv_live_lianmai:

                new DialogView(mContext, mRoot, liveBean.getPayForChat(), 1, new DialogView.MyCallback() {
                    @Override
                    public void refreshActivity() {
                    }

                    @Override
                    public void refreshActivity(String perform, String gold) {

                        if (com.lvshandian.partylive.utils.TextUtils.isEmpty(gold)) {
                            showToast("请输入您点播演员要支付金币数");
                            return;
                        }
                        /**
                         * 进行连麦支付
                         */
                        liannmai("连麦", gold);


                    }

                    @Override
                    public void refreshActivity(String context, String minString, String maxString) {

                    }
                });
                break;
            //礼物
            case R.id.iv_live_gift:

                if (null != mMediaPlayer && !mMediaPlayer.isPlaying()) {
                    Toast.makeText(WatchLiveActivity.this, "暂时还没有直播", Toast.LENGTH_SHORT);
                    return;
                }
                showGiftList();

                break;
            //分享
            case R.id.iv_live_share:
                if (null != mMediaPlayer && !mMediaPlayer.isPlaying()) {
                    Toast.makeText(WatchLiveActivity.this, "暂时还没有直播", Toast.LENGTH_SHORT);
                    return;
                }
                mController.openShare(this, false);

                break;
            //金票
            case R.id.ll_yp_labe:
                if (null != mMediaPlayer && !mMediaPlayer.isPlaying()) {
                    Toast.makeText(WatchLiveActivity.this, "暂时还没有直播", Toast.LENGTH_SHORT);
                    return;
                }
               /* Toast.makeText(WatchLiveActivity.this, "金票", Toast.LENGTH_SHORT).show();*/
                break;
            case R.id.live_jinpiao:
                if (null != mMediaPlayer && !mMediaPlayer.isPlaying()) {
                    Toast.makeText(WatchLiveActivity.this, "暂时还没有直播", Toast.LENGTH_SHORT);
                    return;
                }
                Intent intent = new Intent(this, GongXianActivity.class);
                intent.putExtra(getString(R.string.user_id), liveBean.getCreator().getId());
                startActivity(intent);
                break;
            case R.id.all_header:
                if (zhuBo != null) {
                    showDialogForCallOther(zhuBo);
                }
                break;
            case R.id.watch_room_jaizu:
                //家族
                getFamilyMember();
                break;
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
        hashMap.put("userId", liveBean.getCreator().getId());
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
        final BottomView familySelectView = new BottomView(this, R.style.BottomViewTheme_Transparent, R.layout.view_show_familyr_members);
        familySelectView.setAnimation(R.style.BottomToTopAnim);
        familySelectView.showBottomView(true);
        View mFamilyView = familySelectView.getView();
        RefreshRecyclerView familyMembers = (RefreshRecyclerView) mFamilyView.findViewById(R.id.view_family_members_recycler);
        FamilyMemberAdapter adapter = new FamilyMemberAdapter(1, this, mList, liveBean.getCreator().getId());
        GridLayoutManager mLayoutManager = new GridLayoutManager(WatchLiveActivity.this, 2, GridLayoutManager.HORIZONTAL, false);

        familyMembers.setLayoutManager(mLayoutManager);
        familyMembers.setAdapter(adapter);

        RecyclerViewManager.with(adapter, mLayoutManager)
                .setMode(RecyclerMode.NONE)
                .setOnItemClickListener(new RefreshRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerView.ViewHolder holder, int position) {
                        LogUtils.i("onItemClick:" + position + "--" + mList.get(position).getId());
                        if (!liveBean.getCreator().getId().equals(mList.get(position).getCreator().getId())) {
                            videoPlayEnd();
                            ifEnter(mList.get(position));
                        }
                    }
                })
                .into(familyMembers, this);
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
        httpDatas.getDataForJsoNoloading(details, Request.Method.POST, UrlBuilder.IF_ATTENTION, map, myHandler, handlerCode);
    }

    /**
     * 观众确认观看表演
     * userId为用户id，liveShowId为表演id
     *
     * @param liveShowId 为表演id
     */
    private void showWatch(String liveShowId) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("userId", appUser.getId());
        map.put("liveShowId", liveShowId);
        httpDatas.getDataForJsoNoloading("观众确认观看表演", Request.Method.POST, UrlBuilder.showWatch, map, myHandler, RequestCode.REQUEST_ROOM_SHOW_WATCH);
    }

    /**
     * 主播开始表演后观众观看表演
     * userId为用户id，liveShowId为表演id
     *
     * @param liveShowId 为表演id
     */
    private void showBuy(String liveShowId) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("userId", appUser.getId());
        map.put("liveShowId", liveShowId);
        httpDatas.getDataForJsoNoloading("主播开始表演后观众观看表演", Request.Method.POST, UrlBuilder.showBuy, map, myHandler, RequestCode.REQUEST_ROOM_SHOW_WATCH);
    }


    /**
     * 点播接口,用于是否可点播
     *
     * @param details 打印接口信息
     * @param name    点播节目名字
     * @param cost    点播节目花费金币
     */
    private void roomVod(String details, String name, String cost) {
        LogUtils.i("WangYi_CN", "请求接口，是否可以点播节目");
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("userId", appUser.getId());
        map.put("roomId", liveBean.getId());
        map.put("name", name);
        map.put("cost", cost);
        httpDatas.getDataForJsoNoloading(details, Request.Method.POST, UrlBuilder.room_vod, map, myHandler, RequestCode.REQUEST_ROOM_VOD);
    }


    /**
     * 连麦接口
     *
     * @param details 打印接口信息
     * @param
     * @param
     */
    private void liannmai(String details, String cost) {
        LogUtils.i("WangYi_CN", "请求接口，是否可以点播节目");
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("userId", appUser.getId());
        map.put("roomId", liveBean.getId());
        map.put("type", 2 + "");
        map.put("amount", cost);
        httpDatas.getDataForJsoNoloading(details, Request.Method.POST, UrlBuilder.room_lianmai, map, myHandler, RequestCode.ROOM_LIANMAI);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            videoPlayEnd();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private AVOptions mAVOptions;
    private int mSurfaceWidth = 0;
    private int mSurfaceHeight = 0;
    private String mVideoPath = "";
    private static final String[] DEFAULT_PLAYBACK_DOMAIN_ARRAY = {
            "live.hkstv.hk.lxdns.com"
    };
    private PLMediaPlayer mMediaPlayer;
    private SurfaceView mSurfaceView;
    private boolean mIsStopped = false;
    private boolean mIsActivityPaused = true;
    private static final int MESSAGE_ID_RECONNECTING = 0x01;

    //初始化播放器
    private void initLive(String mVideoPath) {
        try {
            PLNetworkManager.getInstance().startDnsCacheService(this, DEFAULT_PLAYBACK_DOMAIN_ARRAY);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        mSurfaceView = (SurfaceView) findViewById(R.id.SurfaceView);
        mSurfaceView.getHolder().addCallback(mCallback);
        mAVOptions = new AVOptions();
        int isLiveStreaming = getIntent().getIntExtra("liveStreaming", 1);
        // the unit of timeout is ms
        mAVOptions.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
        mAVOptions.setInteger(AVOptions.KEY_GET_AV_FRAME_TIMEOUT, 10 * 1000);
        mAVOptions.setInteger(AVOptions.KEY_PROBESIZE, 128 * 1024);
        // Some optimization with buffering mechanism when be set to 1
        mAVOptions.setInteger(AVOptions.KEY_LIVE_STREAMING, isLiveStreaming);
        if (isLiveStreaming == 1) {
            mAVOptions.setInteger(AVOptions.KEY_DELAY_OPTIMIZATION, 1);
        }
        // 1 -> hw codec enable, 0 -> disable [recommended]设置软硬解
//        int iCodec = getIntent().getIntExtra("mediaCodec", AVOptions.MEDIA_CODEC_AUTO);
        mAVOptions.setInteger(AVOptions.KEY_MEDIACODEC, AVOptions.MEDIA_CODEC_SW_DECODE);
        // whether start play automatically after prepared, default value is 1
        mAVOptions.setInteger(AVOptions.KEY_START_ON_PREPARED, 0);
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
//
//        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
//        mQosThread = new QosThread(activityManager, mHandler);
//        //视频流播放地址
////        String mrl="http://pull6.a8.com/live/1479350967188968.flv";
//        String mrl = laliu;
//        LogUtils.i("拉流地址" + mrl);
//        //视频播放器init
//        ksyMediaPlayer = new KSYMediaPlayer.Builder(mContext).build();
//        /*
//        * 参数：OnBufferingUpdateListener
//        * 功能：设置Buffering的监听器，当播放器在Buffering时会发出此回调，通知外界Buffering的进度
//        * 返回值：无
//        * */
//        ksyMediaPlayer.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
//        /*
//        * 参数：OnCompletionListener
//        * 功能：设置Completion的监听器，在视频播放完成后会发出此回调
//        * 返回值：无
//        * */
//        ksyMediaPlayer.setOnCompletionListener(mOnCompletionListener);
//        /*
//        * 参数：OnPreparedListener
//        * 功能：设置Prepared状态的监听器，在调用prepare()/prepareAsync()之后，正常完成解析后会通过此监听器通知外界。
//        * 返回值：无
//        * */
//        ksyMediaPlayer.setOnPreparedListener(mOnPreparedListener);
//        /*
//        *参数：OnInfoListener
//        *功能：设置Info监听器，播放器可通过此回调接口将消息通知开发者
//        *返回值：无
//        * */
//        ksyMediaPlayer.setOnInfoListener(mOnInfoListener);
//        /*参数：OnVideoSizeChangedListener
//        功能：设置VideoSizeChanged的监听器，当视频的宽度或高度发生变化时会发出次回调，通知外界视频的最新宽度和高度*/
//        ksyMediaPlayer.setOnVideoSizeChangedListener(mOnVideoSizeChangeListener);
//        /*参数：OnErrorListener
//        功能：设置Error监听器，当播放器遇到error时，会发出此回调并送出error code
//        返回值：无*/
//        ksyMediaPlayer.setOnErrorListener(mOnErrorListener);
//        /*参数：OnSeekCompleteListener
//        功能：设置Seek Complete的监听器，Seek操作完成后会有此回调
//        返回值：无*/
//        ksyMediaPlayer.setOnSeekCompleteListener(mOnSeekCompletedListener);
//        /*参数：screenOn 值为true时，播放时屏幕保持常亮，反之则否
//        功能：使用SurfaceHolder控制播放期间屏幕是否保持常亮。须调用接口setDisplay设置SurfaceHolder，此接口才有效
//        返回值：无*/
//        ksyMediaPlayer.setScreenOnWhilePlaying(true);
//        /*参数：直播音频缓存最大值，单位为秒
//        功能：设置直播音频缓存上限，由此可控制追赶功能的阈值。该值为负数时，关闭直播追赶功能。此接口只对直播有效
//        返回值：无*/
//        ksyMediaPlayer.setBufferTimeMax(5);
//
//
//        try {
//            ksyMediaPlayer.setDataSource(mrl);
//            ksyMediaPlayer.prepareAsync();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private SurfaceHolder.Callback mCallback = new SurfaceHolder.Callback() {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            prepare();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            mSurfaceWidth = width;
            mSurfaceHeight = height;
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            // release();
            releaseWithoutStop();
        }
    };

    public void releaseWithoutStop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.setDisplay(null);
        }
    }

    private PLMediaPlayer.OnPreparedListener mOnPreparedListener = new PLMediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(PLMediaPlayer mp) {
            Log.i(TAG, "On Prepared !");
            mMediaPlayer.start();
            mIsStopped = false;
        }
    };
    private PLMediaPlayer.OnVideoSizeChangedListener mOnVideoSizeChangedListener = new PLMediaPlayer.OnVideoSizeChangedListener() {
        public void onVideoSizeChanged(PLMediaPlayer mp, int width, int height) {
            Log.i(TAG, "onVideoSizeChanged, width = " + width + ",height = " + height);
            // resize the display window to fit the screen
            if (width != 0 && height != 0) {
                float ratioW = (float) width / (float) mSurfaceWidth;
                float ratioH = (float) height / (float) mSurfaceHeight;
                float ratio = Math.max(ratioW, ratioH);
                width = (int) Math.ceil((float) width / ratio);
                height = (int) Math.ceil((float) height / ratio);
                RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(mSurfaceWidth, mSurfaceHeight);
                mSurfaceView.setLayoutParams(layout);
            }
        }
    };

    private PLMediaPlayer.OnCompletionListener mOnCompletionListener = new PLMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(PLMediaPlayer mp) {
            Log.d(TAG, "Play Completed !");
            showToastTips("Play Completed !");
            finish();
        }
    };

    private void showToastTips(final String tips) {
        if (mIsActivityPaused) {
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast != null) {
                    mToast.cancel();
                }
                mToast = Toast.makeText(WatchLiveActivity.this, tips, Toast.LENGTH_SHORT);
                mToast.show();
            }
        });
    }


    private void prepare() {

        if (mMediaPlayer != null) {
            mMediaPlayer.setDisplay(mSurfaceView.getHolder());
            return;
        }

        try {
            mMediaPlayer = new PLMediaPlayer(this, mAVOptions);

            mMediaPlayer.setOnPreparedListener(mOnPreparedListener);
            mMediaPlayer.setOnVideoSizeChangedListener(mOnVideoSizeChangedListener);
            mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
            mMediaPlayer.setOnErrorListener(mOnErrorListener);
            mMediaPlayer.setOnInfoListener(mOnInfoListener);
            mMediaPlayer.setOnBufferingUpdateListener(mOnBufferingUpdateListener);

            // set replay if completed
            // mMediaPlayer.setLooping(true);

            mMediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);

            mMediaPlayer.setDataSource(mVideoPath);
            LogUtils.i("拉流地址" + mVideoPath);
            mMediaPlayer.setDisplay(mSurfaceView.getHolder());
            mMediaPlayer.prepareAsync();
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private PLMediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener = new PLMediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(PLMediaPlayer mp, int percent) {
            Log.d(TAG, "onBufferingUpdate: " + percent + "%");
        }
    };
    private PLMediaPlayer.OnInfoListener mOnInfoListener = new PLMediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(PLMediaPlayer mp, int what, int extra) {
            Log.i(TAG, "OnInfo, what = " + what + ", extra = " + extra);
            switch (what) {
                case PLMediaPlayer.MEDIA_INFO_BUFFERING_START:
//                    mLoadingView.setVisibility(View.VISIBLE);
                    startloading();
                    break;
                case PLMediaPlayer.MEDIA_INFO_BUFFERING_END:
                case PLMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
//                    mLoadingView.setVisibility(View.GONE);
                    stoploading();
                    HashMap<String, String> meta = mMediaPlayer.getMetadata();
                    Log.i(TAG, "meta: " + meta.toString());
//                    showToastTips(meta.toString());
                    break;
                case PLMediaPlayer.MEDIA_INFO_SWITCHING_SW_DECODE:
                    Log.i(TAG, "Hardware decoding failure, switching software decoding!");
                    break;
                default:
                    break;
            }
            return true;
        }
    };


    private PLMediaPlayer.OnErrorListener mOnErrorListener = new PLMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(PLMediaPlayer mp, int errorCode) {
            boolean isNeedReconnect = false;
            Log.e(TAG, "Error happened, errorCode = " + errorCode);
            switch (errorCode) {
                case PLMediaPlayer.ERROR_CODE_INVALID_URI:
                    showToastTips("Invalid URL !");
                    break;
                case PLMediaPlayer.ERROR_CODE_404_NOT_FOUND:
                    showToastTips("404 resource not found !");
                    break;
                case PLMediaPlayer.ERROR_CODE_CONNECTION_REFUSED:
                    showToastTips("Connection refused !");
                    break;
                case PLMediaPlayer.ERROR_CODE_CONNECTION_TIMEOUT:
                    showToastTips("Connection timeout !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_EMPTY_PLAYLIST:
                    showToastTips("Empty playlist !");
                    break;
                case PLMediaPlayer.ERROR_CODE_STREAM_DISCONNECTED:
                    showToastTips("Stream disconnected !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_IO_ERROR:
                    showToastTips("Network IO Error !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_UNAUTHORIZED:
                    showToastTips("Unauthorized Error !");
                    break;
                case PLMediaPlayer.ERROR_CODE_PREPARE_TIMEOUT:
                    showToastTips("Prepare timeout !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_READ_FRAME_TIMEOUT:
                    showToastTips("Read frame timeout !");
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.ERROR_CODE_HW_DECODE_FAILURE:
                    mAVOptions.setInteger(AVOptions.KEY_MEDIACODEC, AVOptions.MEDIA_CODEC_SW_DECODE);
                    isNeedReconnect = true;
                    break;
                case PLMediaPlayer.MEDIA_ERROR_UNKNOWN:
                    break;
                default:
                    showToastTips("unknown error !");
                    break;
            }
            // Todo pls handle the error status here, reconnect or call finish()
            release();
            if (isNeedReconnect) {
                sendReconnectMessage();
            } else {
                finish();
            }
            // Return true means the error has been handled
            // If return false, then `onCompletion` will be called
            return true;
        }
    };

    private void sendReconnectMessage() {
        showToastTips("正在重连...");
        startloading();
//        mLoadingView.setVisibility(View.VISIBLE);
        mHandler.removeCallbacksAndMessages(null);
        mHandler.sendMessageDelayed(mHandler.obtainMessage(MESSAGE_ID_RECONNECTING), 500);
    }


    public void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    //开始动画
    private void startloading() {

        ivLoad.setImageResource(R.drawable.live_loading);
        animationDrawable = (AnimationDrawable) ivLoad.getDrawable();
        animationDrawable.start();

//        myHandler.sendEmptyMessage(121212);

    }

    private class UIHandler extends Handler {

        WatchLiveActivity mActivity;

        public UIHandler(WatchLiveActivity activty) {
            mActivity = activty;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_SEEKBAR:
                    break;
                case HIDDEN_SEEKBAR:
                    break;
                case UPDATE_QOS:
                    break;
            }
        }
    }

    //结束动画
    private void stoploading() {
        rlLoading.setVisibility(View.GONE);
        animationDrawable = (AnimationDrawable) ivLoad.getDrawable();
        animationDrawable.stop();
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("WatchLive Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("userId", appUser.getId());
        map.put("roomId", liveBean.getId());
        if (null != mGiftSelectView) {
            mGiftSelectView.dismissBottomView();
        }
        httpDatas.getDataForJsoNoloading("退出直播间", Request.Method.POST, UrlBuilder.roomExit, map, myHandler, RequestCode.REQUEST_ROOM_EXIT);

        timer.cancel();
        release();
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.abandonAudioFocus(null);
        registerObservers(false);
        registerReceiveCustom(false);
        registerMsgUnreadInfoObserver(false);
        registerSystemMessageObservers(false);
        unregisterReceiver(broadcastReceiver);
        barrageview.setSentenceList(new ArrayList<BarrageDateBean>());
    }


    //直播结束释放资源
    public void videoPlayEnd() {

        if (null != mHandler) {
            mHandler.removeCallbacksAndMessages(null);
        }

        finish();

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
                Toast.makeText(WatchLiveActivity.this, R.string.net_broken, Toast.LENGTH_SHORT).show();
            }
            LogUtils.i(TAG, "chat room online status changed to " + chatRoomStatusChangeData.status.name());
        }
    };
    Observer<ChatRoomKickOutEvent> kickOutObserver = new Observer<ChatRoomKickOutEvent>() {
        @Override
        public void onEvent(ChatRoomKickOutEvent chatRoomKickOutEvent) {
//            Toast.makeText(WatchLiveActivity.this, "被踢出聊天室，原因:" + chatRoomKickOutEvent.getReason(), Toast.LENGTH_SHORT).show();
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
            messageFragment.init(roomId, liveBean.getId());
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


    //连送按钮隐藏
    private void recoverySendGiftBtnLayout(GiftBean giftBean) {
        lian_nuum.setText("1");
        ((TextView) mSendGiftLian.findViewById(R.id.tv_show_gift_outtime)).setText("");
        mSendGiftLian.setVisibility(View.GONE);
        mSendGiftBtn.setVisibility(View.VISIBLE);
        mShowGiftSendOutTime = 2;
        dianjiliansong(GiFT_NUM, giftBean);

    }

    //展示礼物列表
    private void showGiftList() {
//        if(mYpNum == null){
//            return;
//        }
        //mButtonMenuFrame.setVisibility(View.GONE);//隐藏菜单栏
        mGiftSelectView = new BottomView(this, R.style.BottomViewTheme_Transparent, R.layout.view_show_viewpager);
        mGiftSelectView.setAnimation(R.style.BottomToTopAnim);
        mGiftSelectView.showBottomView(true);
        View mGiftView = mGiftSelectView.getView();
        mUserCoin = (TextView) mGiftView.findViewById(R.id.tv_show_select_user_coin);
        ll_user_coin = (LinearLayout) mGiftView.findViewById(R.id.gift_coin_ll);
        ll_user_coin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoActivity(ChargeMoneyActivity.class, false);
            }
        });
        mUserCoin.setText(appUser.getGoldCoin());

        mVpGiftView = (ViewPager) mGiftView.findViewById(R.id.vp_gift_page);
        mSendGiftLian = (RelativeLayout) mGiftView.findViewById(R.id.iv_show_send_gift_lian);
        lian_nuum = (TextView) mGiftView.findViewById(R.id.lian_num);
        mSendGiftLian.setOnClickListener(new View.OnClickListener() {//礼物连送
            @Override
            public void onClick(View v) {
//                sendGift("y");//礼物发送
                GiFT_NUM++;
                mShowGiftSendOutTime = 2;
                lian_nuum.setText(GiFT_NUM + "");
                ((TextView) mSendGiftLian.findViewById(R.id.tv_show_gift_outtime)).setText(String.valueOf(mShowGiftSendOutTime));
            }
        });
        mSendGiftBtn = (Button) mGiftView.findViewById(R.id.btn_show_send_gift);
        mSendGiftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GiFT_NUM++;
                onClickSendGift(v);
            }
        });
        if (mGiftViews != null) { //表示已经请求过数据不再向下执行
            fillGift();
            return;
        }

    }

    /**
     * @param v btn
     * @dw 点击赠送礼物按钮
     */
    private void onClickSendGift(View v) {//赠送礼物
        if (mUserCoin.getText().toString().equals("0")) {

            showToast("金币不足，请充值");
            return;
        }

//        if (!mConnectionState) {//没有连接ok
//            return;
//        }
        if ((mSelectedGiftItem != null) && (Integer.parseInt(mSelectedGiftItem.getCombo()) == 1)) {//连送礼物
            v.setVisibility(View.GONE);
            mHandler.postDelayed(new Runnable() {//开启连送定时器
                @Override
                public void run() {
                    if (mShowGiftSendOutTime == 1) {
                        recoverySendGiftBtnLayout(mSelectedGiftItem);

                        mHandler.removeCallbacks(this);
                        return;
                    }
                    mHandler.postDelayed(this, 1000);
                    mShowGiftSendOutTime--;
                    ((TextView) mSendGiftLian.findViewById(R.id.tv_show_gift_outtime)).setText(String.valueOf(mShowGiftSendOutTime));

                }
            }, 1000);

            sendGift("y");//礼物发送
        } else {
            sendGift("n");//礼物发送
        }
    }

    /**
     * @dw 礼物列表填充
     */
    private void fillGift() {
        if (null == mVpGiftAdapter && null != mGiftResStr) {
//            if (mGiftList.size() == 0) {
//                try {
//                    JSONArray giftListJson = new JSONArray(mGiftResStr);
//                    for (int i = 0; i < giftListJson.length(); i++) {
//                        mGiftList.add(mGson.fromJson(giftListJson.getString(i), GiftBean.class));
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
            //礼物item填充
            List<View> mGiftViewList = new ArrayList<>();
            int index = 0;

            for (int i = 0; i < 4; i++) {
                View v = getLayoutInflater().inflate(R.layout.view_show_gifts_gv, null);
                mGiftViewList.add(v);
                List<GiftBean> l = new ArrayList<>();
                for (int j = 0; j < 8; j++) {
                    if (index >= mGiftList.size()) {
                        break;
                    }
                    l.add(mGiftList.get(index));
                    index++;
                }
                mGiftViews.add((GridView) v.findViewById(R.id.gv_gift_list));
                mGiftViews.get(i).setAdapter(new GridViewAdapter(l, getContext()));
                mGiftViews.get(i).setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        giftItemClick(parent, view, position);
                    }
                });
            }
            mVpGiftAdapter = new ViewPageGridViewAdapter(mGiftViewList);
        }
        mVpGiftView.setAdapter(mVpGiftAdapter);
        //mVpGiftView.setCurrentItem(0);
    }

    /**
     * @dw 赠送礼物单项被选中
     */
    private void giftItemClick(AdapterView<?> parent, View view, int position) {
        if ((GiftBean) parent.getItemAtPosition(position) != mSelectedGiftItem) {
            recoverySendGiftBtnLayout(mSelectedGiftItem);
            mSelectedGiftItem = (GiftBean) parent.getItemAtPosition(position);
            LogUtils.e("mSelectedGiftItem" + mSelectedGiftItem.toString());
            //点击其他礼物
            changeSendGiftBtnStatue(true);
            for (int i = 0; i < mGiftViews.size(); i++) {
                for (int j = 0; j < mGiftViews.get(i).getChildCount(); j++) {
                    if (((GiftBean) mGiftViews.get(i).getItemAtPosition(j)).getCombo().equals("1")) {
                        mGiftViews.get(i).getChildAt(j).findViewById(R.id.iv_show_gift_selected).setBackgroundResource(R.mipmap.icon_continue_gift);
                    } else {
                        mGiftViews.get(i).getChildAt(j).findViewById(R.id.iv_show_gift_selected).setBackgroundResource(0);
                    }

                }
            }
            view.findViewById(R.id.iv_show_gift_selected).setBackgroundResource(R.mipmap.icon_continue_gift_chosen);

        } else {
            if (((GiftBean) parent.getItemAtPosition(position)).getCombo().equals("1")) {
                view.findViewById(R.id.iv_show_gift_selected).setBackgroundResource(R.mipmap.icon_continue_gift);
            } else {
                view.findViewById(R.id.iv_show_gift_selected).setBackgroundResource(0);
            }
            mSelectedGiftItem = null;
            changeSendGiftBtnStatue(false);

        }
    }


    /**
     * 点击练送
     */
    private void dianjiliansong(int num, GiftBean msendbean) {
        if (num == 0) {
            return;
        }
//        LogUtils.i("多少钱多少钱" + (msendbean.toString() + ""));
        if (null == msendbean.getCurrencyAmount()) {
            return;
        }
        if (TextUtils.isEmpty(msendbean.getCurrencyAmount())) {
            return;
        }
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("userId", appUser.getId());
        map.put("roomId", liveBean.getId());
        map.put("amount", (Integer.parseInt(msendbean.getCurrencyAmount()) * num) + "");
//        LogUtils.i("多少钱" + (Integer.parseInt(msendbean.getCurrencyAmount()) * num) + "");
        sendgiftmoney = Integer.parseInt(msendbean.getCurrencyAmount()) * num;
        httpDatas.getDataForJsoNoloading("赠送礼物", Request.Method.POST, UrlBuilder.SEND_GIFT, map, myHandler, RequestCode.SEND_GIFT);
    }

    /**
     * @param statue 开启or关闭
     * @dw 赠送礼物按钮状态修改
     */
    private void changeSendGiftBtnStatue(boolean statue) {
        if (statue) {
            mSendGiftBtn.setBackgroundColor(getResources().getColor(R.color.global));
            mSendGiftBtn.setEnabled(true);
        } else {
            mSendGiftBtn.setBackgroundColor(getResources().getColor(R.color.light_gray2));
            mSendGiftBtn.setEnabled(false);
        }
    }

    /**
     * @param isOutTime 是否连送超时(如果是连送礼物的情况下)
     * @dw 赠送礼物请求服务端获取数据扣费
     */
    private void sendGift(final String isOutTime) {

        if (mSelectedGiftItem != null) {
            if (mSelectedGiftItem.getCombo().equals("1")) {
                mSendGiftLian.setVisibility(View.VISIBLE);
                //练送礼物处理，如果练送返回
                return;
            } else {
                changeSendGiftBtnStatue(true);
            }

            ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
            map.put("userId", appUser.getId());
            map.put("roomId", liveBean.getId());
            map.put("amount", mSelectedGiftItem.getCurrencyAmount());
            sendgiftmoney = Integer.parseInt(mSelectedGiftItem.getCurrencyAmount());
            httpDatas.getDataForJsoNoloading("赠送礼物", Request.Method.POST, UrlBuilder.SEND_GIFT, map, myHandler, RequestCode.SEND_GIFT);


        }
    }


    protected void showLit() {

        ThreadManager.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                int[] heartImg = new int[]{R.mipmap.plane_heart_cyan, R.mipmap.plane_heart_pink, R.mipmap.plane_heart_red, R.mipmap.plane_heart_yellow};
                final Random ran = new Random();
                final ImageView heart = new ImageView(WatchLiveActivity.this);
                //点亮的背景图片
                heart.setBackgroundResource(heartImg[ran.nextInt(4)]);
                //尺寸参数
                final int screenW = getWindowManager().getDefaultDisplay().getWidth();

                heart.setLayoutParams(new AutoRelativeLayout.LayoutParams(screenW / 10, screenW / 10));
////                heart.setLayoutParams(new RelativeLayout.LayoutParams(TDevice.pxTodip(ShowLiveActivityBase.this,100)
//                        ,TDevice.pxTodip(ShowLiveActivityBase.this,100)));

                //x位置
                heart.setX(screenW - screenW / 4);
                //y位置
                heart.setY(getWindowManager().getDefaultDisplay().getHeight() - getWindowManager().getDefaultDisplay().getHeight() / 8);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRoot.addView(heart);
                        //点亮xy 平移动画
                        ObjectAnimator translationX = ObjectAnimator.ofFloat(heart, "translationX", ran.nextInt(500) + (screenW - 200) - (screenW / 3));
                        ObjectAnimator translationY = ObjectAnimator.ofFloat(heart, "translationY", ran.nextInt(getWindowManager().getDefaultDisplay().getHeight() / 2) + 200);
                        //渐变动画
                        ObjectAnimator alpha = ObjectAnimator.ofFloat(heart, "alpha", 0f);
                        //xy放大动画
                        ObjectAnimator scaleX = ObjectAnimator.ofFloat(heart, "scaleX", 0.8f, 1f);
                        ObjectAnimator scaleY = ObjectAnimator.ofFloat(heart, "scaleY", 0.8f, 1f);
                        AnimatorSet set = new AnimatorSet();
                        set.playTogether(translationX, translationY, alpha, scaleX, scaleY);
                        set.setDuration(5000);
                        set.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                if (null != mRoot) {
                                    mRoot.removeView(heart);
                                }
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });
                        set.start();
                    }
                });
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


    private void lianmailaliu(String lmll) {
        lmFm.setVisibility(View.VISIBLE);

        mSurfaceHolder2 = videoLian.getHolder();
//        mSurfaceHolder2.addCallback(mSurfaceCallback);
//        隐藏输入框
//        mVideoSurfaceView.setOnTouchListener(mTouchListener);
        videoLian.setKeepScreenOn(true);
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);


        //视频流播放地址
//        String mrl="http://pull6.a8.com/live/1479350967188968.flv";
        String mrl = lmll;
        LogUtils.i("拉流地址" + mrl);
        //视频播放器init
        /*
        * 参数：OnBufferingUpdateListener
        * 功能：设置Buffering的监听器，当播放器在Buffering时会发出此回调，通知外界Buffering的进度
        * 返回值：无
        * */
//        ksyMediaPlayer.setOnBufferingUpdateListener(mOnBufferingUpdateListener2);
        /*
        * 参数：OnCompletionListener
        * 功能：设置Completion的监听器，在视频播放完成后会发出此回调
        * 返回值：无
        * */
//        ksyMediaPlayer.setOnCompletionListener(mOnCompletionListener2);
        /*
        * 参数：OnPreparedListener
        * 功能：设置Prepared状态的监听器，在调用prepare()/prepareAsync()之后，正常完成解析后会通过此监听器通知外界。
        * 返回值：无
        * */
//        ksyMediaPlayer.setOnPreparedListener(mOnPreparedListener2);
        /*
        *参数：OnInfoListener
        *功能：设置Info监听器，播放器可通过此回调接口将消息通知开发者
        *返回值：无
        * */
//        ksyMediaPlayer.setOnInfoListener(mOnInfoListener2);
        /*参数：OnVideoSizeChangedListener
        功能：设置VideoSizeChanged的监听器，当视频的宽度或高度发生变化时会发出次回调，通知外界视频的最新宽度和高度*/
//        ksyMediaPlayer.setOnVideoSizeChangedListener(mOnVideoSizeChangeListener2);
        /*参数：OnErrorListener
        功能：设置Error监听器，当播放器遇到error时，会发出此回调并送出error code
        返回值：无*/
//        ksyMediaPlayer.setOnErrorListener(mOnErrorListener2);
        /*参数：OnSeekCompleteListener
        功能：设置Seek Complete的监听器，Seek操作完成后会有此回调
        返回值：无*/
//        ksyMediaPlayer.setOnSeekCompleteListener(mOnSeekCompletedListener2);
        /*参数：screenOn 值为true时，播放时屏幕保持常亮，反之则否
        功能：使用SurfaceHolder控制播放期间屏幕是否保持常亮。须调用接口setDisplay设置SurfaceHolder，此接口才有效
        返回值：无*/
        /*参数：直播音频缓存最大值，单位为秒
        功能：设置直播音频缓存上限，由此可控制追赶功能的阈值。该值为负数时，关闭直播追赶功能。此接口只对直播有效
        返回值：无*/


    }

    protected List<SendGiftBean> mLuxuryGiftFireworksShowQueue = new ArrayList<>();

    protected List<SendGiftBean> mLuxuryGiftCruisesShowQueue = new ArrayList<>();

    protected List<SendGiftBean> mLuxuryGiftCarShowQueue = new ArrayList<>();

    protected List<SendGiftBean> mLuxuryGiftPlainShowQueue = new ArrayList<>();

    protected boolean isFireworkGiftAnimationPlayEnd = true;

    protected boolean isCruisesGiftAnimationPlayEnd = true;

    protected boolean isCarGiftAnimationPlayEnd = true;

    protected boolean isPlainGiftAnimationPlayEnd = true;


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
                    final TextView flower = new TextView(WatchLiveActivity.this);
                    flower.setX(x);
                    flower.setText("❀");
                    flower.setTextColor(getResources().getColor(colorArr[color]));
                    flower.setTextSize(50);
                    //每个花朵的动画
                    mRootAnimation.addView(flower);
                    flower.animate().alpha(0f).translationX(tx).translationY(ty).setDuration(5000).start();

                }
                //飞机移动到中间后延迟一秒钟,开始继续前行-x
                mHandler.postDelayed(new Runnable() {
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
                mHandler.postDelayed(new Runnable() {
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
                mHandler.postDelayed(new Runnable() {
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
        mHandler.postDelayed(new Runnable() {
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
