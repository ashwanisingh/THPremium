package com.netoperation.retrofit;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by ashwanisingh on 04/23/19.
 */

public class ServiceFactory {


    private static ServiceAPIs sServiceAPIs;

    private static String sBaseUrl;

    public static ServiceAPIs getServiceAPIs(String baseUrl) {
        if(sServiceAPIs == null) {
            sBaseUrl = baseUrl;
            sServiceAPIs = createServiceAPIs();
        }
        return sServiceAPIs;
    }

    private ServiceFactory() {
    }

    /**
     * The CityService communicates with the json api of the city provider.
     */
    private static ServiceAPIs createServiceAPIs() {
        final Retrofit retrofit = createRetrofit();
        return retrofit.create(ServiceAPIs.class);
    }

    /**
     * This creates OKHttpClient
     */
    private static OkHttpClient createOkHttpClient() {
        final OkHttpClient.Builder httpClient =
                new OkHttpClient.Builder();
        return httpClient.build();
    }

    /**
     * Creates a pre configured Retrofit instance
     */
    private static Retrofit createRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(sBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // <- add this
                .client(createOkHttpClient())
                .build();
    }
}
