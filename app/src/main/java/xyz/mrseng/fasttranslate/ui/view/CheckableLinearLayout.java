package xyz.mrseng.fasttranslate.ui.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.LinearLayout;

/**
 * Created by MrSeng on 2016/12/20.
 */

public class CheckableLinearLayout extends LinearLayout implements Checkable {

    private boolean mChecked;

    public CheckableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setChecked(boolean checked) {
        mChecked = checked;
        setBackgroundDrawable(checked ? new ColorDrawable(0xff0000a0) : null);//当选中时呈现蓝色
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }
}
