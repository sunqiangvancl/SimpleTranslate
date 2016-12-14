package xyz.mrseng.fasttranslate.global;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by MrSeng on 2016/12/12.
 * 多线程管理类
 */

public class ThreadManager {
    private static MyThreadPool myThreadPool;

    public synchronized static void execute(Runnable run) {
        if (myThreadPool == null) {
            myThreadPool = new MyThreadPool(10, 10, 20);
        }
        myThreadPool.execute(run);
    }


    static private class MyThreadPool {
        private MyThreadPool(int coreSize, int maxSize, long aliveTime) {
            this.coreSize = coreSize;
            this.maxSize = maxSize;
            this.aliveTime = aliveTime;
        }

        private ThreadPoolExecutor mExecutor;
        private int coreSize;
        private int maxSize;
        private long aliveTime;


        private void execute(Runnable run) {
            if (mExecutor == null) {
                mExecutor = new ThreadPoolExecutor(coreSize, maxSize, aliveTime, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>());
            }
            mExecutor.execute(run);
        }
    }
}
