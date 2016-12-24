package xyz.mrseng.fasttranslate.ui.holder;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.LinkedList;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.domain.TransBean;
import xyz.mrseng.fasttranslate.engine.TransService;
import xyz.mrseng.fasttranslate.global.VoiceManager;
import xyz.mrseng.fasttranslate.ui.activity.SettingActivity;
import xyz.mrseng.fasttranslate.ui.activity.SplashActivity;
import xyz.mrseng.fasttranslate.ui.base.BaseHolder;
import xyz.mrseng.fasttranslate.utils.CommonUtils;
import xyz.mrseng.fasttranslate.utils.UIUtils;

/**
 * Created by MrSeng on 2016/12/23.
 * 点按翻译对话框
 */

public class ClickTransDialogHolder extends BaseHolder<TransBean> implements TransService.TranslateListener, View.OnClickListener {

    private ImageView mIv_setting;
    private ImageView mIv_read_from;
    private ImageView mIv_close;
    private ImageView mIv_read_to;
    private Spinner mSp_from;
    private Spinner mSp_to;
    private EditText mEt_from;
    private TextView mTv_to;
    private ImageView mIv_more;
    private Button mBtn_newTrans;
    private TransService mTransService;
    private VoiceManager mVoiceManager;
    private final AlertDialog mDialog;

    public ClickTransDialogHolder(AlertDialog dialog) {
        super(null);
        mDialog = dialog;
    }

    @Override
    public View initView() {
        mTransService = TransService.getNewInstance(TransService.FLAG_SIMPLE);
        mVoiceManager = VoiceManager.getNewInstance();
        View view = View.inflate(UIUtils.getContext(), R.layout.dialog_simple_trans, null);
        initWidget(view);
        initData();
        initListener();
        return view;
    }

    private void initListener() {
        mSp_from.setOnItemSelectedListener(new OnSPItemSelectedListener(true));
        mSp_to.setOnItemSelectedListener(new OnSPItemSelectedListener(false));
        mEt_from.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                getData().fromWord = s.toString();
                mTransService.doTranslate(getData());
            }
        });
        mTransService.addTransListener(this);
        mIv_read_from.setOnClickListener(this);
        mIv_read_to.setOnClickListener(this);
        mIv_close.setOnClickListener(this);
        mIv_setting.setOnClickListener(this);
        mIv_more.setOnClickListener(this);
        mBtn_newTrans.setOnClickListener(this);
    }

    private boolean readAble(int langCode) {
        switch (langCode) {
            case TransBean.LANG_CODE_ZH:
            case TransBean.LANG_CODE_EN:
            case TransBean.LANG_CODE_WYW:
            case TransBean.LANG_CODE_YUE:
            case TransBean.LANG_CODE_CHT:
                return true;
        }
        return false;
    }

    /*初始化语言条数据*/
    private void initData() {
        String[] from_items = UIUtils.getStringArray(R.array.lang_name);
        String[] to_items = getToItems(from_items);

        ArrayAdapter rightAdapter = new ArrayAdapter<>(UIUtils.getContext(), R.layout.item_spinner_right, to_items);
        ArrayAdapter leftAdapter = new ArrayAdapter<>(UIUtils.getContext(), R.layout.item_spinner_left, from_items);
        rightAdapter.setDropDownViewResource(R.layout.item_spinner_list);
        leftAdapter.setDropDownViewResource(R.layout.item_spinner_list);
        mSp_to.setAdapter(rightAdapter);
        mSp_from.setAdapter(leftAdapter);
        //下拉列表展示
        mSp_from.measure(0, 0);
        int yOffset = (int) (mSp_from.getMeasuredHeight() * 1.5);
        mSp_from.setDropDownVerticalOffset(yOffset);
        mSp_to.setDropDownVerticalOffset(yOffset);

    }

    private void initWidget(View rootView) {
        mIv_setting = (ImageView) rootView.findViewById(R.id.iv_setting_dialog_simple);
        mIv_setting.setAlpha(UIUtils.getInteger(R.integer.alpha_menu_icon));
        mIv_close = (ImageView) rootView.findViewById(R.id.iv_close_dialog_simple);
        mIv_read_from = (ImageView) rootView.findViewById(R.id.iv_read_from_dialog_simple);
        mIv_read_to = (ImageView) rootView.findViewById(R.id.iv_read_to_dialog_simple);
        mSp_from = (Spinner) rootView.findViewById(R.id.sp_from_dialog_simple);
        mSp_to = (Spinner) rootView.findViewById(R.id.sp_to_dialog_simple);
        mEt_from = (EditText) rootView.findViewById(R.id.et_from_word_dialog_simple);
        mTv_to = (TextView) rootView.findViewById(R.id.tv_to_word_dialog_simple);
        mIv_more = (ImageView) rootView.findViewById(R.id.iv_more_dialog_simple);
        mBtn_newTrans = (Button) rootView.findViewById(R.id.btn_new_trans_dialog_simple);
    }

    @Override
    public void onRefresh(TransBean data) {
        mSp_from.setSelection(TransBean.getCodeIntByCodeStr(data.fromCode));
        mSp_to.setSelection(TransBean.getCodeIntByCodeStr(data.toCode) - 1);
        mEt_from.setText(getData().fromWord);
    }

    @NonNull
    private String[] getToItems(String[] from_items) {
        LinkedList<String> list = new LinkedList<>();
        list.addAll(Arrays.asList(from_items).subList(1, from_items.length));
        return list.toArray(new String[0]);
    }

    @Override
    public void afterTrans(TransBean transInfo) {
        getData().toWord = transInfo.toWord == null ? getData().toWord : transInfo.toWord;
        mTv_to.setText(transInfo.toWord == null ? null : transInfo.toWord);
    }

    @Override
    public void beforeTrans(TransBean transInfo) {
        mTv_to.setText("......");
    }


    public  class OnSPItemSelectedListener implements AdapterView.OnItemSelectedListener {
        private boolean isLeft;

        private OnSPItemSelectedListener(boolean isLeft) {
            this.isLeft = isLeft;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            boolean needTrans = false;
            if (isLeft) {
                String fromCode = TransBean.switchCodeAndWord((String) mSp_from.getSelectedItem());
                if (fromCode != null && !fromCode.equals(getData().fromCode)) {
                    getData().fromCode = fromCode;
                    needTrans = true;
                }
            } else {
                String toCode = TransBean.switchCodeAndWord((String) mSp_to.getSelectedItem());
                if (toCode != null && !toCode.equals(getData().toCode)) {
                    getData().toCode = toCode;
                    needTrans = true;
                }
            }
            setData(getData());//更新语言条数据
            if (needTrans) {
                mTransService.doTranslate(getData());
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_read_from_dialog_simple://顶部朗读
                int lang_from = TransBean.getCodeIntByCodeStr(getData().fromCode);
                if (readAble(lang_from)
                        || TextUtils.isEmpty(mEt_from.getText().toString())) {
                    mVoiceManager.readText(mEt_from.getText().toString());
                } else {
                    Toast.makeText(UIUtils.getContext(), UIUtils.getString(R.string.unsopport_lang) + TransBean.getLangNameStr(lang_from),
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_read_to_dialog_simple://朗读译文
                int lang_to = TransBean.getCodeIntByCodeStr(getData().toCode);
                if (readAble(lang_to)
                        || TextUtils.isEmpty(mTv_to.getText().toString())) {
                    mVoiceManager.readText(mTv_to.getText().toString());
                } else {
                    Toast.makeText(UIUtils.getContext(), UIUtils.getString(R.string.unsopport_lang) + TransBean.getLangNameStr(lang_to),
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.iv_close_dialog_simple://关闭
                mDialog.dismiss();
                break;
            case R.id.iv_setting_dialog_simple://进入设置
                Intent intent = new Intent(UIUtils.getContext(), SettingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                UIUtils.getContext().startActivity(intent);
                mDialog.dismiss();
                break;
            case R.id.iv_more_dialog_simple://更多按钮
                View view = UIUtils.inflate(R.layout.ppw_more_dialog_simple);
                TextView tv_main = (TextView) view.findViewById(R.id.tv_open_main);
                TextView tv_copy = (TextView) view.findViewById(R.id.tv_copy);
                final PopupWindow popWin = new PopupWindow(UIUtils.getContext());
                tv_main.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(UIUtils.getContext(), SplashActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        UIUtils.getContext().startActivity(intent);
                        popWin.dismiss();
                        mDialog.dismiss();
                    }
                });
                tv_copy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (TextUtils.isEmpty(mTv_to.getText().toString())) {
                            Toast.makeText(UIUtils.getContext(), R.string.empty_content, Toast.LENGTH_SHORT).show();
                        } else {
                            CommonUtils.copyText(mTv_to.getText().toString());
                            Toast.makeText(UIUtils.getContext(), R.string.copy_success, Toast.LENGTH_SHORT).show();
                        }
                        popWin.dismiss();
                    }
                });
                popWin.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
                popWin.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
                popWin.setContentView(view);
                popWin.setFocusable(true);
                popWin.setBackgroundDrawable(new BitmapDrawable());
                popWin.showAtLocation(mIv_more, Gravity.LEFT, mIv_more.getLeft(), mIv_more.getHeight());
                System.out.println();
                break;
            case R.id.btn_new_trans_dialog_simple://新翻译按钮，清空操作
                getData().fromWord = "";
                mEt_from.setText("");
                mEt_from.requestFocus();
                break;
        }
    }
}
