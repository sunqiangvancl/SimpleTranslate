package xyz.mrseng.fasttranslate.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by MrSeng on 2016/12/18.
 */

public class DataUtils {

    public static <T> ArrayList<T> getListByMap(Map<Object, T> map) {
        ArrayList<T> list = new ArrayList<>();

        return list;
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


}
