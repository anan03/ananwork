package com.lvshandian.menshen.settings;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.lvshandian.menshen.R;
import com.lvshandian.menshen.UrlBuilder;
import com.lvshandian.menshen.base.BaseActivity;
import com.lvshandian.menshen.bean.User;
import com.lvshandian.menshen.httprequest.HttpDatas;
import com.lvshandian.menshen.httprequest.RequestCode;
import com.lvshandian.menshen.utils.JsonUtil;
import com.lvshandian.menshen.utils.TextUtils;
import com.lvshandian.menshen.utils.XUtils;
import com.lvshandian.menshen.view.ToastUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.AutoLinearLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zhang on 2016/11/6.
 * 创建设置界面
 */

public class SettingNameActivity extends BaseActivity {
    @Bind(R.id.view)
    View view;
    @Bind(R.id.iv_back)
    ImageView ivBack;
    @Bind(R.id.ll_parent_view)
    AutoLinearLayout llParentView;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.et_name)
    EditText etName;
    private TextView tv_save;

    @Override
    protected int getLayoutId() {
        return R.layout.seeting_settingname;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle data = msg.getData();
            int tagCode = data.getInt(HttpDatas.code);
            if (tagCode == RequestCode.UPDETE_DELETE) {
                ToastUtils.showSnackBar(snackView, data.getString(HttpDatas.info));
                return;
            }
            switch (msg.what) {
                case RequestCode.UPDETE_DELETE://
                    ToastUtils.showSnackBar(snackView, "昵称修改成功");
                    User user = JsonUtil.json2Bean(data.getString(HttpDatas.info), User.class);
                    XUtils.dropTable(User.class);
                    XUtils.addTable(user);
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("name", TextUtils.getTextContent(etName));
                    intent.putExtras(bundle);
                    setResult(250, intent);
                    EventBus.getDefault().post("reflashname");
                    finish();
                    break;

                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    };

    @Override
    protected void initListener() {
        tv_save = (TextView) findViewById(R.id.tv_save);
        ivBack.setOnClickListener(this);
        tvTitle.setOnClickListener(this);
        tv_save.setOnClickListener(this);


    }

    User user = null;

    @Override
    protected void initialized() {


        if (null != XUtils.findAll(User.class) && null != XUtils.findAll(User.class).get(0)) {
            user = (User) XUtils.findAll(User.class).get(0);
            if (TextUtils.isEmpty(user.getUserName())) {
                etName.setText(user.getPhone());
            } else {
                etName.setText(user.getUserName());
            }

        } else {
            ToastUtils.showSnackBar(snackView, "user不能为空");
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            //返回修改成功
            case R.id.iv_back:

                finish();
                break;
            case R.id.tv_save:
                if (TextUtils.isEmpty(TextUtils.getTextContent(etName))) {
                    ToastUtils.showSnackBar(snackView, "昵称不能为空");
                    return;
                }
                ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
                map.put("userId", "" + user.getUserId());
                map.put("userName", TextUtils.getTextContent(etName));
                httpDatas.getData("修改昵称", Request.Method.POST, UrlBuilder.UPDETE_DELETE, map, mHandler, RequestCode.UPDETE_DELETE);
                break;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
