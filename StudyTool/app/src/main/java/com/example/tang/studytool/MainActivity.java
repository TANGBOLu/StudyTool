package com.example.tang.studytool;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tang.studytool.TabFragment.LearningFragment;
import com.example.tang.studytool.TabFragment.MinToolFragment;
import com.example.tang.studytool.TabFragment.SettingFragment;
import com.example.tang.studytool.TabFragment.TomotoFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    @BindView(R.id.learingplan_image)
    ImageView learingplanImage;
    @BindView(R.id.learingplan_text)
    TextView learingplanText;
    @BindView(R.id.learingplan_layout)
    RelativeLayout learingplanLayout;
    @BindView(R.id.tomato_image)
    ImageView tomatoImage;
    @BindView(R.id.tomato_text)
    TextView tomatoText;
    @BindView(R.id.tomato_layout)
    RelativeLayout tomatoLayout;
    @BindView(R.id.mintool_image)
    ImageView mintoolImage;
    @BindView(R.id.mintool_text)
    TextView mintoolText;
    @BindView(R.id.mintool_layout)
    RelativeLayout mintoolLayout;
    @BindView(R.id.setting_image)
    ImageView settingImage;
    @BindView(R.id.setting_text)
    TextView settingText;
    @BindView(R.id.setting_layout)
    RelativeLayout settingLayout;

    LearningFragment learningFragment;
    MinToolFragment minToolFragment;
    SettingFragment settingFragment;
    TomotoFragment tomotoFragment;

    /*
    * 用于fragment管理
     */
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ButterKnife.bind(this);
        if(savedInstanceState==null) {

            fragmentManager = getFragmentManager();//初始化
        }
        initTabLay();//初始化layout
        setTabSelection(0);//默认第一页

    }

    private void initTabLay() {
        learingplanLayout.setOnClickListener(this);
        tomatoLayout.setOnClickListener(this);
        mintoolLayout.setOnClickListener(this);
        settingLayout.setOnClickListener(this);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.learingplan_layout:
                setTabSelection(0);
                break;
            case R.id.tomato_layout:
                setTabSelection(1);
                break;
            case R.id.mintool_layout:
                setTabSelection(2);
                break;
            case R.id.setting_layout:
                setTabSelection(3);
                break;
        }
    }

    /**
     * 消息分发
     */
    private void setTabSelection(int index) {
        // 每次选中之前先清楚掉上次的选中状态
        clearSelection();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);

        switch (index) {
            case 0:
                //learingplanImage.setImageResource(R.drawable.message_selected);
                learingplanText.setTextColor(Color.WHITE);
                if (learningFragment == null) {
                    // 如果为空，则创建一个并添加到界面上
                    learningFragment = new LearningFragment();
                    transaction.add(R.id.content, learningFragment);

                } else {
                    // 如果不为空，则直接将它显示出来

                    transaction.show(learningFragment);
                    transaction.replace(R.id.content,learningFragment);
                }
                break;
            case 1:
                // 当点击了番茄tab时，改变控件的图片和文字颜色
                //tomatoImage.setImageResource(R.drawable.contacts_selected);
                tomatoText.setTextColor(Color.WHITE);
                if (tomotoFragment == null) {
                    // 如果ContactsFragment为空，则创建一个并添加到界面上
                    tomotoFragment = new TomotoFragment();
                    transaction.add(R.id.content, tomotoFragment);
                } else {
                    // 如果ContactsFragment不为空，则直接将它显示出来
                    transaction.show(tomotoFragment);
                }
                break;
            case 2:
                // 当点击了tab时，改变控件的图片和文字颜色
                //mintoolImage.setImageResource(R.drawable.news_selected);
                mintoolText.setTextColor(Color.WHITE);
                if (minToolFragment == null) {
                    // 如果为空，则创建一个并添加到界面上
                    minToolFragment = new MinToolFragment();
                    transaction.add(R.id.content, minToolFragment);
                } else {
                    // 如果不为空，则直接将它显示出来
                    transaction.show(minToolFragment);

                }
                break;
            case 3:
            default:
                // 当点击了设置tab时，改变控件的图片和文字颜色
                settingImage.setImageResource(R.drawable.ic_setup);
                settingText.setTextColor(Color.WHITE);
                if (settingFragment == null) {
                    // 如果SettingFragment为空，则创建一个并添加到界面上
                    settingFragment = new SettingFragment();
                    transaction.add(R.id.content, settingFragment);
                } else {
                    // 如果SettingFragment不为空，则直接将它显示出来
                    transaction.show(settingFragment);
                }
                break;
        }
        transaction.commit();

    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        learingplanImage.setImageResource(R.drawable.ic_learning_plan);
        learingplanText.setTextColor(Color.parseColor("#82858b"));
        tomatoImage.setImageResource(R.drawable.ic_tomato);
        tomatoText.setTextColor(Color.parseColor("#82858b"));
        mintoolImage.setImageResource(R.drawable.ic_mintool);
        mintoolText.setTextColor(Color.parseColor("#82858b"));
        settingImage.setImageResource(R.drawable.ic_setup);
        settingText.setTextColor(Color.parseColor("#82858b"));
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (learningFragment != null) {
            transaction.hide(learningFragment);
        }
        if (tomotoFragment != null) {
            transaction.hide(tomotoFragment);
        }
        if (minToolFragment != null) {
            transaction.hide(minToolFragment);
        }
        if (settingFragment != null) {
            transaction.hide(settingFragment);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
