package com.netoperation.util;

import net.danlew.android.joda.DateUtils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AppDateUtil {

    public static final DateTime getDateTime(String source) {
        return DateTime.parse(source, ISODateTimeFormat.dateTimeParser());
    }

    public static final boolean isToday(DateTime dateTime) {
        if(dateTime == null) {
            return false;
        }
        return DateUtils.isToday(dateTime);
    }

    public static final boolean isYesterday(DateTime dateTime) {
        if(dateTime == null) {
            return false;
        }
        return DateUtils.isToday(dateTime.minusDays(1));
    }

    public static final String onlyDate_ddMMyyyy(DateTime dateTime) {
        if(dateTime == null) {
            return "";
        }
        DateTimeFormatter builder = DateTimeFormat.forPattern( "dd/MM/yyyy" );
        return  builder.print(dateTime);
    }

    public static final String onlyTime_HHmm(DateTime dateTime) {
        if(dateTime == null) {
            return "";
        }
        int hourOfDay = dateTime.getHourOfDay();
        String srt = "";
        if (hourOfDay == 00) {
            DateTimeFormatter builder = DateTimeFormat.forPattern( "hh:mm' AM" );
            srt =  builder.print(dateTime);
        } else if (0 < hourOfDay && hourOfDay < 12) {
            DateTimeFormatter builder = DateTimeFormat.forPattern( "hh:mm' AM" );
            srt =  builder.print(dateTime);
        } else if (hourOfDay > 12 || hourOfDay == 12) {
            DateTimeFormatter builder = DateTimeFormat.forPattern( "hh:mm' PM" );
            srt =  builder.print(dateTime);
        }
        return srt;
    }

    public static final String onlyTime_hhmm(DateTime dateTime) {
        if(dateTime == null) {
            return "";
        }
        DateTimeFormatter builder = DateTimeFormat.forPattern( "hh:mm" );
        String val = builder.print(dateTime);
        String[] spl = val.split(":");

        return  builder.print(dateTime);
    }

    public static String getDurationFormattedDate(long date_created, Locale locale) {
        String result = "";
        long currentTime = System.currentTimeMillis();
        Date time_created = new Date(date_created);

        String hrAgo = " hr ago";
        String hrsAgo = " hrs ago";
        String secAgo = " sec ago";
        String minAgo = " min ago";


        if (currentTime - date_created < 60 * 1000) {
            result = ((currentTime - date_created) / 1000)
                    + secAgo;
        } else if (currentTime - date_created < 60 * 60 * 1000) {
            result = ((currentTime - date_created) / (60 * 1000))
                    + minAgo;
        } else if (currentTime - date_created < 24 * 60 * 60 * 1000) {
            long t = (currentTime - date_created) / (60 * 60 * 1000);
            if (t == 1)
                result = t + hrAgo;
            else
                result = t + hrsAgo;
        } else {
            result = new SimpleDateFormat("MMM dd, yyyy hh:mm a", locale).format(time_created)+ " IST";
        }
        return result;
    }

    public static long strToMlsForNonBriefing(String dateInString) {
        // May 23, 2019 8:44:42 PM
        SimpleDateFormat formatter = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss a");
        try {
            Date date = formatter.parse(dateInString);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long strToMlsForBriefing(String dateInString) {
        // Thu, 6 Jun 2019 12:17:30 +0530
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss Z");
        try {
            Date date = formatter.parse(dateInString);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    public static String getDetailScreenPublishDate(long date_updated, Locale locale) {
        String result = "";
        try {
            Date time_updated = new Date(date_updated);
//            result = "" + new SimpleDateFormat("MMM dd, yyyy hh:mm a").format(time_updated) + " IST";
            result = "" + new SimpleDateFormat("dd MMMM, yyyy | hh:mm a", locale).format(time_updated);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }



}
