package com.lvshandian.asktoask.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.lvshandian.asktoask.MainActivity;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.module.postquestion.PostQuestionActivity;
import com.lvshandian.asktoask.utils.L;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
    private IWXAPI api;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
    	api = WXAPIFactory.createWXAPI(this, "wxc64df39a4aecb60e");
        api.handleIntent(getIntent(), this);


    }

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			if(resp.errCode==0){
				PostQuestionActivity.paysuccess = true;
				EventBus.getDefault().post("suc");
				Toast.makeText(getApplicationContext(), "发布问题成功", Toast.LENGTH_LONG).show();
//				ToastUtils.showSnackBar(getWindow().getDecorView().findViewById(android.R.id.content),"成功");
				Intent intent = new Intent(this,MainActivity.class);
				startActivity(intent);
				finish();
			}else{
				Toast.makeText(getApplicationContext(),"发布问题失败",Toast.LENGTH_LONG).show();
				L.i("微信支付失败");
//				Intent intent = new Intent(this,MainActivity.class);
//				startActivity(intent);
				finish();
			}

//			Bundle dBundle = new Bundle();
//			dBundle.putString("diamonds", mInfo.getCoin());
//			UIHelper.showMyDiamonds(WXPayEntryActivity.this, dBundle);
			finish();
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setTitle(R.string.app_tip);
//			builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
//			builder.show();
		}
	}
}