package com.mobilisepakistanirfan;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.mobilisepakistanirfan.pdma.R;
import com.mobilisepakistanirfan.pdma.databinding.PmdwebviewBinding;
import com.mobilisepakistanirfan.pdma.databinding.WeatheroptionBinding;
import com.mobilisepakistanirfan.pdma.gps.TurnOnGPS;

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

