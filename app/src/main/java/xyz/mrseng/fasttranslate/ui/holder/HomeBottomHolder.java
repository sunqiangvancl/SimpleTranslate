package xyz.mrseng.fasttranslate.ui.holder;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.domain.TransBean;
import xyz.mrseng.fasttranslate.engine.TransService;
import xyz.mrseng.fasttranslate.ui.activity.HomeActivity;
import xyz.mrseng.fasttranslate.ui.base.BaseHolder;
import xyz.mrseng.fasttranslate.utils.UIUtils;

/**
 * Created by MrSeng on 2016/12/14.
 * 首页底部的逻辑执行者
 */

public class HomeBottomHolder extends BaseHolder<Integer> implements TransService.TranslateListener {
    public static final int SHOW_HISTORY = 0;//历史纪录卡片
    public static final int SHOW_WAITING = 1;//等待卡片
    public static final int SHOW_RESULT = 2;//翻译结果卡片
    public static final int SHOW_ERROR = 3;//网络错误
    public static final int SHOW_NONE = 4;//网络错误

    private View mWaiting;
    private ResultCardHolder mResultHolder;
    private HistoryCardHolder mHistoryHolder;
    private View mErrorPager;
    private HomeActivity homeActivity;

    public HomeBottomHolder(Activity activity) {
        super(activity);
    }


    @Override
    public View initView() {
        homeActivity = (HomeActivity) getActivity();
        FrameLayout frameLayout = new FrameLayout(UIUtils.getContext());
        initWidget(frameLayout);
        //监听翻译状态
        homeActivity.getTransService().addTransListener(this);
        return frameLayout;
    }

    private void initWidget(FrameLayout root) {
        //进度条
        mWaiting = UIUtils.inflate(R.layout.card_wating_home);
        //历史记录
        mHistoryHolder = new HistoryCardHolder(getActivity());
        //结果卡片
        mResultHolder = new ResultCardHolder(getActivity());
        //网络异常
        mErrorPager = UIUtils.inflate(R.layout.card_error_home);
        mErrorPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //更新翻译
                ((HomeActivity) getActivity()).notifyTransInfoChanged();
            }
        });
        //add
        root.removeAllViews();
        root.addView(mWaiting);
        root.addView(mResultHolder.getRootView());
        root.addView(mHistoryHolder.getRootView());
        root.addView(mErrorPager);
        //invisible
        mWaiting.setVisibility(View.INVISIBLE);
        mResultHolder.getRootView().setVisibility(View.INVISIBLE);
        //刚开始应该显示历史记录
//        mHistoryHolder.getRootView().setVisibility(View.INVISIBLE);
        mErrorPager.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onRefresh(final Integer state) {
        /*历史记录数据已经更新了*/
        if (state == SHOW_HISTORY) {
            //通知历史记录卡片更新数据
            mHistoryHolder.notifyDataChange();
        }
        mWaiting.setVisibility(state == SHOW_WAITING ? View.VISIBLE : View.INVISIBLE);
        mHistoryHolder.getRootView().setVisibility(state == SHOW_HISTORY ? View.VISIBLE : View.INVISIBLE);
        mResultHolder.getRootView().setVisibility(state == SHOW_RESULT ? View.VISIBLE : View.INVISIBLE);
        mErrorPager.setVisibility(state == SHOW_ERROR ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void afterTrans(TransBean transInfo) {
        if (!TextUtils.isEmpty(transInfo.toWord)) {//有翻译结果，显示结果卡片
            setData(SHOW_RESULT);
        } else if (TextUtils.isEmpty(transInfo.fromWord)) {//from To，都是null，显示历史记录
            setData(SHOW_HISTORY);
        } else {//from非空，to是null，证明网络错误
            setData(SHOW_ERROR);
        }
    }

    @Override
    public void beforeTrans(TransBean transInfo) {
        //等待状态
        setData(HomeBottomHolder.SHOW_WAITING);
    }
}
