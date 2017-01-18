package com.lvshandian.asktoask.alipay;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.lvshandian.asktoask.BaseActivity;
import com.lvshandian.asktoask.MainActivity;
import com.lvshandian.asktoask.module.postquestion.PostQuestionActivity;
import com.lvshandian.asktoask.module.postquestion.PostQuestionPayActivity;
import com.lvshandian.asktoask.utils.L;
import com.lvshandian.asktoask.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by 张振 on 2016/10/31.
 * 支付宝支付工具类
 */
public class AliPay {
    public static final String TAG = "alipay-sdk";
    //    private String notify_url = "http://yy.yunbaozhibo.com/public/appcmf/alipay_app/notify_url.php";
    private String notify_url = "www.baidu.com";


    private static final int SDK_PAY_FLAG = 1;

    private static final int SDK_CHECK_FLAG = 2;

    private static final int ALIPAY = 3;
    private String mOut_trade_no;
    private PostQuestionPayActivity mPayActivity;
    private String rechargeNum;
    private String qusetionmoney;  //发布问题价格

    public AliPay(PostQuestionPayActivity payActivity) {
        this.mPayActivity = payActivity;
    }

    public void initPay(String money){

        long time1=Long.parseLong(String.valueOf(System.currentTimeMillis()).toString().substring(0,10));//

        qusetionmoney = money;
        final  String subject = "发布问题";
        final String body = "发布问题";
        final String total_fee ="0.01";

        //服务器异步通知页面路径,需要自己定义  参数 notify_url，如果商户没设定，则不会进行该操作
        final String url = notify_url;

        //本地生成订单号
        mOut_trade_no = getOutTradeNo();
        L.i("订单号"+mOut_trade_no);
        String orderInfo = getOrderInfo(mOut_trade_no,subject, body,total_fee,url);
        String sign = sign(orderInfo);
        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
                + getSignType();
        Message msg = new Message();
        msg.what = ALIPAY;
        msg.obj = payInfo;
        mHandler.sendMessage(msg);
    }

//本地生成订单号
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    private void AldiaoYong(final String payInfo){
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(mPayActivity);
                // 调用支付接口
                String result = alipay.pay(payInfo,true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    Result resultObj = new Result((String) msg.obj);

                    String resultStatus = resultObj.toString();

                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultObj.getResultStatus(), "9000")) {
//                        AppContext.showToastAppMsg(mPayActivity, "支付成功");
//                        ToastUtils.showToast(mPayActivity, "支付成功");
                        /*Intent intent = new Intent(mPayActivity, AlipayResultActivity.class);
                        intent.putExtra("result", 1);
                        mPayActivity.startActivity(intent);
                        mPayActivity.finish();*/
                        //支付成功再这个里面处理结果
                        Toast.makeText(mPayActivity,"发布问题成功",Toast.LENGTH_LONG).show();
                        PostQuestionPayActivity.zhifubao=false;
                        PostQuestionPayActivity.weinxinpay=false;
                        PostQuestionActivity.paysuccess = true;
                        EventBus.getDefault().post("suc");
                        Intent intent = new Intent(mPayActivity,MainActivity.class);
                        mPayActivity.startActivity(intent);
                        mPayActivity.finish();



                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000” 代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultObj.getResultStatus(), "8000")) {
//                            AppContext.showToastAppMsg(mPayActivity, "支付结果确认中");
                            ToastUtils.showToast(mPayActivity, "支付结果确认中");
                        } else {
                            ToastUtils.showToast(mPayActivity,"支付失败");
//                            AppContext.showToastAppMsg(mPayActivity, "支付失败");
                            //支付失败结果在这个里面处理


                           /* Intent intent = new Intent(mPayActivity, AlipayResultActivity.class);
                            intent.putExtra("result", -1);
                            mPayActivity.startActivity(intent);
                            mPayActivity.finish();*/
                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    Toast.makeText(mPayActivity, "检查结果为：" + msg.obj,
                            Toast.LENGTH_SHORT).show();
                    break;
                }
                case ALIPAY:{

                    AldiaoYong((String) msg.obj);
                    break;
                }
                default:
                    break;
            }
        };
    };

    /**
     * create the order info. 创建订单信息
     *
     */
    public String getOrderInfo( String out_trade_no,String subject,String body, String price,String url) {
        // 合作者身份ID
        String orderInfo = "partner=" + "\"" + Keys.DEFAULT_PARTNER + "\"";

        // 卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + Keys.DEFAULT_SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + out_trade_no + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径 //服务器异步通知页面路径  参数 notify_url，如果商户没设定，则不会进行该操作
        orderInfo += "&notify_url=" + "\"" + url + "\"";

        // 接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m〜15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }
    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content
     *            待签名订单信息
     */
    public String sign(String content) {
        return SignUtils.sign(content, Keys.PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     *
     */
    public String getSignType() {
        return "sign_type=\"RSA\"";
    }
}
