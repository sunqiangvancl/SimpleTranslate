package xyz.mrseng.fasttranslate.ui.activity;

import android.os.Bundle;

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
        if (savedInstanceState == null) {
            setContentView(R.layout.activity_home);
            getSupportFragmentManager().beginTransaction().add(R.id.fl_home, new HomeFragment()).commit();
        }
    }

    @Override
    public void finish() {
        super.finish();
        ActivityUtils.getNewInstance().exit();
    }
}
