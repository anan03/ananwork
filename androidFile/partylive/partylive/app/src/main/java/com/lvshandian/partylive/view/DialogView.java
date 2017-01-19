package com.lvshandian.partylive.view;

import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.utils.TextUtils;


/**
 * Created by Administrator on 2016/9/24.
 * <p/>
 * 创建一个DialogView
 */
public class DialogView extends PopupWindow {


    /**
     * 头像上传弹出框
     *
     * @param context
     * @param parent
     * @param mycallback
     */
    private MyCameraCallback mycamerback;

    public DialogView(final Context context, final View parent, String tag, String titleOne, String titleTwo, String titleThree,
                      final MyCameraCallback mycallback) {
        super(context);
        this.mycamerback = mycallback;

        if (context == null || parent == null) {
            // Toast.makeText(context, "参数错误", 0).show();
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
     * 相机接口
     */
    public interface MyCameraCallback {

        void refreshCallback(int tag);

    }

    MyCallback mycallback;

    public DialogView(final Context context, final View parent, String title, String yesText, String noText, final int tag, final MyCallback mycallback) {
        super(context);

        if (context == null) {
//            LogUtils.e("context");
            return;
        }
        if (parent == null) {
//            LogUtils.e("parent");
            return;
        }
        this.mycallback = mycallback;
        final View view = LayoutInflater.from(context).inflate(
                R.layout.add_gjz_dialog_view, null);
        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(context.getResources().getDrawable(
                R.drawable.popwindow_bg));
        setContentView(view);
        setFocusable(true);
        showAtLocation((View) parent.getParent(), Gravity.CENTER, 0, 0);
        final EditText et_jb = (EditText) view.findViewById(R.id.et_jb);
        final EditText et_by = (EditText) view.findViewById(R.id.et_by);
        view.findViewById(R.id.iv_back).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // 跳转到个人信息页面
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

                                if (TextUtils.isEmpty(TextUtils.getTextContent(et_by)) || TextUtils.isEmpty(TextUtils.getTextContent(et_jb))) {
                                    mycallback.refreshActivity(TextUtils.getTextContent(et_by), TextUtils.getTextContent(et_jb));

                                } else {
                                    mycallback.refreshActivity(TextUtils.getTextContent(et_by), TextUtils.getTextContent(et_jb));

                                    DialogView.this.dismiss();

                                }


                            }
                        }, 100);
                    }
                });


    }


    public DialogView(final Context context, final View parent, String noText,
                      final int tag, final MyCallback mycallback) {
        super(context);

        if (context == null) {
//            LogUtils.e("context");
            return;
        }
        if (parent == null) {
//            LogUtils.e("parent");
            return;
        }
        this.mycallback = mycallback;
        final View view = LayoutInflater.from(context).inflate(
                R.layout.interaction_dialog_view, null);
        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(context.getResources().getDrawable(
                R.drawable.popwindow_bg));
        setContentView(view);
        setFocusable(true);
        final TextView textView = (TextView) view.findViewById(R.id.tv_less_jb);
       final EditText editText = (EditText) view.findViewById(R.id.et_jb);
        editText.setText(noText);
        textView.setText("需要最少支付"+noText+"金币");
        showAtLocation((View) parent.getParent(), Gravity.CENTER, 0, 0);
        view.findViewById(R.id.tv_sl).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // 跳转到个人信息页面
                        parent.postDelayed(new Runnable() {

                            @Override
                            public void run() {

                                if(android.text.TextUtils.isEmpty(TextUtils.getTextContent(editText))){

                                    mycallback.refreshActivity("","");

                                    return;
                                }
                                mycallback.refreshActivity("",editText.getText().toString());
                                DialogView.this.dismiss();




                            }
                        }, 100);
                    }
                });
        //公开
        view.findViewById(R.id.tv_gk).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // 跳转到个人信息页面
                        parent.postDelayed(new Runnable() {

                            @Override
                            public void run() {


                                DialogView.this.dismiss();

                            }
                        }, 100);
                    }
                });
        //私聊
        view.findViewById(R.id.iv_back).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // 跳转到个人信息页面
                        parent.postDelayed(new Runnable() {

                            @Override
                            public void run() {


                                DialogView.this.dismiss();


                            }
                        }, 100);
                    }
                });

    }
    public DialogView(final Context context, final View parent, final MyCallback mycallback) {
        super(context);

        if (context == null) {
//            LogUtils.e("context");
            return;
        }
        if (parent == null) {
//            LogUtils.e("parent");
            return;
        }
        this.mycallback = mycallback;
        final View view = LayoutInflater.from(context).inflate(
                R.layout.interaction_dialog_view, null);
        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(context.getResources().getDrawable(
                R.drawable.popwindow_bg));
        setContentView(view);
        setFocusable(true);
        showAtLocation((View) parent.getParent(), Gravity.CENTER, 0, 0);
        view.findViewById(R.id.tv_sl).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // 跳转到个人信息页面
                        parent.postDelayed(new Runnable() {

                            @Override
                            public void run() {


                                DialogView.this.dismiss();


                            }
                        }, 100);
                    }
                });
        //公开
        view.findViewById(R.id.tv_gk).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // 跳转到个人信息页面
                        parent.postDelayed(new Runnable() {

                            @Override
                            public void run() {


                                DialogView.this.dismiss();

                            }
                        }, 100);
                    }
                });
        //私聊
        view.findViewById(R.id.iv_back).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // 跳转到个人信息页面
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
     * 确定取消接口
     */
    public interface MyCallback {

        void refreshActivity();

        void refreshActivity(String perform, String gold);

        void refreshActivity(String context, String minString, String maxString);
    }

}
