package com.lvshandian.asktoask.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.lvshandian.asktoask.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by newlq on 2016/9/7.
 * 退出登录提示框
 */
public class ExitLoginDialog extends Dialog {

    @Bind(R.id.tv_exitlogindialog_quxiao)
    TextView tvExitlogindialogQuxiao;
    @Bind(R.id.tv_exitlogindialog_queding)
    TextView tvExitlogindialogQueding;

    public ExitLoginDialog(Context context) {
        super(context, R.style.WinDialog);
        setContentView(R.layout.dialog_exitdialog);
        ButterKnife.bind(this);
    }

    public void setQuXiaoListener(View.OnClickListener l) {
        tvExitlogindialogQuxiao.setOnClickListener(l);
    }

    public void setQueRenListener(View.OnClickListener l) {
        tvExitlogindialogQueding.setOnClickListener(l);
    }

}
