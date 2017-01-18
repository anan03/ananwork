package com.lvshandian.asktoask.module.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.adapter.CommonAdapter;
import com.lvshandian.asktoask.common.adapter.ViewHolder;
import com.lvshandian.asktoask.entry.DataUseQuiz;
import com.lvshandian.asktoask.module.user.activity.QuzaDetailsActivity;
import com.lvshandian.asktoask.utils.DateUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * 创建我的模块提问适配器
 */
public class UserQuzaAdapter extends CommonAdapter<DataUseQuiz.DataBean> {
    Context context;

    public UserQuzaAdapter(Context context, List<DataUseQuiz.DataBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.context = context;
    }

    @Override
    public void convert(ViewHolder helper, DataUseQuiz.DataBean item, int position) {
        //问题时间
        helper.setText(R.id.tv_time_hudong, DateUtil.timesOne(item.getQuestionPublishDate()));
        //问题标题
        helper.setText(R.id.tv_title_hudong, item.getQuestionTitle());

        //类型比如：干货
        helper.setText(R.id.tv_type_hudong, item.getQuestionType());

        //赏金
        helper.setText(R.id.tv_price_hudong, "￥" + item.getQuestionMoney());
        helper.getView(R.id.ll_collectdetails).setOnClickListener(new MyOnclickListener(item));


    }

    class MyOnclickListener implements View.OnClickListener {

        private DataUseQuiz.DataBean item;

        public MyOnclickListener(DataUseQuiz.DataBean item) {

            this.item = item;
        }

        @Override
        public void onClick(View v) {


            switch (v.getId()) {
                /**
                 * 跳转到提问题详情界面
                 */
                case R.id.ll_collectdetails:

                    Intent intent = new Intent(context, QuzaDetailsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("item", item);
                    context.startActivity(intent);
                    break;
            }

        }
    }
}
