package com.mobilisepakistan.pdma;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.mobilisepakistan.pdma.databinding.AboutappBinding;
import com.mobilisepakistan.pdma.databinding.CommunityoutreachBinding;
import com.mobilisepakistan.pdma.gps.TurnOnGPS;

public class AboutApp extends AppCompatActivity {
    AboutappBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.aboutapp);



    }


    @Override
    public void onBackPressed() {

        TurnOnGPS.CloseActivityalerd(this);
    }


}

