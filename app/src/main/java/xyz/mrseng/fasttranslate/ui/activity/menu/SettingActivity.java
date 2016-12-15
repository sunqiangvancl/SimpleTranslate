package xyz.mrseng.fasttranslate.ui.activity.menu;

import android.graphics.Color;
import android.view.View;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.domain.SettingItemBean;
import xyz.mrseng.fasttranslate.ui.activity.base.BaseMenuActivity;
import xyz.mrseng.fasttranslate.ui.adapter.MyBaseAdapter;
import xyz.mrseng.fasttranslate.ui.holder.BaseHolder;
import xyz.mrseng.fasttranslate.utils.SPUtils;
import xyz.mrseng.fasttranslate.utils.TransUtils;
import xyz.mrseng.fasttranslate.utils.UIUtils;

/**
 * 设置
 */
public class SettingActivity extends BaseMenuActivity {

    @Override
    protected View getView() {
        View view = UIUtils.inflate(R.layout.activity_setting);
        ListView lv = (ListView) view.findViewById(R.id.lv_setting);

        initListView(lv);
        return view;
    }

    private void initListView(ListView lv) {

        lv.setAdapter(new MyBaseAdapter<SettingItemBean>(initData()) {
            @Override
            public BaseHolder<SettingItemBean> getHolder() {
                return new BaseHolder<SettingItemBean>(SettingActivity.this) {

                    private Switch toggle;
                    private TextView tv_desc;
                    private TextView tv_title;

                    @Override
                    public View initView() {
                        View view = UIUtils.inflate(R.layout.item_list_setting);
                        tv_title = (TextView) view.findViewById(R.id.tv_title_setting_item);
                        tv_desc = (TextView) view.findViewById(R.id.tv_desc_setting_item);
                        toggle = (Switch) view.findViewById(R.id.tb_toggle);
                        return view;
                    }

                    @Override
                    public void onRefresh(SettingItemBean data) {
                        if (data.toggle == null) {
                            toggle.setVisibility(View.GONE);
                        } else {
                            toggle.setVisibility(View.VISIBLE);
                            toggle.setChecked(data.toggle);
                        }

                        if (data.title == null) {
                            tv_title.setVisibility(View.GONE);
                        } else {
                            tv_title.setVisibility(View.VISIBLE);
                            tv_title.setText(data.title);
                        }

                        if (data.desc == null) {
                            tv_desc.setVisibility(View.GONE);
                        } else {
                            tv_desc.setVisibility(View.VISIBLE);
                            tv_desc.setText(data.desc);
                        }
                    }
                };
            }
        });

    }

    /*    <string name="setting_enable_click_trans">点按翻译</string>
        <string name="setting_show_notice">显示通知</string>
        <string name="setting_first_lang">首选语言</string>
        <string name="setting_output_voice">自动朗读译文</string>
        <string name="setting_enable_trans_now">实时翻译</string>*/
    private ArrayList<SettingItemBean> initData() {
        ArrayList<SettingItemBean> items = new ArrayList<>();
        //点按翻译
        SettingItemBean click = new SettingItemBean(
                UIUtils.getString(R.string.setting_enable_click_trans), null,
                SPUtils.getBoolean(SPUtils.KEY_ENABLE_CLICK_TRANS, false));
        //显示通知
        SettingItemBean notice = new SettingItemBean(
                UIUtils.getString(R.string.setting_show_notice), null,
                SPUtils.getBoolean(SPUtils.KEY_SHOW_NOTICE, false));
        //首选语言
        String fromCode = SPUtils.getString(SPUtils.KEY_FIRST_FROM_CODE,
                UIUtils.getStringArray(R.array.lang_code)[0]);//默认自动检测
        String fromLang = TransUtils.switchCodeAndWord(fromCode);
        String toCode = SPUtils.getString(SPUtils.KEY_FIRST_TO_CODE,
                UIUtils.getStringArray(R.array.lang_code)[2]);//默认英语
        String toLang = TransUtils.switchCodeAndWord(toCode);
        String desc_lang = fromLang + "," + toLang;
        SettingItemBean language = new SettingItemBean(
                UIUtils.getString(R.string.setting_first_lang), desc_lang, null);

        //自动朗读
        SettingItemBean voice = new SettingItemBean(
                UIUtils.getString(R.string.setting_output_voice), null,
                SPUtils.getBoolean(SPUtils.KEY_AUTO_READ,false));

        //实时翻译
        SettingItemBean getnow = new SettingItemBean(
                UIUtils.getString(R.string.setting_enable_trans_now),null,
                SPUtils.getBoolean(SPUtils.KEY_TRANS_NOW,false));

        items.add(click);
        items.add(notice);
        items.add(language);
        items.add(voice);
        items.add(getnow);
        return items;
    }

    @Override
    protected String getTitleStr() {
        return UIUtils.getString(R.string.menu_setting);
    }
}
