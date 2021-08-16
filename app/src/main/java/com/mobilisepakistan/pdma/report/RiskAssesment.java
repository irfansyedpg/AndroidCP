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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobilisepakistan.pdma.R;
import com.mobilisepakistan.pdma.databinding.RecycleviewBinding;
import com.mobilisepakistan.pdma.global.JsonArray;
import com.mobilisepakistan.pdma.global.ServerConfiguration;
import com.squareup.picasso.Picasso;

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


public class RiskAssesment extends AppCompatActivity {

    RecycleviewBinding binding ;
    RiskAssesmentCustomAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    String sHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.recycleview);







        sHeader=getString(R.string.s_erc);
        binding.header.setText(sHeader);

        new GetDataServerRiskAssesment(RiskAssesment.this, ServerConfiguration.ServerURL+ "GetReiskassesmentAction",binding.recycleview).execute();





        binding.lvback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) RiskAssesment.this).finish();
            }
        });





    }



}

class  RiskAssesmentCustomAdapter extends RecyclerView.Adapter {

    Context mContext;
    List<String> Listitle;
    List<String> Listdetial;
    List<String> Listimg;

    public RiskAssesmentCustomAdapter(Context context,List<String> title,List<String> detial,List<String> img ){
        mContext = context;
        Listitle = title;
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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ViewHolder vh = (ViewHolder) holder;

//        vh.txtrcv.setText(mList.get(position));
//        vh.txtconcat.setText(cList.get(position));
//        vh.txtheader.setText(hList.get(position));


        try {
            vh.txttitle.setText(Listitle.get(position));
            vh.txtdetial.setText(Listdetial.get(position));

            String imurl="http://175.107.63.39/pdmamadadgar/DisasterImages/"+Listimg.get(position);

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
          /*      String txtrcv = vh.txtcnt.getText().toString();


                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+txtrcv));
            //    callIntent.setData(Uri.parse(txtrcv));
                mContext.startActivity(intent);*/



            }
        });
    }



    @Override
    public int getItemCount() {
        return Listitle.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtdetial,txttitle;

        public ImageView lv;

        public ViewHolder(View v) {
            super(v);
            txtdetial = (TextView) v.findViewById(R.id.detial);
            txttitle = (TextView) v.findViewById(R.id.title);

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

            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);


                String title = c.getString("title");
                String detail = c.getString("detail");
                String img = c.getString("imageName");




                listtitl.add(title);
                listdetial.add(detail);
                listimg.add(img);
            }

            mLayoutManager = new LinearLayoutManager(mContext);
            recycleviewR.setLayoutManager(mLayoutManager);
            mAdapter = new RiskAssesmentCustomAdapter(mContext,listtitl,listdetial,listimg);
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