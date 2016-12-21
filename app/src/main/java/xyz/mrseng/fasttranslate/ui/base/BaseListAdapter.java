package xyz.mrseng.fasttranslate.ui.base;

import java.util.ArrayList;

/**
 * Created by MrSeng on 2016/12/20.
 */

public abstract class BaseListAdapter<T> extends MyBaseAdapter<T> {
    public BaseListAdapter(ArrayList<T> data) {
        super(data);
    }
}
