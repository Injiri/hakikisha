package org.aplusscreators.hakikisha.utils;

import android.content.Context;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeUtils {

    private static final String TAG = DateTimeUtils.class.getSimpleName();

    public static boolean beforeToday(Date date) {
        Date today = Calendar.getInstance().getTime();
        return date.before(today);
    }

    public static int isBefore(Date initialDate, Date nextDate) {

        Calendar initialCalendar = Calendar.getInstance();
        initialCalendar.setTime(initialDate);
        Calendar nextCalendar = Calendar.getInstance();
        nextCalendar.setTime(nextDate);

        if (initialCalendar.get(Calendar.YEAR) < nextCalendar.get(Calendar.YEAR)) {
            return 1;
        }

        if (initialCalendar.get(Calendar.MONTH) < nextCalendar.get(Calendar.MONTH)) {
            return 1;
        }

        if (initialCalendar.get(Calendar.DAY_OF_MONTH) < nextCalendar.get(Calendar.DAY_OF_MONTH)) {
            return 1;
        }

        if (initialCalendar.get(Calendar.HOUR_OF_DAY) < nextCalendar.get(Calendar.HOUR_OF_DAY)) {
            return 1;
        }

        if (initialCalendar.get(Calendar.MINUTE) < nextCalendar.get(Calendar.MINUTE)) {
            return 1;
        }


        return 0;
    }

    public static boolean isBeforeNow(Date date) {
        Calendar calendar = Calendar.getInstance();
        Calendar calendarDate = Calendar.getInstance();
        calendarDate.setTime(date);

        int nowHour = calendar.get(Calendar.HOUR_OF_DAY);
        int nowMinute = calendar.get(Calendar.MINUTE);

        int dateHour = calendarDate.get(Calendar.HOUR_OF_DAY);
        int dateMinute = calendarDate.get(Calendar.MINUTE);

        if (nowHour > dateHour) {
            return true;
        }

        if (nowHour == dateHour) {
            return nowMinute > dateMinute;
        }

        return false;
    }

    public static boolean isAfterToday(Date date) {
        Date today = Calendar.getInstance().getTime();
        return date.after(today);
    }

    public static boolean isToday(Date date) {
        Calendar calendarDate = Calendar.getInstance();
        calendarDate.setTime(date);
        Calendar calendarToday = Calendar.getInstance();

        int month = calendarToday.get(Calendar.MONTH);
        int year = calendarToday.get(Calendar.YEAR);
        int dayOfMonth = calendarToday.get(Calendar.DAY_OF_MONTH);

        int dateMonth = calendarDate.get(Calendar.MONTH);
        int dateYear = calendarDate.get(Calendar.YEAR);
        int dateDayOfMonth = calendarDate.get(Calendar.DAY_OF_MONTH);

        return month == dateMonth && year == dateYear && dayOfMonth == dateDayOfMonth;

    }

    public static Date parseDateString(String parsableDate) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd, yyyy");
            simpleDateFormat.setLenient(false);
            return simpleDateFormat.parse(parsableDate);
        } catch (Exception ex) {
            Log.e(TAG, "parseDateString: " + ex);
            return null;
        }
    }

    public static Date parseDateLong(long parsableDate) {
        Date date = new Date();
        date.setTime(parsableDate);

        return date;
    }

    public static boolean isBeforeToday(Date date) {
        Calendar todayCalendar = Calendar.getInstance();
        Calendar calendarDate = Calendar.getInstance();
        calendarDate.setTime(date);

        int month = calendarDate.get(Calendar.MONTH);
        int year = calendarDate.get(Calendar.YEAR);
        int dayOfMonth = calendarDate.get(Calendar.DAY_OF_MONTH);

        int todayMonth = todayCalendar.get(Calendar.MONTH);
        int todayYear = todayCalendar.get(Calendar.YEAR);
        int today = todayCalendar.get(Calendar.DAY_OF_MONTH);

        if (todayYear > year) {
            return true;
        }

        if (todayYear < year) {
            return false;
        }

        if (todayMonth > month) {
            return true;
        }

        if (todayMonth < month) {
            return false;
        }

        if (today > dayOfMonth) {
            return true;
        }

        if (today < dayOfMonth) {
            return false;
        }

        return false;
    }

    public static Calendar getMinutesFromNow(Calendar currentTime, int minutes) {
        currentTime.add(Calendar.MINUTE, minutes);
        return currentTime;
    }

    public static String getTimeAM_PM(Calendar calendar, Context context) {
        String today = "";

        String AM_PM = "";
        boolean isAm = calendar.get(Calendar.AM_PM) == Calendar.AM;
        boolean isPm = calendar.get(Calendar.AM_PM) == Calendar.PM;

        AM_PM = isAm ? "AM" : "PM";

        String hour = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
        int minute = calendar.get(Calendar.MINUTE);

        String minuteRepresentation = formartMinute(minute);

        today = String.format(context.getResources().getConfiguration().locale, "%s:%s %s", hour, minuteRepresentation, AM_PM);

        return today;

    }

    private static String formartMinute(int minute) {
        switch (minute) {
            case 0:
                return "00";
            case 1:
                return "01";
            case 2:
                return "02";
            case 3:
                return "03";
            case 4:
                return "04";
            case 5:
                return "05";
            case 6:
                return "06";
            case 7:
                return "07";
            case 8:
                return "08";
            case 9:
                return "09";
            default:
                return Integer.toString(minute);
        }
    }
}
