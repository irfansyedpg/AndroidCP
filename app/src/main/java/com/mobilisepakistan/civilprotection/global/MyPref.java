package com.mobilisepakistan.civilprotection.global;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPref {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    final public static String  USER_ID  = "userId";
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

}
