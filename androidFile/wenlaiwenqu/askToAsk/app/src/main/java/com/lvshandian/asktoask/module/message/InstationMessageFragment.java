package com.lvshandian.asktoask.module.message;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.lvshandian.asktoask.BaseFragment;
import com.lvshandian.asktoask.MainActivity;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.http.HttpDatas;
import com.lvshandian.asktoask.common.http.RequestCode;
import com.lvshandian.asktoask.entry.InstationMessageBean;
import com.lvshandian.asktoask.entry.JpushMessageBean;
import com.lvshandian.asktoask.module.message.adapter.InstationMessageAdapter;
import com.lvshandian.asktoask.utils.Constant;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.JsonUtil;
import com.lvshandian.asktoask.utils.UrlBuilder;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *  站内信 on 2016/9/22.
 * <p/>
 * 创建站内信界面
 */
public class InstationMessageFragment extends BaseFragment {

    public static boolean isForeground = false;
    @Bind(R.id.lv_message)
    ListView lvMessage;
    @Bind(R.id.rl_messge)
    RelativeLayout rlMessge;
    @Bind(R.id.tv_message)
    TextView tvMessage;
    private List<JpushMessageBean.DataBean> dataBeanList;
    private final int MESSAGE = 1001;
    ConcurrentHashMap map = new ConcurrentHashMap<>();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);
            switch (msg.what) {
                //接受推送的消息
                case MESSAGE:


                String message=(String) msg.obj;


                    Log.d("aaa","messsage过来了"+message);
                    MainActivity.messagehot.setVisibility(View.VISIBLE);





//                    tvMessage.setText();
//                    JpushMessageBean jpushMessage = JsonUtil.json2Bean((String) msg.obj, JpushMessageBean.class);
//                    dataBeanList = jpushMessage.getData();
//                    if (Global.isLogin(getContext())) {
//
//                        if (null == dataBeanList || dataBeanList.size() == 0) {
//                            //隐藏站内信息
//                            tvMessage.setVisibility(View.VISIBLE);
//                            lvMessage.setVisibility(View.GONE);
//                            tvMessage.setText("你暂时没有站内信");
//
//                        } else {
//                            //显示站内信息
//                            tvMessage.setVisibility(View.GONE);
//                            lvMessage.setVisibility(View.VISIBLE);
//
//                        }
//
//                    } else {
//                        //隐藏所有图标
//                        lvMessage.setVisibility(View.VISIBLE);
//                        tvMessage.setVisibility(View.GONE);
//
//                    }   //站内信adapter
////                        InstationMessageAdapter istantionAdapter=new InstationMessageAdapter (getContext(), dataBeanList, R.layout.item_message_instation);
////                        lvMessage.setAdapter(istantionAdapter);
                    break;
                case RequestCode.INSTATION_MESSAGET://站内信列表

                    List<InstationMessageBean.DataBean> list=JsonUtil.json2BeanList(json, InstationMessageBean.DataBean.class);
                    if(list.size()==0){
                        //隐藏站内信息
                        tvMessage.setVisibility(View.VISIBLE);
                        lvMessage.setVisibility(View.GONE);
                        tvMessage.setText("你暂时没有站内信");
                    }else{
                        //显示站内信息
                        tvMessage.setVisibility(View.GONE);
                        lvMessage.setVisibility(View.VISIBLE);
                    }
                    //站内信adapter
                    InstationMessageAdapter istantionAdapter=new InstationMessageAdapter (getContext(), list, R.layout.item_message_instation);
                    lvMessage.setAdapter(istantionAdapter);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.instationmessage_fragment_layout;
    }


    @Override
    protected void initialized() {
//        registerMessageReceiver();//注册广播
        requesHttp();//请求站内信列表

    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    /**
     * 注册广播
     */

    public void registerMessageReceiver() {
        MessageReceiver mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(Constant.RECEIVERMESSAGE);
        getContext().registerReceiver(mMessageReceiver, filter);
    }
    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.e("message1", "-----------");
            if (Constant.RECEIVERMESSAGE.equals(intent.getAction())) {
                String messge = intent.getStringExtra(Constant.MESSAGE);
                String extras = intent.getStringExtra(Constant.EXTRAS);
                StringBuilder showMsg = new StringBuilder();

                Message msg = new Message();
                msg.obj = messge;
                msg.what = MESSAGE;
                Log.e("message1", messge);
//                Log.e("message2", messge);
                //登录状态
                mHandler.handleMessage(msg);


            }
        }
    }
    @Override
    protected void initListener() {
        lvMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JpushMessageBean.DataBean dataBean = dataBeanList.get(position);
                Intent intent = new Intent(getContext(), InstationDetailsActivity.class);
                intent.putExtra("dataBean", dataBean);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });




    }

    @Override
    public void onResume() {
        super.onResume();
        isForeground = true;

    }

    @Override
    public void onPause() {
        super.onPause();
        isForeground = false;

    }


    /**
     *消息模块中的站内信列表
     */
    private void requesHttp() {
        if (Global.isLogin(getContext())) {
            map = new ConcurrentHashMap<>();
            map.clear();
            map.put("userId", Global.getUserId(getContext()));
            map.put("area","android");
            httpDatas.getData("站内信", Request.Method.POST, UrlBuilder.LEAVE_INSTATION_EAMIL_URL, map, mHandler, RequestCode.INSTATION_MESSAGET);//站内信
        }
            }
}
