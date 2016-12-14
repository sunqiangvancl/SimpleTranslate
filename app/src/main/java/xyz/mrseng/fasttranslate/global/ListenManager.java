package xyz.mrseng.fasttranslate.global;

/**
 * Created by MrSeng on 2016/12/14.
 * 科大讯飞相关接口管理类
 */

public class ListenManager {
//    //单例
//    private static ListenManager mManager;
//    private final SpeechRecognizer mIat;
//    private final MyVoiceListener mVoiceListener;
//
//    public static ListenManager getNewInstance() {
//        if (mManager == null) {
//            synchronized (ListenManager.class) {
//                mManager = new ListenManager();
//            }
//        }
//        return mManager;
//    }
//
//    private ListenManager() {
//        //本地听写时传InitListener
//        mIat = SpeechRecognizer.createRecognizer(UIUtils.getContext(), null);
//        mVoiceListener = new MyVoiceListener();
//    }
//
//
//    public void read(SpeechBean speech) {
//        mIat.setParameter(SpeechConstant.DOMAIN, speech.sdomain);
//        mIat.setParameter(SpeechConstant.LANGUAGE, speech.slanguage);
//        mIat.setParameter(SpeechConstant.ACCENT, speech.saccent);
//    }
//
//    public String startListener() {
//        mIat.startListening(mVoiceListener);
//        //1.创建SpeechRecognizer对象，第二个参数：本地听写时传InitListener
//        RecognizerDialog iatDialog = new RecognizerDialog(UIUtils.getContext(), null);
//        //2.设置听写参数，同上节
//        //3.设置回调接口
//        iatDialog.setListener(null);
//        //4.开始听写
//        iatDialog.show();
//        return null;
//    }
//
//    //观察者模式
//    private ArrayList<VoiceListener> listenerList = new ArrayList<>();
//
//    //添加侦听
//    public synchronized void addListener(VoiceListener listener) {
//        if (listener != null && !listenerList.contains(listener)) {
//            listenerList.add(listener);
//        }
//    }
//
//    //移除侦听
//    public synchronized void removeListener(VoiceListener listener) {
//        if (listener != null && listenerList.contains(listener)) {
//            listenerList.remove(listener);
//        }
//    }
//
//    public interface VoiceListener {
//
//    }
//
//    class MyVoiceListener implements RecognizerListener {
//        //音量值0~30
//        @Override
//        public void onVolumeChanged(int i, byte[] bytes) {
//            System.out.println("onVolumeChanged:" + i);
//        }
//
//        //开始录音
//        @Override
//        public void onBeginOfSpeech() {
//            System.out.println("---onBeginOfSpeech");
//        }
//
//        //结束录音
//        @Override
//        public void onEndOfSpeech() {
//            System.out.println("---onEndOfSpeech");
//        }
//
//        /*
//         * 听写结果回调接口(返回Json格式结果，用户可参见附录12.1)；
//         * 一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加；
//         * 关于解析Json的代码可参见MscDemo中JsonParser类；
//         * isLast等于true时会话结束。
//         */
//        @Override
//        public void onResult(RecognizerResult recognizerResult, boolean isLast) {
//            System.out.println("onResult");
//            System.out.println("isLast" + isLast);
//            System.out.println("recognizerResult" + recognizerResult);
//        }
//
//        ////会话发生错误回调接口
//        @Override
//        public void onError(SpeechError speechError) {
//            System.out.println("on_Error");
//        }
//
//        //扩展用接口
//        @Override
//        public void onEvent(int i, int i1, int i2, Bundle bundle) {
//
//        }
//    }

}
