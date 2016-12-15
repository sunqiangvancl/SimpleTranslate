package xyz.mrseng.fasttranslate.ui.activity.menu;

import android.view.View;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.ui.activity.base.BaseMenuActivity;
import xyz.mrseng.fasttranslate.utils.UIUtils;

public class MsgActivity extends BaseMenuActivity {

    @Override
    protected View getView() {
        return UIUtils.inflate(R.layout.activity_msg);
    }

    @Override
    protected String getTitleStr() {
        return UIUtils.getString(R.string.menu_msg_trans);
    }
}
