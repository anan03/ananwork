package com.lvshandian.partylive.moudles.nearby.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.moudles.nearby.bean.ContentBean;
import com.lvshandian.partylive.utils.GrademipmapUtils;
import com.lvshandian.partylive.widget.LoadUrlImageView;

import java.util.List;

import butterknife.Bind;

/**
 * Created by sll on 2016/12/20.
 */

/**
 * 附近的人的适配
 *
 * @author sll
 * @time 2016/12/20 21:49
 */
public class UserNearbyAdapter extends RecyclerView.Adapter<UserNearbyAdapter.UserNearbuViewHolder> {
    private List<ContentBean> mData;
    private Context mContext;

    public UserNearbyAdapter(Context context, List data) {
        this.mContext = context;
        this.mData = data;
    }

    @Override
    public UserNearbuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        UserNearbuViewHolder holder = new UserNearbuViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_user_nearby, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(UserNearbuViewHolder holder, int position) {
        if (mData.size() > 0) {
            ContentBean item = mData.get(position);
            holder.userPic.setImageLoadUrl(item.getPicUrl());
            String gender = item.getGender();
            if (TextUtils.equals(gender, "1")) {
                holder.userSex.setImageResource(R.mipmap.male);
            } else {
                holder.userSex.setImageResource(R.mipmap.female);
            }
            String nickName = item.getNickName();
            holder.userName.setText(nickName);
            String level = item.getLevel();
            holder.userLevel.setImageResource(GrademipmapUtils.LevelImg[Integer.valueOf(level) - 1]);
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class UserNearbuViewHolder extends RecyclerView.ViewHolder {
        public LoadUrlImageView userPic;
        public ImageView userSex, userLevel;
        public TextView userName;

        public UserNearbuViewHolder(View itemView) {
            super(itemView);
            userPic = (LoadUrlImageView) itemView.findViewById(R.id.un_user_pic);
            userSex = (ImageView) itemView.findViewById(R.id.un_user_sex);
            userLevel = (ImageView) itemView.findViewById(R.id.un_user_level);
            userName = (TextView) itemView.findViewById(R.id.un_user_nick);
        }
    }
}
