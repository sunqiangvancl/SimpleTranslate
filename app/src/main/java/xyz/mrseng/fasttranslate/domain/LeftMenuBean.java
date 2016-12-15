package xyz.mrseng.fasttranslate.domain;

import android.app.Activity;
import android.graphics.drawable.Drawable;

/**
 * Created by MrSeng on 2016/12/15.
 */

public class LeftMenuBean {

    public LeftMenuBean(Integer index, String text, Drawable icon, Class<? extends Activity> activity) {
        this.text = text;
        this.index = index;
        this.icon = icon;
        this.activity = activity;
    }

    public String text;
    public Integer index;
    public Drawable icon;
    public Class<? extends Activity> activity;

}
