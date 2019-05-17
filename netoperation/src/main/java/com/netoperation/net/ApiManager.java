package com.netoperation.net;

import android.content.Context;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.netoperation.db.BookmarkTable;
import com.netoperation.db.DashboardTable;
import com.netoperation.db.THPDB;
import com.netoperation.model.RecoBean;
import com.netoperation.model.RecomendationData;
import com.netoperation.retrofit.ReqBody;
import com.netoperation.retrofit.ServiceFactory;
import com.netoperation.util.NetConstants;
import com.netoperation.util.RetentionDef;

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

    public static Observable<List<RecoBean>> getRecommendation(final Context context,
                                                               String userid, final @RetentionDef.Recomendation String recotype,
                                                               String size, String siteid) {
        Observable<RecomendationData> observable = ServiceFactory.getServiceAPIs().getRecommendation(userid, recotype, size, siteid);
        return observable.subscribeOn(Schedulers.newThread())
                .timeout(10000, TimeUnit.MILLISECONDS)
                .map(value -> {
                            List<RecoBean> beans = value.getReco();
                            if (context == null) {
                                return beans;
                            }
                            THPDB thp = THPDB.getInstance(context);
                            if (beans != null && beans.size() > 0) {
                                if (recotype.equals(NetConstants.RECO_ALL)) {
                                    DashboardTable dashboardTable = new DashboardTable();
                                    dashboardTable.setBeans(beans);
                                    thp.dashboardDao().insertDashboard(dashboardTable);
                                } else if (recotype.equals(NetConstants.RECO_trending)) {

                                } else if (recotype.equals(NetConstants.RECO_briefcase)) {

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
                            if (recotype.equals(NetConstants.RECO_ALL)) {
                                List<DashboardTable> dashboardTable = thp.dashboardDao().getAllDashboardBean();
                                if(dashboardTable != null) {
                                    for(DashboardTable dash : dashboardTable) {
                                        beans.addAll(dash.getBeans());
                                    }
                                }
                            } else if (recotype.equals(NetConstants.RECO_trending)) {

                            } else if (recotype.equals(NetConstants.RECO_briefcase)) {

                            }
                            return beans;
                        }
                );

    }


    public static Observable isExistInBookmark(Context context, final String aid) {
        return Observable.just(aid)
                .subscribeOn(Schedulers.io())
                .map(articleId->{
                    List<BookmarkTable> bookmarkTable = THPDB.getInstance(context).bookmarkTableDao().getBookmarkArticles(articleId);
                    if(bookmarkTable != null && bookmarkTable.size() > 0) {
                        return bookmarkTable != null && bookmarkTable.get(0).getAid().equals(articleId);
                    }
                    return false;
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

                        BookmarkTable bookmarkTable = new BookmarkTable(articleBean.getArticleId(), bean);
                        THPDB.getInstance(context).bookmarkTableDao().insertBookmark(bookmarkTable);
                        return true;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                ;
    }

    public static Observable createUnBookmark(Context context, final String aid) {
        return Observable.just(aid)
                .subscribeOn(Schedulers.io())
                .map(value->{
                    return THPDB.getInstance(context).bookmarkTableDao().deleteBookmarkArticle(aid)==1;
                })
                .observeOn(AndroidSchedulers.mainThread())
                ;
    }

}
