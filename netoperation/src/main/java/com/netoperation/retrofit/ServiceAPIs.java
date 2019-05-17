package com.netoperation.retrofit;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.netoperation.model.RecomendationData;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by ashwanisingh on 04/23/19.
 */

public interface ServiceAPIs {


    @POST("/userreco")
    Observable<JsonElement> recommendation(@Body JsonObject recommendationBody);

    @POST("/taiauth/login/HINDU")
    Observable<JsonElement> login(@Body JsonObject loginBody);

    @POST("/taiauth/regSubmit/HINDU")
    Observable<JsonElement> signup(@Body JsonObject logoutBody);

    @POST("/taiauth/logout/HINDU")
    Observable<JsonElement> logout(@Body JsonObject loginBody);

    @POST("/taiauth/userVerify/HINDU")
    Observable<JsonElement> userVerification(@Body JsonObject userVerificationBody);

    @POST("/taiauth/resetPassword/HINDU")
    Observable<JsonElement> resetPassword(@Body JsonObject resetPasswordBody);

    @POST("/taiauth/userInfo/HINDU")
    Observable<JsonElement> userInfo(@Body JsonObject userInfoBody);

    @POST("/taiauth/updateUserInfo/HINDU")
    Observable<JsonElement> editProfile(@Body JsonObject editProfileBody);

    @POST("/taiauth/validateOtp/HINDU")
    Observable<JsonElement> validateOtp(@Body JsonObject validateOtpBody);

    @POST("/taiauth/updatePassword/HINDU")
    Observable<JsonElement> updatePassword(@Body JsonObject updatePasswordBody);

    @POST("/taiauth/updateAccountStatus/HINDU")
    Observable<JsonElement> suspendAccount(@Body JsonObject suspendAccountBody);

    @POST("/taiauth/updateAccountStatus/HINDU")
    Observable<JsonElement> deleteAccount(@Body JsonObject deleteAccountBody);

    @POST("/taiauth/userPreference/HINDU")
    Observable<JsonElement> getUserPreference(@Body JsonObject getUserPreferenceBody);

    @POST("/taiauth/userPreference/HINDU")
    Observable<JsonElement> setUserPreference(@Body JsonObject setUserPreferenceBody);


    @POST("/mydashboard/userreco/hindu")
    Observable<RecomendationData> getRecommendation(@Query("userid") String userid, @Query("recotype") String recotype,
                                                    @Query("size") String size, @Query("siteid") String siteid );

}
