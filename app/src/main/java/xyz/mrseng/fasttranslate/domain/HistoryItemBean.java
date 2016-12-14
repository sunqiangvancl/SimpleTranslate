package xyz.mrseng.fasttranslate.domain;

import android.content.ContentValues;

import java.io.Serializable;

import xyz.mrseng.fasttranslate.global.Canstant;

/**
 * Created by MrSeng on 2016/12/13.
 * 历史记录对象
 */

public class HistoryItemBean implements Serializable {
    public String fromCode;
    public String toCode;
    public String fromWord;
    public String toWord;
    public Long time;
    public Integer marked;//0表示为收藏
    public Integer id;

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
        return "HistoryItemBean{" +
                "fromCode='" + fromCode + '\'' +
                ", toCode='" + toCode + '\'' +
                ", fromWord='" + fromWord + '\'' +
                ", toWord='" + toWord + '\'' +
                ", time=" + time +
                ", marked=" + marked +
                ", id=" + id +
                '}';
    }
}
