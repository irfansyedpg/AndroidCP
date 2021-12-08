package com.mobilisepakistanirfan.pdma.global;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class UserPref {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    final public static String  MobileNo  = "MobileNo";
    final public static String  Email  = "Email";
    final public static String  UserType  = "UserType";
    final public static String  UserStatus  = "UserStatus";

    final public static String FILE_NAME = "com.mobilisepakistan.civilprotection.global.user";

    public UserPref(Context context) {
        sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void setUserMobileNo(String param) {
        editor.putString(MobileNo, param);
        editor.apply();
    }
    public String getUserMobileNo() {
        return sharedPreferences.getString(MobileNo, "");

    }



    public void setUserEmail(String param) {
        editor.putString(Email, param);
        editor.apply();
    }
    public String getUserEmail() {
        return  sharedPreferences.getString(Email, "");

    }



    public void setUserTypel(String param) {
        editor.putString(UserType, param);
        editor.apply();
    }
    public String getUserType() {
        return  sharedPreferences.getString(UserType, "");

    }


    public void setUserUserStatus(String param) {
        editor.putString(UserStatus, param);
        editor.apply();
    }
    public String getUserStatus() {
        return  sharedPreferences.getString(UserStatus, "");

    }

}
