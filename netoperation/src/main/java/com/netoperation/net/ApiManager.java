package com.netoperation.net;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.netoperation.db.BookmarkTable;
import com.netoperation.db.BreifingTable;
import com.netoperation.db.DashboardTable;
import com.netoperation.db.THPDB;
import com.netoperation.db.UserProfileTable;
import com.netoperation.model.PersonaliseModel;
import com.netoperation.model.BreifingModel;
import com.netoperation.model.MorningBean;
import com.netoperation.model.PrefListModel;
import com.netoperation.model.RecoBean;
import com.netoperation.model.RecomendationData;
import com.netoperation.model.SearchedArticleModel;
import com.netoperation.retrofit.ReqBody;
import com.netoperation.retrofit.ServiceFactory;
import com.netoperation.util.NetConstants;
import com.netoperation.util.RetentionDef;

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

    public static void userVerification(RequestCallback<Boolean> callback, String email, String contact, String siteId) {

        Observable<JsonElement> observable = ServiceFactory.getServiceAPIs().userVerification(ReqBody.userVerification(email, contact, siteId, NetConstants.EVENT_SIGNUP));
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

    public static void userSignUp(RequestCallback<Boolean> callback, String otp, String countryCode, String password, String emailId, String contact, String deviceId, String siteId, String originUrl) {

        Observable<JsonElement> observable = ServiceFactory.getServiceAPIs().signup(ReqBody.signUp(otp, countryCode, password, emailId, contact, deviceId, siteId, originUrl));
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(value -> {
                            if (((JsonObject) value).has("status")) {
                                String status = ((JsonObject) value).get("status").getAsString();
                                String userInfo = ((JsonObject) value).get("userInfo").getAsString();

                                JSONObject obj = new JSONObject(userInfo);

                                Log.i("", "");

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



    public static void userLogin(RequestCallback<Boolean> callback, String email, String contact,
                                 String siteId, String password, String deviceId, String originUrl) {

        Observable<JsonElement> observable = ServiceFactory.getServiceAPIs().login(ReqBody.login(email, contact, password, deviceId, siteId, originUrl));
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(value -> {
                            if (((JsonObject) value).has("status")) {
                                String status = ((JsonObject) value).get("status").getAsString();
                                if (status.equalsIgnoreCase("success")) {

                                } else if (status.equalsIgnoreCase("Fail")) {

                                }

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
                            List<DashboardTable> dashboardTable = thp.dashboardDao().getAllDashboardBean(recotype);
                            if (dashboardTable != null) {
                                for (DashboardTable dash : dashboardTable) {
                                    beans.add(dash.getBean());
                                }
                            }
                            return beans;
                        }
                );

    }


    public static Observable isExistInBookmark(Context context, final String aid) {
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



    public static Observable<PrefListModel> getPrefList(String userid, String siteid, String size, String recotype) {
        Observable<PrefListModel> observable = ServiceFactory.getServiceAPIs().getPrefList(userid, siteid, size, recotype);
        return observable.subscribeOn(Schedulers.newThread())
                .timeout(10000, TimeUnit.MILLISECONDS)
                .map(value -> {
                    // For topics
                    List<String> topics = value.getTopics();
                    for(String stt : topics) {
                        PersonaliseModel model=new PersonaliseModel();
                        model.setName(stt);
                        value.addTopicsModels(model);
                    }

                    // For cities
                    List<String> cities = value.getCities();
                    for(String stc : cities) {
                        PersonaliseModel model=new PersonaliseModel();
                        model.setName(stc);
                        value.addCitiesModels(model);
                    }

                    // For authors
                    List<String> authors = value.getAuthors();
                    for(String sta : authors) {
                        PersonaliseModel model=new PersonaliseModel();
                        model.setName(sta);
                        value.addAuthorsModels(model);
                    }

                    return value;

                });

    }


}
