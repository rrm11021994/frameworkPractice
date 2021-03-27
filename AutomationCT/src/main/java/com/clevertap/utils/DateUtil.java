package com.clevertap.utils;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private static LocalDate today;
    private static Date date;
    private static SimpleDateFormat simpleDateFormat;
    private static Logger logger=Logger.getLogger(DateUtil.class);

    /*Below function provides current date in format yyyyMMdd.
    * this is in use in campaign tests*/
    public static Date getDateTimeInYYYYMMDDString() throws ParseException {

        DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("yyyyMMdd");
        today= LocalDate.now();
        simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.parse(dateTimeFormatter.format(today));



    }

    /*Below function return the date object based on user input*/
    public static Date getDateTimeInYYYYMMDDObject(String startDate) throws ParseException {
        date = new SimpleDateFormat("yyyyMMdd").parse(startDate);
        return date;
    }


    /*Below function return the date string for current date*/
    public static String getLocalDateInYYYYMMDDString() throws ParseException {
        DateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
        Date date=new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    /*Below function return the date string for current date*/
    public static String get2DaysLaterInYYYYMMDDString() throws ParseException {
        DateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMdd");
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date(System.currentTimeMillis()));
        calendar.add(Calendar.DAY_OF_MONTH,2);

        return simpleDateFormat.format(calendar.getTime());
    }

    /*Below method will give 5 minutes ahead of the current time of the daytime in format hr_mm_PM*/
    public static String getTimeAsHR_MM_PM(int hoursToAdd){
        DateTime dateTime=new DateTime(System.currentTimeMillis());
//        int hour= dateTime.getHourOfDay();
//        int minute=dateTime.getMinuteOfHour();
        int hourOfDay=dateTime.getHourOfDay();


        String hhmm=dateTime.plusHours(hoursToAdd).toString("kkmm");
        if (Integer.parseInt(hhmm.substring(0,2))>12){
            hhmm=hhmm.substring(0,2)+":"+hhmm.substring(2)+" PM";
        }else {
            hhmm=hhmm.substring(0,2)+":"+hhmm.substring(2)+" AM";
        }




//            if (minute+5<=60){
//                if (minute+5<=9){
//                    if (hour<=11){
//                        time=hour+":0"+(minute+5);
//                    }else {
//                        time="0"+hour+":0"+(minute+5);
//                    }
//
//                }else {
//                        time=hour+":"+(minute+5);
//
//
//                }
//
//            }else {
//                minute=(minute+5)-60;
//                time=(hour+1)+":0"+minute;
//            }
//
//            if (hour>12){
//                time=time+" PM";
//            }else {
//                time=time+" AM";
//            }

        logger.info(hhmm);
        return hhmm;


    }


    /*Below method will give some minutes(user specified) ahead of current time.*/
    public static String getCustomTime(int minuteToBeAdded){
        String time = null;
        Calendar calendar=Calendar.getInstance();
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int minute=calendar.get(Calendar.MINUTE);
        int second=calendar.get(Calendar.SECOND);

        if (minute+minuteToBeAdded<60){
            if (hour<11){
                time=hour+":"+(minute+minuteToBeAdded)+" AM";
            }else if (hour>=12){
                time=hour+":"+(minute+minuteToBeAdded)+" PM";
            }
        }else {
            if (minute+minuteToBeAdded-60<10){
                if (hour<11){
                    time=(hour+1)+":0"+(minute+minuteToBeAdded-60)+" AM";
                }else if (hour>=12){
                    time=(hour+1)+":0"+(minute+minuteToBeAdded-60)+" PM";
                }

            }else {
                if (hour<11){
                    time=(hour+1)+":"+(minute+minuteToBeAdded-60)+" AM";
                }else if (hour>=12){
                    time=(hour+1)+":"+(minute+minuteToBeAdded-60)+" PM";
                }
            }
        }
        logger.info("*********getCustomTime() output- ******** : "+time);
        return time;


    }

    /*Below method will return current time in HH:MM PM/AM format as string*/
    public static String getCurrentTimeInStringFormat(){
        LocalTime time=LocalTime.now();
        return time.format(DateTimeFormatter.ofPattern("HH:mm a"));
    }
}
