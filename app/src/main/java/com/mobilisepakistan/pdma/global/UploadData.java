package com.mobilisepakistan.pdma.global;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.mobilisepakistan.pdma.utilities.PostRequestData2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;


public class  UploadData extends AsyncTask {



    Context mContext;
    ProgressDialog mDialog;
    String mUserMsg, URL;
    HashMap<String, List<String>> Param;

    public UploadData(Context context, String URL,HashMap<String, List<String>> Parm1) {
        this.mContext = context;
        this.URL = URL;
        this.Param=Parm1;

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

//        String urlString = "http://175.107.63.137/PEOCMIS/api/values/getDSR";
        String urlString = URL;


        try {
            url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");

            OutputStream os = connection.getOutputStream();
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            bw.write(PostRequestData2.getData(Param));
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
        } finally {
            if (mUserMsg != null)
                Toast.makeText(mContext, mUserMsg, Toast.LENGTH_SHORT).show();
        }
        // hide the progressDialog
        mDialog.hide();

        super.onPostExecute(o);
    }

}
