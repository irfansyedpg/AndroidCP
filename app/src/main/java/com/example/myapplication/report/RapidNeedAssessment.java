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
import com.example.myapplication.databinding.RapidneedassesmentBinding;
import com.example.myapplication.global.District;
import com.example.myapplication.global.Tehsil;
import com.example.myapplication.global.TypeDisaster;
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


        binding.lvback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) RapidNeedAssessment.this).finish();
            }
        });

        binding.btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) RapidNeedAssessment.this).finish();
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
    HashMap<String,String> HashData=new HashMap<>();

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

        new LocalDataManager(this).InsertRespnoseTable(1,HashData,"ReportDisaster");
        ((Activity) RapidNeedAssessment.this).finish();

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
        if(!binding.rna5a.isChecked() || !binding.rna5b.isChecked() || !binding.rna5c.isChecked()|| !binding.rna5d.isChecked()|| !binding.rna5e.isChecked() || !binding.rna5f.isChecked()|| !binding.rna5g.isChecked() )
        {
            Toast.makeText(this,"Please Select Immediate Needs",Toast.LENGTH_SHORT).show();
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
