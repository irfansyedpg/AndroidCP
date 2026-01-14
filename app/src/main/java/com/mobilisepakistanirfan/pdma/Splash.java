package com.mobilisepakistanirfan.pdma;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.messaging.FirebaseMessaging;
import com.mobilisepakistanirfan.pdma.global.MyPref;
import com.mobilisepakistanirfan.pdma.global.UserPref;
import com.mobilisepakistanirfan.pdma.gps.TurnOnGPS;
import com.mobilisepakistanirfan.pdma.report.News;
import com.mobilisepakistanirfan.pdma.utilities.VolunterEngServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;


public class Splash extends AppCompatActivity {

    TextView appnam;
    View splashImage;
    ImageView LotAview, pdma;

    MyPref preferences;
    UserPref userpref;
    String language;
    String country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = new MyPref(Splash.this);
        userpref = new UserPref(Splash.this);

        country = preferences.getCountry();
        language = preferences.getLanguage();

        Locale locale = new Locale(language, country);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        setContentView(R.layout.activity_splash);

        appnam = findViewById(R.id.appname);
        splashImage = findViewById(R.id.img);
        pdma = findViewById(R.id.pdma);
        LotAview = findViewById(R.id.lottie);

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
                mainIntent = new Intent(Splash.this, MainActivity.class);
                // mainIntent=new Intent(Splash.this, ShowlocaitonActivityNew.class);
                // mainIntent=new Intent(Splash.this, newgps.class);
                //     mainIntent=new Intent(Splash.this, newgpsuen.class);


                //  Splash.this.startActivity(mainIntent);


                if (preferences.getLanguageDilage() == false) {
                    preferences.setlanguaDialge(true);
                    TurnOnGPS.Languagealert(Splash.this);

                    //     preferences.setappcount(preferences.getappcount()+1);
                } else {

                    // FCM  here on Splahs Screen

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        // Create channel to show notifications.
                        String channelId = getString(R.string.default_notification_channel_id);
                        String channelName = getString(R.string.default_notification_channel_name);
                        NotificationManager notificationManager =
                                getSystemService(NotificationManager.class);
                        notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                                channelName, NotificationManager.IMPORTANCE_LOW));
                    }

                    // put shared prefrence concept here to checked if subscibed no need to subscibe again

                    if (preferences.getFrbsNews().equals("0")) {
                        FirebaseMessaging.getInstance().subscribeToTopic("news");
                        preferences.setFirbaseNew("1");
                    }

                    String district = preferences.getUserDistrict();

                    if (!district.equals("") && userpref.getUserType().equals("2")) {
                        String volnt = preferences.getFrbsVolnt();


                        district = district.replaceAll(" ", "_");
                        FirebaseMessaging.getInstance().subscribeToTopic(district);

                        preferences.setFirebaseVolnt("1");
                        FirebaseMessaging.getInstance().subscribeToTopic("All");

                    }

                    if (userpref.getUserType().equals("3")) {
                        String uusid = Integer.toString(preferences.getUserId());
                        FirebaseMessaging.getInstance().subscribeToTopic(uusid);
                    }


                    try {

                        if (getIntent().getExtras() != null) {

                            if (getIntent().getExtras().get("from").equals("/topics/news")) {
                                Intent intent = new Intent(Splash.this, News.class);
                                startActivity(intent);

                                Splash.this.finish();
                                return;
                            }


                            String userid = Integer.toString(preferences.getUserId());
                            if (getIntent().getExtras().get("from").equals("/topics/" + userid)) {


                                if (userpref.getUserStatus().equals("Active")) {
                                    userpref.setUserUserStatus("Not Active");
                                } else {
                                    userpref.setUserUserStatus("Active");
                                }


                                Intent intent = new Intent(Splash.this, MainActivity.class);
                                startActivity(intent);
                                Splash.this.finish();
                                return;
                            }


                            String topics = "/topics/" + district;


                            if (getIntent().getExtras().get("from").equals(topics)) {


                                AlertDialog.Builder builder1 = new AlertDialog.Builder(Splash.this);
                                builder1.setMessage(Splash.this.getString(R.string.VolanteerAlertMsg));
                                builder1.setCancelable(true);

                                builder1.setPositiveButton(
                                        Splash.this.getString(R.string.Active),
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                //   ((Activity) Splash.this).finish();


                                                VolunterEngServer.VolunterEngServer(Splash.this, UploadDate(preferences.getUserId(), 1));


                                                dialog.cancel();
                                            }
                                        });
                                builder1.setNegativeButton(
                                        Splash.this.getString(R.string.UnActive),
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {


                                                VolunterEngServer.VolunterEngServer(Splash.this, UploadDate(preferences.getUserId(), 2));


                                                dialog.cancel();

                                                //      Splash.this.finish();
                                            }
                                        });


                                AlertDialog alert11 = builder1.create();
                                alert11.show();


                                return;
                            }


                        }


                    } catch (Exception e) {
                        //  Toast.makeText(Splash.this,e.toString(),Toast.LENGTH_LONG).show();

                        //

                    }


                    //     preferences.setappcount(preferences.getappcount()+1);

                    Intent intt = new Intent(Splash.this, MainActivity.class);
                    //      Intent intt=new Intent(Splash.this, MainActivityFCM.class);

                    startActivity(intt);

                    Splash.this.finish();
                }

                //  Splash.this.finish();
            }
        }, 5300);

    }

    public JSONObject UploadDate(int Userid, int Status) {

        JSONObject log = new JSONObject();
        try {
            log.put("UserId", Userid);
            log.put("Status", Status);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return log;
    }
}
