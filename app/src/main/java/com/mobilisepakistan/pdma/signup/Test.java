package com.mobilisepakistan.pdma.signup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.mobilisepakistan.pdma.R;
import com.mobilisepakistan.pdma.databinding.ActivityLoginBinding;
import com.mobilisepakistan.pdma.global.PushNotificaionSenddata;
import com.mobilisepakistan.pdma.gps.TurnOnGPS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Test extends AppCompatActivity {

    ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login );


        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean uploadStatus= PushNotificaionSenddata.PushNotificaionSenddata(Test.this, UploadDate());



            }
        });
    }



    public JSONObject UploadDate()
    {

        JSONObject log = new JSONObject();

        JSONObject log2 = new JSONObject();






        try {
            log2.put("Title","Title");

            log .put("to", "f-4rwU41L2YCklYXqGBXmA:APA91bFPPhBF6j4jdHoH330d6tVeCNar3J7yo60KCAE7pVmeL4Xma9xV_0uNkZA24p8XPlcKYT1YVlmlbXv0K7kFQxRZ_cCJU0cec0JTGXPAQS9GYRFRj5K6W0w9IBebbsW-a_-CI2pN");
            log .put("notification", log2);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return  log;
    }
    @Override
    public void onBackPressed() {


        TurnOnGPS.CloseActivityalerd(this);

    }

}
