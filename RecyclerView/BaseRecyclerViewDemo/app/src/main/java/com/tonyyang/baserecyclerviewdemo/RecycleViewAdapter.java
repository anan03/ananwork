package com.tonyyang.baserecyclerviewdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhang on 2017/2/7.
 */

public class RecycleViewAdapter extends RecyclerView.Adapter {
    private List<String> mData;
    private Context mContext;
    private LinearViewHolder lholder;
    private GridViewHolder gholder;
    private StaggeredViewHolder sholder;
    private SpecialViewHolder specialHolder;
    private BaseRecycleView.OnItemTouchListener mListener;
    private Map<Integer, Integer> staggeredData = new HashMap<>();

    public RecycleViewAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<String> data) {
        mData = data;
    }

    public void setListener(BaseRecycleView.OnItemTouchListener listener) {
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BaseRecycleView.TYPE_NORMAL) {
            //普通item 即listview
            lholder = new LinearViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_linear_view, parent, false));
            return lholder;
        } else if (viewType == BaseRecycleView.TYPE_GRID) {
            // 网格布局即gridView
            gholder = new GridViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_grid_view, parent, false));
            return gholder;
        } else if (viewType == BaseRecycleView.TYPE_STAGGERED) {
            // 瀑布流布局
            sholder = new StaggeredViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_staggered_view, parent, false));
            return sholder;
        } else {
            // 其他布局
            specialHolder = new SpecialViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_special_view, parent, false));
            return specialHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LinearViewHolder) {
            lholder = (LinearViewHolder) holder;
            lholder.position = position;
            lholder.txt.setText(mData.get(position));
        } else if (holder instanceof GridViewHolder) {
            gholder = (GridViewHolder) holder;
            gholder.position = position;
            gholder.txt.setText(mData.get(position));
        } else if (holder instanceof StaggeredViewHolder) {
            sholder = (StaggeredViewHolder) holder;
            sholder.position = position;
            sholder.txt.setText(mData.get(position));
            if (!staggeredData.containsKey(position))
                staggeredData.put(position, (int) (Math.random() * 100 + 101));//记住高度
            sholder.txt.setMinHeight(staggeredData.get(position));
        } else if (holder instanceof SpecialViewHolder) {
            specialHolder = (SpecialViewHolder) holder;
            specialHolder.position = position;
            specialHolder.txt.setText("特殊view " + mData.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    /**
     * 普通布局Holder
     */
    private class LinearViewHolder extends BaseRecycleView.BaseViewHolder {

        private TextView txt;

        public LinearViewHolder(View itemView) {
            super(itemView, mListener);
            txt = (TextView) itemView.findViewById(R.id.txt);
        }
    }

    /**
     * 网格布局Holder
     */
    private class GridViewHolder extends BaseRecycleView.BaseViewHolder {

        private TextView txt;

        public GridViewHolder(View itemView) {
            super(itemView, mListener);
            txt = (TextView) itemView.findViewById(R.id.txt);
        }
    }

    /**
     * 瀑布流布局Holder
     */
    private class StaggeredViewHolder extends BaseRecycleView.BaseViewHolder {

        private TextView txt;

        public StaggeredViewHolder(View itemView) {
            super(itemView, mListener);
            txt = (TextView) itemView.findViewById(R.id.txt);
        }
    }

    /**
     * 其他布局Holder
     */
    private class SpecialViewHolder extends BaseRecycleView.BaseViewHolder {

        private TextView txt;

        public SpecialViewHolder(View itemView) {
            super(itemView, mListener);
            txt = (TextView) itemView.findViewById(R.id.txt);
        }
    }
}
