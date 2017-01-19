package com.lvshandian.partylive.moudles.mine.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.Request;
import com.lvshandian.partylive.R;
import com.lvshandian.partylive.UrlBuilder;
import com.lvshandian.partylive.base.BaseFragment;
import com.lvshandian.partylive.httprequest.HttpDatas;
import com.lvshandian.partylive.httprequest.RequestCode;
import com.lvshandian.partylive.moudles.mine.bean.OtherPersonBean;
import com.lvshandian.partylive.moudles.mine.bean.RecentVisitorBean;
import com.lvshandian.partylive.moudles.mine.bean.VideoBean;
import com.lvshandian.partylive.moudles.mine.fragment.otherpserons.OtherPersonVideoAdapter;
import com.lvshandian.partylive.moudles.mine.my.OtherPersonHomePageActivity;
import com.lvshandian.partylive.moudles.mine.my.Videotails;
import com.lvshandian.partylive.utils.JsonUtil;
import com.lvshandian.partylive.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;

/**
 * 首页左边关注
 */
public class OrtherVideoMyFragment extends BaseFragment {

    GridView mygrid;
    protected static final int CHOOSE_PICTURE = 120;

    private static final int CROP_SMALL_PICTURE = 123;
    private List<VideoBean> list = new ArrayList<VideoBean>();
    private int tagReqest = 1;
    private OtherPersonVideoAdapter adapter;
    private static final int REQ_CODE = 10001;

    private Handler mHandler = new Handler() {


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);
            switch (msg.what) {

                case RequestCode.MY_VIDEO_LOAD://图片请求列表
                    LogUtils.e("json--" + json);
                    List<VideoBean> listAdd = JsonUtil.json2BeanList(json.toString(), VideoBean.class);
                    if (tagReqest == 1) {
                        list = listAdd;
                        adapter = new OtherPersonVideoAdapter(mContext, list, R.layout.frament_mine_photo);
                        mygrid.setAdapter(adapter);
                    } else if (tagReqest == 2) {
                        list.clear();
                        list.addAll(listAdd);
                        adapter.notifyDataSetChanged();
                    }

                    mygrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            Intent intent = new Intent(getContext(), Videotails.class);
                            intent.putExtra("video", list.get(position));
                            getActivity().startActivity(intent);
                        }
                    });
                    break;

            }

        }
    };
    private OtherPersonBean mBean;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_photo;
    }

    @Override
    protected void initListener() {
        mygrid = (GridView) view.findViewById(R.id.mygrid);


    }

    //请求相册
    private void requestPhoto(String userId) {

        ConcurrentHashMap map = new ConcurrentHashMap<>();
        httpDatas.getDataForJson("视频图片请求列表", Request.Method.GET, UrlBuilder.myVideo(userId), map, mHandler, RequestCode.MY_VIDEO_LOAD);

    }

    @Override
    protected void initialized() {
        EventBus.getDefault().register(this);
        tagReqest = 1;
        OtherPersonHomePageActivity activity = (OtherPersonHomePageActivity) getActivity();
        mBean = activity.getBean();
        requestVedios();
    }

    private void requestVedios() {
        if (mBean != null) {
            requestPhoto(String.valueOf(mBean.getId()));
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    @Subscribe
    public void onEventMainThread(VideoBean videoBean) {
        //接收自Videotails，用来删除小视频后重新请求小视频
        requestVedios();
    }
}
