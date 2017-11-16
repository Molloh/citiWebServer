package cn.edu.nju.polaris.util;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by zhangzy on 2017/11/10 下午9:03
 * 操作日期相关的工具类
 */
public class DateHelper {

    public static String getCurrentMonth(){
        Calendar cal=Calendar.getInstance();
        int month=cal.get(Calendar.MONTH)+1;
        int year=cal.get(Calendar.YEAR);


        String result="";
        if(month<10){
            result=String.valueOf(year)+"-0"+String.valueOf(month);
        }else{
            result=String.valueOf(year)+"-"+String.valueOf(month);
        }
        return result;
    }

    /**
     * 把具体一个日期转换为一个期间
     * @param date  "2010-01-01"
     * @return
     */
    public static String DateToMonth(String date){
        return date.substring(0,7);
    }

    /**
     * 把具体一个日期转换为带时分秒的时间戳
     * @param date
     * @return
     */
    public static Timestamp DateToTimeStamp(String date){
        String dateTime=date+" 13:00:00";
        return Timestamp.valueOf(dateTime);
    }

    public static void main(String[] args) {
        System.out.println(getCurrentMonth());
        System.out.println(DateToMonth("2009-10-11"));
        System.out.println(Timestamp.valueOf("2010-02-03 18:00:00"));
        System.out.println(DateToTimeStamp("2010-10-11"));
        String date="2017-11-01";
        System.out.println(date.contains(getCurrentMonth()));
    }
}
