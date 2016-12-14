package xyz.mrseng.fasttranslate.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.ui.activity.base.BaseActivity;
import xyz.mrseng.fasttranslate.utils.UIUtils;

/**
 * Created by MrSeng on 2016/12/13.
 */

public class TransActivity extends Activity {

    private FrameLayout mFl_root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //no title
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mFl_root = (FrameLayout) UIUtils.inflate(R.layout.activity_trans);
        setContentView(mFl_root);
        initView();
    }

    private void initView() {
        View mTransCard =  UIUtils.inflate(R.layout.pager_trans);
        mFl_root.removeAllViews();
        mFl_root.addView(mTransCard);
    }


    @Override
    public void finish() {
        super.finish();
    }
}
