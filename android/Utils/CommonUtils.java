package com.example.mydemo;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 * Created by hrx on 2016/10/4.
 * 通用工具
 */
public class CommonUtils {
    private static CommonUtils instance;

    private CommonUtils() {
    }

    public static CommonUtils getInstance() {
        if (instance == null) {
            synchronized (CommonUtils.class) {
                if (instance == null) {
                    instance = new CommonUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 获取当前系统版本号
     *
     * @return 系统版本号
     */
    public int getSDKVersionNumber() {
        int sdkVersion;
        try {
            sdkVersion = Integer.valueOf(Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            sdkVersion = 0;
        }
        return sdkVersion;
    }

    /**
     * 获取包名
     *
     * @param context 上下文
     * @return 包名
     */
    public String getPkName(Context context) {
        return context.getApplicationContext().getPackageName();
    }

    /**
     * 获取版本名
     *
     * @param context 上下文
     * @return 版本名
     */
    public String getVersionName(Context context) {
        String pkName = context.getApplicationContext().getPackageName();
        String vsName = "";
        try {
            vsName = context.getApplicationContext().getPackageManager().getPackageInfo(pkName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return vsName;
    }

    /**
     * 获取版本号
     *
     * @param context 上下文
     * @return 版本号
     */
    public int getversionCode(Context context) {
        String pkName = context.getApplicationContext().getPackageName();
        int vsvCode = 1;
        try {
            vsvCode = context.getApplicationContext().getPackageManager().getPackageInfo(pkName, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return vsvCode;
    }

}
