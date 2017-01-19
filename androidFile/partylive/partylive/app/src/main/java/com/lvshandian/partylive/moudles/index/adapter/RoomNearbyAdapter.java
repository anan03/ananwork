package com.lvshandian.partylive.moudles.index.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.bean.LiveBean;
import com.lvshandian.partylive.utils.GrademipmapUtils;
import com.lvshandian.partylive.widget.LoadUrlImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

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
public class RoomNearbyAdapter extends RecyclerView.Adapter<RoomNearbyAdapter.RoomNearbuViewHolder> {
    private List<LiveBean> mData;
    private Context mContext;

    public RoomNearbyAdapter(Context context, List data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public RoomNearbuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RoomNearbuViewHolder holder = new RoomNearbuViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_room_nearby, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RoomNearbuViewHolder holder, int position) {
        if (mData.size() > 0) {
            LiveBean item = mData.get(position);
            holder.userPic.setImageLoadUrl(item.getLivePicUrl());
            String nickName = item.getCreator().getNickName();
            holder.userName.setText(nickName);
            String roomDistance = "0.01";
            String roomOnlineUserNum = "0";
            if (!TextUtils.isEmpty(item.getRoomDistance()))
                roomDistance = item.getRoomDistance();
            holder.roomDistance.setText(roomDistance + "km");
            if (!TextUtils.isEmpty(item.getOnlineUserNum()))
                roomOnlineUserNum = item.getOnlineUserNum();
            holder.roomOnlineUserNum.setText(roomOnlineUserNum + "人");
            ImageLoader.getInstance().displayImage(item.getLivePicUrl(), holder.userPic);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class RoomNearbuViewHolder extends RecyclerView.ViewHolder {
        public LoadUrlImageView userPic;
        public TextView userName, roomDistance, roomOnlineUserNum;

        public RoomNearbuViewHolder(View itemView) {
            super(itemView);
            userPic = (LoadUrlImageView) itemView.findViewById(R.id.um_user_pic);
            userName = (TextView) itemView.findViewById(R.id.um_user_nick);
            roomDistance = (TextView) itemView.findViewById(R.id.um_room_distance);
            roomOnlineUserNum = (TextView) itemView.findViewById(R.id.um_room_onlineUserNum);
        }
    }
}
