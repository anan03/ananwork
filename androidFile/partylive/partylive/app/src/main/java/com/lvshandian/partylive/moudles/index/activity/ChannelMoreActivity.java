package com.lvshandian.partylive.moudles.index.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.UrlBuilder;
import com.lvshandian.partylive.base.BaseActivity;
import com.lvshandian.partylive.bean.ChannelBean;
import com.lvshandian.partylive.bean.ChannelMoreUrlBean;
import com.lvshandian.partylive.bean.ChannelUrlBean;
import com.lvshandian.partylive.bean.LiveBean;
import com.lvshandian.partylive.moudles.index.adapter.ChannelMoreAdapter;
import com.lvshandian.partylive.moudles.mine.my.OtherPersonHomePageActivity;
import com.lvshandian.partylive.moudles.nearby.adapter.UserNearbyAdapter;
import com.lvshandian.partylive.moudles.nearby.bean.ContentBean;
import com.lvshandian.partylive.utils.ChannelToLiveBean;
import com.lvshandian.partylive.utils.JsonUtil;
import com.lvshandian.partylive.utils.LogUtils;
import com.lvshandian.partylive.view.MyTitleBar;
import com.lvshandian.partylive.widget.myrecycler.RefreshRecyclerView;
import com.lvshandian.partylive.widget.myrecycler.adapter.RefreshRecyclerViewAdapter;
import com.lvshandian.partylive.widget.myrecycler.listener.OnBothRefreshListener;
import com.lvshandian.partylive.widget.myrecycler.manager.RecyclerMode;
import com.lvshandian.partylive.widget.myrecycler.manager.RecyclerViewManager;
import com.tandong.sa.json.Gson;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 直播——频道——更多
 *
 * @author sll
 * @time 2016/12/22 15:58
 */
public class ChannelMoreActivity extends BaseActivity {

    @Bind(R.id.title_bar)
    MyTitleBar titleBar;
    @Bind(R.id.channel_more_recycler)
    RefreshRecyclerView channelMoreRecycler;
    @Bind(R.id.activity_channel_more)
    AutoLinearLayout activityChannelMore;

    private String channelId, channelName;
    private List<LiveBean> beanArrayList = new ArrayList<>();
    private int page = 1;
    private ChannelMoreAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_channel_more;
    }

    @Override
    protected void initListener() {
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaultFinish();
            }
        });
    }

    @Override
    protected void initialized() {
        channelId = getIntent().getStringExtra("channelId");
        channelName = getIntent().getStringExtra("channelName");
        titleBar.setTitle(channelName);

        initDate();
        mAdapter = new ChannelMoreAdapter(this, beanArrayList);
        mLayoutManager = new LinearLayoutManager(this);
        channelMoreRecycler.setLayoutManager(mLayoutManager);
        channelMoreRecycler.setAdapter(mAdapter);

        RecyclerViewManager.with(mAdapter, mLayoutManager)
                .setMode(RecyclerMode.BOTH)
                .setOnBothRefreshListener(new OnBothRefreshListener() {
                    @Override
                    public void onPullDown() {
                        beanArrayList.clear();
                        page = 1;
                        initDate();
                    }

                    @Override
                    public void onLoadMore() {
                        initDate();
                    }
                })
                .setOnItemClickListener(new RefreshRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerView.ViewHolder holder, int position) {
//                        Toast.makeText(MainActivity.this, "item" + position, Toast.LENGTH_SHORT).show();
                        ifEnter(beanArrayList.get(position));
                    }
                })
                .into(channelMoreRecycler, this);
    }

    /**
     * 查询此频道下正在直播的用户列表
     * channelId:频道ID
     * page:当前页
     * rows:每页条数
     *
     * @author sll
     * @time 2016/12/22 16:11
     */
    private void initDate() {
        String url = UrlBuilder.chargeServerUrl + UrlBuilder.selectListByChannelId;
        LogUtils.e("查询此频道下正在直播的用户列表url: " + url);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("channelId", channelId);
        hashMap.put("page", page + "");
        hashMap.put("rows", "10");
        String json = new org.json.JSONObject(hashMap).toString();
        LogUtils.e("Json:　" + json);
        OkHttpUtils.get().url(url)
                .params(hashMap)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(com.squareup.okhttp.Request request, Exception e) {
                        LogUtils.i("查询此频道下正在直播的用户列表: " + e.toString());
                        channelMoreRecycler.onRefreshCompleted();
                    }

                    @Override
                    public void onResponse(String response) {
                        LogUtils.i("查询此频道下正在直播的用户列表: " + response);
                        ChannelMoreUrlBean channelMoreUrlBean = JsonUtil.json2Bean(response, ChannelMoreUrlBean.class);
                        if (channelMoreUrlBean.getCode() == 0 && channelMoreUrlBean.isSuccess()) {
                            page++;
                            for (int i = 0; i < channelMoreUrlBean.getObj().getRows().size(); i++) {
                                LiveBean user = ChannelToLiveBean.getLiveBeanFromRowsBean(channelMoreUrlBean.getObj().getRows().get(i));
                                beanArrayList.add(user);
                            }
                            mAdapter.notifyDataSetChanged();
                            channelMoreRecycler.onRefreshCompleted();
                        }
                        channelMoreRecycler.onRefreshCompleted();
                    }
                });
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
