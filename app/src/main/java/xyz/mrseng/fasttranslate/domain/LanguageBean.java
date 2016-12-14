package xyz.mrseng.fasttranslate.domain;

import java.io.Serializable;

/**
 * Created by MrSeng on 2016/12/13.
 * 语言翻译封装类
 */

public class LanguageBean implements Serializable {
    public String fromCode;
    public String toCode;

    public void swapFromTo() {
        if (!"auto".equals(fromCode)) {
            String t = this.fromCode;
            this.fromCode = this.toCode;
            toCode = t;
        }
    }
}
