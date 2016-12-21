package xyz.mrseng.fasttranslate.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by MrSeng on 2016/12/15.
 * SharedPreferences工具类
 */

public class SPUtils {
    public static final String SP_CONFIG = "config";
    public static final String KEY_ENABLE_CLICK_TRANS = "key_enable_click_trans";
    public static final String KEY_SHOW_NOTICE = "key_show_notice";
    public static final String KEY_FIRST_FROM_CODE = "key_first_from_code";
    public static final String KEY_FIRST_TO_CODE = "key_first_to_code";
    public static final String KEY_AUTO_READ = "key_auto_read";
    public static final String KEY_TRANS_NOW = "key_trans_now";
    public static final String KEY_FROM_SITE = "key_from_site";
    public static final String KEY_SORT_BY_TIME = "key_sort_by_time";
    private static SharedPreferences sp = UIUtils.getContext().getSharedPreferences(SP_CONFIG, Context.MODE_PRIVATE);

    public static Boolean getBoolean(String key, Boolean def) {
        return sp.getBoolean(key, def);
    }

    public static void setBoolean(String key, Boolean val) {
        sp.edit().putBoolean(key, val).apply();//apply是异步的
    }

    public static String getString(String key, String def) {
        return sp.getString(key, def);
    }

    public static void setString(String key, String val) {
        sp.edit().putString(key, val).apply();//apply是异步的
    }



}
