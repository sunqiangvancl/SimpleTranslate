package xyz.mrseng.fasttranslate.global;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import xyz.mrseng.fasttranslate.utils.UIUtils;

/**
 * Created by MrSeng on 2016/12/21.
 * 声音管理类
 */

public class VoiceManager {
    //饿汉单例
    private static VoiceManager mManger = new VoiceManager();

    public static VoiceManager getNewInstance() {
        return mManger;
    }

    private VoiceManager() {
    }

    private SpeechRecognizer mIat;
    private SpeechSynthesizer mTts;

    {
        /*语音听写*/
        //1.创建SpeechRecognizer对象，第二个参数：本地听写时传InitListener
        mIat = SpeechRecognizer.createRecognizer(UIUtils.getContext(), null);
        //2.设置听写参数，详见《科大讯飞MSC API手册(Android)》SpeechConstant类
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");


        /*语音合成*/
        //1.创建SpeechSynthesizer对象, 第二个参数：本地合成时传InitListener
        mTts = SpeechSynthesizer.createSynthesizer(UIUtils.getContext(), null);
        //2.合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
        //设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
        //保存在SD卡需要在AndroidManifest.xml添加写SD卡权限
        //如果不需要保存合成音频，注释该行代码
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");
    }


    public synchronized void readText(String text) {
        //3.开始合成
        mTts.startSpeaking(text, mSynListener);
    }

    public synchronized void startListen() {
        mIat.startListening(recognizerListener);
    }

    public synchronized void stopListen() {
        mIat.stopListening();
    }

    //合成监听器
    private SynthesizerListener mSynListener = new SynthesizerListener() {
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {
        }

        //缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
        }

        //开始播放
        public void onSpeakBegin() {
        }

        //暂停播放
        public void onSpeakPaused() {
        }

        //播放进度回调
        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }

        //恢复播放回调接口
        public void onSpeakResumed() {
        }

        //会话事件回调接口
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
        }

    };
    private RecognizerListener recognizerListener = new RecognizerListener() {
        @Override
        public void onVolumeChanged(int i, byte[] bytes) {
        }

        @Override
        public void onBeginOfSpeech() {
            System.out.println("开始录音");
            if (mObserver != null) {
                mObserver.onBegin();
            }
        }

        @Override
        public void onEndOfSpeech() {
            System.out.println("结束录音");
        }

        StringBuilder sb = new StringBuilder();

        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            String str = parseResult(recognizerResult);
            if (!b && !TextUtils.isEmpty(str)) {
                sb.append(str);
            } else {
                sb = new StringBuilder();
            }
            if (mObserver != null) {
                mObserver.onResult(sb.toString(), b);
            }
        }

        @Override
        public void onError(SpeechError speechError) {
            Log.e("科大讯飞出错了", speechError.getErrorDescription());
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {
            System.out.println("onEvent");
        }
    };

    private static String parseResult(RecognizerResult recognizerResult) {
        StringBuilder sb = new StringBuilder();
        try {
            JSONObject jo = new JSONObject(recognizerResult.getResultString());
            JSONArray wsArr = jo.getJSONArray("ws");
            for (int i = 0; i < wsArr.length(); i++) {
                JSONObject jWord = wsArr.getJSONObject(i);
                JSONArray cwArr = jWord.getJSONArray("cw");
                for (int j = 0; j < cwArr.length(); j++) {
                    JSONObject jw = cwArr.getJSONObject(j);
                    String word = jw.getString("w");
                    sb.append(word);
                }
            }
            return sb.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setSpeakLang(String lang) {
        mIat.setParameter(SpeechConstant.LANGUAGE, lang);
    }

    private VoiceObserver mObserver;

    public synchronized void setObserver(VoiceObserver observer) {
        mObserver = observer;
    }

    public interface VoiceObserver {
        void onBegin();

        void onResult(String text, boolean isLast);
    }

    public static final String ZH_CN = "zh_cn";
    public static final String EN_US = "en_us";

}
