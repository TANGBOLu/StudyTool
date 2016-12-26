package com.example.tang.studytool;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * Created by Tang on 2016/12/24.
 */

public class ContentActivity extends AppCompatActivity{

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.content_main);
        ((TextView)findViewById(R.id.mintool_text)).setText("sdf");
    }
}