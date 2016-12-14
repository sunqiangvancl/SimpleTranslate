package xyz.mrseng.fasttranslate.ui.holder;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import xyz.mrseng.fasttranslate.R;
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

    @Override
    public View initView() {
        mService = TransService.getNewInstance();
        View view = UIUtils.inflate(R.layout.card_input_home);

        mIv_camera = (ImageView) view.findViewById(R.id.iv_camera_home);
        mIv_voice = (ImageView) view.findViewById(R.id.iv_camera_voice);
        mIv_handwrite = (ImageView) view.findViewById(R.id.iv_hand_writing_home);

        mEt_home = (EditText) view.findViewById(R.id.et_trans_home);
//        mEt_home.setInputType(InputType.TYPE_NULL);//强制隐藏输入法
        //输入法管理器
//        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(mEt_home.getWindowToken(),0);
        mEt_home.clearFocus();//失去焦点

        mEt_home.addTextChangedListener(new MyTextChangeListener());
        return view;
    }

    @Override
    public void onRefresh(String data) {
        mService.setText(data);
        mService.doTranslate();
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
}
