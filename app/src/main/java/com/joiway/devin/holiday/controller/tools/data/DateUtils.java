package com.joiway.devin.holiday.controller.tools.data;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 潘阳君 on 2015/7/23.
 * 【豆浆框架】-【工具集】
 * 【日期、日历恐惧】
 */
public class DateUtils {
    public static final String TAG = "DateUtils";
    public static final Calendar getCalender(long time) {
        Calendar c = getClearCalender();
        c.setTimeInMillis(time);
        return c;
    }

    public static final Calendar getClearCalender() {
        Calendar c = Calendar.getInstance();
        c.clear();
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return c;
    }
    public final static Date stringToDate(String time, String format){
        SimpleDateFormat sim=new SimpleDateFormat(format);
        try {
            return sim.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 格式化输出 日期
     * @param time Unix时间戳
     * @param format
     * @return
     */
    public static final String dateFormatString(long time, String format){
        return dateFormatString(new Date(time), format);
    }

    /**
     * 格式化输出 日期
     * @param date
     * @param format
     * @return
     */
    public static final String dateFormatString(Date date, String format){
        SimpleDateFormat sim=new SimpleDateFormat(format);
        return sim.format(date);
    }

    /**
     * 计算 起始日期 一共的天数
     * @return
     */
    public static final int countDaysOfDate(long startTime,long endTime){
        long aDayTime = 3600*24*1000;//一天的毫秒数
        long diff = endTime - startTime;

        //绝对值
        diff = diff<0? -diff: diff;

        return (int)(diff/aDayTime)+1;
    }

    public static final int countDaysOfDate(String startTime, String endTime, String format){
        long st,et;
        try{
            st = stringToDate(startTime,format).getTime();
            et = stringToDate(endTime,format).getTime();
            return countDaysOfDate(st,et);
        }catch(Exception e){
            e.printStackTrace();
            Log.e(TAG, "countDaysOfDate: "+e.toString(),e );
        }
        return 0;
    }

}
