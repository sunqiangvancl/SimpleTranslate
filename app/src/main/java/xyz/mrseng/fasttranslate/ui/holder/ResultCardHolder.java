package xyz.mrseng.fasttranslate.ui.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.domain.ResultBean;
import xyz.mrseng.fasttranslate.service.TransService;
import xyz.mrseng.fasttranslate.utils.TransUtils;
import xyz.mrseng.fasttranslate.utils.UIUtils;

/**
 * Created by MrSeng on 2016/12/13.
 * 结果卡片的逻辑
 */

public class ResultCardHolder extends BaseHolder<ResultBean> implements TransService.TranslateListener, View.OnClickListener {

    private TextView mTv_result;
    private TextView mTv_readLang;
    private ImageView mIv_copy;

    @Override
    public View initView() {

        View view = UIUtils.inflate(R.layout.card_result_trans);

        mTv_result = (TextView) view.findViewById(R.id.tv_result_card);
        mTv_readLang = (TextView) view.findViewById(R.id.tv_audio_lang);
        mIv_copy = (ImageView) view.findViewById(R.id.iv_copy_result);
        mIv_copy.setOnClickListener(this);
        mService.addTranslateListener(this);
        return view;
    }

    @Override
    public void onRefresh(final ResultBean data) {
        UIUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                mTv_result.setText(data == null ? "" : data.trans_result.get(0).dst);
                if (data != null) {
                    String lang = TransUtils.switchCodeAndWord(data.to);
                    mTv_readLang.setText(lang);
                }
            }
        });
    }

    @Override
    public void afterTranslate(ResultBean data) {
        onRefresh(data);
    }

    @Override
    public void beforeTranslate() {
    }

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
