package com.lvshandian.asktoask.module.user.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Request;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lvshandian.asktoask.BaseActivity;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.http.HttpDatas;
import com.lvshandian.asktoask.common.http.RequestCode;
import com.lvshandian.asktoask.entry.DataUserCollect;
import com.lvshandian.asktoask.module.user.adapter.UserCollectAdapter;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.JsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *
 * 创建我的界面收藏   无分页加载
 */
public class UserCollectActivity extends BaseActivity {
    ConcurrentHashMap<String, String> map;
    @Bind(R.id.iv_back_approve_address)
    ImageView ivBackApproveAddress;
    @Bind(R.id.pull_lv_collect)
    PullToRefreshListView pullLvCollect;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);
            switch (msg.what) {
                //收藏请求接收数据
                case RequestCode.USERCOLLECT_CODE:
                    DataUserCollect.DataBean list = JsonUtil.json2Bean(json, DataUserCollect.DataBean.class);
                    UserCollectAdapter userCollectAdapter = new UserCollectAdapter(list.getCId(), list.getPId(), getContext(), httpDatas, snackView, list.getQuestions(), R.layout.item_activity_collect,httpDatas);
                    pullLvCollect.setAdapter(userCollectAdapter);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collect;
    }

    @Override
    protected void initListener() {
        ivBackApproveAddress.setOnClickListener(this);
    }

    @Override
    protected void initialized() {

        pullLvCollect.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        pullLvCollect.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                String label = DateUtils.formatDateTime(
                        getContext(),
                        System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                // 显示最后更新的时间
                refreshView.getLoadingLayoutProxy()
                        .setLastUpdatedLabel(label);
                requesHttp();
            }
        });

        requesHttp();

    }

    /**
     * 请求收藏列表
     */
    private void requesHttp() {

        map = new ConcurrentHashMap<>();
        map.clear();
        map.put("userId", Global.getUserId(getContext()));
        httpDatas.getData("我的模块收藏", Request.Method.POST, UrlBuilder.USER_COLLECT, map, mHandler, RequestCode.USERCOLLECT_CODE);
        pullLvCollect.post(new Runnable() {
            @Override
            public void run() {
                pullLvCollect.onRefreshComplete();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /**
             * 点击返回
             */
            case R.id.iv_back_approve_address:
                finish();
                break;

        }
    }

}
