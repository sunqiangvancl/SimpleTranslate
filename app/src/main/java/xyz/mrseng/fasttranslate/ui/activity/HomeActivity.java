package xyz.mrseng.fasttranslate.ui.activity;

import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
        bar.setDisplayShowHomeEnabled(true);
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
        }
        return super.onOptionsItemSelected(item);
    }
}
