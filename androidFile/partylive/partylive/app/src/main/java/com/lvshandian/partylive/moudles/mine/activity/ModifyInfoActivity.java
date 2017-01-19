package com.lvshandian.partylive.moudles.mine.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.base.BaseActivity;
import com.lvshandian.partylive.utils.ToastUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by gjj on 2016/11/15.
 * 修改资料页面
 */

public class ModifyInfoActivity extends BaseActivity {
    @Bind(R.id.iv_image)
    ImageView ivImage;
    private TextView tvCamera;
    private TextView tvCancel;
    private TextView tvPhonePicture;
    private PopupWindow popupWindow;
    private int width;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_modify_info;
    }

    @Override
    protected void initListener() {
        ivImage.setOnClickListener(this);
    }

    @Override
    protected void initialized() {
        initPopView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_image:
                if (popupWindow != null && !popupWindow.isShowing()) {

                    popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                }
                break;
            case R.id.tv_camera:
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                break;
            case R.id.tv_phone_picture:

                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                break;
            case R.id.tv_cancel:
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
                break;
        }
    }


    /**
     * 头像来源选择popWindow
     */
    private void initPopView() {
        View popView = View.inflate(this, R.layout.pop_header_address, null);
        tvCamera = (TextView) popView.findViewById(R.id.tv_camera);
        tvCancel = (TextView) popView.findViewById(R.id.tv_cancel);
        tvPhonePicture = (TextView) popView.findViewById(R.id.tv_phone_picture);
        if (tvCamera != null) {
            tvCamera.setOnClickListener(this);
        }

        if (tvPhonePicture != null) {
            tvPhonePicture.setOnClickListener(this);
        }
        if (tvCancel != null) {
            tvCancel.setOnClickListener(this);
        }
        width = getWindowManager().getDefaultDisplay().getWidth() * 2 / 3;
        int height = FrameLayout.LayoutParams.MATCH_PARENT;
        popupWindow = new PopupWindow(popView, width, height);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));// 颜色设置为透明
    }
}
