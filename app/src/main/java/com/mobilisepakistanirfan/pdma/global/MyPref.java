package com.mobilisepakistanirfan.pdma.global;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class MyPref {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    final public static String  USER_ID  = "userId";
    final public static String  USER_District  = "USERDistrict";

    final public static String  FRBS_NEWS  = "FRBSNEWS";
    final public static String  FRBS_VOLNT  = "FRBSVOLNT";

    final public static String  Language  = "Language";
    final public static String  Country  = "Country";
    final public static String  applunch  = "applunch";
    final public static String  LanguageDailuge  = "LanguageDailuge";


    public  static   ArrayList<String> listDistrict=new  ArrayList<String>();

    public  static   ArrayList<Integer> listDistrictid=new  ArrayList<Integer>();



    final public static String  Latitude  = "Latitude";
    final public static String  Longitudet  = "Longitudet";
    final public static String FILE_NAME = "com.mobilisepakistan.civilprotection.global";
    public MyPref(Context context) {
        sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    public void setUserDistrict(String district) {
        editor.putString(USER_District, district);
        editor.apply();
    }


    public void setFirbaseNew(String News) {
        editor.putString(FRBS_NEWS, News);
        editor.apply();
    }


    public void setFirebaseVolnt(String Volnt) {
        editor.putString(FRBS_VOLNT, Volnt);
        editor.apply();
    }
    public void setUserId(int id) {
        editor.putInt(USER_ID, id);
        editor.apply();
    }

    public void setlanguage(String lang) {
        editor.putString(Language, lang);
        editor.apply();
    }

    public String  getLanguage() {
        String id = sharedPreferences.getString(Language, "en");
        return id;
    }



    public void setlanguaDialge(Boolean lang) {
        editor.putBoolean(LanguageDailuge, lang);
        editor.apply();
    }

    public Boolean  getLanguageDilage() {
        Boolean id = sharedPreferences.getBoolean(LanguageDailuge, false);
        return id;
    }


    public void setcountry(String contr) {
        editor.putString(Country, contr);
        editor.apply();
    }

    public String  getCountry() {
        String id = sharedPreferences.getString(Country, "US");
        return id;
    }




    public String getUserDistrict() {
        String district = sharedPreferences.getString(USER_District, "");
        return district;
    }


    public String getFrbsNews() {
        String news = sharedPreferences.getString(FRBS_NEWS, "0");
        return news;
    }

    public String getFrbsVolnt() {
        String Volnt = sharedPreferences.getString(FRBS_VOLNT, "0");
        return Volnt;
    }



    public int getUserId() {
        int id = sharedPreferences.getInt(USER_ID, 0);
        return id;
    }

    public void setlat(String lat) {
        editor.putString(Latitude, lat);
        editor.apply();
    }

    public String getlat() {
        String lattti = sharedPreferences.getString(Latitude, "0");
        return lattti;
    }

    public void setlong(String longg) {
        editor.putString(Longitudet, longg);
        editor.apply();
    }

    public String getlong() {
        String longgg = sharedPreferences.getString(Longitudet, "0");
        return longgg;
    }



    public void setappcount(int count) {
        editor.putInt(applunch, count);
        editor.apply();
    }

    public int getappcount() {
        int lattti = sharedPreferences.getInt(applunch, 0);
        return lattti;
    }

    //  districts from server


//    public void setDistrict(ArrayList<String> distlst) {
//        editor.putString(listDistrict, distlst);
//        editor.apply();
//    }
//
//    public int getDistrict() {
//        int lattti = sharedPreferences.getInt(String.valueOf(applunch), 0);
//        return lattti;
//    }

}
