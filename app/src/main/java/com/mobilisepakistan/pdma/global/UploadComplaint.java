package com.mobilisepakistan.pdma.global;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mobilisepakistan.pdma.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class UploadComplaint
{
   public static  boolean status;
   public static  String LastId;

   public static  String volleyPost(Context contx, JSONObject Jobj){


        String postUrl = ServerConfiguration.ServerURL+ "InsertComplaintAction";
        final Context mContext=contx;
        final   ProgressDialog pd;
        pd = new ProgressDialog(mContext);
        pd.setTitle("Please Wait...");
        pd.setMessage("Uploading Data...");
        pd.setCancelable(false);
        pd.show();

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        JSONObject postData =Jobj;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pd.cancel();
                System.out.println(response);

                UploadData2.status=true;

                String Dilogtext = mContext.getString(R.string.s_g_subnote);


                    AlertDialog.Builder dialog = new AlertDialog.Builder(mContext).setTitle(mContext.getString(R.string.s_g_h_subnote)).setMessage(Dilogtext);

                    dialog.setCancelable(false);

                    dialog.setPositiveButton(mContext.getString(R.string.s_g_h_subnote_ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            ((Activity) mContext).finish();
                        }
                    });
                    dialog.create().show();







            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                pd.cancel();
                Toast.makeText(mContext,"Error, please check your network connection. Currently unable to brows the app due to net conncectivity",Toast.LENGTH_LONG).show();
                UploadData2.status=false;
            }
        });

        requestQueue.add(jsonObjectRequest);

        return  LastId;
    }



}
