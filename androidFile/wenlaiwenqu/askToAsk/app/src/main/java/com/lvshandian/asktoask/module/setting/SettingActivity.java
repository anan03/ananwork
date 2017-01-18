package com.lvshandian.asktoask.module.setting;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.lvshandian.asktoask.App;
import com.lvshandian.asktoask.BaseActivity;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.http.HttpDatas;
import com.lvshandian.asktoask.common.http.RequestCode;
import com.lvshandian.asktoask.common.http.SdkHttpResult;
import com.lvshandian.asktoask.entry.VersonBean;
import com.lvshandian.asktoask.module.login.LoginActivity;
import com.lvshandian.asktoask.utils.Constant;
import com.lvshandian.asktoask.utils.DataCleanManager;
import com.lvshandian.asktoask.utils.DialogView;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.JsonUtil;
import com.lvshandian.asktoask.utils.L;
import com.lvshandian.asktoask.view.ExitLoginDialog;
import com.lvshandian.asktoask.utils.ToastUtils;
import com.lvshandian.asktoask.widgets.SettingItemView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;

/**
 * author: newlq on 2016/9/5
 * email：@lvshandian.com
 * company:北京绿闪电科技有限公司
 * details：登录页
 */
public class SettingActivity extends BaseActivity {
    @Bind(R.id.tv_titlebar_centertext)
    TextView tvTitlebarCentertext;
    @Bind(R.id.ll_titlebar_zuojiantou)
    LinearLayout llTitlebarZuojiantou;
    @Bind(R.id.rel_setting_qinglihuancun)
    SettingItemView relSettingQinglihuancun;
    @Bind(R.id.rel_setting_xiugaimima)
    SettingItemView relSettingXiugaimima;
    @Bind(R.id.rel_setting_guanyuwomen)
    SettingItemView relSettingGuanyuwomen;
    @Bind(R.id.rel_setting_jiancegengxin)
    SettingItemView relSettingJiancegengxin;
    @Bind(R.id.btn_setting_tuichudenglu)
    Button btnSettingTuichudenglu;
    @Bind(R.id.tv_cache)
    TextView tvCache;
    @Bind(R.id.ll_parent_view)
    LinearLayout llParentView;
    private final int VERSION = 13000;
    private final int ISVERSION = 15000;
    VersonBean versonBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RequestCode.VERSION_CODE:
                    Bundle data = msg.getData();
                    SdkHttpResult httpResult = (SdkHttpResult) data.getSerializable(HttpDatas.inforesponse);
                    String json = data.getString(HttpDatas.info);
                    versonBean = JsonUtil.json2Bean(json, VersonBean.class);

                    versonBean.toString();
                    L.e("拿到的值httpResult" + httpResult.toString());
                    L.e("拿到的值data" + json);
                    L.e("拿到的值versonBean" + json);
                    //判断是否更新版本
                    if (versonBean.getVersionNumber().equals("" + getVersionName())) {
//                        if (versonBean.getVersionNumber().equals("" + 1.01)) {

                        ToastUtils.showSnackBar(snackView, "您使用的就是最新版本");
                        L.i("版本号不同 ,提示用户升级 ");
                    } else {
                        mHandler.sendEmptyMessage(VERSION);

                    }
                    break;

                case VERSION://进行版本升级
                    showUpdataDialog();

                    break;
                case ISVERSION://版本升级的结果
                    //下载apk失败
                    Toast.makeText(SettingActivity.this, "下载新版本失败", Toast.LENGTH_SHORT).show();
                    break;


            }
        }
    };

    @Override
    protected void initListener() {
        llTitlebarZuojiantou.setOnClickListener(this);
        btnSettingTuichudenglu.setOnClickListener(this);

        relSettingXiugaimima.setOnClickListener(this);
        relSettingGuanyuwomen.setOnClickListener(this);
        relSettingJiancegengxin.setOnClickListener(this);
        relSettingQinglihuancun.setOnClickListener(this);
//        relSettingGenghuanshoujihao.setOnClickListener(this);
    }

    @Override
    protected void initialized() {
        tvTitlebarCentertext.setText(R.string.mine_itemtext_shezhi);
        //清理缓存
        try {
            tvCache.setText(DataCleanManager.getTotalCacheSize(mContext));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_titlebar_zuojiantou:               //左箭头
                finish();
                break;

            case R.id.tv_cache:
            case R.id.rel_setting_qinglihuancun:
                new DialogView(mContext, llParentView, 0, "确认清除缓存吗？", "确定", "取消",
                        new DialogView.MyCallback() {

                            public void refreshActivity() {
                                //清理缓存
                                try {
                                    tvCache.setText("0K");
                                    DataCleanManager.clearAllCache(mContext);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });


                break;
            case R.id.rel_setting_xiugaimima:               //修改密码
                gotoActivity(ChangePassWordActivity.class, false);
                break;
//            case R.id.rel_setting_genghuanshoujihao:        //更换手机号
//                gotoActivity(ChangePhoneNumActivity.class, false);
//                break;
            case R.id.rel_setting_guanyuwomen:              //关于我们
                Intent intent = new Intent(this, UserAgreementActivity.class);
                intent.putExtra("classname", "SettingAactivity");
                startActivity(intent);
                break;
            case R.id.rel_setting_jiancegengxin:            //检测更新

                getVersionUpdate();
                break;
            case R.id.btn_setting_tuichudenglu:             //退出登录

                final ExitLoginDialog dialog = new ExitLoginDialog(this);
                new DialogView(mContext, llParentView, 0, "确认退出登录？", "确定", "取消",
                        new DialogView.MyCallback() {
                            @Override
                            public void refreshActivity() {
                                //清除Id和将登录设置为false,将图片头像路径设置为空
                                Global.setParam(getContext(), Constant.USERID, "");
                                Global.setParam(getContext(), Constant.ISLOGIN, false);
                                Global.setParam(getContext(), Constant.IMAGER_HEAD_PATH, "");
                                gotoActivity(LoginActivity.class, false);
                                App.finishActivity();
                            }
                        });


                break;
            default:
                break;
        }
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

    /**
     * 版本检测更新
     */
    private void getVersionUpdate() {

        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        //进行版本更新

        httpDatas.getData("版本更新", Request.Method.POST, UrlBuilder.VERSION_URL, map, mHandler, RequestCode.VERSION_CODE);


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
        android.app.AlertDialog.Builder builer = new android.app.AlertDialog.Builder(SettingActivity.this);
        builer.setTitle("版本升级");
        builer.setMessage("是否更新版本");
        //当点确定按钮时从服务器上下载 新的apk 然后安装
        builer.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                downLoadApk();
            }
        });
        builer.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        android.app.AlertDialog dialog = builer.create();
        dialog.setCancelable(false);

        dialog.show();
    }

    protected void downLoadApk() {
        final ProgressDialog pd;    //进度条对话框
        pd = new ProgressDialog(SettingActivity.this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.show();
        new Thread() {
            public void run() {
                try {
                    Log.d("aaa","打印url"+versonBean.getVersionUrl());

                    File file = getFileFromServer(versonBean.getVersionUrl(), pd);
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

    //安装apk
    protected void installApk(File file) {

        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //执行的数据类型
        //编者按：此处Android应为android，否则造成安装不了
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
    }

}