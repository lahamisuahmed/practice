package com.practice.util;

import com.practice.constants.AppConstants;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.practice.constants.AppConstants.TIME_ZONE;

public class DateUtil {

    public static java.sql.Date getCurrentSqlDate() {
        return new java.sql.Date(getCurrentDate().getTime());
    }

    public static java.sql.Date convertToSqlDate(Date date) {
        return new java.sql.Date(date.getTime());
    }

    public static Date convertToDate(java.sql.Date date) {
        return new Date(date.getTime());
    }

    public static LocalDateTime convertToLocalDateTime(Date date) {
        return new Timestamp(date.getTime()).toLocalDateTime();
    }

    public static java.sql.Date getCurrentSqlDate(String timeZone) {
        return new java.sql.Date(getCurrentDate(timeZone).getTime());
    }

    public static Timestamp getCurrentSqlDatetime() {
        return new Timestamp(getCurrentDate().getTime());
    }

    public static Timestamp convertToSQLTime(Date date) {
        return new Timestamp(date.getTime());
    }

    public static Timestamp getCurrentSqlDatetime(String timeZone) {
        return new Timestamp(getCurrentDate(timeZone).getTime());
    }

    public static java.sql.Date getSqlDate(String dateString) {
        if (dateString == null) {
            return null;
        }
        try {
            SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
            return new java.sql.Date(sf.parse(dateString).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(DateUtil.class.getName()).log(Level.SEVERE, null, ex);
            return new java.sql.Date(getCurrentDate().getTime());
        }
    }

    public static Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        TimeZone fromTimeZone = calendar.getTimeZone();
        TimeZone toTimeZone = TimeZone.getTimeZone("Africa/Lagos");
        return getDate(calendar, fromTimeZone, toTimeZone);
    }

    public static Date getCurrentDate(String timeZone) {
        Calendar calendar = Calendar.getInstance();
        TimeZone fromTimeZone = calendar.getTimeZone();
        TimeZone toTimeZone = TimeZone.getTimeZone(timeZone);

        return getDate(calendar, fromTimeZone, toTimeZone);
    }

    private static Date getDate(Calendar calendar, TimeZone fromTimeZone, TimeZone toTimeZone) {
        calendar.setTimeZone(fromTimeZone);
        calendar.add(Calendar.MILLISECOND, fromTimeZone.getRawOffset() * -1);
        if (fromTimeZone.inDaylightTime(calendar.getTime())) {
            calendar.add(Calendar.MILLISECOND, calendar.getTimeZone().getDSTSavings() * -1);
        }

        calendar.add(Calendar.MILLISECOND, toTimeZone.getRawOffset());
        if (toTimeZone.inDaylightTime(calendar.getTime())) {
            calendar.add(Calendar.MILLISECOND, toTimeZone.getDSTSavings());
        }
        return calendar.getTime();
    }

    /**
     * format datetime with output as "dd-MM-yyyy HH:mm:ss"
     *
     * @param date date paramteter
     * @return String formated DateTime </br> sample output is 2017-11-12
     * 11:17:23
     */
    public static String formatDateTime(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return df.format(date);
    }

    public static Date addSecondsToDate(int seconds) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtil.getCurrentDate());
        cal.add(Calendar.SECOND, seconds);
        return cal.getTime();
    }

    public static Date createLifeSpanFromNowInHours(int lifespan) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtil.getCurrentDate());
        cal.add(Calendar.HOUR, lifespan);
        return cal.getTime();
    }

    public static Date createLifeSpanFromNowInMinutes(int lifespan) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(DateUtil.getCurrentDate());
        cal.add(Calendar.MINUTE, lifespan);
        return cal.getTime();
    }

    public static Date parse(String d) throws ParseException {
        SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy");
        return sf.parse(d);
    }

    public static HashMap<String, Date> getDates() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getCurrentDate());
        cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));
        Date sd = cal.getTime();
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
        Date ed = cal.getTime();
        HashMap<String, Date> dates = new HashMap<>();
        dates.put("sd", sd);
        dates.put("ed", ed);
        return dates;
    }

    public static String formatDate2(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        return df.format(date);
    }

    public static String formatDate2(java.sql.Date date, String format) {
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }


    public static Date getExpiry(Date begindate, int months) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(begindate);
        int currentmonth = cal.get(Calendar.MONTH);
        months += currentmonth;
        int daysspent = cal.get(Calendar.DATE);
        //set new month
        cal.set(Calendar.MONTH, months);
        //set day
        cal.set(Calendar.DATE, daysspent);
        return cal.getTime();
    }

//    public static long getDateDifference(Date earlier, Date latest) {
//        Calendar _earlier = Calendar.getInstance();
//        Calendar _later = Calendar.getInstance();
//
//        _earlier.setTime(earlier);
//        _later.setTime(latest);
//        long me = _earlier.getTimeInMillis();
//        long ml = _later.getTimeInMillis();
//        long diff = ml - me;
//        long days = (diff / (24 * 60 * 60 * 1000));
//        return days;
//    }

    public static int getDateDifference(Date earlier, Date latest) {
        Calendar _earlier = Calendar.getInstance();
        Calendar _later = Calendar.getInstance();

        _earlier.setTime(earlier);
        _later.setTime(latest);
        long me = _earlier.getTimeInMillis();
        long ml = _later.getTimeInMillis();
        long diff = ml - me;
        long days = (diff / (24 * 60 * 60 * 1000));
        return (int) days;
    }

    public static Date addDate(Date currentdate, int datefield, int counts) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentdate);
        cal.add(datefield, counts);
        return cal.getTime();
    }

    public static java.sql.Date addSqlDate(Date date, int datefield, int counts) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(datefield, counts);
        return new java.sql.Date(cal.getTime().getTime());
    }

    public static Timestamp getCurrentSQLDateTimestamp(){
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdfAmerica = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        sdfAmerica.setTimeZone(TimeZone.getTimeZone("Africa/Lagos"));
        String sDateInAmerica = sdfAmerica.format(date);
        Timestamp currentSQLDateTimestamp = Timestamp.valueOf(sDateInAmerica);
        return currentSQLDateTimestamp;
    }

    public static java.sql.Date getCurrentSQLDate(){
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdfAmerica = new SimpleDateFormat("yyyy-MM-dd");
        sdfAmerica.setTimeZone(TimeZone.getTimeZone("Africa/Lagos"));
        String sDateInAmerica = sdfAmerica.format(date);
        java.sql.Date currentSQLDate = java.sql.Date.valueOf(sDateInAmerica);
        return currentSQLDate;
    }

    public static String getSQLDatetime(){
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdfAmerica = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdfAmerica.setTimeZone(TimeZone.getTimeZone("Africa/Lagos"));
        String formattedDate = sdfAmerica.format(date);
        return formattedDate;
    }

    public static String getReference(){
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        sdf.setTimeZone(TimeZone.getTimeZone("Africa/Lagos"));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public static String getSQLDate(){
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdfAmerica = new SimpleDateFormat("yyyy-MM-dd");
        sdfAmerica.setTimeZone(TimeZone.getTimeZone("Africa/Lagos"));
        String formattedDate = sdfAmerica.format(date);
        return formattedDate;
    }

    public static LocalDate convertSqlDateToLocalDate(java.sql.Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    public static java.sql.Date convertLocalDateToSqlDate(LocalDate dateToConvert) {
        return java.sql.Date.valueOf(dateToConvert);
    }

    public static java.sql.Time getSqlTime(){
        return java.sql.Time.valueOf(getSQLDatetime());
    }

    public static LocalDate parseToLocalDate(String dateString) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(AppConstants.DATE_FORMAT);
        return LocalDate.parse(dateString, dtf);
    }

    public static LocalDateTime convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.of(TIME_ZONE))
                .toLocalDateTime();
    }

}
