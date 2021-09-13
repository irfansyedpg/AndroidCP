package com.mobilisepakistan.pdma;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobilisepakistan.pdma.global.MyPref;
import com.mobilisepakistan.pdma.gps.ShowLocationActivity2;
import com.mobilisepakistan.pdma.gps.TurnOnGPS;
import com.mobilisepakistan.pdma.report.NewUPloadImage;
import com.mobilisepakistan.pdma.report.UploadToServer;

import java.util.Locale;


public class Splash extends AppCompatActivity {

    TextView appnam;
    View splashImage;
    ImageView LotAview,pdma;

    MyPref preferences;
    String language;
    String country;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        preferences = new MyPref(Splash.this);



        country=preferences.getCountry();
        language=preferences.getLanguage();

        Locale locale = new Locale(language,country);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        setContentView(R.layout.activity_splash);


        appnam=findViewById(R.id.appname);
        splashImage=findViewById(R.id.img);
        pdma=findViewById(R.id.pdma);
        LotAview=findViewById(R.id.lottie);

        splashImage.animate().translationY(-2700).setDuration(1000).setStartDelay(4200);

        appnam.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        LotAview.animate().translationY(2000).setDuration(1000).setStartDelay(4000);
        pdma.animate().translationY(2000).setDuration(1000).setStartDelay(4000);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent mainIntent;
//                final MyPref preferences = new MyPref(Splash.this);
//                int preff=preferences.getUserId();
//                if(preff==0)
//                {
//                    mainIntent=new Intent(Splash.this, LogIn.class);
//
//                }
//                else
//                {
//                    mainIntent=new Intent(Splash.this,MainActivity.class);
//
//
//                }
         //       mainIntent=new Intent(Splash.this, UploadToServer.class);
           // mainIntent=new Intent(Splash.this, ReportDisaster.class);
            mainIntent=new Intent(Splash.this, MainActivity.class);
              // mainIntent=new Intent(Splash.this, ShowlocaitonActivityNew.class);
                // mainIntent=new Intent(Splash.this, newgps.class);
            //     mainIntent=new Intent(Splash.this, newgpsuen.class);


              //  Splash.this.startActivity(mainIntent);


                if(preferences.getLanguageDilage()==false) {


                    preferences.setlanguaDialge(true);
                    TurnOnGPS.Languagealert(Splash.this);

               //     preferences.setappcount(preferences.getappcount()+1);
                }
                else
                {
               //     preferences.setappcount(preferences.getappcount()+1);

                  Intent intt=new Intent(Splash.this,MainActivity.class);
                 //   Intent intt=new Intent(Splash.this, ShowLocationActivity2.class);

                     startActivity(intt);

                    Splash.this.finish();
                }

              //  Splash.this.finish();
            }
        }, 5300);

    }
}
