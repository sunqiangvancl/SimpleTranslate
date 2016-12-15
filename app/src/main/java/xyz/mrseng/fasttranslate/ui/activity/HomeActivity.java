package xyz.mrseng.fasttranslate.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.ui.activity.base.BaseAppCompatActivity;
import xyz.mrseng.fasttranslate.ui.fragment.HomeFragment;
import xyz.mrseng.fasttranslate.ui.holder.LeftCardHolder;
import xyz.mrseng.fasttranslate.utils.ActivityUtils;

/**
 * Created by MrSeng on 2016/12/12.
 * 主页面Activity
 */
public class HomeActivity extends BaseAppCompatActivity {

    private DrawerLayout mDl;
    private ActionBarDrawerToggle mToggle;
    private android.support.v7.app.ActionBarDrawerToggle togglev7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mDl = (DrawerLayout) findViewById(R.id.ml_home);
        //主内容fragment
        getSupportFragmentManager().beginTransaction().add(R.id.fl_home, new HomeFragment()).commit();
        //侧边栏
        FrameLayout mFl_menu = (FrameLayout) findViewById(R.id.fl_left_menu);
        LeftCardHolder leftHolder = new LeftCardHolder(this);
        mFl_menu.removeAllViews();
        mFl_menu.addView(leftHolder.getRootView());

        //actionbar
        initActionBar();
    }


    @Override
    public void finish() {
        //侧边栏没关闭应该先关闭侧边栏
        if (mDl.isDrawerOpen(Gravity.LEFT)) {
            closeDrawer();
        } else {
            super.finish();
            ActivityUtils.getNewInstance().exit();
        }
    }

    //actionbar和侧边栏
    private void initActionBar() {
        //这三行代码可以是左侧边栏显示成菜单图标
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setDisplayShowHomeEnabled(true);

        mToggle = new ActionBarDrawerToggle(this, mDl, R.string.drawer_open, R.string.drawer_close);
        mToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mToggle.onOptionsItemSelected(item);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /** 关闭抽屉 */
    public void closeDrawer() {
        mDl.closeDrawer(Gravity.LEFT);
    }


}
