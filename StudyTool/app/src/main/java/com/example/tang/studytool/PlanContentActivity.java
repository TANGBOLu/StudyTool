package com.example.tang.studytool;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;

import com.example.tang.studytool.db.PlanItem;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tang on 2017/1/7.
 */

public class PlanContentActivity extends AppCompatActivity {
    @BindView(R.id.plan_context)
    EditText et;

    private PlanItem data;
    private int position=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_context);
        ButterKnife.bind(this);

        et.setMovementMethod(ScrollingMovementMethod.getInstance());
        et.setSelection(et.getText().length());

        Intent intent =this.getIntent();

        data= (PlanItem) intent.getSerializableExtra("ItemData");
        et.setText(data.getContext());
        //resultData();
    }


    public void resultData(){

        String text=et.getText().toString();
        if(text!="") {
            try {
                data.setTitle(text.substring(0, text.indexOf("\n")));
            } catch (Exception e) {
                data.setTitle(text.substring(0,1));
            }
        }
        else data.setTitle("");

        data.setContext(text);

        Intent intent = new Intent();
        Bundle bundle=new Bundle();
        bundle.putSerializable("resultData",data);
        intent.putExtras(bundle);
        //设置返回数据
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        resultData();
        super.onBackPressed();
    }
}
