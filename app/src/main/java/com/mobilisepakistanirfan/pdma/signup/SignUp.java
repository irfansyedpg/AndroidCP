package com.mobilisepakistanirfan.pdma.signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mobilisepakistanirfan.pdma.R;
import com.mobilisepakistanirfan.pdma.data.LocalDataManager;
import com.mobilisepakistanirfan.pdma.databinding.SignupBinding;
import com.mobilisepakistanirfan.pdma.global.MyPref;
import com.mobilisepakistanirfan.pdma.gps.TurnOnGPS;
import com.mobilisepakistanirfan.pdma.report.RecyclerViewA;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SignUp extends AppCompatActivity {
    SignupBinding binding ;
    String sDistrict="";
    String sTehsil="";
    ArrayList<String> listDistrict;
    ArrayList<String> listTehsil;
    MyPref preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.signup );

        listDistrict=new ArrayList<>();
        listDistrict= LocalDataManager.GetDistricts(this);
        preferences = new MyPref(this);
        binding.lvback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // ((Activity) SignUp.this).finish();

                TurnOnGPS.CloseActivityalerd(SignUp.this);
            }
        });

        binding.rd1LV.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, RecyclerViewA.class);
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
                    Toast.makeText(SignUp.this,"Please Select District First",Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent  intent = new Intent(SignUp.this, RecyclerViewA.class);
                listTehsil= LocalDataManager.GetTehsils(sDistrict,SignUp.this);
                intent.putExtra("mylist",listTehsil);
                intent.putExtra("header","Select Tehsil");
                startActivityForResult(intent,12);


            }
        });

        binding.signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(datavalidation()==false)
                {
                    return;
                }
                boolean uploadStatus= SignUpServer.SignUpServer(SignUp.this, UploadDate(), binding.rd1Tv.getText().toString().trim(),binding.mobileno.getText().toString().trim(),binding.email.getText().toString().trim(),PDMAUSER);




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
        else if(requestCode == 12 ) {
            if (resultCode == Activity.RESULT_OK) {
                sTehsil = data.getStringExtra("data");
                binding.rd2Tv.setText(sTehsil);

            }
        }




    }

    public  boolean  datavalidation()
    {
        if(binding.rd1Tv.getText().equals(""))
        {
            Toast.makeText(this,"Please select District",Toast.LENGTH_SHORT).show();
            return  false;
        }
        if(binding.rd2Tv.getText().equals("Select Tehsil"))
        {      Toast.makeText(this,"Please Enter Select Tehsil",Toast.LENGTH_SHORT).show();
            return  false;
        }
        if(binding.rd3Tv.getText().toString().trim().equals("") || binding.rd3Tv.getText().toString().trim().isEmpty())
        {
            Toast.makeText(this,"Please Enter Complete Address",Toast.LENGTH_SHORT).show();
            return  false;
        }

        if(binding.name.getText().toString().trim().equals("") || binding.name.getText().toString().trim().isEmpty())
        {
            Toast.makeText(this,"Please Enter Full Name",Toast.LENGTH_SHORT).show();
            return  false;
        }
//        if(binding.cnic.getText().toString().trim().equals("") || binding.cnic.getText().toString().trim().isEmpty())
//        {
//            Toast.makeText(this,"Please Enter CNIC",Toast.LENGTH_SHORT).show();
//            return  false;
//        }
        else
        {
//            if(binding.cnic.getText().toString().trim().length()!=13)
//            {
//                Toast.makeText(this,"Please Enter Correct CNIC with out dashes",Toast.LENGTH_SHORT).show();
//                return  false;
//            }
        }
        if(binding.mobileno.getText().toString().trim().equals("") || binding.mobileno.getText().toString().trim().isEmpty())
        {
            Toast.makeText(this,"Please Enter Mobile Number",Toast.LENGTH_SHORT).show();
            return  false;
        }
        else
        {
            if(binding.mobileno.getText().toString().trim().length()!=11)
            {
                Toast.makeText(this,"Please Enter Correct Mobile Number",Toast.LENGTH_SHORT).show();
                return  false;
            }

        }

        if(binding.email.getText().toString().trim().equals("") || binding.email.getText().toString().trim().isEmpty())
        {
            Toast.makeText(this,"Please Enter Email",Toast.LENGTH_SHORT).show();
            return  false;
        }
        else
        {
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            String emailPattern2 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+";

            String emailPattern3 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+\\.+[a-z]+";


            if (!binding.email.getText().toString().matches(emailPattern) ||  !binding.email.getText().toString().matches(emailPattern2) ||  !binding.email.getText().toString().matches(emailPattern3))
            {
//                Toast.makeText(getApplicationContext(),"Invalid email address", Toast.LENGTH_SHORT).show();
//                return  false;
            }

        }

        if(binding.pass1.getText().toString().trim().equals("") || binding.pass1.getText().toString().trim().isEmpty())
        {
            Toast.makeText(this,"Please Enter Password",Toast.LENGTH_SHORT).show();
            return  false;
        }
        else
        {
            if(binding.pass1.getText().toString().trim().length()<6)
            {
                Toast.makeText(this,"Password Must be greater than 6 character",Toast.LENGTH_SHORT).show();
                return  false;
            }

        }
        if(binding.pass2.getText().toString().trim().equals("") || binding.pass2.getText().toString().trim().isEmpty())
        {
            Toast.makeText(this,"Please Confirm Password",Toast.LENGTH_SHORT).show();
            return  false;
        }
        else
        {
           if(!binding.pass2.getText().toString().trim().equals(binding.pass1.getText().toString()))
           {
               Toast.makeText(this,"Password do not matches",Toast.LENGTH_SHORT).show();
               return  false;
           }

        }
        if(!binding.rd1.isChecked() && !binding.rd2.isChecked() && !binding.rd3.isChecked() )
        {
            Toast.makeText(this,"Please select Register As",Toast.LENGTH_SHORT).show();
            return  false;
        }



        return  true;
    }

    String PDMAUSER;
    public  JSONObject UploadDate()
    {


        JSONObject log = new JSONObject();
        try {




            log .put("Name", binding.name.getText().toString().trim());
            log .put("ContactNo", binding.mobileno.getText().toString().trim());
            log .put("Email", binding.email.getText().toString().trim());
            log .put("District", binding.rd1Tv.getText().toString().trim());
            log .put("Tehsil", binding.rd2Tv.getText().toString().trim());
            log .put("Address", binding.rd3Tv.getText().toString().trim());
            log .put("Latt", preferences.getlat());
            log .put("Longg", preferences.getlong());
            if(binding.rd1.isChecked()) {
                log.put("Type",
                        "1");

                PDMAUSER="1";

            }
            else if(binding.rd2.isChecked()) {
                log.put("Type", "2");
                PDMAUSER="2";
            }
            else if(binding.rd3.isChecked()) {
                log.put("Type", "3");
                PDMAUSER="3";
            }

            log .put("Password", binding.pass1.getText().toString().trim());
            log .put("CNIC", "00");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  log;
    }

    @Override
    public void onBackPressed() {


        TurnOnGPS.CloseActivityalerd(SignUp.this);

    }

}
