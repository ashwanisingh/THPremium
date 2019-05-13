package com.netoperation.net;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.netoperation.retrofit.ReqBody;
import com.netoperation.retrofit.ServiceFactory;
import com.netoperation.util.NetConstants;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ApiManager {

    private static final String TAG = ApiManager.class.getCanonicalName();

    private ApiManager() {}

    public static void userVerification(RequestCallback<Boolean> callback, String email, String contact, String siteId) {

        Observable<JsonElement> observable = ServiceFactory.getServiceAPIs().userVerification(ReqBody.userVerification(email, contact, siteId, NetConstants.EVENT_SIGNUP));
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(value->{
                    if(((JsonObject) value).has("status")) {
                        String status = ((JsonObject) value).get("status").getAsString();
                        if(status.equalsIgnoreCase("success")) {
                            return true;
                        }
                        return false;
                    }
                    return false;
                        }
                )
                .subscribe(value->{
                    if(callback != null) {
                        callback.onNext(value);
                    }

                }, throwable->{
                    if(callback != null) {
                        callback.onError(throwable, NetConstants.EVENT_SIGNUP);
                    }
                        }, ()->{
                    if(callback != null) {
                        callback.onComplete(NetConstants.EVENT_SIGNUP);
                    }
                        });

    }

    public static void userLogin(RequestCallback<Boolean> callback, String email, String contact,
                                 String siteId, String password, String deviceId, String originUrl) {

        Observable<JsonElement> observable = ServiceFactory.getServiceAPIs().login(ReqBody.login(email, contact, password, deviceId, siteId, originUrl));
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(value->{
                            if(((JsonObject) value).has("status")) {
                                String status = ((JsonObject) value).get("status").getAsString();
                                if(status.equalsIgnoreCase("success")) {

                                }
                                else  if(status.equalsIgnoreCase("Fail")) {

                                }

                            }
                            return false;
                        }
                )
                .subscribe(value->{
                    if(callback != null) {
                        callback.onNext(value);
                    }

                }, throwable->{
                    if(callback != null) {
                        callback.onError(throwable, NetConstants.EVENT_SIGNUP);
                    }
                }, ()->{
                    if(callback != null) {
                        callback.onComplete(NetConstants.EVENT_SIGNUP);
                    }
                });

    }

}
