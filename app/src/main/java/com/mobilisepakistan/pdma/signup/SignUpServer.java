package com.mobilisepakistan.pdma.signup;

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


public class SignUpServer
{
   public static  boolean status;
   public static  String ServerUserID ;
   public static  String Userdistrict ;

    public static  boolean SignUpServer(Context contx, JSONObject Jobj,String district){


        String postUrl = ServerConfiguration.ServerURL+ "Signup";
        final Context mContext=contx;
        SignUpServer.status=false;
        SignUpServer.ServerUserID="0";
        Userdistrict=district;

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
                SignUpServer.status=true;
                String Userid="0";
                try {
                     Userid=response.getString("lastId");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(!Userid.equals("0")) {


                    MyPref prefs = new MyPref(mContext);

                    prefs.setUserId(Integer.parseInt(Userid));
                    prefs.setUserDistrict(Userdistrict);



                    Intent mainIntent = new Intent(mContext, MainActivity.class);
                    mContext.startActivity(mainIntent);
                    ((Activity) mContext).finish();
                }
                else
                {
                    Toast.makeText(mContext,"An account is already registered with this email address. Please sign in or use different email",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                pd.cancel();
                Toast.makeText(mContext,"Error, please check your network connection. Currently unable to brows the app due to net conncectivity",Toast.LENGTH_LONG).show();
                SignUpServer.status=false;
            }
        });

        requestQueue.add(jsonObjectRequest);

        return  status;
    }




}
