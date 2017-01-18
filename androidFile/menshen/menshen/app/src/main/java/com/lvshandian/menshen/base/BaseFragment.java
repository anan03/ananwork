package com.lvshandian.menshen.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.lvshandian.menshen.R;
import com.lvshandian.menshen.UrlBuilder;
import com.lvshandian.menshen.httprequest.HttpDatas;

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int layoutId = getLayoutId();
        view = inflater.inflate(layoutId, null);
        ButterKnife.bind(this, view);
        mContext = this.getActivity();
        httpDatas = new HttpDatas(mContext, view);
        urlBuilder = new UrlBuilder();
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
        // 初始化组件
        initListener();
        // 初始化数据
        initialized();
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


}
