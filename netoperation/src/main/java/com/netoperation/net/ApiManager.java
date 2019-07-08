package com.netoperation.net;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.netoperation.db.BookmarkTable;
import com.netoperation.db.BreifingTable;
import com.netoperation.db.DashboardTable;
import com.netoperation.db.THPDB;
import com.netoperation.db.UserProfileDao;
import com.netoperation.db.UserProfileTable;
import com.netoperation.model.BreifingModel;
import com.netoperation.model.KeyValueModel;
import com.netoperation.model.MorningBean;
import com.netoperation.model.PersonaliseDetails;
import com.netoperation.model.PersonaliseModel;
import com.netoperation.model.PrefListModel;
import com.netoperation.model.RecoBean;
import com.netoperation.model.RecomendationData;
import com.netoperation.model.SearchedArticleModel;
import com.netoperation.model.TxnDataBean;
import com.netoperation.model.UserProfile;
import com.netoperation.retrofit.ReqBody;
import com.netoperation.retrofit.ServiceFactory;
import com.netoperation.util.NetConstants;
import com.netoperation.util.RetentionDef;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ApiManager {

    private static final String TAG = ApiManager.class.getCanonicalName();

    private ApiManager() {
    }

    public static void userVerification(RequestCallback<KeyValueModel> callback,
                                        String email, String contact, String siteId, @RetentionDef.userVerificationMode String event) {
        Observable<JsonElement> observable = ServiceFactory.getServiceAPIs().userVerification(ReqBody.userVerification(email, contact, siteId, event));
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(value -> {
                            KeyValueModel keyValueModel = new KeyValueModel();

                            if (((JsonObject) value).has("status")) {
                                String status = ((JsonObject) value).get("status").getAsString();
                                keyValueModel.setState(status);
                                if (status.equalsIgnoreCase("success")) {

                                } else {
                                    String reason = ((JsonObject) value).get("reason").getAsString();
                                    keyValueModel.setName(reason);
                                }
                            }
                            return keyValueModel;
                        }
                )
                .subscribe(value -> {
                    if (callback != null) {
                        callback.onNext(value);
                    }

                }, throwable -> {
                    if (callback != null) {
                        callback.onError(throwable, NetConstants.EVENT_SIGNUP);
                    }
                }, () -> {
                    if (callback != null) {
                        callback.onComplete(NetConstants.EVENT_SIGNUP);
                    }
                });
    }


    public static void resetPassword(RequestCallback<KeyValueModel> callback, String otp, String password, String countryCode, String emailId, String siteId, String originUrl, String contact) {
        Observable<JsonElement> observable = ServiceFactory.getServiceAPIs().resetPassword(ReqBody.resetPassword(otp, password, countryCode, emailId, siteId, originUrl, contact));
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(value -> {
                            KeyValueModel keyValueModel = new KeyValueModel();
                            if (((JsonObject) value).has("status")) {
                                String status = ((JsonObject) value).get("status").getAsString();
                                keyValueModel.setState(status);
                                if (status.equalsIgnoreCase("success")) {

                                } else {
                                    String reason = ((JsonObject) value).get("reason").getAsString();
                                    keyValueModel.setName(reason);
                                }
                            }
                            return keyValueModel;
                        }
                )
                .subscribe(value -> {
                    if (callback != null) {
                        callback.onNext(value);
                    }

                }, throwable -> {
                    if (callback != null) {
                        callback.onError(throwable, NetConstants.EVENT_SIGNUP);
                    }
                }, () -> {
                    if (callback != null) {
                        callback.onComplete(NetConstants.EVENT_SIGNUP);
                    }
                });
    }

    public static Observable<Boolean> generateOtp(String email, String contact, String siteId, String otpEventType) {

        Observable<JsonElement> observable = ServiceFactory.getServiceAPIs().userVerification(ReqBody.userVerification(email, contact, siteId, otpEventType));
        return observable.subscribeOn(Schedulers.newThread())
                .map(value -> {
                            if (((JsonObject) value).has("status")) {
                                String status = ((JsonObject) value).get("status").getAsString();
                                if (status.equalsIgnoreCase("success")) {
                                    return true;
                                }
                                return false;
                            }
                            return false;
                        }
                );
    }

    public static void validateOTP(RequestCallback<Boolean> callback, String otp, String emailOrContact) {

        Observable<JsonElement> observable = ServiceFactory.getServiceAPIs().validateOtp(ReqBody.validateOtp(otp, emailOrContact));
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(value -> {
                            if (((JsonObject) value).has("status")) {
                                String status = ((JsonObject) value).get("status").getAsString();
                                if (status.equalsIgnoreCase("success")) {
                                    return true;
                                }
                                return false;
                            }
                            return false;
                        }
                )
                .subscribe(value -> {
                    if (callback != null) {
                        callback.onNext(value);
                    }

                }, throwable -> {
                    if (callback != null) {
                        callback.onError(throwable, NetConstants.EVENT_SIGNUP);
                    }
                }, () -> {
                    if (callback != null) {
                        callback.onComplete(NetConstants.EVENT_SIGNUP);
                    }
                });
    }



    public static Observable<KeyValueModel> userSignUp(Context context, String otp, String countryCode, String password, String email, String contact, String deviceId, String siteId, String originUrl) {
        return ServiceFactory.getServiceAPIs().signup(ReqBody.signUp(otp, countryCode, password, email, contact, deviceId, siteId, originUrl))
                .subscribeOn(Schedulers.newThread())
                .map(value -> {

                            KeyValueModel keyValueModel = new KeyValueModel();

                            if (((JsonObject) value).has("status")) {
                                String status = ((JsonObject) value).get("status").getAsString();
                                keyValueModel.setState(status);
                                if (status.equalsIgnoreCase("success")) {
                                    String userInfo = ((JsonObject) value).get("userInfo").getAsString();

                                    JSONObject obj = new JSONObject(userInfo);

                                    String emailId = ((JsonObject) value).get("emailId").getAsString();
                                    String contact_ = ((JsonObject) value).get("contact").getAsString();
                                    String redirectUrl = ((JsonObject) value).get("redirectUrl").getAsString();
                                    String userId = ((JsonObject) value).get("userId").getAsString();
                                    String reason = ((JsonObject) value).get("reason").getAsString();

                                    String authors_preference = obj.getString("authors_preference");
                                    String cities_preference = obj.getString("cities_preference");
                                    String topics_preference = obj.getString("topics_preference");

                                    String address_state = obj.getString("address_state");
                                    String address_pincode = obj.getString("address_pincode");
                                    String address_house_no = obj.getString("address_house_no");
                                    String address_city = obj.getString("address_city");
                                    String address_street = obj.getString("address_street");
                                    String address_fulllname = obj.getString("address_fulllname");
                                    String address_landmark = obj.getString("address_landmark");
                                    String address_default_option = obj.getString("address_default_option");
                                    String address_location = obj.getString("address_location");

                                    String Profile_Country = obj.getString("Profile_Country");
                                    String Profile_State = obj.getString("Profile_State");

                                    String FullName = obj.getString("FullName");
                                    String Gender = obj.getString("Gender");
                                    String DOB = obj.getString("DOB");

                                    String isNew = ((JsonObject) value).get("isNew").getAsString();
                                    String fid = ((JsonObject) value).get("fid").getAsString();
                                    String tid = ((JsonObject) value).get("tid").getAsString();
                                    String gid = ((JsonObject) value).get("gid").getAsString();


                                    THPDB thpdb = THPDB.getInstance(context);
                                    // Deleting Previous Profile DB
                                    thpdb.userProfileDao().deleteAll();

                                    UserProfile userProfile = new UserProfile();

                                    userProfile.setEmailId(emailId);
                                    userProfile.setContact(contact_);
                                    userProfile.setRedirectUrl(redirectUrl);
                                    userProfile.setUserId(userId);
                                    userProfile.setReason(reason);

                                    userProfile.setAuthors_preference(authors_preference);
                                    userProfile.setCities_preference(cities_preference);
                                    userProfile.setTopics_preference(topics_preference);

                                    userProfile.setAddress_state(address_state);
                                    userProfile.setAddress_pincode(address_pincode);
                                    userProfile.setAddress_house_no(address_house_no);
                                    userProfile.setAddress_city(address_city);
                                    userProfile.setAddress_street(address_street);
                                    userProfile.setAddress_fulllname(address_fulllname);
                                    userProfile.setAddress_landmark(address_landmark);
                                    userProfile.setAddress_default_option(address_default_option);
                                    userProfile.setAddress_location(address_location);

                                    userProfile.setProfile_Country(Profile_Country);
                                    userProfile.setProfile_State(Profile_State);

                                    userProfile.setFullName(FullName);
                                    userProfile.setGender(Gender);
                                    userProfile.setDOB(DOB);

                                    userProfile.setIsNew(isNew);
                                    userProfile.setFid(fid);
                                    userProfile.setTid(tid);
                                    userProfile.setGid(gid);

                                    UserProfileTable userProfileTable = new UserProfileTable(userId, userProfile);

                                    thpdb.userProfileDao().insertUserProfile(userProfileTable);


                                } else {
                                    String reason = ((JsonObject) value).get("reason").getAsString();
                                    keyValueModel.setName(reason);
                                }


                            }
                            return keyValueModel;
                        }
                );
    }



    public static Observable<KeyValueModel> userLogin(String email, String contact,
                                 String siteId, String password, String deviceId, String originUrl) {

        Observable<JsonElement> observable = ServiceFactory.getServiceAPIs().login(ReqBody.login(email, contact, password, deviceId, siteId, originUrl));
        return observable.subscribeOn(Schedulers.newThread())
                .map(value -> {
                                KeyValueModel keyValueModel = new KeyValueModel();
                            if (((JsonObject) value).has("status")) {


                                if (((JsonObject) value).has("status")) {
                                    String status = ((JsonObject) value).get("status").getAsString();
                                    String reason = ((JsonObject) value).get("reason").getAsString();
                                    if (((JsonObject) value).has("userId")) {
                                        String userId = ((JsonObject) value).get("userId").getAsString();
                                        keyValueModel.setCode(userId);
                                    }
                                    keyValueModel.setState(status);
                                    keyValueModel.setName(reason);

                                }

                            }
                            return keyValueModel;
                        }
                );

    }


    public static Observable<Boolean> getUserInfo(Context context, String siteId, String deviceId, String usrId) {

        Observable<JsonElement> observable = ServiceFactory.getServiceAPIs().userInfo(ReqBody.userInfo(deviceId, siteId, usrId));
        return observable.subscribeOn(Schedulers.newThread())
                .map(responseFromServer -> {
                            if (((JsonObject) responseFromServer).has("status")) {
                                String status = ((JsonObject) responseFromServer).get("status").getAsString();
                                if (status.equalsIgnoreCase("success")) {

                                    String userInfo = ((JsonObject) responseFromServer).get("userInfo").getAsString();

                                    JSONObject userInfoJsonObj = new JSONObject(userInfo);

                                    String emailId = ((JsonObject) responseFromServer).get("emailId").getAsString();
                                    String contact_ = ((JsonObject) responseFromServer).get("contact").getAsString();
                                    String redirectUrl = "";
                                    String reason = "";
                                    if(((JsonObject) responseFromServer).has("redirectUrl")) {
                                        redirectUrl = ((JsonObject) responseFromServer).get("redirectUrl").getAsString();
                                    }
                                    String userId = ((JsonObject) responseFromServer).get("userId").getAsString();
                                    if(((JsonObject) responseFromServer).has("reason")) {
                                        reason = ((JsonObject) responseFromServer).get("reason").getAsString();
                                    }

                                    String authors_preference = userInfoJsonObj.getString("authors_preference");
                                    String cities_preference = userInfoJsonObj.getString("cities_preference");
                                    String topics_preference = userInfoJsonObj.getString("topics_preference");

                                    String address_state = userInfoJsonObj.getString("address_state");
                                    String address_pincode = userInfoJsonObj.getString("address_pincode");
                                    String address_house_no = userInfoJsonObj.getString("address_house_no");
                                    String address_city = userInfoJsonObj.getString("address_city");
                                    String address_street = userInfoJsonObj.getString("address_street");
                                    String address_fulllname = userInfoJsonObj.getString("address_fulllname");
                                    String address_landmark = userInfoJsonObj.getString("address_landmark");
                                    String address_default_option = userInfoJsonObj.getString("address_default_option");
                                    String address_location = userInfoJsonObj.getString("address_location");

                                    String Profile_Country = userInfoJsonObj.getString("Profile_Country");
                                    String Profile_State = userInfoJsonObj.getString("Profile_State");

                                    String FullName = userInfoJsonObj.getString("FullName");
                                    String Gender = userInfoJsonObj.getString("Gender");
                                    String DOB = userInfoJsonObj.getString("DOB");

                                    String isNew = "";
                                    if(((JsonObject) responseFromServer).has("isNew")) {
                                        isNew = ((JsonObject) responseFromServer).get("isNew").getAsString();
                                    }

                                    String fid = ((JsonObject) responseFromServer).get("fid").getAsString();
                                    String tid = ((JsonObject) responseFromServer).get("tid").getAsString();
                                    String gid = ((JsonObject) responseFromServer).get("gid").getAsString();

                                    UserProfile userProfile = new UserProfile();

                                    if(userInfoJsonObj.has("userPlanList")) {
                                        JSONArray userPlanList = userInfoJsonObj.getJSONArray("userPlanList");
                                        int planSize = userPlanList.length();
                                        for (int i = 0; i < planSize; i++) {
                                            JSONObject object = (JSONObject) userPlanList.get(i);
                                            String planId = object.getString("planId");
                                            String planName = object.getString("planName");
                                            double amount = object.getDouble("amount");
                                            String statusPlan = object.getString("status");
                                            String validity = object.getString("validity");
                                            String nextRenewal = object.getString("nextRenewal");
                                            String sDate = object.getString("sDate");
                                            String eDate = object.getString("eDate");
                                            int isActive = object.getInt("isActive");

                                            TxnDataBean bean = new TxnDataBean();
                                            bean.setAmount(amount);
                                            bean.setPlanId(planId);
                                            bean.setPlanName(planName);
                                            bean.setTrxnstatus(statusPlan);
                                            bean.setValidity(validity);
                                            bean.setNextRenewal(nextRenewal);
                                            bean.setsDate(sDate);
                                            bean.seteDate(eDate);
                                            bean.setIsActive(isActive);

                                            userProfile.addUserPlanList(bean);
                                        }
                                    }

                                    THPDB thpdb = THPDB.getInstance(context);
                                    // Deleting Previous Profile DB
                                    thpdb.userProfileDao().deleteAll();


                                    userProfile.setEmailId(emailId);
                                    userProfile.setContact(contact_);
                                    userProfile.setRedirectUrl(redirectUrl);
                                    userProfile.setUserId(userId);
                                    userProfile.setReason(reason);

                                    userProfile.setAuthors_preference(authors_preference);
                                    userProfile.setCities_preference(cities_preference);
                                    userProfile.setTopics_preference(topics_preference);

                                    userProfile.setAddress_state(address_state);
                                    userProfile.setAddress_pincode(address_pincode);
                                    userProfile.setAddress_house_no(address_house_no);
                                    userProfile.setAddress_city(address_city);
                                    userProfile.setAddress_street(address_street);
                                    userProfile.setAddress_fulllname(address_fulllname);
                                    userProfile.setAddress_landmark(address_landmark);
                                    userProfile.setAddress_default_option(address_default_option);
                                    userProfile.setAddress_location(address_location);

                                    userProfile.setProfile_Country(Profile_Country);
                                    userProfile.setProfile_State(Profile_State);

                                    userProfile.setFullName(FullName);
                                    userProfile.setGender(Gender);
                                    userProfile.setDOB(DOB);

                                    userProfile.setIsNew(isNew);
                                    userProfile.setFid(fid);
                                    userProfile.setTid(tid);
                                    userProfile.setGid(gid);

                                    UserProfileTable userProfileTable = new UserProfileTable(userId, userProfile);

                                    thpdb.userProfileDao().insertUserProfile(userProfileTable);

                                    return true;

                                } else if (status.equalsIgnoreCase("Fail")) {

                                }

                            }
                            return false;
                        }
                );

    }

    public static Observable<List<RecoBean>> getRecommendationFromServer(final Context context,
                                                                         String userid, final @RetentionDef.Recomendation String recotype,
                                                                         String size, String siteid) {
        Observable<RecomendationData> observable = ServiceFactory.getServiceAPIs().getRecommendation(userid, recotype, size, siteid, "app");
        return observable.subscribeOn(Schedulers.newThread())
                .timeout(10000, TimeUnit.MILLISECONDS)
                .map(value -> {
                            List<RecoBean> beans = value.getReco();
                            if (context == null) {
                                return beans;
                            }
                            THPDB thp = THPDB.getInstance(context);
                            if (beans != null && beans.size() > 0) {
                                if(!recotype.equalsIgnoreCase(NetConstants.RECO_TEMP_NOT_EXIST)) {
                                    thp.dashboardDao().deleteAll(recotype);
                                }
                                for (RecoBean bean : beans) {

                                    if(recotype.equalsIgnoreCase(NetConstants.RECO_bookmarks)) {
                                        BookmarkTable bookmarkTable = new BookmarkTable(bean.getArticleId(), bean);
                                        thp.bookmarkTableDao().insertBookmark(bookmarkTable);
                                    }

                                    DashboardTable dashboardTable = new DashboardTable(bean.getArticleId(), recotype, bean);
                                    thp.dashboardDao().insertDashboard(dashboardTable);
                                }
                            }
                            return beans;
                        }
                );

    }


    public static Observable<List<RecoBean>> getRecommendationFromDB(final Context context,
                                                                     final @RetentionDef.Recomendation String recotype) {
        Observable<RecomendationData> observable = Observable.just(new RecomendationData());
        return observable.subscribeOn(Schedulers.newThread())
                .map(value -> {
                            List<RecoBean> beans = new ArrayList<>();
                            if (context == null) {
                                return beans;
                            }
                            THPDB thp = THPDB.getInstance(context);
                            if(recotype.equalsIgnoreCase(NetConstants.RECO_bookmarks)) {
                                List<BookmarkTable> bookmarkTable = thp.bookmarkTableDao().getAllBookmark();
                                if (bookmarkTable != null) {
                                    for (BookmarkTable dash : bookmarkTable) {
                                        beans.add(dash.getBean());
                                    }
                                }
                            } else {
                                List<DashboardTable> dashboardTable = thp.dashboardDao().getAllDashboardBean(recotype);
                                if (dashboardTable != null) {
                                    for (DashboardTable dash : dashboardTable) {
                                        beans.add(dash.getBean());
                                    }
                                }
                            }
                            return beans;
                        }
                );

    }


    public static Observable<RecoBean> isExistInBookmark(Context context, final String aid) {
        return Observable.just(aid)
                .subscribeOn(Schedulers.io())
                .map(articleId -> {
                    List<BookmarkTable> bookmarkTable = THPDB.getInstance(context).bookmarkTableDao().getBookmarkArticles(articleId);
                    if (bookmarkTable != null && bookmarkTable.size() > 0) {
                        return bookmarkTable.get(0).getBean();
                    }
                    return new RecoBean();
                })
                .observeOn(AndroidSchedulers.mainThread());

    }

    public static Observable createBookmark(Context context, final RecoBean articleBean) {
        return Observable.just(articleBean)
                .subscribeOn(Schedulers.io())
                .map(new Function<RecoBean, Boolean>() {
                    @Override
                    public Boolean apply(RecoBean articleBean) throws Exception {
                        RecoBean bean = new RecoBean();
                        bean.setArticleId(articleBean.getArticleId());
                        bean.setArticleSection(articleBean.getArticleSection());
                        bean.setArticletitle(articleBean.getArticletitle());
                        bean.setArticletype(articleBean.getArticletype());
                        bean.setAuthor(articleBean.getAuthor());
                        bean.setThumbnailUrl(articleBean.getThumbnailUrl());
                        bean.setPubDate(articleBean.getPubDate());
                        bean.setPubDateTime(articleBean.getPubDateTime());
                        bean.setRecotype(articleBean.getRecotype());
                        bean.setRank(articleBean.getRank());
                        bean.setIsBookmark(articleBean.getIsBookmark());
                        bean.setIsFavourite(articleBean.getIsFavourite());

                        BookmarkTable bookmarkTable = new BookmarkTable(articleBean.getArticleId(), bean);
                        THPDB.getInstance(context).bookmarkTableDao().insertBookmark(bookmarkTable);
                        return true;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable<RecoBean> updateBookmark(Context context, String aid, int like) {
        Observable<String> observable = Observable.just(aid);
        return observable.subscribeOn(Schedulers.newThread())
                .map(new Function<String, RecoBean>() {
                    @Override
                    public RecoBean apply(String model) {

                        THPDB thp = THPDB.getInstance(context);

                        BookmarkTable bookmarkTable = thp.bookmarkTableDao().getBookmarkArticle(model);

                        if (bookmarkTable != null) {
                            RecoBean recoBean = bookmarkTable.getBean();
                            recoBean.setIsFavourite(like);
                            thp.bookmarkTableDao().updateBookmark(aid, recoBean);
                            return recoBean;
                        }

                        Log.i("", "");
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Observable createUnBookmark(Context context, final String aid) {
        return Observable.just(aid)
                .subscribeOn(Schedulers.io())
                .map(value -> {
                    return THPDB.getInstance(context).bookmarkTableDao().deleteBookmarkArticle(aid) == 1;
                })
                .observeOn(AndroidSchedulers.mainThread());
    }


    public static Observable<Boolean> createBookmarkFavLike(@NonNull String userId, @NonNull String siteId,
                                                            @NonNull String contentId, @NonNull int bookmarkVal,
                                                            @NonNull int favoriteVal) {
        Observable<JsonElement> observable = ServiceFactory.getServiceAPIs().createBookmarkFavLike(ReqBody.createBookmarkFavLike(userId, siteId, contentId, bookmarkVal, favoriteVal));
        return observable.subscribeOn(Schedulers.newThread())
                .map(value -> {
                    if (((JsonObject) value).has("status")) {
                        String status = ((JsonObject) value).get("status").getAsString();
                        if (status.equalsIgnoreCase("success")) {
                            return true;
                        } else if (status.equalsIgnoreCase("Fail")) {
                            return false;
                        }

                    }
                    return false;
                });
    }

    public static Observable isExistFavNdLike(Context context, final String aid) {
        return Observable.just(aid)
                .subscribeOn(Schedulers.io())
                .map(articleId -> {
                    THPDB thp = THPDB.getInstance(context);
                    DashboardTable dashboardTable = thp.dashboardDao().getSingleDashboardBean(aid);
                    if (dashboardTable != null) {
                        return dashboardTable.getBean().getIsFavourite();
                    }
                    return 0;
                })
                .observeOn(AndroidSchedulers.mainThread());

    }


    public static Observable isExistInAllArticle(Context context, final String aid) {
        return Observable.just(aid)
                .subscribeOn(Schedulers.io())
                .map(new Function<String, Boolean>() {
                    @Override
                    public Boolean apply(String aid) throws Exception {
                        boolean isContain = false;

                        THPDB thp = THPDB.getInstance(context);

                        RecoBean recoBean = new RecoBean();
                        recoBean.setArticleId(aid);

                        DashboardTable dashboardTable = thp.dashboardDao().getSingleDashboardBean(aid);
                        if (dashboardTable != null) {
                            if (dashboardTable.getAid().equals(aid)) {
                                isContain = true;
                            }
                        }

                        if (!isContain) {

                        }

                        return isContain;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static final Observable<RecoBean> articleDetailFromServer(Context context, String aid) {
        String url = NetConstants.SEARCH_BY_ARTICLE_ID_URL + aid;
        Observable<SearchedArticleModel> observable = ServiceFactory.getServiceAPIs().searchArticleByIDFromServer(url);
        return observable.subscribeOn(Schedulers.newThread())
                .map(new Function<SearchedArticleModel, RecoBean>() {
                    @Override
                    public RecoBean apply(SearchedArticleModel model) {

                        THPDB thp = THPDB.getInstance(context);

                        DashboardTable dashboardTable = thp.dashboardDao().getSingleDashboardBean(aid);
                        if (dashboardTable != null) {
                            RecoBean recoBean = dashboardTable.getBean();
                            if (model.getData().size() > 0) {
                                recoBean.setDescription(model.getData().get(0).getDe());
                                recoBean.setLeadText(model.getData().get(0).getAl());
                                recoBean.setIMAGES(model.getData().get(0).getMe());
                                recoBean.setYoutubeVideoId(model.getData().get(0).getYoutube_video_id());
                                thp.dashboardDao().updateRecobean(aid, recoBean);
                            }
                            return recoBean;
                        } else {
                            RecoBean recoBean = new RecoBean();
                            if (model.getData().size() > 0) {
                                recoBean.setDescription(model.getData().get(0).getDe());
                                recoBean.setLeadText(model.getData().get(0).getAl());
                                recoBean.setArticletitle(model.getData().get(0).getTi());
                                recoBean.setPubDateTime(model.getData().get(0).getPd());
                                recoBean.setArticleId("" + model.getData().get(0).getAid());
                                recoBean.setIMAGES(model.getData().get(0).getMe());

                                ArrayList<String> authList = new ArrayList();
                                authList.add(model.getData().get(0).getAu());
                                recoBean.setAuthor(authList);

                                recoBean.setArticletype(model.getData().get(0).getArticleType());

                                DashboardTable table = new DashboardTable(recoBean.getArticleId(), NetConstants.RECO_TEMP_NOT_EXIST, recoBean);

                                thp.dashboardDao().insertDashboard(table);
                                return recoBean;

                            }
                        }

                        Log.i("", "");
                        return null;
                    }
                });

    }


    public static Observable<RecoBean> updateLike(Context context, String aid, int like) {
        Observable<String> observable = Observable.just(aid);
        return observable.subscribeOn(Schedulers.newThread())
                .map(new Function<String, RecoBean>() {
                    @Override
                    public RecoBean apply(String model) {

                        THPDB thp = THPDB.getInstance(context);

                        DashboardTable dashboardTable = thp.dashboardDao().getSingleDashboardBean(aid);

                        if (dashboardTable != null) {
                            RecoBean recoBean = dashboardTable.getBean();
                            recoBean.setIsFavourite(like);
                            thp.dashboardDao().updateRecobean(aid, recoBean);
                            return recoBean;
                        }
                        Log.i("", "");
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());
    }


    /**
     * Gets Breifing Data from server
     * @param context
     * @param breifingUrl
     * @return
     */
    public static Observable<List<RecoBean>> getBreifingFromServer(final Context context, String breifingUrl) {
        Observable<BreifingModel> observable = ServiceFactory.getServiceAPIs().getBriefing(breifingUrl);
        return observable.subscribeOn(Schedulers.newThread())
                .timeout(10000, TimeUnit.MILLISECONDS)
                .map(value -> {


                            List<MorningBean> morningBeans = value.getMorning();
                            List<MorningBean> noonBeans = value.getNoon();
                            List<MorningBean> eveningBeans = value.getEvening();

                            List<RecoBean> allBriefing = new ArrayList<>();
                            List<RecoBean> morningBriefing = new ArrayList<>();
                            List<RecoBean> noonBriefing = new ArrayList<>();
                            List<RecoBean> eveningBriefing = new ArrayList<>();

                            for(MorningBean bean : morningBeans) {
                                RecoBean reco = new RecoBean();
                                reco.setArticleId(bean.getArticleId());
                                reco.setArticleSection(bean.getSectionName());
                                reco.setPubDate(bean.getOriginalDate());
                                reco.setPubDateTime(bean.getPublishedDate());
                                reco.setLocation(bean.getLocation());
                                reco.setTitle(bean.getTitle());
                                reco.setArticletitle(bean.getTitle());
                                reco.setArticleLink(bean.getArticleLink());
                                reco.setGmt(bean.getGmt());
                                reco.setYoutubeVideoId(bean.getYoutubeVideoId());
                                reco.setDescription(bean.getDescription());
                                reco.setShortDescription(bean.getShortDescription());
                                reco.setVideoId(bean.getVideoId());
                                reco.setArticleType(bean.getArticleType());

                                String thumbUrl = bean.getThumbnailUrl();
                                ArrayList<String> tu = new ArrayList<>();
                                tu.add(thumbUrl);
                                reco.setThumbnailUrl(tu);

                                reco.setTimeToRead(bean.getTimeToRead());
                                reco.setAuthor(bean.getAuthor());
                                reco.setMedia(bean.getMedia());

                                reco.setLeadText(bean.getLeadText());

                                morningBriefing.add(reco);
                            }

                            for(MorningBean bean : noonBeans) {
                                RecoBean reco = new RecoBean();
                                reco.setArticleId(bean.getArticleId());
                                reco.setArticleSection(bean.getSectionName());
                                reco.setPubDate(bean.getOriginalDate());
                                reco.setPubDateTime(bean.getPublishedDate());
                                reco.setLocation(bean.getLocation());
                                reco.setTitle(bean.getTitle());
                                reco.setArticletitle(bean.getTitle());
                                reco.setArticleLink(bean.getArticleLink());
                                reco.setGmt(bean.getGmt());
                                reco.setYoutubeVideoId(bean.getYoutubeVideoId());
                                reco.setDescription(bean.getDescription());
                                reco.setShortDescription(bean.getShortDescription());
                                reco.setVideoId(bean.getVideoId());
                                reco.setArticleType(bean.getArticleType());

                                String thumbUrl = bean.getThumbnailUrl();
                                ArrayList<String> tu = new ArrayList<>();
                                tu.add(thumbUrl);
                                reco.setThumbnailUrl(tu);

                                reco.setTimeToRead(bean.getTimeToRead());
                                reco.setAuthor(bean.getAuthor());
                                reco.setMedia(bean.getMedia());

                                reco.setLeadText(bean.getLeadText());

                                noonBriefing.add(reco);
                            }

                            for(MorningBean bean : eveningBeans) {
                                RecoBean reco = new RecoBean();
                                reco.setArticleId(bean.getArticleId());
                                reco.setArticleSection(bean.getSectionName());
                                reco.setPubDate(bean.getOriginalDate());
                                reco.setPubDateTime(bean.getPublishedDate());
                                reco.setLocation(bean.getLocation());
                                reco.setTitle(bean.getTitle());
                                reco.setArticletitle(bean.getTitle());
                                reco.setArticleLink(bean.getArticleLink());
                                reco.setGmt(bean.getGmt());
                                reco.setYoutubeVideoId(bean.getYoutubeVideoId());
                                reco.setDescription(bean.getDescription());
                                reco.setShortDescription(bean.getShortDescription());
                                reco.setVideoId(bean.getVideoId());
                                reco.setArticleType(bean.getArticleType());

                                String thumbUrl = bean.getThumbnailUrl();
                                ArrayList<String> tu = new ArrayList<>();
                                tu.add(thumbUrl);
                                reco.setThumbnailUrl(tu);

                                reco.setTimeToRead(bean.getTimeToRead());
                                reco.setAuthor(bean.getAuthor());
                                reco.setMedia(bean.getMedia());

                                reco.setLeadText(bean.getLeadText());

                                eveningBriefing.add(reco);
                            }

                            allBriefing.addAll(morningBriefing);
                            allBriefing.addAll(noonBriefing);
                            allBriefing.addAll(eveningBriefing);
                            if (context == null) {
                                return allBriefing;
                            }
                            THPDB thp = THPDB.getInstance(context);
                            thp.breifingDao().deleteAll();

                            BreifingTable breifingTable = new BreifingTable();
                            breifingTable.setEvening(eveningBriefing);
                            breifingTable.setNoon(noonBriefing);
                            breifingTable.setMorning(morningBriefing);
                            thp.breifingDao().insertBreifing(breifingTable);
                            return allBriefing;
                        }
                );

    }

    /**
     * Gets Breifing Data from local database
     * @param context
     * @param breifingType
     * @return
     */
    public static Observable<List<RecoBean>> getBreifingFromDB(final Context context, final String breifingType) {
        return Observable.just("BreifingItem")
                .subscribeOn(Schedulers.newThread())
                .timeout(10000, TimeUnit.MILLISECONDS)
                .map(value -> {
                            List<RecoBean> briefingItems = new ArrayList<>();

                            THPDB thp = THPDB.getInstance(context);

                            BreifingTable breifingTable = thp.breifingDao().getBreifingTable();

                            if(breifingType.equals(NetConstants.BREIFING_ALL)) {
                                briefingItems.addAll(breifingTable.getMorning());
                                briefingItems.addAll(breifingTable.getNoon());
                                briefingItems.addAll(breifingTable.getEvening());
                            } else if(breifingType.equals(NetConstants.BREIFING_MORNING)) {
                                briefingItems.addAll(breifingTable.getMorning());
                            } else if(breifingType.equals(NetConstants.BREIFING_NOON)) {
                                briefingItems.addAll(breifingTable.getNoon());
                            } else if(breifingType.equals(NetConstants.BREIFING_EVENING)) {
                                briefingItems.addAll(breifingTable.getEvening());
                            }

                            return briefingItems;
                        }
                );

    }


    public static Observable<UserProfile> getUserProfile(Context context) {
        return Observable.just("userProfile")
                .subscribeOn(Schedulers.newThread())
                .map(value-> {
                            UserProfileDao dao = THPDB.getInstance(context).userProfileDao();
                            if (dao.getUserProfileTable() == null) {
                                return new UserProfile();
                            }
                            return dao.getUserProfileTable().getUserProfile();
                        }
                );
    }

    public static Observable<ArrayList<KeyValueModel>> getCountry() {
        return ServiceFactory.getServiceAPIs().getCountry("country")
                .subscribeOn(Schedulers.newThread())
                .map(countryModel->
                        countryModel
                );
    }

    public static Observable<ArrayList<KeyValueModel>> getState(String country) {
        return ServiceFactory.getServiceAPIs().getState("state", country)
                .subscribeOn(Schedulers.newThread())
                .map(stateModel->
                        stateModel
                );
    }

    public static Observable<Boolean> updateProfile(Context context, UserProfile userProfile, String siteId,
                                                        String FullName, String DOB,
                                                        String Gender, String Profile_Country, String Profile_State) {
        return ServiceFactory.getServiceAPIs().updateProfile(ReqBody.updateProfile(userProfile.getEmailId(), userProfile.getContact(),
                siteId, userProfile.getUserId(), FullName, DOB, Gender, Profile_Country, Profile_State))
                .subscribeOn(Schedulers.newThread())
                .map(value-> {
                    if (((JsonObject) value).has("status")) {
                        String status = ((JsonObject) value).get("status").getAsString();
                        if (status.equalsIgnoreCase("success")) {
                            userProfile.setFullName(FullName);
                            userProfile.setDOB(DOB);
                            userProfile.setGender(Gender);
                            userProfile.setProfile_Country(Profile_Country);
                            userProfile.setProfile_State(Profile_State);
                            THPDB thpdb = THPDB.getInstance(context);
                            thpdb.userProfileDao().updateUserProfile(userProfile.getUserId(), userProfile);

                            return true;
                        }
                        return false;
                    }
                    return false;
                        }
                );
    }

    public static Observable<Boolean> updateAddress(Context context, UserProfile userProfile, String siteId,
                                                    String address_house_no, String address_street, String address_landmark,
                                                    String address_pincode, String address_state, String address_city,
                                                     String address_default_option, String address_fulllname) {
        return ServiceFactory.getServiceAPIs().updateAddress(ReqBody.updateAddress(userProfile.getEmailId(),
                userProfile.getContact(), siteId, userProfile.getUserId(), address_house_no,  address_street, address_landmark,
                address_pincode, address_state, address_city, address_default_option, address_fulllname))
                .subscribeOn(Schedulers.newThread())
                .map(value-> {
                            if (((JsonObject) value).has("status")) {
                                String status = ((JsonObject) value).get("status").getAsString();
                                if (status.equalsIgnoreCase("success")) {
                                    userProfile.setAddress_house_no(address_house_no);
                                    userProfile.setAddress_street(address_street);
                                    userProfile.setAddress_landmark(address_landmark);
                                    userProfile.setAddress_pincode(address_pincode);
                                    userProfile.setAddress_state(address_state);
                                    userProfile.setAddress_city(address_city);
                                    userProfile.setAddress_default_option(address_default_option);
                                    THPDB thpdb = THPDB.getInstance(context);
                                    thpdb.userProfileDao().updateUserProfile(userProfile.getUserId(), userProfile);

                                    return true;
                                }
                                return false;
                            }
                            return false;
                        }
                );
    }


    /**
     * To get transaction history
     * @param userId
     * @return
     */
    public static Observable<List<TxnDataBean>> getTxnHistory(String userId) {
        return ServiceFactory.getServiceAPIs().getTxnHistory(userId, "0")
                .subscribeOn(Schedulers.newThread())
                .map(txnModel -> {
                            List<TxnDataBean> txnDataBeans = txnModel.getTxnData();
                            if (txnDataBeans == null) {
                                txnDataBeans = new ArrayList<>();
                            }
                            return txnDataBeans;
                        }
                );
    }

    /**
     * To update Password
     * @param userId
     * @param oldPasswd
     * @param newPasswd
     * @return
     */
    public static Observable<KeyValueModel> updatePassword(String userId, String oldPasswd, String newPasswd) {
        return ServiceFactory.getServiceAPIs().updatePassword(ReqBody.updatePassword(userId, oldPasswd, newPasswd))
                .subscribeOn(Schedulers.newThread())
                .map(value-> {
                            KeyValueModel keyValueModel = new KeyValueModel();
                            if (((JsonObject) value).has("status")) {
                                String status = ((JsonObject) value).get("status").getAsString();
                                String reason = ((JsonObject) value).get("reason").getAsString();
                                keyValueModel.setState(status);
                                keyValueModel.setName(reason);
                            }

                            return keyValueModel;
                        }
                );
    }

    /**
     * To suspend user account
     * @param userId
     * @param siteId
     * @param deviceId
     * @param emailId
     * @param contact
     * @param otp
     * @return
     */
    public static Observable<KeyValueModel> suspendAccount(String userId, String siteId, String deviceId, String emailId, String contact, String otp) {
        return ServiceFactory.getServiceAPIs().suspendAccount(ReqBody.suspendAccount(userId, siteId, deviceId, emailId, contact, otp))
                .subscribeOn(Schedulers.newThread())
                .map(value-> {
                            KeyValueModel keyValueModel = new KeyValueModel();
                            if (((JsonObject) value).has("status")) {
                                String status = ((JsonObject) value).get("status").getAsString();
                                String reason = ((JsonObject) value).get("reason").getAsString();
                                keyValueModel.setState(status);
                                keyValueModel.setName(reason);
                            }

                            return keyValueModel;
                        }
                );
    }

    /**
     * To Delete user account
     * @param userId
     * @param siteId
     * @param deviceId
     * @param emailId
     * @param contact
     * @param otp
     * @return
     */
    public static Observable<KeyValueModel> deleteAccount(String userId, String siteId, String deviceId, String emailId, String contact, String otp) {
        return ServiceFactory.getServiceAPIs().deleteAccount(ReqBody.deleteAccount(userId, siteId, deviceId, emailId, contact, otp))
                .subscribeOn(Schedulers.newThread())
                .map(value-> {
                            KeyValueModel keyValueModel = new KeyValueModel();
                            if (((JsonObject) value).has("status")) {
                                String status = ((JsonObject) value).get("status").getAsString();
                                String reason = ((JsonObject) value).get("reason").getAsString();
                                keyValueModel.setState(status);
                                keyValueModel.setName(reason);
                            }

                            return keyValueModel;
                        }
                );
    }

    /**
     * To logout user
     * @param userId
     * @param siteId
     * @param deviceId
     * @return
     */
    public static Observable<KeyValueModel> logout(Context context, String userId, String siteId, String deviceId) {
        return ServiceFactory.getServiceAPIs().logout(ReqBody.logout(userId, siteId, deviceId))
                .subscribeOn(Schedulers.newThread())
                .map(value-> {
                    KeyValueModel keyValueModel = new KeyValueModel();
                    if (((JsonObject) value).has("status")) {
                        String status = ((JsonObject) value).get("status").getAsString();
                        //Status is not in response as the Api Doc
                        //String reason = ((JsonObject) value).get("reason").getAsString();
                        //keyValueModel.setName(reason);
                        keyValueModel.setState(status);
                    }

                    if (keyValueModel.getState() != null &&
                            keyValueModel.getState().equalsIgnoreCase("success")) {
                        THPDB db = THPDB.getInstance(context);
                        db.userProfileDao().deleteAll();
                    }

                    return keyValueModel;
                });
    }


    /**
     * To get user plan info
     * @param userId
     * @param siteId
     * @return
     */
    public static Observable<List<TxnDataBean>> getUserPlanInfo(String userId, String siteId) {
        return ServiceFactory.getServiceAPIs().getUserPlanInfo(userId, siteId)
                .subscribeOn(Schedulers.newThread())
                .map(value->
                    value.getUserPlanList()
                );
    }

    /**
     * To get user plan info
     * @param userId
     * @param siteId
     * @return
     */
    public static Observable<List<TxnDataBean>> getRecommendedPlan(String userId, String siteId) {
        return ServiceFactory.getServiceAPIs().getRecommendedPlan(siteId, "25", "1", "1")
                .subscribeOn(Schedulers.newThread())
                .map(value->
                        value.getCamapignList()
                );
    }


    public static Observable<PrefListModel> getAllPreferences() {
        Observable<PrefListModel> observable = ServiceFactory.getServiceAPIs().getAllPreferences("https://subscription.thehindu.com/js/preference.json");
        return observable.subscribeOn(Schedulers.newThread())
                .timeout(10000, TimeUnit.MILLISECONDS)
                .map(value -> {
                    // For topics
                    PersonaliseDetails topics = value.getTopics();
                    topics.setName(topics.getName());
                    value.addTopics(topics);

                    // For cities
                    PersonaliseDetails cities = value.getCities();
                    cities.setName(cities.getName());
                    value.addCities(cities);


                    // For authors
                    PersonaliseDetails authors = value.getAuthors();
                    authors.setName(authors.getName());
                    value.addAuthors(authors);

                    return value;

                });
    }

    public static Observable<Boolean> setPersonalise(@NonNull String userId, @NonNull String siteId, @NonNull String deviceId, @NonNull ArrayList<String> topics,
                                                     @NonNull ArrayList<String> cities, @NonNull ArrayList<String> authors) {
        JsonObject personaliseObj = new JsonObject();

        JsonArray ja = new JsonArray();
        for(String topic : topics) {
            ja.add(topic);
        }
        personaliseObj.add("topics", ja);

        ja = new JsonArray();
        for(String city : cities) {
            ja.add(city);
        }
        personaliseObj.add("city", ja);

        ja = new JsonArray();
        for(String author : authors) {
            ja.add(author);
        }
        personaliseObj.add("author", ja);

        return ServiceFactory.getServiceAPIs().setPersonalise(ReqBody.setUserPreference(userId, siteId, deviceId, personaliseObj))
                .subscribeOn(Schedulers.newThread())
                .map(value-> {
                            if (((JsonObject) value).has("status")) {
                                String status = ((JsonObject) value).get("status").getAsString();
                                if (status.equalsIgnoreCase("success")) {
                                    return true;
                                }
                                return false;
                            }
                            return false;
                        }
                );
    }

    public static Observable<PrefListModel> getPersonalise(String userId, String siteId, String deviceId) {
        return ServiceFactory.getServiceAPIs().getPersonalise(ReqBody.getUserPreference(userId, siteId, deviceId))
                .subscribeOn(Schedulers.newThread())
                .map(selectedPrefModel -> {

                    ArrayList<String> cities = selectedPrefModel.getPreferences().getCity();
                    ArrayList<String> authors = selectedPrefModel.getPreferences().getAuthor();
                    ArrayList<String> topics = selectedPrefModel.getPreferences().getTopics();

                    PersonaliseDetails citiesDetails = new PersonaliseDetails();
                    PersonaliseDetails authorsDetails = new PersonaliseDetails();
                    PersonaliseDetails topicsDetails = new PersonaliseDetails();

                    if(cities != null) {
                        for (String city : cities) {
                            PersonaliseModel model = new PersonaliseModel();
                            model.setTitle(city);
                            model.setValue(city);
                            citiesDetails.addPersonalise(model);
                        }
                    }

                    if(authors != null) {
                        for (String author : authors) {
                            PersonaliseModel model = new PersonaliseModel();
                            model.setTitle(author);
                            model.setValue(author);
                            authorsDetails.addPersonalise(model);
                        }
                    }

                    if(topics != null) {
                        for (String topic : topics) {
                            PersonaliseModel model = new PersonaliseModel();
                            model.setTitle(topic);
                            model.setValue(topic);
                            topicsDetails.addPersonalise(model);
                        }
                    }

                    PrefListModel prefListModel = new PrefListModel();
                    prefListModel.addAuthors(authorsDetails);
                    prefListModel.addTopics(topicsDetails);
                    prefListModel.addCities(citiesDetails);

                    return prefListModel;

                });

    }


    public static Observable<KeyValueModel> createSubscription(String userid,
                                          String trxnid,
                                          String amt,
                                          String channel,
                                          String siteid,
                                          String planid,
                                          String plantype,
                                          String billingchannel,
                                          String validity,
                                          String contact,
                                          String currency,
                                          String tax,
                                          String netAmount) {
        return ServiceFactory.getServiceAPIs().createSubscription(ReqBody.createSubscription(userid, trxnid,
                amt, channel, siteid, planid, plantype, billingchannel, validity, contact, currency, tax, netAmount))
                .subscribeOn(Schedulers.newThread())
                .map(jsonElement -> {
                    KeyValueModel keyValueModel = new KeyValueModel();
                    if (((JsonObject) jsonElement).has("status")) {
                        String status = ((JsonObject) jsonElement).get("status").getAsString();
                        String reason = ((JsonObject) jsonElement).get("msg").getAsString();
                        keyValueModel.setState(status);
                        keyValueModel.setName(reason);
                    }
                    return keyValueModel;
                });


    }


    public static Observable<String> socialLogin(String deviceId, String originUrl, String provider,
                                                 String socialId, String userEmail, String userName) {

        Observable<JsonElement> observable = ServiceFactory.getServiceAPIs().socialLogin(ReqBody.socialLogin(deviceId, originUrl, provider, socialId, userEmail, userName));
        return observable.subscribeOn(Schedulers.newThread())
                .map(value -> {
                            if (((JsonObject) value).has("status")) {
                                String status = ((JsonObject) value).get("status").getAsString();
                                if (status.equalsIgnoreCase("success")) {
                                    String userId = ((JsonObject) value).get("userId").getAsString();
                                    return userId;
                                } else if (status.equalsIgnoreCase("Fail")) {

                                }

                            }
                            return "";
                        }
                );

    }


}
