package com.mobilisepakistan.pdma.gps;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;

import com.mobilisepakistan.pdma.MainActivity;
import com.mobilisepakistan.pdma.R;
import com.mobilisepakistan.pdma.global.MyPref;
import com.mobilisepakistan.pdma.signup.LogIn;

public class TurnOnGPS {

    public static void turnGPSOn(final Context mContex){

        LocationManager locationManager ;
        boolean GpsStatus ;
        locationManager = (LocationManager)mContex.getSystemService(mContex.LOCATION_SERVICE);
        assert locationManager != null;
        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(GpsStatus == false) {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(mContex);
            builder1.setMessage("Enable the Device GPS Location and run the App again.");
            builder1.setCancelable(false);

            builder1.setPositiveButton(
                    "Turn on",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    mContex.startActivity(intent);

                            dialog.cancel();

                           ((Activity) mContex).finish();



                        }
                    });


            AlertDialog alert11 = builder1.create();
            alert11.show();


        }
    }
    public static boolean CheckGPS(final Context mContex){

        LocationManager locationManager ;
        boolean GpsStatus ;
        locationManager = (LocationManager)mContex.getSystemService(mContex.LOCATION_SERVICE);
        assert locationManager != null;
        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(GpsStatus == false) {

        return false;

        }

        return true;
    }


    public static void CloseActivityalerd(final Context mContex){

            AlertDialog.Builder builder1 = new AlertDialog.Builder(mContex);
            builder1.setMessage(mContex.getString(R.string.activyt_close));
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    mContex.getString(R.string.yes),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                             ((Activity) mContex).finish();




                            dialog.cancel();
                        }
                    });
            builder1.setNegativeButton(
                    mContex.getString(R.string.no),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();
                        }
                    });


            AlertDialog alert11 = builder1.create();
            alert11.show(   );



    }

    public static void LoginActivityalert(final Context mContex){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(mContex);
        builder1.setMessage("To use this feature, please longin if you have an account or create an account");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {



                        Intent intt=new Intent(mContex,LogIn.class);
                        ((Activity) mContex).startActivity(intt);

                        ((Activity) mContex).finish();


                        dialog.cancel();
                    }
                });
        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show(   );



    }

    public static void Languagealert(final Context mContex){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(mContex);
        builder1.setTitle(mContex.getString(R.string.s_h_lang));
        builder1.setMessage(mContex.getString(R.string.app_lang_header));
        builder1.setCancelable(false);
        final  MyPref preferences;

        preferences = new MyPref(mContex);
        builder1.setPositiveButton(
                mContex.getString(R.string.s_english),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {



                        preferences.setlanguage("en");
                        preferences.setcountry("US");


                        Intent intt=new Intent(mContex,MainActivity.class);
                        ((Activity) mContex).startActivity(intt);

                        ((Activity) mContex).finish();

                        dialog.cancel();
                    }
                });
        builder1.setNegativeButton(
                mContex.getString(R.string.s_urdu),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        preferences.setlanguage("ur");
                        preferences.setcountry("PK");


                        Intent intt=new Intent(mContex,MainActivity.class);
                        ((Activity) mContex).startActivity(intt);

                        ((Activity) mContex).finish();

                        dialog.cancel();
                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show(   );



    }

}
