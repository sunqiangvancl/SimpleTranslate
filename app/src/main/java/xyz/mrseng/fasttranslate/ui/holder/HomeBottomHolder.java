package xyz.mrseng.fasttranslate.ui.holder;

import android.view.View;
import android.widget.FrameLayout;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.domain.ResultBean;
import xyz.mrseng.fasttranslate.service.TransService;
import xyz.mrseng.fasttranslate.utils.UIUtils;

/**
 * Created by MrSeng on 2016/12/14.
 * 首页底部的逻辑执行者
 */

public class HomeBottomHolder extends BaseHolder<Integer> implements TransService.TranslateListener{
    public static final int SHOW_HISTORY = 0;
    public static final int SHOW_WAITING = 1;
    public static final int SHOW_RESULT = 2;
    public static final int SHOW_NO_NET = 3;
    private TransService mService;
    //是否已经进行过一次有效地翻译，有过一次翻译结果不为空的经历，这样可以决定应该显示结果卡片还是历史卡片
    //因为应用初始化的时候回测试翻译，如果没有这个标记，历史卡片将没有时机显示
    private boolean hasValidTranslate = false;

    private View mWaiting;
    private ResultCardHolder mResultHolder;
    private HistoryCardHolder mHistoryHolder;


    @Override
    public View initView() {
        mService = TransService.getNewInstance();
        mService.addTranslateListener(this);
        FrameLayout frameLayout = new FrameLayout(UIUtils.getContext());
        initWidget(frameLayout);
        return frameLayout;
    }

    private void initWidget(FrameLayout root) {
        //进度条
        mWaiting = UIUtils.inflate(R.layout.card_wating_home);
        //结果卡片
        mResultHolder = new ResultCardHolder();
        //历史记录
        mHistoryHolder = new HistoryCardHolder();
        mHistoryHolder.initData(0);

        //add
        root.removeAllViews();
        root.addView(mWaiting);
        root.addView(mResultHolder.getRootView());
        root.addView(mHistoryHolder.getRootView());

        //invisible
        mWaiting.setVisibility(View.GONE);
        mResultHolder.getRootView().setVisibility(View.GONE);
        mHistoryHolder.getRootView().setVisibility(View.GONE);

    }

    @Override
    public void onRefresh(final Integer state) {
        UIUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                mWaiting.setVisibility(state == SHOW_WAITING ? View.VISIBLE : View.GONE);
                mHistoryHolder.getRootView().setVisibility(state == SHOW_HISTORY ? View.VISIBLE : View.GONE);
                mResultHolder.getRootView().setVisibility(state == SHOW_RESULT ? View.VISIBLE : View.GONE);
            }
        });
    }
    @Override
    public void afterTranslate(ResultBean data) {
        //初次进入应用，未进行有效翻译，显示历史卡片
        setData(hasValidTranslate?HomeBottomHolder.SHOW_RESULT:HomeBottomHolder.SHOW_HISTORY);
        if (data!=null){
            hasValidTranslate = true;
        }
    }
    @Override
    public void beforeTranslate() {
        //等待状态
        setData(HomeBottomHolder.SHOW_WAITING);
    }

}
