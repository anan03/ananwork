package com.lvshandian.partylive.moudles.mine;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.bean.AppUser;
import com.lvshandian.partylive.interf.PagerSlidingInterface;
import com.lvshandian.partylive.interf.ResultListener;
import com.lvshandian.partylive.moudles.index.adapter.ViewPageFragmentAdapter;
import com.lvshandian.partylive.moudles.mine.bean.BuyMember;
import com.lvshandian.partylive.moudles.mine.fragment.MyFragment;
import com.lvshandian.partylive.moudles.mine.fragment.PhotoFragment;
import com.lvshandian.partylive.moudles.mine.fragment.SmallVideoFragment;
import com.lvshandian.partylive.moudles.mine.my.FollowListActivity;
import com.lvshandian.partylive.moudles.mine.my.FunseListActivity;
import com.lvshandian.partylive.moudles.start.RegisterUserActivity;
import com.lvshandian.partylive.utils.CacheUtils;
import com.lvshandian.partylive.utils.GrademipmapUtils;
import com.lvshandian.partylive.utils.GuanZhuUtils;
import com.lvshandian.partylive.utils.JsonUtil;
import com.lvshandian.partylive.utils.LogUtils;
import com.lvshandian.partylive.utils.PicassoUtil;
import com.lvshandian.partylive.wangyiyunxin.main.activity.SessionListActivity;
import com.lvshandian.partylive.wangyiyunxin.main.helper.SystemMessageUnreadManager;
import com.lvshandian.partylive.wangyiyunxin.main.reminder.ReminderItem;
import com.lvshandian.partylive.wangyiyunxin.main.reminder.ReminderManager;
import com.lvshandian.partylive.wangyiyunxin.main.reminder.ReminderSettings;
import com.lvshandian.partylive.widget.AvatarView;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 登录用户中心页面
 */
public class MyInformationFragment extends Fragment implements ReminderManager.UnreadNumChangedCallback {


    @Bind(R.id.my_iv_xiugai)
    ImageView myIvXiugai;
    @Bind(R.id.iv_private_chat_user)
    ImageView ivPrivateChatUser;
    @Bind(R.id.my_head)
    AvatarView myHead;
    @Bind(R.id.sex)
    ImageView sex;
    @Bind(R.id.denhgji)
    ImageView denhgji;
    @Bind(R.id.top)
    AutoRelativeLayout top;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.id)
    TextView id;
    @Bind(R.id.fanse)
    TextView fanse;
    @Bind(R.id.attention)
    TextView attention;
    @Bind(R.id.my_tabs)
    PagerSlidingTabStrip tabs;
    @Bind(R.id.my_vierpager)
    ViewPager pager;
    @Bind(R.id.tv_sign)
    TextView tvSign;
    @Bind(R.id.iv_hot_private_chat)
    AutoRelativeLayout ivHotPrivateChat;
    @Bind(R.id.tab_new_indicator)
    ImageView tabNewIndicator;
    @Bind(R.id.tab_new_msg_label)
    DropFake tabNewMsgLabel;
    private ViewPageFragmentAdapter viewPageFragmentAdapter;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_my_information, null);
            ButterKnife.bind(this, view);
            initView();
            initUser();
        } else {
            ButterKnife.bind(this, view);
            tabs.setViewPager(pager);
        }

        EventBus.getDefault().register(this);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        registerMsgUnreadInfoObserver(true);
        registerSystemMessageObservers(true);
        requestSystemMessageUnreadCount();
//        initUnreadCover();
    }

    /**
     * 回显用户信息
     */
    private void initUser() {
        final AppUser userInfo = (AppUser) CacheUtils.readObject(getActivity(), CacheUtils.USERINFO);

        if (userInfo != null) {
            GuanZhuUtils.newInstance().personInfo(getActivity(), userInfo.getId(), new ResultListener() {
                @Override
                public void onSucess(String data) {
                    LogUtils.e("用户信息: " + data);
                    AppUser user = JsonUtil.json2Bean(data, AppUser.class);
                    if (user != null) {
                        initUserInfo(user);
                        CacheUtils.saveObject(getActivity(), user, CacheUtils.USERINFO);
                    } else {
                        onFaild();
                    }
                }

                @Override
                public void onFaild() {
                    initUserInfo(userInfo);
                }
            });
        }
    }

    private void initUserInfo(AppUser userInfo) {
        if (userInfo != null) {
            String nickName = userInfo.getNickName();
            name.setText("昵称:" + nickName);

            String idInfo = userInfo.getId();
            id.setText("ID:" + idInfo);

            String vip = userInfo.getVip();
            if (TextUtils.equals(vip, "1")) {
                //是会员
                Drawable drawable = getResources().getDrawable(R.mipmap.vip_orange);
                id.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
            }

            String fansNum = userInfo.getFansNum();
            fanse.setText("粉丝:" + fansNum);
            String points = userInfo.getFollowNum();
            attention.setText("关注:" + points);
            String gender = userInfo.getGender();
            if (TextUtils.equals("0", gender)) {
                sex.setImageResource(R.mipmap.female);
            } else if (TextUtils.equals("1", gender)) {
                sex.setImageResource(R.mipmap.male);
            }

            String gradeSatisfied = userInfo.getLevel();
            denhgji.setImageResource(GrademipmapUtils.LevelImg[Integer.valueOf(gradeSatisfied) - 1]);

            String picUrl = userInfo.getPicUrl();
            LogUtils.e("头像: " + picUrl);
            PicassoUtil.newInstance().onRoundnessImage(getActivity(), picUrl, myHead);

            tvSign.setText(userInfo.getSignature());
        }
    }


    private void initView() {

        viewPageFragmentAdapter = new ViewPageFragmentAdapter(getFragmentManager(), pager);
        viewPageFragmentAdapter.addTab(getString(R.string.my), "my", MyFragment.class, getBundle());
        viewPageFragmentAdapter.addTab(getString(R.string.photo), "xc", PhotoFragment.class, getBundle());
        viewPageFragmentAdapter.addTab(getString(R.string.smallvideo), "dp", SmallVideoFragment.class, getBundle());
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

        pager.setCurrentItem(0);
        tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), RegisterUserActivity.class));
            }
        });
        myIvXiugai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), RegisterUserActivity.class));
            }
        });

        ivHotPrivateChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SessionListActivity.class));
            }
        });
        tabNewMsgLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SessionListActivity.class));
            }
        });

        fanse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUser appUser = (AppUser) CacheUtils.readObject(getActivity(), CacheUtils.USERINFO);
                Intent intent = new Intent(getActivity(), FunseListActivity.class);
                intent.putExtra(getString(R.string.visiti_person), appUser.getId());
                startActivity(intent);
            }
        });
        attention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppUser appUser = (AppUser) CacheUtils.readObject(getActivity(), CacheUtils.USERINFO);
                Intent intent = new Intent(getActivity(), FollowListActivity.class);
                intent.putExtra(getString(R.string.visiti_person), appUser.getId());
                startActivity(intent);
            }
        });

    }

    private Bundle getBundle() {
        Bundle bundle = new Bundle();
        return bundle;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
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
        DropManager.getInstance().init(getContext(), null,
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
    public void onDestroy() {
        super.onDestroy();
        registerMsgUnreadInfoObserver(false);
        registerSystemMessageObservers(false);

    }


    @Subscribe
    public void onEventMainThread(BuyMember member) {
        initUser();
    }
}
