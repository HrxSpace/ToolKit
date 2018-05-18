package com.yaxon.hudandroid.utils;


import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hrx on 2016/9/18.
 * 单位转换工具
 */
public class UnitUtils {
    /**
     * 将秒转为 天时分
     *
     * @param sec 秒
     * @return 格式化后的时间
     */
    public static String sec2HourMin(int sec) {
        int day = 0;
        int hour;
        int min;
        hour = sec / 3600;
        min = (sec - hour * 3600) / 60;
        while (hour >= 24) {
            day++;
            hour = hour - 24;
        }
        if (day != 0) {
            return day + "天" + hour + "时" + min + "分";
        }
        if (hour != 0) {
            return hour + "时" + min + "分";
        }
        return min + "分";
    }

    /**
     * 格式化时间为HH:MM
     *
     * @return 时间
     */
    public static String formatTime_H_M(long time) {
        SimpleDateFormat sdFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());//24小时制
        return sdFormat.format(time);
    }

    /**
     * 将米转为千米，保留两位小数
     *
     * @param distance 米
     * @return 千米
     */
    public static String meter2Km(float distance) {
        DecimalFormat df = new DecimalFormat("0.00");
        return String.valueOf(df.format(distance / 1000));
    }

    /**
     * 计算从当前位置到目标位置的距离,保留两位小数,单位千米
     *
     * @return 距离
     */
    public static String calculateLineDistanceFromCur(LatLng from, LatLng to) {
        float v = AMapUtils.calculateLineDistance(from, to);
        return UnitUtils.meter2Km(v);
    }

    /**
     * 汉字数字转阿拉伯数字
     *
     * @param chineseNumber 汉字数字
     * @return 阿拉伯数字
     */
    public static int chineseNumber2Int(String chineseNumber) {
        int result = 0;//存放结果
        int temp = 1;//存放临时的一位数
        int count = 0;//判断是否有chArr
        char[] cnArr = new char[]{'一', '二', '三', '四', '五', '六', '七', '八', '九'};
        char[] chArr = new char[]{'十', '百', '千', '万', '亿'};
        for (int i = 0; i < chineseNumber.length(); i++) {
            boolean b = true;//判断是否是chArr
            char c = chineseNumber.charAt(i);
            for (int j = 0; j < cnArr.length; j++) {//非单位，即数字
                if (c == cnArr[j]) {
                    if (0 != count) {//添加下一个单位之前，先把上一个单位值添加到结果中
                        result += temp;
                        count = 0;
                    }
                    // 下标+1，就是对应的值
                    temp = j + 1;
                    b = false;
                    break;
                }
            }
            if (b) {//单位{'十','百','千','万','亿'}
                for (int j = 0; j < chArr.length; j++) {
                    if (c == chArr[j]) {
                        switch (j) {
                            case 0:
                                temp *= 10;
                                break;
                            case 1:
                                temp *= 100;
                                break;
                            case 2:
                                temp *= 1000;
                                break;
                            case 3:
                                temp *= 10000;
                                break;
                            case 4:
                                temp *= 100000000;
                                break;
                            default:
                                break;
                        }
                        count++;
                    }
                }
            }
            if (i == chineseNumber.length() - 1) {//遍历到最后一个字符
                result += temp;
            }
        }
        return result;
    }

    /**
     * 通过正则把字符串中第和个之间的数字提取出来，并过滤数字大于15部分
     */
    public static int getNumFromDiAndGe(String str) {
        int result = -1;
        String chineseNum = "";
        Pattern p = Pattern.compile("第(.*?)个");
        Matcher m = p.matcher(str);
        if (m.find()) {
            chineseNum = m.group(1);
        }
        //判断是否存在禁止出现字符
        boolean findban = chineseNum.contains("百") || chineseNum.contains("千") || chineseNum.contains("万") || chineseNum.contains("亿");
        if (findban) {
            return result;
        }
        int num = chineseNumber2Int(chineseNum);
        if (num > 15) {
            return result;
        } else {
            return num;
        }
    }

    /**
     * byte转mb
     *
     * @param b byte
     */
    public static String byte2mb(long b) {
        DecimalFormat df = new DecimalFormat("0.00");
        return String.valueOf(df.format(b / 1024 / 1024));
    }
}