package com.lvshandian.asktoask.module.interaction.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lvshandian.asktoask.BaseActivity;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.module.interaction.adapter.HuDongSearchAdapter;
import com.lvshandian.asktoask.common.http.HttpDatas;
import com.lvshandian.asktoask.common.http.RequestCode;
import com.lvshandian.asktoask.entry.HuDongItem;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.JsonUtil;
import com.lvshandian.asktoask.utils.TextUtils;
import com.lvshandian.asktoask.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;

/**
 * 互动搜索界面
 */
public class HuDongSearchActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.iv_search)
    ImageView ivSearch;
    @Bind(R.id.pull_lv_hudong_search)
    PullToRefreshListView pullLvHudongSearch;
    @Bind(R.id.et_search)
    EditText etSearch;
    private HuDongSearchAdapter searchAdapter;
    private List<HuDongItem.DataBean.PageBean.DataBean2> reallist;
    private List<HuDongItem.DataBean.PageBean.DataBean2> list = new ArrayList<>();
    ConcurrentHashMap<String, String> map;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hudong_search_layout;
    }

    /**
     * 添加监听
     */
    @Override
    protected void initListener() {
        addListener();
        ivBack.setOnClickListener(this);
        pullLvHudongSearch.setMode(PullToRefreshBase.Mode.BOTH);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    if(TextUtils.isEmpty(etSearch.getText().toString())){
                        ToastUtils.showSnackBar(snackView,"输入不能为空");
                    }else{
                        hotstrin = etSearch.getText().toString();
                        requesHttp();
                    }

                    return true;
                }
                return false;
            }
        });
    }

    private String hotstrin = "";
    private String pageNum = "1";
    private boolean ismore = false;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);
            switch (msg.what) {
                case RequestCode.HUDONGSEARCH_RECODE:  //互动搜索
                    HuDongItem.DataBean bean = JsonUtil.json2Bean(json, HuDongItem.DataBean.class);
                    if((TextUtils.isEmpty(json)||0==bean.pageBean.data.size())&& pageNum.equals("1")){
                        ToastUtils.showSnackBar(snackView,"没有搜索到结果");
                    }else{
                        pullLvHudongSearch.setVisibility(View.VISIBLE);
                        reallist = bean.pageBean.data;
                        Load(bean);}
                    break;
                default:
                    break;
            }



        }

    };

    /**
     * 加载 和刷新
     */

    private void Load(HuDongItem.DataBean pagebean){
        if (ismore) {
            list.addAll(reallist);
            searchAdapter.notifyDataSetChanged();
        } else {
            list.clear();
            list = reallist;
            searchAdapter = new HuDongSearchAdapter(getContext(), list, R.layout.hudong_search_item,pagebean);
            searchAdapter.setmDatas(list);
            pullLvHudongSearch.setAdapter(searchAdapter);
        }
    }

    private void addListener() {
        pullLvHudongSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                goToHUDongDetail(list.get(position - 1));

            }
        });
        pullLvHudongSearch.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {


            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                if(!TextUtils.isEmpty(etSearch.getText().toString())){
                    ismore = false;
                    pageNum = "1";
                    list.clear();
                    requesHttp();
                }

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if(!TextUtils.isEmpty(etSearch.getText().toString())){
                ismore = true;
                int page = Integer.parseInt(pageNum) + 1;
                pageNum = page + "";
                requesHttp();
            }
            }
        });

    }

    /**
     * 互动详情
     *
     * @param bean
     */

    private void goToHUDongDetail(HuDongItem.DataBean.PageBean.DataBean2 bean) {
        Intent intent = new Intent(this, HuDongDetailActivity.class);
        intent.putExtra(HuDongDetailActivity.TRANSFER, bean);
        intent.putExtra(HuDongDetailActivity.COLLECTNUM, bean.getQuestionCollection()+"");
        intent.putExtra(HuDongDetailActivity.PARSENUM, bean.getQuestionPraise()+"");
        startActivity(intent);

    }
    @Override
    protected void initialized() {
        map = new ConcurrentHashMap<>();

    }

    public void requesHttp() {
        map.clear();
        map.put("hotWord", hotstrin);
        map.put("pageNum", pageNum);
        map.put("userId", Global.getUserId(getContext()));
        httpDatas.getData("互动搜索界面", Request.Method.POST, UrlBuilder.HUDONG_SEARCH_URL, map, mHandler, RequestCode.HUDONGSEARCH_RECODE);
        pullLvHudongSearch.post(new Runnable() {
            @Override
            public void run() {
                pullLvHudongSearch.onRefreshComplete();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            default:
                break;
        }
    }

}
