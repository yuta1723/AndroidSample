package com.example.ynaito.androidsamples;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imgView = (ImageView)findViewById(R.id.action_set_framelayout);
        Bitmap btmp = createBitmap();
        if (btmp != null) {
            imgView.setImageBitmap(btmp);
        }


    }

    public Bitmap createBitmap() {

        // ビューを生成
        // 例として、TextViewに動的にテキストを入れる
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.hogeitem,null);

        // 画面内に配置してないので、measureを読んでからBitmapに書き込む
        if (v.getMeasuredHeight() <= 0) {
            v.measure(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
            Bitmap b = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(b);
            v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
            v.draw(c);
            return b;
        }
        return null;
    }
}
