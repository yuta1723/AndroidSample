package com.example.ynaito.androidsamples.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ynaito.androidsamples.R;

public class ButtomSheetFragment extends Fragment {
    private final String TAG = ButtomSheetFragment.class.getSimpleName();
    private BottomSheetBehavior behavior;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_layout1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "[enter] onViewCreated");

        View bottomSheet = view.findViewById(R.id.bottom_sheet);
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        behavior.setHideable(true);
        behavior.setPeekHeight(1000);

        Button button = view.findViewById(R.id.button_for_bottom_sheet);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "[enter] onClick");
                // 開いていれば閉じる。閉じていれば開く。
                if(behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                } else {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

                }

            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
}

//https://qiita.com/meru_h/items/b7cb40deed26f05e2a54
//https://qiita.com/napplecomputer/items/5b3d1225533a59488ac3