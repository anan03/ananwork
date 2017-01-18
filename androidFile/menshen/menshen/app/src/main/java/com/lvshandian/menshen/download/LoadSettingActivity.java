package com.lvshandian.menshen.download;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.lvshandian.menshen.R;
import com.lvshandian.menshen.UrlBuilder;
import com.lvshandian.menshen.base.BaseActivity;
import com.lvshandian.menshen.bean.ApkBean;
import com.lvshandian.menshen.bean.Keyworkinfo;
import com.lvshandian.menshen.download.adapter.LoadSettingAdapter;
import com.lvshandian.menshen.httprequest.HttpDatas;
import com.lvshandian.menshen.httprequest.RequestCode;
import com.lvshandian.menshen.utils.JsonUtil;
import com.lvshandian.menshen.utils.LogUtils;
import com.lvshandian.menshen.utils.NetworkUtils;
import com.lvshandian.menshen.utils.Sp;
import com.lvshandian.menshen.utils.TextUtils;
import com.lvshandian.menshen.utils.XUtils;
import com.lvshandian.menshen.view.DialogView;
import com.lvshandian.menshen.view.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhang on 2016/10/28.
 * 创建设置权限的界面
 */

public class LoadSettingActivity extends BaseActivity {
    private ImageView iv_back;
    private ListView listView;
    TextView tv_size_min, tv_size_max;
    private TextView tv_gjz;
    private LinearLayout ll_text;
    public static RelativeLayout relativeLayou;
    private TextView tv_mb;
    Keyworkinfo keyworkinfo;
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
                    LogUtils.e("添加成功--" + data.getString(HttpDatas.info));
                    List<Keyworkinfo> listKeyWorkType = JsonUtil.json2BeanList(data.getString(HttpDatas.info), Keyworkinfo.class);
                    LogUtils.e("当前type下的关键字--" + listKeyWorkType.toString());
                    XUtils.dropTable(Keyworkinfo.class);
                    for (int i = 0; i < listKeyWorkType.size(); i++) {
                        XUtils.addTable(listKeyWorkType.get(i));
                        LogUtils.e("插入数据---" + listKeyWorkType.get(i));
                    }
                    thred(listKeyWorkType);
                    break;
                case RequestCode.KEYWORK_CODE://查询关键字成功
                    if (tagCode == RequestCode.REQUEST_CODE) {//返回值504
                        ToastUtils.showSnackBar(snackView, data.getString(HttpDatas.info));
                        return;
                    }
                    LogUtils.e("查询成功--" + data.getString(HttpDatas.info));
                    List<Keyworkinfo> listKeyWorkType1 = JsonUtil.json2BeanList(data.getString(HttpDatas.info), Keyworkinfo.class);
                    LogUtils.e("当前type下的关键字--" + listKeyWorkType1.toString());
                    XUtils.dropTable(Keyworkinfo.class);
                    for (int i = 0; i < listKeyWorkType1.size(); i++) {
                        XUtils.addTable(listKeyWorkType1.get(i));
                    }
                    //设置适配器
                    thred(listKeyWorkType1);
                    break;
                case RequestCode.SELECT_APKSIZE://查询apk包名的大小
                    if (tagCode == RequestCode.REQUEST_CODE) {//返回值504
                        ToastUtils.showSnackBar(snackView, data.getString(HttpDatas.info));
                        return;
                    }
                    List<ApkBean> ApkBean = JsonUtil.json2BeanList(data.getString(HttpDatas.info), ApkBean.class);
                    if (null != ApkBean && ApkBean.size() != 0) {
                        if (!TextUtils.isEmpty("" + ApkBean.get(0).getAppSizeMax()) && !TextUtils.isEmpty("" + ApkBean.get(0).getAppSizeSmall())) {
                            Sp.setParam(mContext, "ApkMax", ApkBean.get(0).getAppSizeMax());
                            Sp.setParam(mContext, "ApkMin", ApkBean.get(0).getAppSizeSmall());
                            tv_size_max.setText("" + ApkBean.get(0).getAppSizeMax());
                            tv_size_min.setText("" + ApkBean.get(0).getAppSizeSmall());

                            if (!TextUtils.isEmpty(ApkBean.get(0).getName())) {
                                keyworkinfo = new Keyworkinfo();
                                keyworkinfo.setKeyword(ApkBean.get(0).getName());
                                keyworkinfo.setUserId(0);
                                requstKeyword();
                            }
                        }

                    }


                    break;
            }
        }


    };

    private void thred(List<Keyworkinfo> listKeyWorkType) {
        list.clear();
        int type = 0;
        for (int i = 0; i < listKeyWorkType.size(); i++) {
            type = listKeyWorkType.get(i).getKeywordType();
            if (type == 4) {
                //如果是过滤卸载关键字
                list.add(listKeyWorkType.get(i));
            }
        }
//        if (null == list || list.size() == 0) {
//
//            ll_text.setVisibility(View.GONE);
//        } else {
//            ll_text.setVisibility(View.VISIBLE);
//            String[] str = TextUtils.out(list.get(list.size() - 1).getKeyword());
//            LogUtils.e("str.length" + str.length);
//            if (str.length == 3) {
//                tv_size_min.setText(str[1]);
//                tv_size_max.setText(str[2]);
//            }
//        }
//        for (int i = 0; i < list.size(); i++) {
//            if (TextUtils.isString("riskdefault", list.get(i).getKeyword())) {
//                list.remove(i);
//            }
//
//        }

        if (null != keyworkinfo) {
            list.add(0, keyworkinfo);
        }
        //设置头部赋值
        LoadSettingAdapter adapter = new LoadSettingAdapter(relativeLayou, LoadSettingActivity.this, list, R.layout.anaysesms_item_framentsetting);
        listView.setAdapter(adapter);
        //进行关键字的展示


    }


    @Override
    protected int getLayoutId() {
        return R.layout.download_activity_seeting;
    }


    @Override
    protected void initListener() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        listView = (ListView) findViewById(R.id.listview);
        tv_gjz = (TextView) findViewById(R.id.tv_gjz);
        tv_size_max = (TextView) findViewById(R.id.tv_size_max);
        ll_text = (LinearLayout) findViewById(R.id.ll_text);
        tv_size_min = (TextView) findViewById(R.id.tv_size_min);
        relativeLayou = (RelativeLayout) findViewById(R.id.ll_part_part);
        iv_back.setOnClickListener(this);
        tv_gjz.setOnClickListener(this);


    }

    ArrayList<Keyworkinfo> list = new ArrayList<Keyworkinfo>();

    @Override
    protected void initialized() {
        if (!NetworkUtils.IsNetworkAvailable(mContext)) {
            //无网络处理取数据库
//            List<Keyworkinfo> list = XUtils.findAll(Keyworkinfo.class);
//
//            if (null != list && list.size() != 0) {
//                thred(list);
//            } else {
            ll_text.setVisibility(View.GONE);
            ToastUtils.showSnackBar(snackView, "请连接网络");
//            }
            return;
        }


        requspApk();
    }


    //应用过滤关键字
    private void requstKeyword() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("userId", getIntent().getStringExtra("userId"));
        httpDatas.getData("查询所有短信的关键字type=4下载", Request.Method.POST, UrlBuilder.KEYWORK_URL, map, mHandler, RequestCode.KEYWORK_CODE);
    }

    //应用包大小请求
    private void requspApk() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("userId", getIntent().getStringExtra("userId"));
        httpDatas.getData("查询所有短信的关键字type=4下载", Request.Method.POST, UrlBuilder.SELECT_APKSIZE, map, mHandler, RequestCode.SELECT_APKSIZE);
    }

    private String tagkeyWork;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.iv_back://
                Intent intent = new Intent(LoadSettingActivity.this, DownLoadActivity.class);
                intent.putExtra("userId", getIntent().getStringExtra("userId"));
                startActivity(intent);
                finish();
                break;
            case R.id.tv_gjz://添加关键字

                String max = "";
                String min = "";
                if (!TextUtils.isEmpty(TextUtils.getTextContent(tv_size_max))) {
                    max = TextUtils.getTextContent(tv_size_max);
                }
                if (!TextUtils.isEmpty(TextUtils.getTextContent(tv_size_min))) {
                    min = TextUtils.getTextContent(tv_size_min);
                }

                new DialogView(mContext, relativeLayou, min, max, 1, new DialogView.MyCallback() {
                    @Override
                    public void refreshActivity() {

                    }

                    @Override
                    public void refreshActivity(String tag) {
                        //得到添加的关键字存到数据库
                    }

                    @Override
                    public void refreshActivity(String context, String minString, String maxString) {

//                        String tag = context + "&" + minString.trim() + "&" + maxString.trim();
                        String tag = context;

                        tagkeyWork = tag;
                        if (null == tag || tag.trim().equals("")) {

                            ToastUtils.showSnackBar(snackView, "关键字不能为空");
                            return;
                        }
                        //进行关键字的添加
                        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
                        map.put("userId", getIntent().getStringExtra("userId"));
                        map.put("keywordType", "4");
                        map.put("keyword", tag);
                        httpDatas.getData("添加App关键字", Request.Method.POST, UrlBuilder.ADD_KEYWORK_URL, map, mHandler, RequestCode.ADD_KEYWORK_URL);
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
