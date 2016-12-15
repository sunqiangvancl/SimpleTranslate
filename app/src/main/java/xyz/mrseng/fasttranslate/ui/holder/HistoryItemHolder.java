package xyz.mrseng.fasttranslate.ui.holder;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.domain.HistoryItemBean;
import xyz.mrseng.fasttranslate.utils.UIUtils;

/**
 * Created by MrSeng on 2016/12/14.
 */

public class HistoryItemHolder extends BaseHolder<HistoryItemBean> {

    private ImageView mIv_mark;
    private TextView mTv_from;
    private TextView mTv_to;

    public HistoryItemHolder(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.item_list_history);
        mIv_mark = (ImageView) view.findViewById(R.id.iv_mark_item);
        mTv_from = (TextView) view.findViewById(R.id.tv_fromwrod_item);
        mTv_to = (TextView) view.findViewById(R.id.tv_toword_item);
        return view;
    }
    @Override
    public void onRefresh(HistoryItemBean data) {
        mIv_mark.setImageDrawable(UIUtils.getDrawable(data.marked==0? R.drawable.ic_star_black:R.drawable.ic_star_marked));
        mTv_from.setText(data.fromWord);
        mTv_to.setText(data.toWord);
    }
}
