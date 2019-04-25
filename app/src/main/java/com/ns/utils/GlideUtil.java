package com.ns.utils;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;


public class GlideUtil {

    public static void loadImageFromFragment(Fragment fragment, ImageView imageView, String url, int placeholderResId) {
        GlideApp.with(fragment)
                .load(url)
                .placeholder(placeholderResId)
                .into(imageView);


    }

    public static void loadImage(Context context, final ImageView imageView, String url, int placeholderResId) {
        GlideApp.with(context)
                .load(url)
                .placeholder(placeholderResId)
                .into(imageView);
    }

    public static void loadImage(Context context, final ImageView imageView, String url) {
        GlideApp.with(context)
                .load(url)
                .into(imageView);
    }


    public static void loadProfileImage(Fragment fragment, ImageView imageView, String url, int placeholderResId) {
        GlideApp.with(fragment)
                .load(url)
                .signature(new ObjectKey(System.currentTimeMillis()))
                .placeholder(placeholderResId)
                .into(imageView);

    }

    public static void loadProfileImage(Context context, ImageView imageView, String url, int placeholderResId) {
        GlideApp.with(context)
                .load(url)
                .signature(new ObjectKey(System.currentTimeMillis()))
                .placeholder(placeholderResId)
                .into(imageView);

    }

    public static void loadImageThumbnail(Context context, String imgUrl, ImageView imgView, int placeholder) {
        Glide.with(context)
                .load(imgUrl)
                .apply(new RequestOptions()
                        .placeholder(placeholder))
                .into(imgView);
    }
}
