package com.mobilisepakistan.civilprotection.report;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.mobilisepakistan.civilprotection.R;
import com.mobilisepakistan.civilprotection.data.LocalDataManager;
import com.mobilisepakistan.civilprotection.databinding.DemageloseBinding;
import com.mobilisepakistan.civilprotection.databinding.RapidneedassesmentBinding;
import com.mobilisepakistan.civilprotection.global.District;
import com.mobilisepakistan.civilprotection.global.Tehsil;
import com.mobilisepakistan.civilprotection.global.TypeDisaster;
import com.mobilisepakistan.civilprotection.global.UploadData2;
import com.mobilisepakistan.civilprotection.gps.ShowLocationActivity2;
import com.mobilisepakistan.civilprotection.gps.TurnOnGPS;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class DemagesLosses extends AppCompatActivity  {
    DemageloseBinding binding ;
    ArrayList<String> listDistrict;

    ArrayList<String> listDisaster;
    String sDistrict="";

    String sDisasterType="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.demagelose);
        listDisaster=new ArrayList<>();
        listDistrict=new ArrayList<>();
        listDistrict= District.getDistrictsPDMA();
        listDisaster= TypeDisaster.getDisaster();


        TurnOnGPS.turnGPSOn(this);

        // when clicked on District will open new Activity for District Selection
        binding.dl1LV.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                Intent  intent = new Intent(DemagesLosses.this, RecyclerViewA.class);
                intent.putExtra("mylist",listDistrict);
                intent.putExtra("header","Select District");
                startActivityForResult(intent,11);

            }
        });


        binding.dl2LV.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                Intent  intent = new Intent(DemagesLosses.this, RecyclerViewA.class);
                intent.putExtra("mylist",listDisaster);
                intent.putExtra("header","Select Type of Disaster");
                startActivityForResult(intent,13);

            }
        });


        binding.dl3LV.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                DatePickerDialog picker;
                picker = new DatePickerDialog(DemagesLosses.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                binding.frmDateTv.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);



                            }
                        }, year, month, day);



                picker.show();

            }
        });

        binding.dl4LV.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                DatePickerDialog picker;
                picker = new DatePickerDialog(DemagesLosses.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                binding.toDateTv.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);



                            }
                        }, year, month, day);



                picker.show();

            }
        });



        binding.lvback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) DemagesLosses.this).finish();
            }
        });



        binding.btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    ImageView ImgView;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 11) {
            if (resultCode == Activity.RESULT_OK) {
                sDistrict=data.getStringExtra("data");
                binding.distTv.setText(sDistrict);


            }
        }

        else if(requestCode == 13 ) {
            if (resultCode == Activity.RESULT_OK) {
                sDisasterType = data.getStringExtra("data");
                binding.disasterTv.setText(sDisasterType);

            }
        }


        // upload image starts




    }
    HashMap<String,String> HashData=new HashMap<>();
    Boolean UploadFailur=false;
    String Logpk="";


    @Override
    public  void onBackPressed()
    {

        TurnOnGPS.CloseActivityalerd(this);
    }





}
