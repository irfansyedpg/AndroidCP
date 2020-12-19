package com.example.myapplication.report;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ReportdisasterBinding;
import com.example.myapplication.global.District;
import com.example.myapplication.global.Tehsil;

import java.util.ArrayList;
import java.util.List;

public class ReportDisaster extends AppCompatActivity  {
    ReportdisasterBinding binding ;
    ArrayList<String> listDistrict;
    ArrayList<String> listTehsil;
    String sDistrict="";
    String sTehsil="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.reportdisaster);
        listDistrict= District.getDistricts();



        // when clicked on District will open new Activity for District Selection
        binding.rd1LV.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                Intent  intent = new Intent(ReportDisaster.this, RecyclerViewA.class);
                intent.putExtra("mylist",listDistrict);
                intent.putExtra("header","Select District");
                startActivityForResult(intent,11);

            }
        });

        binding.rd2LV.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {

                if(sDistrict.isEmpty())
                {
                    Toast.makeText(ReportDisaster.this,"Please Select District First",Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent  intent = new Intent(ReportDisaster.this, RecyclerViewA.class);
                listTehsil= Tehsil.get(sDistrict);
                intent.putExtra("mylist",listTehsil);
                intent.putExtra("header","Select Tehsil");
                startActivityForResult(intent,12);

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 11) {
            if (resultCode == Activity.RESULT_OK) {
                sDistrict=data.getStringExtra("data");
                binding.rd1Tv.setText(sDistrict);
                binding.rd2Tv.setText(R.string.rd2);

            }
        }
        else if(requestCode == 12 )
            if (resultCode == Activity.RESULT_OK) {
                sTehsil=data.getStringExtra("data");
                binding.rd2Tv.setText(sTehsil);

            }
    }




}
