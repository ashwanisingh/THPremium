package com.ns.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.ns.alerts.Alerts;


public class ResUtil {


    public static float dpFromPx(Context context, float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    private static float getPixelScaleFactor(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT);
    }


    public static void setBackgroundDrawable(Resources resources, View layout, int drawable) {
        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk <= android.os.Build.VERSION_CODES.LOLLIPOP) {
            layout.setBackgroundDrawable(resources.getDrawable(drawable));
        } else {
            layout.setBackground(resources.getDrawable(drawable, null));
        }
    }

    public static Drawable getBackgroundDrawable(Resources resources, int drawable) {
        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk <= android.os.Build.VERSION_CODES.LOLLIPOP) {
            return resources.getDrawable(drawable);
        } else {
            return resources.getDrawable(drawable, null);
        }
    }

    public static void setBackgroundDrawable(Drawable drawable, View layout) {
        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk <= android.os.Build.VERSION_CODES.LOLLIPOP) {
            layout.setBackgroundDrawable(drawable);
        } else {
            layout.setBackground(drawable);
        }
    }

    public static int getColor(Resources resources, int colorRes) {
        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk <= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            return resources.getColor(colorRes);
        } else {
            return resources.getColor(colorRes, null);
        }
    }

    public static int getDrawableResId(Context context, String name) {
        Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier(name, "drawable",
                context.getPackageName());
        return resourceId;
    }



    public static final void doColorSpanForFirstString(Context context, String firstString,
                                                       String lastString, TextView txtSpan, int colorResId) {

        String changeString = (firstString != null ? firstString : "");

        String totalString = changeString + lastString;
        Spannable spanText = new SpannableString(totalString);
        spanText.setSpan(new ForegroundColorSpan(ResUtil.getColor(context.getResources(), colorResId)), 0, changeString.length(), 0);

        txtSpan.setText(spanText);
    }
    public static final void doColorSpanForSecondString(Context context, String firstString,
                                                        String lastString, TextView txtSpan, int colorResId) {
        String changeString = (lastString != null ? lastString : "");
        String totalString = firstString + changeString;
        Spannable spanText = new SpannableString(totalString);
        spanText.setSpan(new ForegroundColorSpan(ResUtil.getColor(context.getResources(), colorResId)), String.valueOf(firstString)
                .length(), totalString.length(), 0);
        txtSpan.setText(spanText);
    }

    public static final void doColorSpanForSecondThirdString(Context context, String firstString,
                                                             String lastString, String thirdString, TextView txtSpan, int colorResId) {
        String changeString = (lastString != null ? lastString : "");
        String totalString = firstString +thirdString+ changeString ;
        Spannable spanText = new SpannableString(totalString);
        spanText.setSpan(new ForegroundColorSpan(ResUtil.getColor(context.getResources(), colorResId)), String.valueOf(firstString)
                .length(), totalString.length(), 0);
        txtSpan.setText(spanText);
    }
    public static final void doSizeSpanForFirstString(Context context, String firstString,
                                                      String lastString, TextView txtSpan, int colorResId) {
        String changeString = (firstString != null ? firstString : "");

        String totalString = changeString + lastString;
        Spannable spanText = new SpannableString(totalString);
        spanText.setSpan(new ForegroundColorSpan(ResUtil.getColor(context.getResources(), colorResId)), 0, changeString.length(),  0);
        spanText.setSpan(new RelativeSizeSpan(1.5f), 0, changeString.length(), 0);
        txtSpan.setText(spanText);
    }
    public static final void doSizeSpanForSecondString(Context context, String firstString,
                                                       String lastString, TextView txtSpan, int colorResId) {
        String changeString = (lastString != null ? lastString : "");
        String totalString = firstString + changeString;
        Spannable spanText = new SpannableString(totalString);
        spanText.setSpan(new ForegroundColorSpan(ResUtil.getColor(context.getResources(), colorResId)),
                String.valueOf(firstString)
                .length(),totalString.length(), 0);
        spanText.setSpan(new RelativeSizeSpan(1.5f), String.valueOf(firstString)
                .length(), totalString.length(), 0);
        txtSpan.setText(spanText);
    }
    public static final void doStyleSpanForFirstString(String firstString,
                                                       String lastString, TextView txtSpan) {
        String changeString = (firstString != null ? firstString : "");
        String totalString = changeString + lastString;
        Spannable spanText = new SpannableString(totalString);
        spanText.setSpan(new StyleSpan(Typeface.BOLD), 0,
                changeString.length(), 0);
        txtSpan.setText(spanText);
    }
    public static final void doStyleSpanForSecondString(String firstString,
                                                        String lastString, TextView txtSpan) {
        String changeString = (lastString != null ? lastString : "");
        String totalString = firstString + changeString;
        Spannable spanText = new SpannableString(totalString);
        spanText.setSpan(new StyleSpan(Typeface.BOLD),
                String.valueOf(firstString).length(),
                totalString.length(), 0);
        txtSpan.setText(spanText);
    }
    public static final void doClickSpanForString(Context context, String firstString,
                                                  String lastString, TextView txtSpan, int colorResId,
                                                  TextSpanCallback textSpanCallback) {
        String changeString = (lastString != null ? lastString : "");
        String totalString = firstString + changeString;
        Spannable spanText = new SpannableString(totalString);
        spanText.setSpan(new MyClickableSpan(totalString, context, colorResId, textSpanCallback),
                String.valueOf(firstString).length(),
                totalString.length(), 0);
        spanText.setSpan(new StyleSpan(Typeface.BOLD),
                String.valueOf(firstString).length(),
                totalString.length(), 0);
        txtSpan.setText(spanText);
        txtSpan.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public static final void doSizeAndThinSpanForSecondString(String firstString,
                                                              String lastString, TextView txtSpan, int colorResId, float size) {
        String changeString = (lastString != null ? lastString : "");
        String totalString = firstString + changeString;
        Spannable spanText = new SpannableString(totalString);
        spanText.setSpan(new RelativeSizeSpan(size), String.valueOf(firstString).length(),totalString.length(), 0); // set size
        spanText.setSpan(new ForegroundColorSpan(colorResId), String.valueOf(firstString).length(),totalString.length(), 0); // set color
        txtSpan.setText(spanText);
    }

    private static class MyClickableSpan extends ClickableSpan {

        private Context context;
        private int colorResId;
        private TextSpanCallback textSpanCallback;

        public MyClickableSpan(String string, Context context, int colorResId, TextSpanCallback textSpanCallback) {
            super();
            this.context = context;
            this.colorResId = colorResId;
            this.textSpanCallback = textSpanCallback;
        }
        public void onClick(View tv) {
            if(textSpanCallback != null) {
                textSpanCallback.onTextSpanClick();
            }
        }
        public void updateDrawState(TextPaint ds) {

            ds.setColor(ResUtil.getColor(context.getResources(), colorResId));
            ds.setUnderlineText(false); // set to true to show underline
        }
    }

}
