package xyz.mrseng.fasttranslate.service;

import java.util.ArrayList;

import xyz.mrseng.fasttranslate.domain.LanguageBean;
import xyz.mrseng.fasttranslate.domain.ResultBean;

/**
 * Created by MrSeng on 2016/12/13.
 */

public class TransService {
    private TranslateProtocol mTransProto = TranslateProtocol.getNewInstance();
    private long lastReqTime = 0;
    //单例
    private static TransService mService;

    public static TransService getNewInstance() {
        if (mService == null) {
            synchronized (TransService.class) {
                if (mService == null) {
                    mService = new TransService();
                }
            }
        }
        return mService;
    }

    private TransService() {
    }

    private String text;
    private LanguageBean lanBean;

    public String getText() {
        return text;
    }

    public LanguageBean getLanBean() {
        return lanBean;
    }

    public void setLanBean(LanguageBean lanBean) {
        this.lanBean = lanBean;
    }

    public void setText(String text) {
        this.text = text;
    }


    public ResultBean doTranslate() {
        notifyBeforeTranslate();
        ResultBean data = doTranslate(text, lanBean.fromCode, lanBean.toCode);
        notifyAfterTranslate(data);
        return data;
    }

    private ResultBean doTranslate(final String text, final String mFromCode, final String mToCode) {
//        ResultBean data = null;
//        long thisReqTime = System.currentTimeMillis();
//        long time = thisReqTime - lastReqTime;
//        if (111111 > 1000) {
//            data = mTransProto.getData(text, mFromCode, mToCode);
//        }
//        lastReqTime = thisReqTime;
        return mTransProto.getData(text, mFromCode, mToCode);
    }


    private void notifyBeforeTranslate() {
        for (int i = 0; i < mListenerList.size(); i++) {
            mListenerList.get(i).beforeTranslate();
        }
    }

    private void notifyAfterTranslate(ResultBean data) {
        for (int i = 0; i < mListenerList.size(); i++) {
            mListenerList.get(i).afterTranslate(data);
        }
    }

    private ArrayList<TranslateListener> mListenerList = new ArrayList<>();

    public void logoutTranslateListener(TranslateListener listener) {
        if (listener != null && mListenerList.contains(listener)) {
            mListenerList.remove(listener);
        }
    }

    public void registTranslateListener(TranslateListener listener) {
        if (listener != null && !mListenerList.contains(listener)) {
            mListenerList.add(listener);
        }
    }


    public interface TranslateListener {
        void afterTranslate(ResultBean data);

        void beforeTranslate();
    }

}
