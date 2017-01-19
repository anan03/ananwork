package com.lvshandian.partylive.moudles.index.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.UrlBuilder;
import com.lvshandian.partylive.base.BaseFragment;
import com.lvshandian.partylive.bean.ChannelBean;
import com.lvshandian.partylive.bean.ChannelUrlBean;
import com.lvshandian.partylive.bean.JoinRoomBean;
import com.lvshandian.partylive.bean.LiveBean;
import com.lvshandian.partylive.httprequest.HttpDatas;
import com.lvshandian.partylive.httprequest.RequestCode;
import com.lvshandian.partylive.moudles.index.adapter.ChannelAdapter;
import com.lvshandian.partylive.moudles.index.adapter.RoomNearbyAdapter;
import com.lvshandian.partylive.moudles.index.live.WatchLiveActivity;
import com.lvshandian.partylive.utils.JsonUtil;
import com.lvshandian.partylive.utils.LogUtils;
import com.lvshandian.partylive.view.ToastUtils;
import com.lvshandian.partylive.widget.myrecycler.RefreshRecyclerView;
import com.lvshandian.partylive.widget.myrecycler.adapter.RefreshRecyclerViewAdapter;
import com.lvshandian.partylive.widget.myrecycler.listener.OnBothRefreshListener;
import com.lvshandian.partylive.widget.myrecycler.listener.OnPullDownListener;
import com.lvshandian.partylive.widget.myrecycler.manager.RecyclerMode;
import com.lvshandian.partylive.widget.myrecycler.manager.RecyclerViewManager;
import com.tandong.sa.json.Gson;
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

import static com.lvshandian.partylive.UrlBuilder.joinRoom;

/**
 * 频道
 */
public class ChannelFragment extends BaseFragment {

    @Bind(R.id.nearby_room_recycler)
    RefreshRecyclerView nearbyRoomRecycler;

    private List<ChannelBean> beanArrayList = new ArrayList<>();
    private ChannelAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nearby_room;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initialized() {
        getUserList();
        mAdapter = new ChannelAdapter(getActivity(), beanArrayList);
        mLayoutManager = new LinearLayoutManager(getActivity());
        nearbyRoomRecycler.setLayoutManager(mLayoutManager);
        nearbyRoomRecycler.setAdapter(mAdapter);

        RecyclerViewManager.with(mAdapter, mLayoutManager)
                .setMode(RecyclerMode.TOP)
                .setOnPullDownListener(new OnPullDownListener() {
                    @Override
                    public void onPullDown() {
                        beanArrayList.clear();
                        getUserList();
                    }

                })
                .into(nearbyRoomRecycler, getActivity());
    }

    /**
     * 查询正在直播的用户按照频道进行分组(粉丝数最多的前两条记录)展示
     *
     * @author sll
     * @time 2016/12/21 10:32
     */
    public void getUserList() {
        String url = UrlBuilder.chargeServerUrl + UrlBuilder.selectListByChannel;
        LogUtils.e("直播的用户按照频道进行分组url: " + url);
        OkHttpUtils.get().url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(com.squareup.okhttp.Request request, Exception e) {
                        LogUtils.i("直播的用户按照频道进行分组: " + e.toString());
                        nearbyRoomRecycler.onRefreshCompleted();
                    }

                    @Override
                    public void onResponse(String response) {
                        LogUtils.i("直播的用户按照频道进行分组: " + response);
                        ChannelUrlBean channelUrlBean = JsonUtil.json2Bean(response, ChannelUrlBean.class);
                        if (channelUrlBean != null && channelUrlBean.getObj() != null)
                            beanArrayList.addAll(channelUrlBean.getObj());
                        LogUtils.i("直播的用户按照频道进行分组: " + beanArrayList.size());
                        mAdapter.notifyDataSetChanged();
                        nearbyRoomRecycler.onRefreshCompleted();
                    }
                });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
