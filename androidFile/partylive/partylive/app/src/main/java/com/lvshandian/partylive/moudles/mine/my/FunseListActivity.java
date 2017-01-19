package com.lvshandian.partylive.moudles.mine.my;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.UrlBuilder;
import com.lvshandian.partylive.base.BaseActivity;
import com.lvshandian.partylive.base.CustomStringCallBack;
import com.lvshandian.partylive.httprequest.HttpDatas;
import com.lvshandian.partylive.lib.cjj.MaterialRefreshLayout;
import com.lvshandian.partylive.lib.cjj.MaterialRefreshListener;
import com.lvshandian.partylive.moudles.mine.bean.Funse;
import com.lvshandian.partylive.moudles.mine.bean.FunseBean;
import com.lvshandian.partylive.moudles.mine.my.adapter.FunseListAdapter;
import com.lvshandian.partylive.utils.JsonUtil;
import com.lvshandian.partylive.utils.LogUtils;
import com.squareup.okhttp.MediaType;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * Created by gjj on 2016/12/6.
 */

public class FunseListActivity extends BaseActivity {
    @Bind(R.id.lv_list)
    ListView lvList;
    @Bind(R.id.mrl_layout)
    MaterialRefreshLayout mrlLayout;
    private String mUserId;

    private int page = 1;
    private List<FunseBean> mDatas = new ArrayList<>();
    private FunseListAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_funse_list;
    }

    @Override
    protected void initListener() {
        mrlLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                isRefresh = true;
                requestFunse();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                isRefresh = false;
                requestFunse();
            }
        });

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FunseBean funseBean = mDatas.get(position);
                String userId = funseBean.getUserId();
                Intent intent = new Intent(mContext, OtherPersonHomePageActivity.class);
                intent.putExtra(getString(R.string.visiti_person),userId);
                startActivity(intent);
            }
        });

        mAdapter.setOnItemFollowsClick(new FunseListAdapter.OnItemFollowsClickListener() {
            @Override
            public void onItemFollowClick(int position, ImageView view) {
                FunseBean bean = mDatas.get(position);
                changeFollow(bean, view);
            }
        });
    }

    /**
     * 改变是否关注
     *
     * @param bean
     * @param view
     */
    /*1.用户关注(取消关注)
    http://miulive.cc:8080/api/v1/user/follow post提交
    发送：{ "userId":"1","followUserId":"2"}*/
    private void changeFollow(final FunseBean bean, final ImageView view) {
        final String follow = bean.getFollow();
        Map<String, String> params = new HashMap<>();
        params.put("userId", appUser.getId());
        params.put("followUserId", bean.getUserId());
        JSONObject jsonObject = new JSONObject(params);
        String json = jsonObject.toString();
        LogUtils.e("json: " + json);
        String url = UrlBuilder.serverUrl + UrlBuilder.ATTENTION_USER;
        LogUtils.e("ulr: " + url);
        OkHttpUtils.postString().url(url)
                .content(json)
                .mediaType(MediaType.parse("application/json"))
                .build().execute(new CustomStringCallBack(mContext, HttpDatas.KEY_CODE) {
            @Override
            public void onFaild() {
                String toast;
                if (TextUtils.equals(follow, "1")) {
                    toast = "取消关注失败";
                } else {
                    toast = "关注失败";
                }
                showToast(toast);
            }

            @Override
            public void onSucess(String data) {
                if (TextUtils.equals(follow, "1")) {
                    bean.setFollow("0");
                    view.setImageResource(R.mipmap.me_follow);
                } else {
                    bean.setFollow("1");
                    view.setImageResource(R.mipmap.me_following);
                }
                LogUtils.e("关注取消关注: " + data);
            }
        });
    }


    @Override
    protected void initialized() {
        initTitle("", "粉丝", null);
        Intent intent = getIntent();
        mUserId = intent.getStringExtra(getString(R.string.visiti_person));

        mAdapter = new FunseListAdapter(mContext, mDatas, R.layout.item_attention_fans,true);
        lvList.setAdapter(mAdapter);
        mrlLayout.setLoadMore(true);
        mrlLayout.autoRefresh();
    }

    /**
     * 请求粉丝列表
     */
   /* http://miulive.cc:8080/api/v1/user/{id}/fans?pageNum=1 获取列表 get请求 id为用户id*/
    private void requestFunse() {
        page = isRefresh ? 1 : ++page;
        String url = UrlBuilder.serverUrl + UrlBuilder.yqRen;
        url += mUserId;
        url += "/fans?pageNum=" + page;
        OkHttpUtils.get().url(url).build().execute(new CustomStringCallBack(this, HttpDatas.KEY_CODE) {
            @Override
            public void onFaild() {
                showToast(isRefresh ? "刷新失败" : "加载失败");
            }

            @Override
            public void onSucess(String data) {
                LogUtils.e("粉丝列表: " + data);
                handlerJson(data);
            }
        });
    }

    private void handlerJson(String data) {
        Funse funse = JsonUtil.json2Bean(data, Funse.class);
        if (funse != null) {
            List<FunseBean> result = funse.getResult();
            if (result != null && result.size() > 0) {
                if (isRefresh) {
                    mDatas.clear();
                }
                mDatas.addAll(result);
                mAdapter.notifyDataSetChanged();
            } else if (!isRefresh) {
                //如果是上拉加载更多切没有加载出更多，则加载页数应该-1
                page--;
            }
        }
        showToast(isRefresh ? "刷新成" : "加载成功");
        finshRefresh();
    }

    private void finshRefresh() {
        mrlLayout.finishRefresh();
        mrlLayout.finishRefreshLoadMore();
    }

    private boolean isRefresh = true;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_titlebar_left:
                defaultFinish();
                break;
        }
    }
}
