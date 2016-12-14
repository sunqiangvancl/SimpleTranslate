package xyz.mrseng.fasttranslate.ui.holder;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.global.ThreadManager;
import xyz.mrseng.fasttranslate.service.TransService;
import xyz.mrseng.fasttranslate.utils.UIUtils;

/**
 * Created by MrSeng on 2016/12/13.
 */

public class InputHolder extends BaseHolder<String> {
    private ImageView mIv_camera;
    private ImageView mIv_voice;
    private ImageView mIv_handwrite;
    private EditText mEt_home;
    private HomeHolder mHomeHolder;
    public void setHomeHolder(HomeHolder homeHolder){
        mHomeHolder = homeHolder;
    }
    @Override
    public View initView() {
        mService = TransService.getNewInstance();
        View view = UIUtils.inflate(R.layout.card_input_home);
        mEt_home = (EditText) view.findViewById(R.id.et_trans_home);
        mIv_camera = (ImageView) view.findViewById(R.id.iv_camera_home);
        mIv_voice = (ImageView) view.findViewById(R.id.iv_camera_voice);
        mIv_handwrite = (ImageView) view.findViewById(R.id.iv_hand_writing_home);
        mEt_home.addTextChangedListener(new MyTextChangeListener());
        mEt_home.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mHomeHolder!=null){
                    mHomeHolder.onEditTextTouch();
                }
                return false;
            }
        });
        return view;

    }

    @Override
    public void onRefresh(String data) {
        mService.setText(data);
        ThreadManager.execute(new Runnable() {
            @Override
            public void run() {
                mService.doTranslate();
            }
        });
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
            setData(s.toString());
        }
    }

    public String getInputText() {
        return mEt_home.getText().toString();
    }


}
