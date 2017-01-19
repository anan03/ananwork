package com.lvshandian.partylive.wangyiyunxin.main.activity;

import android.os.Bundle;
import android.view.View;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.base.BaseActivity;
import com.lvshandian.partylive.wangyiyunxin.chatroom.fragment.ChatRoomMessageFragment;
import com.lvshandian.partylive.wangyiyunxin.main.fragment.SessionListFragment;
import com.netease.nim.uikit.common.activity.UI;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by sll on 2016/11/21.
 */

public class SessionListActivity extends WangYiBaseActivity {
    //    @Bind(R.id.session_list_fragment)
    SessionListFragment sessionListFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_session_list;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initialized() {
        sessionListFragment = (SessionListFragment) getSupportFragmentManager().findFragmentById(R.id.messages_fragment);
        initTitle("", "消息中心", null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_titlebar_left:
                finish();
                break;
        }
    }

}
