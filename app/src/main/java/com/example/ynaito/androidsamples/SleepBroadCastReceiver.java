package com.example.ynaito.androidsamples;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class SleepBroadCastReceiver extends BroadcastReceiver{
    private String TAG = SleepBroadCastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG,"onReceive");

        Intent i = new Intent();
        i.setData(Uri.parse("https://google.com"));
        context.startActivity(i);
    }
}
