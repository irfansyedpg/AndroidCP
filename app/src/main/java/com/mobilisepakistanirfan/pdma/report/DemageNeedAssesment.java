package com.mobilisepakistanirfan.pdma.report;

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

import com.mobilisepakistanirfan.pdma.R;

import com.mobilisepakistanirfan.pdma.data.LocalDataManager;
import com.mobilisepakistanirfan.pdma.databinding.DemgneedassesmentBinding;
import com.mobilisepakistanirfan.pdma.global.MyPref;
import com.mobilisepakistanirfan.pdma.global.TypeDisaster;
import com.mobilisepakistanirfan.pdma.global.UploadData2;
import com.mobilisepakistanirfan.pdma.gps.ShowLocationActivity2;
import com.mobilisepakistanirfan.pdma.gps.TurnOnGPS;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class DemageNeedAssesment extends AppCompatActivity  {
    DemgneedassesmentBinding binding ;
    ArrayList<String> listDistrict;
    ArrayList<String> listTehsil;
    ArrayList<String> listDisaster;

    String sDistrict="";
    String sTehsil="";
    String Logpk="";
    MyPref preferences;
    int UserID=0;
    String sDisasterType="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.demgneedassesment );
        listDistrict= LocalDataManager.GetDistricts(this);
        listDisaster=new ArrayList<>();

        listDisaster= TypeDisaster.getDisaster();

        TurnOnGPS.turnGPSOn(this);

        preferences = new MyPref(this);
        UserID=preferences.getUserId();

        UploadFailur=false;


        // show alert

       // fun_alert(this);
        // when clicked on District will open new Activity for District Selection
        binding.dna1LV.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                Intent  intent = new Intent(DemageNeedAssesment.this, RecyclerViewA.class);
                intent.putExtra("mylist",listDistrict);
                intent.putExtra("header",getString(R.string.s_g_h_select_dist));
                startActivityForResult(intent,11);

            }
        });

        binding.dna2LV.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {

                if(sDistrict.isEmpty())
                {
                    Toast.makeText(DemageNeedAssesment.this,"Please Select District First",Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent  intent = new Intent(DemageNeedAssesment.this, RecyclerViewA.class);
                listTehsil= LocalDataManager.GetTehsils(sDistrict,getBaseContext());
                intent.putExtra("mylist",listTehsil);
                intent.putExtra("header",getString(R.string.s_g_h_select_tesh));
                startActivityForResult(intent,12);

            }
        });

        binding.lvback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TurnOnGPS.CloseActivityalerd(DemageNeedAssesment.this);
            }
        });

        binding.btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertDb();
            }
        });

        binding.rd6LV1.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                ImgView=binding.rd6Imgv1;
                selectImage(DemageNeedAssesment.this);

            }
        });
        binding.rd6LV2.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                ImgView=binding.rd6Imgv2;
                selectImage(DemageNeedAssesment.this);

            }
        });
        binding.rd6LV3.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                ImgView=binding.rd6Imgv3;
                selectImage(DemageNeedAssesment.this);

            }
        });


        binding.rd4LV.setOnClickListener(new View.OnClickListener() {
            //@Override
            public void onClick(View v) {
                Intent  intent = new Intent(DemageNeedAssesment.this, RecyclerViewA.class);
                intent.putExtra("mylist",listDisaster);
                intent.putExtra("header",getString(R.string.s_h_type_disaster));
                startActivityForResult(intent,13);

            }
        });

        binding.checkboxGps.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    //Case 1

                    if(preferences.getlat().equals("0")) {
                        if (TurnOnGPS.CheckGPS(DemageNeedAssesment.this) == false) {
                            TurnOnGPS.turnGPSOn(DemageNeedAssesment.this);
                            ((CheckBox) v).setChecked(false);
                            return;
                        }
                        Intent intentt = new Intent(DemageNeedAssesment.this, ShowLocationActivity2.class);
                        startActivityForResult(intentt, 22);
                    }
                    else
                    {
                        String lat=preferences.getlat();
                        String longg=preferences.getlong();
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



    public  void fun_alert(Context mContext)
    {
        String Dilogtext = mContext.getString(R.string.DNA_alert);

        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext).setTitle(mContext.getString(R.string.s_g_h_subnote)).setMessage(Dilogtext);

        dialog.setCancelable(false);

        dialog.setPositiveButton(mContext.getString(R.string.s_g_h_subnote_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {







            }
        });
        dialog.create().show();

    }

    ImageView ImgView;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 11) {
            if (resultCode == Activity.RESULT_OK) {
                sDistrict=data.getStringExtra("data");
                binding.dna1Tv.setText(sDistrict);
                binding.dna2Tv.setText(R.string.dna2);

            }
        }
        else if(requestCode == 12 ) {
            if (resultCode == Activity.RESULT_OK) {
                sTehsil = data.getStringExtra("data");
                binding.dna2Tv.setText(sTehsil);

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

    HashMap<String,String> HashData=new HashMap<>();
    Boolean UploadFailur=false;
    public  void insertDb()
    {
        if(datavalidation()==false)
        {
            return;
        }
        HashData.put("District",binding.dna1Tv.getText().toString().trim());
        HashData.put("Tehsil",binding.dna2Tv.getText().toString().trim());
        HashData.put("Disaster Type",binding.rd4Tv.getText().toString().trim());
        HashData.put("Address",binding.dna3Tv.getText().toString().trim());
        HashData.put("Name",binding.dna4Tv.getText().toString().trim());
        HashData.put("Father Name",binding.dna5Tv.getText().toString().trim());
        HashData.put("CNIC",binding.dna6Tv.getText().toString().trim());
        HashData.put("Phone Number",binding.dna7Tv.getText().toString().trim());

        HashData.put("Number of People Injured",binding.dna8aTv.getText().toString().trim());
        HashData.put("Number of People Died",binding.dna8bTv.getText().toString().trim());

        if(binding.dna9a.isChecked()) HashData.put("House","No damage");
        else if(binding.dna9a.isChecked()) HashData.put("House","Partially");
        else if(binding.dna9a.isChecked()) HashData.put("House","Fully");
        else  HashData.put("House","0");

        if(binding.dna10a.isChecked()) HashData.put("type of house","Kacha House");
        else if(binding.dna10b.isChecked()) HashData.put("type of house","Pakha");
        else  HashData.put("type of house","0");

        if(binding.dna11a.isChecked()) HashData.put("Ownership Status of House","Owen");
        else if(binding.dna11b.isChecked()) HashData.put("Ownership Status of House","Rented");
        else  HashData.put("Ownership Status of House","0");


        if(binding.dna12a.isChecked()) HashData.put("Damages to Agriculture Crops","1");
        else HashData.put("Damages to Agriculture Crops","0");
        if(binding.dna12b.isChecked()) HashData.put("Horticulture","1");
        else HashData.put("Horticulture","0");

        if(binding.dna12c.isChecked()) HashData.put("Communal Forest","1");
        else HashData.put("Communal Forest","0");

        if(binding.dna12d.isChecked()) HashData.put("Livestock","1");
        else HashData.put("Livestock","0");

        HashData.put("Any Other Damages Livelihood",binding.dna12e.getText().toString().trim());


        if(binding.dna13a.isChecked()) HashData.put("Shop","1");
        else HashData.put("Shop","0");
        if(binding.dna13b.isChecked()) HashData.put("Hotel","1");
        else HashData.put("Hotel","0");

        if(binding.dna13c.isChecked()) HashData.put("Petrol Pump","1");
        else HashData.put("Petrol Pump","0");

        HashData.put("Other Damages Property ",binding.dna13d.getText().toString().trim());

       // HashData.put("dna14a",binding.dna14a.getText().toString().trim());
      //  HashData.put("dna14b",binding.dna14b.getText().toString().trim());
       // HashData.put("dna14c",binding.dna14c.getText().toString().trim());

        HashData.put("Comments",binding.dna15.getText().toString().trim());




        int DistrictId=LocalDataManager.GetDistrictId(binding.dna1Tv.getText().toString().trim(),this);

        if (UploadFailur==false) {
            Logpk = LocalDataManager.InsertLogTable(Integer.toString(UserID), binding.latitude.getText().toString(), binding.longitude.getText().toString(), "DNA", this);
            new LocalDataManager(this).InsertRespnoseTable(Integer.parseInt(Logpk), HashData, "DNA");
        }

//        HashMap<String,List<String>> MpUplod=new HashMap<>();



        // Upload Picture
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

         UploadData2.volleyPost(this,LocalDataManager.GetData(Logpk,DistrictId,binding.rd4Tv.getText().toString().trim()),"DNA",bitmapparr);

//        if(uploadStatus==true)
//        {
//            LocalDataManager.UpdateLOgtable(Logpk);
//            ((Activity) DemageNeedAssesment.this).finish();
//        }
//        else
//        {
//            UploadFailur=true;
//            new AlertDialog.Builder(this).
//                    setMessage("Unable to Upload Data to Server Due to Internet Would you like to try again or you will upload it latter .").
//                    setPositiveButton("I will Upload Letter", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            ((Activity) DemageNeedAssesment.this).finish();
//                        }
//                    }).setNegativeButton("Try Again", null).create().show();
//        }

    }
        
        // upload image starts
        public  boolean  datavalidation()
        {
            if(binding.dna1Tv.getText().equals(""))
            {
                Toast.makeText(this,"Please select District",Toast.LENGTH_SHORT).show();
                return  false;
            }
            if(binding.dna2Tv.getText().equals("Select Tehsil"))
            {      Toast.makeText(this,"Please Enter Select Tehsil",Toast.LENGTH_SHORT).show();
                return  false;
            }

            if(binding.rd4Tv.getText().equals(""))
            {
                Toast.makeText(this,getString(R.string.s_h_type_disaster),Toast.LENGTH_SHORT).show();
                return  false;
            }

            if(binding.dna3Tv.getText().toString().trim().equals("") || binding.dna3Tv.getText().toString().trim().isEmpty())
            {
                Toast.makeText(this,"Please Enter Complete Address",Toast.LENGTH_SHORT).show();
                return  false;
            }
            if(binding.dna4Tv.getText().equals("") || binding.dna4Tv.getText().toString().trim().isEmpty())
            {
                Toast.makeText(this,"Please Enter Name",Toast.LENGTH_SHORT).show();
                return  false;
            }
            if(binding.dna5Tv.getText().equals("") || binding.dna5Tv.getText().toString().trim().isEmpty())
            {
                Toast.makeText(this,"Please Enter Father Name",Toast.LENGTH_SHORT).show();
                return  false;
            }
            if(binding.dna6Tv.getText().equals("") || binding.dna6Tv.getText().toString().trim().isEmpty())
        {
            Toast.makeText(this,"Please Enter CNIC ",Toast.LENGTH_SHORT).show();
            return  false;
        }

            if(binding.dna7Tv.getText().equals("") || binding.dna7Tv.getText().toString().trim().isEmpty())
            {
                Toast.makeText(this,"Please Enter Phone Number",Toast.LENGTH_SHORT).show();
                return  false;
            }
            if(binding.dna8aTv.getText().equals("") || binding.dna8aTv.getText().toString().trim().isEmpty())
            {
                Toast.makeText(this,"Please Enter Number of People Injured",Toast.LENGTH_SHORT).show();
                return  false;
            }

            if(binding.dna8bTv.getText().equals("") || binding.dna8bTv.getText().toString().trim().isEmpty())
            {
                Toast.makeText(this,"Please Enter Number of People Died",Toast.LENGTH_SHORT).show();
                return  false;
            }
            if(!binding.dna9a.isChecked() && !binding.dna9b.isChecked() && !binding.dna9c.isChecked() )
            {
                Toast.makeText(this,"Please Select House Demage",Toast.LENGTH_SHORT).show();
                return  false;
            }

            if(!binding.dna10a.isChecked() && !binding.dna10b.isChecked()  )
            {
                Toast.makeText(this,"Please Select Construction type of house",Toast.LENGTH_SHORT).show();
                return  false;
            }

            if(!binding.dna11a.isChecked() && !binding.dna11b.isChecked()  )
            {
                Toast.makeText(this,"Please Select Ownership Status of Housee",Toast.LENGTH_SHORT).show();
                return  false;
            }

            if(binding.dna14a.getText().equals("") || binding.dna14a.getText().toString().trim().isEmpty())
            {
              //  Toast.makeText(this,"Please Enter Cost Repair",Toast.LENGTH_SHORT).show();
             //   return  false;
            }
            if(binding.dna14b.getText().equals("")   || binding.dna14b.getText().toString().trim().isEmpty())
            {
               // Toast.makeText(this,"Please Enter Cost content",Toast.LENGTH_SHORT).show();
              //  return  false;
            }

            if(binding.dna14c.getText().equals("")  || binding.dna14c.getText().toString().trim().isEmpty())
            {
               // Toast.makeText(this,"Please Enter Cost Total",Toast.LENGTH_SHORT).show();
                //return  false;
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


    // Upload Picture

    // upload image

    private Bitmap bitmapimag1=null;
    private Bitmap bitmapimag2=null;
    private Bitmap bitmapimag3=null;

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




}






