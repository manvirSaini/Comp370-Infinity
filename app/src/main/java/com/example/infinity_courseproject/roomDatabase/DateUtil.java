package com.example.infinity_courseproject.roomDatabase;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String getFullTime(long time) {
        if (time < 10) {
            return "0" + time;
        } else {
            return time + "";
        }
    }


    public static String getStringDate(Long ms) {
        int day = (int) (ms / 1000 / 60 / 60 / 24);
        int hour = (int) (ms / 1000 / 60 / 60 % 24);
        int minute = (int) (ms / 1000 / 60 % 60);
        int second = (int) (ms / 1000 % 60);
        String dsd = (day + " days " + hour + " hours " + minute + " minutes " + second + " seconds ");
        return dsd;
    }


    public static String dateToStamp(String s) throws ParseException {
        String res;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    public static String stampToDate(String s) {
        String res;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = Long.parseLong(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
}