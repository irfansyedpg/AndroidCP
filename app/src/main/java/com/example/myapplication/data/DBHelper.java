package com.example.myapplication.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "cpapp.db";
    private static final int VERSION = 1;

    Context mContext;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.beginTransaction();

        db.execSQL(RDTableS.getCreateQuery());
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        String query;
        db.beginTransaction();


        query = "DROP TABLE IF EXISTS " + RDTableS.TABLE_NAME;
        db.execSQL(query);

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public Cursor execRAW(String query) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(query, null);
        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.d(DBHelper.class.getName(), " Exception while executing Query");
        }
        return cursor;
    }
}
