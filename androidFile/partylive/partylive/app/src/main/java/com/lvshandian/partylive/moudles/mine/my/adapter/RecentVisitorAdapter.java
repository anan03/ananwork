package com.lvshandian.partylive.moudles.mine.my.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.moudles.mine.bean.RecentVisitorBean;
import com.lvshandian.partylive.utils.GrademipmapUtils;
import com.lvshandian.partylive.utils.PicassoUtil;

import java.util.List;

/**
 * Created by gjj on 2016/11/23.
 * 最近访客列表适配器
 */

public class RecentVisitorAdapter extends RecyclerView.Adapter<VisitorHolder> {


    private LayoutInflater mInflator;
    private Context context;
    private List<RecentVisitorBean> mDatas;

    public RecentVisitorAdapter(Context context, List<RecentVisitorBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
        mInflator = LayoutInflater.from(context);
    }


    @Override
    public VisitorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = mInflator.inflate(R.layout.item_recent_visitor, parent, false);
        return new VisitorHolder(inflate);
    }

    @Override
    public void onBindViewHolder(VisitorHolder holder, final int position) {
        RecentVisitorBean bean = mDatas.get(position);
        ImageView ivHeader = holder.getIvHeader();
        TextView tvName = holder.getTvName();
        TextView tvSign = holder.getTvSign();
        ImageView ivGrade = holder.getIvGrade();

        PicassoUtil.newInstance().onRoundnessImage(context, bean.getPicUrl(), ivHeader);
        tvName.setText(bean.getNickName());
        tvSign.setText(bean.getSignature());

        String level = bean.getLevel();
        ivGrade.setImageResource(GrademipmapUtils.LevelImg[Integer.valueOf(level) - 1]);

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

class VisitorHolder extends RecyclerView.ViewHolder {


    public ImageView getIvGrade() {
        return ivGrade;
    }

    private ImageView ivGrade;

    public TextView getTvSign() {
        return tvSign;
    }

    public TextView getTvName() {
        return tvName;
    }

    public ImageView getIvHeader() {
        return ivHeader;
    }

    private TextView tvSign;
    private TextView tvName;
    private ImageView ivHeader;

    public View getItemView() {
        return itemView;
    }

    private View itemView;

    public VisitorHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        ivHeader = (ImageView) itemView.findViewById(R.id.iv_header);
        tvName = (TextView) itemView.findViewById(R.id.tv_name);
        tvSign = (TextView) itemView.findViewById(R.id.tv_sign);
        ivGrade = (ImageView) itemView.findViewById(R.id.iv_grade);
    }
}
