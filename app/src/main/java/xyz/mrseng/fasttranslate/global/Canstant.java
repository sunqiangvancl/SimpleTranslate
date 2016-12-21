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
    String KDXF_APP_ID = "58501642";


    int REQ_CODE_START_TRANSACTIVITY = 1;
    String EXTRA_MARKED = "extra_marked_show";
    int TOKEN_NET = 1;
    int TOKEN_LOCAL = 2;
    String EXTRA_URL = "url";
    String URL_WEIBO_ME = "http://widget.weibo.com/dialog/follow.php?fuid=3936189034&refer=&language=zh_cn&type=widget_page&vsrc=app_followbutton&backurl=&rnd=1482216051769";
    long TIME_SPLASH_WAITING = 200;
    String EXTRA_SPEECH = "extra_speech";
}
