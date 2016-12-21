package xyz.mrseng.fasttranslate.ui.holder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.domain.TransBean;
import xyz.mrseng.fasttranslate.global.Canstant;
import xyz.mrseng.fasttranslate.service.TransService;
import xyz.mrseng.fasttranslate.ui.activity.HomeActivity;
import xyz.mrseng.fasttranslate.ui.activity.SpeechActivity;
import xyz.mrseng.fasttranslate.ui.base.BaseHolder;
import xyz.mrseng.fasttranslate.utils.SPUtils;
import xyz.mrseng.fasttranslate.utils.UIUtils;

/**
 * Created by MrSeng on 2016/12/13.
 */

public class InputHolder extends BaseHolder<String> implements TransService.TranslateListener {
    private ImageView mIv_voice;
    private EditText mEt_home;
    private HomeActivity homeActivity;
    private Switch mSw_now;

    public InputHolder(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        homeActivity = (HomeActivity) getActivity();
        View view = UIUtils.inflate(R.layout.card_input_home);
        mEt_home = (EditText) view.findViewById(R.id.et_trans_home);
        mEt_home.addTextChangedListener(new MyTextChangeListener());
        mSw_now = (Switch) view.findViewById(R.id.switch_trans_now);
        homeActivity.getTransService().addTransListener(this);
        mIv_voice = (ImageView) view.findViewById(R.id.iv_camera_voice);
        return view;
    }

    private void initListener() {
        mSw_now.setChecked(SPUtils.getBoolean(SPUtils.KEY_TRANS_NOW, false));
        mSw_now.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SPUtils.setBoolean(SPUtils.KEY_TRANS_NOW, isChecked);
                if (isChecked){//刚打开实时翻译，做一次翻译
                    setData(mEt_home.getText().toString());
                }
                Toast.makeText(homeActivity, UIUtils.getString(isChecked ? R.string.trans_now_opened : R.string.trans_now_closed), Toast.LENGTH_SHORT).show();
            }
        });
        mIv_voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转至语音翻译界面
                Intent intent = new Intent(getActivity(), SpeechActivity.class);
                //当前科大讯飞平台仅支持中英文，所以没必要搞多语言适配
//                Bundle bundle = new Bundle();
//                bundle.putSerializable(Canstant.EXTRA_SPEECH,homeActivity.getTransInfo());
//                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });
    }

    @Override
    public void onRefresh(String text) {
        homeActivity.getTransInfo().fromWord = text;
        homeActivity.notifyTransInfoChanged();
    }

    @Override
    public void afterTrans(TransBean transInfo) {
        if (transInfo.token == Canstant.TOKEN_LOCAL) {//本地翻译，需要被动配合翻译
            mEt_home.setText(transInfo.fromWord);
        }
    }

    @Override
    public void beforeTrans(TransBean transInfo) {
    }

    public void onActivityResume() {
        initListener();
    }

    private class MyTextChangeListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().endsWith("\n")) {
                mEt_home.setText(s.toString().replace("\n", ""));
                mEt_home.setSelection(s.toString().length() - 1);//光标移至末尾
            }

            //开启了实时翻译或者按下enter才执行翻译
            if (s.toString().endsWith("\n") || TextUtils.isEmpty(s.toString()) || SPUtils.getBoolean(SPUtils.KEY_TRANS_NOW, false)) {
                //网络翻译，在文本改变的时候更新翻译，如果内容为空，也做一次翻译，这样注册翻译监听的类可以做UI更新
                if (homeActivity.getTransInfo().token == Canstant.TOKEN_NET ) {
                    setData(s.toString());
                }
            }
        }

    }
    public EditText getEt_Input() {
        return mEt_home;
    }
}
