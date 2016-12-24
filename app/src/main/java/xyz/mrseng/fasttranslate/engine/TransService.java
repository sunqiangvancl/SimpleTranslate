package xyz.mrseng.fasttranslate.engine;

import android.text.TextUtils;

import java.util.ArrayList;

import xyz.mrseng.fasttranslate.domain.TransBean;
import xyz.mrseng.fasttranslate.engine.protocol.BDTransProtocol;
import xyz.mrseng.fasttranslate.global.ThreadManager;
import xyz.mrseng.fasttranslate.utils.UIUtils;

/**
 * Created by MrSeng on 2016/12/13.
 * 翻译的api，
 */

public class TransService {
    public static final int FLAG_HOME = 0;
    public static final int FLAG_VOICE = 1;
    public static final int FLAG_SIMPLE = 2;
    //百度协议
    private BDTransProtocol mBdProtocol = BDTransProtocol.getNewInstance();
    //单例
    private static TransService mHomeService = new TransService();
    private static TransService mVoiceService = new TransService();
    private static TransService mSimpleService = new TransService();


    public static TransService getNewInstance(int flag) {
        switch (flag) {
            case FLAG_HOME:
                return mHomeService;
            case FLAG_VOICE:
                return mVoiceService;
            case FLAG_SIMPLE:
                return mSimpleService;
        }
        return mHomeService;
    }

    private TransService() {
    }

    public void doTranslate(final TransBean transInfo) {
        ThreadManager.executeOnTransThread(new Runnable() {
            @Override
            public void run() {
                if (transInfo.fromWord == null) {
                    transInfo.toWord = null;
                    doTransByLocal(transInfo);
                    return;
                } else {
                    transInfo.fromWord = transInfo.fromWord.trim();
                    if (TextUtils.isEmpty(transInfo.fromWord)) {
                        transInfo.toWord = "";
                        doTransByLocal(transInfo);
                        return;
                    }
                }
                //需要从网络上翻译
                if (transInfo.token == TransBean.TOKEN_NET) {
                    doTransFromNet(transInfo);
                } else {//载入本地翻译数据
                    doTransByLocal(transInfo);
                }
            }
        });
    }

    private void doTransByLocal(TransBean transInfo) {
        notifyBeforeTranslate(transInfo);
        notifyAfterTranslate(transInfo);
    }


    private void doTransFromNet(TransBean transInfo) {
        notifyBeforeTranslate(transInfo);
        TransBean transResult = mBdProtocol.getData(transInfo.fromWord, transInfo.fromCode, transInfo.toCode);
        if (transResult != null) {
            transResult.token = TransBean.TOKEN_NET;
            notifyAfterTranslate(transResult);
        } else {
            transInfo.toWord = null;
            notifyAfterTranslate(transInfo);
        }
    }

    private void notifyBeforeTranslate(final TransBean mTransBean) {
        UIUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < mListenerList.size(); i++) {
                    mListenerList.get(i).beforeTrans(mTransBean);
                }
            }
        });
    }

    private void notifyAfterTranslate(final TransBean mTransBean) {
        UIUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < mListenerList.size(); i++) {
                    mListenerList.get(i).afterTrans(mTransBean);
                }
            }
        });
    }

    //监听队列
    private ArrayList<TranslateListener> mListenerList = new ArrayList<>();

    //移除监听
    public void removeTranslateListener(TranslateListener listener) {
        if (listener != null && mListenerList.contains(listener)) {
            mListenerList.remove(listener);
        }
    }

    //注册监听
    public void addTransListener(TranslateListener listener) {
        if (listener != null && !mListenerList.contains(listener)) {
            mListenerList.add(listener);
        }
    }

    public interface TranslateListener {
        void afterTrans(TransBean transInfo);

        void beforeTrans(TransBean transInfo);
    }

}
