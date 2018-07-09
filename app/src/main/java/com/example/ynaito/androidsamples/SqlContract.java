package com.example.ynaito.androidsamples;

/**
 * Created by yuta.n on 2018/07/10.
 */

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;


public class SqlContract extends SQLiteOpenHelper {
    private String TAG = SqlContract.class.getSimpleName();
    public static String DATABASE_NAME = "naito.db";
    public static String TABLE_NAME = "naito";
    public int DATABASE_VERSION = 1;

    public SqlContract(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    public SqlContract(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, TABLE_NAME, null, 1);
    }

    public SqlContract(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, TABLE_NAME, null, 1, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(TAG, "onCreate");
        sqLiteDatabase.execSQL(SQL_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade from " + oldVersion + " to " + newVersion);
        sqLiteDatabase.execSQL(SQL_TABLE_DROP);
        onCreate(sqLiteDatabase);
//        sqLiteDatabase.execSQL();

    }

    public static final String KEY_ID = BaseColumns._ID;
    public static final String KEY_REGISTER_DATE = "register_date";
    public static final String KEY_TITLE = "title";
    public static final String KEY_SUBTITLE = "subtitle";
    public static String SQL_TABLE_CREATE = "CREATE TABLE " + TABLE_NAME
            + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_REGISTER_DATE + " INTEGER DEFAULT 0,"
            + KEY_TITLE + " TEXT UNIQUE NOT NULL,"
            + KEY_SUBTITLE + " TEXT UNIQUE NOT NULL"
            + ")";

    public static final String SQL_TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;

}