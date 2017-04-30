package com.example.frabbi.meem;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ServiceStarter extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent)
    {
        context.startService(new Intent(context, LocationService.class));
    }
}
