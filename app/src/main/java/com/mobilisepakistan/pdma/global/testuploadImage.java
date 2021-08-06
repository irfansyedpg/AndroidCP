package com.mobilisepakistan.pdma.global;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class testuploadImage
{
   public static  boolean status;

    public static  boolean volleyPost(Context contx, JSONObject Jobj){


        String postUrl = "http://175.107.63.39/newm/api/values/InsertResponseMedia";
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
                testuploadImage.status=true;



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                pd.cancel();
                Toast.makeText(mContext,"Error, please check your network connection. Currently unable to brows the app due to net conncectivity",Toast.LENGTH_LONG).show();
                testuploadImage.status=false;
            }
        });

        requestQueue.add(jsonObjectRequest);

        return  status;
    }
}
