package com.findnearby.di;

import android.app.Application;
import android.content.Context;


import com.findnearby.network.NetworkModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Singleton
@Module(includes = {NetworkModule.class})

public class AppModule {

    private Application app;

    public AppModule(Application app) {
        this.app = app;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return app;
    }

    @Provides
    @Singleton
    @AppContext
    Context providesContext() {
        return app;
    }



}
