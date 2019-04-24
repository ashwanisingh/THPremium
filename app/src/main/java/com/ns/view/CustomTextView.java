package com.ns.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.ns.thpremium.R;

public class CustomTextView extends AppCompatTextView {

    String mFontPath;

    public CustomTextView(Context context) {
        super(context);
        init(context, null);

    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);

    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);

    }

    public void applyCustomFont(Context context, String fontName) {
        Typeface customFont = FontCache.getTypeface(fontName, context);
        setTypeface(customFont);
    }

    public void init(Context context, @Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextView);

            mFontPath = typedArray.getString(R.styleable.TextView_font_path);

            if (mFontPath != null && !mFontPath.isEmpty()) {
                applyCustomFont(context, mFontPath);
            }
            typedArray.recycle();
        }
    }
}
