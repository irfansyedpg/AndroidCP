package com.mobilisepakistan.pdma.report;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.mobilisepakistan.pdma.R;
import com.mobilisepakistan.pdma.data.LocalDataManager;
import com.mobilisepakistan.pdma.databinding.ReportdisasterBinding;
import com.mobilisepakistan.pdma.global.MyPref;
import com.mobilisepakistan.pdma.global.ServerConfiguration;
import com.mobilisepakistan.pdma.global.TypeDisaster;

import com.mobilisepakistan.pdma.global.UploadData2;
import com.mobilisepakistan.pdma.global.testVolleyMultipartRequest;
import com.mobilisepakistan.pdma.gps.ShowLocationActivity2;
import com.mobilisepakistan.pdma.gps.TurnOnGPS;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportDisaster extends AppCompatActivity  {
    ReportdisasterBinding binding ;
    ArrayList<String> listDistrict;
    ArrayList<String> listTehsil;
    ArrayList<String> listDisaster;
    String sDistrict="";
    String sTehsil="";
    String sDisasterType="";
    String Logpk="";
    MyPref preferences;
    int UserID=0;
    private Bitmap bitmapimag1=null;
    private Bitmap bitmapimag2=null;
    private Bitmap bitmapimag3=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.reportdisaster);
        listDisaster=new ArrayList<>();
        listDistrict=new ArrayList<>();
        listDistrict= LocalDataManager.GetDistricts(this);
        listDisaster= TypeDisaster.getDisaster();

        UploadFailur=false;
       // TurnOnGPS.turnGPSOn(this);
        preferences = new MyPref(this);
        UserID=preferences.getUserId();

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
                intent.putExtra("header",getString(R.string.s_g_h_select_dist));
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
                listTehsil=LocalDataManager.GetTehsils(sDistrict,getBaseContext());
                intent.putExtra("mylist",listTehsil);
                intent.putExtra("header",getString(R.string.s_g_h_select_tesh));
                startActivityForResult(intent,12);


            }
        });

        binding.rd4LV.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                Intent  intent = new Intent(ReportDisaster.this, RecyclerViewA.class);
                intent.putExtra("mylist",listDisaster);
                intent.putExtra("header",getString(R.string.s_h_type_disaster));
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
        });      binding.btnsubmit.setOnClickListener(new View.OnClickListener() {
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

                    if(preferences.getlat().equals("0")) {

                        if (TurnOnGPS.CheckGPS(ReportDisaster.this) == false) {
                            TurnOnGPS.turnGPSOn(ReportDisaster.this);
                            ((CheckBox) v).setChecked(false);
                            return;
                        }
                        Intent intentt = new Intent(ReportDisaster.this, ShowLocationActivity2.class);
                        startActivityForResult(intentt, 22);
                    }
                    else
                    {
                        binding.latitude.setText(preferences.getlat());
                        binding.longitude.setText(preferences.getlong());

                    }


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

                        if(ImgView==binding.rd6Imgv1)
                        {
                            bitmapimag1=selectedImage;
                        }
                        else if(ImgView==binding.rd6Imgv2)
                        {
                            bitmapimag2=selectedImage;
                        }
                        else if(ImgView==binding.rd6Imgv3)
                        {
                            bitmapimag3=selectedImage;
                        }
                    }

                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        try {
                            final Uri imageUri = data.getData();
                            String Pathtesting=getRealPathFromURI(imageUri);

                            final InputStream imageStream = getContentResolver().openInputStream(imageUri);

                            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            ImgView.setImageBitmap(selectedImage);

                            if(ImgView==binding.rd6Imgv1)
                            {
                                bitmapimag1=selectedImage;
                            }
                            else if(ImgView==binding.rd6Imgv2)
                            {
                                bitmapimag2=selectedImage;
                            }
                            else if(ImgView==binding.rd6Imgv3)
                            {
                                bitmapimag3=selectedImage;
                            }

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
                //    String filpath=android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
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
    Boolean UploadFailur=false;
    public  void insertDb()
    {
        if(datavalidation()==false)
        {
            return;
        }
        HashData.put("District",binding.rd1Tv.getText().toString().trim());
        HashData.put("Tehsil",binding.rd2Tv.getText().toString().trim());
        HashData.put("Address",binding.rd3Tv.getText().toString().trim());
        HashData.put("Disaster Type",binding.rd4Tv.getText().toString().trim());
        HashData.put("Disaster Detail",binding.rd5Tv.getText().toString().trim());
     //   HashData.put("Pic1","Pic1");
      //  HashData.put("Pic2","Pic2");
      //  HashData.put("Pic3","Pic3");

        int DistrictId=LocalDataManager.GetDistrictId(binding.rd1Tv.getText().toString().trim(),this);


        if (UploadFailur==false) {
             Logpk = LocalDataManager.InsertLogTable(Integer.toString(UserID), binding.latitude.getText().toString(), binding.longitude.getText().toString(), "RD", this);

            new LocalDataManager(this).InsertRespnoseTable(Integer.parseInt(Logpk), HashData, "RD");
        }


        ArrayList<Bitmap> bitmapparr=new ArrayList<>();


        try {


            if (!bitmapimag1.equals(null) || !bitmapimag1.equals("")) {

                bitmapparr.add(bitmapimag1);


            }
        }
        catch (Exception e)
        {

        }

        try {


            if (!bitmapimag2.equals(null) || !bitmapimag2.equals("")) {

                bitmapparr.add(bitmapimag2);

            }
        }
        catch (Exception e)
        {

        }

        try {
            if (!bitmapimag3.equals(null) || !bitmapimag3.equals("")) {

                bitmapparr.add(bitmapimag3);

            }
        }
        catch (Exception e)
        {
            
        }


       String LastLogId= UploadData2.volleyPost(this,LocalDataManager.GetData(Logpk,DistrictId,binding.rd4Tv.getText().toString().trim()),"RD",bitmapparr);







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
            Toast.makeText(this,getString(R.string.s_h_type_disaster),Toast.LENGTH_SHORT).show();
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



    // testing to get real path

    public String getRealPathFromURI(Uri contentUri) {

        // can post image
        String [] proj={MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery( contentUri,
                proj, // Which columns to return
                null,       // WHERE clause; which rows to return (all rows)
                null,       // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }




    // upload pictures to server
    private static final String ROOT_URL = ServerConfiguration.ServerURL+ "RDImageAction";

    private void uploadBitmap(final Bitmap bitmap,final String logid) {

        //getting the tag from the edittext
        //   final String tags = editTextTags.getText().toString().trim();

        //our custom volley request
        testVolleyMultipartRequest testVolleyMultipartRequest = new testVolleyMultipartRequest(Request.Method.POST, ROOT_URL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("LogId", logid);
                params.put("Path", "Path");
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                //    long imagename = System.currentTimeMillis();
                params.put("ImageFile", new DataPart("imagename" + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(testVolleyMultipartRequest);
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }




}




