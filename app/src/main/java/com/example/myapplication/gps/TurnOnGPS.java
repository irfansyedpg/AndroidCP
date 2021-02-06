package com.example.myapplication.gps;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.provider.Settings;

import androidx.appcompat.app.AlertDialog;

public class TurnOnGPS {

    public static void turnGPSOn(final Context mContex){

        LocationManager locationManager ;
        boolean GpsStatus ;
        locationManager = (LocationManager)mContex.getSystemService(mContex.LOCATION_SERVICE);
        assert locationManager != null;
        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if(GpsStatus == false) {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(mContex);
            builder1.setMessage("Your Mobile GPS is disabled Would you like to Enable it? to get better result.");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    mContex.startActivity(intent);

                            dialog.cancel();
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

}
