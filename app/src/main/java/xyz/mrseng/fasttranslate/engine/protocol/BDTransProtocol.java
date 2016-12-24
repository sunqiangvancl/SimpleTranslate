package xyz.mrseng.fasttranslate.engine.protocol;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import xyz.mrseng.fasttranslate.domain.ResultToBean;
import xyz.mrseng.fasttranslate.domain.TranslateBean;
import xyz.mrseng.fasttranslate.engine.baidu.TransApi;

/**
 * Created by MrSeng on 2016/12/13.
 * 百度翻译协议
 */

public class BDTransProtocol extends BaseTransProtocol {

    //单例
    private static BDTransProtocol mProtocol;

    public static BDTransProtocol getNewInstance() {
        if (mProtocol == null) {
            synchronized (BDTransProtocol.class) {
                if (mProtocol == null) {
                    mProtocol = new BDTransProtocol();
                }
            }
        }
        return mProtocol;
    }

    private BDTransProtocol() {
    }

    private TransApi mService = TransApi.getNewInstance();

    public TranslateBean getData(String text, String from, String to)  {
        String result = mService.getTransResult(text, from, to);
        if (!TextUtils.isEmpty(result)) {
            ResultToBean bean = parseData(result);
            if (bean != null) {
                TranslateBean trans = new TranslateBean();
                trans.marked = 0;
                trans.fromCode = bean.from;
                trans.toCode = bean.to;
                trans.fromWord = bean.trans_result.get(0).src;
                trans.toWord = bean.trans_result.get(0).dst;
                trans.time = System.currentTimeMillis();
                return trans;
            }
        }
        return null;
    }


    private ResultToBean parseData(String result) {
        if (TextUtils.isEmpty(result)) return null;
        try {
            JSONObject jo = new JSONObject(result);
            ResultToBean bean = new ResultToBean();
            bean.from = jo.getString("from");
            bean.to = jo.getString("to");
            //trans_result
            JSONArray result_arr = jo.getJSONArray("trans_result");
            ArrayList<ResultToBean.TransResult> resultList = new ArrayList<>();
            for (int i = 0; i < result_arr.length(); i++) {
                JSONObject json_result = result_arr.getJSONObject(i);
                ResultToBean.TransResult transResult = new ResultToBean.TransResult();
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
