package com.lvshandian.asktoask.module.answer.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.lvshandian.asktoask.BaseActivity;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.http.HttpDatas;
import com.lvshandian.asktoask.common.http.RequestCode;
import com.lvshandian.asktoask.entry.AnswerSearch;
import com.lvshandian.asktoask.module.answer.adapter.AnswerSearchAdapter;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.JsonUtil;
import com.lvshandian.asktoask.utils.TextUtils;
import com.lvshandian.asktoask.utils.ToastUtils;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * on 2016/9/23.
 * 答咖搜索答师
 */
public class AnserSearchActivity extends BaseActivity {

    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.et_search)
    EditText etSearch;
    @Bind(R.id.pull_lv_anser_search)
    PullToRefreshListView pullLvAnserSearch;
    private String keyWords="";
    private List<AnswerSearch.DataBean> reallist;
    ConcurrentHashMap<String, String> map=new ConcurrentHashMap<>();

    @Override
    protected void initListener() {
        ivBack.setOnClickListener(this);
        pullLvAnserSearch.setMode(PullToRefreshBase.Mode.PULL_FROM_START);//只支持下拉

        pullLvAnserSearch.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {


            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if(!TextUtils.isEmpty(etSearch.getText().toString())){
                    reallist.clear();
                    requesHttp();
                }

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(Global.isLogin(getContext())){
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                        if(TextUtils.isEmpty(etSearch.getText().toString())){
                            ToastUtils.showSnackBar(snackView,"输入不能为空");
                        }else{
                            keyWords = etSearch.getText().toString();
                            requesHttp();
                        }


//
//                        if(TextUtils.isEmpty(etSearch.getText().toString())){
//                            ToastUtils.showSnackBar(snackView,"搜索内容不能为空");
//                        }else{
//                            keyWords = etSearch.getText().toString();
//                            requesHttp();
//                        }

                        return true;
                    }
                }else{
                    ToastUtils.showSnackBar(snackView,"请先登录");
                }

                return false;
            }
        });

    }

    @Override
    protected void initialized() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_anser_search_layout;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);
            switch (msg.what) {
                case RequestCode.ANSER_SEARCH:  //答咖搜索
                    if(TextUtils.isEmpty(json)||JsonUtil.json2BeanList(json, AnswerSearch.DataBean.class).size()==0){
                        ToastUtils.showSnackBar(snackView, "没有搜索到结果");
                    }else{
                        reallist =JsonUtil.json2BeanList(json,AnswerSearch.DataBean.class);
                        pullLvAnserSearch.setAdapter(new AnswerSearchAdapter(getContext(),reallist,R.layout.item_activity_anser_search_layout,httpDatas));
                    }
                    break;
                default:
                    break;
            }
        }
    };

    //答咖搜索  传入搜索值
    public void requesHttp() {
        map.clear();
        map.put("attentorId", Global.getUserId(getContext()));
        map.put("userRealName",keyWords);//关键字
        httpDatas.getData("答咖搜索界面", Request.Method.POST, UrlBuilder.ANSWERSEARCH_URL, map, mHandler, RequestCode.ANSER_SEARCH);
        pullLvAnserSearch.post(new Runnable() {
            @Override
            public void run() {
                pullLvAnserSearch.onRefreshComplete();
            }
        });
    }

};
