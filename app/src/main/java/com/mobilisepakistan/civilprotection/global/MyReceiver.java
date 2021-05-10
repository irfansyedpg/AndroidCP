package com.mobilisepakistan.civilprotection.global;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String status = NetworkUtil.getConnectivityStatusString(context);
        if(status.isEmpty()) {
            status="No internet is available";
        }
     //   Toast.makeText(context, status, Toast.LENGTH_LONG).show();
    }
}