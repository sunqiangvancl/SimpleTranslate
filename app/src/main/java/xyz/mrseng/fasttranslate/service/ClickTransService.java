package xyz.mrseng.fasttranslate.service;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.app.Service;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import xyz.mrseng.fasttranslate.R;
import xyz.mrseng.fasttranslate.domain.TransBean;
import xyz.mrseng.fasttranslate.ui.holder.ClickTransDialogHolder;
import xyz.mrseng.fasttranslate.utils.SPUtils;
import xyz.mrseng.fasttranslate.utils.CommonUtils;

public class ClickTransService extends Service {

    private WindowManager mWinManager;
    private View view_float;
    private WindowManager.LayoutParams params;
    private boolean isAdded = false;
    private ClipboardManager mClipManager;
    private ImageView iv_bg;
    public static boolean isDialogShowed = false;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mClipManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        mClipManager.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                if (!isAdded && !CommonUtils.isInSelfActivity() && !isDialogShowed) {
                    showFloatWindow();
                }
            }
        });
        System.out.println("点按翻译服务已开启");
    }

    /*显示悬浮窗*/
    public void showFloatWindow() {
        System.out.println("显示悬浮窗");
        //获取windowManager
        if (mWinManager==null){
            mWinManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        }
        /*悬浮窗*/
        view_float = View.inflate(this, R.layout.icon_float, null);
        iv_bg = (ImageView) view_float.findViewById(R.id.iv_launcher);
        iv_bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransBean transBean = new TransBean();
                transBean.fromCode = SPUtils.getFirstFromCode();
                transBean.toCode = SPUtils.getFirstToCode();
                transBean.fromWord = mClipManager.getText().toString();
                if (!TextUtils.isEmpty(transBean.fromWord)) {
                    showSimpleTransDialog(transBean);
                    hiddenFloatWindow();
                }
            }
        });

        /*界面参数*/
        params = new WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        params.format = PixelFormat.RGBA_8888;//设置半透明
        params.gravity = Gravity.RIGHT;
        //设置windowFlag
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

        mWinManager.addView(view_float, params);
        getAnim(800, 6).start();
        isAdded = true;
    }

    /*显示*/
    private void showSimpleTransDialog(TransBean transBean) {
        isDialogShowed = true;
        AlertDialog dialog = new AlertDialog.Builder(ClickTransService.this).create();
        ClickTransDialogHolder holder = new ClickTransDialogHolder(dialog);
        holder.setData(transBean);
        dialog.setView(holder.getRootView());
        dialog.getWindow().setGravity(Gravity.TOP);
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();
        p.y = 0;
        p.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;//当context非Activity时，需要做此标记
        dialog.getWindow().setAttributes(p);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                isDialogShowed = false;
            }
        });
        dialog.show();
    }

    /*隐藏悬浮窗*/
    public void hiddenFloatWindow() {
        if (isAdded) {
            mWinManager.removeViewImmediate(view_float);
            isAdded = false;
            System.out.println("悬浮窗关闭");
        }
    }

    private ValueAnimator getAnim(int dur, int repCnt) {
        ValueAnimator valueAnim = new ValueAnimator();
        valueAnim.setFloatValues(1f, 0.5f, 1f);
        valueAnim.setDuration(dur);
        valueAnim.setRepeatCount(repCnt);
        valueAnim.setRepeatMode(ValueAnimator.REVERSE);
        valueAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator anim) {
                if (isAdded) {
                    params.alpha = (float) anim.getAnimatedValue();
                    mWinManager.updateViewLayout(view_float, params);
                }
            }
        });
        valueAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }
            @Override
            public void onAnimationEnd(Animator animation) {
                hiddenFloatWindow();//动画结束，关闭悬浮窗
            }
            @Override
            public void onAnimationCancel(Animator animation) {
            }
            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
        return valueAnim;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("点按翻译服务关闭！");
    }
}
