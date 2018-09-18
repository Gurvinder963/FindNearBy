
package com.findnearby.network;


import android.content.Context;


import com.findnearby.AppConstants;
import com.findnearby.BuildConfig;
import com.findnearby.di.AppContext;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class NetworkModule {

    private static final int CACHE_SIZE = 4;
    private static final int READ_TIMEOUT = 100 * 1000;
    private static final int CONNECTION_TIMEOUT = 100 * 1000;

    public NetworkModule() {
    }

    @Provides
    @Singleton
    ApiServices provideAPIService(Retrofit retrofit) {
        return retrofit.create(ApiServices.class);
    }

    @Provides
    @Singleton
    Retrofit providesRetrofit(OkHttpClient okHttpClient, RxJava2CallAdapterFactory rxJava2CallAdapterFactory, GsonConverterFactory gsonConverterFactory) {
        return new Retrofit.Builder()
                .baseUrl(AppConstants.API_ENDPOINT)
                .client(okHttpClient)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .addConverterFactory(gsonConverterFactory)
                .build();
    }


    @Singleton
    @Provides
    @Inject
    OkHttpClient getOkHttpClient(@AppContext Context context) {
        HttpLoggingInterceptor logger = new HttpLoggingInterceptor();
        logger.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.BODY);


        int cacheSize = CACHE_SIZE * 1024 * 1024;

        return new OkHttpClient.Builder()
                .cache(new Cache(new File(context.getCacheDir(), "http"), cacheSize))

                .retryOnConnectionFailure(false)
                .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)

                .addInterceptor(logger)
                .build();
    }


    @Singleton
    @Provides
    RxJava2CallAdapterFactory providesRxJava2CallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Singleton
    @Provides
    GsonConverterFactory providesGsonConverterFactory() {
        return GsonConverterFactory.create();
    }


}

