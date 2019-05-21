
package com.ns.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.netoperation.util.UserPref;
import com.ns.utils.THPConstants;

public class AutoResizeWebview extends WebView {


    private int currentIndex = -1;
    private Context mContext;

    public AutoResizeWebview(Context context) {
        this(context, null);
        mContext = context;
    }


    public AutoResizeWebview(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
        mContext = context;
    }

    public AutoResizeWebview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;


        // This for page fit
//        getSettings().setJavaScriptEnabled(true);
//        getSettings().setAllowContentAccess(true);
//        getSettings().setLoadWithOverviewMode(true);
//        getSettings().setUseWideViewPort(true);
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    public void setSize(int newIndex) {
        currentIndex = newIndex;
        if (VERSION.SDK_INT >= VERSION_CODES.ICE_CREAM_SANDWICH) {
            if(currentIndex == 4) {
                getSettings().setTextZoom(100 + ((currentIndex - 1) * 17));
            } else if(currentIndex == 3) {
                getSettings().setTextZoom(100 + ((currentIndex - 1) * 12));
            }
            else {
                getSettings().setTextZoom(100 + ((currentIndex - 1) * 10));
            }
        } else {
            switch (currentIndex) {
                case THPConstants.DESCRIPTION_SMALL: {
                    getSettings().setTextSize(WebSettings.TextSize.SMALLER);
                    break;
                }
                case THPConstants.DESCRIPTION_NORMAL: {
                    getSettings().setTextSize(WebSettings.TextSize.NORMAL);
                    break;
                }
                case THPConstants.DESCRIPTION_LARGE: {
                    getSettings().setTextSize(WebSettings.TextSize.LARGER);
                    break;
                }
                case THPConstants.DESCRIPTION_LARGEST: {
                    getSettings().setTextSize(WebSettings.TextSize.LARGEST);
                    break;
                }
            }
        }
        invalidate();

    }

    public static String shoWebTextDescription(Context context, String description, boolean isItalic) {
        final int theme = UserPref.getInstance(context).getThemeVal();
        String color = "#FFFFFF";
        switch (theme) {
            case THPConstants.THEME_WHITE:
                color = "#FFFFFF";
                break;

            case THPConstants.THEME_GREY:
                color = "#919191";
                break;

            default:
                return "";
        }

        String font = "FiraSans-Regular.ttf";

        if(isItalic) {
//            description = "<i>"+description+"</i>";
            font = "TundraOffc.ttf";
        }
        return "<html><head>"
                + "<style type=\"text/css\">body{color: #191919; background-color: " +
                color +
                ";}" +
                "@font-face {\n" +
                "   font-family: 'tundra';\n" +
                "   src: url('file:///android_asset/fonts/" +
                font +
                "');" +
                "} " +
                "body {font-family: 'tundra';}"
                + "</style></head>"
                + "<body>"
                + description
                + "</body></html>";


    }




}
