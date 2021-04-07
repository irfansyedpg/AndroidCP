package com.mobilisepakistan.civilprotection.weather;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.androdocs.httprequest.HttpRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.mobilisepakistan.civilprotection.R;
import com.mobilisepakistan.civilprotection.gps.ShowLocationActivity2;
import com.mobilisepakistan.civilprotection.gps.TurnOnGPS;
import com.mobilisepakistan.civilprotection.report.ReportDisaster;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class getweather extends AppCompatActivity {

    String CITY;
    String lat;
    String lon;

    String API = "55e1d7a613263d5aea5ff2bceda55d4a";



    EditText etCity;

    TextView city, country, time, temp, forecast, humidity, min_temp, max_temp, sunrises, sunsets;
    private FusedLocationProviderClient fusedLocationClient;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.weather);









        city = (TextView) findViewById(R.id.city);

        country = (TextView) findViewById(R.id.country);

        time = (TextView) findViewById(R.id.time);

        temp = (TextView) findViewById(R.id.temp);

        forecast = (TextView) findViewById(R.id.forecast);

        humidity = (TextView) findViewById(R.id.humidity);

        min_temp = (TextView) findViewById(R.id.min_temp);

        max_temp = (TextView) findViewById(R.id.max_temp);

        sunrises = (TextView) findViewById(R.id.sunrises);

        sunsets = (TextView) findViewById(R.id.sunsets);



        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                             lat=location.getLatitude()+"";
                             lon=location.getLongitude()+"";
                            new weatherTask().execute();
                        }
                        else
                        {
                            LocationManager locationManager ;
                            boolean GpsStatus ;
                            locationManager = (LocationManager)getweather.this.getSystemService(getweather.this.LOCATION_SERVICE);
                            assert locationManager != null;
                            GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                            if(GpsStatus == false) {
                                TurnOnGPS.turnGPSOn(getweather.this);
                            }
                            else {
                                Intent intentt = new Intent(getweather.this, ShowLocationActivity2.class);
                                startActivityForResult(intentt, 22);
                            }
                        }
                    }
                });




        CITY = "Peshawar";


       }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 22 ) {
            if (resultCode == Activity.RESULT_OK) {
                String Lat = data.getStringExtra("Lat");
                String Long = data.getStringExtra("Long");


                lat=Lat;
                lon=Long;
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

                String humi_dity = main.getString("humidity");

                String temp_min = main.getString("temp_min");

                String temp_max = main.getString("temp_max");

                Long rise = sys.getLong("sunrise");

                String sunrise = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(rise * 1000));

                Long set = sys.getLong("sunset");

                String sunset = new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(set * 1000));

                // SET ALL VALUES IN TEXTBOX :

                city.setText(city_name);

                country.setText(countryname);

                time.setText(updatedAtText);

                temp.setText(temperature + "°C");

                forecast.setText(cast);

                humidity.setText(humi_dity);

                min_temp.setText(temp_min);

                max_temp.setText(temp_max);

                sunrises.setText(sunrise);

                sunsets.setText(sunset);

            } catch (Exception e) {

                Toast.makeText(getweather.this, "Error:" + e.toString(), Toast.LENGTH_SHORT).show();

            }

        }

    }
}
