package com.findnearby.di;

import android.app.Application;


import com.findnearby.MyApplication;
import com.findnearby.fragment.SearchViewModel;
import com.findnearby.network.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@AppContext
@Component(modules = AppModule.class)
public interface AppComponent {

    public static AppComponent component(Application app) {
        return DaggerAppComponent.builder().appModule(new AppModule(app)).networkModule(new NetworkModule()).build();
    }

    void inject(MyApplication app);
    void inject(SearchViewModel app);

}


