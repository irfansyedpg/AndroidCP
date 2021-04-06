package com.mobilisepakistan.civilprotection.report;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.mobilisepakistan.civilprotection.R;
import com.mobilisepakistan.civilprotection.data.LocalDataManager;
import com.mobilisepakistan.civilprotection.databinding.RapidneedassesmentBinding;
import com.mobilisepakistan.civilprotection.global.District;
import com.mobilisepakistan.civilprotection.global.Tehsil;
import com.mobilisepakistan.civilprotection.global.TypeDisaster;
import com.mobilisepakistan.civilprotection.global.UploadData2;
import com.mobilisepakistan.civilprotection.gps.ShowLocationActivity2;
import com.mobilisepakistan.civilprotection.gps.TurnOnGPS;

import java.util.ArrayList;
import java.util.HashMap;

public class RapidNeedAssessment extends AppCompatActivity  {
    RapidneedassesmentBinding binding ;
    ArrayList<String> listDistrict;
    ArrayList<String> listTehsil;
    ArrayList<String> listDisaster;
    String sDistrict="";
    String sTehsil="";
    String sDisasterType="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.rapidneedassesment);
        listDisaster=new ArrayList<>();
        listDistrict=new ArrayList<>();
        listDistrict= District.getDistricts();
        listDisaster= TypeDisaster.getDisaster();
        UploadFailur=false;

        TurnOnGPS.turnGPSOn(this);

        // when clicked on District will open new Activity for District Selection
        binding.rna1LV.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                Intent  intent = new Intent(RapidNeedAssessment.this, RecyclerViewA.class);
                intent.putExtra("mylist",listDistrict);
                intent.putExtra("header","Select District");
                startActivityForResult(intent,11);

            }
        });

        binding.rna2LV.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {

                if(sDistrict.isEmpty())
                {
                    Toast.makeText(RapidNeedAssessment.this,"Please Select District First",Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent  intent = new Intent(RapidNeedAssessment.this, RecyclerViewA.class);
                listTehsil= Tehsil.get(sDistrict);
                intent.putExtra("mylist",listTehsil);
                intent.putExtra("header","Select Tehsil");
                startActivityForResult(intent,12);

            }
        });

        binding.rna4LV.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                Intent  intent = new Intent(RapidNeedAssessment.this, RecyclerViewA.class);
                intent.putExtra("mylist",listDisaster);
                intent.putExtra("header","Select Type of Disaster");
                startActivityForResult(intent,13);

            }
        });


        binding.lvback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) RapidNeedAssessment.this).finish();
            }
        });


        binding.checkboxGps.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    //Case 1

                    if(TurnOnGPS.CheckGPS(RapidNeedAssessment.this)==false)
                    {
                        TurnOnGPS.turnGPSOn(RapidNeedAssessment.this);
                        ((CheckBox) v).setChecked(false);
                        return;
                    }
                    Intent  intentt = new Intent(RapidNeedAssessment.this, ShowLocationActivity2.class);
                    startActivityForResult(intentt,22);

                }
                else
                {

                }
                //case 2

            }
        });

        binding.btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              insertDb();
            }
        });
    }

    ImageView ImgView;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 11) {
            if (resultCode == Activity.RESULT_OK) {
                sDistrict=data.getStringExtra("data");
                binding.rna1Tv.setText(sDistrict);
                binding.rna2Tv.setText(R.string.rna2);

            }
        }
        else if(requestCode == 12 ) {
            if (resultCode == Activity.RESULT_OK) {
                sTehsil = data.getStringExtra("data");
                binding.rna2Tv.setText(sTehsil);

            }
        }

        else if(requestCode == 13 ) {
            if (resultCode == Activity.RESULT_OK) {
                sDisasterType = data.getStringExtra("data");
                binding.rna4Tv.setText(sDisasterType);

            }
        }
        else if(requestCode == 22 ) {
            if (resultCode == Activity.RESULT_OK) {
                String Lat = data.getStringExtra("Lat");
                String Long = data.getStringExtra("Long");

                binding.latitude.setText(Lat);
                binding.longitude.setText(Long);

            }
        }

        // upload image starts




    }
    HashMap<String,String> HashData=new HashMap<>();
    Boolean UploadFailur=false;
    String Logpk="";
    public  void insertDb()
    {
        if(datavalidation()==false)
        {
            return;
        }
        HashData.put("rna1",binding.rna1Tv.getText().toString().trim());
        HashData.put("rna2",binding.rna2Tv.getText().toString().trim());
        HashData.put("rna3",binding.rna3Tv.getText().toString().trim());
        HashData.put("rna4",binding.rna4Tv.getText().toString().trim());

        if(binding.rna5a.isChecked()) HashData.put("rna5a","1");
        else HashData.put("rna5a","0");

        if(binding.rna5b.isChecked()) HashData.put("rna5b","1");
        else HashData.put("rna5b","0");

        if(binding.rna5c.isChecked()) HashData.put("rna5c","1");
        else HashData.put("rna5c","0");

        if(binding.rna5d.isChecked()) HashData.put("rna5d","1");
        else HashData.put("rna5d","0");

        if(binding.rna5e.isChecked()) HashData.put("rna5e","1");
        else HashData.put("rna5e","0");

        if(binding.rna5f.isChecked()) HashData.put("rna5f","1");
        else HashData.put("rna5f","0");

        if(binding.rna5g.isChecked()) HashData.put("rna5g","1");
        else HashData.put("rna5g","0");

      //  new LocalDataManager(this).InsertRespnoseTable(1,HashData,"ReportDisaster");

        //((Activity) RapidNeedAssessment.this).finish();


        if (UploadFailur==false) {
            Logpk = LocalDataManager.InsertLogTable("1", binding.latitude.getText().toString(), binding.longitude.getText().toString(), "RNA", this);
            new LocalDataManager(this).InsertRespnoseTable(Integer.parseInt(Logpk), HashData, "RNA");
        }

//        HashMap<String,List<String>> MpUplod=new HashMap<>();

        boolean uploadStatus= UploadData2.volleyPost(this,LocalDataManager.GetData(Logpk));

        if(uploadStatus==true)
        {
            LocalDataManager.UpdateLOgtable(Logpk);
            ((Activity) this).finish();
        }
        else
        {
            UploadFailur=true;
            new AlertDialog.Builder(this).
                    setMessage("Unable to Upload Data to Server Due to Internet Would you like to try again or you will upload it latter .").
                    setPositiveButton("I will Upload Letter", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ((Activity) RapidNeedAssessment.this).finish();
                        }
                    }).setNegativeButton("Try Again", null).create().show();
        }


    }
    public  boolean  datavalidation()
    {
        if(binding.rna1Tv.getText().equals(""))
        {
            Toast.makeText(this,"Please select District",Toast.LENGTH_SHORT).show();
            return  false;
        }
        if(binding.rna2Tv.getText().equals("Select Tehsil"))
        {      Toast.makeText(this,"Please Enter Select Tehsil",Toast.LENGTH_SHORT).show();
            return  false;
        }
        if(binding.rna3Tv.getText().toString().trim().equals("") || binding.rna3Tv.getText().toString().trim().isEmpty())
        {
            Toast.makeText(this,"Please Enter Complete Address",Toast.LENGTH_SHORT).show();
            return  false;
        }
        if(binding.rna4Tv.getText().equals(""))
        {
            Toast.makeText(this,"Please Select Type of Disaster",Toast.LENGTH_SHORT).show();
            return  false;
        }
        if(!binding.rna5a.isChecked() && !binding.rna5b.isChecked() && !binding.rna5c.isChecked() && !binding.rna5d.isChecked() && !binding.rna5e.isChecked() && !binding.rna5f.isChecked() && !binding.rna5g.isChecked() )
        {
            Toast.makeText(this,"Please Select Immediate Needs",Toast.LENGTH_SHORT).show();
            return  false;
        }
        if(!binding.checkbox.isChecked())
        {
            Toast.makeText(this,"Please check the Undertaking",Toast.LENGTH_SHORT).show();
            return  false;
        }

        if(!binding.checkboxGps.isChecked())
        {
            Toast.makeText(this,"Please check the GPS",Toast.LENGTH_SHORT).show();
            return  false;
        }

        return  true;
    }

    @Override
    public  void onBackPressed()
    {

        TurnOnGPS.CloseActivityalerd(this);
    }





}
