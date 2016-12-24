package xyz.mrseng.fasttranslate.engine.protocol;

import java.util.ArrayList;

import xyz.mrseng.fasttranslate.dao.TransDao;
import xyz.mrseng.fasttranslate.domain.TransBean;

/**
 * Created by MrSeng on 2016/12/16.
 */

public class HistoryService {
    private TransDao mDao = new TransDao();

    /*是否已经存储过了*/
    private boolean isSaved(TransBean recode) {
        TransBean bean = new TransBean();
        bean.fromWord = recode.fromWord;
        bean.toWord = recode.toWord;
        ArrayList<TransBean> dbResults = mDao.queryByRecode(bean);
        if (dbResults != null && dbResults.size() > 0) {
            return true;
        }
        return false;
    }

}
