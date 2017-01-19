package com.lvshandian.partylive.moudles.mine.my.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.bean.RoomUserBean;
import com.lvshandian.partylive.utils.GrademipmapUtils;
import com.lvshandian.partylive.utils.PicassoUtil;
import com.lvshandian.partylive.widget.AvatarView;

import java.util.List;

/**
 * Created by sll on 2016/12/2.
 */

public class RoomUsersDataAdapter extends RecyclerView.Adapter<RoomUserHolder> {
    private LayoutInflater mInflator;
    private Context context;
    private List<RoomUserBean> mDatas;

    public RoomUsersDataAdapter(Context context, List<RoomUserBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        mInflator = LayoutInflater.from(context);
    }


    @Override
    public RoomUserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = mInflator.inflate(R.layout.item_room_user_head, parent, false);
        return new RoomUserHolder(inflate);
    }

    @Override
    public void onBindViewHolder(RoomUserHolder holder, final int position) {
        RoomUserBean bean = mDatas.get(position);
        ImageView ivHeader = holder.getIvHeader();
//        ivHeader.setAvatarUrl(bean.getPicUrl());
        PicassoUtil.newInstance().onRoundnessImage(context, bean.getPicUrl(), ivHeader);

        View itemView = holder.getItemView();
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v, position);
                }
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mLongClickListener != null) {
                    mLongClickListener.onItemLongClickListener(v, position);
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }


    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    private OnItemLongClickListener mLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        mLongClickListener = longClickListener;
    }
}

class RoomUserHolder extends RecyclerView.ViewHolder {


    public ImageView getIvHeader() {
        return ivHeader;
    }

    private ImageView ivHeader;

    public View getItemView() {
        return itemView;
    }

    private View itemView;

    public RoomUserHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        ivHeader = (ImageView) itemView.findViewById(R.id.room_user_header);
    }
}
