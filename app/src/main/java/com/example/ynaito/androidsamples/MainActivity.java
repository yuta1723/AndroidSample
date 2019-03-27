package com.example.ynaito.androidsamples;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView osView = (TextView)findViewById(R.id.testview01);
        osView.setText("OS VERSION = " + Build.VERSION.RELEASE);

        TextView modelView = (TextView)findViewById(R.id.testview02);
        modelView.setText("MODEL = " + Build.MODEL);

        TextView productView = (TextView)findViewById(R.id.testview03);
        productView.setText("PRODUCT = " + Build.PRODUCT);
    }
}
