package com.mobilisepakistan.civilprotection.gps;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;

import com.mobilisepakistan.civilprotection.signup.LogIn;

public class TurnOnGPS {

    public static void turnGPSOn(final Context mContex){

        LocationManager locationManager ;
        boolean GpsStatus ;
        locationManager = (LocationManager)mContex.getSystemService(mContex.LOCATION_SERVICE);
        assert locationManager != null;
        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(GpsStatus == false) {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(mContex);
            builder1.setMessage("Your Mobile GPS is disabled Please Turn on and run the applicaiton again.");
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
            builder1.setMessage("Do you want to Go back.");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

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

    public static void LoginActivityalert(final Context mContex){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(mContex);
        builder1.setMessage("To avail this feature you need to login or create your account if not registerd." +
                "     Do you want to login or create your account ");
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

}
