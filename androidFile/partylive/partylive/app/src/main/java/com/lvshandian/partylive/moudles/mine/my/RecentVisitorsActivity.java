package com.lvshandian.partylive.moudles.mine.my;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.UrlBuilder;
import com.lvshandian.partylive.base.BaseActivity;
import com.lvshandian.partylive.base.CustomStringCallBack;
import com.lvshandian.partylive.bean.AppUser;
import com.lvshandian.partylive.httprequest.HttpDatas;
import com.lvshandian.partylive.httprequest.RequestCode;
import com.lvshandian.partylive.lib.cjj.MaterialRefreshLayout;
import com.lvshandian.partylive.lib.cjj.MaterialRefreshListener;
import com.lvshandian.partylive.moudles.mine.bean.RecentVisitorBean;
import com.lvshandian.partylive.moudles.mine.bean.Visitor;
import com.lvshandian.partylive.moudles.mine.my.adapter.OnItemClickListener;
import com.lvshandian.partylive.moudles.mine.my.adapter.RecentVisitorAdapter;
import com.lvshandian.partylive.moudles.mine.my.adapter.RecycleViewDivider;
import com.lvshandian.partylive.utils.CacheUtils;
import com.lvshandian.partylive.utils.JsonUtil;
import com.lvshandian.partylive.utils.LogUtils;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;


/**
 * Created by gjj on 2016/11/23.
 */

public class RecentVisitorsActivity extends BaseActivity {
    @Bind(R.id.rlv_list)
    RecyclerView rlvList;
    @Bind(R.id.mrl_layout)
    MaterialRefreshLayout mrlLayout;

    private List<RecentVisitorBean> mDatas = new ArrayList<>();
    private RecentVisitorAdapter mAdapter;
    private GridLayoutManager mLayoutManager;

    /**
     * 最近访客处理
     *
     * @param visitor
     */
    private void hanlderVisitors(Visitor visitor) {
        if (visitor != null) {
            List<RecentVisitorBean> result = visitor.getResult();
            if (result != null) {
                if (isRefresh) {
                    //下拉刷新需要清除数据
                    mDatas.clear();
                }
                mDatas.addAll(result);
                mAdapter.notifyDataSetChanged();
            }
        }

        finshRefresh();
    }

    /**
     * 取消刷新和加载
     */
    private void finshRefresh() {
        mrlLayout.finishRefreshLoadMore();
        mrlLayout.finishRefresh();
    }

    private int page = 1;
    private boolean isRefresh = true;//是否是刷新的标记

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recent_visitors;
    }

    @Override
    protected void initListener() {
        mrlLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                isRefresh = true;
                requestNet();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                isRefresh = false;
                requestNet();
            }
        });

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                RecentVisitorBean bean = mDatas.get(position);
                Intent intent = new Intent(mContext, OtherPersonHomePageActivity.class);
                intent.putExtra(getString(R.string.visiti_person), bean.getUserId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initialized() {
        initTitle("", "最近访客", null);
        initRecycle();
    }

    /**
     * RecycleView初始
     */
    private void initRecycle() {
        mLayoutManager = new GridLayoutManager(mContext, 1, LinearLayoutManager.VERTICAL, false);
        rlvList.setLayoutManager(mLayoutManager);
        mAdapter = new RecentVisitorAdapter(mContext, mDatas);
        rlvList.setAdapter(mAdapter);
        mrlLayout.setLoadMore(true);
        RecycleViewDivider myDecoration = new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL, R.drawable.divider);
        rlvList.addItemDecoration(myDecoration);


        //进入页面自动刷新
        mrlLayout.autoRefresh();
    }

    /**
     * 最近访客请求
     * /api/v1/user/%@/history?pageNum=%ld", userId, (long)iPageNum
     */
    private void requestNet() {
        String url = UrlBuilder.serverUrl + UrlBuilder.recent_visitors;
        AppUser userInfo = (AppUser) CacheUtils.readObject(mContext, CacheUtils.USERINFO);
        if (userInfo != null) {
            url += userInfo.getId();
            if (isRefresh) {
                //下拉刷新,从第一页开始，重新请求
                page = 1;
            } else {
                //上拉加载，从下一页开始加载
                page++;
            }
            url += "/history?pageNum=" + page;
            OkHttpUtils.get().url(url).build().execute(new CustomStringCallBack(mContext, HttpDatas.KEY_CODE) {
                @Override
                public void onFaild() {
                    showToast(isRefresh ? "刷新失败" : "加载失败");
                    finshRefresh();
                }

                @Override
                public void onSucess(String data) {
                    Visitor visitor = JsonUtil.json2Bean(data, Visitor.class);
                    hanlderVisitors(visitor);
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_titlebar_left:
                defaultFinish();
                break;
        }
    }
}
