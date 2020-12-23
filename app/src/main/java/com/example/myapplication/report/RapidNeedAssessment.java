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
import com.example.myapplication.databinding.RapidneedassesmentBinding;
import com.example.myapplication.global.District;
import com.example.myapplication.global.Tehsil;
import com.example.myapplication.global.TypeDisaster;
import java.util.ArrayList;

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
        listDistrict= District.getDistricts();
        listDisaster= TypeDisaster.getDisaster();



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

        // upload image starts




    }





}
