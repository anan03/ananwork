
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
import com.lvshandian.partylive.bean.CustomdateBean;
import com.lvshandian.partylive.httprequest.HttpDatas;
import com.lvshandian.partylive.httprequest.RequestCode;
import com.lvshandian.partylive.moudles.mine.bean.OtherPersonBean;
import com.lvshandian.partylive.moudles.mine.bean.PhotoBean;
import com.lvshandian.partylive.moudles.mine.fragment.otherpserons.OtherPersonImageAdapter;
import com.lvshandian.partylive.moudles.mine.my.OtherPersonHomePageActivity;
import com.lvshandian.partylive.moudles.mine.my.PhotoDetails;
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
public class OrtherPersonImagaFragment extends BaseFragment {
    @Bind(R.id.mygrid)
    GridView mygrid;
    private List<PhotoBean> mDatas = new ArrayList<>();
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);
            switch (msg.what) {
                case RequestCode.MY_PHOTO_LOAD://图片请求列表

                    LogUtils.e("图片列表: " + json);
                    final List<PhotoBean> list = JsonUtil.json2BeanList(json.toString(), PhotoBean.class);
                    mDatas.clear();
                    if (list != null) {
                        mDatas.addAll(list);
                    }
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    private OtherPersonImageAdapter mAdapter;
    private OtherPersonBean mBean;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_photo;
    }

    @Override
    protected void initListener() {
        mygrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), PhotoDetails.class);
                intent.putExtra("photo", mDatas.get(position));
                startActivity(intent);
            }
        });

    }

    //请求相册
    private void requestPhoto(String s) {
        ConcurrentHashMap map = new ConcurrentHashMap<>();
        httpDatas.getDataForJson("图片请求列表", Request.Method.GET, UrlBuilder.myPhoto(s), map, mHandler, RequestCode.MY_PHOTO_LOAD);
    }


    @Override
    protected void initialized() {
        EventBus.getDefault().register(this);
        mAdapter = new OtherPersonImageAdapter(mContext, mDatas, R.layout.frament_mine_photo);
        mygrid.setAdapter(mAdapter);
        OtherPersonHomePageActivity activity = (OtherPersonHomePageActivity) getActivity();
        mBean = activity.getBean();
        requestImages();
    }

    private void requestImages() {
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
    public void onEventMainThread(PhotoBean photoBean) {
        //接收自PhotoDetails,用来删除图片后重新请求图片
        requestImages();
    }
}
