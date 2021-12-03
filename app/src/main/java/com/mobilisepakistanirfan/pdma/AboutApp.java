package com.mobilisepakistanirfan.pdma;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.mobilisepakistanirfan.PMDwebView;
import com.mobilisepakistanirfan.pdma.databinding.AboutappBinding;
import com.mobilisepakistanirfan.pdma.databinding.CommunityoutreachBinding;
import com.mobilisepakistanirfan.pdma.gps.TurnOnGPS;

public class AboutApp extends AppCompatActivity {
    AboutappBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.aboutapp);


        binding.lvback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                AboutApp.this.finish();

            }
        });

    }


    @Override
    public void onBackPressed() {

        AboutApp.this.finish();
      //  TurnOnGPS.CloseActivityalerd(this);
    }


}

