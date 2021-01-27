package com.example.myapplication.report;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.myapplication.R;

import com.example.myapplication.data.LocalDataManager;
import com.example.myapplication.databinding.DemgneedassesmentBinding;
import com.example.myapplication.global.District;
import com.example.myapplication.global.Tehsil;
import com.example.myapplication.global.TypeDisaster;

import java.util.ArrayList;
import java.util.HashMap;

public class DemageNeedAssesment extends AppCompatActivity  {
    DemgneedassesmentBinding binding ;
    ArrayList<String> listDistrict;
    ArrayList<String> listTehsil;

    String sDistrict="";
    String sTehsil="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.demgneedassesment );
        listDistrict= District.getDistricts();




        // when clicked on District will open new Activity for District Selection
        binding.dna1LV.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                Intent  intent = new Intent(DemageNeedAssesment.this, RecyclerViewA.class);
                intent.putExtra("mylist",listDistrict);
                intent.putExtra("header","Select District");
                startActivityForResult(intent,11);

            }
        });

        binding.dna2LV.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {

                if(sDistrict.isEmpty())
                {
                    Toast.makeText(DemageNeedAssesment.this,"Please Select District First",Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent  intent = new Intent(DemageNeedAssesment.this, RecyclerViewA.class);
                listTehsil= Tehsil.get(sDistrict);
                intent.putExtra("mylist",listTehsil);
                intent.putExtra("header","Select Tehsil");
                startActivityForResult(intent,12);

            }
        });

        binding.lvback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) DemageNeedAssesment.this).finish();
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
                binding.dna1Tv.setText(sDistrict);
                binding.dna2Tv.setText(R.string.dna2);

            }
        }
        else if(requestCode == 12 ) {
            if (resultCode == Activity.RESULT_OK) {
                sTehsil = data.getStringExtra("data");
                binding.dna2Tv.setText(sTehsil);

            }
        }


        }

    HashMap<String,String> HashData=new HashMap<>();

    public  void insertDb()
    {
        if(datavalidation()==false)
        {
            return;
        }
        HashData.put("dna1",binding.dna1Tv.getText().toString().trim());
        HashData.put("dna2",binding.dna2Tv.getText().toString().trim());
        HashData.put("dna3",binding.dna3Tv.getText().toString().trim());
        HashData.put("dna4",binding.dna4Tv.getText().toString().trim());
        HashData.put("dna5",binding.dna5Tv.getText().toString().trim());
        HashData.put("dna6",binding.dna6Tv.getText().toString().trim());
        HashData.put("dna7",binding.dna7Tv.getText().toString().trim());

        HashData.put("dna8a",binding.dna8aTv.getText().toString().trim());
        HashData.put("dna8b",binding.dna8bTv.getText().toString().trim());

        if(binding.dna9a.isChecked()) HashData.put("dna9","1");
        else if(binding.dna9a.isChecked()) HashData.put("dna9","2");
        else if(binding.dna9a.isChecked()) HashData.put("dna9","3");
        else  HashData.put("dna9","0");

        if(binding.dna10a.isChecked()) HashData.put("dna10","1");
        else if(binding.dna10b.isChecked()) HashData.put("dna10","2");
        else  HashData.put("dna10","0");

        if(binding.dna11a.isChecked()) HashData.put("dna11","1");
        else if(binding.dna11b.isChecked()) HashData.put("dna11","2");
        else  HashData.put("dna11","0");


        if(binding.dna12a.isChecked()) HashData.put("dna12a","1");
        else HashData.put("dna12a","0");
        if(binding.dna12b.isChecked()) HashData.put("dna12b","1");
        else HashData.put("dna12b","0");

        if(binding.dna12c.isChecked()) HashData.put("dna12c","1");
        else HashData.put("dna12c","0");

        if(binding.dna12d.isChecked()) HashData.put("dna12d","1");
        else HashData.put("dna12d","0");

        HashData.put("dna12e",binding.dna12e.getText().toString().trim());


        if(binding.dna13a.isChecked()) HashData.put("dna13a","1");
        else HashData.put("dna13a","0");
        if(binding.dna13b.isChecked()) HashData.put("dna13b","1");
        else HashData.put("dna13b","0");

        if(binding.dna13c.isChecked()) HashData.put("dna13c","1");
        else HashData.put("dna13c","0");

        HashData.put("dna13d",binding.dna13d.getText().toString().trim());

        HashData.put("dna14a",binding.dna14a.getText().toString().trim());
        HashData.put("dna14b",binding.dna14b.getText().toString().trim());
        HashData.put("dna14c",binding.dna14c.getText().toString().trim());

        HashData.put("dna15",binding.dna15.getText().toString().trim());


        new LocalDataManager(this).InsertRespnoseTable(1,HashData,"ReportDisaster");
        ((Activity) DemageNeedAssesment.this).finish();

    }
        
        // upload image starts
        public  boolean  datavalidation()
        {
            if(binding.dna1Tv.getText().equals(""))
            {
                Toast.makeText(this,"Please select District",Toast.LENGTH_SHORT).show();
                return  false;
            }
            if(binding.dna2Tv.getText().equals("Select Tehsil"))
            {      Toast.makeText(this,"Please Enter Select Tehsil",Toast.LENGTH_SHORT).show();
                return  false;
            }
            if(binding.dna3Tv.getText().toString().trim().equals("") || binding.dna3Tv.getText().toString().trim().isEmpty())
            {
                Toast.makeText(this,"Please Enter Complete Address",Toast.LENGTH_SHORT).show();
                return  false;
            }
            if(binding.dna4Tv.getText().equals(""))
            {
                Toast.makeText(this,"Please Select Type of Disaster",Toast.LENGTH_SHORT).show();
                return  false;
            }
            if(binding.dna5Tv.getText().equals(""))
            {
                Toast.makeText(this,"Please Enter Father Name",Toast.LENGTH_SHORT).show();
                return  false;
            }
            if(binding.dna6Tv.getText().equals(""))
        {
            Toast.makeText(this,"Please Enter CNIC ",Toast.LENGTH_SHORT).show();
            return  false;
        }

            if(binding.dna7Tv.getText().equals(""))
            {
                Toast.makeText(this,"Please Enter Phone Number",Toast.LENGTH_SHORT).show();
                return  false;
            }
            if(binding.dna8aTv.getText().equals(""))
            {
                Toast.makeText(this,"Please Enter Number of People Injured",Toast.LENGTH_SHORT).show();
                return  false;
            }

            if(binding.dna8bTv.getText().equals(""))
            {
                Toast.makeText(this,"Please Enter Number of People Died",Toast.LENGTH_SHORT).show();
                return  false;
            }
            if(!binding.dna9a.isChecked() && !binding.dna9b.isChecked() && !binding.dna9c.isChecked() )
            {
                Toast.makeText(this,"Please Select House Demage",Toast.LENGTH_SHORT).show();
                return  false;
            }

            if(!binding.dna10a.isChecked() && !binding.dna10b.isChecked()  )
            {
                Toast.makeText(this,"Please Select Construction type of house",Toast.LENGTH_SHORT).show();
                return  false;
            }

            if(!binding.dna11a.isChecked() && !binding.dna11b.isChecked()  )
            {
                Toast.makeText(this,"Please Select Ownership Status of Housee",Toast.LENGTH_SHORT).show();
                return  false;
            }

            if(binding.dna14a.getText().equals(""))
            {
                Toast.makeText(this,"Please Enter Cost Repair",Toast.LENGTH_SHORT).show();
                return  false;
            }
            if(binding.dna14b.getText().equals(""))
            {
                Toast.makeText(this,"Please Enter Cost content",Toast.LENGTH_SHORT).show();
                return  false;
            }

            if(binding.dna14c.getText().equals(""))
            {
                Toast.makeText(this,"Please Enter Cost Total",Toast.LENGTH_SHORT).show();
                return  false;
            }



            if(!binding.checkbox.isChecked())
            {
                Toast.makeText(this,"Please check the Undertaking",Toast.LENGTH_SHORT).show();
                return  false;
            }


            return  true;
        }



    }






