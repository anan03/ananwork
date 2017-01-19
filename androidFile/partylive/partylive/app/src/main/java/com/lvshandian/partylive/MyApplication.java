package com.lvshandian.partylive;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.android.volley.toolbox.Volley;
import com.lvshandian.partylive.service.MyPushIntentService;
import com.lvshandian.partylive.utils.LogUtils;
import com.lvshandian.partylive.utils.XUtils;
import com.lvshandian.partylive.wangyiyunxin.common.util.crash.AppCrashHandler;
import com.lvshandian.partylive.wangyiyunxin.common.util.sys.SystemUtil;
import com.lvshandian.partylive.wangyiyunxin.config.DemoCache;
import com.lvshandian.partylive.wangyiyunxin.config.ExtraOptions;
import com.lvshandian.partylive.wangyiyunxin.config.preference.Preferences;
import com.lvshandian.partylive.wangyiyunxin.config.preference.UserPreferences;
import com.netease.nim.uikit.ImageLoaderKit;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.cache.FriendDataCache;
import com.netease.nim.uikit.cache.NimUserInfoCache;
import com.netease.nim.uikit.cache.TeamDataCache;
import com.netease.nim.uikit.contact.ContactProvider;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.qiniu.pili.droid.streaming.StreamingEnv;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;

import com.yixia.camera.VCamera;
import com.yixia.camera.util.DeviceUtils;
import com.zhy.autolayout.config.AutoLayoutConifg;

import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhang on 2016/10/11.
 * 创建app
 */
public class MyApplication extends MultiDexApplication {


    public static Context mContext;
    private static com.android.volley.RequestQueue requestQueue;
    private UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
        @Override
        public void dealWithCustomAction(Context context, UMessage msg) {
            Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
        }

        @Override
        public void openActivity(Context context, UMessage uMessage) {

        }
    };

    public static com.android.volley.RequestQueue requestQueueiInstance() {

        if (requestQueue == null) {
            synchronized (MyApplication.class) {
                if (requestQueue == null) {


                    requestQueue = Volley.newRequestQueue(mContext);
                }
            }
        }

        return requestQueue;
    }

    @Override
    public void onCreate() {

        super.onCreate();
        mContext = this;
        //初始化牵牛云
        StreamingEnv.init(getApplicationContext());
        //初始化数据库
        ActiveAndroid.initialize(this);
        AutoLayoutConifg.getInstance().useDeviceSize();
        x.Ext.init(this);//Xutils初始化
        XUtils.oncreateUtils();
        initImagLoad();
        DemoCache.setContext(this);
        NIMClient.init(this, getLoginInfo(), getOptions());

        ExtraOptions.provide();

        // crash handler
        AppCrashHandler.getInstance(this);

        if (inMainProcess()) {
            // init pinyin
//            PinYin.init(this);
//            PinYin.validate();

            // 初始化UIKit模块
            initUIKit();

            // 注册通知消息过滤器
//            registerIMMessageFilter();

            // 初始化消息提醒
            NIMClient.toggleNotification(UserPreferences.getNotificationToggle());

            // 注册网络通话来电
//            enableAVChat();

            // 注册白板会话
//            enableRTS();

            // 注册语言变化监听
//            registerLocaleReceiver(true);
        }

        initUMengShare();
        initUmTuiSong();

        //		// 设置拍摄视频缓存路径
        File dcim = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        if (DeviceUtils.isZte()) {
            if (dcim.exists()) {
                VCamera.setVideoCachePath(dcim + "/recoder/");
            } else {
                VCamera.setVideoCachePath(dcim.getPath().replace("/sdcard/",
                        "/sdcard-ext/")
                        + "/recoder/");
            }
        } else {
            VCamera.setVideoCachePath(dcim + "/WeChatJuns/");
        }

//		VCamera.setVideoCachePath(FileUtils.getRecorderPath());
        // 开启log输出,ffmpeg输出到logcat
        VCamera.setDebugMode(true);
        // 初始化拍摄SDK，必须
        VCamera.initialize(this);

    }

    /**
     * 友盟推送初始
     */
    private void initUmTuiSong() {
        PushAgent mPushAgent = PushAgent.getInstance(this);

        // mPushAgent.addAlias(String alias,String aliasType);

        mPushAgent.onAppStart();
        // mPushAgent.getTagManager().add(String tag……);
        //目前每个用户tag限制在1024个， 每个tag 最大256个字符。
        mPushAgent.setMessageHandler(messageHandler);
        mPushAgent.setDisplayNotificationNumber(5);
        mPushAgent.setNotificationClickHandler(notificationClickHandler);

        mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);
        mPushAgent.enable(new IUmengRegisterCallback() {
            @Override
            public void onRegistered(String registrationId) {
                LogUtils.e("设备TOLEK: " + registrationId);
            }
        });


    }

    private UmengMessageHandler messageHandler = new UmengMessageHandler() {
        @Override
        public void dealWithCustomMessage(Context context, UMessage uMessage) {
            //dealWithCustomMessage()方法负责处理自定义消息，需由用户处理
            String custom = uMessage.custom;
            LogUtils.e("消息Custom: " + custom);
        }

        @Override
        public void dealWithNotificationMessage(Context context, UMessage uMessage) {
            //dealWithNotificationMessage()方法负责处理通知消息，该方法已经由消息推送SDK 完成
            //若开发者想自己处理通知消息，可以重写方法dealWithNotificationMessage()
            super.dealWithNotificationMessage(context, uMessage);
            String custom = uMessage.custom;
            LogUtils.e("消息Notification: " + custom);
        }
    };

    /**
     * 友盟分享初始化
     */
    private void initUMengShare() {


  /*      PlatformConfig.setWeixin("wx6e96fc0c6b3c92ec", "29cfcc63972b549198f4ab44d9a50f60");
        PlatformConfig.setQQZone("100371282", "aed9b0303e3ed1e27bae87c33761161d");*/
       /* PlatformConfig.setTencentWeibo("801307650", "ae36f4ee3946e1cbb98d6965b0b2ff5c");*/
    }

    public boolean inMainProcess() {
        String packageName = getPackageName();
        String processName = SystemUtil.getProcessName(this);
        return packageName.equals(processName);
    }

    private void initUIKit() {
        // 初始化，需要传入用户信息提供者
        NimUIKit.init(this, infoProvider, contactProvider);

        // 设置地理位置提供者。如果需要发送地理位置消息，该参数必须提供。如果不需要，可以忽略。
//        NimUIKit.setLocationProvider(new NimDemoLocationProvider());

        // 会话窗口的定制初始化。
//        SessionHelper.init();

        // 通讯录列表定制初始化
//        ContactHelper.init();
    }

    private UserInfoProvider infoProvider = new UserInfoProvider() {
        @Override
        public UserInfo getUserInfo(String account) {
            UserInfo user = NimUserInfoCache.getInstance().getUserInfo(account);
            if (user == null) {
                NimUserInfoCache.getInstance().getUserInfoFromRemote(account, null);
            }

            return user;
        }

        @Override
        public int getDefaultIconResId() {
            return R.drawable.avatar_def;
        }

        @Override
        public Bitmap getTeamIcon(String teamId) {
            /**
             * 注意：这里最好从缓存里拿，如果读取本地头像可能导致UI进程阻塞，导致通知栏提醒延时弹出。
             */
            // 从内存缓存中查找群头像
            Team team = TeamDataCache.getInstance().getTeamById(teamId);
            if (team != null) {
                Bitmap bm = ImageLoaderKit.getNotificationBitmapFromCache(team.getIcon());
                if (bm != null) {
                    return bm;
                }
            }

            // 默认图
            Drawable drawable = getResources().getDrawable(R.drawable.nim_avatar_group);
            if (drawable instanceof BitmapDrawable) {
                return ((BitmapDrawable) drawable).getBitmap();
            }

            return null;
        }

        @Override
        public Bitmap getAvatarForMessageNotifier(String account) {
            /**
             * 注意：这里最好从缓存里拿，如果读取本地头像可能导致UI进程阻塞，导致通知栏提醒延时弹出。
             */
            UserInfo user = getUserInfo(account);
            return (user != null) ? ImageLoaderKit.getNotificationBitmapFromCache(user.getAvatar()) : null;
        }

        @Override
        public String getDisplayNameForMessageNotifier(String account, String sessionId, SessionTypeEnum sessionType) {
            String nick = null;
            if (sessionType == SessionTypeEnum.P2P) {
                nick = NimUserInfoCache.getInstance().getAlias(account);
            } else if (sessionType == SessionTypeEnum.Team) {
                nick = TeamDataCache.getInstance().getTeamNick(sessionId, account);
                if (TextUtils.isEmpty(nick)) {
                    nick = NimUserInfoCache.getInstance().getAlias(account);
                }
            }
            // 返回null，交给sdk处理。如果对方有设置nick，sdk会显示nick
            if (TextUtils.isEmpty(nick)) {
                return null;
            }

            return nick;
        }
    };

    private ContactProvider contactProvider = new ContactProvider() {
        @Override
        public List<UserInfoProvider.UserInfo> getUserInfoOfMyFriends() {
            List<NimUserInfo> nimUsers = NimUserInfoCache.getInstance().getAllUsersOfMyFriend();
            List<UserInfoProvider.UserInfo> users = new ArrayList<>(nimUsers.size());
            if (!nimUsers.isEmpty()) {
                users.addAll(nimUsers);
            }

            return users;
        }

        @Override
        public int getMyFriendsCount() {
            return FriendDataCache.getInstance().getMyFriendCounts();
        }

        @Override
        public String getUserDisplayName(String account) {
            return NimUserInfoCache.getInstance().getUserDisplayName(account);
        }
    };

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private LoginInfo getLoginInfo() {
        String account = Preferences.getUserAccount();
        String token = Preferences.getUserToken();
        LogUtils.i("WangYi", "account:" + account + "\ntoken:" + token);

        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
            DemoCache.setAccount(account.toLowerCase());
            return new LoginInfo(account, token);
        } else {
            return null;
        }
    }

    private SDKOptions getOptions() {
        SDKOptions options = new SDKOptions();

        // 如果将新消息通知提醒托管给SDK完成，需要添加以下配置。
        StatusBarNotificationConfig config = UserPreferences.getStatusConfig();
        if (config == null) {
            config = new StatusBarNotificationConfig();
        }

        // 通知铃声的uri字符串
        config.notificationSound = "android.resource://com.netease.nim.demo/raw/msg";

        // 呼吸灯配置
        config.ledARGB = Color.GREEN;
        config.ledOnMs = 1000;
        config.ledOffMs = 1500;

        options.statusBarNotificationConfig = config;
        DemoCache.setNotificationConfig(config);
        UserPreferences.setStatusConfig(config);

        // 配置保存图片，文件，log等数据的目录
        String sdkPath = Environment.getExternalStorageDirectory() + "/" + getPackageName() + "/nim";
        options.sdkStorageRootPath = sdkPath;

        // 配置数据库加密秘钥
        options.databaseEncryptKey = "NETEASE";

        // 配置是否需要预下载附件缩略图
        options.preloadAttach = true;

        // 用户信息提供者
//        options.userInfoProvider = infoProvider;

        return options;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mContext = null;
        ActiveAndroid.dispose();
    }


    private static List<Activity> listActivity = new ArrayList<>();

    public static void setListActivity(Activity activity) {
        listActivity.add(activity);
    }

    public static void finishActivity() {

        for (Activity activity : listActivity
                ) {
            activity.finish();
        }
    }

    public static void finishLastActivity() {
        if (listActivity.size() > 0) {
            listActivity.get(listActivity.size() - 1).finish();
        }
    }

    public void initImagLoad() {
        //默认的方式
//ImageLoaderConfiguration configuration=ImageLoaderConfiguration.createDefault(this);

//sdcard的路径
        String sdPath = Environment.getExternalStorageDirectory().getPath();

//自定义的方式
        com.nostra13.universalimageloader.core.ImageLoaderConfiguration configuration = new com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(480, 800)//内存缓存文件的最大长宽
                .discCacheExtraOptions(480, 800, null)//本地缓存的详细信息
                .threadPoolSize(3)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)//设置当前线程的优先级
                .tasksProcessingOrder(com.nostra13.universalimageloader.core.assist.QueueProcessingType.FIFO)//设置任务的处理顺序
                .denyCacheImageMultipleSizesInMemory()//防止内存中图片重复
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))//设置自己的内存缓存大小   2M
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13)//内存缓存百分比
                .discCache(new com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache(new File(sdPath + "/huang/image1")))//设置缓存的图片在sdcard中的位置
                .discCacheSize(50 * 1024 * 1024)//硬盘缓存大小    50M
                .discCacheFileCount(100)//硬盘缓存文件个数
                .discCacheFileNameGenerator(new com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator())//md5加密的方式，或new HashCodeFileNameGenerator()
                .imageDownloader(new com.nostra13.universalimageloader.core.download.BaseImageDownloader(this))
                .imageDecoder(new BaseImageDecoder(true))//图片解码
                .defaultDisplayImageOptions(getOption())//是否使用默认的图片加载配置，null表示不使用
                .writeDebugLogs()
                .build();

//初始化
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(configuration);
    }

    public DisplayImageOptions getOption() {
        DisplayImageOptions option = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.app_icon)//设置图片下载期间显示的图片
                .showImageForEmptyUri(R.mipmap.app_icon)//设置图片uri为空或是错误的时候显示的图片
                .showImageOnFail(R.mipmap.app_icon)//设置图片加载或解码过程中发生错误显示的图片
                .resetViewBeforeLoading(false)//设置图片在加载前是否重置、复位
//.delayBeforeLoading(1000)//下载前的延迟时间
                .cacheInMemory(true)//设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)//设置下载的图片是否缓存在sd卡中
                .considerExifParams(false)//思考可交换的参数
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
                .displayer(new RoundedBitmapDisplayer(40))//设置图片的圆角半径
                .displayer(new FadeInBitmapDisplayer(0))//设置图片显示的透明度过程的时间
                .build();

        return option;
    }


}
