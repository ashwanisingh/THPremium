package com.netoperation.retrofit;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.netoperation.model.BreifingModel;
import com.netoperation.model.KeyValueModel;
import com.netoperation.model.PrefListModel;
import com.netoperation.model.RecomendationData;
import com.netoperation.model.SearchedArticleModel;
import com.netoperation.model.TransactionHistoryModel;
import com.netoperation.model.UserChoice;
import com.netoperation.model.UserPlanList;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

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


    @GET("/mydashboard/userreco/hindu")
    Observable<RecomendationData> getRecommendation(@Query("userid") String userid, @Query("recotype") String recotype,
                                                    @Query("size") String size, @Query("siteid") String siteid, @Query("requestSource") String requestSource );

    @POST("/mydashboard/userchoice/HINDU")
    Observable<JsonElement> createBookmarkFavLike(@Body JsonObject bookmarkFavLikeBody);

    @GET("/mydashboard/userchoicelist/HINDU")
    Observable<List<UserChoice>> getBookmarkFavLike(@Query("userid") String userid, @Query("siteid") String siteid );

    @GET("")
    Observable<SearchedArticleModel> searchArticleByIDFromServer(@Url String url);


    @GET("")
    Observable<BreifingModel> getBriefing(@Url String url);

    @POST("/mydashboard/preflistreco/hindu")
    Observable<PrefListModel> getPrefList(@Query("userid") String userid, @Query("siteid") String siteid,
                                          @Query("size") String size, @Query("recotype") String recotype);

    @GET("taiauth/list/HINDU")
    Observable<ArrayList<KeyValueModel>> getCountry(@Query("type") String type);

    @GET("taiauth/list/HINDU")
    Observable<ArrayList<KeyValueModel>> getState(@Query("type") String type, @Query("country") String country);

    @POST("taiauth/updateUserInfo/HINDU")
    Observable<JsonElement> updateProfile(@Body JsonObject updateProfile);

    @POST("taiauth/updateUserInfo/HINDU")
    Observable<JsonElement> updateAddress(@Body JsonObject updateProfile);

    @POST("taiauth/userPreference/hindu")
    Observable<JsonElement> setPersonalise(@Body JsonObject updateProfile);

    @GET("charging/transaction/detail/HINDU")
    Observable<TransactionHistoryModel> getTxnHistory(@Query("userid") String userid, @Query("pageno") String pageno);

    @GET("subscription/getuserplaninfo/HINDU")
    Observable<UserPlanList> getUserPlanInfo(@Query("userid") String userid, @Query("siteid") String siteid);







}
