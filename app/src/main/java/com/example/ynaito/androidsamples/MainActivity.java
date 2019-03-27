package com.example.ynaito.androidsamples;

import android.content.Intent;
import android.content.pm.ShortcutManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createShortCut();
            }
        });

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeShortCut();
            }
        });
    }

    private void createShortCut() {
        Log.d(TAG,"createShortCut");


        //ホームに作成したいショートカットをIntentとして作成
//（ここではActivityが自身のショートカットをホームに作成すると仮定）
        Intent shortcutIntent = new Intent();
        shortcutIntent.setClassName(getPackageName(), getClass().getName());
        shortcutIntent.setAction(Intent.ACTION_MAIN);
        shortcutIntent.addCategory(Intent.CATEGORY_LAUNCHER);

//続いてショートカット作成のためのintentを生成
        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "WebViewSample");
        intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        intent.putExtra("duplicate",false);
        sendBroadcast(intent);

    }

    private void removeShortCut() {
        Log.d(TAG,"removeShortCut");
        /** 削除するショートカットのアクティビティ */
        Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
        shortcutIntent.setClassName(
                MainActivity.this,
                getClass().getName());

        /** 削除依頼 */
        Intent removeIntent = new Intent();
        removeIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        removeIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "WebViewSample");
        removeIntent.putExtra("duplicate", false);
        removeIntent.setAction(
                "com.android.launcher.action.UNINSTALL_SHORTCUT");
        sendBroadcast(removeIntent);
    }

    private void removeShortCut2() {
        Log.d(TAG,"removeShortCut2");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N_MR1) {
            ShortcutManager shortcutManager = (ShortcutManager)this.getSystemService(this.SHORTCUT_SERVICE);
        }


    }
}
