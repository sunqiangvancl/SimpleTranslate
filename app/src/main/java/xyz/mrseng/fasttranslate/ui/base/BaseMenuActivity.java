package xyz.mrseng.fasttranslate.ui.base;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by MrSeng on 2016/12/15.
 */

public abstract class BaseMenuActivity extends BaseAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();
        setContentView(view);
        initActionBar();
    }

    //初始化actionbar
    protected void initActionBar() {
        ActionBar bar = getSupportActionBar();
        bar.setHomeButtonEnabled(true);
        bar.setDisplayHomeAsUpEnabled(true);
        bar.setTitle(getTitleStr());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    protected abstract View getView();

    protected abstract String getTitleStr();
}
