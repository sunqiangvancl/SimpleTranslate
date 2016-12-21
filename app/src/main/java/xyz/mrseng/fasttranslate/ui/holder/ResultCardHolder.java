package xyz.mrseng.fasttranslate.ui.holder;

import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.domain.TransBean;
import xyz.mrseng.fasttranslate.global.Canstant;
import xyz.mrseng.fasttranslate.service.MarkService;
import xyz.mrseng.fasttranslate.service.TransService;
import xyz.mrseng.fasttranslate.ui.activity.HomeActivity;
import xyz.mrseng.fasttranslate.ui.base.BaseHolder;
import xyz.mrseng.fasttranslate.utils.TransUtils;
import xyz.mrseng.fasttranslate.utils.UIUtils;

/**
 * Created by MrSeng on 2016/12/13.
 * 结果卡片的逻辑
 */

public class ResultCardHolder extends BaseHolder<TransBean> implements TransService.TranslateListener, View.OnClickListener {

    private TextView mTv_result;//显示翻译结果
    private TextView mTv_readLang;//显示翻译至何种语言，显示朗读
    private MarkService markService = new MarkService();
    private ImageView mIv_mark;
    private HomeActivity homeActivity;
    private ImageView mIv_more;
    private MorePPWHolder moreHolder;
    private TextView mTv_site;

    public ResultCardHolder(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        homeActivity = (HomeActivity) getActivity();

        View view = UIUtils.inflate(R.layout.card_result_home);

        mTv_result = (TextView) view.findViewById(R.id.tv_result_card);
        mTv_readLang = (TextView) view.findViewById(R.id.tv_audio_lang);

        ImageView mIv_copy = (ImageView) view.findViewById(R.id.iv_copy_result);
        mIv_copy.setOnClickListener(this);

        mIv_mark = (ImageView) view.findViewById(R.id.iv_mark_result);
        mIv_mark.setOnClickListener(this);

        mIv_more = (ImageView) view.findViewById(R.id.iv_more_result);
        mIv_more.setOnClickListener(this);

        mTv_site = (TextView) view.findViewById(R.id.tv_from_site);

        //注册翻译监听
        homeActivity.getTransService().addTransListener(this);
        return view;
    }

    @Override
    public void onRefresh(TransBean data) {
        mTv_result.setText(data.toWord == null ? "" : data.toWord);
        mTv_readLang.setText(TransUtils.switchCodeAndWord(data.toCode));
        mIv_mark.setImageDrawable(UIUtils.getDrawable(data.marked == Canstant.MARKED ? R.drawable.ic_star_marked : R.drawable.ic_star36));
        mTv_site.setText(data.site==null?"":data.site);
    }

    @Override
    public void afterTrans(TransBean transInfo) {
        if (transInfo.token == Canstant.TOKEN_NET) {
            //根据FromWord和ToWord更新是否收藏
            transInfo.marked = markService.isMarked(transInfo) ? Canstant.MARKED : Canstant.UN_MARKED;
        }
        setData(transInfo);
    }

    @Override
    public void beforeTrans(TransBean transInfo) {
    }

    //点击事件侦听
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_copy_result://复制
                TransUtils.copyText(mTv_result.getText().toString());
                Toast.makeText(UIUtils.getContext(), UIUtils.getString(R.string.copy_success), Toast.LENGTH_SHORT).show();
                break;
            case R.id.iv_mark_result://收藏
                if (getData().marked == Canstant.MARKED) {//取消
                    markService.unMark(getData());
                    setData(getData());
                } else if (getData().marked == Canstant.UN_MARKED) {//添加
                    markService.mark(getData());
                    setData(getData());
                }
                break;
            case R.id.iv_more_result://更多选项
                PopupWindow popWin = new PopupWindow(getActivity());
                popWin.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
                popWin.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
                moreHolder = new MorePPWHolder(getActivity(),popWin);
                popWin.setContentView(moreHolder.getRootView());

                popWin.setFocusable(true);
                popWin.setBackgroundDrawable(new BitmapDrawable());
                popWin.showAtLocation(mIv_mark, Gravity.LEFT, mIv_more.getLeft(), mIv_more.getHeight());
                break;
        }
    }
}
