package com.lvshandian.asktoask.utils;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.app.ActionBar.LayoutParams;
import android.widget.TextView;

import com.lvshandian.asktoask.R;
import com.lvshandian.asktoask.entry.HuDongItem;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

/**
 * 弹出pupwindow on 2016/9/24.
 * 创建一个DialogView
 */
public class DialogView extends PopupWindow {
    MyCallback mycallback;
    MyCameraCallback mycamerback;

    /**
     * 确定取消弹出卡框
     *
     * @param context
     * @param parent
     * @param title      标题
     * @param yesText    确定
     * @param noText
     * @param mycallback
     */
    public DialogView(final Context context, final View parent, final int typetag, String title, String yesText, String noText,
                      final MyCallback mycallback) {
        super(context);
        if (context == null || parent == null) {
            return;
        }
        this.mycallback = mycallback;
        final View view = LayoutInflater.from(context).inflate(
                R.layout.dialogviewconfirm, null);
        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(context.getResources().getDrawable(
                R.drawable.popwindow_bg));
        TextView conText = (TextView) view.findViewById(R.id.context);
        conText.setText(title);
        TextView queren = (TextView) view.findViewById(R.id.queren);
        queren.setText(yesText);
        TextView close = (TextView) view.findViewById(R.id.close);
        close.setText(noText);
        setContentView(view);
        setFocusable(true);
        showAtLocation((View) parent.getParent(), Gravity.CENTER, 0, 0);
        view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                parent.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        DialogView.this.dismiss();

                    }
                }, 100);

            }
        });
        view.findViewById(R.id.queren).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // 跳转到个人信息页面
                        parent.postDelayed(new Runnable() {

                            @Override
                            public void run() {


                                DialogView.this.dismiss();
                                mycallback.refreshActivity();
                            }
                        }, 100);
                    }
                });
        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                parent.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        DialogView.this.dismiss();

                    }
                }, 100);
            }
        });

    }


    /**
     * 分享弹出的pupwindow  互动分享
     *
     * @param context
     * @param parent
     */

    public DialogView(final Context context, final View parent,
                      final HuDongItem.DataBean.PageBean.DataBean2 item) {
        super(context);
        if (context == null || parent == null) {
            return;
        }

        final View view = LayoutInflater.from(context).inflate(
                R.layout.hudong_share_popupwindow, null);
        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(context.getResources().getDrawable(
                R.drawable.popwindow_bg));
        setOutsideTouchable(true);
        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = 0.5f; //0.0-1.0  1.0的话是黑色 设置透明度
        ((Activity) context).getWindow().setAttributes(lp);
        final TextView tv_cancle = (TextView) view.findViewById(R.id.tv_cancle);
        setContentView(view);
        setFocusable(true);
        showAtLocation((View) parent.getParent(), Gravity.BOTTOM, 0, 100);

        //qq分享
        view.findViewById(R.id.qq_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.shareQQ((Activity) context, true, item.getQuestionData(), item.getQuestionTitle(), ShareUtils.ImagString, new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        tv_cancle.performClick();


                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(Platform platform, int i) {

                    }
                }, ShareUtils.sharehudong + "questionId=" + item.getQuestionId() + "&" + "userId=" + Global.getUserId(context));
                cloes(context);
            }
        });
        //qq空间
        view.findViewById(R.id.qq_kong_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ShareUtils.shareQZone((Activity) context, true, item.getQuestionTitle(), item.getQuestionData(), ShareUtils.ImagString, new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

                        tv_cancle.performClick();

                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(Platform platform, int i) {

                    }
                }, ShareUtils.sharehudong + "questionId=" + item.getQuestionId() + "&" + "userId=" + item.getUserId());
                cloes(context);
            }
        });
        //微博
        view.findViewById(R.id.weibo_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ShareUtils.shareWeiBo((Activity) context, true, item.getQuestionData(), item.getQuestionTitle(), new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

                        tv_cancle.performClick();
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(Platform platform, int i) {

                    }
                }, ShareUtils.sharehudong + "questionId=" + item.getQuestionId() + "&" + "userId=" + Global.getUserId(context));
                cloes(context);
            }
        });
        //微信
        view.findViewById(R.id.weixin_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.shareWeChat((Activity) context, true, item.getQuestionData(), item.getQuestionTitle(), new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        tv_cancle.performClick();
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(Platform platform, int i) {

                    }
                }, "", ShareUtils.sharehudong + "questionId=" + item.getQuestionId() + "&" + "userId=" + Global.getUserId(context));
                cloes(context);
            }
        });
        //微信朋友圈
        view.findViewById(R.id.weixinkong_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareUtils.shareWeChatQuan((Activity) context, true, item.getQuestionData(), item.getQuestionTitle(), new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

                        tv_cancle.performClick();
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(Platform platform, int i) {

                    }
                }, "详情的", ShareUtils.sharehudong + "questionId=" + item.getQuestionId() + "&" + "userId=" + Global.getUserId(context));

                cloes(context);
            }
        });
        //取消 消失pupwindow
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cloes(context);
            }
        });

    }

    public void cloes(Context context) {

        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = 1.0f; //0.0-1.0  1.0的话是黑色 设置透明度
        ((Activity) context).getWindow().setAttributes(lp);
        //取消对话框
        dismiss();
    }


    /**
     * 头像上传弹出框
     *
     * @param context
     * @param parent
     * @param mycallback
     */

    public DialogView(final Context context, final View parent, String tag, String titleOne, String titleTwo, String titleThree,
                      final MyCameraCallback mycallback) {
        super(context);
        this.mycamerback = mycallback;

        if (context == null || parent == null) {
            return;
        }

        final View view = LayoutInflater.from(context).inflate(
                R.layout.dialogview_head, null);
        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(context.getResources().getDrawable(
                R.drawable.popwindow_bg));


        TextView tv_yse = (TextView) view.findViewById(R.id.tv_yse);

        tv_yse.setText(titleOne);
        TextView tv_no = (TextView) view.findViewById(R.id.tv_no);
        tv_no.setText(titleTwo);
        TextView tv_cancle = (TextView) view.findViewById(R.id.tv_cancle);
        tv_cancle.setText(titleThree);
        /**
         * 相机
         */
        tv_yse.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DialogView.this.dismiss();
                mycamerback.refreshCallback(1);
            }
        });
        /**
         * 图库
         */
        tv_no.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DialogView.this.dismiss();
                mycamerback.refreshCallback(2);
            }
        });
        /**
         *
         *取消
         */
        tv_cancle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                DialogView.this.dismiss();
                mycamerback.refreshCallback(3);
            }
        });
        setContentView(view);
        setFocusable(true);
        showAtLocation((View) parent.getParent(), Gravity.BOTTOM, 0, 0);
    }

    /**
     * 确定取消接口
     */
    public interface MyCallback {

        void refreshActivity();
    }

    /**
     * 相机接口
     */
    public interface MyCameraCallback {

        void refreshCallback(int tag);
    }


}
