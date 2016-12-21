package xyz.mrseng.fasttranslate.domain;

import android.content.ContentValues;
import android.content.Context;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.global.Canstant;
import xyz.mrseng.fasttranslate.utils.SPUtils;
import xyz.mrseng.fasttranslate.utils.UIUtils;

/**
 * Created by MrSeng on 2016/12/13.
 * 历史记录对象
 */

public class TransBean implements Serializable, Comparable<TransBean> {
    public TransBean() {
        this.site = SPUtils.getString(SPUtils.KEY_FROM_SITE, UIUtils.getString(R.string.baidu));
    }

    public String fromCode;
    public String toCode;
    public String fromWord;
    public String toWord;
    public Long time;
    public Integer marked;//0表示为收藏
    public Integer id;
    public String site;
    public Integer token;//标记当前的翻译对象是来自哪里的


    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(Canstant.COLUMN_FROM_CODE, fromCode);
        values.put(Canstant.COLUMN_FROM_WORD, fromWord);
        values.put(Canstant.COLUMN_TO_CODE, toCode);
        values.put(Canstant.COLUMN_TO_WORD, toWord);
        values.put(Canstant.COLUMN_TIME, time);
        values.put(Canstant.COLUMN_MARKED, marked);
        return values;
    }

    public ContentValues getNotNullContentValues() {
        ContentValues values = new ContentValues();
        if (fromCode != null)
            values.put(Canstant.COLUMN_FROM_CODE, fromCode);
        if (fromWord != null)
            values.put(Canstant.COLUMN_FROM_WORD, fromWord);
        if (toCode != null)
            values.put(Canstant.COLUMN_TO_CODE, toCode);
        if (toWord != null)
            values.put(Canstant.COLUMN_TO_WORD, toWord);
        if (time != null)
            values.put(Canstant.COLUMN_TIME, time);
        if (marked != null)
            values.put(Canstant.COLUMN_MARKED, marked);

        return values;
    }

    @Override
    public String toString() {
        return "TransBean{" +
                "fromCode='" + fromCode + '\'' +
                ", toCode='" + toCode + '\'' +
                ", fromWord='" + fromWord + '\'' +
                ", toWord='" + toWord + '\'' +
                ", time=" + time +
                ", marked=" + marked +
                ", id=" + id +
                '}';
    }

    public void swapFromToCode() {
        if (!"auto".equals(fromCode)) {
            String t = this.fromCode;
            this.fromCode = this.toCode;
            toCode = t;
        }
    }


    /** 一些静态常量 */

    public static final int LANG_CODE_AUTO = 0;
    public static final int LANG_CODE_ZH = 1;
    public static final int LANG_CODE_EN = 2;
    public static final int LANG_CODE_YUE = 3;
    public static final int LANG_CODE_WYW = 4;
    public static final int LANG_CODE_JP = 5;
    public static final int LANG_CODE_KOR = 6;
    public static final int LANG_CODE_FRA = 7;
    public static final int LANG_CODE_SPA = 8;
    public static final int LANG_CODE_CHT = 9;
    public static final int LANG_CODE_ARA = 10;
    public static final int LANG_CODE_RU = 11;
    public static final int LANG_CODE_PT = 12;
    public static final int LANG_CODE_DE = 13;
    public static final int LANG_CODE_IT = 14;

    public static String getLangCodeStr(int lang_code) {
        return UIUtils.getStringArray(R.array.lang_code)[lang_code];
    }

    public static String getLangNameStr(int lang_code) {
        return UIUtils.getStringArray(R.array.lang_name)[lang_code];
    }

    public static int getCodeIntByCodeStr(String codeStr) {
        Map<Integer, String> mapCode = getAllLangCodeMap();
        for (Map.Entry<Integer, String> entry : mapCode.entrySet()) {
            if (entry.getValue().equals(codeStr)) {
                return entry.getKey();
            }
        }
        Map<Integer, String> mapName = getAllLangNameMap();
        for (Map.Entry<Integer, String> entry : mapName.entrySet()) {
            if (entry.getValue().equals(codeStr)) {
                return entry.getKey();
            }
        }
        return -1;
    }

    public static Map<Integer, String> getAllLangCodeMap() {
        Map<Integer, String> map = new LinkedHashMap<>();
        for (int i = 0; i < LANG_CODE_IT; i++) {
            map.put(i, getLangCodeStr(i));
        }
        return map;
    }

    public static Map<Integer, String> getAllLangNameMap() {
        Map<Integer, String> map = new LinkedHashMap<>();
        for (int i = 0; i < LANG_CODE_IT; i++) {
            map.put(i, getLangNameStr(i));
        }
        return map;
    }

    /** 给出Word，得到code，给出code得到Word */
    public static String switchCodeAndWord(Context context, String wordOrCode) {
        String[] names = context.getResources().getStringArray(R.array.lang_name);
        String[] codes = context.getResources().getStringArray(R.array.lang_code);
        if (names.length == codes.length) {
            for (int i = 0; i < names.length; i++) {
                if (names[i].equals(wordOrCode)) {
                    return codes[i];
                } else if (codes[i].equals(wordOrCode)) {
                    return names[i];
                }
            }
        }
        return null;
    }

    public static String switchCodeAndWord(String wordOrCode) {
        return switchCodeAndWord(UIUtils.getContext(), wordOrCode);
    }


    @Override
    public int compareTo(TransBean o) {
        if (o.fromWord == null || this.fromWord == null) {
            return 0;
        }
        return this.fromWord.compareTo(o.fromWord);
    }
}
