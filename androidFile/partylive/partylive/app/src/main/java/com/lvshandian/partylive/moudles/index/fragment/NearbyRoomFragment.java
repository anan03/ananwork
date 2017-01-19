package com.lvshandian.partylive.moudles.index.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.lvshandian.partylive.R;
import com.lvshandian.partylive.UrlBuilder;
import com.lvshandian.partylive.base.BaseFragment;
import com.lvshandian.partylive.bean.JoinRoomBean;
import com.lvshandian.partylive.bean.LiveBean;
import com.lvshandian.partylive.httprequest.HttpDatas;
import com.lvshandian.partylive.httprequest.RequestCode;
import com.lvshandian.partylive.interf.ResultListener;
import com.lvshandian.partylive.moudles.index.adapter.RoomNearbyAdapter;
import com.lvshandian.partylive.moudles.index.live.WatchLiveActivity;
import com.lvshandian.partylive.moudles.mine.my.OtherPersonHomePageActivity;
import com.lvshandian.partylive.moudles.nearby.adapter.UserNearbyAdapter;
import com.lvshandian.partylive.moudles.nearby.bean.ContentBean;
import com.lvshandian.partylive.moudles.nearby.bean.UserNearbyBean;
import com.lvshandian.partylive.utils.BaseLocationListener;
import com.lvshandian.partylive.utils.JsonUtil;
import com.lvshandian.partylive.utils.LocationUtils;
import com.lvshandian.partylive.utils.LogUtils;
import com.lvshandian.partylive.utils.PermisionUtils;
import com.lvshandian.partylive.view.ToastUtils;
import com.lvshandian.partylive.widget.myrecycler.RefreshRecyclerView;
import com.lvshandian.partylive.widget.myrecycler.adapter.RefreshRecyclerViewAdapter;
import com.lvshandian.partylive.widget.myrecycler.listener.OnBothRefreshListener;
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
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 附近的直播
 */
public class NearbyRoomFragment extends BaseFragment {
    @Bind(R.id.nearby_room_recycler)
    RefreshRecyclerView nearbyRoomRecycler;

    private List<LiveBean> mUserList = new ArrayList<>();
    private int page = 0;
    /**
     * 维度、经度
     */
    private double latitude, longitude;
    private RoomNearbyAdapter mAdapter;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);
            switch (msg.what) {
                //关注请求接收数据
                case RequestCode.REQUEST_ROOM_SERACH:
                    LogUtils.i("获取附近的播列表(json):" + json);
                    try {
                        JSONObject resJson = new JSONObject(json);
                        String res2 = resJson.getString("content");
                        LogUtils.i(res2);
                        if (res2 == null) {
                            return;
                        } else {
                            JSONArray resJa = new JSONArray(res2);
                            LogUtils.i("集合长度" + resJa.length() + "");
                            if (resJa.length() > 0) {
                                page++;
                                for (int i = 0; i < resJa.length(); i++) {
                                    LiveBean user = new Gson().fromJson(resJa.getJSONObject(i).toString(), LiveBean.class);
                                    mUserList.add(user);
                                }
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    nearbyRoomRecycler.onRefreshCompleted();
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_nearby_room;
    }

    @Override
    protected void initListener() {

    }

    GridLayoutManager mLayoutManager;

    @Override
    protected void initialized() {
        initDate();
        mAdapter = new RoomNearbyAdapter(getActivity(), mUserList);
        mLayoutManager = new GridLayoutManager(getActivity(), 3);
        nearbyRoomRecycler.setLayoutManager(mLayoutManager);
        nearbyRoomRecycler.setAdapter(mAdapter);
        RecyclerViewManager.with(mAdapter, mLayoutManager)
                .setMode(RecyclerMode.BOTH)
                .setOnBothRefreshListener(new OnBothRefreshListener() {
                    @Override
                    public void onPullDown() {
                        mUserList.clear();
                        page = 0;
                        initDate();
                    }

                    @Override
                    public void onLoadMore() {
                        getUserList();
                    }
                })
                .setOnItemClickListener(new RefreshRecyclerViewAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(RecyclerView.ViewHolder holder, int position) {
                        ifEnter(mUserList.get(position));
                    }
                })
                .into(nearbyRoomRecycler, getActivity());
    }

    /**
     * 先获取经纬度，在进行网络请求获取数据
     *
     * @author sll
     * @time 2016/12/21 10:26
     */
//    private void initDate() {
//        LocationUtils.newInstance().getLocation(getActivity(), (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE), locationListener);
//    }
//
//    BaseLocationListener locationListener = new BaseLocationListener() {
//        @Override
//        public void onLocationChanged(Location location) {
//            super.onLocationChanged(location);
//            latitude = location.getLatitude();// 纬度
//            longitude = location.getLongitude();// 经度
//            getUserList();
//        }
//    };
    private void initDate() {
        PermisionUtils.newInstance().checkLocationPermission(getActivity(), new PermisionUtils.OnPermissionGrantedLintener() {
            @Override
            public void permissionGranted() {
                openGPSSettings();
                getLocation();
            }
        });

    }

    private void openGPSSettings() {
        LocationManager alm = (LocationManager) getActivity()
                .getSystemService(Context.LOCATION_SERVICE);
        if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            LogUtils.e("GPS模块正常");
            return;
        }
        Toast.makeText(getActivity(), "请开启GPS！", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);
        startActivityForResult(intent, 0); // 此为设置完成后返回到获取界面
    }

    private void getLocation() {
        // 获取位置管理服务
        LocationManager locationManager;
        String serviceName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) getActivity().getSystemService(serviceName);
        // 查找到服务信息
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗
        String provider = locationManager.getBestProvider(criteria, true); // 获取GPS信息
        Location location = locationManager.getLastKnownLocation(provider); // 通过GPS获取位置
        updateToNewLocation(location);
        // 设置监听*器，自动更新的最小时间为间隔N秒(1秒为1*1000，这样写主要为了方便)或最小位移变化超过N米
        locationManager.requestLocationUpdates(provider, 100 * 1000, 500,
                new MyLocationListener());
    }

    class MyLocationListener implements LocationListener { // 位置监听器，作为方法参数
        @Override
        public void onLocationChanged(Location loc) {
            // // TODO Auto-generated method stub
        }

        @Override
        public void onProviderDisabled(String provider) {
            // 当provider被用户关闭时调用
        }

        @Override
        public void onProviderEnabled(String provider) {
            // 当provider被用户开启后调用
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // 当provider的状态在OUT_OF_SERVICE、TEMPORARILY_UNAVAILABLE和AVAILABLE之间发生变化时调用
        }
    }

    private void updateToNewLocation(Location location) {
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            getUserList();
        } else {
            Toast.makeText(getActivity(), "无法定位您的位置", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取附近的播列表
     *
     * @author sll
     * @time 2016/12/21 10:32
     */
    public void getUserList() {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("longitude", longitude + "");
        map.put("latitude", latitude + "");
        map.put("page", page + "");
        httpDatas.getDataForJsoNoloading("查找（附近）直播列表", Request.Method.POST, UrlBuilder.roomSearch, map, mHandler, RequestCode.REQUEST_ROOM_SERACH);
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
