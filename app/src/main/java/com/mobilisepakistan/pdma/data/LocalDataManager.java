package com.mobilisepakistan.pdma.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mobilisepakistan.pdma.tables.LogTable;
import com.mobilisepakistan.pdma.tables.ResponseTable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    public static String getCreateDistrictTehsil() {
        String query;
        query = "CREATE TABLE '" + "DistrictTehsil" + "' ( District TEXT,  "+
                "DistrictId "+"  TEXT,"+
                "Tehsil"+"  TEXT"+
                ")";
        return query;
    }



    public  static  void  InsertDistrictTehsil(ArrayList<String> lDistrict, ArrayList<String> lTehsil,ArrayList<String> lDistrictId,Context context )
    {

        database = new DBHelper(context).getWritableDatabase();

        if(lDistrictId.size()>0)
        {
            DeleteDistrictTehsil();
        }

        for(int i=0; i<lDistrict.size();i++) {

            String distrt=lDistrict.get(i);
            String tehsil=lTehsil.get(i);
            String id=lDistrictId.get(i);

            String query = "insert into DistrictTehsil(District,DistrictId,Tehsil)" + " values('%s', '%s', '%s') ";

            query = String.format(query, distrt, id, tehsil);
            database.execSQL(query);

        }

    }


    public  static  void  DeleteDistrictTehsil()
    {
        String query="delete from DistrictTehsil";

        query = String.format(query);
        database.execSQL(query);


    }






        public  static ArrayList<String> GetDistricts(Context context )
        {

            database = new DBHelper(context).getWritableDatabase();
            ArrayList<String> district=new ArrayList();
            String  query1 = "select distinct District from DistrictTehsil";
            Cursor c1 = database.rawQuery(query1, null);
            if (c1 != null) {
                while (c1.moveToNext()) {



                    district.add(c1.getString(0));




                }


            }
            return  district;
        //  return  Gdata;
    }

    public  static ArrayList<String> GetTehsils(String district,Context context)
    {

        database = new DBHelper(context).getWritableDatabase();

        ArrayList<String> Tehsil=new ArrayList();
        String  query1 = "select  Tehsil from DistrictTehsil where District='"+district+"'";
        Cursor c1 = database.rawQuery(query1, null);
        if (c1 != null) {
            while (c1.moveToNext()) {



                Tehsil.add(c1.getString(0));




            }


        }
        return  Tehsil;
        //  return  Gdata;
    }



    public static  int GetDistrictId(String district,Context context)
    {

        database = new DBHelper(context).getWritableDatabase();

        int districid=0;
        String  query1 = "select  distinct DistrictId from DistrictTehsil where District='"+district+"'";
        Cursor c1 = database.rawQuery(query1, null);
        if (c1 != null) {
            while (c1.moveToNext()) {



                  districid=Integer.parseInt(c1.getString(0));




            }


        }
        return  districid;
        //  return  Gdata;
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

    public  static JSONObject GetData(String logpk,int DistrictId,String DisasterType)
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


                Log=getlog(c1.getInt(0),c1.getString(1),c1.getString(2),c1.getString(3),c1.getString(4),c1.getString(5),DistrictId,DisasterType);



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


   public static   JSONObject   getlog(int UserID, String Datee,String Timee,String Lat,String Long,String Section,int DistrictId,String DisasterType ){

        JSONObject log = new JSONObject();
        try {
            log .put("UserID", UserID);
            log .put("Datee", Datee);
            log .put("Timee", Timee);
            log .put("Lat", Lat);
            log .put("Long", Long);
            log .put("Section", Section);
            log .put("DistrictId", DistrictId);
            log .put("DisasterType", DisasterType);
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
