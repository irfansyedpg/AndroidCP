package com.example.myapplication.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.global.GlobalResponseData;
import com.example.myapplication.tables.LogTable;
import com.example.myapplication.tables.ResponseTable;

import java.util.ArrayList;
import java.util.HashMap;


public class LocalDataManager {
   public static SQLiteDatabase database;
    public  static  String Table1="LogTable";
    public  static  String Table2="ResponseTable";

    Context mContext;


    public LocalDataManager(Context context) {
        this.mContext = context;
        database = new DBHelper(context).getWritableDatabase();
    }

    public static String getCreateQueryResponseTable() {
        String query;
        query = "CREATE TABLE '" + Table2 + "' ("+ ResponseTable.AppPk+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
              ResponseTable.FK+"  INTEGER,"+
              ResponseTable.VarName+"  TEXT,"+
              ResponseTable.Response+"  TEXT,"+
              ResponseTable.Section+"  TEXT "+
                ")";
        return query;
    }
    public static String getCreateQueryLogTable() {
        String query;
        query = "CREATE TABLE '" + Table1 + "' ("+ LogTable.LogPk+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                LogTable.UserId+"  TEXT,"+
                LogTable.Datee+"  TEXT,"+
                LogTable.Timee+"  TEXT,"+
                LogTable.Lat+"  TEXT,"+
                LogTable.Long+"  TEXT,"+
                LogTable.Section+"  TEXT,"+
                LogTable.Status+"  TEXT "+
                ")";
        return query;
    }

    public  static  void  InsertRespnoseTable(int FK, HashMap<String, String> Data, String Section )
    {
        String VarName;
        String Response;

        for (String key: Data.keySet()) {

            VarName=key;
            Response=Data.get(VarName);

            String query="insert into "+Table2+" ("+ResponseTable.FK+","+ResponseTable.VarName+","+ResponseTable.Response+","+ResponseTable.Section+")"
                    +" values('%s', '%s', '%s', '%s') ";

            query = String.format(query,FK,VarName,Response,Section);
            database.execSQL(query);

        }


    }

    public  static GlobalResponseData GetData()
    {
        String  query = "SELECT "+ResponseTable.FK+","+ResponseTable.VarName+","+ResponseTable.Response+","+ResponseTable.Section+" from "+ Table2;

        Cursor c = database.rawQuery(query, null);

        GlobalResponseData Gdata=new GlobalResponseData();
        if (c != null) {
            while (c.moveToNext()) {

                Gdata.FK.add(c.getString(0));
                Gdata.VarName.add(c.getString(1));
                Gdata.Response.add(c.getString(2));
                Gdata.Section.add(c.getString(3));

            }
        }

        return  Gdata;
    }

}
