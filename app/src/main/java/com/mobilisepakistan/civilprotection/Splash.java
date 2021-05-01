package com.mobilisepakistan.civilprotection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.mobilisepakistan.civilprotection.global.MyPref;
import com.mobilisepakistan.civilprotection.report.ReportDisaster;
import com.mobilisepakistan.civilprotection.report.UploadToServer;
import com.mobilisepakistan.civilprotection.signup.LogIn;
import com.mobilisepakistan.civilprotection.weather.getweather;

public class Splash extends AppCompatActivity {

    TextView appnam;
    View splashImage;
    ImageView LotAview,pdma;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        }, 5300);

    }
}
