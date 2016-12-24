package xyz.mrseng.fasttranslate.ui.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.domain.SettingItemBean;
import xyz.mrseng.fasttranslate.ui.base.BaseHolder;
import xyz.mrseng.fasttranslate.ui.base.BaseMenuActivity;
import xyz.mrseng.fasttranslate.ui.base.MyBaseAdapter;
import xyz.mrseng.fasttranslate.ui.holder.LangDialogHolder;
import xyz.mrseng.fasttranslate.utils.CommonUtils;
import xyz.mrseng.fasttranslate.utils.SPUtils;
import xyz.mrseng.fasttranslate.utils.ServiceUtils;
import xyz.mrseng.fasttranslate.utils.UIUtils;

/**
 * 设置
 */
public class SettingActivity extends BaseMenuActivity {

    private static final int CODE_REQ_FLOAT_PERM = 0;
    private ArrayList<SettingItemBean> mData;
    private SettingItemBean mItemClick;
    private SettingItemBean mItemNotice;
    private SettingItemBean mItemLang;
    private SettingItemBean mItemRead;
    private SettingItemBean mItemNow;
    private SettingItemBean mItemSite;
    private Map<Integer, SettingItemBean> mItemMap;
    private ListView mLv_settings;
    private ArrayList<String> mSiteData;

    @Override
    protected View getView() {
        View view = UIUtils.inflate(R.layout.activity_setting);
        mLv_settings = (ListView) view.findViewById(R.id.lv_setting);

        initListView(mLv_settings);
        return view;
    }

    private void initListView(ListView lv) {

        mData = initData();

        //设置adapter
        lv.setAdapter(new MyBaseAdapter<SettingItemBean>(mData) {
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
                        toggle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (getData().toggle != null) {
                                    getData().switchToggle();
                                    if (getData().item == SettingItemBean.ITEM_CLICK_TRANS) {
                                        clickTransClicked(toggle.isChecked());
                                    }
                                    onRefresh(getData());
                                }
                            }
                        });
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

        //设置侦听
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (mData.get(position).item) {
                    case SettingItemBean.ITEM_FIRST_LANG://首选语言
                        showLangDialog();
                        break;
                    case SettingItemBean.ITEM_FROM_SITE://网站
                        showSiteDialog();
                        break;
                }
            }
        });
    }

    /*选择网站对话框*/
    private void showSiteDialog() {
        if (mSiteData == null) {
            initSitData();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setAdapter(new MyBaseAdapter<String>(mSiteData) {
            @Override
            public BaseHolder<String> getHolder() {
                return new BaseHolder<String>(SettingActivity.this) {
                    private TextView tv;

                    @Override
                    public View initView() {
                        View view = UIUtils.inflate(R.layout.item_list_site);
                        tv = (TextView) view.findViewById(R.id.tv_site_list);
                        return view;
                    }

                    @Override
                    public void onRefresh(String data) {
                        tv.setText(data);
                    }
                };
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //更新数据
                SPUtils.setString(SPUtils.KEY_FROM_SITE, mSiteData.get(which));
                //更新UI
                mItemMap.get(SettingItemBean.ITEM_FROM_SITE).desc = mSiteData.get(which);
                ((MyBaseAdapter<SettingItemBean>) mLv_settings.getAdapter()).notifyDataSetChanged(mData = initData());
                Toast.makeText(SettingActivity.this, "已选择" + mSiteData.get(which), Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void initSitData() {
        mSiteData = new ArrayList<>();
        mSiteData.add(UIUtils.getString(R.string.baidu));
        mSiteData.add(UIUtils.getString(R.string.youdao));
        mSiteData.add(UIUtils.getString(R.string.biying));
        mSiteData.add(UIUtils.getString(R.string.jinshan));
    }

    /*选择语言对话框*/
    private void showLangDialog() {
        LangDialogHolder holder = new LangDialogHolder(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(holder.getRootView()).show();
    }

    private ArrayList<SettingItemBean> initData() {

        mItemClick = new SettingItemBean(this, SettingItemBean.ITEM_CLICK_TRANS);
        mItemNotice = new SettingItemBean(this, SettingItemBean.ITEM_SHOW_NOTICE);
        mItemLang = new SettingItemBean(this, SettingItemBean.ITEM_FIRST_LANG);
        mItemRead = new SettingItemBean(this, SettingItemBean.ITEM_AUTO_READ);
        mItemNow = new SettingItemBean(this, SettingItemBean.ITEM_TRANS_NOW);
        mItemSite = new SettingItemBean(this, SettingItemBean.ITEM_FROM_SITE);

        ArrayList<SettingItemBean> items = new ArrayList<>();
        items.add(mItemClick);
        items.add(mItemNotice);
        items.add(mItemLang);
        items.add(mItemRead);
        items.add(mItemNow);
        items.add(mItemSite);
        mItemMap = new HashMap<>();
        for (int i = 0; i < items.size(); i++) {
            mItemMap.put(items.get(i).item, items.get(i));
        }
        mItemMap.values();
        return items;
    }

    @Override
    protected String getTitleStr() {
        return UIUtils.getString(R.string.menu_setting);
    }

    public void refreshFirstLangUI() {
        mData = initData();
        ((MyBaseAdapter<SettingItemBean>) mLv_settings.getAdapter()).notifyDataSetChanged(mData);
    }

    private void clickTransClicked(boolean isCheck) {
        if (isCheck) {
            if (CommonUtils.isGotAppOps()) {
                ServiceUtils.startClickTransService();
            } else {
                Toast.makeText(SettingActivity.this, "无权限", Toast.LENGTH_SHORT).show();
            }
        } else {
            ServiceUtils.stopClickTransService();
        }
    }
}
