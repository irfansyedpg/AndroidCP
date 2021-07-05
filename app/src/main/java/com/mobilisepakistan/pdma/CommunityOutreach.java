package com.mobilisepakistan.pdma;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.mobilisepakistan.pdma.data.LocalDataManager;
import com.mobilisepakistan.pdma.databinding.CommunityoutreachBinding;
import com.mobilisepakistan.pdma.databinding.ReportdisasterBinding;
import com.mobilisepakistan.pdma.global.District;
import com.mobilisepakistan.pdma.global.MyPref;
import com.mobilisepakistan.pdma.global.Tehsil;
import com.mobilisepakistan.pdma.global.TypeDisaster;
import com.mobilisepakistan.pdma.global.UploadData2;
import com.mobilisepakistan.pdma.gps.ShowLocationActivity2;
import com.mobilisepakistan.pdma.gps.TurnOnGPS;
import com.mobilisepakistan.pdma.report.RecyclerViewA;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

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

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.pdma.gov.pk/"));
                startActivity(browserIntent);
            }
        });

    }


    @Override
    public void onBackPressed() {

        TurnOnGPS.CloseActivityalerd(this);
    }


}

