
package com.mobilisepakistan.pdma.report;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.internal.Constants;
import com.mobilisepakistan.pdma.R;
import com.mobilisepakistan.pdma.global.ServerConfiguration;
import com.mobilisepakistan.pdma.global.testVolleyMultipartRequest;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;

    public class NewUPloadImage extends AppCompatActivity {


        private static final String ROOT_URL = "http://175.107.63.39/pdmamadadgar/api/values/RDImageAction";
        private static final int REQUEST_PERMISSIONS = 100;
        private static final int PICK_IMAGE_REQUEST =1 ;
        private Bitmap bitmap;
        private String filePath;
        ImageView imageView;
        TextView textView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.newuploadpicture);

            //initializing views
            imageView =  findViewById(R.id.imageView);
            textView =  findViewById(R.id.textview);

            //adding click listener to button
            findViewById(R.id.buttonUploadImage).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                      // Log.e("Else", "Else");
                        showFileChooser();



                }
            });
        }

        private void showFileChooser() {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }

        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                Uri picUri = data.getData();
                filePath = getPath(picUri);
                if (filePath != null) {
                    try {

                        textView.setText("File Selected");
                      //  Log.d("filePath", String.valueOf(filePath));
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), picUri);
                        uploadBitmap(bitmap);
                        imageView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Toast.makeText(
                            NewUPloadImage.this,"no image selected",
                            Toast.LENGTH_LONG).show();
                }
            }

        }
        public String getPath(Uri contentUri) {
            // can post image
            String [] proj={MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery( contentUri,
                    proj, // Which columns to return
                    null,       // WHERE clause; which rows to return (all rows)
                    null,       // WHERE clause selection arguments (none)
                    null); // Order-by clause (ascending by name)
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();

            return cursor.getString(column_index);
        }


        public byte[] getFileDataFromDrawable(Bitmap bitmap) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }

        private void uploadBitmap(final Bitmap bitmap) {

            testVolleyMultipartRequest testVolleyMultipartRequest = new testVolleyMultipartRequest(Request.Method.POST, ROOT_URL,
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {
                            try {
                                JSONObject obj = new JSONObject(new String(response.data));
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                         //   Log.e("GotError",""+error.getMessage());
                        }
                    }) {


                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();
                    long imagename = System.currentTimeMillis();
                    params.put("image", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                    return params;
                }
            };

            //adding the request to volley
            Volley.newRequestQueue(this).add(testVolleyMultipartRequest);
        }

    }