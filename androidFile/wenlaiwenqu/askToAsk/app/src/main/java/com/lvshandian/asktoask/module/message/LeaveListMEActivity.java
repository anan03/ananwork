package com.lvshandian.asktoask.module.message;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lvshandian.asktoask.BaseActivity;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.http.HttpDatas;
import com.lvshandian.asktoask.common.http.RequestCode;
import com.lvshandian.asktoask.entry.DataMessageLeave;
import com.lvshandian.asktoask.entry.LeaveMeBean;
import com.lvshandian.asktoask.entry.User;
import com.lvshandian.asktoask.module.message.adapter.LeaveMessagMeAdapter;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.JsonUtil;
import com.lvshandian.asktoask.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;

/**
 * 特定的给我留言的列表 on 2016/10/24.
 */
public class LeaveListMEActivity extends BaseActivity {
    public static final String TRANCE = "trance";
    @Bind(R.id.v_title)
    View vTitle;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.tv_leaveed_name_men)
    TextView tvLeaveedNameMen;
    @Bind(R.id.rl_title)
    RelativeLayout rlTitle;//沉浸式状态栏
    @Bind(R.id.pulv_leave_list)
    PullToRefreshListView pulvLeaveList;
    @Bind(R.id.btn_leave)
    Button btnLeave;
    private ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
    DataMessageLeave.DataBean2.DataBean item;//传过来的留言人的bean
    private boolean ismore = false;//是否加载更多
    private String page = "1";
    List<LeaveMeBean.Datebean> list=new ArrayList<>();
    List<LeaveMeBean.Datebean> listreal=new ArrayList<>();
    LeaveMessagMeAdapter userFansAdapter;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);
            switch (msg.what) {
                //消息模块留言
                case RequestCode.MESSAGE_LEAVE_CODE_MYLIST:
                    LeaveMeBean me=JsonUtil.json2Bean(json,LeaveMeBean.class);
                    list=me.data;
                    if (ismore) {
                        listreal.addAll(list);
                        userFansAdapter.setmDatas(listreal);
                        userFansAdapter.notifyDataSetChanged();

                    }else{
                        listreal.clear();
                        listreal=list;
                        userFansAdapter = new LeaveMessagMeAdapter(getContext(), item, listreal, R.layout.item_frament_leavemessage_me);
                        userFansAdapter.setmDatas(listreal);
                        pulvLeaveList.setAdapter(userFansAdapter);

                    }
                    break;
                case RequestCode.MESSAEG_NO_READ_READ:
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void initialized() {
        item = (DataMessageLeave.DataBean2.DataBean) getIntent().getSerializableExtra(TRANCE);//传过来的留言人的id
        tvLeaveedNameMen.setText(item.getUserRealName());
        requesmessage();
        requesHttp();//请求列表数据 给我留言的列表
    }

    @Override
    protected void initListener() {
        ivBack.setOnClickListener(this);
        btnLeave.setOnClickListener(this);
        pulvLeaveList.setMode(PullToRefreshBase.Mode.BOTH);
        pulvLeaveList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
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
                listreal.clear();
                page = "1";
                ismore = false;
                requesHttp();


            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {//上拉加载更多
                ILoadingLayout endLabels = pulvLeaveList.getLoadingLayoutProxy(false, true);
                endLabels.setPullLabel("加载更多");// 刚开始上拉时显示的提示
                ismore = true;
                page = (Integer.parseInt(page) + 1) + "";
                requesHttp();

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_leave://去留言
                    Intent intent=new Intent(LeaveListMEActivity.this,MessageDetailsActivity.class);
                    intent.putExtra("item",item);
                    startActivityForResult(intent,1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1){
            if(MessageDetailsActivity.isleaveSuccess){
                ToastUtils.showSnackBar(snackView,"留言成功");
                MessageDetailsActivity.isleaveSuccess=false;
            }

        }
    }

    /**
     * 给我留言人的列表
     */
    private void requesHttp() {
            map.clear();
            map.put("pageNum", page);
            map.put("leavedId", Global.getUserId(getContext()));
            map.put("leaverId", item.getUserId());//留言列表的id
            httpDatas.getData("给我留言的人给我留的所有言", Request.Method.POST, UrlBuilder.LEAVE_MESSAGE_ME, map, mHandler, RequestCode.MESSAGE_LEAVE_CODE_MYLIST);
            pulvLeaveList.post(new Runnable() {
                @Override
                public void run() {
                    pulvLeaveList.onRefreshComplete();
                }
            });


    }
    /**
     * 消息列表未读和读了
     */
    private void requesmessage() {
            map.clear();
            map.put("leavedId", Global.getUserId(getContext()));
            map.put("leaverId", item.getUserId());//留言列表的id
            httpDatas.getData("留言图标", Request.Method.POST, UrlBuilder.MESSAGE_READ_BOOK, map, mHandler, RequestCode.MESSAEG_NO_READ_READ);


    }





    @Override
    protected int getLayoutId() {
        return R.layout.activity_leave_me_layout;
    }


}
