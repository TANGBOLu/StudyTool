package com.example.tang.studytool.TabFragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.tang.studytool.MinResActivity;
import com.example.tang.studytool.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;

/**
 * Created by Tang on 2016/12/24.
 */

public class MinToolFragment extends Fragment {


    OkHttpClient okHttpClient = new OkHttpClient();
    @BindView(R.id.student_login_btn)
    Button studentLoginBtn;

    View view;
    @BindView(R.id.min_id)
    EditText minId;
    @BindView(R.id.min_passord)
    EditText minPassord;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_mintool, container, false);
        ButterKnife.bind(this, view);
        return view;

    }

    @OnClick(R.id.student_login_btn)
    public void onClick() {
        Intent intent = new Intent();
        intent.setClass(view.getContext(), MinResActivity.class);//从一个activity跳转到另一个activity
        intent.putExtra("id", minId.getText());
        intent.putExtra("passowrd",minPassord.getText());
        startActivity(intent);
    }
}
