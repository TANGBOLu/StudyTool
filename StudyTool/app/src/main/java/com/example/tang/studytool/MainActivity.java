package com.example.tang.studytool;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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
    @BindView(R.id.activity_main)

    LinearLayout activityMain;
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
        ButterKnife.bind(this);

        InitLayout();
        fragmentManager = getFragmentManager();
        setTabSelection(0);//默认第一页
    }

    /**
     * 初始化Layout
     */
    private void InitLayout() {
        learingplanLayout.setOnClickListener(this);
        tomatoLayout.setOnClickListener(this);
        mintoolLayout.setOnClickListener(this);
        settingLayout.setOnClickListener(this);
    }

    /**
     * 重写单击事件
     * @param view
     */
    public void onClick(View view) {
        switch (view.getId()) {
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
    *   消息分发
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
     * @param transaction
     *            用于对Fragment执行操作的事务
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

}
