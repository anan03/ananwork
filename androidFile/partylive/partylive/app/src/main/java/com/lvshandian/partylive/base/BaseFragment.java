package com.lvshandian.partylive.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.UrlBuilder;
import com.lvshandian.partylive.bean.AppUser;
import com.lvshandian.partylive.bean.JoinRoomBean;
import com.lvshandian.partylive.bean.LiveBean;
import com.lvshandian.partylive.httprequest.HttpDatas;
import com.lvshandian.partylive.moudles.index.live.WatchLiveActivity;
import com.lvshandian.partylive.moudles.mine.activity.ChargeMoneyActivity;
import com.lvshandian.partylive.utils.CacheUtils;
import com.lvshandian.partylive.utils.JsonUtil;
import com.lvshandian.partylive.utils.LogUtils;
import com.lvshandian.partylive.view.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;

/**
 * 功能描述：对Fragment类进行扩展
 *
 * @author zhang
 */
public abstract class BaseFragment extends Fragment {

    /**
     */
    protected Context mContext;
    protected View view;
    protected HttpDatas httpDatas;
    protected UrlBuilder urlBuilder;
    protected AppUser appUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int layoutId = getLayoutId();
        view = inflater.inflate(layoutId, null);
        ButterKnife.bind(this, view);
        mContext = this.getActivity();
        httpDatas = new HttpDatas(mContext, view);
        urlBuilder = new UrlBuilder();
        appUser = (AppUser) CacheUtils.readObject(mContext, CacheUtils.USERINFO);
        preliminary();
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 向用户展示信息前的准备工作在这个方法里处理
     */
    protected void preliminary() {

        // 初始化数据
        initialized();

        // 初始化组件
        initListener();
    }

    /**
     * 获取全局的Context
     *
     * @return {@link #mContext = this.getApplicationContext();}
     */
    public Context getContext() {
        return mContext;
    }

    /**
     * 布局文件ID
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化监听
     */
    protected abstract void initListener();

    /**
     * 初始化数据
     */
    protected abstract void initialized();


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    public void gotoActivity(Class<? extends Activity> clazz, boolean finish) {
        Intent intent = new Intent(getActivity(), clazz);
        this.startActivity(intent);
        if (finish) {
            getActivity().finish();
        }
        getActivity().overridePendingTransition(R.anim.activity_move_in_start, R.anim.activity_move_out_start);

    }

    public void gotoActivity(Class<? extends Activity> clazz, boolean finish, Bundle pBundle) {
        Intent intent = new Intent(getActivity(), clazz);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }

        this.startActivity(intent);
        if (finish) {
            getActivity().finish();
        }
        getActivity().overridePendingTransition(R.anim.activity_move_in_start, R.anim.activity_move_out_start);
    }

    /**
     * 跳转到观看页面
     *
     * @author sll
     * @time 2016/12/16 16:58
     */
    private void startActivityToWatch(LiveBean live) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("LIVE", live);
        Intent intent = new Intent(getActivity(), WatchLiveActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 判断是否进入过该私密直播
     *
     * @author sll
     * @time 2016/12/16 17:14
     */
    public void ifEnter(final LiveBean live) {
        String url = UrlBuilder.chargeServerUrl + UrlBuilder.ifEnter;
        LogUtils.e("WangYi_secret", "url: " + url);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("roomId", live.getId());
        hashMap.put("userId", appUser.getId());
        String json = new JSONObject(hashMap).toString();
        LogUtils.e("Json:　" + json);
        OkHttpUtils.get().url(url)
                .params(hashMap)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(com.squareup.okhttp.Request request, Exception e) {
                        LogUtils.i("WangYi_secret", "请求进入直播: " + e.toString());
                    }

                    @Override
                    public void onResponse(String response) {
                        LogUtils.i("WangYi_secret", "是否进入过该私密直播: " + response);
                        JoinRoomBean joinRoom = JsonUtil.json2Bean(response, JoinRoomBean.class);
                        //判断是否是私密直播
                        if (joinRoom != null && joinRoom.isSuccess() && joinRoom.getCode() != 1) {

                            //私密直播第一次
                            if (live != null && !TextUtils.isEmpty(live.getPrivateFlag()) && live.getPrivateFlag().equals("1")) {
                                joinSecret(live);
                            } else {
                                //非首次进入直播
                                startActivityToWatch(live);
                            }
                        } else {
                            //普通直播
                            startActivityToWatch(live);
                        }
                    }
                });
    }

    /**
     * 支付进入该私密直播
     * "createrId":createId , @"entrantId":userId ,@"coinNum":coinNum
     *
     * @author sll
     * @time 2016/12/16 17:14
     */
    private void updateCoin(final LiveBean live) {
        String url = UrlBuilder.chargeServerUrl + UrlBuilder.updateCoin;
        LogUtils.e("WangYi_secret", "url: " + url);
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("createrId", live.getCreator().getId());
        hashMap.put("entrantId", appUser.getId());
        hashMap.put("coinNum", live.getRoomPay());
        String json = new JSONObject(hashMap).toString();
        LogUtils.e("Json:　" + json);
        OkHttpUtils.get().url(url)
                .params(hashMap)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(com.squareup.okhttp.Request request, Exception e) {
                        LogUtils.i("WangYi_secret", "付进入该私密直播: " + e.toString());
                    }

                    @Override
                    public void onResponse(String response) {
                        LogUtils.i("WangYi_secret", "付进入该私密直播: " + response);
                        JoinRoomBean joinRoom = JsonUtil.json2Bean(response, JoinRoomBean.class);
                        if (joinRoom != null && joinRoom.isSuccess() && joinRoom.getCode() == 1) {
                            startActivityToWatch(live);
                        } else {
                            ToastUtils.showSnackBar(view, "账户余额不足");
                            gotoActivity(ChargeMoneyActivity.class, false);
                        }
                    }
                });

    }

    /**
     * 私密直播时弹出，须支付或输入密码
     *
     * @author sll
     * @time 2016/12/16 15:59
     */
    private void joinSecret(final LiveBean live) {
        final Dialog dialog = new Dialog(getActivity(), R.style.homedialog);
        final View view = View.inflate(getActivity(), R.layout.dialog_join_secret, null);
        final TextView joinSecretPrompt = (TextView) view.findViewById(R.id.join_secret_prompt);
        final LinearLayout joinSecretCancel = (LinearLayout) view.findViewById(R.id.join_secret_cancel);
        joinSecretPrompt.setText("需要密码或" + live.getRoomPay() + "金币进入该房间");
        //支付
        view.findViewById(R.id.join_secret_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateCoin(live);
                dialog.dismiss();
            }
        });
        //密码
        view.findViewById(R.id.join_secret_pw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinForPw(live);
                dialog.dismiss();
            }
        });
        //取消
        joinSecretCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        dialog.show();
    }

    /**
     * 支付密码进入私密直播
     *
     * @author sll
     * @time 2016/12/16 16:34
     */
    private void joinForPw(final LiveBean live) {
        final Dialog dialog = new Dialog(getActivity(), R.style.homedialog);
        final View view = View.inflate(getActivity(), R.layout.dialog_join_secret_pwd, null);
        final EditText pwdEdit = (EditText) view.findViewById(R.id.join_secret_pwd_edit);
        //取消
        view.findViewById(R.id.join_secret_pwd_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //确定
        view.findViewById(R.id.join_secret_pwd_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(pwdEdit.getText().toString())) {
                    ToastUtils.showSnackBar(pwdEdit, "请输入密码");
                } else if (pwdEdit.getText().toString().equals(live.getRoomPw())) {
                    //密码正确，进入直播间
                    startActivityToWatch(live);
                    dialog.dismiss();
                } else {
                    ToastUtils.showSnackBar(pwdEdit, "密码错误，请确认后再输入");
                }
            }
        });
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(view);
        dialog.show();
    }

}
