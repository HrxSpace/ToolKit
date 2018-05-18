package com.yaxon.hudmain.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by hrx on 2016/10/4.
 * 通用工具
 */
public class Utils {
    private static Utils instance;

    private Utils() {
    }

    public static Utils getInstance() {
        if (instance == null) {
            synchronized (Utils.class) {
                if (instance == null) {
                    instance = new Utils();
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

    /**
     * 获取编译信息
     *
     * @param context 上下文
     * @return 第0位--日期，第1位--时间，第2位--版本名
     */
    public String getBuildInfo(Context context) {
        String data = "";
        try {
            InputStream open = context.getApplicationContext().getAssets().open("buildInfo/buildInfo.txt");
            InputStreamReader isr = new InputStreamReader(open);
            BufferedReader br = new BufferedReader(isr);
            data = br.readLine();
            br.close();
            isr.close();
            open.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
    /**
     * 修改文件内容
     *
     * @param filePath 文件路径
     * @param content 修改的内容
     * @param append 是否追加
     * @return 0--成功，1--失败
     */
    public int changeFileContent(String filePath, String content, boolean append) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                boolean isMkdirs = file.mkdirs();
                boolean isCreate = file.createNewFile();
                if (!isMkdirs || !isCreate) {
                    return 1;
                }
            }
            FileOutputStream fos = new FileOutputStream(filePath, append);
            fos.write(content.getBytes("GBK"));
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取文件的内容
     *
     * @param filePath 文件路径
     * @return 内容
     */
    public String getFileContent(String filePath) {
        StringBuilder content = new StringBuilder();
        try {
            //读取文件的编码格式
            FileInputStream fis = new FileInputStream(new File(filePath));
            int pre = (fis.read() << 8) + fis.read();
            String code = "US-ASCII";
            switch (pre) {
                case 0xefbb:
                    if (fis.read() == 0xbf) {
                        code = "UTF-8";
                    }
                    break;
                case 0xfffe:
                    code = "Unicode";
                    break;
                case 0xfeff:
                    code = "UTF-16BE";
                    break;
                default:
                    code = "GBK";   // "US-ASCII"
                    break;
            }
            fis.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), code));
            String str;
            while ((str = br.readLine()) != null) {
                content.append(str);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}
