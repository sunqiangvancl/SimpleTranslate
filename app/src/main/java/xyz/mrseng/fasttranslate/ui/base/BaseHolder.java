package xyz.mrseng.fasttranslate.ui.base;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import xyz.mrseng.fasttranslate.service.TransService;

/**
 * Created by MrSeng on 2016/12/12.
 */

public abstract class BaseHolder<T> {

    private View mRootView;
    private T mData;
//    protected TransService mService;
    private Activity mActivity;
    public Activity getActivity() {
        return mActivity;
    }
    public BaseHolder(Activity activity) {
//        mService = TransService.getNewInstance();
        mActivity = activity;
        mRootView = initView();
    }
    public abstract View initView();
    public void setData(T data) {
        mData = data;
        onRefresh(data);
    }
    public abstract void onRefresh(T data);

    public View getRootView() {
        return mRootView;
    }

    public T getData() {
        return mData;
    }


}
