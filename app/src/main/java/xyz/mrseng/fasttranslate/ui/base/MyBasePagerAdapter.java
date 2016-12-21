package xyz.mrseng.fasttranslate.ui.base;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by MrSeng on 2016/12/19.
 */

public abstract class MyBasePagerAdapter extends PagerAdapter {
    @Override
    public abstract int getCount();

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public abstract Object instantiateItem(ViewGroup container, int position);

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
