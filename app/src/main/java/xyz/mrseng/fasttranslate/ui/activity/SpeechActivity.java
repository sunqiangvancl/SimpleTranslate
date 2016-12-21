package xyz.mrseng.fasttranslate.ui.activity;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.domain.TransBean;
import xyz.mrseng.fasttranslate.ui.base.BaseMenuActivity;
import xyz.mrseng.fasttranslate.utils.UIUtils;

public class SpeechActivity extends BaseMenuActivity implements View.OnClickListener {

    private TextView mTv_zh;
    private TextView mTv_en;
    private ImageView mIv_speech;

    private static final int STATE_UNDO = 0;
    private static final int STATE_ZH = 1;
    private static final int STATE_EN = 2;

    private int currState = STATE_UNDO;
    private TransBean mTransInfo;
    private ScaleAnimation scaleAnim;

    @Override
    protected View getView() {
        View view = View.inflate(this, R.layout.activity_speech_activvity, null);
        initWidget(view);
        return view;
    }

    private void initWidget(View view) {
        mTv_zh = (TextView) view.findViewById(R.id.tv_from_speech);
        mTv_en = (TextView) view.findViewById(R.id.tv_to_speech);
        mIv_speech = (ImageView) view.findViewById(R.id.iv_speech);
        mIv_speech.setAlpha(UIUtils.getInteger(R.integer.alpha_menu_icon));
        mTv_zh.setOnClickListener(this);
        mTv_en.setOnClickListener(this);
        mIv_speech.setOnClickListener(this);
        initAnim();
        initData();
        showRightUI();
    }

    private void initData() {
        mTransInfo = new TransBean();
        //中英翻译
        mTransInfo.fromCode = TransBean.getLangCodeStr(TransBean.LANG_CODE_ZH);
        mTransInfo.toCode = TransBean.getLangCodeStr(TransBean.LANG_CODE_EN);
        mTv_zh.setText(TransBean.switchCodeAndWord(mTransInfo.fromCode));
        mTv_en.setText(TransBean.switchCodeAndWord(mTransInfo.toCode));
    }


    @Override
    protected String getTitleStr() {
        return UIUtils.getString(R.string.speech);
    }

    private void showRightUI() {
        mTv_zh.setActivated(currState == STATE_ZH);
        mTv_en.setActivated(currState == STATE_EN);
        mIv_speech.setActivated(currState == STATE_ZH || currState == STATE_EN);
        if (mIv_speech.isActivated()){
            mIv_speech.startAnimation(scaleAnim);
            listenSpeech();
        }
        mTransInfo.fromCode = TransBean.getLangCodeStr(currState == STATE_EN ? TransBean.LANG_CODE_EN : TransBean.LANG_CODE_ZH);
        mTransInfo.toCode = TransBean.getLangCodeStr(currState == STATE_EN ? TransBean.LANG_CODE_ZH : TransBean.LANG_CODE_EN);
    }


    //监听语音
    private void listenSpeech() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_speech://讲话按钮
                currState = currState == STATE_UNDO ? STATE_ZH : STATE_UNDO;
                break;
            case R.id.tv_from_speech://左侧文本框
                currState = currState == STATE_ZH ? STATE_UNDO : STATE_ZH;
                break;
            case R.id.tv_to_speech://右侧文本框
                currState = currState == STATE_EN ? STATE_UNDO : STATE_EN;
                break;
        }
        showRightUI();
    }




    private void initAnim() {
        scaleAnim = new ScaleAnimation(1, 1.2f, 1, 1.2f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnim.setDuration(350);
        scaleAnim.setRepeatMode(Animation.REVERSE);
        scaleAnim.setRepeatCount(Integer.MAX_VALUE);
        scaleAnim.setFillAfter(false);
        scaleAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                if (currState == STATE_UNDO){
                    animation.cancel();
                }
            }
        });
    }
}
