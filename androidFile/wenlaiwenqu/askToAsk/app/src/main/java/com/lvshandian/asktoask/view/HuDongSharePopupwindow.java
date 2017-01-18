package com.lvshandian.asktoask.view;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lvshandian.asktoask.R;

import butterknife.Bind;

/**
 * 分享的弹框on 2016/10/12.
 */
public class HuDongSharePopupwindow extends PopupWindow{
    LinearLayout ll_popup;
    PopupWindow pop;
    Context mContext;
    View parentView;
    @Bind(R.id.qq_share)
    TextView qqShare;
    @Bind(R.id.weibo_share)
    TextView weiboShare;
    @Bind(R.id.weixin_share)
    TextView weixinShare;
    @Bind(R.id.qq_kong_share)
    TextView qqKongShare;
    @Bind(R.id.weixinkong_share)
    TextView weixinkongShare;
    @Bind(R.id.tv_cancle)
    TextView tvCancle;

    public HuDongSharePopupwindow(Context mContext, int parentViewId) {
        this.mContext = mContext;
        init(mContext, parentViewId);
    }

    public void init(Context mContext, int parentViewId) {
        pop = new PopupWindow(mContext);
//        parentView = LayoutInflater.from(mContext).inflate(parentViewId, null);
        View view = LayoutInflater.from(mContext).inflate(R.layout.hudong_share_popupwindow, null);
//        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);

        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);
        pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
         show();
//        view.findViewById(R.id.parent).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pop.dismiss();
//                ll_popup.clearAnimation();
//            }
//        });

//        view.findViewById(R.id.question_rl).setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                pop.dismiss();
//                ll_popup.clearAnimation();
//            }
//        });

//        tvCancle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pop.dismiss();
//            }
//        });
    }

    public void show() {
        Log.d("aaa", "展示界面");
        if(pop!=null){

            pop.dismiss();
        }
        pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
//        if (pop != null && ll_popup != null && mContext != null) {
////            ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.activity_translate_in));
//            pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
//        }
    }


}
