package com.netoperation.net;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.netoperation.db.BookmarkTable;
import com.netoperation.db.DashboardTable;
import com.netoperation.db.THPDB;
import com.netoperation.model.RecoBean;
import com.netoperation.model.RecomendationData;
import com.netoperation.model.SearchedArticleModel;
import com.netoperation.retrofit.ReqBody;
import com.netoperation.retrofit.ServiceFactory;
import com.netoperation.util.NetConstants;
import com.netoperation.util.RetentionDef;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
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

    public static Observable<List<RecoBean>> getRecommendationFromServer(final Context context,
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
                .map(value -> {
                    return THPDB.getInstance(context).bookmarkTableDao().deleteBookmarkArticle(aid) == 1;
                })
                .observeOn(AndroidSchedulers.mainThread());
    }


    public static Observable<Boolean> createBookmarkFavLike(RequestCallback requestCallback, @NonNull String userId, @NonNull String siteId,
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
                .observeOn(AndroidSchedulers.mainThread())
                ;
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


}
