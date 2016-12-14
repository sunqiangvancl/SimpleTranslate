package xyz.mrseng.fasttranslate.ui.holder;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.domain.ResultBean;
import xyz.mrseng.fasttranslate.domain.TranslateBean;
import xyz.mrseng.fasttranslate.service.TransService;
import xyz.mrseng.fasttranslate.ui.activity.TransActivity;
import xyz.mrseng.fasttranslate.utils.UIUtils;

/**
 * Created by MrSeng on 2016/12/12.
 * 翻译页面的逻辑执行者
 */

public class HomeHolder extends BaseHolder<Integer> {

    private FrameLayout mFl_bottom;
    private ResultCardHolder mResultHolder;
    private FrameLayout mFl_top;
    private FrameLayout mFl_center;
    private final static int STATE_UNDO = 0;
    private final static int STATE_TRANSLATING = 1;
    private final static int STATE_TRANSLATED = 2;
    private View mPb_waiting;
    private InputHolder mInputHolder;
    private LangHolder mLangHolder;

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.activity_home);
        initWidget(view);
        mService.registTranslateListener(new MyTransListener());
        return view;
    }

    /** 初始化控件 */
    private void initWidget(View view) {
        //top bar
        mFl_top = (FrameLayout) view.findViewById(R.id.fl_top_home);
        mLangHolder = new LangHolder();
        mFl_top.addView(mLangHolder.getRootView());

        //center
        mFl_center = (FrameLayout) view.findViewById(R.id.fl_center_home);
        mInputHolder = new InputHolder();
        mInputHolder.setHomeHolder(this);
        mFl_center.addView(mInputHolder.getRootView());


        //bottom
        mFl_bottom = (FrameLayout) view.findViewById(R.id.fl_bottom_home);
        mResultHolder = new ResultCardHolder();
        mPb_waiting = UIUtils.inflate(R.layout.card_wating_home);
        mFl_bottom.addView(mResultHolder.getRootView());
        mFl_bottom.addView(mPb_waiting);
    }

    @Override
    public void onRefresh(final Integer data) {
        UIUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                mResultHolder.getRootView().setVisibility(data == STATE_TRANSLATED ? View.VISIBLE : View.GONE);
                mPb_waiting.setVisibility(data == STATE_TRANSLATING ? View.VISIBLE : View.GONE);
            }
        });
    }

    public void onEditTextTouch() {
        Intent intent = new Intent(UIUtils.getContext(), TransActivity.class);
        Bundle bundle = new Bundle();
        TranslateBean bean = new TranslateBean();
        bean.fromCode = mLangHolder.getData().fromCode;
        bean.toCode = mLangHolder.getData().toCode;
        bundle.putSerializable("data", bean);
        //TODO 跳转到下一个activity，解决回调
    }

    private class MyTransListener implements TransService.TranslateListener {

        @Override
        public void afterTranslate(ResultBean data) {
            int currState;
            if (data != null) {
                currState = STATE_TRANSLATED;
            } else if (TextUtils.isEmpty(mInputHolder.getInputText())) {
                //数据为空，并且输入框为空
                currState = STATE_UNDO;
            } else {
                currState = STATE_TRANSLATING;
            }
            onRefresh(currState);
        }

        @Override
        public void beforeTranslate() {
            onRefresh(STATE_TRANSLATING);
        }
    }

}
