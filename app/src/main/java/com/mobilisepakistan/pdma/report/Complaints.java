package com.mobilisepakistan.pdma.report;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.mobilisepakistan.pdma.R;
import com.mobilisepakistan.pdma.data.LocalDataManager;
import com.mobilisepakistan.pdma.databinding.ComplaintsBinding;
import com.mobilisepakistan.pdma.databinding.ReportdisasterBinding;
import com.mobilisepakistan.pdma.global.MyPref;
import com.mobilisepakistan.pdma.global.ServerConfiguration;
import com.mobilisepakistan.pdma.global.TypeDisaster;
import com.mobilisepakistan.pdma.global.UploadComplaint;
import com.mobilisepakistan.pdma.global.UploadData2;
import com.mobilisepakistan.pdma.global.testVolleyMultipartRequest;
import com.mobilisepakistan.pdma.gps.ShowLocationActivity2;
import com.mobilisepakistan.pdma.gps.TurnOnGPS;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Complaints extends AppCompatActivity  {
    ComplaintsBinding binding ;
    int UserID=0;
    MyPref preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.complaints);

        preferences = new MyPref(this);
        binding.lvback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) Complaints.this).finish();
            }
        });


        binding.btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(datavalidation()==false)
                {
                    return;
                }

                UploadComplaint.volleyPost(Complaints.this,UploadDate());

            }
        });

    }




    public  boolean  datavalidation()
    {
        if(binding.rd3Tv.getText().equals(""))
        {
            Toast.makeText(this,"Please Enter Complaint Title",Toast.LENGTH_SHORT).show();
            return  false;
        }
        if(binding.rd5Tv.getText().equals(""))
        {      Toast.makeText(this,"Please Enter Complaint Detail",Toast.LENGTH_SHORT).show();
            return  false;
        }


        return  true;
    }

  @Override
    public  void onBackPressed()
    {

       TurnOnGPS.CloseActivityalerd(this);
    }


    public  JSONObject UploadDate()
    {

        UserID=preferences.getUserId();

        String Title=binding.rd3Tv.getText().toString();
        String Detail=binding.rd5Tv.getText().toString();

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String Currentdate = df.format(c);

        JSONObject log = new JSONObject();
        try {
            log .put("Title", Title);
            log .put("Detail", Detail);
            log .put("UserId", UserID);
            log .put("Dated", "0");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  log;
    }


}




