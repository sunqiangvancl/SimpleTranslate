package xyz.mrseng.fasttranslate.service;

import java.util.ArrayList;

import xyz.mrseng.fasttranslate.domain.TranslateBean;
import xyz.mrseng.fasttranslate.global.ThreadManager;
import xyz.mrseng.fasttranslate.service.protocol.BDTransProtocol;

/**
 * Created by MrSeng on 2016/12/13.
 * 翻译的api，
 */

public class TransService {

    //百度协议
    private BDTransProtocol mBdProtocol = BDTransProtocol.getNewInstance();
    //单例
    private static TransService mService = new TransService();

    public static TransService getNewInstance() {
        return mService;
    }

    private TransService() {
    }

    private TranslateBean mTransBean = new TranslateBean();

    public void setLanguage(String fromCode, String toCode) {
        mTransBean.toCode = toCode;
        mTransBean.fromCode = fromCode;
    }

    public void setText(String text) {
        this.mTransBean.fromWord = text;
    }

    public void doTranslate() {
        ThreadManager.executeOnSingleThread(new Runnable() {
            @Override
            public void run() {
                notifyBeforeTranslate(mTransBean);
                TranslateBean data = mBdProtocol.getData(mTransBean.fromWord, mTransBean.fromCode, mTransBean.toCode);
                if (data != null) {
                    mTransBean = data;
                }else{
                    mTransBean.toWord = null;
                }
                notifyAfterTranslate(mTransBean);

            }
        });
    }

    private void notifyBeforeTranslate(TranslateBean mTransBean) {
        for (int i = 0; i < mListenerList.size(); i++) {
            mListenerList.get(i).beforeTranslate(mTransBean);
        }
    }

    private void notifyAfterTranslate(TranslateBean mTransBean) {
        for (int i = 0; i < mListenerList.size(); i++) {
            mListenerList.get(i).afterTranslate(mTransBean);
        }
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
    public void addTranslateListener(TranslateListener listener) {
        if (listener != null && !mListenerList.contains(listener)) {
            mListenerList.add(listener);
        }
    }

    public abstract interface TranslateListener {
        void afterTranslate(TranslateBean transInfo);


        void beforeTranslate(TranslateBean transInfo);
    }

}
