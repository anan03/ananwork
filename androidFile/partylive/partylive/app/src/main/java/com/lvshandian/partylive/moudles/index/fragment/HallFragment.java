package com.lvshandian.partylive.moudles.index.fragment;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.lvshandian.partylive.R;
import com.lvshandian.partylive.UrlBuilder;
import com.lvshandian.partylive.base.BaseFragment;
import com.lvshandian.partylive.bean.ChannelUrlBean;
import com.lvshandian.partylive.bean.ImagBean;
import com.lvshandian.partylive.bean.JoinRoomBean;
import com.lvshandian.partylive.bean.LiveBean;
import com.lvshandian.partylive.httprequest.HttpDatas;
import com.lvshandian.partylive.httprequest.RequestCode;
import com.lvshandian.partylive.httprequest.SdkHttpResultSuccess;
import com.lvshandian.partylive.moudles.index.live.WatchLiveActivity;
import com.lvshandian.partylive.utils.JsonUtil;
import com.lvshandian.partylive.utils.LogUtils;
import com.lvshandian.partylive.utils.ToastUtil;
import com.lvshandian.partylive.utils.UiUtils;
import com.lvshandian.partylive.utils.XUtils;
import com.lvshandian.partylive.view.ToastUtils;
import com.lvshandian.partylive.widget.AvatarView;
import com.lvshandian.partylive.widget.LoadUrlImageView;
import com.lvshandian.partylive.widget.RefreshLayout;
import com.nostra13.universalimageloader.core.ImageLoader;
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
 * 首页左边关注
 */
public class HallFragment extends BaseFragment {

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
                case RequestCode.GETHALLLIST:
                    LogUtils.e("主播: " + json.toString());
                    String res = json.toString();
                    try {
                        JSONObject resJson = new JSONObject(res);
                        String res2 = resJson.getString("result");
                        LogUtils.i(res2);
                        if (res2 == null) {
                            mUserList.clear();
                            fillUI();
                            return;
                        } else {
                            JSONArray resJa = new JSONArray(res2);
                            LogUtils.i("集合长度" + resJa.length() + "");
                            if (resJa.length() > 0) {
                                page++;

                                if (isla) {
                                    mUserList.clear();
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
                case RequestCode.IMAGER_BANNER:
                    String obj = data.getString(HttpDatas.obj);
                    List<ImagBean> listImag = JsonUtil.json2BeanList(obj, ImagBean.class);
                    LogUtils.e("图片轮播数据: " + listImag.toString());
                    imgesUrl = new ArrayList<>();
                    for (int i = 0; i < listImag.size(); i++) {
                        imgesUrl.add(listImag.get(i).getPicUrl());
                    }
                    bannerHead = View.inflate(mContext, R.layout.fragment_hall_head, null);
                    viewPager = (ViewPager) bannerHead.findViewById(R.id.viewPager);
                    llPoints = (LinearLayout) bannerHead.findViewById(R.id.ll_points);
//                    imgesUrl.add("http://img3.fengniao.com/forum/attachpics/913/114/36502745.jpg");
//                    imgesUrl.add("http://imageprocess.yitos.net/images/public/20160910/99381473502384338.jpg");
//                    imgesUrl.add("http://imageprocess.yitos.net/images/public/20160910/77991473496077677.jpg");
//                    imgesUrl.add("http://imageprocess.yitos.net/images/public/20160906/1291473163104906.jpg");
                    dealLunBo();
                    lvLiveRoom.addHeaderView(bannerHead);
                    refreshLayout.setColorSchemeColors(getResources().getColor(R.color.main));
                    hallLiveAdapter = new HallLiveAdapter();
                    lvLiveRoom.setAdapter(hallLiveAdapter);
                    getLiveList(page);
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
                ifEnter(mUserList.get(position - 1));
            }
        });


    }

    View bannerHead;
    private LinearLayout llPoints; //图片里面的小圆点
    private ViewPager viewPager;  //图片轮播
    List<String> imgesUrl;

    @Override
    protected void initialized() {

        String url = UrlBuilder.chargeServerUrl + UrlBuilder.IMAGER_BANNER;
        LogUtils.e("图片轮播: " + url);
        OkHttpUtils.get().url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(com.squareup.okhttp.Request request, Exception e) {
                        LogUtils.i("图片轮播失败: " + e.toString());
                    }

                    @Override
                    public void onResponse(String response) {
                        LogUtils.i("图片轮播成功: " + response);
//                        ChannelUrlBean channelUrlBean = JsonUtil.json2Bean(response, ChannelUrlBean.class);

                        SdkHttpResultSuccess channelUrlBean = JsonUtil.json2Bean(response, SdkHttpResultSuccess.class);

                        List<ImagBean> listImag = JsonUtil.json2BeanList(channelUrlBean.getObj().toString(), ImagBean.class);
                        LogUtils.e("图片轮播数据: " + listImag.toString());
                        imgesUrl = new ArrayList<>();
                        for (int i = 0; i < listImag.size(); i++) {
                            imgesUrl.add(listImag.get(i).getPicUrl());
                        }
                        if (null != imgesUrl && imgesUrl.size() != 0) {
                            bannerHead = View.inflate(mContext, R.layout.fragment_hall_head, null);
                            viewPager = (ViewPager) bannerHead.findViewById(R.id.viewPager);
                            llPoints = (LinearLayout) bannerHead.findViewById(R.id.ll_points);
                            dealLunBo();
                            lvLiveRoom.addHeaderView(bannerHead);
                        }
                        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.main));
                        hallLiveAdapter = new HallLiveAdapter();
                        lvLiveRoom.setAdapter(hallLiveAdapter);
                        getLiveList(page);
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
                });


    }

    /**
     * 处理轮播图
     */

    private void dealLunBo() {
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter();
        viewPager.setAdapter(myPagerAdapter);
        viewPager.setCurrentItem(imgesUrl.size() * 10000);
        startRool();
        //初始化ViewPager轮播小圆圈
        llPoints.removeAllViews();
        for (int i = 0; i < imgesUrl.size(); i++) {
            ImageView point = new ImageView(getActivity());
            if (i == 0) {
                point.setImageResource(R.drawable.shape_xiao_yuan_quan_selected);
            } else {
                point.setImageResource(R.drawable.shape_xiao_yuan_quan_nomal);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            if (i > 0) {
                params.leftMargin = UiUtils.dp2px(getContext(), 8);
            }
            llPoints.addView(point, params);
        }
        //ViewPager的轮播效果(小圆圈改变)
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                position = position % imgesUrl.size();
                for (int i = 0; i < llPoints.getChildCount(); i++) {
                    ImageView image = (ImageView) llPoints.getChildAt(i);
                    if (i == position) {
                        image.setImageResource(R.drawable.shape_xiao_yuan_quan_selected);
                    } else {
                        image.setImageResource(R.drawable.shape_xiao_yuan_quan_nomal);
                    }
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     * 轮播图的adapter
     */

    class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            ImageLoader.getInstance().displayImage(imgesUrl.get(position % imgesUrl.size()), imageView);
            imageView.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            handler.removeCallbacksAndMessages(null);
                            break;
                        case MotionEvent.ACTION_UP:
                            handler.sendEmptyMessageDelayed(1, 2000);
                            break;
                        /**
                         * 当保持按下操作，并从此控件移到外层控件时，就会触发ACTION_CANCEL事件时，
                         * 当前的手势被中断，不会在接收它的记录
                         * 将它当做ACTION_UP事件进行处理比较好
                         */
                        case MotionEvent.ACTION_CANCEL:
                            handler.sendEmptyMessageDelayed(1, 2000);
                            break;

                        default:
                            break;
                    }
                    // true处理这个事件
                    return true;
                }
            });

            container.addView(imageView);
            return imageView;
        }
    }

    private void startRool() {
        handler.sendEmptyMessageDelayed(1, 3000);
    }

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (viewPager != null) {
                    int currentItem = viewPager.getCurrentItem();
                    currentItem++;
                    viewPager.setCurrentItem(currentItem);
                    startRool();
                }
            }
        }

    };

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
        map.put("timeStamp", System.currentTimeMillis() + "");
        map.put("pageNum", page + "");
        httpDatas.getData("获取大厅直播数据", Request.Method.GET, UrlBuilder.HAllLIST, map, mHandler, RequestCode.GETHALLLIST);


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
