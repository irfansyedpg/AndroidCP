package com.mobilisepakistanirfan.pdma.global;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

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