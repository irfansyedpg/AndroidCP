package com.mobilisepakistanirfan.pdma.report;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobilisepakistanirfan.pdma.R;
import com.mobilisepakistanirfan.pdma.databinding.RecycleviewBinding;
import com.mobilisepakistanirfan.pdma.databinding.RecycleviewbackgroundBinding;
import com.mobilisepakistanirfan.pdma.global.JsonArray;
import com.mobilisepakistanirfan.pdma.global.ServerConfiguration;
import com.mobilisepakistanirfan.pdma.gps.TurnOnGPS;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class RiskAssesment extends AppCompatActivity {

    RecycleviewbackgroundBinding binding ;
    RiskAssesmentCustomAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    String sHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.recycleviewbackground);







        sHeader=getString(R.string.s_rar);
        binding.header.setText(sHeader);
        binding.edSearch.setVisibility(View.GONE);
        new GetDataServerRiskAssesment(RiskAssesment.this, ServerConfiguration.ServerURL+ "GetReiskassesmentAction",binding.recycleview).execute();





        binding.lvback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TurnOnGPS.CloseActivityalerd(RiskAssesment.this);
            }
        });





    }



}

class  RiskAssesmentCustomAdapter extends RecyclerView.Adapter {

    Context mContext;
    List<String> Listitle;
    List<String> Listdetial;
    List<String> Listimg;
    List<String> Listdate;

    public RiskAssesmentCustomAdapter(Context context,List<String> title,List<String> detial,List<String> img,List<String> date ){
        mContext = context;
        Listitle = title;
        Listdate = date;
        Listdetial = detial;
        Listimg = img;

    }

    // filter

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.riskassesment, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }




    Intent data = new Intent();
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder vh = (ViewHolder) holder;

//        vh.txtrcv.setText(mList.get(position));
//        vh.txtconcat.setText(cList.get(position));
//        vh.txtheader.setText(hList.get(position));


        try {
            vh.txttitle.setText(Listitle.get(position));
            vh.txtdetial.setText(Listdetial.get(position));
            vh.txttdate.setText(Listdate.get(position));

            String imurl="https://madadgar.pdma.gov.pk/DisasterImages/"+Listimg.get(position);

        //    String img_url= "http://openweathermap.org/img/wn/"+icon+"@2x.png";

            Picasso.get().load(imurl).into(vh.lv);

         //   vh.lv.setImageBitmap(Listimg.get(position));

        }
        catch (IndexOutOfBoundsException e)
        {
            String a="hiii";
        }
        vh.lv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


             final    Dialog builder = new Dialog(mContext);
                builder.requestWindowFeature(Window.FEATURE_NO_TITLE);

                builder.setContentView(R.layout.cutomdialog);
                builder.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

                builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

               final ImageView imageView =(ImageView)builder.findViewById(R.id.img) ;
                Button btndown =(Button) builder.findViewById(R.id.downlad) ;
                Button btncls =(Button)builder.findViewById(R.id.cancel) ;


               final String imurl="https://madadgar.pdma.gov.pk/DisasterImages/"+Listimg.get(position);
                Picasso.get().load(imurl).into(imageView);




                btncls.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        builder.dismiss();
                    }
                });

                btndown.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        imageView.buildDrawingCache();

                        Bitmap bmp = imageView.getDrawingCache();
                        File storageLoc = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES); //context.getExternalFilesDir(null);

                        int random = (int)(Math.random() * 50 + 1);

                        String filename="PDMAmadagar"+random;
                        File file = new File(storageLoc, filename + ".jpg");

                        try{
                            FileOutputStream fos = new FileOutputStream(file);
                            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.close();



                            Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            scanIntent.setData(Uri.fromFile(file));
                            mContext.sendBroadcast(scanIntent);

                            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            intent.setData(Uri.fromFile(file));
                            mContext.sendBroadcast(intent);

                            builder.dismiss();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });



                builder.show();


            }
        });
    }



    @Override
    public int getItemCount() {
        return Listitle.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtdetial,txttitle,txttdate;

        public ImageView lv;

        public ViewHolder(View v) {
            super(v);
            txtdetial = (TextView) v.findViewById(R.id.detial);
            txttitle = (TextView) v.findViewById(R.id.title);
            txttdate = (TextView) v.findViewById(R.id.dated);

            lv = (ImageView) v.findViewById(R.id.img);
        }
    }








}


class  GetDataServerRiskAssesment extends AsyncTask {
    RiskAssesmentCustomAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView recycleviewR;
    Context mContext;
    ProgressDialog mDialog;
    String mUserMsg, URL;

    public GetDataServerRiskAssesment(Context context, String URL,RecyclerView RV) {
        this.mContext = context;
        this.URL = URL;
        this.recycleviewR=RV;
        mDialog = new ProgressDialog(context);


    }

    @Override
    protected void onPreExecute() {
        mDialog.setMessage("Loading Data...");
        mDialog.setCancelable(false);
        mDialog.show();

        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object[] params) {
        java.net.URL url;
        HttpURLConnection connection;

        String urlString = URL;


        try {
            url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            OutputStream os = connection.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));



            bw.flush();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String data = "", line;
                while ((line = br.readLine()) != null) {
                    data += line;
                }

                return data;
            } else {
                mUserMsg = "Server Couldn't process the request";
            }
        } catch (IOException e) {
            mUserMsg = "Please make sure that Internet connection is available," +
                    " and server IP is inserted in settings";
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        try {

            //connection isn't available or something is wrong with server address
            if(mUserMsg != null)
                throw  new IOException();


            String resp = (String)o;

            JsonArray.ArrayString=resp;

            JSONObject jsonObj = new JSONObject(resp);

            // Getting JSON Array node
            JSONArray contacts = jsonObj.getJSONArray("result");
            ArrayList<String> listtitl=new ArrayList<String>();
            ArrayList<String> listdetial=new ArrayList<String>();
            ArrayList<String> listimg=new ArrayList<String>();
            ArrayList<String> listdate=new ArrayList<String>();

            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);


                String title = c.getString("title");
                String detail = c.getString("detail");
                String img = c.getString("imageName");
                String date = c.getString("datee");




                listtitl.add(title);
                listdetial.add(detail);
                listimg.add(img);
                listdate.add(date);
            }

            mLayoutManager = new LinearLayoutManager(mContext);
            recycleviewR.setLayoutManager(mLayoutManager);
            mAdapter = new RiskAssesmentCustomAdapter(mContext,listtitl,listdetial,listimg,listdate);
            recycleviewR.setAdapter(mAdapter);




            if ( resp == null || resp.equals(""))
                throw new NullPointerException("Server response is empty");
            else if(resp.equals("-1")){
                mUserMsg = "Incorrect username or password";
            } else {
                mUserMsg = null;


            }

        }  catch (IOException e) {
            //if connection was available via connecting but
            //we can't get data from server..
            if(mUserMsg == null)
                mUserMsg = "Please check connection";
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
            mUserMsg = e.getMessage();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (mUserMsg != null)
                Toast.makeText(mContext, mUserMsg, Toast.LENGTH_SHORT).show();
        }
        // hide the progressDialog
        mDialog.hide();

        super.onPostExecute(o);
    }

}