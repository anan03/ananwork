package com.lvshandian.asktoask.module.user.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.adapter.CommonAdapter;
import com.lvshandian.asktoask.common.adapter.ViewHolder;
import com.lvshandian.asktoask.entry.UserAnswerInfo;
import com.lvshandian.asktoask.utils.DateUtil;
import com.lvshandian.asktoask.utils.TextUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by zhang on 2016/10/5.
 * 创建回答详情适配器；
 */
public class UserAnswerInfoAdapter extends CommonAdapter<UserAnswerInfo.DataBean> {
    Context context;

    public UserAnswerInfoAdapter(Context context, List<UserAnswerInfo.DataBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.context = context;
    }

    @Override
    public void convert(ViewHolder helper, UserAnswerInfo.DataBean item, int position) {
        ImageLoader.getInstance().displayImage(item.getUserHeadImg(), (ImageView) helper.getView(R.id.iv_hudong_type_detail));
        ImageView iv=helper.getView(R.id.iv_hudong_detail_sex);
        if (!TextUtils.isEmpty(item.getUserSex())) {
            iv.setVisibility(View.VISIBLE);
            if (item.getUserSex().equals("男")) {
                helper.setImageResource(R.id.iv_hudong_detail_sex, R.drawable.sex_men);

            } else if (item.getUserSex().equals("女")) {
                helper.setImageResource(R.id.iv_hudong_detail_sex, R.drawable.sex_women);
            }
        }else{
            iv.setVisibility(View.INVISIBLE);
        }


        //姓名
        helper.setText(R.id.tv_hudong_type_username, item.getUserRealName());
        //学校
        helper.setText(R.id.tv_hudong_detail_label_school, item.getUserSchool() + "");
        //专业
        helper.setText(R.id.tv_hudong_detail_label_major, item.getUserMajor() + "");
        //年级
        helper.setText(R.id.tv_hudong_detail_label_grade, item.getUserGrade() + "");
        //发表内容
        helper.setText(R.id.tv_title_hudong_detail, item.getAnswerData() + "");
        //时间
        helper.setText(R.id.tv_time_hudong_detail, DateUtil.timesOne(item.getAnswerDate()) + "");
        //收藏个数
//        helper.setText(R.id.tv_hudong_detail_collect_num, item.getQuestionCollection() + "");
        //点赞个数
//        helper.setText(R.id.tv_praise_num, item.getQuestionPraise() + "");
//        //赏金
//        helper.setText(R.id.tv_hudong_detail_price, item.getQuestionPraise() + "");

    }
}
