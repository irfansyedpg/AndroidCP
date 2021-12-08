package com.mobilisepakistanirfan.pdma.signup;

import static com.mobilisepakistanirfan.pdma.global.UploadData2.PushNotificaionUpload;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mobilisepakistanirfan.pdma.MainActivity;
import com.mobilisepakistanirfan.pdma.R;
import com.mobilisepakistanirfan.pdma.Splash;
import com.mobilisepakistanirfan.pdma.global.MyPref;
import com.mobilisepakistanirfan.pdma.global.ServerConfiguration;
import com.mobilisepakistanirfan.pdma.global.UserPref;

import org.json.JSONException;
import org.json.JSONObject;


public class SignUpServer
{
   public static  boolean status;
   public static  String ServerUserID ;
   public static  String Userdistrict ;


    public static  boolean SignUpServer(Context contx, JSONObject Jobj,String district,String MobileNumber,String Email,String Usertype){



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
                    UserPref userpref=new UserPref(mContext);

                    prefs.setUserDistrict(Userdistrict);
                    userpref.setUserEmail(Email);
                    userpref.setUserMobileNo(MobileNumber);
                    userpref.setUserTypel(Usertype);
                    prefs.setUserId(Integer.parseInt(Userid));

                    if(Usertype.equals("3"))
                    {

                        String Dilogtext = mContext.getString(R.string.su_PDMAUSER);

                        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext).setTitle(mContext.getString(R.string.s_g_h_subnote)).setMessage(Dilogtext);

                        dialog.setCancelable(false);

                        dialog.setPositiveButton(mContext.getString(R.string.s_g_h_subnote_ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {

                                userpref.setUserUserStatus("Not Active");

                                Intent mainIntent = new Intent(mContext, Splash.class);
                                mContext.startActivity(mainIntent);
                                ((Activity) mContext).finish();




                            }
                        });
                        dialog.create().show();

                    }

                    else {




                        userpref.setUserUserStatus("Active");


                        Intent mainIntent = new Intent(mContext, Splash.class);
                        mContext.startActivity(mainIntent);
                        ((Activity) mContext).finish();
                    }
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
