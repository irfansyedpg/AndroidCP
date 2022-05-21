package com.mobilisepakistanirfan.pdma.utilities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.mobilisepakistanirfan.pdma.R;
import com.mobilisepakistanirfan.pdma.databinding.AboutappBinding;
import com.mobilisepakistanirfan.pdma.databinding.PolicyBinding;

public class Policy extends AppCompatActivity {
    PolicyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.policy);


        binding.lvback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                Policy.this.finish();

            }
        });

    }


    @Override
    public void onBackPressed() {

        Policy.this.finish();
      //  TurnOnGPS.CloseActivityalerd(this);
    }


}

