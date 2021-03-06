package xyz.mrseng.fasttranslate.global;

/**
 * Created by MrSeng on 2016/12/13.
 * 常量
 */

public interface Canstant {
    String COLUMN_FROM_CODE = "_fc";
    String COLUMN_FROM_WORD = "_fw";
    String COLUMN_TO_CODE = "_tc";
    String COLUMN_TO_WORD = "_tw";
    String COLUMN_ID = "_id";
    String COLUMN_TIME = "_time";
    String COLUMN_MARKED = "_mark";
    String TABLE_HISTORY = "tb_history";
    int LIMIT_PAGE_SIZE = 10;
    int UN_MARKED = 0;
    int MARKED = 1;
    String EXTRA_MARKED = "extra_marked_show";
    String EXTRA_URL = "url";
}
