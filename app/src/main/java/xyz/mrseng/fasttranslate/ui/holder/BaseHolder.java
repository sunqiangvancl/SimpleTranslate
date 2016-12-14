package xyz.mrseng.fasttranslate.ui.holder;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Adapter;

import xyz.mrseng.fasttranslate.service.TransService;
import xyz.mrseng.fasttranslate.utils.UIUtils;

/**
 * Created by MrSeng on 2016/12/12.
 */

public abstract class BaseHolder<T> {
    private View mRootView;
    private T mData;
    private Context mContext;
    protected TransService mService;
    private Activity mActivity;
    private Adapter mAdapter;

    public BaseHolder(Adapter mAdapter) {
        this.mAdapter = mAdapter;
    }

    public Adapter getmAdapter() {
        return mAdapter;
    }

    public void setActivity(Activity activity) {
        mActivity = activity;
    }

    public Activity getActivity() {
        return mActivity;
    }


    public BaseHolder() {
        mService = TransService.getNewInstance();
        mContext = UIUtils.getContext();
        mRootView = initView();
    }

    public BaseHolder(Context context) {
        mContext = context;
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

    public Context getContext() {
        return mContext;
    }

    public T getData() {
        return mData;
    }


}
