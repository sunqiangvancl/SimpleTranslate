package xyz.mrseng.fasttranslate.utils;

import android.app.Activity;

import java.util.LinkedList;

/**
 * Created by MrSeng on 2016/12/12.
 * 维护一个Activity的集合
 */

public class ActivityUtils {

    private LinkedList<Activity> mList = new LinkedList<>();

    //单例
    private static ActivityUtils mUtils;

    /** 获取一个ActivityUtils实例 */
    public static ActivityUtils getNewInstance() {
        if (mUtils == null) {
            synchronized (ActivityUtils.class) {
                if (mUtils == null) {
                    mUtils = new ActivityUtils();
                }
            }
        }
        return mUtils;
    }

    private ActivityUtils() {
    }


    //加入activity
    public void add(Activity activity) {
        if (activity != null && !mList.contains(activity)) {
            mList.add(activity);
        }
    }

    /*移除*/
    public void remove(Activity activity) {
        if (activity != null && mList.contains(activity))
            mList.remove(activity);
    }

    /*获取activity集合*/
    public LinkedList<Activity> getList() {
        return mList;
    }

    /*退出程序*/
    public synchronized void exit() {
        try {
            for (int i = 0; i < mList.size(); i++) {
                mList.get(i).finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

}
