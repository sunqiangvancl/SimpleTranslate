package xyz.mrseng.fasttranslate.ui.activity.menu;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.domain.AboutItemBean;
import xyz.mrseng.fasttranslate.ui.activity.base.BaseMenuActivity;
import xyz.mrseng.fasttranslate.ui.adapter.MyBaseAdapter;
import xyz.mrseng.fasttranslate.ui.holder.BaseHolder;
import xyz.mrseng.fasttranslate.utils.UIUtils;

public class AboutActivity extends BaseMenuActivity {

    private ArrayList<AboutItemBean> items;

    @Override
    protected View getView() {
        View view = UIUtils.inflate(R.layout.activity_about);
        ListView listView = (ListView) view.findViewById(R.id.lv_about_center);
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
        //TODO 给listview设置侦听，响应点击事件，分别跳转到相应的网页或者弹出对话框

    }

    private void initData() {

        AboutItemBean feature = new AboutItemBean(UIUtils.getString(R.string.about_feature),
                UIUtils.getString(R.string.about_feature_url));
        AboutItemBean help = new AboutItemBean(UIUtils.getString(R.string.about_help),
                UIUtils.getString(R.string.about_help_url));
        AboutItemBean getnew = new AboutItemBean(UIUtils.getString(R.string.about_new),
                UIUtils.getString(R.string.about_new_url));
        AboutItemBean give = new AboutItemBean(UIUtils.getString(R.string.about_give), null);
        AboutItemBean conn = new AboutItemBean(UIUtils.getString(R.string.about_follow), null);

        items = new ArrayList<>();
        items.add(feature);
        items.add(help);
        items.add(getnew);
        items.add(give);
        items.add(conn);
    }

    @Override
    protected String getTitleStr() {
        return UIUtils.getString(R.string.menu_about);
    }
}
