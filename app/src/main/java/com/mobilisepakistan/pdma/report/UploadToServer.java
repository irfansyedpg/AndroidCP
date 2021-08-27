
package com.mobilisepakistan.pdma.report;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.mobilisepakistan.pdma.R;
import com.mobilisepakistan.pdma.global.ServerConfiguration;
import com.mobilisepakistan.pdma.global.testVolleyMultipartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class UploadToServer extends AppCompatActivity {


    private static final String ROOT_URL = ServerConfiguration.ServerURL+ "RDImageAction";

    private static final int PICK_IMAGE_REQUEST =1 ;
    private Bitmap bitmap;
  //  private String filePath;
    ImageView imageView;
    TextView textView;
    Button buttonUploadImage,buttonUploadImage2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uploadpicture);

        //initializing views
        imageView =  findViewById(R.id.imageView);
        textView =  findViewById(R.id.textview);


        //adding click listener to button
        buttonUploadImage=  findViewById(R.id.buttonUploadImage);
        buttonUploadImage2=  findViewById(R.id.buttonUploadImage2);

        buttonUploadImage.  setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    showFileChooser();



            }
        });

        buttonUploadImage2.  setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



               // boolean uploadStatus= testuploadImage.volleyPost(UploadToServer.this, UploadDate());

                uploadBitmap(bitmap);

            }
        });
    }

    private void showFileChooser() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Remove Picture","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose your picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    //    String filpath=android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    startActivityForResult(pickPhoto , 1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();




    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    //    super.onActivityResult(requestCode, resultCode, data);



        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK && data != null) {
                    Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                    imageView.setImageBitmap(selectedImage);
                }

                break;
            case 1:
                if (resultCode == RESULT_OK && data != null) {
                    try {
                        final Uri imageUri = data.getData();
                     //   String Pathtesting=getRealPathFromURI(imageUri);

                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        bitmap=selectedImage;
                        imageView.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Something went wrong, Unable to select the immage ", Toast.LENGTH_LONG).show();
                    }

                }

                break;
        }

    }



    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }




    private void uploadBitmap(final Bitmap bitmap) {

        //getting the tag from the edittext
     //   final String tags = editTextTags.getText().toString().trim();

        //our custom volley request
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
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
                params.put("LogId", "2127");
                params.put("Path", "Path");
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
        Volley.newRequestQueue(this).add(testVolleyMultipartRequest);
    }


//    public JSONObject UploadDate()
//    {
//
//        JSONObject log = new JSONObject();
//        try {
//            log .put("Response",getFileDataFromDrawable(bitmap) );
//            log .put("FK", 12);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return  log;
//    }

}



