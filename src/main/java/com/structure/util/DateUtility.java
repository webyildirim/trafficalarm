package com.structure.util;

import java.math.BigDecimal;

import java.sql.Timestamp;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;


public class DateUtility
{
    public static final int WEEKDAY_MONDAY = 0;
    public static final int WEEKDAY_TUESDAY = 1;
    public static final int WEEKDAY_WEDNESDAY = 2;
    public static final int WEEKDAY_THURSDAY = 3;
    public static final int WEEKDAY_FRIDAY = 4;
    public static final int WEEKDAY_SATURDAY = 5;
    public static final int WEEKDAY_SUNDAY = 6;

    public static final long ONE_MINUTE_IN_MILLISECONDS = 1000 * 60;
    public static final long HALF_HOUR_IN_MILLISECONDS = ONE_MINUTE_IN_MILLISECONDS * 30;
    public static final long ONE_HOUR_IN_MILLISECONDS = ONE_MINUTE_IN_MILLISECONDS * 60;
    public static final long ONE_DAY_IN_MILLISECONDS = ONE_HOUR_IN_MILLISECONDS * 24;
    public static final long ONE_MONTH_IN_MILLISECONDS = ONE_DAY_IN_MILLISECONDS * 30;

    public static final String TIMEZONE_UTC = "UTC";
    public static final String TIMEZONE_TR = "Europe/Athens";

    public static final String DEFAULT_TZ_ID = TIMEZONE_TR; //TimeZone.getDefault().getID();

    //for default system timezoneid

    public static Date clearTime(Date date)
    {
        return clearTime(date, DEFAULT_TZ_ID);
    }

    //for any specific timezoneId

    public static Date clearTime(Date date, String timeZoneId)
    {
        Calendar customCalendar = getCalendarInTimeZone(timeZoneId);
        customCalendar.setTime(date);
        customCalendar.set(Calendar.HOUR_OF_DAY, 0);
        customCalendar.set(Calendar.MINUTE, 0);
        customCalendar.set(Calendar.SECOND, 0);
        customCalendar.set(Calendar.MILLISECOND, 0);

        return customCalendar.getTime();
    }

    public static Date clearDate(Date date)
    {
        return clearDate(date, DEFAULT_TZ_ID);
    }

    public static Date clearDate(Date date, String timeZoneId)
    {
        Calendar customCalendar = getCalendarInTimeZone(timeZoneId);

        customCalendar.setTime(date);
        customCalendar.clear();

        return customCalendar.getTime();
    }

    //for default system timezoneid

    public static Date addDaysToDate(Date date, int days)
    {
        return addDaysToDate(date, days, DEFAULT_TZ_ID);
    }

    //for any specific timezoneId

    public static Date addDaysToDate(Date date, int days, String timeZoneId)
    {
        Calendar customCalendar = getCalendarInTimeZone(timeZoneId);
        customCalendar.setTime(date);
        customCalendar.add(Calendar.DATE, days);

        return customCalendar.getTime();

    }

    //for default system timezoneid

    public static Date addMonthsToDate(Date date, int months)
    {
        return addMonthsToDate(date, months, DEFAULT_TZ_ID);
    }

    //for any specific timezoneId

    public static Date addMonthsToDate(Date date, int months, String timeZoneId)
    {
        Calendar customCalendar = getCalendarInTimeZone(timeZoneId);
        customCalendar.setTime(date);
        customCalendar.add(Calendar.MONTH, months);

        return customCalendar.getTime();

    }

    //for default system timezoneid

    public static Date addYearsToDate(Date date, int years)
    {
        return addYearsToDate(date, years, DEFAULT_TZ_ID);
    }

    //for any specific timezoneId

    public static Date addYearsToDate(Date date, int years, String timeZoneId)
    {
        Calendar customCalendar = getCalendarInTimeZone(timeZoneId);
        customCalendar.setTime(date);
        customCalendar.add(Calendar.YEAR, years);

        return customCalendar.getTime();

    }

    //for default system timezoneid

    public static Date addMilliSecondsToDate(Date date, long milliseconds)
    {
        return addMilliSecondsToDate(date, milliseconds, DEFAULT_TZ_ID);
    }

    //for any specific timezoneId

    public static Date addMilliSecondsToDate(Date date, long milliseconds, String timeZoneId)
    {
        Calendar customCalendar = getCalendarInTimeZone(timeZoneId);
        customCalendar.setTimeInMillis(date.getTime() + milliseconds);

        return customCalendar.getTime();

    }

    public static boolean isBetween(Date beginDate, Date endDate, Date checkedDate)
    {
        if (beginDate == null || endDate == null)
            return true;

        if (!checkedDate.before(beginDate) && !checkedDate.after(endDate))
            return true;

        return false;
    }

    //get olarak degistirilmesi lazim
    //metod adi olarak anlasilir olmasi lazim

    public static Date setFirstDayOfWeek(Date date, int dateFlag)
    {
        for (int i = 0; i <= 6; i++)
        {
            if (dateFlag == getDayOfWeek(addDaysToDate(date, i)))
                return addDaysToDate(date, i);
        }

        return date;
    }

    public static boolean isNull(Date date)
    {
        if (date == null)
            return true;

        return false;
    }

    public static Date getToday()
    {
        return getToday(DEFAULT_TZ_ID);
    }

    public static Date getToday(String timeZoneId)
    {
        Calendar customCalendar = getCalendarInTimeZone(timeZoneId);

        return customCalendar.getTime();
    }

    //for default system timezoneid

    public static int getDayOfWeek(Date date)
    {
        return getDayOfWeek(date, DEFAULT_TZ_ID);
    }

    //for any specific timezoneId

    public static int getDayOfWeek(Date date, String timeZoneId)
    {
        Calendar customCalendar = getCalendarInTimeZone(timeZoneId);
        customCalendar.setTime(date);
        return customCalendar.get(Calendar.DAY_OF_WEEK);
    }

    //for default system timezoneid

    public static int getDay(Date date)
    {
        return getDay(date, DEFAULT_TZ_ID);
    }

    //for any specific timezoneId

    public static int getDay(Date date, String timeZoneId)
    {
        Calendar customCalendar = getCalendarInTimeZone(timeZoneId);
        customCalendar.setTime(date);
        return customCalendar.get(Calendar.DAY_OF_MONTH);
    }

    //for default system timezoneid

    public static int getMonth(Date date)
    {
        return getMonth(date, DEFAULT_TZ_ID);
    }

    //for any specific timezoneId

    public static int getMonth(Date date, String timeZoneId)
    {
        Calendar customCalendar = getCalendarInTimeZone(timeZoneId);
        customCalendar.setTime(date);
        return customCalendar.get(Calendar.MONTH);
    }

    //for default system timezoneid

    public static int getYear(Date date)
    {
        return getYear(date, DEFAULT_TZ_ID);
    }

    //for any specific timezoneId

    public static int getYear(Date date, String timeZoneId)
    {
        Calendar customCalendar = getCalendarInTimeZone(timeZoneId);
        customCalendar.setTime(date);
        return customCalendar.get(Calendar.YEAR);
    }

    //for default system timezoneid

    public static int getHour(Date date)
    {
        return getHour(date, DEFAULT_TZ_ID);
    }

    //for any specific timezoneId

    public static int getHour(Date date, String timeZoneId)
    {
        Calendar customCalendar = getCalendarInTimeZone(timeZoneId);
        customCalendar.setTime(date);
        return customCalendar.get(Calendar.HOUR_OF_DAY);
    }

    //for default system timezoneid

    public static int getMinute(Date date)
    {
        return getMinute(date, DEFAULT_TZ_ID);
    }

    //for any specific timezoneId

    public static int getMinute(Date date, String timeZoneId)
    {
        Calendar customCalendar = getCalendarInTimeZone(timeZoneId);
        customCalendar.setTime(date);
        return customCalendar.get(Calendar.MINUTE);
    }

    //for default system timezoneid

    public static int getSecond(Date date)
    {
        return getSecond(date, DEFAULT_TZ_ID);
    }

    //for any specific timezoneId

    public static int getSecond(Date date, String timeZoneId)
    {
        Calendar customCalendar = getCalendarInTimeZone(timeZoneId);
        customCalendar.setTime(date);
        return customCalendar.get(Calendar.SECOND);
    }

    //for default system timezoneid

    public static int getNumberOfDaysInMonth(Date date)
    {
        return getNumberOfDaysInMonth(date, DEFAULT_TZ_ID);
    }

    //for any specific timezoneId

    public static int getNumberOfDaysInMonth(Date date, String timeZoneId)
    {
        Calendar customCalendar = getCalendarInTimeZone(timeZoneId);
        customCalendar.setTime(date);
        return customCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static String convertDateToString(Date date)
    {
        return convertDateToString(date, "dd.MM.yyyy");
    }

    public static String convertDateToString(Date date, String dateFormat)
    {
        return convertDateToString(date, dateFormat, TimeZone.getDefault().getID());
    }

    public static String convertDateToString(Date date, String dateFormat, String timeZoneId)
    {
        if (date == null)
            return "";

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setTimeZone(TimeZone.getTimeZone(timeZoneId));

        return sdf.format(date);
    }

    public static String convertDateToString(Date date, String dateFormat, String timeZoneId, Locale locale)
    {
        if (date == null)
            return "";

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, locale);
        sdf.setTimeZone(TimeZone.getTimeZone(timeZoneId));

        return sdf.format(date);
    }

    public static Date convertStringToDate(String dateAsString, String dateFormat) throws ParseException
    {
        return convertStringToDate(dateAsString, dateFormat, TimeZone.getDefault().getID());
    }

    public static Date convertStringToDate(String dateAsString, String dateFormat, String timeZoneId) throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

        sdf.setTimeZone(TimeZone.getTimeZone(timeZoneId));

        return sdf.parse(dateAsString);
    }

    //for default system timezoneId

    public static Date setYearOfDate(Date date, int year)
    {
        return setYearOfDate(date, year, DEFAULT_TZ_ID);
    }

    //for any specific timezoneId

    public static Date setYearOfDate(Date date, int year, String timeZoneId)
    {
        Calendar customCalendar = getCalendarInTimeZone(timeZoneId);
        customCalendar.setTime(date);
        customCalendar.set(Calendar.YEAR, year);

        return customCalendar.getTime();
    }

    //for default system timezoneId

    public static Date setMonthOfDate(Date date, int month)
    {
        return setMonthOfDate(date, month, DEFAULT_TZ_ID);
    }

    //for any specific timezoneId

    public static Date setMonthOfDate(Date date, int month, String timeZoneId)
    {
        Calendar customCalendar = getCalendarInTimeZone(timeZoneId);
        customCalendar.setTime(date);
        customCalendar.set(Calendar.MONTH, month);

        return customCalendar.getTime();
    }

    //for default system timezoneId

    public static Date setDayOfDate(Date date, int day)
    {
        return setDayOfDate(date, day, DEFAULT_TZ_ID);
    }

    //for any specific timezoneId

    public static Date setDayOfDate(Date date, int day, String timeZoneId)
    {
        Calendar customCalendar = getCalendarInTimeZone(timeZoneId);
        customCalendar.setTime(date);
        customCalendar.set(Calendar.DATE, day);

        return customCalendar.getTime();
    }

    //for default system timezoneId

    public static Date setHourOfDate(Date date, int hour)
    {
        return setHourOfDate(date, hour, DEFAULT_TZ_ID);
    }

    //for any specific timezoneId

    public static Date setHourOfDate(Date date, int hour, String timeZoneId)
    {
        Calendar customCalendar = getCalendarInTimeZone(timeZoneId);
        customCalendar.setTime(date);
        customCalendar.set(Calendar.HOUR_OF_DAY, hour);

        return customCalendar.getTime();
    }

    //for default system timezoneId

    public static Date setMinuteOfDate(Date date, int minute)
    {
        return setMinuteOfDate(date, minute, DEFAULT_TZ_ID);
    }

    //for any specific timezoneId

    public static Date setMinuteOfDate(Date date, int minute, String timeZoneId)
    {
        Calendar customCalendar = getCalendarInTimeZone(timeZoneId);
        customCalendar.setTime(date);
        customCalendar.set(Calendar.MINUTE, minute);

        return customCalendar.getTime();
    }

    public static int getDaysBetween(Date dateBegin, Date dateEnd)
    {
        long diff = getMillisBetween(dateBegin, dateEnd);

        //added for DaylightSaving
        diff += ONE_HOUR_IN_MILLISECONDS;

        //added for leap seconds
        diff += ONE_MINUTE_IN_MILLISECONDS;

        BigDecimal days = new BigDecimal(diff / ONE_DAY_IN_MILLISECONDS);

        return days.setScale(0, BigDecimal.ROUND_DOWN).intValue();
    }

    public static int getHoursBetween(Date dateBegin, Date dateEnd)
    {
        long diff = getMillisBetween(dateBegin, dateEnd);

        BigDecimal hours = new BigDecimal(diff / ONE_HOUR_IN_MILLISECONDS);

        return hours.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
    }

    public static int getMinutesBetween(Date dateBegin, Date dateEnd)
    {
        long diff = getMillisBetween(dateBegin, dateEnd);

        BigDecimal minutes = new BigDecimal(diff / ONE_MINUTE_IN_MILLISECONDS);

        return minutes.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
    }

    public static int getSecondsBetween(Date dateBegin, Date dateEnd)
    {
        long diff = getMillisBetween(dateBegin, dateEnd);

        BigDecimal seconds = new BigDecimal(diff / 1000);

        return seconds.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
    }

    public static long getMillisBetween(Date dateBegin, Date dateEnd)
    {
        return Math.abs(dateEnd.getTime() - dateBegin.getTime());
    }

    public static long getTimeZoneOffsetAsMins(Date date, String timeZoneId)
    {
        return getTimeZoneOffset(date, timeZoneId) / ONE_MINUTE_IN_MILLISECONDS;
    }

    public static long getTimeZoneOffsetAsHour(Date date, String timeZoneId)
    {
        return getTimeZoneOffset(date, timeZoneId) / ONE_HOUR_IN_MILLISECONDS;
    }

    public static int getTimeZoneOffset(Date date, String timeZoneId)
    {
        TimeZone tz = TimeZone.getTimeZone(timeZoneId);

        return tz.getOffset(date.getTime());
    }

    public static Calendar getCalendarInTimeZone(String timeZoneId)
    {
        return new GregorianCalendar(TimeZone.getTimeZone(timeZoneId));
    }

    public static Calendar getCalendarOfDate(Date date, String timeZoneId)
    {
        Calendar customCalendar = getCalendarInTimeZone(timeZoneId);
        customCalendar.setTime(date);
        
        return customCalendar;
    }

    public static Calendar getCalendarOfDate(Date date)
    {
        Calendar customCalendar = getCalendarInTimeZone(DEFAULT_TZ_ID);
        customCalendar.setTime(date);
        
        return customCalendar;
    }

    /*
	 * Asagidaki timezone cevrimlerini raporlar haricinde kullanmayin
	 * Ilerleyen asamalarda raporlar icerisinden de kaldirilmali
	 * yerlerine convertdatetostring kullanilmali
	 */

    public static Timestamp convertLocalToUTC(Date date) throws ParseException
    {
        if (date == null)
            return null;

        long offset = DateUtility.getTimeZoneOffset(date, DateUtility.DEFAULT_TZ_ID);
        date = DateUtility.addMilliSecondsToDate(date, -1 * offset, DateUtility.DEFAULT_TZ_ID);

        return new Timestamp(date.getTime());
    }

    public static Timestamp convertUTCtoLocalTime(Date date) throws ParseException
    {
        if (date == null)
            return null;

        long offset = DateUtility.getTimeZoneOffset(date, DateUtility.DEFAULT_TZ_ID);
        date = DateUtility.addMilliSecondsToDate(date, +1 * offset, DateUtility.DEFAULT_TZ_ID);

        return new Timestamp(date.getTime());
    }

    /*
	 * Yukaridaki timezone cevrimlerini raporlar haricinde kullanmayin
	 * Ilerleyen asamalarda raporlar icerisinden de kaldirilmali
	 * yerlerine convertdatetostring kullanilmali
	 */

    public static Date getNextDay(Date date)
    {
        return getNextDay(date, DEFAULT_TZ_ID);
    }

    public static Date getNextDay(Date date, String timeZoneId)
    {
        Date today = getToday();

        date = DateUtility.setYearOfDate(date, DateUtility.getYear(today, timeZoneId));
        date = DateUtility.setMonthOfDate(date, DateUtility.getMonth(today, timeZoneId));
        date = DateUtility.setDayOfDate(date, DateUtility.getDay(today, timeZoneId));
        date = DateUtility.addDaysToDate(today, 1, timeZoneId);

        return clearTime(date, timeZoneId);
    }

    public static boolean isWeekend(Date date)
    {
        return isWeekend(date, DEFAULT_TZ_ID);
    }

    private static boolean isWeekend(Date date, String timeZoneId)
    {
        Calendar customCalendar = getCalendarInTimeZone(timeZoneId);
        customCalendar.setTime(date);

        return (customCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY | customCalendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
    }

    //get olarak degistirilmesi lazim
    //metod adi olarak anlasilir olmasi lazim

    public static Date setFirstDayOfMonth(Date date)
    {
        Calendar customCalendar = getCalendarInTimeZone(DEFAULT_TZ_ID);

        customCalendar.setTime(date);
        customCalendar.set(Calendar.DATE, 1);

        return customCalendar.getTime();
    }

    //get olarak degistirilmesi lazim
    //metod adi olarak anlasilir olmasi lazim

    public static Date setLastDayOfMonth(Date date)
    {
        Calendar customCalendar = getCalendarInTimeZone(DEFAULT_TZ_ID);

        customCalendar.setTime(date);
        customCalendar.set(Calendar.DATE, getNumberOfDaysInMonth(date));

        return customCalendar.getTime();
    }
    
    public static XMLGregorianCalendar getToDayAsXMLGregorianCalendar() throws DatatypeConfigurationException
    {
        return convertDateToXMLGregorianCalendar(getToday());
    }
    
    public static XMLGregorianCalendar convertDateToXMLGregorianCalendar(Date date) throws DatatypeConfigurationException
    {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(date);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
    }
}
