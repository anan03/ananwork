package com.lvshandian.partylive.moudles.nearby;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.lvshandian.partylive.R;
import com.lvshandian.partylive.UrlBuilder;
import com.lvshandian.partylive.base.BaseFragment;
import com.lvshandian.partylive.httprequest.HttpDatas;
import com.lvshandian.partylive.httprequest.RequestCode;
import com.lvshandian.partylive.moudles.index.live.WatchLiveActivity;
import com.lvshandian.partylive.moudles.mine.my.OtherPersonHomePageActivity;
import com.lvshandian.partylive.moudles.nearby.adapter.UserNearbyAdapter;
import com.lvshandian.partylive.moudles.nearby.bean.ContentBean;
import com.lvshandian.partylive.moudles.nearby.bean.UserNearbyBean;
import com.lvshandian.partylive.utils.BaseLocationListener;
import com.lvshandian.partylive.utils.LocationUtils;
import com.lvshandian.partylive.widget.RefreshLayout;
import com.lvshandian.partylive.widget.myrecycler.RefreshRecyclerView;
import com.lvshandian.partylive.widget.myrecycler.adapter.RefreshRecyclerViewAdapter;
import com.lvshandian.partylive.widget.myrecycler.listener.OnBothRefreshListener;
import com.lvshandian.partylive.widget.myrecycler.listener.OnLoadMoreListener;
import com.lvshandian.partylive.widget.myrecycler.listener.OnPullDownListener;
import com.lvshandian.partylive.widget.myrecycler.manager.RecyclerMode;
import com.lvshandian.partylive.widget.myrecycler.manager.RecyclerViewManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 附近的人
 *
 * @author sll
 * @time 2016/12/19 11:49
 */
public class NearbyFragment extends BaseFragment implements View.OnClickListener {
    @Bind(R.id.nearby_recycler)
    RefreshRecyclerView nearbyRecycler;

    private List<ContentBean> mUserList = new ArrayList<>();
    private int page = 0;
    /**
     * 维度、经度
     */
    private double latitude, longitude;
    private UserNearbyAdapter mAdapter;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);
            switch (msg.what) {
                //关注请求接收数据
                case RequestCode.REQUEST_USER_SERACH:
                    UserNearbyBean userBean = JSON.parseObject(json, UserNearbyBean.class);
                    if (userBean == null) {
                        return;
                    } else {
                        if (userBean.getContent().size() > 0) {
                            page++;
                            mUserList.addAll(userBean.getContent());
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                    if (nearbyRecycler != null) {
                        nearbyRecycler.onRefreshCompleted();

                    }
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nearby;
    }

    @Override
    protected void initListener() {

    }

    GridLayoutManager mLayoutManager;

    @Override
    protected void initialized() {
        initDate();
        mAdapter = new UserNearbyAdapter(getActivity(), mUserList);
        mLayoutManager = new GridLayoutManager(getActivity(), 3);
        nearbyRecycler.setLayoutManager(mLayoutManager);
        nearbyRecycler.setAdapter(mAdapter);

        View header2 = View.inflate(getActivity(), R.layout.recycler_header2, null);
        View footer = View.inflate(getActivity(), R.layout.recycler_footer, null);
//        RecyclerViewManager.setLayoutManager(mLayoutManager);
        RecyclerViewManager.with(mAdapter, mLayoutManager)
                .setMode(RecyclerMode.BOTH)
                .setOnBothRefreshListener(new OnBothRefreshListener() {
                    @Override
                    public void onPullDown() {
                        mUserList.clear();
                        page = 0;
                        getUserList();
                    }

                    @Override
                    public void onLoadMore() {
                        getUserList();
                    }
                })
                .setOnItemClickListener(new RefreshRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerView.ViewHolder holder, int position) {
//                        Toast.makeText(MainActivity.this, "item" + position, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), OtherPersonHomePageActivity.class).putExtra(getString(R.string.visiti_person), mUserList.get(position).getId()));
                    }
                })
                .into(nearbyRecycler, getActivity());
    }

    /**
     * 先获取经纬度，在进行网络请求获取数据
     *
     * @author sll
     * @time 2016/12/21 10:26
     */
    private void initDate() {
        LocationUtils.newInstance().getLocation(getActivity(), (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE), locationListener);
    }

    BaseLocationListener locationListener = new BaseLocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            super.onLocationChanged(location);
            latitude = location.getLatitude();// 纬度
            longitude = location.getLongitude();// 经度
            getUserList();
        }
    };

    /**
     * 获取附近的人列表
     * {"key":"直播","longitude":"123","latitude":"123","online":"0","page":"1"}
     *
     * @author sll
     * @time 2016/12/21 10:32
     */
    public void getUserList() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("longitude", longitude + "");
        map.put("latitude", latitude + "");
        map.put("page", page + "");
        httpDatas.getDataForJsoNoloading("查找（附近）用户列表", Request.Method.POST, UrlBuilder.userSearch, map, mHandler, RequestCode.REQUEST_USER_SERACH);
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
