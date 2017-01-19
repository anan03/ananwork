package com.lvshandian.partylive.moudles.index.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.lvshandian.partylive.R;
import com.lvshandian.partylive.UrlBuilder;
import com.lvshandian.partylive.base.BaseFragment;
import com.lvshandian.partylive.bean.JoinRoomBean;
import com.lvshandian.partylive.bean.LiveBean;
import com.lvshandian.partylive.bean.WXPayBean;
import com.lvshandian.partylive.httprequest.HttpDatas;
import com.lvshandian.partylive.httprequest.RequestCode;
import com.lvshandian.partylive.moudles.index.live.WatchLiveActivity;
import com.lvshandian.partylive.utils.JsonUtil;
import com.lvshandian.partylive.utils.LogUtils;
import com.lvshandian.partylive.view.ToastUtils;
import com.lvshandian.partylive.widget.AvatarView;
import com.lvshandian.partylive.widget.LoadUrlImageView;
import com.lvshandian.partylive.widget.RefreshLayout;
import com.tandong.sa.json.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 首页左边关注
 */
public class AttentionFragment extends BaseFragment {
    @Bind(R.id.lv_live_room)
    ListView lvLiveRoom;
    @Bind(R.id.refreshLayout)
    RefreshLayout refreshLayout;

    private boolean isla = true;

    private List<LiveBean> mUserList = new ArrayList<>();
    private LayoutInflater inflater;
    private HallLiveAdapter hallLiveAdapter;
    private int page = 1;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);


            switch (msg.what) {
                //关注请求接收数据
                case RequestCode.ATTENTION_LIVE:
                    LogUtils.i(json.toString());

                    String res = json.toString();
                    try {
                        JSONObject resJson = new JSONObject(res);

                        String res2 = resJson.getString("result");
                        LogUtils.i(res2);
                        if (res2 == null) {
                            return;
                        } else {
                            JSONArray resJa = new JSONArray(res2);
                            LogUtils.i("集合长度" + resJa.length() + "");
                            if (resJa.length() > 0) {
                                page++;

                                if (isla) {
                                    mUserList.clear();
                                }
                                {

                                }

                                for (int i = 0; i < resJa.length(); i++) {
                                    LiveBean user = new Gson().fromJson(resJa.getJSONObject(i).toString(), LiveBean.class);
                                    mUserList.add(user);
                                }

                                if (isla) {
                                    fillUI();
                                } else {
                                    hallLiveAdapter.notifyDataSetChanged();

                                }
                            } else {
                                if (isla) {
                                    mUserList.clear();
                                    fillUI();
                                }

                            }

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    break;

            }
        }
    };


    //刷新页面
    private void fillUI() {

        refreshLayout.setRefreshing(false);
        if (refreshLayout.isRefreshing()) {
            hallLiveAdapter.notifyDataSetChanged();
        } else {
            lvLiveRoom.setAdapter(hallLiveAdapter);
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hall;
    }

    @Override
    protected void initListener() {
        lvLiveRoom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ifEnter(mUserList.get(position));
            }
        });
    }

    @Override
    protected void initialized() {
        getLiveList(page);
        hallLiveAdapter = new HallLiveAdapter();
        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.main));
        refreshLayout.setOnRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
                isla = true;
                page = 1;
                getLiveList(page);

            }
        });

        // 加载监听器
        refreshLayout.setOnLoadListener(new RefreshLayout.OnLoadListener() {

            @Override
            public void onLoad() {

                refreshLayout.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        isla = false;

                        getLiveList(page);
                        refreshLayout.setLoading(false);
                    }
                }, 2000);

            }
        });


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        ButterKnife.bind(this, rootView);
        this.inflater = inflater;
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void getLiveList(int page) {
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("pageNum", page + "");
        httpDatas.getData("获取关注人直播的接口", Request.Method.GET, UrlBuilder.myattention(appUser.getId()), map, mHandler, RequestCode.ATTENTION_LIVE);


    }


    private class HallLiveAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mUserList.size();
        }

        @Override
        public Object getItem(int position) {
            return mUserList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_hall_live, null);
                viewHolder = new ViewHolder();
                viewHolder.mUserNick = (TextView) convertView.findViewById(R.id.tv_live_nick);
                viewHolder.mUserLocal = (TextView) convertView.findViewById(R.id.tv_live_local);
                viewHolder.mUserNums = (TextView) convertView.findViewById(R.id.tv_live_usernum);
                viewHolder.mUserHead = (AvatarView) convertView.findViewById(R.id.iv_live_user_head);
                viewHolder.mUserPic = (LoadUrlImageView) convertView.findViewById(R.id.iv_live_user_pic);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            LiveBean user = mUserList.get(position);

            viewHolder.mUserNick.setText(user.getCreator().getNickName());
            viewHolder.mUserLocal.setText(user.getCity());
            viewHolder.mUserPic.setImageLoadUrl(user.getLivePicUrl());
            viewHolder.mUserHead.setAvatarUrl(user.getCreator().getPicUrl());
            viewHolder.mUserNums.setText(String.valueOf(user.getOnlineUserNum()));

            return convertView;
        }
    }

    private class ViewHolder {
        public TextView mUserNick, mUserLocal, mUserNums, mRoomTitle;
        public LoadUrlImageView mUserPic;
        public AvatarView mUserHead;
    }

}
