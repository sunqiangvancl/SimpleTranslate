package xyz.mrseng.fasttranslate.utils;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

import xyz.mrseng.fasttranslate.global.MyApplication;

/**
 * Created by MrSeng on 2016/12/12.
 * 界面工具类
 */

public class UIUtils {

    private static WindowManager mManager;

    /** 获取Context */
    public static Context getContext() {
        return MyApplication.getAppContext();
    }

    /** 获取主线程ID */
    public static int getMainThreadId() {
        return MyApplication.getMainThreadID();
    }

    /** 获取handler */
    public static Handler getHandler() {
        return MyApplication.getHandler();
    }

    /** 填充布局文件至View对象,其中跟布局为null */
    public static View inflate(int id) {
        return View.inflate(getContext(), id, null);
    }

    /** 获取字符串数组 */
    public static String[] getStringArray(int id) {
        return getContext().getResources().getStringArray(id);
    }

    /** 获取字符串 */
    public static String getString(int id) {
        return getContext().getResources().getString(id);
    }

    /** 获取数字 */
    public static Integer getInteger(int id) {
        return getContext().getResources().getInteger(id);
    }


    /** 获取图片 */
    public static Drawable getDrawable(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getContext().getResources().getDrawable(id, null);
        } else {
            return getContext().getResources().getDrawable(id);
        }
    }


    /** 是否运行在主线程 */
    public static boolean isRunOnMainThread() {
        return MyApplication.getMainThreadID() == Thread.currentThread().getId();
    }

    /** 提交至主线程运行 */
    public static void runOnMainThread(Runnable run) {
        if (run == null) return;
        if (isRunOnMainThread()) {
            run.run();
        } else {//是子线程，需要handler提交至主线程运行
            getHandler().post(run);
        }
    }

    public static int getWindowHeight() {
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        return point.y;
    }

    public static int getWindowWidth() {
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        return point.x;
    }

    private static WindowManager getWindowManager() {
        if (mManager == null) {
            synchronized (UIUtils.class) {
                if (mManager == null) {
                    mManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
                }
            }
        }
        return mManager;
    }


    //获取颜色
    public static int getColor(int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return getContext().getResources().getColor(id, null);
        } else {
            return getContext().getResources().getColor(id);
        }
    }
}
