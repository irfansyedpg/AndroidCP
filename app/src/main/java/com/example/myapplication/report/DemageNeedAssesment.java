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

import com.example.myapplication.databinding.DemgneedassesmentBinding;
import com.example.myapplication.global.District;
import com.example.myapplication.global.Tehsil;
import com.example.myapplication.global.TypeDisaster;

import java.util.ArrayList;

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
                ((Activity) DemageNeedAssesment.this).finish();
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

        // upload image starts




    }






