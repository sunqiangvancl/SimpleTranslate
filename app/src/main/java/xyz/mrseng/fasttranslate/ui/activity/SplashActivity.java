package xyz.mrseng.fasttranslate.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.ui.activity.base.BaseActivity;
import xyz.mrseng.fasttranslate.utils.UIUtils;

/**
 * Created by MrSeng on 2016/12/12.
 * 闪屏页
 */

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*no title*/
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*fullScreen*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        long startTime = System.currentTimeMillis();
        init();//进行初始化操作
        long endTime = System.currentTimeMillis();
        long rest = 500 - endTime + startTime;
        UIUtils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(UIUtils.getContext(), HomeActivity.class);
                startActivity(intent);
            }
        }, rest > 0 ? rest : 0);

        overridePendingTransition(R.anim.activity_in,R.anim.activity_out);

    }

    private void init() {
    }



}
