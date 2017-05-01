package com.example.frabbi.meem;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ServiceStarter extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Toast.makeText(context, "Service Is Being Started", Toast.LENGTH_SHORT);
        context.startService(new Intent(context, LocationService.class));
    }
}
