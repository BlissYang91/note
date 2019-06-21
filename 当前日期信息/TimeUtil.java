package com.bliss.yang.nicewords.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author YangTianFu
 * @date 2019/6/21  10:51
 * @description 获取当前日期信息
 */
public class TimeUtil {
     private static long mFirstClickTime = 0L;

    public static String getTodayInfo() {
        String todayStr;
        Date today = new Date();
        DateFormat df = new SimpleDateFormat("yyyy年MM月dd日 ", Locale.CHINA);
        todayStr = df.format(today) + getWeek(today);
        return todayStr;
    }

    public static String getWeek(Date date){
        String[] weeks = {"周日","周一","周二","周三","周四","周五","周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if(week_index<0){
            week_index = 0;
        }
        return weeks[week_index];
    }

    public static boolean checkDoubleClick() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - mFirstClickTime > 1000) {
            mFirstClickTime = currentTime;
            return false;
        } else {
            return true;
        }
    }

}
