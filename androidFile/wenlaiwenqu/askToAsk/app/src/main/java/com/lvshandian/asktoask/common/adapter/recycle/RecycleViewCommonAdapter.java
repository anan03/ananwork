package com.lvshandian.asktoask.common.adapter.recycle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class RecycleViewCommonAdapter<T> extends RecyclerView.Adapter<RecycleViewHolder> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;

    private OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public RecycleViewCommonAdapter(Context context, int layoutId) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = new ArrayList<>();
        //setHasStableIds(true);
    }

    @Override
    public RecycleViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        RecycleViewHolder viewHolder = RecycleViewHolder.get(mContext, null, parent, mLayoutId, -1);
        setListener(parent, viewHolder, viewType);
        return viewHolder;
    }

    protected int getPosition(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition();
    }

    protected boolean isEnabled(int viewType) {
        return true;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    protected void setListener(final ViewGroup parent, final RecycleViewHolder viewHolder, int viewType) {
        if (!isEnabled(viewType)) return;
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = getPosition(viewHolder);
                    if (position < 0 || position > mDatas.size()) {
                        return;
                    }
                    mOnItemClickListener.onItemClick(parent, v, mDatas.get(position), position);
                }
            }
        });


        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = getPosition(viewHolder);
                    return mOnItemClickListener.onItemLongClick(parent, v, mDatas.get(position), position);
                }
                return false;
            }
        });
    }

    @Override
    public void onBindViewHolder(RecycleViewHolder holder, int position) {
        holder.updatePosition(position);
        convert(holder, mDatas.get(position));
    }

    public abstract void convert(RecycleViewHolder holder, T t);

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    public void addAll(Collection<? extends T> list) {
        if (list != null) {
            int size = mDatas.size();
            mDatas.addAll(list);
            notifyItemRangeChanged(size, list.size());
        }
    }

    public void clear() {
        int size = mDatas.size();
        mDatas.clear();
        notifyItemRangeRemoved(0, size);
    }


    public List<T> getmDatas() {
        return mDatas;
    }
}
