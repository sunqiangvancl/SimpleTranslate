package xyz.mrseng.fasttranslate.ui.holder;

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
import xyz.mrseng.fasttranslate.domain.LanguageBean;
import xyz.mrseng.fasttranslate.global.ThreadManager;
import xyz.mrseng.fasttranslate.service.TransService;
import xyz.mrseng.fasttranslate.utils.TransUtils;
import xyz.mrseng.fasttranslate.utils.UIUtils;

/**
 * Created by MrSeng on 2016/12/13.
 * 顶部语言选项的逻辑执行者
 */

public class LangHolder extends BaseHolder<LanguageBean> {
    private Spinner mSp_left;
    private Spinner mSp_right;
    private ImageView mIv_switch;
    private RotateAnimation mAnim_rotate;
    private TranslateAnimation mAnim_toRight;
    private TranslateAnimation mAnim_toLeft;

    @Override
    public View initView() {
        mService = TransService.getNewInstance();
        View view = UIUtils.inflate(R.layout.card_language_bar);
        mSp_left = (Spinner) view.findViewById(R.id.sp_trans_left);
        mSp_right = (Spinner) view.findViewById(R.id.sp_trans_right);
        mIv_switch = (ImageView) view.findViewById(R.id.iv_switch);
        initSpinner();
        initImageView();
        initAnim();
        return view;
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

    private void swapLang() {
        getData().swapFromTo();
        int cnt_l = mSp_left.getAdapter().getCount();
        int cnt_r = mSp_right.getAdapter().getCount();
        int sel_l = 0;
        int sel_r = 0;
        for (int i = 0; i < cnt_l; i++) {
            String left = (String) mSp_left.getAdapter().getItem(i);
            if (left.equals(TransUtils.switchCodeAndWord(getData().fromCode))) {
                sel_l = i;
            }
        }
        for (int i = 0; i < cnt_r; i++) {
            String right = (String) mSp_right.getAdapter().getItem(i);
            if (right.equals(TransUtils.switchCodeAndWord(getData().toCode))) {
                sel_r = i;
            }
        }
        mSp_left.setSelection(sel_l);
        mSp_right.setSelection(sel_r);

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
    public void onRefresh(LanguageBean data) {
        mService.setLanBean(data);
        ThreadManager.execute(new Runnable() {
            @Override
            public void run() {
                mService.doTranslate();
            }
        });
    }

    private void initSpinner() {
        //数据
        final String[] from_items = UIUtils.getStringArray(R.array.lang_word);
        final String[] to_items = getToItems(from_items);
        //adapter
        ArrayAdapter rightAdapter = new ArrayAdapter<>(getContext(), R.layout.item_spinner_right, to_items);
        ArrayAdapter leftAdapter = new ArrayAdapter<>(getContext(), R.layout.item_spinner_left, to_items);
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
        //初始化code
        LanguageBean data = new LanguageBean();
        data.fromCode = TransUtils.switchCodeAndWord((String) mSp_left.getSelectedItem());
        data.toCode = TransUtils.switchCodeAndWord((String) mSp_right.getSelectedItem());
        setData(data);
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
            if (isLeft) {
                getData().fromCode = TransUtils.switchCodeAndWord((String) mSp_left.getSelectedItem());
            } else {
                getData().toCode = TransUtils.switchCodeAndWord((String) mSp_right.getSelectedItem());
            }
            setData(getData());
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

}
