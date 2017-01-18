package com.lvshandian.asktoask.module.message;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.lvshandian.asktoask.BaseActivity;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.http.HttpDatas;
import com.lvshandian.asktoask.common.http.RequestCode;
import com.lvshandian.asktoask.entry.InstationMessageBean;
import com.lvshandian.asktoask.entry.JpushMessageBean;
import com.lvshandian.asktoask.utils.DateUtil;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.UrlBuilder;

import org.w3c.dom.ProcessingInstruction;

import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhang on 2016/10/8.
 * 创建站内信详情界面；
 */
public class InstationDetailsActivity extends BaseActivity {
    @Bind(R.id.iv_message_instation)
    ImageView ivMessageInstation;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_message_date)
    TextView tvMessageDate;
    @Bind(R.id.rl_answer_detail)
    RelativeLayout rlAnswerDetail;
    @Bind(R.id.tv_message_data)
    TextView tvMessageData;
    @Bind(R.id.iv_back_approve_address)
    ImageView ivBackApproveAddress;
    @Bind(R.id.ll_parent_view)
    LinearLayout llParentView;
    @Bind(R.id.rl_title)
    RelativeLayout rlTitle;
    ConcurrentHashMap map = new ConcurrentHashMap<>();
    public static final String TRANCE="item";//传过来的对象
    private InstationMessageBean.DataBean trancebean;


    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);
            switch (msg.what) {
                case RequestCode.INSTATION_MESSAGET_DETAIL:
                    Log.d("详情站内信",json);
                    break;
                default:break;
            }
        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_instation_details;
    }

    @Override
    protected void initListener() {

        ivBackApproveAddress.setOnClickListener(this);
    }

    @Override
    protected void initialized() {
        trancebean=(InstationMessageBean.DataBean)getIntent().getSerializableExtra(TRANCE);
        tvTitle.setText(trancebean.extend1);//标题
        tvMessageDate.setText(DateUtil.timesOne(trancebean.addDate));
        tvMessageData.setText(trancebean.message);//内容





    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            //点击返回监听
            case R.id.iv_back_approve_address:
                finish();
                break;
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }



        /**
         * 消息模块中的站内信列表
         */
        private void requesHttp() {


            if (Global.isLogin(getContext())) {
                map = new ConcurrentHashMap<>();
                map.clear();
                map.put("userId", Global.getUserId(getContext()));
                map.put("mailId", trancebean.id);
                httpDatas.getData("站内信详情", Request.Method.POST, com.lvshandian.asktoask.utils.UrlBuilder.LEAVE_INSTATION_EAMIL_DETAIL_URL, map, mHandler, RequestCode.INSTATION_MESSAGET_DETAIL);//站内信详情

            }
        }


    }



