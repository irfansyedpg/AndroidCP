package com.mobilisepakistan.pdma.report;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobilisepakistan.pdma.MapsMarkerActivity;
import com.mobilisepakistan.pdma.R;
import com.mobilisepakistan.pdma.databinding.RecycleviewBinding;
import com.mobilisepakistan.pdma.global.JsonArray;
import com.mobilisepakistan.pdma.global.ServerConfiguration;
import com.mobilisepakistan.pdma.global.emrConacts;
import com.mobilisepakistan.pdma.global.evacCenters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class EvacuationCenter extends AppCompatActivity {

    RecycleviewBinding binding ;
    EvacuationCenterCustomAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    String sHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.recycleview);






        sHeader=getString(R.string.s_ec);
        binding.header.setText(sHeader);


        new GetDataServeEvaCenterContact(EvacuationCenter.this, ServerConfiguration.ServerURL+ "GetEvacuationCenterAction",binding.recycleview).execute();


        binding.lvback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) EvacuationCenter.this).finish();
            }
        });



        binding.edSearch.setVisibility(View.GONE);


        mLayoutManager = new LinearLayoutManager(this);
        binding.recycleview.setLayoutManager(mLayoutManager);





        ((EditText) findViewById(R.id.ed_search)).addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });


    }

    // filter method

    private void filter(String text) {
        //new array location that will hold the filtered data
        ArrayList<String> filterdNames = new ArrayList<>();

       // looping through existing elements
//        for (String s : arrayListall) {
//            //if the existing elements contains the search input
//            if (s.toLowerCase().contains(text.toLowerCase())) {
//                //adding the element to filtered district
//                filterdNames.add(s);
//            }
//        }

        //calling a method of the adapter class and passing the filtered district
   //
        //     mAdapter.filterList(filterdNames);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }



}

class  EvacuationCenterCustomAdapter extends RecyclerView.Adapter {

    Context mContext;
    List<String> mDistrict;
    List<String> nCenterName;
    List<String> mLat;
    List<String> mLong;


    public EvacuationCenterCustomAdapter(Context context,  List<String> listDistrict,  List<String> listCnterName, List<String> listLatitude,  List<String> listLongitude ){
        mContext = context;

        mDistrict=listDistrict;
        nCenterName=listCnterName;
        mLat=listLatitude;
        mLong=listLongitude;


    }

    // filter

    public void filterList(ArrayList<String> filterdNames) {
   //  this.arylistall = filterdNames;
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycleviewdesignevacotion, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    Intent data = new Intent();
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ViewHolder vh = (ViewHolder) holder;

//        vh.txtdistic.setText("DISTRICT: "+mDistrict.get(position) + "\n"+ "\n"
//               + " TEHSIL: "+mTehsil.get(position) + "\n"+ "\n"
//               + " LOC: "+mLocation.get(position) + "\n"+ "\n"
//               + " CENTER: "+mName.get(position) + "\n"+ "\n"
//               + "GPS"+mGPS.get(position) );


        try {

            vh.txthdist.setText(mDistrict.get(position));
       //     vh.txtCntrName.setText(nCenterName.get(position));
         //   vh.txtgps.setText(mLat.get(position)+" "+ mLong.get(position));
        }
        catch (IndexOutOfBoundsException e)
        {

        }


        vh.lv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String dist=vh.txthdist.getText().toString();
              //  String GPS=vh.txtgps.getText().toString();


                try {

//                    String[] cc2name = GPS.split(" ");
//
//
//                    String latt = cc2name[0];
//                    String longg = cc2name[1];
//
//
                    Intent intt = new Intent(mContext, EvacuationCenterFurth.class);
                    intt.putExtra("distt", dist);
//                    intt.putExtra("longg", longg);
//                    intt.putExtra("title", centername);
                   mContext.startActivity(intt);



                }
                catch (Exception ee)
                {
                 //   Toast.makeText(mContext,"Unable to Visit this site GPS="+GPS,Toast.LENGTH_LONG).show();
                }





            }
        });
    }



    @Override
    public int getItemCount() {
        return mDistrict.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txthdist;
    //    public TextView txtCntrName;
    //    public TextView txtgps;

       // public ImageButton lv;
        public LinearLayout lv;

        public ViewHolder(View v) {
            super(v);

            txthdist = (TextView) v.findViewById(R.id.txthdist);
           // txtCntrName = (TextView) v.findViewById(R.id.txtCntrName);
           // txtgps = (TextView) v.findViewById(R.id.txtgps);
            lv=(LinearLayout) v.findViewById(R.id.img2);


        }
    }








}



class  GetDataServeEvaCenterContact extends AsyncTask {
    EvacuationCenterCustomAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView recycleviewR;
    Context mContext;
    ProgressDialog mDialog;
    String mUserMsg, URL;

    public GetDataServeEvaCenterContact(Context context, String URL,RecyclerView RV) {
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
            ArrayList<String> listCnterName=new  ArrayList<String>();
            ArrayList<String> listLatitude=new ArrayList<String>();
            ArrayList<String> listLongitude=new ArrayList<String>();
            ArrayList<String> listDistrict=new ArrayList<String>();

            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);


                String centerName = c.getString("centerName");
                String latitude = c.getString("latitude");
                String longitude = c.getString("longitude");
                String district = c.getString("district");



                if(i>0 )
                {
                    if(!district.equals(contacts.getJSONObject(i-1).getString("district")))
                    {
                        listCnterName.add(centerName);
                        listLatitude.add(latitude);
                        listLongitude.add(longitude);
                        listDistrict.add(district);

                    }

                }
                else
                {
                    listCnterName.add(centerName);
                    listLatitude.add(latitude);
                    listLongitude.add(longitude);
                    listDistrict.add(district);
                }


            }

            mLayoutManager = new LinearLayoutManager(mContext);
            recycleviewR.setLayoutManager(mLayoutManager);
            mAdapter = new EvacuationCenterCustomAdapter(mContext,listDistrict,listCnterName,listLatitude,listLongitude);
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
    private Date parseDate(String date, String format) throws ParseException
    {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.parse(date);
    }
}