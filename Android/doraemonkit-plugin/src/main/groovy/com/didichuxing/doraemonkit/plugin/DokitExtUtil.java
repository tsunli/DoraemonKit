package com.didichuxing.doraemonkit.plugin;

import com.android.build.gradle.AppExtension;

import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 作    者：jint（金台）
 * 版    本：1.0
 * 创建日期：2020/3/24-14:58
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class DokitExtUtil {
    private String mApplicationId;
    /**
     * dokit 插件开关 字段权限必须为public 否则无法进行赋值
     */
    private boolean mDokitPluginSwitch = true;
    /**
     * 慢函数开关
     */
    private boolean mSlowMethodSwitch = true;
    /**
     * 单位为ms 默认500ms
     */
    private int mThresholdTime = 500;

    private List<String> mPackageNames = new ArrayList<>();


    public boolean isDokitPluginSwitch() {
        return mDokitPluginSwitch;
    }

    public boolean isSlowMethodSwitch() {
        return mSlowMethodSwitch;
    }

    public int getThresholdTime() {
        return mThresholdTime;
    }

    public List<String> getPackageNames() {
        return mPackageNames;
    }

    /**
     * 静态内部类单例
     */
    private static class Holder {
        private static DokitExtUtil INSTANCE = new DokitExtUtil();
    }

    public static DokitExtUtil getInstance() {
        return Holder.INSTANCE;
    }

    /**
     * 初始化
     *
     * @param dokitExtension
     * @param appExtension
     */
    public void init(DokitExtension dokitExtension, AppExtension appExtension) {
        if (dokitExtension != null) {
            this.mDokitPluginSwitch = dokitExtension.dokitPluginSwitch;
            this.mSlowMethodSwitch = dokitExtension.slowMethodSwitch;
            this.mThresholdTime = dokitExtension.thresholdTime;
            mPackageNames.clear();
            for (String packageName : dokitExtension.packageNames) {
                mPackageNames.add(packageName.replaceAll("\\.", "/"));
            }
        }
        if (appExtension != null) {
            String applicationId = appExtension.getDefaultConfig().getApplicationId().replaceAll("\\.", "/");
            if (mPackageNames.isEmpty()) {
                mPackageNames.add(applicationId);
            }
        }

    }

    public boolean ignorePackageNames(String className) {
        boolean isMatched = false;
        for (String packageName : ignorePackageNames) {
            if (className.contains(packageName)) {
                isMatched = true;
                break;
            }
        }
        return isMatched;
    }

    private String[] ignorePackageNames = new String[]{
            "com/didichuxing/doraemonkit/aop",
            "com/didichuxing/doraemonkit/kit/methodtrace",
            "com/didichuxing/doraemonkit/kit/network",
            "com/didichuxing/doraemonkit/kit/timecounter",
            "com/didichuxing/doraemonkit/okgo",
            "com/didichuxing/doraemonkit/datapick"
    };

}
