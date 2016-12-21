package xyz.mrseng.fasttranslate.service;

import java.util.ArrayList;

import xyz.mrseng.fasttranslate.dao.TransDao;
import xyz.mrseng.fasttranslate.domain.TransBean;
import xyz.mrseng.fasttranslate.global.Canstant;

/**
 * Created by MrSeng on 2016/12/15.
 * 好词好句的服务类
 */

public class MarkService {
    private TransDao mDao = new TransDao();

    /** 获取历史记录 */
    public ArrayList<TransBean> getHistory(int idx) {
        return mDao.queryList(idx);
    }

    /** 获取好词好句 */
    public ArrayList<TransBean> getMarked(int idx) {
        return mDao.queryMarkedList(idx);
    }

    /** 获取好词好句 */
    public ArrayList<TransBean> getMarkedSortedByABC(int idx) {
        return mDao.queryMarkedList(idx,Canstant.COLUMN_FROM_WORD);
    }

    /** 是否已经存在在数据库中了 */
    public boolean isInDB(TransBean trans) {
        TransBean recode = new TransBean();
        recode.toWord = trans.toWord;
        recode.fromWord = trans.fromWord;
        ArrayList<TransBean> dbResult = mDao.queryByRecode(recode);
        if (dbResult != null && dbResult.size() > 0) {
            trans.id = dbResult.get(0).id;
            return true;
        }
        return false;
    }


    /** 判断一个记录是否已经存在 */
    public boolean isMarked(TransBean trans) {
        TransBean recode = new TransBean();
        recode.toWord = trans.toWord;
        recode.fromWord = trans.fromWord;
        recode.marked = Canstant.MARKED;
        ArrayList<TransBean> dbResult = mDao.queryByRecode(recode);
        if (dbResult != null && dbResult.size() > 0) {
            trans.id = dbResult.get(0).id;
            return true;
        }
        return false;
    }

    /** 插入一个好词好句 */
    public void mark(TransBean result) {
        if (isInDB(result)) {//已经在数据库中存在，需要更新
            if (result.id != null) {
                result.marked = Canstant.MARKED;
                mDao.update(result);
            }
        } else {//数据库中没有，插入之
            if (!isMarked(result)) {
                result.marked = Canstant.MARKED;
                mDao.insert(result);
            }
        }
    }

    public void switchMark(TransBean trans) {
        trans.marked = trans.marked == Canstant.MARKED ? Canstant.UN_MARKED : Canstant.MARKED;
        if (isInDB(trans)) {//已经在数据库中存在，需要更新
            if (trans.id != null) {
                mDao.update(trans);
            }
        } else {//数据库中没有，插入之
            if (!isMarked(trans)) {
                mDao.insert(trans);
            }
        }
    }

    /** 取消收藏好词好句 确保ID */
    public void unMark(TransBean result) {
        if (result.id != null) {
            result.marked = Canstant.UN_MARKED;
            mDao.update(result);
        }
    }
}
