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
import com.lvshandian.asktoask.entry.DataUserAnswer;
import com.lvshandian.asktoask.module.user.adapter.UserAnswerAdapter;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.JsonUtil;
import java.util.concurrent.ConcurrentHashMap;
import butterknife.Bind;


/**
 *
 * 创建我的界面回答界面 回答列表 无分页加载
 */
public class UserAnswerListActivity extends BaseActivity {
    @Bind(R.id.iv_back_approve_address)
    ImageView ivBackApproveAddress;
    @Bind(R.id.pull_lv_collect)
    PullToRefreshListView pullLvCollect;
    private ConcurrentHashMap<String, String> map;
    UserAnswerAdapter userFansAdapter;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);
            switch (msg.what) {
                //我的模块回答首页请求结果
                case RequestCode.USERANSWER_CODE:
                    DataUserAnswer.DataBean bean = JsonUtil.json2Bean(json, DataUserAnswer.DataBean.class);
                    String cidArray = bean.getCId();
                    String pidArray = bean.getPId();
                    userFansAdapter= new UserAnswerAdapter(cidArray, pidArray, getContext(), httpDatas, snackView, bean.getUserAndQuestions(), R.layout.item_activity_answer);
                    pullLvCollect.setAdapter(userFansAdapter);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_useranswer;
    }

    @Override
    protected void initListener() {
        pullLvCollect.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        ivBackApproveAddress.setOnClickListener(this);
        pullLvCollect.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
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

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {


            }
        });

    }


    @Override
    protected void initialized() {
        requesHttp();
    }

    /**
     * 我的模块回答请求
     */
    private void requesHttp() {
        map = new ConcurrentHashMap<>();
        map.clear();
        map.put("userId", Global.getUserId(getContext()));
        httpDatas.getData("我的模块回答", Request.Method.POST, UrlBuilder.USER_ANSWERS, map, mHandler, RequestCode.USERANSWER_CODE);
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
