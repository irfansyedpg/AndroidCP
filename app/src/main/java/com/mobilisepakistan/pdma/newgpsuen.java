package com.mobilisepakistan.pdma;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.mobilisepakistan.pdma.gps.gpsuenway;

import java.util.Locale;

public class newgpsuen extends AppCompatActivity {

    protected Location mLastLocation;

    private String mLatitudeLabel;
    private String mLongitudeLabel;
    private TextView mLatitudeText;
    private TextView mLongitudeText;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newgps);

        mLatitudeLabel = "latitude";
        mLongitudeLabel = "longitude";
        mLatitudeText = (TextView) findViewById((R.id.latitude_text));
        mLongitudeText = (TextView) findViewById((R.id.longitude_text));

       // mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fun_get_gps_by_internet();
    }


    public void fun_get_gps_by_internet() {
        gpsuenway gpsObj = new gpsuenway(this);
        gpsObj.requestLocationUpdate(new gpsuenway.LocationCallback() {
            @Override
            public void onNewLocation(String gpsData) {
                if (gpsData.split("-").length > 1) {
                    mLatitudeText.setText (gpsData.split("-")[0]);
                    mLongitudeText.setText(gpsData.split("-")[1]);


                }
            }
        });
    }










}