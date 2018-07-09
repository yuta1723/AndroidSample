package com.example.ynaito.androidsamples;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private int period = 5000;
    private String RE_BOOT = "REBOOT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!getIntent().getAction().equals(RE_BOOT)) {
            schedAlarm();
        }
    }

    public void schedAlarm() {
        AlarmManager am = (AlarmManager) this.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, MainActivity.class);
        intent.setAction(RE_BOOT);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        am.set(AlarmManager.RTC, System.currentTimeMillis() + period, pIntent);
    }
}

//https://akira-watson.com/android/app-restart.html
