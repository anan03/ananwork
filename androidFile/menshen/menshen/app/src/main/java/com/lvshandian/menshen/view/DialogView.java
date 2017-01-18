package com.lvshandian.menshen.view;

import android.content.Context;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.app.ActionBar.LayoutParams;
import android.widget.TextView;

import com.lvshandian.menshen.R;
import com.lvshandian.menshen.utils.LogUtils;
import com.lvshandian.menshen.utils.TextUtils;


/**
 * Created by Administrator on 2016/9/24.
 * <p/>
 * 创建一个DialogView
 */
public class DialogView extends PopupWindow {
    MyCallback mycallback;

    public DialogView(final Context context, final View parent, String title, String yesText, String noText,
                      final MyCallback mycallback) {
        super(context);
        if (context == null || parent == null) {
            // Toast.makeText(context, "参数错误", 0).show();
            return;
        }
        this.mycallback = mycallback;
        final View view = LayoutInflater.from(context).inflate(
                R.layout.faily_dialog_view, null);
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
        // showAsDropDown(parent);
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

    public DialogView(final Context context, final View parent, String title, int yesText, int noText,
                      final int tag, final MyCallback mycallback) {
        super(context);

        if (context == null) {
            LogUtils.e("context");
            return;
        }
        if (parent == null) {
            LogUtils.e("parent");
            return;
        }
        LogUtils.e("话");
        this.mycallback = mycallback;
        final View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_jubaocg, null);
        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(context.getResources().getDrawable(
                R.drawable.popwindow_bg));
        setContentView(view);
        setFocusable(true);
//        showAsDropDown(parent);
        TextView tv_gujian = (TextView) view.findViewById(R.id.tv_gujian);

        if (!title.equals("0")) {

            tv_gujian.setText("恭喜你获得" + title + "积分");
        } else {
            tv_gujian.setText("你已经上传不能重复上传");
        }


        showAtLocation((View) parent.getParent(), Gravity.CENTER, 0, 0);
        view.findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                parent.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        mycallback.refreshActivity();
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

                                mycallback.refreshActivity();

                                DialogView.this.dismiss();


                            }
                        }, 100);
                    }
                });


    }

    public DialogView(final Context context, final View parent, String title, String yesText, String noText,
                      final int tag, final MyCallback mycallback) {
        super(context);

        if (context == null) {
            LogUtils.e("context");
            return;
        }
        if (parent == null) {
            LogUtils.e("parent");
            return;
        }
        LogUtils.e("话");
        this.mycallback = mycallback;
        final View view = LayoutInflater.from(context).inflate(
                R.layout.add_gjz_dialog_view, null);
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
        final EditText editText = (EditText) view.findViewById(R.id.et_gujian);
        final TextView tv_gujian = (TextView) view.findViewById(R.id.tv_gujian);
        final View view_view = view.findViewById(R.id.view_view);


        if (tag == 1) {//弹出输入框；进行添加关键字
            editText.setHint("如：银行卡、账户");
            editText.setVisibility(View.VISIBLE);
            tv_gujian.setVisibility(View.GONE);
            view_view.setVisibility(View.VISIBLE);

            LogUtils.e("1" + tag);
        } else if (tag == 2) {//弹出提示框进行关键字的删除
            editText.setVisibility(View.GONE);
            tv_gujian.setVisibility(View.VISIBLE);
            view_view.setVisibility(View.GONE);
            LogUtils.e("2" + tag);
        } else if (tag == 3) {//弹出输入框兵器进行手机电话的拦截
            editText.setVisibility(View.VISIBLE);
            editText.setHint("如：175、135");
            tv_gujian.setVisibility(View.GONE);
            view_view.setVisibility(View.VISIBLE);
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
            LogUtils.e("3" + tag);
        }
        setContentView(view);
        setFocusable(true);
//        showAsDropDown(parent);
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

                                mycallback.refreshActivity(TextUtils.getTextContent(editText));

                                DialogView.this.dismiss();


                            }
                        }, 100);
                    }
                });


    }


    public DialogView(final Context context, final View parent, String strMin, String strMax,
                      final int tag, final MyCallback mycallback) {
        super(context);

        if (context == null) {
            LogUtils.e("context");
            return;
        }
        if (parent == null) {
            LogUtils.e("parent");
            return;
        }
        LogUtils.e("话");
        this.mycallback = mycallback;
        final View view = LayoutInflater.from(context).inflate(
                R.layout.load_dialog_view, null);
        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(context.getResources().getDrawable(
                R.drawable.popwindow_bg));
        TextView conText = (TextView) view.findViewById(R.id.context);
        conText.setText("请输入关键字");

        final EditText et_name = (EditText) view.findViewById(R.id.et_name);
        final EditText et_size_max = (EditText) view.findViewById(R.id.et_size_max);
        final EditText et_size_min = (EditText) view.findViewById(R.id.et_size_min);
        et_size_max.setText(strMax);
        et_size_min.setText(strMin);
        setContentView(view);
        setFocusable(true);
//        showAsDropDown(parent);
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

                                if (TextUtils.isEmpty(TextUtils.getTextContent(et_name))) {

                                    ToastUtils.showSnackBar(parent, "请输入关键字");
                                    return;
                                }
//                                if (TextUtils.isEmpty(TextUtils.getTextContent(et_size_max))
//                                        || TextUtils.isEmpty(TextUtils.getTextContent(et_size_min))) {
//                                    ToastUtils.showSnackBar(parent, "请填写应用大小");
//                                    return;
//                                }
//                                int max = Integer.parseInt(TextUtils.getTextContent(et_size_max).trim());
//                                int min = Integer.parseInt(TextUtils.getTextContent(et_size_min).trim());
//                                if (max <= min) {
//                                    ToastUtils.showSnackBar(parent, "请输入正确的文件大小");
//                                    return;
//                                }
                                mycallback.refreshActivity(TextUtils.getTextContent(et_name),
                                        TextUtils.getTextContent(et_size_min),
                                        TextUtils.getTextContent(et_size_max));
                                DialogView.this.dismiss();
                            }
                        }, 100);
                    }
                });


    }

    public DialogView(final Context context, final View parent, String title, String yesText, int noText,
                      final int tag, final MyCallback mycallback) {
        super(context);

        if (context == null) {
            LogUtils.e("context");
            return;
        }
        if (parent == null) {
            LogUtils.e("parent");
            return;
        }
        LogUtils.e("话");
        this.mycallback = mycallback;
        final View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_jubao, null);
        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(context.getResources().getDrawable(
                R.drawable.popwindow_bg));
        TextView conText = (TextView) view.findViewById(R.id.context);
        TextView queren = (TextView) view.findViewById(R.id.queren);
        queren.setText(yesText);
        TextView close = (TextView) view.findViewById(R.id.close);
        final EditText editText = (EditText) view.findViewById(R.id.et_gujian);
        setContentView(view);
        setFocusable(true);
//        showAsDropDown(parent);
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

                                mycallback.refreshActivity(TextUtils.getTextContent(editText));

                                DialogView.this.dismiss();


                            }
                        }, 100);
                    }
                });


    }

    //确定取消
    public DialogView(final Context context, final View parent, String title, String yesText, String noText,
                      final String tag, final MyCallback mycallback) {
        super(context);

        if (context == null) {
            LogUtils.e("context");
            return;
        }
        if (parent == null) {
            LogUtils.e("parent");
            return;
        }
        LogUtils.e("话");
        this.mycallback = mycallback;
        final View view = LayoutInflater.from(context).inflate(
                R.layout.que_dialog_view, null);
        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(context.getResources().getDrawable(
                R.drawable.popwindow_bg));
        //头部内容
        TextView conText = (TextView) view.findViewById(R.id.context);
        conText.setText(title);

        TextView queren = (TextView) view.findViewById(R.id.queren);
        queren.setText(yesText);

        TextView close = (TextView) view.findViewById(R.id.close);
        close.setText(noText);
        //中间内容
        final TextView tv_gujian = (TextView) view.findViewById(R.id.tv_gujian);
        if (tag.equals("")) {
            tv_gujian.setVisibility(View.GONE);
        }
        tv_gujian.setText(tag);
        final View view_view = view.findViewById(R.id.view_view);
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

                                mycallback.refreshActivity();
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

        void refreshActivity(String tag);

        void refreshActivity(String context, String minString, String maxString);
    }

    /**
     * 相机接口
     */
    public interface MyCameraCallback {

        void refreshCallback(int tag);

    }

}
