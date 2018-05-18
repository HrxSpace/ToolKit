package com.example.mydemo;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by hrx on 2016/11/26.
 * Timer工具类，用来统一管理TimerTask
 */

public class TimerUtils {
    private static TimerUtils instance;
    private Map<String, TimerTask> mTaskMap;
    private Timer mTimer;

    private TimerUtils() {
        mTimer = new Timer();
        mTaskMap = new HashMap<>();
    }

    public static TimerUtils getInstance() {
        if (instance == null) {
            synchronized (TimerUtils.class) {
                if (instance == null) {
                    instance = new TimerUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 添加TimerTask
     *
     * @param timerTask 事件
     * @param delay     延迟
     * @param period    重复间隔
     * @param tag       标记
     */
    public synchronized void addTimerTask(TimerTask timerTask, long delay, long period, String tag) {
        boolean isContain = mTaskMap.containsKey(tag);//是否包含这个key
        if (isContain) {//清除旧的task，开启新的task
            TimerTask task = mTaskMap.get(tag);
            if (task != null) {
                task.cancel();
                mTaskMap.remove(tag);
            }
        }
        mTimer.scheduleAtFixedRate(timerTask, delay, period);
        mTaskMap.put(tag, timerTask);
    }

    /**
     * 添加TimerTask
     *
     * @param timerTask 事件
     * @param delay     延迟
     * @param tag       标记
     */
    public void addTimerTask(TimerTask timerTask, long delay, String tag) {
        boolean isContain = mTaskMap.containsKey(tag);//是否包含这个key
        if (isContain) {//清除旧的task，开启新的task
            TimerTask task = mTaskMap.get(tag);
            if (task != null) {
                task.cancel();
                mTaskMap.remove(tag);
            }
        }
        mTaskMap.put(tag, timerTask);
        mTimer.schedule(timerTask, delay);
    }

    /**
     * 添加TimerTask，如果之前存在该TASK，则不替换
     *
     * @param timerTask 事件
     * @param delay     延迟
     * @param tag       标记
     */
    public void addTimerTaskNoReplace(TimerTask timerTask, long delay, String tag) {
        boolean isContain = mTaskMap.containsKey(tag);//是否包含这个key
        if (!isContain) {//存在则跳过
            mTimer.schedule(timerTask, delay);
            mTaskMap.put(tag, timerTask);
        }

    }

    /**
     * 取消指定task
     *
     * @param tag
     */
    public void cancelTask(String tag) {
        TimerTask task = mTaskMap.get(tag);
        if (task != null) {
            mTaskMap.remove(tag);
            task.cancel();
        }
    }

    /**
     * 取消所有task，包括整个类销毁
     */
    public void cancel() {
        if (mTaskMap != null) {
            for (Map.Entry<String, TimerTask> entry : mTaskMap.entrySet()) {
                TimerTask task = entry.getValue();
                if (task != null) {
                    task.cancel();
                }
            }
            mTaskMap.clear();
            mTaskMap = null;
        }
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        if (instance != null) {
            instance = null;
        }
    }
}
