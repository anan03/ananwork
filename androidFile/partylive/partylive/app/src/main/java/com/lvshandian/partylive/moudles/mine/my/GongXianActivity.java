package com.lvshandian.partylive.moudles.mine.my;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.UrlBuilder;
import com.lvshandian.partylive.base.BaseActivity;
import com.lvshandian.partylive.base.CustomStringCallBack;
import com.lvshandian.partylive.httprequest.HttpDatas;
import com.lvshandian.partylive.lib.cjj.MaterialRefreshLayout;
import com.lvshandian.partylive.lib.cjj.MaterialRefreshListener;
import com.lvshandian.partylive.moudles.mine.bean.GongXianBean;
import com.lvshandian.partylive.moudles.mine.bean.GongXianData;
import com.lvshandian.partylive.moudles.mine.my.adapter.GongXianAdapter;
import com.lvshandian.partylive.moudles.mine.my.adapter.RecycleViewDivider;
import com.lvshandian.partylive.utils.JsonUtil;
import com.lvshandian.partylive.utils.LogUtils;
import com.lvshandian.partylive.utils.TextUtils;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;

/**
 * Created by gjj on 2016/11/29.
 * <p>
 * 贡献榜
 */

public class GongXianActivity extends BaseActivity {

    @Bind(R.id.tv_gong_xian)
    TextView tvGongXian;
    @Bind(R.id.rlv_list)
    RecyclerView rlvList;
    @Bind(R.id.mrl_layout)
    MaterialRefreshLayout mrlLayout;
    @Bind(R.id.all_layout)
    AutoLinearLayout allLayout;
    private String userID;
    private List<GongXianBean> mDatas = new ArrayList<>();
    private GongXianAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_gong_xian;
    }

    @Override
    protected void initListener() {
        mrlLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                isRefresh = true;
                requestGongXianByUserId();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                isRefresh = false;
                requestGongXianByUserId();
            }
        });
    }

    @Override
    protected void initialized() {
        initTitle("", "贡献榜", null);
        Intent intent = getIntent();
        userID = intent.getStringExtra(getString(R.string.user_id));
        initRecyclView();
    }

    private void initRecyclView() {
        GridLayoutManager mLayoutManager = new GridLayoutManager(mContext, 1, LinearLayoutManager.VERTICAL, false);
        rlvList.setLayoutManager(mLayoutManager);
        mAdapter = new GongXianAdapter(mContext, mDatas);
        rlvList.setAdapter(mAdapter);
        RecycleViewDivider divider = new RecycleViewDivider(mContext, LinearLayoutManager.HORIZONTAL, R.drawable.divider);
        rlvList.addItemDecoration(divider);
        mrlLayout.setLoadMore(true);

        //进入页面自动刷新
        mrlLayout.autoRefresh();
    }

    private int page = 1;
    private boolean isRefresh = true;

    /**
     * 根据用户ID查询贡献榜
     */
    /*
        /api/v1/user/%@/investors?pageNum=%ld
    */
    private void requestGongXianByUserId() {
        String url = UrlBuilder.serverUrl + UrlBuilder.yqRen;
        if (!TextUtils.isEmpty(userID)) {
            url += userID;
            if (isRefresh) {
                page = 1;
            } else {
                page++;
            }
            url += "/investors?pageNum=" + page;

            OkHttpUtils.get().url(url).build().execute(new CustomStringCallBack(mContext, HttpDatas.KEY_CODE) {
                @Override
                public void onFaild() {
                    showToast(isRefresh ? "刷新失败" : "加载失败");
                    finshRefresh();
                }

                @Override
                public void onSucess(String data) {
                    LogUtils.e("贡献榜: " + data);
                    GongXianData gongXian = JsonUtil.json2Bean(data, GongXianData.class);
                    if (gongXian != null) {
                        List<GongXianBean> result = gongXian.getResult();
                        handlerGongXian(result);
                    } else {
                        showToast("加载完毕");
                    }
                }
            });
        }
    }

    /**
     * 数据处理
     *
     * @param result
     */
    private void handlerGongXian(List<GongXianBean> result) {
        if (result != null && result.size() > 0) {
            if (isRefresh) {
                mDatas.clear();
            }
            mDatas.addAll(result);

            Collections.sort(mDatas, new Comparator<GongXianBean>() {
                @Override
                public int compare(GongXianBean o1, GongXianBean o2) {
                    String investment1 = o1.getInvestment();
                    String investment2 = o2.getInvestment();

                    return -Integer.valueOf(investment1) + Integer.valueOf(investment2);
                }
            });
            mAdapter.notifyDataSetChanged();
            int size = mDatas.size();
            int total = 0;
            for (int i = 0; i < size; i++) {
                GongXianBean gongXianBean = mDatas.get(i);
                String investment = gongXianBean.getInvestment();
                total += Integer.valueOf(investment);
            }

            tvGongXian.setText(total + "金票");

        } else {
            showToast("加载完毕");
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_titlebar_left:
                defaultFinish();
                break;
        }
    }
}
