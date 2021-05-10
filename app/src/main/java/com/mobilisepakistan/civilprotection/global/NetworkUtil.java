package com.mobilisepakistan.civilprotection.global;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.mobilisepakistan.civilprotection.MainActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

class NetworkUtil {
    public static String getConnectivityStatusString(Context context) {
        String status = null;
        ConnectivityManager cm = (ConnectivityManager)           context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                status = "Wifi enabled";
                return status;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                status = "Mobile data enabled";
                return status;
            }
        } else {
            status = "No internet is available";
            alerdailog(context);

            return status;
        }
        return status;
    }

    public static void alerdailog(final Context mContext)
    {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("No Internet ");
        alertDialog.setMessage("Please Check your Mobile Data or Wifi ");




        alertDialog.setPositiveButton("Retry",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        Intent intent = new Intent(mContext, MainActivity.class);
                        intent.addFlags(FLAG_ACTIVITY_NEW_TASK);

                        mContext.startActivity(intent);
                        if (mContext instanceof Activity) {
                            ((Activity) mContext).finish();
                        }

                        Runtime.getRuntime().exit(0);

                    }
                });



        alertDialog.show();
    }
}