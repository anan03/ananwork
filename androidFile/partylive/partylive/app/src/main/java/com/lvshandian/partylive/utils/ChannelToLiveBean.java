package com.lvshandian.partylive.utils;

import com.lvshandian.partylive.bean.ChannelBean;
import com.lvshandian.partylive.bean.LiveBean;
import com.lvshandian.partylive.bean.LiveFamilyMemberBean;
import com.lvshandian.partylive.bean.RowsBean;

/**
 * Created by sll on 2016/12/22.
 */

/**
 * 将频道等里的网络请求返回类转成liveBean
 *
 * @author sll
 * @time 2016/12/22 16:53
 */
public class ChannelToLiveBean {
    /**
     * 根据频道的实体类，获取liveBean用于跳转到watchLive
     *
     * @author sll
     * @time 2016/12/22 11:18
     */
    public static LiveBean getLiveBeanFromUsersListBean(ChannelBean.UsersListBean usersListBean) {
        LiveBean liveBean = new LiveBean();
        LiveBean.CreatorBean creatorBean = new LiveBean.CreatorBean();

        creatorBean.setId(usersListBean.getId());
        creatorBean.setNickName(usersListBean.getNickName());
        creatorBean.setPicUrl(usersListBean.getPicUrl());
        liveBean.setCreator(creatorBean);

        if (usersListBean.getRooms() != null) {
            liveBean.setId(usersListBean.getRooms().getId());
            liveBean.setRoomId(usersListBean.getRooms().getRoomId());
            liveBean.setCity(usersListBean.getRooms().getCity());
            liveBean.setPrivateFlag(usersListBean.getRooms().getPrivateFlag());
            liveBean.setRoomPay(usersListBean.getRooms().getRoomPay());
            liveBean.setRoomPw(usersListBean.getRooms().getRoomPw());
            try {
                liveBean.setBroadcastUrl(DESUtil.encrypt(usersListBean.getRooms().getBroadcastUrl()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            liveBean.setLivePicUrl(usersListBean.getRooms().getLivePicUrl());
            liveBean.setName(usersListBean.getRooms().getName());
            liveBean.setOnlineUserNum(usersListBean.getRooms().getOnlineUserNum());
            liveBean.setPayForChat(usersListBean.getRooms().getPayForChat());
            liveBean.setStatus(usersListBean.getRooms().getStatus());
        }
        return liveBean;
    }

    /**
     * 根据频道的实体类，获取liveBean用于跳转到watchLive
     *
     * @author sll
     * @time 2016/12/22 11:18
     */
    public static LiveBean getLiveBeanFromRowsBean(RowsBean row) {
        LiveBean liveBean = new LiveBean();
        LiveBean.CreatorBean creatorBean = new LiveBean.CreatorBean();

        creatorBean.setId(row.getId());
        creatorBean.setNickName(row.getNickName());
        creatorBean.setPicUrl(row.getPicUrl());
        liveBean.setCreator(creatorBean);

        if (row.getRooms() != null) {
            liveBean.setId(row.getRooms().getId());
            liveBean.setRoomId(row.getRooms().getRoomId());
            liveBean.setCity(row.getRooms().getCity());
            liveBean.setPrivateFlag(row.getRooms().getPrivateFlag());
            liveBean.setRoomPay(row.getRooms().getRoomPay());
            liveBean.setRoomPw(row.getRooms().getRoomPw());
            try {
                liveBean.setBroadcastUrl(DESUtil.encrypt(row.getRooms().getBroadcastUrl()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            liveBean.setLivePicUrl(row.getRooms().getLivePicUrl());
            liveBean.setName(row.getRooms().getName());
            liveBean.setOnlineUserNum(row.getRooms().getOnlineUserNum());
            liveBean.setPayForChat(row.getRooms().getPayForChat());
            liveBean.setStatus(row.getRooms().getStatus());
        }
        return liveBean;
    }

    /**
     * 根据频道的实体类，获取liveBean用于跳转到watchLive
     *
     * @author sll
     * @time 2016/12/22 11:18
     */
    public static LiveBean getLiveBeanFromObj(LiveFamilyMemberBean.ObjBean obj) {
        LiveBean liveBean = new LiveBean();
        LiveBean.CreatorBean creatorBean = new LiveBean.CreatorBean();

        creatorBean.setId(obj.getId());
        creatorBean.setNickName(obj.getNickName());
        creatorBean.setPicUrl(obj.getPicUrl());
        liveBean.setCreator(creatorBean);

        liveBean.setId(obj.getId());
        if (obj.getRooms() != null) {
            liveBean.setRoomId(obj.getRooms().getRoomId());
            liveBean.setCity(obj.getRooms().getCity());
            liveBean.setPrivateFlag(obj.getRooms().getPrivateFlag());
            liveBean.setRoomPay(obj.getRooms().getRoomPay());
            liveBean.setRoomPw(obj.getRooms().getRoomPw());
            //加密
            try {
                liveBean.setBroadcastUrl(DESUtil.encrypt(obj.getRooms().getBroadcastUrl()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            liveBean.setLivePicUrl(obj.getRooms().getLivePicUrl());
            liveBean.setName(obj.getRooms().getName());
            liveBean.setOnlineUserNum(obj.getRooms().getOnlineUserNum());
            liveBean.setPayForChat(obj.getRooms().getPayForChat());
            liveBean.setStatus(obj.getRooms().getStatus());
        }
        return liveBean;
    }
}
