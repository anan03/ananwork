package com.lvshandian.asktoask.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.module.login.LoginActivity;
import com.lvshandian.asktoask.module.postquestion.PostQuestionActivity;
import com.lvshandian.asktoask.utils.Global;


/**
 * Created by ldb on 2016/8/30.提问
 */
public class QuestionPopupwindow {

    LinearLayout ll_popup;
    PopupWindow pop;
    Context mContext;
    View parentView;

    public QuestionPopupwindow(Context mContext, int parentViewId) {
        this.mContext = mContext;
        init(mContext, parentViewId);
    }

    public void init(final Context mContext, int parentViewId) {
        pop = new PopupWindow(mContext);
        parentView = LayoutInflater.from(mContext).inflate(parentViewId, null);
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_popupwindows, null);
        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);

        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
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

        view.findViewById(R.id.question_pop_iv).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
                //发布问题没有登录的话去登录
                if(Global.isLogin(mContext)){
                    goToAskQuestion();
                }else{
                    Intent intent=new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);
                }


            }
        });
    }

    /**
     * 去发布问题
     */

    private void  goToAskQuestion(){
        Intent intent=new Intent(mContext, PostQuestionActivity.class);
        mContext.startActivity(intent);
    }


    public void show() {
        if (pop != null && ll_popup != null && mContext != null) {
            ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.activity_translate_in));
            pop.setOutsideTouchable(true);
            pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
        }
    }
}
