package com.ns.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

public class THPPreferences {
    Context context;
    private static THPPreferences instance = null;

    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;
    private  final String TPHpref = "tphpref";
    private  final String EMAILORMOBILE = "emailOrMobile";


    private THPPreferences(Context context) {
        this.context = context;
        sharedpreferences = context.getSharedPreferences(TPHpref, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
    }

    public static THPPreferences getInstance(Context context) {
        if (instance == null) {
            instance = new THPPreferences(context);
        }
        return instance;
    }

    public void saveSignUpDetails(String emailOrMobile) {
        editor.putString(EMAILORMOBILE, emailOrMobile);
        editor.commit();
    }

}
