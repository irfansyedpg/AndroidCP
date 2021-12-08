package com.mobilisepakistanirfan.pdma.signup;

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
import com.mobilisepakistanirfan.pdma.MainActivity;
import com.mobilisepakistanirfan.pdma.global.MyPref;
import com.mobilisepakistanirfan.pdma.global.ServerConfiguration;
import com.mobilisepakistanirfan.pdma.global.UserPref;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginUpServer
{
   public static  boolean status;
   public static  String ServerUserID ;

    public static  boolean LoginUpServer(Context contx, JSONObject Jobj){


        String postUrl = ServerConfiguration.ServerURL+ "Signin";
        final Context mContext=contx;
        LoginUpServer.status=false;
        LoginUpServer.ServerUserID="0";

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
                LoginUpServer.status=true;
                String Userid="0";
                String District="";
                String MobileNo="";
                String Email="";
                String UserType="";
                String UserStatus="";
                try {
                     Userid=response.getString("id");
                     District=response.getString("district");

                    MobileNo=response.getString("mobileno");
                    Email=response.getString("email");
                    UserType=response.getString("usertype");
                    UserStatus=response.getString("userstatus");



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if(!Userid.equals("-1")) {

                    MyPref prefs = new MyPref(mContext);

                    UserPref upref=new UserPref(mContext);

                    upref.setUserMobileNo(MobileNo);
                    upref.setUserEmail(Email);
                    upref.setUserTypel(UserType);

                    if(UserStatus.equals("1")) {
                        upref.setUserUserStatus("Active");
                    }
                    else
                    {
                        upref.setUserUserStatus("Not Active");

                    }


                    prefs.setUserId(Integer.parseInt(Userid));
                    prefs.setUserDistrict(District);

                    Intent mainIntent = new Intent(mContext, MainActivity.class);
                    mContext.startActivity(mainIntent);
                    ((Activity) mContext).finish();
                }
                else
                {
                    Toast.makeText(mContext,"Sign in Error, your user name or passward is incorrect",Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                pd.cancel();
                Toast.makeText(mContext,"Please turn on your internet",Toast.LENGTH_LONG).show();
                LoginUpServer.status=false;
            }
        });

        requestQueue.add(jsonObjectRequest);

        return  status;
    }




}
