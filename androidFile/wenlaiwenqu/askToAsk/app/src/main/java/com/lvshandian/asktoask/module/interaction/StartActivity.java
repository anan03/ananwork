package com.lvshandian.asktoask.module.interaction;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.lvshandian.asktoask.BaseActivity;
import com.lvshandian.asktoask.MainActivity;
import com.lvshandian.asktoask.R;

/**
 * 启动界面 on 2016/10/20.
 */
public class StartActivity extends BaseActivity{

    private Thread thread;
    private  int zhuan=1;

    // handler类接收数据
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                goToMain();

            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_start_layout;
    }

    @Override
    protected void initialized() {
        thread=new Thread(new ThreadShow());
        thread.start();

    }

    @Override
    protected void initListener() {



    }

    @Override
    public void onClick(View v) {

    }

    // 线程类
    class ThreadShow implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (zhuan==1) {
                try {
                    Thread.sleep(3000);
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                    zhuan=2;
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 去主界面activity
     */

    private void goToMain(){
        Intent intent=new Intent(StartActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
