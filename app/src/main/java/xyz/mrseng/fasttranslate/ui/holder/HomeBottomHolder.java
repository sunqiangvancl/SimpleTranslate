package xyz.mrseng.fasttranslate.ui.holder;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.domain.TranslateBean;
import xyz.mrseng.fasttranslate.service.TransService;
import xyz.mrseng.fasttranslate.utils.UIUtils;

/**
 * Created by MrSeng on 2016/12/14.
 * 首页底部的逻辑执行者
 */

public class HomeBottomHolder extends BaseHolder<Integer> implements TransService.TranslateListener {
    public static final int SHOW_HISTORY = 0;
    public static final int SHOW_WAITING = 1;
    public static final int SHOW_RESULT = 2;
    public static final int SHOW_ERROR = 3;
    public static final int SHOW_NONE = 4;//首次沒有历史记录可以显示，留白
    private TransService mService;

    private View mWaiting;
    private ResultCardHolder mResultHolder;
    private HistoryCardHolder mHistoryHolder;
    private View mErrorPager;

    public HomeBottomHolder(Activity activity) {
        super(activity);
    }


    @Override
    public View initView() {
        mService = TransService.getNewInstance();
        FrameLayout frameLayout = new FrameLayout(UIUtils.getContext());
        initWidget(frameLayout);
        mService.addTranslateListener(this);
        return frameLayout;
    }

    private void initWidget(FrameLayout root) {
        //进度条
        mWaiting = UIUtils.inflate(R.layout.card_wating_home);
        //结果卡片
        mResultHolder = new ResultCardHolder(getActivity());
        //历史记录
        mHistoryHolder = new HistoryCardHolder(getActivity());
        mHistoryHolder.initData(0);
        //网络异常
        mErrorPager = UIUtils.inflate(R.layout.card_no_net_work);
        mErrorPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mService.doTranslate();
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
        mHistoryHolder.getRootView().setVisibility(View.INVISIBLE);
        mErrorPager.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onRefresh(final Integer state) {
        UIUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                mWaiting.setVisibility(state == SHOW_WAITING ? View.VISIBLE : View.INVISIBLE);
                mHistoryHolder.getRootView().setVisibility(state == SHOW_HISTORY ? View.VISIBLE : View.INVISIBLE);
                mResultHolder.getRootView().setVisibility(state == SHOW_RESULT ? View.VISIBLE : View.INVISIBLE);
                mErrorPager.setVisibility(state == SHOW_ERROR ? View.VISIBLE : View.INVISIBLE);
            }
        });
    }

    @Override
    public void afterTranslate(TranslateBean transInfo) {

        //初次进入应用，未进行有效翻译，显示历史卡片
        if (!TextUtils.isEmpty(transInfo.toWord)) {
            setData(SHOW_RESULT);
        } else if (TextUtils.isEmpty(transInfo.fromWord)) {//from To，都是null
            setData(SHOW_HISTORY);
        } else {//from非空，to是null
            setData(SHOW_ERROR);
        }
    }

    @Override
    public void beforeTranslate(TranslateBean transInfo) {
        //等待状态
        setData(HomeBottomHolder.SHOW_WAITING);
    }
}
