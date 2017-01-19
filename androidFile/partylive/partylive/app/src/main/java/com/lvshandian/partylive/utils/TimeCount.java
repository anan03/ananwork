package com.lvshandian.partylive.utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.lvshandian.partylive.R;

public class TimeCount extends CountDownTimer {
    private TextView textView;
    private String data = "重新获取";
    private Context context;

    public TimeCount(Context context, long millisInFuture, long countDownInterval,
                     TextView textView) {
        super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        this.textView = textView;
        this.context = context;
    }

    public TimeCount(long millisInFuture, long countDownInterval,
                     TextView textView, String data) {
        super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        this.textView = textView;
        this.data = data;
    }

    @Override
    public void onFinish() {// 计时完毕时触发
        textView.setText(data);
        textView.setClickable(true);
        textView.setTextColor(context.getResources().getColor(R.color.gray));
        textView.setBackgroundResource(R.drawable.yuanjiao_hui_bg);
    }

    @Override
    public void onTick(long millisUntilFinished) {// 计时过程显示
        textView.setClickable(false);
        textView.setText(millisUntilFinished / 1000 + "秒后重新获取");
        textView.setBackgroundResource(R.drawable.yuanjiao_hui_bg);
        textView.setTextColor(context.getResources().getColor(R.color.gray));
    }
}