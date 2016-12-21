package xyz.mrseng.fasttranslate.domain;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.ui.activity.SettingActivity;
import xyz.mrseng.fasttranslate.utils.SPUtils;
import xyz.mrseng.fasttranslate.utils.TransUtils;
import xyz.mrseng.fasttranslate.utils.UIUtils;

/**
 * Created by MrSeng on 2016/12/15.
 */

public class SettingItemBean {
    private SettingActivity mActivity;

    public SettingItemBean(SettingActivity settingActivity, int item) {
        mActivity = settingActivity;
        this.item = item;
        switch (item) {
            case ITEM_CLICK_TRANS://点按翻译
                title = UIUtils.getString(R.string.setting_enable_click_trans);
                desc = null;
                this.key_toggle = SPUtils.KEY_ENABLE_CLICK_TRANS;
                toggle = SPUtils.getBoolean(key_toggle, false);
                break;
            case ITEM_SHOW_NOTICE:
                title = UIUtils.getString(R.string.setting_show_notice);
                desc = null;
                this.key_toggle = SPUtils.KEY_SHOW_NOTICE;
                toggle = SPUtils.getBoolean(key_toggle, false);
                break;
            case ITEM_FIRST_LANG:
                String fromCode = SPUtils.getString(SPUtils.KEY_FIRST_FROM_CODE,
                        UIUtils.getStringArray(R.array.lang_code)[0]);//默认自动检测
                String fromLang = TransUtils.switchCodeAndWord(fromCode);
                String toCode = SPUtils.getString(SPUtils.KEY_FIRST_TO_CODE,
                        UIUtils.getStringArray(R.array.lang_code)[2]);//默认英语
                String toLang = TransUtils.switchCodeAndWord(toCode);
                String desc_lang = fromLang + "," + toLang;
                title = UIUtils.getString(R.string.setting_first_lang);
                desc = desc_lang;
                toggle = null;
                break;
            case ITEM_AUTO_READ:
                title = UIUtils.getString(R.string.setting_output_voice);
                desc = null;
                this.key_toggle = SPUtils.KEY_AUTO_READ;
                toggle = SPUtils.getBoolean(key_toggle, false);
                break;
            case ITEM_TRANS_NOW:
                title = UIUtils.getString(R.string.setting_enable_trans_now);
                desc = null;
                this.key_toggle = SPUtils.KEY_TRANS_NOW;
                toggle = SPUtils.getBoolean(key_toggle, false);
                break;
            case ITEM_FROM_SITE:
                title = UIUtils.getString(R.string.setting_from_site);
                desc = SPUtils.getString(SPUtils.KEY_FROM_SITE, UIUtils.getString(R.string.baidu));
                toggle = null;
                break;
        }


    }

    public String title;
    public String desc;
    public Boolean toggle;
    public int item;
    private String key_toggle;
    public static final int ITEM_CLICK_TRANS = 0;
    public static final int ITEM_SHOW_NOTICE = 1;
    public static final int ITEM_FIRST_LANG = 2;
    public static final int ITEM_AUTO_READ = 3;
    public static final int ITEM_TRANS_NOW = 4;
    public static final int ITEM_FROM_SITE = 5;

    public void switchToggle() {
        if (toggle != null && key_toggle != null) {
            toggle = !toggle;
            SPUtils.setBoolean(key_toggle, toggle);
            System.out.println("----switch---" + title + "--" + !toggle + "---->" + toggle);
        }
    }


}
