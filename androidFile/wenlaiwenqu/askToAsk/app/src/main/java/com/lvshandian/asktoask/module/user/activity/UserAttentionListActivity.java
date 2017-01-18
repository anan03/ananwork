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
import com.lvshandian.asktoask.entry.DataUserAttention;
import com.lvshandian.asktoask.module.user.adapter.UserAttentionAdapter;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.JsonUtil;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 我的关注列表数据 我的模块中的
 */
public class UserAttentionListActivity extends BaseActivity {
    @Bind(R.id.iv_back_approve_address)
    ImageView ivBackApproveAddress;
    @Bind(R.id.pull_lv_collect)
    PullToRefreshListView pullLvCollect;
    private ConcurrentHashMap<String, String> map;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);
            switch (msg.what) {
                //关注请求接收数据
                case RequestCode.USERATTENTION_CODE:
                    List<DataUserAttention.DataBean> list = JsonUtil.json2BeanList(json, DataUserAttention.DataBean.class);
                    UserAttentionAdapter userAttentionAdapter = new UserAttentionAdapter(getContext(), httpDatas, snackView, list, R.layout.item_activity_attention);
                    pullLvCollect.setAdapter(userAttentionAdapter);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        //重新进行网络请求
        requesHttp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_userattention;
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
     * 我的模块关注请求
     */
    private void requesHttp() {
        map = new ConcurrentHashMap<>();
        map.clear();
        map.put("userId", Global.getUserId(getContext()));
        httpDatas.getData("我的模块关注", Request.Method.POST, UrlBuilder.USER_ATTENTION, map, mHandler, RequestCode.USERATTENTION_CODE);
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
            case R.id.iv_back_approve_address:
                finish();
                break;

        }
    }


}
