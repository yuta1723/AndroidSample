package com.example.ynaito.androidsamples;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;


public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabFragmentPagerAdapter adapter = new TabFragmentPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        switchTabLayout(tabLayout,RelativeLayout.ALIGN_PARENT_BOTTOM);

        // タブの追加方法
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
    }

    private void switchTabLayout(TabLayout tabLayout, int attribute) {
        Log.d(TAG,"switchTabLayout");
        if (tabLayout == null) {
            return;
        }
        if ((attribute != RelativeLayout.ALIGN_PARENT_TOP) && (attribute != RelativeLayout.ALIGN_PARENT_BOTTOM)) {
            return;
        }

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(attribute);
        tabLayout.setLayoutParams(lp);
    }
}




// 参考
// https://developer.android.com/reference/android/support/design/widget/TabLayout
// https://qiita.com/PiyoMoasa/items/a0282483831b5993da02
// https://qiita.com/furu8ma/items/90482dceeea8cfb162af
