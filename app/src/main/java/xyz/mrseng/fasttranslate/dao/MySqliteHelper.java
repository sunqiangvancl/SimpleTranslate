package xyz.mrseng.fasttranslate.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import xyz.mrseng.fasttranslate.global.Canstant;
import xyz.mrseng.fasttranslate.utils.UIUtils;

/**
 * Created by MrSeng on 2016/12/13.
 * 数据库工具
 */

public class MySqliteHelper extends SQLiteOpenHelper {

    public MySqliteHelper(String name) {
        super(UIUtils.getContext(), name, null, 1);
    }

    public MySqliteHelper(Context context, String name) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + Canstant.TABLE_HISTORY +
                " ( " + Canstant.COLUMN_ID + " INTEGER PRIMARY KEY autoincrement, " +
                Canstant.COLUMN_FROM_CODE + " VARCHAR(10)," +
                Canstant.COLUMN_TO_CODE + " VARCHAR(10)," +
                Canstant.COLUMN_FROM_WORD + " VARCHAR(200)," +
                Canstant.COLUMN_TO_WORD + " VARCHAR(200)," +
                Canstant.COLUMN_MARKED + " INTEGER," +
                Canstant.COLUMN_TIME + " LONG)";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
