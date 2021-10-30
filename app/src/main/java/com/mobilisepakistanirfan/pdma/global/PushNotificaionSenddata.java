package com.mobilisepakistanirfan.pdma.global;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class PushNotificaionSenddata
{
   public static  boolean status;
   public static  String ServerUserID ;

    public static  boolean PushNotificaionSenddata(Context contx, JSONObject Jobj){


        String postUrl ="https://fcm.googleapis.com/fcm/send";
        final Context mContext=contx;
    //    PushNotificaionSenddata.status=false;
      //  PushNotificaionSenddata.ServerUserID="0";

        final   ProgressDialog pd;
        //pd = new ProgressDialog(mContext);
      //  pd.setTitle("Please Wait...");
    //    pd.setMessage("Uploading Data...");
  //      pd.setCancelable(false);
//        pd.show();

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        JSONObject postData =Jobj;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
          //      pd.cancel();
//                System.out.println(response);
  //              PushNotificaionSenddata.status=true;

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
           //     pd.cancel();
                Toast.makeText(mContext,"Please turn on your internet",Toast.LENGTH_LONG).show();
                PushNotificaionSenddata.status=false;
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", ServerConfiguration.FirebaseWebServerKey);
                return params;
            }
        };

        requestQueue.add(jsonObjectRequest);

        return  status;
    }




}
