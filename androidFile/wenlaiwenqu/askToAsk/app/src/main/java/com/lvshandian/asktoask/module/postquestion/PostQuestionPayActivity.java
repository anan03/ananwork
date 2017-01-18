package com.lvshandian.asktoask.module.postquestion;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.lvshandian.asktoask.BaseActivity;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.WChatPay.WChatPay;
import com.lvshandian.asktoask.alipay.AliPay;
import com.lvshandian.asktoask.utils.ToastUtils;
import butterknife.Bind;

/**
 * 发布问题支付界面 on 2016/10/7.
 */
public class PostQuestionPayActivity extends BaseActivity {
    @Bind(R.id.back_pay)
    ImageView backPay;
    @Bind(R.id.tv_confirm_real)
    TextView tvConfirmReal;
    @Bind(R.id.ck_weixin)
    CheckBox ckWeixin;
    @Bind(R.id.ck_zhifubao)
    CheckBox ckZhifubao;
    @Bind(R.id.btn_pay)
    Button btnPay;
    public static  boolean weinxinpay;
    public static boolean zhifubao;
    public static String price;
    private AliPay aliPay;
    private WChatPay wChatPay;
    @Override
    protected void initialized() {
        aliPay = new AliPay(this);
        wChatPay = new WChatPay(this);

        price=getIntent().getStringExtra("price");

        tvConfirmReal.setText("￥"+price+".00");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_post_question_pay;
    }

    @Override
    protected void initListener() {
        backPay.setOnClickListener(this);
        btnPay.setOnClickListener(this);
        ckWeixin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    weinxinpay = true;
                    ckZhifubao.setChecked(false);
                } else {
                    weinxinpay = false;
                }
            }
        });

        ckZhifubao.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    zhifubao = true;
                    ckWeixin.setChecked(false);
                } else {
                    zhifubao = false;
                }
            }
        });

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_pay:
                zhifubao=false;
                weinxinpay=false;
                finish();
                break;
            case R.id.btn_pay:
                if ((!zhifubao)&&(!weinxinpay)) {
                    ToastUtils.showSnackBar(snackView,"请选择支付方式");
                }else{
                    if(zhifubao){
                        aliPay.initPay(price);
                    }else{
                        wChatPay.initPay(price);
//                        finish();

                    }
                }
                break;
        }

    }

}
