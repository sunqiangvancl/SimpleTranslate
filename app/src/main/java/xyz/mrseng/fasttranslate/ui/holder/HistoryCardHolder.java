package xyz.mrseng.fasttranslate.ui.holder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.dao.TransDao;
import xyz.mrseng.fasttranslate.domain.TransBean;
import xyz.mrseng.fasttranslate.global.Canstant;
import xyz.mrseng.fasttranslate.engine.MarkService;
import xyz.mrseng.fasttranslate.ui.activity.HomeActivity;
import xyz.mrseng.fasttranslate.ui.base.BaseHolder;
import xyz.mrseng.fasttranslate.ui.base.MyBaseAdapter;
import xyz.mrseng.fasttranslate.utils.UIUtils;

/**
 * Created by MrSeng on 2016/12/14.
 * 历史记录卡片
 */

public class HistoryCardHolder extends BaseHolder<ArrayList<TransBean>> {

    private ListView mLv_history;
    private TransDao mDao;
    private MarkService markService;

    public HistoryCardHolder(Activity activity) {
        super(activity);
        markService = new MarkService();
    }

    @Override
    public View initView() {
        mDao = new TransDao();
        View view = UIUtils.inflate(R.layout.card_history_list_home);
        mLv_history = (ListView) view.findViewById(R.id.lv_history_home);

        mLv_history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HomeActivity homeActivity = (HomeActivity) getActivity();
                TransBean bean = getData().get(position);
                bean.token = TransBean.TOKEN_LOCAL;
                homeActivity.setTransInfo(bean);
                homeActivity.notifyTransInfoChanged();
            }
        });
        mLv_history.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteDialog(position);
                return false;
            }
        });
        //准备历史数据
        initData(0);
        return view;
    }

    /*长按历史记录弹出删除提示*/
    private void showDeleteDialog(final int pos) {
        new AlertDialog.Builder(getActivity())
                .setTitle(UIUtils.getString(R.string.tips))
                .setMessage(UIUtils.getString(R.string.are_you_sure_to_delete))
                .setPositiveButton(UIUtils.getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDao.delete(getData().get(pos).id);
                        getData().remove(getData().get(pos));
                        ((MyBaseAdapter<TransBean>) mLv_history.getAdapter()).notifyDataSetChanged(getData());
                    }
                }).setNegativeButton(UIUtils.getString(R.string.cancel), null)
                .show();
    }

    @Override
    public void onRefresh(ArrayList<TransBean> data) {
        if (data != null) {
            MyBaseAdapter<TransBean> adapter = new MyBaseAdapter<TransBean>(getData()) {
                @Override
                public BaseHolder<TransBean> getHolder() {
                    return new BaseHolder<TransBean>(getActivity()) {
                        private ImageView mIv_mark;
                        private TextView mTv_from;
                        private TextView mTv_to;
                        @Override
                        public View initView() {
                            View view = UIUtils.inflate(R.layout.item_list_history);
                            mIv_mark = (ImageView) view.findViewById(R.id.iv_mark_item);
                            mTv_from = (TextView) view.findViewById(R.id.tv_fromwrod_item);
                            mTv_to = (TextView) view.findViewById(R.id.tv_toword_item);
                            //给收藏图标设置侦听
                            mIv_mark.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    markService.switchMark(getData());
                                    onRefresh(getData());
                                }
                            });
                            return view;
                        }
                        @Override
                        public void onRefresh(TransBean data) {
                            mIv_mark.setImageDrawable(UIUtils.getDrawable(data.marked == Canstant.UN_MARKED ? R.drawable.ic_star_black : R.drawable.ic_star_marked));
                            mTv_from.setText(data.fromWord);
                            mTv_to.setText(data.toWord);
                        }
                    };
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    if (position == getCount()-1){
                        loadMore();
                    }
                    return super.getView(position, convertView, parent);
                }

                //加载更多
                private void loadMore() {
                    if (getData()!=null){
                        if (getData().size() % Canstant.LIMIT_PAGE_SIZE == 0){
                            int page = getData().size()  / Canstant.LIMIT_PAGE_SIZE;
                            ArrayList<TransBean> newData = mDao.queryList(page);
                            if (newData!=null && newData.size()>0){
                                getData().addAll(newData);
                                this.notifyDataSetChanged(getData());
                            }
                        }
                    }
                }
            };
            mLv_history.setAdapter(adapter);
        }
    }

    /** 更新数据 */
    public void notifyDataChange() {
        setData(mDao.queryList(0));
    }

    public void initData(int i) {
        ArrayList<TransBean> data = mDao.queryList(i);
        if (data == null) {
            mLv_history.setVisibility(View.GONE);
        } else {
            setData(data);
            mLv_history.setVisibility(View.VISIBLE);
        }
    }
}
