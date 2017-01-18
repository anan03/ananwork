package com.lvshandian.menshen.phone;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.lvshandian.menshen.MainActivity;
import com.lvshandian.menshen.R;
import com.lvshandian.menshen.UrlBuilder;
import com.lvshandian.menshen.analysesms.AnayseSmsUploadActivity;
import com.lvshandian.menshen.base.BaseActivity;
import com.lvshandian.menshen.bean.Banner;
import com.lvshandian.menshen.bean.Keyworkinfo;
import com.lvshandian.menshen.bean.PhoneBean;
import com.lvshandian.menshen.bean.User;
import com.lvshandian.menshen.bean.VersonBean;
import com.lvshandian.menshen.httprequest.HttpDatas;
import com.lvshandian.menshen.httprequest.RequestCode;
import com.lvshandian.menshen.phone.adapter.PhoneHoldUpAdapter;
import com.lvshandian.menshen.utils.JsonUtil;
import com.lvshandian.menshen.utils.LogUtils;
import com.lvshandian.menshen.utils.NetworkUtils;
import com.lvshandian.menshen.utils.ToastUtil;
import com.lvshandian.menshen.utils.XUtils;
import com.lvshandian.menshen.view.DialogView;
import com.lvshandian.menshen.view.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;
import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionDenied;
import com.zhy.m.permission.PermissionGrant;

import org.xutils.common.util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhang on 2016/10/24.
 */

public class PhoneHoldUpActivity extends BaseActivity {


    @Bind(R.id.view)
    View view;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.ll_parent_view)
    AutoLinearLayout llParentView;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.rl_add)
    AutoLinearLayout rlAdd;
    @Bind(R.id.rl_rl)
    AutoRelativeLayout rlRl;
    @Bind(R.id.lv)
    ListView lv;
    private AutoLinearLayout llPart;
    private int numTag = 0;
    List<PhoneBean> phoneBean;
    private PhoneHoldUpAdapter mAdapter;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            int tagCode = data.getInt(HttpDatas.code);

            switch (msg.what) {
                case RequestCode.PHONE_SELECT://查询成功
                    //请求号段查询
                    if (tagCode == RequestCode.REQUEST_CODE) {
                        ToastUtils.showSnackBar(snackView, data.getString(HttpDatas.info));
                        return;
                    }
                    phoneBean = (List<PhoneBean>) JsonUtil.json2BeanList(data.getString(HttpDatas.info), PhoneBean.class);
                    XUtils.dropTable(PhoneBean.class);
                    for (int i = 0; i < phoneBean.size(); i++) {
                        XUtils.addTable(phoneBean.get(i));
                        LogUtils.e("插入数据---" + phoneBean.get(i));
                    }
                    LogUtils.e("号段拦截查询data---" + data.getString(HttpDatas.info));
                    mAdapter = new PhoneHoldUpAdapter(PhoneHoldUpActivity.this, phoneBean, R.layout.item_phonepholdup, view, getIntent().getStringExtra("userId"));
                    lv.setAdapter(mAdapter);


                    break;
                case RequestCode.PHONE_ADD://添加号段
                    //请求号段查询
                    if (tagCode == RequestCode.REQUEST_CODE) {
                        ToastUtils.showSnackBar(snackView, data.getString(HttpDatas.info));
                        return;
                    }
                    requstPhone(1);
                    break;
            }

        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.phone_activity_phoneholdup;
    }

    @Override
    protected void initListener() {

        rlAdd.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    final int REQUECT_CODE_READ_PHPNE = 202;

    @Override
    protected void initialized() {
        llPart = (AutoLinearLayout) findViewById(R.id.ll_part);
        //权限请求
        MPermissions.requestPermissions(PhoneHoldUpActivity.this, REQUECT_CODE_READ_PHPNE, Manifest.permission.READ_SMS);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    final int READ_PHONE_STATE = 204;

    //请求成功
    @PermissionGrant(REQUECT_CODE_READ_PHPNE)
    public void requestSdcardSuccess() {
        MPermissions.requestPermissions(PhoneHoldUpActivity.this, READ_PHONE_STATE, Manifest.permission.READ_PHONE_STATE);

        if (NetworkUtils.IsNetworkAvailable(mContext)) {//有网络

            initidata();

        } else {

            List<PhoneBean> list = XUtils.findAll(PhoneBean.class);

            if (null != list && list.get(0) != null) {//如果数据不为空

                phoneBean = list;
                mAdapter = new PhoneHoldUpAdapter(PhoneHoldUpActivity.this, phoneBean, R.layout.item_phonepholdup, view, getIntent().getStringExtra("userId"));
                lv.setAdapter(mAdapter);

            } else {
                ToastUtils.showSnackBar(snackView, "请连接网络");
            }

        }

    }

    @PermissionDenied(REQUECT_CODE_READ_PHPNE)
    public void requestSdcardFailed() {
        finish();
    }

    private void initidata() {

        requstPhone(0);
    }

    //进行网络请求；
    private void requstPhone(int tag) {
        numTag = tag;

        //判断是添加还是查询
        if (null != getIntent().getStringExtra("userId")) {
            ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
            map.put("userId", getIntent().getStringExtra("userId"));
            httpDatas.getData("拦截电话号码全部查询", Request.Method.POST, UrlBuilder.PHONE_SELECT, map, mHandler, RequestCode.PHONE_SELECT);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_add:
                new DialogView(mContext, view, "请输入要拦截的号段", "确定", "取消", 3, new DialogView.MyCallback() {
                    @Override
                    public void refreshActivity() {

                    }

                    @Override
                    public void refreshActivity(String tag) {
                        //得到添加的关键字存到数据库

                        if (null == tag || tag.trim().equals("")) {

                            ToastUtils.showSnackBar(snackView, "拦截的号段不能为空");

                        } else {
                            ToastUtils.showSnackBar(snackView, tag.trim());
                            //添加拦截号码字段
                            addPhoneRequst(tag);
                        }
                    }

                    @Override
                    public void refreshActivity(String context, String minString, String maxString) {

                    }
                });
                break;
            case R.id.iv_back://返回
                finish();
                break;

        }


    }

    private void addPhoneRequst(String phone) {
        if (null != getIntent().getStringExtra("userId")) {
            ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
            map.put("userId", getIntent().getStringExtra("userId"));
            map.put("dnsegType", "2");
            map.put("dnseg", phone);
            httpDatas.getData("添加手机拦截号码", Request.Method.POST, UrlBuilder.PHONE_ADD, map, mHandler, RequestCode.PHONE_ADD);

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
