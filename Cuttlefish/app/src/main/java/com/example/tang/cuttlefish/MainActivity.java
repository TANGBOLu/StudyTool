package com.example.tang.cuttlefish;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.tang.cuttlefish.BottomBarFragment.IndexFragment;
import com.example.tang.cuttlefish.BottomBarFragment.LearningFragment;
import com.example.tang.cuttlefish.BottomBarFragment.TestScanActivity;
import com.example.tang.cuttlefish.BottomBarFragment.UserFragment;
import com.example.tang.studytool.TabFragment.MinToolFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.my_toolbar)
    Toolbar indexToolBar;
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    LearningFragment learningFragment;
    MinToolFragment minToolFragment;
    UserFragment userFragment;
    @BindView(R.id.contentContainer)
    FrameLayout contentContainer;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        /* 添加toolbar*/
        setSupportActionBar(indexToolBar);

        /* 初始化toolbar*/
        InitToolBar();
        /* 初始化fragmentManager*/
        if (savedInstanceState == null) {
            fragmentManager = getFragmentManager();//初始化
        }
        InitBottomBar();
    }

    /**
     * 初始化toolBar
     */
    private void InitToolBar() {
//        indexToolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                Intent intent=null;
//                switch (item.getItemId()) {
//                    case R.id.action_search:
//                        /* 搜索按钮*/
//                        intent = new Intent();
//                        intent.setClass(MainActivity.this, SearchActivity.class);
//                        startActivity(intent);
//                        break;
//                    case R.id.action_Scanning:
//                        /* 二维码扫描按钮*/
//                        intent=new Intent();
//                        intent.setClass(MainActivity.this,TestScanActivity.class);
//                        startActivity(intent);
//                        break;
//                    default:
//                        System.out.println("toolbar菜单选择错误");
//                        break;
//                }
//                return true;
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 初始化bottomBar
     */
    private void InitBottomBar() {
        /* bottomBar Tab事件*/
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_index:
                        setTabSelection(0);
                        break;
                    case R.id.tab_nearby:
                        setTabSelection(1);
                        break;
                    case R.id.tab_shoppingcart:
                        setTabSelection(2);
                        break;
                    case R.id.tab_user:
                        setTabSelection(3);
                        break;
                }
            }
        });
    }

    /**
     * 设置当前选中的Tab页
     *
     * @param index
     */
    public void setTabSelection(int index) {

        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);

        switch (index) {
            case 0:
                if (learningFragment == null) {
                    // 如果为空，则创建一个并添加到界面上
                    learningFragment = new LearningFragment();
                    transaction.add(R.id.contentContainer, learningFragment);
                } else {
                    // 如果不为空，则直接将它显示出来
                    transaction.show(learningFragment);
                }
                break;
            case 1:
                if (minToolFragment == null) {
                    // 如果SettingFragment为空，则创建一个并添加到界面上
                    minToolFragment = new MinToolFragment();
                    transaction.add(R.id.contentContainer, minToolFragment);
                } else {
                    // 如果SettingFragment不为空，则直接将它显示出来
                    transaction.show(userFragment);
                }
                break;
            case 2:
                break;
            case 3:
                if (userFragment == null) {
                    // 如果SettingFragment为空，则创建一个并添加到界面上
                    userFragment = new UserFragment();
                    transaction.add(R.id.contentContainer, userFragment);
                } else {
                    // 如果SettingFragment不为空，则直接将它显示出来
                    transaction.show(userFragment);
                }
                break;
            default:
        }

        transaction.commit();
    }

    /**
     * 隐藏全部fragment
     *
     * @param transaction
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (learningFragment != null) {
            transaction.hide(learningFragment);
        }
        if(minToolFragment!=null){
            transaction.hide(minToolFragment);
        }
        if (userFragment != null) {
            transaction.hide(userFragment);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* 设置toolbar菜单*/
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
