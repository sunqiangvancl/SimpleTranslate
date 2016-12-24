package xyz.mrseng.fasttranslate.ui.holder;

import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.ui.base.BaseHolder;
import xyz.mrseng.fasttranslate.ui.base.MyBasePagerAdapter;
import xyz.mrseng.fasttranslate.utils.UIUtils;

/**
 * Created by MrSeng on 2016/12/19.
 * 向我付款卡片
 */

public class PayToMeHolder extends BaseHolder {
    public PayToMeHolder(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        View view = View.inflate(getActivity(),R.layout.dialog_payme_about,null);
        TabLayout tl_pay = (TabLayout) view.findViewById(R.id.tl_pay);
        ViewPager vp_pay = (ViewPager) view.findViewById(R.id.vp_pay_about);
        initViewPager(vp_pay);
        tl_pay.setupWithViewPager(vp_pay);
        return view;
    }

    private void initViewPager(ViewPager viewPager) {
        viewPager.setAdapter(new MyBasePagerAdapter() {
            @Override
            public int getCount() {
                return 2;
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position) {
                ImageView imageView = new ImageView(getActivity());
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView.setImageDrawable(UIUtils.getDrawable(position == 0 ? R.drawable.pay_zfb : R.drawable.pay_wx));
                container.addView(imageView);
                return imageView;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return UIUtils.getString(position == 0 ? R.string.alipay : R.string.wechat);
            }
        });
    }

    @Override
    public void onRefresh(Object data) {

    }


}
