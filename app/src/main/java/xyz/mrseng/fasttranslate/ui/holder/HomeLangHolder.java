package xyz.mrseng.fasttranslate.ui.holder;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.Arrays;
import java.util.LinkedList;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.domain.TransBean;
import xyz.mrseng.fasttranslate.engine.TransService;
import xyz.mrseng.fasttranslate.ui.activity.HomeActivity;
import xyz.mrseng.fasttranslate.ui.base.BaseHolder;
import xyz.mrseng.fasttranslate.utils.CommonUtils;
import xyz.mrseng.fasttranslate.utils.SPUtils;
import xyz.mrseng.fasttranslate.utils.UIUtils;

/**
 * Created by MrSeng on 2016/12/13.
 * 顶部语言选项的逻辑执行者
 */

public class HomeLangHolder extends BaseHolder<TransBean> {
    private Spinner mSp_left;
    private Spinner mSp_right;
    private ImageView mIv_switch;
    private RotateAnimation mAnim_rotate;
    private TranslateAnimation mAnim_toRight;
    private TranslateAnimation mAnim_toLeft;
    private HomeActivity homeActivity;


    public HomeLangHolder(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        homeActivity = (HomeActivity) getActivity();
        View view = UIUtils.inflate(R.layout.card_language_home);
        mSp_left = (Spinner) view.findViewById(R.id.sp_trans_left);
        mSp_right = (Spinner) view.findViewById(R.id.sp_trans_right);
        mIv_switch = (ImageView) view.findViewById(R.id.iv_switch);
        initSpinner();
        initImageView();
        initAnim();
        initListener();
        return view;
    }

    private void initListener() {
        homeActivity.getTransService().addTransListener(new TransService.TranslateListener() {
            @Override
            public void afterTrans(TransBean transInfo) {
                if (transInfo.fromCode != null) {
                    getData().fromCode = transInfo.fromCode;
                }
                if (transInfo.toCode != null) {
                    getData().toCode = transInfo.toCode;
                }
                onRefresh(getData());
            }

            @Override
            public void beforeTrans(TransBean transInfo) {

            }
        });


    }


    private void initImageView() {
        mIv_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAniming) return;
                mSp_left.startAnimation(mAnim_toRight);
                mSp_right.startAnimation(mAnim_toLeft);
                v.startAnimation(mAnim_rotate);
                swapLang();
            }

        });
    }

    /*交换语言*/
    private void swapLang() {
        getData().swapFromToCode();
        int cnt_l = mSp_left.getAdapter().getCount();
        int cnt_r = mSp_right.getAdapter().getCount();
        int sel_l = 0;
        int sel_r = 0;
        for (int i = 0; i < cnt_l; i++) {
            String left = (String) mSp_left.getAdapter().getItem(i);
            if (left.equals(TransBean.switchCodeAndWord(getData().fromCode))) {
                sel_l = i;
            }
        }
        for (int i = 0; i < cnt_r; i++) {
            String right = (String) mSp_right.getAdapter().getItem(i);
            if (right.equals(TransBean.switchCodeAndWord(getData().toCode))) {
                sel_r = i;
            }
        }
        mSp_left.setSelection(sel_l);
        mSp_right.setSelection(sel_r);

        updateTrans();
    }

    private void updateTrans() {
        homeActivity.getTransInfo().fromCode = getData().fromCode;
        homeActivity.getTransInfo().toCode = getData().toCode;
        homeActivity.notifyTransInfoChanged();
    }


    private void initAnim() {
        int d = 100;
        //to left
        mAnim_toLeft = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, -0.5f,
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0
        );
        mAnim_toLeft.setDuration(d);
        mAnim_toLeft.setRepeatCount(1);
        mAnim_toLeft.setRepeatMode(Animation.REVERSE);

        //to right
        mAnim_toRight = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0.5f,
                TranslateAnimation.RELATIVE_TO_SELF, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0
        );
        mAnim_toRight.setDuration(d);
        mAnim_toRight.setRepeatCount(1);
        mAnim_toRight.setRepeatMode(Animation.REVERSE);
        //旋转
        mAnim_rotate = new RotateAnimation(0, 180,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mAnim_rotate.setDuration(d * 2);

        //侦听
        Animation.AnimationListener listener = new MyAnimListener();
        mAnim_toLeft.setAnimationListener(listener);
        mAnim_toRight.setAnimationListener(listener);
        mAnim_rotate.setAnimationListener(listener);

    }

    private boolean isAniming = false;//标记是否正在执行动画

    public void onActivityResume() {

        //初始化code,保持此holder的data与Activity的data是同一个对象
        TransBean data = homeActivity.getTransInfo();
        data.fromCode = SPUtils.getFirstFromCode();
        data.toCode = SPUtils.getFirstToCode();
        mSp_left.setSelection(TransBean.getCodeIntByCodeStr(data.fromCode));
        mSp_right.setSelection(TransBean.getCodeIntByCodeStr(data.toCode) - 1);
        if (getData() != null &&
                !(CommonUtils.equeals(getData().toCode, data.toCode) && CommonUtils.equeals(getData().fromCode, data.fromCode))) {
            homeActivity.setTransInfo(data);
            homeActivity.notifyTransInfoChanged();
        }
        setData(data);//更新显示
    }

    class MyAnimListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {
            isAniming = true;
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            isAniming = false;
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }


    @Override
    public void onRefresh(TransBean data) {
        if (data.token != null && data.token == TransBean.TOKEN_NET) {
            homeActivity.setTransInfo(getData());
        }
        mSp_left.setSelection(TransBean.getCodeIntByCodeStr(data.fromCode));
        mSp_right.setSelection(TransBean.getCodeIntByCodeStr(data.toCode) - 1);
    }

    private void initSpinner() {
        //数据
        final String[] from_items = UIUtils.getStringArray(R.array.lang_name);
        final String[] to_items = getToItems(from_items);
        //adapter
        //TODO 左侧选中的哪个语言，右侧该语言就不应出现在列表中，即动态控制语言列表
        ArrayAdapter rightAdapter = new ArrayAdapter<>(UIUtils.getContext(), R.layout.item_spinner_right, to_items);
        ArrayAdapter leftAdapter = new ArrayAdapter<>(UIUtils.getContext(), R.layout.item_spinner_left, from_items);
        rightAdapter.setDropDownViewResource(R.layout.item_spinner_list);
        leftAdapter.setDropDownViewResource(R.layout.item_spinner_list);
        mSp_right.setAdapter(rightAdapter);
        mSp_left.setAdapter(leftAdapter);


        //下拉列表展示
        mSp_left.measure(0, 0);
        int yOffset = (int) (mSp_left.getMeasuredHeight() * 1.5);
        mSp_left.setDropDownVerticalOffset(yOffset);
        mSp_right.setDropDownVerticalOffset(yOffset);
        //侦听
        mSp_left.setOnItemSelectedListener(new MyOnItemSelectedListener(true));
        mSp_right.setOnItemSelectedListener(new MyOnItemSelectedListener(false));

    }

    @NonNull
    private String[] getToItems(String[] from_items) {
        LinkedList<String> list = new LinkedList<>();
        list.addAll(Arrays.asList(from_items).subList(1, from_items.length));
        return list.toArray(new String[0]);
    }


    private class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        private boolean isLeft;

        private MyOnItemSelectedListener(boolean isLeft) {
            this.isLeft = isLeft;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            boolean needTrans = false;
            if (isLeft) {
                String fromCode = TransBean.switchCodeAndWord((String) mSp_left.getSelectedItem());
                if (fromCode != null && !fromCode.equals(getData().fromCode)) {
                    getData().fromCode = fromCode;
                    needTrans = true;
                }
            } else {
                String toCode = TransBean.switchCodeAndWord((String) mSp_right.getSelectedItem());
                if (toCode != null && !toCode.equals(getData().toCode)) {
                    getData().toCode = toCode;
                    needTrans = true;
                }
            }
            setData(getData());//更新语言条数据
            if (needTrans) {
                updateTrans();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

}
