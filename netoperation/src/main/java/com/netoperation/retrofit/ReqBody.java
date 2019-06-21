package com.netoperation.retrofit;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.JsonObject;
import com.netoperation.util.RetentionDef;


public class ReqBody {

    public static JsonObject recommendation(String tagId, String aid, String siteId,
                                            String sectionName, String transactionId) {
        JsonObject object = new JsonObject();
        object.addProperty("tagId", tagId);
        object.addProperty("aid", aid);
        object.addProperty("siteId", siteId);
        object.addProperty("section", sectionName);
        object.addProperty("transactionId", transactionId);
        return object;
    }

    public static JsonObject login(String emailId, String contact, String password, String deviceId, String siteId, String originUrl) {
        JsonObject object = new JsonObject();
        object.addProperty("password", password);
        object.addProperty("emailId", emailId);
        object.addProperty("contact", contact);
        object.addProperty("deviceId", deviceId);
        object.addProperty("siteId", siteId);
        object.addProperty("originUrl", originUrl);
        return object;
    }

    public static JsonObject signUp(String otp, String countryCode, String password, String emailId, String contact, String deviceId, String siteId, String originUrl) {
        JsonObject object = new JsonObject();
        object.addProperty("otp", otp);
        object.addProperty("countryCode", countryCode);
        object.addProperty("password", password);
        object.addProperty("emailId", emailId);
        object.addProperty("contact", contact);
        object.addProperty("deviceId", deviceId);
        object.addProperty("siteId", siteId);
        object.addProperty("originUrl", originUrl);
        return object;
    }

    public static JsonObject logout(String userId, String deviceId, String siteId) {
        JsonObject object = new JsonObject();
        object.addProperty("userId", userId);
        object.addProperty("deviceId", deviceId);
        object.addProperty("siteId", siteId);
        return object;
    }


    public static JsonObject userVerification(String emailId, String contact, String siteId, @RetentionDef.userVerificationMode String event) {
        JsonObject object = new JsonObject();
        object.addProperty("emailId", emailId);
        object.addProperty("contact", contact);
        object.addProperty("siteId", siteId);
        object.addProperty("event", event);
        return object;
    }

    public static JsonObject resetPassword(String otp, String password, String countryCode, String emailId, String siteId, String originUrl, String contact) {
        JsonObject object = new JsonObject();
        object.addProperty("otp", otp);
        object.addProperty("password", password);
        object.addProperty("countryCode", countryCode);
        object.addProperty("emailId", emailId);
        object.addProperty("siteId", siteId);
        object.addProperty("originUrl", originUrl);
        object.addProperty("contact", contact);
        return object;
    }

    public static JsonObject userInfo(String deviceId, String siteId) {
        JsonObject object = new JsonObject();
        object.addProperty("deviceId", deviceId);
        object.addProperty("siteId", siteId);
        return object;
    }

    public static JsonObject editProfile(String userId, String siteId, String emailId, String contact) {
        JsonObject object = new JsonObject();
        object.addProperty("userId", userId);
        object.addProperty("siteId", siteId);
        object.addProperty("emailId", emailId);
        object.addProperty("contact", contact);
        return object;
    }

    public static JsonObject validateOtp(String otp, String emailOrContact) {
        JsonObject object = new JsonObject();
        object.addProperty("otp", otp);
        object.addProperty("userName", emailOrContact);
        return object;
    }


    public static JsonObject updatePassword(String userId, String oldPassword, String newPassword) {
        JsonObject object = new JsonObject();
        object.addProperty("userId", userId);
        object.addProperty("oldPassword", oldPassword);
        object.addProperty("newPassword", newPassword);
        return object;
    }

    public static JsonObject suspendAccount(String userId, String siteId, String deviceId, String emailId, String contact, String otp) {
        JsonObject object = new JsonObject();
        object.addProperty("userId", userId);
        object.addProperty("siteId", siteId);
        object.addProperty("deviceId", deviceId);
        object.addProperty("emailId", emailId);
        object.addProperty("contact", contact);
        object.addProperty("otp", otp);
        object.addProperty("event", "suspendAccount");
        return object;
    }

    public static JsonObject deleteAccount(@NonNull String userId, @NonNull String siteId, @NonNull String deviceId, @NonNull String emailId, @NonNull String contact, @NonNull String otp) {
        JsonObject object = new JsonObject();
        object.addProperty("userId", userId);
        object.addProperty("siteId", siteId);
        object.addProperty("deviceId", deviceId);
        object.addProperty("emailId", emailId);
        object.addProperty("contact", contact);
        object.addProperty("otp", otp);
        object.addProperty("event", "deleteAccount");
        return object;
    }

    public static JsonObject setUserPreference(@NonNull String userId, @NonNull String siteId, @NonNull String deviceId, @NonNull JsonObject preferences) {
        JsonObject object = new JsonObject();
        object.addProperty("userId", userId);
        object.addProperty("siteId", siteId);
        object.addProperty("deviceId", deviceId);

        preferences.addProperty("event", "set");

        object.add("preferences", preferences);

        return object;
    }

    public static JsonObject getUserPreference(@NonNull String userId, @NonNull String siteId, @NonNull String deviceId, @Nullable JsonObject preferences) {
        JsonObject object = new JsonObject();
        object.addProperty("userId", userId);
        object.addProperty("siteId", siteId);
        object.addProperty("deviceId", deviceId);

        if(preferences != null) {
            preferences.addProperty("event", "get");
            object.add("preferences", preferences);
        }

        return object;
    }

    public static JsonObject createBookmarkFavLike(@NonNull String userId, @NonNull String siteId,
                                                   @NonNull String contentId, @NonNull int isBookmark,
                                                   @NonNull int isFavourite) {
        JsonObject object = new JsonObject();
        object.addProperty("userId", userId);
        object.addProperty("siteId", siteId);
        object.addProperty("contentId", contentId);
        object.addProperty("isBookmark", isBookmark);
        object.addProperty("isFavourite", isFavourite);

        return object;
    }

    public static JsonObject updateProfile(String emailId, String contact, String siteId,
                                           String userId, String FullName, String DOB,
                                           String Gender, String Profile_Country, String Profile_State) {
        JsonObject object = new JsonObject();
        object.addProperty("emailId", emailId);
        object.addProperty("contact", contact);
        object.addProperty("siteId", siteId);
        object.addProperty("userId", userId);
        object.addProperty("FullName", FullName);
        object.addProperty("DOB", DOB);
        object.addProperty("Gender", Gender);
        object.addProperty("Profile_Country", Profile_Country);
        object.addProperty("Profile_State", Profile_State);
        return object;
    }




}
