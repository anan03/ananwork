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
import com.lvshandian.partylive.moudles.mine.bean.PhotoBean;
import com.lvshandian.partylive.moudles.mine.fragment.adapter.PhotoAdapter;
import com.lvshandian.partylive.moudles.mine.my.PhotoDetails;
import com.lvshandian.partylive.utils.Constant;
import com.lvshandian.partylive.utils.JsonUtil;
import com.lvshandian.partylive.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 首页左边关注
 */
public class PhotoFragment extends BaseFragment {
    GridView mygrid;
    protected static final int CHOOSE_PICTURE = 120;

    private static final int CROP_SMALL_PICTURE = 123;
    private List<PhotoBean> list = new ArrayList<PhotoBean>();
    private int tagReqest = 1;
    private PhotoAdapter adapter;
    private Handler mHandler = new Handler() {


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);
            switch (msg.what) {

                case RequestCode.MY_PHOTO_LOAD://图片请求列表
                    List<PhotoBean> listAdd = JsonUtil.json2BeanList(json.toString(), PhotoBean.class);
                    LogUtils.e("listAdd--" + listAdd.toString());

                    if (tagReqest == 1) {
                        list = listAdd;
                        list.add(new PhotoBean());
                        adapter = new PhotoAdapter(mContext, list);
                        mygrid.setAdapter(adapter);
                    } else if (tagReqest == 2) {
                        list.clear();
                        list.addAll(listAdd);
                        list.add(new PhotoBean());
                        adapter.notifyDataSetChanged();
                    }


                    mygrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                            if (position == list.size() - 1) {
                                //进行头像的上传
                                //调用相册权限
                                Intent openAlbumIntent = new Intent(
                                        Intent.ACTION_GET_CONTENT);
                                openAlbumIntent.setType("image/*");
                                getActivity().startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                                //不对图片进行裁剪
//                                getActivity().startActivityForResult(openAlbumIntent, CROP_SMALL_PICTURE);

                            } else {
                                Intent intent = new Intent(getContext(), PhotoDetails.class);
                                intent.putExtra("photo", list.get(position));
                                intent.putExtra("isShow", "Show");
                                startActivity(intent);
                            }
                        }
                    });

                    break;

            }

        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_photo;
    }

    @Override
    protected void initListener() {
        mygrid = (GridView) view.findViewById(R.id.mygrid);
        EventBus.getDefault().register(this); //第1步: 注册


    }

    //请求相册
    private void requestPhoto() {

        ConcurrentHashMap map = new ConcurrentHashMap<>();
        httpDatas.getDataForJson("图片请求列表", Request.Method.GET, UrlBuilder.myPhoto(appUser.getId()), map, mHandler, RequestCode.MY_PHOTO_LOAD);

    }


    @Override
    protected void initialized() {
        tagReqest = 1;

        requestPhoto();
    }

    //接受方：
    @Subscribe //第2步:注册一个在后台线程执行的方法,用于接收事件
    public void onEventMainThread(PhotoBean photoBean) {

        if ("yes".equals(photoBean.getId())) {
            LogUtils.e("进行我的相册图库的更新");
            tagReqest = 2;
            if (list != null) {
                list.remove(list.size() - 1);
            }
            requestPhoto();
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册
    }
}
