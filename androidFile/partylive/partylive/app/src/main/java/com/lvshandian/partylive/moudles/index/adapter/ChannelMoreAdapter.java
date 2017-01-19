package com.lvshandian.partylive.moudles.index.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.bean.LiveBean;
import com.lvshandian.partylive.moudles.nearby.bean.ContentBean;
import com.lvshandian.partylive.widget.AvatarView;
import com.lvshandian.partylive.widget.LoadUrlImageView;

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
public class ChannelMoreAdapter extends RecyclerView.Adapter<ChannelMoreAdapter.ChannelMoreViewHolder> {
    private List<LiveBean> mData;
    private Context mContext;

    public ChannelMoreAdapter(Context context, List data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public ChannelMoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ChannelMoreViewHolder holder = new ChannelMoreViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_hall_live, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ChannelMoreViewHolder holder, int position) {
        if (mData.size() > 0) {
//            ContentBean item = mData.get(position);
//            holder.userPic.setImageLoadUrl(item.getPicUrl());
//            String gender = item.getGender();
//            if (TextUtils.equals(gender, "1")) {
//                holder.userSex.setImageResource(R.mipmap.male);
//            } else {
//                holder.userSex.setImageResource(R.mipmap.female);
//            }
//            String nickName = item.getNickName();
//            holder.userName.setText(nickName);
//            String level = item.getLevel();
//            holder.userLevel.setImageResource(GrademipmapUtils.LevelImg[Integer.valueOf(level) - 1]);
            LiveBean user = mData.get(position);

            holder.mUserNick.setText(user.getCreator().getNickName());
            holder.mUserLocal.setText(user.getCity());
            holder.mUserPic.setImageLoadUrl(user.getLivePicUrl());
            holder.mUserHead.setAvatarUrl(user.getCreator().getPicUrl());
            holder.mUserNums.setText(String.valueOf(user.getOnlineUserNum()));
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ChannelMoreViewHolder extends RecyclerView.ViewHolder {
        public TextView mUserNick, mUserLocal, mUserNums, mRoomTitle;
        public LoadUrlImageView mUserPic;
        public AvatarView mUserHead;

        public ChannelMoreViewHolder(View itemView) {
            super(itemView);
            mUserNick = (TextView) itemView.findViewById(R.id.tv_live_nick);
            mUserLocal = (TextView) itemView.findViewById(R.id.tv_live_local);
            mUserNums = (TextView) itemView.findViewById(R.id.tv_live_usernum);
            mUserHead = (AvatarView) itemView.findViewById(R.id.iv_live_user_head);
            mUserPic = (LoadUrlImageView) itemView.findViewById(R.id.iv_live_user_pic);
        }
    }
}
