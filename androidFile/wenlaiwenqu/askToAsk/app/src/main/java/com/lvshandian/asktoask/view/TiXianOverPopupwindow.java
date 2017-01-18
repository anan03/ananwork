package com.lvshandian.asktoask.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.lvshandian.asktoask.R;

/**
 * Created by newlq on 2016/9/6.
 * 提现成功 的提示
 */
public class TiXianOverPopupwindow {

    LinearLayout ll_popup;
    PopupWindow pop;
    Context mContext;
    View parentView;

    public TiXianOverPopupwindow(Context mContext, int parentViewId) {
        this.mContext = mContext;
        init(mContext, parentViewId);
    }

    public void init(Context mContext, int parentViewId) {
        pop = new PopupWindow(mContext);
        parentView = LayoutInflater.from(mContext).inflate(parentViewId, null);
        View view = LayoutInflater.from(mContext).inflate(R.layout.tixianover_popupwindows, null);
        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);

        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
//        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);

        view.findViewById(R.id.parent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });

        view.findViewById(R.id.question_rl).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
    }


    public void show() {
        if (pop != null && ll_popup != null && mContext != null) {
            ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.activity_translate_in));
            pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
        }
    }

    public void dismiss(){
        pop.dismiss();
    }
}
