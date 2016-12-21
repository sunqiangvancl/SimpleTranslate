package xyz.mrseng.fasttranslate.ui.base;

import android.app.Activity;
import android.os.Bundle;

import xyz.mrseng.fasttranslate.utils.ActivityUtils;

/**
 * Created by MrSeng on 2016/12/12.
 */

public class BaseActivity extends Activity {


    private ActivityUtils mAu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAu = ActivityUtils.getNewInstance();
        mAu.add(this);
    }

    @Override
    public void finish() {
        mAu.remove(this);
        super.finish();
    }
}
