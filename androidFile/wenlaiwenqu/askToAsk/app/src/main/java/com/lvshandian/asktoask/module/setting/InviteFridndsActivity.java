package com.lvshandian.asktoask.module.setting;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.Request;
import com.lvshandian.asktoask.BaseActivity;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.http.HttpDatas;
import com.lvshandian.asktoask.common.http.RequestCode;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.ShareUtils;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import butterknife.Bind;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * 推荐好友分享界面 on 2016/9/6.
 */
public class InviteFridndsActivity extends BaseActivity {
    @Bind(R.id.ll_tuijianhaoyou_weixinhaoyou)
    LinearLayout llTuijianhaoyouWeixinhaoyou;
    @Bind(R.id.ll_tuijianhaoyou_qqhaoyou)
    LinearLayout llTuijianhaoyouQqhaoyou;
    @Bind(R.id.ll_tuijianhaoyou_xinlanghaoyou)
    LinearLayout llTuijianhaoyouXinlanghaoyou;
    @Bind(R.id.tv_titlebar_centertext)
    TextView tvtitlebarcentertext;
    @Bind(R.id.ll_titlebar_zuojiantou)
    LinearLayout llTitlebarZuojiantou;
    private String ImagString = "http://101.201.120.234:8080/wlwq/resource//images/147556213401877.jpg";
    private String code;//邀请码
    private int type=0;//分享平台
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String json = data.getString(HttpDatas.info);
            switch (msg.what) {
                //我的模块提问请求接收数据
                case RequestCode.INVITE_CODE:
                    code=json;
                    if(type==1){
                        fenxiang_QQ();
                    }
                    if(type==2){
                        fenxiang_WEIXIN();
                    }
                    if(type==3){
                        fenxiang_XINLANG();
                    }
                    break;
                case RequestCode.INVITE_CODE_SUCCESS:
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected int getLayoutId() {
        return R.layout.activity_tuijianhaoyou;
    }

    @Override
    protected void initListener() {
        llTitlebarZuojiantou.setOnClickListener(this);
        llTuijianhaoyouQqhaoyou.setOnClickListener(this);
        llTuijianhaoyouWeixinhaoyou.setOnClickListener(this);
        llTuijianhaoyouXinlanghaoyou.setOnClickListener(this);
    }

    @Override
    protected void initialized() {
        tvtitlebarcentertext.setText(R.string.mine_itemtext_tuijianhaoyou);

//        //设置文字变色
//        ForegroundColorSpan redSpan = new ForegroundColorSpan(getResources().getColor(R.color.main));
//        String textContent = MethodUtils.getTextContent(tvTuijianhaoyouTuijianguize);
//        SpannableStringBuilder builder = new SpannableStringBuilder(textContent);
//        builder.setSpan(new AbsoluteSizeSpan(40), textContent.length() - 12, textContent.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        builder.setSpan(redSpan, textContent.length() - 12, textContent.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        tvTuijianhaoyouTuijianguize.setText(builder);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_tuijianhaoyou_weixinhaoyou:
                type=2;
                httpInviteCode();

                break;
            case R.id.ll_tuijianhaoyou_qqhaoyou:
                type=1;
                httpInviteCode();

                break;
            case R.id.ll_tuijianhaoyou_xinlanghaoyou:
                type=3;
                httpInviteCode();

                break;
            case R.id.ll_titlebar_zuojiantou:
                finish();
                break;
            default:
                break;
        }
    }



    /**
     * 新浪分享
     */
    private void fenxiang_XINLANG() {
        notifyHouTai();
        ShareUtils.share(InviteFridndsActivity.this, 0, "我的邀请码是:"+code+",注册的时候记得填噢(七天有效期)", new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        }, ImagString);
    }

    /**
     * QQ分享
     */
    private void fenxiang_QQ() {

        notifyHouTai();
        ShareUtils.share(InviteFridndsActivity.this, 2, "我的邀请码是:"+code+",注册的时候记得填噢(七天有效期)", new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {


            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
            }

            @Override
            public void onCancel(Platform platform, int i) {
            }
        }, ImagString);
    }


    private void notifyHouTai(){

        map.clear();
        map.put("inviterUserId",Global.getUserId(getContext()));
        map.put("invitationCode",code);
        map.put("state","1");
        httpDatas.getData("推荐成功后告诉后台", Request.Method.POST, UrlBuilder.INVITE_URL_SUCCESS, map, mHandler, RequestCode.INVITE_CODE_SUCCESS);
    }




    /**
     * 微信分享
     */
    private void fenxiang_WEIXIN() {
        notifyHouTai();
        ShareUtils.shareWeChat(InviteFridndsActivity.this, true, "我的邀请码是:"+code+",注册的时候记得填噢(七天有效期)", "问来问去", new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
            }

            @Override
            public void onCancel(Platform platform, int i) {
            }
        },"");


    }



    ConcurrentHashMap map = new ConcurrentHashMap<>();

    /**
     * 获取邀请码
     */
    private void httpInviteCode() {
        map.clear();
        httpDatas.getData("推荐好友获取邀请码", Request.Method.POST, UrlBuilder.INVITE_URL, map, mHandler, RequestCode.INVITE_CODE);


    }



}
