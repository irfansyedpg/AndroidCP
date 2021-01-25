package com.example.myapplication.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapplication.tables.ResponseTable;

import java.util.ArrayList;
import java.util.HashMap;


public class LocalDataManager {
   public static SQLiteDatabase database;
    public  static  String Table2="ResponseTable";
    Context mContext;


    public LocalDataManager(Context context) {
        this.mContext = context;
        database = new DBHelper(context).getWritableDatabase();
    }

    public static String getCreateQueryResponseTable() {
        String query;
        query = "CREATE TABLE '" + Table2 + "' ("+ ResponseTable.AppPk+" INTEGER PRIMARY KEY AUTOINCREMENT, "+                "A2 TEXT, B1 TEXT, B2 TEXT, C1 TEXT," +
              ResponseTable.FK+"  INTEGER,"+
              ResponseTable.VarName+"  TEXT,"+
              ResponseTable.Response+"  TEXT,"+
              ResponseTable.Section+"  TEXT "+
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
}
