package xyz.mrseng.fasttranslate.ui.activity.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import xyz.mrseng.fasttranslate.utils.ActivityUtils;

/**
 * Created by MrSeng on 2016/12/12.
 * AppComPatActivity的基类
 */

public class BaseAppCompatActivity extends AppCompatActivity {
    private ActivityUtils mAu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAu = ActivityUtils.getNewInstance();
        mAu.add(this);
    }

    @Override
    public void finish() {
        super.finish();
        mAu.remove(this);
    }


}
