package com.lvshandian.asktoask.module.user.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
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
import com.lvshandian.asktoask.entry.DataUseQuiz;
import com.lvshandian.asktoask.module.user.adapter.UserQuzaAdapter;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.JsonUtil;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import butterknife.Bind;

/**
 * 创建我的界面提问   没有分页加载
 */
public class UserQuizListActivity extends BaseActivity {
    @Bind(R.id.iv_back_approve_address)
    ImageView ivBackApproveAddress;
    @Bind(R.id.pull_lv_collect)
    PullToRefreshListView pullLvCollect;
    private ConcurrentHashMap<String, String> map;
    private UserQuzaAdapter userFansAdapter;//提问适配器
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);
            switch (msg.what) {
                //我的模块提问请求到的数据
                case RequestCode.USERQUIZ_CODE:
                    List<DataUseQuiz.DataBean> list = JsonUtil.json2BeanList(json, DataUseQuiz.DataBean.class);
                    userFansAdapter = new UserQuzaAdapter(getContext(), list, R.layout.item_activity_quiz);
                    pullLvCollect.setAdapter(userFansAdapter);
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.activity_userquiz;
    }

    @Override
    protected void initListener() {

        ivBackApproveAddress.setOnClickListener(this);
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


    }

    @Override
    protected void initialized() {
        pullLvCollect.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        requesHttp();

    }

    /**
     * 我的模块提问请求
     */
    private void requesHttp() {
        map = new ConcurrentHashMap<>();
        map.clear();
        map.put("userId", Global.getUserId(getContext()));
        httpDatas.getData("我的模块提问", Request.Method.POST, UrlBuilder.USER_QUIZ, map, mHandler, RequestCode.USERQUIZ_CODE);
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
