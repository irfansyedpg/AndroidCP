package com.mobilisepakistan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.mobilisepakistan.pdma.R;
import com.mobilisepakistan.pdma.databinding.PmdwebviewBinding;
import com.mobilisepakistan.pdma.databinding.WeatheroptionBinding;
import com.mobilisepakistan.pdma.gps.TurnOnGPS;
import com.mobilisepakistan.pdma.report.EarlyWarning;

public class PMDwebView extends AppCompatActivity {
    PmdwebviewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.pmdwebview);


        String header = getIntent().getExtras().getString("header","header");
        String link = getIntent().getExtras().getString("link","link");

        binding.headertxt.setText(header);

      // binding.webView.loadUrl("https://nwfc.pmd.gov.pk/new/press-releases-urdu.php");
      binding.webView.loadUrl(link);




    }


    @Override
    public void onBackPressed() {

        TurnOnGPS.CloseActivityalerd(this);
    }


}

