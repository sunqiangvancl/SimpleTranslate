package xyz.mrseng.fasttranslate.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.utils.UIUtils;

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
        /*初始化科大讯飞*/
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=" + UIUtils.getString(R.string.app_id_kdxf));

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
