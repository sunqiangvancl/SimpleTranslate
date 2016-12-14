package xyz.mrseng.fasttranslate.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.domain.TranslateBean;
import xyz.mrseng.fasttranslate.service.TransService;
import xyz.mrseng.fasttranslate.utils.TransUtils;
import xyz.mrseng.fasttranslate.utils.UIUtils;

/**
 * Created by MrSeng on 2016/12/13.
 * 结果卡片的逻辑
 */

public class ResultCardHolder extends BaseHolder<TranslateBean> implements TransService.TranslateListener, View.OnClickListener {

    private TextView mTv_result;//显示翻译结果
    private TextView mTv_readLang;//显示翻译至何种语言，显示朗读

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.card_result_trans);

        mTv_result = (TextView) view.findViewById(R.id.tv_result_card);
        mTv_readLang = (TextView) view.findViewById(R.id.tv_audio_lang);

        ImageView mIv_copy = (ImageView) view.findViewById(R.id.iv_copy_result);
        mIv_copy.setOnClickListener(this);

        //注册监听
        mService.addTranslateListener(this);
        return view;
    }

    @Override
    public void onRefresh(final TranslateBean data) {
        UIUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                mTv_result.setText(data.toWord == null ? "" : data.toWord);
                mTv_readLang.setText(TransUtils.switchCodeAndWord(data.toCode));
            }
        });
    }

    @Override
    public void afterTranslate(TranslateBean transInfo) {
        onRefresh(transInfo);
    }

    @Override
    public void beforeTranslate(TranslateBean transInfo) {
    }

    //点击事件侦听，复制
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_copy_result://复制
                TransUtils.copyText(mTv_result.getText().toString());
                Toast.makeText(UIUtils.getContext(), UIUtils.getString(R.string.copy_success), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
