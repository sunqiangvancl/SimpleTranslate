package xyz.mrseng.fasttranslate.ui.holder;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.domain.LeftMenuBean;
import xyz.mrseng.fasttranslate.ui.activity.HomeActivity;
import xyz.mrseng.fasttranslate.ui.activity.AboutActivity;
import xyz.mrseng.fasttranslate.ui.activity.MarkedActivity;
import xyz.mrseng.fasttranslate.ui.activity.MsgActivity;
import xyz.mrseng.fasttranslate.ui.activity.SettingActivity;
import xyz.mrseng.fasttranslate.ui.base.BaseHolder;
import xyz.mrseng.fasttranslate.ui.base.MyBaseAdapter;
import xyz.mrseng.fasttranslate.utils.UIUtils;

/**
 * Created by MrSeng on 2016/12/15.
 */

public class LeftCardHolder extends BaseHolder<ArrayList<LeftMenuBean>> {

    private ListView mLv_menu;

    public LeftCardHolder(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.left_menu);
        initListView(view);
        initData();
        return view;

    }

    private void initListView(View view) {
        mLv_menu = (ListView) view.findViewById(R.id.lv_menu_left);
        mLv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HomeActivity homeActivity = (HomeActivity) getActivity();
                homeActivity.closeDrawer();
                if (position > 0) {
                    Intent intent = new Intent(getActivity(), getData().get(position).activity);
                    getActivity().startActivity(intent);
                }
            }
        });
    }

    private void initData() {

        //首页,不用设置activity
        LeftMenuBean home = new LeftMenuBean(0, UIUtils.getString(R.string.menu_home),
                UIUtils.getDrawable(R.drawable.ic_home_blue_24), null);

        //好词好句
        LeftMenuBean mark = new LeftMenuBean(1, UIUtils.getString(R.string.menu_mark),
                UIUtils.getDrawable(R.drawable.ic_stars_black_24), MarkedActivity.class);

        //短信翻译
        LeftMenuBean msg = new LeftMenuBean(2, UIUtils.getString(R.string.menu_msg_trans),
                UIUtils.getDrawable(R.drawable.ic_textsms_black_24), MsgActivity.class);

        //设置
        LeftMenuBean setting = new LeftMenuBean(3, UIUtils.getString(R.string.menu_setting),
                UIUtils.getDrawable(R.drawable.ic_settings_black_24), SettingActivity.class);

        //帮助
        LeftMenuBean about = new LeftMenuBean(4, UIUtils.getString(R.string.menu_about),
                UIUtils.getDrawable(R.drawable.ic_help_black_24), AboutActivity.class);

        //
        ArrayList<LeftMenuBean> menuList = new ArrayList<>();
        menuList.add(home);
        menuList.add(mark);
        menuList.add(msg);
        menuList.add(setting);
        menuList.add(about);

        setData(menuList);
    }

    @Override
    public void onRefresh(ArrayList<LeftMenuBean> data) {
        mLv_menu.setAdapter(new MyBaseAdapter<LeftMenuBean>(data) {
            @Override
            public BaseHolder<LeftMenuBean> getHolder() {
                return new BaseHolder<LeftMenuBean>(getActivity()) {
                    private ImageView iv_icon;
                    private TextView tv_name;

                    @Override
                    public View initView() {
                        View view = UIUtils.inflate(R.layout.item_list_left_menu);
                        tv_name = (TextView) view.findViewById(R.id.tv_text_menu_left);
                        iv_icon = (ImageView) view.findViewById(R.id.iv_icon_menu_left);
                        return view;
                    }

                    @Override
                    public void onRefresh(LeftMenuBean data) {


                        //首页icon不用设置透明度
                        if (data.index > 0) {
                            iv_icon.setImageAlpha(UIUtils.getInteger(R.integer.alpha_menu_icon));
                        }else{
                            tv_name.setTextColor(UIUtils.getColor(R.color.colorPrimary));
                        }
                        iv_icon.setImageDrawable(data.icon);
                        tv_name.setText(data.text);
                    }
                };
            }
        });
    }

}
