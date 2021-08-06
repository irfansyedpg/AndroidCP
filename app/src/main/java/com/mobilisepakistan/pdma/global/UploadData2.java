package com.mobilisepakistan.pdma.global;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.mobilisepakistan.pdma.R;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;


public class UploadData2
{
   public static  boolean status;

    public static  boolean volleyPost(Context contx, JSONObject Jobj, final String type){


        String postUrl = "http://175.107.63.39/newm/api/values/InsertResponse";
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

//                // RD uploading close the mian activity then
//                if(type=="RD") {
//                    Dilogtext = "Information about reported disaster recieved at PDMA Emergency Operation Center. Action will be initiated soon";
//
//                }
//                else if(type=="RNA" )
//                {
//                    Dilogtext = "Rapid Need Assessment report successfully submitted to PDMA Emergency Operation Centre";
//
//                }
//                else
//                {
//                    Dilogtext = "Damage Need Assessment Report successfully submitted to PDMA Emergency Operation Centre";
//
//                }


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

        return  status;
    }
}
