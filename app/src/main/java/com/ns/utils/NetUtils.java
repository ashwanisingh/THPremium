package com.ns.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.annotation.IntDef;
import android.telephony.TelephonyManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;



public class NetUtils {
    /**
     * Opens wifi setting screen
     */
    public static void openWirelessSettings(Context context) {
        if (android.os.Build.VERSION.SDK_INT > 10) {
            context.startActivity(new Intent(Settings.ACTION_SETTINGS));
        } else {
            context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
        }
    }

    /**
     * Returns, boolean whether device is connected with internet or not.
     * need <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    /**
     * Returns, boolean whether device is connected with wi-fi network or not.
     * need <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
     */
    public static boolean isWifiConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm != null && cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * Return, network operator name
     */
    public static String getNetworkOperatorName(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getNetworkOperatorName() : null;
    }

    /**
     * PHONE_TYPE_NONE  : 0
     * PHONE_TYPE_GSM   : 1
     * PHONE_TYPE_CDMA  : 2
     * PHONE_TYPE_SIP   : 3
     */
    public static int getPhoneType(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getPhoneType() : -1;
    }

    /**
     * Returns connected internet type only for (2G,3G,4G)
     * This is for sim card internet type check
     */
    public static int getNetworkTpye(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return NETWORK_2G;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return NETWORK_3G;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return NETWORK_4G;
            default:
                return NETWORK_NONE;
        }
    }

    /**
     * Get the current mobile phone network types available (WIFI,2G,3G,4G)
     * Need <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
     *
     */
    public static int getNetworkTypeDetail(Context context) {
        int netWorkType = NETWORK_NONE;
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            int type = networkInfo.getType();
            if (type == ConnectivityManager.TYPE_WIFI) {
                netWorkType = NETWORK_WIFI;
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                netWorkType = getNetworkTpye(context);
            }
        }
        return netWorkType;
    }

    public static int getNetworkState(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        // Wifi
        NetworkInfo.State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState();
        if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
            return NETWORK_WIFI;
        }

        // Mobile
        state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .getState();
        if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
            return NETWORK_MOBILE;
        }
        return NETWORK_NONE;
    }


    public static final int NETWORK_NONE = 0;
    // wifi network
    public static final int NETWORK_WIFI = 1;
    // "2G" networks
    public static final int NETWORK_2G = 2;
    // "3G" networks
    public static final int NETWORK_3G = 3;
    // "4G" networks
    public static final int NETWORK_4G = 4;
    // moblie networks
    public static final int NETWORK_MOBILE = 5;


    // Reference Link
    // https://medium.com/@ssaurel/how-to-retrieve-an-unique-id-to-identify-android-devices-6f99fd5369eb
    public static String getDiviceId(Context context) {
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return "android_"+androidId;
    }


    public static String genImageUrl(String imgBaseUrl, String propId, String imgTypeThumb, String imgId) {
        return imgBaseUrl + propId + "/" + imgTypeThumb + "/" + imgId;
    }



    public final static int HB = 1;
    public final static int DB = 2;
    public final static int THUMB = 3;
    public final static int GRID = 4;
    public final static int WIDGET = 5;
    public final static int EPL_2 = 6;
    public final static int EPL_3 = 7;
    public final static int GALLERY_SINGLE = 8;
    public final static int ORIGINAL = 9;


    @IntDef({HB, DB, THUMB, GRID, WIDGET, EPL_2, EPL_3, GALLERY_SINGLE, ORIGINAL })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ImageTypeFrom {}
    public static String imageType(@ImageTypeFrom int typeFrom, String deviceDensity) {

        String imageType = "16_9/172_97";

        switch (deviceDensity) {
            case "LDPI":
            case "MDPI":
            case "HDPI":
                if(typeFrom == HB || typeFrom == DB) {
                    imageType = "16_9/172_97";
                }
                else if(typeFrom == THUMB) {
                    imageType = "1_1/70_70";
                }
                else if(typeFrom == WIDGET) {
                    imageType = "1.34_1/374_279";
                }
                else if(typeFrom == GALLERY_SINGLE) {

                }
                else if(typeFrom == ORIGINAL) {
                    imageType = "original/images";
                }
                break;
            case "XHDPI":
            case "XXHDPI":
                if(typeFrom == HB || typeFrom == DB) {
                    imageType = "16_9/390_219";
                }
                else if(typeFrom == THUMB) {
                    imageType = "1_1/100_100";
                }
                else if(typeFrom == WIDGET) {
                    imageType = "1.34_1/374_279";
                }
                else if(typeFrom == GALLERY_SINGLE) {

                }
                else if(typeFrom == ORIGINAL) {
                    imageType = "original/images";
                }
            case "XXXHDPI":
                if(typeFrom == HB || typeFrom == DB) {
                    imageType = "16_9/414_233";
                }
                else if(typeFrom == THUMB) {
                    imageType = "1_1/100_100";
                }
                else if(typeFrom == WIDGET) {
                    imageType = "1.34_1/374_279";
                }
                else if(typeFrom == GALLERY_SINGLE) {

                }
                else if(typeFrom == ORIGINAL) {
                    imageType = "original/images";
                }
                default:

        }

        return imageType;
    }

}
