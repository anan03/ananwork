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
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.lvshandian.menshen.MainActivity;
import com.lvshandian.menshen.R;
import com.lvshandian.menshen.UrlBuilder;
import com.lvshandian.menshen.analysesms.adapter.LazyFragment;
import com.lvshandian.menshen.bean.ApkBean;
import com.lvshandian.menshen.bean.Downloadinfo;
import com.lvshandian.menshen.bean.Keyworkinfo;
import com.lvshandian.menshen.bean.User;
import com.lvshandian.menshen.download.adapter.DownLoadAdapter;
import com.lvshandian.menshen.httprequest.HttpDatas;
import com.lvshandian.menshen.httprequest.RequestCode;
import com.lvshandian.menshen.utils.JsonUtil;
import com.lvshandian.menshen.utils.LogUtils;
import com.lvshandian.menshen.utils.NetworkUtils;
import com.lvshandian.menshen.utils.Sp;
import com.lvshandian.menshen.utils.TextUtils;
import com.lvshandian.menshen.utils.ToastUtil;
import com.lvshandian.menshen.utils.XUtils;
import com.lvshandian.menshen.view.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Handler;

import butterknife.ButterKnife;

import static com.tandong.sa.eventbus.EventBus.TAG;

/**
 * Created by on 2015/10/30.
 * 卸载应用界面
 */
public class FragmentLoad extends LazyFragment implements View.OnClickListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private Double apkMins;
    private Double apkMaxs;
    private android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {

            Bundle data = msg.getData();
            int tagCode = data.getInt(HttpDatas.code);

            switch (msg.what) {
                case 220:
                    packageInfos = filtration(apkMins + "", apkMins + "", packageInfos);
                    adapter = new DownLoadAdapter(getActivity(), packageInfos, R.layout.download_item);
                    listView.setAdapter(adapter);
                    break;

                case RequestCode.SELECT_APKSIZE://查询apk包名的大小
                    if (tagCode == RequestCode.REQUEST_CODE) {//返回值504
                        Toast.makeText(getActivity(), data.getString(HttpDatas.info), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    List<ApkBean> ApkBean = JsonUtil.json2BeanList(data.getString(HttpDatas.info), ApkBean.class);
                    LogUtils.e("ApkBean--" + ApkBean.toString());

                    if (null != ApkBean && ApkBean.size() != 0) {
                        if (!TextUtils.isEmpty("" + ApkBean.get(0).getAppSizeMax()) && !TextUtils.isEmpty("" + ApkBean.get(0).getAppSizeSmall())) {
                            Sp.setParam(getActivity(), "ApkMax", ApkBean.get(0).getAppSizeMax());
                            Sp.setParam(getActivity(), "ApkMin", ApkBean.get(0).getAppSizeSmall());
                            initData("" + ApkBean.get(0).getAppSizeMax(), "" + ApkBean.get(0).getAppSizeSmall());
                            apkMaxs = ApkBean.get(0).getAppSizeMax();
                            apkMins = ApkBean.get(0).getAppSizeSmall();
                        }
                    }

                    break;
            }
        }


    };

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
    }

    @Override
    protected void onVisible() {
        if (adapter != null) {
            adapter.indata();

            FragmentLoad.textView.setText("一键卸载(0个)");
            adapter.notifyDataSetChanged();
        }

    }

    //全局变量，保存当前查询包得信息
    private long cachesize; //缓存大小

    private long datasize;  //数据大小

    private long codesize;  //应用程序大小

    private long totalsize; //总大小
    protected HttpDatas httpDatas;

    private ListView listView;
    private List<Downloadinfo> list = new ArrayList<Downloadinfo>();
    private PackageManager mPackageManager;
    public static TextView textView;
    private DownLoadAdapter adapter;

    public static List<Downloadinfo> getPackageInfos() {
        return packageInfos;
    }

    public static List<Downloadinfo> packageInfos;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this); //第1步: 注册
        View mView = inflater.inflate(R.layout.download_fragment_load, container, false);
        httpDatas = new HttpDatas(getActivity(), mView);
        listView = (ListView) mView.findViewById(R.id.listview);
        textView = (TextView) mView.findViewById(R.id.textView2);
        textView.setOnClickListener(this);

        if (!NetworkUtils.IsNetworkAvailable(getActivity())) {
            Toast.makeText(getActivity(), "请连接网络", Toast.LENGTH_SHORT).show();
            return mView;
        }
        requspApk();
        ButterKnife.bind(this, mView);
        return mView;

    }

    private void initData(String apkMax, String apkMin) {
        packageInfos = XUtils.findAll(Downloadinfo.class);
        if (null != packageInfos && packageInfos.size() != 0) {
            for (int i = 0; i < packageInfos.size(); i++) {
                packageInfos.get(i).setAppIcon((MainActivity.packageInfos.get(i).getAppIcon()));
            }
            packageInfos = filtration(apkMax, apkMin, packageInfos);
            LogUtils.e("sqlitepackageInfos--" + packageInfos.toString());
            adapter = new DownLoadAdapter(getActivity(), packageInfos, R.layout.download_item);
            listView.setAdapter(adapter);
            return;
        }

        //获取系统应用
        mPackageManager = getActivity().getPackageManager();
        packageInfos = null;
        try {
            packageInfos = getAllApps(getActivity());
            LogUtils.e("packageInfos--" + packageInfos.toString());

            for (int i = 0; i < packageInfos.size(); i++) {
                try {
                    queryPacakgeSize(packageInfos.get(i).getPkgName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            mHandler.sendEmptyMessageAtTime(220, 2000);
            LogUtils.e("packageInfosd--" + packageInfos.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //应用包大小请求
    private void requspApk() {

        if (null != XUtils.findAll(User.class) && null != XUtils.findAll(User.class).get(0)) {
            User user = (User) XUtils.findAll(User.class).get(0);
            ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
            map.put("userId", user.getUserId() + "");
            httpDatas.getData("查询所有短信的关键字type=4下载", Request.Method.POST, UrlBuilder.SELECT_APKSIZE, map, mHandler, RequestCode.SELECT_APKSIZE);

        }
    }

    /**
     * 过滤数据满足
     * 1.必须包含该关键字
     * 2.包名大小必须在范围之内
     *
     * @param packageInfos
     * @return
     */
    private List<Downloadinfo> filtration(String apkMax, String apkMin, List<Downloadinfo> packageInfos) {

        List<Downloadinfo> listDownload = new ArrayList<Downloadinfo>();
        List<Downloadinfo> listDownloadApk = new ArrayList<Downloadinfo>();
        List<Downloadinfo> listDownloadApkH = new ArrayList<Downloadinfo>();
        listDownloadApk.addAll(packageInfos);
        List<Downloadinfo> listDownloadreturn = new ArrayList<Downloadinfo>();
        //过滤关键字
        List<Keyworkinfo> listkey = XUtils.findAll(Keyworkinfo.class);
        if (listkey == null || listkey.size() == 0) {
            LogUtils.e("数据库没有数据关键字");
            LogUtils.e("sqlitepackageInfos--筛选关键前packageInfos" + packageInfos.toString());
            return listDownloadreturn;
        }
        List<String> listkeyname = new ArrayList<String>();
        List<String> listkeysize = new ArrayList<String>();
        int type = 0;
        String[] strs;
        /*将关键字提取出来
          *
          */
//        for (int i = 0; i < listkey.size(); i++) {
//            type = listkey.get(i).getKeywordType();
//            if (type == 4) {
//                //如果是过滤卸载关键字
//                strs = TextUtils.out(listkey.get(i).getKeyword());
//                if (strs.length == 3) {
//                    listkeyname.add(strs[0]);
//                    listkeysize.add(strs[1] + "&" + strs[2]);
//                }
//            }
//        }
        for (int i = 0; i < listkey.size(); i++) {
            type = listkey.get(i).getKeywordType();
            if (type == 4) {
                listkeyname.add(listkey.get(i).getKeyword());
            }
        }

        //对关键字进行管理
        if (listkeyname.size() == 0 || listkeyname == null) {
            LogUtils.e("没有");
            return listDownloadreturn;

        } else {
            listDownload.addAll(packageInfos);
            LogUtils.e("过滤关键前name---" + listkeyname.toString());
            LogUtils.e("过滤关键前load---" + listDownload.toString());
            //进行过滤关键字
            for (int i = listDownload.size() - 1; i >= 0; i--) {
                for (int k = 0; k < listkeyname.size(); k++) {
                    if (TextUtils.isString(listkeyname.get(k), listDownload.get(i).getAppName())) {
                        listDownloadreturn.add(listDownload.get(i));
                        LogUtils.e("相等的关键字---" + listDownload.get(i).getAppName());
                        listDownload.remove(i);
                        break;
                    }
                }


            }

        }
        listDownload.clear();
        LogUtils.e("过滤关键字后---" + listDownloadreturn.toString());
        listDownload.addAll(listDownloadreturn);
        listDownloadreturn.clear();


        //如果为0则直接返回
        if (Double.parseDouble(apkMax.trim()) == 0 && Double.parseDouble(apkMin.trim()) == 0) {
            return listDownloadreturn;
        }
        String douloadSize = "";
        for (int i = 0; i < listDownloadApk.size(); i++) {
            douloadSize = listDownloadApk.get(i).getApkSize();
            if (!TextUtils.isEmpty(douloadSize)) {
                douloadSize = douloadSize.replace("MB", "").trim();
                douloadSize = douloadSize.replace("KB", "").trim();
                /**
                 * 過濾大于大，小于小
                 */
                LogUtils.e("过滤包大小---apkMax:" + apkMax + "apkMin:" + apkMin + "douloadSize" + douloadSize);
                if (Double.parseDouble(apkMax.trim()) < Double.parseDouble(douloadSize.trim()) ||
                        Double.parseDouble(apkMin.trim()) > Double.parseDouble(douloadSize.trim())) {
                    listDownloadApkH.add(listDownloadApk.get(i));
                }
            }
        }
        LogUtils.e("过滤包大小后---listDownload" + listDownload.toString());
        LogUtils.e("过滤包大小后---listDownloadApkH" + listDownloadApkH.toString());
        for (int i = listDownload.size() - 1; i >= 0; i--) {
            int k = 0;
            for (int j = listDownloadApkH.size() - 1; j >= 0; j--) {

                if (!listDownload.get(i).getPkgName().equals(listDownloadApkH.get(j).getPkgName())) {
                    k++;
                }

                if (k == listDownloadApkH.size()) {
//                    listDownload.add(listDownloadApkH.get(j));
                    listDownloadApkH.add(listDownload.get(i));
                }
            }
        }
        LogUtils.e("过滤包大小后---" + listDownloadreturn.toString());
//        return listDownloadreturn;
        return listDownloadApkH;
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
                        //更新数据库进行删除
                        XUtils.deleteEntity(Downloadinfo.class, "pkgName", packageInfos.get(i).getPkgName() + "");
                        if (packageInfos.get(i).getPkgName().equals(MainActivity.packageInfos.get(i).getPkgName())) {
                            MainActivity.packageInfos.remove(i);
                        }
                        packageInfos.remove(i);
                        adapter.notifyDataSetChanged();
                        break;
                    }
                }
                adapter.indata();
                FragmentLoad.textView.setText("一键卸载(0个)");
                adapter.notifyDataSetChanged();

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

                    if (unLoads.size() <= 0) {
                        ToastUtil.makeToast("请选择卸载的应用", true);
                        LogUtils.e("请选择卸载的应用");
                        return;
                    }
                    for (int i = 0; i < unLoads.size(); i++) {
                        if (checkApplication(unLoads.get(i).getPkgName())) {
                            Uninstall(unLoads.get(i).getPkgName());
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

        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);//反注册
    }

    public String FormetFileSize(long fileS) {

        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeLong = "";
        fileSizeLong = Double.valueOf(df.format((double) fileS / 1048576)) + "MB";
        return fileSizeLong;
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

}
