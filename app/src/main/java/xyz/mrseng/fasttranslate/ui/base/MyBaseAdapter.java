package xyz.mrseng.fasttranslate.ui.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

import xyz.mrseng.fasttranslate.domain.TransBean;
import xyz.mrseng.fasttranslate.global.Canstant;

/**
 * Created by MrSeng on 2016/12/14.
 * listView的适配器封装
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {

    private ArrayList<T> mList;

    public MyBaseAdapter(ArrayList<T> data) {
        mList = data;
    }

    public void notifyDataSetChanged(ArrayList<T> data) {
        mList = data;
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder<T> holder;
        if (convertView == null) {
            holder = getHolder();
            convertView = holder.getRootView();
            convertView.setTag(holder);
        } else {
            holder = (BaseHolder<T>) convertView.getTag();
        }
        holder.setData(getItem(position));
        return holder.getRootView();
    }

    abstract public BaseHolder<T> getHolder();

}
