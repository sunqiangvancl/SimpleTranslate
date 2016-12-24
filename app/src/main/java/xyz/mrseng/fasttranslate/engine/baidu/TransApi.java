package xyz.mrseng.fasttranslate.engine.baidu;

import java.util.HashMap;
import java.util.Map;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.utils.UIUtils;

public class TransApi {
    private static final String TRANS_API_HOST = "http://api.fanyi.baidu.com/api/trans/vip/translate";
    private String securityKey;
    private String appid;

    //单例
    private static TransApi mApi;

    private TransApi(String appid, String securityKey) {
        this.appid = appid;
        this.securityKey = securityKey;
    }

    public static TransApi getNewInstance() {
        if (mApi == null) {
            synchronized (TransApi.class) {
                if (mApi == null) {
                    mApi = new TransApi(UIUtils.getString(R.string.app_id_baidu), UIUtils.getString(R.string.securityKey));
                }
            }
        }
        return mApi;
    }


    public String getTransResult(String query, String from, String to) {
        Map<String, String> params = buildParams(query, from, to);
        return HttpGet.get(TRANS_API_HOST, params);
    }

    private Map<String, String> buildParams(String query, String from, String to) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);

        params.put("appid", appid);

        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);

        // 签名
        String src = appid + query + salt + securityKey; // 加密前的原文
        params.put("sign", MD5.md5(src));

        return params;
    }

}
