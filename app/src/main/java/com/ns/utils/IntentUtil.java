package com.ns.utils;

import android.content.Context;
import android.content.Intent;

import com.ns.activity.SignInAndUpActivity;
import com.ns.activity.SubscriptionActivity;

public class IntentUtil {

    public static void openSignInOrUpActivity(Context context, String from) {
        Intent intent = new Intent(context, SignInAndUpActivity.class);
        intent.putExtra("from", from);
        context.startActivity(intent);
    }

    public static void openSubscriptionActivity(Context context, String from) {
        Intent intent = new Intent(context, SubscriptionActivity.class);
        intent.putExtra("from", from);
        context.startActivity(intent);
    }
}
