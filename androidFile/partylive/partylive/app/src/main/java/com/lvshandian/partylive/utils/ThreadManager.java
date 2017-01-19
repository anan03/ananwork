package com.lvshandian.partylive.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2016/3/11.
 */
public class ThreadManager {
    private static ThreadPool mThreadPool;
    public static ThreadPool getThreadPool(){
        if(mThreadPool == null){
            synchronized (ThreadManager.class){
                if(mThreadPool == null){
                    int cpuCount = Runtime.getRuntime().availableProcessors();
                    int threadCount = cpuCount * 2 +1;
                    mThreadPool = new ThreadPool(threadCount,threadCount,0);
                }
            }
        }
        return mThreadPool;
    }
    public static class ThreadPool{
        private int corePoolSize;
        private int maxinumPoolSize;
        private long keepAliveTime;
        private ThreadPoolExecutor poolExecutor;

        private ThreadPool(int corePoolSize,int maxinumPoolSize,int keepAliveTime){
            this.corePoolSize = corePoolSize;
            this.maxinumPoolSize = maxinumPoolSize;
            this.keepAliveTime = keepAliveTime;

        }
        public void execute(Runnable r){
            if(poolExecutor == null){
                poolExecutor = new ThreadPoolExecutor(
                        corePoolSize,//核心线程数
                        maxinumPoolSize,//最大线程数
                        keepAliveTime,//2
                        TimeUnit.SECONDS,
                        new LinkedBlockingQueue<Runnable>(),
                        Executors.defaultThreadFactory(),
                        new ThreadPoolExecutor.AbortPolicy()//异常处理策略
                );

            }
            poolExecutor.execute(r);

        }


    }
}
