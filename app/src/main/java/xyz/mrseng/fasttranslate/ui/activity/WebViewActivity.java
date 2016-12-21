package xyz.mrseng.fasttranslate.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.global.Canstant;
import xyz.mrseng.fasttranslate.ui.base.BaseMenuActivity;

public class WebViewActivity extends BaseMenuActivity {

    private String url;

    @Override
    protected View getView() {
        View view = View.inflate(this,R.layout.activity_web_view,null);
        url = getIntent().getStringExtra(Canstant.EXTRA_URL);
        if (!TextUtils.isEmpty(url)){
            WebView webView = (WebView) view.findViewById(R.id.web_view);
            webView.loadUrl(url);
        }else{
            finish();
        }
        return view;
    }

    @Override
    protected String getTitleStr() {
        return "";
    }


}
