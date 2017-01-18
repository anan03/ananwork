package com.lvshandian.asktoask.module.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.lvshandian.asktoask.BaseActivity;
import com.lvshandian.asktoask.MainActivity;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.http.HttpDatas;
import com.lvshandian.asktoask.common.http.RequestCode;
import com.lvshandian.asktoask.utils.AndroidAdjustResizeBugFix;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.MethodUtils;
import com.lvshandian.asktoask.utils.TextUtils;
import com.lvshandian.asktoask.utils.ToastUtils;
import com.lvshandian.asktoask.view.TiXianOverPopupwindow;

import java.util.concurrent.ConcurrentHashMap;

import butterknife.Bind;

/**
 * author: newlq on 2016/9/6
 * email：@lvshandian.com
 * company:北京绿闪电科技有限公司
 * details：提现页面
 */
public class WithDrawActivity extends BaseActivity implements View.OnLayoutChangeListener {


    @Bind(R.id.ll)
    View ll;
    @Bind(R.id.tv_titlebar_centertext)
    TextView tvTitlebarCentertext;
    @Bind(R.id.ll_titlebar_zuojiantou)
    LinearLayout llTitlebarZuojiantou;
    @Bind(R.id.tv_titlebar_righttext)
    TextView tvTitlebarRighttext;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.tv_all_crash)
    TextView tvAllCrash;
    @Bind(R.id.et_cash_money)
    EditText etCashMoney;
    @Bind(R.id.et_input_name)
    EditText etInputName;
    @Bind(R.id.et_zhifubao_zhanghu)
    EditText etZhifubaoZhanghu;
    @Bind(R.id.btn_tixian_gotixian)
    Button btnTixianGotixian;
    @Bind(R.id.root_layout)
    LinearLayout rootLayout;
    private String wodexuanshangjine = "0";

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case RequestCode.GETXUANSHANG_TIXIAN_REQUESTCODE:
                    Bundle data = msg.getData();
                    Object o = data.get(HttpDatas.info);
                    popupwindow.show();
                    this.sendEmptyMessageDelayed(2, 2000L);
                    break;
                case 2:
                    popupwindow.dismiss();
                    Intent intent = new Intent(WithDrawActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };
    private TiXianOverPopupwindow popupwindow;
    private float mone = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tixian;
    }

    @Override
    protected void initListener() {
        btnTixianGotixian.setOnClickListener(this);//体现按钮
        tvAllCrash.setOnClickListener(this);//全部体现

        llTitlebarZuojiantou.setOnClickListener(this);
        //体现金额做监听
        etCashMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //体现金额和名字
                if (!TextUtils.isEmpty(etCashMoney.getText().toString()) && !TextUtils.isEmpty(etInputName.getText().toString())) {
                    if (!TextUtils.isEmpty(etZhifubaoZhanghu.getText().toString())) {
                        btnTixianGotixian.setBackgroundColor(getResources().getColor(R.color.main));//设置背景色  colordadada灰色
                        btnTixianGotixian.setEnabled(true);
                    } else {
                        btnTixianGotixian.setBackgroundColor(getResources().getColor(R.color.colordadada));//置灰按钮 不可点击
                        btnTixianGotixian.setEnabled(false);
                    }
                } else {
                    btnTixianGotixian.setBackgroundColor(getResources().getColor(R.color.colordadada));//置灰按钮 不可点击
                    btnTixianGotixian.setEnabled(false);
                }
            }
        });
        etZhifubaoZhanghu.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //体现金额和名字
                if (!TextUtils.isEmpty(etCashMoney.getText().toString()) && !TextUtils.isEmpty(etInputName.getText().toString())) {
                    if (!TextUtils.isEmpty(etZhifubaoZhanghu.getText().toString())) {
                        btnTixianGotixian.setBackgroundColor(getResources().getColor(R.color.main));//设置背景色  colordadada灰色
                        btnTixianGotixian.setEnabled(true);
                    } else {
                        btnTixianGotixian.setBackgroundColor(getResources().getColor(R.color.colordadada));//置灰按钮 不可点击
                        btnTixianGotixian.setEnabled(false);
                    }
                } else {
                    btnTixianGotixian.setBackgroundColor(getResources().getColor(R.color.colordadada));//置灰按钮 不可点击
                    btnTixianGotixian.setEnabled(false);
                }
            }
        });
        etInputName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //体现金额和名字
                if (!TextUtils.isEmpty(etCashMoney.getText().toString()) && !TextUtils.isEmpty(etInputName.getText().toString())) {
                    if (!TextUtils.isEmpty(etZhifubaoZhanghu.getText().toString())) {
                        btnTixianGotixian.setBackgroundColor(getResources().getColor(R.color.main));//设置背景色  colordadada灰色
                        btnTixianGotixian.setEnabled(true);
                    } else {
                        btnTixianGotixian.setBackgroundColor(getResources().getColor(R.color.colordadada));//置灰按钮 不可点击
                        btnTixianGotixian.setEnabled(false);
                    }
                } else {
                    btnTixianGotixian.setBackgroundColor(getResources().getColor(R.color.colordadada));//置灰按钮 不可点击
                    btnTixianGotixian.setEnabled(false);
                }
            }
        });


    }


    @Override
    protected void initialized() {
        tvTitlebarCentertext.setText(R.string.wodexuanshang_tixiantitle);
        wodexuanshangjine = getIntent().getStringExtra("xuanshangjine");
        if (!MethodUtils.isEmpty(wodexuanshangjine)) {
            tvPrice.setText(wodexuanshangjine);
        }
        mone = Float.parseFloat(wodexuanshangjine.trim());

        popupwindow = new TiXianOverPopupwindow(this, R.layout.activity_tixian);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_titlebar_zuojiantou:
                finish();
                break;
            case R.id.btn_tixian_gotixian:
                tixian();
                break;
            case R.id.tv_all_crash:
                tixian();
                break;
            default:
                break;
        }
    }

    /**
     * 提现按钮
     */
    private void tixian() {
        String tixianjine = MethodUtils.getTextContent(etCashMoney);
        String zhanghao = MethodUtils.getTextContent(etZhifubaoZhanghu);
        String xingming = MethodUtils.getTextContent(etInputName);
        if (MethodUtils.isEmpty(xingming)) {
            ToastUtils.showSnackBar(snackView, "姓名不可为空");
            return;
        }

        if (!TextUtils.IsString(xingming)) {
            ToastUtils.showSnackBar(snackView, "请输入正确的姓名格式");
            return;
        }

        if (MethodUtils.isEmpty(zhanghao)) {
            ToastUtils.showSnackBar(snackView, "支付宝帐号不可为空");
            return;
        }
        if ((TextUtils.isMobileNO(zhanghao) || TextUtils.isEmail(zhanghao))) {
            ToastUtils.showSnackBar(snackView, "请输入正确的支付宝账号账号");
            return;
        }
        if (!MethodUtils.isEmpty(tixianjine)) {
            float jine = 0;
            try {
                jine = Float.parseFloat(tixianjine);
                if (mone < jine) {
                    ToastUtils.showSnackBar(snackView, "您的悬赏余额不足");
                    return;
                }

                if (jine < 15) {
                    ToastUtils.showSnackBar(snackView, "每次最少提现15元");
                    return;
                }

                if (jine > 0) {
                    ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
                    map.put("userId", Global.getUserId(getContext()));
                    map.put("userRealName", xingming);
                    map.put("alipayNumber", zhanghao);
                    map.put("cashMoney", String.valueOf(jine));
                    httpDatas.getData("确认提现", Request.Method.POST, UrlBuilder.WODEXUANSHANG_TIXIAN_URL, map, mHandler, RequestCode.GETXUANSHANG_TIXIAN_REQUESTCODE);
                } else {
                    ToastUtils.showSnackBar(snackView, "提现金额不合法");
                }
            } catch (NumberFormatException e) {
                ToastUtils.showSnackBar(snackView, "提现金额不合法");
                e.printStackTrace();
            }
        } else {
            ToastUtils.showSnackBar(snackView, "提现金额不可为空");
        }

    }


    @Override
    public void onLayoutChange(View v, int left, int top, int right,
                               int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

        //old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值

//      System.out.println(oldLeft + " " + oldTop +" " + oldRight + " " + oldBottom);
//      System.out.println(left + " " + top +" " + right + " " + bottom);


        //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {

//            Toast.makeText(WithDrawActivity.this, "监听到软键盘弹起...", Toast.LENGTH_SHORT).show();

        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {

//            Toast.makeText(WithDrawActivity.this, "监听到软件盘关闭...", Toast.LENGTH_SHORT).show();

        } else {
//            ToastUtils.showSnackBar(snackView, "没有监听到");
        }


    }

    //屏幕高度
    private int screenHeight = 0;
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;
    LinearLayout activityRootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidAdjustResizeBugFix.assistActivity(this);
//        //软件盘弹起后所占高度阀值
//        activityRootView = (LinearLayout)findViewById(R.id.root_layout);
//        //获取屏幕高度
//        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
//        //阀值设置为屏幕高度的1/3
//        keyHeight = screenHeight/10;

    }

    @Override
    protected void onResume() {
        super.onResume();
        //添加layout大小发生改变监听器
//        activityRootView.addOnLayoutChangeListener(this);
    }
}
