package com.lvshandian.asktoask.module.interaction.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.common.adapter.CommonAdapter;
import com.lvshandian.asktoask.common.adapter.ViewHolder;
import com.lvshandian.asktoask.entry.HuDonglPicType;
import com.lvshandian.asktoask.entry.User;
import com.lvshandian.asktoask.utils.DateUtil;
import com.lvshandian.asktoask.utils.L;
import com.lvshandian.asktoask.utils.TextUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sina.weibo.sdk.api.TextObject;

import java.util.List;

/**
 * 互动问题类型adapter on 2016/9/30.
 */
public class HuDongPicTypeAdapter extends CommonAdapter<HuDonglPicType.DataBean.PageBeanBean.DataBeanInner>{

    private Context context;
    String[] pic;
    public HuDongPicTypeAdapter(Context context, List<HuDonglPicType.DataBean.PageBeanBean.DataBeanInner> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
        this.context = context;
    }

    @Override
    public void convert(ViewHolder helper, HuDonglPicType.DataBean.PageBeanBean.DataBeanInner item, int position) {

        ImageView left=(ImageView)helper.getView(R.id.left_detail_pic);//左图
        ImageView right=(ImageView)helper.getView(R.id.right_detail_pic);//右图
        final ImageView button=(ImageView)helper.getView(R.id.button_detail_pic);//下图
//        final ImageView shi=(ImageView)helper.getView(R.id.iv_shi);//2
        final View line=(View)helper.getView(R.id.vline);//线

        final LinearLayout llpicks=(LinearLayout)helper.getView(R.id.ll_pics);
        ImageView top1=(ImageView)helper.getView(R.id.top1);//右图



        TextView textview=(TextView)helper.getView(R.id.question_content);//问题内容
        textview.setText(item.questionData);//问题内容  tv_title
        ((TextView)helper.getView(R.id.tv_title)).setText(item.questionTitle);//问题类型
        ((TextView)helper.getView(R.id.tv_question_type)).setText(item.questionType);//问题类型
        ((TextView)helper.getView(R.id.tv_price)).setText("￥" + item.questionMoney+"0");//悬赏金额
        ((TextView)helper.getView(R.id.tv_time_type_pic)).setText(DateUtil.timesOne(item.questionPublishDate));//时间


        if(!TextUtils.isEmpty(item.questionImgs)){
            pic= TextUtils.convertStrToArray(item.questionImgs);
            if(pic.length==1){
                llpicks.setVisibility(View.GONE);
                textview.setVisibility(View.GONE);
                left.setVisibility(View.GONE);
                right.setVisibility(View.GONE);
                button.setVisibility(View.VISIBLE);
                ImageLoader.getInstance().displayImage(pic[0], button);

            }if(pic.length==2){
                top1.setVisibility(View.VISIBLE);
                textview.setVisibility(View.GONE);
                line.setVisibility(View.GONE);
                button.setVisibility(View.VISIBLE);
                left.setVisibility(View.GONE);
                right.setVisibility(View.GONE);

//
//                Glide.with(mContext).load(pic[0]).asBitmap().into(new SimpleTarget<Bitmap>() {
//                    @Override
//                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                        Drawable drawable = new BitmapDrawable(resource);
//                        llpicks.setBackground(drawable);
//                    }
//                });
                ImageLoader.getInstance().displayImage(pic[0], top1);
                ImageLoader.getInstance().displayImage(pic[1], button);
            }if(pic.length>=3){
                top1.setVisibility(View.GONE);
                textview.setVisibility(View.GONE);
                llpicks.setVisibility(View.VISIBLE);
                left.setVisibility(View.VISIBLE);
                right.setVisibility(View.VISIBLE);
                button.setVisibility(View.VISIBLE);
                ImageLoader.getInstance().displayImage(pic[0], left);
                ImageLoader.getInstance().displayImage(pic[1],right);
                ImageLoader.getInstance().displayImage(pic[2], button);
            }
        }else{
            textview.setVisibility(View.VISIBLE);
            llpicks.setVisibility(View.GONE);
            button.setVisibility(View.GONE);
            textview.setVisibility(View.VISIBLE);
        }

    }


}
