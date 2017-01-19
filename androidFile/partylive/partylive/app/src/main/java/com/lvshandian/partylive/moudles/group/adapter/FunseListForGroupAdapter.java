package com.lvshandian.partylive.moudles.group.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.base.CommonAdapter;
import com.lvshandian.partylive.base.ViewHolder;
import com.lvshandian.partylive.moudles.mine.bean.FunseBean;
import com.lvshandian.partylive.utils.GrademipmapUtils;
import com.lvshandian.partylive.utils.LogUtils;
import com.lvshandian.partylive.utils.PicassoUtil;
import com.lvshandian.partylive.widget.CircleImageView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by gjj on 2016/12/6.
 */

/**
 * 新建高级群，邀请成员的列表，即我关注的列表适配
 *
 * @author sll
 * @time 2016/12/20 13:44
 */
public class FunseListForGroupAdapter extends CommonAdapter<FunseBean> {
    private boolean isFunse;
    // 用来控制CheckBox的选中状况
    private static HashMap<Integer, Boolean> isSelected;

    public FunseListForGroupAdapter(Context context, List<FunseBean> mDatas, int itemLayoutId, boolean isFunse) {
        super(context, mDatas, itemLayoutId);
        this.isFunse = isFunse;
        isSelected = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            getIsSelected().put(i, false);
        }
    }

    public void setDate(List<FunseBean> mDatas) {
        for (int i = 0; i < mDatas.size(); i++) {
            getIsSelected().put(i, false);
        }
    }

    @Override
    public void convert(ViewHolder helper, FunseBean item, final int position) {

        CircleImageView ivHeader = helper.getView(R.id.cv_userHead);
        TextView tvName = helper.getView(R.id.tv_item_uname);
        TextView tvSign = helper.getView(R.id.tv_item_usign);
        ImageView ivSex = helper.getView(R.id.tv_item_usex);
        ImageView tvLevel = helper.getView(R.id.tv_item_ulevel);
        CheckBox tvCheck = helper.getView(R.id.iv_item_check);

        String picUrl = item.getPicUrl();
        PicassoUtil.newInstance().onRoundnessImage(mContext, picUrl, ivHeader);

        String nickName = item.getNickName();
        tvName.setText(nickName);

        String signature = item.getSignature();
        tvSign.setText(signature);

        String gender = item.getGender();
        if (TextUtils.equals(gender, "1")) {
            ivSex.setImageResource(R.mipmap.male);
        } else {
            ivSex.setImageResource(R.mipmap.female);
        }

        String level = item.getLevel();
        tvLevel.setImageResource(GrademipmapUtils.LevelImg[Integer.valueOf(level) - 1]);
        tvCheck.setChecked(item.isChecked());
        tvCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemFollowClick(position, (CheckBox) v);
                }
            }
        });
//        tvCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                getIsSelected().put(position, isChecked);
//                LogUtils.i("选择关注的人:" + position + "/" + isChecked);
//            }
//        });
    }

    public static HashMap<Integer, Boolean> getIsSelected() {
        return isSelected;
    }

    public static void setIsSelected(HashMap<Integer, Boolean> isSelected) {
        FunseListForGroupAdapter.isSelected = isSelected;
    }

    private OnItemFollowsClickListener mListener;

    public void setOnItemFollowsClick(OnItemFollowsClickListener listener) {
        mListener = listener;
    }

    public interface OnItemFollowsClickListener {
        void onItemFollowClick(int position, CheckBox tvCheck);
    }
}


