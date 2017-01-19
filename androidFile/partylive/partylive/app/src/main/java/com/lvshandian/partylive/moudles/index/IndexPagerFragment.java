package com.lvshandian.partylive.moudles.index;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.interf.PagerSlidingInterface;
import com.lvshandian.partylive.moudles.index.activity.SearchActivity;
import com.lvshandian.partylive.moudles.index.adapter.ViewPageFragmentAdapter;
import com.lvshandian.partylive.moudles.index.fragment.AttentionFragment;
import com.lvshandian.partylive.moudles.index.fragment.ChannelFragment;
import com.lvshandian.partylive.moudles.index.fragment.HallFragment;
import com.lvshandian.partylive.moudles.index.fragment.NearbyRoomFragment;
import com.lvshandian.partylive.wangyiyunxin.main.activity.SessionListActivity;
import com.lvshandian.partylive.wangyiyunxin.main.helper.SystemMessageUnreadManager;
import com.lvshandian.partylive.wangyiyunxin.main.reminder.ReminderItem;
import com.lvshandian.partylive.wangyiyunxin.main.reminder.ReminderManager;
import com.lvshandian.partylive.wangyiyunxin.main.reminder.ReminderSettings;
import com.lvshandian.partylive.widget.PagerSlidingTabStrip;
import com.netease.nim.uikit.common.ui.drop.DropCover;
import com.netease.nim.uikit.common.ui.drop.DropFake;
import com.netease.nim.uikit.common.ui.drop.DropManager;
import com.netease.nim.uikit.common.util.log.LogUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.msg.MsgService;
import com.netease.nimlib.sdk.msg.SystemMessageObserver;
import com.netease.nimlib.sdk.msg.SystemMessageService;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.msg.model.RecentContact;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class IndexPagerFragment extends Fragment implements View.OnClickListener, ReminderManager.UnreadNumChangedCallback {

    @Bind(R.id.iv_hot_search)
    ImageView ivHotSearch;
    @Bind(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @Bind(R.id.iv_hot_private_chat)
    AutoRelativeLayout ivHotPrivateChat;
    @Bind(R.id.mviewpager)
    ViewPager pager;
    @Bind(R.id.tab_new_indicator)
    ImageView tabNewIndicator;
    @Bind(R.id.tab_new_msg_label)
    DropFake tabNewMsgLabel;
    @Bind(R.id.unread_cover)
    DropCover unreadCover;
    private View view;


    private ViewPageFragmentAdapter viewPageFragmentAdapter;
    public static int mSex = 0;
    public static String mArea = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_index, null);
            ButterKnife.bind(this, view);
            initView();
        } else {
            ButterKnife.bind(this, view);
            tabs.setViewPager(pager);
        }

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        registerMsgUnreadInfoObserver(true);
        registerSystemMessageObservers(true);
        requestSystemMessageUnreadCount();
        initUnreadCover();
    }


    //    @OnClick({R.id.iv_hot_private_chat, R.id.iv_hot_search})
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.iv_hot_private_chat:
//                int uid = AppContext.getInstance().getLoginUid();
//                if (0 < uid) {
//                    UIHelper.showPrivateChatSimple(getActivity(), uid);
//                }
//
//                break;
//            case R.id.iv_hot_search:
//                UIHelper.showScreen(getActivity());
//                break;
//        }
//    }

    private void initView() {

        viewPageFragmentAdapter = new ViewPageFragmentAdapter(getFragmentManager(), pager);
        viewPageFragmentAdapter.addTab(getString(R.string.attention), "gz", AttentionFragment.class, getBundle());
        viewPageFragmentAdapter.addTab(getString(R.string.hall), "rm", HallFragment.class, getBundle());
        viewPageFragmentAdapter.addTab(getString(R.string.fujin), "dr", NearbyRoomFragment.class, getBundle());
        viewPageFragmentAdapter.addTab(getString(R.string.pindao), "ve", ChannelFragment.class, getBundle());
        pager.setAdapter(viewPageFragmentAdapter);
        pager.setOffscreenPageLimit(3);
        tabs.setViewPager(pager);
        tabs.setUnderlineColor(getResources().getColor(R.color.global));
        tabs.setDividerColor(getResources().getColor(R.color.global));
        tabs.setIndicatorColor(getResources().getColor(R.color.chose));
        tabs.setTextColor(Color.WHITE);
        tabs.setTextSize(15);
        tabs.setSelectedTextColor(getResources().getColor(R.color.chose));
        tabs.setIndicatorHeight(2);
        tabs.setZoomMax(0f);


        tabs.setPagerSlidingListen(new PagerSlidingInterface() {
            @Override
            public void onItemClick(View v, int currentPosition, int position) {

            }
        });

        pager.setCurrentItem(1);
        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                enableMsgNotification(false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ivHotPrivateChat.setOnClickListener(this);
        ivHotSearch.setOnClickListener(this);

    }

    private Bundle getBundle() {
        Bundle bundle = new Bundle();
        return bundle;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.unread_cover:
            case R.id.iv_hot_private_chat:
                //消息记录
                startActivity(new Intent(getActivity(), SessionListActivity.class));
                break;

            case R.id.iv_hot_search:
                //搜索
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
        }
    }

    /*设置网易云信消息未读等  开始*/

    /**
     * 注册未读消息数量观察者
     */
    private void registerMsgUnreadInfoObserver(boolean register) {
        if (register) {
            ReminderManager.getInstance().registerUnreadNumChangedCallback(this);
        } else {
            ReminderManager.getInstance().unregisterUnreadNumChangedCallback(this);
        }
    }

    /**
     * 未读消息数量观察者实现
     */
    @Override
    public void onUnreadNumChanged(ReminderItem item) {
//        MainTab tab = MainTab.fromReminderId(item.getId());
//        if (tab != null) {
//            tabs.updateTab(tab.tabIndex, item);
//        }
        int unread = NIMClient.getService(MsgService.class).getTotalUnreadCount();
        if (item == null || tabNewMsgLabel == null || tabNewIndicator == null) {
            if (tabNewMsgLabel != null)
                tabNewMsgLabel.setVisibility(View.GONE);
            if (tabNewIndicator != null)
                tabNewIndicator.setVisibility(View.GONE);
            return;
        }
//        int unread = item.unread();
        boolean indicator = item.indicator();
        tabNewMsgLabel.setVisibility(unread > 0 ? View.VISIBLE : View.GONE);
        tabNewIndicator.setVisibility(indicator ? View.VISIBLE : View.GONE);
        if (unread > 0) {
            tabNewMsgLabel.setText(String.valueOf(ReminderSettings.unreadMessageShowRule(unread)));
        }
    }

    /**
     * 注册/注销系统消息未读数变化
     *
     * @param register
     */
    private void registerSystemMessageObservers(boolean register) {
        NIMClient.getService(SystemMessageObserver.class).observeUnreadCountChange(sysMsgUnreadCountChangedObserver,
                register);
    }

    private Observer<Integer> sysMsgUnreadCountChangedObserver = new Observer<Integer>() {
        @Override
        public void onEvent(Integer unreadCount) {
            SystemMessageUnreadManager.getInstance().setSysMsgUnreadCount(unreadCount);
            ReminderManager.getInstance().updateContactUnreadNum(unreadCount);
        }
    };

    /**
     * 查询系统消息未读数
     */
    private void requestSystemMessageUnreadCount() {
        int unread = NIMClient.getService(SystemMessageService.class).querySystemMessageUnreadCountBlock();
        SystemMessageUnreadManager.getInstance().setSysMsgUnreadCount(unread);
        ReminderManager.getInstance().updateContactUnreadNum(unread);
    }

    /**
     * 初始化未读红点动画
     */
    private void initUnreadCover() {
        DropManager.getInstance().init(getContext(), unreadCover,
                new DropCover.IDropCompletedListener() {
                    @Override
                    public void onCompleted(Object id, boolean explosive) {
                        if (id == null || !explosive) {
                            return;
                        }

                        if (id instanceof RecentContact) {
                            RecentContact r = (RecentContact) id;
                            NIMClient.getService(MsgService.class).clearUnreadCount(r.getContactId(), r.getSessionType());
                            LogUtil.i("HomeFragment", "clearUnreadCount, sessionId=" + r.getContactId());
                        } else if (id instanceof String) {
                            if (((String) id).contentEquals("0")) {
                                List<RecentContact> recentContacts = NIMClient.getService(MsgService.class).queryRecentContactsBlock();
                                for (RecentContact r : recentContacts) {
                                    if (r.getUnreadCount() > 0) {
                                        NIMClient.getService(MsgService.class).clearUnreadCount(r.getContactId(), r.getSessionType());
                                    }
                                }
                                LogUtil.i("HomeFragment", "clearAllUnreadCount");
                            } else if (((String) id).contentEquals("1")) {
                                NIMClient.getService(SystemMessageService.class).resetSystemMessageUnreadCount();
                                LogUtil.i("HomeFragment", "clearAllSystemUnreadCount");
                            }
                        }
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        enableMsgNotification(false);
        //quitOtherActivities();
    }

    @Override
    public void onPause() {
        super.onPause();
        enableMsgNotification(true);
    }

    private void enableMsgNotification(boolean enable) {
        if (enable) {
            /**
             * 设置最近联系人的消息为已读
             *
             * @param account,    聊天对象帐号，或者以下两个值：
             *                    {@link #MSG_CHATTING_ACCOUNT_ALL} 目前没有与任何人对话，但能看到消息提醒（比如在消息列表界面），不需要在状态栏做消息通知
             *                    {@link #MSG_CHATTING_ACCOUNT_NONE} 目前没有与任何人对话，需要状态栏消息通知
             */
            NIMClient.getService(MsgService.class).setChattingAccount(MsgService.MSG_CHATTING_ACCOUNT_NONE, SessionTypeEnum.None);
        } else {
            NIMClient.getService(MsgService.class).setChattingAccount(MsgService.MSG_CHATTING_ACCOUNT_ALL, SessionTypeEnum.None);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        registerMsgUnreadInfoObserver(false);
        registerSystemMessageObservers(false);
    }
}
