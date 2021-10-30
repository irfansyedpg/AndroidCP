package com.mobilisepakistanirfan.pdma.global;

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
import com.mobilisepakistanirfan.pdma.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.mobilisepakistanirfan.pdma.global.UploadData2.PushNotificaionUpload;


public class UploadComplaint
{
   public static  boolean status;
   public static  String LastId;

   public static  String volleyPost(Context contx, JSONObject Jobj,final ArrayList<Bitmap> bitmapPic){


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

                try {
                    LastId=response.getInt("result")+"";
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                UploadData2.status=true;


                String Dilogtext = mContext.getString(R.string.s_g_subnote);

                if(bitmapPic.size()!=0) {

                    for (Bitmap img:bitmapPic
                    ) {

                        uploadBitmap(img,LastId,mContext);

                    }

                    AlertDialog.Builder dialog = new AlertDialog.Builder(mContext).setTitle(mContext.getString(R.string.s_g_h_subnote)).setMessage(Dilogtext);

                    dialog.setCancelable(false);

                    dialog.setPositiveButton(mContext.getString(R.string.s_g_h_subnote_ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {

                            PushNotificaionUpload(mContext,"User Complaint");
                            ((Activity) mContext).finish();
                        }
                    });
                    dialog.create().show();


                }


                else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(mContext).setTitle(mContext.getString(R.string.s_g_h_subnote)).setMessage(Dilogtext);

                    dialog.setCancelable(false);

                    dialog.setPositiveButton(mContext.getString(R.string.s_g_h_subnote_ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {

                            PushNotificaionUpload(mContext,"User Complaint");
                            ((Activity) mContext).finish();
                        }
                    });
                    dialog.create().show();

                }







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

    // upload pictures to server
    private static final String ROOT_URL = ServerConfiguration.ServerURL+ "RDImageAction";

    private static void uploadBitmap(final Bitmap bitmap, final String logid, Context mCContext) {

        //getting the tag from the edittext
        //   final String tags = editTextTags.getText().toString().trim();

        //our custom volley request
        testVolleyMultipartRequest testVolleyMultipartRequest = new testVolleyMultipartRequest(Request.Method.POST, ROOT_URL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            //Toast.makeText(, obj.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        String err=error.getMessage();
                        //  Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("LogId", logid);
                params.put("Path", "Path");
                params.put("PK", "007");
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                //    long imagename = System.currentTimeMillis();
                params.put("ImageFile", new DataPart("imagename" + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(mCContext).add(testVolleyMultipartRequest);
    }

    public static byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

}
