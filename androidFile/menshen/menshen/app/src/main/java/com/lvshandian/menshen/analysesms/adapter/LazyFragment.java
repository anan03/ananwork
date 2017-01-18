package com.lvshandian.menshen.analysesms.adapter;

import android.support.v4.app.Fragment;

public class LazyFragment extends Fragment {
    /**
     * ��ǰFragment�Ƿ���ʾ��true,��ʾ
     */
    public boolean isVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = false;
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        }
    }

    protected void onVisible() {

    }

}
