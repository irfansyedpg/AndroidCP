package com.mobilisepakistan.pdma;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;

import com.androdocs.httprequest.HttpRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.mobilisepakistan.pdma.global.MyPref;

import com.mobilisepakistan.pdma.global.MyReceiver;
import com.mobilisepakistan.pdma.gps.ShowLocationActivity2;
import com.mobilisepakistan.pdma.gps.ShowlocaitonActivityNew;
import com.mobilisepakistan.pdma.gps.TurnOnGPS;
import com.mobilisepakistan.pdma.report.DailySituationReport;
import com.mobilisepakistan.pdma.report.DemageNeedAssesment;
import com.mobilisepakistan.pdma.report.DemagesLosses;
import com.mobilisepakistan.pdma.report.EarlyWarning;
import com.mobilisepakistan.pdma.report.RapidNeedAssessment;
import com.mobilisepakistan.pdma.report.RecyclerViewCC;
import com.mobilisepakistan.pdma.report.RecyclerViewEC;
import com.mobilisepakistan.pdma.report.ReportDisaster;

import com.mobilisepakistan.pdma.report.WeatherForecast;
import com.mobilisepakistan.pdma.signup.LogIn;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    String CITY;
    String lat;
    String lon;
    private FusedLocationProviderClient fusedLocationClient;
    String API = "55e1d7a613263d5aea5ff2bceda55d4a";
    TextView tTemp,tHuminty,tLocaiton,tDisciption;
    ImageView img;
    LinearLayout lnwa,lnew,lnds,lnrd;
    int userId;

    private BroadcastReceiver MyReceiver = null;

    MyPref preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // call intenet check
        MyReceiver = new MyReceiver();
        broadcastIntent();


        preferences = new MyPref(MainActivity.this);


        // new way to check GPS




        userId=preferences.getUserId();

        lnwa=findViewById(R.id.lnwa);
        lnew=findViewById(R.id.lnew);
        lnds=findViewById(R.id.lnds);
        lnrd=findViewById(R.id.lnrd);
        lnwa.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        intent = new Intent(MainActivity.this, WeatherForecast.class);
                                       startActivity(intent);



                                    }
                                });

        lnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent = new Intent(MainActivity.this, EarlyWarning.class);
                startActivity(intent);



            }
        });

        lnds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent = new Intent(MainActivity.this, DailySituationReport.class);
                startActivity(intent);



            }
        });

        lnrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(userId==0)
                {
                   //mainIntent=new Intent(Splash.this, LogIn.class);

                    TurnOnGPS.LoginActivityalert(MainActivity.this);

                }
                else
                {
                  //  mainIntent=new Intent(Splash.this,MainActivity.class);

                    intent = new Intent(MainActivity.this, ReportDisaster.class);
                    startActivity(intent);


                }




            }
        });





        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        navigationView.setNavigationItemSelectedListener(this);


        // weather info


        tTemp=findViewById(R.id.temp);


        tHuminty=findViewById(R.id.humidity);
        tLocaiton=findViewById(R.id.city);

        tDisciption=findViewById(R.id.discp);
        img=findViewById(R.id.img);


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        fusedLocationClient.getLastLocation()
//                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//                        // Got last known location. In some rare situations this can be null.
//                        if (location != null) {
//                            // Logic to handle location object
//                            lat=location.getLatitude()+"";
//                            lon=location.getLongitude()+"";
//
//
//
//
//                            preferences.setlat(lat);
//                            preferences.setlong(lon);
//                            new weatherTask().execute();
//                        }
//
//                    }
//                });

//         if (!preferences.getlat().equals("0"))
//        {
//
//            lat=preferences.getlat();
//            lon=preferences.getlong();
//            new weatherTask().execute();
//
//        }
//        else
//        {
            LocationManager locationManager ;
            boolean GpsStatus ;
            locationManager = (LocationManager)MainActivity.this.getSystemService(MainActivity.this.LOCATION_SERVICE);
            assert locationManager != null;
            GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if(GpsStatus == false) {
                TurnOnGPS.turnGPSOn(MainActivity.this);
            }
            else {
                Intent intentt = new Intent(MainActivity.this, ShowlocaitonActivityNew.class);
                startActivityForResult(intentt, 22);
            }
//        }

    }
    // get location new




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    Intent intent = null;
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();
        if (id == R.id.nav_RD) {
            if(userId==0)
            {
                TurnOnGPS.LoginActivityalert(MainActivity.this);

            }
            else
            {
                //  mainIntent=new Intent(Splash.this,MainActivity.class);

                intent = new Intent(MainActivity.this, ReportDisaster.class);
                startActivity(intent);


            }
        }
        else if (id == R.id.nav_ew) {
            intent = new Intent(this, EarlyWarning.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_ds) {
            intent = new Intent(this, DailySituationReport.class);
            startActivity(intent);
        }
        else if (id == R.id.emgncyContact) {
            intent = new Intent(this, RecyclerViewEC.class);
            startActivity(intent);
        }

        else if (id == R.id.evactioncenter) {
            intent = new Intent(this, RecyclerViewCC.class);
            startActivity(intent);
        }

        else if (id == R.id.nav_wf) {
            intent = new Intent(this, WeatherForecast.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_RNA) {


            if(userId==0)
            {
                TurnOnGPS.LoginActivityalert(MainActivity.this);

            }
            else
            {
                //  mainIntent=new Intent(Splash.this,MainActivity.class);

                intent = new Intent(MainActivity.this, RapidNeedAssessment.class);
                startActivity(intent);


            }
        }
        else if (id == R.id.nav_DNA) {

            if(userId==0)
            {
                TurnOnGPS.LoginActivityalert(MainActivity.this);

            }
            else
            {
                //  mainIntent=new Intent(Splash.this,MainActivity.class);

                intent = new Intent(MainActivity.this, DemageNeedAssesment.class);
                startActivity(intent);


            }
        }
        else if (id == R.id.nav_LD) {
            intent = new Intent(this, DemagesLosses.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_logout) {

            MyPref prefs = new MyPref(this);

            prefs.setUserId(0);
            intent = new Intent(this, LogIn.class);
            startActivity(intent);
            this.finish();
        }
        else if (id == R.id.nav_login) {


            intent = new Intent(this, LogIn.class);
            startActivity(intent);
            this.finish();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 22 ) {
            if (resultCode == Activity.RESULT_OK) {
                String Lat = data.getStringExtra("Lat");
                String Long = data.getStringExtra("Long");


                lat=Lat;
                lon=Long;


                preferences.setlat(lat);
                preferences.setlong(lon);

                new weatherTask().execute();

            }
        }

    }
    class weatherTask extends AsyncTask<String, Void, String> {
        @Override

        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... args) {
            //String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&units=metric&appid=" + API);
            String url="https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&units=metric&appid=" + API;
            String response = HttpRequest.excuteGet(url);



            return response;
        }


        @Override

        protected void onPostExecute(String result) {

            try {

                JSONObject jsonObj = new JSONObject(result);

                JSONObject main = jsonObj.getJSONObject("main");

                JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);

                JSONObject sys = jsonObj.getJSONObject("sys");

                // CALL VALUE IN API :

                String city_name = jsonObj.getString("name");

                String countryname = sys.getString("country");

                Long updatedAt = jsonObj.getLong("dt");

                String updatedAtText = "Last Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));

                String temperature = main.getString("temp");

                String cast = weather.getString("description");
                String icon = weather.getString("icon");
                String mainn = weather.getString("main");

                String humi_dity = main.getString("humidity");

                String temp_min = main.getString("temp_min");

                String temp_max = main.getString("temp_max");

                Long rise = sys.getLong("sunrise");

                String sunrise = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(rise * 1000));

                Long set = sys.getLong("sunset");

                String sunset = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(set * 1000));

                // SET ALL VALUES IN TEXTBOX :

                tLocaiton.setText(city_name+", "+countryname);




                tTemp.setText(temperature + "°C");



                tHuminty.setText("Humidity "+humi_dity);


                tDisciption.setText(cast.toUpperCase());




                String img_url= "http://openweathermap.org/img/wn/"+icon+"@2x.png";

                Picasso.get().load(img_url).into(img);


            } catch (Exception e) {

                Toast.makeText(MainActivity.this, "Error:" + e.toString(), Toast.LENGTH_SHORT).show();

            }

        }

    }

    @Override
    public void onBackPressed() {


        TurnOnGPS.CloseActivityalerd(this);

    }


    // check internet connection

    public void broadcastIntent() {
        registerReceiver(MyReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }
    @Override
    protected void onPause() {
        super.onPause();
    //    unregisterReceiver(MyReceiver);
    }

}