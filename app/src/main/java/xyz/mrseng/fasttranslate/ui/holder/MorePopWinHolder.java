package xyz.mrseng.fasttranslate.ui.holder;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.domain.PpwMoreBean;
import xyz.mrseng.fasttranslate.domain.TransBean;
import xyz.mrseng.fasttranslate.ui.activity.HomeActivity;
import xyz.mrseng.fasttranslate.ui.base.BaseHolder;
import xyz.mrseng.fasttranslate.ui.base.MyBaseAdapter;
import xyz.mrseng.fasttranslate.utils.UIUtils;

/**
 * Created by MrSeng on 2016/12/17.
 * 结果卡片点击更多按钮展示的
 */

public class MorePopWinHolder extends BaseHolder<ArrayList<PpwMoreBean>> {
    private PopupWindow mWindow;

    public MorePopWinHolder(Activity activity, PopupWindow window) {
        super(activity);
        mWindow = window;
    }

    @Override
    public View initView() {
        View view = UIUtils.inflate(R.layout.ppw_more_result);
        ListView lv = (ListView) view.findViewById(R.id.lv_more_pup_result);
        initListView(lv);
        return view;
    }

    private void initListView(ListView lv) {
        setData(initData());
        lv.setAdapter(new MyBaseAdapter<PpwMoreBean>(getData()) {
            @Override
            public BaseHolder<PpwMoreBean> getHolder() {
                return new BaseHolder<PpwMoreBean>(getActivity()) {

                    private TextView tv;

                    @Override
                    public View initView() {
                        View view = UIUtils.inflate(R.layout.item_list_more_result);
                        tv = (TextView) view.findViewById(R.id.tv_more_result_item);
                        return view;
                    }

                    @Override
                    public void onRefresh(PpwMoreBean data) {
                        tv.setText(data.text);
                    }
                };
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (getData().get(position).item) {
                    case PpwMoreBean.ITEM_REFRESH://刷新
                        ((HomeActivity) getActivity()).getTransInfo().token = TransBean.TOKEN_NET;
                        ((HomeActivity) getActivity()).notifyTransInfoChanged();
                        break;
                    case PpwMoreBean.ITEM_INVERSE://反相
                        doInverse();
                        break;
                    case PpwMoreBean.ITEM_FULLSCREEN://全屏
                        break;
                    case PpwMoreBean.ITEM_SHARE://分享
                        break;
                }
                mWindow.dismiss();
            }
        });
    }

    /*反相*/
    private void doInverse() {
        TransBean transInfo = ((HomeActivity)getActivity()).getTransInfo();
        String temp = transInfo.fromCode;
        transInfo.fromCode = transInfo.toCode;
        transInfo.toCode = temp;

        temp = transInfo.fromWord;
        transInfo.fromWord = transInfo.toWord;
        transInfo.toWord = temp;
        ((HomeActivity)getActivity()).notifyTransInfoChanged();
    }

    private ArrayList<PpwMoreBean> initData() {
        PpwMoreBean share = new PpwMoreBean(PpwMoreBean.ITEM_SHARE);
        PpwMoreBean full = new PpwMoreBean(PpwMoreBean.ITEM_FULLSCREEN);
        PpwMoreBean refresh = new PpwMoreBean(PpwMoreBean.ITEM_REFRESH);
        PpwMoreBean inverse = new PpwMoreBean(PpwMoreBean.ITEM_INVERSE);
        ArrayList<PpwMoreBean> menus = new ArrayList<>();
        menus.add(share);
        menus.add(full);
//        menus.add(inverse);
        menus.add(refresh);
        return menus;
    }

    @Override
    public void onRefresh(ArrayList<PpwMoreBean> data) {
    }

}
