package com.mobilisepakistan.pdma.utilities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mobilisepakistan.pdma.MainActivity;
import com.mobilisepakistan.pdma.global.MyPref;
import com.mobilisepakistan.pdma.global.ServerConfiguration;

import org.json.JSONException;
import org.json.JSONObject;


public class VolunterEngServer
{
   public static  boolean status;
   public static  String ServerUserID ;

    public static  boolean VolunterEngServer(Context contx, JSONObject Jobj){


        String postUrl = ServerConfiguration.ServerURL+ "UpdateVolntStatus";
        final Context mContext=contx;
        VolunterEngServer.status=false;
        VolunterEngServer.ServerUserID="0";

        final   ProgressDialog pd;
        pd = new ProgressDialog(mContext);
        pd.setTitle("Please Wait...");
        pd.setMessage("Responed Sedning...");
        pd.setCancelable(false);
        pd.show();

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        JSONObject postData =Jobj;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pd.cancel();
                ((Activity) mContext).finish();





            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                pd.cancel();
                Toast.makeText(mContext,"Please turn on your internet",Toast.LENGTH_LONG).show();
                VolunterEngServer.status=false;
                ((Activity) mContext).finish();
            }
        });

        requestQueue.add(jsonObjectRequest);

        return  status;
    }




}
