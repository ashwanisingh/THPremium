package com.ns.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;

import com.netoperation.model.MeBean;
import com.netoperation.model.RecoBean;
import com.ns.activity.AppTabActivity;
import com.ns.activity.BecomeMemberActivity;
import com.ns.activity.DemoActivity;
import com.ns.activity.ImageGallaryActivity;
import com.ns.activity.ImageGallaryVerticleActivity;
import com.ns.activity.SignInAndUpActivity;
import com.ns.activity.THPPersonaliseActivity;
import com.ns.activity.THP_BookmarkActivity;
import com.ns.activity.THP_DetailActivity;
import com.ns.activity.THP_WebActivity;
import com.ns.activity.THP_YouTubeFullScreenActivity;
import com.ns.activity.UserProfileActivity;
import com.ns.model.ImageGallaryUrl;
import com.ns.thpremium.BuildConfig;
import com.ns.thpremium.R;

import java.net.URLEncoder;
import java.util.ArrayList;

public class IntentUtil {

    public static void openContentListingActivity(Context context, String from) {
        Intent intent = new Intent(context, AppTabActivity.class);
        intent.putExtra("from", from);
        context.startActivity(intent);
    }

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
        Intent intent = new Intent(context, UserProfileActivity.class);
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


    public static void openCommentActivity(Context context, RecoBean articlesBean) {
        String Vukkle_API_KEY = BuildConfig.VUUKLE_API_KEY;

        String articleId = articlesBean.getArticleId();
        String articleLink = articlesBean.getArticleUrl();
        String articleTitle = articlesBean.getArticletitle();
        String imgUrl = "";
        if(articlesBean.getThumbnailUrl().size() > 0) {
            imgUrl = articlesBean.getThumbnailUrl().get(0);
        }

        Intent intent = new Intent(context, THP_WebActivity.class);

        String host = BuildConfig.ORIGIN_URL;

        String url = "https://cdn.vuukle.com/amp.html?" +
                "apiKey="+ Vukkle_API_KEY+"&" +
                "host="+host+"&" +
                "socialAuth=false"+"&" +
                "id="+articleId+"&" +
                "img="+imgUrl+"&" +
                "title="+ URLEncoder.encode(articleTitle)+"&" +
                "url="+articleLink;
        intent.putExtra("from", context.getResources().getString(R.string.comments));
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    /**
     * Ask the current default engine to launch the matching INSTALL_TTS_DATA activity
     * so the required TTS files are properly installed.
     */
    public static void installVoiceData(Context activity) {
        Intent intent = new Intent(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setPackage("com.google.android.tts"/*replace with the package name of the target TTS engine*/);
        try {
            Log.v("installVoiceData", "Installing voice data: " + intent.toUri(0));
            activity.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Log.e("installVoiceData", "Failed to install TTS data, no acitivty found for " + intent + ")");
        }
    }

    public static void openHorizontalGalleryActivity(Context context, ArrayList<ImageGallaryUrl> imageGallaryUrls, ArrayList<MeBean> meBeanList, int position) {
        if (meBeanList != null && meBeanList.size() > 0) {
            imageGallaryUrls = new ArrayList<>();
            for (MeBean imageBean : meBeanList) {
                imageGallaryUrls.add(new ImageGallaryUrl(imageBean.getListingImgUrl(), imageBean.getBigImgUrl(), imageBean.getCa(), ""));
            }
        }

        if (imageGallaryUrls != null) {
            Intent intent = new Intent(context, ImageGallaryActivity.class);
            intent.putParcelableArrayListExtra("ImageUrl", imageGallaryUrls);
            intent.putExtra("selectedPosition", position);
            context.startActivity(intent);
        }
    }




    public static void openVerticleGalleryActivity(Context context, ArrayList<MeBean> mImageList, String title) {
        ArrayList<ImageGallaryUrl> mImageUrlList = null;
        if (mImageList != null && mImageList.size() > 0) {
            mImageUrlList = new ArrayList<ImageGallaryUrl>();
            for (MeBean imageBean : mImageList) {
                mImageUrlList.add(new ImageGallaryUrl(imageBean.getListingImgUrl(), imageBean.getBigImgUrl(), imageBean.getCa(), title));
            }
        }
        if (mImageUrlList != null) {
            Intent intent = new Intent(context, ImageGallaryVerticleActivity.class);
            intent.putParcelableArrayListExtra("ImageUrl", mImageUrlList);
            intent.putExtra("title", title);
            context.startActivity(intent);
        }

    }

    public static void openPersonaliseActivity(Context context, String from) {
        Intent intent = new Intent(context, THPPersonaliseActivity.class);
        intent.putExtra("from", from);
        context.startActivity(intent);
    }

    public static void openBookmarkActivity(Context context, String from, String userId) {
        Intent intent = new Intent(context, THP_BookmarkActivity.class);
        intent.putExtra("from", from);
        intent.putExtra("userId", userId);
        context.startActivity(intent);
    }

    public static void openYoutubeActivity(Context context, String videoId) {
            Intent intent = new Intent(context, THP_YouTubeFullScreenActivity.class);
            intent.putExtra("videoId", videoId);
            context.startActivity(intent);
    }

    public static void openDemoActivity(Context context) {
        Intent intent = new Intent(context, DemoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public static void openAppSetting(Context context) {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",
                BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
