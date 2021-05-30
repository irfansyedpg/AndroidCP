package com.mobilisepakistan.pdma.report;

import android.app.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobilisepakistan.pdma.R;
import com.mobilisepakistan.pdma.databinding.DailysituationBinding;
import com.mobilisepakistan.pdma.databinding.EarlywarningBinding;
import com.mobilisepakistan.pdma.global.JsonArray;
import com.mobilisepakistan.pdma.gps.TurnOnGPS;

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


public class EarlyWarning extends AppCompatActivity  {
    EarlywarningBinding binding ;
 public static    ArrayList<String> listname=new ArrayList<String>();
 public static   ArrayList<String> listsDate=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.earlywarning);



        binding.lvback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) EarlyWarning.this).finish();
            }
        });


        new GetDataServerB(EarlyWarning.this, "http://175.107.63.137/PEOCMIS/api/values/GetAdvisories",binding.recycleviewR).execute();

    }

    @Override
    public void onBackPressed() {


        TurnOnGPS.CloseActivityalerd(this);

    }
}

// get data from server
class  GetDataServerB extends AsyncTask {
    ViewCustomAdapterB mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView recycleviewR;
    Context mContext;
    ProgressDialog mDialog;
    String mUserMsg, URL;

    public GetDataServerB(Context context, String URL,RecyclerView RV) {
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
        URL url;
        HttpURLConnection connection;

//        String urlString = "http://175.107.63.137/PEOCMIS/api/values/getDSR";
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
            JSONArray contacts = jsonObj.getJSONArray("advisories");
            ArrayList<String> listDate=new ArrayList<String>();
            ArrayList<String> listname=new ArrayList<String>();

            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);
                String shift = c.getString("sub_File");

                String adddate = c.getString("adv_Date");


                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = dateFormat.parse(adddate);
                dateFormat.applyPattern("dd-MM-yyyy");
                adddate=dateFormat.format(date);
                
                 listDate.add(adddate);
                 listname.add(shift);
            }

            mLayoutManager = new LinearLayoutManager(mContext);
            recycleviewR.setLayoutManager(mLayoutManager);
            mAdapter = new ViewCustomAdapterB(mContext, listDate,listname);
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
        } catch (ParseException e) {
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

// Recycle View

class  ViewCustomAdapterB extends RecyclerView.Adapter {

    Context mContext;
    List<String> mListDate;
    List<String> mlistname;
    public ViewCustomAdapterB(Context context, List<String> list,List<String> listname){
        mContext = context;
        mListDate = list;
        mlistname = listname;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dailysituationitem, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ViewHolder vh = (ViewHolder) holder;

        vh.txtdate.setText(mListDate.get(position));
        vh.txtShift.setText(mlistname.get(position));


        vh.txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtdate = vh.txtdate.getText().toString();
                String txtshift = vh.txtShift.getText().toString();



            //    Toast.makeText(mContext,txtdate,Toast.LENGTH_SHORT).show();

                String url="http://175.107.63.39/Advisories/";
                url=url+txtshift;

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                mContext.startActivity(browserIntent);

            }
        });
    }



    @Override
    public int getItemCount() {
        return mListDate.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtdate;
        public TextView txtShift;
        public TextView txtView;

        public ViewHolder(View v) {
            super(v);
            txtdate = (TextView) v.findViewById(R.id.txtdate);
            txtShift = (TextView) v.findViewById(R.id.txtshift);
            txtView = (TextView) v.findViewById(R.id.txtView);
        }
    }


}
