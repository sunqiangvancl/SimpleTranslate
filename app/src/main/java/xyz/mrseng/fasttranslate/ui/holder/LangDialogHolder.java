package xyz.mrseng.fasttranslate.ui.holder;

import android.app.Activity;
import android.view.View;

import java.util.ArrayList;
import java.util.Map;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.domain.TransBean;
import xyz.mrseng.fasttranslate.ui.activity.SettingActivity;
import xyz.mrseng.fasttranslate.ui.base.BaseHolder;
import xyz.mrseng.fasttranslate.ui.view.WheelView;
import xyz.mrseng.fasttranslate.utils.SPUtils;
import xyz.mrseng.fasttranslate.utils.UIUtils;

/**
 * Created by MrSeng on 2016/12/18.
 */

public class LangDialogHolder extends BaseHolder<TransBean> {

    private WheelView mWv_from;
    private WheelView mWv_to;
    private Map<Integer, String> leftMap;
    private Map<Integer, String> rightMap;

    public LangDialogHolder(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.dialog_lang_setting);
        mWv_from = (WheelView) view.findViewById(R.id.wv_lang_from);
        mWv_to = (WheelView) view.findViewById(R.id.wv_lang_to);
        initData();
        initListener();
        return view;
    }

    private void initData() {
        leftMap = TransBean.getAllLangNameMap();
        rightMap = TransBean.getAllLangNameMap();
        rightMap.remove(TransBean.LANG_CODE_AUTO);
        mWv_from.setItems(new ArrayList<String>(leftMap.values()));
        mWv_to.setItems(new ArrayList<String>(rightMap.values()));
        int from_idx = getLangIdx(SPUtils.getFirstFromCode());
        mWv_from.setSeletion(from_idx);
        int to_idx = getLangIdx(SPUtils.getFirstToCode()) -1;
        mWv_to.setSeletion(to_idx);
    }

    private int getLangIdx(String lang) {
        return TransBean.getCodeIntByCodeStr(lang);
    }

    private void initListener() {
        mWv_from.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                String code = TransBean.switchCodeAndWord(item);
                SPUtils.setString(SPUtils.KEY_FIRST_FROM_CODE, code);
                ((SettingActivity) getActivity()).refreshFirstLangUI();
            }
        });
        mWv_to.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                String code = TransBean.switchCodeAndWord(item);
                SPUtils.setString(SPUtils.KEY_FIRST_TO_CODE, code);
                ((SettingActivity) getActivity()).refreshFirstLangUI();
            }
        });
    }

    @Override
    public void onRefresh(TransBean data) {
    }
}
