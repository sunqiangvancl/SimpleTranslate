package xyz.mrseng.fasttranslate.domain;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.utils.UIUtils;

/**
 * Created by MrSeng on 2016/12/17.
 */

public class PpwMoreBean {
    public PpwMoreBean(int item) {
        this.item = item;
        initText(item);
    }

    private void initText(int item) {
        this.item = item;
        switch (item) {
            case ITEM_FULLSCREEN:
                text = UIUtils.getString(R.string.full_screen);
                break;
            case ITEM_SHARE:
                text = UIUtils.getString(R.string.share);
                break;
            case ITEM_JINSHAN:
                text = UIUtils.getString(R.string.jinshan);
                break;
            case ITEM_YOUDAO:
                text = UIUtils.getString(R.string.youdao);
                break;
            case ITEM_BAIDU:
                text = UIUtils.getString(R.string.baidu);
                break;
            case ITEM_BIYING:
                text = UIUtils.getString(R.string.biying);
                break;
            case ITEM_REFRESH:
                text = UIUtils.getString(R.string.refresh);
                break;
            case ITEM_INVERSE:
                text = UIUtils.getString(R.string.inverse_trans);
                break;
        }
    }

    public String text;
    public Integer item;


    public static final int ITEM_FULLSCREEN = 0;
    public static final int ITEM_SHARE = 1;
    public static final int ITEM_JINSHAN = 2;
    public static final int ITEM_YOUDAO = 3;
    public static final int ITEM_BAIDU = 4;
    public static final int ITEM_BIYING = 5;
    public static final int ITEM_REFRESH = 6;
    public static final int ITEM_INVERSE = 7;

}