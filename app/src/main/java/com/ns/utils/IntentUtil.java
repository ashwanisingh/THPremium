package com.ns.utils;

import android.content.Context;
import android.content.Intent;

import com.ns.activity.BecomeMemberActivity;
import com.ns.activity.SignInAndUpActivity;
import com.ns.activity.SubscriptionActivity;
import com.ns.activity.THP_DetailActivity;
import com.ns.activity.THP_WebActivity;
import com.ns.activity.UserProfileActivity;

public class IntentUtil {

    public static void openMemberActivity(Context context, String from) {
        Intent intent = new Intent(context, BecomeMemberActivity.class);
        intent.putExtra("from", from);
        context.startActivity(intent);
    }

    public static void openUserProfileActivity(Context context, String from) {
        Intent intent = new Intent(context, UserProfileActivity.class);
        intent.putExtra("from", from);
        context.startActivity(intent);
    }

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

    public static void openDetailActivity(Context context, String from, String url, int clickedPosition, String articleId) {
        Intent intent = new Intent(context, THP_DetailActivity.class);
        intent.putExtra("from", from);
        intent.putExtra("url", url);
        intent.putExtra("clickedPosition", clickedPosition);
        intent.putExtra("articleId", articleId);
        context.startActivity(intent);
    }

    public static void openWebActivity(Context context, String from, String url) {
        Intent intent = new Intent(context, THP_WebActivity.class);
        intent.putExtra("from", from);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }
}
