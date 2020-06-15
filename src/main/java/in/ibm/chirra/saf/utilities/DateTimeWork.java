package in.ibm.chirra.saf.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTimeWork {

    public final String dateTimeFormat_1 = "dd/MM/yyyy HH:mm:ss";
    public final String dateTimeFormat_2 = "MM/dd/yyyy HH:mm:ss";
    public final String dateTimeFormat_3 = "yyyy/MM/dd HH:mm:ss";
    public final String dateTimeFormat_4 = "yyyy_MM_dd_HH_mm_ss";
    public final String dateFormat_1 = "dd/MM/yyyy";
    public final String dateFormat_2 = "MM/dd/yyyy";
    public final String dateFormat_3 = "yyyy/MM/dd";
    public final String dateFormat_4 = "yyyy_MM_dd";
    public final String timeFormat_1 = "HH:mm:ss";
    public final String timeFormat_2 = "HH_mm_ss";

    public String getCurrentDateTime(String sampleDateTimeFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateTimeFormat_1);
        if (sampleDateTimeFormat.equalsIgnoreCase(dateTimeFormat_1))
            formatter = new SimpleDateFormat(dateTimeFormat_1);
        else if (sampleDateTimeFormat.equalsIgnoreCase(dateTimeFormat_2))
            formatter = new SimpleDateFormat(dateTimeFormat_2);
        else if (sampleDateTimeFormat.equalsIgnoreCase(dateTimeFormat_3))
            formatter = new SimpleDateFormat(dateTimeFormat_3);
        else if (sampleDateTimeFormat.equalsIgnoreCase(dateTimeFormat_4))
            formatter = new SimpleDateFormat(dateTimeFormat_4);
        Date date = new Date();
        return "" + formatter.format(date);
    }

    public static String getCurrentDateTimeInMillis() {
        return ""+System.currentTimeMillis();
    }

    public static String getCurrentDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date date = new Date();
        String currentDateTime = formatter.format(date);
        System.out.println("Now: " + currentDateTime);
        return currentDateTime;
    }

    public String getDateTimeDifferance(String startDateTime, String enddateTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = formatter.parse(startDateTime);
            d2 = formatter.parse(enddateTime);
        } catch (ParseException e) {
        }

        //in milliseconds
        long diff = d2.getTime() - d1.getTime();

        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        String dateTimeDifferance = "" + diffDays + " days, " + diffHours + " hours, " + diffMinutes + " minutes, " + diffSeconds + " seconds.";
        return dateTimeDifferance;
    }

    public static String getDateTimeDifferance(String format, String startDateTime, String enddateTime) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = formatter.parse(startDateTime);
            d2 = formatter.parse(enddateTime);
        } catch (ParseException e) {
        }

        //in milliseconds
        long diff = d2.getTime() - d1.getTime();

        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        String dateTimeDifferance = "" + diffDays + " days, " + diffHours + " hours, " + diffMinutes + " minutes, " + diffSeconds + " seconds.";
        return dateTimeDifferance;
    }


    public String getCurrentDate(String sampleDateFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat_1);
        if (sampleDateFormat.equalsIgnoreCase(dateFormat_1))
            formatter = new SimpleDateFormat(dateFormat_1);
        else if (sampleDateFormat.equalsIgnoreCase(dateFormat_2))
            formatter = new SimpleDateFormat(dateFormat_2);
        else if (sampleDateFormat.equalsIgnoreCase(dateFormat_3))
            formatter = new SimpleDateFormat(dateFormat_3);
        else if (sampleDateFormat.equalsIgnoreCase(dateFormat_4))
            formatter = new SimpleDateFormat(dateFormat_4);
        Date date = new Date();
        return "" + formatter.format(date);
    }

    public String getCurrentTime(String sampleTimeFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat_1);
        if (sampleTimeFormat.equalsIgnoreCase(timeFormat_1))
            formatter = new SimpleDateFormat(timeFormat_1);
        else if (sampleTimeFormat.equalsIgnoreCase(timeFormat_2))
            formatter = new SimpleDateFormat(timeFormat_2);
        Date date = new Date();
        return "" + formatter.format(date);
    }

    public String addDaysToCurrentDateTime(int days){
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DATE, days);
        SimpleDateFormat formatter = new SimpleDateFormat(dateTimeFormat_4);
        Date modifiedDate = cal.getTime();
        return "" + formatter.format(modifiedDate);
    }
    public String addMonthsToCurrentDateTime(int months){
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.MONTH, months);
        SimpleDateFormat formatter = new SimpleDateFormat(dateTimeFormat_4);
        Date modifiedDate = cal.getTime();
        return "" + formatter.format(modifiedDate);
    }
    public String addYearsToCurrentDateTime(int years){
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.YEAR, years);
        SimpleDateFormat formatter = new SimpleDateFormat(dateTimeFormat_4);
        Date modifiedDate = cal.getTime();
        return "" + formatter.format(modifiedDate);
    }


    public String getLatestData(String date1, String date2){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = formatter.parse(date1);
            d2 = formatter.parse(date2);
        } catch (ParseException e) {
        }
        if(d1.after(d2))
            return ""+d1;
        else
            return ""+d2;
    }

    public boolean isDate1GreaterThanData2(String date1, String date2){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = formatter.parse(date1);
            d2 = formatter.parse(date2);
        } catch (ParseException e) {
        }
        if(d1.after(d2))
            return true;
        else
            return false;
    }
    public boolean isDate1GreaterThanData2(String date1, String date2, String dateFormat){
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = formatter.parse(date1);
            d2 = formatter.parse(date2);
        } catch (ParseException e) {
        }
        if(d1.after(d2))
            return true;
        else
            return false;
    }

}
