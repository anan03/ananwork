package com.lvshandian.menshen.analysesms;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Telephony;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.lvshandian.menshen.R;
import com.lvshandian.menshen.UrlBuilder;
import com.lvshandian.menshen.analysesms.adapter.FragmentPagerAdapter;
import com.lvshandian.menshen.analysesms.adapter.SmsAdapter;
import com.lvshandian.menshen.base.BaseActivity;
import com.lvshandian.menshen.bean.Keyworkinfo;
import com.lvshandian.menshen.bean.SmsInfo;
import com.lvshandian.menshen.httprequest.HttpDatas;
import com.lvshandian.menshen.httprequest.RequestCode;
import com.lvshandian.menshen.utils.JsonUtil;
import com.lvshandian.menshen.utils.LogUtils;
import com.lvshandian.menshen.utils.NetworkUtils;
import com.lvshandian.menshen.utils.SmsWriteOpUtil;
import com.lvshandian.menshen.utils.Sp;
import com.lvshandian.menshen.utils.TextUtils;
import com.lvshandian.menshen.utils.XUtils;
import com.lvshandian.menshen.view.MyViewPager;
import com.lvshandian.menshen.view.ToastUtils;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhang on 2016/10/24.
 * 创建消息分析界面
 */
public class AnayseSmsActivity extends BaseActivity {


    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.rl_guanjianzi)
    AutoLinearLayout rlGuanjianzi;
    @Bind(R.id.rl_delete)
    AutoLinearLayout rlDelete;
    @Bind(R.id.view)
    View view;

    @Bind(R.id.rl_sc)
    AutoRelativeLayout rlSc;
    public static TextView tvAll;
    public static TextView tvSingle;
    public static boolean isShow = false;

    private MyViewPager mViewPager;
    TabLayout mTabLayout;
    private int smsNumUi = 4;
    private List<String> titles = new ArrayList<>();
    private Fragment[] fragmentClass = new Fragment[smsNumUi];
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    FragmentPagerAdapter fragmnetpageradapter;
    Bundle[] mBundles;
    private TextView tv_cancle;

    @Override
    protected int getLayoutId() {
        return R.layout.analysesms_activity_sms;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle data = msg.getData();
            int tagCode = data.getInt(HttpDatas.code);
            switch (msg.what) {
                case 200://
                    LogUtils.e("适配数据Handler1");

                    if (isRefalsh) {
                        refalshDate(listpublic, listzp, listlj, listwjz);
                        return;
                    }


                    mBundles = new Bundle[smsNumUi];


                    for (int i = 0; i < smsNumUi; i++) {
                        if (i == 0) {
                            titles.add("全部短信");
                        } else if (i == 1) {
                            titles.add("诈骗短信");
                        } else if (i == 2) {
                            titles.add("垃圾短信");
                        } else if (i == 3) {
                            titles.add("伪基站");
                        }
                        Bundle mBundle = new Bundle();
                        mBundle.putString("title", "第" + (i + 1) + "个fragment");
                        if (i == 0) {

                            LogUtils.e("listSmslistpublic" + listpublic.toString());
                            mBundle.putSerializable("list", (Serializable) listpublic);
                        } else if (i == 1) {
                            LogUtils.e("listSmslistzp" + listzp.toString());
                            mBundle.putSerializable("list", (Serializable) listzp);
                        } else if (i == 2) {
                            LogUtils.e("listSmslistlj" + listlj.toString());
                            mBundle.putSerializable("list", (Serializable) listlj);
                        } else if (i == 3) {
                            LogUtils.e("listSmslistwjz" + listwjz.toString());
                            mBundle.putSerializable("list", (Serializable) listwjz);
                        }

                        mBundles[i] = mBundle;
                    }


                    for (int j = 0; j < smsNumUi; j++) {
                        fragmentClass[j] = new FragmentSmsApp();
                    }

                    fragmnetpageradapter = new FragmentPagerAdapter(getSupportFragmentManager(),

                            fragmentClass, titles, mBundles);

                    mViewPager.setAdapter(fragmnetpageradapter);
                    mTabLayout.setupWithViewPager(mViewPager);
                    //MODE_FIXED:固定tabs，并同时显示所有的tabs。
                    //MODE_SCROLLABLE：适用于多屏展示的页卡选项，并不会把所有的tab全部显示出来，会根据title的长度来显示tab的宽度。
                    mTabLayout.setTabMode(TabLayout.MODE_FIXED);
//        mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
                    //设置tabLayout文字选中和未选中效果
                    mTabLayout.setTabTextColors(getResources().getColor(R.color.black), getResources().getColor(R.color.back347));
                    //设置下划线颜色
                    mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.back347));
                    //设置下划线高度，宽度跟随tab的宽度
                    mTabLayout.setSelectedTabIndicatorHeight(4);
                    LogUtils.e("适配数据Handler");
                    break;

                case RequestCode.KEYWORK_CODE://进行关键字的查询
                    if (tagCode == RequestCode.REQUEST_CODE) {//返回值500
                        ToastUtils.showSnackBar(snackView, data.getString(HttpDatas.info));
                        return;
                    }
                    List<Keyworkinfo> listKeyWork = JsonUtil.json2BeanList(data.getString(HttpDatas.info), Keyworkinfo.class);
                    LogUtils.e("短信界面关键字list" + listKeyWork.toString());
                    //请求完之后进行数据的过滤
                    ThreadStart(listKeyWork);
                    break;


            }
        }
    };


    @Override
    protected void initListener() {
        EventBus.getDefault().register(this); //第1步: 注册
        tvAll = (TextView) findViewById(R.id.tv_all);
        tvSingle = (TextView) findViewById(R.id.tv_single);
        tv_cancle = (TextView) findViewById(R.id.tv_cancle);

        ivBack.setOnClickListener(this);
        rlDelete.setOnClickListener(this);
        rlGuanjianzi.setOnClickListener(this);
        tv_cancle.setOnClickListener(this);
        tvAll.setOnClickListener(this);
        tvSingle.setOnClickListener(this);
    }

    ArrayList<SmsInfo> list;

    final int REQUECT_CODE_READ_SMS = 201;

    @Override
    protected void initialized() {
        mTabLayout = (TabLayout) findViewById(R.id.tl);
        mViewPager = (MyViewPager) findViewById(R.id.viewpager);
        SmsAdapter.isDelete = false;
        rlSc.setVisibility(View.GONE);
        isShow = false;
        list = new ArrayList<>();
        list.clear();

        //权限请求
        MPermissions.requestPermissions(AnayseSmsActivity.this, REQUECT_CODE_READ_SMS, Manifest.permission.READ_SMS);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //请求成功
    @PermissionGrant(REQUECT_CODE_READ_SMS)
    public void requestSdcardSuccess() {


        if (!NetworkUtils.IsNetworkAvailable(mContext)) {

            List<Keyworkinfo> list = XUtils.findAll(Keyworkinfo.class);
            if (null != list && list.size() > 0) {
                ThreadStart(list);
            } else {
                ToastUtils.showSnackBar(snackView, "请连接网络");
            }
        } else {
            requstKeyword();
        }
    }

    @PermissionDenied(REQUECT_CODE_READ_SMS)
    public void requestSdcardFailed() {
        finish();
    }

    //查询关键字
    private void requstKeyword() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("userId", getIntent().getStringExtra("userId"));
        httpDatas.getData("查询所有短信的关键字", Request.Method.POST, UrlBuilder.KEYWORK_URL, map, mHandler, RequestCode.KEYWORK_CODE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_delete:
                if (fragmnetpageradapter == null || mViewPager == null) {
                    return;
                }
                try {
                    Bundle[] bundles = fragmnetpageradapter.getmBundles();
                    Bundle bundlesa = bundles[mViewPager.getCurrentItem()];
                    bundles[mViewPager.getCurrentItem()] = bundlesa;
                    List<SmsInfo> list = (List<SmsInfo>) bundlesa.getSerializable("list");

                    if (null == list || list.size() <= 0) {
                        ToastUtils.showSnackBar(snackView, "当前页面没有数据");
                        return;
                    }

                    if (isShow) {
                        mViewPager.setCanScroll(true);
                        SmsAdapter.isDelete = false;
                        rlSc.setVisibility(View.GONE);
                        rlDelete.setVisibility(View.VISIBLE);
                        rlGuanjianzi.setVisibility(View.VISIBLE);
                        tv_cancle.setVisibility(View.GONE);
                        isShow = false;
                    } else {
                        isShow = true;
                        SmsAdapter.isDelete = true;
                        mViewPager.setCanScroll(false);
                        rlSc.setVisibility(View.VISIBLE);
                        rlDelete.setVisibility(View.GONE);
                        rlGuanjianzi.setVisibility(View.GONE);
                        tv_cancle.setVisibility(View.VISIBLE);
                    }

                    /**
                     *刷新数据
                     */
                    for (int j = 0; j < smsNumUi; j++) {
                        fragmentClass[j] = new FragmentSmsApp();
                    }
                    mBundles = bundles;
                    fragmnetpageradapter.setFragments(fragmentClass);
                    SmsAdapter.smsAdapter.notifyDataSetChanged();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rl_guanjianzi://关键字编辑
                Intent intent = new Intent(AnayseSmsActivity.this, SmsSettingActivity.class);
                intent.putExtra("userId", getIntent().getStringExtra("userId"));
                startActivity(intent);
                finish();
                break;
            case R.id.iv_back://返回
                finish();
                break;
            case R.id.tv_all://全部删除
                Bundle[] bundles1 = fragmnetpageradapter.getmBundles();
                Bundle bundlesa1 = bundles1[mViewPager.getCurrentItem()];
                ArrayList<SmsInfo> listAll = (ArrayList<SmsInfo>) bundlesa1.getSerializable("list");
                //删除
                if (!SmsWriteOpUtil.isWriteEnabled(getApplicationContext())) {
                    SmsWriteOpUtil.setWriteEnabled(
                            getApplicationContext(), true);
                }

                if (listAll.size() < 1) {
                    ToastUtils.showSnackBar(snackView, "请选择删除的短信");
                    return;
                }


                String deleteStringAll = "";
                for (int i = listAll.size() - 1; i >= 0; i--) {
                    //循环删除短信
                    LogUtils.e("_id" + listAll.get(i).get_id());
                    cr.delete(Uri.parse("content://sms/"), "_id=" + listAll.get(i).get_id().trim(), null);// 读取完短信内容后删除
                    if (deleteStringAll.equals("")) {
                        deleteStringAll = listAll.get(i).get_id();
                    } else {
                        deleteStringAll = deleteStringAll + "&" + listAll.get(i).get_id();
                    }
                }
                //刷新卸载界面
                Sp.setParam(mContext, "reflashsmsdelete", deleteStringAll);
                try {

                    mViewPager.setCanScroll(true);
                    SmsAdapter.isDelete = false;
                    rlSc.setVisibility(View.GONE);
                    rlDelete.setVisibility(View.VISIBLE);
                    rlGuanjianzi.setVisibility(View.VISIBLE);
                    tv_cancle.setVisibility(View.GONE);
                    isShow = false;

                    //更新适配器
                    LogUtils.e("uploadsingle" + listAll.toString());
                    listAll.clear();
                    bundlesa1.putSerializable("list", listAll);
                    bundles1[mViewPager.getCurrentItem()] = bundlesa1;
                    mBundles = bundles1;
                    fragmnetpageradapter.setFragments(fragmentClass);
                    SmsAdapter.smsAdapter.notifyDataSetChanged();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.tv_single://部分删除

                Bundle[] bundles = fragmnetpageradapter.getmBundles();
                Bundle bundlesa = bundles[mViewPager.getCurrentItem()];
                ArrayList<SmsInfo> listsingle = (ArrayList<SmsInfo>) bundlesa.getSerializable("list");
                ArrayList<SmsInfo> listdelete = new ArrayList<>();
                for (int i = listsingle.size() - 1; i >= 0; i--) {
                    if (SmsAdapter.isSelected.get(i)) {
                        listdelete.add(listsingle.get(i));
                        LogUtils.e("delete" + listdelete.toString());
                        listsingle.remove(listsingle.get(i));
                    }
                }

                LogUtils.e("delete" + listdelete.toString());
                if (listdelete.size() < 1) {
                    ToastUtils.showSnackBar(snackView, "请选择删除的短信");
                    return;
                }
                if (!SmsWriteOpUtil.isWriteEnabled(getApplicationContext())) {
                    SmsWriteOpUtil.setWriteEnabled(
                            getApplicationContext(), true);
                }
                String deleteString = "";

                //对短信进行删除
                for (int i = listdelete.size() - 1; i >= 0; i--) {
                    LogUtils.e("_id" + listdelete.get(i).get_id());
                    LogUtils.e("getThread_id" + listdelete.get(i).getThread_id());
                    cr.delete(Uri.parse("content://sms/"), "_id=" + listdelete.get(i).get_id().trim(), null);// 读取完短信内容后删除
                    if (deleteString.equals("")) {
                        deleteString = listdelete.get(i).get_id();
                    } else {
                        deleteString = deleteString + "&" + listdelete.get(i).get_id();
                    }


                }

                //刷新卸载界面
                Sp.setParam(mContext, "reflashsmsdelete", deleteString);

                try {
                    mViewPager.setCanScroll(true);
                    SmsAdapter.isDelete = false;
                    rlSc.setVisibility(View.GONE);
                    rlDelete.setVisibility(View.VISIBLE);
                    rlGuanjianzi.setVisibility(View.VISIBLE);
                    tv_cancle.setVisibility(View.GONE);
                    isShow = false;
                    bundlesa.putSerializable("list", listsingle);
                    bundles[mViewPager.getCurrentItem()] = bundlesa;
                    mBundles = bundles;
                    fragmnetpageradapter.setFragments(fragmentClass);
                    SmsAdapter.smsAdapter.notifyDataSetChanged();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
                LogUtils.e("deletelistUpload" + listdelete.toString());
                break;
            case R.id.tv_cancle://取消
                try {
                    mViewPager.setCanScroll(true);
                    SmsAdapter.isDelete = false;
                    rlSc.setVisibility(View.GONE);
                    rlDelete.setVisibility(View.VISIBLE);
                    rlGuanjianzi.setVisibility(View.VISIBLE);
                    tv_cancle.setVisibility(View.GONE);
                    isShow = false;
                    Bundle[] bundless = fragmnetpageradapter.getmBundles();
                    Bundle bundlesas = bundless[mViewPager.getCurrentItem()];
                    bundless[mViewPager.getCurrentItem()] = bundlesas;
                    /**
                     *刷新数据
                     */
                    for (int j = 0; j < smsNumUi; j++) {
                        fragmentClass[j] = new FragmentSmsApp();
                    }
                    mBundles = bundless;
                    fragmnetpageradapter.setFragments(fragmentClass);
                    SmsAdapter.smsAdapter.notifyDataSetChanged();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
                break;
        }


    }

    boolean isRefalsh = false;

    //接受方：
    @Subscribe //第2步:注册一个在后台线程执行的方法,用于接收事件
    public void onEventMainThread(String event) {

        if (event.equals("allReflash")) {
            isRefalsh = true;
            initialized();
        }


    }

    //刷新数据
    public void refalshDate(List<SmsInfo> list0, List<SmsInfo> list1, List<SmsInfo> list2, List<SmsInfo> list3) {
        if (fragmnetpageradapter == null || mViewPager == null) {
            return;
        }
        try {
            Bundle[] bundles = fragmnetpageradapter.getmBundles();
            Bundle bundlesa0 = bundles[0];
            Bundle bundlesa1 = bundles[1];
            Bundle bundlesa2 = bundles[2];
            Bundle bundlesa3 = bundles[3];
            bundlesa0.putSerializable("list", (Serializable) list0);
            bundlesa1.putSerializable("list", (Serializable) list1);
            bundlesa2.putSerializable("list", (Serializable) list2);
            bundlesa3.putSerializable("list", (Serializable) list3);
            bundles[0] = bundlesa0;
            bundles[1] = bundlesa1;
            bundles[2] = bundlesa2;
            bundles[3] = bundlesa3;
            /**
             *刷新数据
             */
            for (int j = 0; j < smsNumUi; j++) {
                fragmentClass[j] = new FragmentSmsApp();
            }
            mBundles = bundles;
            fragmnetpageradapter.setFragments(fragmentClass);
            SmsAdapter.smsAdapter.notifyDataSetChanged();
            isRefalsh = false;
            LogUtils.e("listSmslistreflahs");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    List<SmsInfo> listzp;
    List<SmsInfo> listlj;
    List<SmsInfo> listwjz;
    List<SmsInfo> listpublic;
    ContentResolver cr;

    private void ThreadStart(final List<Keyworkinfo> listKeyWork) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                cr = mContext.getContentResolver();
                Cursor cursor = cr.query(Uri.parse("content://sms"), new String[]{"address", "date", "body", "type", "read", "person", "_id", "thread_id"}, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String address = cursor.getString(0);
                        long date = cursor.getLong(1);
                        String body = cursor.getString(2);
                        int type = cursor.getInt(3);
                        int read = cursor.getInt(4);
                        String person = cursor.getString(5);
                        String _id = cursor.getString(6);
                        String thread_id = cursor.getString(7);
                        if (!TextUtils.isEmpty(body)) {
                            LogUtils.e("perosn");
                            list.add(new SmsInfo(_id, address, date, body, person, read, type, thread_id));
                            System.out.println("aaa" + address + date + body + type);
                        }
                    }
                    cursor.close();
                }
                int typte = 0;
                LogUtils.e("短信条数" + list.size() + list.toString());
                LogUtils.e("关键字集合" + listKeyWork.toString());
                //全部短信关键字
                List<String> listStringpublic = new ArrayList<String>();
                //诈骗短信关键字
                List<String> listStringzp = new ArrayList<String>();
                //垃圾短信关键字
                List<String> listStringlj = new ArrayList<String>();
                //伪基站短信关键字
                List<String> listStringwjz = new ArrayList<String>();
                for (int i = 0; i < listKeyWork.size(); i++) {
                    typte = listKeyWork.get(i).getKeywordType();
                    listStringpublic.add(listKeyWork.get(i).getKeyword());
                    if (typte == 1) {//诈骗短信
                        listStringzp.add(listKeyWork.get(i).getKeyword());
                    } else if (typte == 2) {//垃圾短信
                        listStringlj.add(listKeyWork.get(i).getKeyword());
                    } else if (typte == 3) {//伪基站短信
                        listStringwjz.add(listKeyWork.get(i).getKeyword());
                    }
                }
                LogUtils.e("全部关键字" + listStringpublic.toString());
                LogUtils.e("诈骗关键字" + listStringzp.toString());
                LogUtils.e("垃圾关键字" + listStringlj.toString());
                LogUtils.e("伪基站关键字" + listStringwjz.toString());
                //诈骗短信
                listzp = selectList(list, listStringzp);
                LogUtils.e("诈骗短信" + listzp.toString());
                //全部短信
                listpublic = selectList(list, listStringpublic);
                LogUtils.e("全部短信" + listpublic.toString());

                listlj = selectList(list, listStringlj);
                LogUtils.e("诈骗短信" + listlj.toString());
                //伪基站短信
                listwjz = selectList(list, listStringwjz);
                LogUtils.e("诈骗短信" + listwjz.toString());
                mHandler.sendEmptyMessage(200);
            }
        }).start();
    }

    /**
     * 参数一总集合
     * 参数二关键字
     *
     * @param list
     * @param listString
     */
    public List<SmsInfo> selectList(ArrayList<SmsInfo> list, List<String> listString) {
        ArrayList<SmsInfo> mList = new ArrayList<>();
        mList.addAll(list);
        LogUtils.e("短信内容" + mList.size());
        LogUtils.e("短信关键字" + listString.size());
        List<SmsInfo> listSeclct = new ArrayList<>();
        if (mList.size() == 0 || listString.size() == 0) {
            return listSeclct;
        }
        for (int k = mList.size() - 1; k >= 0; k--) {
            for (int m = 0; m < listString.size(); m++) {
                if (TextUtils.isString(listString.get(m), mList.get(k).getBody())) {
                    listSeclct.add(mList.get(k));
                    mList.remove(k);
                    break;
                }
            }
        }
        return listSeclct;
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("AnayseSms Page") // TODO: Define a title for the content shown.
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
        EventBus.getDefault().unregister(this);//反注册
        Sp.setParam(getContext(), "reflashsmsdelete", "");
        Sp.setParam(mContext, "read", "");
    }
}
