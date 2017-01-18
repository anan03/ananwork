package com.lvshandian.menshen.analysesms;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.lvshandian.menshen.R;
import com.lvshandian.menshen.UrlBuilder;
import com.lvshandian.menshen.analysesms.adapter.FragmentPagerAdapter;
import com.lvshandian.menshen.analysesms.adapter.FragmentPagerZiAdapter;
import com.lvshandian.menshen.base.BaseActivity;
import com.lvshandian.menshen.bean.Keyworkinfo;
import com.lvshandian.menshen.bean.PhoneBean;
import com.lvshandian.menshen.httprequest.HttpDatas;
import com.lvshandian.menshen.httprequest.RequestCode;
import com.lvshandian.menshen.utils.JsonUtil;
import com.lvshandian.menshen.utils.LogUtils;
import com.lvshandian.menshen.utils.NetworkUtils;
import com.lvshandian.menshen.utils.TextUtils;
import com.lvshandian.menshen.utils.ToastUtil;
import com.lvshandian.menshen.utils.XUtils;
import com.lvshandian.menshen.view.DialogView;
import com.lvshandian.menshen.view.ToastUtils;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhang on 2016/10/28.
 */

public class SmsSettingActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.rl_guanjianzi)
    AutoLinearLayout rlGuanjianzi;
    @Bind(R.id.rl_delete)
    AutoLinearLayout rlDelete;
    @Bind(R.id.rl_rl)
    AutoRelativeLayout rlRl;
    @Bind(R.id.tv_gjz)
    TextView tvGjz;
    public static AutoLinearLayout ll_part;
    public static ViewPager mViewPager;
    TabLayout mTabLayout;
    private int smsNumUi = 3;
    private List<String> titles = new ArrayList<>();
    private Fragment[] fragmentClass = new Fragment[smsNumUi];
    private Bundle[] mBundles;
    public static FragmentPagerZiAdapter fragmnetpageradapter;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle data = msg.getData();
            int tagCode = data.getInt(HttpDatas.code);
            switch (msg.what) {
                case RequestCode.ADD_KEYWORK_URL://添加关键字返回成功
                    if (tagCode == RequestCode.REQUEST_CODE) {//返回值504
                        ToastUtils.showSnackBar(snackView, data.getString(HttpDatas.info));
                        return;
                    }
                    List<Keyworkinfo> listKeyWorkType = JsonUtil.json2BeanList(data.getString(HttpDatas.info), Keyworkinfo.class);
                    XUtils.dropTable(Keyworkinfo.class);
                    for (int i = 0; i < listKeyWorkType.size(); i++) {
                        XUtils.addTable(listKeyWorkType.get(i));
                    }
                    Keyworkinfo keyworkinfo = new Keyworkinfo();
                    for (int i = 0; i < listKeyWorkType.size(); i++) {
                        if (tagkeyWork.equals(listKeyWorkType.get(i).getKeyword())) {
                            //相等的时候获取该对想
                            keyworkinfo = listKeyWorkType.get(i);
                            break;
                        }

                    }
                    try {
                        Bundle[] bundles = fragmnetpageradapter.getmBundles();
                        Bundle bundlesa = bundles[mViewPager.getCurrentItem()];
                        ArrayList<Keyworkinfo> lists = (ArrayList<Keyworkinfo>) bundlesa.getSerializable("list");
                        lists.add(keyworkinfo);
                        bundlesa.putSerializable("list", lists);
                        bundles[mViewPager.getCurrentItem()] = bundlesa;
                        /**
                         *刷新数据
                         */
                        for (int j = 0; j < smsNumUi; j++) {
                            fragmentClass[j] = new FragmentSeeting();
                        }
                        mBundles = bundles;
                        fragmnetpageradapter.setFragments(fragmentClass);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    }
                    break;

                case RequestCode.KEYWORK_CODE://进行关键字的查询
                    if (tagCode == RequestCode.REQUEST_CODE) {//返回值504
                        ToastUtils.showSnackBar(snackView, data.getString(HttpDatas.info));
                        return;
                    }

                    List<Keyworkinfo> listKeyWork = JsonUtil.json2BeanList(data.getString(HttpDatas.info), Keyworkinfo.class);
                    XUtils.dropTable(Keyworkinfo.class);
                    for (int i = 0; i < listKeyWork.size(); i++) {
                        XUtils.addTable(listKeyWork.get(i));
                    }
                    indata(listKeyWork);

                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.analysesms_activity_seeting;
    }

    @Override
    protected void initListener() {
        ivBack.setOnClickListener(this);
        tvGjz.setOnClickListener(this);
    }

    public static View view;

    @Override
    protected void initialized() {

        mTabLayout = (TabLayout) findViewById(R.id.tl);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        ll_part = (AutoLinearLayout) findViewById(R.id.ll_part_part);
        view = (View) findViewById(R.id.view);
        mBundles = new Bundle[smsNumUi];
//        inite();

        if (!NetworkUtils.IsNetworkAvailable(mContext)) {

            List<Keyworkinfo> list = XUtils.findAll(Keyworkinfo.class);
            if (null != list && list.size() > 0) {
                indata(list);
            } else {
                ToastUtils.showSnackBar(snackView, "请连接网络");
            }
        } else {
            requstKeyword();
        }


    }

    private void indata(List<Keyworkinfo> listKeyWork) {
        //全部短信关键字
        ArrayList<Keyworkinfo> listStringpublic = new ArrayList<Keyworkinfo>();
        //诈骗短信关键字
        ArrayList<Keyworkinfo> listStringzp = new ArrayList<Keyworkinfo>();
        //垃圾短信关键字
        ArrayList<Keyworkinfo> listStringlj = new ArrayList<Keyworkinfo>();
        //伪基站短信关键字
        ArrayList<Keyworkinfo> listStringwjz = new ArrayList<Keyworkinfo>();
        int typte = 0;

        for (int i = 0; i < listKeyWork.size(); i++) {

            typte = listKeyWork.get(i).getKeywordType();
            listStringpublic.add(listKeyWork.get(i));
            if (typte == 1) {//诈骗短信
                listStringzp.add(listKeyWork.get(i));
            } else if (typte == 2) {//垃圾短信
                listStringlj.add(listKeyWork.get(i));
            } else if (typte == 3) {//伪基站短信
                listStringwjz.add(listKeyWork.get(i));
            }
        }


        if (listStringzp.size() != 0) {
            for (int i = listStringzp.size() - 1; i >= 0; i--) {

                if (listStringzp.get(i).getUserId() == 0) {
                    listStringzp.add(0, listStringzp.get(i));
                    listStringzp.remove(i + 1);
                    break;
                }

            }
        }
        if (listStringlj.size() != 0) {
            for (int i = listStringlj.size() - 1; i >= 0; i--) {

                if (listStringlj.get(i).getUserId() == 0) {
                    listStringlj.add(0, listStringlj.get(i));
                    listStringlj.remove(i + 1);
                    break;
                }
            }
        }
        if (listStringwjz.size() != 0) {
            for (int i = listStringwjz.size() - 1; i >= 0; i--) {
                if (listStringwjz.get(i).getUserId() == 0) {
                    listStringwjz.add(0, listStringwjz.get(i));
                    listStringwjz.remove(i + 1);
                    break;
                }

            }
        }


        for (int i = 0; i < smsNumUi; i++) {
            if (i == 0) {
                titles.add("诈骗短信");
            } else if (i == 1) {
                titles.add("垃圾短信");

            } else if (i == 2) {
                titles.add("伪基站");
            }
            Bundle mBundle = new Bundle();
            mBundle.putString("title", "第" + (i + 1) + "个fragment");
            if (i == 0) { //诈骗短信


                mBundle.putSerializable("list", listStringzp);
            } else if (i == 1) { //垃圾短信


                mBundle.putSerializable("list", listStringlj);
            } else if (i == 2) { //伪基站

                mBundle.putSerializable("list", listStringwjz);
            }
            mBundle.putInt("tag", i);
            mBundles[i] = mBundle;
        }


        for (int j = 0; j < smsNumUi; j++) {
            fragmentClass[j] = new FragmentSeeting();
        }


        fragmnetpageradapter = new FragmentPagerZiAdapter(getSupportFragmentManager(),

                fragmentClass, titles, mBundles);
        mViewPager.setAdapter(fragmnetpageradapter);
        mTabLayout.setupWithViewPager(mViewPager);
        //MODE_SCROLLABLE：适用于多屏展示的页卡选项，并不会把所有的tab全部显示出来，会根据title的长度来显示tab的宽度。
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
//        mTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        //设置tabLayout文字选中和未选中效果
        mTabLayout.setTabTextColors(getResources().getColor(R.color.black), getResources().getColor(R.color.back347));
        //设置下划线颜色
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.back347));
        //设置下划线高度，宽度跟随tab的宽度
        mTabLayout.setSelectedTabIndicatorHeight(4);


    }

    //查询关键字
    private void requstKeyword() {


        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("userId", getIntent().getStringExtra("userId"));
        httpDatas.getData("查询所有短信的关键字", Request.Method.POST, UrlBuilder.KEYWORK_URL, map, mHandler, RequestCode.KEYWORK_CODE);

    }

    private String tagkeyWork;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_back://
                Intent intent = new Intent(SmsSettingActivity.this, AnayseSmsActivity.class);
                intent.putExtra("userId", getIntent().getStringExtra("userId"));
                startActivity(intent);
                finish();
                break;
            case R.id.tv_gjz://添加关键字

                new DialogView(mContext, view, "请输入关键字", "确定", "取消", 1, new DialogView.MyCallback() {
                    @Override
                    public void refreshActivity() {

                    }

                    @Override
                    public void refreshActivity(String tag) {
                        //得到添加的关键字存到数据库
                        tagkeyWork = tag;
                        if (null == tag || tag.trim().equals("")) {

                            ToastUtils.showSnackBar(snackView, "关键字不能为空");
                            return;
                        }
                        //进行关键字的添加
                        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
                        map.put("userId", getIntent().getStringExtra("userId"));
                        map.put("keywordType", mViewPager.getCurrentItem() + 1 + "");
                        map.put("keyword", tag);
                        httpDatas.getData("添加短信关键字", Request.Method.POST, UrlBuilder.ADD_KEYWORK_URL, map, mHandler, RequestCode.ADD_KEYWORK_URL);
                    }

                    @Override
                    public void refreshActivity(String context, String minString, String maxString) {

                    }
                });
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);

    }

}
