package xyz.mrseng.fasttranslate.ui.holder;

import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.dao.HistoryDao;
import xyz.mrseng.fasttranslate.domain.HistoryItemBean;
import xyz.mrseng.fasttranslate.ui.adapter.MyBaseAdapter;
import xyz.mrseng.fasttranslate.utils.UIUtils;

/**
 * Created by MrSeng on 2016/12/14.
 * 历史记录卡片
 */

public class HistoryCardHolder extends BaseHolder<ArrayList<HistoryItemBean>> {

    private ListView mLv_history;
    private HistoryDao mDao = new HistoryDao();

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.card_history_list_home);
        mLv_history = (ListView) view.findViewById(R.id.lv_history_home);
        return view;
    }

    @Override
    public void onRefresh(ArrayList<HistoryItemBean> data) {
        //设置适配器
        mLv_history.setAdapter(new MyBaseAdapter<HistoryItemBean>(data) {
            @Override
            public BaseHolder<HistoryItemBean> getHolder() {
                return new HistoryItemHolder();
            }
        });
    }

    public void initData(int i) {
        setData(mDao.queryList(i));
    }

}
