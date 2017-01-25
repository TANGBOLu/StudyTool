package com.example.tang.studytool;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * Created by Tang on 2017/1/25.
 */

public class MinResActivity extends AppCompatActivity {
    @BindView(R.id.postResTest_btn)
    TextView postResTest;

    Intent intent=null;
    String userid;
    String userpassowrd;

    //网络请求
    OkHttpClient client=new OkHttpClient();

    Handler handler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle b=msg.getData();
            postResTest.setText(b.getString("data"));
        }
    };

    Runnable runnable=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp_student);
        ButterKnife.bind(this);
        init_data();//初始化数据
        Thread th=new Thread(runnable);
        th.start();
    }

    /**
     * 初始化数据
     */
    private void init_data() {
        intent =this.getIntent();
        userid=intent.getExtras().get("id").toString();
        userpassowrd=intent.getExtras().get("passowrd").toString();

        runnable=new Runnable() {
            @Override
            public void run() {
                try {
                    startLogin();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }
    public void startLogin() throws IOException{
        OkHttpClient client=new OkHttpClient();
        String url="http://jwgl.hunnu.edu.cn/(mv4qck45ho3d5v55tdoj3f55)/default2.aspx";
        String data=null;
        //
        String  __VIEWSTATE="dDwtMTg3MTM5OTI5MTs7PoNaM1R0Lzmz2RmByRUZhd7s1Gx3";

        //提交的数据
        RequestBody body=new FormBody.Builder()
                .add("__VIEWSTATE",__VIEWSTATE)
                .add("TextBox1",userid)
                .add("TextBox2",userpassowrd)
                .add("TextBox3","")
                .add("RadioButtonList1","学生")
                .add("Button1","")
                .add("lbLanguage","")
                .build();
        //提交的头
        Headers header=new Headers.Builder()
                .add("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .add("Accept-Language","zh-CN,zh;q=0.8")
                .add("Cache-Control","max-age=0")
                .add("Connection","keep-alive")
                .add("Content-Length","163")
                .add("Content-Type","application/x-www-form-urlencoded")
                .add("Host","jwgl.hunnu.edu.cn")
                .add("Origin","http://jwgl.hunnu.edu.cn")
                .add("Referer",url)
                .add("Upgrade-Insecure-Requests","1")
                .add("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36'")
                .build();

        //提交的数据
        Request request=new Request.Builder().url(url).headers(header).post(body).build();
        Response response=client.newCall(request).execute();
        if(response.isSuccessful()){
            data=response.body().string();
        }

        //获取名字
        Document doc = Jsoup.parse(data);
        Element el= doc.getElementById("xhxm");
        String userInfo=el.toString();
        String username=userInfo.substring(userInfo.lastIndexOf(" "),userInfo.indexOf("同学"));//获取姓名

        //提交给handle
        Message msg=new Message();
        Bundle bundle=new Bundle();
        bundle.putString("data",username);//保存数据
        msg.setData(bundle);
        handler.sendMessage(msg);
    }
}

