package xyz.mrseng.fasttranslate.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

/**
 * Created by MrSeng on 2016/12/12.
 * 全局的application，用以初始化整个应用的不分对象
 */

public class MyApplication extends Application {

    private static Handler mHandler;
    private static Context mContext;
    private static int mMainThreadID;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mHandler = new Handler();
        mMainThreadID = android.os.Process.myTid();//主线程ID


        SpeechUtility.createUtility(mContext, SpeechConstant.APPID + "="+Canstant.KDXF_APP_ID);

    }

    public static Context getAppContext() {
        return mContext;
    }

    public static Handler getHandler() {
        return mHandler;
    }

    public static int getMainThreadID() {
        return mMainThreadID;
    }


}
