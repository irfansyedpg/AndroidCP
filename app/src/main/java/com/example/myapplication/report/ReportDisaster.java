package com.example.myapplication.report;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.example.myapplication.data.LocalDataManager;
import com.example.myapplication.databinding.ReportdisasterBinding;
import com.example.myapplication.global.District;
import com.example.myapplication.global.GlobalResponseData;
import com.example.myapplication.global.Tehsil;
import com.example.myapplication.global.TypeDisaster;

import com.example.myapplication.global.UploadData2;
import com.example.myapplication.gps.ShowLocationActivity2;
import com.example.myapplication.gps.TurnOnGPS;
import com.google.gson.JsonObject;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class ReportDisaster extends AppCompatActivity  {
    ReportdisasterBinding binding ;
    ArrayList<String> listDistrict;
    ArrayList<String> listTehsil;
    ArrayList<String> listDisaster;
    String sDistrict="";
    String sTehsil="";
    String sDisasterType="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.reportdisaster);
        listDisaster=new ArrayList<>();
        listDistrict=new ArrayList<>();
        listDistrict= District.getDistricts();
        listDisaster= TypeDisaster.getDisaster();


        TurnOnGPS.turnGPSOn(this);


        binding.lvback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) ReportDisaster.this).finish();
            }
        });

        // when clicked on District will open new Activity for District Selection
        binding.rd1LV.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                Intent  intent = new Intent(ReportDisaster.this, RecyclerViewA.class);
                intent.putExtra("mylist",listDistrict);
                intent.putExtra("header","Select District");
                startActivityForResult(intent,11);

            }
        });

        binding.rd2LV.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {

                if(sDistrict.isEmpty())
                {
                    Toast.makeText(ReportDisaster.this,"Please Select District First",Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent  intent = new Intent(ReportDisaster.this, RecyclerViewA.class);
                listTehsil= Tehsil.get(sDistrict);
                intent.putExtra("mylist",listTehsil);
                intent.putExtra("header","Select Tehsil");
                startActivityForResult(intent,12);


            }
        });

        binding.rd4LV.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                Intent  intent = new Intent(ReportDisaster.this, RecyclerViewA.class);
                intent.putExtra("mylist",listDisaster);
                intent.putExtra("header","Select Type of Disaster");
                startActivityForResult(intent,13);

            }
        });
        binding.rd6LV1.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                ImgView=binding.rd6Imgv1;
                selectImage(ReportDisaster.this);

            }
        });
        binding.rd6LV2.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                ImgView=binding.rd6Imgv2;
                selectImage(ReportDisaster.this);

            }
        });
        binding.rd6LV3.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                ImgView=binding.rd6Imgv3;
                selectImage(ReportDisaster.this);

            }
        });

        binding.btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertDb();
               // ((Activity) ReportDisaster.this).finish();

               // turnGPSOn(ReportDisaster.this);



            }
        });

        binding.checkboxGps.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    //Case 1

                   if(TurnOnGPS.CheckGPS(ReportDisaster.this)==false)
                   {
                       TurnOnGPS.turnGPSOn(ReportDisaster.this);
                       ((CheckBox) v).setChecked(false);
                       return;
                   }
                    Intent  intentt = new Intent(ReportDisaster.this, ShowLocationActivity2.class);
                    startActivityForResult(intentt,22);

                }
                else
                {

                }
                //case 2

            }
        });
    }

    ImageView ImgView;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 11) {
            if (resultCode == Activity.RESULT_OK) {
                sDistrict=data.getStringExtra("data");
                binding.rd1Tv.setText(sDistrict);
                binding.rd2Tv.setText(R.string.rd2);

            }
        }
        else if(requestCode == 12 ) {
            if (resultCode == Activity.RESULT_OK) {
                sTehsil = data.getStringExtra("data");
                binding.rd2Tv.setText(sTehsil);

            }
        }

        else if(requestCode == 13 ) {
            if (resultCode == Activity.RESULT_OK) {
                sDisasterType = data.getStringExtra("data");
                binding.rd4Tv.setText(sDisasterType);

            }
        }
        else if(requestCode == 22 ) {
            if (resultCode == Activity.RESULT_OK) {
                String Lat = data.getStringExtra("Lat");
                String Long = data.getStringExtra("Long");

                binding.latitude.setText(Lat);
                binding.longitude.setText(Long);

            }
        }

        // upload image starts

        if(resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        ImgView.setImageBitmap(selectedImage);
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        try {
                            final Uri imageUri = data.getData();
                            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            ImgView.setImageBitmap(selectedImage);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                            Toast.makeText(this, "Something went wrong, Unable to select the immage ", Toast.LENGTH_LONG).show();
                        }

                    }

                    break;
            }
        }


        // upload image Ends

    }


    // upload image
    private void selectImage(Context context) {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Remove Picture","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }else
                {
                    ImgView.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_black_24dp));
                }
            }
        });
        builder.show();
    }


    HashMap<String,String> HashData=new HashMap<>();
    public  void insertDb()
    {
        if(datavalidation()==false)
        {
            return;
        }
        HashData.put("rd1",binding.rd1Tv.getText().toString().trim());
        HashData.put("rd2",binding.rd2Tv.getText().toString().trim());
        HashData.put("rd3",binding.rd3Tv.getText().toString().trim());
        HashData.put("rd4",binding.rd4Tv.getText().toString().trim());
        HashData.put("rd5",binding.rd5Tv.getText().toString().trim());
        HashData.put("rd6a","Pic1");
        HashData.put("rd6b","Pic2");
        HashData.put("rd6c","Pic3");


        String Logpk=LocalDataManager.InsertLogTable("1",binding.latitude.getText().toString(),binding.longitude.getText().toString(),"RD");

        new LocalDataManager(this).InsertRespnoseTable(Integer.parseInt(Logpk),HashData,"ReportDisaster");


//        HashMap<String,List<String>> MpUplod=new HashMap<>();

       boolean uploadStatus= UploadData2.volleyPost(this,LocalDataManager.GetData());


       // ((Activity) ReportDisaster.this).finish();

    }
    public  boolean  datavalidation()
    {
        if(binding.rd1Tv.getText().equals(""))
        {
            Toast.makeText(this,"Please select District",Toast.LENGTH_SHORT).show();
            return  false;
        }
        if(binding.rd2Tv.getText().equals("Select Tehsil"))
        {      Toast.makeText(this,"Please Enter Select Tehsil",Toast.LENGTH_SHORT).show();
            return  false;
        }
        if(binding.rd3Tv.getText().toString().trim().equals("") || binding.rd3Tv.getText().toString().trim().isEmpty())
        {
            Toast.makeText(this,"Please Enter Complete Address",Toast.LENGTH_SHORT).show();
            return  false;
        }
        if(binding.rd4Tv.getText().equals(""))
        {
            Toast.makeText(this,"Please Select Type of Disaster",Toast.LENGTH_SHORT).show();
            return  false;
        }
        if(binding.rd5Tv.getText().toString().trim().equals("") || binding.rd5Tv.getText().toString().trim().isEmpty())
        {
            Toast.makeText(this,"Please Enter Detail of Disaster",Toast.LENGTH_SHORT).show();
            return  false;
        }
        if(!binding.checkbox.isChecked())
        {
            Toast.makeText(this,"Please check the Undertaking",Toast.LENGTH_SHORT).show();
            return  false;
        }
        if(!binding.checkboxGps.isChecked())
        {
            Toast.makeText(this,"Please check the GPS",Toast.LENGTH_SHORT).show();
            return  false;
        }

        return  true;
    }

  @Override
    public  void onBackPressed()
    {

       TurnOnGPS.CloseActivityalerd(this);
    }



//    public JSONObject getJsonResponse(){
//
//        JSONObject Log = new JSONObject();
//        Log=getlog(1,"date","time","lat","long","section");
//
//        JSONArray Resp = new JSONArray();
//        Resp.put(getResponce("Resp1","Varname1","Section1"));
//        Resp.put(getResponce("Resp2","Varname2","Section2"));
//        Resp.put(getResponce("Resp3","Varname3","Section3"));
//
//
//        JSONObject response= new JSONObject();
//
//        try {
//            response.put("logs", Log );
//            response.put("responseTables", Resp );
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//        return response;
//    }
//
//
//    JSONObject  getlog(int UserID, String Datee,String Timee,String Lat,String Long,String Section){
//
//        JSONObject log = new JSONObject();
//        try {
//            log .put("UserID", UserID);
//            log .put("Datee", Datee);
//            log .put("Timee", Timee);
//            log .put("Lat", Lat);
//            log .put("Long", Long);
//            log .put("Section", Section);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//        return log ;
//    }
//    JSONObject  getResponce(String Response, String VarName,String Section){
//
//        JSONObject Resp = new JSONObject();
//        try {
//            Resp .put("Response", Response);
//            Resp .put("VarName", VarName);
//            Resp .put("Section", Section);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//        return Resp ;
//    }






    }




