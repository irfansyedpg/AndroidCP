package com.mobilisepakistan;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.mobilisepakistan.pdma.R;
import com.mobilisepakistan.pdma.databinding.CommunityoutreachBinding;
import com.mobilisepakistan.pdma.databinding.WeatheroptionBinding;
import com.mobilisepakistan.pdma.gps.TurnOnGPS;
import com.mobilisepakistan.pdma.report.EarlyWarning;

public class WeatherAdvisOption extends AppCompatActivity {
    WeatheroptionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.weatheroption);





        binding.lltwter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              Intent  intt = new Intent(WeatherAdvisOption.this, EarlyWarning.class);
                startActivity(intt);
            }
        });


        binding.llyoutube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intt = new Intent(WeatherAdvisOption.this, PMDwebView.class);
                intt.putExtra("header", getString(R.string.wadvpmd));
                intt.putExtra("link", "https://nwfc.pmd.gov.pk/new/press-releases-urdu.php");
                startActivity(intt);
            }
        });

    }


    @Override
    public void onBackPressed() {

        TurnOnGPS.CloseActivityalerd(this);
    }


}

