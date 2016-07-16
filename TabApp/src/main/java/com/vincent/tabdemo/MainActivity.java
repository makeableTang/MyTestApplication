package com.vincent.tabdemo;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, TabHost.OnTabChangeListener {

    /**
     * 使用ButterKnife 使我们从控件id中解放出来，@InjectView(R.id.fragmenttabhost)的添加 即可使用
     */
    @InjectView(R.id.fragmenttabhost)
    FragmentTabHost mFragmenttabhost;
    @InjectView(R.id.drawerable)
    DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /**初始化ButterKnife*/
        ButterKnife.inject(this);

        initView();
        /**暂时先不显示回退键*/
        initActionBar();
    }

    private void initActionBar() {
        ActionBar actionBar = getSupportActionBar();
        /**显示回退键，默认为false*/
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void initView() {
        /**滑动菜单时回退键动画效果*/
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        //初始化FragmentTabHost
        mFragmenttabhost.setup(this, getSupportFragmentManager(), R.id.real);
        if (Build.VERSION.SDK_INT > 10) {
            mFragmenttabhost.getTabWidget().setShowDividers(0);
            initTab();
            mFragmenttabhost.setCurrentTab(0);
            mFragmenttabhost.setOnTabChangedListener(this);
        }
    }

    /**
     * 加载底部FragmentTab
     */
    private void initTab() {
        MainTab[] tabs = MainTab.values();

        final int size = tabs.length;

        for (int i = 0; i < 2; i++) {
            // 找到每一个枚举的Fragment对象
            MainTab mainTab = tabs[i];

            // 1. 创建一个新的选项卡
            /**给每个Tab按钮设置图标、文字和内容*/
            TabHost.TabSpec tab = mFragmenttabhost.newTabSpec(String.valueOf(mainTab.getResName()));

            View indicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, null);

            TextView title = (TextView) indicator.findViewById(R.id.tab_title);
            Drawable drawable = this.getResources().getDrawable(mainTab.getResIcon());

            //setCompoundDrawablesWithIntrinsicBounds 在上下左右方向显示图片
            title.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
            title.setText(getString(mainTab.getResName()));
            tab.setIndicator(indicator);

            tab.setContent(new TabHost.TabContentFactory() {
                @Override
                public View createTabContent(String tag) {
                    return new View(MainActivity.this);
                }
            });

            //Bundle传递数据
            Bundle bundle = new Bundle();
            bundle.putString("key", String.valueOf(mainTab.getResName()));

            // 2. 把新的选项卡添加到TabHost中

            mFragmenttabhost.addTab(tab, mainTab.getClz(), bundle);

            // mFragmenttabhost.getTabWidget().getChildAt(i).setOnTouchListener(this);
        }
        mFragmenttabhost.setOnTouchListener(this);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return false;
    }

    @Override
    public void onTabChanged(String tabId) {
        //    Toast.makeText(MainActivity.this,tabId.toString(),Toast.LENGTH_SHORT).show();
    }

    /**
     * 回退键与侧滑菜单联动
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /**安卓自带的返回箭头 ActionBar中有*/
            case android.R.id.home:
                mDrawerToggle.onOptionsItemSelected(item);
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
