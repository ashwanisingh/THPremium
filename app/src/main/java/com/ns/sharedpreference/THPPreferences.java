package com.ns.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;

public class THPPreferences {
    Context context;
    private static THPPreferences instance = null;

    private SharedPreferences mPref;
    private SharedPreferences.Editor editor;
    private  final String TPHpref = "tphpref";


    private THPPreferences(Context context) {
        this.context = context;
        mPref = context.getSharedPreferences(TPHpref, Context.MODE_PRIVATE);
        editor = mPref.edit();
    }

    public static THPPreferences getInstance(Context context) {
        if (instance == null) {
            instance = new THPPreferences(context);
        }
        return instance;
    }

    public void saveEmail(String email) {
        editor.putString("email", email);
        editor.commit();
    }

    public String getEmail() {
        return mPref.getString("email", "");
    }

    public void saveContact(String contact) {
        editor.putString("contact", contact);
        editor.commit();
    }

    public String getContact() {
        return mPref.getString("contact", "");
    }

    public void saveName(String name) {
        editor.putString("name", name);
        editor.commit();
    }

    public String getName() {
        return mPref.getString("name", "");
    }

    public void saveDOB(String dob) {
        editor.putString("dob", dob);
        editor.commit();
    }

    public String getDOB() {
        return mPref.getString("dob", "");
    }

    public void saveState(String state) {
        editor.putString("state", state);
        editor.commit();
    }

    public String getState() {
        return mPref.getString("state", "");
    }

    public void saveCountry(String country) {
        editor.putString("country", country);
        editor.commit();
    }

    public String getCountry() {
        return mPref.getString("country", "");
    }

}
