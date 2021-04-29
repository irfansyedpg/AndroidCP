package com.mobilisepakistan.civilprotection.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mobilisepakistan.civilprotection.tables.LogTable;
import com.mobilisepakistan.civilprotection.tables.ResponseTable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    public  static  String  InsertLogTable(String Userid,String Lat,String Long,String secion,Context context )
    {
        database = new DBHelper(context).getWritableDatabase();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();


        Date date = new Date();


        String Datee=dateFormat.format(date);
        String Timee= dateFormat.format(cal.getTime());




            String query="insert into "+Table1+" ("+LogTable.UserId+","+LogTable.Timee+","+LogTable.Datee+","+LogTable.Lat+

                    ","+LogTable.Long+","+LogTable.Section+","+LogTable.Status+")"
                    +" values('%s', '%s', '%s', '%s', '%s', '%s', '%s') ";

            query = String.format(query,Userid,Timee,Datee,Lat,Long,secion,"0");
            database.execSQL(query);


            // get last inserted row

        query = "SELECT "+LogTable.LogPk +" from "+ Table1 +" ORDER BY "+LogTable.LogPk+" DESC LIMIT 1";

        Cursor c = database.rawQuery(query, null);

        if (c != null) {
            while (c.moveToNext()) {


                return c.getString(0);

            }
        }


return  "0";
    }

    public  static  void  UpdateLOgtable(String PK )
    {
            String query="update "+Table1+" set "+LogTable.Status+"='1'"+" where "+LogTable.LogPk+"="+PK;
            query = String.format(query);
            database.execSQL(query);


    }

    public  static JSONObject GetData(String logpk)
    {
        JSONArray Resp = new JSONArray();
        JSONObject Log = new JSONObject();
        String Logpk;
    //    Log=getlog(1,"date","time","lat","long","section");



        String  query1 = "SELECT "+LogTable.UserId+","+LogTable.Datee+","+LogTable.Timee+","+LogTable.Lat
                +","+LogTable.Long+","+LogTable.Section+","+LogTable.LogPk +" from "+ Table1+" where "+LogTable.Status+"='0' and "+LogTable.LogPk+" = "+logpk;
        Cursor c1 = database.rawQuery(query1, null);
        if (c1 != null) {
            while (c1.moveToNext()) {


                Log=getlog(c1.getInt(0),c1.getString(1),c1.getString(2),c1.getString(3),c1.getString(4),c1.getString(5));



                String  query = "SELECT "+ResponseTable.FK+","+ResponseTable.VarName+","+ResponseTable.Response+","+ResponseTable.Section+" from "+ Table2 +" where "+ResponseTable.FK+"="+logpk;
                Cursor c = database.rawQuery(query, null);
                if (c != null) {
                    while (c.moveToNext()) {
                        Resp.put(getResponce(c.getString(2),c.getString(1),c.getString(3)));
                    }
                }
            }
        }


        JSONObject response= new JSONObject();

        try {
            response.put("logs", Log );
            response.put("responseTables", Resp );
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return response;

      //  return  Gdata;
    }


   public static   JSONObject   getlog(int UserID, String Datee,String Timee,String Lat,String Long,String Section){

        JSONObject log = new JSONObject();
        try {
            log .put("UserID", UserID);
            log .put("Datee", Datee);
            log .put("Timee", Timee);
            log .put("Lat", Lat);
            log .put("Long", Long);
            log .put("Section", Section);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return log ;
    }
 public static   JSONObject  getResponce(String Response, String VarName,String Section){

        JSONObject Resp = new JSONObject();
        try {
            Resp .put("Response", Response);
            Resp .put("VarName", VarName);
            Resp .put("Section", Section);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return Resp ;
    }



}
