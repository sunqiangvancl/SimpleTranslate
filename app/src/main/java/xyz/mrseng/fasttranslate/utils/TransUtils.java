package xyz.mrseng.fasttranslate.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import xyz.mrseng.fasttranslate.R;

/**
 * Created by MrSeng on 2016/12/12.
 */

public class TransUtils {

    private static ClipboardManager mClipManager;

    /** 给出Word，得到code，给出code得到Word */
    public static String switchCodeAndWord(Context context, String wordOrCode) {
        String[] words = context.getResources().getStringArray(R.array.lang_name);
        String[] codes = context.getResources().getStringArray(R.array.lang_code);
        if (words.length == codes.length) {
            for (int i = 0; i < words.length; i++) {
                if (words[i].equals(wordOrCode)) {
                    return codes[i];
                } else if (codes[i].equals(wordOrCode)) {
                    return words[i];
                }
            }
        }
        return null;
    }

    public static String switchCodeAndWord(String wordOrCode) {
        return switchCodeAndWord(UIUtils.getContext(), wordOrCode);
    }

    public static void copyText(String text) {
        if (mClipManager == null) {
            synchronized (TransUtils.class) {
                if (mClipManager == null) {
                    mClipManager = (ClipboardManager) UIUtils.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                }
            }
        }
        ClipData data = ClipData.newPlainText("simple text", text);
        mClipManager.setPrimaryClip(data);
    }
}
