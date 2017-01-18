package com.lvshandian.asktoask.module.message;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.widget.ListView;
import com.android.volley.Request;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lvshandian.asktoask.BaseFragment;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.http.HttpDatas;
import com.lvshandian.asktoask.common.http.RequestCode;
import com.lvshandian.asktoask.entry.DataMessageLeave;
import com.lvshandian.asktoask.module.message.adapter.LeaveMessagAdapter;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.JsonUtil;
import com.lvshandian.asktoask.utils.UrlBuilder;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import butterknife.Bind;

/**
 *
 * 消息模块中
 * 留言fragment on 2016/9/22.
 * <p/>
 * 创建留言界面
 */
public class LeaveMessageFragment extends BaseFragment {
    @Bind(R.id.pull_lv_leave)
    PullToRefreshListView pullLvCollect;
    private ConcurrentHashMap<String, String> map;
    private String realpageNum="1";
    private boolean ismore=false;
    List<DataMessageLeave.DataBean2.DataBean> list;
    List<DataMessageLeave.DataBean2.DataBean> reallist;//这里是真实的集合
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);
            switch (msg.what) {
                //消息模块留言列表
                case RequestCode.MESSAGE_LEAVE_CODE:
                    DataMessageLeave.DataBean2 datebean = JsonUtil.json2Bean(json, DataMessageLeave.DataBean2.class);
                    list= datebean.getData();
                    if(ismore){
                        reallist.addAll(list);
                    }else{
                        reallist=list;
                    }
                    LeaveMessagAdapter userFansAdapter = new LeaveMessagAdapter(getContext(), reallist, R.layout.item_frament_leavemessage);
                    pullLvCollect.setAdapter(userFansAdapter);


                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.leavemessage_fragment_layout;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initialized() {

//        pullLvCollect.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
//        pullLvCollect.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
//            @Override
//            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//                String label = DateUtils.formatDateTime(
//                        getContext(),
//                        System.currentTimeMillis(),
//                        DateUtils.FORMAT_SHOW_TIME
//                                | DateUtils.FORMAT_SHOW_DATE
//                                | DateUtils.FORMAT_ABBREV_ALL);
//                // 显示最后更新的时间
//                refreshView.getLoadingLayoutProxy()
//                        .setLastUpdatedLabel(label);
//                requesHttp();
//            }
//        });

        pullLvCollect.setMode(PullToRefreshBase.Mode.BOTH);//支持加载更多和刷新
        pullLvCollect.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {//下拉刷新
                String label = DateUtils.formatDateTime(
                        getContext(),
                        System.currentTimeMillis(),
                        DateUtils.FORMAT_SHOW_TIME
                                | DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_ABBREV_ALL);
                // 显示最后更新的时间
                refreshView.getLoadingLayoutProxy()
                        .setLastUpdatedLabel(label);
                ismore = false;
                reallist.clear();
                realpageNum = 1 + "";
                requesHttp();


            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {//上拉加载更多
                ILoadingLayout endLabels = pullLvCollect.getLoadingLayoutProxy(false, true);
                endLabels.setPullLabel("加载更多");// 刚开始上拉时显示的提示
                ismore = true;
                int p = Integer.parseInt(realpageNum) + 1;
                realpageNum = "" + p;
                requesHttp();

            }
        });




    }

    /**
     * 我的模块回答请求
     */
    private void requesHttp() {
        if (Global.isLogin(getContext())) {
            map = new ConcurrentHashMap<>();
            map.clear();
            map.put("pageNum", realpageNum);
            map.put("leavedId", Global.getUserId(getContext()));
            httpDatas.getData("消息留言", Request.Method.POST, UrlBuilder.MESSAGER_LEAVE, map, mHandler, RequestCode.MESSAGE_LEAVE_CODE);
            pullLvCollect.post(new Runnable() {
                @Override
                public void run() {
                    pullLvCollect.onRefreshComplete();
                }
            });
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        realpageNum="1";
        requesHttp();
    }
}
