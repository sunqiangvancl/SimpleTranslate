package xyz.mrseng.fasttranslate.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.ui.holder.HomeBottomHolder;
import xyz.mrseng.fasttranslate.ui.holder.InputHolder;
import xyz.mrseng.fasttranslate.ui.holder.LangHolder;

/**
 * Created by MrSeng on 2016/12/14.
 * 主页面的逻辑
 */

public class HomeFragment extends Fragment {
    private InputHolder mInputHolder;
    private LangHolder mLangHolder;
    private FrameLayout mFl_bottom;
    private FrameLayout mFl_top;
    private FrameLayout mFl_center;
    private HomeBottomHolder mBottomHolder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, null);
        initWidget(rootView);
        return rootView;
    }


    private void initWidget(View view) {
        //top bar
        mFl_top = (FrameLayout) view.findViewById(R.id.fl_top_home);
        mLangHolder = new LangHolder(getActivity());
        mFl_top.addView(mLangHolder.getRootView());

        //center
        mFl_center = (FrameLayout) view.findViewById(R.id.fl_center_home);
        mInputHolder = new InputHolder(getActivity());
        mFl_center.addView(mInputHolder.getRootView());

        //bottom
        mFl_bottom = (FrameLayout) view.findViewById(R.id.fl_bottom_home);
        mBottomHolder = new HomeBottomHolder(getActivity());
        mFl_bottom.addView(mBottomHolder.getRootView());
    }
}
