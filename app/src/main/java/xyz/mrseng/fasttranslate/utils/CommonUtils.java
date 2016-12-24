package xyz.mrseng.fasttranslate.utils;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Binder;

import java.lang.reflect.Method;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by MrSeng on 2016/12/12.
 * 公共工具类
 */

public class CommonUtils {

    /*向剪贴板复制文本*/
    public static void copyText(String text) {
        ClipboardManager mClipManager = (ClipboardManager) UIUtils.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData data = ClipData.newPlainText("simple text", text);
        mClipManager.setPrimaryClip(data);
    }

    //当前activity是否是本应用的
    public static boolean isInSelfActivity() {
        ActivityManager am = (ActivityManager) UIUtils.getContext().getSystemService(ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String packageName = cn.getPackageName();
        if (packageName != null && packageName.equals(UIUtils.getContext().getPackageName())) {
            return true;
        }
        return false;
    }


    /*获取当前版本号*/
    public static String getCurrVersion() {
        PackageManager manager = UIUtils.getContext().getPackageManager();
        try {
            PackageInfo pi = manager.getPackageInfo(UIUtils.getContext().getPackageName(), 0);
            String versionName = pi.versionName;
            return versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*判断悬浮窗权限是否可用*/
    public static boolean isGotAppOps() {
        try {
            Object object = UIUtils.getContext().getSystemService(Context.APP_OPS_SERVICE);
            if (object == null) {
                return false;
            }
            Class localClass = object.getClass();
            Class[] arrayOfClass = new Class[3];
            arrayOfClass[0] = Integer.TYPE;
            arrayOfClass[1] = Integer.TYPE;
            arrayOfClass[2] = String.class;
            Method method = localClass.getMethod("checkOp", arrayOfClass);
            if (method == null) {
                return false;
            }
            Object[] arrayOfObject1 = new Object[3];
            arrayOfObject1[0] = Integer.valueOf(24);
            arrayOfObject1[1] = Integer.valueOf(Binder.getCallingUid());
            arrayOfObject1[2] = UIUtils.getContext().getPackageName();
            int m = ((Integer) method.invoke(object, arrayOfObject1)).intValue();
            return m == AppOpsManager.MODE_ALLOWED;
        } catch (Exception ex) {

        }
        return false;
    }

    public static boolean equeals(Object a, Object b) {
        if (a == null && b == null) {
            return true;
        } else if (a == null || b == null) {
            return false;
        }
        return a.equals(b);
    }
}
