package com.lvshandian.partylive.moudles.group.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.UrlBuilder;
import com.lvshandian.partylive.base.BaseActivity;
import com.lvshandian.partylive.base.CustomStringCallBack;
import com.lvshandian.partylive.httprequest.HttpDatas;
import com.lvshandian.partylive.moudles.group.adapter.FunseListForGroupAdapter;
import com.lvshandian.partylive.moudles.mine.bean.Funse;
import com.lvshandian.partylive.moudles.mine.bean.FunseBean;
import com.lvshandian.partylive.utils.JsonUtil;
import com.lvshandian.partylive.utils.LogUtils;
import com.lvshandian.partylive.view.ToastUtils;
import com.netease.nim.uikit.contact_selector.activity.ContactSelectActivity;
import com.netease.nim.uikit.team.cjj.MaterialRefreshLayout;
import com.netease.nim.uikit.team.cjj.MaterialRefreshListener;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 新建高级群，邀请成员的列表，即我关注的列表
 *
 * @author sll
 * @time 2016/12/20 13:41
 */
public class FollowListForGroupActivity extends BaseActivity {
    @Bind(R.id.lv_list)
    ListView lvList;
    @Bind(R.id.uikit_layout)
    MaterialRefreshLayout uikitLayout;
    public static final String RESULT_DATA = "RESULT_DATA"; // 返回结果
    private String mUserId;
    private int page = 1;
    private List<FunseBean> mDatas = new ArrayList<>();
    private FunseListForGroupAdapter mAdapter;
    private boolean isRefresh = true;
    private ArrayList<String> selectedAccounts;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_funse_list_uikit;
    }

    @Override
    protected void initListener() {
        uikitLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
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


    @Override
    protected void initialized() {
        selectedAccounts = new ArrayList<>();
        initTitle("", "选择关注的人", "确定");
//        Intent intent = getIntent();
//        mUserId = intent.getStringExtra(getString(R.string.visiti_person));
        mUserId = appUser.getId();

        mAdapter = new FunseListForGroupAdapter(mContext, mDatas, R.layout.item_follow_group_uikit, false);
        lvList.setAdapter(mAdapter);
        uikitLayout.setLoadMore(true);
        uikitLayout.autoRefresh();
    }

    /**
     * 请求关注列表
     */
   /* http://miulive.cc:8080/api/v1/user/{id}/fans?pageNum=1 获取列表 get请求 id为用户id*/
    private void requestFunse() {
        page = isRefresh ? 1 : ++page;
        String url = UrlBuilder.serverUrl + UrlBuilder.yqRen;
        url += mUserId;
        url += "/follows?pageNum=" + page;
        LogUtils.e("关注列表(url): " + url);
        OkHttpUtils.get().url(url).build().execute(new CustomStringCallBack(this, HttpDatas.KEY_CODE) {
            @Override
            public void onFaild() {
                showToast(isRefresh ? "刷新失败" : "加载失败");
            }

            @Override
            public void onSucess(String data) {
                LogUtils.e("关注列表: " + data);
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
        uikitLayout.finishRefresh();
        uikitLayout.finishRefreshLoadMore();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_titlebar_left:
                //返回
                defaultFinish();
                break;
            case R.id.tv_titlebar_right:
                //确定,弹输入名字的框
                joinForPw();
                break;
        }
    }

    /**
     * 支付密码进入私密直播
     *
     * @author sll
     * @time 2016/12/16 16:34
     */
    private void joinForPw() {
        final Dialog dialog = new Dialog(this, R.style.homedialog);
        final View view = View.inflate(this, R.layout.dialog_join_secret_pwd, null);
        final EditText pwdEdit = (EditText) view.findViewById(R.id.join_secret_pwd_edit);
        TextView prompt = (TextView) view.findViewById(R.id.dialog_prompt_text);
        prompt.setText("请输入群名称");
        pwdEdit.setHint("请输入群名称");
        //取消
        view.findViewById(R.id.join_secret_pwd_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //确定
        view.findViewById(R.id.join_secret_pwd_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(pwdEdit.getText().toString())) {
                    //名字不空
                    ToastUtils.showSnackBar(pwdEdit, "请输入群名称");
                } else {
                    if (pwdEdit.getText().toString().length() > 12) {
                        ToastUtils.showSnackBar(pwdEdit, "群名称不能超过12个字符");
                    } else {
                        onSelected(pwdEdit.getText().toString());
                        dialog.dismiss();
                    }

                }
            }
        });
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        dialog.show();
    }

    /**
     * 获取选中的发送
     *
     * @author sll
     * @time 2016/12/20 17:10
     */
    public void onSelected(String teamName) {
        for (int i = 0; i < mDatas.size(); i++) {
//            if (FunseListForGroupAdapter.getIsSelected().get(i)) {
            if (mDatas.get(i).isChecked()) {
                selectedAccounts.add("miu_" + mDatas.get(i).getUserId());
            }
        }
        LogUtils.i("获取选中的发送(size):" + selectedAccounts.size());
        Intent intent = new Intent();
        intent.putStringArrayListExtra(RESULT_DATA, selectedAccounts);
        intent.putExtra(ContactSelectActivity.TEAM_NAME, teamName);
        setResult(Activity.RESULT_OK, intent);
        defaultFinish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
