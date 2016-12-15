package xyz.mrseng.fasttranslate.domain;

/**
 * Created by MrSeng on 2016/12/15.
 */

public class SettingItemBean {
    public SettingItemBean(String title, String desc, Boolean toggle) {
        this.title = title;
        this.desc = desc;
        this.toggle = toggle;
    }

    public String title;
    public String desc;
    public Boolean toggle;
}
