package com.example.tang.studytool.TabFragment;


import android.os.Bundle;
import android.support.annotation.Nullable;

import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.tang.studytool.R;

/**
 * Created by Tang on 2016/12/24.
 */

public class MinToolFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_mintool,container,false);
    }
}
