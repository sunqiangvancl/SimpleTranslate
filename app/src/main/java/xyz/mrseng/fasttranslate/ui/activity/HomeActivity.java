package xyz.mrseng.fasttranslate.ui.activity;

import android.os.Bundle;

import xyz.mrseng.fasttranslate.ui.activity.base.BaseAppCompatActivity;
import xyz.mrseng.fasttranslate.ui.holder.HomeHolder;
import xyz.mrseng.fasttranslate.utils.ActivityUtils;

/**
 * Created by MrSeng on 2016/12/12.
 * 翻译页面
 */
public class HomeActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeHolder mTHolder = new HomeHolder();
        setContentView(mTHolder.getRootView());
    }

    /** 返回直接结束应用 */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityUtils.getNewInstance().exit();
    }
}
