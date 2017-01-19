package com.lvshandian.partylive.moudles.index.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.base.BaseActivity;
import com.lvshandian.partylive.bean.ChannelBean;
import com.lvshandian.partylive.bean.LiveBean;
import com.lvshandian.partylive.moudles.index.activity.ChannelMoreActivity;
import com.lvshandian.partylive.utils.ChannelToLiveBean;
import com.lvshandian.partylive.utils.LogUtils;
import com.lvshandian.partylive.widget.LoadUrlImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;

/**
 * Created by sll on 2016/12/20.
 */

/**
 * 附近的人的适配
 *
 * @author sll
 * @time 2016/12/20 21:49
 */
public class ChannelAdapter extends RecyclerView.Adapter<ChannelAdapter.ChannelViewHolder> {
    private List<ChannelBean> mData;
    private Context mContext;

    public ChannelAdapter(Context context, List<ChannelBean> data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public ChannelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ChannelViewHolder holder = new ChannelViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_room_channel, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ChannelViewHolder holder, int position) {
        if (mData.size() > 0) {
            final ChannelBean item = mData.get(position);
            holder.channelName.setText(item.getChannelName());
            holder.channelMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转该频道更多
                    //channelId, channelName
                    mContext.startActivity(new Intent(mContext, ChannelMoreActivity.class).putExtra("channelName", item.getChannelName()).putExtra("channelId", item.getId()));
                }
            });

            List<ChannelBean.UsersListBean> usersList = item.getUsersList();
            if (usersList != null && usersList.size() > 0) {
                final ChannelBean.UsersListBean usersBean = usersList.get(0);
                //该频道存在第一个room
                holder.roomPic_1.setImageLoadUrl(usersBean.getPicUrl());
                holder.roomName_1.setText(usersBean.getNickName());
                String roomOnlineUserNum = "0";
                if (!TextUtils.isEmpty(usersBean.getRooms().getOnlineUserNum()))
                    roomOnlineUserNum = usersBean.getRooms().getOnlineUserNum();
                holder.roomOnlinenum_1.setText(roomOnlineUserNum + "人");
                holder.roomPic_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((BaseActivity) mContext).ifEnter(ChannelToLiveBean.getLiveBeanFromUsersListBean(usersBean));
                    }
                });
            }
            if (usersList != null && usersList.size() > 1) {
                final ChannelBean.UsersListBean usersBean = usersList.get(1);
                //该频道存在第一个room
                holder.roomPic_2.setImageLoadUrl(usersBean.getPicUrl());
                holder.roomName_2.setText(usersBean.getNickName());
                String roomOnlineUserNum = "0";
                if (!TextUtils.isEmpty(usersBean.getRooms().getOnlineUserNum()))
                    roomOnlineUserNum = usersBean.getRooms().getOnlineUserNum();
                holder.roomOnlinenum_2.setText(roomOnlineUserNum + "人");
                holder.roomPic_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((BaseActivity) mContext).ifEnter(ChannelToLiveBean.getLiveBeanFromUsersListBean(usersBean));
                    }
                });
            } else {
                holder.room2.setVisibility(View.INVISIBLE);
            }

        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ChannelViewHolder extends RecyclerView.ViewHolder {
        public LoadUrlImageView roomPic_1, roomPic_2;
        public TextView channelName, channelMore, roomName_1, roomName_2, roomOnlinenum_1, roomOnlinenum_2;
        public AutoRelativeLayout room2;

        public ChannelViewHolder(View itemView) {
            super(itemView);
            channelName = (TextView) itemView.findViewById(R.id.channel_name);
            channelMore = (TextView) itemView.findViewById(R.id.channel_more);
            roomPic_1 = (LoadUrlImageView) itemView.findViewById(R.id.im_room_pic_1);
            roomPic_2 = (LoadUrlImageView) itemView.findViewById(R.id.im_room_pic_2);
            roomName_1 = (TextView) itemView.findViewById(R.id.im_room_name_1);
            roomName_2 = (TextView) itemView.findViewById(R.id.im_room_name_2);
            roomOnlinenum_1 = (TextView) itemView.findViewById(R.id.im_room_onlinenum_1);
            roomOnlinenum_2 = (TextView) itemView.findViewById(R.id.im_room_onlinenum_2);
            room2 = (AutoRelativeLayout) itemView.findViewById(R.id.im_room_2);
        }
    }
}
