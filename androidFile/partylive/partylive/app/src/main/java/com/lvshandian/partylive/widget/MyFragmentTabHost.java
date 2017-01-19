package com.lvshandian.partylive.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.support.v4.app.FragmentTabHost;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.moudles.index.live.PrapareVedioActivity;
import com.lvshandian.partylive.utils.TextUtils;
import com.zhy.autolayout.AutoLinearLayout;


/**
 * tabhost
 */

public class MyFragmentTabHost extends FragmentTabHost {

    private String mCurrentTag;

    private String mNoTabChangedTag = "1";
    private Context context;
    private final LayoutInflater mInflator;
    private PopupWindow mPop;
    private int heightPixels;


    public MyFragmentTabHost(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        mInflator = LayoutInflater.from(context);
        initPop();
    }

    private OnClickListener mClick = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.all_live:
                    Intent intent = new Intent(context, PrapareVedioActivity.class);
                    context.startActivity(intent);
                    if (mPop != null && mPop.isShowing()) {
                        mPop.dismiss();
                    }
                    break;
                case R.id.iv_x:
                    if (mPop != null && mPop.isShowing()) {
                        mPop.dismiss();
                    }
                    break;
            }
        }
    };

    /**
     * initPOP
     */
    private void initPop() {

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        heightPixels = displayMetrics.heightPixels;
        View inflate = mInflator.inflate(R.layout.pop_ready_vedio, null);
        AutoLinearLayout ivLive = (AutoLinearLayout) inflate.findViewById(R.id.all_live);
        ImageView ivX = (ImageView) inflate.findViewById(R.id.iv_x);

        ivLive.setOnClickListener(mClick);
        ivX.setOnClickListener(mClick);
        mPop = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPop.setContentView(inflate);
        mPop.setFocusable(true);
        mPop.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.transparent)));
    }

    @Override
    public void onTabChanged(String tag) {
        if (tag.equals(mNoTabChangedTag)) {
            if (mPop != null && !mPop.isShowing()) {
                mPop.showAsDropDown(this, 0, -heightPixels);
            }
            setCurrentTabByTag(mCurrentTag);

        } else {
            super.onTabChanged(tag);
            mCurrentTag = tag;
        }
    }

    public void setNoTabChangedTag(String tag) {
        this.mNoTabChangedTag = tag;
    }
}
