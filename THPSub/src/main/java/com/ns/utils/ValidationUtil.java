package com.ns.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ValidationUtil {

    // Validation Email
    public static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Validation Name
    public static boolean isValidName(String name) {
        // TODO Auto-generated method stub
        boolean valid_firstname;
        if (name == null) {
            // edt.setError("Accept Alphabets Only.");
            valid_firstname = false;
        } else if (!name.matches("[a-zA-Z ]+")) {
            // edt.setError("Accept Alphabets Only.");
            valid_firstname = false;
        } else {
            valid_firstname = true;
        }
        return valid_firstname;
    }

    public static boolean isEmpty(String string) {
        if(string == null || TextUtils.isEmpty(string.trim())) {
            return true;
        }
        return false;
    }

    public static boolean isValidMobile(String mobile) {
        String MOBILE_PATTERN = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[6789]\\d{9}$";
        Pattern pattern = Pattern.compile(MOBILE_PATTERN);
        Matcher matcher = pattern.matcher(mobile);
        return matcher.matches();
    }

    public static boolean isValidIFSC(String ifsc) {
        String IFSC_PATTERN = "^[A-Za-z]{4}[a-zA-Z0-9]{7}$";
        Pattern pattern = Pattern.compile(IFSC_PATTERN);
        Matcher matcher = pattern.matcher(ifsc);
        return matcher.matches();
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm  = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo     = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static boolean isTimeUp(Context context, long savesMillis) {
        long MILLIS_PER_DAY = 24*60*60 * 1000L;
        boolean moreThanDay;

        long myCurrentTimeMillis = System.currentTimeMillis();
        int offset = TimeZone.getDefault().getOffset(myCurrentTimeMillis);
        long myLocalCurrentMillis = myCurrentTimeMillis + offset;

        Date date1 = formatDate(savesMillis);
        Date date2 = formatDate(myLocalCurrentMillis);
        moreThanDay = Math.abs(date1.getTime() - date2.getTime()) > MILLIS_PER_DAY;
        return moreThanDay;
    }

    static Date formatDate(long dateInMillis) {
        Date date = new Date(dateInMillis);
        return date;
    }
}
