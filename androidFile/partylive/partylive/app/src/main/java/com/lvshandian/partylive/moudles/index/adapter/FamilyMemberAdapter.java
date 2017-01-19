package com.lvshandian.partylive.moudles.index.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.bean.LiveBean;
import com.lvshandian.partylive.moudles.index.live.WatchLiveActivity;
import com.lvshandian.partylive.utils.LogUtils;
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
public class FamilyMemberAdapter extends RecyclerView.Adapter<FamilyMemberAdapter.FamilyMemberViewHolder> {
    private List<LiveBean> mData;
    private Context mContext;
    private String creatorId;
    int tag = 0;

    public FamilyMemberAdapter(int tag, Context context, List data, String creatorId) {
        this.mContext = context;
        this.mData = data;
        this.tag = tag;
        this.creatorId = creatorId;
    }

    @Override
    public FamilyMemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FamilyMemberViewHolder holder = new FamilyMemberViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_live_family, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(FamilyMemberViewHolder holder, final int position) {
        if (mData.size() > 0) {
            LiveBean user = mData.get(position);
            holder.userNick.setText(user.getCreator().getNickName());
            holder.userHead.setAvatarUrl(user.getCreator().getPicUrl());
//            holder.userHead.setFocusable(false);
            holder.userHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LogUtils.i("holder.userHead.setOnClickListener");
                    if (!creatorId.equals(mData.get(position).getCreator().getId())) {
                        if (tag == 1) {
                            ((WatchLiveActivity) mContext).videoPlayEnd();
                            ((WatchLiveActivity) mContext).ifEnter(mData.get(position));
                        }
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class FamilyMemberViewHolder extends RecyclerView.ViewHolder {
        public TextView userNick;
        public AvatarView userHead;

        public FamilyMemberViewHolder(View itemView) {
            super(itemView);
            userNick = (TextView) itemView.findViewById(R.id.il_live_family_nick);
            userHead = (AvatarView) itemView.findViewById(R.id.il_live_family_head);
        }
    }
}
