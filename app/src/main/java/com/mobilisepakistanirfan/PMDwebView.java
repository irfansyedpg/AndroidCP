package com.mobilisepakistanirfan;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.mobilisepakistanirfan.pdma.R;
import com.mobilisepakistanirfan.pdma.databinding.PmdwebviewBinding;
import com.mobilisepakistanirfan.pdma.databinding.WeatheroptionBinding;
import com.mobilisepakistanirfan.pdma.gps.TurnOnGPS;
import com.mobilisepakistanirfan.pdma.report.WeatherForecast;

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


        binding.lvback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                PMDwebView.this.finish();

            }
        });

    }


    @Override
    public void onBackPressed() {
        PMDwebView.this.finish();

       // TurnOnGPS.CloseActivityalerd(this);
    }


}

