package com.lvshandian.partylive.moudles.mine.my.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.moudles.mine.bean.GongXianBean;
import com.lvshandian.partylive.utils.PicassoUtil;

import java.util.List;


/**
 * Created by gjj on 2016/11/29.
 * 贡献榜RecyclerView的adapter
 */

public class GongXianAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private List<GongXianBean> mDatas;
    private Context mContext;
    private LayoutInflater mInflator;

    public GongXianAdapter(Context context, List<GongXianBean> datas) {
        mContext = context;
        mDatas = datas;
        mInflator = LayoutInflater.from(mContext);
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = mInflator.inflate(R.layout.item_gong_xian_list, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        GongXianBean bean = mDatas.get(position);

        String nickName = bean.getNickName();
        holder.getTvName().setText(nickName);

        String investment = bean.getInvestment();
        holder.getTvGongXian().setText(investment);

        String picUrl = bean.getPicUrl();
        PicassoUtil.newInstance().onRoundnessImage(mContext, picUrl, holder.getIvHeader());

        holder.getTvNo().setText("No." + (position + 1));
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {

    private ImageView ivHeader;
    private TextView tvName;
    private TextView tvGongXian;
    private TextView tvNo;

    public ImageView getIvHeader() {
        return ivHeader;
    }

    public TextView getTvName() {
        return tvName;
    }

    public TextView getTvGongXian() {
        return tvGongXian;
    }

    public TextView getTvNo() {
        return tvNo;
    }

    public MyViewHolder(View itemView) {
        super(itemView);
        ivHeader = (ImageView) itemView.findViewById(R.id.iv_header);
        tvName = (TextView) itemView.findViewById(R.id.tv_name);
        tvGongXian = (TextView) itemView.findViewById(R.id.tv_gong_xian);
        tvNo = (TextView) itemView.findViewById(R.id.tv_no);
    }
}
