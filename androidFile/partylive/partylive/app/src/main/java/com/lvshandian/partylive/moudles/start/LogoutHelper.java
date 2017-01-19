package com.lvshandian.partylive.moudles.start;

import com.lvshandian.partylive.wangyiyunxin.chatroom.helper.ChatRoomHelper;
import com.lvshandian.partylive.wangyiyunxin.config.DemoCache;
import com.netease.nim.uikit.LoginSyncDataStatusObserver;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.common.ui.drop.DropManager;

/**
 * 注销帮助类
 * Created by huangjun on 2015/10/8.
 */
public class LogoutHelper {
    public static void logout() {
        // 清理缓存&注销监听&清除状态
        NimUIKit.clearCache();
        ChatRoomHelper.logout();
        DemoCache.clear();
        LoginSyncDataStatusObserver.getInstance().reset();
        //未读消息红点
//        DropManager.getInstance().destroy();
    }
}
