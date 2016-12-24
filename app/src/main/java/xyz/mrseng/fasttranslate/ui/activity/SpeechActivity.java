package xyz.mrseng.fasttranslate.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.domain.TransBean;
import xyz.mrseng.fasttranslate.global.Canstant;
import xyz.mrseng.fasttranslate.global.VoiceManager;
import xyz.mrseng.fasttranslate.global.VoiceManager.VoiceObserver;
import xyz.mrseng.fasttranslate.engine.TransService;
import xyz.mrseng.fasttranslate.ui.base.BaseMenuActivity;
import xyz.mrseng.fasttranslate.utils.SPUtils;
import xyz.mrseng.fasttranslate.utils.UIUtils;

public class SpeechActivity extends BaseMenuActivity implements View.OnClickListener, VoiceObserver, TransService.TranslateListener {

    private TextView mTv_zh;
    private TextView mTv_en;
    private ImageView mIv_speech;

    private static final int STATE_UNDO = 0;
    private static final int STATE_ZH = 1;
    private static final int STATE_EN = 2;

    private int currState = STATE_UNDO;
    private TransBean mTransInfo;
    private VoiceManager mVoiceManager;
    private TextView mTv_top;
    private TextView mTv_bottom;
    private TransService mTranService;
    private ImageView mIv_bg1;
    private ImageView mIv_audio_bottom;

    @Override
    protected View getView() {
        View view = View.inflate(this, R.layout.activity_speech, null);

        /*初始化科大讯飞语音配置对象*/
        mVoiceManager = VoiceManager.getNewInstance();
        mVoiceManager.setObserver(this);

        mTranService = TransService.getNewInstance(TransService.FLAG_VOICE);
        mTranService.addTransListener(this);
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
        mTv_top = (TextView) view.findViewById(R.id.tv_text_top_speech);
        mTv_bottom = (TextView) view.findViewById(R.id.tv_text_bottom_speech);
        mIv_bg1 = (ImageView) view.findViewById(R.id.iv_bg_speech1);
        mIv_audio_bottom = (ImageView) view.findViewById(R.id.iv_audio_play_bottom_speech);
        mIv_audio_bottom.setVisibility(View.INVISIBLE);
        mIv_audio_bottom.setOnClickListener(this);
        initData();
        showRightUI();
    }

    private void initData() {
        mTransInfo = new TransBean();
        //中英翻译
        mTransInfo.fromCode = TransBean.getLangCodeStr(TransBean.LANG_CODE_ZH);
        mTransInfo.toCode = TransBean.getLangCodeStr(TransBean.LANG_CODE_EN);
        mTransInfo.token = TransBean.TOKEN_NET;
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
        if (mIv_speech.isActivated()) {
            mIv_bg1.startAnimation(getScaleAnim(500,1.5f));
            mIv_speech.startAnimation(getScaleAnim(250,1.03f));
            mTransInfo.fromWord="";
            mTransInfo.toWord="";
            mTv_top.setText("");
            mTv_bottom.setText("");
            mVoiceManager.setSpeakLang(currState == STATE_ZH ? VoiceManager.ZH_CN : VoiceManager.EN_US);
            mVoiceManager.startListen();
        } else {
            mVoiceManager.stopListen();
        }
        mTransInfo.fromCode = TransBean.getLangCodeStr(currState == STATE_EN ? TransBean.LANG_CODE_EN : TransBean.LANG_CODE_ZH);
        mTransInfo.toCode = TransBean.getLangCodeStr(currState == STATE_EN ? TransBean.LANG_CODE_ZH : TransBean.LANG_CODE_EN);

        /*是否显示朗读图标*/
        mIv_audio_bottom.setVisibility(TextUtils.isEmpty(mTransInfo.toWord) ? View.INVISIBLE : View.VISIBLE);
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
            case R.id.iv_audio_play_bottom_speech://发音
                mVoiceManager.readText(mTransInfo.toWord);
                break;
        }
        showRightUI();
    }


    private ScaleAnimation getScaleAnim(int dur, float scale) {
        ScaleAnimation scaleAnim;
        scaleAnim = new ScaleAnimation(1, scale, 1, scale,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnim.setDuration(dur);
        scaleAnim.setRepeatMode(Animation.REVERSE);
        scaleAnim.setRepeatCount(Integer.MAX_VALUE);
        scaleAnim.setFillAfter(false);
        scaleAnim.setAnimationListener(new Animation.AnimationListener() {
            private int cnt = 0;

            @Override
            public void onAnimationStart(Animation animation) {
                cnt = 0;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                cnt++;
                //避免可能在放大的时候动画结束，导致UI显示错误
                if (cnt % 2 == 0 && currState == STATE_UNDO) {
                    animation.cancel();
                }
            }
        });
        return scaleAnim;
    }

    @Override
    public void onBegin() {
    }

    @Override
    public void onResult(String text, boolean isLast) {
        text = mTv_top.getText() + text;
        mTv_top.setText(text);
        mTransInfo.fromWord = text;
        if (isLast) {
            currState = STATE_UNDO;
            mTranService.doTranslate(mTransInfo);
            showRightUI();
        }
    }

    @Override
    public void afterTrans(TransBean transInfo) {
        mTransInfo.toWord = transInfo.toWord;
        mTv_bottom.setText(transInfo.toWord != null ? transInfo.toWord : "");
        if (SPUtils.getBoolean(SPUtils.KEY_AUTO_READ,false)){
            mVoiceManager.readText(mTv_bottom.getText().toString());
        }
        showRightUI();
    }
    @Override
    public void beforeTrans(TransBean transInfo) {

    }
}
