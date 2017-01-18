package com.lvshandian.menshen.analysesms;

import android.Manifest;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.lvshandian.menshen.MainActivity;
import com.lvshandian.menshen.R;
import com.lvshandian.menshen.UrlBuilder;
import com.lvshandian.menshen.analysesms.adapter.FragmentPagerAdapter;
import com.lvshandian.menshen.analysesms.adapter.SmsAdapter;
import com.lvshandian.menshen.base.BaseActivity;
import com.lvshandian.menshen.bean.Keyworkinfo;
import com.lvshandian.menshen.bean.SmsInfo;
import com.lvshandian.menshen.bean.SmsUpload;
import com.lvshandian.menshen.bean.User;
import com.lvshandian.menshen.httprequest.HttpDatas;
import com.lvshandian.menshen.httprequest.RequestCode;
import com.lvshandian.menshen.utils.JsonUtil;
import com.lvshandian.menshen.utils.LogUtils;
import com.lvshandian.menshen.utils.NetworkUtils;
import com.lvshandian.menshen.utils.Sp;
import com.lvshandian.menshen.utils.TextUtils;
import com.lvshandian.menshen.utils.ToastUtil;
import com.lvshandian.menshen.utils.XUtils;
import com.lvshandian.menshen.view.DialogView;
import com.lvshandian.menshen.view.MyViewPager;
import com.lvshandian.menshen.view.ToastUtils;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import org.apache.commons.logging.Log;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhang on 2016/10/24.
 * 创建上传短信界面
 */
public class AnayseSmsUploadActivity extends BaseActivity {


    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.rl_guanjianzi)
    AutoLinearLayout rlGuanjianzi;
    @Bind(R.id.rl_delete)
    AutoLinearLayout rlDelete;
    @Bind(R.id.ll_parent_view_view)
    AutoLinearLayout llParentViewView;
    @Bind(R.id.rl_sc)
    AutoRelativeLayout rlSc;
    public static TextView tvAll;
    public static TextView tvSingle;
    private MyViewPager mViewPager;
    TabLayout mTabLayout;
    private int smsNumUi = 4;
    private List<String> titles = new ArrayList<>();
    private Fragment[] fragmentClass = new Fragment[smsNumUi];
    private GoogleApiClient client;
    private FragmentPagerAdapter fragmnetpageradapter;
    public static boolean isShow = false;
    private TextView tv_cancle;

    @Override
    protected int getLayoutId() {
        return R.layout.analysesms_activity_sms_upload;
    }

    private Bundle[] mBundles;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle data = msg.getData();
            int tagCode = data.getInt(HttpDatas.code);
            switch (msg.what) {
                case 200://
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
                        mBundle.putString("title", i + "");
                        if (i == 0) {
                            mBundle.putSerializable("list", (Serializable) listpublic);
                        } else if (i == 1) {
                            mBundle.putSerializable("list", (Serializable) listzp);
                        } else if (i == 2) {
                            mBundle.putSerializable("list", (Serializable) listlj);
                        } else if (i == 3) {
                            mBundle.putSerializable("list", (Serializable) listwjz);
                        }
                        mBundles[i] = mBundle;
                    }

                    for (int j = 0; j < smsNumUi; j++) {
                        fragmentClass[j] = new FragmentApp();
                    }
                    fragmnetpageradapter = new FragmentPagerAdapter(getSupportFragmentManager(),
                            fragmentClass, titles, mBundles);
                    mViewPager.setOffscreenPageLimit(1);
                    mViewPager.setAdapter(fragmnetpageradapter);
                    mTabLayout.setupWithViewPager(mViewPager);

                    CharSequence character = fragmnetpageradapter.getPageTitle(0);

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
                case RequestCode.YIJIAN_CODE://举报成功

                    if (tagCode == RequestCode.REQUEST_CODE) {
                        ToastUtils.showSnackBar(snackView, data.getString(HttpDatas.info));
                        return;
                    }
                    EventBus.getDefault().post("reflashMessage");
                    new DialogView(mContext, llParentViewView, "", 1, 1, 1, new DialogView.MyCallback() {
                        @Override

                        public void refreshActivity() {

                        }

                        @Override
                        public void refreshActivity(String tag) {

                        }

                        @Override
                        public void refreshActivity(String context, String minString, String maxString) {

                        }
                    });
                    break;
                case RequestCode.SMS_UPLOAD://上传短信成功

                    if (tagCode == RequestCode.REQUEST_CODE) {
                        ToastUtils.showSnackBar(snackView, data.getString(HttpDatas.info));
                        return;
                    }
                    EventBus.getDefault().post("reflashMessage");
                    LogUtils.d("上传短信成功---");
                    new DialogView(mContext, llParentViewView, data.getString(HttpDatas.info), 1, 1, 1, new DialogView.MyCallback() {
                        @Override

                        public void refreshActivity() {
                            try {
                                mViewPager.setCanScroll(true);
                                SmsAdapter.isDelete = false;
                                rlSc.setVisibility(View.GONE);
                                tv_cancle.setVisibility(View.GONE);
                                rlDelete.setVisibility(View.VISIBLE);
                                rlGuanjianzi.setVisibility(View.VISIBLE);
                                isShow = false;
                                Bundle[] bundles = fragmnetpageradapter.getmBundles();
                                Bundle bundlesa = bundles[mViewPager.getCurrentItem()];
                                bundles[mViewPager.getCurrentItem()] = bundlesa;
                                /**
                                 *刷新数据
                                 */
                                for (int j = 0; j < smsNumUi; j++) {
                                    fragmentClass[j] = new FragmentApp();
                                }
                                mBundles = bundles;
                                fragmnetpageradapter.setFragments(fragmentClass);
                                SmsAdapter.smsAdapter.notifyDataSetChanged();

                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InstantiationException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void refreshActivity(String tag) {

                        }

                        @Override
                        public void refreshActivity(String context, String minString, String maxString) {

                        }
                    });

                    break;
            }
        }
    };

    @Override
    protected void initListener() {
        tvAll = (TextView) findViewById(R.id.tv_all);
        tvSingle = (TextView) findViewById(R.id.tv_single);
        tv_cancle = (TextView) findViewById(R.id.tv_cancle);
        ivBack.setOnClickListener(this);
        rlDelete.setOnClickListener(this);
        rlGuanjianzi.setOnClickListener(this);
//        tvAll.setOnClickListener(this);
        tvAll.setVisibility(View.GONE);
        tv_cancle.setOnClickListener(this);
        tvSingle.setOnClickListener(this);
        isShow = false;
    }

    ArrayList<SmsInfo> list = new ArrayList<>();
    final int REQUECT_CODE_READ_SMS = 100;
    private String userId;

    @Override
    protected void initialized() {
        mTabLayout = (TabLayout) findViewById(R.id.tl);
        mViewPager = (MyViewPager) findViewById(R.id.viewpager);
        SmsAdapter.isDelete = false;
        rlSc.setVisibility(View.GONE);
        list = new ArrayList<>();
        list.clear();
        mTabLayout.setActivated(false);
        mTabLayout.setClipChildren(false);
        mTabLayout.setSelected(false);
        mTabLayout.setEnabled(false);
        mTabLayout.setClickable(false);
        //权限请求
        MPermissions.requestPermissions(AnayseSmsUploadActivity.this, REQUECT_CODE_READ_SMS, Manifest.permission.PROCESS_OUTGOING_CALLS);
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
        userId = getIntent().getStringExtra("userId");
        httpDatas.getData("查询所有短信的关键字", Request.Method.POST, UrlBuilder.KEYWORK_URL, map, mHandler, RequestCode.KEYWORK_CODE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_delete:
                new DialogView(mContext, llParentViewView, "请输入关键字", "确定", 1, 1, new DialogView.MyCallback() {
                    @Override
                    public void refreshActivity() {
                    }

                    @Override
                    public void refreshActivity(String tag) {
                        if (TextUtils.isEmpty(tag)) {
                            ToastUtils.showSnackBar(snackView, "举报内容不能为空");
                            return;
                        }
                        //得到添加的关键字存到数据库
                        //进行关键字的添加
                        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
                        //意见反馈
                        map.put("userId", getIntent().getStringExtra("userId"));
                        map.put("content", tag);
                        map.put("status", "2");
                        httpDatas.getData("举报短信", Request.Method.POST, UrlBuilder.YIJIAN_URL, map, mHandler, RequestCode.YIJIAN_CODE);
                    }

                    @Override
                    public void refreshActivity(String context, String minString, String maxString) {

                    }
                });
                break;
            case R.id.rl_guanjianzi://上传
                if (fragmnetpageradapter == null || mViewPager == null) {
                    return;
                }
                try {
                    if (isShow) {
                        mViewPager.setCanScroll(true);
                        SmsAdapter.isDelete = false;
                        rlSc.setVisibility(View.GONE);
                        isShow = false;
                        tv_cancle.setVisibility(View.GONE);
                        rlDelete.setVisibility(View.VISIBLE);
                        rlGuanjianzi.setVisibility(View.VISIBLE);

                    } else {
                        isShow = true;
                        SmsAdapter.isDelete = true;
                        mViewPager.setCanScroll(false);
                        rlSc.setVisibility(View.VISIBLE);
                        tv_cancle.setVisibility(View.VISIBLE);
                        rlDelete.setVisibility(View.GONE);
                        rlGuanjianzi.setVisibility(View.GONE);

                    }
                    Bundle[] bundles = fragmnetpageradapter.getmBundles();
                    Bundle bundlesa = bundles[mViewPager.getCurrentItem()];
                    bundles[mViewPager.getCurrentItem()] = bundlesa;
                    /**
                     *刷新数据
                     */
                    for (int j = 0; j < smsNumUi; j++) {
                        fragmentClass[j] = new FragmentApp();
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
            case R.id.iv_back://返回
                finish();
                break;
            case R.id.tv_all://全部上传
                if (!NetworkUtils.IsNetworkAvailable(mContext)) {
                    ToastUtils.showSnackBar(snackView, "请连接网络");
                    return;
                }
                Bundle[] bundles1 = fragmnetpageradapter.getmBundles();
                Bundle bundlesa1 = bundles1[mViewPager.getCurrentItem()];
                ArrayList<SmsInfo> listAll = (ArrayList<SmsInfo>) bundlesa1.getSerializable("list");
                LogUtils.e("uploadsingle" + listAll.toString());
                List<SmsUpload> list = new ArrayList<>();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for (int i = 0; i < listAll.size(); i++) {

                    list.add(new SmsUpload(mViewPager.getCurrentItem() + 1, Integer.parseInt(userId.trim()),
                            listAll.get(i).getAddress(), listAll.get(i).getPerson(),
                            new Date(listAll.get(i).getDate()), listAll.get(i).getBody()));
                }
                //短信上传
                ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
                map.put("list", "" + JsonUtil.toJson(list) + "");
                LogUtils.e("JsonUtil----" + JsonUtil.toJson(list));
                httpDatas.getData("短信上传", Request.Method.POST, UrlBuilder.SMS_UPLOAD, map, mHandler, RequestCode.SMS_UPLOAD);
                break;
            case R.id.tv_single://上传
                if (!NetworkUtils.IsNetworkAvailable(mContext)) {
                    ToastUtils.showSnackBar(snackView, "请连接网络");
                    return;
                }
                Bundle[] bundles = fragmnetpageradapter.getmBundles();
                Bundle bundlesa = bundles[mViewPager.getCurrentItem()];
                ArrayList<SmsInfo> listsingle = (ArrayList<SmsInfo>) bundlesa.getSerializable("list");

                ArrayList<SmsInfo> listUpload = new ArrayList<>();
                for (int i = 0; i < listsingle.size(); i++) {
                    if (SmsAdapter.isSelected.get(i)) {
                        listUpload.add(listsingle.get(i));
                    }
                }
                LogUtils.e("uploadsingle" + listUpload.toString());
                if (listUpload.size() < 1) {
                    ToastUtils.showSnackBar(snackView, "请选择上传短信");
                    return;
                }
                if (listUpload.size() > 10) {
                    ToastUtils.showSnackBar(snackView, "上传短信个数不能超过10个");
                    return;
                }
                ToastUtils.showSnackBar(snackView, "uploadDatelistUpload" + listUpload.toString());
                LogUtils.e("uploadsingle" + listUpload.toString());
                List<SmsUpload> list1 = new ArrayList<>();
                SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for (int i = 0; i < listUpload.size(); i++) {
                    list1.add(new SmsUpload(listUpload.get(i).getUpLoadType(), Integer.parseInt(userId.trim()),
                            listUpload.get(i).getAddress(), listUpload.get(i).getPerson(),
                            new Date(listUpload.get(i).getDate()), listUpload.get(i).getBody()));
                }
                //短信上传
                ConcurrentHashMap<String, String> map1 = new ConcurrentHashMap<>();
                map1.put("list", "" + JsonUtil.toJson(list1) + "");
                LogUtils.e("JsonUtil----" + JsonUtil.toJson(list1));
                httpDatas.getData("短信上传", Request.Method.POST, UrlBuilder.SMS_UPLOAD, map1, mHandler, RequestCode.SMS_UPLOAD);
                break;
            case R.id.tv_cancle://取消
                try {
                    mViewPager.setCanScroll(true);
                    SmsAdapter.isDelete = false;
                    rlSc.setVisibility(View.GONE);
                    tv_cancle.setVisibility(View.GONE);
                    rlDelete.setVisibility(View.VISIBLE);
                    rlGuanjianzi.setVisibility(View.VISIBLE);
                    isShow = false;
                    Bundle[] bundless = fragmnetpageradapter.getmBundles();
                    Bundle bundlesas = bundless[mViewPager.getCurrentItem()];
                    bundless[mViewPager.getCurrentItem()] = bundlesas;
                    /**
                     *刷新数据
                     */
                    for (int j = 0; j < smsNumUi; j++) {
                        fragmentClass[j] = new FragmentApp();
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

    //全部短信关键字
    List<String> listStringpublic = new ArrayList<String>();
    //诈骗短信关键字
    List<String> listStringzp = new ArrayList<String>();
    //垃圾短信关键字
    List<String> listStringlj = new ArrayList<String>();
    //伪基站短信关键字
    List<String> listStringwjz = new ArrayList<String>();

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

    private void ThreadStart(final List<Keyworkinfo> listKeyWork) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                ContentResolver cr = mContext.getContentResolver();

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

                ArrayList<SmsInfo> listce = new ArrayList<SmsInfo>();

                listce = list;
                LogUtils.e("短信条数" + list.size() + list.toString());
                LogUtils.e("关键字集合" + listKeyWork.toString());

                List<Keyworkinfo> publicKey = new ArrayList<Keyworkinfo>();
                for (int i = 0; i < listKeyWork.size(); i++) {

                    typte = listKeyWork.get(i).getKeywordType();
                    listStringpublic.add(listKeyWork.get(i).getKeyword());
                    if (typte == 1) {//诈骗短信
                        listStringzp.add(listKeyWork.get(i).getKeyword());
                        publicKey.add(listKeyWork.get(i));

                    } else if (typte == 2) {//垃圾短信
                        listStringlj.add(listKeyWork.get(i).getKeyword());
                        publicKey.add(listKeyWork.get(i));
                    } else if (typte == 3) {//伪基站短信
                        listStringwjz.add(listKeyWork.get(i).getKeyword());
                        publicKey.add(listKeyWork.get(i));
                    }
                }
                LogUtils.e("全部关键字" + listStringpublic.toString());
                LogUtils.e("诈骗关键字" + listStringzp.toString());
                LogUtils.e("垃圾关键字" + listStringlj.toString());
                LogUtils.e("伪基站关键字" + listStringwjz.toString());
                //诈骗短信
                listzp = selectList(list, listStringzp, 1);
                LogUtils.e("诈骗短信" + listzp.toString());
                //全部短信
                listpublic = selectAllList(list, publicKey);
                LogUtils.e("全部短信" + listpublic.toString());

                listlj = selectList(list, listStringlj, 2);
                LogUtils.e("诈骗短信" + listlj.toString());
                //伪基站短信
                listwjz = selectList(list, listStringwjz, 3);
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
    public List<SmsInfo> selectList(ArrayList<SmsInfo> list, List<String> listString, int tag) {
        ArrayList<SmsInfo> mList = new ArrayList<>();
        mList.clear();
        mList.addAll(list);
        LogUtils.e("短信内容" + mList.toString());
        LogUtils.e("短信关键字" + listString.toString());
        List<SmsInfo> listSeclct = new ArrayList<>();
        LogUtils.e("短信关键字" + mList.size());
        LogUtils.e("短信关键字" + listString.size());
        for (int k = mList.size() - 1; k >= 0; k--) {

            for (int m = 0; m < listString.size(); m++) {
                if (TextUtils.isString(listString.get(m), mList.get(k).getBody())) {
                    mList.get(k).setUpLoadType(tag);
                    listSeclct.add(mList.get(k));
                    mList.remove(k);
                    break;
                }
            }
        }
        return listSeclct;
    }

    /**
     * 全部关键字
     *
     * @param list
     * @param listKeyWork
     */
    public List<SmsInfo> selectAllList(ArrayList<SmsInfo> list, List<Keyworkinfo> listKeyWork) {
        ArrayList<SmsInfo> mList = new ArrayList<>();
        mList.clear();
        mList.addAll(list);
        List<SmsInfo> listSeclct = new ArrayList<>();
        LogUtils.e("短信关键字p" + listKeyWork.size());
        LogUtils.e("短信关键字listKeyWork" + listKeyWork.toString());
        LogUtils.e("短信内容p" + mList.toString());
        LogUtils.e("短信内容p" + mList.size());

        for (int k = mList.size() - 1; k >= 0; k--) {
            for (int m = 0; m < listKeyWork.size(); m++) {
                if (TextUtils.isString(listKeyWork.get(m).getKeyword(), mList.get(k).getBody())) {
                    mList.get(k).setUpLoadType(listKeyWork.get(m).getKeywordType());
                    LogUtils.e("短信关键字listSeclct" + mList.get(k));
                    listSeclct.add(mList.get(k));
                    mList.remove(k);
                    break;
                }
            }
        }
        LogUtils.e("短信关键字mList" + mList.toString());
        LogUtils.e("短信关键字listSeclct" + listSeclct.toString());
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

    //接受方：
    @Subscribe //第2步:注册一个在后台线程执行的方法,用于接收事件
    public void onEventMainThread(String event) {


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
        Sp.setParam(mContext, "read", "");
    }
}
