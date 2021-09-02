package com.mobilisepakistan.pdma;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.mobilisepakistan.pdma.databinding.CommunityoutreachBinding;
import com.mobilisepakistan.pdma.gps.TurnOnGPS;
import com.mobilisepakistan.pdma.report.Flyers;

public class CommunityOutreach extends AppCompatActivity {
    CommunityoutreachBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.communityoutreach);

        binding.imgfbk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/PDMAmediacell"));
                startActivity(browserIntent);
            }
        });



        binding.twiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/pdmakpk"));
                startActivity(browserIntent);
            }
        });


        binding.imgyoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com/channel/UCCfpxBgGJqV4phk_aqdfdSg"));
                startActivity(browserIntent);
            }
        });


        binding.lnk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/company/provincial-disaster-management-authority-pdma-peshawar/"));
                startActivity(browserIntent);
            }
        });


        binding.web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent browserIntent = new Intent(CommunityOutreach.this, Flyers.class);
                startActivity(browserIntent);
            }
        });

    }


    @Override
    public void onBackPressed() {

        TurnOnGPS.CloseActivityalerd(this);
    }


}

