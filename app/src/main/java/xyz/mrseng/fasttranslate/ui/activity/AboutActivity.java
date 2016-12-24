package xyz.mrseng.fasttranslate.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.domain.AboutItemBean;
import xyz.mrseng.fasttranslate.global.Canstant;
import xyz.mrseng.fasttranslate.ui.base.BaseHolder;
import xyz.mrseng.fasttranslate.ui.base.BaseMenuActivity;
import xyz.mrseng.fasttranslate.ui.base.MyBaseAdapter;
import xyz.mrseng.fasttranslate.ui.holder.PayToMeHolder;
import xyz.mrseng.fasttranslate.utils.CommonUtils;
import xyz.mrseng.fasttranslate.utils.UIUtils;

public class AboutActivity extends BaseMenuActivity {

    private ArrayList<AboutItemBean> items;

    @Override
    protected View getView() {
        View view = UIUtils.inflate(R.layout.activity_about);
        ListView listView = (ListView) view.findViewById(R.id.lv_about_center);
        TextView tv_version = (TextView) view.findViewById(R.id.tv_version_about);
        tv_version.setText(UIUtils.getString(R.string.curr_version) + CommonUtils.getCurrVersion());
        initListView(listView);
        return view;
    }


    private void initListView(ListView listView) {
        initData();
        //设置适配器
        listView.setAdapter(new MyBaseAdapter<AboutItemBean>(items) {
            @Override
            public BaseHolder<AboutItemBean> getHolder() {
                return new BaseHolder<AboutItemBean>(AboutActivity.this) {
                    private TextView tv_item;

                    @Override
                    public View initView() {
                        View view = UIUtils.inflate(R.layout.item_list_about);
                        tv_item = (TextView) view.findViewById(R.id.tv_about_item);
                        return view;
                    }

                    @Override
                    public void onRefresh(AboutItemBean data) {
                        tv_item.setText(data.text);
                    }
                };
            }
        });
        //设置侦听
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (items.get(position).text.equals(UIUtils.getString(R.string.about_source))) {//查看源代码
                    //跳转到GitHub
                    Uri webPage = Uri.parse(UIUtils.getString(R.string.github_url));
                    Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
                    boolean isIntentSafe = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0;
                    if (isIntentSafe) {
                        startActivity(intent);
                    }
                } else if (items.get(position).text.equals(UIUtils.getString(R.string.about_give))) {//捐助
                    //弹出支付宝微信支付
                    showPayToMeDialog();
                } else if (items.get(position).text.equals(UIUtils.getString(R.string.about_author))) {//关于作者
                    //开启网页，引导关注
                    Uri weibo = Uri.parse(UIUtils.getString(R.string.url_weibo_about_me));
                    Intent intent = new Intent(Intent.ACTION_VIEW, weibo);
                    boolean isIntentSafe = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0;
                    if (isIntentSafe) {
                        startActivity(intent);
                    }
                } else if (items.get(position).text.equals(UIUtils.getString(R.string.about_new_version))) {//更新版本
                    showAlreadNewDialog();
                }
                // TODO: 2016/12/20 帮助与反馈的点击事件处理
            }
        });
    }

    /*已经是最新版本的dialog*/
    private void showAlreadNewDialog() {
       new  AlertDialog.Builder(this).setTitle(R.string.tips)
               .setMessage(R.string.alread_is_the_last_version)
               .setPositiveButton(R.string.ok,null)
               .show();
    }

    /*向我付款的dialog*/
    private void showPayToMeDialog() {
        PayToMeHolder holder = new PayToMeHolder(this);
        new AlertDialog.Builder(this).setView(holder.getRootView())
                .setTitle(R.string.thinks_for_pay)
                .show();
    }

    private void initData() {
        AboutItemBean feature = new AboutItemBean(UIUtils.getString(R.string.about_feature),
                UIUtils.getString(R.string.about_feature_url));
        AboutItemBean help = new AboutItemBean(UIUtils.getString(R.string.about_help),
                UIUtils.getString(R.string.about_help_url));
        AboutItemBean getnew = new AboutItemBean(UIUtils.getString(R.string.about_new_version),
                UIUtils.getString(R.string.about_new_url));
        AboutItemBean give = new AboutItemBean(UIUtils.getString(R.string.about_give), null);
        AboutItemBean conn = new AboutItemBean(UIUtils.getString(R.string.about_author), null);
        AboutItemBean src = new AboutItemBean(UIUtils.getString(R.string.about_source), UIUtils.getString(R.string.github_url));
        items = new ArrayList<>();
        items.add(help);
        items.add(getnew);
        items.add(give);
        items.add(conn);
        items.add(src);
    }

    @Override
    protected String getTitleStr() {
        return UIUtils.getString(R.string.menu_about);
    }
}
