package com.lvshandian.partylive.view;

/**
 * Created by sll on 2016/12/10.
 */

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lvshandian.partylive.R;
import com.lvshandian.partylive.base.BarrageDateBean;
import com.lvshandian.partylive.widget.AvatarView;

import java.util.ArrayList;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.AttributeSet;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 2秒一条
 * 屏幕上同时存在5条
 * Created by nangua on 2016/7/28.
 */
public class BarrageView extends FrameLayout {
    private static ArrayList<BarrageDateBean> date = new ArrayList<>(); //数据
    private int nowIndex = 0; //date的下标
    private Bitmap nowBitmap; //当前图片
    int width;    //控件宽
    int height;  //控件高
    float scale;    //像素密度
    FrameLayout frameLayout;
    FrameLayout.LayoutParams tvParams;

    static boolean IS_START = false;    //判断是否开始

    long alltime; //视频总时长
    long starttime; //开始时间

    //    LinearLayout layout;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            BarrageDateBean barrageDate = (BarrageDateBean) msg.getData().getSerializable("BarrageDateBean");
            final LinearLayout layout = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.item_barrage, null);
            layout.setLayoutParams(tvParams);
            //随机获得Y值
            layout.setY(getRamdomY());
            layout.setX(width + layout.getWidth());

            //设置名字
            TextView textNick = (TextView) layout.findViewById(R.id.barrageview_item_nick);
            textNick.setText(barrageDate.getNickName());
            //设置文字
            TextView textView = (TextView) layout.findViewById(R.id.barrageview_item_tv);
            textView.setText(barrageDate.getContent());

            //设置图片
            AvatarView ngNormalCircleImageView = (AvatarView) layout.findViewById(R.id.barrageview_item_img);
            ngNormalCircleImageView.setAvatarUrl(barrageDate.getPicUrl());
//            if (nowBitmap != null) {
//                ngNormalCircleImageView.setImageBitmap(nowBitmap);
//            }

            frameLayout.addView(layout);

            final ObjectAnimator anim = ObjectAnimator.ofFloat(layout, "translationX", -width);
            anim.setDuration(10000);

            //释放资源
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    anim.cancel();
                    layout.clearAnimation();
                    frameLayout.removeView(layout);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
            anim.start();
        }
    };

    /**
     * 使用httprulconnection通过发送网络请求path获得bitmap
     *
     * @param path
     * @return
     */
    public static Bitmap getBitmapFromUrl(String path) {
        try {
            //获得url
            URL url = new URL(path);
            //打开httprulconnection获得实例
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //设置超时时间
            conn.setConnectTimeout(5000);
            //设置Get
            conn.setRequestMethod("GET");
            //连接成功
            if (conn.getResponseCode() == 200) {
                //获得输入流
                InputStream inputStream = conn.getInputStream();
                //得到bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                if (bitmap == null) {
                }
                //返回
                return bitmap;
            }
            //错误信息处理
        } catch (Exception e) {
            //打印错误信息
            e.printStackTrace();
        }
        return null;
    }

    int lastY;//上一次出现的Y值

    /**
     * 获得随机的Y轴的值
     *
     * @return
     */
    private float getRamdomY() {
        int tempY;
        int rY;
        int result = 0;
        // height * 2 / 4 - 25
        //首先随机选择一条道路
        int nowY = (int) (Math.random() * 2);
        switch (nowY) {
            case 0:
                nowY = avoidTheSameY(nowY, lastY);
                //第一条
                /*tempY = height / 2 - 200;
                rY = (int) (Math.random() * height / 2);
                if (rY >= height / 4) {
                    result = tempY + rY;
                } else {
                    result = tempY - rY + 100;
                }*/
                result = 0;
                lastY = nowY;
                break;
            case 1:
                nowY = avoidTheSameY(nowY, lastY);
                //第二条
                /*tempY = height - 200;
                rY = (int) (Math.random() * height / 2);
                if (rY >= height / 4) {
                    result = tempY + rY;
                } else {
                    result = tempY - rY;
                }*/
                result = height / 2;
                lastY = nowY;
                break;
        }
        return result;
    }

    /**
     * 避免Y重合的方法
     *
     * @param lastY
     * @return
     */
    private int avoidTheSameY(int nowY, int lastY) {
        if (nowY == lastY) {
            nowY++;
        }
        if (nowY == 2) {
            nowY = 0;
        }
        return nowY;
    }


    public BarrageView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth(); //宽度
        height = getHeight();   //高度
        init();
    }

    private void init() {
        setTime(600000);    //设置初始时长，改完记得删

        starttime = System.currentTimeMillis();

        scale = this.getResources().getDisplayMetrics().density;
        //获得自身实例
        frameLayout = (FrameLayout) findViewById(R.id.barrageview);
        tvParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);

        if (IS_START) {
            //开始动画线程
            startBarrageDateBeanView();
            IS_START = false;
        }
    }

    public void startBarrageDateBeanView() {
        //开启线程发送弹幕
        new Thread() {
            @Override
            public void run() {

                while ((System.currentTimeMillis() - starttime < alltime)
                        && (nowIndex <= date.size() - 1)
                        ) {

                    try {
                        nowBitmap = getBitmapFromUrl(date.get(nowIndex).getPicUrl());
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("BarrageDateBean", date.get(nowIndex));
                        nowIndex++;
                        message.setData(bundle);
                        handler.sendMessage(message);
                        Thread.sleep((long) (Math.random() * 3000) + 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                return;
            }
        }.start();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    //设置数据
    public void setSentenceList(ArrayList<BarrageDateBean> date1) {
        date = date1;
        IS_START = true;
    }

    /**
     * 添加单个数据
     *
     * @author sll
     * @time 2016/12/12 10:23
     */
    public void addSentence(BarrageDateBean item) {
        if (date == null) {
            date = new ArrayList<>();
        }
        date.add(item);
        IS_START = true;
    }

    //获得视频总时长
    public void setTime(long time) {
        alltime = time;
    }

}
