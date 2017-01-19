package com.lvshandian.partylive.moudles.index.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.lvshandian.partylive.R;
import com.lvshandian.partylive.UrlBuilder;
import com.lvshandian.partylive.adapter.UserBaseInfoAdapter;
import com.lvshandian.partylive.base.BaseActivity;
import com.lvshandian.partylive.bean.CreatReadyBean;
import com.lvshandian.partylive.bean.UserBean;
import com.lvshandian.partylive.httprequest.HttpDatas;
import com.lvshandian.partylive.httprequest.RequestCode;
import com.lvshandian.partylive.moudles.index.live.StartLiveActivity;
import com.lvshandian.partylive.moudles.index.live.StartLiveReadyActivity;
import com.lvshandian.partylive.moudles.mine.my.OtherPersonHomePageActivity;
import com.lvshandian.partylive.utils.JsonUtil;
import com.lvshandian.partylive.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 搜索页面
 * Created by zz on 2016/11/22.
 */

public class SearchActivity extends BaseActivity {
    @Bind(R.id.iv_private_chat_back)
    ImageView ivPrivateChatBack;
    @Bind(R.id.et_search_input)
    EditText etSearchInput;
    @Bind(R.id.tv_search_btn)
    TextView tvSearchBtn;
    @Bind(R.id.ll_title)
    RelativeLayout llTitle;
    @Bind(R.id.lv_search)
    ListView lvSearch;
    private List<UserBean> userlist = new ArrayList<UserBean>();


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);


            switch (msg.what) {
                //关注请求接收数据
                case RequestCode.SEACH_USER:
                    LogUtils.i(json.toString());

                    userlist = JsonUtil.json2BeanList(json,UserBean.class);
                    if(userlist.size()==0){
                        showToast("没有搜索到用户");
                    }else {
                        fillUI();

                    }





                    break;

            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_index;
    }

    @Override
    protected void initListener() {
        tvSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(etSearchInput.getText().toString())){

                    showToast("请输入您要搜索的信息");
                }else{
                    seachUser(etSearchInput.getText().toString());
//                    userlist.clear();
//                    ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
//                    map.put("followUserId", 10020+"");
//                    map.put("userId", appUser.getId());
//
//
//                    httpDatas.getDataForJson("搜索用户", Request.Method.POST, UrlBuilder.ATTENTION_USER, map, mHandler, RequestCode.SEACH_USER);


                }
            }
        });

        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserBean userBean = userlist.get(position);
                String id1 = userBean.getId();
                Intent intent = new Intent(mContext, OtherPersonHomePageActivity.class);
                intent.putExtra(getString(R.string.visiti_person),id1);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void initialized() {
        ivPrivateChatBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onClick(View v) {



    }
    private void seachUser(String user){
        userlist.clear();
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        map.put("id", user);
        map.put("userId", appUser.getId());


        httpDatas.getData("搜索用户", Request.Method.GET, UrlBuilder.SEARCH, map, mHandler, RequestCode.SEACH_USER);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void fillUI() {

        lvSearch.setAdapter(new UserBaseInfoAdapter(userlist,getContext(),appUser.getId()));

    }
}
