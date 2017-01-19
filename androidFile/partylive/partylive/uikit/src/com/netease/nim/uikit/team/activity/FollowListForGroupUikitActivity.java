package com.netease.nim.uikit.team.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.netease.nim.uikit.R;
import com.netease.nim.uikit.team.cjj.MaterialRefreshLayout;
import com.netease.nim.uikit.team.cjj.MaterialRefreshListener;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 新建高级群，邀请成员的列表，即我关注的列表
 *
 * @author sll
 * @time 2016/12/20 13:41
 */
public class FollowListForGroupUikitActivity extends Activity implements View.OnClickListener {
    ListView lvList;
    MaterialRefreshLayout mrlLayout;

    public static final String RESULT_DATA = "RESULT_DATA"; // 返回结果
    private String mUserId;
    private int page = 1;
    private List<FunseBean> mDatas = new ArrayList<>();
    private FunseListForGroupAdapter mAdapter;
    private boolean isRefresh = true;
    private ArrayList<String> selectedAccounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funse_list_uikit);

        lvList = (ListView) findViewById(R.id.lv_list);
        mrlLayout = (MaterialRefreshLayout) findViewById(R.id.uikit_layout);

        initialized();
        initListener();
    }

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

//        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                // 取得ViewHolder对象，这样就省去了通过层层的findViewById去实例化我们需要的cb实例的步骤
//                CheckBox tvCheck = (CheckBox) view.findViewById(R.id.iv_item_check);
//                // 改变CheckBox的状态
//                tvCheck.toggle();
//                // 将CheckBox的选中状况记录下来
//                FunseListForGroupAdapter.getIsSelected().put(position, tvCheck.isChecked());
//                LogUtils.i("选择关注的人:" + position + "/" + tvCheck.isChecked());
//            }
//        });
        mAdapter.setOnItemFollowsClick(new FunseListForGroupAdapter.OnItemFollowsClickListener() {
            @Override
            public void onItemFollowClick(int position, CheckBox tvCheck) {
                FunseBean bean = mDatas.get(position);
                bean.setChecked(!bean.isChecked());
            }
        });
    }


    protected void initialized() {
        selectedAccounts = new ArrayList<>();
        initTitle("", "选择关注的人", "确定");
//        Intent intent = getIntent();
//        mUserId = intent.getStringExtra(getString(R.string.visiti_person));
        mUserId = getIntent().getStringExtra("userId");

        mAdapter = new FunseListForGroupAdapter(this, mDatas, R.layout.item_follow_group_uikit, false);
        lvList.setAdapter(mAdapter);
        mrlLayout.setLoadMore(true);
        mrlLayout.autoRefresh();
        requestFunse();
    }

    private void initTitle(String left, String title, String right) {
        TextView titlebar_left = (TextView) findViewById(R.id.tv_titlebar_left);
        TextView titlebar_title = (TextView) findViewById(R.id.tv_titlebar_title);
        TextView titlebar_right = (TextView) findViewById(R.id.tv_titlebar_right);

        if (left == null) {
            titlebar_left.setVisibility(View.INVISIBLE);
        } else {
            titlebar_left.setText(left);
            titlebar_left.setVisibility(View.VISIBLE);
        }
        if (right == null) {
            titlebar_right.setVisibility(View.INVISIBLE);
        } else {
            titlebar_right.setText(right);
            titlebar_right.setVisibility(View.VISIBLE);
        }
        titlebar_title.setText(title);
        titlebar_left.setOnClickListener(this);
        titlebar_right.setOnClickListener(this);
        titlebar_title.setOnClickListener(this);
    }

    /**
     * 请求关注列表
     */
   /* http://miulive.cc:8080/api/v1/user/{id}/fans?pageNum=1 获取列表 get请求 id为用户id*/
    private void requestFunse() {
        page = isRefresh ? 1 : ++page;
        String url = "http://60.205.171.6:8800/api/v1/user/";
        url += mUserId;
        url += "/follows?pageNum=" + page;
        Log.i("lsd", "requestFunse: url" + url);
        OkHttpUtils.get().url(url).build().execute(new CustomStringCallBack(this, 0) {
            @Override
            public void onFaild() {
//                showToast(isRefresh ? "刷新失败" : "加载失败");
            }

            @Override
            public void onSucess(String data) {
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
//        showToast(isRefresh ? "刷新成" : "加载成功");
        finshRefresh();
    }

    private void finshRefresh() {
        mrlLayout.finishRefresh();
        mrlLayout.finishRefreshLoadMore();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_titlebar_left) {//返回
            finish();

        } else if (i == R.id.tv_titlebar_right) {//确定,弹输入名字的框
            onSelected();
        }
    }

    /**
     * 获取选中的发送
     *
     * @author sll
     * @time 2016/12/20 17:10
     */
    public void onSelected() {
        for (int i = 0; i < mDatas.size(); i++) {
//            if (FunseListForGroupAdapter.getIsSelected().get(i)) {
            if (mDatas.get(i).isChecked()) {
                selectedAccounts.add("miu_" + mDatas.get(i).getUserId());
            }
        }
        Intent intent = new Intent();
        intent.putStringArrayListExtra(RESULT_DATA, selectedAccounts);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

}
