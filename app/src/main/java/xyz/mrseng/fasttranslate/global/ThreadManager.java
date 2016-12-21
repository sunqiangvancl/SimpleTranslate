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
    private static MyThreadPool myThreadPoolSingle;

    public synchronized static void execute(Runnable run) {
        if (myThreadPool == null) {
            myThreadPool = new MyThreadPool(10, 10, 20);
        }
        myThreadPool.execute(run);
    }

    //翻译类需要单一子线程来实现，可以避免数据错乱
    public synchronized static void executeOnTransThread(Runnable run) {
        if (myThreadPoolSingle == null) {
            myThreadPoolSingle = new MyThreadPool(1, 1, 20);
        }
        myThreadPoolSingle.execute(run);
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
