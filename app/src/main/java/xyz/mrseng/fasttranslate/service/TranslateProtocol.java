package xyz.mrseng.fasttranslate.service;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import xyz.mrseng.fasttranslate.domain.ResultBean;

/**
 * Created by MrSeng on 2016/12/13.
 */

public class TranslateProtocol {

    //单例
    private static TranslateProtocol mProtocol;

    public static TranslateProtocol getNewInstance() {
        if (mProtocol == null) {
            synchronized (TranslateProtocol.class) {
                if (mProtocol == null) {
                    mProtocol = new TranslateProtocol();
                }
            }
        }
        return mProtocol;
    }

    private TranslateProtocol() {
    }

    private TransApi mService = TransApi.getNewInstance();

    public ResultBean getData(String text, String from, String to) {
        String result = mService.getTransResult(text, from, to);
        if (!TextUtils.isEmpty(result)) {
            return parseData(result);
        }
        return null;
    }


    private ResultBean parseData(String result) {
        if (TextUtils.isEmpty(result)) return null;
        try {
            JSONObject jo = new JSONObject(result);
            ResultBean bean = new ResultBean();
            bean.from = jo.getString("from");
            bean.to = jo.getString("to");
            //trans_result
            JSONArray result_arr = jo.getJSONArray("trans_result");
            ArrayList<ResultBean.TransResult> resultList = new ArrayList<>();
            for (int i = 0; i < result_arr.length(); i++) {
                JSONObject json_result = result_arr.getJSONObject(i);
                ResultBean.TransResult transResult = new ResultBean.TransResult();
                transResult.dst = json_result.getString("dst");
                transResult.src = json_result.getString("src");
                resultList.add(transResult);
            }
            bean.trans_result = resultList;
            return bean;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
