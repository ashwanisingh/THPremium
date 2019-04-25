package com.ns.utils;

import android.content.Context;
import android.content.Intent;

import com.ns.activity.SignInAndUpActivity;

public class IntentUtil {

    public static void openSignInOrUpActivity(Context context, String from) {
        Intent intent = new Intent(context, SignInAndUpActivity.class);
        intent.putExtra("from", from);
        context.startActivity(intent);
    }
}
