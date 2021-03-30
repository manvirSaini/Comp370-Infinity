package com.example.infinity_courseproject.base;

import android.content.Context;
import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author: Paper
 * time :2019/9/18 15:03
 * desc:
 */
public class DateUtil {


    //get the current time yyyy-MM-dd
    public static String getTodayData() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//date format
        String str = df.format(new Date());// new Date() get current time
        return str;
    }

    //get the current time yyyy-MM-dd HH:mm
    public static String getTodayData_2() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//
        String str = df.format(new Date());// new Date()
        return str;
    }

    //get the current time yyyy-MM-dd HH:mm:ss
    public static String getTodayData_3() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//
        String str = df.format(new Date());// new Date()
        return str;
    }

    //get the current time，Example：10-26 19:29
    public static String getCurTime(Context context) {
        String time_refresh = DateUtils.formatDateTime(
                context,
                System.currentTimeMillis(),
                DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL
        );
        return time_refresh;
    }

    //get next month
    public static String getNextMonth() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
        String preMonth = dft.format(cal.getTime());
        return preMonth;
    }


    public static String getStandardDate(String timeStr) {

        StringBuffer sb = new StringBuffer();

        long t = Long.parseLong(timeStr);
        long time = System.currentTimeMillis() - (t * 1000);
        long mill = (long) Math.ceil(time / 1000);//

        long minute = (long) Math.ceil(time / 60 / 1000.0f);//

        long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);//

        long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);//
        if (day - 1 > 0) {
            sb.append(day + "Days");
        } else if (hour - 1 > 0) {
            if (hour >= 24) {
                sb.append("1 day");
            } else {
                sb.append(hour + "Hours");
            }
        } else if (minute - 1 > 0) {
            if (minute == 60) {
                sb.append("1 hour");
            } else {
                sb.append(minute + "Minutes");
            }
        } else if (mill - 1 > 0) {
            if (mill == 60) {
                sb.append("1 minute");
            } else {
                sb.append(mill + "second");
            }
        } else {
            sb.append("Right now");
        }
        if (!sb.toString().equals("right now")) {
            sb.append("before");
        }
        return sb.toString();
    }

    //get days of every month
    public static int getDay(int year, int month) {
        int day = 0;
        boolean flag = false;
        switch (year % 4) {
            case 0:
                flag = true;
                break;
            default:
                flag = false;
                break;
        }
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 31;
                break;
            case 2:
                if (flag) {
                    day = 29;
                } else {
                    day = 28;
                }
                break;
            default:
                day = 30;
                break;
        }
        return day;
    }

    //
    public static String ssToHMS(long totalSecond) {
        if (totalSecond < 60) {
            return "00:00:" + getFullTime(totalSecond);
        } else {
            long second = totalSecond % 60;
            long totalMinute = totalSecond / 60;
            if (totalMinute < 60) {
                return "00:" + getFullTime(totalMinute) + ":" + getFullTime(second);
            } else {
                long minute = totalMinute % 60;
                long totalHour = totalMinute / 60;
                return getFullTime(totalHour) + ":" + getFullTime(minute) + ":" + getFullTime(second);
            }
        }

    }

    //
    public static String ssToHMS2(long totalSecond) {
        if (totalSecond < 60) {
            return "00:" + getFullTime(totalSecond);
        } else {
            long second = totalSecond % 60;
            long totalMinute = totalSecond / 60;
            if (totalMinute < 60) {
                return getFullTime(totalMinute) + ":" + getFullTime(second);
            } else {
                long minute = totalMinute % 60;
                long totalHour = totalMinute / 60;
                return getFullTime(totalHour) + ":" + getFullTime(minute) + ":" + getFullTime(second);
            }
        }

    }

    /*
     *
     *
     *  10:00:01  -> xxx
     * */
    public static long formatTurnSecond(String time) {
        String s = time;
        int index1 = s.indexOf(":");
        int index2 = s.indexOf(":", index1 + 1);
        int hh = Integer.parseInt(s.substring(0, index1));
        int mi = Integer.parseInt(s.substring(index1 + 1, index2));
        int ss = Integer.parseInt(s.substring(index2 + 1));
        return hh * 60 * 60 + mi * 60 + ss;
    }


    //
    public static String getFullTime(long time) {
        if (time < 10) {
            return "0" + time;
        } else {
            return time + "";
        }
    }


    public static String type_1 = "yyyy-MM-dd";
    public static String type_2 = "yyyy-MM-dd HH:mm:ss";
    public static String type_3 = "yyyy/MM/dd HH:mm";
    public static String type_4 = "yyyy年MM月dd日";

    public static Date stringToDate(String strTime, String formatType) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }


    public static long dateTNow(String strTime1, String formatType) {
        return (new Date().getTime() - stringToDate(strTime1, formatType).getTime());
    }

    /**
     *
     * @param seconds
     * @param
     * @return
     */
    public static String timeStamp2Date(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;

    }
    /**
     *
     * @param
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String date_str, String format){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime()/1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    /**
     *
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public static String getStringDate(Long ms) {
        int day = (int) (ms / 1000 / 60 / 60 / 24);
        int hour = (int) (ms / 1000 / 60 / 60 % 24);
        int minute = (int) (ms / 1000 / 60 % 60);
        int second = (int) (ms / 1000 % 60);
        String dsd = (day + " days " + hour + " hours " + minute + " minutes " + second + " seconds ");
        return dsd;
    }

    /*
     *
     */
    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts);
        return res;
    }

    /*
     *
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
}


