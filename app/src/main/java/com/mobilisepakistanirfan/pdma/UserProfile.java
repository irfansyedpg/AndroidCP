package com.mobilisepakistanirfan.pdma;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.mobilisepakistanirfan.pdma.databinding.AboutappBinding;
import com.mobilisepakistanirfan.pdma.databinding.ActivityProfileBinding;
import com.mobilisepakistanirfan.pdma.global.MyPref;
import com.mobilisepakistanirfan.pdma.global.UserPref;

public class UserProfile extends AppCompatActivity {
    ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);


        UserPref userpref=new UserPref(UserProfile.this);
        MyPref mypref=new MyPref(UserProfile.this);

        binding.dist.setText(mypref.getUserDistrict());

        binding.email.setText(userpref.getUserEmail());
        binding.mobno.setText(userpref.getUserMobileNo());

        String Usertype="";
        if(userpref.getUserType().equals("1"))
        {
            Usertype="citizen";

        }
        else  if(userpref.getUserType().equals("2"))
        {
            Usertype="volunteer";

        }

        else  if(userpref.getUserType().equals("3"))
        {
            Usertype="PDMA Staff";

        }


        binding.usertype.setText(Usertype);

        if(userpref.getUserStatus().equals("Not Active"))
        {
            binding.userstatus.setTextColor((ContextCompat.getColor(this, R.color.red)));
        }
        binding.userstatus.setText(userpref.getUserStatus());



        binding.lvback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserProfile.this.finish();

            }
        });

    }


    @Override
    public void onBackPressed() {

        UserProfile.this.finish();
      //  TurnOnGPS.CloseActivityalerd(this);
    }


}

