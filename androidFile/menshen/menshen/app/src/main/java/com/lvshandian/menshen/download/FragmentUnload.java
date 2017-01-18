package com.lvshandian.menshen.download;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.IPackageStatsObserver;
import android.content.pm.PackageManager;
import android.content.pm.PackageStats;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lvshandian.menshen.MainActivity;
import com.lvshandian.menshen.R;
import com.lvshandian.menshen.analysesms.adapter.LazyFragment;
import com.lvshandian.menshen.bean.Downloadinfo;
import com.lvshandian.menshen.download.adapter.DownUnLoadAdapter;
import com.lvshandian.menshen.utils.LogUtils;
import com.lvshandian.menshen.utils.Sp;
import com.lvshandian.menshen.utils.TextUtils;
import com.lvshandian.menshen.utils.XUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

import static com.tandong.sa.eventbus.EventBus.TAG;

/**
 * Created by on 2015/10/30.
 * 第三方所有应用展示
 */
public class FragmentUnload extends LazyFragment implements View.OnClickListener {

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 200://


                    break;
            }
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
    }

    @Override
    protected void onVisible() {

        if (adapter != null) {

            adapter.notifyDataSetChanged();
        }


    }

    //全局变量，保存当前查询包得信息
    private long cachesize; //缓存大小

    private long datasize;  //数据大小

    private long codesize;  //应用程序大小

    private long totalsize; //总大小

    private ListView listView;
    private List<Downloadinfo> list = new ArrayList<Downloadinfo>();
    private PackageManager mPackageManager;
    private TextView textView;
    private DownUnLoadAdapter adapter;
    List<Downloadinfo> packageInfos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this); //第1步: 注册
        View mView = inflater.inflate(R.layout.download_fragment_load, container, false);
        listView = (ListView) mView.findViewById(R.id.listview);
        textView = (TextView) mView.findViewById(R.id.textView2);
        RelativeLayout relativeLayout = (RelativeLayout) mView.findViewById(R.id.rl_xie);
        relativeLayout.setVisibility(View.GONE);
        textView.setOnClickListener(this);
        packageInfos = XUtils.findAll(Downloadinfo.class);
        if (null != packageInfos && packageInfos.size() != 0) {
            for (int i = 0; i < packageInfos.size(); i++) {
                packageInfos.get(i).setAppIcon((MainActivity.packageInfos.get(i).getAppIcon()));
            }

            adapter = new DownUnLoadAdapter(getActivity(), packageInfos, R.layout.download_all_item);
            listView.setAdapter(adapter);
            return mView;
        }
        //获取系统应用
        mPackageManager = getActivity().getPackageManager();
        packageInfos = null;
        try {
            packageInfos = getAllApps(getActivity());
            LogUtils.e("packageInfos--" + packageInfos.toString());

            for (int i = 0; i < packageInfos.size(); i++) {
                queryPacakgeSize(packageInfos.get(i).getPkgName());
            }

            adapter = new DownUnLoadAdapter(getActivity(), packageInfos, R.layout.download_all_item);
            listView.setAdapter(adapter);
            ButterKnife.bind(this, mView);
            return mView;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mView;

    }


    //接受方：
    @Subscribe //第2步:注册一个在后台线程执行的方法,用于接收事件
    public void onEventMainThread(String event) {
        LogUtils.e("FragmentUnload======");

        if (event.equals("reflashapk")) {
            //刷新当前页面
            String apk = (String) Sp.getParam(getContext(), "apk", "");

            LogUtils.e("apk" + apk + "");
            if (!TextUtils.isEmpty(apk)) {
                for (int i = 0; i < packageInfos.size(); i++) {
                    LogUtils.e("名字apkname" + packageInfos.get(i).getPkgName());
                    if (apk.equals("package:" + packageInfos.get(i).getPkgName())) {
                        LogUtils.e("名字相同apkname" + packageInfos.get(i).getPkgName());
                        packageInfos.remove(i);
                        //更新数据库进行删除
                        XUtils.deleteEntity(Downloadinfo.class, "pkgName", packageInfos.get(i).getPkgName() + "");
                        if (packageInfos.get(i).getPkgName().equals(MainActivity.packageInfos.get(i).getPkgName())) {
                            MainActivity.packageInfos.remove(i);
                        }
                        adapter.indata();
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }

            }

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
        List<ResolveInfo> rinfo = getActivity().getPackageManager().queryIntentActivities(i,
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
                mPackageManager = getActivity().getPackageManager();// 得到被反射调用函数所在的类对象
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

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.textView2:

                final List<Downloadinfo> unLoads = new ArrayList<Downloadinfo>();
                if (adapter != null) {

                    for (int k = 0; k < packageInfos.size(); k++) {
                        if (adapter.isSelected.get(k)) {
                            unLoads.add(packageInfos.get(k));
                        }
                    }

                }


                break;

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
                    break;
                }
            }


            LogUtils.i(TAG, "cachesize--->" + cachesize + " datasize---->" + datasize + " codeSize---->" + codesize);
        }

    }
    //系统函数，字符串转换 long -String (kb)

    private String formateFileSize(long size) {
        return Formatter.formatFileSize(getActivity(), size);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);//反注册
    }

    /**
     * 卸载指定包名的应用
     *
     * @param packageName
     */
    private void Uninstall(String packageName) {
        if (checkApplication(packageName)) {
            Uri packageURI = Uri.parse("package:" + packageName);
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(packageURI);
            startActivity(intent);
        }
    }

    /**
     * 判断该包名的应用是否安装
     *
     * @param packageName
     * @return
     */
    public boolean checkApplication(String packageName) {
        if (packageName == null || "".equals(packageName)) {
            return false;
        }
        try {
            getActivity().getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * 转换文件大小
     *
     * @param fileS
     * @return
     */

    public String FormetFileSize(long fileS) {

        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeLong = "";
        fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576)) + "MB";
        return fileSizeLong;
    }

}
