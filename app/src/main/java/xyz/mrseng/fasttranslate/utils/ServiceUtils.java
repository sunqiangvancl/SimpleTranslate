package xyz.mrseng.fasttranslate.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;

import java.util.List;

import xyz.mrseng.fasttranslate.service.ClickTransService;

/**
 * Created by MrSeng on 2016/12/23.
 * service方面的工具类
 */

public class ServiceUtils {

    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param serviceName 是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public static boolean isServiceWork(String serviceName) {
        boolean isWork = false;
        serviceName = UIUtils.getContext().getPackageName() + serviceName;
        ActivityManager myAM = (ActivityManager) UIUtils.getContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(100);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

    private static Intent intent_click_trans_service = new Intent(UIUtils.getContext(), ClickTransService.class);
    private static boolean enable_click_trans_Service = false;

    // TODO: 2016/12/23 点按翻译重启才能生效

    public static void startClickTransService() {
        if (!enable_click_trans_Service) {
            UIUtils.getContext().startService(intent_click_trans_service);
            enable_click_trans_Service = true;
        }
    }

    public static void stopClickTransService() {
        if (enable_click_trans_Service) {
            UIUtils.getContext().stopService(intent_click_trans_service);
            enable_click_trans_Service = false;
        }
    }
}
