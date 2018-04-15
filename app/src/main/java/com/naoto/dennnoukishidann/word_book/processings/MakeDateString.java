package com.naoto.dennnoukishidann.word_book.processings;

import java.util.Calendar;

import lombok.Cleanup;

/**
 * Created by gotounaoto on 2018/01/03.
 */

public class MakeDateString {

    public static String makeDateNow(){
        //Now
        Calendar calendar = Calendar.getInstance();
        String date_string = makeString(calendar);
        return date_string;
    }

    public static String makeDateYesterday(){
        //Yesterday
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        String date_string = makeString(calendar);
        return date_string;
    }

    public static String makeDateOneWeekBefore(){
        //8days before
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -8);
        String date_string = makeString(calendar);
        return date_string;
    }

    public static String makeDateOneMonthBefore(){
        //28days before
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -28);
        String date_string = makeString(calendar);
        return date_string;
    }

    public static String makeString(Calendar calendar){
        //ここは日にちを受け取りそれを文字列にして前しているこのクラスの中枢
        String year = String.valueOf(calendar.get(Calendar.YEAR));
        //monthはJanuaryが0
        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        StringBuffer buffer = new StringBuffer();
        buffer.append(year);
        buffer.append("-");
        buffer.append(month);
        buffer.append("-");
        buffer.append(day);
        String date_string = buffer.toString();
        return date_string;
    }
}
