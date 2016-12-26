package com.example.tang.studytool.TabFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.tang.studytool.R;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Tang on 2016/12/24.
 */

public class TomotoFragment extends Fragment {

    final int MESSAGE_UPDATA_TEXTVIEW=0;

    @BindView(R.id.tomatoClock)
    TextView tomatoClock;
    @BindView(R.id.tomatoStart)
    Button tomatoStart;
    @BindView(R.id.tomatoEnd)
    Button tomatoEnd;


    //获取时分秒
    String clockCount=null;//只获取一次省掉读取时间


    private Timer timer = null;//计时器
    private TimerTask timerTask = null;//

    /**
     *  设置tomatoClock，定时时间
     */
    private Handler myHander=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_UPDATA_TEXTVIEW:
                    updataClockView();
                    break;
                case 1:
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_tomato, container, false);
        ButterKnife.bind(this, view);
        return view;
    }


    @OnClick({R.id.tomatoStart, R.id.tomatoEnd, R.id.tomatoClock})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tomatoStart:
                startTomotoWork(view);
                break;
            case R.id.tomatoEnd:
                endTomotoWork(view);
                break;
            case R.id.tomatoClock:
                setTimeCountDown(view);
                break;
        }
    }

    /**
     * 设置时间对话框
     */
    private void setTimeCountDown(View view){
        TimePickerDialog.OnTimeSetListener listener=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                tomatoClock.setText(String.format("%02d",hourOfDay)+":"+String.format("%02d",minute)+":"+"00");//初始化时间
            }
        };

        //开启选择时间对话框
        TimePickerDialog timePickerDialog=new TimePickerDialog(view.getContext(),listener,0,0,true);
        timePickerDialog.setTitle("番茄工作法");
        timePickerDialog.show();
    }

    /**
     * 开始番茄工作法
     * @param view
     */
    private void startTomotoWork(View view){

        tomatoClock.setEnabled(false);
        tomatoStart.setEnabled(false);
        tomatoEnd.setEnabled(true);
        startTimer();//开始倒计时
    }

    /**
     * 结束番茄工作法
     * @param view
     */
    private void endTomotoWork(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());//对话框
        builder.setMessage("确定提前退出吗？");
        builder.setTitle("提示");

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                tomatoClock.setEnabled(true);//按钮可用控制
                tomatoStart.setEnabled(true);
                tomatoEnd.setEnabled(false);

                endTimer();//结束计时
                tomatoClock.setText("00"+":"+"00"+":"+"00");//初始化时间
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
        public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    /**
     * 开始之后更新控件
     */
    private void updataClockView(){

        int myhour;
        int myminute;
        int mysecond;

        if(clockCount==null){
             clockCount=tomatoClock.getText().toString();
        }
        //获取时分秒
        myhour=Integer.parseInt(clockCount.substring(0,2));
        myminute=Integer.parseInt(clockCount.substring(3,5));
        mysecond=Integer.parseInt(clockCount.substring(6,8));

        if(myhour==0&&myminute==0&&mysecond==0) {//如果全为0计时结束
            endTimer();//
            tomatoClock.setEnabled(true);//按钮可用控制
            tomatoStart.setEnabled(true);
            tomatoEnd.setEnabled(false);
            tomatoClock.setText("00"+":"+"00"+":"+"00");//初始化时间
            //执行完成启动闹钟

            return;
        }

        if(mysecond<=0) {
            if (myminute >0) {//借位
                myminute = myminute - 1;
                mysecond=59;
            }
            else {//借位
                myhour=myhour-1;
                myminute=59;
            }
        }else {
            mysecond = mysecond - 1;
        }
        clockCount=String.format("%02d",myhour)+":"+String.format("%02d",myminute)+":"+String.format("%02d",mysecond);
        tomatoClock.setText(clockCount);
    }

    /**
     *开始计时
     */
    private void startTimer(){
        if(timer==null){
            timer = new Timer();
        }

        timerTask = new TimerTask() {

            @Override
            public void run() {
                Message msg=new Message();
                msg.what=MESSAGE_UPDATA_TEXTVIEW;
                myHander.sendMessage(msg);//发送消息
            }
        };
        timer.schedule(timerTask, 1000,1000);//1000ms执行一次
    }

    /**
     * 取消计时
     */
    private void endTimer(){
        if(timer!=null)
            timer.cancel();
        timer=null;
        clockCount=null;
    }

}
