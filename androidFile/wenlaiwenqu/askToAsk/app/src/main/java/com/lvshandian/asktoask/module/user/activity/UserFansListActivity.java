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
import com.lvshandian.asktoask.entry.DataUserFans;
import com.lvshandian.asktoask.module.user.adapter.UserFansAdapter;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.JsonUtil;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import butterknife.Bind;

/**
 * 我的模块中的粉丝列表
 * 创建我的界面粉丝  无分页
 */
public class UserFansListActivity extends BaseActivity {
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
                //粉丝请求接收数据
                case RequestCode.USERFANSES_CODE:
                    List<DataUserFans.DataBean> list = JsonUtil.json2BeanList(json, DataUserFans.DataBean.class);
                    UserFansAdapter userFansAdapter = new UserFansAdapter(getContext(), httpDatas, snackView, list, R.layout.item_activity_fanses);//粉丝适配器
                    pullLvCollect.setAdapter(userFansAdapter);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_userfans;
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

        requesHttp();//请求数据粉丝数据

    }

    /**
     * 我的模块粉丝请求
     */
    private void requesHttp() {
        map = new ConcurrentHashMap<>();
        map.clear();
        map.put("userId", Global.getUserId(getContext()));
        httpDatas.getData("我的模块粉丝", Request.Method.POST, UrlBuilder.USER_FANAES, map, mHandler, RequestCode.USERFANSES_CODE);
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
