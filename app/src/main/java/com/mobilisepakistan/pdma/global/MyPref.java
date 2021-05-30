package com.mobilisepakistan.pdma.global;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPref {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    final public static String  USER_ID  = "userId";
    final public static String  Latitude  = "Latitude";
    final public static String  Longitudet  = "Longitudet";
    final public static String FILE_NAME = "com.mobilisepakistan.civilprotection.global";
    public MyPref(Context context) {
        sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setUserId(int id) {
        editor.putInt(USER_ID, id);
        editor.apply();
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

}
