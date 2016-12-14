package xyz.mrseng.fasttranslate.ui.activity;

import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.ui.activity.base.BaseAppCompatActivity;
import xyz.mrseng.fasttranslate.ui.fragment.HomeFragment;
import xyz.mrseng.fasttranslate.utils.ActivityUtils;

/**
 * Created by MrSeng on 2016/12/12.
 * 主页面Activity
 */
public class HomeActivity extends BaseAppCompatActivity {

    private DrawerLayout mDl;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mDl = (DrawerLayout) findViewById(R.id.ml_home);
        getSupportFragmentManager().beginTransaction().add(R.id.fl_home, new HomeFragment()).commit();
        initActionBar();
    }


    @Override
    public void finish() {
        super.finish();
        ActivityUtils.getNewInstance().exit();
    }

    //actionbar
    private void initActionBar() {
        ActionBar bar = getSupportActionBar();

        bar.setHomeButtonEnabled(true);
        bar.setDisplayHomeAsUpEnabled(true);//显示返回箭头,当与侧边栏绑定后显示侧边栏图标

        //初始化侧边栏开关
        mToggle = new
                ActionBarDrawerToggle(this, mDl, R.drawable.ic_menu_white, R.string.drawer_open, R.string.drawer_close);
        mToggle.syncState();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_text:
                Toast.makeText(this, "MenuTest", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                mToggle.onOptionsItemSelected(item);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
