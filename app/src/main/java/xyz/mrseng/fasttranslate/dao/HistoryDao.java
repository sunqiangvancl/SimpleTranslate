package xyz.mrseng.fasttranslate.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import xyz.mrseng.fasttranslate.domain.HistoryItemBean;
import xyz.mrseng.fasttranslate.global.Canstant;

/**
 * Created by MrSeng on 2016/12/13.
 * 查询历史持久层
 */

public class HistoryDao {
    private MySqliteHelper mDbHelper;
    private static final String TABLE_NAME = Canstant.TABLE_HISTORY;
    private static final String ID = Canstant.COLUMN_ID;
    private static final String COLUMN_TIME = Canstant.COLUMN_TIME;
    private static final String COLUMN_MARKED = Canstant.COLUMN_MARKED;

    public HistoryDao() {
        mDbHelper = new MySqliteHelper(TABLE_NAME);
    }

    public HistoryDao(Context context) {
        mDbHelper = new MySqliteHelper(context, TABLE_NAME);
    }

    /*插入*/
    public boolean insert(HistoryItemBean history) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long r = db.insert(TABLE_NAME, null, history.getContentValues());
        db.close();
        return r == 1;
    }

    /*插入*/
    public boolean delete(Integer id) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int r = db.delete(TABLE_NAME, ID + "=?", new String[]{id + ""});
        db.close();
        return r == 1;
    }

    /*更新*/
    public boolean update(HistoryItemBean history) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int r = db.update(TABLE_NAME, history.getNotNullContentValues(), ID + "=?", new String[]{history.id + ""});
        db.close();
        return r == 1;
    }

    /*查询单个*/
    public HistoryItemBean queryOne(Integer id) {
        HistoryItemBean result = null;
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Cursor query = db.query(TABLE_NAME, null, ID + "=?", new String[]{id + ""}, null, null, COLUMN_TIME + " desc");
        result = parseOne(query);
        db.close();
        return result;
    }


    public ArrayList<HistoryItemBean> queryList(int page) {
        ArrayList<HistoryItemBean> result = null;
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Cursor query = db.query(TABLE_NAME, null, null, null, null, null, COLUMN_TIME + " desc", page + "," + Canstant.LIMIT_PAGE_SIZE);
        result = parseList(query);
        db.close();
        return result;
    }

    public ArrayList<HistoryItemBean> queryMarkedList(int page) {
        ArrayList<HistoryItemBean> result = null;
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Cursor query = db.query(TABLE_NAME, null, COLUMN_MARKED + " = " + 1, null, null, null, COLUMN_TIME + " desc", page + "," + Canstant.LIMIT_PAGE_SIZE);
        result = parseList(query);
        db.close();
        return result;
    }

    private ArrayList<HistoryItemBean> parseList(Cursor cursor) {
        ArrayList<HistoryItemBean> result = new ArrayList<>();
        while (cursor != null && cursor.moveToNext()) {
            HistoryItemBean bean = new HistoryItemBean();
            bean.fromCode = cursor.getString(cursor.getColumnIndex(Canstant.COLUMN_FROM_CODE));
            bean.toCode = cursor.getString(cursor.getColumnIndex(Canstant.COLUMN_TO_CODE));
            bean.fromWord = cursor.getString(cursor.getColumnIndex(Canstant.COLUMN_FROM_WORD));
            bean.toWord = cursor.getString(cursor.getColumnIndex(Canstant.COLUMN_TO_WORD));
            bean.time = cursor.getLong(cursor.getColumnIndex(Canstant.COLUMN_TIME));
            bean.id = cursor.getInt(cursor.getColumnIndex(Canstant.COLUMN_ID));
            bean.marked = cursor.getInt(cursor.getColumnIndex(Canstant.COLUMN_MARKED));
            result.add(bean);
        }
        return result.size() == 0 ? null : result;
    }


    private HistoryItemBean parseOne(Cursor cursor) {
        if (cursor != null && cursor.moveToFirst()) {
            HistoryItemBean bean = new HistoryItemBean();
            bean.fromCode = cursor.getString(cursor.getColumnIndex(Canstant.COLUMN_FROM_CODE));
            bean.toCode = cursor.getString(cursor.getColumnIndex(Canstant.COLUMN_TO_CODE));
            bean.fromWord = cursor.getString(cursor.getColumnIndex(Canstant.COLUMN_FROM_WORD));
            bean.toWord = cursor.getString(cursor.getColumnIndex(Canstant.COLUMN_TO_WORD));
            bean.time = cursor.getLong(cursor.getColumnIndex(Canstant.COLUMN_TIME));
            bean.marked = cursor.getInt(cursor.getColumnIndex(Canstant.COLUMN_MARKED));
            bean.id = cursor.getInt(cursor.getColumnIndex(Canstant.COLUMN_ID));
            return bean;
        }
        return null;
    }


}
