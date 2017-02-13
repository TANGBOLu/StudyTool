package com.example.tang.studytool;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Tang on 2017/1/25.
 */

public class MinResActivity extends AppCompatActivity {

    String postYear;

    Intent intent = null;
    String userid;
    String userpassowrd;
    String username;
    //网络请求
    OkHttpClient client = new OkHttpClient();

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle b = msg.getData();
            scoreView.setText(b.getString("data"));
        }
    };

    Runnable runnable = null;

    @BindView(R.id.YearDate_Spinner)
    Spinner YearDateSpinner;
    @BindView(R.id.postFrom_btn)
    Button postFromBtn;
    @BindView(R.id.score_View)
    TextView scoreView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp_student);
        ButterKnife.bind(this);
        init_data();//初始化数据

    }

    /**
     * 初始化数据
     */
    private void init_data() {
        intent = this.getIntent();
        userid = intent.getExtras().get("id").toString();
        userpassowrd = intent.getExtras().get("passowrd").toString();
        postYear = getResources().getStringArray(R.array.yearSpingarr)[0];

        //登陆
        runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    startLogin();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread th = new Thread(runnable);
        th.start();

        //显示姓名

        //保存选项
        YearDateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] languages = getResources().getStringArray(R.array.yearSpingarr);
                postYear = languages[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

    }


    String basicUrl = "http://jwgl.hunnu.edu.cn/(mv4qck45ho3d5v55tdoj3f55)/";//基本url

    //开始登陆
    public void startLogin() throws IOException {

        String url = basicUrl + "default2.aspx";
        String data = null;
        //
        String __VIEWSTATE = "dDwtMTg3MTM5OTI5MTs7PoNaM1R0Lzmz2RmByRUZhd7s1Gx3";//标志

        //提交的数据
        RequestBody body = new FormBody.Builder()
                .add("__VIEWSTATE", __VIEWSTATE)
                .add("TextBox1", userid)
                .add("TextBox2", userpassowrd)
                .add("TextBox3", "")
                .add("RadioButtonList1", "学生")
                .add("Button1", "")
                .add("lbLanguage", "")
                .build();
        //提交的头
        Headers header = new Headers.Builder()
                .add("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .add("Accept-Language", "zh-CN,zh;q=0.8")
                .add("Cache-Control", "max-age=0")
                .add("Connection", "keep-alive")
                .add("Content-Length", "163")
                .add("Content-Type", "application/x-www-form-urlencoded")
                .add("Host", "jwgl.hunnu.edu.cn")
                .add("Origin", "http://jwgl.hunnu.edu.cn")
                .add("Referer", url)
                .add("Upgrade-Insecure-Requests", "1")
                .add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36'")
                .build();

        //提交的数据
        Request request = new Request.Builder().url(url).headers(header).post(body).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            data = response.body().string();
        }

        //获取名字
        Document doc = Jsoup.parse(data);
        Elements loginFlag = doc.getElementsByTag("script");
        if (loginFlag.size() != 2) {
            Element el = doc.getElementById("xhxm");
            String userInfo = el.toString();
            username = userInfo.substring(userInfo.lastIndexOf(" "), userInfo.indexOf("同学"));//获取姓名
            username = URLEncoder.encode(username.trim(), "gb2312");
        } else {
            setResult(RESULT_OK);
            this.finish();
        }

    }

    //获取等级考试成绩
    private boolean getGradeExam() throws IOException {

        String gradeExamScoreUrl = basicUrl + "xsdjkscx.aspx?xh=" + userid + "&xm=" + username + "&gnmkdm=N121606";

        //等级考试header
        Headers gradeExamHeader = new Headers.Builder()
                .add("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .add("Accept-Language", "zh-CN,zh;q=0.8")
                .add("Connection", "keep-alive")
                .add("Host", "jwgl.hunnu.edu.cn")
                .add("Referer", gradeExamScoreUrl)
                .add("Upgrade-Insecure-Requests", "1")
                .add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36")
                .build();
        String htmlBody = null;
        Request request = new Request.Builder().url(gradeExamScoreUrl).headers(gradeExamHeader).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            htmlBody = response.body().string();
        }
        List<List<String>> dataList = new ArrayList<>();
        //解析并获取数据
        Document doc = Jsoup.parse(htmlBody);
        Element el = doc.getElementById("DataGrid1");
        Elements trs = el.select("table").select("tr");
        for (int i = 0; i < trs.size(); i++) {

            Elements tds = trs.get(i).select("td");//获取td数据
            List<String> td = new ArrayList<>();
            for (int j = 0; j < tds.size(); j++) {
                td.add(tds.get(j).text());//添加数据
            }
            dataList.add(td);//获取一行数据
        }

        String dataExam = "";
        for (int i = 0; i < dataList.size(); i++) {
            List<String> td = dataList.get(i);
            for (int j = 0; j < td.size(); j++) {
                dataExam = dataExam + td.get(j) + " ";
            }
            dataExam = dataExam + "\n";
        }
        //提交给handle
        Message msg = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("data", dataExam);
        msg.setData(bundle);
        handler.sendMessage(msg);
        return true;
    }

    /**
     * 获取成绩
     *
     * @param sectionYear 选择查询哪一年
     * @param flag        学年查询或者
     * @return
     */
    private boolean getExamPerformance(String sectionYear, String flag) throws IOException {

        //sectionYear = "2016-2017";
        //flag = "按学年查询";

        String gradeScoreUrl = basicUrl + "xscj_gc.aspx?xh=" + userid + "&xm=" + username + "&gnmkdm=N121605";

        String score_VIEWSTATE = "dDwtMTQxMjE2MzAyMzt0PHA8bDx4aDs+O2w8MjAxNDMwMTg1MDIyOz4+O2w8aTwxPjs+O2w8dDw7bDxpPDE+O2k8Mj47aTwzPjtpPDQ+O2k8NT47aTw2PjtpPDc+O2k8OT47aTwxOD47aTwxOT47aTwyNj47aTwyOD47aTwzMD47aTwzMj47aTwzND47aTwzNj47aTwzOD47aTw1MD47aTw1NT47aTw1Nz47PjtsPHQ8cDxwPGw8VGV4dDs+O2w85a2m5Y+377yaMjAxNDMwMTg1MDIyOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzlp5PlkI3vvJrllJDms6I7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOWtpumZou+8muW3peeoi+S4juiuvuiuoeWtpumZoijogYzkuJrmioDmnK/lrabpmaIpOz4+Oz47Oz47dDxwPHA8bDxUZXh0Oz47bDzkuJPkuJrvvJo7Pj47Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPOiuoeeul+acuuenkeWtpuS4juaKgOacr++8iOiBjOmZou+8iTs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w86KGM5pS/54+t77yaMTTnuqforqHnrpfmnLrnp5HlrabkuI7mioDmnK/vvIjluIjojIPvvIkwNOePre+8iOiBjO+8iTs+Pjs+Ozs+O3Q8cDxwPGw8VGV4dDs+O2w8MjAxNDE4MDc7Pj47Pjs7Pjt0PHQ8O3Q8aTwxOD47QDxcZTsyMDAxLTIwMDI7MjAwMi0yMDAzOzIwMDMtMjAwNDsyMDA0LTIwMDU7MjAwNS0yMDA2OzIwMDYtMjAwNzsyMDA3LTIwMDg7MjAwOC0yMDA5OzIwMDktMjAxMDsyMDEwLTIwMTE7MjAxMS0yMDEyOzIwMTItMjAxMzsyMDEzLTIwMTQ7MjAxNC0yMDE1OzIwMTUtMjAxNjsyMDE2LTIwMTc7MjAxNy0yMDE4Oz47QDxcZTsyMDAxLTIwMDI7MjAwMi0yMDAzOzIwMDMtMjAwNDsyMDA0LTIwMDU7MjAwNS0yMDA2OzIwMDYtMjAwNzsyMDA3LTIwMDg7MjAwOC0yMDA5OzIwMDktMjAxMDsyMDEwLTIwMTE7MjAxMS0yMDEyOzIwMTItMjAxMzsyMDEzLTIwMTQ7MjAxNC0yMDE1OzIwMTUtMjAxNjsyMDE2LTIwMTc7MjAxNy0yMDE4Oz4+Oz47Oz47dDxwPDtwPGw8b25jbGljazs+O2w8d2luZG93LnByaW50KClcOzs+Pj47Oz47dDxwPDtwPGw8b25jbGljazs+O2w8d2luZG93LmNsb3NlKClcOzs+Pj47Oz47dDxAMDw7Ozs7Ozs7Ozs7Pjs7Pjt0PEAwPDs7Ozs7Ozs7Ozs+Ozs+O3Q8QDA8Ozs7Ozs7Ozs7Oz47Oz47dDxAMDw7Ozs7Ozs7Ozs7Pjs7Pjt0PEAwPDs7Ozs7Ozs7Ozs+Ozs+O3Q8QDA8cDxwPGw8VmlzaWJsZTs+O2w8bzxmPjs+Pjs+Ozs7Ozs7Ozs7Oz47Oz47dDxAMDxwPHA8bDxWaXNpYmxlOz47bDxvPGY+Oz4+Oz47Ozs7Ozs7Ozs7Pjs7Pjt0PHA8cDxsPFRleHQ7PjtsPEhOQ0o7Pj47Pjs7Pjt0PEAwPDs7Ozs7Ozs7Ozs+Ozs+O3Q8QDA8Ozs7Ozs7Ozs7Oz47Oz47Pj47Pj47PsVNg3nVUSbPUFWRshE/xpOOMk3W";

        //等级考试header
        Headers gradeHeader = new Headers.Builder()
                .add("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .add("Accept-Language", "zh-CN,zh;q=0.8")
                .add("Cache-Control", "max-age=0")
                .add("Connection", "keep-alive")
                .add("Content-Length", "1925")
                .add("Content-Type", "application/x-www-form-urlencoded")
                .add("Host", "jwgl.hunnu.edu.cn")
                .add("Origin", "http://jwgl.hunnu.edu.cn")
                .add("Referer", gradeScoreUrl)
                .add("Upgrade-Insecure-Requests", "1")
                .add("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.87 Safari/537.36")
                .build();

        RequestBody formdata = new FormBody.Builder()
                .add("__VIEWSTATE", score_VIEWSTATE)
                .add("ddlXN", sectionYear)
                .add("ddlXQ", "1")
                .add("Button5", flag)
                .build();

        String htmlBody = null;
        Request request = new Request.Builder().url(gradeScoreUrl).headers(gradeHeader).post(formdata).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            htmlBody = response.body().string();
        }
        List<List<String>> dataList = new ArrayList<>();

        //解析并获取数据
        Document doc = Jsoup.parse(htmlBody);
        Element el = doc.getElementById("Datagrid1");
        Elements trs = el.select("table").select("tr");
        for (int i = 0; i < trs.size(); i++) {

            Elements tds = trs.get(i).select("td");//获取td数据
            List<String> td = new ArrayList<>();
            for (int j = 0; j < tds.size(); j++) {
                td.add(tds.get(j).text());//添加数据
            }
            dataList.add(td);//获取一行数据
        }

        String dataExam = "";
        for (int i = 1; i < dataList.size(); i++)//第一个数据是标题
        {
            List<String> td = dataList.get(i);
            dataExam = dataExam + "课程名称：" + td.get(3) + "\t成绩：" + td.get(8);
            dataExam = dataExam + "\n";
        }

        //提交给handle
        Message msg = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("data", dataExam);
        msg.setData(bundle);
        handler.sendMessage(msg);
        return true;
    }

    //开始发送post
    @OnClick(R.id.postFrom_btn)
    public void onClick() {
        runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    //startLogin();
                    //getGradeExam();

                    getExamPerformance(postYear, "按学年查询");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread th = new Thread(runnable);
        th.start();
    }
}

