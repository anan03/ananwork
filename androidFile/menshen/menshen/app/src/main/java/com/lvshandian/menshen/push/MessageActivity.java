package com.lvshandian.menshen.push;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.lvshandian.menshen.R;
import com.lvshandian.menshen.UrlBuilder;
import com.lvshandian.menshen.base.BaseActivity;
import com.lvshandian.menshen.httprequest.HttpDatas;
import com.lvshandian.menshen.httprequest.RequestCode;
import com.lvshandian.menshen.push.adapter.MessageAdapter;
import com.lvshandian.menshen.utils.JsonUtil;
import com.lvshandian.menshen.utils.LogUtils;
import com.lvshandian.menshen.utils.ToastUtil;
import com.lvshandian.menshen.view.ToastUtils;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhang on 2016/10/24.
 * 创建推消息界面
 */

public class MessageActivity extends BaseActivity {
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.ll_parent_view)
    AutoLinearLayout llParentView;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.rl_includ)
    AutoRelativeLayout rlInclud;
    @Bind(R.id.refresh)
    MaterialRefreshLayout refresh;
    @Bind(R.id.lv)
    ListView lv;
    private List<com.lvshandian.menshen.bean.Message> messageInfoList;
    private MessageAdapter messageAdapter;
    boolean isRequst = true;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();

            int tagCode = data.getInt(HttpDatas.code);

            switch (msg.what) {
                case RequestCode.MESSAGE_CODE://消息数据请求成功
                    if (tagCode == RequestCode.REQUEST_CODE) {
                        ToastUtils.showSnackBar(snackView, data.getString(HttpDatas.info));
                        return;
                    }
                    LogUtils.e("messagedata" + data.getString(httpDatas.info));
                    List<com.lvshandian.menshen.bean.Message> messageNew = (List<com.lvshandian.menshen.bean.Message>) JsonUtil.json2BeanList(data.getString(httpDatas.info), com.lvshandian.menshen.bean.Message.class);
                    LogUtils.e("message" + messageNew.size() + messageNew.toString());
                    LogUtils.e("===" + messageNew.toString());
                    if (null == messageNew || messageNew.size() < 1) {
                        isRequst = false;
                        ToastUtils.showSnackBar(snackView, "加载完毕");
                        LogUtils.e("加载完毕");
                        refresh.finishRefreshLoadMore();
                        refresh.finishRefreshing();
                        refresh.setLoadMore(false);
                        return;
                    }

                    if (page == 1) {//刷新
                        messageInfoList = messageNew;
                        messageAdapter = new MessageAdapter(mContext, messageInfoList, R.layout.item_phone);
                        lv.setAdapter(messageAdapter);
                        refresh.setLoadMore(true);
                        // refresh complete
                        refresh.finishRefresh();

                    } else {//加载
                        messageInfoList.addAll(messageNew);
                        messageAdapter.notifyDataSetChanged();
                        // load more refresh complete
                        refresh.finishRefreshLoadMore();
                    }

                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //跳转到消息详情界面
                            Intent intent = new Intent(getContext(), MessageDetails.class);
                            intent.putExtra("msg", messageInfoList.get(position));
                            intent.putExtra("userId", getIntent().getStringExtra("userId"));
                            startActivity(intent);
                        }
                    });


                    break;


            }
        }
    };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected int getLayoutId() {
        return R.layout.push_activity_message;
    }

    @Override
    protected void initListener() {
        ivBack.setOnClickListener(this);
    }

    private String userid = "";

    @Override
    protected void initialized() {
        userid = getIntent().getStringExtra("userId");
        LogUtils.e("userid" + userid);
        rlInclud.setBackgroundColor(getResources().getColor(R.color.back347));
        tvTitle.setText("消息");
        requstMessage();
        int[] colors = new int[1];
        colors[0] = getResources().getColor(R.color.back347);
        refresh.setProgressColors(colors);
        refresh.setLoadMore(true);
        refresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                /**
                 * 刷新
                 */
                page = 1;
                isRequst = true;
                requstMessage();
                LogUtils.e("刷新");
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                // TODO Auto-generated method stub
                super.onRefreshLoadMore(materialRefreshLayout);

                if (isRequst) {
                    page++;
                    LogUtils.e("加载");
                    requstMessage();
                }

            }
        });


    }

    private int page = 1;

    private void requstMessage() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("userId", userid);//个人id
        map.put("page", page + "");//page第几页
        map.put("rows", "10");//每页显示多少条
        //进行消息界面的数据请求
        httpDatas.getData("消息界面", Request.Method.POST, UrlBuilder.MESSAGE_URL, map, mHandler, RequestCode.MESSAGE_CODE);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back://返回
                finish();
                break;
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

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Message Page") // TODO: Define a title for the content shown.
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
}
