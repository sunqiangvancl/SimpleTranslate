package xyz.mrseng.fasttranslate.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import xyz.mrseng.fasttranslate.domain.TransBean;
import xyz.mrseng.fasttranslate.global.Canstant;

/**
 * Created by MrSeng on 2016/12/13.
 * 查询历史持久层
 */

public class TransDao {
    private MySqliteHelper mDbHelper;
    private static final String TABLE_NAME = Canstant.TABLE_HISTORY;
    private static final String ID = Canstant.COLUMN_ID;
    private static final String COLUMN_TIME = Canstant.COLUMN_TIME;
    private static final String COLUMN_MARKED = Canstant.COLUMN_MARKED;

    public TransDao() {
        mDbHelper = new MySqliteHelper(TABLE_NAME);
    }

    public TransDao(Context context) {
        mDbHelper = new MySqliteHelper(context, TABLE_NAME);
    }

    /*插入*/
    public synchronized boolean insert(TransBean history) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long r = db.insert(TABLE_NAME, null, history.getContentValues());
        db.close();
        if (r != -1) {
            history.id = (int) r;
        }
        return r != -1;
    }

    /*删除*/
    public synchronized boolean delete(Integer id) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int r = db.delete(TABLE_NAME, ID + "=?", new String[]{id + ""});
        db.close();
        return r == 1;
    }

    /*更新*/
    public synchronized boolean update(TransBean history) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int r = db.update(TABLE_NAME, history.getNotNullContentValues(), ID + "=?", new String[]{history.id + ""});
        db.close();
        return r == 1;
    }

    /*查询单个*/
    public synchronized TransBean queryOne(Integer id) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Cursor query = db.query(TABLE_NAME, null, ID + "=?", new String[]{id + ""}, null, null, COLUMN_TIME + " desc");
        TransBean result = parseOne(query);
        db.close();
        return result;
    }

    /*根据条件查找*/
    public synchronized ArrayList<TransBean> queryByRecode(TransBean recode) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        String selection = getSelection(recode);
        String[] selectArg = getSelectArgs(recode);
        Cursor cursor = db.query(Canstant.TABLE_HISTORY, null, selection, selectArg, null, null, COLUMN_TIME + " desc");
        ArrayList<TransBean> result = parseList(cursor);
        db.close();
        return result;
    }


    /*分页查找历史记录数据*/
    public synchronized ArrayList<TransBean> queryList(int page) {
        ArrayList<TransBean> result = null;
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Cursor query = db.query(TABLE_NAME, null, null, null, null, null, COLUMN_TIME + " desc", page + "," + Canstant.LIMIT_PAGE_SIZE);
        result = parseList(query);
        db.close();
        return result;
    }


    /*分页查找好词好句*/
    public synchronized ArrayList<TransBean> queryMarkedList(int page) {
        ArrayList<TransBean> result = null;
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Cursor query = db.query(TABLE_NAME, null, COLUMN_MARKED + " = " + 1, null, null, null, COLUMN_TIME + " desc", page + "," + Canstant.LIMIT_PAGE_SIZE);
        result = parseList(query);
        query.close();
        db.close();
        return result;
    }
    public synchronized ArrayList<TransBean> queryMarkedList(int page,String ordrby) {
        ArrayList<TransBean> result = null;
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        Cursor query = db.query(TABLE_NAME, null, COLUMN_MARKED + " = " + 1, null, null, null, ordrby + " desc", page + "," + Canstant.LIMIT_PAGE_SIZE);
        result = parseList(query);
        query.close();
        db.close();
        return result;
    }

    /**
     * 解析Cursor至对象列表
     * 返回对象列表或null，不存在size为0的情况
     */
    private ArrayList<TransBean> parseList(Cursor cursor) {
        ArrayList<TransBean> result = new ArrayList<>();
        while (cursor != null && cursor.moveToNext()) {
            TransBean bean = new TransBean();
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

    /**
     * 解析单个Cursor对象
     * 解析失败返回null，成功返回对象
     */
    private TransBean parseOne(Cursor cursor) {
        if (cursor != null && cursor.moveToFirst()) {
            TransBean bean = new TransBean();
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


    /* 根据条件组织查询参数 */
    private String[] getSelectArgs(TransBean recode) {
        ArrayList<String> args = new ArrayList<>();
        if (recode.fromCode != null) {
            args.add(recode.fromCode);
        }
        if (recode.toCode != null) {
            args.add(recode.toCode);
        }
        if (recode.fromWord != null) {
            args.add(recode.fromWord);
        }
        if (recode.toWord != null) {
            args.add(recode.toWord);
        }
        if (recode.id != null) {
            args.add(recode.id + "");
        }
        if (recode.marked != null) {
            args.add(recode.marked + "");
        }
        if (recode.time != null) {
            args.add(recode.time + "");
        }
        return args.toArray(new String[0]);
    }

    /** 组织查询语句 */
    private String getSelection(TransBean recode) {
        StringBuilder sb = new StringBuilder("1 = 1 ");
        if (recode.fromCode != null) {
            sb.append(" and " + Canstant.COLUMN_FROM_CODE + " = " + "?");
        }
        if (recode.toCode != null) {
            sb.append(" and " + Canstant.COLUMN_TO_CODE + " = " + "?");
        }
        if (recode.fromWord != null) {
            sb.append(" and " + Canstant.COLUMN_FROM_WORD + " = " + "?");
        }
        if (recode.toWord != null) {
            sb.append(" and " + Canstant.COLUMN_TO_WORD + " = " + "?");
        }
        if (recode.id != null) {
            sb.append(" and " + Canstant.COLUMN_ID + " = " + "?");
        }
        if (recode.marked != null) {
            sb.append(" and " + Canstant.COLUMN_MARKED + " = " + "?");
        }
        if (recode.time != null) {
            sb.append(" and " + Canstant.COLUMN_TIME + " = " + "?");
        }
        return sb.toString();
    }
}
