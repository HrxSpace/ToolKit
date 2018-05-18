package com.example.mydemo;

import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by hrx on 2017/2/14.
 * 储存路径操作类
 */
public class StoragePathUtil {

    private String TAG = "StoragePathUtil";
    private static StoragePathUtil instance = null;
    private Context mContext;

    private String externalPath = "";//外部储存卡路径
    private String innerPath = "";//内部储存卡路径

    public static StoragePathUtil getInstance() {
        if (instance == null) {
            synchronized (StoragePathUtil.class) {
                if (instance == null) {
                    instance = new StoragePathUtil();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        mContext = context.getApplicationContext();
    }

    /**
     * 创建在sd卡一个新文件夹
     *
     * @param path       例如：/data/data
     * @param isExternal true--外部sd卡，false--内部储存
     * @return 创建的文件夹路径
     */
    public String newFolder(String path, boolean isExternal) {
        String filePath;
        String sdcardPath = getSdcardPath(isExternal);
        Log.d(TAG, sdcardPath);
        File file_sd = new File(sdcardPath + path);
        filePath = file_sd.getPath();
        if (!file_sd.exists()) {
            boolean isMkdirs = file_sd.mkdirs();
            return isMkdirs ? filePath : null;
        }
        return filePath;
    }

    /**
     * 获取储存路径
     *
     * @param isExternal true--外部sd卡，false--内部储存
     * @return 储存路径
     */
    public String getSdcardPath(boolean isExternal) {
        //如果已经有记录就没必要再去反射获取
        String sdPath;
        if (isExternal) {
            if (externalPath != null && !"".equals(externalPath)) {
                return externalPath;
            }
            externalPath = getStoragePath(true);
            externalPath = externalPath != null ? externalPath : Environment.getExternalStorageDirectory().getPath();
            sdPath = externalPath;
        } else {
            if (innerPath != null && !"".equals(innerPath)) {
                return innerPath;
            }
            innerPath = getStoragePath(false);
            innerPath = innerPath != null ? innerPath : Environment.getExternalStorageDirectory().getPath();
            sdPath = innerPath;
        }
        Log.d(TAG, "sdPath: " + sdPath);
        return sdPath;
    }

    /**
     * 获取sd卡路径
     *
     * @param isExternal false--内置储存卡，true--外置储存卡
     * @return sd卡路径
     */
    private String getStoragePath(boolean isExternal) {
        StorageManager mStorageManager = (StorageManager) mContext.getSystemService(Context.STORAGE_SERVICE);
        Class<?> storageVolumeClazz = null;
        try {
            storageVolumeClazz = Class.forName("android.os.storage.StorageVolume");
            Method getVolumeList = mStorageManager.getClass().getMethod("getVolumeList");
            Method getPath = storageVolumeClazz.getMethod("getPath");
            Method isRemovable = storageVolumeClazz.getMethod("isRemovable");
            Object result = getVolumeList.invoke(mStorageManager);
            final int length = Array.getLength(result);
            for (int i = 0; i < length; i++) {
                Object storageVolumeElement = Array.get(result, i);
                String path = (String) getPath.invoke(storageVolumeElement);
                boolean removable = (Boolean) isRemovable.invoke(storageVolumeElement);
                if (isExternal == removable) {
                    File file = new File(path);
                    long totalSpace = file.getTotalSpace();
                    if (totalSpace == 0) {
                        return null;
                    }
                    return path;
                }
            }
        } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


}
