package com.lvshandian.asktoask;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.lvshandian.asktoask.module.answer.AnswerFragment;
import com.lvshandian.asktoask.module.interaction.GuidActivity;
import com.lvshandian.asktoask.module.interaction.InteractionFragment;
import com.lvshandian.asktoask.module.login.LoginActivity;
import com.lvshandian.asktoask.module.message.MessageFragment;
import com.lvshandian.asktoask.module.user.UserFragment;
import com.lvshandian.asktoask.utils.Constant;
import com.lvshandian.asktoask.utils.DialogView;
import com.lvshandian.asktoask.utils.Global;
import com.lvshandian.asktoask.utils.TextUtils;
import com.lvshandian.asktoask.view.HuDongSharePopupwindow;
import com.lvshandian.asktoask.view.QuestionPopupwindow;
import com.lvshandian.asktoask.utils.ToastUtils;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

/**
 * author: ldb on 2016/8/30 14:28.
 * email：lvdabing@lvshandian.com
 * company:北京绿闪电科技有限公司
 * details：主界面
 */
public class MainActivity extends BaseActivity {
    @Bind(R.id.d_rg_graoup)
    RadioGroup dRgGraoup;
    @Bind(R.id.question_iv)
    ImageView questionIv;
    public static LinearLayout llParentView;
    private FragmentManager mManager;
    private Fragment interactionFragment, answerFragment, messageFragment, userFragment;
    private int checkedId = 0;//记录位置
    QuestionPopupwindow questionPopupwindow;
    public static HuDongSharePopupwindow pop;
    public static TextView messagehot;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initListener() {
        dRgGraoup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(messagehot.getVisibility()==View.VISIBLE&&checkedId==R.id.d_rb_message){
                    messagehot.setVisibility(View.GONE);
                }
                setttingFragment(checkedId);
            }
        });
        findViewById(R.id.d_rb_message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(messagehot.getVisibility()==View.VISIBLE){
                    messagehot.setVisibility(View.GONE);
                }
            }
        });
        questionIv.setOnClickListener(this);
    }

    @Override
    protected void initialized() {
        messagehot=(TextView) findViewById(R.id.message_hot);
        mManager = getSupportFragmentManager();
        setttingFragment(R.id.d_rb_interaction);
        questionPopupwindow = new QuestionPopupwindow(this, R.layout.activity_main);
        llParentView=(LinearLayout)findViewById(R.id.ll_parent_view);
        registerMessageReceiver();
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
            if (Constant.RECEIVERMESSAGE.equals(intent.getAction())) {
                String messge = intent.getStringExtra(Constant.MESSAGE);
                String extras = intent.getStringExtra(Constant.EXTRAS);
                StringBuilder showMsg = new StringBuilder();
                Log.e("message11", messge);
                String get=TextUtils.get6str(messge);
                if("您的认证已通".equals(get)){
                    Global.setParam(getContext(),"isappprove","1");
                }
                if("您的认证没有".equals(get)||"有人回复了你".equals(get)||"您的好友已完".equals(get)||"有人关注了你".equals(get)||"有人回答了你".equals(get)||"你的回答被采".equals(get)||"您的认证没有".equals(get)){
                } else{
                    MainActivity.messagehot.setVisibility(View.VISIBLE);//显示小红点
                }

            }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.question_iv:
                //提问
                questionPopupwindow.show();
                break;
        }
    }


    private void setttingFragment(int position) {
        this.checkedId = position;
        FragmentTransaction transaction = mManager.beginTransaction();
        hideFragments(transaction);

        switch (position) {
            case R.id.d_rb_interaction://互动
                if (interactionFragment == null) {
                    interactionFragment = new InteractionFragment();
                    transaction.add(R.id.d_fl_content, interactionFragment);
                } else {
                    transaction.show(interactionFragment);
                }
                break;
            case R.id.d_rb_answer://答咖
                if (answerFragment == null) {
                    answerFragment = new AnswerFragment();
                    transaction.add(R.id.d_fl_content, answerFragment);
                } else {
                    transaction.show(answerFragment);
                }
                break;
            case R.id.d_rb_message://消息

                if(!Global.isLogin(getContext())){
                  gotoActivity(LoginActivity.class,true);
                }else{


                    if (messageFragment == null) {
                        messageFragment = new MessageFragment();
                        transaction.add(R.id.d_fl_content, messageFragment);
                    } else {
                        transaction.show(messageFragment);
                    }

                    if (!Global.isLogin(mContext)) {
                        transaction.commitAllowingStateLoss();
                        new DialogView(mContext, llParentView, 0, "你还未登录，请登录", "确定", "取消",
                                new DialogView.MyCallback() {


                                    @Override
                                    public void refreshActivity() {

                                        gotoActivity(LoginActivity.class, true);


                                    }
                                });
                        return;

                    }
                }


                break;
            case R.id.d_rb_my://我的
                if(!Global.isLogin(getContext())){
                    gotoActivity(LoginActivity.class, true);
                }else{
//                if (userFragment == null) {
//                    userFragment = new UserFragment();
//                    transaction.add(R.id.d_fl_content, userFragment);
//                } else {
//
//                    transaction.show(userFragment);
//                }
                    userFragment = new UserFragment();
                    transaction.add(R.id.d_fl_content, userFragment);
                    transaction.show(userFragment);

                }
                break;
        }
        transaction.commitAllowingStateLoss();
    }

    // 当fragment已被实例化，就隐藏起来
    public void hideFragments(FragmentTransaction ft) {
        if (interactionFragment != null)
            ft.hide(interactionFragment);
        if (messageFragment != null)
            ft.hide(messageFragment);
        if (answerFragment != null)
            ft.hide(answerFragment);
        if (userFragment != null)
            ft.hide(userFragment);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        this.checkedId = savedInstanceState.getInt("position");
        setttingFragment(this.checkedId);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        /**
//         * 设置为横屏
//         */
//        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//竖屏
//        }
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //记录当前的position
        outState.putInt("position", this.checkedId);
    }


    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastUtils.showSnackBar(dRgGraoup, "再按一次退出程序");
                Global.setParam(getContext(),"isapprove","2");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
//        //显示上方边框  沉浸式状态栏
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }

        if((Boolean)Global.getParam(getContext(),"first",true)){
            Global.setParam(getContext(), "first", false);
            startActivity(new Intent(getContext(), GuidActivity.class));

        }
    }
}
