package com.lvshandian.menshen;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.bumptech.glide.Glide;
import com.lvshandian.menshen.analysesms.AnayseSmsActivity;
import com.lvshandian.menshen.analysesms.AnayseSmsUploadActivity;
import com.lvshandian.menshen.base.BaseActivity;
import com.lvshandian.menshen.bean.Banner;
import com.lvshandian.menshen.bean.Downloadinfo;
import com.lvshandian.menshen.bean.Keyworkinfo;
import com.lvshandian.menshen.bean.User;
import com.lvshandian.menshen.bean.VersonBean;
import com.lvshandian.menshen.download.DownLoadActivity;
import com.lvshandian.menshen.download.FragmentUnload;
import com.lvshandian.menshen.download.adapter.DownUnLoadAdapter;
import com.lvshandian.menshen.httprequest.HttpDatas;
import com.lvshandian.menshen.httprequest.RequestCode;
import com.lvshandian.menshen.login.ForgetPasswordActivity;
import com.lvshandian.menshen.login.LoginActivity;
import com.lvshandian.menshen.phone.PhoneHoldUpActivity;
import com.lvshandian.menshen.push.MessageActivity;
import com.lvshandian.menshen.settings.AboutActivity;
import com.lvshandian.menshen.settings.AboutMeActivity;
import com.lvshandian.menshen.settings.SettingPerson;
import com.lvshandian.menshen.utils.Constant;
import com.lvshandian.menshen.utils.JsonUtil;
import com.lvshandian.menshen.utils.LogUtils;
import com.lvshandian.menshen.utils.Sp;
import com.lvshandian.menshen.utils.TextUtils;
import com.lvshandian.menshen.utils.ToastUtil;
import com.lvshandian.menshen.utils.XUtils;
import com.lvshandian.menshen.view.CircleImageView;
import com.lvshandian.menshen.view.DialogView;
import com.lvshandian.menshen.view.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.AutoFrameLayout;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.lvshandian.menshen.R.id.context;
import static com.lvshandian.menshen.R.id.imageView;
import static com.tandong.sa.eventbus.EventBus.TAG;


/**
 * 创建首页互
 */

public class MainActivity extends BaseActivity {

    @Bind(R.id.main_dl)
    DrawerLayout mainDl;
    @Bind(R.id.main_content_frame)
    AutoLinearLayout mainContentFrame;
    @Bind(R.id.iv_head)
    CircleImageView ivHead;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_fen)
    TextView tvFen;
    @Bind(R.id.rl_update_password)
    AutoRelativeLayout rlUpdatePassword;
    @Bind(R.id.rl_update)
    AutoRelativeLayout rlUpdate;
    @Bind(R.id.rl_me)
    AutoRelativeLayout rlMe;
    @Bind(R.id.rl_yijian)
    AutoRelativeLayout rlYijian;
    @Bind(R.id.bt_tuilogin)
    Button btTuilogin;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.ll_parent_view)
    AutoLinearLayout llParentView;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.rl_includ)
    AutoRelativeLayout rlInclud;
    @Bind(R.id.iv_request_img)
    ImageView ivRequestImg;
    @Bind(R.id.iv_head_main)
    CircleImageView ivHeadMain;
    @Bind(R.id.tv_phone_main)
    TextView tvPhoneMain;
    @Bind(R.id.tv_fen_main)
    TextView tvFenMain;
    @Bind(R.id.tv_messge_main)
    ImageView tvMessgeMain;
    @Bind(R.id.tv_count_main_message)
    TextView tvCountMainMessage;
    @Bind(R.id.fl_messge_main)
    AutoFrameLayout flMessgeMain;
    @Bind(R.id.tv_messge_mainimg_main)
    TextView tvMessgeMainimgMain;
    @Bind(R.id.iv_phone_zha)
    ImageView ivPhoneZha;
    @Bind(R.id.rl_zp_phone)
    AutoRelativeLayout rlZpPhone;
    @Bind(R.id.iv_duan_zha)
    ImageView ivDuanZha;
    @Bind(R.id.rl_zp_sms)
    AutoRelativeLayout rlZpSms;
    @Bind(R.id.iv_fen_zha)
    ImageView ivFenZha;
    @Bind(R.id.iv_mu_zha)
    ImageView ivMuZha;
    @Bind(R.id.rl_zp_du)
    AutoRelativeLayout rlZpDu;
    @Bind(R.id.rl_zp_sms_shar)
    AutoLinearLayout rlZpSmsShar;
    @Bind(R.id.rl_count_message)
    AutoRelativeLayout rlCountMessage;
    private VersonBean versonBean;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            int tagCode = data.getInt(HttpDatas.code);

            switch (msg.what) {
                //版本更新请求接口
                case RequestCode.VERSION_CODE://成功
                    //判断是否更新版本
                    if (tagCode == RequestCode.REQUEST_CODE) {
                        ToastUtils.showSnackBar(snackView, data.getString(HttpDatas.info));
                        return;
                    }
                    versonBean = JsonUtil.json2Bean(data.getString(HttpDatas.info), VersonBean.class);

                    if (null != versonBean) {
                        LogUtils.e("版本升级versonBean-" + versonBean.toString());
                        double code = getVersionName();
                        if (versonBean.getVersionNumber() > code) {
                            mHandler.sendEmptyMessage(VERSION);
                        } else {
                            ToastUtils.showSnackBar(snackView, "当前为最新版本");
                            LogUtils.e("版本号不同 ,提示用户升级 ");
                        }
                    } else {
                        ToastUtil.debugToast("versonbean为空", true);
                    }
                    break;
                case VERSION://进行版本升级
                    showUpdataDialog();

                    break;
                case ISVERSION://版本升级的结果
                    //下载apk失败
                    Toast.makeText(MainActivity.this, "下载新版本失败", Toast.LENGTH_SHORT).show();
                    break;
                case RequestCode.BANNER_IMAGER_CODE://banner图片请求成功
                    if (tagCode == RequestCode.REQUEST_CODE) {
                        ToastUtils.showSnackBar(snackView, data.getString(HttpDatas.info));
                        return;
                    }
                    Banner banner = JsonUtil.json2Bean(data.getString(HttpDatas.info), Banner.class);
                    //头像
                    Glide.with(mContext)
                            .load(user.getHeadPortraitImgUrl()).error(R.mipmap.wode_touxiang).placeholder(R.mipmap.wode_touxiang)
                            .into(ivHead);
                    Glide.with(mContext)
                            .load(user.getHeadPortraitImgUrl()).error(R.mipmap.wode_touxiang).placeholder(R.mipmap.shouyetouxiang)
                            .into(ivHeadMain);
                    //手机号

                    tvPhone.setText(user.getUserName());
                    tvPhoneMain.setText(user.getUserName());
                    //积分
                    tvFen.setText("积分: " + user.getIntegral());
                    tvFenMain.setText("积分: " + user.getIntegral());

                    if (null != banner && null != banner.getImgUrl()) {
                        Glide.with(mContext)
                                .load(banner.getImgUrl()).error(R.mipmap.banener).placeholder(R.mipmap.banener)
                                .into(ivRequestImg);
                    }

                    break;
                case RequestCode.MESSAGE_WEI_CODE://请求未读消息个数成功
                    if (tagCode == RequestCode.REQUEST_CODE) {
                        ToastUtils.showSnackBar(snackView, data.getString(HttpDatas.info));
                        return;
                    }

                    tvCountMainMessage.setText(data.getString(HttpDatas.info));

                    if (tvCountMainMessage.getText().toString().equals("0")) {
                        tvCountMainMessage.setVisibility(View.GONE);
                    } else {
                        tvCountMainMessage.setVisibility(View.VISIBLE);
                    }
                    break;
                case 205://监听包的是否完善
                    if (packSize == packageInfos.size()) {
                        XUtils.dropTable(Downloadinfo.class);
                        for (int i = 0; i < packageInfos.size(); i++) {
                            XUtils.addTable(packageInfos.get(i));
                        }
                        List<Downloadinfo> list = XUtils.findAll(Downloadinfo.class);
                        LogUtils.e("sqlite" + list.toString());
                        timer.cancel();
                    }


                    break;
                case RequestCode.KEYWORK_CODE://查询关键字成功
                    if (tagCode == RequestCode.REQUEST_CODE) {//返回值504
//                        ToastUtils.showSnackBar(snackView, data.getString(HttpDatas.info));
                        return;
                    }
                    List<Keyworkinfo> listKeyWorkType1 = JsonUtil.json2BeanList(data.getString(HttpDatas.info), Keyworkinfo.class);

                    if (listKeyWorkType1 != null && listKeyWorkType1.size() > 0) {
                        XUtils.dropTable(Keyworkinfo.class);
                        for (int i = 0; i < listKeyWorkType1.size(); i++) {
                            XUtils.addTable(listKeyWorkType1.get(i));
                        }
                    }
                    Sp.setParam(mContext, Constant.IS_MAIN, true);

                    break;

            }
        }
    };
    private final int VERSION = 20001;
    private final int ISVERSION = 20002;
    private AutoLinearLayout fl;
    public static RelativeLayout llLift;
    public static DrawerLayout dl;
    private User user;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initListener() {
        ivHead.setOnClickListener(this);
        tvFen.setOnClickListener(this);
        tvPhone.setOnClickListener(this);
        rlMe.setOnClickListener(this);
        rlUpdate.setOnClickListener(this);
        rlUpdatePassword.setOnClickListener(this);
        rlYijian.setOnClickListener(this);
        btTuilogin.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        rlZpDu.setOnClickListener(this);
        rlZpPhone.setOnClickListener(this);
        rlZpSms.setOnClickListener(this);
        rlZpSmsShar.setOnClickListener(this);
        rlCountMessage.setOnClickListener(this);
    }

    private MyReceiver hemeDate;

    @Override
    protected void initialized() {

        EventBus.getDefault().register(this); //第1步: 注册
        rlInclud.setBackgroundColor(getResources().getColor(R.color.back347));
        ivBack.setImageResource(R.mipmap.cehuaimg);
        tvTitle.setText("门神手机监测");
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        llLift = (RelativeLayout) findViewById(R.id.ll_lift);
        DrawerLayout.LayoutParams linearParams = (DrawerLayout.LayoutParams) llLift.getLayoutParams();
        linearParams.width = screenWidth * 5 / 6;
        fl = (AutoLinearLayout) findViewById(R.id.main_content_frame);
        dl = (DrawerLayout) findViewById(R.id.main_dl);
        dl.setScrimColor(Color.TRANSPARENT);
        //给数据赋值
        if (null != XUtils.findAll(User.class) && null != XUtils.findAll(User.class).get(0)) {
            user = (User) XUtils.findAll(User.class).get(0);
            //请求banner图片
            requstBanner();
//            请求消息个数
            requestMessage();
        } else {
            ToastUtil.debugToast("userid不能为空", true);
        }
        //查询应用以及短信的关键
        if (!(boolean) Sp.getParam(mContext, Constant.IS_MAIN, false)) {
            requstKeyword();
        }

        tvTitle.setOnClickListener(this);
        //获取所有软件的信息；
        indateAak();
        hemeDate = new MyReceiver();
        IntentFilter intentfilter = new IntentFilter();
        intentfilter.addAction("修改我的信息");
        // 注册广播
        mContext.registerReceiver(hemeDate, intentfilter);
    }


    class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            if (null != XUtils.findAll(User.class) && null != XUtils.findAll(User.class).get(0)) {
                User user = (User) XUtils.findAll(User.class).get(0);
                tvPhone.setText(user.getUserName());
                tvPhoneMain.setText(user.getUserName());
                //头像
                Glide.with(mContext)
                        .load(user.getHeadPortraitImgUrl()).error(R.mipmap.wode_touxiang).placeholder(R.mipmap.wode_touxiang)
                        .into(ivHead);
                Glide.with(mContext)
                        .load(user.getHeadPortraitImgUrl()).error(R.mipmap.wode_touxiang).placeholder(R.mipmap.shouyetouxiang)
                        .into(ivHeadMain);

                tvFen.setText("积分: " + user.getIntegral());
                tvFenMain.setText("积分: " + user.getIntegral());
            } else {
                ToastUtils.showSnackBar(snackView, "user不能为空");
            }
        }
    }


    private void requestMessage() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("userId", user.getUserId() + "");
        httpDatas.getData("请求消息个数", Request.Method.GET, UrlBuilder.MESSAGE_WEI, map, mHandler, RequestCode.MESSAGE_WEI_CODE);
    }

    private void requstBanner() {//请求banner图片
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        httpDatas.getData("banner图片请求", Request.Method.GET, UrlBuilder.BANNER_IMAGER, map, mHandler, RequestCode.BANNER_IMAGER_CODE);
    }

    final int WRITE_EXTERNAL_STORAGE = 300;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //请求成功
    @PermissionGrant(WRITE_EXTERNAL_STORAGE)
    public void requestSdcardSuccess() {
        getVersionUpdate();//版本更新

    }

//请求失败


    @PermissionDenied(WRITE_EXTERNAL_STORAGE)
    public void requestSdcardFailed() {
        ToastUtils.showSnackBar(snackView, "请打开访问权限");
    }

    public static void shouMenu() {
        dl.openDrawer(llLift);//打开抽屉
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_head://头像
                //跳转到修改个人信息页面

                if (user == null) {
                    ToastUtil.debugToast("user不能为空", true);
                    return;
                }
                gotoActivity(SettingPerson.class, false);
                break;
            case R.id.rl_update://版本更新
                if (user == null) {
                    ToastUtil.debugToast("user不能为空", true);
                    return;
                }
                MPermissions.requestPermissions(MainActivity.this, WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);

                break;
            case R.id.rl_update_password://修改密码
                if (user == null) {
                    ToastUtil.debugToast("user不能为空", true);
                    return;
                }
                Intent intent = new Intent(mContext, ForgetPasswordActivity.class);
                intent.putExtra("tag", "main");
                startActivity(intent);
                break;
            case R.id.rl_yijian://关于我们
                gotoActivity(AboutActivity.class, false);
                break;
            case R.id.rl_me://意见反馈
                if (user == null) {
                    ToastUtil.debugToast("user不能为空", true);
                    return;
                }
                Intent intent5 = new Intent(mContext, AboutMeActivity.class);
                intent5.putExtra("userId", "" + user.getUserId());
                startActivity(intent5);
                break;
            case R.id.bt_tuilogin://退出登录

                new DialogView(getContext(), llParentView, "是否退出登录", "确定", "取消", "", new DialogView.MyCallback() {
                    @Override
                    public void refreshActivity() {
                        Sp.setParam(mContext, Constant.IS_LOGIN, false);
                        gotoActivity(LoginActivity.class, true);
                        MyApplication.finishLastActivity();
                    }

                    @Override
                    public void refreshActivity(String tag) {
                    }

                    @Override
                    public void refreshActivity(String context, String minString, String maxString) {

                    }
                });
                break;
            case R.id.iv_back://切换侧滑
                shouMenu();
                break;
            case R.id.rl_zp_du://木马监测
                Intent intent4 = new Intent(MainActivity.this, DownLoadActivity.class);
                intent4.putExtra("userId", "" + user.getUserId());
                startActivity(intent4);
                break;
            case R.id.rl_zp_phone://电话监测
                if (user == null) {
                    ToastUtil.debugToast("user不能为空", true);
                    return;
                }
                Intent intent3 = new Intent(MainActivity.this, PhoneHoldUpActivity.class);
                intent3.putExtra("userId", "" + user.getUserId());
                startActivity(intent3);
                break;
            case R.id.rl_zp_sms://短信监测
                if (user == null) {
                    ToastUtil.debugToast("user不能为空", true);

                    return;
                }

                Intent intent2 = new Intent(MainActivity.this, AnayseSmsActivity.class);
                LogUtils.e("userId前" + user.getUserId());
                intent2.putExtra("userId", "" + user.getUserId());
                startActivity(intent2);
                break;
            case R.id.rl_zp_sms_shar://短信监测分享
                if (user == null) {
                    ToastUtil.debugToast("user不能为空", true);
                    return;
                }
                Intent intent6 = new Intent(MainActivity.this, AnayseSmsUploadActivity.class);
                intent6.putExtra("userId", "" + user.getUserId());
                startActivity(intent6);
                break;
            case R.id.rl_count_message://消息推送界面
                if (user != null) {
                    Intent intent1 = new Intent(MainActivity.this, MessageActivity.class);
                    LogUtils.e("userId前" + user.getUserId());
                    intent1.putExtra("userId", "" + user.getUserId());
                    startActivity(intent1);
                } else {
                    ToastUtil.debugToast("userid不能为空", true);
                }
                break;
            //获取版本号
            case R.id.tv_title:
                ToastUtil.debugToast("" + getVersionName(), true);
                break;


        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * @throws Exception
     * @return获取当前程序的版本号
     */
    private int getVersionName() {
        //获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        //getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return packInfo.versionCode;
    }

    public void getVersionUpdate() {


        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        //进行版本更新
        httpDatas.getData("版本更新", Request.Method.GET, UrlBuilder.VERSION_URL, map, mHandler, RequestCode.VERSION_CODE);

    }

    /*   *
       * 弹出对话框通知用户更新程序
       * 弹出对话框的步骤：
       *  1.创建alertDialog的builder.
       *  2.要给builder设置属性, 对话框的内容,样式,按钮
       *  3.通过builder 创建一个对话框
       *  4.对话框show()出来
       */
    protected void showUpdataDialog() {


        new DialogView(getContext(), llParentView, "有新版本啦", "确定", "取消", "是否升级版本", new DialogView.MyCallback() {
            @Override
            public void refreshActivity() {
                downLoadApk();
            }

            @Override
            public void refreshActivity(String tag) {

            }

            @Override
            public void refreshActivity(String context, String minString, String maxString) {

            }
        });
    }

    protected void downLoadApk() {
        final ProgressDialog pd;    //进度条对话框
        pd = new ProgressDialog(MainActivity.this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.show();
        new Thread() {
            public void run() {
                Log.i("MainActivity", "进入线程下载apk");
                try {
                    File file = getFileFromServer(versonBean.getDownloadUrl(), pd);
                    installApk(file);
                    pd.dismiss(); //结束掉进度条对话框
                } catch (Exception e) {
                    mHandler.sendEmptyMessage(2);
                }
            }
        }.start();
    }

    //从服务器下载apk
    public static File getFileFromServer(String path, ProgressDialog pd) throws Exception {
        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            //获取到文件的大小
            pd.setMax(conn.getContentLength() / 1000);
            InputStream is = conn.getInputStream();

            File file = new File(Environment.getExternalStorageDirectory(), "apk2.apk");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                total += len;
                //获取当前下载量
                pd.setProgress(total / 1000);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        } else {
            return null;
        }
    }

    //在注册了的Activity中,声明处理事件的方法
    @Subscribe //第2步:注册一个在后台线程执行的方法,用于接收事件
    public void onEventMainThread(String event) {

        if (event.equals("reflash")) {
            requestMessage();
        } else if (event.equals("reflashname")) {
            if (null != XUtils.findAll(User.class) && null != XUtils.findAll(User.class).get(0)) {
                User user = (User) XUtils.findAll(User.class).get(0);
                tvPhone.setText(user.getUserName());
                tvPhoneMain.setText(user.getUserName());
            }
        } else if (event.equals("reflashMessage")) {
            requestMessage();
            requstBanner();
        }


    }

    //安装apk
//    //安装apk
//    protected void installApk(File file) {
//
//        Intent intent = new Intent();
//        //执行动作
//        intent.setAction(Intent.ACTION_VIEW);
//        //执行的数据类型
//        //编者按：此处Android应为android，否则造成安装不了
//        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//        startActivity(intent);
//    }
    //安装apk

    protected void installApk(File file) {

//        Intent intent = new Intent();
//
//        //执行动作
//        intent.setAction(Intent.ACTION_VIEW);
//        //执行的数据类型
//        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//
//        startActivity(intent);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        MainActivity.this.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册
    }


    //全局变量，保存当前查询包得信息
    private long cachesize; //缓存大小

    private long datasize;  //数据大小

    private long codesize;  //应用程序大小

    private long totalsize; //总大小
    private PackageManager mPackageManager;
    public static List<Downloadinfo> packageInfos = new ArrayList<Downloadinfo>();
    private Timer timer;
    private int packSize;

    //进行数据的保存
    private void indateAak() {
        packSize = 0;
        packageInfos.clear();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(205);
            }
        }, 1000, 500);

        mPackageManager = mContext.getPackageManager();
        try {
            packageInfos = getAllApps(mContext);
            LogUtils.e("packageInfos--" + packageInfos.toString());
            for (int i = 0; i < packageInfos.size(); i++) {
                queryPacakgeSize(packageInfos.get(i).getPkgName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //应用过滤关键字
    private void requstKeyword() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        if (user != null) {
            map.put("userId", user.getUserId() + "");
            httpDatas.getData("查询所有短信的关键字type=4下载", Request.Method.POST, UrlBuilder.KEYWORK_URL, map, mHandler, RequestCode.KEYWORK_CODE);
        }

    }

    /**
     * 查询手机内非系统应用
     *
     * @param context
     * @return
     */
    public List<Downloadinfo> getAllApps(Context context) throws Exception {
        //获取手机内所有应用
        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> rinfo = mContext.getPackageManager().queryIntentActivities(i,
                0);
        List<Downloadinfo> appInfos = new ArrayList<Downloadinfo>(); // 保存过滤查到的AppInfo
        // 根据条件来过滤
        for (ResolveInfo app : rinfo) {
            if ((app.activityInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {// 第三方应用

                Downloadinfo temp = getAppInfo(app);

                if (temp.getPkgName().equals("com.lvshandian.menshen")) {
                    continue;
                }
                if (null != temp) {

                    appInfos.add(temp);
                }
            } else {// 系统应用
                Downloadinfo temp = getAppInfo(app);
                if (null != temp && !TextUtils.isEmpty(temp.getPkgName())
                        && !temp.getPkgName().equals("com.lvshandian.menshen")) {
                }
            }
        }
        return appInfos;
    }


    private Downloadinfo getAppInfo(ResolveInfo app) throws Exception {
        Downloadinfo appInfo = new Downloadinfo();
        String appName = app.activityInfo.loadLabel(mPackageManager).toString();
        appInfo.setActivityName(app.activityInfo.name);//activityname
        appInfo.setAppName(appName);//应用名称
        appInfo.setAppIcon(app.activityInfo.loadIcon(mPackageManager));//应用头像
        appInfo.setPkgName(app.activityInfo.packageName);//应用包名
        return appInfo;
    }

    public void queryPacakgeSize(String pkgName) throws Exception {
        if (pkgName != null) {

            if (mPackageManager == null) {
                mPackageManager = mContext.getPackageManager();// 得到被反射调用函数所在的类对象
            }
            try {
                String methodName = "getPackageSizeInfo";// 想通过反射机制调用的方法名
                Class<?> parameterType1 = String.class;// 被反射的方法的第一个参数的类型
                Class<?> parameterType2 = IPackageStatsObserver.class;// 被反射的方法的第二个参数的类型
                Method getPackageSizeInfo = mPackageManager.getClass().getMethod(
                        methodName, parameterType1, parameterType2);
                getPackageSizeInfo.invoke(mPackageManager, pkgName, new PkgSizeObserver());
            } catch (Exception ex) {
                LogUtils.e("tag", "queryPkgSize()-->NoSuchMethodException");
                ex.printStackTrace();
                throw ex; // 抛出异常
            }
        }
    }


    //aidl文件形成的Bindler机制服务类

    public class PkgSizeObserver extends IPackageStatsObserver.Stub {
        /***
         * 回调函数，
         *
         * @param pStats    ,返回数据封装在PackageStats对象中
         * @param succeeded 代表回调成功
         */
        @Override
        public void onGetStatsCompleted(PackageStats pStats, boolean succeeded)
                throws RemoteException {
            // TODO Auto-generated method stub
            cachesize = pStats.cacheSize; //缓存大小

            datasize = pStats.dataSize;  //数据大小

            codesize = pStats.codeSize;  //应用程序大小
            totalsize = cachesize + datasize + codesize;

            for (int i = 0; i < packageInfos.size(); i++) {
                if (pStats.packageName.equals(packageInfos.get(i).getPkgName())) {
                    packageInfos.get(i).setApkSize(FormetFileSize(totalsize));
                    packSize++;
                    break;
                }
            }


            LogUtils.i(TAG, "cachesize--->" + cachesize + " datasize---->" + datasize + " codeSize---->" + codesize);
        }

    }
    //系统函数，字符串转换 long -String (kb)

    private String formateFileSize(long size) {
        return Formatter.formatFileSize(mContext, size);
    }

    public String FormetFileSize(long fileS) {

        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeLong = "";
        fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576)) + "MB";
        return fileSizeLong;
    }

}
