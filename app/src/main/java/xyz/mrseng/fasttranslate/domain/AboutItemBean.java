package xyz.mrseng.fasttranslate.domain;

/**
 * Created by MrSeng on 2016/12/15.
 * 帮助与反馈界面的列表条目
 */

public class AboutItemBean {
    public AboutItemBean(String text, String url) {
        this.text = text;
        this.url = url;
    }

    public String text;//显示文本
    public String url;

}
