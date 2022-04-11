package com.youxin.alumni_management.utils;

import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


@Service
public class DateUtil
{
    public Date dateAddDay(Date date, int day)
            throws Exception
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, day);
        return calendar.getTime();
    }

    public int dateCompareDay(String target, String source)
            throws Exception
    {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition spp = new ParsePosition(0);
        ParsePosition epp = new ParsePosition(0);
        Date start = format.parse(target, spp);
        Date end = format.parse(source, epp);
        return (int)((start.getTime() - end.getTime()) / 86400000L);
    }

    public String formatCharacter(Date date, String pattern)
            throws Exception
    {
        if (null == date) date = new Date();
        if (null == pattern) pattern = "yyyy-MM-dd";
        DateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public Date formatDateTime(String date, String pattern)
            throws Exception
    {
        if (date == null) return null;
        if (pattern == null) pattern = "yyyy-MM-dd";
        DateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(date); } catch (ParseException e) {
        }
        return null;
    }

    public String getSystemDateToString()
    {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(date);
    }

    public long getSystemDateTime()
    {
        return new Date().getTime();
    }

    public Date getSystemDate(){
        return new Date();
    }
}